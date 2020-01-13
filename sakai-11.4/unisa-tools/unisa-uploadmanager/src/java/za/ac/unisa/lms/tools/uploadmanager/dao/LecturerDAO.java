package za.ac.unisa.lms.tools.uploadmanager.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import za.ac.unisa.lms.db.StudentSystemDAO;
public class LecturerDAO extends StudentSystemDAO{
	
	
	public boolean isLecturer(String networkCode) throws Exception {
	                   GregorianCalendar calCurrent = new GregorianCalendar();
                       int currYear=calCurrent.get(Calendar.YEAR);
                       boolean isLec=true;
                       String sql=    "Select * from usrsun a, staff b  where a.system_type='L'  AND  "+
                                      "a.mk_academic_year= "+currYear +" and b.RESIGN_DATE is null "+
                                       " and UPPER(b.novell_user_id)=UPPER(a.novell_user_id) and UPPER(b.novell_user_id)='"+networkCode.toUpperCase()+"'";
                       
	                         JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		                     List queryList = jdt.queryForList(sql);
		                     if((queryList==null)||(queryList.isEmpty())){
		                	      isLec= false;
		                     }
		                     return isLec;	
                       
     }
	
	
	public String getStaffSurname(String networkCode) {
		
		try {
		String sql = "SELECT SURNAME FROM STAFF WHERE UPPER(NOVELL_USER_ID) = UPPER(?)";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		 Object surname = jdt.queryForObject(sql,new Object[]{networkCode}, new RowMapper() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getString("SURNAME"); 

            }});
		
		return surname.toString();
		} catch (EmptyResultDataAccessException e) {
			
			return "";
		}

	}
	
	

	
}
