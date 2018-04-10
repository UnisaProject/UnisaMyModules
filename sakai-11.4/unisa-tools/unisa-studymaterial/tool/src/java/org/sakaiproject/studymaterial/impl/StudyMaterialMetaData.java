package org.sakaiproject.studymaterial.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.sakaiproject.studymaterial.dao.WebserviceAccess;
import org.sakaiproject.studymaterial.module.StudyMaterialModel;
import org.sakaiproject.studymaterial.utils.SortUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class StudyMaterialMetaData {

   
	public List getStudyMaterialList(String courseId,String academicYear,short semester){
                       return studyMaterialXmlConvert(courseId, academicYear,semester);
    }
	private List studyMaterialXmlConvert(String courseId,String academicYear,short semester){
			           List<StudyMaterialModel> studyMaterialList = new ArrayList<StudyMaterialModel>();
		               try{
		                   WebserviceAccess scmXmlDataGetter=new WebserviceAccess();
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
				  		        	             studyMaterialList.add(studMaterial);
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
        	           // studMaterial.setFileSize();
        	            
        	            //reading the file size from the Webservice
        	            studMaterial.setFileSize(element.getAttribute("fileSize"));
        	            
        	            
        	            
        	            studMaterial.setItemDisplayName();
        	            studMaterial.setFilePath();
        	            studMaterial.setLanguage();
     }
		 
}
