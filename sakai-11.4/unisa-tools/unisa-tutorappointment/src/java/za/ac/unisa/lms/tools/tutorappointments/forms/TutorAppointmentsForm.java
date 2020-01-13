package za.ac.unisa.lms.tools.tutorappointments.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.validator.ValidatorForm;

public class TutorAppointmentsForm extends ValidatorForm {

	private ArrayList academicYearOptions =new ArrayList();;  
	private ArrayList academicPeriodOptions=new ArrayList();
	
	public ArrayList getAcademicYearOptions() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String stringYear = sdf.format(date);
		
		int currentYear = new Integer(stringYear).intValue();
		int prevYear = currentYear -1;
		int prevYear2 = currentYear -2;
		int nextYear = currentYear + 1;
		int nextYear2 = currentYear + 2;
		String currentYearStr = Integer.toString(currentYear);
		String prevYear2Str = Integer.toString(prevYear2); 
		String prevYearStr = Integer.toString(prevYear);
		String nextYearStr = Integer.toString(nextYear);
		String nextYear2Str = Integer.toString(nextYear2);

		academicYearOptions.add(new org.apache.struts.util.LabelValueBean("..", ""));
		academicYearOptions.add(new org.apache.struts.util.LabelValueBean(prevYear2Str, prevYear2Str));
		academicYearOptions.add(new org.apache.struts.util.LabelValueBean(prevYearStr, prevYearStr));
		academicYearOptions.add(new org.apache.struts.util.LabelValueBean(currentYearStr, currentYearStr));
		academicYearOptions.add(new org.apache.struts.util.LabelValueBean(nextYearStr, nextYearStr));
		academicYearOptions.add(new org.apache.struts.util.LabelValueBean(nextYear2Str, nextYear2Str));		
		
		return academicYearOptions;
	}
	public void setAcademicYearOptions(ArrayList academicYearOptions) {
		this.academicYearOptions = academicYearOptions;
	}
	public ArrayList getAcademicPeriodOptions() {
		
		academicPeriodOptions.add(new org.apache.struts.util.LabelValueBean("..",""));
		academicPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 1","1"));
		academicPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Semester 2","2"));
		academicPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course","0"));
		academicPeriodOptions.add(new org.apache.struts.util.LabelValueBean("Year Course June","6"));
		
		return academicPeriodOptions;
	}
	public void setAcademicPeriodOptions(ArrayList academicPeriodOptions) {
		this.academicPeriodOptions = academicPeriodOptions;
	}
	
}
