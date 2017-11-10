package br.edu.utfpr.pb.projetojsp.web.controller;

import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimento;
import br.edu.utfpr.pb.projetojsp.enumeration.MotivoRequerimentoConsts;
import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.enumeration.TipoUsuarioEnum;
import br.edu.utfpr.pb.projetojsp.model.*;
import br.edu.utfpr.pb.projetojsp.repository.*;
import br.edu.utfpr.pb.projetojsp.specification.RequerimentoSpecification;
import br.edu.utfpr.pb.projetojsp.web.handler.RequerimentoConvalidacaoJQGridHandler;
import br.edu.utfpr.pb.projetojsp.web.handler.RequerimentoDisciplinaJQGridHandler;
import br.edu.utfpr.pb.projetojsp.web.handler.RequerimentoJQGridHandler;
import br.edu.utfpr.pb.projetojsp.web.util.ControllersUtil;
import br.edu.utfpr.pb.projetojsp.web.util.RequerimentoControllerUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
    private RequerimentoConvalidacaoRepository requerimentoConvalidacaoRepository;
    @Autowired
    private DisciplinaRepository disciplinaRepository;
    @Autowired
    private RequerimentoJQGridHandler requerimentoJQGridHandler;
    @Autowired
    private RequerimentoDisciplinaJQGridHandler requerimentoDisciplinaJQGridHandler;
    @Autowired
    private RequerimentoConvalidacaoJQGridHandler requerimentoConvalidacaoJQGridHandler;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Secured("ROLE_ALUNO")
    @RequestMapping("/")
    public String initRequerimento(Map<String, Object> model) {
        Usuario usuario = ControllersUtil.getLoggedUser();
        model.put("cursoId", usuario.getCurso().getId());
        model.put("motivos", MotivoRequerimentoConsts.getMotivosList());
        model.put("cursos", cursoRepository.findAll());
        model.put("requerimento", new Requerimento()); //apenas para nào dar erros
        model.put("classActiveRequerimento", "active");

        return "requerimento/requerimentoForm";
    }

    private String changeStatus(Long requerimentoId, StatusRequerimentoEnum status, RequerimentoObservacao requerimentoObservacao) {
        JSONObject retorno = new JSONObject();
        try{
            Requerimento requerimento = requerimentoRepository.findById(requerimentoId).orElse(null);
            if (Objects.nonNull(requerimento)) {
                requerimentoObservacao.setUsuario(ControllersUtil.getLoggedUser());
                requerimentoObservacao.setRequerimento(requerimento);
                requerimento.getObservacoes().add(requerimentoObservacao);
                requerimento.setStatus(status);
                requerimento.setDataUltimoStatus(new Date());
                requerimentoRepository.save(requerimento);
                retorno.put("state", "OK");
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Requerimento Inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao gravar requerimento!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }

    @Secured({"ROLE_DERAC", "ROLE_COORDENACAO"})
    @PutMapping(value = "/edit/{requerimentoId}/changeStatus/{status}", consumes = {"application/json"})
    @ResponseBody
    public String changeStatus(@PathVariable(name = "requerimentoId") Long requerimentoId,
                               @PathVariable(name = "status") Integer statusId,
                               @RequestBody RequerimentoObservacao requerimentoObservacao) {
        return changeStatus(requerimentoId,
                StatusRequerimentoEnum.values()[statusId],
                requerimentoObservacao);
    }

    @Secured("ROLE_PROFESSOR")
    @PutMapping(value = "/convalidacao/gravarParecer/", consumes = {"application/json"})
    @ResponseBody
    public String gravarParecer(@RequestBody RequerimentoConvalidacao itemConvalidacao) {
        JSONObject retorno = new JSONObject();
        try{
            RequerimentoConvalidacao requerimentoConvalidacao = requerimentoConvalidacaoRepository.findById(itemConvalidacao.getId()).orElse(null);
            if (Objects.nonNull(requerimentoConvalidacao)) {
                requerimentoConvalidacao.setDeferido(itemConvalidacao.getDeferido());
                requerimentoConvalidacao.setJustificativa(itemConvalidacao.getJustificativa());
                requerimentoConvalidacaoRepository.save(requerimentoConvalidacao);
                retorno.put("state", "OK");
                retorno.put("message", "Parecer gravado com sucesso!");
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Item da convalidação inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao gravar parecer!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }

    @Secured("ROLE_COORDENACAO")
    @PutMapping(value = "/convalidacao/{convalidacaoId}/definirProfessor/{professorId}")
    @ResponseBody
    public String definirProfessor(@PathVariable(name = "convalidacaoId") Long convalidacaoId,
                                   @PathVariable(name = "professorId") Long professorId) {
        JSONObject retorno = new JSONObject();
        try{
            RequerimentoConvalidacao requerimentoConvalidacao = requerimentoConvalidacaoRepository.findById(convalidacaoId).orElse(null);
            if (Objects.nonNull(requerimentoConvalidacao)) {
                Usuario professor = usuarioRepository.findById(professorId).get();
                requerimentoConvalidacao.setProfessor(professor);
                requerimentoConvalidacaoRepository.save(requerimentoConvalidacao);
                retorno.put("state", "OK");
                retorno.put("message", "Professor definido com sucesso!");
                retorno.put("professor", professor.getNome());
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Item da convalidação inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao gravar professor no item da convalidação!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }

    @Secured({"ROLE_DERAC", "ROLE_COORDENACAO"})
    @PutMapping(value = "/edit/{requerimentoId}/changeStatusKanban/{status}", consumes = {"application/json"})
    @ResponseBody
    public String changeStatus(@PathVariable(name = "requerimentoId") Long requerimentoId,
                               @PathVariable(name = "status") String status,
                               @RequestBody RequerimentoObservacao requerimentoObservacao) {
        return changeStatus(requerimentoId, StatusRequerimentoEnum.valueOf(status), requerimentoObservacao);
    }

    @Secured({"ROLE_ALUNO", "ROLE_DERAC", "ROLE_COORDENACAO", "ROLE_PROFESSOR"})
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Requerimento requerimento = requerimentoRepository.findById(id).orElse(null);
        if (Objects.nonNull(requerimento)) {
            Boolean hasRolesSuper = ControllersUtil.hasLoggedUserAnyRole("ROLE_DERAC", "ROLE_COORDENACAO", "ROLE_PROFESSOR");

            Usuario loggedUser = ControllersUtil.getLoggedUser();

            //Só pode alterar requerimento do usuário que criou e que o status esteja em falta de documentos ou aguardando derac OU o não é ALUNO
            // essa validação é válida pois algum usuário pode
            //editar a URL e informar um id aleatório

            if (hasRolesSuper ||
                    (requerimento.getUsuario().equals(loggedUser) && requerimento.getStatus().equals(StatusRequerimentoEnum.EM_ABERTO))) {

                if (hasRolesSuper) {
                    model.addAttribute("statuses", RequerimentoControllerUtil.getStatuses());
                }

                //TODO AJUSTAR PARA QUE QUANDO FOR COORDENADOR OU DERAC, NÀO ENVIE TODOS OS CURSOS, POIS ELES NAO PODEM ALTERAR OS EDITORES

                model.addAttribute("cursos", cursoRepository.findAll());
                model.addAttribute("motivos", MotivoRequerimentoConsts.getMotivosList());
                model.addAttribute("requerimento", requerimento);
                model.addAttribute("classActiveRequerimento", "active");
                if (Objects.nonNull(requerimento.getDisciplinas()) && !requerimento.getDisciplinas().isEmpty()) {
                    List<Disciplina> disciplinaList;

                    //posiciona no primeiro, pq se entrar nesse if, ao menos uma disciplina existirá, e se houver mais de uma, todas pertencerão ao mesmo curso
                    Long cursoId = requerimento.getDisciplinas().get(0).getDisciplina().getCurso().getId();

                    if (requerimento.getMotivo() == MotivoRequerimentoConsts.SEGUNDA_CHAMADA_PROVA) {
                        disciplinaList = disciplinaRepository.findByCursoId(cursoId);
                    } else {
                        //Pega as disciplinas do requerimento e as ignora no select, pois no requerimento.js é feita uma nova consulta
                        //para pegar as mesmas, caso contrário ficará duplicado os registros no duallistbox
                        List<Long> disciplinas = new ArrayList<>();
                        requerimento.getDisciplinas().forEach(d -> disciplinas.add(d.getDisciplina().getId()));
                        disciplinaList = disciplinaRepository.findByCursoIdAndIdNotIn(cursoId, disciplinas);
                    }

                    model.addAttribute("cursoId", cursoId);
                    model.addAttribute("disciplinas", disciplinaList);
                    model.addAttribute("professores", usuarioRepository.findByTipo(TipoUsuarioEnum.PROFESSOR));
                } else if (requerimento.getMotivo() == MotivoRequerimentoConsts.CONVALIDACAO){
                    Long cursoId = requerimento.getConvalidacoes().get(0).getDisciplinaUtfpr().getCurso().getId();
                    model.addAttribute("cursoId", cursoId);
                    model.addAttribute("disciplinas", disciplinaRepository.findByCursoId(cursoId));
                    if (ControllersUtil.hasLoggedUserRole("ROLE_COORDENACAO")) {
                        model.addAttribute("professores", usuarioRepository.findByTipo(TipoUsuarioEnum.PROFESSOR));
                    } else if (ControllersUtil.hasLoggedUserRole("ROLE_PROFESSOR")) {
                        //exibe somente as disciplinas as quais o professor está vinculado para dar o parecer
                        requerimento.getConvalidacoes().removeIf(i -> Objects.isNull(i.getProfessor()) || i.getProfessor().getId() != loggedUser.getId());
                    }
                } else {
                    if (hasRolesSuper) { //se nào for o aluno o usuário logado, pega o id do curso do usuário que originou o requerimento
                        model.addAttribute("cursoId", cursoRepository.findById(requerimento.getUsuario().getCurso().getId()).get().getId());
                    } else {
                        model.addAttribute("cursoId", loggedUser.getCurso().getId());
                    }
                }
                if (hasRolesSuper) {
                    model.addAttribute("alunoNome", usuarioRepository.findById(requerimento.getUsuario().getId()).get().getNome());
                }
            } else {
                model.addAttribute("error", "Você não possui permissão para editar o Requerimento solicitado!");
                return "forward:/requerimento/list";
            }
        } else {
            model.addAttribute("error", "Requerimento inexistente!");
        }
        return "requerimento/requerimentoForm";
    }

    @RequestMapping("/list")
    public String listaRequerimentos(Model model) {
        model.addAttribute("classActiveListaRequerimento", "active");
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

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, path = "/findConvalidacao")
    public String findConvalidacao(HttpServletRequest request) {
        String forward = "common/formData";

        String json = requerimentoConvalidacaoJQGridHandler.loadData(request).getJson();
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
        Usuario usuario = ControllersUtil.getLoggedUser();

        requerimento.setStatus(StatusRequerimentoEnum.EM_ABERTO);
        requerimento.setUsuario(usuario);

        if (!Objects.isNull(requerimento.getDisciplinas()) && !requerimento.getDisciplinas().isEmpty()) {
            if (Objects.nonNull(requerimento.getId()) && requerimento.getId() > 0) {
                requerimentoDisciplinaRepository.deleteByRequerimentoId(requerimento.getId());
            }
            requerimento.getDisciplinas().forEach(d -> d.setRequerimento(requerimento));
        } else if (!Objects.isNull(requerimento.getConvalidacoes()) && !requerimento.getConvalidacoes().isEmpty()) {
            if (Objects.nonNull(requerimento.getId()) && requerimento.getId() > 0) {
                requerimentoConvalidacaoRepository.deleteByRequerimentoId(requerimento.getId());
            }
            requerimento.getConvalidacoes().forEach(c -> c.setRequerimento(requerimento));
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

    /*@Secured("ROLE_ALUNO")
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String excluir(@PathVariable Long id) {
        JSONObject retorno = new JSONObject();
        try{
            Requerimento requerimento = requerimentoRepository.findById(id).orElse(null);
            if (Objects.nonNull(requerimento)) {
                if (requerimento.getUsuario().equals(ControllersUtil.getLoggedUser()) && requerimento.getStatus().equals(StatusRequerimentoEnum.EM_ABERTO)) {
                    requerimentoRepository.deleteById(id);
                    retorno.put("state", "OK");
                    retorno.put("message", "Registro removido com sucesso!");
                } else {
                    retorno.put("state", "ERROR");
                    retorno.put("message", "Você não possui permissão para excluir o Requerimento!");
                }
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Falha ao remover registro!\nRequerimento inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao remover registro!\n" + ex.getCause().getCause().getMessage());
        }
        return retorno.toString();
    }*/

    @Secured("ROLE_ALUNO")
    @PutMapping("/cancel/{id}")
    @ResponseBody
    public String cancelar(@PathVariable Long id) {
        JSONObject retorno = new JSONObject();
        try{
            Requerimento requerimento = requerimentoRepository.findById(id).orElse(null);
            if (Objects.nonNull(requerimento)) {
                if (requerimento.getUsuario().equals(ControllersUtil.getLoggedUser()) && requerimento.getStatus().equals(StatusRequerimentoEnum.EM_ABERTO)) {
                    requerimento.setStatus(StatusRequerimentoEnum.CANCELADO);
                    requerimentoRepository.save(requerimento);
                    retorno.put("state", "OK");
                    retorno.put("message", "Requerimento cancelado com sucesso!");
                } else {
                    retorno.put("state", "ERROR");
                    if (requerimento.getStatus().equals(StatusRequerimentoEnum.CANCELADO)) {
                        retorno.put("message", "O Requerimento já está Cancelado!");
                    } else {
                        retorno.put("message", "Você não possui permissão para cancelar o Requerimento!");
                    }
                }
            } else {
                retorno.put("state", "ERROR");
                retorno.put("message", "Falha ao cancelar registro!\nRequerimento inexistente!");
            }
        }catch (Exception ex){
            retorno.put("state", "ERROR");
            retorno.put("message", "Falha ao cancelar registro!\n" + ex.getCause().getCause().getMessage());
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

    @Secured("ROLE_DERAC")
    @GetMapping(value = "/findToDerac")
    @ResponseBody
    public List<Requerimento> findByDerac() {
        List<Requerimento> requerimentos = requerimentoRepository.findAll(
                Specification.where(RequerimentoSpecification.withStatus(StatusRequerimentoEnum.EM_ABERTO))
                .or(RequerimentoSpecification.withStatus(StatusRequerimentoEnum.DEVOLVIDO_DERAC))
        );
        return requerimentos;
    }

    @Secured("ROLE_ALUNO")
    @GetMapping(value = "/findToAluno")
    @ResponseBody
    public List<Requerimento> findToAluno() {
        return requerimentoRepository.findAll(
                Specification.where(RequerimentoSpecification.withUsuarioId(ControllersUtil.getLoggedUser().getId()))
                    .and(
                            Specification.where(RequerimentoSpecification.withStatus(StatusRequerimentoEnum.EM_ABERTO))
//                            .or(RequerimentoSpecification.withDeferido(Boolean.FALSE)) TODO implementar
                            )
        );
    }

    @Secured("ROLE_COORDENACAO")
    @GetMapping(value = "/findToCoordenacao")
    @ResponseBody
    public List<Requerimento> findToCoordenacao() {
        List<Requerimento> requerimentos = requerimentoRepository.findAllToCoordenacao(ControllersUtil.getLoggedUser().getId(),
                StatusRequerimentoEnum.AGUARDANDO_COORDENACAO);
        return requerimentos;
    }

    @Secured("ROLE_PROFESSOR")
    @GetMapping(value = "/findToProfessor")
    @ResponseBody
    public List<Requerimento> findToProfessor() {
        List<Requerimento> requerimentos = requerimentoRepository.findAllToProfessor(ControllersUtil.getLoggedUser().getId());
        return requerimentos;
    }

    @GetMapping(value = "/findById")
    @ResponseBody
    public Requerimento findById(HttpServletRequest request) {
        Long id = Long.valueOf(request.getParameter("id"));
        Requerimento requerimento = requerimentoRepository.findById(id).get();
        return requerimento;
    }
}
