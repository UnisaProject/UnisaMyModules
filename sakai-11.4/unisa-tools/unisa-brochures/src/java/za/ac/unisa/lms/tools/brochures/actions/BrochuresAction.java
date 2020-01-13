package za.ac.unisa.lms.tools.brochures.actions;


import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import java.io.*;
import javax.servlet.http.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;

import za.ac.unisa.lms.tools.brochures.dao.BrochureDAO;
import za.ac.unisa.lms.tools.brochures.dao.BrochureDetails;
import za.ac.unisa.lms.tools.brochures.dao.MychoiceDAO;
import za.ac.unisa.lms.tools.brochures.forms.BrochuresForm;



public class BrochuresAction extends LookupDispatchAction {
	
	//the code for removing white space problem when you use ENTER button instead of mouse .
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
          if (request.getParameter("act") == null){ 
        	  return brochuresPage(mapping, form, request, response);
          }
          return super.execute(mapping, form, request, response);           
    }
    private SessionManager sessionManager;
	private Log log = LogFactory.getLog(this.getClass());
	protected Map getKeyMethodMap(){
		Map map = new HashMap();
		map.put("brochuresPage","brochuresPage");
		map.put("myChoiceType","myChoiceType");
		map.put("myRegistration","myRegistration");
		map.put("myModules","myModules");
		map.put("button.export","exportReport");
		map.put("button.back","goBack");
		map.put("auditReport","auditReport");
		map.put("button.audit","auditReport");
		map.put("button.clear","clearAll");
		return map;
	}
	
	
	/**
	 * Method exportReport
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward exportReport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		 BrochuresForm brochuresForm = (BrochuresForm)form;
		
		String gotoStepValidate = "";
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = brochureType(mapping,form,request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = myRegExport(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = myModuleExport(mapping,form,request, response);
		}
		return mapping.findForward(gotoStepValidate);
	}
	
	/**
	 * Method gotoBack
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward goBack(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {

		 BrochuresForm brochuresForm = (BrochuresForm)form;
		
		String gotoStepValidate = "";
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = back1(mapping,form,request, response);
		} else if ("2".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = back2(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
			gotoStepValidate = back3(mapping,form,request, response);
		}
		return mapping.findForward(gotoStepValidate);
	}
	
	public ActionForward brochuresPage(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
			 Cookie[] cookies= request.getCookies();
			 if (cookies != null){
				 
				 for(int i=0; i < cookies.length; i++)  {
			    	 Cookie thisCookie = cookies[i];
			    	 if (thisCookie.getName().equals("novelluser")){
				                 brochuresForm.setUserId(thisCookie.getValue());
			    	 }
	      
				 }
				 
			 }
		     
		    brochuresForm.setCategory("-1");
	        brochuresForm.setCollegeCode("-1");
	        brochuresForm.setFormat("-1");
		   
			return mapping.findForward("mainmenu");
	}
	
	public ActionForward myChoiceType(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    ActionMessages messages = new ActionMessages();
		    HttpSession session = request.getSession(true);
		    MychoiceDAO dao =new MychoiceDAO();
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    String type = request.getParameter("type");
		    brochuresForm.setType1(type);
		    System.out.println("AAAAAAAAAAAA      " + type);
		    if(type.equals("a")){
		    	brochuresForm.setType("myChoice");
		    }else if(type.equals("b")){
		    	brochuresForm.setType("myChoice M&D");
		    }else if(type.equals("c")){
		    	brochuresForm.setType("myRegistration");
		    }
		    session.setAttribute("yearsList",brochuresForm.getYearsList());
		    session.setAttribute("colleges",dao.getColleges());
			session.setAttribute("schools",dao.getSchools(brochuresForm.getCollegeCode()));
			session.setAttribute("dptList",dao.getDepartment(brochuresForm.getCollegeCode(), brochuresForm.getSchCode()));
			session.setAttribute("quallist",dao.getQualification(brochuresForm.getCollegeCode()));
			session.setAttribute("speclist",dao.getSpecialization(brochuresForm.getQualCode()));
			session.setAttribute("catlist",dao.getCategoryList());

			return mapping.findForward("mychoice");
	}
	
	/**
	 * Method brochureType
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String brochureType(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 MychoiceDAO mychoiceDAO = new MychoiceDAO();
		 BrochureDAO dao = new BrochureDAO();
		 ActionMessages messages = new ActionMessages();
		 BrochuresForm brochuresForm = (BrochuresForm)form;
		 String colCode =brochuresForm.getCollegeCode();
		 String schCode = brochuresForm.getSchCode();
		 String dptCode = brochuresForm.getDptCode();
		 String catCode =brochuresForm.getCategory();
		 int year =Integer.parseInt(brochuresForm.getYear());
		 String type = request.getParameter("type");
		 brochuresForm.setType1(type);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date = new Date();
	     
	     String timeStamp = dateFormat.format(date);
	     String collName = mychoiceDAO.getCollegeName(colCode);
			try{
				    Document document =null;
				    String filename ="";
				    String filename1 ="";
				    String sourcePath = "";
				    
				  /* 
				   * Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
				   * Comment out this line of code to test on localhost, and uncomment the localhost path.
				   * 
				   */
				    
				  String path = getServlet().getServletContext().getInitParameter("mypath");
				   
				  /* 
				  * Path for localhost folder, that holds the path for myChoiceMD.xsl
				  * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
				  * 
				  */   
				   
				  //String path = "C:\\myChoiceMD\\";
				  
				  /* ****************************************************************************************************/ 
				  
				    if(type.equals("a")){
				    	boolean list = mychoiceDAO.ismyChoiceList(colCode, catCode, year, schCode, dptCode,brochuresForm.getResearchFlag(), brochuresForm.getHeqf(), brochuresForm.getNqfLevel(),brochuresForm.getQualCode());
					    if(list==true){
					    	messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("errors.message", "There is no data for your selection."));
					    	addErrors(request, messages); 
							return "mychoice";
					    }
					    
				    	document = mychoiceDAO.myChoiceXml(colCode, catCode, year, schCode, dptCode,brochuresForm.getResearchFlag(), brochuresForm.getHeqf(), brochuresForm.getNqfLevel(),brochuresForm.getQualCode());
				    	filename = "myChoice.xml";
				    	filename1 = "myChoice.doc";
				    	
				    	 getServlet().getServletContext().getInitParameter("mypath");
				    	 
				    	    	
				    	 
				    	 /* ***********************************************************************************************
						  * Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
						  * Comment out this line of code to test on localhost, and uncomment the localhost path.
						  * 
						  */
				    	 
				    	 //sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myChoice/mychoice.xsl"; 
				    	 
				    	 //						OR
				    	 
				    	 sourcePath =  ServerConfigurationService.getString("brochures.path")+"/myChoice/mychoice.xsl";
				    	 
				    	 /* ***************************************************************************************************
						  * Source Path for localhost folder, that holds the path for myChoice.xsl
						  * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
						  * 
						  */   
				    	
				    	 //sourcePath = "C:\\myChoice\\mychoice.xsl";
				    	 
				    	 /* ****************************************************************************************************/ 
				    	 
				    	brochuresForm.setType("myChoice");
				    	
				    }else if(type.equals("b")){
				    	boolean list = mychoiceDAO.ismyChoiceMandD(colCode, catCode, year, schCode, dptCode,brochuresForm.getResearchFlag(), brochuresForm.getHeqf(), brochuresForm.getNqfLevel(),brochuresForm.getQualCode());
					    if(list==true){
					    	messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("errors.message", "There is no data for your selection."));
					    	addErrors(request, messages); 
							return "mychoice";
					    }
					    
				    	document = mychoiceDAO.myChoiceMandD(colCode, catCode, year,schCode, dptCode,brochuresForm.getResearchFlag(), brochuresForm.getHeqf(), brochuresForm.getNqfLevel(),brochuresForm.getQualCode());
				    	filename = "myChoiceM&D.xml";
				    	filename1 = "myChoiceM&D.doc";
				    	
				    	/* 
						 * Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
						 * Comment out this line of code to test on localhost, and uncomment the localhost path.
						 * 
						 */
				    	
				    	sourcePath = ServerConfigurationService.getString("brochures.path")+"/myChoicemd/myChoiceMD.xsl";
				    	
				    	//			    	OR
				    	
				    	//sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myChoicemd/myChoiceMD.xsl";
				         
				    	/* 
						 * Source Path for localhost folder, that holds the path for myChoice.xsl
						 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
						 * 
						 */ 
				    	
				    	//sourcePath = "C:\\myChoicemd\\myChoiceMD.xsl";
				    	 
				    	/* ****************************************************************************************************/ 
				    	brochuresForm.setType("myChoice M&D");
				    }else if(type.equals("c")){
				    	brochuresForm.setType("myRegistration");
				    }
				
				    //String tempPath = "C:\\cms\\";
				 	File file= new File(path+filename);
				 	//File file = new File(tempPath+filename);
				 	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				
				 	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 
		        	Transformer transformer = transformerFactory.newTransformer();

		        	transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		        	DOMSource source1 = new DOMSource(document);
		        
		        	StreamResult result =  new StreamResult(output);
		        	transformer.transform(source1, result);
		 
		        	if (output != null){
		        		output.close();
		        	}
		        	
		        		        	
		        	if(brochuresForm.getFormat().equals("1")){
		        	
		        	Reader rdr = new InputStreamReader(new FileInputStream(path+filename), "ISO-8859-1");   
		        	Source source = new StreamSource(rdr);
		        	Source xsl = new StreamSource(sourcePath);
		            TransformerFactory factory = TransformerFactory.newInstance();
		            Templates template = factory.newTemplates(xsl);
		           
		           // File tmpFile = new File(tmpPathname);
		            
		            //String path ="c://drd//";
		            File tmpFile = new File(path+filename1);
		            
		            FileOutputStream file1 = new FileOutputStream(tmpFile);
		            Result result1 = new StreamResult(file1);
		            Transformer transformer1 = factory.newTransformer(xsl);
		            transformer1.setOutputProperty(OutputKeys.INDENT, "yes");
		            transformer1.transform(source, result1);	
		        	DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
					ServletOutputStream out = response.getOutputStream();
					response.setDateHeader("Expires", 0);
					response.setHeader( "Pragma", "public" );
					response.setContentType("application/octet-stream");
					response.setContentLength((int)tmpFile.length());
					response.addHeader("Content-Disposition", "attachment;filename=" + filename1 );
					saveToClient(in, out);
		        	} else if (brochuresForm.getFormat().equals("2")){
		        		File xmlfile = new File(path+filename);
						DataInputStream in = new DataInputStream(new FileInputStream(xmlfile));
						ServletOutputStream out = response.getOutputStream();
						response.setDateHeader("Expires", 0);
						response.setHeader( "Pragma", "public" );
						response.setContentType("application/octet-stream");
						response.setContentLength((int)file.length());
						response.addHeader("Content-Disposition", "attachment;filename=" + filename );
						saveToClient(in, out);
		        	}
		        	dao.setAuditReport(brochuresForm.getUserId(), timeStamp, brochuresForm.getType(),brochuresForm.getCollegeCode(),year, brochuresForm.getFormat());
			}catch(Exception e){
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		return null;
	}
	
	/**
	 * Method brochureType
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String myRegExport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 MychoiceDAO mychoiceDAO = new MychoiceDAO();
		 BrochureDAO dao = new BrochureDAO();
		 ActionMessages messages = new ActionMessages();
		 BrochuresForm brochuresForm = (BrochuresForm)form;
		 String reglevel = brochuresForm.getRegLevel();
		 String colCode =brochuresForm.getCollegeCode();
		 String catCode =brochuresForm.getCategory();
		 String qualCode =brochuresForm.getQualCode();
		 String specialization =brochuresForm.getSpec();
		 int year =Integer.parseInt(brochuresForm.getYear());
		 int repeatYear =Integer.parseInt(brochuresForm.getRepeatYear());
		 
		 //Added for phase 2 requirements
		 String school = brochuresForm.getSchCode();
		 String department = brochuresForm.getDptCode();
		 		 
		 String type = request.getParameter("type");
		 
		 brochuresForm.setType1(type);
		    if(type.equals("m")){
		    	brochuresForm.setType("myRegistration");
		    	brochuresForm.setRegLevel("UH");
		    }else if(type.equals("n")){
		    	brochuresForm.setType("myRegistration M&D");
		    	brochuresForm.setRegLevel("MD");
		    }
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date = new Date();
	     
	     String timeStamp = dateFormat.format(date);
	     String collName = mychoiceDAO.getCollegeName(colCode);
	     if(brochuresForm.getSpecRepeat().equals("-1")){
	    	 brochuresForm.setSpecRepeat("2");
	     }
			try{
				    Document document =null;
				    String filename ="";
				    String filename1 ="";
				    String filename2="";
				    String sourcePath = "";
				   
				    /* ****************************************************************************************************************
					 * Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
					 * Comment out this line of code to test on localhost, and uncomment the localhost path.
					 * 
					 */
				    
				    String path = getServlet().getServletContext().getInitParameter("mypath");
				    
				    /* ***************************************************************************************************************
					 * Source Path for localhost folder, that holds the path for myChoice.xsl
					 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
					 * 
					 */ 
				    //String path = "C:\\myRegistration\\";
				    
				    /* *************************************************************************************************************/
				    
				   if(brochuresForm.getFormat().equals("3")){
					   
				   /* ****************************************************************************************************************
				    * Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
					* Comment out this line of code to test on localhost, and uncomment the localhost path.
					* 
					*/ 
					   
					sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myRegistration/CCMOutput.xsl"; 
					   
					/* ***************************************************************************************************************
					 * Source Path for localhost folder, that holds the path for myChoice.xsl
					 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
					 * 
					*/ 
					   
					//sourcePath = "C:\\myRegistration\\CCMOutput.xsl";
					 
					 /* *************************************************************************************************************/
					 
				   }else{
					
				   /* ****************************************************************************************************************
					* Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
					* Comment out this line of code to test on localhost, and uncomment the localhost path.
					* 
					*/
					   
				    sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myRegistration/myRegistration.xsl";
					   
					/* ***************************************************************************************************************
					 * Source Path for localhost folder, that holds the path for myChoice.xsl
					 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
					 * 
					*/ 
					   
				   // sourcePath = "C:\\myRegistration\\myRegistration.xsl";
				    
				    /* **************************************************************************************************************/
				    
				   }
				   if(brochuresForm.getRegLevel().equals("UH")){
				   
				    document = mychoiceDAO.myRegistration(school, department, brochuresForm.getResearchFlag(), colCode, catCode, qualCode, specialization, year, repeatYear, brochuresForm.getSpecRepeat(), brochuresForm.getHeqf(), reglevel);
				   }else if(brochuresForm.getRegLevel().equals("MD")){
					   document = mychoiceDAO.myRegistrationMD(school, department, brochuresForm.getResearchFlag(), colCode, catCode, qualCode, specialization, year, repeatYear, brochuresForm.getSpecRepeat(), brochuresForm.getHeqf(), reglevel);
					   
				   }
				    if(mychoiceDAO.ismyRegistration()==true){
				    	messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("errors.message", "There is no data for your selection."));
				    	addErrors(request, messages); 
						return "myregistration";
				    }
				    
				    if(type.equals("m")){
				    	  filename = "myRegistration.xml";
					    	filename1 = "myRegistration.doc";
					    	filename2 = "CCMOutput.doc";
				    	
				    }else if (type.equals("n")){
				    	  filename = "myRegistrationM&D.xml";
					    	filename1 = "myRegistrationM&D.doc";
					    	filename2 = "CCMOutput.doc";
				    	
				    }
				    
				    
				 	File file= new File(path+filename);
				 	PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				
				 	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 
		        	Transformer transformer = transformerFactory.newTransformer();

		        	transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		        	DOMSource source1 = new DOMSource(document);
		        
		        	StreamResult result =  new StreamResult(output);
		        	transformer.transform(source1, result);
		 
		        	if (output != null){
		        		output.close();
		        	}
		        	
		        	if(brochuresForm.getFormat().equals("1")){
			        	
			        	Reader rdr = new InputStreamReader(new FileInputStream(path+filename), "ISO-8859-1");   
			        	Source source = new StreamSource(rdr);
			        	Source xsl = new StreamSource(sourcePath);
			            TransformerFactory factory = TransformerFactory.newInstance();
			            Templates template = factory.newTemplates(xsl);
			           
			           // File tmpFile = new File(tmpPathname);
			            
			            //String path ="c://drd//";
			            File tmpFile = new File(path+filename1);
			            
			            FileOutputStream file1 = new FileOutputStream(tmpFile);
			            Result result1 = new StreamResult(file1);
			            Transformer transformer1 = factory.newTransformer(xsl);
			            transformer1.setOutputProperty(OutputKeys.INDENT, "yes");
			            transformer1.transform(source, result1);	
			        	DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
						ServletOutputStream out = response.getOutputStream();
						response.setDateHeader("Expires", 0);
						response.setHeader( "Pragma", "public" );
						response.setContentType("application/octet-stream");
						response.setContentLength((int)tmpFile.length());
						response.addHeader("Content-Disposition", "attachment;filename=" + filename1 );
						saveToClient(in, out);
			        	} else if (brochuresForm.getFormat().equals("3")){
			        		Reader rdr = new InputStreamReader(new FileInputStream(path+filename), "ISO-8859-1");   
				        	Source source = new StreamSource(rdr);
				        	Source xsl = new StreamSource(sourcePath);
				            TransformerFactory factory = TransformerFactory.newInstance();
				            Templates template = factory.newTemplates(xsl);
				           
				           // File tmpFile = new File(tmpPathname);
				            
				            //String path ="c://drd//";
				            File tmpFile = new File(path+filename2);
				            
				            FileOutputStream file1 = new FileOutputStream(tmpFile);
				            Result result1 = new StreamResult(file1);
				            Transformer transformer1 = factory.newTransformer(xsl);
				            transformer1.setOutputProperty(OutputKeys.INDENT, "yes");
				            transformer1.transform(source, result1);	
				        	DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
							ServletOutputStream out = response.getOutputStream();
							response.setDateHeader("Expires", 0);
							response.setHeader( "Pragma", "public" );
							response.setContentType("application/octet-stream");
							response.setContentLength((int)tmpFile.length());
							response.addHeader("Content-Disposition", "attachment;filename=" + filename2 );
							saveToClient(in, out);
			        	  } else if (brochuresForm.getFormat().equals("2")){
			        		File xmlfile = new File(path+filename);
							DataInputStream in = new DataInputStream(new FileInputStream(xmlfile));
							ServletOutputStream out = response.getOutputStream();
							response.setDateHeader("Expires", 0);
							response.setHeader( "Pragma", "public" );
							response.setContentType("application/octet-stream");
							response.setContentLength((int)file.length());
							response.addHeader("Content-Disposition", "attachment;filename=" + filename );
							saveToClient(in, out);
			        	}
		        	
		        	
		     
		        	
		        	dao.setAuditReport(brochuresForm.getUserId(), timeStamp, brochuresForm.getType(),brochuresForm.getCollegeCode(),year, brochuresForm.getFormat());
			}catch(Exception e){
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		return null;
	}
	
	/**
	 * Method brochureType
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public String myModuleExport(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		 MychoiceDAO mychoiceDAO = new MychoiceDAO();
		 BrochureDAO dao = new BrochureDAO();
		 ActionMessages messages = new ActionMessages();
		 BrochuresForm brochuresForm = (BrochuresForm)form;
		 
		 String colCode =brochuresForm.getCollegeCode();
		 int year =Integer.parseInt(brochuresForm.getYear());
		 String type = request.getParameter("type");
		 brochuresForm.setType(type);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date = new Date();
	     brochuresForm.setType("myModules");
	     String timeStamp = dateFormat.format(date);
	  
			try{
				    Document document =null;
				    String filename ="";
				    String filename1 ="";
				    String sourcePath = "";
				  
				    
				    /* ****************************************************************************************************************
					* Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
					* Comment out this line of code to test on localhost, and uncomment the localhost path.
					* 
					*/
					   
				    String path = getServlet().getServletContext().getInitParameter("mypath");
					   
					/* ***************************************************************************************************************
					 * Source Path for localhost folder, that holds the path for myChoice.xsl
					 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
					 * 
					*/ 
					   
				    //String path = "C:\\myModules\\";
				    
				    /* ***************************************************************************************************************/
				    
				 
				    /* ****************************************************************************************************************
					* Source Path into the DEV, QA, and PROD folder, that holds the path for myChoiceMD.xsl
					* Comment out this line of code to test on localhost, and uncomment the localhost path.
					* 
					*/
				    
				    sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myModules/myModules.xsl";
				    
				    /* ****************************************************************************************************************
					 * Source Path for localhost folder, that holds the path for myChoice.xsl
					 * Comment out this line of code when finished testing on localhost, and uncomment the server path above.
					 * 
					*/ 
				   // sourcePath = "C:\\myModules\\myModules.xsl";
				 	
				    /* ***************************************************************************************************************/
				    
				    document = mychoiceDAO.myModuleXml(colCode, brochuresForm.getSchCode(), 
							brochuresForm.getDptCode(),brochuresForm.getModule(), brochuresForm.getSubCode(), year);
				  		   
				    
				   if(mychoiceDAO.ismyModules()==true){
				    	messages.add(ActionMessages.GLOBAL_MESSAGE,
								new ActionMessage("errors.message", "There is no data for your selection."));
				    	addErrors(request, messages); 
						return "mymodules";
				    }
				    	filename = "myModules.xml";
				        filename1 = "myModules.doc";
				    	//filename1 = "myChoice.doc";
				    	// getServlet().getServletContext().getInitParameter("mypath");
				    	//sourcePath = ServerConfigurationService.getString("brochures.path")+"/myChoice/mychoice.xsl";
				    	// sourcePath =  getServlet().getServletContext().getInitParameter("brochures")+"/myChoice/mychoice.xsl";
				    	//brochuresForm.setType("myChoice");
				  
				
				  
				 	File file= new File(path+filename);
				    PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(file)));
				
				 	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	 
		        	Transformer transformer = transformerFactory.newTransformer();

		        	transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		        	DOMSource source1 = new DOMSource(document);
		        
		        	StreamResult result =  new StreamResult(output);
		        	transformer.transform(source1, result);
		 
		        	if (output != null){
		        		output.close();
		        	}
		        	
		        	if(brochuresForm.getFormat().equals("1")){
			        	
			        	Reader rdr = new InputStreamReader(new FileInputStream(path+filename), "ISO-8859-1");   
			        	Source source = new StreamSource(rdr);
			        	Source xsl = new StreamSource(sourcePath);
			            TransformerFactory factory = TransformerFactory.newInstance();
			            Templates template = factory.newTemplates(xsl);
			           
			           // File tmpFile = new File(tmpPathname);
			            
			            //String path ="c://drd//";
			            File tmpFile = new File(path+filename1);
			            
			            FileOutputStream file1 = new FileOutputStream(tmpFile);
			            Result result1 = new StreamResult(file1);
			            Transformer transformer1 = factory.newTransformer(xsl);
			            transformer1.setOutputProperty(OutputKeys.INDENT, "yes");
			            transformer1.transform(source, result1);	
			        	DataInputStream in = new DataInputStream(new FileInputStream(tmpFile));
						ServletOutputStream out = response.getOutputStream();
						response.setDateHeader("Expires", 0);
						response.setHeader( "Pragma", "public" );
						response.setContentType("application/octet-stream");
						response.setContentLength((int)tmpFile.length());
						response.addHeader("Content-Disposition", "attachment;filename=" + filename1 );
						saveToClient(in, out);
			}else if(brochuresForm.getFormat().equals("2")){
			        		File xmlfile = new File(path+filename);
							DataInputStream in = new DataInputStream(new FileInputStream(xmlfile));
							ServletOutputStream out = response.getOutputStream();
							response.setDateHeader("Expires", 0);
							response.setHeader( "Pragma", "public" );
							response.setContentType("application/octet-stream");
							response.setContentLength((int)file.length());
							response.addHeader("Content-Disposition", "attachment;filename=" + filename );
							saveToClient(in, out);
			     
			}
		        	
		     
		        	
		        	dao.setAuditReport(brochuresForm.getUserId(), timeStamp, brochuresForm.getType(),brochuresForm.getCollegeCode(),year, brochuresForm.getFormat());
			}catch(Exception e){
				//TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		return null;
	}
	/**
	 * Method myRegistration
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward myRegistration(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    HttpSession session = request.getSession(true);
		    MychoiceDAO dao =new MychoiceDAO();
			 Cookie[] cookies= request.getCookies();
		     for(int i=0; i < cookies.length; i++)  {
		    	 Cookie thisCookie = cookies[i];
		    	 if (thisCookie.getName().equals("novelluser")){
			                 brochuresForm.setUserId(thisCookie.getValue());
		    	 }
      
			 }
		    String type = request.getParameter("type");
		    brochuresForm.setType1(type);
		    if(type.equals("m")){
		    	brochuresForm.setType("myRegistration");
		    	brochuresForm.setRegLevel("UH");
		    }else if(type.equals("n")){
		    	brochuresForm.setType("myRegistration M&D");
		    	brochuresForm.setRegLevel("MD");
		    }

	        session.setAttribute("colleges",dao.getColleges());
			session.setAttribute("schools",dao.getSchools(brochuresForm.getCollegeCode()));
			session.setAttribute("yearsList",brochuresForm.getYearsList());
			if(brochuresForm.getCollegeCode() != null) {
				
				System.out.println("lets see  college when college is not selected :  " + brochuresForm.getCollegeCode());
				
			}
			
			if (brochuresForm.getSchCode() != null){
				System.out.println("lets see  school  :  " + brochuresForm.getSchCode());
			}
			
			session.setAttribute("catlist",dao.getCategoryList());
			session.setAttribute("quallist",dao.getQualification(brochuresForm.getCollegeCode()));
			session.setAttribute("speclist",dao.getSpecialization(brochuresForm.getQualCode()));
			session.setAttribute("repeatYearList",brochuresForm.getFromYearList());
			
			//Modified to add to the bean
			session.setAttribute("departmentL",dao.getDepartment(brochuresForm.getCollegeCode(), brochuresForm.getSchCode()));
			session.setAttribute("researchFlag",brochuresForm.getResearchFlag());
			
			return mapping.findForward("myregistration");
	}
	
	/**
	 * Method myRegistration
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward myModules(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    HttpSession session = request.getSession(true);
		    MychoiceDAO dao =new MychoiceDAO();
			 Cookie[] cookies= request.getCookies();
		     for(int i=0; i < cookies.length; i++)  {
		    	 Cookie thisCookie = cookies[i];
		    	 if (thisCookie.getName().equals("novelluser")){
			                 brochuresForm.setUserId(thisCookie.getValue());
		    	 }
      
			 }
		
		    brochuresForm.setType("myModules");
	        session.setAttribute("colleges",dao.getColleges());
			session.setAttribute("schList",dao.getSchools(brochuresForm.getCollegeCode()));
			session.setAttribute("yearsList",brochuresForm.getYearsList());
			session.setAttribute("dptList",dao.getDepartment(brochuresForm.getCollegeCode(), brochuresForm.getSchCode()));
			session.setAttribute("moduleList",dao.getModules(brochuresForm.getCollegeCode(), brochuresForm.getSchCode(), 
		    brochuresForm.getDptCode(), brochuresForm.getYear()));
			session.setAttribute("subjectsList",dao.getSubjects(brochuresForm.getModule()));
			return mapping.findForward("mymodules");
	}
	
	/*
	/*
	 * to get audit report
	 */
	public ActionForward auditReport(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response) throws Exception {
		      HttpSession session = request.getSession(true);

		      MychoiceDAO mychoiceDAO = new MychoiceDAO();
			  BrochureDAO dao = new BrochureDAO();
			  ActionMessages messages = new ActionMessages();
			  BrochuresForm brochuresForm = (BrochuresForm)form;
				

		            String path = getServlet().getServletContext().getInitParameter("mypath");
			       // String path ="c://drd//";
		            String filename = "Audit_Report.xls";
		            String pathName= path+filename;
			
			    	saveToServer(pathName,brochuresForm);
			    	 if ((brochuresForm.getExportList().size())==0){
				    	  messages.add(ActionMessages.GLOBAL_MESSAGE,
									new ActionMessage("export.nodata"));
							  addErrors(request, messages); 
							return mapping.findForward("mainmenu");
				    	  
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
					
		   
			  return  null;
	}
	
	public void saveToServer(String filename, BrochuresForm brochuresForm) throws Exception {
		  
		
		File fileobject = new File(filename);
		FileWriter fw = new FileWriter(fileobject);
		ActionMessages messages = new ActionMessages();
		String seperatewith = "";
	    MychoiceDAO mychoiceDAO = new MychoiceDAO();
	    BrochureDAO dao = new BrochureDAO();
	     List list = dao.getAuditExport();
	     brochuresForm.setExportList(list);
	     seperatewith = "\t";
	     List auditExport = (List)list;	
	     
	    
		 fw.write("User ID"+seperatewith+" Generation Date "+seperatewith+" Report Type "+seperatewith+" Report "+seperatewith+" College "+seperatewith+" Format "+seperatewith+" Year generated for "+seperatewith+" \n");
			for(int i=0; i < auditExport.size(); i++) {
		    fw.write(((BrochureDetails)auditExport.get(i)).getUserid()+seperatewith);
		    fw.write(((BrochureDetails)auditExport.get(i)).getDate()+seperatewith);	
		    fw.write(((BrochureDetails)auditExport.get(i)).getReportType()+seperatewith);	
		    fw.write(((BrochureDetails)auditExport.get(i)).getReport()+seperatewith);	
		    fw.write(((BrochureDetails)auditExport.get(i)).getCollege()+seperatewith);	
		    fw.write(((BrochureDetails)auditExport.get(i)).getFormat()+seperatewith);	
		    fw.write(((BrochureDetails)auditExport.get(i)).getYear()+seperatewith+"\n");
		 
			}
		fw.flush();
		fw.close();
	}
	/*
	 * go to Back
	 */
	public String back1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    brochuresForm.setCategory("-1");
		    brochuresForm.setCollegeCode("-1");
		    brochuresForm.setFormat("-1");
			return "mainmenu";
	}
	
	public String back2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    brochuresForm.setCategory("-1");
		    brochuresForm.setCollegeCode("-1");
		    brochuresForm.setFormat("-1");
		    brochuresForm.setQualCode("-1");
		    brochuresForm.setSpec("-1");
			return "mainmenu";
	}
	public String back3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    brochuresForm.setSchCode("-1");
		    brochuresForm.setCollegeCode("-1");
		    brochuresForm.setModule("-1");
		    brochuresForm.setDptCode("-1");
		    brochuresForm.setSubCode("-1");
			return "mainmenu";
	}
	public ActionForward clearAll(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    BrochuresForm brochuresForm = (BrochuresForm)form;
		    brochuresForm.setCategory("-1");
		    brochuresForm.setCollegeCode("-1");
		    brochuresForm.setFormat("-1");
		    brochuresForm.setQualCode("-1");
		    brochuresForm.setSpec("-1");
		  if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
				  brochuresForm.setSchCode("-1");
				    brochuresForm.setCollegeCode("-1");
				    brochuresForm.setModule("-1");
				    brochuresForm.setDptCode("-1");
				    brochuresForm.setSubCode("-1");
				 return mapping.findForward("mymodules");
			}
		    return mapping.findForward("myregistration");
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
	/*
	 * remove leading whitespace 
	 */
	 public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	     }

	    /* remove trailing whitespace */
	 public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
}
