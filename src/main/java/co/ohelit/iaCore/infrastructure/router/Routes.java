package co.ohelit.iaCore.infrastructure.router;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.ohelit.iaCore.infrastructure.adapters.MessageIncomingAdapter;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class Routes {

    private final MessageIncomingAdapter messageIncomingAdapter;

    public Routes(MessageIncomingAdapter messageIncomingAdapter) {
        this.messageIncomingAdapter = messageIncomingAdapter;
    }

    @PostMapping
    public ResponseEntity<?> handleIncoming(@RequestBody Map<String, Object> body) {
        return messageIncomingAdapter.handleIncoming(body);
    }

    @GetMapping
    public ResponseEntity<?> verifyWebhook(HttpServletRequest request) {
        return messageIncomingAdapter.verifyWebhook(request);
    }
}