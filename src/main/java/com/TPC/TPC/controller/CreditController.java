package com.TPC.TPC.controller;

import com.TPC.TPC.model.Credit;
import com.TPC.TPC.repository.CreditRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("credits")
@CacheConfig(cacheNames = "credits")
@Tag(name = "Créditos", description = "Informações de créditos disponíveis a serem vinculados com diferentes usuários.")
public class CreditController {

    @Autowired
    private CreditRepository creditRepository;

    // Buscar todos os créditos
    @GetMapping
    @Cacheable("credits")
    @Operation(
        summary = "Listar créditos",
        description = "Retorna uma página com todos os créditos cadastrados, ordenados pela data de início."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Créditos retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem créditos cadastrados.", useReturnTypeSchema = false)
    })
    public Page<Credit> listarCreditos(
        @RequestParam(required = false) String credit,
        @PageableDefault(sort = "dataCredito", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (credit != null){
            return creditRepository.findByDataCredito(credit, pageable);
        }
        return creditRepository.findAll(pageable);
    }

    // Buscar um crédito pelo ID
    @GetMapping("{creditID}")
    @Operation(
        summary = "Listar créditos por ID",
        description = "Retorna um determinado crédito correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do crédito retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe crédito com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Credit> getCreditById(@PathVariable Integer creditID) {
        return creditRepository.findById(creditID)
                .map(credit -> ResponseEntity.ok().body(credit))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar um novo crédito
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar crédito",
        description = "Cadastra um novo crédito a ser vinculado a um determinado usuário com os dados enviados no corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Crédito cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Credit> createCredit(@Valid @RequestBody Credit credit) {
        Credit savedCredit = creditRepository.save(credit);
        return new ResponseEntity<>(savedCredit, HttpStatus.CREATED);
    }

    // Atualizar um crédito existente
    @PutMapping("{creditID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar crédito",
        description = "Atualiza um determinado crédito correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do crédito atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe crédito com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Credit> updateCredit(@PathVariable Integer creditID, @Valid @RequestBody Credit creditDetails) {
        return creditRepository.findById(creditID)
                .map(credit -> {
                    credit.setValor(creditDetails.getValor());
                    credit.setDataCredito(creditDetails.getDataCredito());
                    credit.setDataExpiracao(creditDetails.getDataExpiracao());
                    credit.setUtilizado(creditDetails.getUtilizado());
                    Credit updatedCredit = creditRepository.save(credit);
                    return ResponseEntity.ok().body(updatedCredit);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um crédito
    @DeleteMapping("{creditID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar crédito",
        description = "Deleta um determinado crédito correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do crédito deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe crédito com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deleteCredit(@PathVariable Integer creditID) {
        return creditRepository.findById(creditID)
                .map(credit -> {
                    creditRepository.delete(credit);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
