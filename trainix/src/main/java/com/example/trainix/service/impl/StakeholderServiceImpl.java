package com.example.trainix.service.impl;

import com.example.trainix.entity.Stakeholder;
import com.example.trainix.repository.StakeholderRepository;
import com.example.trainix.service.StakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StakeholderServiceImpl implements StakeholderService {

    @Autowired
    private StakeholderRepository stakeholderRepository;


    @Override
    public List<Stakeholder> getAll() {
        return stakeholderRepository.findAll().stream().toList();
    }
}
