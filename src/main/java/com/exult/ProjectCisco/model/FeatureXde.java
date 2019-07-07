package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.ManyToOne;

public@Data
class FeatureXde {
    @ManyToOne
    private Feature feature;
    @ManyToOne
    private Xde xde ;
    private String typeOfRelation ;
}
