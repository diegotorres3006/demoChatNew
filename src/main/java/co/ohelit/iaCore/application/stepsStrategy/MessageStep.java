package co.ohelit.iaCore.application.stepsStrategy;

import co.ohelit.iaCore.application.services.MessengerService;
import co.ohelit.iaCore.application.services.WhatsAppService;
import co.ohelit.iaCore.domain.ports.in.MessageSenderIn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageStep implements Steps {

    private final WhatsAppService whatsAppService;
    private final MessengerService messengerService;

    public MessageStep( WhatsAppService whatsAppService, MessengerService messengerService){
        this.whatsAppService = whatsAppService;
        this.messengerService = messengerService;
    }

    @Override
    public void ejecutar(Map<String, Object> step) {
        System.out.println("DESDE WHATSPAPP STEP: Ejecuto  await MessageHandler.useSendMessage(messageOrigin, paso.steps);");

        //String yaml = baseRecipe.getConfiguration();
        //System.out.println("yaml base de getConfigutaion " + yaml);

        Map<String, Object> paso = (Map<String, Object>) step.get("steps");
        System.out.println(paso);

        Map<String, Object> parameters = (Map<String, Object>) paso.get("parameters");
        System.out.println("Debo ir al api wstp y enviar: "+parameters.get("message"));
        //entrarPrimerElemento(yaml);

        if(paso.get("variableNumber")!=null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

        MessageSenderIn messageSenderIn;


        messageSenderIn = this.messengerService;


        messageSenderIn.sendMessage("573228656468", (String) parameters.get("message"), "123");


    }

}
