package org.sakaiproject.studymaterial.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.studymaterial.module.StudyMaterialModel;
import org.sakaiproject.studymaterial.tool.params.StudyMaterialParams;
import org.sakaiproject.studymaterial.tool.producers.PdfProducer;
import org.sakaiproject.studymaterial.utils.DateUtil;
import org.sakaiproject.studymaterial.utils.Utilities;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;

public class StudyMaterialUI {
	
	    public void displayStudyMaterials(UIContainer tofill, List resourceList) {
                      
	    	
	    	    if ((resourceList == null) || resourceList.size() == 0) {
			              displayNoStudyMaterialErrorMsg(tofill);
			              return ;
		         }      
		 	       if ((resourceList != null) && (resourceList.size() ==1)&&
					   (((StudyMaterialModel)resourceList.get(0)).getItemBarcode().equals("-1"))){
			    	         setWebServiceErrorMsg(tofill);
			    	         return ;
			       }
				    setHeading(tofill);
				    displayStudyMaterial(tofill,resourceList);
		}
		private void setHeading(UIContainer tofill){
			           UIOutput.make(tofill, "instruction");
			           UIOutput.make(tofill, "listdate_lable", " List Date: ");
			           UIOutput.make(tofill, "listdate", "" +DateUtil.todayStr());
			           UIOutput.make(tofill, "studymaterial", " Study Material ");
			           UIOutput.make(tofill, "color");
			           UIOutput.make(tofill, "description", "Description");
			           UIOutput.make(tofill, "size", "File Size");
			           UIOutput.make(tofill, "available", "Available Since");
		}
		private void displayNoStudyMaterialErrorMsg(UIContainer tofill){
			UIOutput.make(tofill, "cala");
			UIOutput.make(tofill, "study", " Study Material ");
			UIMessage.make(tofill, "info_message",
					"studymaterial.general.no-course-available");
		}
		public void displayNoSecondSemsterMsg(UIContainer tofill) {
			UIOutput.make(tofill, "cala");
			UIOutput.make(tofill, "study", " Study Material ");
			UIMessage.make(tofill, "info_message",
					"studymaterial.general.no-course-available_due_to_second_semester_not_yet_started");
		}
		private void displayStudyMaterial(UIContainer tofill,List resourceList){
			         Iterator resourceIter = resourceList.iterator();
			 for (int i = 0; resourceIter.hasNext(); i++) {
					StudyMaterialModel studyMaterialModule = (StudyMaterialModel) resourceIter
							.next();
					UIBranchContainer urlBranch = UIBranchContainer.make(tofill,
							"url-row:");
					UIOutput.make(urlBranch, "implementation_date",
							studyMaterialModule.getDateAvailable());
					UIOutput.make(urlBranch, "filesize",
							studyMaterialModule.displayFileSize());
					UIOutput.make(urlBranch, "icon1");
					UIOutput.make(urlBranch, "img1");
					   String protectedPath = "/studymaterial/material/"; // Same as AuthTokenPrefix
						// boolean ipLimitation=false; // Same as AuthTokenLimitByIp
						long time = (new Date()).getTime(); // Time in decimal
						time = (time / 1000); // + (60 * 30); // timestamp of java is longer than PHP
						String hexTime = Long.toHexString(time); // hexTime in Hexadecimal
						//String filePathName = "/Adobe.exe";
						String seccc=secret;
						String resourceEditId=Utilities.getFilepathInserver(studyMaterialModule.getFilename(),studyMaterialModule.getModule(),studyMaterialModule.getDocumentType());
						String token = getMD5((secret +"/"+ resourceEditId  + hexTime).getBytes());
						String protectedUrl =  protectedPath+token + "/" + hexTime +"/"+ resourceEditId;
						
//					   UILink.make(urlBranch,"url", studyMaterialModule.getItemDisplayName(),
//				    		   "http://www.unisa.ac.za"+protectedUrl);
					 
					   
					   UILink.make(urlBranch,"url", studyMaterialModule.getItemDisplayName(),
							   urlPrefixforwww3+protectedUrl);
			}
		}
		public String getMD5(byte[] source) {
			String s = null;
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f' };
			try {
				java.security.MessageDigest md = java.security.MessageDigest
						.getInstance("MD5");
				md.update(source);
				byte tmp[] = md.digest();
				char str[] = new char[16 * 2];
				int k = 0;
				for (int i = 0; i < 16; i++) {
					byte byte0 = tmp[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				s = new String(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
		private void setWebServiceErrorMsg(UIContainer tofill){
						UIOutput.make(tofill, "cala");
	    		        UIOutput.make(tofill, "study", " Study Material ");
        	            UIMessage.make(tofill, "info_message","studymaterial.oraclescm.webservice.down");
		}
		private String urlPrefix = ServerConfigurationService.getString("studyMaterialUrlPrefix");
		private String urlPrefixforwww3 = ServerConfigurationService.getString("www3.base"); 
		private String secret = ServerConfigurationService.getString("studymaterial.secret");
		
		
}
