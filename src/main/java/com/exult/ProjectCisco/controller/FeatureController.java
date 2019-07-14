package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.FeatureDto;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Feature.FeatureService;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@Controller
@RestController
@RequestMapping("feature")
public class FeatureController {

    @Autowired
    private FeatureService featureService;
    @Autowired
    private XdeService xdeService;

    @Autowired
    private MavenRepository mavenRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Optional<Feature> getFeature(@PathVariable("id") Long id) {
        return featureService.findById(id);
    }

    @RequestMapping(value = "/xde/{id}", method = RequestMethod.GET)
    Set<Xde> getXde(@PathVariable("id") Long id) {
        Set<FeatureXde> featureXdes=featureService.findById(id).get().getXdeSet();
        Set<Xde> xdes = new HashSet<>();
        for(FeatureXde  featureXde: featureXdes){
            xdes.add(featureXde.getXde());
        }
    return xdes;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Feature postFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenRepository.findById(featureDto.getMavenId()).get());
    }

    // Update Feature
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Feature putFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenRepository.findById(featureDto.getMavenId()).get());
    }
    // Delete Feature
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteFeature(FeatureDto featureDto) {
        return featureService.deleteFeature(mavenRepository.findById(featureDto.getMavenId()).get().getId());
    }
}