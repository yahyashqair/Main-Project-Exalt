package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"artifactId", "groupId"})
})
public class Maven {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupId;
    private String artifactId;
    private String version;
}


