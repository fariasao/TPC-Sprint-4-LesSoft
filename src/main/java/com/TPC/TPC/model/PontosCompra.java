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
public class PontosCompra extends Object{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pontoscompraid")
    private Integer pontosCompraID;

    @NotNull (message = "{pontoscompras.compraid.notnull}")
    @ManyToOne
    @JoinColumn(name = "compraid", referencedColumnName = "compraid")
    private Compras compraID;

    @NotNull (message = "{pontoscompras.pointid.notnull}")
    @ManyToOne
    @JoinColumn(name = "pointid", referencedColumnName = "pointid")
    private Pontos pointID;

}
