package br.com.jherrerocavadas.apiLivros.api;

import br.com.jherrerocavadas.apiLivros.dto.Emprestimo;
import br.com.jherrerocavadas.apiLivros.repository.LivroRepository;
import br.com.jherrerocavadas.apiLivros.dto.Livro;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
//@Api(value = "api-livros", tags = {"API de gerenciamento de livros"})
//@RequestMapping("/api/v1/livros")
public class LivrosAPI {
    private LivroRepository livroRepository;

    @Autowired
    private LivrosAPI(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    @Operation(summary =  "Verificar o serviço de CRUD de livros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-livro-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }

    @Operation(summary =  "Retornar os livros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livros retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/livros")
    public Iterable<Livro> retornarLivros(){
        return livroRepository.findAll();
    }

    @Operation(summary =  "Retornar a quantidade de livros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de livros cadastrados retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/livros/contagem")
    public Long retornarContagemLivros(){
        return livroRepository.count();
    }

    @Operation(summary =  "Retornar um livro de acordo com seu código identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livro retornado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> retornarLivroPorId(@PathVariable long id){
        Livro livro = livroRepository.getLivroByLivroId(id);
        if(Objects.nonNull(livro)){
            return ResponseEntity.ok(livro);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary =  "Retornar livro de acordo com os filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livro retornados de acordo com os filtros configurados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/livros/search")
    public ResponseEntity<List<Livro>> retornarLivrosPorFiltro(@RequestParam (value = "titulo", required = false) String titulo,
                                               @RequestParam (value = "autor", required = false) String autor){
       List<Livro> livros;

       //Normalizar filtros
        if(titulo == null){
            titulo = "";
        }
        if(autor == null){
            autor = "";
        }
        log.warn(String.valueOf(livroRepository.getLivrosByTituloAndAutor("%"+ titulo +"%", "%"+ autor +"%")));
        log.warn("------------------------------------");
        log.warn(String.valueOf(livroRepository.getLivrosByAutor("%Author%")));

        //Acionar consultas diferentes de acordo com os filtros
        if((titulo != "" && autor == "")){
            livros = livroRepository.getLivrosByTitulo(titulo);
        } else if ((titulo == "" && autor != "")) {
            livros = livroRepository.getLivrosByAutor(autor);
        } else if ((titulo != "" && autor!= "")) {
            livros = livroRepository.getLivrosByTituloAndAutor(titulo, autor);
        } else{
            livros = (List<Livro>) livroRepository.findAll();
        }


        if(Objects.nonNull(livros) || !livros.isEmpty()){
            return ResponseEntity.ok(livros);
        }
        else{
            return ResponseEntity.notFound().build();
        }


    }

    @Operation(summary =  "Cadastrar um novo livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livro salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/livros")
    public void salvarLivro(@RequestBody Livro livro){
        livroRepository.save(livro);
    }




    @Operation(summary =  "Editar um livro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livro alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/livros/{id}")
    public void editarLivro(@RequestBody Livro livro, @PathVariable Long id){
        livro.setLivroId(id);
        livroRepository.save(livro);
    }



    @Operation(summary =  "Remover um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Livro removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/livros/{id}")
    public void removerLivro(@PathVariable Long id){
        livroRepository.deleteById(id);
    }



}
