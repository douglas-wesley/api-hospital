package com.douglas.APIHosital.controller.exceptions; // Novo pacote

import com.douglas.APIHosital.service.exceptions.BusinessRuleException;
import com.douglas.APIHosital.service.exceptions.EmailCadastradoException;
import com.douglas.APIHosital.service.exceptions.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Captura o erro 409 (Email duplicado)
    @ExceptionHandler(EmailCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleEmailConflict(EmailCadastradoException ex) {
        Map<String, String> body = Map.of("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body); // 409 CONFLICT
    }

    // Captura o erro 404 (Não encontrado)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> body = Map.of("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body); // 404 NOT FOUND
    }

    // Captura todas as outras regras de negócio (400)
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRule(BusinessRuleException ex) {
        Map<String, String> body = Map.of("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // 400 BAD REQUEST
    }

    // Captura os erros de validação dos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors); // 400 BAD REQUEST
    }
}