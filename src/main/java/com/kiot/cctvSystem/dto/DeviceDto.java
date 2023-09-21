package com.kiot.cctvSystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeviceDto {

    private Long id;
    private Long deviceId;
    private String name;
    private String address;
    private Long region;
    private String url;
    private String lon;
    private String lat;

    public DeviceDto(Long id, Long deviceId, String name, String address, Long region, String url, String lon, String lat) {
        this.id = id;
        this.deviceId = deviceId;
        this.name = name;
        this.address = address;
        this.region = region;
        this.url = url;
        this.lon = lon;
        this.lat = lat;
    }
}
