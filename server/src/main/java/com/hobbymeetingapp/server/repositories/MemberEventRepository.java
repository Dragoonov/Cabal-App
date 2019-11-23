package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.database.Event;
import com.hobbymeetingapp.server.models.database.Member;
import com.hobbymeetingapp.server.models.database.MemberEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberEventRepository extends JpaRepository<MemberEvent, Integer> {
    List<MemberEvent> findByMember(Member creator);
    List<MemberEvent> findByEvent(Event event);
}