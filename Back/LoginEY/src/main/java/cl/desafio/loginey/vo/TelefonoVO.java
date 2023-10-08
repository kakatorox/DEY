package cl.desafio.loginey.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelefonoVO {
  private String number;
  private String citycode;
  private String contrycode;
}
