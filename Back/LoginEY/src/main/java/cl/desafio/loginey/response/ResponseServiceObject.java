package cl.desafio.loginey.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseServiceObject {
  private Object data;
  private ResponseServiceMessage message;
}
