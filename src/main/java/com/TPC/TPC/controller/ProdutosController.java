package com.TPC.TPC.controller;

import com.TPC.TPC.model.Produtos;
import com.TPC.TPC.repository.ProdutosRepository;

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
@RequestMapping("produtos")
@CacheConfig(cacheNames = "produtos")
@Tag(name = "Produtos", description = "Produtos cadastrados para relação com lojas que o vendem.")
public class ProdutosController {

    @Autowired
    private ProdutosRepository produtosRepository;

    // Buscar todos os produtos
    @GetMapping
    @Cacheable("produtos")
    @Operation(
        summary = "Listar produtos",
        description = "Retorna uma página com todos os produtos cadastrados, ordenados pelo valor."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Produtos retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existem produtos cadastrados.", useReturnTypeSchema = false)
    })
    public Page<Produtos> listarProdutos(
        @RequestParam(required = false) String produtos,
        @PageableDefault(sort = "valor", direction = Direction.ASC  ) Pageable pageable
    ) {
        if (produtos != null){
            return produtosRepository.findByValor(produtos, pageable);
        }
        return produtosRepository.findAll(pageable);
    }

    // Buscar um produto pelo ID
    @GetMapping("{produtoID}")
    @Operation(
        summary = "Listar produto por ID",
        description = "Retorna um determinado produto correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do produto retornados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe produto com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Produtos> getProdutoById(@PathVariable Integer produtoID) {
        return produtosRepository.findById(produtoID)
                .map(produto -> ResponseEntity.ok().body(produto))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Criar um novo produto
    @PostMapping
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Cadastrar produto",
        description = "Cadastra um novo produto a ser vinculado à lojas que o vendem com os dados enviados no corpo da requisição."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "201", description = "Cluster cadastrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição", useReturnTypeSchema = false)
    })
    public ResponseEntity<Produtos> createProduto(@Valid @RequestBody Produtos produto) {
        Produtos savedProduto = produtosRepository.save(produto);
        return new ResponseEntity<>(savedProduto, HttpStatus.CREATED);
    }

    // Atualizar um produto existente
    @PutMapping("{produtoID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Atualizar produto",
        description = "Atualiza um determinado produto correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do produto atualizados com sucesso."),
        @ApiResponse(responseCode = "400", description = "Validação falhou. Verifique as regras para o corpo da requisição.", useReturnTypeSchema = false),
        @ApiResponse(responseCode = "404", description = "Não existe produto com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<Produtos> updateProduto(@PathVariable Integer produtoID, @Valid @RequestBody Produtos produtoDetails) {
        return produtosRepository.findById(produtoID)
                .map(produto -> {
                    produto.setPdvID(produtoDetails.getPdvID());
                    produto.setCategoriaID(produtoDetails.getCategoriaID());
                    produto.setNome(produtoDetails.getNome());
                    produto.setDescricao(produtoDetails.getDescricao());
                    produto.setValor(produtoDetails.getValor());
                    produto.setAtivo(produtoDetails.getAtivo());
                    Produtos updatedProduto = produtosRepository.save(produto);
                    return ResponseEntity.ok().body(updatedProduto);
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um produto
    @DeleteMapping("{produtoID}")
    @CacheEvict(allEntries = true)
    @Operation(
        summary = "Deletar produto",
        description = "Deleta um determinado produto correspondente com o ID selecionado."
    )
    @ApiResponses({ 
        @ApiResponse(responseCode = "200", description = "Dados do produto deletados com sucesso."),
        @ApiResponse(responseCode = "404", description = "Não existe produto com o id informado.", useReturnTypeSchema = false)
    })
    public ResponseEntity<?> deleteProduto(@PathVariable Integer produtoID) {
        return produtosRepository.findById(produtoID)
                .map(produto -> {
                    produtosRepository.delete(produto);
                    return ResponseEntity.ok().build();
                }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
