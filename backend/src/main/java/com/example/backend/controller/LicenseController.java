package com.example.backend.controller;

import com.example.backend.dto.LicenseDto;
import com.example.backend.mapper.LicenseMapper;
import com.example.backend.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;
    private final LicenseMapper licenseMapper;

    @GetMapping("/{id}")
    public ResponseEntity<LicenseDto> findById(@PathVariable Integer id) {
        return licenseService.findById(id)
                .map(licenseMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<LicenseDto> findByUserId(@PathVariable Integer userId) {
        return licenseService.findByUserId(userId)
                .stream()
                .map(licenseMapper::toDto)
                .toList();
    }

    @PostMapping
    public LicenseDto save(@RequestBody LicenseDto licenseDto) {
        var license = licenseMapper.toEntity(licenseDto);
        return licenseMapper.toDto(licenseService.save(license));
    }

    @PutMapping("/{id}")
    public LicenseDto update(@PathVariable Integer id, @RequestBody LicenseDto licenseDto) {
        var license = licenseMapper.toEntity(licenseDto);
        return licenseMapper.toDto(licenseService.update(id, license));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        licenseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
