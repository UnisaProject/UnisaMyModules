package za.ac.unisa.lms.tools.monitor.actions;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import Sfrrf03h.Abean.Sfrrf03sMntOnlineCcPayments;
import Srrsa01h.Abean.Srrsa01sRegStudentPersDetail;
import Saaal40j.Abean.Saaal40jGetStudentCourses;
import Exerp01h.Abean.Exerp01sPrtResultsAndTimetab;
import za.ac.unisa.utils.CoursePeriodLookup;
import za.ac.unisa.exceptions.JavaProxyExceptionListener;


public class ProxyTestAction extends DispatchAction {
	String studentNr = "43201059";
	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
	
	public ActionForward execute(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		String results = "";
		String hostname ="";

		try
		{
			hostname = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex){		
		}
		
		try{
			if (hostname.equalsIgnoreCase("linuxnode1")){
				//Test SQL proxy funnel 3010

			} else if (hostname.equalsIgnoreCase("linuxnode2")){
				//Test Exam proxy funnels 3021
				results = performExamProxyTest();
			} else if (hostname.equalsIgnoreCase("linuxnode3")){
				//Test Registration proxy funnels 3032				
				results = performRegProxyTest();
			} else if (hostname.equalsIgnoreCase("linuxnode4") || hostname.equalsIgnoreCase("linuxnode6")){
				//Test Assignment proxy funnels 3043 and General Funnel 3060
				results = performAssignProxyTest();				
			} else if (hostname.equalsIgnoreCase("linuxnode5")){
				//Test Finance proxy funnels 3054
				results = performFinProxyTest();								
			} else {
				results = performAssignProxyTest();
			}								
		} catch (java.lang.Exception e){
			System.out.println("FAILED IN THE CATCH BLOCK ");
			return mapping.findForward("proxyfailureforward");
		}
		return mapping.findForward("proxysuccessforward");
	}
	
	public String performExamProxyTest() throws Exception {
		//Test the Exam javaproxy funnel 3021
		Exerp01sPrtResultsAndTimetab op = new Exerp01sPrtResultsAndTimetab();
		Short examYear = 2012;
		Short examPeriod = 0;
		String result = "";
	    operListener opl = new operListener();
	    op.addExceptionListener(opl);
	    op.clear();
	    /* op.setTracing(Trace.MASK_ALL); */
	    op.setInIpAddressCsfStringsString15("my.unisa.ac.za");
	    op.setInSecurityWsWorkstationCode("intjsp");
	    op.setInSecurityWsFunctionNumber(373);
	    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
	    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
	    op.setInCsfClientServerCommunicationsAction("D");
	    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
	    op.setInExamDetailStudentAnnualRecordMkStudentNr(Integer.parseInt(studentNr));
	    op.setInExamDetailStudentAnnualRecordMkAcademicPeriod(examPeriod);
	    op.setInExamDetailStudentAnnualRecordMkAcademicYear(examYear);
	    op.execute();
	    if (opl.getException() != null){
	    	result = "Error";
	    	throw opl.getException();
	    }
	    if (op.getExitStateType() < 3){
	    	result = "Error";
	    	throw new Exception(op.getExitStateMsg());
	    }
	    int count = op.getOutResultGroupCount();
	    return result;
	}
	
	public String performRegProxyTest() throws Exception {
		//Test the Registration javaproxy funnel 3032 
		Srrsa01sRegStudentPersDetail op = new Srrsa01sRegStudentPersDetail();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);     
		op.setInCsfClientServerCommunicationsAction("D");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");  
		op.setInWsUserNumber(99998);
		op.setInStudentAnnualRecordMkAcademicYear(Short.parseShort(String.valueOf(2012)));
		op.setInStudentAnnualRecordMkAcademicPeriod(Short.parseShort("1"));
		op.setInStudentAnnualRecordMkStudentNr(Integer.parseInt("43201059"));
		  
		op.execute();
		if (opl.getException() != null) throw opl.getException();
		if (op.getExitStateType() < 3) throw new Exception(op.getExitStateMsg());
		String errorMessage = op.getOutCsfStringsString500();
		if(!"".equalsIgnoreCase(errorMessage)){
			if ("WARNING".equalsIgnoreCase(errorMessage.substring(0,7))){
				errorMessage="";
			}
		}
			
		if (!"".equals(errorMessage)){
		    return errorMessage;
		}
		if(op.getOutCsfClientServerCommunicationsReturnCode()== 9999){
			// error returned
			return errorMessage;
		}
		return "";
	}
	
	public String performAssignProxyTest() throws Exception {
		//Test assignment proxy funnel 3043
		Saaal40jGetStudentCourses op = new Saaal40jGetStudentCourses();
		JavaProxyExceptionListener exception = new JavaProxyExceptionListener();
		op.addExceptionListener(exception);
		op.clear();
		op.setAsStringInWsStudentAnnualRecordMkStudentNr("43201059");
		op.execute();
		
		if (exception.getException() != null)
			throw exception.getException();
		if (op.getExitStateType() < 3)
			throw new Exception(op.getExitStateMsg());
		
		for (int i = 0; i < op.getOutStusunCount(); i++) {
			String course = op
					.getOutGWsStudentStudyUnitMkStudyUnitCode(i)
					.toUpperCase();
			String period = CoursePeriodLookup.getCourseTypeAsString(op
					.getOutGWsStudentStudyUnitSemesterPeriod(i));
			String acadyear = new Short(op
					.getOutGWsStudentAnnualRecordMkAcademicYear(i))
					.toString();
			acadyear = acadyear.substring(2);
		}
		return "";
	}
	
	public String performFinProxyTest() throws Exception {
		//Test the Finance javaproxy funnel 3054 
		Sfrrf03sMntOnlineCcPayments op = new Sfrrf03sMntOnlineCcPayments();
		operListener opl = new operListener();
		op.addExceptionListener(opl);
		op.clear();

		op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
		op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
		op.setInCsfClientServerCommunicationsAction("DS");
		op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
		op.setInSecurityWsUserNumber(99998);
		op.setInWsWorkstationCode("internet");
		op.setInWsStudentNr(Integer.parseInt("43201059"));
		
		op.execute();
		if (opl.getException() != null)
			throw opl.getException();
		if (op.getExitStateType() < 3){
			System.out.println("CreditCardPayment- CoolGEN error; action: DS; ");
			throw new Exception(op.getExitStateMsg());
		}
		return "";
	}

}
