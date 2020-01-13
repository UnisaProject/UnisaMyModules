package za.ac.unisa.lms.tools.assmarkerreallocation.forms;

public class MarkerDetailRecord {
	
	private MarkerModel markerModel;	
	private int actualMarkedPerc;
	private int studentSubmit;
	private int marked;
	private int marksOutstanding;
	private int avgMarkPerc;
	
	public MarkerModel getMarker() {
		return markerModel;
	}
	public void setMarker(MarkerModel markerModel) {
		this.markerModel = markerModel;
	}
	public int getActualMarkedPerc() {
		return actualMarkedPerc;
	}
	public void setActualMarkedPerc(int actualMarkedPerc) {
		this.actualMarkedPerc = actualMarkedPerc;
	}
	public int getStudentSubmit() {
		return studentSubmit;
	}
	public void setStudentSubmit(int studentSubmit) {
		this.studentSubmit = studentSubmit;
	}
	public int getMarked() {
		return marked;
	}
	public void setMarked(int marked) {
		this.marked = marked;
	}
	public int getMarksOutstanding() {
		return marksOutstanding;
	}
	public void setMarksOutstanding(int marksOutstanding) {
		this.marksOutstanding = marksOutstanding;
	}
	public int getAvgMarkPerc() {
		return avgMarkPerc;
	}
	public void setAvgMarkPerc(int avgMarkPerc) {
		this.avgMarkPerc = avgMarkPerc;
	}	
	
}
