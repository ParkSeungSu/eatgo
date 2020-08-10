package kr.co.parkseungsu.eatgo.interfaces;

import kr.co.parkseungsu.eatgo.application.EmailNotExistedException;
import kr.co.parkseungsu.eatgo.application.PasswordWrongException;
import kr.co.parkseungsu.eatgo.application.UserService;
import kr.co.parkseungsu.eatgo.domain.User;
import kr.co.parkseungsu.eatgo.utiles.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private UserService userService;

    @Test
    public void createWithValidAttributes() throws Exception {
        String email="cldkr0401@naver.com";
        String password="test";
        String name="John";
        Long id=1004L;
        User mockUser= User.builder().email(email).id(id).name(name).build();

        given(userService.authenticate(email,password)).willReturn(mockUser);

        given(jwtUtil.createToken(id,name)).willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"cldkr0401@naver.com\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(
                        containsString("{\"accessToken\":\"header.payload.signature\"")
                ));

        verify(userService).authenticate(eq(email),eq(password));
    }

    @Test
    public void createWithNotExistedEmail() throws Exception {
        given(userService.authenticate("x@naver.con","test"))
                .willThrow(EmailNotExistedException.class);


        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"x@naver.com\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x@naver.com"),eq("test"));
    }
    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("cldkr0401@naver.con","x"))
                .willThrow(PasswordWrongException.class);


        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"cldkr0401@naver.com\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("cldkr0401@naver.com"),eq("x"));
    }

}