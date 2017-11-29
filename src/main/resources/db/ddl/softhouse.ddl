/* Removendo o contrato remove os usuários e suas permissoes */
CREATE TABLE `contratos` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `razao_social` varchar(100) NOT NULL,
  `fantasia` varchar(100) NOT NULL,
  `email` varchar(25) NOT NULL,
  `data_registro` datetime NOT NULL,
  `logradouro` varchar(100) DEFAULT NULL,
  `numero` varchar(25) DEFAULT NULL,
  `complemento` varchar(150) DEFAULT NULL,
  `bairro` varchar(80) DEFAULT NULL,
  `cep` varchar(25) DEFAULT NULL,
  `cidade` varchar(60) DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  `cnpj` varchar(20) NOT NULL,
  `aliases` varchar(150) NOT NULL,
  `ativo` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `codigo_UNIQUE` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

CREATE TABLE `permissao` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8


/* Remove os usuário ao remover o contrato */
CREATE TABLE `usuarios` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) NOT NULL,
  `email` varchar(150) NOT NULL,
  `senha` varchar(70) NOT NULL,
  `codigo_contrato` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_usuario_contrato_idx` (`codigo_contrato`),
  CONSTRAINT `fk_usuario_contrato` FOREIGN KEY (`codigo_contrato`) REFERENCES `contratos` (`codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

/* Remove as permissões ao remover o usuário */
/* Remove as permissões ao remover a permissão */
CREATE TABLE `usuarios_permissao` (
  `codigo_usuario` bigint(20) NOT NULL,
  `codigo_permissao` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo_usuario`,`codigo_permissao`),
  KEY `fk_c_permissao_idx` (`codigo_permissao`),
  CONSTRAINT `fk_c_permissao` FOREIGN KEY (`codigo_permissao`) REFERENCES `permissao` (`codigo`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_c_usuario` FOREIGN KEY (`codigo_usuario`) REFERENCES `usuarios` (`codigo`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8