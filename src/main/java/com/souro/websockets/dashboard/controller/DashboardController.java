package com.souro.websockets.dashboard.controller;

import com.souro.websockets.dashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @MessageMapping("/dashboard")
    @RequestMapping("/initializeData")
    public ResponseEntity dashboard() throws InterruptedException {
        dashboardService.getUserDetails();
        return new ResponseEntity(HttpStatus.OK);
    }


}
