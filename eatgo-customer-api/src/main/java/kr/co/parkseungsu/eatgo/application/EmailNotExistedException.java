package kr.co.parkseungsu.eatgo.application;

public class EmailNotExistedException extends RuntimeException {
    EmailNotExistedException(String email){
        super("Email "+email+" not Existed");
    }
}
