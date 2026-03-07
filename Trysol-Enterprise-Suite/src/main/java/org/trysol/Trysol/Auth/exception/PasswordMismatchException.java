package org.trysol.Trysol.Auth.exception;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message){
        super(message);
    }
}
