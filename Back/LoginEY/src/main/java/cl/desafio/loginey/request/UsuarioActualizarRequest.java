package cl.desafio.loginey.request;

import cl.desafio.loginey.vo.TelefonoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioActualizarRequest {
  private String uuid;
  private String name;
  private String email;
  private String password;
  private List<TelefonoVO> phones;
}
