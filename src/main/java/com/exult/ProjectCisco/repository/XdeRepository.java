package com.exult.ProjectCisco.repository;

import com.exult.ProjectCisco.model.Server;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XdeRepository extends JpaRepository<Xde,Long>, PagingAndSortingRepository<Xde, Long> {
    List<Xde> findByName(String name);
    Page<Xde> findAll(Pageable pageable);
    Page<Xde> findByNameLikeAndServer(String username,Server server,Pageable pageable);
    List<Xde> findAllByServer(Server server);
    Page<Xde> findAllByServer(Server server,Pageable pageable);
    Integer countAllByServer(Server server);

}
