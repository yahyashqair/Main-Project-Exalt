package com.exult.ProjectCisco.service;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;

import java.util.Set;

public interface XdeService {
    Set<Xde> findXde(String x);
    boolean deleteXde(Integer id);
    Xde updateXde(Integer id, String name, Maven maven);
    Xde insertXde(String name , Maven maven);
}
