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
//@Api(value = "api-livros", tags = {"API de gerenciamento de HQs"})
//@RequestMapping("/v1/hqs")
public class HqsAPI {
    private LivroRepository livroRepository;

    @Autowired
    private HqsAPI(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    @Operation(summary = "Verificar o serviço de CRUD de HQs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-hq-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }

    @Operation(summary = "Retornar as HQs cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HQs retornadas"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/hqs")
    public Iterable<Livro> retornarHQs(){
        return livroRepository.findAll();
    }


    @Operation(summary = "Retornar um empréstimo de acordo com seu código identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HQ retornada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/hqs/{id}")
    public ResponseEntity<Livro> retornarHQPorId(@PathVariable long id){
        Livro livro = livroRepository.getLivroByLivroId(id);
        if(Objects.nonNull(livro)){
            return ResponseEntity.ok(livro);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Retornar HQ de acordo com os filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HQs retornadas de acordo com os filtros configurados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/hqs/search")
    public ResponseEntity<List<Livro>> retornarHQsPorFiltro(@RequestParam (value = "titulo", required = false) String titulo,
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
    @Operation(summary = "Cadastrar uma nova HQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HQ salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })

    @PostMapping("/hqs")
    public void salvarLivro(@RequestBody Livro livro){
        livroRepository.save(livro);
    }





    @Operation(summary = "Editar uma HQ existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hq alterada e salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/hqs/{id}")
    public void editarLivro(@RequestBody Livro livro, @PathVariable Long id){
        livro.setLivroId(id);
        livroRepository.save(livro);
    }



    @Operation(summary = "Remover uma HQ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HQ removida"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/hqs/{id}")
    public void removerLivro(@PathVariable Long id){
        livroRepository.deleteById(id);
    }



}
