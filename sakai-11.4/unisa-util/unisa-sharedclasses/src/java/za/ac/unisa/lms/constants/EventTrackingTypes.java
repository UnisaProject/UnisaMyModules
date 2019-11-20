package za.ac.unisa.lms.constants;

import za.ac.unisa.lms.constants.EventTrackingTypes;

public class EventTrackingTypes {
	//NB Event string values may not be longer than 32 characters
	public static String EVENT_ACADHISTORY_VIEW = "acadhistory.view";
	public static String EVENT_ACADLIST_VIEW = "acadlist.view";
	public static String EVENT_ASSESSCRIT_AUTH_AUTHORISE="assessCriteriaAuth.authorise";
	public static String EVENT_ASSESSCRIT_AUTH_REJECT="assessCriteriaAuth.reject";	
	public static String EVENT_ASSESSCRIT_AUTH_VIEW="assessCriteriaAuth.view";	
	public static String EVENT_ASSESSCRIT_AUTH_VIEWLIST="assessCriteriaAuth.viewList";	
	public static String EVENT_ASSESSMENT_CRITERIA_ASS_ADD ="assessmentCriteria.assAdd";
	public static String EVENT_ASSESSMENT_CRITERIA_ASS_DELETE ="assessmentCriteria.assDelete";
	public static String EVENT_ASSESSMENT_CRITERIA_ASS_UPDATE ="assessmentCriteria.assUpdate";
	public static String EVENT_ASSESSMENT_CRITERIA_CANCEL_REQUEST ="assessmentCriteria.cancelReq";
	public static String EVENT_ASSESSMENT_CRITERIA_FINMRK_UPDATE ="assessmentCriteria.finmrkUpdate";
	public static String EVENT_ASSESSMENT_CRITERIA_REQUEST_AUTH ="assessmentCriteria.requestAuth";
	public static String EVENT_ASSESSMENT_CRITERIA_VIEW ="assessmentCriteria.view";
	public static String EVENT_AUDIT_VIEW = "audit.view";
	public static String EVENT_BIODETAILS_VIEW = "biodetails.view";
	public static String EVENT_BIODETAILS_ADDRESSCHANGE = "biodetails.addresschange";
	public static String EVENT_BIODETAILS_ADDRESSCHANGEWORKFLOW = "biodetails.addresschangeworkflow";
	public static String EVENT_BIODETAILS_CONTACTDETAILSCHANGE = "biodetails.contactdetailschange";
	public static String EVENT_BIODETAILS_EXAMCENTRECHANGE = "biodetails.examcentrechange";
	public static String EVENT_BIODETAILS_CORRESPONDENCECHANGE = "biodetails.correspondencechange";
	public static String EVENT_BIODETAILS_EMAILVERIFIED = "biodetails.emailverified";
	public static String EVENT_BULKEMAIL_VIEW = "bulkemail.view";
	public static String EVENT_BULKEMAIL_SEND = "bulkemail.send";
	public static String EVENT_CALENDAR_VIEW = "calendar.view";
	public static String EVENT_CALENDAR_ERROR = "calendar.error";
	public static String EVENT_CANCELUSER_SUCCESS = "cancel.user";
	public static String EVENT_CANCELUSER_ERROR = "canceluser.error";
	public static String EVENT_CHANGEPASSWORD_VIEW = "changepassword.view";
	public static String EVENT_CHANGEPASSWORD_CHANGE = "changepassword.change";
	public static String EVENT_CLASSLIST_VIEW = "classlist.view";
	public static String EVENT_CLASSLIST_UPDATE = "classlist.update";
	public static String EVENT_CONTACTUS_EMAILDEPARTMENT = "contactus.emaildepartment";
	public static String EVENT_CONTACTUS_DISPLAYNUMBER = "contactus.displaystudentnumber";
	public static String EVENT_CRONJOBS_START = "cronjobs.start";
	public static String EVENT_CRONJOBS_STOP = "cronjobs.stop";
	public static String EVENT_DOWNTIMESCHEDULER_VIEW = "downtimescheduler.view";
	public static String EVENT_DOWNTIMESCHEDULER_ADD = "downtimescheduler.add";
	public static String EVENT_DOWNTIMESCHEDULER_EDIT = "downtimescheduler.edit";
	public static String EVENT_DOWNTIMESCHEDULER_DELETE = "downtimescheduler.delete";
	public static String EVENT_EBOOKSHOP_VIEW = "ebookshop.view";
	public static String EVENT_EBOOKSHOP_UPDATE = "ebookshop.update";
	public static String EVENT_EBOOKSHOP_DELETE = "ebookshop.delete";
	public static String EVENT_EBOOKSHOP_INSERT = "ebookshop.insert";
	public static String EVENT_FAQS_VIEW = "faqs.view";
	public static String EVENT_FAQS_CONTENTADD = "faqs.contentadd";
	public static String EVENT_FAQS_CONTENTDELETE = "faqs.contentdelete";
	public static String EVENT_FAQS_CONTENTEDIT = "faqs.contentedit";
	public static String EVENT_FAQS_CONTENTVIEW = "faqs.contentview";
	public static String EVENT_FAQS_CATEGORYADD = "faqs.categoryadd";
	public static String EVENT_FAQS_CATEGORYDELETE = "faqs.categorydelete";
	public static String EVENT_FAQS_CATEGORYDEDIT = "faqs.categoryedit";
	public static String EVENT_JOIN_VIEW = "join.view";
	public static String EVENT_JOIN_STEP1 = "join.step1";
	public static String EVENT_JOIN_STEP2 = "join.step2";
	public static String EVENT_JOIN_STEP3 = "join.step3";
	public static String EVENT_JOIN_ERROR = "join.error";
	public static String EVENT_JOIN_ACTIVATION = "join.activation";
	public static String EVENT_JOIN_ACTIVATIONERROR = "join.activationerror";
	public static String EVENT_JOIN_SMSYES = "join.sms.yes";
	public static String EVENT_JOIN_SMSNO = "join.sms.no";
	public static String EVENT_MDADMISSION_VIEW = "mdadmission.view";
	public static String EVENT_MDADMISSION_SIGNOFF = "mdadmission.signoff";
	
	public static String EVENT_MYLIFE_VIEW = "claim.mylife.view";
	public static String EVENT_MYLIFE_STEP1 = "claim.mylife.step1";
	public static String EVENT_MYLIFE_STEP2 = "claim.mylife.step2";
	public static String EVENT_MYLIFE_ERROR = "claim.mylife.error";
	public static String EVENT_MYLIFE_MYLIFEACCEPT = "mylife.accept";
	public static String EVENT_MYLIFE_SMSYES = "mylife.mylifesms.yes";
	public static String EVENT_MYLIFE_SMSNO = "mylife.mylifesms.no";
	
	public static String EVENT_UNISALOGIN_VIEW = "claim.unisalogin.view";
	public static String EVENT_UNISALOGIN_STEP1 = "claim.unisalogin.step1";
	public static String EVENT_UNISALOGIN_STEP2 = "claim.unisalogin.step2";
	public static String EVENT_UNISALOGIN_ERROR = "claim.unisalogin.error";
	public static String EVENT_UNISALOGIN_ACKNOWACCEPT ="claim.unisalogin.accept";
	public static String EVENT_UNISALOGIN_SMSYES = "claim.unisaloginsms.yes";
	public static String EVENT_UNISALOGIN_SMSNO = "mylife.unisaloginsms.no";
	
	public static String EVENT_REJOIN_VIEW = "rejoin.view";
	public static String EVENT_REJOIN_STEP1 = "rejoin.step1";
	public static String EVENT_REJOIN_STEP2 = "rejoin.step2";
	public static String EVENT_REJOIN_STEP3 = "rejoin.step3";
	public static String EVENT_REJOIN_COMPLETED = "rejoin.completed";
	public static String EVENT_REJOIN_ERROR = "rejoin.error";
	public static String EVENT_REJOIN_SMSYES = "rejoin.mylifesms.yes";
	public static String EVENT_REJOIN_SMSNO = "rejoin.mylifesms.no";
	
	public static String EVENT_MDACTIVITY_VIEW = "mdActivity.view";
	public static String EVENT_MDACTIVITY_ADD = "mdActivity.add";
	public static String EVENT_MDACTIVITY_EDIT = "mdActivity.edit";
	public static String EVENT_MONITOR_VIEW = "monitor.view";
	
	public static String EVENT_PASSWORD_VIEW = "forgotpassword.view";	
	public static String EVENT_PASSWORD_STUDENTNRSTEP = "forgotpassword.studentnrstep";
	public static String EVENT_PASSWORD_PERSDETAILSTEP = "forgotpassword.personalstep";
	public static String EVENT_PASSWORD_MYLIFEPWDVIEW = "forgotpassword.mylifepwdviewed";
	public static String EVENT_PASSWORD_MYUNISAPWDVIEW = "forgotpassword.myunisapwdviewed";
	public static String EVENT_PASSWORD_SENDEMAIL = "forgotpassword.sendemail";	
	public static String EVENT_PASSWORD_ACTIVATE = "forgotpassword.activate";
	public static  String EVENT_PASSWORD_ERROR = "forgotpassword.error";
	
	public static String EVENT_PAYMENTS_VIEWCURRENT = "payments.viewcurrent";
	public static String EVENT_PAYMENTS_VIEWHISTORY = "payments.viewhistory";
	public static String EVENT_PAYMENTS_CCPAYMENT = "payments.ccpayment";
	public static String EVENT_CREDITCARD_PAYMENT = "creditcard.payment";
	public static String EVENT_CREDITCARD_DISPLAY = "creditcard.display";
	public static String EVENT_PROXY_VIEW = "proxy.view";
	public static String EVENT_REGDETAILS_VIEW = "regdetails.view";
	public static String EVENT_REGDETAILS_ADDITION = "regdetails.addition";
	public static String EVENT_REGDETAILS_CANCELLATION = "regdetails.cancellation";
	public static String EVENT_REGDETAILS_FINALYEAR = "regdetails.finalyearapplication";
	public static String EVENT_REGDETAILS_SEMESTEREXCHANGE = "regdetails.semesterexchange";
	public static String EVENT_STUDENTREGISTRATION_APPLYFORSTUDENTNR = "studentregistration.applystunr";
	public static String EVENT_RESULTS_VIEW = "results.view";
	public static String EVENT_STUDENTEMAIL_VIEW = "studentemail.view";
	public static String EVENT_STUDENTEMAIL_CHANGEEMAIL = "studentemail.changeemail";
	public static String EVENT_STUDENTEMAIL_SENDMAIL = "studentemail.sendmail";
	public static String EVENT_STUDENTLIST_VIEW = "studentlist.view";
	public static String EVENT_STUDENTLIST_DISPLAY = "studentlist.display";
	public static String EVENT_STUDENTLIST_DOWNLOAD = "studentlist.download";
	public static String EVENT_STUDYQUOTATION_VIEW = "studyquotation.view";
	public static String EVENT_TRACKANDTRACE_VIEW = "trackandtrace.view";
	
	public static String EVENT_WELCOME_VIEW = "welcome.view";
	public static String EVENT_WELCOME_UPDATE = "welcome.update";
	public static String EVENT_WELCOME_REVERT = "welcome.revert";
	public static String EVENT_EBOOKSHOP_ADD = "ebookshop.add";
	public static String EVENT_EBOOKSHOP_EDIT = "ebookshop.edit";
	
	public static String EVENT_DISCUSSIONFORUMS_ADD = "discussionforums.add";
	public static String EVENT_DISCUSSIONFORUMS_EDIT = "discussionforums.edit";
	public static String EVENT_DISCUSSIONFORUMS_DELETE = "discussionforums.delete";
	public static String EVENT_DISCUSSIONFORUMS_ADD_TOPIC = "discussionforums.addtopic";
	public static String EVENT_DISCUSSIONFORUMS_DELETE_TOPIC = "discussionforums.deletetopic";
	public static String EVENT_DISCUSSIONFORUMS_ADD_REPLY = "discussionforums.addreply";
	public static String EVENT_DISCUSSIONFORUMS_DELETE_REPLY = "discussionforums.deletereply";
	
	public static String EVENT_SATBOOK_BKNG_ADD = "satbook.addbkng";
	public static String EVENT_SATBOOK_BKNG_DELETE = "satbook.delbkng";
	public static String EVENT_SATBOOK_CONFIRM = "satbook.confirm";
	public static String EVENT_SATBOOK_UNCONFIRM = "satbook.unconfirm";
	public static String EVENT_SATBOOK_EDIT_HEADING = "satbook.editbkngheading";
	public static String EVENT_SATBOOK_EDIT_SUBJECTS = "satbook.editbkngsubjects";
	public static String EVENT_SATBOOK_EDIT_MATERIALS = "satbook.editbkngmaterials";
	public static String EVENT_SATBOOK_EDIT_VISITORS = "satbook.editbkngvisitors";
	public static String EVENT_SATBOOK_EDIT_CLASSROOMS = "satbook.editbkngclassrooms";
	public static String EVENT_SATBOOK_LECTURER_ADD = "satbook.addlecturer";
	public static String EVENT_SATBOOK_LECTURER_DELETE = "satbook.dellecturer";
	public static String EVENT_SATBOOK_LECTURER_EDIT = "satbook.editlecturer";
	public static String EVENT_SATBOOK_INST_ADD = "satbook.addinstitution";
	public static String EVENT_SATBOOK_INST_EDIT = "satbook.editinstitution";
	public static String EVENT_SATBOOK_INST_DELETE = "satbook.delinstitution";
	public static String EVENT_SATBOOK_INSTDAYS_ADD = "satbook.addinstdays";
	public static String EVENT_SATBOOK_INSTDAYS_EDIT = "satbook.addinstdays";
	public static String EVENT_SATBOOK_CLASSROOM_ADD = "satbook.addclassroom";
	public static String EVENT_SATBOOK_CLASSROOM_EDIT = "satbook.editclassroom";
	public static String EVENT_SATBOOK_CLASSROOM_DELETE = "satbook.delclassroom";
	public static String EVENT_SATBOOK_MATERIAL_ADD = "satbook.addmaterial";
	public static String EVENT_SATBOOK_MATERIAL_EDIT = "satbook.editmaterial";
	public static String EVENT_SATBOOK_MATERIAL_DELETE = "satbook.delmaterial";
	public static String EVENT_SATBOOK_ASSISTANT_ADD = "satbook.addassistant";
	public static String EVENT_SATBOOK_ASSISTANT_EDIT = "satbook.editassistant";
	public static String EVENT_SATBOOK_ASSISTANT_DELETE = "satbook.delassistant";
	public static String EVENT_SATBOOK_ASSTYPE_ADD = "satbook.addasstype";
	public static String EVENT_SATBOOK_ASSTYPE_EDIT = "satbook.editasstype";
	public static String EVENT_SATBOOK_ASSTYPE_DELETE = "satbook.delasstype";
	public static String EVENT_SATBOOK_BKNGTYPE_ADD = "satbook.addbkngtype";
	public static String EVENT_SATBOOK_BKNGTYPE_EDIT = "satbook.editbkngtype";
	public static String EVENT_SATBOOK_BKNGTYPE_DELETE = "satbook.deletebkngtype";
	
	public static String EVENT_VENBOOK_BKNG_ADD = "venbook.addbkng";
	public static String EVENT_VENBOOK_BKNG_DELETE = "venbook.delbkng";
	public static String EVENT_VENBOOK_CONFIRM = "venbook.confirm";
	public static String EVENT_VENBOOK_UNCONFIRM = "venbook.unconfirm";
	public static String EVENT_VENBOOK_EDIT_HEADING = "venbook.editbkngheading";
	public static String EVENT_VENBOOK_EDIT_SUBJECTS = "venbook.editbkngsubjects";
	public static String EVENT_VENBOOK_EDIT_MATERIALS = "venbook.editbkngmaterials";
	public static String EVENT_VENBOOK_EDIT_VISITORS = "venbook.editbkngvisitors";
	public static String EVENT_VENBOOK_EDIT_VENUES = "venbook.editbkngvenues";
	public static String EVENT_VENBOOK_VENUE_ADD = "venbook.addvenue";
	public static String EVENT_VENBOOK_VENUE_EDIT = "venbook.editvenue";
	public static String EVENT_VENBOOK_VENUE_DELETE = "venbook.delvenue";
	public static String EVENT_VENBOOK_MATERIAL_ADD = "venbook.addmaterial";
	public static String EVENT_VENBOOK_MATERIAL_EDIT = "venbook.editmaterial";
	public static String EVENT_VENBOOK_MATERIAL_DELETE = "venbook.delmaterial";
	
	public static String EVENT_SIGNUP_MTNG_ADD = "signup.mtngadd";	
	public static String EVENT_SIGNUP_MTNG_REMOVE = "signup.mtngdelete";
	public static String EVENT_SIGNUP_MTNG_MODIFY = "signup.mtngchanged";
	public static String EVENT_SIGNUP_ADD_ATTENDEE_L = "signup.attendeeadd";
	public static String EVENT_SIGNUP_REMOVE_ATTENDEE_L = "signup.attendeeadd";
	public static String EVENT_SIGNUP_ADD_ATTENDEE_WL_L = "signup.attendeeaddwl";
	public static String EVENT_SIGNUP_REMOVE_ATTENDEE_WL_L = "signup.attendeeremovewl ";
	public static String EVENT_SIGNUP_MTNG_COPY = "signup.mtngcopy";
	public static String EVENT_SIGNUP_ADD_ATTENDEE_S = "signup.studentsignup";
	public static String EVENT_SIGNUP_REMOVE_ATTENDEE_S = "signup.studentcancel";
	public static String EVENT_SIGNUP_ADD_ATTENDEE_WL_S = "signup.studentsignupwl";
	public static String EVENT_SIGNUP_REMOVE_ATTENDEE_WL_S = "signup.studentcancelwl ";
	
	public static String EVENT_MAINTAINSTAFF_VIEW = "maintain.view";
	public static String EVENT_MAINTAINSTAFF_ROLE_ADD = "maintain.addrole";
	public static String EVENT_MAINTAINSTAFF_ROLE_UPDATE = "maintain.updaterole";
	public static String EVENT_MAINTAINSTAFF_ROLE_REMOVE = "maintain.removerole";
	public static String EVENT_MAINTAINSTAFF_ROLE_UPDATE_PRIML = "maintain.update.primary.lecturer";
	public static String EVENT_MAINTAINSTAFF_COPY_COURSE_EXAM = "maintain.copy.examfunctions";
	public static String EVENT_MAINTAINSTAFF_COPY_COURSE_MARKING = "maintain.copy.markfunctions";
	public static String EVENT_MAINTAINSTAFF_COPY_COURSE_TEACH = "maintain.copy.teachroles";
	public static String EVENT_MAINTAINSTAFF_MANAGEMENT_VIEW = "maintain.management.view";
	
	public static String EVENT_RESENDMYLIFEPWD_SMS_SEND = "resend.mylifepwd";
	
	
	public static String EVENT_ADDQUAL_HEQF_INFO = "addqual.HEQFrelevantInfo"; 
	public static String EVENT_ADDQUAL_UNISA_INFO = "addqual.UnisaRelevantInfo";
	public static String EVENT_ADDQUAL_HEQF_NQF_INFO = "addqual.HEQFInfoNQFlevels";  
	public static String EVENT_ADDQUAL_HEMIS_INFO = "addqual.HemisInfoAdded";
	public static String EVENT_ADDQUAL_CHEONLINE_STEP1 = "addqual.cheCrit1Step1"; 
	public static String EVENT_ADDQUAL_CHEONLINE_STEP2 = "addqual.cheCrit1Step2";
	public static String EVENT_ADDQUAL_CHEONLINE_STEP3 = "addqual.cheCrit1Step3";
	public static String EVENT_ADDQUAL_CHEONLINE_STEP4 = "addqual.cheCrit1Step4";
	public static String EVENT_ADDQUAL_CHEONLINE_CRITERION2 = "addqual.CheCriterion2";
	public static String EVENT_ADDQUAL_CHEONLINE_CRITERION5 = "addqual.CheCriterion5";
	public static String EVENT_ADDQUAL_CHEONLINE_CRITERION6 = "addqual.CheCriterion6";
	public static String EVENT_ADDQUAL_PHASE_OUTQUALIFICATION = "addqual.PhaseoutQual";
	public static String EVENT_ADDQUAL_JUSTIFICATION_ADDED= "addqual.JustificationOfAppl";
	
	
	public static String EVENT_ADOBEDOWNLOAD_VERIFY_COURSE= "adobedownload.verifyCourse";
	public static String EVENT_ADOBEDOWNLOAD_TERMS= "adobedownload.acceptedTerms";
	
	

}
