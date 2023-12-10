package br.com.infnet.service;

import br.com.infnet.exception.ResourceNotFoundException;
import br.com.infnet.model.Avaliacao;
import br.com.infnet.model.Book;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AvaliacaoService {
    private Map<Integer, Avaliacao> avalicacoes = new HashMap<>();
    private int lastId = 0;
    public int create(Avaliacao avaliacao){
        int id = ++this.lastId;
        avaliacao.setId(id);
        avalicacoes.put(avaliacao.getId(), avaliacao);
        return id;
    }
    public void saveAll(List<Avaliacao> avaliacoes) {
        for(Avaliacao avaliacao:avaliacoes){
            avaliacao.setId(++this.lastId);
            avalicacoes.put(avaliacao.getId(), avaliacao);
        }
    }

    public List<Avaliacao> getAll() {
        return avalicacoes.values().stream().toList();
    }

    public void deleteById(int id) {
        if(avaliacaoNaoExiste(id)) throw new ResourceNotFoundException("Avaliação inexistente");
        avalicacoes.remove(id);
    }

    private boolean avaliacaoNaoExiste(int id) {
        return !avalicacoes.containsKey(id);
    }

    public Avaliacao getById(int id) {
        Avaliacao avaliacao = avalicacoes.get(id);
        if(avaliacao == null) throw new ResourceNotFoundException("Avaliação não localizada.");
        return avaliacao;
    }

    public void update(int id, Avaliacao avaliacaoAtualizada) {
        if(avaliacaoNaoExiste(id)) throw new ResourceNotFoundException("Avaliação inexistente!");
        avaliacaoAtualizada.setId(id);
        avalicacoes.replace(id, avaliacaoAtualizada);
    }
}
