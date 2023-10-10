package cl.desafio.loginey;

import cl.desafio.loginey.util.ToolsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordValidatorTest {
    @Test
    public void validarPassword() {
        // Arrange
        String validPassword = "VValid@123";

        boolean isValid = ToolsUtil.validarPassword(validPassword);

        assertTrue(isValid);
    }

    @Test
    public void validarEmail() {
        // Arrange
        String validPassword = "juannnn@rodriguez.org";

        boolean isValid = ToolsUtil.validarFormatoCorreo(validPassword);

        assertTrue(isValid);
    }
}
