package com.hobbymeetingapp.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping(value = "/health")
    public ResponseEntity<Map<String, String>> getHealth() {
        return new ResponseEntity<>(Collections.singletonMap("health", "OK"), HttpStatus.OK);
    }
}
