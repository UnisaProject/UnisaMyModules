package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CollegeDeptStandinDetailRowMapper implements RowMapper{
	


		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
			collegeDeptDetails.setCollege(rs.getString("COLLEGE"));
			collegeDeptDetails.setDepartment(rs.getString("DEPT"));
			collegeDeptDetails.setDeptCode(rs.getString("code"));
			
							
			return collegeDeptDetails;
		}			

}
