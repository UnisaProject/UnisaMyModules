package za.ac.unisa.lms.tools.broadbandrequest.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.dao.Gencod;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.tools.broadbandrequest.forms.Contact;
import za.ac.unisa.lms.tools.broadbandrequest.forms.PackageRequest;
import za.ac.unisa.lms.tools.broadbandrequest.forms.ServiceProviderCost;
import za.ac.unisa.lms.tools.broadbandrequest.forms.Student;

public class BroadbandRequestDao  extends StudentSystemDAO {
	
public Student getStudent(int studentNr) throws Exception {
		
		Student student = new Student();
		
		String sql = "select mk_title,surname,initials,TO_CHAR(birth_date,'YYYY-MM-DD') as bday," +
		"MK_COUNTRY_CODE" +
		" from stu" +
		" where nr=" + studentNr;
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String title ="";
				String surname="";
				String initials="";
				title= replaceNull(data.get("mk_title"));
				surname= replaceNull(data.get("surname"));
				initials= replaceNull(data.get("initials"));
				student.setNumber(studentNr);
				student.setName(surname + ' ' + initials + ' ' + title);
				student.setPrintName(title + ' ' + initials + ' ' + surname);
				student.setBirthDate(replaceNull(data.get("bday")));
				student.setCountryCode(replaceNull(data.get("MK_COUNTRY_CODE")));
				Contact contact = new Contact();
				contact = getContactDetails(studentNr, 1);
				student.setContactInfo(contact);
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading STU / " + ex);
		}	
		return student;
		
	}
	
	public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
		
		Contact contact = new Contact();
		
		String sql = "select home_number,work_number,fax_number,email_address,cell_number" +
		" from adrph" +
		" where reference_no=" + referenceNo +
		" and fk_adrcatcode=" + category;
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				contact.setHomeNumber(replaceNull(data.get("home_number")).trim());
				contact.setWorkNumber(replaceNull(data.get("work_number")).trim());
				contact.setFaxNumber(replaceNull(data.get("fax_number")).trim());
				contact.setCellNumber(replaceNull(data.get("cell_number")).trim());
				contact.setEmailAddress(replaceNull(data.get("email_address")).trim());
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading ADRPH / " + ex);
		}	
		return contact;	
	}
	
	public ServiceProviderCost getServiceProviderCost(String code) throws Exception {
		ServiceProviderCost sp = new ServiceProviderCost();
		
		String sql = "select a.SERVICE_PROVIDER_GC202,a.FROM_DATE,a.SIM_COST,a.MODEM_COST,b.eng_description" +
				" from bbcst a, gencod b" +
				" where a.SERVICE_PROVIDER_GC202='" + code + "'" +
				" and a.from_date <= sysdate" +
				" and b.code='" + code + "'" +
				" and b.in_use_flag='Y'" +
				" and b.FK_GENCATCODE =202" +
				" order by FROM_DATE desc";
				
			  	try{ 
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList = jdt.queryForList(sql);			
					Iterator i = queryList.iterator();
					while (i.hasNext()) {
						ListOrderedMap data = (ListOrderedMap) i.next();						
						sp.setSpCode(code);
						sp.setSpDescription(replaceNull(data.get("eng_description")).trim());
						sp.setSimFeeDbl(0.00);
						if (!replaceNull(data.get("SIM_COST")).trim().equalsIgnoreCase("")){
							sp.setSimFeeDbl(Double.parseDouble(replaceNull(data.get("SIM_COST")).trim()));
						}
						if (sp.getSimFeeDbl()==0){
							sp.setSimFeeStr("Pay at Service Provider. See T&Cs for pricing.");
						}else{
							sp.setSimFeeStr(String.format("R%.2f",sp.getSimFeeDbl()));
						}
						sp.setModemFeeDbl(0.00);
						if (!replaceNull(data.get("MODEM_COST")).trim().equalsIgnoreCase("")){
							sp.setModemFeeDbl(Double.parseDouble(replaceNull(data.get("MODEM_COST")).trim()));
						}						
						if (sp.getModemFeeDbl()==0.00){
							sp.setModemFeeStr("Purchase from Service Provider. See T&C's for pricing.");
						}else{
							sp.setModemFeeStr(String.format("R%.2f",sp.getModemFeeDbl()));
						}
						
						break;
					}
				}
				catch (Exception ex) {
					throw new Exception("Error reading BBCST,GENCOD / " + ex);
				}	
		
		return sp;
	}
	
	public boolean isStudentRegisteredForFormalCourse(int studentNr) throws Exception {
		
		String sql="select count(*)" +
				" from stusun a, sun b" +
				" where a.FK_STUDENT_NR=" + studentNr +
				" and a.status_code in ('RG','FC')" +
				" and a.MK_STUDY_UNIT_CODE=b.code" +
				" and b.FORMAL_TUITION_FLA='F'";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}		
	}
	
public boolean isBatchWorkBusy() throws Exception {
		
		String sql="select BATCHWORK_BUSY" +
				" from finglo" +
				" where type='F'";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String busy = (String) jdt.queryForObject(sql, String.class);
		if (busy.equalsIgnoreCase("Y")) {
			return true;
		} else {
			return false;
		}		
	}
	
	public int createStudentPackageRequest (int studentNr, PackageRequest request) throws Exception {
		int seqnr =0;
		
		//get current year
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");		
		Calendar calendar = Calendar.getInstance();	
		Date currentDate=calendar.getTime();					
		int year = Integer.parseInt(formatter.format(currentDate));
		
		Connection connection =null;
		
		String sql = "select max(calendar_period)" +
		             " from stu3G" +
		             " where mk_student_nr =" + studentNr +
		             " and mk_academic_year =" + year;
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			seqnr = jdt.queryForInt(sql) ;			
				
			seqnr = seqnr + 1;
			
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);			   
		   
		   sql ="insert into STU3G (MK_STUDENT_NR,MK_ACADEMIC_YEAR,CALENDAR_PERIOD,SIM_FEE,MODEM_FEE," +
		        "BUNDLE_NR,DOCUMENT_SEQ_NR,MSISDN_NR,SP_PAYMENT_BUNDLE,SP_PAYMENT_DOC,PAYMENT_TYPE,MOBILE_NR," + 
		         "STATUS,SERVICE_PROVIDER_GC202,CONTRACT_MONTHS,T_AND_C_ACCEPTED_ON,REGION_GC207)" +
		         " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,systimestamp,?)";
		   
		   PreparedStatement ps = connection.prepareStatement(sql);
		   
		   ps.setInt(1, studentNr); 
		   ps.setInt(2, year); 
		   ps.setInt(3, seqnr); 
		   ps.setDouble(4, request.getServiceProviderCost().getSimFeeDbl());
		   if (request.getModemFlag()!=null && request.getModemFlag().equalsIgnoreCase("Y")){
			   ps.setDouble(5, request.getServiceProviderCost().getModemFeeDbl());
		   }else{
			   ps.setDouble(5, 0.00);
		   }
		   ps.setInt(6, 0); //bundle_nr
		   ps.setInt(7, 0); //document_seq_nr
		   ps.setInt(8, 0); //msisdn_nr
		   ps.setInt(9, 0); //sp_payment_bundle
		   ps.setInt(10, 0); //sp_payment_doc
		   ps.setString(11, null); //payment_type
		   ps.setString(12, request.getCardMobileNr()); //mobile_nr - change 20131114 - add card mobile number - for vodacom only
		   ps.setString(13, "NC"); //status
		   ps.setString(14, request.getServiceProviderCost().getSpCode());
		   ps.setInt(15, 6);
		   if (request.getRegionalCentre()!=null && 
				   request.getRegionalCentre().getCode()!=null && 
				   !request.getRegionalCentre().getCode().equalsIgnoreCase("None")){
			   ps.setString(16,request.getRegionalCentre().getCode()); 
		   }else{ 
			   ps.setString(16,null);
		   }		   
       	   ps.executeUpdate();	
       	   
       	   //insert audit trail record
       	   sql = "insert into stuchg (mk_student_nr,timestamp,MK_USER_CODE,change_code,SUPERVISOR_CODE,ERROR_FLAG,value_changed)" +
       	         "values (?,systimestamp,?,?,?,?,?)";
       	   
       	   ps = connection.prepareStatement(sql);
       	   
       	   ps.setInt(1, studentNr);
       	   ps.setInt(2, 99998);
       	   ps.setString(3, "3G");
       	   ps.setInt(4,0); //supervisor_code
       	   ps.setString(5, ""); //error_flag
	       	StringBuffer strBuf = new StringBuffer((request.getServiceProviderCost().getSpCode() + "          ").substring(0, 6));
	       	if (request.getServiceProviderCost().getSpCode().equalsIgnoreCase("XXX")){
	       		strBuf.insert(3, request.getRegionalCentre().getCode().substring(0,2) + "          ");
	       	}
	       	strBuf.insert(6, ":");
       	   //StringBuffer strBuf = new StringBuffer((request.getServiceProviderCost().getSpCode() + "          ").substring(0, 6));
       	   //strBuf.insert(6, ":");
       	   if (request.getModemFlag()!=null && request.getModemFlag().equalsIgnoreCase("Y")){
       		   strBuf.insert(7, "M");
       	   }else{
       		strBuf.insert(7, " "); 
       	   }
       	strBuf.insert(8, ":");
       	   if (request.getTransferFundsFlag()!=null && request.getTransferFundsFlag().equalsIgnoreCase("Y")){
    		   strBuf.insert(9, "T");
    	   }else{
    		strBuf.insert(9, " "); 
    	   }
       	   String change = new String(strBuf);
       	   ps.setString(6, change.trim()); 
       	   ps.executeUpdate();	
       	   
       	  connection.commit();
		  connection.setAutoCommit(true);
		  connection.close();
			
		}
		catch (java.sql.SQLException ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					if (ex.getErrorCode() == 1){
						createStudentPackageRequest(studentNr,request); 
					}
					throw new Exception("BroadbandRequestDao : Data has been rollback, Error inserting records into STU3G, STUCHG / " + ex,ex);
			}
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("BroadbandRequestDao : Data has been rollback, Error inserting records into STU3G, STUCHG / " + ex,ex);
			}else{
				throw new Exception("BroadbandRequestDao : Error reading STU3G to get latest sequence number (calendar_period) / " + ex,ex);
			}
		}
			
		return seqnr;
	}
	
	public void cancelRequest(int studentNr, PackageRequest request) throws Exception {
		//get current year
						
			Connection connection =null;
			
			String sql ="update STU3G set STATUS=?,CANCELLATION_DATE=systimestamp where MK_STUDENT_NR=?" + 
						" and MK_ACADEMIC_YEAR=? and CALENDAR_PERIOD=?";
				  
			try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				connection = jdt.getDataSource().getConnection();
				connection.setAutoCommit(false);	
				PreparedStatement ps = connection.prepareStatement(sql);
				   
				ps.setString(1, "CA");
				ps.setInt(2, studentNr); 
				ps.setInt(3, request.getYear()); 
				ps.setInt(4, request.getSequenceNr()); 
				
		       	ps.executeUpdate();	
		       	   
		       	//insert audit trail record
		       	sql = "insert into stuchg (mk_student_nr,timestamp,MK_USER_CODE,change_code,SUPERVISOR_CODE,ERROR_FLAG,value_changed)" +
		       	         "values (?,systimestamp,?,?,?,?,?)";
		       	   
		       	ps = connection.prepareStatement(sql);
		       	   
		       	ps.setInt(1, studentNr);
		       	ps.setInt(2, 99998);
		       	ps.setString(3, "C3");
		       	ps.setInt(4,0); //supervisor_code
		       	ps.setString(5, ""); //error_flag
		       	StringBuffer strBuf = new StringBuffer((request.getServiceProviderCost().getSpCode() + "          ").substring(0, 6));
		       	if (request.getServiceProviderCost().getSpCode().equalsIgnoreCase("XXX")){
		       		strBuf.insert(3, request.getRegionalCentre().getCode().substring(0,2) + "          ");
		       	}
		       	strBuf.insert(6, ":");
		       	if (request.getModemFlag()!=null && request.getModemFlag().equalsIgnoreCase("Y")){
		       		strBuf.insert(7, "M");
		       	}else{
		       		strBuf.insert(7, " "); 
		       	}
		       	strBuf.insert(8, ":");
		       	if (request.getTransferFundsFlag()!=null && request.getTransferFundsFlag().equalsIgnoreCase("Y")){
		       		strBuf.insert(9, "T");
		    	}else{
		    		strBuf.insert(9, " "); 
		    	}
		       	String change = new String(strBuf);
		       	ps.setString(6, change.trim()); 
		       	ps.executeUpdate();	
		       	  
		       	connection.commit();
				connection.setAutoCommit(true);
				connection.close();
					
				}
				catch (java.sql.SQLException ex) {
					if (connection!=null){
							connection.rollback();	
							connection.setAutoCommit(true);
							connection.close();
							if (ex.getErrorCode() == 1){
								createStudentPackageRequest(studentNr,request); 
							}
							throw new Exception("BroadbandRequestDao : Data has been rollback, Error updating STU3G or inserting into STUCHG / " + ex,ex);
					}
				}
	}
	
	public List getStudentRequestList(int studentNr, String requirement) throws Exception {
		List<PackageRequest> listRequest = new ArrayList<PackageRequest>();
		
		String sql ="select mk_academic_year,calendar_period,sim_fee,modem_fee,to_char(bundle_date,'YYYYMMDD') as paymentDate," +
				"bundle_nr,document_seq_nr,to_char(date_info_to_sp,'YYYYMMDD') as toSPDate,status,payment_type,to_char(date_card_activated,'YYYYMMDD') as activationDate," +
				"to_char(cancellation_date,'YYYYMMDD') as cancelDate,mobile_nr,service_provider_gc202,to_char(t_and_c_accepted_on,'YYYYMMDD') as requestDate,region_gc207," +
				"to_char(CANCEL_BUNDLE_DATE,'YYYYMMDD') as refundDate,CANCEL_BUNDLE_NR,CANCEL_DOC_SEQ_NR,to_char(CANCELLATION_TO_SP_ON,'YYYYMMDD') as CancelToSP" +
				" from stu3g" +
				" where mk_student_nr=" + studentNr +
				" order by mk_academic_year desc,calendar_period desc";
			
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				PackageRequest record = new PackageRequest();				
				record.setYear(Integer.parseInt((data.get("mk_academic_year").toString())));
				record.setSequenceNr(Integer.parseInt((data.get("calendar_period").toString())));
				record.setActivationDate(replaceNull(data.get("activationDate")).trim());
				record.setInfoToSpDate(replaceNull(data.get("toSPDate")).trim());
				record.setPaymentDate(replaceNull(data.get("paymentDate")).trim());
				record.setRefundDate(replaceNull(data.get("refundDate")).trim());
				record.setCancellationDate(replaceNull(data.get("cancelDate")).trim());
				record.setRequestDate(replaceNull(data.get("requestDate")).trim());
				record.setCardMobileNr(replaceNull(data.get("mobile_nr")).trim());
				record.setPaymentType(replaceNull(data.get("payment_type")).trim());
				Date activationDate = null;
				Date termDate = null;
				if (record.getActivationDate().equalsIgnoreCase("")){
					record.setTerminationDate("");
				}else{
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
					activationDate = formatter.parse(record.getActivationDate());
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(activationDate);
					calendar.add(Calendar.MONTH,6);					
					termDate=calendar.getTime();					
					String strDate = formatter.format(termDate);
					record.setTerminationDate(strDate);
				}
				record.setStatus(replaceNull(data.get("status")).trim());
				if (record.getStatus().equalsIgnoreCase("NC")){
					record.setStatusDisplayed("Not cancelled");
					Calendar calendar = Calendar.getInstance();
					Date currentDate = calendar.getTime();
					if (activationDate==null){
						record.setStatusDisplayed("In progress");
					}else{
						if (activationDate.before(currentDate) && termDate.after(currentDate)){
							record.setStatusDisplayed("Active");
						}
						if (termDate.before(currentDate)){
							record.setStatusDisplayed("Terminated");
						}
					}
				}else{
					record.setStatusDisplayed("Cancelled");	
				}
				
				//Payment reference
				String bundleNr=replaceNull(data.get("bundle_nr")).trim();
				if (bundleNr.equalsIgnoreCase("")){
					record.setBundleNr(0);
				}else{
					record.setBundleNr(Integer.parseInt(bundleNr));
				}
								
				String docSeqNr=replaceNull(data.get("document_seq_nr")).trim();
				if (docSeqNr.equalsIgnoreCase("")){
					record.setDocumentSeqNr(0);
				}else{
					record.setDocumentSeqNr(Integer.parseInt(docSeqNr));
				}
				
				if (!record.getPaymentDate().equalsIgnoreCase("")){
					StringBuffer strBuf = new StringBuffer(record.getPaymentDate());
					strBuf.insert(8, "-");
					strBuf.insert(9, String.format("%04d",record.getBundleNr()));
					strBuf.insert(13, "-");
					strBuf.insert(14, String.format("%03d",record.getDocumentSeqNr()));
					String paymentReference = new String(strBuf);
					record.setPaymentReference(paymentReference);
				}else{
					record.setPaymentReference("");
				}	
				
				//Refund reference
				String refundBundleNr=replaceNull(data.get("CANCEL_BUNDLE_NR")).trim();
				int intRefundBundleNr=0;
				if (refundBundleNr.equalsIgnoreCase("")){
					//do nothing
				}else{
					intRefundBundleNr=(Integer.parseInt(refundBundleNr));
				}
								
				String refundDocSeqNr=replaceNull(data.get("CANCEL_DOC_SEQ_NR")).trim();
				int intRefundDocSeqNr=0;
				if (refundDocSeqNr.equalsIgnoreCase("")){
					//do nothing
				}else{
					intRefundDocSeqNr=(Integer.parseInt(refundDocSeqNr));
				}
				
				if (!record.getRefundDate().equalsIgnoreCase("")){
					StringBuffer strBuf = new StringBuffer(record.getRefundDate());
					strBuf.insert(8, "-");
					strBuf.insert(9, String.format("%04d",intRefundBundleNr));
					strBuf.insert(13, "-");
					strBuf.insert(14, String.format("%03d",intRefundDocSeqNr));
					String refundReference = new String(strBuf);
					record.setRefundReference(refundReference);
				}else{
					record.setRefundReference("");
				}	
				
				Gencod sp = new Gencod();
				StudentSystemGeneralDAO studentDao = new StudentSystemGeneralDAO();
				sp.setCode(replaceNull(data.get("service_provider_gc202")).trim());
				sp.setEngDescription(sp.getCode());
				if (!sp.getCode().equalsIgnoreCase("")){
					Gencod gencod = new Gencod();
					gencod = studentDao.getGenCode("202", sp.getCode());
					if (gencod!=null && gencod.getEngDescription()!=null){
						sp.setEngDescription(gencod.getEngDescription());
					}	
				}else{
					sp.setCode("");
					sp.setEngDescription("");
				}
				ServiceProviderCost costRecord = new ServiceProviderCost();
				String modemFee=replaceNull(data.get("modem_fee")).trim();
				if (modemFee.equalsIgnoreCase("")){
					costRecord.setModemFeeDbl(Double.parseDouble("0.00"));					
				}else{
					costRecord.setModemFeeDbl(Double.parseDouble(modemFee));
				}
				record.setModemFlag("N");
				if (costRecord.getModemFeeDbl() > 0){
					record.setModemFlag("Y");
				}
				String simFee=replaceNull(data.get("sim_fee")).trim();
				if (simFee.equalsIgnoreCase("")){
					costRecord.setSimFeeDbl(Double.parseDouble("0.00"));
				}else{
					costRecord.setSimFeeDbl(Double.parseDouble(simFee));
				}
				if (costRecord.getSimFeeDbl()==0){
					costRecord.setSimFeeStr("Pay at Service Provider. See T&Cs for pricing.");
				}else{
					costRecord.setSimFeeStr(String.format("R%.2f",costRecord.getSimFeeDbl()));
				}								
				costRecord.setModemFeeStr(String.format("R%.2f",costRecord.getModemFeeDbl()));
				costRecord.setSpCode(sp.getCode());
				costRecord.setSpDescription(sp.getEngDescription());
				record.setServiceProviderCost(costRecord);	
				Gencod regionalCentre = new Gencod();
				regionalCentre.setCode(replaceNull(data.get("region_gc207")).trim());
				regionalCentre.setEngDescription(regionalCentre.getCode());
				if (!regionalCentre.getCode().equalsIgnoreCase("")){
					Gencod gencod = new Gencod();
					gencod = studentDao.getGenCode("207", regionalCentre.getCode());
					if (gencod!=null && gencod.getEngDescription()!=null){
						regionalCentre.setEngDescription(gencod.getEngDescription());
					}	
				}else{
					regionalCentre.setCode("");
					regionalCentre.setEngDescription("");
				}
				record.setRegionalCentre(regionalCentre);
			
				if (record.getStatus().equalsIgnoreCase("CA") || 
						record.getBundleNr()!=0 || 
						(record.getServiceProviderCost().getSimFeeDbl()==0 && !record.getInfoToSpDate().equalsIgnoreCase(""))){
					record.setLink("view");
				}else{
					record.setLink("cancel");
				}				
				
				listRequest.add(record);
				if (requirement.equalsIgnoreCase("latest")){
					break;
				}
			}			
		}
		catch (Exception ex) {
			throw new Exception("Error reading STU3G / " + ex);
		}	
		return listRequest;	
	}
	

	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}

}
