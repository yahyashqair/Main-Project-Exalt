package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public @Data
class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cli_ADDRESS;
    private String cli_LOGIN_USERNAME;
    private String cli_LOGIN_PASSWORD;
    private String cli_PORT;
    private String cli_TRANSPORT;
    private String cli_enable_password;
    private String snmp_READ_CS;
    private String snmp_PORT;

        @ManyToMany
        private List<Profile> profileSet= new ArrayList<>();
}
