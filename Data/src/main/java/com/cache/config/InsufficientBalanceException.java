package com.cache.config;

public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(){}

    public InsufficientBalanceException(String message){
       super(message);
    }

}
