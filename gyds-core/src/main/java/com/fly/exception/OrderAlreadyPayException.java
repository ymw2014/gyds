package com.fly.exception;

public class OrderAlreadyPayException extends Exception{
    public OrderAlreadyPayException(){
        super("订单已经支付");
    }
}
