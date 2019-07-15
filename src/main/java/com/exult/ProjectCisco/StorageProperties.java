package com.exult.ProjectCisco;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
@Data
public class StorageProperties {

    private String location;


}