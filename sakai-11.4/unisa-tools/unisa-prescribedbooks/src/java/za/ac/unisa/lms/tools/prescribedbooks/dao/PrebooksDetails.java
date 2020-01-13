package za.ac.unisa.lms.tools.prescribedbooks.dao;

public class PrebooksDetails  extends editionedBooks{

	private String bookNotes;
	private String courseNotes;
	public String getBookNotes() {
		return bookNotes;
	}
	public void setBookNotes(String bookNotes) {
		this.bookNotes = bookNotes;
	}
	public String getCourseNotes() {
		return courseNotes;
	}
	public void setCourseNotes(String courseNotes) {
		this.courseNotes = courseNotes;
	}
	
}
