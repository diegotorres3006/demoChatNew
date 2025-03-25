package co.ohelit.iaCore.application.services;

import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PromesasService {

    private ConcurrentHashMap<String, CompletableFuture<String>> respuestasPendientes = new ConcurrentHashMap<>();

    public void agregarRespuestaPendiente(String user, CompletableFuture<String> future) {
        respuestasPendientes.put(user, future);
    }

    public CompletableFuture<String> obtenerYEliminarRespuestaPendiente(String user) {
        return respuestasPendientes.remove(user);
    }
}
