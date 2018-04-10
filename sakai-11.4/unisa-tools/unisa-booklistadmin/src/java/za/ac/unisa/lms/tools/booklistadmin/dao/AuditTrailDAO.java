package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;

public class AuditTrailDAO extends StudentSystemDAO {
	public void  updateAuditTrail(AuditTrailModule trail) {
		String insertSql = "INSERT INTO PBSAUD (MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS, ";
		       insertSql += "TRANSACTION_TIME,UPDATE_INFO) VALUES('"+trail.getNetworkId()+"',";
		       insertSql += "'"+trail.getStudyUnitCode()+"',";
		       insertSql += "'"+trail.getAcadYear()+"',";
		       insertSql += "'"+trail.getBookStatus()+"',";
		       DatabaseUtil databaseUtil=new  DatabaseUtil();
		       insertSql += "systimestamp,'"+databaseUtil.makeJDBCCompatible(trail.getUpdateInfo())+"')";
		    
		   	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			jdt.execute(insertSql);	
	}
	public AuditTrailModule getLastUpdated (String subject,String acadyear, String typeofbooklist) throws Exception {
		AuditTrailModule auditTrail  = null;
		String sql = "SELECT MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS," +
				"to_char(transaction_time, 'YYYY-MM-DD HH24:MI:SS') transaction_time,UPDATE_INFO FROM PBSAUD WHERE " +
				"  MK_NETWORK_ID <> 'import' and MK_STUDY_UNIT_CODE = ? AND MK_ACADEMIC_YEAR= ? and MK_BOOK_STATUS= ? " +
				" ORDER BY  transaction_time DESC";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		List auditTrailList = jdt.query(sql,new Object[] { subject,acadyear,typeofbooklist}, new int[] { Types.VARCHAR,Types.VARCHAR,Types.VARCHAR},
			new AuditTrailerMapper());
		if (auditTrailList.size() > 0) {
			 auditTrail = (AuditTrailModule) auditTrailList.get(0);
		}		
		return auditTrail;
	}
}
