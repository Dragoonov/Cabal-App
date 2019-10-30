package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepository extends JpaRepository<Event, Integer> {
}
