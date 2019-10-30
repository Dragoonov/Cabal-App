package com.hobbymeetingapp.server.controllers;

import com.hobbymeetingapp.server.models.Event;
import com.hobbymeetingapp.server.repositories.EventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/events")
public class EventsController {
    @Autowired
    private EventsRepository events;

    @GetMapping()
    public ResponseEntity<List<Event>> get() {
        List<Event> allEvents = events.findAll();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }
}
