package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.exult.ProjectCisco.model.Profile;

import java.util.HashMap;
import java.util.List;

public interface DeviceService {
    List<Profile> getMatchingProfile(HashMap<String,String> map);
}
