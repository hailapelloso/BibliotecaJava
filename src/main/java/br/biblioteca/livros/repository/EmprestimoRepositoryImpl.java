package br.biblioteca.livros.repository;

import java.util.ArrayList;
import java.util.List;

import br.biblioteca.livros.beans.Emprestimo;
import br.biblioteca.livros.beans.Usuario;

public abstract class EmprestimoRepositoryImpl implements EmprestimoRepository {
	
	List<Emprestimo> emprestimos = new ArrayList<>();
	
	public List<Emprestimo> findByUsuario(Usuario usuario) {

		List<Emprestimo> emprestimosUsuario = new ArrayList<>();
		for (Emprestimo e : emprestimos) {
			if (e.getUsuario().getId().equals(usuario.getId())) {
				emprestimosUsuario.add(e);
			}
		}
		
		return emprestimosUsuario;
	}	
}
