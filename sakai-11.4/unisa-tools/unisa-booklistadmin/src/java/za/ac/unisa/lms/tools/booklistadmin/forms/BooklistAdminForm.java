package za.ac.unisa.lms.tools.booklistadmin.forms;
import java.util.List;
import org.apache.struts.validator.ValidatorForm;
import za.ac.unisa.lms.tools.booklistadmin.module.AuditTrailModule;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
public class BooklistAdminForm extends ValidatorForm{

	private String collegeCode;
	private String college;
	private String colleg;
	private String year;
	private String lastYear;	
	private String currentYear;
	private String nextYear;
	private String statusOption;
	private String status;
	private List courselist,daysList,monthList,daysList2,monthList2,yearsList,closingYearsList,levelList,dateslist,dateslist2;
	private String courseId;
	private List booklist;
	private String cancelOption;
	private String courseNote="";
	private String  submitOption;
	private String typeOfBookList;
	private String searchOption;
	private String bookId;
	private String publisherName;
    private String typeOfBookListStr;
    private String releaseDate;
    private boolean dateRemovalScrnEntered=false,dateRemoved=false,releaseDateRemovalScrnEntered=false,manageDatesScrEntered=false,
    fromDeclareNoBookWin=false;

	private String displayOption;
	private String selectedStatus;
    private AuditTrailModule lastUpdated;
    private String displayAuditTrailName;
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
    private String course;
    private String eReserveType;
	private BookModule bookDetails;
 	private String txtOtherAuthor;
	private String noteToLibrary;
	private String availableAsEbook;
	private String ebookVolume;
	private String ereserveSearchOption;
	private String ebook_pages;
	private String url;
	private String masterCopy;
	private String masterCopyFormat;
	private String lastmodifiedforbook;
	private Integer publishStatus;
    private String courseLang;
    private String copyBook;
    private String continueOption;
    private String copyExistingBookOption;
    private String networkId;
    private String courseCount;
    private String backOption;
    private String academicYear;
    private String listEnteredFrom;
    private boolean includeYear,openDateChoosen=true,closingdatechoosen=false,remove=false,releaseDateRemoved=false;
    private String listyear,level="All",openingMonth,openingDay,openingYear,closingMonth="12",closingDay="31",closingYear="2030";
    
    
    
    
    public String getListEnteredFrom(){
        return listEnteredFrom;
    }
    public void setListEnteredFrom(String listEnteredFrom){
    	this.listEnteredFrom=listEnteredFrom;
    }
    public boolean isFromDeclareNoBookWin(){
    	return fromDeclareNoBookWin;
     }
     public void setFromDeclareNoBookWin(boolean fromDeclareNoBookWin){
    	    this.fromDeclareNoBookWin=fromDeclareNoBookWin;
     }
    String fromButton;
    
    public void setFromButton(String fromButton){
    	          this.fromButton=fromButton;
    }
    public String getFromButton(){
    	            return fromButton;
    }
    public void setReleaseDateRemovalScrnEntered(boolean releaseDateRemovalScrnEntered){
    	this.releaseDateRemovalScrnEntered=releaseDateRemovalScrnEntered;
    }
    public boolean isReleaseDateRemovalScrnEntered(){
    	  return releaseDateRemovalScrnEntered;
    }
    public void setReleaseDateRemoved(boolean releaseDateRemoved){
	    this.releaseDateRemoved=releaseDateRemoved;
    }
    public boolean isReleaseDateRemoved(){
	     return releaseDateRemoved;
    }
    public void setReleaseDate(String releaseDate){
    	   this.releaseDate=releaseDate;
    }
    public String getReleaseDate(){
    	  return releaseDate;
    }
    public void setDateRemoved(boolean dateRemoved){
    	    this.dateRemoved=dateRemoved;
     }
     public boolean isDateRemoved(){
    	 return dateRemoved;
     }
     public boolean isRemove(){
    	return remove;
     }
     public void setRemove(boolean remove){
    	    this.remove=remove;
     }
     public void setDatesList2(List dateslist2){
    	    this.dateslist2=dateslist2;
     }
     public List getDateslist2(){
    	 return   dateslist2;
     }
      public void setDateRemovalScrnEntered(boolean dateRemovalScrnEntered){
    	       this.dateRemovalScrnEntered=dateRemovalScrnEntered;
      }
      public boolean getDateRemovalScrnEntered(){
    	    return dateRemovalScrnEntered;
      }
     public String getOpeningMonth(){
    	    return openingMonth;
     }
     public void setOpeningMonth(String openingMonth){
    	       this.openingMonth=openingMonth;
     }
     public String getOpeningDay(){
    	    return openingDay;
     }
     public void setOpeningDay(String openingDay){
    	    this.openingDay=openingDay;
     }    
     public String getOpeningYear(){
    	 return openingYear;
     }
     public void  setOpeningYear(String openingYear){
    	    this.openingYear=openingYear;
     }
     public String getClosingMonth(){
 	    return closingMonth;
     }
     public void setClosingMonth(String closingMonth){
 	       this.closingMonth=closingMonth;
     }
     public String getClosingDay(){
 	    return closingDay;
     }
     public void setClosingDay(String closingDay){
 	    this.closingDay=closingDay;
     }
     public String getClosingYear(){
 	     return closingYear;
     }
     public void setClosingYear(String closingYear){
 	    this.closingYear=closingYear;
  }
    public void setOpenDateChoosen(boolean openDateChoosen){
    	    this.openDateChoosen=openDateChoosen;
    }
    public boolean getOpenDateChoosen(){
	          return openDateChoosen;
    }
    public void setClosingdatechoosen(boolean closingdatechoosen){
	    this.closingdatechoosen=closingdatechoosen;
    }
    public boolean isClosingdatechoosen(){
          return closingdatechoosen;
    }
    public void setLevel(String level){
    	this.level=level;
    }
    
    public String getLevel(){
    	return level;
    }
    public void setDaysList(List daysList){
    	this.daysList=daysList;
    }
    public List getDaysList(){
    	return daysList;
    }
    public void setMonthList(List monthList){
    	     this.monthList=monthList;
    }
    public List getMonthList(){
	     return monthList;
    }
    public void setDaysList2(List daysList2){
    	this.daysList2=daysList2;
    }
    public List getDaysList2(){
    	return daysList2;
    }
    public void setMonthList2(List monthList2){
    	     this.monthList2=monthList2;
    }
    public List getMonthList2(){
	     return monthList2;
    }
    public void setYearsList(List yearsList){
    	this.yearsList=yearsList;
    }
    public List getYearsList(){
    	return yearsList;
    }
    public List getClosingYearsList(){
    	return closingYearsList;
    }
    public void setClosingYearsList(List closingYearsList){
    	this.closingYearsList=closingYearsList;
    }
   public void setLevelList(List levelList){
    	 this.levelList=levelList;
    }
   public boolean isManageDatesScrEntered(){
	   return manageDatesScrEntered;
   }
   public void setManageDatesScrEntered(boolean manageDatesScrEntered){
	        this.manageDatesScrEntered=manageDatesScrEntered;
   }
    public List getLevelList(){
   	        return levelList;
   }
	public String getListyear() {
		return listyear;
	}
	public void setListyear(String listyear) {
		this.listyear = listyear;
	}
	public boolean isIncludeYear() {
		return includeYear;
	}
	public void setIncludeYear(boolean includeYear) {
		this.includeYear = includeYear;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getBackOption() {
		return backOption;
	}
	public void setBackOption(String backOption) {
		this.backOption = backOption;
	}
	public String getCourse() {
 		return course;
 	}
 	public void setCourse(String course) {
 		this.course = course;
 	} 
	public String getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(String courseCount) {
		this.courseCount = courseCount;
	}
	public String getColleg() {
		return colleg;
	}
	public void setColleg(String colleg) {
		this.colleg = colleg;
	}     
 	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}    
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
    public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getCopyExistingBookOption() {
		return copyExistingBookOption;
	}
	public void setCopyExistingBookOption(String copyExistingBookOption) {
		this.copyExistingBookOption = copyExistingBookOption;
	}
	public String getCopyBook() {
		return copyBook;
	}
	public void setCopyBook(String copyBook) {
		this.copyBook = copyBook;
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
	public BookModule getBookDetails() {
		return bookDetails;
	}
	public void setBookDetails(BookModule bookDetails) {
		this.bookDetails = bookDetails;
	}
	public String getTxtOtherAuthor() {
		return txtOtherAuthor;
	}
	public void setTxtOtherAuthor(String txtOtherAuthor) {
		this.txtOtherAuthor = txtOtherAuthor;
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
	public String getLastmodifiedforbook() {
		return lastmodifiedforbook;
	}
	public void setLastmodifiedforbook(String lastmodifiedforbook) {
		this.lastmodifiedforbook = lastmodifiedforbook;
	}
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public String getCourseLang() {
		return courseLang;
	}
	public void setCourseLang(String courseLang) {
		this.courseLang = courseLang;
	}
	public String getDisplayAuditTrailName() {
		return displayAuditTrailName;
	}
	public void setDisplayAuditTrailName(String displayAuditTrailName) {
		this.displayAuditTrailName = displayAuditTrailName;
	}
	public AuditTrailModule getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(AuditTrailModule lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Object getBookIndex(int index){
        return booklist.get(index);
   }
	public List getBooklist() {
		return booklist;
	}
   public Object getDatesIndex(int index){
	          return dateslist.get(index);
   }
   public void setDateslist(List dateslist){
    	this.dateslist=dateslist;
    }
    public List getDateslist(){
     	   return dateslist;
     }
	public void setBooklist(List booklist) {
		this.booklist = booklist;
	}
	public String getSelectedStatus() {
		return selectedStatus;
	}
	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusOption() {
		return statusOption;
	}

	public void setStatusOption(String statusOption) {
		this.statusOption = statusOption;
	}

	public String getLastYear() {
		return lastYear;
	}

	public void setLastYear(String lastYear) {
		this.lastYear = lastYear;
	}

	public String getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

	public String getNextYear() {
		return nextYear;
	}

	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}

	public String getCollegeCode() {
		return collegeCode;
	}

	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List getCourselist() {
		return courselist;
	}

	public void setCourselist(List courselist) {
		this.courselist = courselist;
	}

	public String getDisplayOption() {
		return displayOption;
	}

	public void setDisplayOption(String displayOption) {
		this.displayOption = displayOption;
	}
	public String getCancelOption() {
		return cancelOption;
	}
	public void setCancelOption(String cancelOption) {
		this.cancelOption = cancelOption;
	}
	public String getCourseNote() {
		return courseNote;
	}
	public void setCourseNote(String courseNote) {
		this.courseNote = courseNote;
	}
	public String getSubmitOption() {
		return submitOption;
	}
	public void setSubmitOption(String submitOption) {
		this.submitOption = submitOption;
	}
	public String getTypeOfBookList(){
		return typeOfBookList;
	}
	public void setTypeOfBookList(String typeOfBookList) {
		this.typeOfBookList = typeOfBookList;
	}
	public String getTypeOfBookListStr() {
		return typeOfBookListStr;
	}
	public void setTypeOfBookListStr(String typeOfBookListStr) {
		this.typeOfBookListStr = typeOfBookListStr;
	}
	public String getSearchOption() {
		return searchOption;
	}
	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getContinueOption() {
		return continueOption;
	}
	public void setContinueOption(String continueOption) {
		this.continueOption = continueOption;
	}
	public String geteReserveType(){
		return eReserveType;
	}
	public void seteReserveType(String eReserveType){
		this.eReserveType=eReserveType;
	}
	public String getEreserveSearchOption(){
		return ereserveSearchOption;
	}
	public void setEreserveSearchOption(String ereserveSearchOption){
		this.ereserveSearchOption=ereserveSearchOption;
	}
}
