package com.exult.ProjectCisco.service.Profile;

import com.exult.ProjectCisco.model.Profile;
import java.util.Set;

public interface ProfileService {
    Set<Profile> findProfile(String x);
    boolean deleteProfile(Integer id);
    Profile updateProfile(Integer id, String name);
    Profile insertProfile(String name);
}
