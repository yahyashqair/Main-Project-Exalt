package com.exult.ProjectCisco.dto;

import lombok.Data;

@Data
public class DeviceDto {
private String CLI_ADDRESS;
    private String CLI_LOGIN_USERNAME;
    private String CLI_LOGIN_PASSWORD;
    private String CLI_PORT;
    private String CLI_TRANSPORT;
    private String SNMP_PORT;
    private String SNMP_READ_CS;
    private String cli_enable_password;
}
