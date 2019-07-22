package com.exult.ProjectCisco.service.ifmDevice.Profile;

import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Profile;

import java.util.List;
import java.util.Set;

public interface ProfileService {
    Profile findProfile(String x);
    boolean deleteProfile(Long id);
    Profile updateProfile(Long id, String name, Maven maven);
    Profile insertProfile(String name, Maven maven);
    Profile findById(Long x);
    public List<Profile> findAll();
    Set<FeatureXde> getFeatureXde(long id);

}
