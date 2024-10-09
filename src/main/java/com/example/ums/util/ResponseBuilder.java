package com.example.ums.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder {
    public <T>ResponseEntity<ResponseStructure<T>> success(HttpStatus status,String message,T data){
        return ResponseEntity.status(status).body(ResponseStructure.success(status.value(),message,data));
    }
}
