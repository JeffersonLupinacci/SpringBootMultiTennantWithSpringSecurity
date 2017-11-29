CREATE TABLE lancamentos (
  codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
  descricao VARCHAR(50) NOT NULL,
  data_vencimento DATE NOT NULL,
  data_pagamento DATE NULL,
  valor DECIMAL(10,2) NOT NULL,
  observacao VARCHAR(300) NULL,
  tipo VARCHAR(20) NOT NULL,
  codigo_categoria BIGINT NOT NULL,
  codigo_pessoa BIGINT NOT NULL,  
  FOREIGN KEY (codigo_categoria) REFERENCES categorias(codigo),
  FOREIGN KEY (codigo_pessoa) REFERENCES pessoas(codigo)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
    