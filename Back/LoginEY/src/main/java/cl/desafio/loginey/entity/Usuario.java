package cl.desafio.loginey.entity;

import cl.desafio.loginey.vo.TelefonoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.accessibility.AccessibleRole;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario implements UserDetails{
  @Id
  @Column(name = "uuid")
  private String uuid;
  @NotEmpty(message = "El Nombre no puede estar vacio")
  @Column(name = "name")
  private String name;
  @NotEmpty(message = "El Correo no puede estar vacio")
  @Email(message = "Formato de Email Incorrecto")
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;

  @CreatedDate
  @Column(name = "created")
  private Date created;

  @LastModifiedDate
  @Column(name = "modified")
  private Date modified;

  @Column(name = "last_login")
  private Date lastLogin;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Telefono> telefonos;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.asList(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
