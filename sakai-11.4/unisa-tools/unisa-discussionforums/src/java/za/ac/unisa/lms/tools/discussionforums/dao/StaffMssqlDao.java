package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.MSSQLServerDAO;

public class StaffMssqlDao extends MSSQLServerDAO{
	public StaffMssqlDao(String database) {
		super(database);
	}
	
	public PosterDetails getLastPoster(String novellid) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		PosterDetails posterDetails = new PosterDetails();		
		List result = jdt.query("select p_vrltrs,p_van from persinlig where p_netwerkkode = ? ",
				new Object[] { novellid},
				new int[] { Types.VARCHAR },
						new MssqlPosterRowMapper());
		Iterator i = result.iterator();
		if(i.hasNext()){
			posterDetails = (PosterDetails) i.next();
		}
		return (posterDetails); 
	}	
}
