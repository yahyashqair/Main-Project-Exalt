package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.XdeDto;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import com.exult.ProjectCisco.service.ifmDevice.maven.MavenService;
import com.exult.ProjectCisco.service.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping("xde")
@CrossOrigin(origins = "http://localhost:4200")
public class XdeController {

    @Autowired
    private XdeService xdeService;

    @Autowired
    private MavenService mavenService;
    @Autowired
    private ServerService serverService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    Xde getXde(@PathVariable("id") Long id) {
        return xdeService.findById(id);
    }

    @RequestMapping(value = {"/server/{id}/{qstring}", "/server/{id}/"}, method = RequestMethod.GET)
    public Page<Xde> getpro(@PathVariable("id") Long id, @PathVariable("qstring") Optional<String> string, @RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        String query = "";
        if (string.isPresent()) query = string.get();
        return xdeService.findByNameLikeAndServer(serverService.getServer(id), "%" + query + "%", PageRequest.of(pagenumber, size));
    }

    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    Page<Xde> getXdePage(@RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        return xdeService.findAllPage(PageRequest.of(pagenumber, size));
    }

    //    @RequestMapping(value = "/", method = RequestMethod.POST)
//    Xde postXde(XdeDto xdeDto) {
//        return xdeService.insertXde(xdeDto.getName(), mavenService.findMavenById(xdeDto.getMavenId()));
//    }
//
//    // Update Xde
//    @RequestMapping(value = "/", method = RequestMethod.PUT)
//    Xde putXde(XdeDto xdeDto) {
//        return xdeService.insertXde(xdeDto.getName(), mavenService.findMavenById(xdeDto.getMavenId()));
//    }
    // Delete Xde
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    boolean deleteXde(XdeDto xdeDto) {
        return xdeService.deleteXde(mavenService.findMavenById(xdeDto.getMavenId()).getId());
    }

//    @RequestMapping(value = "/server/{id}/", method = RequestMethod.GET)
//    Page<Xde> getXdePage(@PathVariable Long id, @RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
//        return xdeService.getAllXdesBelongToServer(serverService.getServer(id), PageRequest.of(pagenumber, size));
//    }

    @RequestMapping(value = "/count/{id}", method = RequestMethod.GET)
    public Integer countAllWithServer(@PathVariable("id") Long id) {
        return xdeService.countAllByServer(serverService.getServer(id));
    }
}