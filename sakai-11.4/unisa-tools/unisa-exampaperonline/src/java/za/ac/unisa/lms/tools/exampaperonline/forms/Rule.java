package za.ac.unisa.lms.tools.exampaperonline.forms;

public class Rule {
	private String approvalRequired;
	private String rejectionAllowed;
	private String approvalAllowed;
	private String docsShown;
	
	public String getApprovalRequired() {
		return approvalRequired;
	}
	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}
	public String getRejectionAllowed() {
		return rejectionAllowed;
	}
	public void setRejectionAllowed(String rejectionAllowed) {
		this.rejectionAllowed = rejectionAllowed;
	}
	public String getApprovalAllowed() {
		return approvalAllowed;
	}
	public void setApprovalAllowed(String approvalAllowed) {
		this.approvalAllowed = approvalAllowed;
	}
	public String getDocsShown() {
		return docsShown;
	}
	public void setDocsShown(String docsShown) {
		this.docsShown = docsShown;
	}	
}

