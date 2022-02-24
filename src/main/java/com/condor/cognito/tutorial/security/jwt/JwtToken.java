package com.condor.cognito.tutorial.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtToken {
    private String access_token;
    private String refresh_token;
    private String expires_in;
    private String token_type;
}
