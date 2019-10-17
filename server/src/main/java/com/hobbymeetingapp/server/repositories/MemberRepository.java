package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    List<Member> findByEmail(String email);
}
