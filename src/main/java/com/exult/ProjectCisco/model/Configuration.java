package com.exult.ProjectCisco.model;

import lombok.Data;

import javax.persistence.*;

@Embeddable
public @Data class Configuration {
    private String operation;
    private String value;
    private boolean isReg;
}
