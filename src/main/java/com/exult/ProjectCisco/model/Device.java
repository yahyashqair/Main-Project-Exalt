package com.exult.ProjectCisco.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public @Data
class Device {

    @ElementCollection
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    @CollectionTable(name = "device_Configuration", joinColumns = @JoinColumn(name = "device_id"))
    Map<String, String> configurations = new HashMap<String, String>(); // maps from attribute name to value

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("CLI_ADDRESS")
    @Column(name="CLI_ADDRESS")
    private String cliAddress;

    @JsonProperty("CLI_LOGIN_USERNAME")
    @Column(name="CLI_LOGIN_USERNAME")
    private String cliLoginUsername;

    @JsonProperty("CLI_LOGIN_PASSWORD")
    @Column(name="CLI_LOGIN_PASSWORD")

    private String cliLoginPassword;

    @JsonProperty("CLI_PORT")
    @Column(name="CLI_PORT")

    private String cliPort;

    @JsonProperty("CLI_TRANSPORT")
    @Column(name="CLI_TRANSPORT")

    private String cliTransport;

    @JsonProperty("CLI_ENABLE_PASSWORD")
    @Column(name="CLI_ENABLE_PASSWORD")
    private String cliEnablePassword;

    @JsonProperty("SNMP_READ_CS")
    @Column(name="SNMP_READ_CS")
    private String snmpReadCs;

    @JsonProperty("SNMP_PORT")
    @Column(name="SNMP_PORT")
    private String snmpPort;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @JsonProperty(access = Access.READ_ONLY)
    @ManyToMany
    private List<Profile> profileSet = new ArrayList<>();

}
