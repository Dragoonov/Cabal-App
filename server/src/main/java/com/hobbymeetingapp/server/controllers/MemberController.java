package com.hobbymeetingapp.server.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hobbymeetingapp.server.exceptions.BadRequestException;
import com.hobbymeetingapp.server.exceptions.NotFoundException;
import com.hobbymeetingapp.server.models.database.Interest;
import com.hobbymeetingapp.server.models.database.Member;
import com.hobbymeetingapp.server.models.request.TokenRequest;
import com.hobbymeetingapp.server.models.request.UpdateUserRequest;
import com.hobbymeetingapp.server.models.response.Base64ImageResponse;
import com.hobbymeetingapp.server.models.response.BaseResponse;
import com.hobbymeetingapp.server.models.response.GenericResponse;
import com.hobbymeetingapp.server.models.response.TokenResponse;
import com.hobbymeetingapp.server.repositories.InterestRepository;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import com.hobbymeetingapp.server.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/member")
public class MemberController {
    private final int DEFAULT_SEARCH_RADIUS = 10;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MemberRepository members;
    @Autowired
    private InterestRepository interests;

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
            return new ResponseEntity<>(GenericResponse.singletonError(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        HttpStatus status = HttpStatus.OK;
        if (!members.findByEmail(mail).isPresent()) {
            // TODO: fill other details
            Member member = new Member();
            member.setName("");
            member.setEmail(mail);
            member.setSearchRadius(DEFAULT_SEARCH_RADIUS);
            member.setDeleted(false);
            members.save(member);
            status = HttpStatus.CREATED;
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mail, ""));
        String token = tokenService.generate(mail);

        return new ResponseEntity<>(new TokenResponse(token), status);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<GenericResponse> updateUser(Principal principal, @Valid @RequestBody UpdateUserRequest request) {
        Member member = members.findByEmail(principal.getName()).get();
        member.setName(request.getNickname());
        member.setSearchRadius(request.getSearchRadius());
        member.setAvatar(request.getAvatar());

        List<Interest> interests_ = new ArrayList<>();
        for (int interestId : request.getInterestIds()) {
            Optional<Interest> interest = interests.findById(interestId);
            if (!interest.isPresent())
                throw new BadRequestException("Interest ID not found: " + interestId);
            interests_.add(interest.get());
        }

        member.interests.clear();
        member.interests.addAll(interests_.stream().distinct().collect(Collectors.toList()));
        members.save(member);

        return new ResponseEntity<>(GenericResponse.singletonMessage("User updated successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<Member> getMyDetails(Principal principal) {
        Member me = members.findByEmail(principal.getName()).get();
        return new ResponseEntity<>(me, HttpStatus.OK);
    }

    @GetMapping(value = "/{memberId}/avatar")
    public ResponseEntity<Base64ImageResponse> getAvatar(@PathVariable("memberId") int memberId) {
        Optional<Member> member = members.findById(memberId);
        if (!member.isPresent())
            throw new NotFoundException();

        return new ResponseEntity<>(new Base64ImageResponse(member.get().getAvatar()), HttpStatus.OK);
    }
}