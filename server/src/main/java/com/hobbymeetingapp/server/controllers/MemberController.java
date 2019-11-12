package com.hobbymeetingapp.server.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hobbymeetingapp.server.models.*;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import com.hobbymeetingapp.server.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MemberRepository members;

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> loginUser(@Valid @RequestBody TokenRequest request) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).build();
        String mail;

        try {
            GoogleIdToken idToken = verifier.verify(request.getToken());
            if (idToken == null)
                throw new Exception("Token is invalid");

            mail = idToken.getPayload().getEmail();
        } catch (Exception e) {
            GenericResponse response = new GenericResponse();
            response.getErrors().add(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        HttpStatus status = HttpStatus.OK;
        if (!members.findByEmail(mail).isPresent()) {
            // TODO: fill other details
            Member m = new Member();
            m.setName("");
            m.setEmail(mail);
            m.setSearchRadius("");
            m.setDeleted(false);
            members.save(m);
            status = HttpStatus.CREATED;
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, ""));
        String token = tokenService.generate(mail);

        return new ResponseEntity<>(new TokenResponse(token), status);
    }
}
