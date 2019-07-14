package com.exult.ProjectCisco;

import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.repository.XdeRepository;
import com.exult.ProjectCisco.service.deviceLoader.DeviceLoader;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

@SpringBootApplication
public class ProjectCiscoApplication implements CommandLineRunner {
    
    @Autowired
    private MavenRepository mavenRepository;
    @Autowired
    private XdeRepository xdeRepository;
    @Autowired
    private XdeService xdeService;

    @Autowired
    DeviceLoader loader ;
    public static void main(String[] args) {
        SpringApplication.run(ProjectCiscoApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        //loader.run(new File("C:\\Users\\user\\Desktop\\device_packages_ifm"));
        //System.out.println(loader.findConfigurationsSet(new File("C:\\Users\\user\\Desktop\\device_packages_ifm\\ifm_device_profiles\\com.cisco.ifm.deviceprofile.cat4k_wireless\\xmpdevice.xml")));
    }

}