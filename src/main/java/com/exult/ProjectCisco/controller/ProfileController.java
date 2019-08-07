package com.exult.ProjectCisco.controller;

import com.cisco.nm.expression.function.FunctionException;
import com.exult.ProjectCisco.dto.DeviceDto;
import com.exult.ProjectCisco.dto.ProfileDto;
import com.exult.ProjectCisco.dto.ProfileRelation;
import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Device.DeviceService;
import com.exult.ProjectCisco.service.ifmDevice.Profile.ProfileService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
import org.hibernate.annotations.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MavenService mavenService;

    @Autowired
    private DeviceService deviceService ;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Profile getProfile(@PathVariable("id") Long id) {
        return profileService.findById(id);
    }

//    @RequestMapping(value = "/match/", method = RequestMethod.POST)
//    public List<Profile> getMatchingProfiles(@RequestBody HashMap<String,String> map) throws FunctionException {
//         return deviceService.getMatchingProfile(map);
//    }


    @RequestMapping(value = "/parents/{id}", method = RequestMethod.GET)
    public List<Profile> getParents(@PathVariable("id") Long id) {
        List<Profile> profileList = new ArrayList<>();
        Profile profile = profileService.findById(id);
        profileList.add(profile);
        while(profile.getParent()!=null){
            profileList.add(profile.getParent());
            profile = profile.getParent();
        }
        return profileList;
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    Page<Profile> findAllPage(@RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        return profileService.findAllPage(PageRequest.of(pagenumber,size));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Profile> getAllProfile() {
        return profileService.findAll();
    }

    @RequestMapping(value = "/feature/{id}", method = RequestMethod.GET)
    public Set<Feature> getFeature(@PathVariable("id") Long id) {
        return profileService.findById(id).getFeatures();
    }

    @RequestMapping(value = "/search/{qstring}", method = RequestMethod.GET)
    public List<Profile> getpro(@PathVariable("qstring") String qstring) {
        return profileService.findByNameLike("%"+qstring+"%");
    }
    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public List<Profile> getpros() {
        return  findAllPage(1,10).getContent();
    }
    @RequestMapping(value = "/xde/{id}", method = RequestMethod.GET)
    public Set<FeatureXde> getXde(@PathVariable("id") Long id) {
        return profileService.getFeatureXde(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Profile> getProfiles() {
        return profileService.findAll();
    }

    @RequestMapping(value = "/relations/", method = RequestMethod.GET)
    public List<ProfileRelation> getRelations() {
           List<Profile> profiles=profileService.findAll();
           List<ProfileRelation> profileRelations = new ArrayList<ProfileRelation>();
        for (Profile profile:profiles) {
            if(profile.getParent()!=null){
                ProfileRelation profileRelation = new ProfileRelation();
                profileRelation.setChild(profile.getId());

                profileRelation.setParent(profile.getParent().getId());
                profileRelations.add(profileRelation);}
        }
        return profileRelations;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Profile postProfile(@RequestBody ProfileDto profileDto) {
        System.out.println(profileDto);
        return profileService.insertProfile(profileDto.getName(), mavenService.findMavenById(profileDto.getMavenId()));
    }
    // Update Profile
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Profile putProfile(ProfileDto profileDto) {
        return profileService.insertProfile(profileDto.getName(), mavenService.findMavenById(profileDto.getMavenId()));
    }
    // Delete Profile
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public boolean deleteProfile(ProfileDto profileDto) {
        return profileService.deleteProfile(mavenService.findMavenById(profileDto.getMavenId()).getId());
    }
}