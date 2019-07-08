package com.exult.ProjectCisco.service.Feature;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Maven;

import java.util.Set;

public interface FeatureService {
    Set<Feature> findFeature(String x);
    boolean deleteFeature(Integer id);
    Feature updateFeature(Integer id, String name, Maven maven);
    Feature insertFeature(String name, Maven maven);
}
