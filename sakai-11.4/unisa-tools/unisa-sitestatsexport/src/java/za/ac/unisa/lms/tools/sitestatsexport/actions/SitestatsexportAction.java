package za.ac.unisa.lms.tools.sitestatsexport.actions;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;


import za.ac.unisa.lms.tools.sitestatsexport.dao.SiteStatesDAO;
import za.ac.unisa.lms.tools.sitestatsexport.dao.SitestatsexportDAO;
import za.ac.unisa.lms.tools.sitestatsexport.dao.SitestatsexportDetails;
import za.ac.unisa.lms.tools.sitestatsexport.forms.SitestatsexportForm;



public class SitestatsexportAction extends LookupDispatchAction  {

	
	/**
	 * Method view
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	private Log log = LogFactory.getLog(this.getClass());
	@Override
	
  
	
	protected Map getKeyMethodMap() {
		// TODO Auto-generated method stub
	
		 Map map = new HashMap();
		 map.put("mainview","mainview");
	       map.put("button.export","export");
		return map;
	}

	  private SessionManager sessionManager;
	
	
	public ActionForward mainview(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		sessionManager = (SessionManager) ComponentManager.get(SessionManager.class);
		      HttpSession session = request.getSession(true);
		      //SitestatsexportForm sitestatsexportForm = (SitestatsexportForm) form;
		      SitestatsexportForm sitestatsexportForm = (SitestatsexportForm)form; 
		      Calendar cal=Calendar.getInstance();
		      Session currentSession = sessionManager.getCurrentSession();
				String userID = currentSession.getUserEid();
				sitestatsexportForm.setNetworkId(userID);
		      sitestatsexportForm.setCurrentyear(cal.get(Calendar.YEAR));
		      sitestatsexportForm.setNextyear(cal.get(Calendar.YEAR)+1);
		     SitestatsexportDAO dao = new SitestatsexportDAO();
		     SiteStatesDAO statsdao = new SiteStatesDAO();
		     String collegecode = sitestatsexportForm.getCollegeCode();
		     String schcode = sitestatsexportForm.getSchool();
		     String dept = sitestatsexportForm.getDepartment();
		 	String[] months = {"January", "February",
		            "March", "April", "May", "June", "July",
		            "August", "September", "October", "November",
		            "December"};
		 	    Calendar calender = Calendar.getInstance(); 
		        String month = months[cal.get(Calendar.MONTH)];
		        sitestatsexportForm.setMonths(months);
		  session.setAttribute("colleges",dao.getColleges());
		  session.setAttribute("schools",dao.getSchools(collegecode));
		  session.setAttribute("departments",dao.getDepts(schcode,collegecode));
		  session.setAttribute("courses",dao.getCourseCodes(dept));
		  session.setAttribute("events",dao.getEvents());
		  session.setAttribute("yearsList",sitestatsexportForm.getYearsList());
		  session.setAttribute("yearsList1",sitestatsexportForm.getYearsList1());
		  return  mapping.findForward("viewforward");
		
	}


	@SuppressWarnings("deprecation")
	public ActionForward export(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      HttpSession session = request.getSession(true);
		      //SitestatsexportForm sitestatsexportForm = (SitestatsexportForm) form;
		      SitestatsexportForm sitestatsexportForm = (SitestatsexportForm)form; 
		      ActionMessages messages = new ActionMessages();
		      Calendar cal=Calendar.getInstance();
		      sitestatsexportForm.setCurrentyear(cal.get(Calendar.YEAR));
		      sitestatsexportForm.setNextyear(cal.get(Calendar.YEAR)+1);
		      SitestatsexportDAO dao = new SitestatsexportDAO();
		 	if (sitestatsexportForm.getCollegeCode().equals("-1")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("error.college"));
				  addErrors(request, messages); 
				return mapping.findForward("viewforward");
				
			}
		/*	if (sitestatsexportForm.getSemister().equals("-1")) {
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("select.semister"));
				  addErrors(request, messages); 
				return mapping.findForward("viewforward");
				
			}*/
		      String person= sitestatsexportForm.getPerson().trim();
		      if ((dao.isUserIdValid(person.toUpperCase())==false)&&!(person.toUpperCase().equals(""))){
		    	  messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("userId.notvalid"));
					  addErrors(request, messages); 
					return mapping.findForward("viewforward");
		    	  
		      }
		         int yr = Integer.parseInt(sitestatsexportForm.getYear1());
			     String month = sitestatsexportForm.getMonth();
			     if(month.equals("January")){
					 month = "01";
					}else if(month.equals("February")){
					 month = "02";
					}else if(month.equals("March")){
					 month = "03";
					}else if(month.equals("April")){
					 month = "04";
					}else if(month.equals("May")){
					 month = "05";
					}else if(month.equals("June")){
					 month = "06";
					}else if(month.equals("July")){
					 month = "07";
					}else if(month.equals("August")){
					 month = "08";
					}else if(month.equals("September")){
					 month = "09";
					}else if(month.equals("October")){
					 month = "10";
					}else if(month.equals("November")){
					 month = "11";
					}else if(month.equals("December")){
					 month = "12";
					}else if(month.equals("-1")){
					 month = "0";
					}
			     
			     System.out.println(cal.get(Calendar.MONTH));
			if(yr==cal.get(Calendar.YEAR)){
				if((Integer.parseInt(month))>(cal.get(Calendar.MONTH)+1)){
					 messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("transaction.future"));
						  addErrors(request, messages); 
						return mapping.findForward("viewforward");
				}
			}
		      
		            String path = getServlet().getServletContext().getInitParameter("mypath");
			        //String path ="c://drd//";
		            String filename = "sitestats.xls";
		            String pathName= path+filename;
			
			    	saveToServer(pathName,sitestatsexportForm);
			    	 if ((sitestatsexportForm.getExportList().size())==0){
				    	  messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("export.nodata"));
							  addErrors(request, messages); 
							return mapping.findForward("viewforward");
				    	  
				      }
					File file = new File(path+filename);
					DataInputStream in = new DataInputStream(new FileInputStream(file));
					ServletOutputStream out = response.getOutputStream();
					response.setDateHeader("Expires", 0);
					response.setHeader( "Pragma", "public" );
					response.setContentType("application/octet-stream");
					response.setContentLength((int)file.length());
					response.addHeader("Content-Disposition", "attachment;filename=" + filename );

					saveToClient(in, out);
					
		     /* String filename = "C:\\var\\test.xls";
					String collegeCode = sitestatsexportForm.getCollegeCode();
					String schCode = sitestatsexportForm.getSchool();
					String dept = sitestatsexportForm.getDepartment();
					String courseCode = sitestatsexportForm.getCourseCode1();
					    
					String role = sitestatsexportForm.getRole();
					String userId = sitestatsexportForm.getPerson();
					String eventId = sitestatsexportForm.getEvent();
					int year = sitestatsexportForm.getYear();	
					List list = dao.getSiteStatsExport(year, collegeCode, schCode, dept, userId, eventId, courseCode);
					 List siteInfo = (List)list;	
					 System.out.println("list size"+siteInfo.size());
			 Iterator i = siteInfo.iterator();
			 HSSFWorkbook hwb = new HSSFWorkbook();
	         HSSFSheet sheet = hwb.createSheet("new sheet");
		     HSSFRow rowhead = sheet.createRow((short) 2);
		 	File file = new File(filename);
		    
		     int index = 2;
		     FileOutputStream fileOut = new FileOutputStream(filename);
			  while (i.hasNext())
			  {		
				  //ListOrderedMap data = (ListOrderedMap) i.next();
			  //SitestatsexportDetails sitestatsDetails = new SitestatsexportDetails();
				  SitestatsexportDetails sitestatsDetails = (SitestatsexportDetails) i.next();
			 
				try{
					 HSSFRow row = sheet.createRow((short) index);
					 row.createCell((int) 0).setCellValue("College"+sitestatsDetails.getCollege());
					 row.createCell((int) 1).setCellValue("School"+sitestatsDetails.getSchool());
					 row.createCell((int) 2).setCellValue("Department"+sitestatsDetails.getDepartment());
					 row.createCell((int) 3).setCellValue("Course Code"+sitestatsDetails.getCourseCode());
					 row.createCell((int) 4).setCellValue("Role"+sitestatsDetails.getRole());
					 row.createCell((int) 5).setCellValue("User ID"+sitestatsDetails.getPerson());
					 row.createCell((int) 6).setCellValue("Event"+sitestatsDetails.getEvent());
					 row.createCell((int) 7).setCellValue("Event Count"+sitestatsDetails.getEventCount());
					 
		             
		             
					 index++;
					
					}
			    catch(NullPointerException e ){
			    	e.printStackTrace();
			    	}
				
			  }	
			
			  hwb.write(fileOut);
			  fileOut.close();
            
			 DataInputStream in = new DataInputStream(new FileInputStream(file));
			 //FileOutputStream out = response.getOutputStream();
			 ServletOutputStream out = response.getOutputStream();
			  saveToClient(in, out);
		     
		 /* session.setAttribute("colleges",dao.getColleges());
		  session.setAttribute("schools",dao.getSchools(collegecode));
		  session.setAttribute("departments",dao.getDepts(schcode));
		  session.setAttribute("courses",dao.getCourseCodes(dept));
		  session.setAttribute("events",dao.getEvents());*/
			  return  null;
	}
	
	public void saveToClient(DataInputStream in, ServletOutputStream out) throws Exception {

		int w = in.read();

		while (w != -1) {
			out.write(w);
			w = in.read();
		}

		out.flush();
		out.close();
		in.close();
	}
	
	public void saveToServer(String filename, SitestatsexportForm sitestatsexportForm) throws Exception {
		  
	
		File fileobject = new File(filename);
		FileWriter fw = new FileWriter(fileobject);
		   ActionMessages messages = new ActionMessages();
		String seperatewith = "";
	    SitestatsexportDAO dao = new SitestatsexportDAO();

	     String collegeCode = sitestatsexportForm.getCollegeCode();
	     String schCode = sitestatsexportForm.getSchool();
	     String dept = sitestatsexportForm.getDepartment();
	     String courseCode = sitestatsexportForm.getCourseCode1();
	    
	     String role = sitestatsexportForm.getRole();
	     String user= sitestatsexportForm.getPerson().trim();
	     String userId = user.toUpperCase();
	     String eventId = sitestatsexportForm.getEvent();
	     String year = sitestatsexportForm.getYear();
	     String semister = sitestatsexportForm.getSemister();
	     String yr = sitestatsexportForm.getYear1();
	     String month = sitestatsexportForm.getMonth();
	     boolean valid = dao.isUserIdValid(sitestatsexportForm.getPerson().toUpperCase());

	     if(month.equals("January")){
			 month = "01";
			}else if(month.equals("February")){
			 month = "02";
			}else if(month.equals("March")){
			 month = "03";
			}else if(month.equals("April")){
			 month = "04";
			}else if(month.equals("May")){
			 month = "05";
			}else if(month.equals("June")){
			 month = "06";
			}else if(month.equals("July")){
			 month = "07";
			}else if(month.equals("August")){
			 month = "08";
			}else if(month.equals("September")){
			 month = "09";
			}else if(month.equals("October")){
			 month = "10";
			}else if(month.equals("November")){
			 month = "11";
			}else if(month.equals("December")){
			 month = "12";
			}else if(month.equals("-1")){
			 month = "0";
			}
	    
	   

	    List list = dao.getSiteStatsExport(year, collegeCode, schCode, dept, userId, eventId, courseCode,semister, yr,month,valid);
	    sitestatsexportForm.setExportList(list);
	     seperatewith = "\t";
	     List siteInfo = (List)list;	
		 fw.write("College"+seperatewith+" School "+seperatewith+" Department "+seperatewith+" Course Code "+seperatewith+" Role "+seperatewith+" Person "+seperatewith+" Event "+seperatewith+"Month"+seperatewith+" Event Count "+seperatewith+" \n");
			for(int i=0; i < siteInfo.size(); i++) {
		    fw.write(((SitestatsexportDetails)siteInfo.get(i)).getCollege()+seperatewith);		
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getSchool()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getDepartment()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getCourseCode()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getRole()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getPerson()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getEvent()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getMonth()+seperatewith);
			fw.write(((SitestatsexportDetails)siteInfo.get(i)).getEventCount()+seperatewith+"\n");
	
			}
		fw.flush();
		fw.close();
	}
	
	
}
