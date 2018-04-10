package za.ac.unisa.lms.tools.prescribedbooks.dao;
public abstract class books {

	protected String title,author,pubYear,publisher,coloured;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubYear() {
		return pubYear;
	}
	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getColoured() {
		return coloured;
	}
	public void setColoured(String coloured) {
		this.coloured = coloured;
	}
}
