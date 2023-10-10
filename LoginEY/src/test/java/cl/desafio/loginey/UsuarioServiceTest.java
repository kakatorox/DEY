package cl.desafio.loginey;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cl.desafio.loginey.entity.Role;
import cl.desafio.loginey.entity.Telefono;
import cl.desafio.loginey.entity.Usuario;
import cl.desafio.loginey.repository.UsuarioRepository;
import cl.desafio.loginey.request.UsuarioRequest;
import cl.desafio.loginey.response.ResponseServiceObject;
import cl.desafio.loginey.service.UsuarioService;
import cl.desafio.loginey.vo.TelefonoVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void shouldCreateUsuarioWhenDataIsValid() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("Securepassword123");
        usuario.setName("Test nombre");
        List<Telefono> telefonos = new ArrayList<>();
        Telefono telefono1 = new Telefono();
        Telefono telefono2 = new Telefono();
        telefono1.setNumber("1112233");
        telefono1.setContrycode("199");
        telefono1.setCitycode("123");
        telefono2.setNumber("1112233");
        telefono2.setContrycode("199");
        telefono2.setCitycode("123");
        telefonos.add(telefono1);
        telefonos.add(telefono2);
        usuario.setTelefonos(telefonos);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioRequest usuario2 = new UsuarioRequest();
        usuario2.setEmail("test2@example.com");
        usuario2.setPassword("Securepassword123");
        usuario2.setName("Test nombre2");
        List<TelefonoVO> telefonos2 = new ArrayList<>();
        TelefonoVO telefono3 = new TelefonoVO();
        TelefonoVO telefono4 = new TelefonoVO();
        telefono3.setNumber("1112233");
        telefono3.setContrycode("199");
        telefono3.setCitycode("123");
        telefono4.setNumber("1112233");
        telefono4.setContrycode("199");
        telefono4.setCitycode("123");
        telefonos2.add(telefono3);
        telefonos2.add(telefono4);
        usuario2.setPhones(telefonos2);
        // Act
        ResponseServiceObject createdUsuario = usuarioService.registrarUsuario(usuario2);

        // Assert
        assertNotNull(createdUsuario);
        assertEquals("200", createdUsuario.getMensaje().getCode());
    }
}

