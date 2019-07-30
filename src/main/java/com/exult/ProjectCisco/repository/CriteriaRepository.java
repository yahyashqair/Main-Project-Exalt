package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria,Long> {

}
