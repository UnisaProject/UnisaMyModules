package za.ac.unisa.lms.tools.monitor.forms;

import org.apache.struts.action.ActionForm;

public class QueryForm extends ActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String query;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
