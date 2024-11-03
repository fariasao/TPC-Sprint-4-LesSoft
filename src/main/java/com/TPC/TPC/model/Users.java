package com.TPC.TPC.model;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "tb_users")
public class Users extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "usersid")
    private Integer usersID;

    @NotBlank (message = "{users.nome.notnull}")
    @Size(max = 50, message = "{users.nome.size}")
    @Column(name = "nome")
    private String nome;

    @NotBlank (message = "{users.sobrenome.notnull}")
    @Size(max = 50, message = "{users.sobrenome.size}")
    @Column(name = "sobrenome")
    private String sobrenome;

    @Email
    @NotBlank (message = "{users.email.notnull}")
    @Size(max = 50, message = "{users.email.size}")
    @Column(name = "email")
    private String email;

    @NotBlank (message = "{users.password.notnull}")
    @Size(max = 255, message = "{users.password.size}")
    @Column(name = "password")
    private String password;

    @NotBlank
    @Size(max = 15, message = "{users.telefone.size}")
    @Column(name = "telefone")
    private String telefone;

    @NotBlank (message = "{users.endereco.notnull}")
    @Size(max = 255, message = "{users.endereco.size}")
    @Column(name = "endereco")
    private String endereco;

    @NotBlank (message = "{users.numero.notnull}")
    @Size(max = 50, message = "{users.numero.size}")
    @Column(name = "numero")
    private String numero;

    @Size(max = 50, message = "{users.complemento.size}")
    @Column(name = "complemento")
    private String complemento;

    @NotNull (message = "{users.ativo.notnull}")
    @Column(name = "ativo")
    private char ativo;
}
