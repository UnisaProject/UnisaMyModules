package org.sakaiproject.studymaterial.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.studymaterial.module.DataRow;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class StudentSystemQueryDAO extends StudentSystemDAO {

	public Vector doQuery(String query) throws Exception {

		/* See http://www.springframework.org/docs/reference/jdbc.html for
		 * information on the use of JdbcTemplate to access data.
		 */

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List list = jdt.queryForList(query);

		Vector columns = null;
		Vector values = null;
		Vector results = new Vector();
		DataRow row = null;

		Iterator i = list.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (columns == null) {
				columns = new Vector();
				row = new DataRow();
				Iterator ci = data.keySet().iterator();
				while (ci.hasNext()) {
					columns.add(ci.next());
				}
				row.setColumns(columns);
				results.add(row);
			}

			Iterator vi = data.values().iterator();
			values = new Vector();
			while (vi.hasNext()) {
				values.add(vi.next());
			}

			row = new DataRow();
			row.setValues(values);
			results.add(row);

		}

		return results;
	}
	
	/*
	 * 1 stands for displaying second semester study material in electronic format
	 * 0 stands for not displaying second semester study material in electronic format
	 */
	
	public int getSecondSemesterStatusForDisplay (String ACADEMIC_YEAR) {
		
		String sql = "SELECT COUNT(*) FROM REGDAT WHERE TYPE = 'NOSTMISS' and SYSDATE >= TO_DATE and ACADEMIC_YEAR = ? AND SEMESTER_PERIOD = 2";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		int status = (Integer) jdt.queryForObject(
				sql,
		        new Object[]{ACADEMIC_YEAR}, Integer.class);
		
		return status;
	}
}
