package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long>, PagingAndSortingRepository<Profile, Long> {
    Profile findByName(String name);
    Page<Profile> findByNameLikeAndServer(String username,Server server,Pageable pageable);
    List<Profile> findAllByServer(Server server);
    Page<Profile> findAllByServer(Server server, Pageable pageable);
    Integer countAllByServer(Server server);
}
