package com.philately.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.philately.service.PaperServiceImpl;
import com.philately.service.StampsServiceImpl;
import com.philately.service.UserServiceImpl;

import java.io.IOException;

@Component
public class FirstInit implements CommandLineRunner {

    private final UserServiceImpl userService;
    private final PaperServiceImpl paperService;

    private final StampsServiceImpl stampsService;


    public FirstInit(UserServiceImpl userService, PaperServiceImpl paperService, StampsServiceImpl stampsService) {
        this.userService = userService;
        this.paperService = paperService;
        this.stampsService = stampsService;
    }

    @Override
    public void run(String... args) throws IOException {
        this.userService.initUsers();
        this.paperService.initPapers();
        this.stampsService.initStamps();
//        this.userService.initAdmin();
//        this.userService.initTest();
    }
}
