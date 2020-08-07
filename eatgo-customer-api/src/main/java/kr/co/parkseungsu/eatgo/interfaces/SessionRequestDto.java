package kr.co.parkseungsu.eatgo.interfaces;

import lombok.Data;

@Data
public class SessionRequestDto {
    private String email;
    private String password;
}
