package com.exult.ProjectCisco.Controller;

import com.exult.ProjectCisco.dto.XdeDto;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.service.Xde.XdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RestController
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
        return xdeService.insertXde(xdeDto.getName(), mavenRepository.findById(xdeDto.getMavenId().intValue()).get());
    }

    // Update Xde
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    Xde putXde(XdeDto xdeDto) {
        return xdeService.insertXde(xdeDto.getName(), mavenRepository.findById(xdeDto.getMavenId().intValue()).get());
    }
    // Delete Xde
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteXde(XdeDto xdeDto) {
        return xdeService.deleteXde(mavenRepository.findById(xdeDto.getMavenId().intValue()).get().getId());
    }
}