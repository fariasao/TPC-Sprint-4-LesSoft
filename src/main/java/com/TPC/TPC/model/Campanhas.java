package com.TPC.TPC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
public class Campanhas extends Object{
    
    @Id
    @Column(name = "campanhaid")
    private Integer campanhaID;

    @NotNull (message =  "{campanhas.masterid.notnull}")
    @ManyToOne
    @JoinColumn(name = "masterid", referencedColumnName = "masterid")
    private UserMaster masterID;

    @NotNull (message =  "{campanhas.clusterid.notnull}")
    @ManyToOne
    @JoinColumn(name = "cluesterid", referencedColumnName = "clusterid")
    private Cluster clusterID;

    @NotBlank (message =  "{campanhas.titulo.notnull}")
    @Size(max = 255, message = "{campanhas.titulo.size}")
    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "conteudo")
    private String conteudo;

    @NotNull (message =  "{campanhas.descricao.notnull}")
    @Lob
    @Column(name = "descricao")
    private String descricao;

    @NotNull (message =  "{campanhas.canaltipo.notnull}")
    @Column(name = "canaltipo")
    private Integer canalTipo;
}
