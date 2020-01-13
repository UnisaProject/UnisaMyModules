//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.studentoffer.actions;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import za.ac.unisa.lms.tools.studentoffer.forms.Student;

public class GeneralMethods {


	@SuppressWarnings("unused")
	public String validateDate(String testDate){
		String errorMessage = "";

		// ------ Check date format
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			formatter.setLenient(false);
			Date test = formatter.parse(testDate);
		}catch (ParseException e){
				errorMessage="Please enter a valid date of birth.";
				return errorMessage;
		}
		// ----- check for numeric
        //check month
		if (!isNumeric(testDate.substring(0,4))){
			errorMessage="Date of birth must be numeric.";
			return errorMessage;
		}
		//check month
		if (!isNumeric(testDate.substring(4,6))){
			errorMessage="Date of birth must be numeric.";
			return errorMessage;
		}
		//check day
		if (!isNumeric(testDate.substring(6,8))){
			errorMessage="Date of birth must be numeric.";
			return errorMessage;
		}

		//	Can not be future date
		Calendar currentDate = Calendar.getInstance();
		Calendar inputDate = Calendar.getInstance();
		int year = Integer.parseInt(testDate.substring(0,4));
		int month = Integer.parseInt(testDate.substring(4,6));
		int day = Integer.parseInt(testDate.substring(6,8));
		inputDate.set(year,month-1,day,0,0);
		currentDate.set(currentDate.get(Calendar.YEAR),currentDate.get(Calendar.MONTH),currentDate.get(Calendar.DATE),0,0);
		int result = inputDate.compareTo(currentDate);
		if (result<0 || result==0) {
		}else{
			errorMessage="Date of birth can not be a future date.";
		}

		return errorMessage;
	}

	public String validateDateOfBirth(Student student){

		String errorMessage ="";

		if (student.getSurname()==null || "".equals(student.getSurname().trim())
			|| student.getFirstnames()==null || "".equals(student.getFirstnames().trim())
			|| student.getBirthYear()==null || "".equals(student.getBirthYear().trim())
			|| student.getBirthMonth()==null || "".equals(student.getBirthMonth().trim())
			|| student.getBirthDay()==null || "".equals(student.getBirthDay().trim())){
			errorMessage = "Enter the date of birth.";
		}
		// add zero to month and day if needed
		student.setBirthMonth(addZeroToDate(student.getBirthMonth()));
		student.setBirthDay(addZeroToDate(student.getBirthDay()));
		errorMessage = validateDate(student.getBirthYear()+student.getBirthMonth()+student.getBirthDay());

		return errorMessage;
	}

	public boolean isNumeric(String testString){

		boolean result = true;

		try{
			Long.parseLong(testString);
		} catch (NumberFormatException e) {
			result = false;
		}

		return result;
	}

	private String addZeroToDate(String partOfDate){
		String returnDate = partOfDate;

		if(partOfDate.length()==1){
			returnDate = "0" + partOfDate;
		}

		return returnDate;
	}

}

