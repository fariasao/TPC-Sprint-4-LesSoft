package com.TPC.TPC.controller;

import com.TPC.TPC.model.UserCluster;
import com.TPC.TPC.repository.UserClusterRepository;

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
@RequestMapping("usercluster")
@CacheConfig(cacheNames = "usercluster")
@Tag(name = "User-Cluster", description = "Relação entre usuário e cluster.")
public class UserClusterController {

    @Autowired
    private UserClusterRepository userClusterRepository;

    // Buscar todas as associações UserCluster
    @GetMapping
    @Cacheable("usercluster")
    @Operation(
        summary = "Listar relações entre usuário e cluster",
        description = "Retorna uma página com todas as relações entre usuário e cluster cadastradas, ordenadas pelo ID."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Relações entre usuário e cluster retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem relações entre usuário e cluster cadastradas.", useReturnTypeSchema = false)
    })
    public Page<UserCluster> listarUserClusters(
        @RequestParam(required = false) String userCluster,
        @PageableDefault(sort = "userClusterID", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (userCluster != null){
            return userClusterRepository.findByUserClusterID(userCluster, pageable);
        }
        return userClusterRepository.findAll(pageable);
    }

    // Buscar uma associação UserCluster pelo ID
    @GetMapping("{userClusterID}")
    @Operation(
        summary = "Listar relação entre usuário e cluster por ID",
        description = "Retorna uma determinada relação entre usuário e cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da relação entre usuário e cluster retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe relação entre usuário e cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<UserCluster> getUserClusterById(@PathVariable Integer userClusterID) {
        return userClusterRepository.findById(userClusterID)
                .map(userCluster -> ResponseEntity.ok().body(userCluster))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar uma nova associação UserCluster
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar relação entre usuário e cluster",
        description = "Cadastra uma nova relação entre usuário e cluster com os dados enviados no corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Relação entre usuário e cluster cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<UserCluster> createUserCluster(@Valid @RequestBody UserCluster userCluster) {
        UserCluster savedUserCluster = userClusterRepository.save(userCluster);
        return new ResponseEntity<>(savedUserCluster, HttpStatus.CREATED);
    }

    // Atualizar uma associação UserCluster existente
    @PutMapping("{userClusterID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar relação entre usuário e cluster",
        description = "Atualiza uma determinada relação entre usuário e cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da relação entre usuário e cluster atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe relação entre usuário e cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<UserCluster> updateUserCluster(@PathVariable Integer userClusterID, @Valid @RequestBody UserCluster userClusterDetails) {
        return userClusterRepository.findById(userClusterID)
                .map(userCluster -> {
                    userCluster.setClusterID(userClusterDetails.getClusterID());
                    userCluster.setUsersID(userClusterDetails.getUsersID());
                    UserCluster updatedUserCluster = userClusterRepository.save(userCluster);
                    return ResponseEntity.ok().body(updatedUserCluster);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar uma associação UserCluster
    @DeleteMapping("{userClusterID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar relação entre usuário e cluster",
        description = "Deleta uma determinada relação entre usuário e cluster correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados da relação entre usuário e cluster deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe relação entre usuário e cluster com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deleteUserCluster(@PathVariable Integer userClusterID) {
        return userClusterRepository.findById(userClusterID)
                .map(userCluster -> {
                    userClusterRepository.delete(userCluster);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
