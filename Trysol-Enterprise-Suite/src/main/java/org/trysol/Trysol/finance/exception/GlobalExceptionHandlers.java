package org.trysol.Trysol.finance.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.trysol.Trysol.finance.dto.ErrorResponse;


@ControllerAdvice
public class GlobalExceptionHandlers {
    @ExceptionHandler(ExcelOperationException.class)
    public ResponseEntity<ErrorResponse> handleExcelException(
            ExcelOperationException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
