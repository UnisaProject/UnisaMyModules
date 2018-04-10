package za.ac.unisa.lms.tools.mdadm.dao;


import java.io.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.datacontract.schemas._2004._07.UniflowFindAndGetService.*;
import org.tempuri.*;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.mdadm.Constants;
import za.ac.unisa.lms.tools.mdadm.exception.UniflowException;
import za.ac.unisa.lms.tools.mdadm.forms.AddressPH;
import za.ac.unisa.lms.tools.mdadm.forms.MdAdmissionApplication;
import za.ac.unisa.lms.tools.mdadm.forms.MdAdmissionForm;
import za.ac.unisa.lms.tools.mdadm.forms.PreviousQualification;
import za.ac.unisa.lms.tools.mdadm.forms.SignOffLevel;
import za.ac.unisa.lms.tools.mdadm.forms.SignOffRouteRecord;
import za.ac.unisa.lms.tools.mdadm.forms.Staff;
import za.ac.unisa.lms.tools.mdadm.forms.TrackRecord;
import za.ac.unisa.lms.tools.mdadm.forms.UniflowFile;
import za.ac.unisa.lms.tools.mdadm.forms.Contact;
import za.ac.unisa.lms.tools.mdadm.forms.Student;

public class MdAdmissionQueryDAO extends StudentSystemDAO {


	public static Log log = LogFactory.getLog(MdAdmissionQueryDAO.class);
	
	public ArrayList<UniflowFile> getDocsList(String studentNo) throws MalformedURLException, ServiceException, UniflowException{
		
		log.debug("get doc list");

		ArrayList<UniflowFile> docList = new ArrayList<UniflowFile>();
		IUniflowFindAndGetServiceProxy proxy = new IUniflowFindAndGetServiceProxy();
		RetrievalResult retrievalResult = new RetrievalResult();
		AuthenticationDetails details = new AuthenticationDetails();
		try
		{
			
			// --- 
			// set authentication details
			details.setPassword("VFR$5tgb");
			details.setUserName("MDUniflow_user");
			
			// ---
			// get search targets
			GetSearchTargetsResult result = proxy.getSearchTargets(details);
			// if search for target not successfull
			if (!"Successful".equalsIgnoreCase(result.getResultStatus().toString())){
				//todo error occured!!!!!!!!
			}
			
			// --- 
			// search for m&d documents for specific student
			//remove call webserver twice instead once with 7 number student number and once with padded 0 student number
//			String paddedStudentNumber = "";
//			if (studentNo.length()==7){
//				paddedStudentNumber = "0"+ studentNo;
//			}else{
//				paddedStudentNumber = studentNo;
//			}
				
			//SearchField studentField = new SearchField("studentno", paddedStudentNumber);
			SearchField studentField = new SearchField("studentno", studentNo);
			SearchField[] field1 = { studentField };
		    SearchRequest searchRequest = new SearchRequest();
            DocumentLocation[] documentLocation = result.getSearchTargets();
			 for ( int m =0; m < documentLocation.length; m++)
			 {
				 documentLocation[m].getName();
				 documentLocation[m].getType();
			 }
			searchRequest.setAuthenticationDetails(details);
			searchRequest.setMandDOnly(true);
			searchRequest.setSearchFields(field1);
			searchRequest.setSearchTargets(documentLocation);
			SearchResult results = null;
			results = proxy.search(searchRequest);
			
			// ---
			// get all documents
		    RecordSet[] resultSet = results.getRecordSets();
		    for (int i = 0; i < resultSet.length; i++) {
		    	log.debug("Read record set: " + i);
		    	log.debug("Doc location: " + i);
		    	
		    	DocumentLocation docLocation = resultSet[i].getLocation();
		    	log.debug("docLocation Name: "+docLocation.getName());
		    	log.debug("docLocattion type: " +docLocation.getType());

		    	String[] fieldNames = resultSet[i].getFieldNames();
		    	Record[] records = resultSet[i].getRecords();
		    	String docType = "";
		    	for (int k=0 ; k  < records.length ; k++)
		    	{
		    		// ---
		    		// get first documents doctype
		    		String[] recordField = records[k].getFields();
		    		for(int l =0;  l< recordField.length ; l++)
		    		{
		    			if (fieldNames[l] != null && "DOCTYPE".equalsIgnoreCase(fieldNames[l].toString())){
		    				docType = recordField[l];
		    			}
		    			log.debug("Field name:"+ fieldNames[l] + "== Record field:"+recordField[l]);
		    		}
		    		
		    		// add document to list
			    	UniflowFile uFile = new UniflowFile();
		    		uFile.setDisplayName(getDocTypeDesc(docType));
		    		if (docType == null){
		    			uFile.setShortDesc("defaultDocument");
		    		}else{
		    			uFile.setShortDesc(docType);
		    		}
		    		uFile.setUniqueId(records[k].getUniqueId().toString());
		    		uFile.setDocumentLocation(resultSet[i].getLocation());
		    		log.debug(uFile.toString());
		    		docList.add(uFile);
			    	if ("Worklist".equalsIgnoreCase(docLocation.getType().toString())){
			    		// FOR WORKLISTS: add first doc and check for more via sibling count
			    		DocumentID docId = new DocumentID(1,docLocation ,records[k].getUniqueId());
			    		retrievalResult = getFileFromUniflow(docId);
			    		Document doc = new Document();
			    		doc = retrievalResult.getDocument();
			    		int counter = 1;
			    		while(doc.getSiblingCount()>0){
			    			// get the next doc and add to list
			    			counter = counter +1;
			    			docId = new DocumentID(counter,docLocation ,records[k].getUniqueId());
				    		retrievalResult = getFileFromUniflow(docId);
				    		doc = new Document();
				    		doc = retrievalResult.getDocument();
				    		// add document to list
					    	UniflowFile siblingFile = new UniflowFile();
					    	siblingFile.setDisplayName(getDocTypeDesc(docType));
				    		if (docType == null){
				    			siblingFile.setShortDesc("defaultDocument");
				    		}else{
				    			siblingFile.setShortDesc(docType);
				    		}
				    		siblingFile.setUniqueId(records[k].getUniqueId().toString());
				    		siblingFile.setDocumentLocation(resultSet[i].getLocation());
				    		log.debug(uFile.toString());
				    		docList.add(siblingFile);
			    		}
			    	}
		    	}
		    	
		    }
		}catch (RemoteException rex) {
			
			throw new UniflowException ("MdAdmissionQueryDAO: getDocList : /" + rex.getMessage(), rex);
		}
	
		log.debug("get doc list END");
		return docList;

	}
	
	public RetrievalResult getFileFromUniflow(ArrayList<UniflowFile> docList,String uniqueID,String in) throws UniflowException, Exception{
		log.debug("get file from uniflow");
		
		RetrievalRequest rRequest = new RetrievalRequest();
		RetrievalResult retrievalResult = new RetrievalResult();		
		IUniflowFindAndGetServiceProxy proxy = new IUniflowFindAndGetServiceProxy();
		DocumentLocation docLocation = new DocumentLocation();
		
		try
		{		
			for (int i = 0; i < docList.size(); i++) {
				UniflowFile uFile = docList.get(i);
				if (uFile.getUniqueId().equalsIgnoreCase(uniqueID)){
					docLocation = uFile.getDocumentLocation();
					break;
				}
			}
			
			// --- 
			// set authentication details
			AuthenticationDetails details = new AuthenticationDetails();
			details.setPassword("VFR$5tgb");
			details.setUserName("MDUniflow_user");
			
	        rRequest.setAuthenticationDetails(details);
	        rRequest.setMandDOnly(true);
	        DocumentID docId = new DocumentID(1,docLocation ,uniqueID);
	        rRequest.setDocumentID(docId);
	       
	        retrievalResult = proxy.retrieve(rRequest);
		}
		catch (IOException io)
		{
			throw new UniflowException ("MdAdmissionQueryDAO: getFileFromUniflow /" + io, io );
		}
		
		log.debug("get file from uniflow END");
        		
        return retrievalResult;
	}
	
	private RetrievalResult getFileFromUniflow(DocumentID docId) throws RemoteException, MalformedURLException{

		log.debug("get file from uniflow2");
		RetrievalRequest rRequest = new RetrievalRequest();
		RetrievalResult retrievalResult = new RetrievalResult();		
		
		IUniflowFindAndGetServiceProxy proxy = new IUniflowFindAndGetServiceProxy();
		// --- 
		// set authentication details
		AuthenticationDetails details = new AuthenticationDetails();
		details.setPassword("VFR$5tgb");
		details.setUserName("MDUniflow_user");
		
        rRequest.setAuthenticationDetails(details);
        rRequest.setMandDOnly(true);
        //DocumentLocation docLocation = new DocumentLocation("AMEACCESS", LocationType.Worklist);
       // DocumentID docId = new DocumentID(1,docLocation ,"1:\\DD9\\1425.VSD\\16.CP");
        rRequest.setDocumentID(docId);
        retrievalResult = proxy.retrieve(rRequest);

		log.debug("get file from uniflow2 END");
		
        return retrievalResult;
	}
	
	private String getDocTypeDesc(String docType){
		String desc = "default description";
		
		if("AR".equalsIgnoreCase(docType)){
			desc = "Academic record";
		} else if("ID".equalsIgnoreCase(docType)){
			desc = docType;
		}else if("LETTERS".equalsIgnoreCase(docType)){
			desc = "Letters";
		}else if("CV".equalsIgnoreCase(docType)){
			desc = docType;
		}else if("SYLLABI".equalsIgnoreCase(docType)){
			desc = "Syllabi";
		}else if("QUEST".equalsIgnoreCase(docType)){
			desc = "Questionare";
		}else if("WORKEXP".equalsIgnoreCase(docType)){
			desc = "Proof of work experience";
		}else if("ESSAY".equalsIgnoreCase(docType)){
			desc = "Essay";
		}else if("SWREG".equalsIgnoreCase(docType)){
			desc = "Social worker registration";
		}else if("SAQA".equalsIgnoreCase(docType)){
			desc = "SAQA evauation";
		}else if("DISS".equalsIgnoreCase(docType)){
			desc = "Dissertation/Thesis";
		}else if("MVAPORT".equalsIgnoreCase(docType)){
			desc = "MVA Portfolio";
		}else if("ENGREG".equalsIgnoreCase(docType)){
			desc = "English language requirement";
		}
		
		return desc;
		
	}
	public Student getStudent(int studentNr) throws Exception {

		Student student = new Student();

		String sql = "select nr,mk_title,surname,initials,TO_CHAR(birth_date,'YYYY-MM-DD') as bday, gender," +
				" lns.eng_description as country" + " from stu , lns" + " where nr=" + studentNr +
				" and lns.code=stu.mk_country_code";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String title = "";
				String surname = "";
				String initials = "";
				title = replaceNull(data.get("mk_title"));
				surname = replaceNull(data.get("surname"));
				initials = replaceNull(data.get("initials"));
				student.setStudentNumber(replaceNull(data.get("nr")));
				student.setName(surname + ' ' + initials + ' ' + title);
				student.setPrintName(title + ' ' + initials + ' ' + surname);
				student.setBirthDate(replaceNull(data.get("bday")));
				student.setCountry(replaceNull(data.get("country")));
				student.setGender(replaceNull(data.get("gender")));
				if (student.getGender().equalsIgnoreCase("M")){
					student.setGender("Male");
				}
				if (student.getGender().equalsIgnoreCase("F")){
					student.setGender("Female");
				}
				Contact contact = new Contact();
				contact = getContactDetails(studentNr, 1);
				student.setContactInfo(contact);
			}
		} catch (Exception ex) {
			throw new Exception("Error reading STU / " + ex);
		}
		return student;

	}

	public Contact getContactDetails(Integer referenceNo, Integer category)
			throws Exception {

		Contact contact = new Contact();

		String sql = "select home_number,work_number,fax_number,email_address,cell_number"
				+ " from adrph"
				+ " where reference_no="
				+ referenceNo
				+ " and fk_adrcatcode=" + category;

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				contact.setHomeNumber(replaceNull(data.get("home_number"))
						.trim());
				contact.setWorkNumber(replaceNull(data.get("work_number"))
						.trim());
				contact.setFaxNumber(replaceNull(data.get("fax_number")).trim());
				contact.setCellNumber(replaceNull(data.get("cell_number"))
						.trim());
				contact.setEmailAddress(replaceNull(data.get("email_address"))
						.trim());
			}
		} catch (Exception ex) {
			throw new Exception("Error reading ADRPH / " + ex);
		}
		return contact;
	}
	/**
	 * This method returns a list of previous qualification record for the specific student
	 *
	 * @param studNr Student number
	 * @param applicationNr Application number
	 */
	public ArrayList<PreviousQualification> getPreviousQualification(String studentNr, String applicationNr) throws Exception {


		log.debug("get previous qual");
		ArrayList<PreviousQualification> list = new ArrayList<PreviousQualification>();

		String sql = " SELECT * from mdprev where mk_student_nr="+studentNr+
		             " and app_sequence_nr="+applicationNr;
		log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				PreviousQualification prevQual = new PreviousQualification();
				if (data.get("PREV_QUAL") != null){
					prevQual.setQualification(data.get("PREV_QUAL").toString());
				}
				if (data.get("INSTITUTION") != null){
					prevQual.setInstitution(data.get("INSTITUTION").toString());
				}
				if (data.get("INSTITUTION") != null){
					prevQual.setInstitution(data.get("INSTITUTION").toString());
				}
				if (data.get("YEAR_COMPLETED") != null){
					prevQual.setYearCompleted(data.get("YEAR_COMPLETED").toString());
				}
				if (data.get("ACCREDIT_SOURCE") != null){
					if("AC".equalsIgnoreCase(data.get("ACCREDIT_SOURCE").toString())){
						prevQual.setAccreditationSource("Commonwealth Universities Handbook");
					} else if("IH".equalsIgnoreCase(data.get("ACCREDIT_SOURCE").toString())){
						prevQual.setAccreditationSource("International Handbook of Universities");
					} else if("PE".equalsIgnoreCase(data.get("ACCREDIT_SOURCE").toString())){
						prevQual.setAccreditationSource("Accredited Institutions of Postsecondary Education");
					}else if("SD".equalsIgnoreCase(data.get("ACCREDIT_SOURCE").toString())){
						prevQual.setAccreditationSource("Senate's discretionary approval");
					}else if("SA".equalsIgnoreCase(data.get("ACCREDIT_SOURCE").toString())){
						prevQual.setAccreditationSource("SAQA certificate");
					}
				}
				list.add(prevQual);
			}
			log.debug("get previous qual END ");
		return list;
	}	
	
	
	/**
	 * This method returns the admission application for the specific student
	 *
	 * @param studNr Student number
	 * change Johanet 20140214 - add input parameter applicationNr if 
	 */
	public MdAdmissionApplication getApplicationRecord(String applicationNr, String studentNr) throws Exception {

		log.debug("get appl record");	
		MdAdmissionApplication app = new MdAdmissionApplication();

		String sql = " SELECT mdappl.*, LONG_ENG_DESCRIPTI as descr from mdappl, grd where mk_student_nr =?"+
		" and admission_status in ('R','A') and code=MK_QUALIFICATION_C and and app_sequence_nr=" + Integer.parseInt(applicationNr.trim());	
		
		log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{studentNr});

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				app = new MdAdmissionApplication();
				
				if (data.get("APP_SEQUENCE_NR") != null){
					app.setApplicationNr(data.get("APP_SEQUENCE_NR").toString());
				}
				if (data.get("ADMISSION_STATUS") != null){
					app.setStatus(data.get("ADMISSION_STATUS").toString());
				}
				if (data.get("PROPOSED_TITLE") != null){
					app.setTitle(data.get("PROPOSED_TITLE").toString());
				}
				if (data.get("PROPOSED_TITLE") != null){
					app.setTitle(data.get("PROPOSED_TITLE").toString());
				}
				if (data.get("COMMENTS") != null){
					app.setAdvisorComment(data.get("COMMENTS").toString());
				}
				if (data.get("STRUCT_DEGREE") != null){
					app.setStructuredComment(data.get("STRUCT_DEGREE").toString());
				}
				if (data.get("ADDITIONAL_REQUIREMENTS") != null){
					app.setRequirementComment(data.get("ADDITIONAL_REQUIREMENTS").toString());
				}
				if (data.get("REASON_NOT_ADMIT") != null){
					app.setNotAdmittedComment(data.get("REASON_NOT_ADMIT").toString());
				}
				String recommendation = replaceNull(data.get("RECOMMEND_STATUS"));
				Gencod gencod = new Gencod();
				if (!recommendation.equalsIgnoreCase("")){
					StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
					gencod = genDao.getGenCode("223", recommendation);
				}
				app.setRecommendation(gencod);
				if (data.get("MK_QUALIFICATION_C") != null){
					app.getQualification().setQualCode(data.get("MK_QUALIFICATION_C").toString());
				}
				if (data.get("DESCR") != null){
					app.getQualification().setQualDesc(data.get("DESCR").toString());
				}
				if  (data.get("SPECIALITY_CODE") != null){
					app.getQualification().setSpecCode(data.get("SPECIALITY_CODE").toString());
				}
				if  (data.get("INTEREST_AREA") != null){
					app.setAreaOfInterest(data.get("INTEREST_AREA").toString());
				}
				if  (data.get("CONTACT_PERSON") != null){
					String persno = (replaceNull(data.get("CONTACT_PERSON")));
					Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("CONTACT_PERSON").toString());
					}
					app.setContactPerson(person);
				}
				if  (data.get("SUPERVISOR") != null){
					String persno = (replaceNull(data.get("SUPERVISOR")));				Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("SUPERVISOR").toString());
					}				
					app.setSupervisor(person);
				}
				if  (data.get("JOINT_SUPERVISOR") != null){
					String persno = (replaceNull(data.get("JOINT_SUPERVISOR")));
					Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
					}
					app.setJointSupervisor(person);
				}			
				break;
				
			}

			log.debug("get appl record END");
		return app;
	}
	
	public MdAdmissionApplication getLatestApplicationRecordForStudent(String studentNr,List<Gencod> recommendationList) throws Exception {

		log.debug("get appl record");	
		MdAdmissionApplication app = new MdAdmissionApplication();

		String sql = " SELECT mdappl.*, LONG_ENG_DESCRIPTI as descr from mdappl, grd where mk_student_nr =?"+
		" and code=MK_QUALIFICATION_C and application_date > to_date('20130901','YYYYMMDD') and admission_status <> 'DE' order by application_date desc";  //Todo: date range to pick up application records only for last academic year
		log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{studentNr});

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				app = new MdAdmissionApplication();
				
				if (data.get("APP_SEQUENCE_NR") != null){
					app.setApplicationNr(data.get("APP_SEQUENCE_NR").toString());
				}
				if (data.get("ADMISSION_STATUS") != null){
					app.setStatus(data.get("ADMISSION_STATUS").toString());
				}
				if (data.get("PROPOSED_TITLE") != null){
					app.setTitle(data.get("PROPOSED_TITLE").toString());
				}
				if (data.get("PROPOSED_TITLE") != null){
					app.setTitle(data.get("PROPOSED_TITLE").toString());
				}
				if (data.get("COMMENTS") != null){
					app.setAdvisorComment(data.get("COMMENTS").toString());
				}
				if (data.get("STRUCT_DEGREE") != null){
					app.setStructuredComment(data.get("STRUCT_DEGREE").toString());
				}
				if (data.get("ADDITIONAL_REQUIREMENTS") != null){
					app.setRequirementComment(data.get("ADDITIONAL_REQUIREMENTS").toString());
				}
				if (data.get("REASON_NOT_ADMIT") != null){
					app.setNotAdmittedComment(data.get("REASON_NOT_ADMIT").toString());
				}
				Gencod gencod = new Gencod();
				gencod.setCode(replaceNull(data.get("RECOMMEND_STATUS")));
				gencod.setEngDescription("");
				for (int j = 0; j < recommendationList.size(); j++){
					if (gencod.getCode().equalsIgnoreCase(((Gencod)recommendationList.get(j)).getCode())){
						gencod.setEngDescription(((Gencod)recommendationList.get(j)).getEngDescription());
						j = recommendationList.size();
					}
				}			
				app.setRecommendation(gencod);
				if (data.get("MK_QUALIFICATION_C") != null){
					app.getQualification().setQualCode(data.get("MK_QUALIFICATION_C").toString());
				}
				if (data.get("DESCR") != null){
					app.getQualification().setQualDesc(data.get("DESCR").toString());
				}
				if  (data.get("SPECIALITY_CODE") != null){
					app.getQualification().setSpecCode(data.get("SPECIALITY_CODE").toString());
				}
				if  (data.get("INTEREST_AREA") != null){
					app.setAreaOfInterest(data.get("INTEREST_AREA").toString());
				}
				if  (data.get("CONTACT_PERSON") != null){
					String persno = (replaceNull(data.get("CONTACT_PERSON")));
					Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("CONTACT_PERSON").toString());
					}
					app.setContactPerson(person);
				}
				if  (data.get("SUPERVISOR") != null){
					String persno = (replaceNull(data.get("SUPERVISOR")));				Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("SUPERVISOR").toString());
					}				
					app.setSupervisor(person);
				}
				if  (data.get("JOINT_SUPERVISOR") != null){
					String persno = (replaceNull(data.get("JOINT_SUPERVISOR")));
					Person person = new Person();
					if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
						PersonnelDAO persdao = new PersonnelDAO();
						person = persdao.getPerson(Integer.parseInt(persno));		
					}				
					if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
						 person = getExternalStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
					}
					app.setJointSupervisor(person);
				}			
				break;
				
			}

			log.debug("get appl record END");
		return app;
	}
	
	public List<TrackRecord> getApplTrackingList(int studentNr, int seqNr, List<Gencod> recommendationList) throws Exception {
		List <TrackRecord> trackingList = new ArrayList<TrackRecord>();
		
		String sql = "select to_char(timestamp,'YYYY/MM/DD') as trackDate,current_level,assigned_to_level,assigned_to_pers_no,pers_nr,recommended_value,recommended_comment,status_code,comments" +
		" from mdtrac" +
		" where mk_student_nr=" + studentNr +
		" and app_sequence_nr=" + seqNr +
		" and status_code in ('R','RS','FS','RB','A','AS')" +
		" and tracking_type='APPL'" +
		" order by trackdate";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				TrackRecord record = new TrackRecord();
				record.setSeqNr(seqNr);
				record.setDate(replaceNull(data.get("trackDate")));	
				record.setCurrentLevel(replaceNull(data.get("current_level")));	
				record.setAssignToLevel(replaceNull(data.get("assigned_to_level")));
				String status = replaceNull(data.get("status_code"));
				Gencod gencod = new Gencod();
				if (!status.equalsIgnoreCase("")){
					StudentSystemGeneralDAO genDao = new StudentSystemGeneralDAO();
					gencod = genDao.getGenCode("136", status);
					gencod.setCode(status);
				}
				record.setStatus(gencod);
				gencod = new Gencod();
				gencod.setCode(replaceNull(data.get("RECOMMENDED_VALUE")));
				gencod.setEngDescription("");
				if (!gencod.getCode().equalsIgnoreCase("")){
					for (int j = 0; j < recommendationList.size(); j++){
						if (gencod.getCode().equalsIgnoreCase(((Gencod)recommendationList.get(j)).getCode())){
							gencod.setEngDescription(((Gencod)recommendationList.get(j)).getEngDescription());
							j = recommendationList.size();
						}
					}	
				}						
				record.setRecommendation(gencod);
				record.setRecommendationComment(replaceNull(data.get("recommended_comment")));
				record.setSignoffComment(replaceNull(data.get("comments")));
				
				Person person = new Person();	
				PersonnelDAO dao = new PersonnelDAO();
				
				String current = replaceNull(data.get("pers_nr"));
				
				if (isInteger(current)){
					person = dao.getPersonnelFromSTAFF(Integer.parseInt(current));
				}
				record.setCurrentPerson(person);
								
				String assignedTo = replaceNull(data.get("assigned_to_pers_no"));	
				person = new Person();
				
				if (isInteger(assignedTo)){
					person = dao.getPersonnelFromSTAFF(Integer.parseInt(assignedTo));
				}
				record.setAssignToPerson(person);
				
				trackingList.add(record);
				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdAdmissionDAO : Error reading tracking list / " + ex,
					ex);
		}
			log.debug("get tracking records END");
		return trackingList;
	}
	
	
	public TrackRecord getLatestTrackRecord(int studentNr, int seqNr) throws Exception {
		TrackRecord record = new TrackRecord();
		
		String sql = "select to_char(timestamp,'YYYY/MM/DD') as trackDate,current_level,assigned_to_level,assigned_to_pers_no,pers_nr,recommended_value,status_code,comments" +
		" from mdtrac" +
		" where mk_student_nr=" + studentNr +
		" and app_sequence_nr=" + seqNr +
		" and tracking_type='APPL'" +
		" order by timestamp desc";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				record.setSeqNr(seqNr);
				record.setDate(replaceNull(data.get("trackDate")));	
				record.setCurrentLevel(replaceNull(data.get("current_level")));	
				record.setAssignToLevel(replaceNull(data.get("assigned_to_level")));	
				record.setSignoffComment(replaceNull(data.get("comments")));
				String status = replaceNull(data.get("status_code"));
				Gencod gencod = new Gencod();
				gencod.setCode(status);
				gencod.setEngDescription("");
				record.setStatus(gencod);
				break;				
			}
		} catch (Exception ex) {
			throw new Exception(
					"MdAdmissionDAO : Error reading tracking list / " + ex,
					ex);
		}
			log.debug("get tracking records END");
		return record;
	}

	/**
	 * This method returns the admission application for the specific student
	 *
	 * @param studNr Student number
	 */
	/**
	 * @param staffNumber 
	 * 				Logged in staff number
	 * @param mdForm 
	 * 				Form object for storing the selected student data
	 * @return Map 
	 * 				Containing applications details per student
	 * @throws Exception
	 */
	public Map <String, MdAdmissionApplication> getApplicationRecordList(
						String staffNumber, 
						Map<String, MdAdmissionApplication> studentApplicationList, 
						String signoffLevel,
						String qualificationCode,
						String specialityCode,
						String type,
						List<Gencod> recommendationList) 
	throws Exception {

		log.debug("get students application records");
		MdAdmissionApplication app = null;
		
	String sql = "SELECT mdappl.*, grd.LONG_ENG_DESCRIPTI as descr" +
		" FROM mdappl, grd, mdtrac" +
		" WHERE mdappl.ADMISSION_STATUS = '" + type + "'" +
		" and mdappl.MK_QUALIFICATION_C = ?" +
		" and mdappl.SPECIALITY_CODE = ?" + 
		" and grd.code = mdappl.MK_QUALIFICATION_C" + 
		" and mdtrac.mk_student_nr = mdappl.mk_student_nr" + 
		" and mdtrac.APP_SEQUENCE_NR = mdappl.APP_SEQUENCE_NR" + 
		" and mdtrac.tracking_type = 'APPL'" +
		" and mdtrac.ASSIGNED_TO_LEVEL = ?" + 
		" and mdtrac.timestamp = (" + 
		" select max(timestamp) from mdtrac b where b.mk_student_nr=mdappl.mk_student_nr" +
		" and b.app_sequence_nr=mdappl.app_sequence_nr and mdtrac.tracking_type='APPL')";
		
		Object [] params  =  null;
				  
		params  = 
				new Object []{qualificationCode,
					  		  specialityCode,
					  		  signoffLevel};
		
		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql, params);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			app = new MdAdmissionApplication();
			ListOrderedMap data = (ListOrderedMap) i.next();
			
			if (data.get("APP_SEQUENCE_NR") != null){
				app.setApplicationNr(data.get("APP_SEQUENCE_NR").toString());
			}
			if (data.get("ADMISSION_STATUS") != null){
				app.setStatus(data.get("ADMISSION_STATUS").toString());
			}
			if (data.get("PROPOSED_TITLE") != null){
				app.setTitle(data.get("PROPOSED_TITLE").toString());
			}
			if (data.get("PROPOSED_TITLE") != null){
				app.setTitle(data.get("PROPOSED_TITLE").toString());
			}
			if (data.get("COMMENTS") != null){
				app.setAdvisorComment(data.get("COMMENTS").toString());
			}
			if (data.get("STRUCT_DEGREE") != null){
				app.setStructuredComment(data.get("STRUCT_DEGREE").toString());
			}
			if (data.get("ADDITIONAL_REQUIREMENTS") != null){
				app.setRequirementComment(data.get("ADDITIONAL_REQUIREMENTS").toString());
			}
			if (data.get("REASON_NOT_ADMIT") != null){
				app.setNotAdmittedComment(data.get("REASON_NOT_ADMIT").toString());
			}
			Gencod gencod = new Gencod();
			gencod.setCode(replaceNull(data.get("RECOMMEND_STATUS")));
			gencod.setEngDescription("");
			for (int j = 0; j < recommendationList.size(); j++){
				if (gencod.getCode().equalsIgnoreCase(((Gencod)recommendationList.get(j)).getCode())){
					gencod.setEngDescription(((Gencod)recommendationList.get(j)).getEngDescription());
					j = recommendationList.size();
				}
			}			
			app.setRecommendation(gencod);
			if (data.get("MK_QUALIFICATION_C") != null){
				app.getQualification().setQualCode(data.get("MK_QUALIFICATION_C").toString());
			}
			if (data.get("DESCR") != null){
				app.getQualification().setQualDesc(data.get("DESCR").toString());
			}
			if  (data.get("SPECIALITY_CODE") != null){
				app.getQualification().setSpecCode(data.get("SPECIALITY_CODE").toString());
			}
			if  (data.get("INTEREST_AREA") != null){
				app.setAreaOfInterest(data.get("INTEREST_AREA").toString());
			}
			if (data.get("LECTURER_NAME") != null){
				app.setLecturerConsulted(data.get("LECTURER_NAME").toString());
			}
			if  (data.get("CONTACT_PERSON") != null){
				String persno = (replaceNull(data.get("CONTACT_PERSON")));
				Person person = new Person();
				if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
					PersonnelDAO persdao = new PersonnelDAO();
					person = persdao.getPerson(Integer.parseInt(persno));		
				}				
				if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
					 person = getExternalStaffMemberDetail(data.get("CONTACT_PERSON").toString());
				}
				app.setContactPerson(person);
			}
			if  (data.get("SUPERVISOR") != null){
				String persno = (replaceNull(data.get("SUPERVISOR")));				Person person = new Person();
				if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
					PersonnelDAO persdao = new PersonnelDAO();
					person = persdao.getPerson(Integer.parseInt(persno));		
				}				
				if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
					 person = getExternalStaffMemberDetail(data.get("SUPERVISOR").toString());
				}				
				app.setSupervisor(person);
			}
			if  (data.get("JOINT_SUPERVISOR") != null){
				String persno = (replaceNull(data.get("JOINT_SUPERVISOR")));
				Person person = new Person();
				if (persno!=null && !persno.trim().equalsIgnoreCase("")){					
					PersonnelDAO persdao = new PersonnelDAO();
					person = persdao.getPerson(Integer.parseInt(persno));		
				}				
				if (person.getPersonnelNumber() == null || "".equals(person.getPersonnelNumber().trim())){
					 person = getExternalStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
				}
				app.setJointSupervisor(person);
			}			

			
			//change Johanet - key of map cannot just be student number, must also include the sequence number
			String studentNr=data.get("MK_STUDENT_NR").toString().trim();
			if (studentNr.length()==7){
				studentNr="0" + studentNr; 
			}
			String mdapplKey=studentNr + app.getApplicationNr().trim();
			
			studentApplicationList.put(mdapplKey, app);			

		}

		log.debug("get appl record END");
		return studentApplicationList;
	}
	
	/**
	 * This method returns the student's name
	 *
	 * @param StudentNr       The student number
	 */
	public String getStudentName(String studentNr) throws Exception{

		// Return student name
		String name = "";

		String query = "select mk_title || ' ' || first_names ||' ' || surname as name" +
		" from stu " +
		" where nr = " + studentNr ;
		log.debug(query);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		name = (String) jdt.queryForObject(query, String.class);
		
		return name;
	}
	
	/**
	 * This method returns staff name
	 *
	 * @param StudentNr       The student number
	 */
	public String getStaffName(String staffNr) throws Exception{
		// Return staff name
		String name = "";

		String query = "select title || ' ' || initials ||' ' || surname as name" +
		" from staff " +
		" where persno = " + staffNr ;

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		name = (String) jdt.queryForObject(query, String.class);

		return name;
	}
	
	/** Populate student look up list
	 * 
	 * @param studentApplicationList 
	 * 		  Students application list	
	 * @return LabelValueBean
	 * 			code and description for each student on the list
	 * @throws Exception
	 */
	public ArrayList<LabelValueBean> getStudentLookupList(
			Map<String, 
			MdAdmissionApplication> studentApplicationList) 
	throws Exception
	{
		ArrayList<LabelValueBean> studentList = new ArrayList<LabelValueBean>();
		
		String code = null;
		String desc = null;
		
		for (String mdapplKey : studentApplicationList.keySet())
		{			
			String studentNumber=mdapplKey.substring(0, 8);
			if (studentNumber.substring(0, 1).equalsIgnoreCase("0")){
				studentNumber=studentNumber.substring(1,8);
			}
			code = mdapplKey + " - " + studentNumber + " - " + getStudentName(studentNumber);
			desc = studentNumber + " - " + getStudentName(studentNumber);
			MdAdmissionApplication appl = studentApplicationList.get(mdapplKey);			
			String specCode=padString(appl.getQualification().getSpecCode(), 3);
			desc = appl.getQualification().getQualCode() + '\u00A0' + specCode + '\u00A0' + desc;
			if (appl.getStatus().equalsIgnoreCase("A")){
				desc = desc + '\u00A0' + "(Appeal)";
			}
			studentList.add(new org.apache.struts.util.LabelValueBean(desc, code));
		}
		
		Collections.sort(studentList);
		
		return studentList;
	}
	
	/**
	 * Retrieves Address Phone and Home number for the specified student
	 * 
	 * @param referenceNo
	 * 			Student number
	 * @return	AddressPH
	 * 			  Contains Phone and Cell number details
	 */
	public AddressPH getAddressPH(String referenceNo, String adrCatCode)
	{
		AddressPH adrph = new AddressPH();
		
		String query = "SELECT * FROM adrph WHERE reference_no = ? and FK_ADRCATCODE = ?";
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(
				query, 
				new Object []{referenceNo, adrCatCode});

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			
			if (data.get("HOME_NUMBER") != null)
			{
				adrph.setHomeNumber(data.get("HOME_NUMBER").toString());
			}
			
			if (data.get("CELL_NUMBER") != null)
			{
				adrph.setCellNumber(data.get("CELL_NUMBER").toString());
			}
			
			if (data.get("EMAIL_ADDRESS") != null)
			{
				adrph.setEmail(data.get("EMAIL_ADDRESS").toString());
			}			
		}
		
		return adrph;
	}

	public ArrayList<LabelValueBean> getStaffList(String staffNr, String surname) 
	throws Exception{
		// Return staff name
		ArrayList<LabelValueBean> staffList = new ArrayList<LabelValueBean>();
		String query = "";
		Object [] parameters = null;
		if(!"".equals(surname.trim())){

			query = "select persno,surname || ' '|| initials ||' ' ||  title as name, long_eng_desc " +
			" from staff, dpt " +
			" where surname like '" + surname.toUpperCase() +
			"%' and mk_dept_code=code";
			
			//parameters = new Object []{surname.toUpperCase()};
		}else{

			query = "select persno,surname || ' '|| initials ||' ' ||  title as name, long_eng_desc " +
			" from staff, dpt " +
			" where persno = " + staffNr.trim() +
			" and mk_dept_code=code";
			
			//parameters = new Object []{staffNr.trim()};
		}
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String code = "";
			if ( data.get("PERSNO").toString().trim().length()==7){
				code = "0" + data.get("PERSNO").toString();
			}else{
				code = data.get("PERSNO").toString();
			}
			String desc = data.get("NAME").toString()+ ", "+ data.get("LONG_ENG_DESC").toString().trim();
			staffList.add(new org.apache.struts.util.LabelValueBean(code + " - "
						+ desc, code + desc));
		}
		
		return staffList;
	}
	
	public Person getExternalStaffMemberDetail(String staffNr) throws Exception{
		// Return staff name
		Person person = new Person();
		
		String query = "";
		// Get name
		query = "select nr,title || ' ' || initials ||' ' || surname as name" +
			" from mbe " +
			" where nr = ?" +
			" and in_use_flag='Y'";
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{staffNr.trim()});

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			person.setName(data.get("NAME").toString().trim());
			person.setPersonnelNumber(data.get("NR").toString().trim());
		} 
		
		// Get name
		query = "select * from adrph " +
			" where reference_no = ?" +
			" and fk_adrcatcode=22";
		log.debug(query);

		JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
		List queryList2 = jdt2.queryForList(query, new Object []{staffNr.trim()});

		Iterator i2 = queryList2.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i2.next();
			if(data.get("EMAIL_ADDRESS")!= null){
				person.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("WORK_NUMBER")!= null){
				person.setContactNumber(data.get("WORK_NUMBER").toString().trim());
			}
		}
		return person;
	}	
	
	
	public List<SignOffLevel> getSignOffLevelList(String networkCode) throws Exception{
		log.debug("get user Sign-Off list from network code");	
		// Return staff name
		List<SignOffLevel> signOffLevelList = new ArrayList<SignOffLevel>();
		
		String query = "SELECT qsprout.final_flag , qsprout.MK_QUAL_CODE, qsprout.MK_SPES_CODE, qsprout.type, qsprout.seq_nr " +
						"FROM qsprout " +
						"WHERE qsprout.STAFF_NUMBER=? " +
						" and qsprout.type in ('ADM','APL')" +
						" order by qsprout.MK_QUAL_CODE, qsprout.MK_SPES_CODE";
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{networkCode.toUpperCase()});
		
		SignOffLevel  signOffLevel = null;
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
						
			signOffLevel = new SignOffLevel();
			signOffLevel.setLevel(data.get("FINAL_FLAG").toString());
			signOffLevel.setQualCode(data.get("MK_QUAL_CODE").toString());
			signOffLevel.setSpecCode(data.get("MK_SPES_CODE").toString());
			signOffLevel.setType(data.get("TYPE").toString());
			signOffLevel.setPosition(data.get("SEQ_NR").toString());
			
			signOffLevelList.add(signOffLevel);
		}
		
		log.debug("get user Sign-Off list from network code END");	
		
		return signOffLevelList;
	}
	
	public ArrayList<LabelValueBean> getExternalList(String staffNr, String surname) throws Exception{
		// Return staff name
		ArrayList<LabelValueBean> staffList = new ArrayList<LabelValueBean>();
		String query = "";
		
		if(!"".equals(surname.trim())){

			query = "select nr,surname || ' '|| initials ||' ' ||  title as name" +
			" from mbe" +
			" where surname like '" + surname.toUpperCase() +
			"%' and in_use_flag='Y'";
		}else{

			query = "select nr,surname || ' '|| initials ||' ' ||  title as name" +
			" from mbe " +
			" where nr = " + staffNr.trim() +
			" and in_use_flag='Y'";
		}
		log.debug(query);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query);

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String code = "";
			
			if ( data.get("NR").toString().trim().length()==7){
				code = "0" + data.get("NR").toString();
			}else{
				code = data.get("NR").toString();
			}
			String desc = data.get("NAME").toString();
			staffList.add(new org.apache.struts.util.LabelValueBean(code + " - "
					+ desc, code + desc));
		}
		
		return staffList;
	}
	
	public void recordSignOff(TrackRecord trackRecord, MdAdmissionApplication app)throws Exception {
		
		Connection connection = null;
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);	
			String sql ="";
			String status = "";
			String reasonNotAdmitted = "";
			String additionalRequirements = "";
			String recommendation="";
			int contactPerson=0;
			int supervisor=0;
			int coSupervisor=0;				
			
			//initial recommendation process
			//update mdappl record - only on recommendation - if current level is 1 (only level 1 can make an recommendation)			
			if (app.getStatus().equalsIgnoreCase("R")){
				if (trackRecord.getCurrentLevel().equalsIgnoreCase("1")){	
					recommendation=	trackRecord.getRecommendation().getCode();	
					if ( trackRecord.getRecommendation().getCode().equalsIgnoreCase("LN")){
						status ="RB";	
						recommendation="";
					}else{
						status="R";						
					}
					if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LT")){
						reasonNotAdmitted = trackRecord.getRecommendationComment();
					}if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LE")){
						additionalRequirements = trackRecord.getRecommendationComment();
					}
					if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LF")){
						//* set to values supplied
						if (app.getContactPerson()!=null && app.getContactPerson().getPersonnelNumber()!=null && !app.getContactPerson().getPersonnelNumber().trim().equalsIgnoreCase("")){
							contactPerson=Integer.parseInt(app.getContactPerson().getPersonnelNumber());
						}
						if (app.getSupervisor()!=null && app.getSupervisor().getPersonnelNumber()!=null && !app.getSupervisor().getPersonnelNumber().trim().equalsIgnoreCase("")){
							supervisor=Integer.parseInt(app.getSupervisor().getPersonnelNumber());
						}
						if (app.getJointSupervisor()!=null && app.getJointSupervisor().getPersonnelNumber()!=null && !app.getJointSupervisor().getPersonnelNumber().trim().equalsIgnoreCase("")){
							coSupervisor=Integer.parseInt(app.getJointSupervisor().getPersonnelNumber());
						}				
					}
					
					sql = "update mdappl set admission_status=?,recommend_status=?,reason_not_admit=?,additional_requirements=?," +
		 			" contact_person=?,supervisor =?,joint_supervisor=?" +
		 			" where mk_student_nr=?" +
		 			" and app_sequence_nr=?";
					
					 PreparedStatement ps = connection.prepareStatement(sql);
					   
				   	 ps.setString(1, status); 
					 ps.setString(2, recommendation); 
					 ps.setString(3, reasonNotAdmitted);
					 ps.setString(4, additionalRequirements); 
					 ps.setInt(5, contactPerson);
					 ps.setInt(6, supervisor);
					 ps.setInt(7, coSupervisor);
					 ps.setInt(8, trackRecord.getStudentNumber()); 
					 ps.setInt(9, trackRecord.getSeqNr()); 
					 
			         ps.executeUpdate();
				}
			}
			//Appeal process
			//Only record recommendation on final sign-off - every level can do a recommendation
			if (app.getStatus().equalsIgnoreCase("A")){	
				recommendation=	trackRecord.getRecommendation().getCode();	
				if (trackRecord.getStatus().getCode().equalsIgnoreCase("FS") || 
						(trackRecord.getCurrentLevel().equalsIgnoreCase("E") && trackRecord.getRecommendation().getCode().equalsIgnoreCase("LN"))){	
					if (trackRecord.getCurrentLevel().equalsIgnoreCase("E") && trackRecord.getRecommendation().getCode().equalsIgnoreCase("LN")){
						status ="AB";	
						recommendation ="";
					}else{
						status="A";						
					}
					if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LT")){
						reasonNotAdmitted = trackRecord.getRecommendationComment();
					}if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LE")){
						additionalRequirements = trackRecord.getRecommendationComment();
					}
					if (trackRecord.getRecommendation().getCode().equalsIgnoreCase("LF")){
						//* set to values supplied
						if (app.getContactPerson()!=null && app.getContactPerson().getPersonnelNumber()!=null && !app.getContactPerson().getPersonnelNumber().trim().equalsIgnoreCase("")){
							contactPerson=Integer.parseInt(app.getContactPerson().getPersonnelNumber());
						}
						if (app.getSupervisor()!=null && app.getSupervisor().getPersonnelNumber()!=null && !app.getSupervisor().getPersonnelNumber().trim().equalsIgnoreCase("")){
							supervisor=Integer.parseInt(app.getSupervisor().getPersonnelNumber());
						}
						if (app.getJointSupervisor()!=null && app.getJointSupervisor().getPersonnelNumber()!=null && !app.getJointSupervisor().getPersonnelNumber().trim().equalsIgnoreCase("")){
							coSupervisor=Integer.parseInt(app.getJointSupervisor().getPersonnelNumber());
						}				
					}
					
					
					sql = "update mdappl set admission_status=?,recommend_status=?,reason_not_admit=?,additional_requirements=?," +
		 			" contact_person=?,supervisor =?,joint_supervisor=?" +
		 			" where mk_student_nr=?" +
		 			" and app_sequence_nr=?";
					
					 PreparedStatement ps = connection.prepareStatement(sql);
					   
				   	 ps.setString(1, status); 
					 ps.setString(2, recommendation); 
					 ps.setString(3, reasonNotAdmitted);
					 ps.setString(4, additionalRequirements); 
					 ps.setInt(5, contactPerson);
					 ps.setInt(6, supervisor);
					 ps.setInt(7, coSupervisor);
					 ps.setInt(8, trackRecord.getStudentNumber()); 
					 ps.setInt(9, trackRecord.getSeqNr()); 
					 
			         ps.executeUpdate();
				}
			}
			
			//write tracking record		   
			sql = "insert into mdtrac (MK_STUDENT_NR,APP_SEQUENCE_NR,TIMESTAMP,STATUS_CODE,USER_CODE,PERS_NR,EMAIL_TO,COMMENTS,ASSIGNED_TO_PERS_NO,TRACKING_TYPE," +
					" MK_STUDY_UNIT_CODE,CURRENT_LEVEL,ASSIGNED_TO_LEVEL,RECOMMENDED_VALUE,RECOMMENDED_COMMENT)" +
					" values (?,?,sysTimestamp,?,?,?,?,?,?,?,?,?,?,?,?)";
		   
		     PreparedStatement ps = connection.prepareStatement(sql);
		   
		   	 ps.setInt(1, trackRecord.getStudentNumber()); 
			 ps.setInt(2, trackRecord.getSeqNr()); 
			 ps.setString(3, trackRecord.getStatus().getCode());
			 ps.setString(4, ""); //user code
			 ps.setInt(5, Integer.parseInt(trackRecord.getCurrentPerson().getPersonnelNumber()));
			 ps.setString(6, trackRecord.getAssignToPerson().getEmailAddress());
			 ps.setString(7, trackRecord.getSignoffComment());
			 //if assigned to admin then the assignToPerson is not set
			 if (trackRecord.getAssignToPerson()==null || trackRecord.getAssignToPerson().getPersonnelNumber()==null){
				 ps.setInt(8, 0);
			 }else{
				 ps.setInt(8, Integer.parseInt(trackRecord.getAssignToPerson().getPersonnelNumber())); 
			 }
			 
			 ps.setString(9, "APPL"); //tracking type
			 ps.setString(10, ""); //study unit
			 ps.setString(11, trackRecord.getCurrentLevel());
			 ps.setString(12, trackRecord.getAssignToLevel());
			 ps.setString(13, recommendation);
			 ps.setString(14, trackRecord.getRecommendationComment());
			 
	         ps.executeUpdate();		  
	      
		 
		  connection.commit();
		  connection.setAutoCommit(true);
		  connection.close();
		  log.debug("record sign-off END");	
			
		}
		catch (Exception ex) {
			if (connection!=null){
					log.debug("rollback - record sign-off END");	
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("MdAdmissionQueryDAO: Data has been rollback, Error updating records on signoff / " + ex,ex);
			}
		}
	}
	
	
	/**
	 * Retrieves the a list of lecturers to route the referral/appeal to.
	 * 
	 * @param qualCode
	 * 			Qualification code
	 * @param specCode
	 * 			Speciality code
	 * @return ArrayList<Staff>
	 * 			A list of lecturers to route the referral/appeal to.
	 */
	public 	List<SignOffRouteRecord> getSignOffRoute(String qualCode, String specCode, String type) throws Exception
	{
		List<SignOffRouteRecord> routingList = new ArrayList<SignOffRouteRecord>();

		String query = "SELECT * FROM qsprout" +
					   " WHERE qsprout.MK_QUAL_CODE = ? and qsprout.MK_SPES_CODE = ? and qsprout.TYPE= ? order by final_flag";
		
		try {	
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
		List queryList = jdt.queryForList(query, new Object[]{qualCode, specCode, type});
		Iterator i = queryList.iterator();
		
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				
				Person person = new Person();
				SignOffLevel signOffLevel = new SignOffLevel();
				SignOffRouteRecord signOffRoute = new SignOffRouteRecord();
				
				String persno = (data.get("STAFF_NUMBER").toString().trim());
				PersonnelDAO persnoDao = new PersonnelDAO();
				person = persnoDao.getPerson(Integer.parseInt(persno));	
				
				signOffLevel = new SignOffLevel();
				signOffLevel.setLevel(data.get("FINAL_FLAG").toString());
				signOffLevel.setQualCode(data.get("MK_QUAL_CODE").toString());
				signOffLevel.setSpecCode(data.get("MK_SPES_CODE").toString());
				signOffLevel.setType(data.get("TYPE").toString());
				signOffLevel.setPosition(data.get("SEQ_NR").toString());
				
				signOffRoute.setPerson(person);
				signOffRoute.setSignOffLevel(signOffLevel);
							
				routingList.add(signOffRoute);
			}
		} catch (Exception ex) {
			throw new Exception("MdAdmissionDAO : Error reading RoutingList / " + ex, ex);
		}
				
		return routingList;
	}
	
	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query     The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 */
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get(field)!= null){
				result = data.get(field).toString();
			}else{
				result = "";
			}
		}

		return result;
	}
	
	private String padString(String str, int length){
		StringBuffer outputBuffer = new StringBuffer(str);
		length = 3 - str.length();
		for (int i = 0; i < length; i++){
		   outputBuffer.append("\u00A0");
		}
		return outputBuffer.toString();
	}
	
	private String replaceNull(Object object) {
		String stringValue = "";
		if (object == null) {
		} else {
			stringValue = object.toString().trim();
		}
		return stringValue;
	}
	
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}
}
