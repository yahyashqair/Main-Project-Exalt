package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.service.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@Controller
@RestController
@RequestMapping("/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Server> getAllServers() {
        return serverService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Server getServer(@PathVariable("id") Long id) {
        return serverService.getServer(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void getData(@PathVariable("id") Long id) throws ParserConfigurationException, SAXException, IOException {
        serverService.ReadDataFromServer(id);
    }


}
