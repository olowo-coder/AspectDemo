package com.example.aspecttest.service;

import com.example.aspecttest.dto.RequestDTO;
import com.example.aspecttest.interfaces.LogExecutionTime;
import com.example.aspecttest.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrialService {

    public void doPayment(){
        System.out.println("executing doPayment method");
    }

    @LogExecutionTime
    public void serve(String value, Employee emp){
        System.out.println("executing serve method");
    }

    public Employee doTransaction(RequestDTO requestDTO) {
        System.out.println(requestDTO);
        if(requestDTO.getValue().equalsIgnoreCase("error")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"DISALLOWED");
        }
        return new Employee("Bob", requestDTO.getValue());
    }
}
