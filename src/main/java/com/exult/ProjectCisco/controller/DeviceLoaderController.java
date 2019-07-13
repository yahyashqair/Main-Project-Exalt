package com.exult.ProjectCisco.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("loader")
public class DeviceLoaderController {

@PostMapping(value = "/")
    String fun(){
    
    return null;
}

}
