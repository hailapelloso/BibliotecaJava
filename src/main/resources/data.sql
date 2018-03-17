insert into Usuario (id, username, password) values
	(1, 'admin', '$2a$10$ytoUZwUZjitWUYdgeBIak.WGg4gE9WjNZgUd2a3xCXDBwSuNbeFT2'),
	(2, 'usuario1', '$2a$10$ytoUZwUZjitWUYdgeBIak.WGg4gE9WjNZgUd2a3xCXDBwSuNbeFT2'),
	(3, 'usuario2', '$2a$10$ytoUZwUZjitWUYdgeBIak.WGg4gE9WjNZgUd2a3xCXDBwSuNbeFT2');

insert into role(id, role, usuario_id) values
	(1, 'ROLE_ADMIN', 1),
	(2, 'ROLE_BASIC', 2),
	(3, 'ROLE_BASIC', 3);

insert into Autor (id, nome) values
	(1,'Monteiro L'),
	(2,'Machado de Assis'),
	(3,'José de Alencar'),
	(4,'William Shakespeare');

insert into Livro(id, capa, isbn, nome, quantidade_paginas, autor_id ) VALUES
	(1, '', '8798464', 'Dom Casmurro 2', 200, 2),
	(2, '', '8784566', 'O Guarani', 300, 3),
	(3, '', '7585858', 'Memórias Póstumas de Brás Cubas', 258, 2),
	(4, '', '6585478', 'Sítio do Pica-pau Amarelo', 147, 1);
