package za.ac.unisa.lms.tools.mdadmission;

public class Constants {

	//Wild card
	public static final String WILD_CARD = "%";
	
	//Admission status
	public static final String ADMISSION_STATUS_REFERAL = "R";
	public static final String ADMISSION_STATUS_APPEAL = "A";
	public static final String MDTRAC_STATUS_REFERAL = "RS";
	public static final String MDTRAC_STATUS_APPEAL = "AS";
	
	//Empty string
	public static final String EMPTY_STRING = "";
	
	//Address category codes
	public static final String FK_ADRCATCODE_STU = "1";
	
	// User type
	public static String USER_TYPE_LECTURER = "lecturer";

	// Action display pages
	public static final String DEFAULT_PAGE = "default";
	
	// Qualifaction and Specialty routing flags
	public static final String QSPROUT_FINAL_FLAG_1 = "1";
	public static final String QSPROUT_FINAL_FLAG_F = "F";
	
	public static final String ZERO_VALUE = "0";
	
	//Action names
    public static final String BUTTON_CANCEL_ACTION = "button.cancel";
	public static final String BUTTON_DISPLAY_ACTION = "button.display";
	public static final String BUTTON_SIGNOFF_ACTION = "button.signoff";
	public static final String DISPLAY_ACTION = "display";
    public static final String BUTTON_LIST_ACTION = "button.list";
    public static final String BUTTON_CONTINUE_ACTION ="button.continue";
	public static final String INPUTSTEP_1_ACTION ="inputstep1";
	public static final String BUTTON_EXTERNAL_ACTION ="button.external";
	public static final String GET_FILE_ACTION ="getFile";
		
	//Action methods	
    public static final String BUTTON_CANCEL_ACTION_METHOD = "cancel";
	public static final String BUTTON_DISPLAY_ACTION_METHOD = "display";
	public static final String BUTTON_SIGNOFF_ACTION_METHOD = "signoff";
	public static final String DISPLAY_ACTION_METHOD = "display";
    public static final String BUTTON_LIST_ACTION_METHOD = "listStaff";
    public static final String BUTTON_CONTINUE_ACTION_METHOD ="staffSelected";
	public static final String INPUTSTEP_1_ACTION_METHOD ="inputstep1";
	public static final String BUTTON_EXTERNAL_ACTION_METHOD ="listExternal";
	public static final String GET_FILE_ACTION_METHOD ="getFile";
	
	//Velocity configuration properties
	public static final String RESOURCE_LOADER = "resource.loader";
	public static final String RESOURCE_LOADER_NAME = "classpath";
	public static final String RESOURCE_LOADER_CLASSPATH_CLASS = "classpath.resource.loader.class";
	public static final String RUNTIME_LOG_LOGSYSTEM_CLASS_VALUE = "org.apache.velocity.runtime.log.Log4JLogChute";
	public static final String RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER = "runtime.log.logsystem.log4j.logger";
	public static final String RUNTIME_LOG_LOGGER_LOGSYSTEM_LOG4J_LOGGER_NAME = "displayMdAdmissionAction_velocity";
}
