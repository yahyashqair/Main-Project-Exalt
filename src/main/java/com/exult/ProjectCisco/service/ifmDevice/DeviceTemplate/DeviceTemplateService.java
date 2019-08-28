package com.exult.ProjectCisco.service.ifmDevice.DeviceTemplate;

import com.exult.ProjectCisco.model.DeviceTemplate;

import java.util.List;

public interface DeviceTemplateService {
    DeviceTemplate insertDevice(DeviceTemplate device) ;
    List<DeviceTemplate> getAllDevices();
    DeviceTemplate getDevice(Long id);
}
