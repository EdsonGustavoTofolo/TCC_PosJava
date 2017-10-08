package br.edu.utfpr.pb.projetojsp.web.controller;


import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.web.util.ControllersUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(Model model){
		Usuario usuario = ControllersUtil.getLoggedUser();
		if (StringUtils.isEmpty(usuario.getTelefone()) || Objects.isNull(usuario.getCurso())) {
			model.addAttribute("mensagem", "Identificamos que seu cadastro está incompleto! Por favor, complete-o!");
		}
		return "index";
	}
	
	@RequestMapping("/index/")
	public String index(Model model) {
		model.addAttribute("info", "Login efetuado com sucesso");
		return home(model);
	}
	
	@RequestMapping("/erro403")
	public String errorPage(Model model) {
		model.addAttribute("info", "Acesso negado. Área restrita.");
		return "erro403";
	}
}
