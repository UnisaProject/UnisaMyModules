package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import za.ac.unisa.lms.tools.cronjobs.dao.ForgotPasswordDAO;
import za.ac.unisa.lms.tools.cronjobs.locks.SingleClusterInstanceJob;

public class ForgotPasswordJob extends SingleClusterInstanceJob implements StatefulJob, OutputGeneratingJob {

	/** Job to remove all records from table UNISA_PASSWORD
	 * that is older that 24 hours.
	 */
	public static long runcount = 1L;
	private ByteArrayOutputStream outputStream;
	private PrintWriter output;

	/*
	 * Crontab file
___________
Crontab syntax :-
A crontab file has five fields for specifying day , date and time  followed by the command to be run at that interval.
*     *   *   *    *  command to be executed
-     -    -    -    -
|     |     |     |     |
|     |     |     |     +----- day of week (0 - 6) (Sunday=0)
|     |     |     +------- month (1 - 12)
|     |     +--------- day of month (1 - 31)
|     +----------- hour (0 - 23)
+------------- min (0 - 59) (non-Javadoc)
	 */

	public void executeLocked(JobExecutionContext context)
			throws JobExecutionException {

		System.out.println("Cronjob: FORGOTPASSWORDJOB");
		ForgotPasswordDAO db1 = new ForgotPasswordDAO();

		/** ===========  select bookings between from and to dates */
		try {
			db1.deleteForgottenPassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOutput() {
		if (output != null) {
			output.flush();
			return outputStream.toString();
		}
		return null;
	}

}
