package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoRepository;
import br.edu.utfpr.pb.projetojsp.specification.RequerimentoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
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

    @Override
    public List<Requerimento> findData(HttpServletRequest request, boolean isSearching, PageRequest pageRequest) {
        Page<Requerimento> page = null;
        if (isSearching) {
            Long id = null;
            Date data = null;
            String observacao = null;
            Integer motivo = null;

            if (Objects.nonNull(request.getParameter("id")) && !"".equals(request.getParameter("id"))) {
                id = Long.valueOf(request.getParameter("id"));
            }

            if (Objects.nonNull(request.getParameter("motivo")) && !"".equals(request.getParameter("motivo"))) {
                motivo = Integer.valueOf(request.getParameter("motivo"));
            }

            if (Objects.nonNull(request.getParameter("data")) && !"".equals(request.getParameter("data"))) {
                String dataStr = request.getParameter("data");
                DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                        .parseStrict()
                        .appendPattern("dd/MM/uuuu")
                        .toFormatter()
                        .withResolverStyle(ResolverStyle.STRICT);
                LocalDate localDate = LocalDate.parse(dataStr, formatter);
                data = java.sql.Date.valueOf(localDate);
            }

            if (Objects.nonNull(request.getParameter("observacao")) && !"".equals(request.getParameter("observacao"))) {
                observacao = request.getParameter("observacao");
            }
            page = repository.findAll(Specification.where(RequerimentoSpecification.withId(id))
                            .and(RequerimentoSpecification.withData(data))
                            .and(RequerimentoSpecification.withObservacao(observacao))
                            .and(RequerimentoSpecification.withMotivo(motivo)), pageRequest);
        } else {
            page = repository.findAll(pageRequest);
        }
		List<Requerimento> list = page.getContent();
        return list;
    }

    @Override
    public long getTotalRecords() {
        return repository.count();
    }
}
