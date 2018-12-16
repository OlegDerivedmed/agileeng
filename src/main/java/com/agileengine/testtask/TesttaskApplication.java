package com.agileengine.testtask;

import com.agileengine.testtask.app.AppRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesttaskApplication {

    public static void main(String[] args) {
        AppRunner appRunner = new AppRunner(args);
        appRunner.runApp();
    }

}

