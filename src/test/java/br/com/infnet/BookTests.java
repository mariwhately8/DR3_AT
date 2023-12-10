package br.com.infnet;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Book;
import br.com.infnet.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class BookTests {
    @Autowired
    BookService bookService;

    @Test
    @DisplayName("Deve retornar uma lista de Livros")
    public void getListaDeLivros(){
        List<Book> livros = bookService.getAll();
        assertEquals(63, livros.size());
    }

    @Test
    @DisplayName("Deve retornar um livro pelo ID")
    public void getById(){
        BookService livros = new BookService();
        Book livro = livros.getById(1);
        assertEquals("Carrie", livro.getTitle());
    }

    @Test
    @DisplayName("Deve retornar uma exceptpion para um livro inexistente")
    public void testaLivroInexistente(){
        Book livros = new Book();
        assertThrows(ResourceNotFoundException.class, () -> {
            Book livro = bookService.getById(80);
        });
    }
}
