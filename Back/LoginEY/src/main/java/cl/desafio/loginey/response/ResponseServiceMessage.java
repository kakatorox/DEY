package cl.desafio.loginey.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component("responseServiceMessage")
public class ResponseServiceMessage {
  private Date timestamp;
  private String code;
  private ResponseServiceMessageType type;
  private String mensaje;
}
