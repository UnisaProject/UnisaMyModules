package za.ac.unisa.lms.tools.uploadmanager.actions;
import impl.BasicFile;
import impl.StudyMaterial;
import impl.Email;
import impl.LoginManager;
import impl.PdfDownloader;
import module.StudyMaterialModule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.upload.FormFile;
import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.event.api.UsageSessionService;
import org.sakaiproject.tool.api.SessionManager;

import utils.MetaDataUtils;
import utils.PdfDownloadUtil;
import utils.Utilities;
import za.ac.unisa.lms.tools.uploadmanager.dao.ModuleDAO;
import za.ac.unisa.lms.tools.uploadmanager.dao.StudyMaterialDAO;
import za.ac.unisa.lms.tools.uploadmanager.forms.UploadManagerForm;
import UILayer.LoginManagerUI;
import UILayer.PdfUploaderUI;
import UILayer.PdfViewerUI;
import java.util.UUID;
import org.apache.commons.io.IOUtils;

public class StudyMaterialAction extends LookupDispatchAction {

	private EmailService emailService;
	private static Log logger = LogFactory.getLog(StudyMaterialAction.class);
	 
	//the code for removing white space problem when you use ENTER button instead of mouse .
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
          if (request.getParameter("act") == null){ 
        	  return materialFilter(mapping, form, request, response);
          }
          return super.execute(mapping, form, request, response);           
    }
    private SessionManager sessionManager;
	private UsageSessionService usageSessionService;
	private Log log = LogFactory.getLog(this.getClass());
	protected Map getKeyMethodMap(){
		Map map = new HashMap();
		map.put("ajaxCall", "ajaxCall");
		map.put("materialFilter","materialFilter");
		map.put("button.upload","uploadMaterial");
		map.put("button.view","viewMaterial");
		//map.put("button.upload","uploadPdf");
		map.put("upload","upload");
		map.put("view","view");
		map.put("reload","doReload");
		map.put("button.cancel","cancel");
		map.put("download","download"); 
		map.put("button.search","goToSearch");
		map.put("button.next","goNext");
		map.put("button.back","goToMainFilter");
		map.put("goToMainFilter","goToMainFilter");
		map.put("email","email");
		
		return map;
	}
	
	/**
	 * Method Cancel
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	
	public ActionForward ajaxCall(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		
		 PrintWriter out=response.getWriter();
		 
		 out.print("Welcome to Ajax world".toString());
		 logger.info("testing ajax call---------");
	
		return null;
	}
	

	/**
	 * Method submit
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward uploadMaterial(
		                     ActionMapping mapping,
		                     ActionForm form,
		                     HttpServletRequest request,
		                     HttpServletResponse response)throws Exception {
		                          ActionMessages messages = new ActionMessages();
                                  UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
                                  PdfUploaderUI   PdfUploaderUI =new  PdfUploaderUI ();                        
                                  if ("upload".equalsIgnoreCase(request.getParameter("atStep"))||
                                		  ("reload".equalsIgnoreCase(request.getParameter("atStep")))){
                                           
                                         
                                    		//Get the servers upload directory real path name
                                    	    String filePath = Utilities.tempFolderLocation();
                                    		FormFile file = uploadManagerForm.getFile();
                                    		BasicFile inputFileInfo = null;
                                    		if (file == null) {
                                    		   inputFileInfo=getInputFilepath(request);
                                    		   uploadManagerForm.setFile(null);
                                    		} else {
                                    		
                                    		String fileName = file.getFileName();
                                    		String fileNamePath = null;
                                    			    
                                    	    if (!("").equals(fileName)) {  
                                    		    String tempFileName = ""+UUID.randomUUID()+".pdf";
                                    			File newFile = new File(filePath, tempFileName);
                                    			fileNamePath = newFile.getAbsolutePath();

                                    			
                                    			 if (!newFile.exists()){
                                    			     FileOutputStream fos = new FileOutputStream(newFile);
                                    			   /*  byte[] data = null; //new byte[size]; // allocate byte array of right size
                                    			     int size = (int) file.getFileSize();
                                    			     data = new byte[size];
                                    			     FileInputStream fis = (FileInputStream) file.getInputStream();
                                    	             BufferedInputStream bis = new BufferedInputStream(fis);
                                    	             bis.read(data, 0, size);*/
                                    	             IOUtils.write(file.getFileData(), fos);
                                    			     // fos.write(data);
                                    			     fos.flush();
                                    			     fos.close();
                                    			   }  
                                    			    uploadManagerForm.setFile(null);
                                    			    request.setAttribute("uploadedFilePath",newFile.getAbsoluteFile());
                                    			    request.setAttribute("uploadedFileName",newFile.getName());
                                    			    }
                                    			    
                                    			  
                                    			    
                                    			     inputFileInfo = new  BasicFile();
                                    			    inputFileInfo.setFilename(fileName);;
                                    			    inputFileInfo.setFilepath(fileNamePath);
                                    		}
                                            uploadManagerForm.setFileName(inputFileInfo.getFilename());
		                                    uploadManagerForm.setInputFilePath(inputFileInfo.getFilepath());
		                                    uploadManagerForm.setUserAction(request.getParameter("atStep"));
		                          }
                                  String nextScreen = null;
                    /*              if(uploadManagerForm != null && uploadManagerForm.getMaterialList() !=null
                                		  ){
                                	  for ( Object ds : uploadManagerForm.getMaterialList()) {
                                		  System.out.println("Document Available Date ---0>"+ ((StudyMaterialModule)ds).getDateavailable());
                                		 
                                	  }
                                	  
                                  }*/
                                  
                                  try {
		                                  nextScreen= PdfUploaderUI.processUploadRequest(request, uploadManagerForm, messages);
	                               } catch(Exception e) {
	                            	   log.error("System error--->"+e);
	                            	   e.printStackTrace();
	                            	   messages.add( ActionMessages.GLOBAL_MESSAGE,
	   		                                 new ActionMessage("errors.message",e.getMessage()));
	                            	   nextScreen = "uploadscreen";
	                               }
                                  if(!messages.isEmpty()){
                             	         addErrors(request, messages);
                                  }
     	                        return mapping.findForward(nextScreen);
	}



	private  BasicFile  getInputFilepath(HttpServletRequest request){ 
		

		 
		               String inputFileName = request.getAttribute("file").toString();
		               String [] input = inputFileName.split(",");
		               String[] parseInput;
		               String extensions = "pdf";
		               String[] extList = extensions.split(",");
		               BasicFile  pdfUtil=new  BasicFile();
		               for (int i=0; i < input.length; i++){
			                    parseInput = input[i].split("=");
			                    if (parseInput.length > 1){
				                     if (i == 0){
					                      String tmp = parseInput[1];
					                      if (parseInput[1].lastIndexOf("/") > 0 ){
						                        tmp = parseInput[1].substring(parseInput[1].lastIndexOf("/")+1);
					                      }
					                      pdfUtil.setFilename(tmp);
				                     } else if (i == 1){
				                    	 pdfUtil.setFilepath(parseInput[1]);
				                     }
			                     }
		               }
		               request.setAttribute("inputFileName", inputFileName);
		               return pdfUtil;
	}
	/**
	 * Method Cancel
	 * 		
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward cancel(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)throws Exception {
		
		String nextScreen = "";
		UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
		if ("1".equalsIgnoreCase(request.getParameter("atStep"))){
			if(uploadManagerForm.getFrom().equals("staff")){
				nextScreen= cancel3(mapping,form,request, response);
			}else{		
				    nextScreen= cancel1(mapping,form,request, response);
			}
		} else if ("upload".equalsIgnoreCase(request.getParameter("atStep"))){
			        nextScreen = cancel2(mapping,form,request, response);
		} else if ("reload".equalsIgnoreCase(request.getParameter("atStep"))){
			        nextScreen = cancel2(mapping,form,request, response);
		} else if ("3".equalsIgnoreCase(request.getParameter("atStep"))){
			        nextScreen = cancel3(mapping,form,request, response);
		} 
		return mapping.findForward(nextScreen);
	}
	
	public String cancel1(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
			
			uploadManagerForm.setLanguage("-1");
			//uploadManagerForm.setCourseCode("-1");
			uploadManagerForm.setLanguage("-1");
			uploadManagerForm.setType("-1");
			uploadManagerForm.setPeriod("-1");
			uploadManagerForm.setAcadYear("-1");
			if (!"Yes".equals(uploadManagerForm.getSearch())) {
				uploadManagerForm.setCourseCode("-1");
			}
					
			return "mainfilter";
			
		}
	public ActionForward  goToMainFilter(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
			
		//	uploadManagerForm.setLanguage("-1");
			//uploadManagerForm.setCourseCode("-1");
		//	uploadManagerForm.setLanguage("-1");
			uploadManagerForm.setType("-1");
		//	uploadManagerForm.setPeriod("-1");
			uploadManagerForm.setSearch("");
			
			if (uploadManagerForm.getFrom().equals("myUnisa")) {
				return mapping.findForward("mainfilter");
			}
			return mapping.findForward("search");
			
		}
	public String cancel2(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
			
			String inputFileName = "";

			try{
				inputFileName = request.getAttribute("file").toString();
			} catch (NullPointerException ne){
				
			}
			inputFileName = "";		
			StudyMaterial studyMaterial=new StudyMaterial();
            studyMaterial.setMetaDataToFormBean(uploadManagerForm);
		
			return "uploadscreen";	
		
			
		}
	public String cancel3(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
			
			uploadManagerForm.setSearch("");
			uploadManagerForm.setSearchCode("");
			
			return "search";
			
		}
	public ActionForward materialFilter(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		              UploadManagerForm uploadManagerForm = (UploadManagerForm)form;
	       		      ActionMessages messages = new ActionMessages();
	       		      uploadManagerForm.setSearch("");
	       		      LoginManagerUI loginManagerUI=new LoginManagerUI();
	       		      String nextScreen= loginManagerUI.createMainScreen(request,uploadManagerForm, usageSessionService, messages);
	       		      if(!messages.isEmpty()){
	       		    	    addErrors(request, messages);
	       		      }
	       		      
	       		      
			     return mapping.findForward(nextScreen);
	}
	public ActionForward goToSearch(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
		    HttpSession session = request.getSession(true);
		    ActionMessages messages = new ActionMessages();
		    ModuleDAO dao = new ModuleDAO();
		    String code = request.getParameter("searchCode");
			if (code==null||code.equals("")){					
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("searchCode", "Please enter the first three letters of course code."));
		    	addErrors(request, messages); 
		    	 return mapping.findForward("search");
		
			}
			 ArrayList moduleList = dao.getAllModuleCodes(uploadManagerForm.getSearchCode());
			 if (moduleList.isEmpty()){					
					messages.add(ActionMessages.GLOBAL_MESSAGE,
							new ActionMessage("errors.message", "There is no modules for entered text."));
			    	addErrors(request, messages); 
			    	 return mapping.findForward("search");
			
				}
			session.setAttribute("courseCodeList", moduleList);
			uploadManagerForm.setSearch("Yes");
			uploadManagerForm.setFrom("staff");
			
			return mapping.findForward("search");
		}
	public ActionForward goNext(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
		    HttpSession session = request.getSession(true);
		    StudyMaterialDAO dao = new StudyMaterialDAO();
		    ActionMessages messages = new ActionMessages();
		    String courseCode = uploadManagerForm.getSelectedCourseCode();
		    uploadManagerForm.setCourseCode(courseCode);
		    //uploadManagerForm.setFromSearch(request.getParameter("from"));
		    session.setAttribute("yearList",uploadManagerForm.getFromYearList());
		    if (courseCode==null||courseCode.equals("")){					
				messages.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("selectedCourseCode", "Please choose course code."));
		    	addErrors(request, messages); 
		    	 return mapping.findForward("search");
		
			}
		    LoginManager ploadManagerUser=new LoginManager();
		 //   ploadManagerUser.setUserPrivileges("arlowm",uploadManagerForm);
		    uploadManagerForm.setFrom("staff");
			return mapping.findForward("mainfilter");
		}
	public ActionForward viewMaterial(ActionMapping mapping,
			                   ActionForm form,
			                   HttpServletRequest request,
			                   HttpServletResponse response) throws Exception {
	                                   UploadManagerForm uploadManagerForm = (UploadManagerForm)form;
		                               ActionMessages messages = new ActionMessages();
		                               PdfViewerUI pdfViewerUI=new PdfViewerUI();
		                               String nextScreen = null;
		                               try {
 		                                  nextScreen= pdfViewerUI.createViewStudyMaterialScreen(uploadManagerForm, messages);
		                               } catch(Exception e) {
		                            	   
		                            	   System.out.println("System error--->"+e);
		                            	   e.printStackTrace();
		                            	   
		                            	   messages.add( ActionMessages.GLOBAL_MESSAGE,
		   		                                 new ActionMessage("errors.message",e.getMessage()));
		                            	   nextScreen = "viewstudymaterial";
		                               }
 		                               
 		                               if(!messages.isEmpty()){
 		    	                             addErrors(request, messages);
 		                               }

			              		   return mapping.findForward(nextScreen);
	}

	public ActionForward upload(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    UploadManagerForm uploadManagerForm= (UploadManagerForm)form;		    
		    uploadManagerForm.setMethod("upload");
		    uploadManagerForm.setBarcode(request.getParameter("barcode"));
		    uploadManagerForm.setFile_name(request.getParameter("filename"));
		    uploadManagerForm.setMod_code(request.getParameter("module"));
		    
		    String docType = request.getParameter("docType");
		    uploadManagerForm.setDocumentType(docType);
		    if(docType.equals("TL")){
		    	 uploadManagerForm.setDoc_type("Tutorial Letter");
		    }else if(docType.equals("GD")){
		   	 uploadManagerForm.setDoc_type("Study Guides");
		    }else if(docType.equals("MA")){
		   	 uploadManagerForm.setDoc_type("Manuals");
		    }else if(docType.equals("WB")){
			   	 uploadManagerForm.setDoc_type("Workbooks");
		    }else if(docType.equals("QB")){
			   	 uploadManagerForm.setDoc_type("Question Bank");
			}
		    else if(docType.equals("MO"))
			{
			   	 uploadManagerForm.setDoc_type("Florida Module");
		    }
			else if(docType.equals("LB"))
		    {
			   	 uploadManagerForm.setDoc_type("Logbook");
			}else if(docType.equals("BL")){
			   	 uploadManagerForm.setDoc_type("Booklet");
			}else if(docType.equals("BB")){
			   	 uploadManagerForm.setDoc_type("Basic Business Calculations");
			}else if(docType.equals("HL")){
			   	 uploadManagerForm.setDoc_type("H Law Documents");
			}else if(docType.equals("RE")){
			   	 uploadManagerForm.setDoc_type("Reader");
			}else if(docType.equals("MG")){
			   	 uploadManagerForm.setDoc_type("Mentor Guides");
			}
		    
		    String lang = request.getParameter("lang");
		    if(lang.equals("A")){
		    	 uploadManagerForm.setLang("Afrikans");
		    }else if(lang.equals("E")){
		    	 uploadManagerForm.setLang("English");
		    }else if(lang.equals("B")){
		    	 uploadManagerForm.setLang("Both");
		    }
		    
		    String period = uploadManagerForm.getPeriod();
		    
		    if(period.equals("1")){
		    	 uploadManagerForm.setMaterialPeriod("First Semester");
		    }else if(period.equals("2")){
		   	 uploadManagerForm.setMaterialPeriod("Second Semester");
		    }else if(period.equals("7")){
		   	 uploadManagerForm.setMaterialPeriod("Year");
		    }
		    
		    uploadManagerForm.setItemfulldesc(request.getParameter("itemfulldesc"));
			return mapping.findForward("fileupload");
	}
	
	
	public ActionForward doReload(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		    UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
		    
		    uploadManagerForm.setMethod("reload");
		    uploadManagerForm.setBarcode(request.getParameter("barcode"));
		    uploadManagerForm.setFile_name(request.getParameter("filename"));
		    uploadManagerForm.setMod_code(request.getParameter("module"));
		    
		    String docType = request.getParameter("docType");
		    uploadManagerForm.setDocumentType(docType);
		    if(docType.equals("TL")){
		    	 uploadManagerForm.setDoc_type("Tutorial Letter");
		    }else if(docType.equals("GD")){
		   	 uploadManagerForm.setDoc_type("Study Guides");
		    }else if(docType.equals("MA")){
		   	 uploadManagerForm.setDoc_type("Manuals");
		    }else if(docType.equals("WB")){
			   	 uploadManagerForm.setDoc_type("Workbooks");
		    }else if(docType.equals("QB")){
			   	 uploadManagerForm.setDoc_type("Question Bank");
			}else if(docType.equals("MO")){
			   	 uploadManagerForm.setDoc_type("Florida Module");
		    }else if(docType.equals("LB")){
			   	 uploadManagerForm.setDoc_type("Logbook");
			}else if(docType.equals("BL")){
			   	 uploadManagerForm.setDoc_type("Booklet");
			}else if(docType.equals("BB")){
			   	 uploadManagerForm.setDoc_type("Basic Business Calculations");
			}else if(docType.equals("HL")){
			   	 uploadManagerForm.setDoc_type("H Law Documents");
			}else if(docType.equals("RE")){
			   	 uploadManagerForm.setDoc_type("Reader");
			}else if(docType.equals("MG")){
			   	 uploadManagerForm.setDoc_type("Mentor Guides");
			}
		    
		    String lang = request.getParameter("lang");
		    
		    if(lang.equals("A")){
		    	 uploadManagerForm.setLang("Afrikans");
		    }else if(lang.equals("E")){
		    	 uploadManagerForm.setLang("English");
		    }else if(lang.equals("B")){
		    	 uploadManagerForm.setLang("Both");
		    }
		    
		    String period = uploadManagerForm.getPeriod();
		    
		    if(period.equals("1")){
		    	 uploadManagerForm.setMaterialPeriod("First Semester");
		    }else if(period.equals("2")){
		   	 uploadManagerForm.setMaterialPeriod("Second Semester");
		    }else if(period.equals("7")){
		   	 uploadManagerForm.setMaterialPeriod("Year");
		    }
		    
		    uploadManagerForm.setItemfulldesc(request.getParameter("itemfulldesc"));
		    request.getParameter("period");
			
			
			return mapping.findForward("fileupload");
	}
	
	
	public ActionForward view(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
						String itembarcode = request.getParameter("itembarcode");
			            String itemshortdesc = request.getParameter("itemshortdesc");
			            uploadManagerForm.setMod_code(request.getParameter("module"));
						String filename = MetaDataUtils.getfileName(itemshortdesc);
			          //  String modCode = uploadManagerForm.getCourseCode();
						String modCode = request.getParameter("module");
			            String type =  MetaDataUtils.getType(itemshortdesc);
			            type = Utilities.getStudyMaterialTypeDirectoryName(type);
			       		String pdffullPath = PdfDownloadUtil.getFilepath(itembarcode,modCode,type,filename);
			       	    PdfViewerUI   pdfViewerUI=new  PdfViewerUI();
			       	    pdfViewerUI.outPutDataToClient(response, pdffullPath);
			       	    
		  		       return null;
	}
	public ActionForward download(
			               ActionMapping mapping,
			               ActionForm form,
			               HttpServletRequest request,
			               HttpServletResponse response)throws Exception {
			                             UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
			                             String itembarcode = request.getParameter("itembarcode");
			                             String itemshortdesc = request.getParameter("itemshortdesc");
			                             String filename =  MetaDataUtils.getfileName(itemshortdesc);
			                             String type =  MetaDataUtils.getType(itemshortdesc);
			                             type = Utilities.getStudyMaterialTypeDirectoryName(type);
			                             //String modCode = uploadManagerForm.getCourseCode();
			                             String modCode = request.getParameter("module");
                  		                 String pdffullPath = PdfDownloadUtil.getFilepath(itembarcode,modCode,type,filename);
       	    			                 PdfDownloader  pdfDownloader=new  PdfDownloader();
			                             pdfDownloader.sendPdfDataToClient(response, pdffullPath, filename);
			
			          return null;
			
		}
	
	public ActionForward email(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception {
			          UploadManagerForm uploadManagerForm= (UploadManagerForm)form;
					  String itembarcode = request.getParameter("itembarcode");
                      String itemshortdesc = request.getParameter("itemshortdesc");
                      String filename =  MetaDataUtils.getfileName(itemshortdesc);
                      String type =  MetaDataUtils.getType(itemshortdesc);
                      type = Utilities.getStudyMaterialTypeDirectoryName(type);
                      //String modCode = uploadManagerForm.getCourseCode();
                      String modCode = request.getParameter("module");
                      String pdffullPath = PdfDownloadUtil.getFilepath(itembarcode,modCode,type,filename);
                 	  Email  email=new Email();
                 	  String emailAddress=uploadManagerForm.getUserId()+"@unisa.ac.za";
			          email.sendEmail(emailService, pdffullPath, filename, emailAddress);
			       	  ActionMessages messages = new ActionMessages();
                      messages.add(ActionMessages.GLOBAL_MESSAGE,
					               new ActionMessage("errors.message", "Study material has been sent to your email address"));
                        addMessages(request, messages);
			return mapping.findForward("viewstudymaterial");
			
		}
	

	
	
	    
   
 

}
