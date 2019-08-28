package com.exult.ProjectCisco.service.ifmDevice.DeviceTemplate;

import com.exult.ProjectCisco.model.DeviceTemplate;
import com.exult.ProjectCisco.repository.DeviceTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTemplateImplemention implements DeviceTemplateService{

    @Autowired
    DeviceTemplateRepository deviceTemplateRepository;

    @Override
    public DeviceTemplate insertDevice(DeviceTemplate device) {
        return deviceTemplateRepository.save(device);
    }

    @Override
    public List<DeviceTemplate> getAllDevices() {
        return deviceTemplateRepository.findAll();
    }

    @Override
    public DeviceTemplate getDevice(Long id) {
        return deviceTemplateRepository.findById(id).get();
    }

}
