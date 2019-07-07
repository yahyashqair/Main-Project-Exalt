package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public @Data
class Maven {
    @Id
    private String groupArtifactId;
    private String groupId;
    private String artifactId;
    private String version;
}