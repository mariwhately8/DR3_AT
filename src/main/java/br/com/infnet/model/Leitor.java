package br.com.infnet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Leitor {
    private long id;
    private String nome;
    private String cpf;
    private String email;
}
