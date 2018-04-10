package za.ac.unisa.lms.tools.mdadmission.dao;


import java.io.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
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
import org.tempuri.*;                                                       //new Uniflow change
import org.tempuri.newUniflow.*;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.mdadmission.Constants;
import za.ac.unisa.lms.tools.mdadmission.exception.UniflowException;
import za.ac.unisa.lms.tools.mdadmission.forms.AddressPH;
import za.ac.unisa.lms.tools.mdadmission.forms.MdAdmissionApplication;
import za.ac.unisa.lms.tools.mdadmission.forms.MdAdmissionForm;
import za.ac.unisa.lms.tools.mdadmission.forms.PreviousQualification;
import za.ac.unisa.lms.tools.mdadmission.forms.SignOffLevel;
import za.ac.unisa.lms.tools.mdadmission.forms.Staff;
import za.ac.unisa.lms.tools.mdadmission.forms.UniflowFile;
import za.ac.unisa.lms.tools.mdadmission.forms.TrackRecord;

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
				System.out.println(result.getResultStatus().toString());
				log.debug("proxy error: " + result.getResultStatus().toString());
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
		    		uFile.setUniflowVersion("old");
		    		log.debug(uFile.toString());
		    		docList.add(uFile);
			    	if ("Worklist".equalsIgnoreCase(docLocation.getType().toString())){
			    		// FOR WORKLISTS: add first doc and check for more via sibling count
			    		DocumentID docId = new DocumentID(1,docLocation ,records[k].getUniqueId());
			    		retrievalResult = getFileFromOldUniflow(docId);
			    		Document doc = new Document();
			    		doc = retrievalResult.getDocument();
			    		int counter = 1;
			    		while(doc.getSiblingCount()>0){
			    			// get the next doc and add to list
			    			counter = counter +1;
			    			docId = new DocumentID(counter,docLocation ,records[k].getUniqueId());
				    		retrievalResult = getFileFromOldUniflow(docId);
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
				    		siblingFile.setUniflowVersion("old");
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
	
	public RetrievalResult getFileFromUniflow(ArrayList<UniflowFile> docList,String uniqueID,String uniflowVersion,String in) throws UniflowException, Exception{
		log.debug("get file from uniflow");
		
		RetrievalRequest rRequest = new RetrievalRequest();
		RetrievalResult retrievalResult = new RetrievalResult();		
		IUniflowFindAndGetServiceProxy oldProxy = new IUniflowFindAndGetServiceProxy();
		NewIUniflowFindAndGetServiceProxy newProxy = new NewIUniflowFindAndGetServiceProxy();
		DocumentLocation docLocation = new DocumentLocation();
		
		try
		{		
			for (int i = 0; i < docList.size(); i++) {
				UniflowFile uFile = docList.get(i);
				if (uFile.getUniqueId().equalsIgnoreCase(uniqueID) && uFile.getUniflowVersion().equalsIgnoreCase(uniflowVersion) ){
					docLocation = uFile.getDocumentLocation();
					uniflowVersion=uFile.getUniflowVersion();
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
	       
	        if (uniflowVersion.equalsIgnoreCase("old")){
	        	retrievalResult = oldProxy.retrieve(rRequest);
	        } 
	        if (uniflowVersion.equalsIgnoreCase("new")){
	        	retrievalResult = newProxy.retrieve(rRequest);
	        } 
	        
		}
		catch (IOException io)
		{
			throw new UniflowException ("MdAdmissionQueryDAO: getFileFromUniflow /" + io, io );
		}
		
		log.debug("get file from uniflow END");
        		
        return retrievalResult;
	}
	
	private RetrievalResult getFileFromOldUniflow(DocumentID docId) throws RemoteException, MalformedURLException{

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
	
//New Uniflow webservice change 20160914
public ArrayList<UniflowFile> getNewDocsList(String studentNo) throws MalformedURLException, ServiceException, UniflowException{
		
		log.debug("get doc list");

		ArrayList<UniflowFile> docList = new ArrayList<UniflowFile>();
		NewIUniflowFindAndGetServiceProxy proxy = new NewIUniflowFindAndGetServiceProxy();	
		RetrievalResult retrievalResult = new RetrievalResult();
		AuthenticationDetails details = new AuthenticationDetails();
		try
		{
			
			// --- 
			// set authentication details
			details.setPassword("VFR$5tgb");
			details.setUserName("MDUniflow_user");
			//details.setPassword("md");
			//details.setUserName("md");
			
			// ---
			// get search targets
			GetSearchTargetsResult result = proxy.getSearchTargets(details); 
			// if search for target not successfull
			if (!"Successful".equalsIgnoreCase(result.getResultStatus().toString())){
				System.out.println(result.getResultStatus().toString());
				log.debug("proxy error: " + result.getResultStatus().toString());
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
			SearchField studentField = new SearchField("unifstudentno", studentNo);
			//SearchField studentField = new SearchField("unifstudentno", "37315749");
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
		    	String docExt = "";
		    	for (int k=0 ; k  < records.length ; k++)
		    	{
		    		// ---
		    		// get first documents doctype
		    		String[] recordField = records[k].getFields();
		    		for(int l =0;  l< recordField.length ; l++)
		    		{
		    			if (fieldNames[l] != null && "UNIFDOCTYPE".equalsIgnoreCase(fieldNames[l].toString())){    //debug Johanet 
		    				docType = recordField[l];
		    			}
		    			if (fieldNames[l] != null && "FILE_EXT".equalsIgnoreCase(fieldNames[l].toString())){    //add code to eliminate txt files
		    				docExt = recordField[l];                                                            //add code to eliminate txt files
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
		    		uFile.setUniflowVersion("new");
		    		log.debug(uFile.toString());
		    		if (!docExt.equalsIgnoreCase("TXT")){		    		
		    		docList.add(uFile);
				    	if ("Worklist".equalsIgnoreCase(docLocation.getType().toString())){
				    		// FOR WORKLISTS: add first doc and check for more via sibling count
				    		DocumentID docId = new DocumentID(1,docLocation ,records[k].getUniqueId());
				    		retrievalResult = getFileFromNewUniflow(docId);
				    		Document doc = new Document();
				    		doc = retrievalResult.getDocument();
				    		int counter = 1;
				    		while(doc.getSiblingCount()>0){
				    			// get the next doc and add to list
				    			counter = counter +1;
				    			docId = new DocumentID(counter,docLocation ,records[k].getUniqueId());
					    		retrievalResult = getFileFromNewUniflow(docId);
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
					    		siblingFile.setUniflowVersion("new");
					    		log.debug(uFile.toString());
					    		docList.add(siblingFile);
				    		}
				    	}				    	
		    		}	
		    	}
		    	
		    }
		}catch (RemoteException rex) {
			
			throw new UniflowException ("MdAdmissionQueryDAO: getNewDocList : /" + rex.getMessage(), rex);
		}
	
		log.debug("get doc list END");
		return docList;

	}

private RetrievalResult getFileFromNewUniflow(DocumentID docId) throws RemoteException, MalformedURLException{

	log.debug("get file from new uniflow2");
	RetrievalRequest rRequest = new RetrievalRequest();
	RetrievalResult retrievalResult = new RetrievalResult();		
	
	NewIUniflowFindAndGetServiceProxy proxy = new NewIUniflowFindAndGetServiceProxy();
	// --- 
	// set authentication details
	AuthenticationDetails details = new AuthenticationDetails();
	details.setPassword("VFR$5tgb");
	details.setUserName("MDUniflow_user");
	//details.setPassword("md");
	//details.setUserName("md");	

    rRequest.setAuthenticationDetails(details);
    rRequest.setMandDOnly(true);
    //DocumentLocation docLocation = new DocumentLocation("AMEACCESS", LocationType.Worklist);
   // DocumentID docId = new DocumentID(1,docLocation ,"1:\\DD9\\1425.VSD\\16.CP");
    rRequest.setDocumentID(docId);
    retrievalResult = proxy.retrieve(rRequest);

	log.debug("get file from new uniflow2 END");
	
    return retrievalResult;
}
	
	private String getDocTypeDesc(String docType){
		String desc = "default description";
		
		if("AR".equalsIgnoreCase(docType)){
			desc = "Academic record";
		} else if("ID".equalsIgnoreCase(docType)){
			desc = docType;
		}else if("LETTERS".equalsIgnoreCase(docType)){
			desc = "Letter";
		}else if("CV".equalsIgnoreCase(docType)){
			desc = docType;
		}else if("SYLLABI".equalsIgnoreCase(docType)){
			desc = "Syllabi";
		}else if("QUEST".equalsIgnoreCase(docType)){
			desc = "Questionnaire";
		}else if("WORKEXP".equalsIgnoreCase(docType)){
			desc = "Proof of work experience";
		}else if("ESSAY".equalsIgnoreCase(docType)){
			desc = "Essay";
		}else if("SWREG".equalsIgnoreCase(docType)){
			desc = "Social worker registration";
		}else if("SAQA".equalsIgnoreCase(docType)){
			desc = "SAQA evaluation";
		}else if("DISS".equalsIgnoreCase(docType)){
			desc = "Dissertation/Thesis";
		}else if("MVAPORT".equalsIgnoreCase(docType)){
			desc = "MVA Portfolio";
		}else if("ENGREQ".equalsIgnoreCase(docType)){
			desc = "English language requirement";
		}else if("OUTLINE".equalsIgnoreCase(docType)){
			desc = "Research outline";	
		}else if("DECLARE".equalsIgnoreCase(docType)){
			desc = "Declaration";	
		}		
		return desc;
		
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
	 * This method returns a list of sign-off record for the specific student
	 *
	 * @param studNr Student number
	 */
	public ArrayList<Staff> getSignOffList(String studentNr, String applicationNr, String statusCode) throws Exception {
		log.debug("get sign off");
		ArrayList<Staff> list = new ArrayList<Staff>();
		String trackingStatus = "";
		
		if ("R".equalsIgnoreCase(statusCode)){
			trackingStatus = "RS";
		}else if ("A".equalsIgnoreCase(statusCode)){
			trackingStatus = "AS";
		}

		String sql = " SELECT PERS_NR,  substr(to_char(timestamp,'yyyy/mm/dd HH:MI:SS:FF6 AM'),1,10) as timestamp, comments from mdtrac where mk_student_nr="+studentNr+
		             " and app_sequence_nr=?"+
		             " and status_code=? order by timestamp asc";
		log.debug(sql);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{applicationNr, trackingStatus});

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Staff staff = new Staff();
				if (data.get("PERS_NR") != null){
					staff.setStaffNr(data.get("PERS_NR").toString());
				}
				if (data.get("TIMESTAMP") != null){
					staff.setSignOffDate(data.get("TIMESTAMP").toString());
				}
				if (data.get("COMMENTS") != null){
					staff.setSignOffComment(data.get("COMMENTS").toString());
				}
				staff.setName(getStaffName(staff.getStaffNr()));
				
				list.add(staff);
			}
			log.debug("get sign off END");

		return list;
	}
	
	public boolean isFinalSignOff(String studentNr, String applicationNr, String statusCode) throws Exception {

		log.debug("get final sign off");
		boolean result = false;
		String trackingStatus = "";

		if ("R".equalsIgnoreCase(statusCode)){
			trackingStatus = "RS";
		}else if ("A".equalsIgnoreCase(statusCode)){
			trackingStatus = "AS";
		}

		String sql = " SELECT count(*) as totalno from mdtrac where mk_student_nr="+studentNr+
		" and app_sequence_nr=?"+
		" and status_code=? order by timestamp asc";
		log.debug(sql);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql, new Object []{applicationNr, trackingStatus});
		int numberOfSignOffs = 0;

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("TOTALNO") != null){
				numberOfSignOffs = Integer.valueOf(data.get("TOTALNO").toString());
			};
		}

		// number of sign-off's needed
		sql = " SELECT count(*) as totalno from mdtrac where mk_student_nr="+studentNr+
		" and app_sequence_nr="+applicationNr+
		" and status_code='"+statusCode+"' order by timestamp asc";

		 jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList(sql);
		int signoffsNeeded = 0;

		 i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("TOTALNO") != null){
			  signoffsNeeded = Integer.valueOf(data.get("TOTALNO").toString());
			}
		}
		
		if(numberOfSignOffs >= signoffsNeeded){
			result = true;
		}

		log.debug("get final sign off END");
		return result;
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
		" and admission_status in ('R','A') and code=MK_QUALIFICATION_C";
		
		//Johanet 20140214 added code - read on application number if entered otherwise get the latest application number
		if (applicationNr == null || applicationNr.trim().equalsIgnoreCase("")){
			sql = sql + " order by application_date";
		}else{
			sql = sql + " and app_sequence_nr=" + Integer.parseInt(applicationNr.trim())		;
		}
		
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
				if (data.get("RECOMMEND_STATUS") != null){
					app.setRecommendation(data.get("RECOMMEND_STATUS").toString());
				}
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
					app.getContactPerson().setStaffNr(data.get("CONTACT_PERSON").toString());
					Staff staff = getStaffMemberDetail(data.get("CONTACT_PERSON").toString());
					if ("".equals(staff.getName().trim())){
						 staff = getExternalStaffMemberDetail(data.get("CONTACT_PERSON").toString());
					}
					app.setContactPerson(staff);
				}
				if  (data.get("SUPERVISOR") != null){
					app.getSupervisor().setStaffNr(data.get("SUPERVISOR").toString());
					Staff staff = getStaffMemberDetail(data.get("SUPERVISOR").toString());
					if ("".equals(staff.getName().trim())){
						 staff = getExternalStaffMemberDetail(data.get("SUPERVISOR").toString());
					}
					app.setSupervisor(staff);
				}
				if  (data.get("JOINT_SUPERVISOR") != null){
					app.getJointSupervisor().setStaffNr(data.get("JOINT_SUPERVISOR").toString());
					Staff staff = getStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
					if ("".equals(staff.getName().trim())){
						 staff = getExternalStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
					}
					app.setJointSupervisor(staff);
				}
				
				//Johanet 20140220 - add code to display previous decision on appeal
				app.setPreviousRecommendation(app.getRecommendation());
				app.setPreviousRecommendationComment("");
				
				if (app.getRecommendation().equals("LT")){
					app.setPreviousRecommendation("Student not admitted");
					app.setPreviousRecommendationComment(app.getNotAdmittedComment());	
				}
				if (app.getRecommendation().equals("LS")){	
					app.setPreviousRecommendation("The student may only register for the following modules");
					app.setPreviousRecommendationComment(app.getStructuredComment());				
					
				}
				if (app.getRecommendation().equals("LE")){
					app.setPreviousRecommendation("Student not admitted. Student must first complete the following module(s). Compliance with below requirements will automatically qualify the student to proceed.");				
					app.setPreviousRecommendationComment(app.getRequirementComment());	
				}
				
				//Johanet 20140212 - added check to determine if a decision has been made after an appeal or an referral
				List <TrackRecord> trackingList = new ArrayList<TrackRecord>();
				trackingList = getApplTrackingList(Integer.parseInt(studentNr), Integer.parseInt(app.getApplicationNr()));
				
				app.setRecommendation("");
				for (int j=0; j < trackingList.size(); j++){
					TrackRecord trackRecord = new TrackRecord();
					trackRecord = (TrackRecord)trackingList.get(j);
					if (trackRecord.getStatusCode().equalsIgnoreCase(app.getStatus())){
						j=trackingList.size();
					}
					if (trackRecord.getStatusCode().equalsIgnoreCase("LF")){
						app.setRecommendation("yes"); 
					}
					if (trackRecord.getStatusCode().equalsIgnoreCase("LT")){
						app.setRecommendation("no"); 
					}
					if (trackRecord.getStatusCode().equalsIgnoreCase("LS")){
						app.setRecommendation("struct"); 
					}
					if (trackRecord.getStatusCode().equalsIgnoreCase("LE")){
						app.setRecommendation("addreq"); 
					}					
				}
				
				//Johanet 20140220 - clear fields for new recommendation
				if (app.getStatus().equalsIgnoreCase("A") && app.getRecommendation().equalsIgnoreCase("")){
					app.setRequirementComment("");	
					app.setStructuredComment("");
					app.setNotAdmittedComment("");
				}
				
//				if  (data.get("RECOMMEND_STATUS") != null){
//					if ("LF".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//						app.setRecommendation("yes");
//					}else if ("LT".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//						app.setRecommendation("no");
//					}else if ("LS".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//						app.setRecommendation("struct");
//					}else if ("LE".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//						app.setRecommendation("addreq");
//					}
//				}
			}

			log.debug("get appl record END");
		return app;
	}
	
	public List<TrackRecord> getApplTrackingList(int studentNr, int seqNr) throws Exception {
		List <TrackRecord> trackingList = new ArrayList<TrackRecord>();
		
		String sql = "select to_char(timestamp,'YYYY/MM/DD') as trackDate, status_code" +
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
				TrackRecord record = new TrackRecord();
				record.setSeqNr(seqNr);
				record.setDate(replaceNull(data.get("trackDate")));
				record.setStatusCode(replaceNull(data.get("status_code")));			
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
						String staffNumber, Map<String, 
						MdAdmissionApplication> studentApplicationList, 
						Staff prevSignoff,
						String qualificationCode,
						String specialityCode) 
	throws Exception {

		log.debug("get students application records");
		MdAdmissionApplication app = null;
		
//		String sql = "SELECT mdappl.*, stu.*, grd.LONG_ENG_DESCRIPTI as descr " +
//					 "FROM mdappl, qsprout, grd,  stu " +
//						  "WHERE mdappl.ADMISSION_STATUS in (?,?) and " +
//						  "(mdappl.MK_QUALIFICATION_C = qsprout.MK_QUAL_CODE and " +
//						  "mdappl.SPECIALITY_CODE = qsprout.MK_SPES_CODE) " +
//						  "and grd.code = mdappl.MK_QUALIFICATION_C and qsprout.STAFF_NUMBER = ? and " +
//						  "mdappl.MK_STUDENT_NR = stu.NR";
		
		String sql = "SELECT mdappl.*, stu.*, grd.LONG_ENG_DESCRIPTI as descr " +
						"FROM mdappl, qsprout, grd,  stu " +
						"WHERE mdappl.ADMISSION_STATUS in (?,?) " + 
							"and mdappl.MK_QUALIFICATION_C = qsprout.MK_QUAL_CODE " +  
							"and mdappl.SPECIALITY_CODE = qsprout.MK_SPES_CODE " + 
							"and grd.code = mdappl.MK_QUALIFICATION_C " +
							"and qsprout.MK_QUAL_CODE = ? " + 
							"and qsprout.MK_SPES_CODE = ? " +  
							"and qsprout.STAFF_NUMBER = ? " + 
							"and mdappl.MK_STUDENT_NR = stu.NR " +
							"and not exists (select mk_student_nr from mdtrac where mk_student_nr = mdappl.mk_student_nr " +  
								"and mdappl.APP_SEQUENCE_NR = mdtrac.APP_SEQUENCE_NR " +
								"and mdtrac.PERS_NR =  qsprout.STAFF_NUMBER " +
								"and mdtrac.STATUS_CODE = decode(mdappl.ADMISSION_STATUS,?,?,?,?))";
		
		
		Object [] params  =  null;
				  
		if (prevSignoff != null && prevSignoff.getStaffNr() != null)			
		{			
			sql = sql + "and exists (select mk_student_nr from mdtrac where mk_student_nr = mdappl.mk_student_nr " +  
			"and mdappl.APP_SEQUENCE_NR = mdtrac.APP_SEQUENCE_NR " +
			"and mdtrac.PERS_NR =  ? " +
			"and mdtrac.STATUS_CODE = decode(mdappl.ADMISSION_STATUS,?,?,?,?))";		
			
			params  = 
				new Object []{Constants.ADMISSION_STATUS_REFERAL, 
					  		  Constants.ADMISSION_STATUS_APPEAL,
					  		  qualificationCode,
					  		  specialityCode,
					  		  staffNumber, 
					  		  Constants.ADMISSION_STATUS_REFERAL, 
					  		  Constants.MDTRAC_STATUS_REFERAL,
					  		  Constants.ADMISSION_STATUS_APPEAL,					  		
					  		  Constants.MDTRAC_STATUS_APPEAL,
					  		  prevSignoff.getStaffNr(),
					  		  Constants.ADMISSION_STATUS_REFERAL, 
					  		  Constants.MDTRAC_STATUS_REFERAL,
					  		  Constants.ADMISSION_STATUS_APPEAL,					  		
					  		  Constants.MDTRAC_STATUS_APPEAL};
		}else {
			params  = 
				new Object []{Constants.ADMISSION_STATUS_REFERAL, 
					  		  Constants.ADMISSION_STATUS_APPEAL, 
					  		  qualificationCode,
					  		  specialityCode,
					  		  staffNumber, 
					  		  Constants.ADMISSION_STATUS_REFERAL, 
					  		  Constants.MDTRAC_STATUS_REFERAL,
					  		  Constants.ADMISSION_STATUS_APPEAL, 					  		 
					  		  Constants.MDTRAC_STATUS_APPEAL};
		}
		
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
			if (data.get("RECOMMEND_STATUS") != null){
				app.setRecommendation(data.get("RECOMMEND_STATUS").toString());
			}
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
				app.getContactPerson().setStaffNr(data.get("CONTACT_PERSON").toString());
				Staff staff = getStaffMemberDetail(data.get("CONTACT_PERSON").toString());
				if ("".equals(staff.getName().trim())){
					 staff = getExternalStaffMemberDetail(data.get("CONTACT_PERSON").toString());
				}
				app.setContactPerson(staff);
			}
			if  (data.get("SUPERVISOR") != null){
				app.getSupervisor().setStaffNr(data.get("SUPERVISOR").toString());
				Staff staff = getStaffMemberDetail(data.get("SUPERVISOR").toString());
				if ("".equals(staff.getName().trim())){
					 staff = getExternalStaffMemberDetail(data.get("SUPERVISOR").toString());
				}
				app.setSupervisor(staff);
			}
			if  (data.get("JOINT_SUPERVISOR") != null){
				app.getJointSupervisor().setStaffNr(data.get("JOINT_SUPERVISOR").toString());
				Staff staff = getStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
				if ("".equals(staff.getName().trim())){
					 staff = getExternalStaffMemberDetail(data.get("JOINT_SUPERVISOR").toString());
				}
				app.setJointSupervisor(staff);
			}
			
			//Johanet 20140220 - add code to display previous decision on appeal
			app.setPreviousRecommendation(app.getRecommendation());
			app.setPreviousRecommendationComment("");
			
			if (app.getRecommendation().equals("LT")){
				app.setPreviousRecommendation("Student not admitted");
				app.setPreviousRecommendationComment(app.getNotAdmittedComment());	
			}
			if (app.getRecommendation().equals("LS")){	
				app.setPreviousRecommendation("The student may only register for the following modules");
				app.setPreviousRecommendationComment(app.getStructuredComment());				
				
			}
			if (app.getRecommendation().equals("LE")){
				app.setPreviousRecommendation("Student not admitted. Student must first complete the following module(s). Compliance with below requirements will automatically qualify the student to proceed.");				
				app.setPreviousRecommendationComment(app.getRequirementComment());	
			}
			
			//Johanet 20140212 - added check to determine if a decision has been made after an appeal or an referral
			List <TrackRecord> trackingList = new ArrayList<TrackRecord>();
			trackingList = getApplTrackingList(Integer.parseInt(data.get("MK_STUDENT_NR").toString()), Integer.parseInt(app.getApplicationNr()));
					
			app.setRecommendation("");
			for (int j=0; j < trackingList.size(); j++){
				TrackRecord trackRecord = new TrackRecord();
				trackRecord = (TrackRecord)trackingList.get(j);
				if (trackRecord.getStatusCode().equalsIgnoreCase(app.getStatus())){
					j=trackingList.size();
				}
				if (trackRecord.getStatusCode().equalsIgnoreCase("LF")){
					app.setRecommendation("yes"); 
				}
				if (trackRecord.getStatusCode().equalsIgnoreCase("LT")){
					app.setRecommendation("no"); 
				}
				if (trackRecord.getStatusCode().equalsIgnoreCase("LS")){
					app.setRecommendation("struct"); 
				}
				if (trackRecord.getStatusCode().equalsIgnoreCase("LE")){
					app.setRecommendation("addreq"); 
				}					
			}
			
			//Johanet 20140220 - clear fields for new recommendation
			if (app.getStatus().equalsIgnoreCase("A") && app.getRecommendation().equalsIgnoreCase("")){
				app.setRequirementComment("");	
				app.setStructuredComment("");
				app.setNotAdmittedComment("");
			}
			
			
//			if  (data.get("RECOMMEND_STATUS") != null){
//				if ("LF".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//					app.setRecommendation("yes");
//				}else if ("LT".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//					app.setRecommendation("no");
//				}else if ("LS".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//					app.setRecommendation("struct");
//				}else if ("LE".equalsIgnoreCase(data.get("RECOMMEND_STATUS").toString())){
//					app.setRecommendation("addreq");
//				}
//			}
			
			//change Johanet - key of map cannot just be student number, must also include the sequence number
			String studentNr=data.get("MK_STUDENT_NR").toString().trim();
			if (studentNr.length()==7){
				studentNr="0" + studentNr; 
			}
			String mdapplKey=studentNr + app.getApplicationNr().trim();
			
			studentApplicationList.put(mdapplKey, app);
			
//			studentApplicationList.put(
//					data.get("MK_STUDENT_NR").toString(), app);
		}

		log.debug("get appl record END");
		return studentApplicationList;
	}
	
public void writeSignOff(MdAdmissionApplication app, String studentNr, Staff staff) throws NumberFormatException, InterruptedException{
		
		Calendar cal = Calendar.getInstance();
		java.util.Date now = cal.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		String statusCode = "";
		if("A".equalsIgnoreCase(app.getStatus())){
			statusCode = "AS";
		}else if("R".equalsIgnoreCase(app.getStatus())){
			statusCode = "RS";
		}
		
		String sql = "insert into mdtrac (mk_student_nr, app_sequence_nr,timestamp, status_code,pers_nr,comments,tracking_type,mk_study_unit_code) values (?, ?, ?, ?, ?, ?,'APPL',' ')";
		log.debug(sql);
		Thread.sleep(Long.parseLong("1000"));
		jdt.update(sql,new Object[] { studentNr,app.getApplicationNr(),currentTimestamp,statusCode,staff.getStaffNr(),app.getSignOffComment()});
		
	}

public void writeRecommendStatusTracking(MdAdmissionApplication app, String studentNr, Staff staff, String recommendStatus) throws NumberFormatException, InterruptedException{
	
	Calendar cal = Calendar.getInstance();
	java.util.Date now = cal.getTime();
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
	String statusCode = "";
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	if ("yes".equalsIgnoreCase(app.getRecommendation())){
		statusCode = "LF";
	}else if ("no".equalsIgnoreCase(app.getRecommendation())){
		statusCode = "LT";
	}else if ("struct".equalsIgnoreCase(app.getRecommendation())){
		statusCode = "LS";
	}else if ("addreq".equalsIgnoreCase(app.getRecommendation())){
		statusCode = "LE";
	}
	
	String sql = "insert into mdtrac (mk_student_nr, app_sequence_nr,timestamp, status_code,pers_nr,comments,tracking_type,mk_study_unit_code) values (?, ?, ?, ?, ?, ?,'APPL',' ')";
	log.debug(sql);
	Thread.sleep(Long.parseLong("1000"));
	jdt.update(sql,new Object[] { studentNr,app.getApplicationNr(),currentTimestamp,statusCode,staff.getStaffNr(),""});
	
}

public void writeFinalSignOff(MdAdmissionApplication app, String studentNr, Staff staff){
	
	Calendar cal = Calendar.getInstance();
	java.util.Date now = cal.getTime();
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	
	String sql = "insert into mdtrac (mk_student_nr, app_sequence_nr,timestamp, status_code,pers_nr,comments,tracking_type,mk_study_unit_code) values (?, ?, ?, ?, ?, ?,'APPL',' ')";
	log.debug(sql);
	jdt.update(sql,new Object[] { studentNr,app.getApplicationNr(),currentTimestamp,"FS",staff.getStaffNr(),""});
		
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
	
	public Staff getStaffMemberDetail(String staffNr) throws Exception{		
		
		log.debug("get staff member detail");	
		// Return staff name
		Staff staff = new Staff();
		
		String query = "select persno, contact_telno, email_address, title || ' ' || initials ||' ' || surname as name" +
		" from staff " +
		" where persno = ?" ;
		log.debug(query);
		

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{staffNr});

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if(data.get("EMAIL_ADDRESS")!= null){
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("CONTACT_TELNO")!= null){
				staff.setTelephone(data.get("CONTACT_TELNO").toString().trim());
			}
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("PERSNO").toString().trim());
		}
		log.debug("get staff member detail END");		
		return staff;
	}
	
	public Staff getExternalStaffMemberDetail(String staffNr) throws Exception{
		// Return staff name
		Staff staff = new Staff();
		
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
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("NR").toString().trim());
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
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("WORK_NUMBER")!= null){
				staff.setTelephone(data.get("WORK_NUMBER").toString().trim());
			}
		}
		return staff;
	}
	
	public Staff getStaffFromNetworkCode(String networkCode) throws Exception{
		log.debug("get staff from network code");	
		// Return staff name
		Staff staff = new Staff();
		
		String query = "select persno, contact_telno, email_address, title || ' ' || initials ||' ' || surname as name" +
		" from staff " +
		" where novell_user_id = ?" ;
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{networkCode.toUpperCase()});

		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if(data.get("EMAIL_ADDRESS")!= null){
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("CONTACT_TELNO")!= null){
				staff.setTelephone(data.get("CONTACT_TELNO").toString().trim());
			}
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("PERSNO").toString().trim());
		}
		log.debug("get staff from network code END");	
		
		return staff;
	}
	
	public Staff getStaffRoutDet(String networkCode) throws Exception{
		log.debug("get staff from network code");	
		// Return staff name
		Staff staff = new Staff();
		
		String query = "SELECT staff.persno, staff.contact_telno, " +
							"staff.email_address, " +
							"staff.title || ' ' || staff.initials ||' ' || staff.surname as name, " +
							"qsprout.final_flag , qsprout.MK_QUAL_CODE, qsprout.MK_SPES_CODE " +
						"FROM staff, qsprout " +
						"WHERE staff.novell_user_id = ? and staff.PERSNO = qsprout.STAFF_NUMBER " +
						"order by qsprout.MK_QUAL_CODE, qsprout.MK_SPES_CODE";
		log.debug(query);

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(query, new Object []{networkCode.toUpperCase()});
		
		ArrayList<SignOffLevel> signOffLevelList = new ArrayList<SignOffLevel>();
		SignOffLevel  signOffLevel = null;
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if(data.get("EMAIL_ADDRESS")!= null){
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			if(data.get("CONTACT_TELNO")!= null){
				staff.setTelephone(data.get("CONTACT_TELNO").toString().trim());
			}
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("PERSNO").toString().trim());
			
			signOffLevel = new SignOffLevel();
			signOffLevel.setFinalFlag(data.get("FINAL_FLAG").toString());
			signOffLevel.setQualCode(data.get("MK_QUAL_CODE").toString());
			signOffLevel.setSpecCode(data.get("MK_SPES_CODE").toString());
			
			staff.setFinalFlag(data.get("FINAL_FLAG").toString());
			signOffLevelList.add(signOffLevel);
		}
		
		staff.setSignOffLevelList(signOffLevelList);
		log.debug("get staff from network code END");	
		
		return staff;
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
	
	public void updateApplication(MdAdmissionApplication app, String studentNr) {

		log.debug("update appl");	
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "";
		String contact = app.getContactPerson().getStaffNr();
		String supervisor = app.getSupervisor().getStaffNr();
		String jointsuper = app.getJointSupervisor().getStaffNr();
		
		Object[] parameters = null;
		
		if ("".equals(contact)){
			contact = "0";
		}
		if ("".equals(supervisor)){
			supervisor = "0";
		}
		if ("".equals(jointsuper)){
			jointsuper = "0";
		}

		if ("yes".equalsIgnoreCase(app.getRecommendation())){
			sql = "update mdappl set contact_person=?,reason_not_admit='',struct_degree='',additional_requirements=''" +
			" , supervisor =?" +
			" , joint_supervisor=?" +
			" , recommend_status='LF'"+
			" where mk_student_nr=?" +
			" and app_sequence_nr=?";
			
			parameters = new Object []{contact, supervisor, jointsuper, studentNr, app.getApplicationNr() };
			
		}else if ("no".equalsIgnoreCase(app.getRecommendation())){
			sql = "update mdappl set reason_not_admit=?,struct_degree='',additional_requirements=''"+
			" , recommend_status='LT'"+
			" where mk_student_nr= ?"+
			" and app_sequence_nr= ?";
			
			parameters = new Object []{app.getNotAdmittedComment(), studentNr, app.getApplicationNr() };			
			
		}else if ("struct".equalsIgnoreCase(app.getRecommendation())){
			sql = "update mdappl set struct_degree=?,reason_not_admit='',additional_requirements='' " +
			" , recommend_status='LS'"+
			" where mk_student_nr= ?" +
			" and app_sequence_nr= ?";
			
			parameters = new Object []{app.getStructuredComment(), studentNr, app.getApplicationNr() };	
		}else if ("addreq".equalsIgnoreCase(app.getRecommendation())){
			sql = "update mdappl set additional_requirements= ?,reason_not_admit='',struct_degree=''"+
			" , recommend_status='LE'"+
			" where mk_student_nr= ?"+
			" and app_sequence_nr= ?";
			
			parameters = new Object []{app.getRequirementComment(), studentNr, app.getApplicationNr() };	
		}
		log.debug(sql);
		jdt.update(sql, parameters);
		
		log.debug("update appl END");	
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
	public ArrayList<Staff> getRoutingList(String qualCode, String specCode)
	{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String query = "SELECT * FROM qsprout, staff " +
					   "WHERE qsprout.STAFF_NUMBER = staff.PERSNO and " +
					   "qsprout.MK_QUAL_CODE = ? and qsprout.MK_SPES_CODE = ? ";
		
		ArrayList<Staff> routingList = new ArrayList<Staff>();
		Staff staff = null;
		List queryList = jdt.queryForList(query, new Object[]{qualCode, specCode});
		Iterator i = queryList.iterator();
		
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			
			staff = new Staff();
			
			if(data.get("EMAIL_ADDRESS")!= null){
				staff.setEmailAddress(data.get("EMAIL_ADDRESS").toString().trim());
			}
			
			if(data.get("CONTACT_TELNO")!= null){
				staff.setTelephone(data.get("CONTACT_TELNO").toString().trim());
			}
			
			staff.setName(data.get("NAME").toString().trim());
			staff.setStaffNr(data.get("PERSNO").toString().trim());
			
			if (data.get("FINAL_FLAG")!= null){
				staff.setFinalFlag(data.get("FINAL_FLAG").toString());
			}
						
			routingList.add(staff);
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
	
}
