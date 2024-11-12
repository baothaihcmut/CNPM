package com.example.printer_api.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.printer_api.entities.User;
import com.example.printer_api.modules.authentication.dtos.request.AuthenticationRequest;
import com.example.printer_api.modules.authentication.dtos.request.ExchangeTokenRequest;
import com.example.printer_api.modules.authentication.dtos.response.AuthenticationResponse;
import com.example.printer_api.modules.authentication.dtos.response.ExchangeTokenResponse;
import com.example.printer_api.repositories.OutboundIdentityClient;
import com.example.printer_api.repositories.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jose.Payload;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.experimental.NonFinal;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OutboundIdentityClient outboundIdentityClient;

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;
    
    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;
    
    @NonFinal
    @Value("${outbound.identity.grant-type}")
    protected String GRANT_TYPE;



    public AuthenticationResponse outboundAuthentication(String googleCode){
        ExchangeTokenResponse response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                                .code(googleCode)
                                .clientId(CLIENT_ID)
                                .clientSecret(CLIENT_SECRET)
                                .redirectUri(REDIRECT_URI)
                                .grantType(GRANT_TYPE)
                                .build()
        );
        return AuthenticationResponse.builder()
                    .token(response.getAccessToken())
                    .build();
    }



    // public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
    //     var user = userRepository.findByUsername(authenticationRequest.getUsername());
    //             // .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

    //     PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    //     boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

    //     // if(!authenticated){
    //     //     throw new AppException(ErrorCode.UNAUTHENTICATED);
    //     // }

    //     var token = generateToken(user);

    //     return AuthenticationResponse.builder()
    //             .token(token)
    //             .build();
    // }

    public String generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("dcberr.com")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    // .claim("scope", buildScope(user))
                    .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
