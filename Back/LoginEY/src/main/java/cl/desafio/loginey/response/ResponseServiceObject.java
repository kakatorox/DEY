package cl.desafio.loginey.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component("responseServiceObject")
public class ResponseServiceObject {
  private ResponseServiceMessage mensaje;
  private Object data;
}
