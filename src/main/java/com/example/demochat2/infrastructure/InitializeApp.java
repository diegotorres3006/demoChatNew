package com.example.demochat2.infrastructure;

import com.example.demochat2.infrastructure.config.AppConfig;
import com.example.demochat2.infrastructure.adapters.WhatsAppService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class InitializeApp implements CommandLineRunner {


    private final int serverPort;

    public InitializeApp(AppConfig appConfig, WhatsAppService whatsappService) {
        this.serverPort = appConfig.getServerPort();
    }

    @Override
    public void run(String... args) {
        System.out.println("Server is listening on port: " + serverPort);
    }





}