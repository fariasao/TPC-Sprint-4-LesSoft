package com.TPC.TPC.controller;

import com.TPC.TPC.model.Pontos;
import com.TPC.TPC.repository.PontosRepository;

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
@RequestMapping("pontos")
@CacheConfig(cacheNames = "pontos")
@Tag(name = "Pontos", description = "Quantidade de pontos referentes às compras feitas.")
public class PontosController {

    @Autowired
    private PontosRepository pontosRepository;

    // Buscar todos os registros de pontos
    @GetMapping
    @Cacheable("pontos")
    @Operation(
        summary = "Listar pontos",
        description = "Retorna uma página com todos os pontos cadastrados, ordenados pela data inicial."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Pontos retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem pontos cadastrados.", useReturnTypeSchema = false)
    })
    public Page<Pontos> listarPontos(
        @RequestParam(required = false) String pontos,
        @PageableDefault(sort = "dataCredito", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (pontos != null){
            return pontosRepository.findByDataCredito(pontos, pageable);
        }
        return pontosRepository.findAll(pageable);
    }
    
    // Buscar um registro de pontos pelo ID
    @GetMapping("{pointID}")
    @Operation(
        summary = "Listar pontos por ID",
        description = "Retorna uma determinada quantidade de pontos correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados de quantidade de pontos retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe quantidade de pontos com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Pontos> getPontosById(@PathVariable Integer pointID) {
        return pontosRepository.findById(pointID)
                .map(ponto -> ResponseEntity.ok().body(ponto))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar um novo registro de pontos
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar pontos",
        description = "Cadastra uma nova quantidade de pontos a ser vinculada a uma compra com os dados do corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Pontos cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Pontos> createPontos(@Valid @RequestBody Pontos ponto) {
        Pontos savedPontos = pontosRepository.save(ponto);
        return new ResponseEntity<>(savedPontos, HttpStatus.CREATED);
    }

    // Atualizar um registro de pontos existente
    @PutMapping("{pointID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar pontos",
        description = "Atualiza uma determinada quantidade de pontos correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da quantidade de pontos atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe quantidade de pontos com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Pontos> updatePontos(@PathVariable Integer pointID, @Valid @RequestBody Pontos pontosDetails) {
        return pontosRepository.findById(pointID)
                .map(ponto -> {
                    ponto.setValor(pontosDetails.getValor());
                    ponto.setDataCredito(pontosDetails.getDataCredito());
                    ponto.setDataExpiracao(pontosDetails.getDataExpiracao());
                    ponto.setUtilizado(pontosDetails.getUtilizado());
                    Pontos updatedPontos = pontosRepository.save(ponto);
                    return ResponseEntity.ok().body(updatedPontos);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um registro de pontos
    @DeleteMapping("{pointID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar pontos",
        description = "Deleta uma determinada quantidade de pontos correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados de quantidade de pontos deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe quantidade de pontos com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deletePontos(@PathVariable Integer pointID) {
        return pontosRepository.findById(pointID)
                .map(ponto -> {
                    pontosRepository.delete(ponto);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
