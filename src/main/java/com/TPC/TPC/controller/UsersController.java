package com.TPC.TPC.controller;

import com.TPC.TPC.model.Users;
import com.TPC.TPC.repository.UsersRepository;

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
@RequestMapping("users")
@CacheConfig(cacheNames = "users")
@Tag(name = "Usuários", description = "Usuários cadastrados no banco de dados.")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    // Buscar todos os usuários
    @GetMapping
    @Cacheable("users")
    @Operation(
        summary = "Listar usuários",
        description = "Retorna uma página com todos os usuários cadastrados, ordenados pelo nome."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem usuários cadastrados.", useReturnTypeSchema = false)
    })
    public Page<Users> listarUsers(
        @RequestParam(required = false) String user,
        @PageableDefault(sort = "nome", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (user != null){
            return usersRepository.findByNome(user, pageable);
        }
        return usersRepository.findAll(pageable);
    }

    // Buscar um usuário pelo ID
    @GetMapping("{usersID}")
    @Operation(
        summary = "Listar usuário por ID",
        description = "Retorna um determinado usuário correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do usuário retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe usuário com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Users> getUserById(@PathVariable Integer usersID) {
        return usersRepository.findById(usersID)
                .map(user -> ResponseEntity.ok(user))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar um novo usuário
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar usuário",
        description = "Cadastra um novo usuário com os dados enviados no corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users savedUser = usersRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Atualizar um usuário existente
    @PutMapping("{usersID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar usuário",
        description = "Atualiza um determinado usuário correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do usuário atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe usuário com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Users> updateUser(@PathVariable Integer usersID, @Valid @RequestBody Users userDetails) {
        return usersRepository.findById(usersID)
                .map(user -> {
                    user.setNome(userDetails.getNome());
                    user.setSobrenome(userDetails.getSobrenome());
                    user.setEmail(userDetails.getEmail());
                    user.setPassword(userDetails.getPassword());
                    user.setTelefone(userDetails.getTelefone());
                    user.setEndereco(userDetails.getEndereco());
                    user.setNumero(userDetails.getNumero());
                    user.setComplemento(userDetails.getComplemento());
                    user.setAtivo(userDetails.getAtivo());
                    final Users updatedUser = usersRepository.save(user);
                    return ResponseEntity.ok(updatedUser);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um usuário
    @DeleteMapping("{usersID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar usuário",
        description = "Deleta um determinado usuário correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do usuário deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe usuário com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Integer usersID) {
        return usersRepository.findById(usersID)
                .map(user -> {
                    usersRepository.delete(user);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
