package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;

public @Data class Configuration {
    @Id
    private String name ;
}
