package br.edu.utfpr.pb.projetojsp.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimentoConsts;
import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Edson on 29/05/2017.
 */
@Controller
@RequestMapping("/requerimento")
public class RequerimentoController {

    @Autowired
    private RequerimentoRepository requerimentoRepository;

    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {
        model.put("message", "HELLO TO THE JSP WORLD!");
        return "requerimento/requerimentoForm";
    }

    @PostMapping(value = "/salvar")
    public String salvar(@ModelAttribute("requerimentoForm") Requerimento requerimento, BindingResult result, Model model) {

        requerimento.setStatus(StatusRequerimentoEnum.ENVIADO_COORDENACAO);
//        requerimento.setUsuario();

        if (requerimento.getMotivo().equals(MotivoRequerimentoConsts.SEGUNDA_CHAMADA_PROVA)) {
            //TODO pegar os valores dos outros inputs
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
