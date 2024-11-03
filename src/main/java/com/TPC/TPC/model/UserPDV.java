package com.TPC.TPC.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UserPDV extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userpdvid")
    private Integer userPdvID;

    @NotNull (message = "{userpdv.pdvid.notnull}")
    @ManyToOne
    @JoinColumn(name = "pdvid", referencedColumnName = "pdvid")
    private Loja pdvID;

    @NotBlank (message = "{userpdv.nome.notnull}")
    @Size(max = 255, message = "{userpdv.nome.size}")
    @Column(name = "nome")
    private String nome;

    @NotBlank (message = "{userpdv.sobrenome.notnull}")
    @Size(max = 255, message = "{userpdv.sobrenome.size}")
    @Column(name = "sobrenome")
    private String sobrenome;

    @Email
    @NotBlank (message = "{userpdv.email.notnull}")
    @Size(max = 255, message = "{userpdv.email.size}")
    @Column(name = "email")
    private String email;

    @NotBlank (message = "{userpdv.password.notnull}")
    @Size(max = 255, message = "{userpdv.password.size}")
    @Column(name = "password")
    private String password;

    @NotNull (message = "{userpdv.dataregistro.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(name = "dataregistro")
    private Date dataRegistro;

    @NotNull (message = "{userpdv.ativo.notnull}")
    @Column(name = "ativo")
    private char ativo;
}
