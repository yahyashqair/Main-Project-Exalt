package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.ProfileRepository;
import com.exult.ProjectCisco.service.ifmDevice.Profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class deviceServiceImplementaion implements deviceService {
    @Autowired
    ProfileRepository profileRepository;

    @Override
    public List<Profile> getMatchingProfile(HashMap<String,String> map) {
        List<Profile> profiles = profileRepository.findAll();
        for (Profile profile:profiles) {
            Map<String,Boolean> stringBooleanMap=new HashMap<>();
            for (Configuration configuration:profile.getConfigurations()) {
                if(map.containsKey(configuration.getName())){
                    
                }
            }
        }
        return null;
    }
}
