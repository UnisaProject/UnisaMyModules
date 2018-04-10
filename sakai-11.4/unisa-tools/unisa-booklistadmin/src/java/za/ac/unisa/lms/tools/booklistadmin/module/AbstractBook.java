package za.ac.unisa.lms.tools.booklistadmin.module;

public abstract class AbstractBook {
	 private String BookId;
	 private String txtTitle;
	 private String txtAuthor;
	 private String txtOtherAuthor;
	 private String txtEdition;
	 private String txtYear;
	 private String txtPublisher;
	 private String txtISBN;
	 private String txtISBN1;
     private String txtISBN2;
     private String txtISBN3;
	 private String txtBookNote;
	 private String masterCopy;
	 private String masterCopyFormat;
	 private String bibliographicnr;
	 private Integer publishStatus;
	 private String isPublished;	
	 private String otherAuthor;
	 private String noteToLibrary;
	 private String typeOfBookList;
	 
	 public void setNoteToLibrary(String noteToLibrary) {
			this.noteToLibrary = noteToLibrary;
		}
     public String getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(String isPublished) {
		this.isPublished = isPublished;
	}
	public String getTxtOtherAuthor() {
		return txtOtherAuthor;
	}
	public void setTxtOtherAuthor(String txtOtherAuthor) {
		this.txtOtherAuthor = txtOtherAuthor;
	}
	public String getBookId() {
		return BookId;
	}
	
	public void setBookId(String bookId) {
		BookId = bookId;
	}
	public String getTxtTitle() {
		return txtTitle;
	}
	public void setTxtTitle(String txtTitle) {
		this.txtTitle = txtTitle;
	}
	public String getTxtAuthor() {
		return txtAuthor;
	}
	public void setTxtAuthor(String txtAuthor) {
		this.txtAuthor = txtAuthor;
	}
	public String getTxtEdition() {
		return txtEdition;
	}
	public void setTxtEdition(String txtEdition) {
		this.txtEdition = txtEdition;
	}
	public String getTxtYear() {
		return txtYear;
	}
	public void setTxtYear(String txtYear) {
		this.txtYear = txtYear;
	}
	public String getTxtPublisher() {
		return txtPublisher;
	}
	public void setTxtPublisher(String txtPublisher) {
		this.txtPublisher = txtPublisher;
	}
	public String getTxtISBN() {
		return txtISBN;
	}
	public void setTxtISBN(String txtISBN) {
		this.txtISBN = txtISBN;
	}
	public String getTxtISBN1() {
		return txtISBN1;
	}
	public void setTxtISBN1(String txtISBN1) {
		this.txtISBN1 = txtISBN1;
	}
	public String getTxtISBN2() {
		return txtISBN2;
	}
	public void setTxtISBN2(String txtISBN2) {
		this.txtISBN2 = txtISBN2;
	}
	public String getTxtISBN3() {
		return txtISBN3;
	}
	public void setTxtISBN3(String txtISBN3) {
		this.txtISBN3 = txtISBN3;
	}
	public String getTxtBookNote() {
		return txtBookNote;
	}
	public void setTxtBookNote(String txtBookNote) {
		this.txtBookNote = txtBookNote;
	}

	public String getOtherAuthor() {
		return otherAuthor;
	}
	public void setOtherAuthor(String otherAuthor) {
		this.otherAuthor = otherAuthor;
	}
	public String getNoteToLibrary() {
		return noteToLibrary;
	}
	public String getMasterCopy() {
		return masterCopy;
	}
	public void setMasterCopy(String masterCopy) {
		this.masterCopy = masterCopy;
	}
	public String getMasterCopyFormat() {
		return masterCopyFormat;
	}
	public void setMasterCopyFormat(String masterCopyFormat) {
		this.masterCopyFormat = masterCopyFormat;
	}
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public String getBibliographicnr() {
			return bibliographicnr;
	}
	public void setBibliographicnr(String bibliographicnr) {
			this.bibliographicnr = bibliographicnr;
	}
	public String getTypeOfBookList() {
		return typeOfBookList;
	}
	public void setTypeOfBookList(String typeOfBookList) {
		this.typeOfBookList = typeOfBookList;
	}
	
}
