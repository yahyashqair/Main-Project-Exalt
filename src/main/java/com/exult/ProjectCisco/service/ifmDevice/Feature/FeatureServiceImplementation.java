package com.exult.ProjectCisco.service.ifmDevice.Feature;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.FeatureRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
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

    public Set<Xde> getFeatureXdeSet(Long id) {
        // move logic to service
        //
        Set<FeatureXde> featureXdes=findFeatureById(id).getXdeSet();
        Set<Xde> xdes = new HashSet<>();
        for(FeatureXde  featureXde: featureXdes){
            xdes.add(featureXde.getXde());
        }
        return xdes;
    }

    @Transactional
    public Feature findFeature(String x){
        List<Feature> featureList = featureRepository.findByName(x);
        if(featureList.size()>0){
            return featureList.get(0);
        }
        return null;
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
    public Feature findFeatureById(Long x) {
        return featureRepository.findById(x).get();
    }


}
