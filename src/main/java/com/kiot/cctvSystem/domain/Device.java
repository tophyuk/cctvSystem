package com.kiot.cctvSystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long deviceId;
    private String name;
    private String address;
    private Long region;
    private String url;
    private String lon;
    private String lat;

    @Builder
    public Device(Long id, Long deviceId, String name, String address, Long region, String url, String lon, String lat) {
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
