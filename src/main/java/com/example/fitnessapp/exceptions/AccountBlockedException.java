package com.example.fitnessapp.exceptions;

public class AccountBlockedException extends RuntimeException{
    public AccountBlockedException(){
        super("Account blocked");
    }

    public AccountBlockedException(String message){
        super(message);
    }
}
