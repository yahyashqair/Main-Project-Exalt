package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature,Long>, PagingAndSortingRepository<Feature, Long> {
    List<Feature> findByName(String name);
    Page<Feature> findAll(Pageable pageable);
    List<Feature> findByNameLike(String username);
    List<Feature> findAllByServer(Server server);
    Page<Feature> findAllByServer(Server server,Pageable pageable);
}
