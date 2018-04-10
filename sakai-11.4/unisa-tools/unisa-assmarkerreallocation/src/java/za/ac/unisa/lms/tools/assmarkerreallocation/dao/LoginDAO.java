package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
public class LoginDAO extends StudentSystemDAO{

	public boolean isNetworkCodeValid(String networkcode)throws Exception{
			String sql = "select distinct novell_user_id" +
		    " from staff" +
		    " where  (RESIGN_DATE is null or resign_date >sysdate)  and novell_user_id='"+networkcode+"'";
			boolean returnValue=true;
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);
                if((queryList==null)||(queryList.isEmpty())){
                	returnValue= false;
			    }
			}catch (Exception ex) {
				throw new Exception("AssMarkerReallocationDAO : Error reading staff / " + ex);
			}		
			return returnValue;	
	}	
}
