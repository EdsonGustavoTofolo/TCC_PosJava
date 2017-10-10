package br.edu.utfpr.pb.projetojsp.web.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.TipoUsuarioEnum;
import br.edu.utfpr.pb.projetojsp.model.Permissao;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.CursoRepository;
import br.edu.utfpr.pb.projetojsp.repository.PermissaoRepository;
import br.edu.utfpr.pb.projetojsp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private CursoRepository cursoRepository;

    @RequestMapping(value = "/")
    public String novo(){
        return "usuario/form";
    }

    @RequestMapping(value = "/conta/")
    public String conta(Model model) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("usuario", usuario);
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("titulo", "Edição da Conta do Usuário");
        return "usuario/formConfirmaDados";
    }

    @PostMapping("criarNovoUsuario/")
    public String criarNovoUsuario(@Valid Usuario usuario, BindingResult errors, Model model) {
        usuario.setSenha(usuario.getEncodedPassword(usuario.getSenha()));
        usuario.addPermissao(getPermissao());
        usuario.setTipo(TipoUsuarioEnum.ALUNO);
        usuarioRepository.save(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("titulo", "Conclusão de Cadastro");
        return "usuario/formConfirmaDados"; //chama o form do usuario para completar o cadastro
    }

    @PostMapping("gravar/")
    public String gravar(@Valid Usuario usuario, BindingResult erros, Model model) {
        Usuario usuarioBd = usuarioRepository.findById(usuario.getId()).get();

        usuario.setTipo(TipoUsuarioEnum.ALUNO);
        usuario.setUsername(usuarioBd.getUsername());
        usuario.setDataCadastro(usuarioBd.getDataCadastro());
        usuario.setPermissoes(usuarioBd.getPermissoes());
        usuario.setSenha(usuarioBd.getSenha());

        String telefoneNoFormat = usuario.getTelefone();
        telefoneNoFormat = telefoneNoFormat.replaceAll("[^0-9]+", "");
        usuario.setTelefone(telefoneNoFormat);
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    @PostMapping("validarUsername/")
    @ResponseBody
    public boolean validarUsername(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        Usuario usuario = usuarioRepository.findByUsername(username);
        return Objects.isNull(usuario);
    }

    @GetMapping(value = "/findByProfessores")
    @ResponseBody
    public List<Usuario> findByProfessores() {
        return usuarioRepository.findByTipo(TipoUsuarioEnum.PROFESSOR);
    }

    private Permissao getPermissao() {
        Permissao permissao = permissaoRepository.findByPermissao("ROLE_ALUNO");
        if (Objects.isNull(permissao)){
            permissao = new Permissao();
            permissao.setPermissao("ROLE_ALUNO");
            permissaoRepository.save(permissao);
        }
        return permissao;
    }

}
