package za.ac.unisa.lms.tools.mdrpm.forms;

import java.util.List;
import za.ac.unisa.lms.domain.general.*;
import za.ac.unisa.lms.dao.Gencod;

public class RpmRecord {
	
	private List<Promotor> promotorList;
	private String title;
	private List<Qsprout>	routingList;
	private Student student;
	private Qualification qualification;
	private StudyUnit studyunit;	
	private List<TrackRecord> trackingList;
	
	public List<TrackRecord> getTrackingList() {
		return trackingList;
	}
	public void setTrackingList(List<TrackRecord> trackingList) {
		this.trackingList = trackingList;
	}
	public Qualification getQualification() {
		return qualification;
	}
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}
	public StudyUnit getStudyunit() {
		return studyunit;
	}
	public void setStudyunit(StudyUnit studyunit) {
		this.studyunit = studyunit;
	}	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Promotor> getPromotorList() {
		return promotorList;
	}
	public void setPromotorList(List<Promotor> promotorList) {
		this.promotorList = promotorList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public List<Qsprout> getRoutingList() {
		return routingList;
	}
	public void setRoutingList(List<Qsprout> routingList) {
		this.routingList = routingList;
	}
	
	
	
}
