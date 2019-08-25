package com.exult.ProjectCisco.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public @Data
class Profile {

    @Id
    private Long id;
    private String name;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @ManyToMany(targetEntity = Feature.class)
    private Set<Feature> features = new HashSet<Feature>();

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Maven maven;

    @ManyToOne
    @JoinColumn(name="parent_id")
    @JsonIgnore
    private Profile parent ;

    @Transient
    private Set<Feature> excludeFeature = new HashSet<Feature>();

    /*
     * Each Feature has more than one configuration
     * */
//    @ElementCollection
//    @CollectionTable(
//            joinColumns=@JoinColumn(name="profile_id")
//    )
    @ManyToMany
    @JoinTable(name = "profile_criteria", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "criteria_id"))
    private Set<Criteria> criteriaSet = new HashSet<Criteria>();

    @ManyToOne
    @JoinColumn(name="SERVER_ID")
    private Server server;

}
