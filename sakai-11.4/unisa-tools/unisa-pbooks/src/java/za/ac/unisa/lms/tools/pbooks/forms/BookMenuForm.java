package za.ac.unisa.lms.tools.pbooks.forms;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;


import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;
import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;

import za.ac.unisa.lms.tools.pbooks.dao.AuditTrail;
import za.ac.unisa.lms.tools.pbooks.dao.BookDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CollegeDeptDetails;
import za.ac.unisa.lms.tools.pbooks.dao.CourseDAO;


public class BookMenuForm extends ValidatorForm{
         private String typeOfBookList;
         private String courseId;
         private List crs,crs2;
         private String txtTitle;
         private String txtAuthor;
         private String txtEdition;
         private String txtYear;
         private String txtPublisher,txtISBN,txtISBN1,txtISBN2,txtISBN3;
         private String txtBookNote,courseNote,searchOption,ereserveSearchOption;
         private Integer publishStatus;
         private String courseLang;
         private BookDetails bookDetails;
         private CollegeDeptDetails collegeDeptDetails; 
         private List bookList,rbookList,ebookList,yearsList;
         private String remove;
         private List selectedBooks;
         private String acadyear; 
         private String submitOption;
         private String cancelOption;
         private String continueOption;
         private String bookListStatus,rbookListStatus,ebookListStatus,bookListDatesStatus,rbookListDatesStatus,ebookListDatesStatus;
         private String networkId;
         private AuditTrail lastUpdated,rlastUpdated,elastUpdated;
         private String copyBook;
         private String deptName,booklistClosedMessage="Booklist is closed";
    
         private String bookId;
         private String copyExistingBookOption;
         private String displayAuditTrailName,displayAuditTrailNameForR,displayAuditTrailNameForE;
         private String hodComments;
         
     	 private String college;
     	 private String department;
     	 private String codInitials;
     	 private String codSurname;
     	 private String codNovelluserId;
     	 private String deptCode;
     	 private List standInChairs; 
     	 private List standInSchools; 
     	 private String standinInitial;
     	 private String standinSurname;
     	 
		private String standinNovellCode;
     	 private String selectedPerson;
     	 
     	 private String txtOtherAuthor;
    	 private String noteToLibrary;
    	 private String availableAsEbook;
    	 private String ebookVolume;
    	 private String ebook_pages;
    	 private String url;
    	 private String masterCopy;
    	 private String masterCopyFormat;
    	 private String lastmodifiedforbook,lastmodifiedforEbook,lastmodifiedforRbook;
    	          
         private boolean add,deleteBooks,editBooks,confirmBooks,addCourseNote,unConfirmBooks;
         private boolean authorizeBooks;
         private boolean validateBooks,dateIsWithinLimits,dateIsWithinLimitsForE,dateIsWithinLimitsForR;
         private String editOption,deanViewStep1Reached="notReached";
         
         private List presCourseList,recCourseList,eresCourseList,deanCourseList;
         private Set schools;
         private Set schoolDetails1;         
         private ArrayList selectedList,nonSelectedList;
         private String selectedCourse="Select Course Code";
         private String authComment;
         private String schDirector;
         private String personnelNr;
         private String schDirSurname;
         private String schDirNetworkId;
         private String deanSurname;
         private String deanNetworkId;       
         private String deputydean1Surname;
         private String deputydean1NetworkId;     
         private String deputydean2Surname;
         private String deputydean2NetworkId; 
         private String deputydean3Surname;
         private String deputydean3NetworkId; 
         private String deputydean4Surname;
         private String deputydean4NetworkId; 
         private String userName;
         private String whoModifiedPbook,whoModifiedRBook,whoModifiedEBook;
         private String listStatus,plistStatus,rlistStatus,elistStatus;
         private String secWinTracker="0",selectionTracker="1";//if  selectionTracker ==0 acadyear id selected else course is selected
         private String listDate,mainViewTracker="0";                         	
     	 private static final long serialVersionUID = 1L;
    	 private List prebooksList;
    	 private List courselist;
    	 private String status;
    	 private boolean selectedAuthorize=true,acadYearSelected=false;
    	 private String course,yearselected="false",bookListClosedMessage="Booklist is closed";
    	 private List deputyDeanList; 
    	 private String eReserveType;
    	 
    	 //Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4, 10 & 14:Tutor Booklist
    	 private int tutorBookListStatus = 0, dissAuthTracker = 0;
    	 private String countUpdateTracker = "0", tutorCount, isDiss = "false";	
    	 private List dissAuthNames, dissAuthEmails;			//DISS Authoriser names and emails
    	 private String selectedDissName;						//Radio button selection
    	 
    	public String geteReserveType() {
			return eReserveType;
		}
		public void seteReserveType(String eReserveType) {
			this.eReserveType = eReserveType;
		}
		public String getEreserveSearchOption() {
			return ereserveSearchOption;
		}
		public void setEreserveSearchOption(String ereserveSearchOption) {
			this.ereserveSearchOption = ereserveSearchOption;
		}
		public List getDeputyDeanList() {
			return deputyDeanList;
		}
		public void setDeputyDeanList(List deputyDeanList) {
			this.deputyDeanList = deputyDeanList;
		}
		public List getStandInSchools() {
			return standInSchools;
		}
		public void setStandInSchools(List standInSchools) {
			this.standInSchools = standInSchools;
		}
    	
    	public String deanViewStep1Reached(){
    		   return deanViewStep1Reached;
    	}
    	public void setDeanViewStep1Reached(String deanViewStep1Reached){
    		       this.deanViewStep1Reached=deanViewStep1Reached;
    	}
    	public boolean isAcadYearSelected(){
    		    return acadYearSelected;
    	}
    	public void setAcadYearSelected(boolean acadYearSelected){
    		  this.acadYearSelected=acadYearSelected;
    	}
    	public void setBookListClosedMessage(String bookListClosedMessage){
    		    this.bookListClosedMessage=bookListClosedMessage;
    	}
    	public String getbookListClosedMessage(){
    		return bookListClosedMessage;
    	}
    	public void setSelectionTracker(String selectionTracker){
    		     this.selectionTracker=selectionTracker;
    	}
    	public String  getSelectionTracker(){
    		     return selectionTracker;
    	}
    	public String getYearselected(){
    		      return yearselected;
    	}
    	public void setYearselected(String yearselected){
    		   this.yearselected=yearselected;
    	}
    	public boolean isDateIsWithinLimits(){
    		return  dateIsWithinLimits;
    	}
    	public void setDateIsWithinLimits(boolean dateIsWithinLimits){
    		this.dateIsWithinLimits=dateIsWithinLimits;
    	}
    	public boolean isDateIsWithinLimitsForR(){
    		return  dateIsWithinLimitsForR;
    	}
    	public void setDateIsWithinLimitsForR(boolean dateIsWithinLimitsForR){
    		this.dateIsWithinLimitsForR=dateIsWithinLimitsForR;
    	}
    	public boolean isDateIsWithinLimitsForE(){
    		return  dateIsWithinLimitsForE;
    	}
    	public void setDateIsWithinLimitsForE(boolean dateIsWithinLimitsForE){
    		this.dateIsWithinLimitsForE=dateIsWithinLimitsForE;
    	}
        public void setYearsList(List yearsList){
        	     this.yearsList=yearsList;
        }
    	public List getYearsList(){
    		  return yearsList;
    	}
    	public String getCourse() {
			  return course;
		}
		public void setCourse(String course) {
			  this.course = course;
		}
		public Object getRecordIndexed2(int index) {
    		  return bookList.get(index);
    	}    	
    	public boolean isSelectedAuthorize() {
			  return selectedAuthorize;
		}
		public void setSelectedAuthorize(boolean selectedAuthorize) {
			 this.selectedAuthorize = selectedAuthorize;
		}
		public String getStatus() {
			 return status;
		}
		public void setStatus(String status) {
			 this.status = status;
		}
		public List getCourselist() {
			return courselist;
		}
		public void setCourselist(List courselist) {
			this.courselist = courselist;
		}
		public void setMainViewTracker(String mainViewTracker){
    		   this.mainViewTracker=mainViewTracker;
    	}
    	public String getMainViewTracker(){
    		return mainViewTracker;
    	}
    	public void setSecWinTracker(String secWinTracker){
    		        this.secWinTracker=secWinTracker;
    	}
    	public String getSecWinTracker(){
	        return  secWinTracker;
        }
        public void setListStatus(String listStatus){
    		 this.listStatus= listStatus;
    	}
    	public String getListStatus(){
   		        return listStatus;
   	    }
    	public void setListDate(String listDate){
   		     this.listDate= listDate;
   	    }
   	    public String getListDate(){
  		        return listDate;
  	    }
    	public void setPlistStatus(String listStatus){
   		     this.plistStatus= listStatus;
   	    }
   	    public String getPlistStatus(){
  		        return plistStatus;
  	    }
   	    public void setRlistStatus(String listStatus){
		     rlistStatus= listStatus;
	    }
	    public String getRlistStatus(){
		        return rlistStatus;
	    }
	    public void setElistStatus(String listStatus){
   		      elistStatus= listStatus;
      	}
      	public String getElistStatus(){
  		        return elistStatus;
  	    }
    	public void setWhoModifiedPbook(String whoModifiedPBook){
    		  this.whoModifiedPbook=whoModifiedPBook;
    	}
        public String getWhoModifiedPbook(){
        	    return whoModifiedPbook;
        }
        public void setWhoModifiedRBook(String whoModifiedRBook){
  		      this.whoModifiedRBook=whoModifiedRBook;
  	    }
        public String getWhoModifiedRBook(){
      	      return whoModifiedRBook;
        }
        public void setWhoModifiedEBook(String whoModifiedEBook){
		      this.whoModifiedEBook=whoModifiedEBook;
	    }
        public String getWhoModifiedEBook(){
    	      return whoModifiedEBook;
        }
        public List getPrebooksList() {
			return prebooksList;
		}
		public void setPrebooksList(List prebooksList) {
			this.prebooksList = prebooksList;
		}
		public String getCopyExistingBookOption() {
			return copyExistingBookOption;
		}
		public void setCopyExistingBookOption(String copyExistingBookOption) {
			this.copyExistingBookOption = copyExistingBookOption;
		}
		public AuditTrail getLastUpdated() {
                return lastUpdated;
        }
        public void setLastUpdated(AuditTrail lastUpdated) {
                this.lastUpdated = lastUpdated;
        }
        public void setRlastUpdated(AuditTrail rlastUpdated) {
               this.rlastUpdated = rlastUpdated;
        }
        public AuditTrail getRlastUpdated() {
            return rlastUpdated;
        }
        public AuditTrail getElastUpdated() {
               return elastUpdated;
        }
        public void setElastUpdated(AuditTrail elastUpdated) {
               this.elastUpdated = elastUpdated;
        }
        public String getNetworkId() {
                return networkId;
        }
        public void setNetworkId(String networkId) {
                this.networkId = networkId;
        }
        public String getBookListDatesStatus() {
                return bookListDatesStatus;
        }
        public void setBookListDatesStatus(String bookListDatesStatus) {
                this.bookListDatesStatus = bookListDatesStatus;
        }
        public String getRbookListDatesStatus() {
            return rbookListDatesStatus;
       }
       public void setRbookListDatesStatus(String bookListDatesStatus) {
            this.rbookListDatesStatus = bookListDatesStatus;
       }
        public String getEbookListDatesStatus() {
            return ebookListDatesStatus;
        }
        public void setEbookListDatesStatus(String bookListDatesStatus) {
             this.ebookListDatesStatus = bookListDatesStatus;
        }
        public String getBookListStatus() {
            return bookListStatus;
    }
    public void setBookListStatus(String bookListStatus) {
            this.bookListStatus = bookListStatus;
    }
    public String getRbookListStatus() {
        return rbookListStatus;
   }
   public void setRbookListStatus(String bookListStatus) {
        this.rbookListStatus = bookListStatus;
   }
    public String getEbookListStatus() {
        return ebookListStatus;
    }
    public void setEbookListStatus(String bookListStatus) {
         this.ebookListStatus = bookListStatus;
    }
        public List getSelectedBooks() {
                return selectedBooks;
        }
        public void setSelectedBooks(List selectedBooks) {
                this.selectedBooks = selectedBooks;
        }
        public String getRemove() {
                return remove;
        }
        public void setRemove(String remove) {
                this.remove = remove;
        }
        public Object getBookIndex(int index){
                return bookList.get(index);
        }
        public List getBookList() {
                return bookList;
        }
        public void setBookList(List bookList) {
                this.bookList = bookList;
        }
        public List getRbookList() {
            return rbookList;
        }
        public void setRbookList(List bookList) {
            this.rbookList = rbookList;
        }
        public List getEbookList() {
            return ebookList;
        }
        public void setEbookList(List bookList) {
            this.ebookList = ebookList;
        }
        public String getHodComments() {
			return hodComments;
		}
		public void setHodComments(String hodComments) {
			this.hodComments = hodComments;
		}
		public String getSearchOption() {
                return searchOption;
        }
        public void setSearchOption(String searchOption) {
                this.searchOption = searchOption;
        }
        public String getCourseNote() {
                return courseNote;
        }
        public void setCourseNote(String courseNote) {
                this.courseNote = courseNote;
        }
        public String getTypeOfBookList() {
                return typeOfBookList;
        }
        public void setTypeOfBookList(String typeOfBookList) {
                this.typeOfBookList = typeOfBookList;
        }
        public String getCourseId() {
                return courseId;
        }
        public void setCourseId(String courseId) {
                this.courseId = courseId;
        }
        public List getCrs() {
        	  return crs;
        }
        public void setCrs(List crs) {
                this.crs = crs;
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

        public BookDetails getBookDetails() {
                return bookDetails;
        }

        public void setBookDetails(BookDetails bookDetails) {
                this.bookDetails = bookDetails;
        }

        public String getCourseLang() {
                return courseLang;
        }

        public void setCourseLang(String courseLang) {
                this.courseLang = courseLang;
        }
        public String getAcadyear() {
                return acadyear;
        }
        private boolean yearClicked=false;
        public boolean isYearClicked(){
        	return yearClicked;
        }
        public void setYearClicked(boolean yearClicked){
        	   this.yearClicked=yearClicked;
        }
        public void setAcadyear(String acadyear) {
        	           this.acadyear = acadyear;
        }
        public String getSubmitOption() {
                return submitOption;
        }
        public void setSubmitOption(String submitOption) {
                this.submitOption = submitOption;
        }
        public String getCancelOption() {
                return cancelOption;
        }
        public void setCancelOption(String cancelOption) {
                this.cancelOption = cancelOption;
        }
        public String getContinueOption() {
                return continueOption;
        }
        public void setContinueOption(String continueOption) {
                this.continueOption = continueOption;
        }
		public String getCopyBook() {
			return copyBook;
		}
		public void setCopyBook(String copyBook) {
			this.copyBook = copyBook;
		}
		public String getBookId() {
			return bookId;
		}
		public void setBookId(String bookId) {
			this.bookId = bookId;
		}
		public String getDisplayAuditTrailName() {
			return displayAuditTrailName;
		}
		public void setDisplayAuditTrailName(String displayAuditTrailName) {
			this.displayAuditTrailName = displayAuditTrailName;
		}
		public String getDisplayAuditTrailNameForR() {
			return displayAuditTrailNameForR;
		}
		public void setDisplayAuditTrailNameForR(String displayAuditTrailName) {
			this.displayAuditTrailNameForR = displayAuditTrailName;
		}
		public String getDisplayAuditTrailNameForE() {
			return displayAuditTrailNameForE;
		}
		public void setDisplayAuditTrailNameForE(String displayAuditTrailName) {
			this.displayAuditTrailNameForE = displayAuditTrailName;
		}
		public boolean isAdd() {
			return add;
		}
		public void setAdd(boolean add) {
			this.add = add;
		}
		public boolean isDeleteBooks() {
			return deleteBooks;
		}
		public void setDeleteBooks(boolean deleteBooks) {
			this.deleteBooks = deleteBooks;
		}
		public boolean isEditBooks() {
			return editBooks;
		}
		public void setEditBooks(boolean editBooks) {
			this.editBooks = editBooks;
		}
		public boolean isConfirmBooks() {
			return confirmBooks;
		}
		public void setConfirmBooks(boolean confirmBooks) {
			this.confirmBooks = confirmBooks;
		}
		public boolean isUnConfirmBooks() {
			return unConfirmBooks;
		}
		public void setUnConfirmBooks(boolean unConfirmBooks) {
			this.unConfirmBooks = unConfirmBooks;
		}
		public boolean isAuthorizeBooks() {
			return authorizeBooks;
		}
		public void setAuthorizeBooks(boolean authorizeBooks) {
			this.authorizeBooks = authorizeBooks;
		}
		public boolean isValidateBooks() {
			return validateBooks;
		}
		public void setValidateBooks(boolean validateBooks) {
			this.validateBooks = validateBooks;
		}
		public CollegeDeptDetails getCollegeDeptDetails() {
			return collegeDeptDetails;
		}
		public void setCollegeDeptDetails(CollegeDeptDetails collegeDeptDetails) {
			this.collegeDeptDetails = collegeDeptDetails;
		}
		public String getCollege() {
			return college;
		}
		//this method sets the college.
		public void setCollege(String college) {
			this.college = college;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
/*		public String getCollegeChair() {
			return collegeChair;
		}
		public void setCollegeChair(String collegeChair) {
			this.collegeChair = collegeChair;
		}
		public String getPersno() {
			return persno;
		}
		public void setPersno(String persno) {
			this.persno = persno;
		}*/
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
		public void setLastmodifiedforbook(String modifier){
    	    this.lastmodifiedforbook=modifier;
        }
		public String getLastmodifiedforbook() {
			return lastmodifiedforbook;
		}
		public void setLastmodifiedforRbook(String lastmodifiedforRbook) {
			this.lastmodifiedforRbook = lastmodifiedforRbook;
		}
		public String getLastmodifiedforRbook() {
			  return lastmodifiedforRbook;
		}
		public void setLastmodifiedforEbook(String lastmodifiedforEbook) {
			this.lastmodifiedforEbook = lastmodifiedforEbook;
		}
		public String getLastmodifiedforEbook() {
			  return lastmodifiedforEbook;
		}
		public String getEditOption() {
			return editOption;
		}
		public void setEditOption(String editOption) {
			this.editOption = editOption;
		}
		public String getCodInitials() {
			return codInitials;
		}
		public void setCodInitials(String codInitials) {
			this.codInitials = codInitials;
		}
		public String getCodSurname() {
			return codSurname;
		}
		public void setCodSurname(String codSurname) {
			this.codSurname = codSurname;
		}
		public String getCodNovelluserId() {
			return codNovelluserId;
		}
		public void setCodNovelluserId(String codNovelluserId) {
			this.codNovelluserId = codNovelluserId;
		}
		public String getDeptCode() {
			return deptCode;
		}
		public void setDeptCode(String deptCode) {
			this.deptCode = deptCode;
		}
		public void setStandInChairs(List standInChairs) {
			this.standInChairs = standInChairs;
		}
		public List getStandInChairs() {
			return standInChairs;
		}
		public String getStandinInitial() {
			return standinInitial;
		}
		public void setStandinInitial(String standinInitial) {
			this.standinInitial = standinInitial;
		}
		public String getStandinSurname() {
			return standinSurname;
		}
		public void setStandinSurname(String standinSurname) {
			this.standinSurname = standinSurname;
		}
		public String getStandinNovellCode() {
			return standinNovellCode;
		}
		public void setStandinNovellCode(String standinNovellCode) {
			this.standinNovellCode = standinNovellCode;
		}
		public String getSelectedPerson() {
			return selectedPerson;
		}
		public void setSelectedPerson(String selectedPerson) {
			this.selectedPerson = selectedPerson;
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
		public String getSelectedCourse() {
			return selectedCourse;
		}
		public void setSelectedCourse(String selectedCourse) {
			        	this.selectedCourse = selectedCourse;
		}
		public String getAuthComment() {
			return authComment;
		}
		public void setAuthComment(String authComment) {
			this.authComment = authComment;
		}
		public String getSchDirector() {
			return schDirector;
		}
		public void setSchDirector(String schDirector) {
			this.schDirector = schDirector;
		}
		public String getPersonnelNr() {
			return personnelNr;
		}
		public void setPersonnelNr(String personnelNr) {
			this.personnelNr = personnelNr;
		}
		public List getPresCourseList() {
			return presCourseList;
		}
		public void setPresCourseList(List presCourseList) {
			this.presCourseList = presCourseList;
		}
		public List getRecCourseList() {
			return recCourseList;
		}
		public void setRecCourseList(List recCourseList) {
			this.recCourseList = recCourseList;
		}
		public List getEresCourseList() {
			return eresCourseList;
		}
		public void setEresCourseList(List eresCourseList) {
			this.eresCourseList = eresCourseList;
		}
		public List getDeanCourseList() {
			return deanCourseList;
		}
		public void setDeanCourseList(List deanCourseList) {
			this.deanCourseList = deanCourseList;
		}
		
	 	public Object getRecordIndexed(int index) {
    		return deanCourseList.get(index);
    	}
			public Set getSchools() {
			return schools;
		}
		public void setSchools(Set schools) {
			this.schools = schools;
		}
		public Set getSchoolDetails1() {
			return schoolDetails1;
		}
		public void setSchoolDetails1(Set schoolDetails1) {
			this.schoolDetails1 = schoolDetails1;
		}
		public ArrayList getSelectedList() {
			return selectedList;
		}
		public void setSelectedList(ArrayList selectedList) {
			this.selectedList = selectedList;
		}
		public ArrayList getNonSelectedList() {
			return nonSelectedList;
		}
		public void setNonSelectedList(ArrayList nonSelectedList) {
			this.nonSelectedList = nonSelectedList;
		}
		public String getDeanNetworkId() {
			return deanNetworkId;
		}
		public void setDeanNetworkId(String deanNetworkId) {
			this.deanNetworkId = deanNetworkId;
		}
		public String getDeputydean1NetworkId() {
			return deputydean1NetworkId;
		}
		public void setDeputydean1NetworkId(String deputydean1NetworkId) {
			this.deputydean1NetworkId = deputydean1NetworkId;
		}
		public String getDeputydean2NetworkId() {
			return deputydean2NetworkId;
		}
		public void setDeputydean2NetworkId(String deputydean2NetworkId) {
			this.deputydean2NetworkId = deputydean2NetworkId;
		}
		public String getDeputydean3NetworkId() {
			return deputydean3NetworkId;
		}
		public void setDeputydean3NetworkId(String deputydean3NetworkId) {
			this.deputydean3NetworkId = deputydean3NetworkId;
		}
		public String getDeputydean4NetworkId() {
			return deputydean4NetworkId;
		}
		public void setDeputydean4NetworkId(String deputydean4NetworkId) {
			this.deputydean4NetworkId = deputydean4NetworkId;
		}
		public String getDeanSurname() {
			return deanSurname;
		}
		public void setDeanSurname(String deanSurname) {
			this.deanSurname = deanSurname;
		}
		public String getDeputydean1Surname() {
			return deputydean1Surname;
		}
		public void setDeputydean1Surname(String deputydean1Surname) {
			this.deputydean1Surname = deputydean1Surname;
		}
		public String getDeputydean2Surname() {
			return deputydean2Surname;
		}
		public void setDeputydean2Surname(String deputydean2Surname) {
			this.deputydean2Surname = deputydean2Surname;
		}
		public String getDeputydean3Surname() {
			return deputydean3Surname;
		}
		public void setDeputydean3Surname(String deputydean3Surname) {
			this.deputydean3Surname = deputydean3Surname;
		}
		public String getDeputydean4Surname() {
			return deputydean4Surname;
		}
		public void setDeputydean4Surname(String deputydean4Surname) {
			this.deputydean4Surname = deputydean4Surname;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getDeptName() {
			return deptName;
		}
		public void setDeptName(String deptName) {
			this.deptName = deptName;
		}
		public String getSchDirSurname() {
			return schDirSurname;
		}
		public void setSchDirSurname(String schDirSurname) {
			this.schDirSurname = schDirSurname;
		}
		public String getSchDirNetworkId() {
			return schDirNetworkId;
		}
		public void setSchDirNetworkId(String schDirNetworkId) {
			this.schDirNetworkId = schDirNetworkId;
		}
		
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Booklist: Get/Set Tutor Status
		public int getTutorBookListStatus() {
			return tutorBookListStatus;
		}
		public void setTutorBookListStatus( int tutorBookListStatus ){
			this.tutorBookListStatus = tutorBookListStatus;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4 & 10:Tutor Booklist: Get/Set Tutor Count
		public String getTutorCount() {
			return tutorCount;
		}
		public void setTutorCount( String tutorCount ){
			this.tutorCount = tutorCount;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Tutor Booklist: Track Update of Count
		public String getCountUpdateTracker() {
			return countUpdateTracker;
		}
		public void setCountUpdateTracker( String countUpdateTracker ){
			this.countUpdateTracker = countUpdateTracker;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10 & 14:Tutor Booklist: Names of DISS authorisers
		public List getDissAuthNames() {
			return dissAuthNames;
		}
		public void setDissAuthNames( List dissAuthNames ){
			this.dissAuthNames = dissAuthNames;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10 & 14:Tutor Booklist: Emails of DISS authorisers
		public List getDissAuthEmails() {
			return dissAuthEmails;
		}
		public void setDissAuthEmails( List dissAuthEmails ){
			this.dissAuthEmails = dissAuthEmails;
		}
		
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10 & 14:Tutor Booklist: Radio button Selection of DISS Authoriser name
		public String getSelectedDissName() {
			return selectedDissName;
		}
		public void setSelectedDissName( String selectedDissName ){
			this.selectedDissName = selectedDissName;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 14 and 54:Specify if logged in user is diss
		public String getIsDiss() {
			return isDiss;
		}
		public void setIsDiss( String isDiss ){
			this.isDiss = isDiss;
		}
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 54:Track authorization page
		public int getDissAuthTracker() {
			return dissAuthTracker;
		}
		public void setDissAuthTracker( int dissAuthTracker ){
			this.dissAuthTracker = dissAuthTracker;
		}
		
}