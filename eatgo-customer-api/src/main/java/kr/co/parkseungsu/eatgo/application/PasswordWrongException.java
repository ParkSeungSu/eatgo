package kr.co.parkseungsu.eatgo.application;

public class PasswordWrongException extends RuntimeException{
    PasswordWrongException(){
        super("Password Wrong!");
    }
}
