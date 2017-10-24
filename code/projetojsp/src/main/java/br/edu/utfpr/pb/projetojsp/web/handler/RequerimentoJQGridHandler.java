package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.enumeration.StatusRequerimentoEnum;
import br.edu.utfpr.pb.projetojsp.model.Permissao;
import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.model.Usuario;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoRepository;
import br.edu.utfpr.pb.projetojsp.repository.repositoryImpl.RequerimentoRepositoryImpl;
import br.edu.utfpr.pb.projetojsp.specification.RequerimentoSpecification;
import br.edu.utfpr.pb.projetojsp.web.exclusionStrategyGson.RequerimentoExclusionStrategy;
import br.edu.utfpr.pb.projetojsp.web.util.ControllersUtil;
import br.edu.utfpr.pb.projetojsp.web.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Edson on 26/09/2017.
 */
@Component
public class RequerimentoJQGridHandler extends JQGridHandler<Requerimento> {

    @Autowired
    private RequerimentoRepository repository;
    @Autowired
    private RequerimentoRepositoryImpl repositoryImpl;

    @Override
    public List<Requerimento> findData(HttpServletRequest request, boolean isSearching, PageRequest pageRequest) {
        Page<Requerimento> page = null;
        if (isSearching) {
            Long id = null;
            Date data = null;
            String observacao = null;
            Integer motivo = null;
            String aluno = null;
            StatusRequerimentoEnum status = null;

            if (Objects.nonNull(request.getParameter("id")) && !"".equals(request.getParameter("id"))) {
                id = Long.valueOf(request.getParameter("id"));
            }

            if (Objects.nonNull(request.getParameter("status")) && !"".equals(request.getParameter("status"))) {
                status = StatusRequerimentoEnum.values()[Integer.valueOf(request.getParameter("status")) - 1];
            }

            if (Objects.nonNull(request.getParameter("aluno")) && !"".equals(request.getParameter("aluno"))) {
                aluno = request.getParameter("aluno");
            }

            if (Objects.nonNull(request.getParameter("motivo")) && !"".equals(request.getParameter("motivo"))) {
                motivo = Integer.valueOf(request.getParameter("motivo"));
            }

            if (Objects.nonNull(request.getParameter("data")) && !"".equals(request.getParameter("data"))) {
                String dataStr = request.getParameter("data");
                try {
                    data = DateUtils.parseDate(dataStr, new String[] {"dd/MM/yyyy"});
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (Objects.nonNull(request.getParameter("observacao")) && !"".equals(request.getParameter("observacao"))) {
                observacao = request.getParameter("observacao");
            }

            if (ControllersUtil.hasLoggedUserRole("ROLE_COORDENACAO")) {
                page = repositoryImpl.findAllToCoordenacao(getCommonSpecification()
                        .and(RequerimentoSpecification.withAlunoNome(aluno))
                        .and(RequerimentoSpecification.withId(id))
                        .and(RequerimentoSpecification.withData(data))
                        .and(RequerimentoSpecification.withObservacao(observacao))
                        .and(RequerimentoSpecification.withMotivo(motivo))
                        .and(RequerimentoSpecification.withStatus(status)), pageRequest);
            } else {
                page = repository.findAll(getCommonSpecification()
                        .and(RequerimentoSpecification.withAlunoNome(aluno))
                        .and(RequerimentoSpecification.withId(id))
                        .and(RequerimentoSpecification.withData(data))
                        .and(RequerimentoSpecification.withObservacao(observacao))
                        .and(RequerimentoSpecification.withMotivo(motivo))
                        .and(RequerimentoSpecification.withStatus(status)), pageRequest);
            }
        } else {
            if (ControllersUtil.hasLoggedUserRole("ROLE_COORDENACAO")) {
                page = repositoryImpl.findAllToCoordenacao(getCommonSpecification(), pageRequest);
            } else {
                page = repository.findAll(getCommonSpecification(), pageRequest);
            }
        }
		List<Requerimento> list = page.getContent();
        return list;
    }

    @Override
    public String getJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .serializeNulls()
                .setExclusionStrategies(new RequerimentoExclusionStrategy())
                .create();
        return JsonUtil.toJsonObj(gson, this.getData());
    }

    @Override
    public long getTotalRecords() {
        return repository.count(getCommonSpecification());
    }

    private Specification<Requerimento> getCommonSpecification() {
        Long alunoId = null;
        Long professorId = null;
        Long coordenadorId = null;

        Usuario usuario = ControllersUtil.getLoggedUser();
        for (Permissao permissao : usuario.getPermissoes()) {
            if (permissao.getPermissao().equals(Permissao.ROLE_ALUNO)) {//se for do tipo aluno, retorna somente os requerimentos dele
                alunoId = usuario.getId();
                break;
            } else if (permissao.getPermissao().equals(Permissao.ROLE_PROFESSOR)) {
                professorId = usuario.getId();
                break;
            } else if (permissao.getPermissao().equals(Permissao.ROLE_COORDENACAO)) {
                coordenadorId = usuario.getId();
                break;
            }
        }
        return Specification.where(RequerimentoSpecification.withUsuarioId(alunoId))
                .and(RequerimentoSpecification.withProfessorId(professorId))
                .and(RequerimentoSpecification.withCoordenacaoId(coordenadorId));
    }
}
