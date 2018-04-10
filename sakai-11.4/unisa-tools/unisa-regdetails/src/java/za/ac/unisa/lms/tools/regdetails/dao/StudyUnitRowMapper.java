package za.ac.unisa.lms.tools.regdetails.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.tools.regdetails.forms.StudyUnit;


public class StudyUnitRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		StudyUnit su = new StudyUnit();
		
		
		su.setCode(rs.getString("CODE"));
		su.setDesc(rs.getString("LOCK_TIME"));
		su.setRegPeriod(rs.getString("MODIFIED_ON"));
	//	su.setExamDate1(rs.getDate("SERVER_ID"));
		
		return su;
	}

}
