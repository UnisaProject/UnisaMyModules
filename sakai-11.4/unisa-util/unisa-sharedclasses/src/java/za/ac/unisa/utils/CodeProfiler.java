package za.ac.unisa.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CodeProfiler {

	private long runStartTime;

	public void start(HttpServletRequest request) {
		if (request.getAttribute("profiler.startdate") == null) {
			request.setAttribute("profiler.startdate", new Date());
		}
		runStartTime = new Date().getTime();
	}

	public void stop(HttpServletRequest request, String message) {
		Log log = LogFactory.getLog(this.getClass());
		try {
			long runEndTime = new Date().getTime();
			Date profilerStartDate = (Date) request.getAttribute("profiler.startdate");
			long profilerStartTime = profilerStartDate.getTime();
			long profilerEndTime = new Date().getTime();
			String profilerId = request.getSession().getId();
			log.info(profilerId+" "+message+" "+(runEndTime - runStartTime)+"ms "+(profilerEndTime - profilerStartTime)+"ms ");
		} catch (Exception e) {
			log.error("Unhandled exception: "+e);
			e.printStackTrace();
		}
	}
}
