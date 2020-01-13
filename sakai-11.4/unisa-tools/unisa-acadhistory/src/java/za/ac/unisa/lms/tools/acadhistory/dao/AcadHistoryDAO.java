package za.ac.unisa.lms.tools.acadhistory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.acadhistory.forms.AcadQualRecord;
import za.ac.unisa.lms.tools.acadhistory.forms.Student;

public class AcadHistoryDAO extends StudentSystemDAO {
	
	public AcadQualRecord getAcademicRecord(int studentNr, String qualCode) throws Exception {
		
		AcadQualRecord record = new AcadQualRecord();
		
		PreparedStatement pst = null;
		Connection con = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    con = jdt.getDataSource().getConnection();		
		    String sql =
		    		"select status_code,actual_completion,to_char(last_exam_date,'YYYYMMDD') as lastExamDate," +
		            "to_char(first_registration,'YYYYMMDD') as firstRegDate,to_char(graduation_ceremon,'YYYYMMDD') as gradCeremonyDate," +
		    		"last_academic_regi,audit_flag,grd.short_description " +
		    		" from stuaca,grd " +
		    		" where mk_Student_nr=? and mk_qualification_c=?" +
		    		" and STUACA.MK_QUALIFICATION_C=grd.code";
		        
		    pst = con.prepareStatement(sql);	
		    pst.setInt(1, studentNr);
		    pst.setString(2, qualCode);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	record.setQualCode(qualCode);
		    	record.setQualShortDescription(replaceNull(rs.getString("short_description")).trim());
		    	record.setStatus(rs.getString("status_code"));
		    	record.setLastRegistrationYear(rs.getShort("last_academic_regi"));
		    	record.setAuditFlag(replaceNull(rs.getString("audit_flag")).trim());
		    	record.setGraduationCeremonyDate((rs.getString("gradCeremonyDate")).trim());	
		    	record.setFirstRegistrationDate((rs.getString("firstRegDate")).trim());
		    }		     
		    return record;
	    }
		    catch (Exception ex) {
				throw new Exception("AcadHistoryDAO  : Error reading table stuaca/ " + ex, ex);				
		    } finally {
				try {
					if (con!=null){con.close();}
					if (pst!=null){pst.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
	}	

	
public String getLastAREmailRequest(String studentNr, String emailARType, String qualCode)throws Exception{
	
	PreparedStatement pst = null;
	Connection con = null;
    ResultSet rs = null;
	String lastRequesDate ="";		
	
	String sql = "select to_char(max(date_sent),'YYYY-MM-DD HH24:mi:ss') as dateSent from emllog" +
					" where recipient=?" +
					" and UPPER(program)='SRSRJ11H'" + 
					" and UPPER(email_type_gc138)=?" +
					" and UPPER(req_system_gc285)='MYUNISA'" +
					" and UPPER(req_program)='ACADEMIC RECORD'" +
					" and UPPER(body0)=?";
			
	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		con = jdt.getDataSource().getConnection();
		pst = con.prepareStatement(sql);		    
			
		pst.setString(1, studentNr.trim());
		pst.setString(2, emailARType);
		pst.setString(3, qualCode.toUpperCase());
		
		rs = pst.executeQuery();
		while (rs.next()) {
			lastRequesDate =replaceNull(rs.getString("dateSent")).trim();
		}
		
		return lastRequesDate;
	}
	 catch (Exception ex) {
			throw new Exception("AcadHistoryDAO  : Error reading table emllog/ " + ex, ex);				
	    } finally {
			try {
				if (con!=null){con.close();}
				if (pst!=null){pst.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
}

	
public String getEmailAddress(int studentNr) throws Exception{
		
		PreparedStatement pst = null;
		Connection con = null;
	    ResultSet rs = null;
		String emailAddress="";		
		
		String sql = "select email_address from adrph" +
		 			" where adrph.REFERENCE_NO = ?" +
		 			" and adrph.FK_ADRCATCODE = 1";		
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			con = jdt.getDataSource().getConnection();
			pst = con.prepareStatement(sql);		    
				
			pst.setInt(1, studentNr);
			
			rs = pst.executeQuery();
			while (rs.next()) {
				emailAddress =replaceNull(rs.getString("email_address")).trim();
			}
			
			return emailAddress;
		}
		 catch (Exception ex) {
				throw new Exception("AcadHistoryDAO  : Error reading table adrph/ " + ex, ex);				
		    } finally {
				try {
					if (con!=null){con.close();}
					if (pst!=null){pst.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
	}

	public Student getStudent(int studentNr) throws Exception {
	
	PreparedStatement pst = null;
	Connection con = null;
    ResultSet rs = null;
    Student student = new Student();
    
    try{
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    con = jdt.getDataSource().getConnection();		
	    String sql =
	        "select nr,surname, initials,mk_title,library_black_list,exam_fees_debt_fla," +
	        "disciplinary_incid,nvl(nsfas_contract_block,0) as nsfasBlock,financial_block_fl,number_restricted" +
	    	" from stu where nr = ?";
	    pst = con.prepareStatement(sql);	
	    pst.setInt(1, studentNr);
	    rs = pst.executeQuery();
	    if (rs.next()){
	    	student.setNumber(rs.getString("nr"));
	    	student.setName(rs.getString("surname"));  
	    	student.setInitials(rs.getString("initials"));
	    	student.setTitle(rs.getString("mk_title"));
	    	student.setDisciplinaryIncid(rs.getInt("disciplinary_incid"));
	    	student.setLibraryBlackList(rs.getInt("library_black_list"));
	    	student.setExamFeesDebtFlag(rs.getString("exam_fees_debt_fla"));
	    	student.setFinancialBlockFlag(rs.getString("financial_block_fl"));
	    	student.setNumberRestricted(rs.getString("number_restricted"));
	    	student.setNsfasContractBlock(rs.getInt("nsfasBlock"));
	    }		     
	    return student;
    }
	    catch (Exception ex) {
			throw new Exception("AcadHistoryDAO   : : Error reading table stu/ " + ex, ex);				
	    } finally {
			try {
				if (con!=null){con.close();}
				if (pst!=null){pst.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}	

	
//	public String xxxgetMessage(String msgCode, String program) throws Exception {
//		
//		PreparedStatement pst = null;
//		Connection con = null;
//	    ResultSet rs = null;
//	    String message="";
//	    
//	    try{
//		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
//		    con = jdt.getDataSource().getConnection();		
//
//		    String sql =
//		    "select (message || ' (Msg: ' || msg_Code || ')') as message from genmsg where UPPER(program)=UPPER(?)" +
//	    	" and UPPER(msg_code)=UPPER(?) " +
//	    	" and system_gc285='MYUNISA'";
//		    pst = con.prepareStatement(sql);	
//		    pst.setString(1, program);
//		    pst.setString(2, msgCode);
//		    
//		    rs = pst.executeQuery();
//		    if (rs.next()){
//		    	message =replaceNull(rs.getString("message")).trim();
//		    }		     
//		    return message;
//	    }
//		    catch (Exception ex) {
//				throw new Exception("AcadHistoryDAO   : : Error reading table genmsg/ " + ex, ex);				
//		    } finally {
//				try {
//					if (con!=null){con.close();}
//					if (pst!=null){pst.close();}
//					if (rs!=null){rs.close();}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}		
//	}	
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue.trim();
	}

}
