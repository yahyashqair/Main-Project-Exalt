package com.exult.ProjectCisco.service.ifmDevice.Xde;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;

import java.util.Optional;
import java.util.Set;

public interface XdeService {
    Xde findXde(String x);
    Xde findById(Long x);
    boolean deleteXde(Long id);
    Xde updateXde(Long id, String name, Maven maven);
    Xde insertXde(String name , Maven maven);
}
