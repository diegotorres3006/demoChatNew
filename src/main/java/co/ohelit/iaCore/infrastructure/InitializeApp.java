package co.ohelit.iaCore.infrastructure;

import co.ohelit.iaCore.infrastructure.config.AppConfig;
import co.ohelit.iaCore.infrastructure.adapters.WhatsAppAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class InitializeApp implements CommandLineRunner {


    private final int serverPort;

    public InitializeApp(AppConfig appConfig, WhatsAppAdapter whatsAppAdapter) {
        this.serverPort = appConfig.getServerPort();
    }

    @Override
    public void run(String... args) {
        System.out.println("Server is listening on port: " + serverPort);
    }





}