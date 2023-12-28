package br.com.jherrerocavadas.apiLivros.api;

import br.com.jherrerocavadas.apiLivros.dto.Emprestimo;
import br.com.jherrerocavadas.apiLivros.repository.EmprestimoRepository;
import br.com.jherrerocavadas.apiLivros.repository.LivroRepository;
import br.com.jherrerocavadas.apiLivros.repository.PessoaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
//@Api(value = "api-livros", tags = {"API de gerenciamento de empréstimos"})
//@RequestMapping("/api/v1/emprestimos")
public class EmprestimosAPI {
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EmprestimosAPI(EmprestimoRepository emprestimoRepository){
        this.emprestimoRepository = emprestimoRepository;

    }

    @Operation(summary =  "Verificar o serviço de empréstimos de livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serviço OK"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/check-emprestimo-service")
    public ResponseEntity<String> verificaServico(){

        log.info("[EmprestimosAPI] - Checagem de serviço de empréstimos");
        return ResponseEntity.ok().body("Serviço operacional");
    }

    @Operation(summary =  "Retornar os empréstimos de livros vigentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimos retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/emprestimos")
    public Iterable<Emprestimo> retornarEmprestimos(){
        log.info("[EmprestimosAPI] - Consulta de todos os empréstimos");
        return emprestimoRepository.findAll();
    }


    @Operation(summary =  "Retornar a quantidade de empréstimos de livros vigentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de empréstimos retornados"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/emprestimos/contagem")
    public Long retornarContagemEmprestimos(){
        log.info("[EmprestimosAPI] - Consulta da quantidade de empréstimos vigentes");
        return emprestimoRepository.count();
    }


    @Operation(summary =  "Retornar um empréstimo de acordo com seu código identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo retornado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @GetMapping("/emprestimos/{idEmprestimo}")
    public ResponseEntity<Emprestimo> retornarEmprestimoPorId(@PathVariable long idEmprestimo){
        Emprestimo emprestimo = emprestimoRepository.getEmprestimoByEmprestimoId(idEmprestimo);
        if(Objects.nonNull(emprestimo)){
            log.info("[EmprestimosAPI] - Consulta de empréstimo com ID " + idEmprestimo);
            return ResponseEntity.ok(emprestimo);
        }
        else{
            log.error("[EmprestimosAPI] - Empréstimo com ID" + idEmprestimo + "não encontrado");
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary =  "Cadastrar um novo empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PostMapping("/emprestimos")
    public void salvarEmprestimo(@RequestBody Emprestimo emprestimo){
        emprestimoRepository.save(emprestimo);
    }


    @Operation(summary =  "Editar um empréstimo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo alterado e salvo"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @PutMapping("/emprestimos/{id}")
    public void editarEmprestimo(@RequestBody Emprestimo emprestimo, @PathVariable Long id){
        emprestimo.setEmprestimoId(id);
        emprestimoRepository.save(emprestimo);
    }

    @Operation(summary =  "Remover um empréstimo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo removido"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/emprestimos/{id}")
    public void removerEmprestimo(@PathVariable long id){
        emprestimoRepository.deleteById(id);
    }



}
