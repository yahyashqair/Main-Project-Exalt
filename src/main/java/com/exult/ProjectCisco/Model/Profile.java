package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Profile {

    @Id
    private Long id;
    private String name;

    @ManyToMany(targetEntity = Feature.class)
    private Set<Feature> features = new HashSet<Feature>();

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Maven maven;


    @Transient
    private Set<Feature> excludeFeature = new HashSet<Feature>();

    /*
     * Each Feature has more than one configuration
     * */
    @ManyToMany
    @JoinTable(name = "profile_configuration", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "configuration_id"))
    private Set<Configuration> configurations = new HashSet<Configuration>();

}
