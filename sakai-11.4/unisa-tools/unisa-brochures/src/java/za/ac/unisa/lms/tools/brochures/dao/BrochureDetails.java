package za.ac.unisa.lms.tools.brochures.dao;

import java.util.Date;

public class BrochureDetails {
	
	private String userid;
	private String date;
	private String reportType;
	private String report;
	private String college;
	private String format;
	private String year;
	private boolean data;
	
	public boolean isData() {
		return data;
	}
	public void setData(boolean data) {
		this.data = data;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
