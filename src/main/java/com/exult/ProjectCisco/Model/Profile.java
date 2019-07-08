package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    @ManyToMany(targetEntity = Feature.class)
    private Set<Feature> features = new HashSet<Feature>();

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Maven maven;

    /*
    * Each Feature has more than one configuration
    * */
    @ManyToMany
    private Set<Configuration> configurations=new HashSet<Configuration>();

}
