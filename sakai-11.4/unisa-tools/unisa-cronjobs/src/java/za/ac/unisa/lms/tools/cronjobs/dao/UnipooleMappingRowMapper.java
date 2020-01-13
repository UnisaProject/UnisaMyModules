package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UnipooleMappingRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		UnipooleMapping unipooleMapping = new UnipooleMapping();
	
		try {
		
			unipooleMapping.setToModuleId(rs.getString("to_Module_id"));
			unipooleMapping.setFromModuleId(rs.getString("from_Module_id"));
			unipooleMapping.setTitle(rs.getString("to_title"));
		
		} catch (Exception e) {
		}
		
		return unipooleMapping;
	} //end of mapRow

} // end of UnipooleMappingRowMapper