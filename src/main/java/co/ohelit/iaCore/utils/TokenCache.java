package co.ohelit.iaCore.utils;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public class TokenCache {
    private final String token;
    private final Instant expiration;

    public TokenCache() {
        this.token = "";
        this.expiration = Instant.now();
    }


    public TokenCache(String token, Instant expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpiration() {
        return expiration;
    }
}
