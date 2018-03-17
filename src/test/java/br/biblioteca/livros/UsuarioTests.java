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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.beans.Role;
import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.RoleRepository;
import br.biblioteca.livros.repository.UsuarioRepository;
import br.biblioteca.livros.services.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioTests {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void insereUsuario(){
		Usuario usuario = new Usuario();
		usuario.setUsername("usuario3");
		usuario.setPassword(bCryptPasswordEncoder.encode("123456"));
        usuario = usuarioRepository.save(usuario);
		
		assertThat(usuario).isNotNull();
		assertThat(usuario.getUsername()).isEqualTo("usuario3");
	}
	
	@Test
	public void alteraUsuario(){
		Usuario usuario = this.usuarioRepository.findOne((long)2);
		usuario.setUsername("usuario2Alterado");
		usuario = usuarioRepository.save(usuario);
		assertThat(usuario).isNotNull();
		assertThat(usuario.getUsername()).isEqualTo("usuario2Alterado");
	}		
	
	@Test
	public void buscaUsuariosCadastrados() {
		Page<Usuario> usuarios = this.usuarioRepository.findAll(new PageRequest(0, 10));
		assertThat(usuarios.getTotalElements()).isGreaterThan(1L);
	}
	
	@Test
	public void buscaUsuarioMachadoDeAssis() {
		Usuario usuarioNaoEncontrado = this.usuarioRepository.findByUsername("usuarioTeste");
		assertThat(usuarioNaoEncontrado).isNull();
		
		Usuario usuarioEncontrado = this.usuarioRepository.findByUsername("admin");
		assertThat(usuarioEncontrado).isNotNull();
		assertThat(usuarioEncontrado.getUsername()).isEqualTo("admin");
	}
}
