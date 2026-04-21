package com.example.backend.service.logic;
import com.example.backend.model.Skill;
import com.example.backend.repository.SkillRepository;
import com.example.backend.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;

    @Override @Transactional(readOnly = true)
    public List<Skill> findAll() { return skillRepository.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<Skill> findById(Integer id) { return skillRepository.findById(id); }

    @Override @Transactional(readOnly = true)
    public List<Skill> findByUserId(Integer userId) { return skillRepository.findByUserId(userId); }

    @Override
    public Skill save(Skill skill) { return skillRepository.save(skill); }

    @Override
    public Skill update(Integer id, Skill skill) {
        Skill existing = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found: " + id));
        existing.setName(skill.getName());
        return skillRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) { skillRepository.deleteById(id); }
}
