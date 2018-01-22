package br.biblioteca.livros.controladores;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Role;
import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.UsuarioRepository;
import br.biblioteca.livros.services.SecurityService;
import br.biblioteca.livros.services.UsuarioService;
import br.biblioteca.livros.validator.LoginValidator;
import br.biblioteca.livros.validator.UsuarioValidator;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private SecurityService securityService;
	  
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private LoginValidator loginValidator;  
	  
	@Autowired
	private UsuarioRepository usuarioRepository;	
	
	@GetMapping("/list")
	public ModelAndView usuarios() {

		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		return new ModelAndView("usuarios/list", "usuarios", usuarios);

	}
	
	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Usuario usuario) {
		return new ModelAndView("usuarios/form");
	} 

	@PostMapping(params = "form")
	public ModelAndView create(@RequestParam("usuarioRole") String usuarioRole, @ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult) {

		usuarioValidator.validate(usuario, bindingResult);

		 if (bindingResult.hasErrors()) {
			 return new ModelAndView("usuarios/form");
		 }

		 String password = usuario.getPassword();

		 usuarioService.save(usuario, usuarioRole);

		 try {
			securityService.login(usuario.getUsername(), password);
			return new ModelAndView("redirect:/usuarios/list");

		 } catch (Exception e) {

			return new ModelAndView("redirect:/usuarios/login");
		 }		
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		Usuario usuario = this.usuarioRepository.findOne(id);
		return new ModelAndView("usuarios/form", "usuario", usuario);
	}
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id) {
		Usuario usuario = this.usuarioRepository.findOne(id);
		this.usuarioService.delete(usuario);
		return new ModelAndView("redirect:/usuarios/list");
	}
	
	@GetMapping("/login")
	public ModelAndView login(@ModelAttribute Usuario usuario) {
		return new ModelAndView("usuarios/login");
	}

	@PostMapping("/autentication")
	public ModelAndView autentication(@ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, Model model) {

	 loginValidator.validate(usuario, bindingResult);

	 if (bindingResult.hasErrors()) {
		return new ModelAndView("usuarios/login");
	 }

	 securityService.login(usuario.getUsername(), usuario.getPassword());
	 return new ModelAndView("redirect:/");
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {

	 HttpSession session = request.getSession(false);
	 SecurityContextHolder.clearContext();
	 if (session != null) {
		session.invalidate();
	 }

	 return "redirect:/usuarios/login";
	}	
}
