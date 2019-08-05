package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.cisco.nm.expression.function.FunctionException;
import com.epnm.bootstrap.DeviceCredentials;
import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Criteria;
import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.DeviceRepository;
import com.exult.ProjectCisco.repository.ProfileRepository;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            }
        }
        return b;
    }

    @Override
    public List<Profile> getMatchingProfile(Map<String, String> map) throws FunctionException {
        deviceCredentials.readDeviceCredentials(map);
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
            return matchProfile;
        }
        for (int i = 0; i < matchProfile.size(); i++) {
            Profile p = matchProfile.get(i).getParent();
            while (p != null) {
                matchProfile.remove(p);
                p = p.getParent();
            }
        }
        return matchProfile;
    }

    @Override
    public Device insertDevice(Device device) {
        try {
            device.setProfileSet(getMatchingProfile(deviceToMap(device)));
        } catch (FunctionException e) {
            e.printStackTrace();
        }
        deviceRepository.save(device);
        return device;
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device getDevice(Long id) {
        return deviceRepository.findById(id).get();
    }

    public Map<String, String> deviceToMap(Device device) {
        Map<String,String> map = new HashMap<>();
        map.put("CLI_ADDRESS",device.getCli_ADDRESS());
        map.put("CLI_LOGIN_USERNAME",device.getCli_LOGIN_USERNAME());
        map.put("CLI_LOGIN_PASSWORD",device.getCli_LOGIN_PASSWORD());
        map.put("CLI_PORT",device.getCli_PORT());
        map.put("CLI_TRANSPORT",device.getCli_TRANSPORT());
        map.put("SNMP_READ_CS",device.getSnmp_READ_CS());
        map.put("CLI_ENABLE_PASSWORD",device.getCli_enable_password());
        map.put("SNMP_PORT",device.getSnmp_PORT());
        System.out.println(map);
        return map;
    }
}
