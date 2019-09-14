package br.com.softexpert.acoes.rest;

import br.com.softexpert.acoes.objects.Acoes;
import br.com.softexpert.acoes.objects.DadosNegociacao;
import br.com.softexpert.acoes.respository.AcoesRepository;
import br.com.softexpert.acoes.respository.DadosNegociacaoRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/")
public class RestHistorico {
    public static final Logger logger = LoggerFactory.getLogger(RestMonitoramento.class);

    @Autowired
    private DadosNegociacaoRespository dadosNegociacaoRespository;

    @Autowired
    private AcoesRepository acoesRepository;

    @RequestMapping(value = "/getHistorico", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DadosNegociacao>> historico() {
        List<DadosNegociacao> historico = dadosNegociacaoRespository.findAll();
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAcoes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Acoes>> acoes() {
        List<Acoes> listaAcoes = acoesRepository.findAll();
        return new ResponseEntity<>(listaAcoes, HttpStatus.OK);
    }


}
