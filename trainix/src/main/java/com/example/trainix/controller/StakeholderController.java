package com.example.trainix.controller;

import com.example.trainix.dto.ApiResponse;
import com.example.trainix.entity.Stakeholder;
import com.example.trainix.service.impl.StakeholderServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stakeholder")
public class StakeholderController {

    @Autowired
    private StakeholderServiceImpl stakeholderService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Stakeholder>>> getAllStakeholders() {
        List<Stakeholder> stakeholder = stakeholderService.getAll();
        return ResponseUtil.success(stakeholder, "Stakeholders retrieved successfully!!");
    }
}
