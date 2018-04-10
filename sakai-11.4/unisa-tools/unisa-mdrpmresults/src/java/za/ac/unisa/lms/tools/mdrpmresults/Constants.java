package za.ac.unisa.lms.tools.mdrpmresults;

public class Constants {
	
	//Yes or No codes
	public static final String YES_CODE = "Y";
	public static final String NO_CODE = "N";
	
	//RPM status results description
	public static final String COMPLIED_RESULTS_DESC = "Complied with requirements";
	public static final String NOT_COMPLIED_RESULTS_DESC = "Does not comply - may re-register" ;
	public static final String FAILED_RESULTS_DESC = "Failed, does not comply - may not re-register";
	public static final String NOACTIVITY_RESULTS_DESC = "No Activity - may not re-register" ;	
	//RPM status results codes
	public static final String COMPLIED_RESULTS_CODE = "CR";
	public static final String NOT_COMPLIED_RESULTS_CODE = "NC" ;
	public static final String FAILED_RESULTS_CODE = "DF";
	public static final String NOACTIVITY_RESULTS_CODE = "NA";
	//RPM final sign-off status results codes
	public static final String COMPLIED_FINAL_SIGNOFF_CODE = "FC";
	public static final String NOT_COMPLIED_FINAL_SIGNOFF_CODE = "FN" ;
	public static final String FAILED_FINAL_SIGNOFF_CODE = "FF";
	public static final String NOACTIVITY_FINAL_SIGNOFF_CODE = "FA";
	//Final sign-oof or review codes
	public static final String REVIEW_CODE = "RW";
	public static final String FINAL_SIGNOFF_CODE = "FINAL";
	//Final sign-oof or review code descriptions
	public static final String REVIEW_DESC = "Review";
	public static final String FINAL_SIGNOFF_DESC = "Final Sign-off";
	
	//Flags
	public static final String FINAL_FLAG = "F";
	public static final String RPM_FLAG = "P";
	public static final String QSPROUT_FINAL_FLAG_1 = "1";
	public static final String QSPROUT_FINAL_FLAG_F = "F";
	
	//Status codes
	public static final String REGISTERED_STATUS_CODE = "RG";
	
	//Address category codes
	public static final String FK_ADRCATCODE_STU = "1";
	
	//String constants
	public static final String EMPTY_STRING = "";
	
	//Actions
	public static final String ENTER_STUDENT_KEY =  "enterstudent";
	public static final String DISPLAY_KEY =  "display";
	public static final String BUTTON_SIGNOFF_KEY =  "button.signoff";
	public static final String BUTTON_CANCEL_KEY =  "button.cancel";
	
	public static final String ENTER_STUDENT_VALUE =  "enterstudent";
	public static final String DISPLAY_VALUE =  "display";
	public static final String BUTTON_SIGNOFF_VALUE =  "signoff";
	public static final String BUTTON_CANCEL_VALUE =  "cancel";
	
	
	//Mapping names
	
	//M&D Activities
	public static final String MD_ACTIVITY_10 = "10";
	public static final String MD_ACTIVITY_9 = "9";
	
	//Velocity configuration properties
	public static final String RESOURCE_LOADER = "resource.loader";
	public static final String RESOURCE_LOADER_NAME = "classpath";
	public static final String RESOURCE_LOADER_CLASSPATH_CLASS = "classpath.resource.loader.class";
	public static final String RUNTIME_LOG_LOGSYSTEM_CLASS_VALUE = "org.apache.velocity.runtime.log.Log4JLogChute";
	public static final String RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER = "runtime.log.logsystem.log4j.logger";
	public static final String RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER_NAME = "displayMdAdmissionAction_velocity";
	
	
	
}
