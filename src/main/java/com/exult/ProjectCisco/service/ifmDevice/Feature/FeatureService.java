package com.exult.ProjectCisco.service.ifmDevice.Feature;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Maven;

import java.util.Optional;
import java.util.Set;

public interface FeatureService {
    Feature findFeature(String x);
    boolean deleteFeature(Long id);
    Feature updateFeature(Long id, String name, Maven maven);
    Feature insertFeature(String name, Maven maven);
    Feature findFeatureById(Long x);
}
