package br.com.infnet.service;


import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookService {
    private Map<Long, Book> livros = initLivros();

    private Map<Long, Book> initLivros() {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://stephen-king-api.onrender.com/api/books"))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());

            List<JsonNode> bookNodes = rootNode.findValues("data");

            Map<Long, Book> livros = new HashMap<>();
            if (!bookNodes.isEmpty()) {

                for (JsonNode bookNode : bookNodes.get(0)) {
                    Book book = new Book(bookNode.get("id").asInt(), bookNode.get("Year").asInt(), bookNode.get("Title").asText());
                    livros.put((long) book.getId(), book);
                }
            }
            return livros;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Book> getAll(){

        return livros.values().stream().toList();
    }

    public Book getById(int id) {
        Book livro = livros.get((long) id);
        if(livro == null) throw new ResourceNotFoundException("Livro não localizado");
        return livro;
    }

}
