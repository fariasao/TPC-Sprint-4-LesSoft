package com.TPC.TPC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Loja extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pdvid")
    private Integer pdvID;

    @NotBlank (message = "{loja.nomeloja.notnull}")
    @Size(max = 255, message = "{loja.nomeloja.size}")
    @Column(name = "nomeloja")
    private String nomeLoja;

    @NotBlank (message = "{loja.endereco.notnull}")
    @Size(max = 255, message = "{loja.endereco.size}")
    @Column(name = "endereco")
    private String endereco;

    @NotNull (message = "{loja.numero.notnull}")
    @Column(name = "numero")
    private Integer numero;

    @Size(max = 50, message = "{loja.complemento.size}")
    @Column(name = "complemento")
    private String complemento;

    @Size(max = 9, message = "{loja.cep.size}")
    @Column(name = "cep")
    private String cep;

    @Column(name = "active")
    private char active;
}
