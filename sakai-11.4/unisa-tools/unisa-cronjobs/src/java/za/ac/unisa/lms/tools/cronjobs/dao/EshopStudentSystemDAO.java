package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class EshopStudentSystemDAO extends StudentSystemDAO{



	public String studyUnitCode(String code) throws Exception {

		String courseCode = "";
		String query = "SELECT CODE FROM SUN WHERE CODE LIKE '" + code.toUpperCase() + "%'";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> list = jdt.queryForList(query);
			if(!list.isEmpty()) {
				Iterator<?> i = list.iterator();
				if(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					courseCode = (data.get("CODE").toString());
				}
			} else {
				courseCode = code;
			}

		}catch (Exception ex) {
			throw new Exception("cronjobs: EshopStudentSystemDAO error occurred while querying the study unit code: /" + ex, ex);
		}
		return courseCode;
	}

}

