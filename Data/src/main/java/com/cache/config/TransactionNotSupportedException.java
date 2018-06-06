package com.cache.config;

public class TransactionNotSupportedException extends Exception {


    public TransactionNotSupportedException() {
    }

    public TransactionNotSupportedException(String message){
        super(message);
    }

}
