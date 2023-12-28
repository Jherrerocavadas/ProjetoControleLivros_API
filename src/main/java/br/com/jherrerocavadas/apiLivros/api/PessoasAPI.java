package br.com.jherrerocavadas.apiLivros.api;

import br.com.jherrerocavadas.apiLivros.dto.Pessoa;
import br.com.jherrerocavadas.apiLivros.repository.PessoaRepository;
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
//@Api(value = "api-livros", tags = {"API de gerenciamento de cadastro de pessoas"})
//@RequestMapping("/api/v1/pessoas")
public class PessoasAPI {
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoasAPI(PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    @Operation(summary =  "Verificar o serviço de CRUD de pessoas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-pessoa-service")
    public ResponseEntity<String> verificaServico(){
        return ResponseEntity.ok().body("Serviço operacional");
    }


    @Operation(summary =  "Retornar as pessoas cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa retornadas"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/pessoas")
    public Iterable<Pessoa> retornarLivros(){
        return pessoaRepository.findAll();
    }


    @Operation(summary =  "Retornar uma pessoa de acordo com seu código identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Pessoa retornada"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Pessoa> retornarLivroPorId(@PathVariable long id){
        Pessoa pessoa = pessoaRepository.getPessoaByPessoaId(id);
        if(Objects.nonNull(pessoa)){
            return ResponseEntity.ok(pessoa);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary =  "Retornar pessoas de acordo com os filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Pessoas retornadas de acordo com os filtros configurados"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/pessoas/search")
    public ResponseEntity<List<Pessoa>> retornarLivrosPorFiltro(
            @RequestParam (value = "nome") String nome,
            @RequestParam (value = "telefone", required = false) String telefone){

//        List<Pessoa> pessoas = (List<Pessoa>) pessoaRepository.findAll();

        List<Pessoa> pessoas;

        if(telefone == null){
            telefone = "";
        }
        //Acionar consultas diferentes de acordo com os filtros
        if(telefone == ""){
            pessoas = pessoaRepository.getPessoaByNome(nome);
        } else {
            pessoas = pessoaRepository.getPessoaByNomeAndTelefone(nome, telefone);
        }


        if(Objects.nonNull(pessoas) || !pessoas.isEmpty()){
            return ResponseEntity.ok(pessoas);
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary =  "Cadastrar uma nova pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Pessoa salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/pessoas")
    public void salvarPessoa(@RequestBody Pessoa pessoa){
        pessoaRepository.save(pessoa);
    }



    @Operation(summary =  "Editar uma pessoa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Pessoa alterada e salva"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/pessoas/{id}")
    public void editarPessoa(@RequestBody Pessoa pessoa, @PathVariable Long id){
        pessoa.setPessoaId(id);
        pessoaRepository.save(pessoa);
    }



    @Operation(summary =  "Remover uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description =  "Pessoa removida"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/pessoas/{id}")
    public void removerPessoa(@PathVariable Long id){
        pessoaRepository.deleteById(id);
    }



}
