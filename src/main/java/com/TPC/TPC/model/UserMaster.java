package com.TPC.TPC.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
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
public class UserMaster extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "masterid")
    private Integer masterID;

    @NotBlank (message = "{usermaster.nome.notnull}")
    @Size(max = 255, message = "{usermaster.nome.size}")
    @Column(name = "nome")
    private String nome;

    @NotBlank (message = "{usermaster.sobrenome.notnull}")
    @Size(max = 255, message = "{usermaster.sobrenome.size}")
    @Column(name = "sobrenome")
    private String sobrenome;

    @Email
    @NotBlank (message = "{usermaster.email.notnull}")
    @Size(max = 255, message = "{usermaster.email.size}")
    @Column(name = "email")
    private String email;

    @NotBlank (message = "{usermaster.password.notnull}")
    @Size(max = 255, message = "{usermaster.password.size}")
    @Column(name = "password")
    private String password;

    @NotNull (message = "{usermaster.dataregistro.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(name = "dataregistro")
    private Date dataRegistro;

    @NotNull (message = "{usermaster.ativo.notnull}")
    @Column(name = "ativo")
    private char ativo;
}
