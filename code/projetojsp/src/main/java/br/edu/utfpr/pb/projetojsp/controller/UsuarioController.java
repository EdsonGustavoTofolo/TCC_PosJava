package br.edu.utfpr.pb.projetojsp.controller;

import br.edu.utfpr.pb.projetojsp.model.Permissao;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.PermissaoRepository;
import br.edu.utfpr.pb.projetojsp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by Edson on 05/08/2017.
 */

@Controller
@RequestMapping("/usuario/")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;

    @RequestMapping(value = "/")
    public String novo(){
        return "usuario/form";
    }

    @PostMapping("criarNovoUsuario/")
    public String criarNovoUsuario(@Valid Usuario usuario, BindingResult errors, Model model) {
        usuario.setSenha(usuario.getEncodedPassword(usuario.getSenha()));

        usuarioRepository.save(usuario);

        model.addAttribute("usuario", usuario);

        return "usuario/formConfirmaDados"; //chama o form do usuario para completar o cadastro
    }

    private Permissao getPermissao() {
        Permissao permissao = permissaoRepository.findByPermissao("ROLE_USER");
        if (permissao == null){
            permissao = new Permissao();
            permissao.setPermissao("ROLE_USER");
            permissaoRepository.save(permissao);
        }
        return permissao;
    }

    @PostMapping("gravar/")
    public String gravar(@Valid Usuario usuario, BindingResult erros, Model model) {
        usuario.addPermissao(getPermissao());
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

}
