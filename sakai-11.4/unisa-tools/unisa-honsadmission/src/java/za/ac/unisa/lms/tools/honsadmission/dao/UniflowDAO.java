package za.ac.unisa.lms.tools.honsadmission.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.datacontract.schemas._2004._07.UniflowFindAndGetService.*;
import org.tempuri.*;                                                       //new Uniflow change
import org.tempuri.newUniflow.*;

import za.ac.unisa.lms.tools.honsadmission.exception.UniflowException;
import za.ac.unisa.lms.tools.honsadmission.forms.UniflowFile;

public class UniflowDAO {

		public static Log log = LogFactory.getLog(UniflowDAO.class);
	
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
			//search for m&d documents for specific student				
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
			
			throw new UniflowException ("UniflowDAO: getDocList : /" + rex.getMessage(), rex);
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
			throw new UniflowException ("UniflowDAO: getFileFromUniflow /" + io, io );
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
			
			throw new UniflowException ("UniflowDAO: getNewDocList : /" + rex.getMessage(), rex);
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

}
