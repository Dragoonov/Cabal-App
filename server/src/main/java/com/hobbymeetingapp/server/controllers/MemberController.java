package com.hobbymeetingapp.server.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hobbymeetingapp.server.models.GenericResponse;
import com.hobbymeetingapp.server.models.Member;
import com.hobbymeetingapp.server.models.MemberToken;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    @Autowired
    MemberRepository members;

    @PostMapping(value = "/login")
    public ResponseEntity<GenericResponse> loginUser(@Valid @RequestBody MemberToken token) {
        GenericResponse r = new GenericResponse();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory()).build();
        String mail;

        try {
            GoogleIdToken idToken = verifier.verify(token.getToken());
            if (idToken == null)
                throw new Exception("Token is invalid");

            mail = idToken.getPayload().getEmail();
        } catch (Exception e) {
            r.getErrors().add(e.getMessage());
            return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
        }

        if (members.findByEmail(mail).size() != 0) {
            r.getMessages().add("User logged in");
            return new ResponseEntity<>(r, HttpStatus.OK);
        }

        // TODO: fill other details
        Member m = new Member();
        m.setName("");
        m.setEmail(mail);
        m.setSearchRadius("");
        m.setDeleted(false);
        members.save(m);

        r.getMessages().add("User registered");
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }
}
