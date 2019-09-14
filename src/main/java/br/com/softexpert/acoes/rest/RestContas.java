package br.com.softexpert.acoes.rest;

import br.com.softexpert.acoes.objects.Conta;
import br.com.softexpert.acoes.objects.Monitoramento;
import br.com.softexpert.acoes.respository.ContasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/")
public class RestContas {
    public static final Logger logger = LoggerFactory.getLogger(RestMonitoramento.class);

    @Autowired
    ContasRepository contasRespository;

    @RequestMapping(value = "/getContas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Conta>> listaContas() {
        List<Conta> funcionarios = contasRespository.findAll();
        return new ResponseEntity<>(funcionarios, HttpStatus.OK);
    }

    @RequestMapping(value = "/novaConta", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Conta> novaConta(@RequestBody Conta conta) {
        Conta cadastrado = contasRespository.save(conta);
        return new ResponseEntity<>(cadastrado, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deletaConta/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletaFuncionario(@PathVariable("id") long id) {
        Conta conta = contasRespository.findById(id);
        if (conta == null) {
            logger.error("Não foi encontrado a conta do ID: ", id);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        contasRespository.delete(conta);
        return new ResponseEntity<Monitoramento>(HttpStatus.NO_CONTENT);


    }

    @RequestMapping(value = "/atualizaConta/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> atualizaFuncionario(@PathVariable("id") long id, @RequestBody Conta atualiza) {
        Conta conta = contasRespository.findById(id);

        if (conta == null) {
            logger.error("Conta do ID: " + id + "não encontrado");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        conta.setValor(atualiza.getValor());
        conta.setEmail(atualiza.getEmail());
        contasRespository.save(conta);
        return new ResponseEntity<Monitoramento>(HttpStatus.OK);
    }
}
