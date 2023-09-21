package com.kiot.cctvSystem.mapper;

import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.dto.DeviceDto;
import com.kiot.cctvSystem.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceMapper {

    private final DeviceRepository deviceRepository;

    public DeviceDto toDeviceDto(Device device) {
        return new DeviceDto(
                device.getId(),
                device.getDeviceId(),
                device.getName(),
                device.getAddress(),
                device.getRegion(),
                device.getUrl(),
                device.getLon(),
                device.getLat()
        );
    }
    public Device toDeviceEntity(DeviceDto deviceDto) {
        Device device = deviceRepository.findById(deviceDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("해당 장비는 존재하지 않습니다 : " + deviceDto.getId()));

        return Device.builder()
                .deviceId(device.getDeviceId())
                .name(deviceDto.getName())
                .address(deviceDto.getAddress())
                .url(deviceDto.getUrl())
                .region(deviceDto.getRegion())
                .lon(deviceDto.getLon())
                .lat(deviceDto.getLat())
                .build();
    }
}
