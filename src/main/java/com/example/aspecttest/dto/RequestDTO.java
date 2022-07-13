package com.example.aspecttest.dto;

import javax.validation.constraints.Pattern;

public class RequestDTO {

    @Pattern(regexp = "[a-z]{3}", message = "must three words")
    private String value;

    public RequestDTO() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "value='" + value + '\'' +
                '}';
    }
}
