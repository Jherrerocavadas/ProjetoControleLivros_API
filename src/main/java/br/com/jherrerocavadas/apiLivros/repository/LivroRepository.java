package br.com.jherrerocavadas.apiLivros.repository;

import br.com.jherrerocavadas.apiLivros.dto.Livro;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LivroRepository extends CrudRepository<Livro, Long> {


    Livro getLivroByLivroId(long LivroId);

    List<Livro> getLivrosByTitulo(String titulo);

    List<Livro> getLivrosByAutor(String autor);

    List<Livro> getLivrosByTituloAndAutor(String titulo, String autor);

//    List<Livro> getLivrosbyDataCompra(LocalDate dataCompra);






}
