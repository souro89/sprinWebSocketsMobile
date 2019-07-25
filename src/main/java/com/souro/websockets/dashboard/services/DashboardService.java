package com.souro.websockets.dashboard.services;

import com.souro.websockets.dashboard.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    DashboardServiceImpl dashboardService;

    public void getUserDetails(){
        dashboardService.getUserDetails();
    }

}
