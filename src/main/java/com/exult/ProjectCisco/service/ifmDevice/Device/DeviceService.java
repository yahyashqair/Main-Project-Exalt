package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.cisco.nm.expression.function.FunctionException;
import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeviceService {
    List<Profile> getMatchingProfile(Map<String,String> map) throws FunctionException;
    Device insertDevice(Device device);
    List<Device> getAllDevices();
    Device getDevice(Long id);
}
