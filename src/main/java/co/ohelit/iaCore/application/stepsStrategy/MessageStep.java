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
        System.out.println("DESDE MESSAGE STEP: " + step);

        /*Este paso debe extraer información del step para enviar un mensaje por algún medio
        ya sea WhatsApp u otro

        A continuación se explican todos los atributos de un paso:
        name: ENROLLMENT_ID
        type: WHATSAPP_MESSAGE
        saveUserResponse: true
        variableNumber: 1
        expectedDataType: number
        parameters:
            message: "mensaje"
        stepNumber: 1
        nextStep: 2
        */

        Integer variableNumber = (Integer) step.get("variableNumber");
        String dataType = (String) step.get("expectedDataType");
        Integer nextStep = (Integer) step.get("nextStep");

        Map<String, Object> parameters = (Map<String, Object>) step.get("parameters");
        String message = (String) parameters.get("message");


        System.out.println(variableNumber +" "+ dataType +" "+ nextStep +" "+ message);

        if(variableNumber != null){
            System.out.println("Se supone que tengo que guardar la respuesta del usuario");
        } else {
            System.out.println("No debo guardar nadota");
        }

        MessageSenderIn messageSenderIn;

        messageSenderIn = this.whatsAppService;

        System.out.println(messageSenderIn.sendMessage("573228656468", message, "123", true));
        //messageSenderIn.sendMessage("573203298262", message, "321", true);
        //messageSenderIn.sendMessage("573014507055", message, "321", true);

    }

}
