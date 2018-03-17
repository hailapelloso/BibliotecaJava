package br.biblioteca.livros;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import br.biblioteca.livros.beans.Autor;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.AutorRepository;
import br.biblioteca.livros.repository.LivroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LivroTests {

	@Autowired
	LivroRepository livroRepository;
	@Autowired
	AutorRepository autorRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void insereLivro(){
		Autor autor = this.autorRepository.findOne((long)1);

		Livro livro = new Livro();
		livro.setNome("Quincas Borba");
		livro.setIsbn("5798487");
		livro.setQuantidadePaginas(175);
		livro.setAutor(autor);
		
		livro = livroRepository.save(livro);
		assertThat(livro).isNotNull();
		assertThat(livro.getNome()).isEqualTo("Quincas Borba");
	}
	
	@Test
	public void alteraLivro(){
		Livro livro = this.livroRepository.findOne((long)1);
		livro.setNome("Dom Casmurro");
		livro = livroRepository.save(livro);
		assertThat(livro).isNotNull();
		assertThat(livro.getNome()).isEqualTo("Dom Casmurro");
	}	
	
	@Test
	public void excluiLivro(){
		Livro livro = this.livroRepository.findOne((long)4);
		livroRepository.delete(livro);
		Livro livroRemovido = this.livroRepository.findByNome("Sítio do Pica-pau Amarelo");
		assertThat(livroRemovido).isNull();
	}		
	
	@Test
	public void buscaLivrosCadastrados() {
		Page<Livro> livros = this.livroRepository.findAll(new PageRequest(0, 10));
		assertThat(livros.getTotalElements()).isGreaterThan(1L);
	}
	
	@Test
	public void buscaLivroMachadoDeAssis() {
		Livro livroNaoEncontrado = this.livroRepository.findByNome("O Alienista");
		assertThat(livroNaoEncontrado).isNull();
		
		Livro livroEncontrado = this.livroRepository.findByNome("O Guarani");
		assertThat(livroEncontrado).isNotNull();
		assertThat(livroEncontrado.getNome()).isEqualTo("O Guarani");
	}
}
