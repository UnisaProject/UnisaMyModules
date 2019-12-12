package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class DeleteCourseUsrSunDAO extends StudentSystemDAO {

	private Log log;

	public DeleteCourseUsrSunDAO() {
		log = LogFactory.getLog(this.getClass());
	}

	public void DeleteCourses(int academicYear) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String count ="";

		String query = "Select count(*) COUNT from usrsun where mk_academic_year=? and system_type='L'";

		log.debug("Executing query: "+query);
		List<?> records = jdt.queryForList(
			query,
			new Object[] {
				new Integer(academicYear)
			},
			new int[] {
				Types.NUMERIC
			}
		);
		Iterator<?> i = records.iterator();
		while (i.hasNext()){
			ListOrderedMap data = (ListOrderedMap) i.next();
			count = data.get("COUNT").toString();
		}
		log.debug("About to delete "+count+" subjects");
		
		query = "delete from usrsun where mk_academic_year=? and system_type='L'";
		log.debug("Executing query:" +query+ "academic Year= "+academicYear);
		jdt.update(
				query,
				new Object[] {
						new Integer(academicYear)
				},
				new int[] {
					Types.NUMERIC
				}
			);
		
	}

}
