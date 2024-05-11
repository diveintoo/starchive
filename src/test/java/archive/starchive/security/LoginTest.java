package archive.starchive.security;

import archive.starchive.domain.Member;
import archive.starchive.domain.Role;
import archive.starchive.repository.MemberRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class LoginTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    ObjectMapper objectMapper = new ObjectMapper();

    private static String LOGIN_URL = "/login";
    public static String JSON_USERNAME_KEY = "username";
    public static String JSON_PASSWORD_KEY = "password";
    private static String USERNAME = "로그인 테스트용 아이디";
    private static String PASSWORD = "로그인 테스트용 비밀번호";

    @BeforeEach
    private void setUp() {
        memberRepository.save(Member.builder()
                .username(USERNAME)
                .password(delegatingPasswordEncoder.encode(PASSWORD))
                .nickName("nick")
                .role(Role.USER)
                .build());

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("로그인 테스트 : 성공")
    public void testLoginSuccess() throws Exception {
        String content = getContent(USERNAME, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(content().string("success"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 테스트 : 실패 - 아이디 불일치")
    public void testLoginWithIncorrectUsername() throws Exception {
        String content = getContent("not"+USERNAME, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 테스트 : 실패 - 비밀번호 불일치")
    public void testLoginWithIncorrectPassword() throws Exception {
        String content = getContent(USERNAME, "not"+PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 테스트 : 실패 - 주소가 ‘/login’이 아님")
    public void testLoginWithOtherURL() throws Exception {
        String content = getContent(USERNAME, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URL+"nooo")
                .contentType(APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("로그인 테스트 : 실패 - ContentType이 JSON이 아님")
    public void testLoginWithOtherContentType() throws Exception {
        String content = getContent(USERNAME, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOGIN_URL)
                .contentType(APPLICATION_FORM_URLENCODED_VALUE)
                .content(content))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 테스트 : 실패 - Http Method가 Post가 아님")
    public void testLoginWithOtherMethod() throws Exception {
        String content = getContent(USERNAME, PASSWORD);

        mockMvc.perform(MockMvcRequestBuilders
                .put(LOGIN_URL)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    private String getContent(String username, String password) throws JsonProcessingException {
        Map<String, String> usernamePasswordMap = new HashMap<>();
        usernamePasswordMap.put(JSON_USERNAME_KEY, username);
        usernamePasswordMap.put(JSON_PASSWORD_KEY, password);
        return objectMapper.writeValueAsString(usernamePasswordMap);
    }
}
