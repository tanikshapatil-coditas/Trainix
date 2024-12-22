package com.example.trainix.controller;

import com.example.trainix.dto.ApiResponse;
import com.example.trainix.dto.EvaluationDto;
import com.example.trainix.dto.UserApiResponse;
import com.example.trainix.service.impl.EvaluationServiceImpl;
import com.example.trainix.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationServiceImpl evaluationService;


    @PostMapping("/create")
    public ResponseEntity<UserApiResponse> createEvaluation(@RequestBody EvaluationDto evaluationDto) {
        evaluationService.createEvaluation(evaluationDto);
        return ResponseUtil.okay("Evaluation created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<ApiResponse<List<EvaluationDto>>> getEvaluationsByUserId(@PathVariable Long userId) {
        List<EvaluationDto> evaluations = evaluationService.getEvaluationsByUserId(userId);
        return ResponseUtil.success(evaluations,"Evaluations retrieved successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserApiResponse> updateEvaluation(
            @PathVariable Long id, @RequestBody EvaluationDto evaluationDto) {
        evaluationService.updateEvaluation(id, evaluationDto);
        return ResponseUtil.okay("Evaluation updated successfully!", HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserApiResponse> deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        return ResponseUtil.delete("Evaluation deleted successfully!", HttpStatus.NO_CONTENT);
    }
}
