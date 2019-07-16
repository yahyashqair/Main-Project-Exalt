package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.XdeDto;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("xde")
public class XdeController {

    @Autowired
    private XdeService xdeService;

    @Autowired
    private MavenService mavenService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Xde getXde(@PathVariable("id") Long id) {
        return xdeService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Xde postXde(XdeDto xdeDto) {
        return xdeService.insertXde(xdeDto.getName(), mavenService.findMavenById(xdeDto.getMavenId()));
    }

    // Update Xde
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Xde putXde(XdeDto xdeDto) {
        return xdeService.insertXde(xdeDto.getName(), mavenService.findMavenById(xdeDto.getMavenId()));
    }
    // Delete Xde
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteXde(XdeDto xdeDto) {
        return xdeService.deleteXde(mavenService.findMavenById(xdeDto.getMavenId()).getId());
    }
}