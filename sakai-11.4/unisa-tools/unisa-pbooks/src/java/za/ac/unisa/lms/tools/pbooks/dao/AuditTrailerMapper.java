package za.ac.unisa.lms.tools.pbooks.dao;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	import org.springframework.jdbc.core.RowMapper;

	public class AuditTrailerMapper implements RowMapper {

		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			AuditTrail auditTrail = new AuditTrail();
			
			auditTrail.setNetworkId(rs.getString("MK_NETWORK_ID"));
			auditTrail.setStudyUnitCode(rs.getString("MK_STUDY_UNIT_CODE"));
			auditTrail.setAcadYear(rs.getString("MK_ACADEMIC_YEAR"));
			auditTrail.setBookStatus(rs.getString("MK_BOOK_STATUS"));
			auditTrail.setTransactionTime(rs.getString("TRANSACTION_TIME"));
			auditTrail.setUpdateInfo(rs.getString("UPDATE_INFO"));
			
			return auditTrail;
		}			
}
