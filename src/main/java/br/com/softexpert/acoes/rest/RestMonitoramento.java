package br.com.softexpert.acoes.rest;

import br.com.softexpert.acoes.objects.Monitoramento;
import br.com.softexpert.acoes.respository.MonitoramentoRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class RestMonitoramento {
    public static final Logger logger = LoggerFactory.getLogger(RestMonitoramento.class);

    @Autowired
    MonitoramentoRespository monitoramentoRespository;

    @RequestMapping(value = "/getMonitoramentos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Monitoramento>> listaMonitoramento() {
        List<Monitoramento> monitoramentos = monitoramentoRespository.findAll();
        return new ResponseEntity<>(monitoramentos, HttpStatus.OK);
    }

    @RequestMapping(value = "/novoMonitoramento", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Monitoramento> novoMonitoramento(@RequestBody Monitoramento monitoramento) {
        List<Monitoramento> validaMonitoramento = monitoramentoRespository.findAll();
        boolean valida = false;
        if (validaMonitoramento != null && validaMonitoramento.size() > 0) {
            for (Monitoramento val : validaMonitoramento) {
                if (val.getEmpresa().equalsIgnoreCase(monitoramento.getEmpresa())) {
                    valida = false;
                    break;
                } else {
                    valida = true;
                }
            }
            if (valida) {
                Monitoramento cadastrado = monitoramentoRespository.save(monitoramento);
                return new ResponseEntity<>(cadastrado, HttpStatus.CREATED);
            } else {
                logger.error("A empresa " + monitoramento.getEmpresa() + " já está cadastrado no monitoramento");
                return new ResponseEntity<>(HttpStatus.FOUND);
            }
        }else{
            Monitoramento cadastrado = monitoramentoRespository.save(monitoramento);
            return  new ResponseEntity<>(cadastrado, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/deletaMonitoramento/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletaMonitoramento(@PathVariable("id") long id) {
        Monitoramento monitoramento = monitoramentoRespository.findById(id);
        if (monitoramento == null) {
            logger.error("Não foi encontrado a empresa com ID: ", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        monitoramentoRespository.delete(monitoramento);
        return new ResponseEntity<Monitoramento>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value = "/atualizaMonitoramento/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaFuncionario(@PathVariable("id") long id, @RequestBody Monitoramento atualiza) {
        Monitoramento monitoramento = monitoramentoRespository.findById(id);

        if (monitoramento == null) {
            logger.error("Empresa: " + atualiza.getEmpresa() + "não encontrado");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        monitoramento.setEmpresa(atualiza.getEmpresa());
        monitoramento.setValorCompra(atualiza.getValorCompra());
        monitoramento.setValorVenda(atualiza.getValorVenda());
        monitoramentoRespository.save(monitoramento);
        return new ResponseEntity<Monitoramento>(HttpStatus.OK);
    }
}
