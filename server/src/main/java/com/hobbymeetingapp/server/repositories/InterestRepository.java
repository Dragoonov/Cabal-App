package com.hobbymeetingapp.server.repositories;

import com.hobbymeetingapp.server.models.database.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
    List<Interest> findByParent(Interest parent);
}
