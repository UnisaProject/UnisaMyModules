package org.sakaiproject.studymaterial.impl;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.sakaiproject.studymaterial.ui.StudyMaterialUI;
import org.sakaiproject.studymaterial.utils.SortUtil;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.sakaiproject.studymaterial.dao.StudentSystemQueryDAO;
import org.sakaiproject.studymaterial.dao.StudyMaterialDAO;
import org.sakaiproject.studymaterial.module.StudyMaterialModel;

import uk.org.ponder.rsf.components.UIContainer;
import za.ac.unisa.utils.CoursePeriodLookup;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.sakaiproject.email.api.EmailService;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import com.unisa.uploadermanager.jaxb.beans.ResourceDTO; 

public class StudyMaterial {
	
	private EmailService emailService;
	
	
  private List getStudyMaterialList(String courseId,String academicYear,short semester){
			           List<StudyMaterialModel> studyMaterialList = new ArrayList<StudyMaterialModel>();
		               try{
		                   StudyMaterialDAO scmXmlDataGetter=new StudyMaterialDAO();
		                   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				     	   DocumentBuilder builder = factory.newDocumentBuilder();
				     	   InputStream streamWithXmlData = scmXmlDataGetter.getStudyInfoDataInXML(courseId, academicYear, semester);
				     	   Document document =  builder.parse(streamWithXmlData);
				     	   document.getDocumentElement().normalize();
				  		   studyMaterialList = new ArrayList<StudyMaterialModel>();
				  		   NodeList nodeList = document.getElementsByTagName("Resource");
				  		   for (int i = 0; i < nodeList.getLength(); i++) {
				  	              Node node = nodeList.item(i);
				  		          if (node instanceof Element) {
				  		        	      StudyMaterialModel  studMaterial=new StudyMaterialModel();
				  		        	      setStudyMaterialData(studMaterial,node); 
				  		            	 if(studMaterial.isStudyMaterialValid()){
				  		            		 
				  		            		 //check for unavailable files and send e mail if file is not there
				  		            		 
				  		            		 if(studMaterial.getFileSize().equalsIgnoreCase("unavailable")){
				  		            			 
				  		            			sendEmailToFixFiles(courseId,studMaterial.getItemShortDesc());
				  		            			
				  		            		 }else{		            		 
				  		            			 
				  		        	             studyMaterialList.add(studMaterial);
				  		            		 }
				  		        	     }
				  		          } 
				  		        
				  		   }
				  		  }catch(Exception ex){
		            	      ex.printStackTrace();
		            	      StudyMaterialModel studyMaterial=new StudyMaterialModel();
		            	      studyMaterial.setItemBarcode("-1");
		            	      studyMaterialList.add(studyMaterial);
		            	      return studyMaterialList; 
		               }
		               SortUtil sortUtil=new SortUtil();
		               return sortUtil.sortByDate(studyMaterialList);
		}
  
  //method to send e mail if file is not available 
  public void sendEmailToFixFiles(String course, String shortDescription) throws Exception {
	  
	  String emailAddress = "unavailableSM@unisa.ac.za"; 
	  emailService=(EmailService) ComponentManager.get(EmailService.class);
	  String subject = course+""+shortDescription; 
	  String body=""; 
	  
	  String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom"); 
	  InternetAddress toEmail = new InternetAddress(emailAddress); 
	  InternetAddress iaTo[] = new InternetAddress[1]; 
	  iaTo[0] = toEmail;
	  InternetAddress iaHeaderTo[] = new InternetAddress[1]; 
	  iaHeaderTo[0] = toEmail; 
	  InternetAddress iaReplyTo[] = new InternetAddress[1]; 
	  iaReplyTo[0] = new InternetAddress (tmpEmailFrom); 
	  List contentList = new ArrayList(); 
	  contentList.add("Content-Type: text/html"); 
	  
	  emailService.sendMail(iaReplyTo[0], iaTo, subject, body, iaHeaderTo, iaReplyTo, contentList);
  }
	
	
		 private  void setStudyMaterialData(StudyMaterialModel studMaterial,Node node){
			            Element element = (Element) node;
        	            studMaterial.setItemBarcode(element.getAttribute("barcode"));
        	            studMaterial.setItemShortDesc(element.getAttribute("shortDescription"));
        	            studMaterial.setUnitNumber(element.getAttribute("unitNumber"));
        	            studMaterial.setDocumentType(element.getAttribute("documentType"));
        	            studMaterial.setModule(element.getAttribute("module"));
        	            studMaterial.setPeriod(element.getAttribute("period"));
        	            studMaterial.setYear(element.getAttribute("year"));
        	            studMaterial.setDateAvailable(element.getAttribute("dateAvailable").substring(0,10));
        	            studMaterial.setFilename();
        	            //studMaterial.setFileSize();
        	            
        	            //Reading the file size from the webservice test
        	            studMaterial.setFileSize(element.getAttribute("fileSize")); 
        	            studMaterial.setItemDisplayName();
        	            studMaterial.setFilePath();
        	            studMaterial.setLanguage();
        	            
        	           
     }
		 
		 
    public void  displayStudyMaterials(UIContainer tofill,String siteId,Log log) {
                          log.info("Official study material: Get resourceList for " + siteId);
                          StudyMaterial studyMaterial=new StudyMaterial(); 
                          StudyMaterialUI studyMaterialUI   =new StudyMaterialUI ();
                          StudentSystemQueryDAO studentSystemQueryDAO = new StudentSystemQueryDAO();
                          if ("S2".equals(siteId.substring(11, 13)) && 
                        		  studentSystemQueryDAO.getSecondSemesterStatusForDisplay ("20" + siteId.substring(8, 10)) == 0) {
                        	  studyMaterialUI.displayNoSecondSemsterMsg(tofill);
                        	  return;
                        	  
                        	  
                          }
                          
                          List resourceList=getStudyMaterialList(siteId.substring(0, 7), "20" + siteId.substring(8, 10),
		                                      CoursePeriodLookup.getPeriodTypeAsString(siteId.substring(11, 13)));
                          if ((resourceList == null) || (resourceList.size() == 0)) {
                                    log.error("Official study material: Failed to get resourceList from query for "	+ siteId);
                          }
                          
                          studyMaterialUI.displayStudyMaterials(tofill,resourceList);
      }	 
}
