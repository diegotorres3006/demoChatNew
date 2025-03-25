package co.ohelit.iaCore.application.services;
import co.ohelit.iaCore.application.repositories.RecipesRepository;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import co.ohelit.iaCore.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageHandler {

    private final MessageSenderOut messageSenderOut;
    private final RecipesRepository recipesRepository;
    private final WhatsAppService whatsAppService;
    @Autowired
    private PromesasService promesasService;

    @Autowired
    public MessageHandler( MessageSenderOut messageSenderOut, RecipesRepository recipesRepository, WhatsAppService whatsAppService) {
        this.messageSenderOut = messageSenderOut;
        this.recipesRepository = recipesRepository;
        this.whatsAppService = whatsAppService;
    }

    public void handleIncomingMessage(Message message) {
        if (message != null && "text".equals(message.getType())) {
            String response = "Echo: " + message.getText().getBody();
            messageSenderOut.sendMessage(message.getFrom(), response, message.getId());
            messageSenderOut.markAsRead(message.getId());

            // Buscar y completar la respuesta pendiente
            CompletableFuture<String> futureResponse = promesasService.obtenerYEliminarRespuestaPendiente(message.getFrom());
            if (futureResponse != null) {
                System.out.println("Completando promesa");
                futureResponse.complete(message.getText().getBody());
                //this.recipesRepository.iniciarReceta(this.recipesRepository.searchRecipe(1L));
            } else if ("hola".equalsIgnoreCase(message.getText().getBody())){
                this.whatsAppService.sendMessage(message.getFrom(), this.recipesRepository.generateMenu(), message.getId(), true);
            }else {
                this.whatsAppService.sendMessage(message.getFrom(), "No estoy en modo receta", message.getId(), false);
            }
        }
    }

}
