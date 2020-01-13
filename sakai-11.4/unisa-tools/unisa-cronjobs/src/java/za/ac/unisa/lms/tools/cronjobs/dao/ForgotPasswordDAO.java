package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Calendar;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class ForgotPasswordDAO extends SakaiDAO{

	/**
	 * Delete all records where request_date older than 24 hours.
	 * @param
	 * @return
	 * @throws Exception
	 */
	public boolean deleteForgottenPassword() throws Exception{

		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());

		boolean deleteSuccess = false;

		String sql1 = "DELETE FROM UNISA_PASSWORD " +
					  "WHERE TO_CHAR(REQUEST_DATE,'DD-MON-YYYY HH:MI') < TO_CHAR(SYSDATE - 24/24,'DD-MON-YYYY HH:MI')";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
			deleteSuccess = true;
		} catch (Exception ex) {
			throw new Exception("(FORGOTPASSWORDJOB: ForgotPasswordDAO: deleteForgottenPassword (Delete): Error occurred /"+ ex,ex);
		}

       return deleteSuccess;
	}

}
