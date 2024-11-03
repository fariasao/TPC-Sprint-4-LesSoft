package com.TPC.TPC.model;

import jakarta.persistence.*;
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
@Table(name = "tb_user_cluster")
public class UserCluster extends Object{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userclusterid")
    private Integer userClusterID;

    @NotNull(message = "{usercluster.clusterid.notnull}")
    @Column(name = "clusterid", nullable = false)
    private Integer clusterID;

    @NotNull(message = "{usercluster.usersid.notnull}")
    @Column(name = "usersid", nullable = false)
    private Integer usersID;
}
