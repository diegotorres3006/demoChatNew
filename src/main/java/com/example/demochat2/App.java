package com.example.demochat2;

import com.example.demochat2.config.AppConfig;
import com.example.demochat2.services.WhatsAppService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class App implements CommandLineRunner {


    private final int serverPort;

    public App(AppConfig appConfig, WhatsAppService whatsappService) {
        this.serverPort = appConfig.getServerPort();
    }

    @Override
    public void run(String... args) {
        System.out.println("Server is listening on port: " + serverPort);
    }





}