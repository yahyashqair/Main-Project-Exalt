package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.service.ifmDevice.Device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("device")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Device insertDevice(@RequestBody Device device) {
        System.out.println(device);
        return deviceService.insertDevice(device);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Device getDevice(@PathVariable("id") Long id) {
        return deviceService.getDevice(id);
    }
}