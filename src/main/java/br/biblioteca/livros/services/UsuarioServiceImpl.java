package br.biblioteca.livros.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.biblioteca.livros.beans.Role;
import br.biblioteca.livros.beans.Usuario;
import br.biblioteca.livros.repository.RoleRepository;
import br.biblioteca.livros.repository.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Usuario save(Usuario usuario, String usuarioRole) {
    	
    	if (usuario.getId() > 0){
	    	Usuario usuarioRoles = usuarioRepository.findOne(usuario.getId());
	    	for (Role role : usuarioRoles.getRoles()) {
	    		roleRepository.delete(role);
	        }
    	}
    	
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        usuario = usuarioRepository.save(usuario);
                       
        Role role = new Role(usuarioRole);
        role.setUsuario(usuario); 
        
        roleRepository.save(role);
        return usuario;
    }

    @Override
    public void delete(Usuario usuario) {
    	Usuario usuarioDelete = usuarioRepository.findOne(usuario.getId());
    	
    	for (Role role : usuarioDelete.getRoles()) {
    		roleRepository.delete(role);
        }
    	    	
    	usuarioRepository.delete(usuario);
    }
    
    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    @Override
    public Usuario getUsuarioLogado(){
    	Object usuarioLogado = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String username;
    	if (usuarioLogado  instanceof UserDetails ) {
    	   username= ((UserDetails)usuarioLogado).getUsername();
    	} else {
    	   username = usuarioLogado .toString();
    	}
    	
    	return findByUsername(username);
    }
}