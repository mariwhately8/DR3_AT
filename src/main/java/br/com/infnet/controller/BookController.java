package br.com.infnet.controller;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Book;
import br.com.infnet.model.ResponsePayload;
import br.com.infnet.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;
    @GetMapping
    public ResponseEntity getAll(){

        LOGGER.info("Trazendo todos os livros.");

        List<Book> livros = bookService.getAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders).body(livros);
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable int id){

        LOGGER.info("Trazendo um livro pelo ID.");

        try{
            Book book = bookService.getById(id);
            return ResponseEntity.ok(book);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }

    }

}
