package br.edu.utfpr.pb.projetojsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Edson on 05/08/2017.
 */

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value="error", required=false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário e/ou senha inválidos!");
        }
        return "login";
    }

    @GetMapping("/login/novoUsuario")
    public String novoUsuario() {
        return "login/novoUsuario";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "login";
    }
}
