package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.cisco.nm.expression.function.FunctionException;
import com.epnm.bootstrap.DeviceCredentials;
import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Criteria;
import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.DeviceRepository;
import com.exult.ProjectCisco.repository.ProfileRepository;
import com.exult.ProjectCisco.service.deviceLoader.Mdfdata;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class deviceServiceImplementaion implements DeviceService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private DeviceCredentials deviceCredentials;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Mdfdata mdfdata;


    private boolean testOrCondition(Criteria criteria, Map<String, String> map) {
        boolean b = false;
        for (Configuration configuration : criteria.getConfigurationSet()) {
            try {
                switch (configuration.getOperation()) {
                    case "equal":
                        if (map.get(criteria.getName()).equals(configuration.getValue())) {
                            b = true;
                        }
                        break;
                    case "lessAndEqual":
                        if (Integer.valueOf(map.get(criteria.getName())) <= Integer.valueOf(configuration.getValue())) {
                            b = true;
                        }
                        break;
                    case "greater":
                        if (Integer.valueOf(map.get(criteria.getName())) > Integer.valueOf(configuration.getValue())) {
                            b = true;
                        }
                        break;
                    case "greaterAndEqual":
                        if (Double.valueOf(map.get(criteria.getName())) >= Double.valueOf(configuration.getValue())) {
                            b = true;
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return b;
    }

    private boolean testAndCondition(Criteria criteria, Map<String, String> map) {

        boolean b = true;
        for (Configuration configuration : criteria.getConfigurationSet()) {
            try{
            switch (configuration.getOperation()) {
                case "equal":
                    if (!map.get(criteria.getName()).equals(configuration.getValue())) {
                        b = false;
                    }
                    break;
                case "lessAndEqual":
                    if (Integer.valueOf(map.get(criteria.getName())) > Integer.valueOf(configuration.getValue())) {
                        b = false;
                    }
                    break;
                case "greater":
                    if (Integer.valueOf(map.get(criteria.getName())) <= Integer.valueOf(configuration.getValue())) {
                        b = false;
                    }
                    break;
                case "greaterAndEqual":
                    if (Integer.valueOf(map.get(criteria.getName())) >= Integer.valueOf(configuration.getValue())) {
                        b = true;
                    }
                    break;
            }}catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return b;
    }

    @Override
    public Device getMatchingProfile(Device device) throws FunctionException {
        Map map = device.getConfigurations();
        List<Profile> profiles = profileRepository.findAll();
        List<Profile> matchProfile = new ArrayList<>();
        // Loop Over All Profiles
        for (Profile profile : profiles) {
            Map<String, Boolean> stringBooleanMap = new HashMap<>();
            // Loop over all criteria
            for (Criteria criteria : profile.getCriteriaSet()) {
                // if Device has this criteria
                if (map.containsKey(criteria.getName())) {
                    // Or operator or And operator
                    if (criteria.getOperator().equals("or")) {
                        boolean b = false;
                        b = testOrCondition(criteria, map);
                        if (b) {
                            stringBooleanMap.put(criteria.getName(), b);
                        } else {
                            if (!stringBooleanMap.containsKey(criteria.getName())) {
                                stringBooleanMap.put(criteria.getName(), b);
                            }
                        }
                    } else {
                        // IF AND OPERATOR
                        boolean b = testAndCondition(criteria, map);
                        if (!b) {
                            stringBooleanMap.put(criteria.getName(), b);
                        } else {
                            if (!stringBooleanMap.containsKey(criteria.getName())) {
                                stringBooleanMap.put(criteria.getName(), b);
                            }
                        }
                    }
                } else {
                    stringBooleanMap.put(criteria.getName(), false);
                    break;
                }
            }
            if (!stringBooleanMap.values().contains(false)) {
                matchProfile.add(profile);
            }
        }

        if (matchProfile.isEmpty()) {
            device.setProfileSet(matchProfile);
            deviceRepository.save(device);
            return device ;
        }
        for (int i = 0; i < matchProfile.size(); i++) {
            Profile p = matchProfile.get(i).getParent();
            while (p != null) {
                matchProfile.remove(p);
                p = p.getParent();
            }
        }

        device.setProfileSet(matchProfile);
        deviceRepository.save(device);
        return device ;
    }


    @Override
    public Device insertDevice(Device device) throws FunctionException {
        deviceToMap(device);
        deviceCredentials.readDeviceCredentials(device.getConfigurations());
        try {
            device.getConfigurations().putAll(mdfdata.getDeviceDetails(device.getConfigurations().get("sysObjectId")));
            System.out.println(device.getConfigurations());
            getMatchingProfile(device);
        } catch (Exception e) {
            e.printStackTrace();
        }
        deviceRepository.save(device);
        return device;
    }

    @Override
    public List<Device> getAllDevices() {
        for(Device device : deviceRepository.findAll()){
            isUpdated(device);
        }
        return deviceRepository.findAll();
    }

    @Override
    public Device getDevice(Long id) {
        return isUpdated(deviceRepository.findById(id).get());
    }

    @Override
    public Device isMatch(Profile profile, Device device) {
        Map map = device.getConfigurations();
        Map<String, Boolean> stringBooleanMap = new HashMap<>();
        // Loop over all criteria
        for (Criteria criteria : profile.getCriteriaSet()) {
            // if Device has this criteria
            if (map.containsKey(criteria.getName())) {
                // Or operator or And operator
                if (criteria.getOperator().equals("or")) {
                    boolean b = false;
                    b = testOrCondition(criteria, map);
                    if (b) {
                        stringBooleanMap.put(criteria.getName(), b);
                    } else {
                        if (!stringBooleanMap.containsKey(criteria.getName())) {
                            stringBooleanMap.put(criteria.getName(), b);
                        }
                    }
                } else {
                    // IF AND OPERATOR
                    boolean b = testAndCondition(criteria, map);
                    if (!b) {
                        stringBooleanMap.put(criteria.getName(), b);
                    } else {
                        if (!stringBooleanMap.containsKey(criteria.getName())) {
                            stringBooleanMap.put(criteria.getName(), b);
                        }
                    }
                }
            } else {
                stringBooleanMap.put(criteria.getName(), false);
                break;
            }
        }
        if (!stringBooleanMap.values().contains(false)) {
            device.getProfileSet().add(profile);
        }else{
            device.getProfileSet().remove(profile);
        }
        return device;
    }

    @Override
    @Transactional
    public Device isUpdated(Device device) {
        // loop over all profiles related to Device ( device )
        for(Profile profile:device.getProfileSet()){
            if(profile.getLocalDateTime().isAfter(device.getLocalDateTime())){
                isMatch(profile,device);
            }
        }
        deviceRepository.save(device);
        return device;
    }

    @Override
    public Device syncDevice(Device device) throws FunctionException {
        return insertDevice(device);
    }

    public void deviceToMap(Device device) {
        Map<String,String> map = device.getConfigurations();
        map.put("CLI_ADDRESS",device.getCliAddress());
        map.put("CLI_LOGIN_USERNAME",device.getCliLoginUsername());
        map.put("CLI_LOGIN_PASSWORD",device.getCliLoginPassword());
        map.put("CLI_PORT",device.getCliPort());
        map.put("CLI_TRANSPORT",device.getCliTransport());
        map.put("SNMP_READ_CS",device.getSnmpReadCs());
        map.put("CLI_ENABLE_PASSWORD",device.getCliEnablePassword());
        map.put("SNMP_PORT",device.getSnmpPort());
        System.out.println(map);
    }
}
