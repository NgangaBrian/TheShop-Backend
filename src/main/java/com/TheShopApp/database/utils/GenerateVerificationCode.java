package com.TheShopApp.database.utils;

public class GenerateVerificationCode {
    public String generateVerificationCode(){
        return String.valueOf((int)(Math.random() * 900000 + 100000));
    }
}
