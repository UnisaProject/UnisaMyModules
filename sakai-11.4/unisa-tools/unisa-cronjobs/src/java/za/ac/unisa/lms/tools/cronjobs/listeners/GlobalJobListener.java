package za.ac.unisa.lms.tools.cronjobs.listeners;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import za.ac.unisa.lms.tools.cronjobs.jobs.OutputGeneratingJob;
import za.ac.unisa.utils.AdminEmail;

public class GlobalJobListener implements JobListener {

	private static Log log = LogFactory.getLog(GlobalJobListener.class);
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		log.debug(context.getJobDetail().getFullName() + " to be executed.");
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		log.debug(context.getJobDetail().getFullName() + " execution vetoed.");
	}

	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		log.debug(context.getJobDetail().getFullName() + " was executed");
		
		try {
			Job job = context.getJobInstance();
			
			if (job instanceof OutputGeneratingJob) {
				OutputGeneratingJob ojob = (OutputGeneratingJob) job;
				String output = ojob.getOutput();
				if ((output != null) && (!output.equals(""))) {
					String subject = context.getJobDetail().getFullName() + " output";
					output += "\n\nStart time: "+ojob.getStartTime();
					output += "\nEnd time: "+ojob.getEndTime();
					try {
						AdminEmail.sendAdminEmail(subject, output);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("Exception: "+e+" "+e.getMessage());
					}
				}
			}
		
			if (jobException != null) {
				
				jobException.printStackTrace();
				log.error(context.getJobDetail().getFullName() + "Exception: "+jobException);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintWriter pw = new PrintWriter(baos);
				pw.println("Exception thrown: "+jobException);
				pw.println("Stacktrace follows");
				jobException.printStackTrace(pw);
				String exceptionName = jobException.getClass().getName();
				
				Throwable cause = jobException.getCause();
				
				if (cause != null) {
					pw.println();
					pw.println("Caused by: "+cause);
					pw.println("Stacktrace follows");
					exceptionName = cause.getClass().getName();
					cause.printStackTrace(pw);
				}
				
				cause = jobException.getUnderlyingException();
				
				if (cause != null) {
					pw.println();
					pw.println("Caused by: "+cause);
					pw.println("Stacktrace follows");
					exceptionName = cause.getClass().getName();
					cause.printStackTrace(pw);
				}
				
				String subject = context.getJobDetail().getFullName() + " exception: "+exceptionName;
				pw.flush();
				String message = baos.toString();
				try {
					AdminEmail.sendAdminEmail(subject, message);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("Exception: "+e+" "+e.getMessage());
					throw e;
				}
			}
		} catch (Exception e) {
			log.error("Unhandled exception: "+e+" "+e.getMessage());
			e.printStackTrace();
		}
	}

}
