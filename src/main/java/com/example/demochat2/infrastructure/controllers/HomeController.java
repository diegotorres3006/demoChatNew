package com.example.demochat2.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class HomeController {


    @RequestMapping("/error")
    @ResponseBody
    public ResponseEntity<String> handleError() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La URL que has solicitado no está disponible.");
    }
}