package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Server {
    @Id
    private Long id ;
    private String ipAddress;
    private String password;
    private String hostName;
    private String username;
}
