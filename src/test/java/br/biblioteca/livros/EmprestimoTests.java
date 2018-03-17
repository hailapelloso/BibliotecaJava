package br.biblioteca.livros;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.biblioteca.livros.beans.Emprestimo;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.EmprestimoRepository;
import br.biblioteca.livros.repository.LivroRepository;
import br.biblioteca.livros.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmprestimoTests {

	@Autowired
	EmprestimoRepository emprestimoRepository;

	@Autowired
	LivroRepository livroRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void criaEmprestimo(){
		Livro livro = livroRepository.findOne((long)1);
		Usuario usuario = usuarioRepository.findOne((long)1);
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setLivro(livro);
		emprestimo.setDataEmprestimo(new Date());
		emprestimo.setUsuario(usuario);
		emprestimo = emprestimoRepository.save(emprestimo);
		
		assertThat(emprestimo).isNotNull();
	}
		
	@Test
	public void devolveLivro(){
		Livro livro = livroRepository.findOne((long)1);
		Usuario usuario = usuarioRepository.findOne((long)1);
		Emprestimo emprestimo = new Emprestimo();
		emprestimo.setLivro(livro);
		emprestimo.setDataEmprestimo(new Date());
		emprestimo.setUsuario(usuario);
		emprestimo = emprestimoRepository.save(emprestimo);		
		
		emprestimo.setDataDevolucao(new Date());
		emprestimoRepository.save(emprestimo);
		
		Emprestimo emprestimoDevolvido = this.emprestimoRepository.findOne((long)1);
		assertThat(emprestimoDevolvido.getDataDevolucao()).isNotNull();
	}		
	

}
