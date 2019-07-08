package com.exult.ProjectCisco.Controller;

import com.exult.ProjectCisco.model.Xde;
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

    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
    Optional<Xde> getXde(@PathVariable("id") Integer id){
    return xdeService.findById(id);
    }
//    @RequestMapping(value = "/" ,method = RequestMethod.POST)
//    Optional<Xde> postXde(){
//        return xdeService.findById(id);
//    }




}
