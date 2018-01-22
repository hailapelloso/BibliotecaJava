package br.biblioteca.livros.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.services.UsuarioService;

@Component
public class UsuarioValidator implements Validator {
	@Autowired
	private UsuarioService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Usuario.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		Usuario usuario = (Usuario) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.usuario.username");
		if (usuario.getUsername().length() < 5 || usuario.getUsername().length() > 32) {
			errors.rejectValue("username", "Size.usuario.username");
		}
		
		Usuario usuarioVal = userService.findByUsername(usuario.getUsername());
		if (usuarioVal != null && usuarioVal.getId() != usuario.getId()) {
			errors.rejectValue("username", "Duplicate.usuario.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		if (usuario.getPassword().length() < 6 || usuario.getPassword().length() > 30) {
			errors.rejectValue("password", "Size.usuario.password");
		}

		/*if (!usuario.getPassword().equals(usuario.getPassword())) {
			errors.rejectValue("passwordConfirm", "Diff.usuario.passwordConfirm");
		}*/
		
	}
}