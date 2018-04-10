package za.ac.unisa.lms.tools.smsbatch.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.smsbatch.actions.SmsFileAction;

public class SmsFileDAO extends StudentSystemDAO{
	
public int createBatch(String[] studentArray,String[] cellArray,String message,int persNo,
		String rcCode, Short department, String controlCell, int smsCount, double totalCost,
		int reason, String criteria, List controlCellList) throws Exception {
	
	int batchNr = 0;
	final int count = studentArray.length; 
	final String[] studentArr = studentArray;
	final String[] cellArr = cellArray;
	List controlCellPhoneList = new ArrayList<String>();
	
	for (int i=0; i < controlCellList.size(); i++){
		if (controlCellList.get(i)!=null){
			if (!controlCellList.get(i).toString().trim().equalsIgnoreCase("")){
				controlCellPhoneList.add(controlCellList.get(i).toString().trim());
			}
		}
	}
	for (int i=controlCellPhoneList.size(); i<3; i++){
		controlCellPhoneList.add("");
	}
	
	String sql = "select max(batch_nr) + 1 as new_batch from smsreq";
	
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
		throw new Exception("SmsFileDao : Error reading smsreq to get next batch number/ " + ex);
	}		
	
	Connection connection = null;
	
	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		connection = jdt.getDataSource().getConnection();
		connection.setAutoCommit(false);		
		
	   Statement stmt = connection.createStatement();
	   
	   message = message.replaceAll("'", "''");
	   stmt.executeUpdate("insert into smsreq (BATCH_NR,MK_PERS_NR,MK_RC_CODE,MK_DEPARTMENT_CODE," +
	    		"REQUEST_TIMESTAMP,SMS_MSG,CONTROL_CELL_NR,MESSAGE_CNT,TOTAL_COST,FROM_SYSTEM_GC26," +
	    		"REASON_GC27,SELECTION_CRITERIA,BUDGET_UPDATED_FLA,STATUS,PRIORITY,PROGRAM_NAME," +
	    		"CONTROL_CELL_NR2,CONTROL_CELL_NR3)" +
	    		" values (" +
	    		batchNr + "," +
	    		persNo + "," +
	    		"'" + rcCode + "'," +
	    		department + "," +
	    		"SYSDATE," +
	    		"'" + message + "'," +
	    		"'" + controlCellPhoneList.get(0).toString() + "'," +
	    		smsCount + "," +
	    		totalCost + "," +
	    		"4," +
	    		reason + "," +
	    		"'" + criteria + "'," +
	    		"'N'," +
	    		"null," +
	    		"5," +
	    		"'STD_BATCH:FILE'," + 
	    		"'" + controlCellPhoneList.get(1).toString() + "'," +
	    		"'" + controlCellPhoneList.get(2).toString() + "'" +
	    		")" );
	   
	   sql = "insert into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) " +
		"values (" + batchNr +
		",?,?,?,'" + message + "'," + persNo + ")";
	   
	   PreparedStatement ps = connection.prepareStatement(sql);
	   
	   for (int i=0; i < cellArr.length;i++ ){
		 ps.setInt(1, i + 1);  
		 ps.setInt(2, Integer.parseInt(studentArr[i].toString()));
         ps.setString(3, cellArr[i].toString()); 
         ps.addBatch();
	   }
	   
	  ps.executeBatch();	  
	 
	  connection.commit();
	  connection.setAutoCommit(true);
	  connection.close();
		
	}
	catch (Exception ex) {
		if (connection!=null){
				connection.rollback();	
				connection.setAutoCommit(true);
				connection.close();
				throw new Exception("SmsFileDao : Data has been rollback, error inserting SMSREQ and SMSQUE records / " + ex,ex);
		}
	}
		
	return batchNr;
}

public void doMultiAdd(int batchNr,String[] studentArray,String[] cellArray,String message,int persNo) throws Exception {
		
		final int count = studentArray.length; 
		final String[] studentArr = studentArray;
		final String[] cellArr = cellArray;
		/*batch nr 62696*/
		
		String sql = "insert into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) " +
		"values (" + batchNr +
		",?,?,?,'" + message + "'," + persNo + ")"; 		

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());			
			jdt.batchUpdate(sql, new BatchPreparedStatementSetter() {   
					
			public void setValues(PreparedStatement ps, int i) throws SQLException {  
				ps.setInt(1, i + 1);  
				ps.setInt(2, Integer.parseInt(studentArr[i].toString()));
                ps.setString(3, cellArr[i].toString());              
			    }                        
				
				public int getBatchSize() {                                
					return count;                        
					}                
			});	
		}
		catch (Exception ex) {
			throw new Exception("SMSBatchDAO : Error inserting SMSQUE / " + ex,ex);
		}	
}

public int insertSmsRequest(int persNo, String rcCode, Short department,
		String message, String controlCell, int smsCount, double totalCost,
		int reason, String criteria) throws Exception {
	
		int batchNr=0;
		
		String sql = "select max(batch_nr) + 1 as new_batch from smsreq";
		
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
			throw new Exception("SmsFileDao : Error reading smsreq to get next batch number/ " + ex);
		}		
	
		sql = "insert into smsreq (BATCH_NR,MK_PERS_NR,MK_RC_CODE,MK_DEPARTMENT_CODE," +
				"REQUEST_TIMESTAMP,SMS_MSG,CONTROL_CELL_NR,MESSAGE_CNT,TOTAL_COST,FROM_SYSTEM_GC26," +
				"REASON_GC27,SELECTION_CRITERIA,BUDGET_UPDATED_FLA,STATUS,PRIORITY,PROGRAM_NAME)" +
			" values (" +
			batchNr + "," +
			persNo + "," +
			"'" + rcCode + "'," +
			department + "," +
			"SYSDATE," +
			"'" + message + "'," +
			"'" + controlCell + "'," +
			smsCount + "," +
			totalCost + "," +
			"4," +
			reason + "," +
			"'" + criteria + "'," +
			"'N'," +
			"null," +
			"5," +
			"'STD_BATCH'" + 
			")";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("SmsFileDao : Error inserting SMSREQ / " + ex,ex);
		}
		
		return batchNr;
	}
	
public double getSmsCost() throws Exception{
		
		double smsCost=0;
		
		String sql = "select cost_per_sms from smscst" +
			" where from_date <= SYSDATE order by from_date desc";				
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				smsCost = Double.valueOf(data.get("cost_per_sms").toString()).doubleValue();
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsFileDao : Error reading table smscst/ " + ex);
		}		
		return smsCost;	
	}

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

public boolean isDCMUser(String userId) throws Exception {
	
	PreparedStatement pst = null;
    ResultSet rs = null;
    
    try{
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    Connection con = jdt.getDataSource().getConnection();
	    int cnt = 0;
	
	    String sql =
	        "select count(*) from genuser where code = ? and fk_genucatcode='SMSDCMUSR'";
	    pst = con.prepareStatement(sql);	
	    pst.setString(1, userId);
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
			throw new Exception("SmsFileDao : Error reading table genuser/ " + ex);
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
private String replaceNull(Object object){
	String stringValue="";
	if (object==null){
	}else{
		stringValue=object.toString();
	}
		
	return stringValue;
}

}
