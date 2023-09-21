package com.kiot.cctvSystem.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deviceId")
    private Device device;
    @NotNull
    private Long region;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetId")
    private Target target;
    @NotNull
    private int count; // 횟수
    @NotNull
    private LocalDateTime detectedTime; // 측정 시간
    @NotNull
    private int level; // 경보레벨(0~2)

    @Builder
    public Alarm(Device device, Long region, Target target, int count, LocalDateTime detectedTime, int level) {
        this.device = device;
        this.region = region;
        this.target = target;
        this.count = count;
        this.detectedTime = detectedTime;
        this.level = level;
    }
}
