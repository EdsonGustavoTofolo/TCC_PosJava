package br.edu.utfpr.pb.projetojsp.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimentoConsts;
import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.DisciplinaRepository;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoDisciplinaRepository;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Edson on 29/05/2017.
 */
@Controller
@RequestMapping("/requerimento")
public class RequerimentoController {

    @Autowired
    private RequerimentoRepository requerimentoRepository;
    @Autowired
    private RequerimentoDisciplinaRepository requerimentoDisciplinaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {
        model.put("motivos", MotivoRequerimentoConsts.getMotivosList());
        model.put("disciplinas", disciplinaRepository.findAll());
        return "requerimento/requerimentoForm";
    }

    @PostMapping(value = "/salvar")
    @ResponseBody
    public String salvar(@RequestBody Requerimento requerimento, BindingResult result, Model model, HttpServletRequest request) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        requerimento.setStatus(StatusRequerimentoEnum.ENVIADO_COORDENACAO);
        requerimento.setUsuario(usuario);

        if (!Objects.isNull(requerimento.getDisciplinas()) && !requerimento.getDisciplinas().isEmpty()) {
            requerimento.getDisciplinas().forEach(d -> d.setRequerimento(requerimento));
        }

        JSONObject retorno = new JSONObject();
        try{
            requerimentoRepository.save(requerimento);
            retorno.put("situacao", "OK");
            retorno.put("mensagem", "Registro salvo com sucesso!");
            retorno.put("id", requerimento.getId());
        }catch (Exception ex){
            retorno.put("situacao", "ERRO");
            retorno.put("mensagem", "Falha ao salvar registro! - <br /> " + ex.getMessage());
        }

        return retorno.toString();
    }

}
