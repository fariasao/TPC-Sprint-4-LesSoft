package com.TPC.TPC.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "tb_categorias")
public class Categorias extends Object{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoriaid")
    private Integer categoriaID;

    @NotBlank (message =  "{categorias.nome.notnull}")
    @Size(max = 50, message = "{categorias.nome.size}")
    @Column(name = "nome")
    private String nome;

    @NotBlank (message =  "{categorias.descricao.notnull}")
    @Size(max = 255, message = "{categorias.descricao.size}")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private char ativo;
}
