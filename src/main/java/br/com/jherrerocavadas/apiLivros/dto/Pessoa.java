package br.com.jherrerocavadas.apiLivros.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pessoaId;

    private String nome;
    private String telefone;

    public Pessoa(String nome, String telefone) {
        this.pessoaId = pessoaId;
        this.nome = nome;
        this.telefone = telefone;
    }
}
