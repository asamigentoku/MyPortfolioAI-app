package com.example.backend.controller;

import com.example.backend.dto.SkillDto;
import com.example.backend.mapper.SkillMapper;
import com.example.backend.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SkillDto> findById(@PathVariable Integer id) {
        return skillService.findById(id)
                .map(skillMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<SkillDto> findByUserId(@PathVariable Integer userId) {
        return skillService.findByUserId(userId)
                .stream()
                .map(skillMapper::toDto)
                .toList();
    }

    @PostMapping
    public SkillDto save(@RequestBody SkillDto skillDto) {
        var skill = skillMapper.toEntity(skillDto);
        return skillMapper.toDto(skillService.save(skill));
    }

    @PutMapping("/{id}")
    public SkillDto update(@PathVariable Integer id, @RequestBody SkillDto skillDto) {
        var skill = skillMapper.toEntity(skillDto);
        return skillMapper.toDto(skillService.update(id, skill));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        skillService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
