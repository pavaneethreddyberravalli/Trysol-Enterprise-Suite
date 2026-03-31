package org.trysol.Trysol.finance.exception;

public class ExcelOperationException extends RuntimeException{
    public ExcelOperationException(String message) {
        super(message);
    }

    public ExcelOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

