package com.exult.ProjectCisco.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data
class Xde {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private MavenRepository mavenRepository;
}
