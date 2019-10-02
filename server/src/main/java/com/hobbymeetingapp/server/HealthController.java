package com.hobbymeetingapp.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @RequestMapping(value="/health")
    public ResponseEntity<Health> getHealth()
    {
        return new ResponseEntity<>(new Health("OK"), HttpStatus.OK);
    }
}
