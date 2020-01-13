//Created by MyEclipse Struts

package za.ac.unisa.lms.tools.graduationceremony.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class GraduationCeremonyForm extends ValidatorActionForm {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Student student = new Student();
	private Address address = new Address();
	private Address addrchg = new Address();
	private Gradcerem ceremony = new Gradcerem();
	
	private String displayname;
	private String begindate;
	private String enddate;
	private String confirmname;
	private String confirmdate;
	private String pagetype;
	private String pagedisplay;

	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getConfirmname() {
		return confirmname;
	}
	public void setConfirmname(String confirmname) {
		this.confirmname = confirmname;
	}
	public String getConfirmdate() {
		return confirmdate;
	}
	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}
	public String getPagetype() {
		return pagetype;
	}
	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Address getAddrchg() {
		return addrchg;
	}
	public void setAddrchg(Address addrchg) {
		this.addrchg = addrchg;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Gradcerem getCeremony() {
		return ceremony;
	}
	public void setCeremony(Gradcerem ceremony) {
		this.ceremony = ceremony;
	}
	public String getPagedisplay() {
		return pagedisplay;
	}
	public void setPagedisplay(String pagedisplay) {
		this.pagedisplay = pagedisplay;
	}

	
}
