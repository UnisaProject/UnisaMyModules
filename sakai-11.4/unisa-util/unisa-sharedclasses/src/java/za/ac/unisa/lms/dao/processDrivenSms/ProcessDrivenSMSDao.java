package za.ac.unisa.lms.dao.processDrivenSms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRecipientResult;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRequest;
import za.ac.unisa.lms.domain.processDrivenSms.SmsRequestResult;


public class ProcessDrivenSMSDao extends StudentSystemDAO{
	
	public boolean isStudentNr(int studentNr) throws Exception {
		
		PreparedStatement pst = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    Connection con = jdt.getDataSource().getConnection();
		    int cnt = 0;
		
		    String sql =
		        "select count(*) from stu where nr = ?";
		    pst = con.prepareStatement(sql);	
		    pst.setInt(1, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	cnt = rs.getInt(1);	    
		    }
		    con.close();
		    rs.close();	    
		    if (cnt > 0){
		    	return true;
		    }else{
		    	return false;
		    }
	    }
		    catch (Exception ex) {
				throw new Exception("SmsFileDao : Error reading table stu/ " + ex);
			}		
	}	

	public String getCellNumber(int studentNr) throws Exception{
		
		String cellNumber="";
		PreparedStatement pst = null;
	    ResultSet rs = null;
		
		String sql = "select CELL_NUMBER from adrph" +
		 			" where adrph.REFERENCE_NO = ?" +
		 			" and adrph.FK_ADRCATCODE = 1";		
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			Connection con = jdt.getDataSource().getConnection();
			pst = con.prepareStatement(sql);		    
				
			pst.setInt(1, studentNr);
			
			rs = pst.executeQuery();
			while (rs.next()) {
				cellNumber = rs.getString(1);
			}
			if (cellNumber == null){
				cellNumber ="";
			}
			con.close();
			rs.close();
		}
		catch (Exception ex) {
			throw new Exception("SmsFileDao : Error reading table adrph/ " + ex);
		}		
		return cellNumber;	
	}
	
	public SmsRequestResult createSmsRequestRecords(SmsRequest smsRequest, SmsRequestResult requestResult) throws Exception {
		
		int batchNr = 0;
		int sequenceNr = 0;
		int smsCount = 0;
		boolean createNewSmsReq =false;
		String message = "";	
				
		String sql = "select batch_nr from smsreq" +
		             " where smsreq.mk_rc_code = '" + smsRequest.getRcCode() + "'" +
		             " and smsreq.budget_updated_fla='N'" +
		             " and smsreq.reason_gc27=" + smsRequest.getReasonCodeGC27();
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				batchNr = Integer.parseInt((data.get("batch_nr").toString()));
				break;
			}	
		}
		catch (Exception ex) {
			throw new Exception("ProcessDrivenSMSDao : Error reading smsreq batch number/ " + ex);
		}
			
		if (batchNr == 0){
			createNewSmsReq = true;
			sql = "select max(batch_nr) + 1 as new_batch from smsreq";
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					batchNr = Integer.parseInt((data.get("new_batch").toString()));
					break;
				}
			}
			catch (Exception ex) {
				throw new Exception("ProcessDrivenSMSDao : Error reading smsreq to get next batch number/ " + ex);
			}		
		}else{
			//get latest sequence number from smsque
			sql = "select max(sequence_nr) as last_sequence from smsque where mk_batch_nr=" + batchNr;
			
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					if (data.get("last_sequence")==null){
						sequenceNr=0;
					}else{
						sequenceNr = Integer.parseInt((data.get("last_sequence").toString()));
					}					
					break;
				}
			}
			catch (Exception ex) {
				throw new Exception("ProcessDrivenSMSDao : Error reading smsque to get next sequence number/ " + ex);
			}		
			
		}
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		connection.setAutoCommit(false);	
		
		try{ 
			if (createNewSmsReq){
								
				   Statement stmt = connection.createStatement();
				   
				   message = message.replaceAll("'", "''");				  
					   
				   stmt.executeUpdate("insert into smsreq (BATCH_NR,MK_PERS_NR,MK_RC_CODE,MK_DEPARTMENT_CODE," +
				    		"REQUEST_TIMESTAMP,SMS_MSG,CONTROL_CELL_NR,MESSAGE_CNT,TOTAL_COST,FROM_SYSTEM_GC26," +
				    		"REASON_GC27,SELECTION_CRITERIA,BUDGET_UPDATED_FLA,STATUS,PRIORITY,PROGRAM_NAME," +
				    		"CONTROL_CELL_NR2,CONTROL_CELL_NR3)" +
				    		" values (" +
				    		batchNr + "," +
				    		smsRequest.getPersNo() + "," +
				    		"'" + smsRequest.getRcCode() + "'," +
				    		requestResult.getUserDeptCode() + "," +
				    		"SYSDATE," +
				    		"'" + smsRequest.getMessage() + "'," +
				    		"null," +
				    		"0," +
				    		"0," +
				    		"7," +
				    		smsRequest.getReasonCodeGC27() + "," +
				    		"'" + smsRequest.getCriteria() + "'," +
				    		"'N'," +
				    		"null," +
				    		"5," +
				    		"'" + smsRequest.getProgramName() + "'," + 
				    		"null," +
				    		"null" +
				    		")" );
			}
		
		   
			sql = "insert into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) " +
			"values (" + batchNr +
			",?,?,?,?," + smsRequest.getPersNo() + ")";
		   
			PreparedStatement ps = connection.prepareStatement(sql);
		   		   
			for (int i=0; i < requestResult.getListRecipient().size(); i++ ){
				SmsRecipientResult sms = new SmsRecipientResult();
			    sms = (SmsRecipientResult) requestResult.getListRecipient().get(i);
				if (sms.getErrMsg()==null || sms.getErrMsg().equalsIgnoreCase("")){
					 String smsMessage=sms.getMessage();
					 if (sms.getMessage().equalsIgnoreCase("")){
						 smsMessage = smsRequest.getMessage();
					 }
					 sequenceNr = sequenceNr + 1;
					 smsCount = smsCount + 1;
					 ps.setInt(1, sequenceNr);
					 ps.setInt(2, sms.getStudentNr());
					 ps.setString(3, sms.getCellNr());
			         ps.setString(4, smsMessage); 
			         ps.addBatch();
				}
			}
		   
			ps.executeBatch();
			  
			sql = "update smsreq set message_cnt = message_cnt + ?" +
			        " where batch_nr=?";
				
			ps = connection.prepareStatement(sql);
					   
			ps.setInt(1, smsCount); 
			ps.setInt(2, batchNr); 
			ps.executeUpdate(); 
					
			connection.commit();
			requestResult.setBatchNr(batchNr);
			requestResult.setSmsSendCount(smsCount);
			connection.setAutoCommit(true);
			connection.close();
			
		}
		catch (java.sql.SQLException e) {
			if (connection!=null){				
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					if (e.getErrorCode() == 1){
						createSmsRequestRecords(smsRequest, requestResult);
					}else{
						throw new Exception("ProcessDrivenSMSDao : Data has been rollback, error inserting SMSREQ and SMSQUE records / " + e,e);
					}
					
			}
		}			
		return requestResult;
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
