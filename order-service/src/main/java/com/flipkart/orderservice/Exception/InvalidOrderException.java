package com.flipkart.orderservice.Exception;


public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String msg){
        super(msg);

    }
}
