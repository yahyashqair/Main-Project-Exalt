package com.exult.ProjectCisco;

import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.repository.XdeRepository;
import com.exult.ProjectCisco.service.Xde.XdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ProjectCiscoApplication implements CommandLineRunner {

    @Autowired
    private MavenRepository mavenRepository;
    @Autowired
    private XdeRepository xdeRepository;
    @Autowired
    private  XdeService xdeService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectCiscoApplication.class, args);

        //        Maven maven1 = new Maven();
//        Maven maven2 = new Maven();
//        Maven maven3 = new Maven();
//        maven1.setGroupId("1");
//        maven1.setArtifactId("1");
//        maven1.setGroupArtifactId("11");
//
//        maven2.setGroupId("1");
//        maven2.setArtifactId("2");
//        maven2.setGroupArtifactId("12");
//
//        maven3.setGroupId("1");
//        maven3.setArtifactId("3");
//        maven3.setGroupArtifactId("13");
//        mavenRepository.save(maven1);
//        mavenRepository.save(maven2);
//        mavenRepository.save(maven3);
//
//        Xde xde=new Xde();
//        xde.setMaven(maven1);
//        xde.setName("FirstXde");
//        xdeRepository.save(xde);
//        System.out.println("Finished");

    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
                Maven maven1 = new Maven();
        Maven maven2 = new Maven();
        Maven maven3 = new Maven();
        maven1.setGroupId("1");
        maven1.setArtifactId("1");
        maven2.setGroupId("1");
        maven2.setArtifactId("2");
        maven3.setGroupId("1");
        maven3.setArtifactId("3");
        System.out.println("Start");
        mavenRepository.save(maven2);
//        mavenRepository.save(maven2);
//        mavenRepository.save(maven3);
//
//        Xde xde=new Xde();
//        xde.setMaven(maven1);
//        xde.setName("FirstXde");
//        xdeRepository.save(xde);
        System.out.println("Finished");
    }
}
