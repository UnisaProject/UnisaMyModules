package za.ac.unisa.lms.tools.tutoringplan.impl;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
public class TutoringplanUtils {

	public static boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e){}
		return false;
	}
	
	public static void addErrorMessage(ActionMessages messages,String errMsg){
		            messages.add(ActionMessages.GLOBAL_MESSAGE,
				          new ActionMessage("message.generalmessage", errMsg));
				
	}
	public static void addErrorMessages(ActionMessages messages,List errMsgList){
		               Iterator iter=errMsgList.iterator();
		               while(iter.hasNext()){
                               messages.add(ActionMessages.GLOBAL_MESSAGE,
	                             new ActionMessage("message.generalmessage",iter.next().toString()));
		               }    
   }
	public static String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	public static String networkCode;
	public static String getNetworkCode(){
		           return networkCode;
	}
	public static void setNetworkCode(String userId){
                 networkCode=userId;
    }
	private static String studyUnit;
	private static  Short academicYear;
	private static  Short semester;
	public static void setStudyUnit(String module){
		studyUnit=module;
	}
	public static String getStudyUnit(){
		return studyUnit;
	}

	public static void setAcademicYear(Short acadYear){
		         academicYear=acadYear;
	}
	public static Short getAcademicYear(){
		return academicYear;
	}
	public static void setSemester(Short semester_period){
		       semester=semester_period;
	}
	public static Short getSemester(){
		return semester;
	}

}
