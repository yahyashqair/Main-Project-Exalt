package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@Data
public class Criteria {
    private String name;
    private String operater ;
    private String operation;
    @ElementCollection
    @CollectionTable(
            joinColumns=@JoinColumn(name="profile_id")
    )
    private Set<Configuration> configurationSet= new HashSet<Configuration>();
}
