package br.edu.utfpr.pb.projetojsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Edson on 05/08/2017.
 */

@Controller
@RequestMapping("/usuario/")
public class UsuarioController {

    @RequestMapping(value = "/")
    public String novo(){
        return "usuario/form";
    }
}
