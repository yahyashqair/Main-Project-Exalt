package com.exult.ProjectCisco.service.ifmDevice.Feature;

import com.exult.ProjectCisco.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface FeatureService {
    Feature findFeature(String x);
    boolean deleteFeature(Long id);
    Feature updateFeature(Long id, String name, Maven maven);
    Feature insertFeature(String name, Maven maven, Server server);
    Feature findFeatureById(Long x);
    public Set<Xde> getFeatureXdeSet(Long id);
    List<Feature> getAllFeatures();
    List<Feature> findByNameLike(String username);
    Page<Feature> findAllPage(Pageable pageable);
    List<Feature> getAllFeaturesBelongToServer(Server server);
}
