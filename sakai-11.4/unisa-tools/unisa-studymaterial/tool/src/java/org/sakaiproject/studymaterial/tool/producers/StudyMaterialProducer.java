
package org.sakaiproject.studymaterial.tool.producers;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.studymaterial.dao.MyUnisaContentQueryDAO;
import org.sakaiproject.tool.api.ToolManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.email.api.EmailService;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.sakaiproject.studymaterial.utils.*;
import org.sakaiproject.studymaterial.impl.StudyMaterial;
import java.util.*;

 public class StudyMaterialProducer implements ViewComponentProducer,DefaultView {

                           	private ToolManager toolManager;
                          	private SiteService siteService;
                          	private EmailService emailService;
                          	Log log = LogFactory.getLog(StudyMaterialProducer.class);
                          	// --------------------------------------------------------- Methods

                          	// The VIEW_ID must match the html template (without the .html)
                          	public static final String VIEW_ID = "StudyMaterial";
                          	private String id = "";
                          	public MessageLocator messageLocator;
                          	private String course;
                          	private String year;
                          	private short period;
                          	private int paper;
                          	private String examUrl = Utilities.currServer();
                          	org.w3c.dom.Document doc;

                          	private NodeList coursen;
                          	private NodeList yearn;
                          	private NodeList periodn;
                          	private NodeList languagen;
                          	private NodeList papernumbern;
                          	private NodeList filesizen;
                          	private NodeList linkn;

                          	// defining string object instead of a normal object{form}
                          	ArrayList<String> w_list = new ArrayList<String>();

                          	private URLConnection link;
                          	private URL url, url2;

                          	public void setMessageLocator(MessageLocator messageLocator) {
                          		this.messageLocator = messageLocator;
                          	}

                          	                 	public String getId() {
                          		return id;
                          	}

                          	public void setId(String id) {
                          		this.id = id;
                          	}

                          	public String getViewID() {
                          		return VIEW_ID;
                          	}

                          	// setters and getters for exam paper display
                          	public String getCourse() {
                          		return course;
                          	}

                          	public void setCourse(String course) {
                          		this.course = course;
                          	}
                          	/*String siteId = this.getId();
                          	siteId = "Site="+siteService.getSite(siteId).getTitle().toString();*/
                          	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
                          			ComponentChecker checker) {

                          		toolManager = (ToolManager) ComponentManager.get(ToolManager.class);
                          		siteService = (SiteService) ComponentManager.get(SiteService.class);

                          		MyUnisaContentQueryDAO dao = new MyUnisaContentQueryDAO();
                          		this.setId(toolManager.getCurrentPlacement().getContext());

                          		List resourceList = new ArrayList();

                          		Calendar now = Calendar.getInstance();
                          		int year = now.get(Calendar.YEAR);
                          		int month = now.get(Calendar.MONTH) + 1;
                          		int day = now.get(Calendar.DAY_OF_MONTH);
                          		String dm = "";
                          		String mth = "";
                          		String coursecode = "";

                          		if (month < 10) {
                          			mth = "0";
                          		}
                          		if (day < 10) {
                          			dm = "0";
                          		}

                          		String siteId = this.getId();
                          		log.info("Official study material: Get resourceList for " + siteId);
                          		StudyMaterial studyMaterial=new StudyMaterial();
              		            studyMaterial.displayStudyMaterials(tofill,this.getId(),log);
                          		List courseList = new ArrayList();
                          		String courcecode = "";
                          		
                          		//Web-service list
                          		List examList= new ArrayList();
                          		
                          		try {
                          			coursecode = dao.getMainCourse(this.getId().substring(0,7));
                          			//courseList = dao.getCourseList(this.getId().substring(0,7));
                          			} catch (Exception e1) {
                          			// TODO Auto-generated catch block
                          			log.error("Official study material: Failed to get courseList from quey(getMainCourse or getCourseList) for "+siteId);
                          			e1.printStackTrace();
                          		}
                          		
                          		if (coursecode.length() < 1){
                          			coursecode = this.getId().substring(0,7);
                          		}
                          		UIOutput.make(tofill,"coursecode",coursecode);
                          		
                          		List validPaper =new ArrayList();
                          		boolean recordMatch = false;
                          		String coursecodes;
                          		String periodDesc="";
                          		Boolean headerStatus = Boolean.FALSE;
                          		//examList = examXmlConvert(this.getId().substring(0,7));
                          		int noExamPaperMsgCounter=0;
                          		//compare the two lists	
                          		if (courseList.size() > 0){ //courseList.size() > 0
                          			Iterator courseIter = courseList.iterator();
                          			//looping Query List 
                          			
                          			
                          			for(int i=0; courseIter.hasNext(); i++){
                          				LinkedHashMap resHashMap = (LinkedHashMap)courseIter.next();			
                          				String course = resHashMap.get("FK_STUDY_UNIT_CODE").toString().toLowerCase();
                          				String examy = resHashMap.get("FK_EXAM_YEAR").toString().toLowerCase();
                          				String examp = resHashMap.get("FK_EXAM_PERIOD_COD").toString().toLowerCase();
                          				if (examp.equalsIgnoreCase("1")){
                          				periodDesc="January/February";
                          				}
                          				if(examp.equalsIgnoreCase("6")){
                          				periodDesc="May/June";
                          				}
                          				if(examp.equalsIgnoreCase("10")){
                          				periodDesc="October/November";
                          				}
                          				
                          				String lange=resHashMap.get("DATE_SCANNED").toString().toLowerCase();
                          				String langa=resHashMap.get("DATE_AFR_SCANNED").toString().toLowerCase();
                          				String papern = resHashMap.get("PAPER_NO").toString().toLowerCase();
                          				
                          				//set language null value to "00010101" **if language column is empty 00010101 will be assigned instead**
                          				if(lange.equalsIgnoreCase("00010101")){
                          					lange = "";
                          				}else{
                          					lange = "E";
                          				}
                          				
                          				if(langa.equalsIgnoreCase("00010101")){
                          					langa = "";
                          				}else{
                          					langa = "A";
                          				}
                          				String english = "";
                          				String afrikaans = "";
                          				String[] exam = null;
                          				String eFileSize="";
                          				String eFileLink="";
                          				String aFileSize="";
                          				String aFileLink="";
                          				//loop xml  List
                          				if(examList !=null){
                          				Iterator examIter = examList.iterator();
                          				for(int s=0; examIter.hasNext() ; s++){
                          					String examStr = (String)examIter.next();
                          					exam= examStr.split(",");
                          					
                          					//Compare two lists: from the query and web service url 									
                          					if (course.equalsIgnoreCase(exam[0])&& examy.equalsIgnoreCase(exam[1])&& examp.equalsIgnoreCase(exam[2])&& papern.equalsIgnoreCase(exam[4])){
                          						//compare per language 
                          						if(lange.equalsIgnoreCase(exam[3])){
                          							english = "Eng";
                          							eFileSize = exam[5];
                          							eFileLink = exam[6];
                          						}
                          						if(langa.equalsIgnoreCase(exam[3])){
                          							afrikaans = "Afr";
                          							aFileSize = exam[5];
                          							aFileLink = exam[6];
                          						}
                          						recordMatch = true;
                          						//break;
                          						
                          					} //end of comparison
                          					
                          			}//End of xml list loop		
                          			
                          			
                          			//String periodDesc="";
                          			if (recordMatch == true){
                          				if(i==0 || !headerStatus){
                          					headerStatus = Boolean.TRUE;
                          				UIOutput.make(tofill,"color3");
                          				UIOutput.make(tofill,"descriptionexam","Description");
                          				UIOutput.make(tofill,"sizeexam","File Size");
                          				UIOutput.make(tofill,"icon1");
                          			    UIOutput.make(tofill,"img1");
                          				}
                          			    
                          				//Display record for each languages
                          				if (english.equalsIgnoreCase("Eng") ) {
                          					UIBranchContainer urlBranch1 = UIBranchContainer.make(tofill, "url2-row:");
                          					UILink.make(urlBranch1,"url2",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+english+")"),examUrl+eFileLink.substring(9).replace("\">", "")); //mydev path to access webservice files
                          				    //UILink.make(urlBranch1,"url2",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+english+")"),"http://umkn-wrk01.int.unisa.ac.za/"+eFileLink.substring(10).replace("\">", ""));//erase 1st characters of the link then escape & replace with space at the end of link(local test) 
                          				    int efsize = Integer.parseInt(eFileSize)/1024;
                          				    String engSize=Integer.toString(efsize);
                          				    UIOutput.make(urlBranch1, "sizeng",engSize+" "+"KB");
                          				}
                          				if (afrikaans.equalsIgnoreCase("Afr") ){
                          					UIBranchContainer urlBrancha = UIBranchContainer.make(tofill, "url2-row:");
                          					UILink.make(urlBrancha,"url2",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+afrikaans+")"),examUrl+aFileLink.substring(9).replace("\">", "")); 
                          					//UILink.make(urlBrancha,"url1",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+afrikaans+")"),"http://umkn-wrk01.int.unisa.ac.za/"+aFileLink.substring(10).replace("\">", ""));
                          					int afsize = Integer.parseInt(aFileSize)/1024;
                          				    String afrSize=Integer.toString(afsize);
                          				    UIOutput.make(urlBrancha, "sizeng",afrSize+" "+"KB");
                          				}
                          					
                          				}else{

                          					if(!(lange.length()==0) ){
                          						lange="Eng";
                          						if (i==0){
                          						UIOutput.make(tofill,"icon2");
                          						UIOutput.make(tofill,"nofile",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+lange+")"));
                          						UIOutput.make(tofill,"kb","0 KB");
                          						}
                          						//send email if physical file not existing 
                          						String email;
                          						try {
                          							 email=dao.getEmailID(this.getId().substring(0,7));
                          						String emailSubject;
                          						String emailMessage;
                          						emailSubject = "Exam Question Paper File does not exist from "+this.getId();
                          						//System.out.println("Email siteId "+siteId);
                          						emailMessage = "Hello <p>"+						
                          				        			   "A request via the official Study Material tool in myUnisa was made for "+coursecode+"-"+examy+"-"+examp+"-"+lange+"-"+papern+".pdf <p>"+ 
                          				        			   "The file does not exist/has gone missing on Examination Question Paper archive.<br>"+"Please attend to this matter urgently. <p>"+
                          				                       "Regards <br>myUnisa Support Team ";
                          						sendEmail(emailSubject, emailMessage, email);
                          						} catch (Exception e) {
                          							// TODO Auto-generated catch block
                          							log.error("Failed to send email to fix the exam paper "+this.getId());
                          							e.printStackTrace();
                          						}
                          					}
                          					if(!(langa.length()==0) ){
                          						langa="Afr";
                          						if (i==0){
                          						UIOutput.make(tofill,"icon3");
                          						UIOutput.make(tofill,"nafile",("Examination Question Paper "+papern+" "+periodDesc+" "+examy+"("+langa+")"));
                          						UIOutput.make(tofill,"akb","0 KB");
                          						}
                          						//send email if physical file not existing 
                          						String email;
                          						try {
                          							 email=dao.getEmailID(this.getId().substring(0,7));
                          						String emailSubject;
                          						String emailMessage;
                          						emailSubject = "Exam Question Paper File does not exist from "+this.getId();
                          						//System.out.println("Email siteId "+siteId);
                          						emailMessage = "Hallo <p>"+						
                          				        			   "A request via the official Study Material tool in myUnisa was made for "+coursecode+"-"+examy+"-"+examp+"-"+lange+"-"+papern+".pdf <p>"+ 
                          				        			   "The file does not exist/had gone missing on Examination Question Paper archive.<br>"+"Please attend to this matter urgently. <p>"+
                          				                       "Regards <br>myUnisa Support Team ";
                          						sendEmail(emailSubject, emailMessage, email);

                          						} catch (Exception e) {
                          							// TODO Auto-generated catch block
                          							log.error("Failed to send email to fix the exam paper "+this.getId());
                          							e.printStackTrace();
                          						}
                          					}			
                          				
                          				}
                          				}else{
                          					     if(noExamPaperMsgCounter==0){
                          					           UIOutput.make(tofill,"cale"); 
                          					           UIOutput.make(tofill,"exam"," Previous Examination Papers "); 
                          					           UIMessage.make(tofill,"info_messagexam", "exampaper.general.no-exampaper-available");
                          					           noExamPaperMsgCounter++;
                          					     }
                          				}
                          				//clear values after the loop
                          				english = "";
                          				afrikaans = "";	
                          	/*			UIOutput.make(tofill,"color2");
                          				UIOutput.make(tofill,"examinationpapers","Previous Examination Papers");*/
                          				
                          		  }//End of query list loop
                          		}else{
                          			     if(noExamPaperMsgCounter==0){
                          			            UIOutput.make(tofill,"cale"); 
                          			            UIOutput.make(tofill,"exam"," Previous Examination Papers "); 
                          			            UIMessage.make(tofill,"info_messagexam", "exampaper.general.no-exampaper-available");	
                          			            noExamPaperMsgCounter++;
                          			     }
                          		}
                          				
                          	 }
                          		
                          		/* method to convert xml list from web services to Java list using dom parser **/ 
                          		
                          		private List examXmlConvert(String courseName){
                          			   InputStream stream = null;
                          			  
                          			   try {
                          				 
                          				  String webServiceUrl = examUrl + "/rcmclient/Default.aspx?coursecode=" + courseName;
                                         //String webServiceUrl = "https://mydev.unisa.ac.za/rcmclient/Default.aspx?coursecode="+courseName;
                          				log.info("Official Study Material: examXmlConvert "+courseName);
                          				URL url = new URL(webServiceUrl);
                          				URLConnection huc = (URLConnection) url.openConnection();
                          				huc.setConnectTimeout(1000);
                          				huc.setReadTimeout(5000);
                          				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
                          				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
                          			    stream = (InputStream)  huc.getInputStream();
                          				doc = docBuilder.parse(stream);
                          				doc.getDocumentElement().normalize();
                          				stream=null;

                          				NodeList listOfStudentDocuments = doc.getElementsByTagName("exampaper");

                          				if (listOfStudentDocuments.getLength() != 0) {

                          					for (int s = 0; s < listOfStudentDocuments.getLength(); s++) {

                          						Node firstDocTypeNode = listOfStudentDocuments.item(s);

                          						if (firstDocTypeNode.getNodeType() == Node.ELEMENT_NODE) {

                          							Element firstElement = (Element) firstDocTypeNode;

                          							NodeList coursecode = firstElement.getElementsByTagName("coursecode");
                          							Element courses = (Element) coursecode.item(0);
                          							coursen = courses.getChildNodes();
                          							String coursecodes = ((Node) coursen.item(0)).getNodeValue().trim();

                          							NodeList examyear = firstElement.getElementsByTagName("examyear");
                          							Element years = (Element) examyear.item(0);
                          							yearn = years.getChildNodes();
                          							String yearss = ((Node) yearn.item(0)).getNodeValue().trim();

                          							NodeList eperiod = firstElement.getElementsByTagName("period");
                          							Element periods = (Element) eperiod.item(0);
                          							periodn = periods.getChildNodes();
                          							String periodlist = ((Node) periodn.item(0)).getNodeValue().trim();

                          							NodeList languages = firstElement.getElementsByTagName("language");
                          							Element elanguage = (Element) languages.item(0);
                          							languagen = elanguage.getChildNodes();
                          							String language1 = ((Node) languagen.item(0)).getNodeValue().trim();

                          							NodeList epapernumber = firstElement.getElementsByTagName("papernumber");
                          							Element papernumbers = (Element) epapernumber.item(0);
                          							papernumbern = papernumbers.getChildNodes();
                          							String papernumber1 = ((Node) papernumbern.item(0)).getNodeValue().trim();

                          							NodeList efilesize = firstElement.getElementsByTagName("filesize");
                          							Element filesizes = (Element) efilesize.item(0);
                          							filesizen = filesizes.getChildNodes();
                          							String filesize1 = ((Node) filesizen.item(0)).getNodeValue().trim();

                          							NodeList links = firstElement.getElementsByTagName("link");
                          							Element elink = (Element) links.item(0);
                          							linkn = elink.getChildNodes();
                          							String linkl = ((Node) linkn.item(0)).getNodeValue().trim();

                          							String listItem = new String(coursecodes + "," + yearss + "," + periodlist + "," + language1 + ","
                          									+ papernumber1 + "," + filesize1 + "," + linkl);

                          							w_list.add(listItem);

                          						}
                          	            	}
                          					return w_list;
                          				} else {
                          					log.info("Official Study Material: webServiceUrl returns empty list for "+courseName);
                          					return null;
                          				}
                          			} catch (FileNotFoundException e) {
                          				e.printStackTrace();
                          				return null;
                          			} catch (Exception e) {
                          				e.printStackTrace();
                          				return null;
                          			} finally {
                          				try{
                          			     if (stream != null) {
                          				 stream.close();
                          				 }
                          				}catch(IOException e){
                          					e.printStackTrace();
                          				}
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

                          		private void sendEmail(String subject, String body, String emailAddress) throws AddressException {
                          			
                          			String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

                          			InternetAddress toEmail = new InternetAddress(emailAddress);
                          			InternetAddress iaTo[] = new InternetAddress[1];
                          			iaTo[0] = toEmail;
                          			InternetAddress iaHeaderTo[] = new InternetAddress[1];
                          			iaHeaderTo[0] = toEmail;
                          			InternetAddress iaReplyTo[] = new InternetAddress[1];
                          			iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
                          			List contentList = new ArrayList();
                          			contentList.add("Content-Type: text/html");
                          			//contentList.add("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");

                          			//EmailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
                          			emailService = (EmailService) ComponentManager.get(EmailService.class);
                          			emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
                          		} // end of sendEmail	
}



