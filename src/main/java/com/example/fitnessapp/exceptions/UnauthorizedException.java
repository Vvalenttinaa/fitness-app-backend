package com.example.fitnessapp.exceptions;


public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException()
    {
        super("Unauthorized");
    }

    public UnauthorizedException(String message)
    {
        super(message);
    }

}
