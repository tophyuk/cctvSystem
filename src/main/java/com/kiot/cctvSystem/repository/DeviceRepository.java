package com.kiot.cctvSystem.repository;

import com.kiot.cctvSystem.domain.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query(value = "SELECT url from Device d WHERE deviceId=:deviceId")
    String findURLByDeviceId(@Param("deviceId") Long deviceId);

    Optional<Device> findByDeviceId(Long id);
}
