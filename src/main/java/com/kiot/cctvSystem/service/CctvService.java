package com.kiot.cctvSystem.service;

import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CctvService {
    private final DeviceRepository deviceRepository;
    public List<Device> getAllDeviceList() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList;
    }
}
