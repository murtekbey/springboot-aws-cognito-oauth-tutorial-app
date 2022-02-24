package com.condor.cognito.tutorial.controller;

import com.condor.cognito.tutorial.configuration.ApplicationConfig;
import com.condor.cognito.tutorial.dto.MessageDto;
import com.condor.cognito.tutorial.security.jwt.JwtEntryPoint;
import com.condor.cognito.tutorial.security.jwt.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Autowired
    ApplicationConfig applicationConfig;

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ROLE_SUPERUSER')")
    public ResponseEntity<MessageDto> hello() throws IOException, InterruptedException {
        return ResponseEntity.ok(new MessageDto("Hello from cognito"));
    }

    @GetMapping("/login/oauth2/code/cognito")
    public ResponseEntity<JwtToken> getToken(@RequestParam String code) throws IOException, InterruptedException {
        return ResponseEntity.ok(jwtEntryPoint.getAuthToken(code));
    }

    @GetMapping("/sign-in")
    public RedirectView signIn() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(this.applicationConfig.getSignInUrl());
        return redirectView;
    }
}
