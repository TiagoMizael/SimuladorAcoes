package br.com.softexpert.acoes.rest;

import br.com.softexpert.acoes.email.GeradorExcel;
import br.com.softexpert.acoes.objects.*;
import br.com.softexpert.acoes.respository.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@RestController
@RequestMapping("/")
public class RestRegras {

    @Autowired
    private MonitoramentoRespository monitoramentoRespository;
    @Autowired
    private ValoresBolsaRepository valoresBolsaRepository;
    @Autowired
    private AcoesRepository acoesRepository;
    @Autowired
    private ContasRepository contasRepository;
    @Autowired
    private DadosNegociacaoRespository dadosNegociacaoRespository;

    @RequestMapping(value = "/simular", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> simular() {
        try {
            List<Monitoramento> monitoramentos = monitoramentoRespository.findAll();
            List<ValorBolsa> valoresBolsa = valoresBolsaRepository.findAll();
            List<Conta> contas = contasRepository.findAll();
            ArrayList<DadosNegociacao> relatorio = new ArrayList<>();
            int count = 0;
            while (count < 100) {
                new Thread().sleep(5000);
                if (monitoramentos != null) {
                    Random random = new Random();
                    for (Monitoramento monitoramento : monitoramentos) {
                        String empresa = monitoramento.getEmpresa();
                        ValorBolsa valorBolsa = valoresBolsaRepository.findByEmpresa(empresa);
                        double preco = 10 + (1 * random.nextDouble());
                        BigDecimal p = new BigDecimal(preco).setScale(2, RoundingMode.HALF_EVEN);
                        if (valorBolsa != null) {
                            valorBolsa.setEmpresa(empresa);
                            valorBolsa.setValor(p);
                            valoresBolsaRepository.save(valorBolsa);
                        } else {
                            ValorBolsa newValorBolsa = new ValorBolsa();
                            newValorBolsa.setEmpresa(empresa);
                            newValorBolsa.setValor(p);
                            valoresBolsa.add(newValorBolsa);
                            valoresBolsaRepository.save(newValorBolsa);
                        }
                    }
                }
                for (ListIterator<ValorBolsa> it = valoresBolsa.listIterator(); it.hasNext(); ) {
                    ValorBolsa valorBolsa = it.next();
                    Monitoramento monitoramento = monitoramentoRespository.findByEmpresa(valorBolsa.getEmpresa());
                    Acoes acoes = acoesRepository.findByEmpresa(valorBolsa.getEmpresa());
                    DadosNegociacao dadosNegociacao = new DadosNegociacao();
                    if (monitoramento != null) {
                        for (Conta conta : contas) {
                            if (conta.getValor().doubleValue() > 0 &&
                                    valorBolsa.getValor().doubleValue() <= monitoramento.getValorCompra().doubleValue()) {

                                BigDecimal quantidade = conta.getValor().divide(valorBolsa.getValor(), 2, RoundingMode.HALF_EVEN);
                                Date newDate = new Date();
                                String data = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(newDate);

                                dadosNegociacao.setData(data);
                                dadosNegociacao.setQuantidade(quantidade);
                                dadosNegociacao.setEmpresa(monitoramento.getEmpresa());
                                dadosNegociacao.setTipo("Compra");
                                dadosNegociacao.setValor(valorBolsa.getValor());
                                conta.setValor(new BigDecimal(0));
                                relatorio.add(dadosNegociacao);
                                dadosNegociacaoRespository.save(dadosNegociacao);
                                contasRepository.save(conta);
                                count++;

                                if (acoes != null) {
                                    BigDecimal qtdAcoes = acoes.getQuantidade();
                                    acoes.setQuantidade(qtdAcoes.add(quantidade));
                                    acoesRepository.save(acoes);
                                } else {
                                    Acoes newAcoes = new Acoes();
                                    newAcoes.setEmpresa(monitoramento.getEmpresa());
                                    newAcoes.setQuantidade(quantidade);
                                    acoesRepository.save(newAcoes);
                                }

                            }
                            if (valorBolsa.getValor().doubleValue() >= monitoramento.getValorVenda().doubleValue()
                                    && acoes != null && acoes.getQuantidade().doubleValue() > 0) {

                                BigDecimal qtdAcoes = acoes.getQuantidade();
                                Date newDate = new Date();
                                String data = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(newDate);

                                dadosNegociacao.setData(data);
                                dadosNegociacao.setQuantidade(qtdAcoes);
                                dadosNegociacao.setValor(valorBolsa.getValor());
                                dadosNegociacao.setEmpresa(monitoramento.getEmpresa());
                                dadosNegociacao.setTipo("Venda");
                                BigDecimal valorPosVenda = conta.getValor().add(qtdAcoes.multiply(valorBolsa.getValor()))
                                        .setScale(2, RoundingMode.HALF_EVEN);


                                conta.setValor(valorPosVenda);
                                acoes.setQuantidade(new BigDecimal(0));
                                relatorio.add(dadosNegociacao);
                                dadosNegociacaoRespository.save(dadosNegociacao);
                                count++;
                                contasRepository.save(conta);
                                acoesRepository.save(acoes);

                            }
                        }
                    }
                }

            }
            GeradorExcel ge = new GeradorExcel();
            ge.gerar(relatorio, contas);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/deletaRelatorio", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletaRelatorio(@RequestBody FiltroID ids) {
        //  Postgres
        String jdbcUrl = "jdbc:postgresql://localhost:5432/SoftExpert";
        String username = "postgres";
        String password = "postgres";

        //SQL Server
//        String jdbcUrl = "jdbc:sqlserver://localhost;databaseName=SoftExpert";
//        String username = "sa";
//        String password = "123";

        String sql = "delete from tb_dados_negociacao where id>=" + ids.getId1() + "and id <=" + ids.getId2();

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = conn.createStatement();) {
            statement.execute(sql);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
