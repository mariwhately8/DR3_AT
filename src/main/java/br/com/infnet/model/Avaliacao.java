package br.com.infnet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Avaliacao {
    private int id;
    private int valorAvaliacao;
    private String comentario;
    private Long idBook;

    public Avaliacao(int i, String s, long l) {
        this.valorAvaliacao = i;
        this.comentario = s;
        this.idBook = l;
    }
}
