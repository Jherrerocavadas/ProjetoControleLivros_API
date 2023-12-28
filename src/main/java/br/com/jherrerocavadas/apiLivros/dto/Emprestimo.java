package br.com.jherrerocavadas.apiLivros.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emprestimoId;


//    @ManyToMany
//    @JoinTable(name="pessoas",
//    joinColumns = @JoinTable(
//            name = "PessoaID",
//            foreignKey = @ForeignKey(name = "PessoaId")
//            ),
//    inverseJoinColumns = @JoinTable(
//            name = "PessoaID",
//            foreignKey = @ForeignKey(name = "PessoaId")
//            )
//    )

    @Column(insertable=true, updatable=true)
    private Long pessoaId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pessoaId", referencedColumnName = "pessoaId",
            insertable=false, updatable=false, foreignKey = @ForeignKey(name = "UK_EMPRESTIMO_PESSOA"))
    private Pessoa pessoa;

    @Column(insertable=true, updatable=true)
    private Long livroId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "livroId", referencedColumnName = "livroId",
            insertable=false, updatable=false, foreignKey = @ForeignKey(name = "UK_EMPRESTIMO_LIVRO"))
    private Livro livro;


    private Date dataEmprestimo;
}
