package kr.co.parkseungsu.eatgo.interfaces;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponseDto {

    private String accessToken;

}
