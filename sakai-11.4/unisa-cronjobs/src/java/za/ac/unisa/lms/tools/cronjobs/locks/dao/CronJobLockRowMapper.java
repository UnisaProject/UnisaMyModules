package za.ac.unisa.lms.tools.cronjobs.locks.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class CronJobLockRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		CronJobLock cjl = new CronJobLock();
		
		cjl.setJobId(rs.getString("JOB_ID"));
		cjl.setLockTime(rs.getTimestamp("LOCK_TIME"));
		cjl.setModifiedOn(rs.getTimestamp("MODIFIED_ON"));
		cjl.setServerId(rs.getString("SERVER_ID"));
		
		return cjl;
	}

}
