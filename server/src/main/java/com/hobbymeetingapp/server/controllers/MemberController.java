package com.hobbymeetingapp.server.controllers;

import com.hobbymeetingapp.server.models.GenericResponse;
import com.hobbymeetingapp.server.models.Member;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MemberController {
    @Autowired
    MemberRepository members;

    @PostMapping(value = "/member/register")
    public ResponseEntity<GenericResponse> registerUser(@Valid @RequestBody Member member) {
        GenericResponse r = new GenericResponse();

        if (members.findByNickname(member.getNickname()).size() != 0)
        {
            r.getErrors().add("User already exists");
        }
        else
        {
            members.save(member);
            r.getMessages().add("Member registered successfully");
        }

        return new ResponseEntity<>(r, HttpStatus.OK);
    }
}
