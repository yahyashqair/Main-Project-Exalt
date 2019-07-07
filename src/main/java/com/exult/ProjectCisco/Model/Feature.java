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
    @OneToOne
    @JoinColumn(name="id")
    @MapsId
    private MavenRepository mavenRepository;
    @ManyToMany(targetEntity=Xde.class)
    private Set<Xde> xdeSet=new HashSet<Xde>();

}
