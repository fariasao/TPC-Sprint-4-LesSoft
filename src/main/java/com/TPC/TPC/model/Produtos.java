package com.TPC.TPC.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_produtos")
public class Produtos extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "produtoid")
    private Integer produtoID;

    @NotNull (message = "{produtos.pdvid.notnull}")
    @ManyToOne
    @JoinColumn(name = "pdvid", referencedColumnName = "pdvid")
    private Loja pdvID;
    
    @NotNull (message = "{produtos.categoriaid.notnull}")
    @ManyToOne
    @JoinColumn(name = "categoriaid", referencedColumnName = "categoriaid")
    private Categorias categoriaID;

    @NotBlank (message = "{produtos.nome.notnull}")
    @Size(max = 255, message = "{produtos.nome.size}")
    @Column(name = "nome")
    private String nome;

    @NotBlank (message = "{produtos.descricao.notnull}")
    @Size(max = 255, message = "{produtos.descricao.size}")
    @Column(name = "descricao")
    private String descricao;

    @NotNull (message = "{produtos.valor.notnull}")
    @Column(name = "valor")
    private double valor;

    @NotNull (message = "{produtos.ativo.notnull}")
    @Column(name = "ativo")
    private char ativo;
}
