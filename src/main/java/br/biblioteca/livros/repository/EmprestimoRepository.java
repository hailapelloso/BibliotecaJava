package br.biblioteca.livros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.biblioteca.livros.beans.Emprestimo;
import br.biblioteca.livros.beans.Usuario;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

	List<Emprestimo> findByUsuario(Usuario usuario);

}
