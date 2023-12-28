package br.com.jherrerocavadas.apiLivros.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long livroId;

    private String titulo;
    private String autor;
    private LocalDate dataCompra;

    public Livro(String titulo, String autor, LocalDate dataCompra) {
        this.titulo = titulo;
        this.autor = autor;
        this.dataCompra = dataCompra;
    }
}
