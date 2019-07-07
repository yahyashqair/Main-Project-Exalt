package com.exult.ProjectCisco.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;
    @ManyToMany(targetEntity = Feature.class)
    private Set<Feature> features = new HashSet<Feature>();

}
