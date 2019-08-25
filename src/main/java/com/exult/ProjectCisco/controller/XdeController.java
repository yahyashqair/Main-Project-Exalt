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

    @RequestMapping(value = "/search/{qstring}", method = RequestMethod.GET)
    public List<Xde> getpro(@PathVariable("qstring") String qstring) {
        return xdeService.findByNameLike("%" + qstring + "%");
    }

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    List<Xde> getXdes() {
//        return xdeService.findAllXde();
//    }

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

    @RequestMapping(value = "/server/{id}/", method = RequestMethod.GET)
    Page<Xde> getXdePage(@PathVariable Long id, @RequestParam(defaultValue = "pagenumber") int pagenumber, @RequestParam(defaultValue = "size") int size) {
        return xdeService.getAllXdesBelongToServer(serverService.getServer(id), PageRequest.of(pagenumber, size));
    }
}