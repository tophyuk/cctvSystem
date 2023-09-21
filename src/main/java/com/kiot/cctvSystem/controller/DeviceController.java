package com.kiot.cctvSystem.controller;

import com.kiot.cctvSystem.domain.Device;
import com.kiot.cctvSystem.repository.DeviceRepository;
import com.kiot.cctvSystem.service.DeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/device")
public class DeviceController {

}
