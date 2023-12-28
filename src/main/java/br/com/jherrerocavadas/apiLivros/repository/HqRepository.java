package br.com.jherrerocavadas.apiLivros.repository;

import br.com.jherrerocavadas.apiLivros.dto.Livro;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HqRepository extends CrudRepository<Livro, Long> {


    Livro getHqByLivroId(long LivroId);

    List<Livro> getHqByTitulo(String titulo);

    List<Livro> getHqByAutor(String autor);

    List<Livro> getHqByTituloAndAutor(String titulo, String autor);

//    List<Livro> getLivrosbyDataCompra(int mes, int ano);






}
