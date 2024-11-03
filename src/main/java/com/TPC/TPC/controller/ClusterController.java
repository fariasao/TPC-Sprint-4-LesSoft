package com.TPC.TPC.controller;

import com.TPC.TPC.model.Cluster;
import com.TPC.TPC.repository.ClusterRepository;

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
@RequestMapping("clusters")
@CacheConfig(cacheNames = "clusters")
@Tag(name = "Clusters", description = "Clusters personalizados para cada tipo de usuário.")
public class ClusterController {

    @Autowired
    private ClusterRepository clusterRepository;

    // Buscar todos os clusters
    @GetMapping
    @Cacheable("clusters")
    @Operation(
        summary = "Listar clusters",
        description = "Retorna uma página com todos os clusters cadastrados, ordenados pelo nome."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Clusters retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem clusters cadastrados.", useReturnTypeSchema = false)
    })
    public Page<Cluster> listarClusters(
        @RequestParam(required = false) String cluster,
        @PageableDefault(sort = "name", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (cluster != null){
            return clusterRepository.findByName(cluster, pageable);
        }
        return clusterRepository.findAll(pageable);
    }

    // Buscar um cluster pelo ID
    @GetMapping("{clusterID}")
    @Operation(
        summary = "Listar cluster por ID",
        description = "Retorna um determinado cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do cluster retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Cluster> getClusterById(@PathVariable Integer clusterID) {
        return clusterRepository.findById(clusterID)
                .map(cluster -> ResponseEntity.ok().body(cluster))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar um novo cluster
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar cluster",
        description = "Cadastra um novo cluster a ser vinculado a determinados tipos de usuários com os dados enviados no corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Cluster cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Cluster> createCluster(@Valid @RequestBody Cluster cluster) {
        Cluster savedCluster = clusterRepository.save(cluster);
        return new ResponseEntity<>(savedCluster, HttpStatus.CREATED);
    }

    // Atualizar um cluster existente
    @PutMapping("{clusterID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar cluster",
        description = "Atualiza um determinado cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do cluster atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Cluster> updateCluster(@PathVariable Integer clusterID, @Valid @RequestBody Cluster clusterDetails) {
        return clusterRepository.findById(clusterID)
                .map(cluster -> {
                    cluster.setName(clusterDetails.getName());
                    cluster.setDescricao(clusterDetails.getDescricao());
                    Cluster updatedCluster = clusterRepository.save(cluster);
                    return ResponseEntity.ok().body(updatedCluster);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um cluster
    @DeleteMapping("{clusterID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar cluster",
        description = "Deleta um determinado cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do cluster deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deleteCluster(@PathVariable Integer clusterID) {
        return clusterRepository.findById(clusterID)
                .map(cluster -> {
                    clusterRepository.delete(cluster);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
