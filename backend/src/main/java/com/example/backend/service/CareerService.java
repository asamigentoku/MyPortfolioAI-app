package com.example.backend.service;

import com.example.backend.model.Career;

import java.util.List;
import java.util.Optional;

public interface CareerService {
    List<Career> findAll();
    Optional<Career> findById(Integer id);
    List<Career> findByUserIdOrderByTimeDesc(Integer userId);
    Career save(Career career);
    Career update(Integer id, Career career);
    void deleteById(Integer id);
}
