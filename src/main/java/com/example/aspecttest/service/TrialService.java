package com.example.aspecttest.service;

import org.springframework.stereotype.Service;

@Service
public class TrialService {

    public void doPayment(){
        System.out.println("executing doPayment method");
    }
}
