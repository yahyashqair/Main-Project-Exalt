package com.exult.ProjectCisco.service.ifmDevice.Profile;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Profile;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProfileService {
    Set<Profile> findProfile(String x);
    boolean deleteProfile(Long id);
    Profile updateProfile(Long id, String name, Maven maven);
    Profile insertProfile(String name, Maven maven);
    Optional<Profile> findById(Long x);
    public List<Profile> findAll();

}
