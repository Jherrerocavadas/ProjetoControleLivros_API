package br.com.jherrerocavadas.apiLivros.repository;

import br.com.jherrerocavadas.apiLivros.dto.Pessoa;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    Pessoa getPessoaByPessoaId(long PessoaId);

    List<Pessoa> getPessoaByNome(String nome);

    List<Pessoa> getPessoaByNomeAndTelefone(String nome, String telefone);
}