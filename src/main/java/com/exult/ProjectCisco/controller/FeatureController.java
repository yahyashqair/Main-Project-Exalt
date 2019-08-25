package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.FeatureDto;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.service.ifmDevice.Feature.FeatureService;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
import com.exult.ProjectCisco.service.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RestController
@RequestMapping("/feature")
@CrossOrigin(origins = "http://localhost:4200")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private MavenService mavenService;

    @Autowired
    private ServerService serverService;
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Feature getFeature(@PathVariable("id") Long id) {
        return featureService.findFeatureById(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Feature> getFeatures() {
        return featureService.getAllFeatures();
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    Page<Feature> getFeaturePage(@RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        return featureService.findAllPage(PageRequest.of(pagenumber,size));
    }

    @RequestMapping(value = "/xde/{id}", method = RequestMethod.GET)
    public Set<Xde> getFeatureXdeSet(@PathVariable("id") Long id) {
        return featureService.getFeatureXdeSet(id);
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    Feature postFeature(FeatureDto featureDto) {
//        return featureService.insertFeature(featureDto.getName(), mavenService.findMavenById(featureDto.getMavenId()));
//    }

    @RequestMapping(value = "/search/{qstring}", method = RequestMethod.GET)
    public List<Feature> getpro(@PathVariable("qstring") String qstring) {
        return featureService.findByNameLike("%"+qstring+"%");
    }
//    // Update Feature
//    @RequestMapping(value = "/", method = RequestMethod.PUT)
//    Feature putFeature(FeatureDto featureDto) {
//        return featureService.insertFeature(featureDto.getName(), mavenService.findMavenById(featureDto.getMavenId()));
//    }

    // Delete Feature
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteFeature(FeatureDto featureDto) {
        return featureService.deleteFeature(mavenService.findMavenById(featureDto.getMavenId()).getId());
    }

    @RequestMapping(value = "/server/{id}/", method = RequestMethod.GET)
    Page<Feature> getFeaturePage(@PathVariable("id") Long id ,@RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        return featureService.getAllFeaturesBelongToServer(serverService.getServer(id),PageRequest.of(pagenumber,size));
    }

}