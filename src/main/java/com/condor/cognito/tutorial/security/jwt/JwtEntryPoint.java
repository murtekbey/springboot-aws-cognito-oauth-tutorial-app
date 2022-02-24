package com.condor.cognito.tutorial.security.jwt;

import com.condor.cognito.tutorial.configuration.ApplicationConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    ApplicationConfig applicationConfig;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("Unauthorized Request");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
    }

    public JwtToken getAuthToken(String code) throws IOException, InterruptedException {
        var keys = this.applicationConfig.getClientId() + ":" + this.applicationConfig.getClientKey();
        var URL = this.applicationConfig.getTokenUrl();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", this.applicationConfig.getGrantType());
        parameters.put("client_id", this.applicationConfig.getClientId());
        parameters.put("code", code);
        parameters.put("redirect_uri", this.applicationConfig.getRedirectUri());
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic "+encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JwtToken jwtToken = gson.fromJson(response.body().toString(), JwtToken.class);
        return jwtToken;
    }
}
