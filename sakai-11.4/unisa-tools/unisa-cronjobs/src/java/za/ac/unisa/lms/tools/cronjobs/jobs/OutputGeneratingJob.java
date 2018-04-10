package za.ac.unisa.lms.tools.cronjobs.jobs;

import java.util.Date;

public interface OutputGeneratingJob {
	
	public String getOutput();
	public Date getStartTime();
	public Date getEndTime();
	
}
