CREATE TABLE usuarios (
  codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL,
  senha VARCHAR(70) NOT NULL,
  contrato VARCHAR(150) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
CREATE TABLE permissao (
  codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
  descricao VARCHAR(100) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    
CREATE TABLE usuarios_permissao (
  codigo_usuario BIGINT,
  codigo_permissao BIGINT,
  PRIMARY KEY(codigo_usuario, codigo_permissao),
  FOREIGN KEY (codigo_usuario) REFERENCES usuarios(codigo),
  FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;