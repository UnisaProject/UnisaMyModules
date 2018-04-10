package za.ac.unisa.lms.tools.monitor.forms;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
