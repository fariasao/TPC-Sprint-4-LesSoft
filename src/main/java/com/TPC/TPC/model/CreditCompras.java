package com.TPC.TPC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CreditCompras extends Object{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "creditcompraid")
    private Integer creditCompraID;
    
    @NotNull (message = "{creditcompras.creditid.notnull}")
    @ManyToOne
    @JoinColumn(name = "creditid", referencedColumnName = "creditid")
    private Credit creditID;

    @NotNull (message = "{creditcompras.compraid.notnull}")
    @ManyToOne
    @JoinColumn(name = "compraid", referencedColumnName = "compraid")
    private Compras compraID;
}
