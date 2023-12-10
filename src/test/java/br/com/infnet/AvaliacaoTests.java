package br.com.infnet;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Avaliacao;
import br.com.infnet.service.AvaliacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AvaliacaoTests {

    @Autowired
    AvaliacaoService avaliacaoService;

    @Test
    @DisplayName("Deve inserir uma avaliação.")
    void testaCreate(){
        int initialSize = avaliacaoService.getAll().size();
        Avaliacao avaliacao1 = Avaliacao.builder()
                .valorAvaliacao(5)
                .idBook((long)10)
                .comentario("gostei")
                .build();
        avaliacaoService.create(avaliacao1);
        int finalSize = avaliacaoService.getAll().size();
        assertEquals(initialSize + 1, finalSize);
    }

    @Test
    @DisplayName("Deve inserir uma lista de avaliações.")
    public void testaInserirListaAvaliacoes(){
        int initialSize = avaliacaoService.getAll().size();
        List<Avaliacao> listaDeAvaliacoes = new ArrayList<>();
        listaDeAvaliacoes.add(new Avaliacao(5,"gostei tb 2",3L));
        listaDeAvaliacoes.add(new Avaliacao(5,"gostei tb 3",4L));
        listaDeAvaliacoes.add(new Avaliacao(1,"gostei não",5L));

        avaliacaoService.saveAll(listaDeAvaliacoes);
        int finalSize = avaliacaoService.getAll().size();
        assertEquals( initialSize + 3, finalSize);
    }

    @Test
    @DisplayName("Deve retornar uma lista de Avaliações.")
    public void getListaDeAvaliacoes(){
        List<Avaliacao> listaDeAvaliacoes = new ArrayList<>();
        listaDeAvaliacoes.add(new Avaliacao(5,"gostei tb 2",3L));
        listaDeAvaliacoes.add(new Avaliacao(5,"gostei tb 3",4L));
        listaDeAvaliacoes.add(new Avaliacao(1,"gostei não",5L));
        avaliacaoService.saveAll(listaDeAvaliacoes);

        List<Avaliacao> avaliacoes = avaliacaoService.getAll();
        assertEquals( 3, avaliacoes.size());
    }

    @Test
    @DisplayName("Deve retornar uma avaliação pelo ID.")
    public void getById(){
        AvaliacaoService avaliacao = new AvaliacaoService();
        Avaliacao avaliacao1 = Avaliacao.builder()
                .valorAvaliacao(5)
                .idBook((long)11)
                .comentario("gostei")
                .build();
        avaliacaoService.create(avaliacao1);
        Avaliacao avaliacao2 = avaliacaoService.getById(1);
        assertEquals(avaliacao2.getId(),1);
        avaliacaoService.deleteById(1);
    }

    @Test
    @DisplayName("Deve remover uma avaliação pelo ID")
    void testaDeleta(){
        int initialSize = avaliacaoService.getAll().size();
        Avaliacao avaliacao1 = Avaliacao.builder()
                .valorAvaliacao(5)
                .idBook((long)10)
                .comentario("gostei")
                .build();
        int idCriado = avaliacaoService.create(avaliacao1);
        avaliacaoService.deleteById(idCriado);
        int finalSize = avaliacaoService.getAll().size();
        assertEquals(initialSize, finalSize);
    }

    @Test
    @DisplayName("Deve atualizar uma avaliação.")
    void testaUpdate(){
        Avaliacao avaliacao1 = Avaliacao.builder()
                .valorAvaliacao(5)
                .idBook((long)10)
                .comentario("gostei")
                .build();
        int idCriado = avaliacaoService.create(avaliacao1);
        Avaliacao avaliacaoUpdate = new Avaliacao(5, "gostei editado", 9L);
        avaliacaoService.update(idCriado, avaliacaoUpdate);
        Avaliacao avaliacaoEditada = avaliacaoService.getById(idCriado);
        assertEquals(avaliacaoEditada.getComentario(), "gostei editado");
    }

}
