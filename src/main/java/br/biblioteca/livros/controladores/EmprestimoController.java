package br.biblioteca.livros.controladores;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.biblioteca.livros.beans.Emprestimo;
import br.biblioteca.livros.beans.Livro;
import br.biblioteca.livros.repository.EmprestimoRepository;
import br.biblioteca.livros.repository.LivroRepository;
import br.biblioteca.livros.services.UsuarioService;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

	@Autowired
	private EmprestimoRepository emprestimoRepository;

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/list")
	public ModelAndView emprestimos() {

		Iterable<Emprestimo> emprestimos = emprestimoRepository.findByUsuario(usuarioService.getUsuarioLogado());
		return new ModelAndView("emprestimos/list", "emprestimos", emprestimos);

	}
	
	@GetMapping("/novo")
	public ModelAndView createForm(@ModelAttribute Emprestimo emprestimo) {
		ModelAndView modelAndView = new ModelAndView("emprestimos/form");

		Iterable<Livro> livros = livroRepository.findAll();
		modelAndView.addObject("livros", livros);
		
		return modelAndView;
	} 

	@PostMapping(params = "form")
	public ModelAndView create(@RequestParam(value = "livrosSelecionados", required = false) String livrosSelecionados, @ModelAttribute("emprestimo") @Valid Emprestimo emprestimo, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors() || livrosSelecionados == null || livrosSelecionados == "") {
			ModelAndView modelAndView = new ModelAndView("emprestimos/form");

			model.addAttribute("message", "É necessário selecionar um livro para realizar o empréstimo");

			Iterable<Livro> livros = livroRepository.findAll();
			modelAndView.addObject("livros", livros);
			
			return modelAndView;
		}
		
		for (String idLivro : livrosSelecionados.split(",")) {
			Livro livro = livroRepository.findOne(Long.parseLong(idLivro));
			Emprestimo emprestimoNovo = new Emprestimo();
			emprestimoNovo.setLivro(livro);
			emprestimoNovo.setDataEmprestimo(new Date());
			emprestimoNovo.setUsuario(usuarioService.getUsuarioLogado());
			emprestimoNovo = emprestimoRepository.save(emprestimoNovo);
		}
		
		return new ModelAndView("redirect:/emprestimos/list");
	}
	
	@GetMapping("/devolver/{id}")
	public ModelAndView devolver(@PathVariable("id") Long id) {
		Emprestimo emprestimo = emprestimoRepository.findOne(id);
		emprestimo.setDataDevolucao(new Date());
		emprestimoRepository.save(emprestimo);
		return new ModelAndView("redirect:/emprestimos/list");
	}
	
	
	
}
