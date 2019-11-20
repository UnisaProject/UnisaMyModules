package za.ac.unisa.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.tool.cover.ToolManager;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.cover.SiteService;

import za.ac.unisa.exceptions.NotACourseSiteException;

public class CoursePeriodLookup {


	public static String getCourseTypeAsString(String semester_code) {
		short code = Short.parseShort(semester_code);
		return getCourseTypeAsString(code);
	}

	public static String getCourseTypeAsString(short code) {
		switch (code) {
		case 0: { return "Y1";}
		case 1: { return "S1";}
		case 2: { return "S2";}
		case 6: { return "Y2";}
		}
		return "?";
	}
	/** This method takes the "Display Period" and convert it to a short **/
	public static short getPeriodTypeAsString(String semesterDisplay_code) {
		if ("Y1".equals(semesterDisplay_code)){
			return new Integer(0).shortValue();
		} else if ("S1".equals(semesterDisplay_code)){
			return new Integer(1).shortValue();
		} else if ("S2".equals(semesterDisplay_code)){
			return new Integer(2).shortValue();
		} else if ("Y2".equals(semesterDisplay_code)){
			return new Integer(6).shortValue();
		} else {
			/*
			 * This is just for an invalid Period sent through
			 * Should not happen
			 */
			return new Integer(99).shortValue();
		}

	}
	/** This method takes the "Display year" and convert it to int of 4 eg '05' -> 2005**/
	public static int getYearTypeAsString(String displayYear){
		int year = Integer.parseInt(displayYear);
		year = 2000 + year;
		return year;
	}

	/** This method takes the Semester period and returns the semeter type */
	public static String getSemesterPeriodAsString(short code)
	{
		switch (code) {
		case 0: { return "Full Year First Registration";}
		case 1: { return "First Semester";}
		case 2: { return "Second Semester";}
		case 6: { return "Full Year Second Registration";}
		}
		return "?";
	}


	/** This Method recieves the site ID and split it into the diffrent parts
	 * 1. Course code
	 * 2. Year
	 * 4. Semester period
	 */

	public static CoursePeriod getCoursePeriod(String siteId) throws IdUnusedException, NotACourseSiteException {

		Site site = SiteService.getSite(siteId);

		if (site == null) {
			throw new IdUnusedException(siteId);
		}
		//Allow site types for group courses 
		if ((site.getType() == null) || ((!site.getType().equalsIgnoreCase("course")) && (!site.getType().equalsIgnoreCase("onlcourse"))
				&& (!site.getType().equalsIgnoreCase("onlgroup")) && (!site.getType().equalsIgnoreCase("group")))) {
			throw new NotACourseSiteException(siteId);
		}

		CoursePeriod coursePeriod = new CoursePeriod();
		coursePeriod.setCourseCode(siteId.substring(0,7));
		coursePeriod.setSemesterPeriod(getPeriodTypeAsString(siteId.substring(11,13)));
		coursePeriod.setYear(getYearTypeAsString(siteId.substring(8,10)));
		coursePeriod.setSemesterType(getSemesterPeriodAsString(coursePeriod.getSemesterPeriod()));
		//for group site the length is greater than 13 ex: SUS1501-13-S1-1T 
		if(siteId.length()>13){
			coursePeriod.setGroup(siteId.substring(14));
		}else{
			coursePeriod.setGroup("");
		}
		return coursePeriod;
	}

	public static CoursePeriod getCoursePeriod() throws NotACourseSiteException {
		String siteId = ToolManager.getCurrentPlacement().getContext();
		//String siteId = PortalService.getCurrentSiteId(); /// o PortalService is no more
		Log log = LogFactory.getLog(CoursePeriodLookup.class);
		try {
			return getCoursePeriod(siteId);
		} catch (IdUnusedException idu) {
			log.error("How can this be???");
			idu.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList getYearList() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String stringYear = sdf.format(d);
		ArrayList yearOptions=new ArrayList();

		Integer i = new Integer(stringYear);
		int currentYear = i.intValue();
		int nextYear = currentYear + 1;
		int prevYear = currentYear -1;
		
		String currentYearStr = Integer.toString(currentYear);
		String nextYearStr = Integer.toString(nextYear);
		String prevYearStr = Integer.toString(prevYear);
		
		yearOptions.add(nextYearStr);
		yearOptions.add(currentYearStr);
		yearOptions.add(prevYearStr);		
		return yearOptions;
	}
	
}

