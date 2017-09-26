package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.repository.RequerimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Edson on 26/09/2017.
 */
@Service
public class RequerimentoJQGridHandler extends JQGridHandler<Requerimento> {

    @Autowired
    private RequerimentoRepository repository;

    @Override
    public List<Requerimento> findData(HttpServletRequest request, boolean isSearching, PageRequest pageRequest) {
        if (isSearching) {
            //TODO
        }
        Page<Requerimento> page = repository.findAll(pageRequest);
		List<Requerimento> list = page.getContent();
        return list;
    }

    @Override
    public long getTotalRecords() {
        return repository.count();
    }
}
