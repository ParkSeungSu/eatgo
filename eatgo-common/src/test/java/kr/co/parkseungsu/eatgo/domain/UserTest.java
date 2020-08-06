package kr.co.parkseungsu.eatgo.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void creation(){
        User user=User.builder()
                .email("cldkr0401@naver.com")
                .name("Tester")
                .level(100L)
                .build();

        assertThat(user.getName(),is("Tester"));
        assertThat(user.isAdmin(),is(true));
        assertThat(user.isActive(),is(true));

        user.deactivate();

        assertThat(user.isActive(),is(false));
    }

}