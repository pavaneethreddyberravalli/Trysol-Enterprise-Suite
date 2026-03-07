package org.trysol.Trysol.Auth.exception;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
