package com.example.backend.repository;

import com.example.backend.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Integer> {
    List<License> findByUserId(Integer userId);

}