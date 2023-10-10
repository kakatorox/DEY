CREATE TABLE usuario (
    uuid VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255),
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    is_active BOOLEAN,
    token VARCHAR(255),
    role VARCHAR(10)
);

CREATE TABLE telefono (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    citycode VARCHAR(3),
    contrycode VARCHAR(3),
    number VARCHAR(9),
    usuario_uuid VARCHAR(36),
    FOREIGN KEY (usuario_uuid) REFERENCES usuario(uuid)
);
