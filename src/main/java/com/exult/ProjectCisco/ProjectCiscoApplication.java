package com.exult.ProjectCisco;

import com.epnm.bootstrap.DeviceCredentials;
import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.repository.MavenRepository;
import com.exult.ProjectCisco.repository.XdeRepository;
import com.exult.ProjectCisco.service.deviceLoader.DeviceLoader;
import com.exult.ProjectCisco.service.deviceLoader.Mdfdata;
import com.exult.ProjectCisco.service.ifmDevice.Device.DeviceService;
import com.exult.ProjectCisco.service.ifmDevice.Profile.ProfileService;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import com.exult.ProjectCisco.service.server.ServerService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@ImportResource("classpath:beans.xml")
public class ProjectCiscoApplication implements CommandLineRunner {
    @Autowired
    private MavenRepository mavenRepository;
    @Autowired
    private XdeRepository xdeRepository;
    @Autowired
    private XdeService xdeService;
    @Autowired
    private DeviceLoader loader;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private Mdfdata mdfdata;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private ServerService serverService;

    @Autowired
    private DeviceCredentials deviceCredentials;

    public static void main(String[] args) {
        SpringApplication.run(ProjectCiscoApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("productSeries","281716314");
//        map.put("software","IOS");
//        System.out.println(deviceService.getMatchingProfile(map));
        // mdfdata.getDeviceDetails("1.3.6.1.4.1.9.1.924");
//        map.put("CLI_ADDRESS", "10.63.10.206");
//        map.put("CLI_LOGIN_USERNAME", "lab");
//        map.put("CLI_LOGIN_PASSWORD", "lab");
//        map.put("CLI_PORT", "23");
//        map.put("CLI_TRANSPORT", "telnet");
//        map.put("SNMP_READ_CS", "public");
//        map.put("CLI_ENABLE_PASSWORD", "lab");
//        map.put("SNMP_PORT", "161");
//        deviceCredentials.readDeviceCredentials(map);
//        System.out.println(map);
//        Device device = new Device();
//        Class<Device> deviceClass = Device.class;
//        Field[] fields = deviceClass.getDeclaredFields();
//        for (Field field : fields) {
//            JsonProperty annotation = field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class);
//            if (annotation != null) {
//                String key=annotation.value();
//                String fieldName=field.getName();
//                String methodName="get"+Character.toUpperCase(fieldName.charAt(0))+fieldName.substring(1);
//                Method method =deviceClass.getMethod(methodName);
//                Object value=method.invoke(device);
//            }
//        }

//        System.err.println("Result");
//        System.out.println(deviceService.getMatchingProfile(map));
        //loader.run(new File("C:\\Users\\user\\Desktop\\devices\\device_packages_ifm"));
        //System.out.println(loader.findConfigurationsSet(new File("C:\\Users\\user\\Desktop\\device_packages_ifm\\ifm_device_profiles\\com.cisco.ifm.deviceprofile.cat4k_wireless\\xmpdevice.xml")));
        //serverService.zipFile("root","172.20.68.7","Karmi123");
        //loader.runServer(new File("C:\\Users\\user\\Desktop\\ProjectCisco\\files"));

    }

}