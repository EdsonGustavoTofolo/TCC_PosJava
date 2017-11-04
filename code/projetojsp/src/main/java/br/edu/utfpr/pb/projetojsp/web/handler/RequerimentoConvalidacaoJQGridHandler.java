package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.model.RequerimentoConvalidacao;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoConvalidacaoRepository;
import br.edu.utfpr.pb.projetojsp.specification.RequerimentoCommonSpecification;
import br.edu.utfpr.pb.projetojsp.web.exclusionStrategyGson.RequerimentoCommonExclusionStrategy;
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
 * Created by Edson on 04/11/2017.
 */
@Component
public class RequerimentoConvalidacaoJQGridHandler extends JQGridHandler<RequerimentoConvalidacao> {
    @Autowired
    private RequerimentoConvalidacaoRepository repository;

    private Long requerimentoId;

    @Override
    public List<RequerimentoConvalidacao> findData(HttpServletRequest request, boolean isSearching, PageRequest pageRequest) {
        String requerimentoIdParam = request.getParameter("requerimentoId");
        this.requerimentoId = Long.valueOf(requerimentoIdParam);
        Page<RequerimentoConvalidacao> page = repository.findAll(RequerimentoCommonSpecification.withRequerimentoId(this.requerimentoId), pageRequest);
        List<RequerimentoConvalidacao> list = page.getContent();
        return list;
    }

    @Override
    public long getTotalRecords() {
        return repository.count(RequerimentoCommonSpecification.withRequerimentoId(this.requerimentoId));
    }

    @Override
    public String getJson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
                .serializeNulls()
                .setExclusionStrategies(new RequerimentoCommonExclusionStrategy())
                .create();
        return JsonUtil.toJsonObj(gson, this.getData());
    }
}
