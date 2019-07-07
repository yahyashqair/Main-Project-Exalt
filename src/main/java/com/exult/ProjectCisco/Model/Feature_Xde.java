package com.exult.ProjectCisco.Model;

import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public@Data
class Feature_Xde {
    @ManyToOne
    private Feature feature;
    @ManyToOne
    private Xde xde ;
    private String name ;
}
