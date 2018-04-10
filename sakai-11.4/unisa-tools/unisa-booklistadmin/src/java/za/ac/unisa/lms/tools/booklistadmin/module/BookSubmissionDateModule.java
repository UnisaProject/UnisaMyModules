package za.ac.unisa.lms.tools.booklistadmin.module;

public class BookSubmissionDateModule {
    	private String  academicYear="2000";
	    private String  openingDate="12-01-2009";
	    private String  closingDate="12-01-2009";
	    private String  level="1";
	    private String  activeStatus="";
	    private boolean  remove=false;
	    private String releaseDate="";
	    public String getReleaseDate(){
	    	return releaseDate;
	    }
	    public void setReleaseDate(String releaseDate){
	    	 this.releaseDate=releaseDate;
	    }
	    public String getAcademicYear(){
		         return  academicYear;
	    }
	    public void setAcademicYear(String academicYear){
		         this.academicYear=academicYear;
	    }
	    public String getOpeningDate(){
		        return openingDate;
    	}
	    public void setOpeningDate(String openingDate){
		        this.openingDate=openingDate;
	    }
	    public String getClosingDate() {
		       return closingDate;
	    }
	    public void setClosingDate(String closingDate) {
		       this.closingDate = closingDate;
    	}
	    public String getLevel() {
		        return level;
	    }
	    public void setLevel(String academicLevel) {
		       this.level=academicLevel;
	    
	    }
	    public void  setActiveStatus(String activeStatus){
	    	     this.activeStatus=activeStatus;
	    }
	    public String  getActiveStatus(){
    	            return activeStatus;
        }
	    public boolean isRemove() {
			  return remove;
		}
		public void setRemove(boolean remove) {
			  this.remove = remove;
		}
}
