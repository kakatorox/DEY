package cl.desafio.loginey.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioVO {
  private String uuid;
  private Date created;
  private Date modified;
  private Date lastLogin;
  private String token;
  private Boolean isActive;
}
