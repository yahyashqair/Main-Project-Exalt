package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.DeviceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceTemplateRepository  extends JpaRepository<DeviceTemplate,Long> {

}
