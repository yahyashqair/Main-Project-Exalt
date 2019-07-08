package com.exult.ProjectCisco.Controller;

import com.exult.ProjectCisco.dto.ProfileDto;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.Profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private MavenRepository mavenRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Optional<Profile> getProfile(@PathVariable("id") Long id) {
        return profileService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Profile postProfile(ProfileDto profileDto) {
        return profileService.insertProfile(profileDto.getName(), mavenRepository.findById(profileDto.getMavenId().intValue()).get());
    }

    // Update Profile
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Profile putProfile(ProfileDto profileDto) {
        return profileService.insertProfile(profileDto.getName(), mavenRepository.findById(profileDto.getMavenId().intValue()).get());
    }
    // Delete Profile
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteProfile(ProfileDto profileDto) {
        return profileService.deleteProfile(mavenRepository.findById(profileDto.getMavenId().intValue()).get().getId());
    }
}