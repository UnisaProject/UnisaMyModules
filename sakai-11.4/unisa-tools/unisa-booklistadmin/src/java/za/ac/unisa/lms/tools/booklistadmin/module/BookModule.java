package za.ac.unisa.lms.tools.booklistadmin.module;

public class BookModule extends EreserveBookModule{
	 private boolean remove;
	 private String networkID;
	 private String engCount;
	 private String afrCount;
	 private String afrSem1Count;
	 private String afrSem2Count;
	 private String afrYearCount;
	 private String engSem1Count;
	 private String engSem2Count;
	 private String engYearCount;
	 private String availableAsEbook;
	 private  String eReserveType;
	 
	 
	 
	 public  void seteReserveType(String eReserveType){
		 this.eReserveType=eReserveType;
	 }
	 public String geteReserveType(){
		 return eReserveType;
	 }
		public String getAvailableAsEbook() {
				return availableAsEbook;
		}
		public void setAvailableAsEbook(String availableAsEbook) {
				this.availableAsEbook = availableAsEbook;
		}
		
	public String getAfrSem1Count() {
		return afrSem1Count;
	}
	public void setAfrSem1Count(String afrSem1Count) {
		this.afrSem1Count = afrSem1Count;
	}
	public String getAfrSem2Count() {
		return afrSem2Count;
	}
	public void setAfrSem2Count(String afrSem2Count) {
		this.afrSem2Count = afrSem2Count;
	}
	public String getAfrYearCount() {
		return afrYearCount;
	}
	public void setAfrYearCount(String afrYearCount) {
		this.afrYearCount = afrYearCount;
	}
	public String getEngSem1Count() {
		return engSem1Count;
	}
	public void setEngSem1Count(String engSem1Count) {
		this.engSem1Count = engSem1Count;
	}
	public String getEngSem2Count() {
		return engSem2Count;
	}
	public void setEngSem2Count(String engSem2Count) {
		this.engSem2Count = engSem2Count;
	}
	public String getEngYearCount() {
		return engYearCount;
	}
	public void setEngYearCount(String engYearCount) {
		this.engYearCount = engYearCount;
	}
	
	 public String getEngCount() {
		return engCount;
	}
	public void setEngCount(String engCount) {
		this.engCount = engCount;
	}
	public String getAfrCount() {
		return afrCount;
	}
	public void setAfrCount(String afrCount) {
		this.afrCount = afrCount;
	}
	
	public String getUpdterNetworkID() {
		return networkID;
	}
	public void setUpdterNetworkID(String networkID) {
		this.networkID = networkID;
	}
	public boolean isRemove() {
		  return remove;
	}
	public void setRemove(boolean remove) {
		  this.remove = remove;
	}
	public void setConfirmStatus(String confirmStatus){
		//**
	}
	

}
