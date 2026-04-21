package com.example.backend.service.logic;

import com.example.backend.model.Project;
import com.example.backend.repository.ProjectRepository;
import com.example.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceimpl implements ProjectService {
    private final ProjectRepository projectRepository;
    @Override
    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findById(Integer id){
        return projectRepository.findById(id);
    }

    @Override
    public List<Project> findByUserId(Integer userId){
        return projectRepository.findByUserId(userId);
    }

    @Override
    public Project save(Project project){
        return projectRepository.save(project);
    }

    @Override
    public Project update(Integer id, Project project){
        Project existing = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found: " + id));
        //ここに変更する項目を追加する
        existing.setTitle(project.getTitle());
        return projectRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id){
        projectRepository.deleteById(id);
    }


}
