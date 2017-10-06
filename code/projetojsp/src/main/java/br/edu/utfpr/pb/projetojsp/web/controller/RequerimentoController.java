package br.edu.utfpr.pb.projetojsp.web.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimento;
import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimentoConsts;
import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.enumeration.TipoUsuarioEnum;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.model.RequerimentoAnexo;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.*;
import br.edu.utfpr.pb.projetojsp.web.handler.RequerimentoDisciplinaJQGridHandler;
import br.edu.utfpr.pb.projetojsp.web.handler.RequerimentoJQGridHandler;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private CursoRepository cursoRepository;
    @Autowired
    private RequerimentoAnexoRepository requerimentoAnexoRepository;
    @Autowired
    private RequerimentoDisciplinaRepository requerimentoDisciplinaRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private RequerimentoJQGridHandler requerimentoJQGridHandler;
    @Autowired
    private RequerimentoDisciplinaJQGridHandler requerimentoDisciplinaJQGridHandler;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.put("cursoId", usuario.getCurso().getId());
        model.put("motivos", MotivoRequerimentoConsts.getMotivosList());
        model.put("cursos", cursoRepository.findAll());
        model.put("requerimento", new Requerimento()); //apenas para nào dar erros
        return "requerimento/requerimentoForm";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Requerimento requerimento = requerimentoRepository.findById(id).orElse(null);
        if (Objects.nonNull(requerimento)) {
            model.addAttribute("cursos", cursoRepository.findAll());
            model.addAttribute("motivos", MotivoRequerimentoConsts.getMotivosList());
            model.addAttribute("requerimento", requerimento);
            if (Objects.nonNull(requerimento.getDisciplinas()) && !requerimento.getDisciplinas().isEmpty()) {
                //Pega as disciplinas do requerimento e as ignora no select, pois no requerimento.js é feita uma nova consulta
                //para pegar as mesmas, caso contrário ficará duplicado os registros no duallistbox
                List<Long> disciplinas = new ArrayList<>();
                requerimento.getDisciplinas().forEach(d -> disciplinas.add(d.getDisciplina().getId()));

                //posiciona no primeiro, pq se entrar nesse if, ao menos uma disciplina existirá, e se houver mais de uma, todas pertencerão ao mesmo curso
                Long cursoId = requerimento.getDisciplinas().get(0).getDisciplina().getCurso().getId();

                model.addAttribute("cursoId", cursoId);
                model.addAttribute("disciplinas", disciplinaRepository.findByCursoIdAndIdNotIn(cursoId, disciplinas));
                model.addAttribute("professores", usuarioRepository.findByTipo(TipoUsuarioEnum.PROFESSOR));
            } else {
                Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                model.addAttribute("cursoId", usuario.getCurso().getId());
            }
        } else {
            model.addAttribute("error", "Requerimento inexistente!");
        }
        return "requerimento/requerimentoForm";
    }

    @RequestMapping("/list")
    public String listaRequerimentos(Model model) {
        return "requerimento/requerimentoList";
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, path = "/loadData")
    public String loadData(HttpServletRequest req, HttpServletResponse res) {
        String forward = "common/formData";

        String json = requerimentoJQGridHandler.loadData(req).getJson();
        req.setAttribute("formData", json);

        return forward;
    }

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, path = "/findDisciplinas")
    public String findDisciplinas(HttpServletRequest request) {
        String forward = "common/formData";

        String json = requerimentoDisciplinaJQGridHandler.loadData(request).getJson();
        request.setAttribute("formData", json);

        return forward;
    }


    /**
     *
     * @param requerimento
     * @param anexos - pra funcionar tive que alterar o método _getParamName no arquivo dropzone.js
     * @return
     */
    @RequestMapping(value = "/salvar", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public String salvar(@RequestPart("requerimento") Requerimento requerimento,
                         @RequestPart("file") MultipartFile[] anexos) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (requerimento.getMotivo() == MotivoRequerimentoConsts.SEGUNDA_CHAMADA_PROVA) {
            requerimento.setStatus(StatusRequerimentoEnum.AGUARDANDO_COORDENACAO);
        } else {
            requerimento.setStatus(StatusRequerimentoEnum.AGUARDANDO_DERAC);
        }
        requerimento.setUsuario(usuario);

        if (!Objects.isNull(requerimento.getDisciplinas()) && !requerimento.getDisciplinas().isEmpty()) {
            if (Objects.nonNull(requerimento.getId()) && requerimento.getId() > 0) {
                requerimentoDisciplinaRepository.deleteByRequerimentoId(requerimento.getId());
            }
            requerimento.getDisciplinas().forEach(d -> d.setRequerimento(requerimento));
        }

        if (anexos.length > 0) {
            List<RequerimentoAnexo> requerimentoAnexos = new ArrayList<>();
            try {
                for (MultipartFile anexo : anexos) {
                    RequerimentoAnexo requerimentoAnexo = new RequerimentoAnexo();
                    requerimentoAnexo.setRequerimento(requerimento);
                    requerimentoAnexo.setNome(anexo.getOriginalFilename());
                    requerimentoAnexo.setTipo(anexo.getName());
                    requerimentoAnexo.setArquivo(anexo.getBytes());
                    requerimentoAnexo.setContentType(anexo.getContentType());
                    requerimentoAnexo.setTamanho(new Long(anexo.getSize()));

                    requerimentoAnexos.add(requerimentoAnexo);
                }
                requerimento.setAnexos(requerimentoAnexos); //salva tudo junto
            } catch (IOException e) {
                e.printStackTrace();
                //TODO
            }
        }

        JSONObject retorno = new JSONObject();
        try{
            requerimentoRepository.save(requerimento);
            retorno.put("state", "OK");
            retorno.put("message", "Requerimento gravado com sucesso!");
            retorno.put("id", requerimento.getId());
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao gravar requerimento!\n" + ex.getCause().getCause().getMessage());
        }

        return retorno.toString();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String excluir(@PathVariable Long id) {
        JSONObject retorno = new JSONObject();
        try{
            if (Objects.nonNull(requerimentoRepository.findById(id).orElse(null))) {
                requerimentoRepository.deleteById(id);
                retorno.put("state", "OK");
                retorno.put("message", "Registro removido com sucesso!");
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Falha ao remover registro!\nRequerimento inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao remover registro!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }

    @RequestMapping("/anexos/download/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void download(@PathVariable("id") Long id, HttpServletResponse response) {
        RequerimentoAnexo anexo = requerimentoAnexoRepository.findById(id).get();
        response.setContentType(anexo.getContentType());
        response.setContentLength(anexo.getArquivo().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + anexo.getNome() + "\"");

        try {
            FileCopyUtils.copy(anexo.getArquivo(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/anexo/delete/{id}")
    @ResponseBody
    public String excluirAnexo(@PathVariable Long id) {
        JSONObject retorno = new JSONObject();
        try{
            if (Objects.nonNull(requerimentoAnexoRepository.findById(id).orElse(null))) {
                requerimentoAnexoRepository.deleteById(id);
                retorno.put("state", "OK");
                retorno.put("message", "Anexo removido com sucesso!");
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Falha ao remover Anexo!\nAnexo inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao remover Anexo!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }

    @GetMapping(value = "/motivos/findAll")
    @ResponseBody
    public List<MotivoRequerimento> findByCurso(HttpServletRequest request) {
        return MotivoRequerimentoConsts.getMotivosList();
    }

}
