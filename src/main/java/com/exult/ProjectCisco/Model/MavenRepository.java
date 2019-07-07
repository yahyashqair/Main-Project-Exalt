package com.exult.ProjectCisco.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public @Data
class MavenRepository {
    @Id
    private String groupArtifactId;
    private String groupId;
    private String artifactId;
    private String version;


}
