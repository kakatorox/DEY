# API RESTful de creación de usuarios

API RESTful para creacion de usuario y autenticación de estos.

## Requisitos
  - Springboot 
  - JDK 1.8
  - JWT
  - JPA
  - Spring Security
  - Swagger
  - Hibernate
  - H2
  - Tomcat
  - Gradle
  - Junit

## Características

- Registro de usuarios con los campos:
  - name
  - email
  - password
  - phones.
    
- Autenticación de usuarios con correo y contraseña.
- Actualización de información de usuarios existentes.
- Validaciones de correo y contraseña.
- Generación y persistencia de tokens de acceso JWT.
- Uso de pruebas unitarias para validar funcionalidades.


## Instrucciones de Uso

1. Clonar repositorio en local
2. Abrir proyecto con IDE a gusto.
3. Para acceder a api ingfesar a : http://localhost:8084/swagger-ui/index.html
4. Para acceder a H2: http://localhost:8084/h2-console
   user:sa
   password:
5.- el token que se entrega al crear el usuario ese debe ponerse en authrization porque los otros endpoint estan controlador por token 
