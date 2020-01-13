package za.ac.unisa.lms.tools.honsadmission.forms;

public class Student {
	
	private int number;
	private String surname;
	private String title;
	private String initials;
	private String name;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getName() {
		StringBuffer stuName = new StringBuffer(this.title);
		stuName.append(' ');
		stuName.append(this.initials);
		stuName.append(' ');
		stuName.append(this.surname);
		
		return stuName.toString();
//		return this.title + ' ' + this.initials + ' ' + this.surname;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
