package za.ac.unisa.lms.tools.tutoringplan.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import za.ac.unisa.lms.tools.tutoringplan.dao.CollegeDAO;
import za.ac.unisa.lms.tools.tutoringplan.dao.TutoringPlanDao;
import za.ac.unisa.lms.tools.tutoringplan.forms.CollegeRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.DepartmentRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.SchoolRecord;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetails;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanForm;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringPlan;
public class TutoringPlanDetailAction extends LookupDispatchAction{
	
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
	
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("initial", "initial");	
		map.put("button.display", "displayTutorPlanDetails");		
		map.put("button.cancel", "cancel");	
		map.put("button.back", "prevStep");	
		return map;
	}
	
	public ActionForward initial(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		ActionMessages messages = new ActionMessages();
		
		CollegeDAO  dao=new CollegeDAO();
		DepartmentRecord dpt = new DepartmentRecord();
		CollegeRecord college = new CollegeRecord();
		List<DepartmentRecord> listDept = new ArrayList<DepartmentRecord>();
		List<SchoolRecord> listSchool = new ArrayList<SchoolRecord>(); 
		
		dpt = dao.getDepartment(Short.parseShort(tutPlanForm.getUser().getDepartmentCode()));
		if (dpt==null || dpt.getCode()==null){
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage",
								"You are not authorised to draw Tutoring plan details as you are not linked to a valid department."));
			addErrors(request,messages);
			return mapping.findForward("displayTutoringMode");
		}
		college = dao.getCollege(dpt.getCollegeCode());
		listDept= dao.getDepartmentList(college.getCode());
		listSchool = dao.getSchoolList(college.getCode());	

		tutPlanForm.setUserCollege(college);
		tutPlanForm.setListDepartment(listDept);
		tutPlanForm.setListSchool(listSchool);
		tutPlanForm.setSearchCriteria("D");
		tutPlanForm.setSelectedDepartment(dpt.getCode());
		
		return mapping.findForward("inputDetailList");		
	}
	
	public ActionForward displayTutorPlanDetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		         TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
		         ActionMessages messages = new ActionMessages();
		         TutoringPlan   TutoringPlan=new TutoringPlan();
		         String acadYear=tutPlanForm.getAcadYear();
		         String semester=tutPlanForm.getSemester();
		         List errMsgList=TutoringPlan.validateAcademicPeriod(acadYear, semester);
		         if (!errMsgList.isEmpty()) {
			            addErrors(request,messages);
			            return mapping.findForward("inputDetailList");
		          }
		
		List<TutoringPlanDetails> tutorPlanList = new ArrayList<TutoringPlanDetails>();
		TutoringPlan=new TutoringPlan();
		Short academicYear=Short.parseShort(tutPlanForm.getAcadYear());
		Short semester_period=Short.parseShort(tutPlanForm.getSemester());
		String searchCriteria=tutPlanForm.getSearchCriteria();
		Short schoolSelected=tutPlanForm.getSelectedSchool();
		Short selectedDept=tutPlanForm.getSelectedDepartment();
		CollegeRecord selectedCollege=tutPlanForm.getUserCollege();
		tutorPlanList = TutoringPlan.getTutorPlanDetailList(academicYear,semester_period,searchCriteria,schoolSelected,selectedDept,selectedCollege);
		request.setAttribute("tutorPlanList", tutorPlanList);
		return mapping.findForward("displayDetailList");		
	}	
	
	public ActionForward cancel(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
			TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
						
			return mapping.findForward("cancel");
			
	}
	
	public ActionForward prevStep(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		TutoringPlanForm tutPlanForm = (TutoringPlanForm) form;
			String nextPage="";
			
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("inputDetailList")){
				nextPage="home";
			}
			if (tutPlanForm.getCurrentPage().equalsIgnoreCase("displayDetailList")){
				nextPage="home";
			}			
			
			return mapping.findForward(nextPage);
			
	}
	
	

}
