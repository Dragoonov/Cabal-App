package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.database.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Optional<Member> findByEmail(String email);
}
