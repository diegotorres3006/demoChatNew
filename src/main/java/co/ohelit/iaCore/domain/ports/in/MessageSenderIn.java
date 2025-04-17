package co.ohelit.iaCore.domain.ports.in;

import co.ohelit.iaCore.domain.models.Message;



public interface MessageSenderIn {
    void receiveMessage(Message message);
}
