package za.ac.unisa.lms.tools.monitor.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;


public class SakaiQueryDAO extends SakaiDAO {

	public Vector<DataRow> doQuery(String query) throws Exception {

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
}
