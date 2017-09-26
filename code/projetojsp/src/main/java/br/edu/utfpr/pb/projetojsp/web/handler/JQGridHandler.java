package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.web.model.JQGrid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * The handler class used to fetch the data required.
 * 
 * @author Dinuka Arseculeratne
 *
 */
public class JQGridHandler<T extends Serializable> {
	/**
	 * This method will fetch the super hero list. Of course i have mixed and
	 * matched DC and Marvel in order to keep peace on the universe.
	 * 
	 * @return
	 */
	public JQGrid<T> loadData(final HttpServletRequest req, JpaRepository repository) {
		int pageRequestParam = Integer.valueOf(req.getParameter("page")).intValue();
		int pageSizeRequestParam = Integer.valueOf(req.getParameter("rows")).intValue();

//		int startIndex = page == 1 ? 0 : (pageSize * (page - 1));
//		int endIndex = page == 1 ? pageSize : pageSize * page;
		int startPage = pageRequestParam == 1 ? 0 : (pageRequestParam - 1);

		PageRequest pageRequest = PageRequest.of(startPage, pageSizeRequestParam, Sort.Direction.ASC, "id");
		Page<T> page = repository.findAll(pageRequest);
		List<T> list = page.getContent();

		long total = repository.count();

		JQGrid<T> jqGridData = new JQGrid<T>();
		jqGridData.setPage(pageRequestParam);
		jqGridData.setTotal(String.valueOf(Math.ceil((double) total / pageSizeRequestParam)));
		jqGridData.setRecords(String.valueOf(total));
		jqGridData.setRows(list);
		return jqGridData;
	}
}
