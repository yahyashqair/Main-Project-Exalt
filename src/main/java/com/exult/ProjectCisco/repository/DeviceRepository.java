package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device , Long> {
    List<Device> findAllByServer(Server server);
    Integer countAllByServer(Server server);
}
