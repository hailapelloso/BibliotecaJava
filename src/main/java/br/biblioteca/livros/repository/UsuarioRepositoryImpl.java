package br.biblioteca.livros.repository;

import java.util.ArrayList;
import java.util.List;

import br.biblioteca.livros.beans.Usuario;

public abstract class UsuarioRepositoryImpl implements UsuarioRepository {
	List<Usuario> usuarios = new ArrayList<>();
	
	public Usuario findByUsername(String username) {

		Usuario usuario = null;

		for (Usuario u : usuarios) {
			if (u.getUsername().equals(username)) {
				usuario = u;
			}
		}
		
		return usuario;
	}
}
