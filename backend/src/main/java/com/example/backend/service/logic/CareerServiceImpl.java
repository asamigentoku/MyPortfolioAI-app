package com.example.backend.service.logic;

import com.example.backend.model.Career;
import com.example.backend.repository.CareerRepository;
import com.example.backend.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareerServiceImpl implements CareerService {
    private final CareerRepository careerRepository;

    @Override @Transactional(readOnly = true)
    public List<Career> findAll() { return careerRepository.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<Career> findById(Integer id) { return careerRepository.findById(id); }

    @Override @Transactional(readOnly = true)
    public List<Career> findByUserIdOrderByTimeDesc(Integer userId) {
        return careerRepository.findByUserIdOrderByTimeDesc(userId);
    }

    @Override
    public Career save(Career career) { return careerRepository.save(career); }

    @Override
    public Career update(Integer id, Career career) {
        Career existing = careerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career not found: " + id));
        existing.setCareerName(career.getCareerName());
        existing.setTime(career.getTime());
        return careerRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) { careerRepository.deleteById(id); }
}
