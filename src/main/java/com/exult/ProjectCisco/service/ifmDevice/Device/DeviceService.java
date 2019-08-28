package com.exult.ProjectCisco.service.ifmDevice.Device;

import com.cisco.nm.expression.function.FunctionException;
import com.exult.ProjectCisco.model.Device;
import com.exult.ProjectCisco.model.Profile;
import com.exult.ProjectCisco.model.Server;

import java.util.List;

public interface DeviceService {
    Device getMatchingProfile(Device device) throws FunctionException;
    Device insertDevice(Device device) throws FunctionException;
    List<Device> getAllDevices();
    List<Device> getAllDevicesBelongToServer(Server server);
    Device getDevice(Long id);
    Device isMatch(Profile profile , Device device);
    Device isUpdated(Device device);
    Device syncDevice(Device device) throws FunctionException;
    Integer countAllByServer(Server server);
}
