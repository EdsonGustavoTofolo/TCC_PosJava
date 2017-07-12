package br.edu.utfpr.pb.projetojsp.controller;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Edson on 29/05/2017.
 */
@Controller
@RequestMapping("/requerimento")
public class RequerimentoController {
    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {
        model.put("message", "HELLO TO THE JSP WORLD!");
        return "requerimento/requerimentoForm";
    }

    @PostMapping(value = "/", produces = "application/json")
    @ResponseBody
    public String salvar(Requerimento requerimento, Model model) {

        return "requerimento/requerimentoForm";
    }


}
