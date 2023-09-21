package com.kiot.cctvSystem.dto.alarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmStatDataDto {

    private String deviceName;
    private String targetName;
    private int level;
    private String detectedTime;

    public AlarmStatDataDto(String deviceName, String targetName, int level, String detectedTime) {
        this.deviceName = deviceName;
        this.targetName = targetName;
        this.level = level;
        this.detectedTime = detectedTime;
    }
}
