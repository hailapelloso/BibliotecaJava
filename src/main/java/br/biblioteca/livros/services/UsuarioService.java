package br.biblioteca.livros.services;

import java.util.List;

import br.biblioteca.livros.beans.Usuario;

public interface UsuarioService {
	
	Usuario save(Usuario usuario, String usuarioRole);
	
	void delete(Usuario usuario);

	Usuario findByUsername(String username);

	List<Usuario> findAll();
	
	Usuario getUsuarioLogado();
	
}