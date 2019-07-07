package com.exult.ProjectCisco.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;
    private String name ;
    /*
    * Take the id from Maven Repository
    * */
    @OneToOne
    @JoinColumn(name="id")
    @MapsId
    private MavenRepository mavenRepository;
    /*
    * split relation ManyToMany between " Feature and Xde " To 2 relations "OneToMany"
    * */
    @OneToMany(targetEntity=Feature_Xde.class)
    private Set<Feature_Xde> xdeSet=new HashSet<Feature_Xde>();

}
