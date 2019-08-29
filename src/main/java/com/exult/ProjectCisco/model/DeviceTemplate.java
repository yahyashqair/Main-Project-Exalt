package com.exult.ProjectCisco.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class DeviceTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("CLI_ADDRESS")
    @Column(name = "CLI_ADDRESS")
    private String cliAddress;

    @JsonProperty("CLI_LOGIN_USERNAME")
    @Column(name = "CLI_LOGIN_USERNAME")
    private String cliLoginUsername;

    @JsonProperty("CLI_LOGIN_PASSWORD")
    @Column(name = "CLI_LOGIN_PASSWORD")
    private String cliLoginPassword;

    @JsonProperty("CLI_PORT")
    @Column(name = "CLI_PORT")
    private String cliPort;

    @JsonProperty("CLI_TRANSPORT")
    @Column(name = "CLI_TRANSPORT")
    private String cliTransport;

    @JsonProperty("CLI_ENABLE_PASSWORD")
    @Column(name = "CLI_ENABLE_PASSWORD")
    private String cliEnablePassword;

    @JsonProperty("SNMP_READ_CS")
    @Column(name = "SNMP_READ_CS")
    private String snmpReadCs;

    @JsonProperty("SNMP_PORT")
    @Column(name = "SNMP_PORT")
    private String snmpPort;

    @Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;
}
