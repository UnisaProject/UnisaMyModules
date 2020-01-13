package za.ac.unisa.lms.tools.tracking.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import za.ac.unisa.lms.tools.tracking.bo.Consignment;

@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class TrackingForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996784762350764989L;
	private static final String version = "2016001";
	private Log log = LogFactory.getLog(TrackingForm.class.getName());
	
	
	private String unchecked;
	public String getUnchecked() {
		return unchecked;
	}
	public void setUnchecked(String unchecked) {
		this.unchecked = unchecked;
	}
	private String  novelUserId;
	private String persNo;
   	private String searchPersNo;
    private ArrayList colleges;
    private ArrayList schools;
    private ArrayList departments;
    private ArrayList modules;
    private ArrayList buildings;

    private ArrayList provinces;
    private ArrayList regions;
    private ArrayList searhResults;
    private String shipListNumber;
    private String displayShipListNumber;
    private String displayShipListDate;
    private String college;
    private String school;
    private String department;
    private String module;
    private String csdmUsers;
    private String building;    
    private String buildingUsers;
    private String user;
    private String users;
    private String province;
    private String provinceUsers;
    private String region;
    private String date;
    private boolean dsaaCheck = false;
    private boolean dispatchCheck = false ;
    private boolean shipListIncluded = false;
    private boolean singleAssignment;
    private boolean docketValidationCheck = false;
    private boolean search = false;
   	private String surname;
   	private String userAddress;
	private String userAddress1;
	private String userAddress2;
	private String userAddress3;
	private String userAddress4;
	private String userAddress5;
	private String userAddress6;
	private String userPostal;
	private String userEmail;
	//Final Result Address
	private String saveAddress1;
	private String saveAddress2;
	private String saveAddress3;
	private String saveAddress4;
	private String saveAddress5;
	private String savePostal;
	private String saveEmail;
	//Report Address
	private String displayAddress1;
	private String displayAddress2;
	private String displayAddress3;
	private String displayAddress4;
	private String displayPostal;
	private String displayEmail;
	
	private String destinationAddress;
	private String emailAddress;

	private boolean userAdd;
   	private boolean dsaaAdd;
   	private boolean dispatchAdd;
   	private boolean csdAdd;
   	
    private String userSelection;
    private boolean remove = true;
    private String bookoutErrorStatus;
    private boolean hiddenButtonBookIn;
    private boolean hiddenButtonBookOut;
    private String webErrMsg = "";
    private String searchCheck;
    
	private List<Consignment> consignment;
    private int consignmentCounter=0;
    private String docCompare = "";
    private String docID = "";
    
	//SearchPage
    private String searchString="";
    
	public String getVersion() {
		return version;
	}
    public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	private String hostName ;

	public String getUserAddress1() {
		return userAddress1;
	}
	public void setUserAddress1(String userAddress1) {
		this.userAddress1 = userAddress1;
	}
	public String getUserAddress2() {
		return userAddress2;
	}
	public void setUserAddress2(String userAddress2) {
		this.userAddress2 = userAddress2;
	}
	public String getUserAddress3() {
		return userAddress3;
	}
	public void setUserAddress3(String userAddress3) {
		this.userAddress3 = userAddress3;
	}
	public String getUserAddress4() {
		return userAddress4;
	}
	public void setUserAddress4(String userAddress4) {
		this.userAddress4 = userAddress4;
	}
	public String getUserAddress5() {
		return userAddress5;
	}
	public void setUserAddress5(String userAddress5) {
		this.userAddress5 = userAddress5;
	}
	public String getUserAddress6() {
		return userAddress6;
	}
	public void setUserAddress6(String userAddress6) {
		this.userAddress6 = userAddress6;
	}
	public String getUserPostal() {
		return userPostal;
	}
	public void setUserPostal(String userPostal) {
		this.userPostal = userPostal;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}	
	public String getSaveAddress1() {
		return saveAddress1;
	}
	public void setSaveAddress1(String saveAddress1) {
		this.saveAddress1 = saveAddress1;
	}
	public String getSaveAddress2() {
		return saveAddress2;
	}
	public void setSaveAddress2(String saveAddress2) {
		this.saveAddress2 = saveAddress2;
	}
	public String getSaveAddress3() {
		return saveAddress3;
	}
	public void setSaveAddress3(String saveAddress3) {
		this.saveAddress3 = saveAddress3;
	}
	public String getSaveAddress4() {
		return saveAddress4;
	}
	public void setSaveAddress4(String saveAddress4) {
		this.saveAddress4 = saveAddress4;
	}
	public String getSaveAddress5() {
		return saveAddress5;
	}
	public void setSaveAddress5(String saveAddress5) {
		this.saveAddress5 = saveAddress5;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	public String getSavePostal() {
		return savePostal;
	}
	public void setSavePostal(String savePostal) {
		this.savePostal = savePostal;
	}
	public String getSaveEmail() {
		return saveEmail;
	}
	public void setSaveEmail(String saveEmail) {
		this.saveEmail = saveEmail;
	}
	public String getDisplayAddress1() {
		return displayAddress1;
	}
	public void setDisplayAddress1(String displayAddress1) {
		this.displayAddress1 = displayAddress1;
	}
	public String getDisplayAddress2() {
		return displayAddress2;
	}
	public void setDisplayAddress2(String displayAddress2) {
		this.displayAddress2 = displayAddress2;
	}
	public String getDisplayAddress3() {
		return displayAddress3;
	}
	public void setDisplayAddress3(String displayAddress3) {
		this.displayAddress3 = displayAddress3;
	}
	public String getDisplayAddress4() {
		return displayAddress4;
	}
	public void setDisplayAddress4(String displayAddress4) {
		this.displayAddress4 = displayAddress4;
	}
	public String getDisplayPostal() {
		return displayPostal;
	}
	public void setDisplayPostal(String displayPostal) {
		this.displayPostal = displayPostal;
	}
	public String getDisplayEmail() {
		return displayEmail;
	}
	public void setDisplayEmail(String displayEmail) {
		this.displayEmail = displayEmail;
	}
    public boolean isUserAdd() {
		return userAdd;
	}
	public void setUserAdd(boolean userAdd) {
		this.userAdd = userAdd;
	}
	public boolean isDsaaAdd() {
		return dsaaAdd;
	}
	public void setDsaaAdd(boolean dsaaAdd) {
		this.dsaaAdd = dsaaAdd;
	}
	public boolean isDispatchAdd() {
		return dispatchAdd;
	}
	public void setDispatchAdd(boolean dispatchAdd) {
		this.dispatchAdd = dispatchAdd;
	}
	public boolean isCsdAdd() {
		return csdAdd;
	}
	public void setCsdAdd(boolean csdAdd) {
		this.csdAdd = csdAdd;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	private String addressType;
	private String searchRadio;
	private String consignmentListNumber;
	private String enteredConsignmentNumber;
    private String docketNumber;
    private String studentNumber;
    private String uniqueNumber;
    private final Map values = new HashMap();   
    private int count; 
    private String studNo;
    private String uniqueAssignmentNr ;
    private String assignStatus ;
    private ArrayList noOfRecordsOptions; 
    private ArrayList courseList;
    private int noOfRecords = 0;
    private String singleDocketNumber;
    private String value;
    private ArrayList listOfDocketNumbers = new ArrayList();
    private ArrayList listOfStudentAssignments = new ArrayList();
    
    private int expandCollapse;
    private int loopCount = 0;
    public ArrayList getListOfStudentAssignments() {
		return listOfStudentAssignments;
	}
	public void setListOfStudentAssignments(ArrayList listOfStudentAssignments) {
		this.listOfStudentAssignments = listOfStudentAssignments;
	}
	private ArrayList displayDocketsForConsignment ;
	private ArrayList displayDctAssignmentsForConsignment;
	private ArrayList expandDisplayDocketsForConsignment ;
	private ArrayList displayUniqueNumbersForConsignment;
	private ArrayList unCheckedDockets;
	private ArrayList unCheckedStudentNumbers;
	
	private ArrayList bookoutunCheckedDockets;
	private ArrayList bookoutunCheckedStudentNumbers;
	
	
    public ArrayList getBookoutunCheckedDockets() {
		return bookoutunCheckedDockets;
	}
	public void setBookoutunCheckedDockets(ArrayList bookoutunCheckedDockets) {
		this.bookoutunCheckedDockets = bookoutunCheckedDockets;
	}
	public ArrayList getBookoutunCheckedStudentNumbers() {
		return bookoutunCheckedStudentNumbers;
	}
	public void setBookoutunCheckedStudentNumbers(
			ArrayList bookoutunCheckedStudentNumbers) {
		this.bookoutunCheckedStudentNumbers = bookoutunCheckedStudentNumbers;
	}
	public ArrayList getUnCheckedStudentNumbers() {
		return unCheckedStudentNumbers;
	}
	public void setUnCheckedStudentNumbers(ArrayList unCheckedStudentNumbers) {
		this.unCheckedStudentNumbers = unCheckedStudentNumbers;
	}
	public ArrayList getUnCheckedDockets() {
		return unCheckedDockets;
	}
	public void setUnCheckedDockets(ArrayList unCheckedDockets) {
		this.unCheckedDockets = unCheckedDockets;
	}

    private String enteredCosignmentNumber ;
   
	public String getEnteredCosignmentNumber() {
		return enteredCosignmentNumber;
	}
	public void setEnteredCosignmentNumber(String enteredCosignmentNumber) {
		this.enteredCosignmentNumber = enteredCosignmentNumber;
	}
	public String getStudNo() {
		return studNo;
	}
	public void setStudNo(String studNo) {
		this.studNo = studNo;
	}
	public String getUniqueAssignmentNr() {
		return uniqueAssignmentNr;
	}
	public void setUniqueAssignmentNr(String uniqueAssignmentNr) {
		this.uniqueAssignmentNr = uniqueAssignmentNr;
	}
	public String getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(String assignStatus) {
		this.assignStatus = assignStatus;
	}
	public String getNovelUserId() {
		return novelUserId;
	}
	public void setNovelUserId(String novelUserId) {
		this.novelUserId = novelUserId.toUpperCase().trim();
	}
	public String getPersNo() {
		return persNo;
	}
	public void setPersNo(String persNo) {
		this.persNo = persNo.trim();
	}
	public String getSearchPersNo() {
		return searchPersNo;
	}
	public void setSearchPersNo(String searchPersNo) {
		this.searchPersNo = searchPersNo.trim();
	}
	public ArrayList getColleges() {
		return colleges;
	}
	public void setColleges(ArrayList colleges) {
		this.colleges = colleges;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public ArrayList getSchools() {
		return schools;
	}
	public void setSchools(ArrayList schools) {
		this.schools = schools;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public boolean isDsaaCheck() {
		return dsaaCheck;
	}
	public void setDsaaCheck(boolean dsaaCheck) {
		this.dsaaCheck = dsaaCheck;
	}
	public boolean isDispatchCheck() {
		return dispatchCheck;
	}
	public void setDispatchCheck(boolean dispatchCheck) {
		this.dispatchCheck = dispatchCheck;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public ArrayList getDepartments() {
		return departments;
	}
	public void setDepartments(ArrayList departments) {
		this.departments = departments;
	}
	public ArrayList getModules() {
		return modules;
	}
	public void setModules(ArrayList modules) {
		this.modules = modules;
	}
	public ArrayList getBuildings() {
		return buildings;
	}
	public void setBuildings(ArrayList buildings) {
		this.buildings = buildings;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getProvinceUsers() {
		return provinceUsers;
	}
	public void setProvinceUsers(String provinceUsers) {
		this.provinceUsers = provinceUsers;
	}
	public String getCsdmUsers() {
		return csdmUsers;
	}
	public void setCsdmUsers(String csdmUsers) {
		this.csdmUsers = csdmUsers;
	}
	public String getBuildingUsers() {
		return buildingUsers;
	}
	public void setBuildingUsers(String buildingUsers) {
		this.buildingUsers = buildingUsers;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getConsignmentListNumber() {
		return consignmentListNumber;
	}
	public void setConsignmentListNumber(String consignmentListNumber) {
		this.consignmentListNumber = consignmentListNumber;
	}
	public String getEnteredConsignmentNumber() {
		return enteredConsignmentNumber;
	}
	public void setEnteredConsignmentNumber(String enteredConsignmentNumber) {
		this.enteredConsignmentNumber = enteredConsignmentNumber;
	}
	public ArrayList getProvinces() {
		return provinces;
	}
	public void setProvinces(ArrayList provinces) {
		this.provinces = provinces;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public ArrayList getRegions() {
		return regions;
	}
	public void setRegions(ArrayList regions) {
		this.regions = regions;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDocketNumber() {
		return docketNumber;
	}
	public void setDocketNumber(String docketNumber) {
		this.docketNumber = docketNumber;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getUniqueNumber() {
		return uniqueNumber;
	}
	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
	public Map getValues() {
		return values;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	 public void setValue(String key, Object value)
	 {      
		 values.put(key, value);   
		 }   
	 
	 public Object getValue(String key){     
		 return values.get(key);     
		 } 
	
	 public boolean isSingleAssignment() {
			return singleAssignment;
		}
		public void setSingleAssignment(boolean singleAssignment) {
			this.singleAssignment = singleAssignment;
		}
	
		public ArrayList getNoOfRecordsOptions() {
			noOfRecordsOptions = new ArrayList();
			for (int i=0; i <= 18; i++) {
				noOfRecordsOptions.add(new org.apache.struts.util.LabelValueBean(Integer.toString(i),Integer.toString(i)));
			}
			return noOfRecordsOptions;
		}
		
		public void setNoOfRecordsOptions(ArrayList noOfRecordsOptions) {
			this.noOfRecordsOptions = noOfRecordsOptions;
		}
		public int getNoOfRecords() {
			return noOfRecords;
		}
		public void setNoOfRecords(int noOfRecords) {
			this.noOfRecords = noOfRecords;
		}
		public ArrayList getCourseList() {
			return courseList;
		}
		public void setCourseList(ArrayList courseList) {
			this.courseList = courseList;
		}
		public String getSingleDocketNumber() {
			return singleDocketNumber;
		}
		public void setSingleDocketNumber(String singleDocketNumber) {
			this.singleDocketNumber = singleDocketNumber;
		}
		public boolean isDocketValidationCheck() {
			return docketValidationCheck;
		}
		public void setDocketValidationCheck(boolean docketValidationCheck) {
			this.docketValidationCheck = docketValidationCheck;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public ArrayList getDisplayDocketsForConsignment() {
			return displayDocketsForConsignment;
		}
		public void setDisplayDocketsForConsignment(
				ArrayList displayDocketsForConsignment) {
			this.displayDocketsForConsignment = displayDocketsForConsignment;
		}	
		public ArrayList getDisplayDctAssignmentsForConsignment() {
			return displayDctAssignmentsForConsignment;
		}
		public void setDisplayDctAssignmentsForConsignment(
				ArrayList displayDctAssignmentsForConsignment) {
			this.displayDctAssignmentsForConsignment = displayDctAssignmentsForConsignment;
		}
		public boolean isShipListIncluded() {
			return shipListIncluded;
		}
		public void setShipListIncluded(boolean shipListIncluded) {
			this.shipListIncluded = shipListIncluded;
		}

		public ArrayList getListOfDocketNumbers() {
			return listOfDocketNumbers;
		}
		public void setListOfDocketNumbers(ArrayList listOfDocketNumbers) {
			this.listOfDocketNumbers = listOfDocketNumbers;
		}
		public Object getRecordIndexed(int index) {
			log.info("getRecordIndex");
			if(displayDocketsForConsignment != null && !displayDocketsForConsignment.isEmpty() && index >= 0){
			    return displayDocketsForConsignment.get(index);
			}else {
				return displayDocketsForConsignment;
			}
		}
		public Object getRecordIndexed1(int index) {
			if(displayUniqueNumbersForConsignment != null && !displayUniqueNumbersForConsignment.isEmpty() && index >= 0){
			return displayUniqueNumbersForConsignment.get(index);
			}else {
				return displayUniqueNumbersForConsignment;
			}
		}
		public ArrayList getDisplayUniqueNumbersForConsignment() {
			return displayUniqueNumbersForConsignment;
		}
		public void setDisplayUniqueNumbersForConsignment(
				ArrayList displayUniqueNumbersForConsignment) {
			this.displayUniqueNumbersForConsignment = displayUniqueNumbersForConsignment;
		}
		public ArrayList getExpandDisplayDocketsForConsignment() {
			return expandDisplayDocketsForConsignment;
		}
		public void setExpandDisplayDocketsForConsignment(
				ArrayList expandDisplayDocketsForConsignment) {
			this.expandDisplayDocketsForConsignment = expandDisplayDocketsForConsignment;
		}
		public int getExpandCollapse() {
			return expandCollapse;
		}
		public void setExpandCollapse(int expandCollapse) {
			this.expandCollapse = expandCollapse;
		}
		public String getUserSelection() {
			return userSelection;
		}
		public void setUserSelection(String userSelection) {
			this.userSelection = userSelection;
		}
		public String getHostName() {
			return hostName;
		}
		public void setHostName(String hostName) {
			this.hostName = hostName;
		}
		public String getBookoutErrorStatus() {
			return bookoutErrorStatus;
		}
		public void setBookoutErrorStatus(String bookoutErrorStatus) {
			this.bookoutErrorStatus = bookoutErrorStatus;
		}
		
		public boolean isHiddenButtonBookIn() {
			return hiddenButtonBookIn;
		}

		public void setHiddenButtonBookIn(boolean hiddenButtonBookIn) {
			this.hiddenButtonBookIn = hiddenButtonBookIn;
		}
		public boolean isHiddenButtonBookOut() {
			return hiddenButtonBookOut;
		}

		public void setHiddenButtonBookOut(boolean hiddenButtonBookOut) {
			this.hiddenButtonBookOut = hiddenButtonBookOut;
		}
		
		public String getAddressType() {
			return addressType;
		}

		public void setAddressType(String addressType) {
			this.addressType = addressType;
			//if(addressType.equalsIgnoreCase("dsaa")){
			//	setDsaaCheck(true);
			//}else if(addressType.equalsIgnoreCase("dispatch")){
			//	setDispatchCheck(true);
			//}
		}

		public String getSearchRadio() {
			return searchRadio;
		}

		public void setSearchRadio(String searchRadio) {
			this.searchRadio = searchRadio;
		}
		
		public List<Consignment> getConsignment() {
			return consignment;
		}

		public void setConsignment(List<Consignment> consignment) {
			this.consignment = consignment;
		}
	    public int getConsignmentCounter() {
			return consignmentCounter;
		}
		public void setConsignmentCounter(int consignmentCounter) {
			this.consignmentCounter = consignmentCounter;
		}
		public String getDocCompare() {
			return docCompare;
		}
		public void setDocCompare(String docCompare) {
			this.docCompare = docCompare;
		}
		public String getDocID() {
			return docID;
		}
		public void setDocID(String docID) {
			this.docID = docID;
		}
		
		public String getSearchString() {
			return searchString;
		}
		
		public void setSearchString(String searchString){
			this.searchString = searchString;
		}

		public ArrayList getSearchResults() {
			return searhResults;
		}
		public void setSearchResults(ArrayList searhResults) {
			this.searhResults = searhResults;
		}
		public String getShipListNumber() {
			return shipListNumber;
		}
		public void setShipListNumber(String shipListNumber) {
			this.shipListNumber = shipListNumber;
		}
		public String getDisplayShipListNumber() {
			return displayShipListNumber;
		}
		public void setDisplayShipListNumber(String displayShipListNumber) {
			this.displayShipListNumber = displayShipListNumber;
		}
		public String getDisplayShipListDate() {
			return displayShipListDate;
		}
		public void setDisplayShipListDate(String displayShipListDate) {
			this.displayShipListDate = displayShipListDate;
		}
		public boolean isSearch() {
			return search;
		}
		public void setSearch(boolean search) {
			this.search = search;
		}
		
	    public String getWebErrMsg() {
			return webErrMsg;
		}
		public void setWebErrMsg(String webErrMsg) {
			this.webErrMsg = webErrMsg;
		}
		public String getSearchCheck() {
			return searchCheck;
		}
		public void setSearchCheck(String searchCheck) {
			this.searchCheck = searchCheck;
		}
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		
}