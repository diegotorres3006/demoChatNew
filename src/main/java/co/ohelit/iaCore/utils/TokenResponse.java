package co.ohelit.iaCore.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private long expiresIn;

    public TokenResponse() {}

    public TokenResponse(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TokenResponse parsed = mapper.readValue(json, TokenResponse.class);
            this.accessToken = parsed.accessToken;
            this.expiresIn = parsed.expiresIn;
        } catch (IOException e) {
            throw new RuntimeException("Error parsing token response", e);
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}