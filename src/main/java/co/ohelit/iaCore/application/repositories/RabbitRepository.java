package co.ohelit.iaCore.application.repositories;

import co.ohelit.iaCore.infrastructure.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitRepository {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleMessage(String message){
        System.out.println("Mensaje recibido del queue: " + message);
    }

}
