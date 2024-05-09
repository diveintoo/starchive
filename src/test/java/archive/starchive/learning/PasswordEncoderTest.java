package archive.starchive.learning;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {
    private PasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    void testPasswordEncoder() {
        String password = "패쓰워드";

        String result = encoder.encode(password);

        assertTrue(encoder.matches(password, result));
    }

    @Test
    void testPasswordEncoderRandomResult() {
        String password = "패쓰워드";

        String result1 = encoder.encode(password);
        String result2 = encoder.encode(password);

        assertNotEquals(result1, result2);
    }
}
