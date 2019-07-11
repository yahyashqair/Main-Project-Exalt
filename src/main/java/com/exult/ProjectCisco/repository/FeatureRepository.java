package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Long> {
    List<Feature> findByName(String name);

}
