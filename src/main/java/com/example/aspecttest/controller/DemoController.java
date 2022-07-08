package com.example.aspecttest.controller;


import com.example.aspecttest.model.Employee;
import com.example.aspecttest.service.TrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class DemoController {

    private final TrialService trialService;

    @Autowired
    public DemoController(TrialService trialService) {
        this.trialService = trialService;
    }

    @GetMapping
    public ResponseEntity<?> tested(){
//        trialService.doPayment();
        trialService.serve("Based", new Employee("Bob", "test@bob.com"));
        return ResponseEntity.ok(Map.of("test", "working"));
    }
}
