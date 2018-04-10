package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CollegeDeptDetailRowMapper implements RowMapper{
	


		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
			collegeDeptDetails.setCollege(rs.getString("COLLEGE"));
			collegeDeptDetails.setDepartment(rs.getString("DEPT"));
		//	collegeDeptDetails.setCollegeChair(rs.getString("NAME"));
		//	collegeDeptDetails.setPersno(rs.getString("PERSNO"));
			collegeDeptDetails.setCodInitials(rs.getString("intials"));
			collegeDeptDetails.setCodNevellUserCode(rs.getString("novelluserId"));
			collegeDeptDetails.setCodSurname(rs.getString("surname"));
			collegeDeptDetails.setDeptCode(rs.getString("code"));
			
							
			return collegeDeptDetails;
		}			

}
