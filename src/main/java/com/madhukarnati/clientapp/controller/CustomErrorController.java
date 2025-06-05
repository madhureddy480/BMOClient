package com.madhukarnati.clientapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Void> handleError(HttpServletRequest request) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int status = statusObj != null ? Integer.parseInt(statusObj.toString()) : 500;
        return ResponseEntity.status(status).build(); // No body
    }
}