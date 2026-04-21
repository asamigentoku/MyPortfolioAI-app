package com.example.backend.controller;

import com.example.backend.dto.ProjectDto;
import com.example.backend.mapper.ProjectMapper;
import com.example.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable Integer id) {
        return projectService.findById(id)
                .map(projectMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<ProjectDto> findByUserId(@PathVariable Integer userId) {
        return projectService.findByUserId(userId)
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @PostMapping
    public ProjectDto save(@RequestBody ProjectDto projectDto) {
        var project = projectMapper.toEntity(projectDto);
        return projectMapper.toDto(projectService.save(project));
    }

    @PutMapping("/{id}")
    public ProjectDto update(@PathVariable Integer id, @RequestBody ProjectDto projectDto) {
        var project = projectMapper.toEntity(projectDto);
        return projectMapper.toDto(projectService.update(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
