INSERT INTO usuarios(nome, email, contrato, senha) VALUES ("admin", "admin", "contrato111", "$2a$10$4ygn16vpsPnKIOqV/8AsAu7bT2ULw9sE4pzAYRhUB3EDg4JagHI7W");

INSERT INTO permissao(descricao) VALUES ("ROLE_CATEGORIA_PESQUISAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_CATEGORIA_VISUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_CATEGORIA_CADASTRAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_CATEGORIA_ATUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_CATEGORIA_DELETAR");

INSERT INTO permissao(descricao) VALUES ("ROLE_PESSOA_PESQUISAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_PESSOA_VISUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_PESSOA_CADASTRAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_PESSOA_ATUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_PESSOA_DELETAR");

INSERT INTO permissao(descricao) VALUES ("ROLE_LANCAMENTO_PESQUISAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_LANCAMENTO_VISUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_LANCAMENTO_CADASTRAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_LANCAMENTO_ATUALIZAR");
INSERT INTO permissao(descricao) VALUES ("ROLE_LANCAMENTO_DELETAR");

INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 1);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 2);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 3);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 4);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 5);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 6);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 7);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 8);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 9);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 10);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 11);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 12);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 13);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 14);
INSERT INTO usuarios_permissao (codigo_usuario, codigo_permissao) values(1, 15);