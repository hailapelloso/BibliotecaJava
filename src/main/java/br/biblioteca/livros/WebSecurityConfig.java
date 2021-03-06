package br.biblioteca.livros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/webjars/**", "/uploaded-images/**").permitAll()
		.antMatchers(HttpMethod.POST, "/usuarios/autentication/**").permitAll()
		.antMatchers(HttpMethod.GET, "/livros/excluir/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/livros/alterar/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/autores/excluir/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/autores/alterar/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/usuarios/excluir/**").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/usuarios/alterar/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/usuarios/login").permitAll()
		.and().logout().permitAll();
		
		http.authorizeRequests().antMatchers("/").permitAll().and()
        .authorizeRequests().antMatchers("/console/**").permitAll();
		
        http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
}
