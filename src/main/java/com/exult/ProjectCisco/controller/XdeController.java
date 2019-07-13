package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.XdeDto;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
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
    private MavenRepository mavenRepository;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Optional<Xde> getXde(@PathVariable("id") Long id) {
        return xdeService.findById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    Xde postXde(XdeDto xdeDto) {
        return xdeService.insertXde(xdeDto.getName(), mavenRepository.findById(xdeDto.getMavenId()).get());
    }

    // Update Xde
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Xde putXde(XdeDto xdeDto) {
        return xdeService.insertXde(xdeDto.getName(), mavenRepository.findById(xdeDto.getMavenId()).get());
    }
    // Delete Xde
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteXde(XdeDto xdeDto) {
        return xdeService.deleteXde(mavenRepository.findById(xdeDto.getMavenId()).get().getId());
    }
}