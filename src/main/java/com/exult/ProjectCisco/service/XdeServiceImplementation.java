package com.exult.ProjectCisco.service;

import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.XdeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class XdeServiceImplementation implements XdeService{
    @Autowired
    private XdeRepository xdeRepository;

    /*
    * Add
    * Delete
    * Update
    * Find
    * */

    @Transactional
    Set<Xde> readData(){
        return (Set<Xde>) xdeRepository.findByName("Test");
    }

}
