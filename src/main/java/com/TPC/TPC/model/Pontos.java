package com.TPC.TPC.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pontos extends Object{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pointid")
    private Integer pointID;

    @NotNull (message = "{pontos.valor.notnull}")
    @Column(name = "valor")
    private Integer valor;

    @NotNull (message = "{pontos.datacredito.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(name = "datacredito")
    private Date dataCredito;

    @NotNull (message = "{pontos.dataexpiracao.notnull}")
    @Temporal(TemporalType.DATE)
    @Column(name = "dataexpiracao")
    private Date dataExpiracao;

    @NotNull (message = "{pontos.utilizado.notnull}")
    @Column(name = "utilizado")
    private char utilizado;
}
