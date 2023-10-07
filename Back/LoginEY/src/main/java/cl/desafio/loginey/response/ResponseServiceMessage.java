package cl.desafio.loginey.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseServiceMessage {
  private Date timestamp;
  private String code;
  private ResponseServiceMessageType type;
  private String mensaje;
}
