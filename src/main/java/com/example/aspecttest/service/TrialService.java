package com.example.aspecttest.service;

import com.example.aspecttest.interfaces.LogExecutionTime;
import com.example.aspecttest.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class TrialService {

    public void doPayment(){
        System.out.println("executing doPayment method");
    }

    @LogExecutionTime
    public void serve(String value, Employee emp){
        System.out.println("executing serve method");
    }
}
