package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data
class Xde {
    @Id
    private Long id;
    private String name;
    /*
     * Take the id from Maven repository
     * */
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Maven maven;


    // Server Relation ;
    @ManyToOne
    @JoinColumn(name="SERVER_ID")
    private Server server;
}
