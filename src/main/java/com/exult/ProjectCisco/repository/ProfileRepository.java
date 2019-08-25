package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long>, PagingAndSortingRepository<Profile, Long> {
    Profile findByName(String name);
    List<Profile> findByNameLike(String username);
    List<Profile> findAllByServer(Server server);
}
