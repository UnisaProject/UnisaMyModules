package za.ac.unisa.lms.tools.maintainstaff.actions;


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
import za.ac.unisa.lms.tools.maintainstaff.DAO.HodQueryDao;
import za.ac.unisa.lms.tools.maintainstaff.DAO.MaintainStaffStudentDAO;
import za.ac.unisa.lms.tools.maintainstaff.forms.DepartmentRecordForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.HodDisplayForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.MainForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.StaffListForm;


public class MyUnisaHodAction extends LookupDispatchAction {
	@SuppressWarnings("unchecked")
	protected Map getKeyMethodMap() {
	      Map map = new HashMap();
	      map.put("hodDisplay","hodDisplay");
	      map.put("button.getDetail","hodDisplay");
	      map.put("button.addStandin","addStandin");
	      map.put("button.removeStandin","removeStandin");
	      map.put("button.saveStandin", "saveStandin");
	      map.put("button.deleteStandin", "deleteStandin");
	      map.put("button.cancel","cancel");
	      
	      return map;
	}
	
	/** Retrieve HOD detail and to to HOD view screen */
	public ActionForward hodDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HodDisplayForm hoddisplayform=(HodDisplayForm) form;
	
		HodQueryDao db = new HodQueryDao();
		try {
			
			hoddisplayform.setDepartmentList(db.selectDepartmentList());//sets the values for drop down list in hodstep1
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());
		
		try {
			hoddisplayform.setDepartmentOrderList(db. selectDepartmentTypeList(hoddisplayform.getDepartmentTypeForReport(),true));//for getting the values if chooses get detail
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("records", hoddisplayform.getDepartmentOrderList());
		
		
		return mapping.findForward("step1");
		}
	
	/** Go to add stand-in screen */
	public ActionForward addStandin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		HodDisplayForm hoddisplayform=(HodDisplayForm) form;
		ActionMessages messages = new ActionMessages();
		String[] list =hoddisplayform.getSelectedDepartment();
		HodQueryDao db = new HodQueryDao();
		DepartmentRecordForm department = new DepartmentRecordForm(); //to set the values 
		request.setAttribute("department", department);
		
		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());
		if ((list != null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Cannot add when a Stand-in is checked for removal."));
			addErrors(request, messages);
		    
			hoddisplayform.setSelectedDepartment(null); //for resetting the multiboxes	
			return mapping.findForward("step1");
		}

		
		try {
			hoddisplayform.setDepartmentList(db.selectDepartmentList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());
		
		 hoddisplayform.setSelectedDepartment(null); //for resetting the multiboxes	
		return mapping.findForward("saveStandin");
		

	}
	
	/** Go to remove standin screen */
	public ActionForward removeStandin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		ActionMessages messages = new ActionMessages();
		
		HodDisplayForm hoddisplayform=(HodDisplayForm) form;
		String[] list =hoddisplayform.getSelectedDepartment();
		
		HodQueryDao db = new HodQueryDao();
		String[] standinList;
		
		try {
			hoddisplayform.setDepartmentList(db.selectDepartmentList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());
		
		try {
			hoddisplayform.setDepartmentOrderList(db. selectDepartmentTypeList(hoddisplayform.getDepartmentTypeForReport(),true));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("records", hoddisplayform.getDepartmentOrderList());
		
		
		if ((list == null)||(list.length==0)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please select 1 or more Stand-in."));
			addErrors(request, messages);
		
			return mapping.findForward("step1");
		}
	
			hoddisplayform.setSelectedStandin(list);
			
			request.setAttribute("list", hoddisplayform.getSelectedStandin());
			
		return mapping.findForward("removeStandin");
		
		
	}
	
	/** Save new standin detail */
	public ActionForward saveStandin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HodDisplayForm hoddisplayform=(HodDisplayForm)form;
		HodQueryDao db = new HodQueryDao();
		MaintainStaffStudentDAO db2 = new MaintainStaffStudentDAO();
		
		ActionMessages messages = new ActionMessages();
		
		DepartmentRecordForm department = new DepartmentRecordForm();
	
		department.setDepertmentId(request.getParameter("departmentTypeForReport"));
		
		department.setUserCode(request.getParameter("userCode"));
		
		department.setPnumber(request.getParameter("pnumber"));
	
		request.setAttribute("department", department);

		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());


		if ((department.getUserCode().length()==0)||(department.getUserCode()==null)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the network user code."));
			addErrors(request, messages);
		
			request.setAttribute("department", department);

			return mapping.findForward("saveStandin");
		}

		// validate staff network code
		String msg = "";
		try {
			msg = db2.staffValidation(department.getUserCode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (msg.length() >=1) {
			if (msg.equals("Error reading staff record.")) {
				
				// CHECK IF CONTRACTOR
				String tmpName = db2.personExist(department.getUserCode());
				if (tmpName.equals("0")) {
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("message.generalmessage", "Staff information is not valid ("+department.getUserCode()+").  " +
									"Verify network code was correctly entered."));
					addErrors(request, messages);
					return mapping.findForward("saveStandin");			
				} else {
					department.setSurName(tmpName);
				}
				
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", msg));
				addErrors(request, messages);
				return mapping.findForward("saveStandin");
			}
		}
		department.setPnumber(db2.selectStaffPersno(department.getUserCode()));
	
		StaffListForm staffDetailsForm=new StaffListForm();
		
		String tmpPersNo ="";
		String tmpNovelUserCode="";
        try {
			
			department.setUserCode(rtrim(department.getUserCode()).toUpperCase());//to remove spaces in the query and to convert into upper case 
			tmpNovelUserCode= db.selectStaffDetailsUserCode(department.getUserCode());
			
			staffDetailsForm.getNovellUserCode();
			
		}catch(Exception e){
			e.printStackTrace();				
		}
		
		if (!(department.getUserCode()).equals(tmpNovelUserCode)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the VALID network user code."));
			addErrors(request, messages);
			request.setAttribute("userCode", department.getUserCode());
			
			return mapping.findForward("saveStandin");
		}
	
		/*
		try {
			
			department.setPnumber(rtrim(department.getPnumber()));//to remove spaces in the query
			tmpPersNo = db.selectStaffDetails(department.getPnumber());
			
			staffDetailsForm.getPersNo();
			
		}catch(Exception e){
			e.printStackTrace();				
		}
		
		if (!(department.getPnumber()).equals(tmpPersNo)) {
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Please enter the VALID personnel number."));
			addErrors(request, messages);

			request.setAttribute("pnumber",department.getPnumber());
			request.setAttribute("userCode", department.getUserCode());
			
			return mapping.findForward("saveStandin");
		}
		*/
		
		try {
			db.insertStandin(department);
			
			messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", "Stand-in  was added successfully"));
			addMessages(request, messages);
			//for resetting the values in jsp.
			try {
				hoddisplayform.setDepartmentOrderList(db. selectDepartmentTypeList(hoddisplayform.getDepartmentTypeForReport(),true));//for getting the values if chooses get detail
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return mapping.findForward("step1");
}
		
		
	/* remove leading whitespace */
    public static String ltrim(String source) {
        return source.replaceAll("^\\s+", "");
    }
    /* remove trailing whitespace */
    public static String rtrim(String source) {
        return source.replaceAll("\\s+$", "");
    }
    
    /** Delete stadin */
   public ActionForward deleteStandin(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		HodDisplayForm hoddisplayform=(HodDisplayForm)form;
		
		hoddisplayform.getSelectedStandin();
		ActionMessages messages = new ActionMessages();
		HodQueryDao db = new HodQueryDao();
		DepartmentRecordForm department = new DepartmentRecordForm();
		
		
		try {
			hoddisplayform.setDepartmentList(db.selectDepartmentList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("departmentList", hoddisplayform.getDepartmentList());
		
		try {
			hoddisplayform.setDepartmentOrderList(db. selectDepartmentTypeList(hoddisplayform.getDepartmentTypeForReport(),true));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("records", hoddisplayform.getDepartmentOrderList());
		
		 String[] tmp = hoddisplayform.getSelectedStandin();
				 
			department.setPnumber(request.getParameter("pnumber"));
			
			
			// if ("Y".equals(hoddisplayform.getChoice())) {
				 
		if(hoddisplayform.isChoice()){
		   /*for (int i=0; i < tmp.length; i++) {
			   String tmpPersno="";
			  tmpPersno =tmpPersno+tmp[i];
			  
			   System.out.println("in Y if loop  "+tmpPersno);*/
		 //  String tmpPersno;
		   StringBuffer tmpPersno = new StringBuffer();  //to retrieve the elements from string array.
		    if (tmp.length > 0) {
		    	tmpPersno.append("'"+tmp[0]+"'");
		        for (int i=1; i<tmp.length; i++) {
		        	tmpPersno.append(',');
		        	tmpPersno.append("'"+tmp[i]+"'");
		        	
		        	//System.out.println("string buffer if Y  "+tmpPersno);
		        }
		    }
			   try {
				   db.deleteStandin(tmpPersno);
				
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("message.generalmessage", "Stand-in  was deleted successfully"));
				addMessages(request, messages);
				

				try {
					hoddisplayform.setDepartmentOrderList(db. selectDepartmentTypeList(hoddisplayform.getDepartmentTypeForReport(),true));//for getting the values if chooses get detail
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("records", hoddisplayform.getDepartmentOrderList());
				
			  }//try
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end of catch
				
				 hoddisplayform.setSelectedDepartment(null); //for resetting the multiboxes
				return mapping.findForward("step1");
		   }//end of if
	
	   else{
		   
		   hoddisplayform.setSelectedDepartment(null);  //for resetting the multiboxes
		   messages.add(ActionMessages.GLOBAL_MESSAGE,
					new ActionMessage("message.generalmessage", " No Stand-in  was deleted "));
			addMessages(request, messages);
			
		   return mapping.findForward("step1");
	   }
    	
    	
	   }
   
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			MainForm mainForm = (MainForm) request.getSession().getAttribute("mainForm");
			
			mainForm.resetFormbean(mapping, request);
			return mapping.findForward("mainDisplay");		
	}
}

