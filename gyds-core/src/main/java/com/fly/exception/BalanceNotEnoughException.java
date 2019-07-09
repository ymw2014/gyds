package com.fly.exception;

public class BalanceNotEnoughException extends RuntimeException{

    public BalanceNotEnoughException(){
        super("余额不足,请充值");
    }
}
