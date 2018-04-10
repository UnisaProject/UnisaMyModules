package za.ac.unisa.lms.tools.publicprescribedbooks.dao;

public class PrescribedBooksDetails {

	private String courseCode;
	private String description;
	private String author;
	private String title;
	private String yearPublished;
	private String edition;
	private String publisher;
	private String bookNotes;
	private String courseNotes;
	private Integer count;
	private String coloured;
	
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYearPublished() {
		return yearPublished;
	}
	public void setYearPublished(String yearPublished) {
		this.yearPublished = yearPublished;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getColoured() {
		return coloured;
	}
	public void setColoured(String coloured) {
		this.coloured = coloured;
	}
	
}
