package com.luv2tech.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorCollectorImpl implements ErrorCollector {

    @Override
    public Map<String, String> getErrorResponses(BindingResult result) {

        return result
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,
                        FieldError::getDefaultMessage, (a, b) -> b));
    }
}
