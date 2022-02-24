package com.condor.cognito.tutorial;

import com.condor.cognito.tutorial.configuration.ApplicationConfig;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootApplication
public class TutorialApplication {

	@Autowired
	ApplicationConfig applicationConfig;

	public static void main(String[] args) {
		SpringApplication.run(TutorialApplication.class, args);
	}

	@Bean
	public ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
		ResourceRetriever resourceRetriever = new DefaultResourceRetriever(this.applicationConfig.getConnectionTimeout(), this.applicationConfig.getReadTimeout());
		URL jwkURL = new URL(this.applicationConfig.getJwkUrl());
		JWKSource jwkSource = new RemoteJWKSet(jwkURL, resourceRetriever);
		ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
		JWSKeySelector keySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource);
		jwtProcessor.setJWSKeySelector(keySelector);
		return jwtProcessor;
	}
}
