package br.edu.utfpr.pb.projetojsp.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimentoConsts;
import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.model.RequerimentoDisciplina;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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
    public String salvar(@ModelAttribute("requerimentoForm") Requerimento requerimento, BindingResult result, Model model, HttpServletRequest request) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        requerimento.setStatus(StatusRequerimentoEnum.ENVIADO_COORDENACAO);
        requerimento.setUsuario(usuario);

        JSONObject retorno = new JSONObject();
        try{
            requerimentoRepository.save(requerimento);
            if (requerimento.getMotivo().equals(MotivoRequerimentoConsts.SEGUNDA_CHAMADA_PROVA)) {
                salvarRequerimentoDisciplina(requerimento, request.getParameter("disciplina"), request.getParameter("professor"), request.getParameter("data"));
            }
            retorno.put("situacao", "OK");
            retorno.put("mensagem", "Registro salvo com sucesso!");
            retorno.put("id", requerimento.getId());
        }catch (Exception ex){
            retorno.put("situacao", "ERRO");
            retorno.put("mensagem", "Falha ao salvar registro! - <br /> " + ex.getMessage());
        }

        return retorno.toString();
    }

    private void salvarRequerimentoDisciplina(Requerimento requerimento, String disciplina, String professor, String data) {
        Date d = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d = sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RequerimentoDisciplina requerimentoDisciplina = new RequerimentoDisciplina();
        requerimentoDisciplina.setRequerimento(requerimento);
        requerimentoDisciplina.setNome(disciplina);
        requerimentoDisciplina.setProfessor(professor);
        requerimentoDisciplina.setDataProva(d);
        requerimentoDisciplinaRepository.save(requerimentoDisciplina);
    }


}
