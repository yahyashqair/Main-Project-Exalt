package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    /*
     * Take the id from Maven repository
     * */
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Maven maven;

    /*
     * split relation ManyToMany between " Feature and Xde " To 2 relations "OneToMany"
     * */
    @OneToMany(targetEntity = FeatureXde.class)
    private Set<FeatureXde> xdeSet = new HashSet<FeatureXde>();

}
