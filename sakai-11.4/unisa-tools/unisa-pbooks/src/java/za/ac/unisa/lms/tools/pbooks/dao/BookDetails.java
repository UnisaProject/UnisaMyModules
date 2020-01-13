package za.ac.unisa.lms.tools.pbooks.dao;

public class BookDetails {
	
	 private String BookId;
	 private String txtTitle;
	 private String txtAuthor;
	 private String txtEdition;
	 private String txtYear;
	 private String txtPublisher;
	 private String txtISBN;
	 private String txtISBN1;
     private String txtISBN2;
     private String txtISBN3;
	 private String txtBookNote;
	 private Integer publishStatus;
	 private String course;
	 private String typeofbooklist;
	 private String courseLang;
	 private Integer acadYear;
	 private boolean remove;
	 private String confirmStatus;
	 private String networkID;
	 private String coloured;
	 private String typeOfBookList;
	 private String eReserveType;
	 
	 private String otherAuthor;
	 private String noteToLibrary;
	 private String availableAsEbook;
	 private String ebookVolume;
	 private String ebook_pages;
	 private String url;
	 private String masterCopy;
	 private String masterCopyFormat;
	 
	 private String bookAdded;
	 private String status;
	 private String editOption;
	 private boolean selectedCourse;
	 private boolean selectedAuthorize;

	 
	public String geteReserveType() {
		return eReserveType;
	}
	public void seteReserveType(String eReserveType) {
		this.eReserveType = eReserveType;
	}
	public boolean isSelectedAuthorize() {
		return selectedAuthorize;
	}
	public void setSelectedAuthorize(boolean selectedAuthorize) {
		this.selectedAuthorize = selectedAuthorize;
	}
	public boolean isSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(boolean selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public String getEditOption() {
		return editOption;
	}
	public void setEditOption(String editOption) {
		this.editOption = editOption;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNetworkID() {
		return networkID;
	}
	public void setNetworkID(String networkID) {
		this.networkID = networkID;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public Integer getAcadYear() {
		return acadYear;
	}
	public void setAcadYear(Integer acadYear) {
		this.acadYear = acadYear;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourseLang() {
		return courseLang;
	}
	public void setCourseLang(String courseLang) {
		this.courseLang = courseLang;
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
	public String getTxtBookNote() {
		return txtBookNote;
	}
	public void setTxtBookNote(String txtBookNote) {
		this.txtBookNote = txtBookNote;
	}
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public String getBookId() {
		return BookId;
	}
	public void setBookId(String bookId) {
		BookId = bookId;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public String getColoured() {
		return coloured;
	}
	public void setColoured(String coloured) {
		this.coloured = coloured;
	}
	public String getTypeOfBookList() {
		return typeOfBookList;
	}
	public void setTypeOfBookList(String typeOfBookList) {
		this.typeOfBookList = typeOfBookList;
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
	public void setNoteToLibrary(String noteToLibrary) {
		this.noteToLibrary = noteToLibrary;
	}
	public String getAvailableAsEbook() {
		return availableAsEbook;
	}
	public void setAvailableAsEbook(String availableAsEbook) {
		this.availableAsEbook = availableAsEbook;
	}
	public String getEbookVolume() {
		return ebookVolume;
	}
	public void setEbookVolume(String ebookVolume) {
		this.ebookVolume = ebookVolume;
	}
	public String getEbook_pages() {
		return ebook_pages;
	}
	public void setEbook_pages(String ebook_pages) {
		this.ebook_pages = ebook_pages;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getBookAdded() {
		return bookAdded;
	}
	public void setBookAdded(String bookAdded) {
		this.bookAdded = bookAdded;
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
	public String getTypeofbooklist() {
		return typeofbooklist;
	}
	public void setTypeofbooklist(String typeofbooklist) {
		this.typeofbooklist = typeofbooklist;
	}

}
