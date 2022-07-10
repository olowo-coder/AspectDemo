package com.example.aspecttest.dto;

public class RequestDTO {

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
