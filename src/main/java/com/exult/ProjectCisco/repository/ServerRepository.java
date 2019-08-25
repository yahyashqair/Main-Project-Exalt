package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends JpaRepository<Server,Long> {
    Server findByName(String name);
    List<Server> findByUsernameLike(String username);
}
