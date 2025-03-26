package co.ohelit.iaCore.application.services;
import co.ohelit.iaCore.application.repositories.RecipesRepository;
import co.ohelit.iaCore.domain.ports.out.MessageSenderOut;
import co.ohelit.iaCore.domain.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<String, String> lastMessage = new ConcurrentHashMap<>();

    public void handleIncomingMessage(Message message) {
        if (message != null && "text".equals(message.getType())) {
            //String response = "Echo: " + message.getText().getBody();
            //messageSenderOut.sendMessage(message.getFrom(), response, message.getId());
            messageSenderOut.markAsRead(message.getId());

            lastMessage.put(message.getFrom(), message.getId());

            // Buscar y completar la respuesta pendiente
            CompletableFuture<String> futureResponse = promesasService.obtenerYEliminarRespuestaPendiente(message.getFrom());
            if (futureResponse != null) {
                System.out.println("Completando promesa");
                futureResponse.complete(message.getText().getBody());
            } else if ("hola".equalsIgnoreCase(message.getText().getBody())){
                CompletableFuture<String> promesa = this.whatsAppService.sendMessage(message.getFrom(), this.recipesRepository.generateMenu(), message.getId(), true);
                promesa.join();
                try{
                    Long idReceta = Long.valueOf(promesa.join());
                    this.recipesRepository.iniciarReceta(this.recipesRepository.searchRecipe(idReceta), message.getFrom());
                } catch (NumberFormatException e) {
                    String lastId = lastMessage.getOrDefault(message.getFrom(), message.getId());
                    this.whatsAppService.sendMessage(message.getFrom(), "Se espera un número por respuesta\nVuelva a empezar con un 'hola'", lastId, false);

                }


                System.out.printf(promesa.join());
            }else {
                System.out.println("no está en medio de una receta");
            }
        }
    }

}
