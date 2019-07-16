package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.ProfileDto;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Profile.ProfileService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MavenService mavenService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Profile getProfile(@PathVariable("id") Long id) {
        return profileService.findById(id);
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    List<Profile> getAllProfile() {
        return profileService.findAll();
    }

    @RequestMapping(value = "/feature/{id}", method = RequestMethod.GET)
    Set<Feature> getFeature(@PathVariable("id") Long id) {
        return profileService.findById(id).getFeatures();
    }

    @RequestMapping(value = "/xde/{id}", method = RequestMethod.GET)
    Set<FeatureXde> getXde(@PathVariable("id") Long id) {
        return profileService.getFeatureXde(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Profile> getProfiles() {
        return profileService.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Profile postProfile(@RequestBody ProfileDto profileDto) {
        System.out.println(profileDto);
        return profileService.insertProfile(profileDto.getName(), mavenService.findMavenById(profileDto.getMavenId()));
    }

    // Update Profile
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Profile putProfile(ProfileDto profileDto) {
        return profileService.insertProfile(profileDto.getName(), mavenService.findMavenById(profileDto.getMavenId()));
    }

    // Delete Profile
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteProfile(ProfileDto profileDto) {
        return profileService.deleteProfile(mavenService.findMavenById(profileDto.getMavenId()).getId());
    }
}