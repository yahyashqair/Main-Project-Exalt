package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.DeviceTemplate;
import com.exult.ProjectCisco.service.ifmDevice.DeviceTemplate.DeviceTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("template")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceTemplateController {

    @Autowired
    DeviceTemplateService deviceTemplateService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DeviceTemplate getDevice(@PathVariable("id") Long id ) {
        return deviceTemplateService.getDevice(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<DeviceTemplate> getDevices( ) {
        return deviceTemplateService.getAllDevices();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public DeviceTemplate insertDevice(@RequestBody DeviceTemplate device) {
        return deviceTemplateService.insertDevice(device);
    }

}
