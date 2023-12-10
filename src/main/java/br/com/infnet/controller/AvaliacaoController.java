package br.com.infnet.controller;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Avaliacao;
import br.com.infnet.model.ResponsePayload;
import br.com.infnet.service.AvaliacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvaliacaoController.class);

    @Autowired
    AvaliacaoService avaliacaoService;

    @PostMapping
    public ResponseEntity<ResponsePayload> create(@RequestBody List<Avaliacao> avaliacoes){

        LOGGER.info("Inserindo regitros.");

        avaliacaoService.saveAll(avaliacoes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponsePayload("Livro cadastrado com sucesso!"));
    }

    @GetMapping
    public ResponseEntity getAll(){

        LOGGER.info("Trazendo todos os registros.");

        try {
            List<Avaliacao> avaliacaos = avaliacaoService.getAll();
            HttpHeaders responseHeaders = new HttpHeaders();
            return ResponseEntity.status(HttpStatus.OK)
                  .headers(responseHeaders).body(avaliacaos);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable int id){

        LOGGER.info("Trazendo um registro pelo ID.");

        try{
            Avaliacao avaliacao = avaliacaoService.getById(id);
            return ResponseEntity.ok(avaliacao);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable int id) {

        LOGGER.info("Deletando um registro.");

        try {
            avaliacaoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Avaliação deletada com sucesso!"));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable int id, @RequestBody Avaliacao atualizada){

        LOGGER.info("Editando um registro.");

        try{
            avaliacaoService.update(id,atualizada);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Avaliação alterada com sucesso!"));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }

}
