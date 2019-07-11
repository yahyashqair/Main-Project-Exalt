package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Xde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface XdeRepository extends JpaRepository<Xde,Long> {
    List<Xde> findByName(String name);
}
