package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.cisco.nm.expression.function.FunctionException;
import com.epnm.bootstrap.DeviceCredentials;
import com.exult.ProjectCisco.model.Configuration;
import com.exult.ProjectCisco.model.Criteria;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class deviceServiceImplementaion implements DeviceService {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    DeviceCredentials deviceCredentials;

    @Override
    public List<Profile> getMatchingProfile(HashMap<String, String> map) throws FunctionException {
        deviceCredentials.readDeviceCredentials(map);
        List<Profile> profiles = profileRepository.findAll();
        List<Profile> matchProfile = new ArrayList<>();
        for (Profile profile : profiles) {
            Map<String, Boolean> stringBooleanMap = new HashMap<>();
            for (Criteria criteria : profile.getCriteriaSet()) {
                // LOOP OVER Configurations
                if(map.containsKey(criteria.getName())){
                if (criteria.getOperator().equals("or")) {
                    boolean b = false;
                    for (Configuration configuration : criteria.getConfigurationSet()) {
                       try{
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
                        }} catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                    }
                    if(b){
                        stringBooleanMap.put(criteria.getName(), b);
                    }else{
                        if(!stringBooleanMap.containsKey(criteria.getName())) {
                            stringBooleanMap.put(criteria.getName(), b);
                        }
                    }

                } else {
                    // IF AND OPERATOR
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
                    if(!b){
                        stringBooleanMap.put(criteria.getName(), b);
                    }else{
                        if(!stringBooleanMap.containsKey(criteria.getName())) {
                            stringBooleanMap.put(criteria.getName(), b);
                        }
                    }
                }
            }else{
                    stringBooleanMap.put(criteria.getName(),false);
                    break;
                }
            }
            if (stringBooleanMap.size()>0&&!stringBooleanMap.values().contains(false)){
                matchProfile.add(profile);
            }
        }

        if(matchProfile.isEmpty()){
            return matchProfile;
        }
        for (int i = 0 ; i < matchProfile.size();i++){
            Profile p = matchProfile.get(i).getParent();
            while(p!=null){
                matchProfile.remove(p);
                p=p.getParent();
            }
        }
        return matchProfile;
    }
}
