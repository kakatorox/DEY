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
2. Se uso OpenJDK 8u332-b09 tanto en macos como en windows la url de descarga es:
     Win: https://builds.openlogic.com/downloadJDK/openlogic-openjdk/8u332-b09/openlogic-openjdk-8u332-b09-windows-x64.msi
   macos: https://builds.openlogic.com/downloadJDK/openlogic-openjdk/8u332-b09/openlogic-openjdk-8u332-b09-mac-x64.pkg
4. Abrir proyecto con IDE a gusto.
5. Para acceder a api ingfesar a : http://localhost:8084/swagger-ui/index.html
6. El token que se entrega al crear el usuario ese debe ponerse en authrization porque los otros endpoint estan controlador por token 
7. Para acceder a H2: http://localhost:8084/h2-console
   - user: sa
   - password: libre

