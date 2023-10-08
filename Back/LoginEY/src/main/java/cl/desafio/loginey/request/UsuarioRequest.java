package cl.desafio.loginey.request;

import cl.desafio.loginey.vo.TelefonoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {
  private String name;
  private String email;
  private String password;
  private List<TelefonoVO> phones;
}
