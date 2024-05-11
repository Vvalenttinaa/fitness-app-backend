package com.example.fitnessapp.exceptions;

public class NotApprovedException extends RuntimeException{
    public NotApprovedException()
    {
        super("Not approved account");
    }

    public NotApprovedException(String message)
    {
        super(message);
    }
}