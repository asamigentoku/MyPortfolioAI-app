package com.example.backend.repository;

import com.example.backend.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CareerRepository extends JpaRepository<Career, Integer> {
    List<Career> findByUserIdOrderByTimeDesc(Integer userId);
}