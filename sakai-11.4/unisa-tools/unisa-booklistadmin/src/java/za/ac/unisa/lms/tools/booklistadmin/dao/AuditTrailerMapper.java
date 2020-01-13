package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;

public class AuditTrailerMapper implements RowMapper {

	public Object mapRow(ResultSet rs,int arg1) throws SQLException {
		AuditTrailModule auditTrail = new AuditTrailModule();
		
		auditTrail.setNetworkId(rs.getString("MK_NETWORK_ID"));
		auditTrail.setStudyUnitCode(rs.getString("MK_STUDY_UNIT_CODE"));
		auditTrail.setAcadYear(rs.getString("MK_ACADEMIC_YEAR"));
		auditTrail.setBookStatus(rs.getString("MK_BOOK_STATUS"));
		auditTrail.setTransactionTime(rs.getString("TRANSACTION_TIME"));
		auditTrail.setUpdateInfo(rs.getString("UPDATE_INFO"));
		
		return auditTrail;
	}			
}
