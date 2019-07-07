package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device , Integer> {

}
