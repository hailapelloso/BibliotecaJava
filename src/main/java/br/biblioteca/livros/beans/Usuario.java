package br.biblioteca.livros.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "Usuário é obrigatório")
	@Size(min = 2, max = 100, message = "A quantidade de caracteres deve estar entre 2 a 100 caracteres")	
	private String username;
	
	@NotNull(message = "Usuário é obrigatório")
	@Size(min = 6, max = 30, message = "A quantidade de caracteres deve estar entre 6 a 30 caracteres")	
	private String passsword;
	
	@OneToMany(mappedBy="usuario")
	private List<Review> reviews = new ArrayList<>();
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasssword() {
		return passsword;
	}

	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}
	
	
	
	
	
	

}
