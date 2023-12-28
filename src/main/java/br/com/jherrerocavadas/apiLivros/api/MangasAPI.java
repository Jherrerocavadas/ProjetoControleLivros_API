package br.com.jherrerocavadas.apiLivros.api;

import br.com.jherrerocavadas.apiLivros.dto.Emprestimo;
import br.com.jherrerocavadas.apiLivros.dto.Livro;
import br.com.jherrerocavadas.apiLivros.repository.LivroRepository;
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
//@Api(value = "api-livros", tags = {"API de gerenciamento de mangas"})
//@RequestMapping("/api/v1/mangas")
public class MangasAPI {
    private LivroRepository livroRepository;

    @Autowired
    private MangasAPI(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    @Operation(summary = "Verificar o serviço de CRUD de mangás")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-manga-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }


    @Operation(summary = "Retornar os mangás cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangás retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/mangas")
    public Iterable<Livro> retornarMangas(){
        return livroRepository.findAll();
    }


    @Operation(summary = "Retornar um mangá de acordo com seu código identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangá retornado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/mangas/{id}")
    public ResponseEntity<Livro> retornarMangaPorId(@PathVariable long id){
        Livro livro = livroRepository.getLivroByLivroId(id);
        if(Objects.nonNull(livro)){
            return ResponseEntity.ok(livro);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Retornar mangá de acordo com os filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangás retornados de acordo com os filtros configurados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/mangas/search")
    public ResponseEntity<List<Livro>> retornarMangasPorFiltro(@RequestParam (value = "titulo", required = false) String titulo,
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

    @Operation(summary = "Cadastrar um novo mangá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangá salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/mangas")
    public void salvarManga(@RequestBody Livro livro){
        livroRepository.save(livro);
    }



    @Operation(summary = "Editar um mangá existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangá alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/mangas/{id}")
    public void editarManga(@RequestBody Livro livro, @PathVariable Long id){
        livro.setLivroId(id);
        livroRepository.save(livro);
    }



    @Operation(summary = "Remover um Mangá")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangá removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/mangas/{id}")
    public void removerManga(@PathVariable Long id){
        livroRepository.deleteById(id);
    }



}
