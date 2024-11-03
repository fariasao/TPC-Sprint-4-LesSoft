package com.TPC.TPC.controller;

import com.TPC.TPC.model.Loja;
import com.TPC.TPC.repository.LojaRepository;

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
@RequestMapping("lojas")
@CacheConfig(cacheNames = "lojas")
@Tag(name = "Lojas", description = "Lojas parceiras do aplicativo.")
public class LojaController {

    @Autowired
    private LojaRepository lojaRepository;

    // Buscar todas as lojas
    @GetMapping
    @Cacheable("lojas")
    @Operation(
        summary = "Listar lojas",
        description = "Retorna uma página com todas as lojas cadastradas, ordenadas pelo nome da loja."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Lojas retornadas com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem lojas cadastradas.", useReturnTypeSchema = false)
    })
    public Page<Loja> listarLojas(
        @RequestParam(required = false) String loja,
        @PageableDefault(sort = "nomeLoja", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (loja != null){
            return lojaRepository.findByNomeLoja(loja, pageable);
        }
        return lojaRepository.findAll(pageable);
    }

    // Buscar uma loja pelo ID
    @GetMapping("{pdvID}")
    @Operation(
        summary = "Listar loja por ID",
        description = "Retorna uma determinada loja correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da loja retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe loja com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Loja> getLojaById(@PathVariable Integer pdvID) {
        return lojaRepository.findById(pdvID)
                .map(loja -> ResponseEntity.ok().body(loja))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar uma nova loja
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar loja",
        description = "Cadastra uma nova loja com os dados do corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Loja cadastrada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Loja> createLoja(@Valid @RequestBody Loja loja) {
        Loja savedLoja = lojaRepository.save(loja);
        return new ResponseEntity<>(savedLoja, HttpStatus.CREATED);
    }

    // Atualizar uma loja existente
    @PutMapping("{pdvID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar loja",
        description = "Atualiza uma determinada loja correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da loja retornados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe loja com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Loja> updateLoja(@PathVariable Integer pdvID, @Valid @RequestBody Loja lojaDetails) {
        return lojaRepository.findById(pdvID)
                .map(loja -> {
                    loja.setNomeLoja(lojaDetails.getNomeLoja());
                    loja.setEndereco(lojaDetails.getEndereco());
                    loja.setNumero(lojaDetails.getNumero());
                    loja.setComplemento(lojaDetails.getComplemento());
                    loja.setCep(lojaDetails.getCep());
                    loja.setActive(lojaDetails.getActive());
                    Loja updatedLoja = lojaRepository.save(loja);
                    return ResponseEntity.ok().body(updatedLoja);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar uma loja
    @DeleteMapping("{pdvID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar loja",
        description = "Deleta uma determinada loja correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da loja deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe loja com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deleteLoja(@PathVariable Integer pdvID) {
        return lojaRepository.findById(pdvID)
                .map(loja -> {
                    lojaRepository.delete(loja);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
