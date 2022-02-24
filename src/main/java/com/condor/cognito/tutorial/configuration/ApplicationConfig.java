package com.condor.cognito.tutorial.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationConfig {

    @Value("${com.condor.cognito.jwt.aws.sign-in-url}")
    private String signInUrl;

    @Value("${com.condor.cognito.jwt.aws.identityPoolUrl}")
    private String identityPoolUrl;

    @Value("${com.condor.cognito.jwt.token.grant-type}")
    private String grantType;

    @Value("${com.condor.cognito.jwt.token.url}")
    private String tokenUrl;

    @Value("${com.condor.cognito.jwt.token.client_id}")
    private String clientId;

    @Value("${com.condor.cognito.jwt.token.client_secret_key}")
    private String clientKey;

    @Value("${com.condor.cognito.jwt.token.redirect_uri}")
    private String redirectUri;

    @Value("${com.condor.cognito.jwt.aws.connectionTimeout}")
    private int connectionTimeout;

    @Value("${com.condor.cognito.jwt.aws.readTimeout}")
    private int readTimeout;

    @Value("${com.condor.cognito.jwt.aws.jwkUrl}")
    private String jwkUrl;
}
