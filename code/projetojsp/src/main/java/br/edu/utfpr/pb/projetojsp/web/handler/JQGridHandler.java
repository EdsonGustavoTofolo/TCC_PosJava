package br.edu.utfpr.pb.projetojsp.web.handler;

import br.edu.utfpr.pb.projetojsp.web.model.JQGrid;
import br.edu.utfpr.pb.projetojsp.web.util.JsonUtil;
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

	private JQGrid<T> jqGridData;

	public JQGridHandler() {
		this.jqGridData = new JQGrid<T>();
	}

	/**
	 * This method will fetch the super hero list. Of course i have mixed and
	 * matched DC and Marvel in order to keep peace on the universe.
	 * 
	 * @return
	 */
	public JQGridHandler<T> loadData(final HttpServletRequest req, JpaRepository repository) {
		int pageRequestParam = Integer.valueOf(req.getParameter("page")).intValue();
		int pageSizeRequestParam = Integer.valueOf(req.getParameter("rows")).intValue();
		String directionRequestParam = req.getParameter("sord");
		String columnRequestParam = req.getParameter("sidx");

//		int startIndex = page == 1 ? 0 : (pageSize * (page - 1));
//		int endIndex = page == 1 ? pageSize : pageSize * page;
		int startPage = pageRequestParam == 1 ? 0 : (pageRequestParam - 1);
		String column = columnRequestParam.isEmpty() ? "id" : columnRequestParam;

		PageRequest pageRequest = PageRequest.of(startPage, pageSizeRequestParam,
				Sort.Direction.fromString(directionRequestParam.toUpperCase()), column);

		Page<T> page = repository.findAll(pageRequest);
		List<T> list = page.getContent();

		long total = repository.count();

		this.jqGridData.setPage(pageRequestParam);
		this.jqGridData.setTotal(String.valueOf(Math.ceil((double) total / pageSizeRequestParam)));
		this.jqGridData.setRecords(String.valueOf(total));
		this.jqGridData.setRows(list);

		return this;
	}

	public JQGrid<T> getData() {
		return this.jqGridData;
	}

	public String getJson() {
		return JsonUtil.toJsonObj(this.jqGridData);
	}
}
