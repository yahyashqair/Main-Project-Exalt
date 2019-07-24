package com.exult.ProjectCisco.service.ifmDevice.Feature;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface FeatureService {
    Feature findFeature(String x);
    boolean deleteFeature(Long id);
    Feature updateFeature(Long id, String name, Maven maven);
    Feature insertFeature(String name, Maven maven);
    Feature findFeatureById(Long x);
    public Set<Xde> getFeatureXdeSet(Long id);
    List<Feature> getAllFeatures();
    List<Feature> findByNameLike(String username);
    Page<Feature> findAllPage(Pageable pageable);
}
