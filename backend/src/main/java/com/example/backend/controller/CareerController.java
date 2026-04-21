package com.example.backend.controller;

import com.example.backend.dto.CareerDto;
import com.example.backend.mapper.CareerMapper;
import com.example.backend.service.CareerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careers")
@RequiredArgsConstructor
public class CareerController {

    private final CareerService careerService;
    private final CareerMapper careerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CareerDto> findById(@PathVariable Integer id) {
        return careerService.findById(id)
                .map(careerMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<CareerDto> findByUserId(@PathVariable Integer userId) {
        return careerService.findByUserIdOrderByTimeDesc(userId)
                .stream()
                .map(careerMapper::toDto)
                .toList();
    }

    @PostMapping
    public CareerDto save(@RequestBody CareerDto careerDto) {
        var career = careerMapper.toEntity(careerDto);
        return careerMapper.toDto(careerService.save(career));
    }

    @PutMapping("/{id}")
    public CareerDto update(@PathVariable Integer id, @RequestBody CareerDto careerDto) {
        var career = careerMapper.toEntity(careerDto);
        return careerMapper.toDto(careerService.update(id, career));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        careerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}