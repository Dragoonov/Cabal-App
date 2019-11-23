package com.hobbymeetingapp.server.controllers;

import com.hobbymeetingapp.server.exceptions.BadRequestException;
import com.hobbymeetingapp.server.exceptions.NotFoundException;
import com.hobbymeetingapp.server.models.database.Event;
import com.hobbymeetingapp.server.models.database.Interest;
import com.hobbymeetingapp.server.models.database.Member;
import com.hobbymeetingapp.server.models.database.MemberEvent;
import com.hobbymeetingapp.server.models.request.EventCreationRequest;
import com.hobbymeetingapp.server.models.request.JoinEventRequest;
import com.hobbymeetingapp.server.repositories.EventRepository;
import com.hobbymeetingapp.server.repositories.InterestRepository;
import com.hobbymeetingapp.server.repositories.MemberEventRepository;
import com.hobbymeetingapp.server.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/events")
public class EventController {
    @Autowired
    private EventRepository events;
    @Autowired
    private InterestRepository interests;
    @Autowired
    private MemberRepository members;
    @Autowired
    private MemberEventRepository memberEvents;

    @GetMapping()
    public ResponseEntity<List<Event>> get() {
        List<Event> allEvents = events.findAll();
        return new ResponseEntity<>(allEvents, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Event> create(Principal principal, @Valid @RequestBody EventCreationRequest request) {
        Member member = members.findByEmail(principal.getName()).get();
        Optional<Interest> interest = interests.findById(request.getInterestId());
        if (!interest.isPresent())
            throw new NotFoundException("Interest ID not found");

        Event event = new Event();
        event.setName(request.getName());
        event.setCreator(member);
        event.setInterest(interest.get());
        event.setDate(new Date()); // TODO: use real date
        event.setLocationId("");
        event.setFinished(false);
        event.setDeleted(false);
        events.save(event);

        MemberEvent memberEvent = new MemberEvent();
        memberEvent.setMember(member);
        memberEvent.setEvent(event);
        memberEvent.setIsAccepted(true);
        memberEvents.save(memberEvent);

        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{eventId}/join")
    public ResponseEntity<MemberEvent> join(Principal principal, @PathVariable("eventId") int eventId,
                                            @Valid @RequestBody JoinEventRequest request) {
        Member member = members.findByEmail(principal.getName()).get();
        Optional<Event> event = events.findById(eventId);
        if (!event.isPresent())
            throw new NotFoundException("Event ID not found");

        List<MemberEvent> allEvents = memberEvents.findAll();
        for (int i = 0; i < allEvents.size(); i++) {
            if (allEvents.get(i).getEvent() == event.get())
                if (allEvents.get(i).getMember() == member)
                    throw new BadRequestException("User already joined event #" + eventId);
        }

        MemberEvent memberEvent = new MemberEvent();
        memberEvent.setMember(member);
        memberEvent.setEvent(event.get());
        memberEvent.setIsAccepted(request.getIsAccepted());
        memberEvents.save(memberEvent);
        return new ResponseEntity<>(memberEvent, HttpStatus.CREATED);
    }

    @GetMapping(value = "/unread")
    public ResponseEntity<List<Event>> getByInterest(Principal principal) {
        Member member = members.findByEmail(principal.getName()).get();
        List<Event> allEvents = events.findAll();
        List<MemberEvent> memberEvent = memberEvents.findByMember(member);
        if (allEvents == null || memberEvent == null) {
            return new ResponseEntity<>(allEvents, HttpStatus.OK);
        }

        List<Event> listOfAllMemberEvents = new ArrayList<>();
        for (int i = 0; i < memberEvent.size(); i++) {
            listOfAllMemberEvents.add(memberEvent.get(i).getEvent());
        }

        if (!listOfAllMemberEvents.isEmpty())
            allEvents.removeAll(listOfAllMemberEvents);

        listOfAllMemberEvents = new ArrayList<>();
        List<Interest> memberInterests = member.interests;
        for (int i = 0; i < allEvents.size(); i++) {
            if (memberInterests.contains(allEvents.get(i).getInterest())) {
                listOfAllMemberEvents.add(allEvents.get(i));
            }
        }

        return new ResponseEntity<>(listOfAllMemberEvents, HttpStatus.OK);
    }
}