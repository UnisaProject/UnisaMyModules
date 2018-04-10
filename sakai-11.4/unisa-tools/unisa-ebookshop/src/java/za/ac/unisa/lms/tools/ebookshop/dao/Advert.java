package za.ac.unisa.lms.tools.ebookshop.dao;

public class Advert {

	private String bookId;
	private String courseCode;
	private String courseDescription;
	private String addHeading;
	private String addText;
	private String dateAdded;
	private String contactDetails;

	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getAddHeading() {
		return addHeading;
	}
	public void setAddHeading(String addHeading) {
		this.addHeading = addHeading;
	}
	public String getAddText() {
		return addText;
	}
	public void setAddText(String addText) {
		this.addText = addText;
	}
	public String getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public String getContactDetails() {
		return contactDetails;
	}
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
}
