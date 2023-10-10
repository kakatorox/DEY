package cl.desafio.loginey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="telefono")
public class Telefono {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String citycode;
  private String contrycode;
  private String number;

  @ManyToOne
  @JoinColumn(name = "usuario_uuid")
  @JsonIgnore
  @ToString.Exclude
  private Usuario usuario;
}
