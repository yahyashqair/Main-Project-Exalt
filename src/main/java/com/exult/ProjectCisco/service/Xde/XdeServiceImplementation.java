package com.exult.ProjectCisco.service.Xde;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.repository.XdeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public  @Setter class  XdeServiceImplementation implements XdeService {
    @Autowired
    private XdeRepository xdeRepository;


    /*
    * Add
    * Delete
    * Update
    * Find
    * */

    @Transactional
    public Set<Xde> findXde(String x){
        return (Set<Xde>) xdeRepository.findByName(x);
    }

    @Override
    public Optional<Xde> findById(Long x) {
        return xdeRepository.findById(x);
    }

    @Transactional
    public boolean deleteXde(Long id){
        Xde xde =  xdeRepository.findById(id).get();
        xdeRepository.delete(xde);
        return true;
    }
    @Transactional
    public Xde updateXde(Long id, String name, Maven maven){
        Xde xde =  xdeRepository.findById(id).get();
        xde.setMaven(maven);
        xde.setName(name);
        return xde;
    }
    @Transactional
    public Xde insertXde(String name, Maven maven){
        Xde xde = new Xde();
        xde.setMaven(maven);
        xde.setName(name);
        xde=xdeRepository.save(xde);
        return xde;
    }
}