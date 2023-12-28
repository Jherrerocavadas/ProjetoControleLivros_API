package br.com.jherrerocavadas.apiLivros.repository;

import br.com.jherrerocavadas.apiLivros.dto.Emprestimo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends CrudRepository<Emprestimo, Long> {


    Emprestimo getEmprestimoByEmprestimoId(long emprestimoId);

//    List<Emprestimo> getEmprestimoByLivroId(long livroId);

//    List<Emprestimo> getEmprestimoByPessoaId(long emprestimoId);

    List<Emprestimo> getEmprestimoByDataEmprestimo(LocalDate dataEmprestimo);


}
