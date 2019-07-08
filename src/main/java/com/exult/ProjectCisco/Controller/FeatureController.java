package com.exult.ProjectCisco.Controller;

import com.exult.ProjectCisco.dto.FeatureDto;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.Feature.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("feature")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @Autowired
    private MavenRepository mavenRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Optional<Feature> getFeature(@PathVariable("id") Long id) {
        return featureService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Feature postFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenRepository.findById(featureDto.getMavenId().intValue()).get());
    }

    // Update Feature
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Feature putFeature(FeatureDto featureDto) {
        return featureService.insertFeature(featureDto.getName(), mavenRepository.findById(featureDto.getMavenId().intValue()).get());
    }
    // Delete Feature
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteFeature(FeatureDto featureDto) {
        return featureService.deleteFeature(mavenRepository.findById(featureDto.getMavenId().intValue()).get().getId());
    }
}