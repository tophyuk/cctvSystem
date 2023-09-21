package com.kiot.cctvSystem.dto.alarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlarmSearchDto {

    private int year;
    private Integer month;
    private Integer day;
    private Integer hour;


    public AlarmSearchDto(int year, Integer month, Integer day, Integer hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

}
