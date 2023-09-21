package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.domain.Alarm;
import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.repository.AlarmRepository;
import com.kiot.cctvSystem.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {

    private final DeviceRepository deviceRepository;
    private final AlarmRepository alarmRepository;

    public List<Device> getDeviceList() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList;
    }

    public List<Alarm> getMapAlarmList(Long deviceId) {
        List<Alarm> alarmList = alarmRepository.mapAlarmList(deviceId);
        return alarmList;
    }

}
