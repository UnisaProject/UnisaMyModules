package za.ac.unisa.lms.tools.studyquotation.forms;

import org.apache.struts.validator.ValidatorForm;

import za.ac.unisa.lms.studyquotation.StudyQuotation;

public class StudyQuotationForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;
	private StudyQuotation studyQuotation;

	public StudyQuotation getStudyQuotation() {
		return studyQuotation;
	}
	public void setStudyQuotation(StudyQuotation studyQuotation) {
		this.studyQuotation = studyQuotation;
	}
}