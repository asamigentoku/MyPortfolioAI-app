package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * データが見つからない場合に投げるカスタム例外
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // この例外が投げられたら自動的に404を返す
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}