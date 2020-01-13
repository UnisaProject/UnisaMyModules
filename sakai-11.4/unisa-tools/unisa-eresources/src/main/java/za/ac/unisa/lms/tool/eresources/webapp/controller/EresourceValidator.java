package za.ac.unisa.lms.tool.eresources.webapp.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import za.ac.unisa.lms.tool.eresources.model.Eresource;

public class EresourceValidator implements Validator{

	public EresourceValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> clazz) {
		//just validate the User instances
		return Eresource.class.isAssignableFrom(clazz);
	}
 
	//validate page 1, Basic Eresouces Data Text Areas
	public void validatePage1Form(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceName",
		        "required.resourceName", "Field name is required.");
	}
 
	//validate page 2, Adding Placements into Eresource Record
	public void validatePage2Form(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceName",
			"required.resourceName", "Field name is required.");
	}
 
	//validate page 3, Adding Subject to Eresource Record
	public void validatePage3Form(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceName",
			"required.resourceName", "Field name is required.");
	}
 
	//validate page 4, Confirm Record + Selecting Trial Database Fields
		public void validatePage4Form(Object target, Errors errors) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resourceName",
				"required.resourceName", "Field name is required.");
		}
	 

	@Override
	public void validate(Object target, Errors errors) {
		validatePage1Form(target, errors);
		validatePage2Form(target, errors);
		validatePage3Form(target, errors);
		validatePage4Form(target, errors);
	}

}
