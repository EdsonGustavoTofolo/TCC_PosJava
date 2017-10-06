package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoDisciplina;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoDisciplinaRepository;
import br.edu.utfpr.pb.projetojsp.specification.RequerimentoDisciplinaSpecification;
import br.edu.utfpr.pb.projetojsp.web.exclusionStrategyGson.RequerimentoDisciplinaExclusionStrategy;
import br.edu.utfpr.pb.projetojsp.web.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Edson on 05/10/2017.
 */
@Component
public class RequerimentoDisciplinaJQGridHandler extends JQGridHandler<RequerimentoDisciplina> {

    @Autowired
    private RequerimentoDisciplinaRepository repository;

    private Long requerimentoId;

    @Override
    public List<RequerimentoDisciplina> findData(HttpServletRequest request, boolean isSearching, PageRequest pageRequest) {
        Page<RequerimentoDisciplina> page = null;

        String requerimentoIdParam = request.getParameter("requerimentoId");
        this.requerimentoId = Long.valueOf(requerimentoIdParam);

        page = repository.findAll(RequerimentoDisciplinaSpecification.withRequerimentoId(this.requerimentoId), pageRequest);

        List<RequerimentoDisciplina> list = page.getContent();
        return list;
    }

    @Override
    public long getTotalRecords() {
        return repository.count(RequerimentoDisciplinaSpecification.withRequerimentoId(this.requerimentoId));
    }

    @Override
    public String getJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .serializeNulls()
                .setExclusionStrategies(new RequerimentoDisciplinaExclusionStrategy())
                .create();
        return JsonUtil.toJsonObj(gson, this.getData());
    }
}
