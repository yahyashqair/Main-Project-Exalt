package com.exult.ProjectCisco.service.ifmDevice.Feature;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.repository.FeatureRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public  @Setter class  FeatureServiceImplementation implements FeatureService {
    @Autowired
    private FeatureRepository featureRepository;

    /*
    * Add
    * Delete
    * Update
    * Find
    * */

    @Transactional
    public Set<Feature> findFeature(String x){
        return (Set<Feature>) featureRepository.findByName(x);
    }

    @Transactional
    public boolean deleteFeature(Long id){
        Feature feature =  featureRepository.findById(id).get();
        featureRepository.delete(feature);
        return true;
    }
    @Transactional
    public Feature updateFeature(Long id, String name, Maven maven){
        Feature feature =  featureRepository.findById(id).get();
        feature.setMaven(maven);
        feature.setName(name);
        return feature;
    }

    @Transactional
    public Feature insertFeature(String name, Maven maven){
        Feature feature= new Feature();
        feature.setMaven(maven);
        feature.setName(name);
        feature=featureRepository.save(feature);
        return feature;
    }

    @Override
    public Optional<Feature> findById(Long x) {
        return featureRepository.findById(x);
    }


}
