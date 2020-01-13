//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.9.210/xslt/JavaClass.xsl

package za.ac.unisa.lms.tools.creditcardpayment.forms;
import za.ac.unisa.lms.tools.creditcardpayment.utils.EmailValidator;

/** 
 * MyEclipse Struts
 * Creation date: 08-18-2005
 * 
 * XDoclet definition:
 */
public class Student {

	// --------------------------------------------------------- Instance Variables

	private String studentNumber;
	private String title;
	private String name;
	private String initials;
	private String emailAddress;
	

	// --------------------------------------------------------- Methods

	public String getTitle() {
		return title;
	}

		public void setTitle(String title) {
		this.title = title;
	}

		public String getName() {
		return name;
	}

    public void setName(String name) {
		this.name = name;
	}
	public String getInitials() {
		return initials;
	}

		public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public  String  validateStudentData(){
		               String stuNumErrMsg=checkStudentNr(getStudentNumber());
		               if(!stuNumErrMsg.isEmpty()){
			                 return stuNumErrMsg;
		               }
		               // email addr
		               String emailErrorMsg=validateEmail(getEmailAddress());
		               if(!emailErrorMsg.equals("")){
			                return emailErrorMsg;
		               }
		               return "";
	}
	private  String validateEmail(String emailAdrress){
     	          if( emailAdrress== null || "".equals(emailAdrress.trim())){
                          return  "E-mail address cannot be empty.";
                  }
                  EmailValidator  emValidator=new EmailValidator();
                  boolean valid = emValidator.validate(emailAdrress);
                  if(!valid){			
                           return "E-mail address invalid.";
                  }
              return "";
   }
	public String  checkStudentNr(String studentNr){
		
		
		if (studentNr == null || "".equalsIgnoreCase(studentNr.trim())){
			return  "Enter a valid student or application reference number.";			
		}

		if (studentNr.trim().length() < 7 ){
			return "Student or application reference number must be at least 7 characters.";
		}
		if (studentNr.trim().length() >8 ){
			return "Student or application reference number must not be more than 8 characters.";
		}
		/* stud number must be numeric */
		try {
			Long.parseLong(studentNr);
		} catch (NumberFormatException e) {
				return "Student or application reference number must be numeric.";
		}
		return "";
	}
	

}

