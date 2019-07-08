package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Integer> {
    Feature findByName(String name);
}
