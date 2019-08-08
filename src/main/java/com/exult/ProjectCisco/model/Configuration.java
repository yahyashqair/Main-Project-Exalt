package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;

@Embeddable
public @Data class Configuration {
    @Column(name="operation")
    private String operation;
    @Column(name="value")
    private String value;
    @Column(name="isReg")
    private boolean isReg;
}
