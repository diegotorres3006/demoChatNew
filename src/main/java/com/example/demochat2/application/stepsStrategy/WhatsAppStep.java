package com.example.demochat2.application.stepsStrategy;

import org.springframework.stereotype.Component;

@Component
public class WhatsAppStep implements Steps {
    @Override
    public void ejecutar(){
        System.out.println("Ejecuto  await MessageHandler.useSendMessage(messageOrigin, paso.steps);");
    }
}
