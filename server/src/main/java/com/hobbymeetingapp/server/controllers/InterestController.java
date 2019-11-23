package com.hobbymeetingapp.server.controllers;

import com.hobbymeetingapp.server.exceptions.NotFoundException;
import com.hobbymeetingapp.server.models.database.Interest;
import com.hobbymeetingapp.server.models.response.Base64ImageResponse;
import com.hobbymeetingapp.server.repositories.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/interests")
public class InterestController {
    @Autowired
    private InterestRepository interests;

    @GetMapping()
    public ResponseEntity<List<Interest>> get() {
        List<Interest> allInterests = interests.findAll();
        List<Interest> result = allInterests.stream().filter(x -> x.getParent() == null).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{interestId}/children")
    public ResponseEntity<List<Interest>> getChild(@PathVariable("interestId") int interestId) {
        Optional<Interest> parent = interests.findById(interestId);
        if (!parent.isPresent())
            throw new NotFoundException("Interest ID not found");

        List<Interest> children = interests.findByParent(parent.get());
        return new ResponseEntity<>(children, HttpStatus.OK);
    }

    @GetMapping(value = "/{interestId}/picture")
    public ResponseEntity<Base64ImageResponse> getAvatar(@PathVariable("interestId") int interestId) {
        Optional<Interest> interest = interests.findById(interestId);
        if (!interest.isPresent())
            throw new NotFoundException();

        return new ResponseEntity<>(new Base64ImageResponse(interest.get().getPicture()), HttpStatus.OK);
    }
}