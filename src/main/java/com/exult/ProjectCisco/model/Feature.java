package com.exult.ProjectCisco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public

class Feature {

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

    /*
     * split relation ManyToMany between " Feature and Xde " To 2 relations "OneToMany"
     * */
    @OneToMany(mappedBy = "feature")
    private Set<FeatureXde> xdeSet = new HashSet<FeatureXde>();

}
