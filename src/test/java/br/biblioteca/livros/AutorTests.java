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
import br.biblioteca.livros.repository.AutorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutorTests {

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
	public void insereAutor(){
		Autor autor = new Autor();
		autor.setNome("Ariano Suassuna");
		autor = autorRepository.save(autor);
		assertThat(autor).isNotNull();
		assertThat(autor.getNome()).isEqualTo("Ariano Suassuna");
	}
	
	@Test
	public void alteraAutor(){
		Autor autor = this.autorRepository.findOne((long)1);
		autor.setNome("Monteiro Lobato");
		autor = autorRepository.save(autor);
		assertThat(autor).isNotNull();
		assertThat(autor.getNome()).isEqualTo("Monteiro Lobato");
	}	
	
	@Test
	public void excluiAutor(){
		Autor autor = this.autorRepository.findOne((long)4);
		autorRepository.delete(autor);
		Autor autorRemovido = this.autorRepository.findByNome("William Shakespeare");
		assertThat(autorRemovido).isNull();
	}		
	
	@Test
	public void buscaAutoresCadastrados() {
		Page<Autor> autores = this.autorRepository.findAll(new PageRequest(0, 10));
		assertThat(autores.getTotalElements()).isGreaterThan(1L);
	}
	
	@Test
	public void buscaAutorMachadoDeAssis() {
		Autor autorNaoEncontrado = this.autorRepository.findByNome("Guimarães Rosa");
		assertThat(autorNaoEncontrado).isNull();
		
		Autor autorEncontrado = this.autorRepository.findByNome("Machado de Assis");
		assertThat(autorEncontrado).isNotNull();
		assertThat(autorEncontrado.getNome()).isEqualTo("Machado de Assis");
	}
}
