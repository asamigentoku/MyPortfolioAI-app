package com.example.backend.service.logic;
import com.example.backend.model.License;
import com.example.backend.repository.LicenseRepository;
import com.example.backend.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LicenseServiceImpl implements LicenseService
{
    private final LicenseRepository licenseRepository;

    @Override @Transactional(readOnly = true)
    public List<License> findAll() { return licenseRepository.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<License> findById(Integer id) { return licenseRepository.findById(id); }

    @Override @Transactional(readOnly = true)
    public List<License> findByUserId(Integer userId) { return licenseRepository.findByUserId(userId); }

    @Override
    public License save(License license) { return licenseRepository.save(license); }

    @Override
    public License update(Integer id, License license) {
        License existing = licenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("License not found: " + id));
        existing.setLicenseName(license.getLicenseName());
        return licenseRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) { licenseRepository.deleteById(id); }

}
