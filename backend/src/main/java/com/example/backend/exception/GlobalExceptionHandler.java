package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//「継承していない独立したコントローラー」に対しても、外側から共通処理を適用できる仕組みになっています。
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404: 見つからない
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 409: 既に存在している
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyExists(AlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 500: その他すべての予期せぬエラー
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
        // 実務ではここでログを出力する (log.error(...) など)
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "予期せぬエラーが発生しました");
    }

    // ヘルパーメソッド：レスポンスの形を揃える
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(status.value(), message);
        return new ResponseEntity<>(error, status);
    }

    // 内部用のデータ構造
    @lombok.Value
    public static class ErrorResponse {
        int status;
        String message;
    }
}