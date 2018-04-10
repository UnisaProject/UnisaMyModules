package za.ac.unisa.lms.tools.liberesource.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;


import za.ac.unisa.lms.tools.liberesource.forms.LibmainForm;
import za.ac.unisa.lms.tools.liberesource.dao.ResourceDAO;
import za.ac.unisa.lms.tools.liberesource.forms.LibmainForm;
import za.ac.unisa.lms.tools.liberesource.forms.ResourceForm;
import za.ac.unisa.lms.tools.liberesource.forms.Tab;
import za.ac.unisa.utils.CodeProfiler;

import java.io.*;


public class ResourceAction extends LookupDispatchAction {
	
	private final String database = "libresource";
	ResourceDAO dao = new ResourceDAO(database);

	@SuppressWarnings("unchecked")
	@Override
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
		
		Map map = new HashMap();
		
	    map.put("libmainDisplay","libmainDisplay");
	    map.put("resourcebySubject","resourcebySubject");
	    map.put("resourcebyVendor","resourcebyVendor");
	    map.put("eresourcebySpecific","eresourcebySpecific");
		return map;
	}
	/** mainDisplay: Forward to maintenance menu */
	public ActionForward libmainDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LibmainForm libmainForm = (LibmainForm) form;
		//libmainForm.setSelectedTab(0); FL
		initializeForm(libmainForm, request.getRemoteAddr());
		return mapping.findForward("libmainDisplay");
	}
	
	/** subjectDisplay: Forward to maintenance of Subjects */
	public ActionForward resourcebySubject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LibmainForm libmainForm = (LibmainForm) form;
		 ResourceForm requestform = new ResourceForm();
		//ResourceDAO dao = new ResourceDAO(libmainForm.getDatabase()); FL
		libmainForm.setDataList(dao.selectSubjectList());
		setSelectedTab(21, libmainForm);

		return mapping.findForward("subjectDisplay");
	}
	/** subjectDisplay: Forward to maintenance of Subjects */
	public ActionForward resourcebyVendor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LibmainForm libmainForm = (LibmainForm) form;
		ResourceForm requestform = new ResourceForm();
		//ResourceDAO dao = new ResourceDAO(libmainForm.getDatabase()); FL
		libmainForm.setVendordataList((dao.selectVendorList()));
		setSelectedTab(20, libmainForm);
	
		return mapping.findForward("vendorDisplay");
	}
	


	
	/**this method returns resources according to subject , vendor and DB  */
	public ActionForward eresourcebySpecific(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String ipAddress =  request.getRemoteAddr();
		CodeProfiler profiler = new CodeProfiler();

		LibmainForm libmainForm = (LibmainForm) form;
		libmainForm.setSelectedSubjectList(null);
		libmainForm.setSelectedVendortList(null);
		libmainForm.setSelectSpecificdbList(null);
	    ResourceForm requestform = new ResourceForm();
	    requestform.setIpaddr(ipAddress);
		//ResourceDAO dao = new ResourceDAO(libmainForm.getDatabase());  // FL

	    
	    if(!(request.getParameter("heading")== null))
	    {
	    	if(request.getParameter("heading").equals("EJournals"))
	    	{
	    		response.sendRedirect("http://rv7pd4xp9l.search.serialssolutions.com"); 
	    	}
	    }
	    
	    
		if(!(request.getParameter("alphabet")== null))
		{
			int placementID = 0;
			
			Iterator<Tab> iterator = libmainForm.getTabs().iterator();
			
			while(iterator.hasNext())
			{
				Object o = iterator.next();
				Tab tab = (Tab) o;
				
				if(tab.getSelected()== "true")
				{
					placementID = tab.getPlacementID();
				}
			}
			
			Iterator<Tab> moreTabsIterator = libmainForm.getMoreTabsList().iterator();
			
			while(moreTabsIterator.hasNext())
			{
				Object o = moreTabsIterator.next();
				Tab tab = (Tab) o;
				
				if(tab.getSelected()== "true")
				{
					placementID = tab.getPlacementID();
				}
			}
			
			if(placementID == 21)
			{
				//libmainForm.setSelectedSubjectList(dao.selectSpecificSubject(Integer.toString(), request.getParameter("alphabet"), ipAddress));
				profiler.start(request);
				libmainForm.setSelectedSubjectList(dao.selectSpecificSubject(libmainForm.getSelectedSubjectId(), request.getParameter("alphabet"), ipAddress));
				profiler.stop(request, "unisa-liberesource: Select specific subject list (dao.selectSpecificSubject)");
			}
			else if(placementID == 20)
			{
				profiler.start(request);
				libmainForm.setSelectedVendortList(dao.selectSpecificVendor(Integer.toString(dao.getVendorID()), request.getParameter("alphabet"), ipAddress));
				profiler.stop(request, "unisa-liberesource: Select specific vendor list (dao.selectSpecificVendorList)");
			}
			else
			{
				profiler.start(request);
				libmainForm.setSelectSpecificdbList((dao.selectSpecificdb(Integer.toString(placementID),request.getParameter("alphabet"),ipAddress)));
				profiler.stop(request, "unisa-liberesource: Select specific resource list (dao.selectSpecificdb)");
			}
			
			return mapping.findForward("eresourceDisplay");
		}
		
		//if(!(request.getParameter("status").equals("main")))
		if (!(request.getParameter("place").equals("sub") || request.getParameter("place").equals("vend")))
		{
			setSelectedTab(Integer.parseInt(request.getParameter("place")), libmainForm);
		}
				
		if ((libmainForm.getHeading() == null)||(libmainForm.getHeading().length() == 0)) {
		    libmainForm.setHeading(request.getParameter("heading"));
		}
		try{

			if (request.getParameter("status").equals("main")) {
				libmainForm.setPlaceId(request.getParameter("place"));
				
			}
		}catch(NullPointerException ne){
			
		}

		if ((libmainForm.getAlpha() == null)||(libmainForm.getAlpha().equals(""))) {
			libmainForm.setAlpha("a");
			
		}else{
			libmainForm.setAlpha(request.getParameter("alpha"));
			if ((libmainForm.getAlpha() == null)||(libmainForm.getAlpha().equals(""))) {
				libmainForm.setAlpha("a");	
			}
		}
		try{
			//if(libmainForm.getPlaceId().equals("sub")){
			//	if(libmainForm.getSelectedSubjectId()==null||(libmainForm.getSelectedSubjectId().length()==0)){
			if (!(request.getParameter("selectedSubjectId")== null)){
				libmainForm.setSelectedSubjectId(request.getParameter("selectedSubjectId"));
				
			
				String subId= libmainForm.getSelectedSubjectId();
				/*if ((libmainForm.getPrevHeading() == null)||(libmainForm.getPrevHeading().length() == 0)) {
				    libmainForm.setPrevHeading(libmainForm.getHeading());
				}
				if (!libmainForm.getPrevHeading().equals(libmainForm.getHeading())) {
					libmainForm.setPrevHeading(libmainForm.getHeading());*/
				profiler.start(request);
					libmainForm.setAlphabets(dao.getAlphabets(21, Integer.parseInt(subId), 0));
				profiler.stop(request, "unisa-liberesource: Set alphabet list");
				//}
				profiler = new CodeProfiler();
				profiler.start(request);
				libmainForm.setSelectedSubjectList((dao.selectSpecificSubject(subId,dao.getStartAlphabet(),ipAddress)));
				profiler.stop(request, "unisa-liberesource: Retrieve Specific subject list; subject id: "+subId);
				dao.clearStartAlphabet();
			}
		}catch(NullPointerException ne){
			
		}
		try{
			//if(libmainForm.getPlaceId().equals("vend")){
			
			//	if(libmainForm.getSelectedVendorId()==null||(libmainForm.getSelectedVendorId().length()==0)){
			if (!(request.getParameter("selectedVendorId")== null)){
					libmainForm.setSelectedVendorId(request.getParameter("selectedVendorId"));
				
				String venId= libmainForm.getSelectedVendorId();
				profiler.start(request);
				libmainForm.setAlphabets(dao.getAlphabets(20, 0, Integer.parseInt(venId)));
				profiler.stop(request, "unisa-liberesource: Set alphabets");
				profiler.start(request);
				libmainForm.setSelectedVendortList((dao.selectSpecificVendor(venId,dao.getStartAlphabet(),ipAddress)));
				profiler.stop(request, "unisa-liberesource: Retrieve specific vendor list. Vendor "+venId);
				dao.clearStartAlphabet();
			}
		}catch(NullPointerException ne){
			
		}
		try{
			if(request.getParameter("selectedSubjectId")== null && request.getParameter("selectedVendorId")== null){
				
				profiler.start(request);
				libmainForm.setAlphabets(dao.getAlphabets(Integer.parseInt(request.getParameter("place")), 0, 0));
				profiler.stop(request,"unisa-liberesource: Retrieve alphabet list");
				
				profiler.start(request);
				libmainForm.setSelectSpecificdbList((dao.selectSpecificdb(libmainForm.getPlaceId(),dao.getStartAlphabet(),ipAddress)));
				profiler.stop(request, "unisa-liberesource: Retrieve Specific placement list. Placemement id: "+libmainForm.getPlaceId());
				
				dao.clearStartAlphabet();
				
			}	
		}catch(NullPointerException ne){
			
		}
		
		return mapping.findForward("eresourceDisplay");
	}
	
	private void setSelectedTab(int tabID, LibmainForm libmainForm) throws Exception
	{
		ArrayList<Tab> tmp = new ArrayList<Tab>();
		ArrayList<Tab> tmp2 = new ArrayList<Tab>();
		Iterator<Tab> tabsIter = libmainForm.getTabs().iterator();
		Iterator<Tab> moreTabsIter = libmainForm.getMoreTabsList().iterator();
		
		while(tabsIter.hasNext())
		{
			Object o = tabsIter.next();
			Tab tab = (Tab) o;
			
			if(tab.getPlacementID() == tabID)
			{
				tab.setSelected("true");
			}
			else
			{
				tab.setSelected("false");
			}
			tmp.add(tab);
		}
		
		while(moreTabsIter.hasNext())
		{
			Object o = moreTabsIter.next();
			Tab tab = (Tab) o;
			
			if(tab.getPlacementID() == tabID)
			{
				tab.setSelected("true");
			}
			else
			{
				tab.setSelected("false");
			}

			tmp2.add(tab);
		}

		libmainForm.setTabs(tmp);
		libmainForm.setMoreTabsList(tmp2);
	} 
	
	private void initializeForm(LibmainForm libmainForm, String ipAddress) throws Exception
	{
		dao.removeSpaces();
		
		ArrayList<Tab> tmp1 = new ArrayList<Tab>();
		ArrayList<Tab> tmp2 = new ArrayList<Tab>();

		Iterator<Tab> iterator = dao.getTabs().iterator();
		
		while(iterator.hasNext())
		{
			Object o = iterator.next();
			Tab tab = (Tab) o;
			
			if(tab.getNumber() < 7)
			{
				tmp1.add(tab);
			}
			else
			{
				tmp2.add(tab);
			}
		}
		
		libmainForm.setTabs(tmp1);
		libmainForm.setMoreTabsList(tmp2);
		libmainForm.setFeaturedDatabase(dao.getFeaturedDatabase(ipAddress));
		
	}
	
}
