package com.exult.ProjectCisco.service.ifmDevice.Xde;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.XdeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public @Setter
class XdeServiceImplementation implements XdeService {
    @Autowired
    private XdeRepository xdeRepository;
    /*
     * Add
     * Delete
     * Update
     * Find
     * */
    @Transactional
    public Xde findXde(String x) {
        try{
        List<Xde> xdeList = xdeRepository.findByName(x);
        if (xdeList.size() > 0) {
            return xdeList.get(0);
        }}catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public Xde findById(Long x) {
        return xdeRepository.findById(x).get();
    }

    @Transactional
    public boolean deleteXde(Long id) {
        Xde xde = xdeRepository.findById(id).get();
        xdeRepository.delete(xde);
        return true;
    }

    @Transactional
    public Xde updateXde(Long id, String name, Maven maven) {
        Xde xde = xdeRepository.findById(id).get();
        xde.setMaven(maven);
        xde.setName(name);
        return xde;
    }

    @Transactional
    public Xde insertXde(String name, Maven maven, Server server) {
        try {
            Xde xde = new Xde();
            xde.setServer(server);
            xde.setMaven(maven);
            xde.setName(name);
            xde = xdeRepository.save(xde);
            return xde;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
        }

    @Override
    public List<Xde> findAllXde() {
        return xdeRepository.findAll();
    }

    @Override
    public Page<Xde> findAllPage(Pageable P) {
        Page<Xde> xdePage=xdeRepository.findAll(P);
        return xdePage;
    }

    @Override
    public List<Xde> findByNameLike(String search) {
        return xdeRepository.findByNameLike(search);
    }

    @Override
    public List<Xde> getAllXdesBelongToServer(Server server) {
        return xdeRepository.findAllByServer(server);
    }

    @Override
    public Page<Xde> getAllXdesBelongToServer(Server server, Pageable p) {
        return xdeRepository.findAllByServer(server,p);
    }
}