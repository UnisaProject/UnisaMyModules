package za.ac.unisa.lms.tools.cronjobs.locks.dao;

import java.sql.Types;
import java.util.Collection;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import za.ac.unisa.lms.db.SakaiDAO;

public class CronJobLockDAO extends SakaiDAO {
	
	public CronJobLock getCronJobLockById(String lockId) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String query = "select * from CRONJOB_LOCK where JOB_ID = ?";
		
		return (CronJobLock) 
			DataAccessUtils.uniqueResult(
				(Collection<?>) jdt.query(query,
				new Object[] { lockId },
				new int[] { Types.VARCHAR },
				new RowMapperResultSetExtractor(new CronJobLockRowMapper(), 1)));
	}
	
	public void updateCronJobLock(CronJobLock cjl) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update("update CRONJOB_LOCK set " +
				"LOCK_TIME = ?, " +
				"MODIFIED_ON = ?," +
				"SERVER_ID = ? where JOB_ID = ?",
				new Object[] { 
					cjl.getLockTime(),
					cjl.getModifiedOn(),
					cjl.getServerId(),
					cjl.getJobId()});
	}
	
	public void deleteCronJobLock(CronJobLock cjl) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update("delete from CRONJOB_LOCK where " +
				"JOB_ID = ?",
				new Object[] { cjl.getJobId() });
	}
	
	public void insertCronJobLock(CronJobLock cjl) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update("insert into CRONJOB_LOCK " +
				"(JOB_ID, LOCK_TIME, MODIFIED_ON, SERVER_ID) values" +
				"(?, ?, ?, ?)", 
				new Object[] {
					cjl.getJobId(),
					cjl.getLockTime(),
					cjl.getModifiedOn(),
					cjl.getServerId() });
		
	}
}
