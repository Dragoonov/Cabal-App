package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.database.Event;
import com.hobbymeetingapp.server.models.database.Interest;
import com.hobbymeetingapp.server.models.database.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByInterest(Interest interest);
    List<Event> findByCreator(Member creator);
}