package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.model.Requerimento;
import br.edu.utfpr.pb.projetojsp.web.model.JQGrid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The handler class used to fetch the data required.
 * 
 * @author Dinuka Arseculeratne
 *
 */
public class JQGridHandler {
	/**
	 * This method will fetch the super hero list. Of course i have mixed and
	 * matched DC and Marvel in order to keep peace on the universe.
	 * 
	 * @return
	 */
	public JQGrid<Requerimento> loadSuperHeroes(final HttpServletRequest req, JpaRepository repository) {
		int page = Integer.valueOf(req.getParameter("page")).intValue();
		int pageSize = Integer.valueOf(req.getParameter("rows")).intValue();

//		int startIndex = page == 1 ? 0 : (pageSize * (page - 1));
//		int endIndex = page == 1 ? pageSize : pageSize * page;
		int startIndex = page == 1 ? 0 : (page - 1);
		int endIndex = pageSize;

		PageRequest pageRequest = PageRequest.of(startIndex, endIndex, Sort.Direction.ASC, "id");
		Page<Requerimento> pg = repository.findAll(pageRequest);
		List<Requerimento> requerimentoList = pg.getContent();

		long total = repository.count();

		JQGrid<Requerimento> jqGridData = new JQGrid<Requerimento>();
		jqGridData.setPage(page);
		jqGridData.setTotal(String.valueOf(Math.ceil((double) total / pageSize)));
		jqGridData.setRecords(String.valueOf(total));
		jqGridData.setRows(requerimentoList);
		return jqGridData;
	}
}
