package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
@Entity
public @Data class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private String value ;
}
