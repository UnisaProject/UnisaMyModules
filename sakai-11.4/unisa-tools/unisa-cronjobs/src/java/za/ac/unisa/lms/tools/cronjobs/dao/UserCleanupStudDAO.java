package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class UserCleanupStudDAO extends StudentSystemDAO {
	
	public ArrayList<LabelValueBean> selectResignedPers(String start,String end) throws Exception {

		ArrayList<LabelValueBean> persList = new ArrayList<LabelValueBean>();

		String select = "select NOVELL_USER_ID "+
						"from   staff "+
						"where  to_char(resign_date,'YYYY-MM-DD') between(+)="+start+ 
						" and(+)="+end+
						"and    novell_user_id is not null "+
						"order  by novell_user_id ";

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
			int counter = 0;
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			persList.add(new org.apache.struts.util.LabelValueBean(data.get("NOVELL_USER_ID").toString(), data.get("NOVELL_USER_ID").toString()));
    			counter++;
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.tools.cronjobs.dao.UserCleanupStudDAO: selectResignedPers: Error occurred / "+ ex,ex);
    	} // end try

		return persList;
	}

}
