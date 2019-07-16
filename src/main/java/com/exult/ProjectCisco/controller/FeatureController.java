package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.FeatureDto;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Feature.FeatureService;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
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
@RequestMapping("/feature")
public class FeatureController {

    @Autowired
    private FeatureService featureService;
    @Autowired
    private XdeService xdeService;

    @Autowired
    private MavenService mavenService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Feature getFeature(@PathVariable("id") Long id) {
        return featureService.findFeatureById(id);
    }
    @RequestMapping(value = "/xde/{id}", method = RequestMethod.GET)
    public Set<Xde> getFeatureXdeSet(@PathVariable("id") Long id) {
        return featureService.getFeatureXdeSet(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Feature postFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenService.findMavenById(featureDto.getMavenId()));
    }
    // Update Feature
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Feature putFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenService.findMavenById(featureDto.getMavenId()));
    }
    // Delete Feature
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteFeature(FeatureDto featureDto) {
        return featureService.deleteFeature(mavenService.findMavenById(featureDto.getMavenId()).getId());
    }
}