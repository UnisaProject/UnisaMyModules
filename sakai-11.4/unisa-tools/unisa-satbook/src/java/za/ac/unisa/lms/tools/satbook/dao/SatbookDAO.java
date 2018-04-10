package za.ac.unisa.lms.tools.satbook.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.satbook.forms.AdminLinkForm;
import za.ac.unisa.lms.tools.satbook.forms.AssistantRecord;
import za.ac.unisa.lms.tools.satbook.forms.AssistantTypeRecord;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.ClassroomForm;
import za.ac.unisa.lms.tools.satbook.forms.InstDaysRecord;
import za.ac.unisa.lms.tools.satbook.forms.InstitutionRecord;
import za.ac.unisa.lms.tools.satbook.forms.LecturerForm;
import za.ac.unisa.lms.tools.satbook.forms.MaterialRecord;
import za.ac.unisa.lms.tools.satbook.forms.VBookingTypeForm;

public class SatbookDAO extends SakaiDAO{

	public String selectUserId(String userEID) throws Exception {
		String userId = "";
		
		String sql1 = " SELECT USER_ID "+
			          " FROM SAKAI_USER_ID_MAP" +
			          " WHERE EID = '"+userEID+"'";

		try{
			userId = this.querySingleValue(sql1,"USER_ID");	
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: selectUserId: Error occurred / "+ ex,ex);
		}
		return userId;
	}
	//select administrators linked to booking system 
	public ArrayList selectAdmin() throws Exception {

		ArrayList adminList = new ArrayList();
		
		String select="SELECT SAKAI_USER_ID_MAP.EID as USERID, NVL(SATBOOK_USERS.SYSTEM_ID,5) as SYSTEM "+ 
					  "FROM SAKAI_SITE_USER,SAKAI_USER_ID_MAP,SATBOOK_USERS "+
					  "WHERE SAKAI_SITE_USER.SITE_ID='unisa-satbook' "+
					  "AND SAKAI_SITE_USER.PERMISSION='-1' "+
					  "AND SAKAI_SITE_USER.USER_ID=SAKAI_USER_ID_MAP.USER_ID "+
		              "AND SATBOOK_USERS.USER_ID(+)=SAKAI_USER_ID_MAP.EID ";
		//String select="SELECT USER_ID as USERID ,SYSTEM_ID as SYSTEM "+ 
		  //			  "FROM SATBOOK_USERS ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			AdminLinkForm adminLinkForm = new AdminLinkForm() ;
    			
    			adminLinkForm.setAdministrator(data.get("USERID").toString());
    			adminLinkForm.setSystem(data.get("SYSTEM").toString());
    			
    			adminList.add(adminLinkForm);
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAdmin: "+ ex,ex);
    	} // end try

		return adminList;
	}
	
	//update administrator record
	public void insertAdministrator(String systemId, String userId) throws Exception{
              
			String aExist=administratorExist(userId);
			// update existing administrator record
			if (!aExist.equals("5")){	
				if (!systemId.equals("5")){
					String sql1 = "UPDATE SATBOOK_USERS " +
					              "SET    SYSTEM_ID= '"+systemId+"' "+
					              "WHERE  USER_ID = '"+userId+"' ";
		
					try{
						JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
						jdt1.update(sql1);
					} catch (Exception ex) {
						throw new Exception("SatbookDAO: insertAdministrator (EDIT:update): Error occurred / "+ ex,ex);
					}
				}
			}
			// 	insert new administrator if record does not exist
			else{
				
				// systemId=5 means user is not linked to a system
				if (!systemId.equals("5")){
					String sql1 = "INSERT INTO SATBOOK_USERS(USER_ID,SYSTEM_ID) "+
									"VALUES ('"+userId+"',"+Integer.parseInt(systemId)+")";
					try{
						JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
						jdt1.update(sql1);
					} catch (Exception ex) {
						throw new Exception("SatbookDAO: insertAdministrator (Insert): Error occurred / "+ ex,ex);
					}
				}
		}
	}

	
	//Check if administrator exist in the table SATBOOK_USERS
	public String administratorExist(String userid) throws Exception {
		//value 5 is for default linked to no system
		String aExist="5";
		
		String sql1 = "SELECT NVL(SYSTEM_ID,5) as SYSTEM_ID "+
		              "FROM   SATBOOK_USERS "+
		              "WHERE  USER_ID = '"+userid+"' ";

		try{
			aExist = this.querySingleValue(sql1,"SYSTEM_ID");
			if (aExist.equals("")) {
				aExist="5";
			}

		} catch (Exception ex) {
            throw new Exception("SatbookDAO: administratorExist: Error occurred / "+ ex,ex);
		}

		return aExist;
	}

	
	//Display different values of systems in a drop down list: value"5" is passed for "Select System" because all systems must have an id
	public ArrayList selectSystemList() throws Exception {
		ArrayList systemList = new ArrayList();
		systemList.add(new org.apache.struts.util.LabelValueBean("Select System ", "5"));
		
		String select = "SELECT SYSTEM_ID, SYSTEM_NAME "+
        			    "FROM   SATBOOK_SYSTEMS "+
        			    "WHERE    SYSTEM_ID != 1";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
			
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String tmpSysId = data.get("SYSTEM_ID").toString();
    			String tmpSysName = data.get("SYSTEM_NAME").toString();

    			systemList.add(new org.apache.struts.util.LabelValueBean(tmpSysName, tmpSysId));
    			

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectSystemList: Error occurred / "+ ex,ex);
    	} // end try

		return systemList;
	}
	

	//Retrieve booking type information
	public VBookingTypeForm selectBookingType(String typeId,String SystemId) throws Exception {

		VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();


		String select = "SELECT TYPE_NAME "+
        			    "FROM   SATBOOK_BKNG_TYPES "+
        			    "WHERE  TYPE_ID = '"+typeId+"'"+
        			    "AND SYSTEM_ID= '"+SystemId+"'";

			
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			vbookingTypeForm.setBookingTypeId(typeId);    			 			
    			vbookingTypeForm.setBookingTypeDesc(data.get("TYPE_NAME").toString());
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectMaterial: Error occurred / "+ ex,ex);
    	} // end try

		return vbookingTypeForm;
	}

	//Display list of  booking types for specific system
	public ArrayList selectBkngTypeList(String systemID,String typeArrayList) throws Exception {
		ArrayList bkngTypeList = new ArrayList();
		
		// typeArrayList: "label"=labelvaluebean{usually used for dropdown list}; "object" - VBookingTypeForm
		if (typeArrayList.equals("label")) {
			bkngTypeList.add(new org.apache.struts.util.LabelValueBean("Select Booking Type ", ".."));
		}
		
		String select = "SELECT TYPE_ID, TYPE_NAME ,ACTIVE "+
        			    "FROM   SATBOOK_BKNG_TYPES "+
        			    "WHERE  SYSTEM_ID = "+systemID ;
						
		if (typeArrayList.equals("label")) {
			select = select +" AND   NVL(ACTIVE,'Y') = 'Y'";
		}
		select = select +" ORDER BY TYPE_NAME ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List tmpList = jdt.queryForList(select);
			Iterator j = tmpList.iterator();
			
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			if (typeArrayList.equals("label")) {
	    			String tmpId = data.get("TYPE_ID").toString();
	    			String tmpName = data.get("TYPE_NAME").toString();
	    			String tmpActive = data.get("ACTIVE").toString();
	
	    			bkngTypeList.add(new org.apache.struts.util.LabelValueBean(tmpName, tmpId));
    			} else {
    				VBookingTypeForm vbookingTypeForm = new VBookingTypeForm();
    				
    				vbookingTypeForm.setBookingTypeId(data.get("TYPE_ID").toString());
    				vbookingTypeForm.setBookingTypeDesc(data.get("TYPE_NAME").toString());
    				if (data.get("ACTIVE").toString().equalsIgnoreCase("Y")){
    					vbookingTypeForm.setBookingTypeAct(true);
    				}
    				else{
    					vbookingTypeForm.setBookingTypeAct(false);
    				}
    				bkngTypeList.add(vbookingTypeForm);
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectBkngTypeList: Error occurred / "+ ex,ex);
    	} // end try

		return bkngTypeList;
	}
	/**
	 * Check for duplicate booking types
	*/
	public boolean bookingTypeExist(String systemId ,String bookingTypeDesc) throws Exception {
		String noOfRecords;
		boolean bookingTypeExist = false;

		String sql1 = "SELECT COUNT(*) AS COUNTER FROM SATBOOK_BKNG_TYPES " +
					  "WHERE SYSTEM_ID= '"+systemId+"'" +
					  "AND TYPE_NAME = '"+bookingTypeDesc+"'" ;

		try{
			noOfRecords = this.querySingleValue(sql1,"COUNTER");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: bookingTypeExist (SELECT): Error occurred / "+ ex,ex);
		}
		//String noOfRecords converted to integer
		if (Integer.parseInt(noOfRecords) >=1){
			bookingTypeExist = true;
		} else {
			bookingTypeExist = false;
		}

		return bookingTypeExist;
	}
	
	/**
	 * Check if booking type is in use/used
	*/
	public boolean bkngTypeUsed(String typeId) throws Exception {
		String BtypeUsed;
		boolean bkngTypeUsed = false;

		String sql1 = "SELECT COUNT(BKNGTYPE_ID) AS BOOKTYPE_USED "+
						"FROM   SATBOOK_BKNG_MAIN "+
						"WHERE  BKNGTYPE_ID = "+typeId+" ";

		try{
			BtypeUsed = this.querySingleValue(sql1,"BOOKTYPE_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: bkngTypeUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int typeUsedInt = Integer.parseInt(BtypeUsed);

		if (typeUsedInt > 0) {
			bkngTypeUsed = true;
		} else {
			bkngTypeUsed = false;
		}

		return bkngTypeUsed;
	}
	
	/**
	 * 	Delete booking type)-and update indicator to "N" when in use
	 */
	
	public void deleteBkngType(String typeId, boolean bkngTypeUsed , String systemId ) throws Exception{

		if (bkngTypeUsed == true) {
			// change/set the active indicator to "N" which means bkngtype cannot be deleted
			String sql1 = " UPDATE SATBOOK_BKNG_TYPES" +
			  			  " SET ACTIVE = 'N'"+
			  			  " WHERE  TYPE_ID = "+typeId +
			  			  " AND SYSTEM_ID= "+systemId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: deleteBkngType (set): Error occurred / "+ ex,ex);
			}
		} else {
			// delete booking type
			String sql1 = " DELETE FROM SATBOOK_BKNG_TYPES " +
					      " WHERE  TYPE_ID = "+typeId +
					      " AND SYSTEM_ID= "+systemId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: deleteBkngType (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	// insert new booking type
	public void insertBookingType(VBookingTypeForm vbookingTypeForm, String actionStatus ,String systemId) throws Exception{
		
		boolean bookingTypeExist = false;
		
		if (actionStatus.equals("EDIT")) {
			bookingTypeExist = true;
		}
		
		String bookingTypeAct = "Y";
		if (vbookingTypeForm.getBookingTypeAct() == false) {
			bookingTypeAct = "N";
		} else {
			bookingTypeAct = "Y";
		}
	
			if (actionStatus.equals("ADD")) {
			String typeId = "";
			// insert next key for typeId
			String sql1 = "SELECT NVL(max(TYPE_ID)+1,1) as A "+
						  "FROM   SATBOOK_BKNG_TYPES ";

			try{
				typeId = this.querySingleValue(sql1,"A");

			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: saveBookingHeading (SELECT unique bkng_id): Error occurred / "+ ex,ex);
			}
			// INSERT BOOKING TYPE
			String sql3 = "INSERT INTO SATBOOK_BKNG_TYPES(TYPE_ID,TYPE_NAME,ACTIVE,SYSTEM_ID) " +
					"VALUES ("+typeId+",'"+vbookingTypeForm.getBookingTypeDesc()+"','"+bookingTypeAct+"','"+systemId+"')";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertBookingType (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			bookingTypeExist = true;
			// update existing bkng type
			String sql4 = "UPDATE SATBOOK_BKNG_TYPES " +
					"SET TYPE_NAME = '"+vbookingTypeForm.getBookingTypeDesc()+"', " +
					"    ACTIVE = '"+bookingTypeAct+"' "+
					"WHERE TYPE_ID = "+vbookingTypeForm.getBookingTypeId()+" "+
					"AND SYSTEM_ID= '"+systemId+"'";
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertBookingType (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	}

	public ArrayList selectLecturers() throws Exception {
		ArrayList lecturerList = new ArrayList();
		SatbookOracleDAO db = new SatbookOracleDAO();

		String select = "SELECT NOVELL_ID, CONTACT, NVL(CELLNO,' ') AS CELLNO "+
		                "FROM SATBOOK_LECTURERS "+
		                "ORDER BY NOVELL_ID ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			LecturerForm lecturer = new LecturerForm();
    			lecturer.setNovellId(data.get("NOVELL_ID").toString());
    			lecturer.setTelNumber(data.get("CONTACT").toString());
    			lecturer.setCellNumber(data.get("CELLNO").toString());

    			String lectExist = db.lecturerExistStaff(lecturer.getNovellId());
    			if ((lectExist != null)&&(lectExist.length() > 0)){
    				db.selectLecturerDetail(lecturer);
    			} else {
    				lecturer.setContact1(" ");
    				lecturer.setContact2(" ");
    				lecturer.setEmail(" ");
    				lecturer.setName("Not Found");
    				lecturer.setPersNumber(" ");
    			}

    			lecturerList.add(lecturer);

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectLecturers: Error occurred / "+ ex,ex);
    	} // end try

		return lecturerList;
	}

	public void insertLecturer(String novellId, String telNumber, String cellNum, String actionStatus) throws Exception{

		if (telNumber == null) {
			telNumber = " ";
		}
		if (cellNum == null) {
			cellNum = " ";
		}

		novellId = novellId.toUpperCase();
		if (actionStatus.equals("EDIT")) {
			// update existing Lecturer record
			String sql1 = "UPDATE SATBOOK_LECTURERS " +
			              "SET    CONTACT= '"+telNumber+"', "+
			              "       CELLNO = '"+cellNum+"' "+
			              "WHERE  NOVELL_ID = '"+novellId+"' ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertLecturer (EDIT:update): Error occurred / "+ ex,ex);
			}

		} else {
			// 	insert new lecturer
			String sql1 = "INSERT INTO SATBOOK_LECTURERS(NOVELL_ID,CONTACT,CELLNO)"+
							"VALUES (?,?,?)";
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[] {novellId,telNumber,cellNum});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertLecturer (Insert): Error occurred / "+ ex,ex);
			}
		}
	}

	public void deleteLecturer(String novellId) throws Exception{

		novellId = novellId.toUpperCase();
		String sql1 = "DELETE FROM SATBOOK_LECTURERS " +
					  "WHERE NOVELL_ID = '"+novellId+"'";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteLecturer (Delete): Error occurred /"+ ex,ex);
		}
	}

	public LecturerForm selectLecturer(String novellId) {

		LecturerForm lecturer = new LecturerForm();

		String select = "SELECT NOVELL_ID, CONTACT, NVL(CELLNO,' ') as CELLNO "+
        			    "FROM   SATBOOK_LECTURERS "+
        			    "WHERE  NOVELL_ID = '"+novellId+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			lecturer.setNovellId(data.get("NOVELL_ID").toString());
    			lecturer.setTelNumber(data.get("CONTACT").toString());
    			lecturer.setCellNumber(data.get("CELLNO").toString());

    		} // end while

    	} catch (Exception ex) {
    		//throw new Exception("calendarDateStudentSystemQueryDAO: getExamInformation: (stno: "+studentNr+
    		//		") Error occurred: "+ ex,ex);
    	} // end try

		return lecturer;
	}

	public boolean lecturerExist(String novellId) throws Exception {
		boolean lecturerExist = false;
		String lExist;
		// does record already exist lecturer

		novellId = novellId.toUpperCase();
		String sql1 = "SELECT NOVELL_ID "+
		              "FROM   SATBOOK_LECTURERS "+
		              "WHERE  NOVELL_ID = '"+novellId+"' ";

		try{
			lExist = this.querySingleValue(sql1,"NOVELL_ID");

		} catch (Exception ex) {
            throw new Exception("SatbookDAO: lecturerExist: Error occurred / "+ ex,ex);
		}

		if (lExist.length() > 0) {
			lecturerExist = true;
		} else {
			lecturerExist = false;
		}

		return lecturerExist;
	}

	public boolean institutionExist(String instName) throws Exception {
		boolean instExist = false;
		String iExist;

		String sql1 = "SELECT INST_ID "+
		              "FROM   SATBOOK_INSTITUTIONS "+
		              "WHERE  INST_NAME = '"+instName+"' ";

		try{
			iExist = this.querySingleValue(sql1,"INST_ID");

		} catch (Exception ex) {
            throw new Exception("SatbookDAO: institutionExist: Error occurred / "+ ex,ex);
		}

		if (iExist.length() > 0) {
			instExist = true;
		} else {
			instExist = false;
		}

		return instExist;
	}

	public void insertInstitution(String instId, String instName, String actionStatus) throws Exception{

		if (actionStatus.equals("EDIT")) {
			// update existing Lecturer record
			String sql1 = "UPDATE SATBOOK_INSTITUTIONS " +
			              "SET    INST_NAME= '"+instName+"' "+
			              "WHERE  INST_ID = "+instId+" ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertInstitution (EDIT:update): Error occurred / "+ ex,ex);
			}

		} else {
			// 	insert new lecturer
			String sql1 = "INSERT INTO SATBOOK_INSTITUTIONS(INST_ID,INST_NAME)"+
							"VALUES (SATBOOK_INSTITUTIONS_0.NEXTVAL,?)";
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[] {instName});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertInstitution (Insert): Error occurred / "+ ex,ex);
			}
		}
	}

	public InstitutionRecord selectInstitution(String instId) throws Exception {

		InstitutionRecord inst = new InstitutionRecord();

		String select = "SELECT INST_ID, INST_NAME "+
        			    "FROM   SATBOOK_INSTITUTIONS "+
        			    "WHERE  INST_ID = '"+instId+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			inst.setInstId(data.get("INST_ID").toString());
    			inst.setInstName(data.get("INST_NAME").toString());

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectInstitution (Insert): Error occurred / "+ ex,ex);
    	} // end try

		return inst;
	}

	public String[] selectDaysPerInstitution(String instId, String forYear, String forMonth) throws Exception {

		String[] instDays = new String[31];
		int counter = 0;
		String yearMonth = "";
		if (forMonth.length() == 1) {
			yearMonth = forYear+"-0"+forMonth;
		} else {
			yearMonth = forYear+"-"+forMonth;
		}

		String select = "SELECT TO_CHAR(BROADCAST_DATE,'DD') AS DAY " +
				        "FROM   SATBOOK_INST_DAYS "+
				        "WHERE  INST_ID = "+instId+" "+
				        "AND    TO_CHAR(BROADCAST_DATE,'YYYY-MM') = '"+yearMonth+"' "+
				        "ORDER  BY BROADCAST_DATE";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			instDays[counter] = data.get("DAY").toString();
    			counter++;
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectDaysPerInstitution (Insert): Error occurred / "+ ex,ex);
    	} // end try

		return instDays;
	}


	public void deleteInstitution(String instId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_INSTITUTIONS " +
					  "WHERE  INST_ID = '"+instId+"'";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteInstitution (Delete): Error occurred /"+ ex,ex);
		}
	}



	public ArrayList selectInstitutions(boolean objectList) throws Exception {
		ArrayList instList = new ArrayList();

		String select = "SELECT INST_ID, INST_NAME "+
        			    "FROM   SATBOOK_INSTITUTIONS ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
			if (objectList == false) {
				instList.add(new org.apache.struts.util.LabelValueBean("Select Institution.. ", "0"));
			}
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String tmpInstId = data.get("INST_ID").toString();
    			String tmpInstName = data.get("INST_NAME").toString();

    			if (objectList == true) {
    				InstitutionRecord inst = new InstitutionRecord();
    				inst.setInstId(tmpInstId);
    				inst.setInstName(tmpInstName);

    				instList.add(inst);
    			} else {
    				instList.add(new org.apache.struts.util.LabelValueBean(tmpInstName, tmpInstId));
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectInstitutions: Error occurred / "+ ex,ex);
    	} // end try

		return instList;
	}

	public ArrayList selectInstitutionDays(String selectedYear, String selectedMonth, String systemID) throws Exception {
		ArrayList instDaysList = new ArrayList();

		if (Integer.parseInt(selectedMonth) < 10) {
			selectedMonth = "0"+selectedMonth;
		}

		GregorianCalendar calCurrent = new GregorianCalendar();
		int currentDay = calCurrent.get(Calendar.DAY_OF_MONTH);
		int currentMonth = calCurrent.get(Calendar.MONTH) + 1;
		int currentYear = calCurrent.get(Calendar.YEAR);

		String currentDayStr = "";
		String currentMonthStr = "";
		if (currentDay <= 9) {
			currentDayStr = "0"+currentDay;
		}else {
			currentDayStr = Integer.toString(currentDay);
		}
		if (currentMonth <= 9) {
			currentMonthStr = "0"+currentMonth;
		}else {
			currentMonthStr = Integer.toString(currentMonth);
		}

		String currentDate = currentDayStr+"-"+currentMonthStr+"-"+currentYear;

		GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth) -1, 01);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int i=1; i <= count; i++) {

			// create new instance of day
			InstDaysRecord instDaysRecord = new InstDaysRecord();

			String select = "SELECT SATBOOK_INST_DAYS.INST_ID AS instId, SATBOOK_INSTITUTIONS.INST_NAME AS instName, " +
				        "TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'DD-MM-YYYY') AS instDate,TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'DAY') AS D "+
        			 	"FROM   SATBOOK_INST_DAYS, SATBOOK_INSTITUTIONS "+
        			 	"WHERE  TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'YYYY') = "+selectedYear+" "+
        			 	"AND    TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'MM') = "+selectedMonth+" "+
        			 	"AND    TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'DD') = "+i+" "+
        			 	"AND    SATBOOK_INST_DAYS.INST_ID = SATBOOK_INSTITUTIONS.INST_ID "+
        			 	"ORDER BY SATBOOK_INST_DAYS.BROADCAST_DATE";

			try{
				JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
				List examInfoList = jdt.queryForList(select);
				Iterator j = examInfoList.iterator();
				int tmpCounter = 0;
				String tmpWeekday = "";
				while (j.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) j.next();
					tmpCounter = tmpCounter+1;

					instDaysRecord.setInstId(data.get("instId").toString());
					instDaysRecord.setInstDesc(data.get("instName").toString());
					instDaysRecord.setInstDate(data.get("instDate").toString());
					instDaysRecord.setInstWeekday(data.get("D").toString());

				} // end while

				String tmpDay = "";
				if (i < 10) {
					tmpDay = "0"+i;
				} else {
					tmpDay = Integer.toString(i);
				}

				// if date does not exist in table SATBOOK_INST_DAYS add to list so that it can be updated/inserted
				if (tmpCounter == 0) {
					GregorianCalendar cal2 = new GregorianCalendar(Integer.parseInt(selectedYear), Integer.parseInt(selectedMonth) -1, i);
					SimpleDateFormat sdf = new SimpleDateFormat("EEE");
					if(sdf.format(cal2.getTime()).equalsIgnoreCase("Sun")) {
						tmpWeekday = "SUNDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Mon")) {
						tmpWeekday = "MONDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Tue")) {
						tmpWeekday = "TUESDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Wed")) {
						tmpWeekday = "WEDNESDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Thu")) {
						tmpWeekday = "THURSDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Fri")) {
						tmpWeekday = "FRIDAY";
					} else if (sdf.format(cal2.getTime()).equalsIgnoreCase("Sat")) {
						tmpWeekday = "SATURDAY";
					}

					instDaysRecord.setInstDesc(" ");
					instDaysRecord.setInstDate(tmpDay+"-"+selectedMonth+"-"+selectedYear);
					instDaysRecord.setInstWeekday(tmpWeekday);
				}


				if (Integer.parseInt(selectedYear) < currentYear) {
					instDaysRecord.setInstUpdateable(false);
				} else if ((Integer.parseInt(selectedYear) == currentYear)&&(Integer.parseInt(selectedMonth) < Integer.parseInt(currentMonthStr))) {
					instDaysRecord.setInstUpdateable(false);
				} else if ((Integer.parseInt(selectedYear) == currentYear)&&
						(Integer.parseInt(selectedMonth) == Integer.parseInt(currentMonthStr))&&
						(Integer.parseInt(tmpDay) <= Integer.parseInt(currentDayStr))) {
					instDaysRecord.setInstUpdateable(false);
				} else {
					instDaysRecord.setInstUpdateable(true);
				}

				// check if a booking was already placed on the date then day may not be updated.
				if (instDaysRecord.getInstUpdateable() == true) {
					boolean bookingExist = bookingExistOnDay(tmpDay+"-"+selectedMonth+"-"+selectedYear,systemID);
					if (bookingExist == true) {
						instDaysRecord.setInstUpdateable(false);
					} else {
						instDaysRecord.setInstUpdateable(true);
					}
				}

				instDaysList.add(instDaysRecord);

			} catch (Exception ex) {
				throw new Exception("SatbookDAO: selectInstitutionDays: Error occurred / "+ ex,ex);
			} // end try

		}

		return instDaysList;
	}

	public void insertInstitutionDays(ArrayList instDaysList) throws Exception{
		/**
		 * 1. Check if day already exist in database
		 * 2. if exist update the record
		 * 3. if not exist insert the record
		 */

		String dExist;
		boolean dayExist = false;

		for (int i=0; i< instDaysList.size(); i++) {

			InstDaysRecord instDayRecord1 = (InstDaysRecord) instDaysList.get(i);

			if ((instDayRecord1.getInstId() != null)) {

				String sql1 = "SELECT INST_ID "+
								"FROM   SATBOOK_INST_DAYS "+
								"WHERE  TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'DD-MM-YYYY') = '"+instDayRecord1.getInstDate()+"' ";

				try{
					dExist = this.querySingleValue(sql1,"INST_ID");

				} catch (Exception ex) {
					throw new Exception("SatbookDAO: insertInstitutionDays (SELECT): Error occurred / "+ ex,ex);
				}

				if (dExist.length() > 0) {
					dayExist = true;
				} else {
					dayExist = false;
				}

				if (dayExist == true) {
					// update record
					String sql2 = "UPDATE SATBOOK_INST_DAYS " +
					"SET    INST_ID= "+instDayRecord1.getInstId()+" "+
					"WHERE  TO_CHAR(BROADCAST_DATE,'DD-MM-YYYY') = '"+instDayRecord1.getInstDate()+"' ";


					try{
						JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
						jdt1.update(sql2);
					} catch (Exception ex) {
						throw new Exception("SatbookDAO: insertInstitutionDays (UPDATE): Error occurred / "+ ex,ex);
					}

				} else {
					//insert record
					String [] tmpdateArray =  instDayRecord1.getInstDate().split("-");
					String tmpDate = tmpdateArray[2] +"-"+tmpdateArray[1]+"-"+tmpdateArray[0];

					String sql3 = "INSERT INTO SATBOOK_INST_DAYS(INST_ID,BROADCAST_DATE) "+
								"VALUES ("+instDayRecord1.getInstId()+" ,TO_DATE('"+tmpDate+"','YYYY-MM-DD'))";

					try{
						JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
						jdt1.update(sql3,new Object[] {});
					} catch (Exception ex) {
						throw new Exception("SatbookDAO: insertInstitutionDays (INSERT): Error occurred / "+ ex,ex);
					}
				} // end insert
			} //if (!(instDayRecord1.getInstId().equalsIgnoreCase("0"))) {

		} //for (int i=0; i< instDaysList.size(); i++) {
	} //insertInstitutionDays

	/**
	 * This method will test if any bookings was placed on a certain date.
	 *
	 * @param bookingDate   	The query to be executed on the database
	 * @param bookingExist		The field that is queried on the database
	*/
	public boolean bookingExistOnDay(String bookingDate, String systemID) throws Exception {
		boolean bookingExist = false;
		String noOfRecords;

		String sql1 = "SELECT COUNT(*) as NoOfRecords "+
		              "FROM   SATBOOK_BKNG_MAIN "+
		              "WHERE  (TO_CHAR(BKNG_START,'DD-MM-YYYY') = '"+bookingDate+"' "+
		              "OR     TO_CHAR(BKNG_END,'DD-MM-YYYY') = '"+bookingDate+"') " +
		              "AND    SYSTEM_ID = "+systemID;

		try{
			noOfRecords = this.querySingleValue(sql1,"NoOfRecords");

		} catch (Exception ex) {
            throw new Exception("SatbookDAO: bookingExistOnDay: Error occurred / "+ ex,ex);
		}

		if (Integer.parseInt(noOfRecords) > 0) {
			bookingExist = true;
		} else {
			bookingExist = false;
		}

		return bookingExist;
	}

	/**
	 * select of all the classrooms
	 * @return arraylist of classrooms
	 * @throws Exception
	 */
	public ArrayList selectClassrooms(boolean objectList, String systemId) throws Exception {
		ArrayList classroomList = new ArrayList();

		String select = "SELECT CLASSROOM_ID, CLASSROOM_NAME,CLASSROOM_CONTACT,CLASSROOM_CONTACT1," +
		                "NVL(CLASSROOM_CONTACT2,' ') as CLASSROOM_CONTACT2,CLASSROOM_ACTIVE, "+
		                "NVL(CLASSROOM_ADR1,' ')AS A1,NVL(CLASSROOM_ADR2,' ') AS A2, "+
		                "NVL(CLASSROOM_ADR3,' ') AS A3,NVL(CLASSROOM_ADR4,' ') AS A4,CLASSROOM_PCODE "+
		                "FROM   SATBOOK_CLASSROOMS " +
		                "WHERE  SYSTEM_ID = "+systemId;
		if (objectList == false) {
			select = select + " and NVL(CLASSROOM_ACTIVE,'Y')='Y'";
		}
		
		select = select +" ORDER BY CLASSROOM_NAME ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classrList = jdt.queryForList(select);
			Iterator j = classrList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			if (objectList == false) {
     				// select region description.
    				SatbookOracleDAO db1 = new SatbookOracleDAO();
    				//String rgnDesc = db1.selectRegionDesc(data.get("REGION_CODE").toString());

    				classroomList.add(new org.apache.struts.util.LabelValueBean(data.get("CLASSROOM_ID").toString(),data.get("CLASSROOM_NAME").toString()));

    			} else {
    				ClassroomForm classroom = new ClassroomForm();
    				classroom.setRegionCode(data.get("CLASSROOM_ID").toString());
    				classroom.setRegionDesc(data.get("CLASSROOM_NAME").toString());
    				classroom.setContactPerson(data.get("CLASSROOM_CONTACT").toString());
    				classroom.setContactNum1(data.get("CLASSROOM_CONTACT1").toString());
    				classroom.setContactNum2(data.get("CLASSROOM_CONTACT2").toString());
    				String rgnActive = data.get("CLASSROOM_ACTIVE").toString();
    				if (rgnActive.equals("N")) {
    					classroom.setRegionActive(false);
    				} else {
    					classroom.setRegionActive(true);
    				}

    				classroom.setRegionAddress1(data.get("A1").toString());
    				classroom.setRegionAddress2(data.get("A2").toString());
    				classroom.setRegionAddress3(data.get("A3").toString());
    				classroom.setRegionAddress4(data.get("A4").toString());
        			if ((null == data.get("CLASSROOM_PCODE").toString())||(data.get("CLASSROOM_PCODE").toString().length() == 0)) {
        				classroom.setRegionAddressPcode(" ");
        			} else {
        				classroom.setRegionAddressPcode(data.get("CLASSROOM_PCODE").toString());
        			}

    				classroomList.add(classroom);
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectClassrooms: Error occurred / "+ ex,ex);
    	} // end try

		return classroomList;
	}
	//select of active venues
	/*
	 * desc satbook_classrooms
Name                           Null     Type                                                                                                                                                                                          
------------------------------ -------- --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- 
CLASSROOM_ID                   NOT NULL NUMBER(11)                                                                                                                                                                                    
CLASSROOM_NAME                 NOT NULL VARCHAR2(50)                                                                                                                                                                                  
CLASSROOM_CONTACT                       VARCHAR2(50)                                                                                                                                                                                  
CLASSROOM_CONTACT1                      VARCHAR2(30)                                                                                                                                                                                  
CLASSROOM_CONTACT2                      VARCHAR2(30)                                                                                                                                                                                  
CLASSROOM_ADR1                          VARCHAR2(28)                                                                                                                                                                                  
CLASSROOM_ADR2                          VARCHAR2(28)                                                                                                                                                                                  
CLASSROOM_ADR3                          VARCHAR2(28)                                                                                                                                                                                  
CLASSROOM_ADR4                          VARCHAR2(28)                                                                                                                                                                                  
CLASSROOM_PCODE                         VARCHAR2(4)                                                                                                                                                                                   
CLASSROOM_ACTIVE                        VARCHAR2(1)                                                                                                                                                                                   
SYSTEM_ID                      NOT NULL NUMBER(2) 
	 */
	public ArrayList selectVenues(String SystemId) throws Exception {
		ArrayList classroomList = new ArrayList();
		
		
		String select="SELECT CLASSROOM_ID, CLASSROOM_NAME, CLASSROOM_CONTACT, NVL(CLASSROOM_CONTACT1,' ') AS CLASSROOM_CONTACT1, " +
						"NVL(CLASSROOM_CONTACT2,' ') as CLASSROOM_CONTACT2, "+
						" NVL(CLASSROOM_ADR1,' ') AS CLASSROOM_ADR1, NVL(CLASSROOM_ADR2,' ') AS CLASSROOM_ADR2, " +
						" NVL(CLASSROOM_ADR3,' ') AS CLASSROOM_ADR3, NVL(CLASSROOM_ADR4,' ') AS CLASSROOM_ADR4, " +
						" NVL(CLASSROOM_PCODE,' ') AS CLASSROOM_PCODE, CLASSROOM_ACTIVE, SYSTEM_ID "+
						" FROM SATBOOK_CLASSROOMS " +
						" WHERE SYSTEM_ID='"+SystemId+"' ";
//						" AND CLASSROOM_ACTIVE='Y'";
	
		
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			
    			ClassroomForm classroom = new ClassroomForm();

    			classroom.setRegionCode(data.get("CLASSROOM_ID").toString());
    			classroom.setRegionDesc(data.get("CLASSROOM_NAME").toString());
    			classroom.setContactPerson(data.get("CLASSROOM_CONTACT").toString());
    			classroom.setContactNum1(data.get("CLASSROOM_CONTACT1").toString());
    			classroom.setContactNum2(data.get("CLASSROOM_CONTACT2").toString());
    			classroom.setRegionAddress1(data.get("CLASSROOM_ADR1").toString());
    			classroom.setRegionAddress2(data.get("CLASSROOM_ADR2").toString());
    			classroom.setRegionAddress3(data.get("CLASSROOM_ADR3").toString());
    			classroom.setRegionAddress4(data.get("CLASSROOM_ADR4").toString());
    			classroom.setRegionAddressPcode(data.get("CLASSROOM_PCODE").toString());
    		    String rgnActive = data.get("CLASSROOM_ACTIVE").toString();
    		    if (rgnActive.equals("Y")) {
    		    	classroom.setRegionActive(true);
    		    } 
    		    classroomList.add(classroom);
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectVenues: Error occurred / "+ ex,ex);
    	} // end try

		return classroomList;
	}

	/**
	 * This method to be used for Venbook. Contact 2 is email address for contact p
	 * person at venbook
	 * @param SystemId
	 * @return
	 * @throws Exception
	 */
	public String selectVenuesContact2(String SystemId, String venCode) throws Exception {
		String email = "";
		
		String select="SELECT NVL(CLASSROOM_CONTACT2,' ') as CLASSROOM_CONTACT2 "+
						" FROM SATBOOK_CLASSROOMS " +
						" WHERE SYSTEM_ID='"+SystemId+"' " +
						" AND CLASSROOM_ID = "+venCode;
		
		try{
			email = this.querySingleValue(select,"CLASSROOM_CONTACT2");

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectVenuesContact2: Error occurred / "+ ex,ex);
    	} // end try

		return email;
	}
	
	/**
	 * Test if classroom already exist for region.
	*/
	public boolean classroomExist(String regionDesc) throws Exception {
		String cExist;
		boolean classroomExist = false;

		String sql1 = "SELECT CLASSROOM_ID "+
						"FROM   SATBOOK_CLASSROOMS "+
						"WHERE  CLASSROOM_NAME like ('%"+regionDesc+"%') ";

		try{
			cExist = this.querySingleValue(sql1,"CLASSROOM_ID");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: classroomExist (SELECT): Error occurred / "+ ex,ex);
		}

		if (cExist.length() > 0) {
			classroomExist = true;
		} else {
			classroomExist = false;
		}

		return classroomExist;
	}

	/**
	 * Check for duplicate Venues
	*/
	public boolean venueExist(String systemId ,String regionDesc) throws Exception {
		String noOfRecords;
		boolean venueExist = false;

		String sql1 = "SELECT COUNT(*) AS COUNTER FROM SATBOOK_CLASSROOMS " +
					  "WHERE SYSTEM_ID= '"+systemId+"'" +
					  "AND CLASSROOM_NAME = '"+regionDesc+"'" ;

		try{
			noOfRecords = this.querySingleValue(sql1,"COUNTER");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: venueExist (SELECT): Error occurred / "+ ex,ex);
		}
		//String noOfRecords converted to integer
		if (Integer.parseInt(noOfRecords) >=1){
			venueExist = true;
		} else {
			venueExist = false;
		}

		return venueExist;
	}
	
	/**
	 * Test if venue(s) are in use/used
	*/
	public boolean venueUsed(String regionCode) throws Exception {
		String vUsed;
		boolean venueUsed = false;

		String sql1 = "SELECT COUNT(REGION_ID) AS VENUE_USED "+
						"FROM   SATBOOK_BKNG_CLASSROOM "+
						"WHERE  REGION_ID = "+regionCode+" ";

		try{
			vUsed = this.querySingleValue(sql1,"VENUE_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: venueUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int vUsedInt = Integer.parseInt(vUsed);

		if (vUsedInt > 0) {
			venueUsed = true;
		} else {
			venueUsed = false;
		}

		return venueUsed;
	}
	/**
	 * Delete venue(s)-and update indicator to "N" when in use
	 */
	
	public void deleteVenue(String classroomId, boolean venUsed , String systemId ) throws Exception{

		if (venUsed == true) {
			// change/set the active indicator to "N"
			String sql1 = " UPDATE SATBOOK_CLASSROOMS" +
			  " SET    CLASSROOM_ACTIVE = 'N'"+
			  " WHERE  CLASSROOM_ID = "+classroomId +
			  " AND SYSTEM_ID= "+systemId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: deleteVenue (set): Error occurred / "+ ex,ex);
			}
		} else {
			// remove venue
			String sql1 = " DELETE FROM SATBOOK_CLASSROOMS " +
			  " WHERE  CLASSROOM_ID = "+classroomId;
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: deleteVenue (Delete): Error occurred /"+ ex,ex);
			}
		}

	}
	
	
	/**
	 * Test if classroom was used in future broadcast booking
	*/
	public boolean classroomUsed(String regionCode) throws Exception {
		String cUsed;
		boolean classroomUsed = false;

		String sql1 = "SELECT COUNT(REGION_ID) AS COUNTER_USED "+
						"FROM   SATBOOK_BKNG_CLASSROOM "+
						"WHERE  REGION_ID = '"+regionCode+"' ";

		try{
			cUsed = this.querySingleValue(sql1,"COUNTER_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: classroomUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int cUsedInt = Integer.parseInt(cUsed);

		if (cUsedInt > 0) {
			classroomUsed = true;
		} else {
			classroomUsed = false;
		}

		return classroomUsed;
	}

	public void insertClassroom(ClassroomForm classroom, String actionStatus, String systemId) throws Exception{
		/**
		 * 1. Check if classroom was already linked to a region
		 * 2. if exist give error message
		 * 3. if not exist insert the record
		 */

		boolean classroomExist = false;

		if (actionStatus.equals("EDIT")) {
			classroomExist = true;
		}

		String tmpClassroomActive = "Y";
		if (classroom.getRegionActive() == false) {
			tmpClassroomActive = "N";
		} else {
			tmpClassroomActive = "Y";
		}

		if (classroomExist == false) {
			// INSERT CLASSROOM
			String sql3 = "INSERT INTO SATBOOK_CLASSROOMS(CLASSROOM_ID,CLASSROOM_NAME,CLASSROOM_CONTACT," +
					"CLASSROOM_CONTACT1, CLASSROOM_CONTACT2, CLASSROOM_ACTIVE," +
					"CLASSROOM_ADR1, CLASSROOM_ADR2, CLASSROOM_ADR3, CLASSROOM_ADR4, CLASSROOM_PCODE,SYSTEM_ID) "+
					"VALUES (SATBOOK_CLASSROOMS_0.NEXTVAL,'"+classroom.getRegionDesc()+"' ,'"+classroom.getContactPerson()+"', '"+
					classroom.getContactNum1() +"', '"+classroom.getContactNum2()+"', '"+ tmpClassroomActive+"'," +
				    "'"+classroom.getRegionAddress1()+"','"+classroom.getRegionAddress2()+"',"+
				    "'"+classroom.getRegionAddress3()+"','"+classroom.getRegionAddress4()+"',"+
				    "'"+classroom.getRegionAddressPcode()+"','"+systemId+"')";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertClassroom (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			// update classroom
			String sql4 = "UPDATE SATBOOK_CLASSROOMS " +
					"SET CLASSROOM_NAME = '"+classroom.getRegionDesc()+"', " +
					"    CLASSROOM_CONTACT = '"+classroom.getContactPerson()+"', " +
					"    CLASSROOM_CONTACT1 = '"+classroom.getContactNum1()+"', " +
					"    CLASSROOM_CONTACT2 = '"+classroom.getContactNum2()+"', " +
					"    CLASSROOM_ACTIVE = '"+tmpClassroomActive+"', " +
					"    CLASSROOM_ADR1 = '"+classroom.getRegionAddress1()+"', "+
					"    CLASSROOM_ADR2 = '"+classroom.getRegionAddress2()+"', "+
					"    CLASSROOM_ADR3 = '"+classroom.getRegionAddress3()+"', "+
					"    CLASSROOM_ADR4 = '"+classroom.getRegionAddress4()+"', "+
					"    CLASSROOM_PCODE = '"+classroom.getRegionAddressPcode()+"' "+
					"WHERE CLASSROOM_ID = '"+classroom.getRegionCode()+"' "+
					"AND SYSTEM_ID= '"+systemId+"'";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertClassroom (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	} 
//detail of a classroom
	 public ClassroomForm selectClassroom(String classroomId) throws Exception {

		 ClassroomForm classroom = new ClassroomForm();
		

		String select = "SELECT CLASSROOM_ID, CLASSROOM_NAME, CLASSROOM_CONTACT, CLASSROOM_CONTACT1, "+
						"       NVL(CLASSROOM_CONTACT2,' ') AS CLASSROOM_CONTACT2, CLASSROOM_ACTIVE," +
					    "       NVL(CLASSROOM_ADR1,' ') AS A1, NVL(CLASSROOM_ADR2,' ') AS A2, "+
					    "       NVL(CLASSROOM_ADR3,' ') AS A3, NVL(CLASSROOM_ADR4,' ') AS A4, "+
					    "       CLASSROOM_PCODE "+
        			    "FROM   SATBOOK_CLASSROOMS "+
        			    "WHERE  CLASSROOM_ID = '"+classroomId+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			classroom.setRegionCode(data.get("CLASSROOM_ID").toString());
    			classroom.setRegionDesc(data.get("CLASSROOM_NAME").toString());
    			classroom.setContactPerson(data.get("CLASSROOM_CONTACT").toString());
    			classroom.setContactNum1(data.get("CLASSROOM_CONTACT1").toString());
    			classroom.setContactNum2(data.get("CLASSROOM_CONTACT2").toString());
    			classroom.setRegionAddress1(data.get("A1").toString());
    			classroom.setRegionAddress2(data.get("A2").toString());
    			classroom.setRegionAddress3(data.get("A3").toString());
    			classroom.setRegionAddress4(data.get("A4").toString());
    			classroom.setRegionAddressPcode(data.get("CLASSROOM_PCODE").toString());
    		    String rgnActive = data.get("CLASSROOM_ACTIVE").toString();
    		    if (rgnActive.equals("N")) {
    		    	classroom.setRegionActive(false);
    		    } else {
    		    	classroom.setRegionActive(true);
    		    }
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectClassroom: Error occurred / "+ ex,ex);
    	} // end try

		return classroom;
	}

	public String selectClassroomDesc(String regionCode) throws Exception {
		String regionDesc = "";

		String select = "SELECT CLASSROOM_NAME "+
		                "FROM   SATBOOK_CLASSROOMS "+
		                "WHERE  CLASSROOM_ID = '"+regionCode+"' ";

  		try{
    		regionDesc = this.querySingleValue(select,"CLASSROOM_NAME");

     	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectClassroomDesc: Error occurred / "+ ex,ex);
    	} // end try

		return regionDesc;
	}

	public void deleteClassroom(String regionCode) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_CLASSROOMS " +
					  "WHERE CLASSROOM_ID = '"+regionCode+"'";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteClassroom (Delete): Error occurred /"+ ex,ex);
		}
	}

	/**
	 * Test if material already exist
	*/
	public boolean materialExist(String materialDesc) throws Exception {
		String mExist;
		boolean materialExist = false;

		String sql1 = "SELECT MATERIAL_ID "+
						"FROM   SATBOOK_MATERIALS "+
						"WHERE  UPPER(MATERIAL_NAME) = UPPER('"+materialDesc+"') ";

		try{
			mExist = this.querySingleValue(sql1,"MATERIAL_ID");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: materialExist (SELECT): Error occurred / "+ ex,ex);
		}

		if (mExist.length() > 0) {
			materialExist = true;
		} else {
			materialExist = false;
		}

		return materialExist;
	}

	public void insertMaterial(MaterialRecord material, String actionStatus, String systemId) throws Exception{

		String tmpMaterialAct;
		if (material.getMaterialAct()==true) {
			tmpMaterialAct = "Y";
		} else {
			tmpMaterialAct = "N";
		}

		if (actionStatus.equals("ADD")) {
			// INSERT MATERIAL
			String sql3 = "INSERT INTO SATBOOK_MATERIALS(MATERIAL_ID,MATERIAL_NAME,MATERIAL_ACT,SYSTEM_ID) " +
					"VALUES (SATBOOK_MATERIALS_0.NEXTVAL,UPPER('"+material.getMaterialDesc()+"'),'"+tmpMaterialAct+"','"+systemId+"' )";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertMaterial (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			// update MATERIAL
			String sql4 = "UPDATE SATBOOK_MATERIALS " +
					"SET MATERIAL_NAME = UPPER('"+material.getMaterialDesc()+"'), " +
					"    MATERIAL_ACT = '"+tmpMaterialAct+"' "+
					"WHERE MATERIAL_ID = "+material.getMaterialId()+" " +
					"AND   SYSTEM_ID = "+systemId;

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertMaterial (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	}

	/**
	 * select of all the materials
	 * @return arraylist of materials
	 * @throws Exception
	 */
	public ArrayList selectMaterialList(boolean objectList,String systemId) throws Exception {
		ArrayList materialList = new ArrayList();

		String select = "SELECT MATERIAL_ID, MATERIAL_NAME, MATERIAL_ACT "+
		                " FROM  SATBOOK_MATERIALS "+
		                " WHERE SYSTEM_ID = "+systemId+
		                " ORDER BY MATERIAL_NAME ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List matList = jdt.queryForList(select);
			Iterator j = matList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			if (objectList == false) {
    				select = select +" AND   NVL(ACTIVE,'Y') = 'Y'";
    				materialList.add(new org.apache.struts.util.LabelValueBean(data.get("MATERIAL_ID").toString(),data.get("MATERIAL_NAME").toString()));

    			} else {
    				MaterialRecord material = new MaterialRecord();
    				material.setMaterialId(data.get("MATERIAL_ID").toString());
    				material.setMaterialDesc(data.get("MATERIAL_NAME").toString());
    				if (data.get("MATERIAL_ACT").toString().equals("Y")) {
    					material.setMaterialAct(true);
    				} else {
    					material.setMaterialAct(false);
    				}

    				materialList.add(material);
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectMaterialList: Error occurred / "+ ex,ex);
    	} // end try

		return materialList;
	}

	public MaterialRecord selectMaterial(String materialId) throws Exception {

		MaterialRecord material = new MaterialRecord();


		String select = "SELECT MATERIAL_NAME, MATERIAL_ACT "+
        			    "FROM   SATBOOK_MATERIALS "+
        			    "WHERE  MATERIAL_ID = '"+materialId+"'";

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			//material.setMaterialId(materialId);
    			material.setMaterialId(materialId);
    			material.setMaterialDesc(data.get("MATERIAL_NAME").toString());
    			if (data.get("MATERIAL_ACT").toString().equals("Y")) {
    				material.setMaterialAct(true);
    			} else {
    				material.setMaterialAct(false);
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectMaterial: Error occurred / "+ ex,ex);
    	} // end try

		return material;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
	private String querySingleValue(String query, String field){

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}

	/**
	 * Test if material was used in a broadcast
	*/
	public boolean materialUsed(String materialId) throws Exception {
		String mUsed;
		boolean materialUsed = false;

		String sql1 = "SELECT COUNT(MAT_ID) AS MAT_USED "+
						"FROM   SATBOOK_BKNG_MATERIAL "+
						"WHERE  MAT_ID = "+materialId+" ";

		try{
			mUsed = this.querySingleValue(sql1,"MAT_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: materialUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int mUsedInt = Integer.parseInt(mUsed);

		if (mUsedInt > 0) {
			materialUsed = true;
		} else {
			materialUsed = false;
		}

		return materialUsed;
	}

	public void deleteMaterial(String materialId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_MATERIALS " +
					  "WHERE MATERIAL_ID = "+materialId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteMaterial (Delete): Error occurred /"+ ex,ex);
		}
	}

	/**
	 * select of all the assistant types
	 * @return arraylist of assistant types
	 * @throws Exception
	 */
	public ArrayList selectAssistantTypeList(boolean objectList, String systemId) throws Exception {
		
		ArrayList assTypeList = new ArrayList();

		//if (objectList == false) {
		//	assTypeList.add(new org.apache.struts.util.LabelValueBean("..", "0"));
		//}

		String select = " SELECT ATYPE_ID, ATYPE_NAME, ATYPE_ACT "+
		                " FROM   SATBOOK_ASSISTANT_TYPES " +
		                " WHERE  SYSTEM_ID = "+systemId+
		                " ORDER BY ATYPE_NAME ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List atList = jdt.queryForList(select);
			Iterator j = atList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			String tmpId = data.get("ATYPE_ID").toString();
    			String tmpName = data.get("ATYPE_NAME").toString();
    			String tmpAct = data.get("ATYPE_ACT").toString();

    			if (objectList == true) {
    				AssistantTypeRecord assType = new AssistantTypeRecord();
    				assType.setAssTypeId(tmpId);
    				assType.setAssTypeName(tmpName);
    				if (tmpAct.toString().equals("Y")) {
    					assType.setAssTypeAct(true);
    				} else {
    					assType.setAssTypeAct(false);
    				}
    				assTypeList.add(assType);
    			} else {
    				if (tmpAct.toString().equals("Y")) {
    					assTypeList.add(new org.apache.struts.util.LabelValueBean(tmpName, tmpId));
    				}
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAssistantTypeList: Error occurred / "+ ex,ex);
    	} // end try

		return assTypeList;
	}

	/**
	 * Test if assistant type already exist
	*/
	public boolean assTypeExist(String assTypeName) throws Exception {
		String atExist;
		boolean assTypeExist = false;

		String sql1 = "SELECT ATYPE_ID "+
						"FROM   SATBOOK_ASSISTANT_TYPES "+
						"WHERE  UPPER(ATYPE_NAME) = UPPER('"+assTypeName+"') ";

		try{
			atExist = this.querySingleValue(sql1,"ATYPE_ID");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: assTypeExist (SELECT): Error occurred / "+ ex,ex);
		}

		if (atExist.length() > 0) {
			assTypeExist = true;
		} else {
			assTypeExist = false;
		}

		return assTypeExist;
	}

	public void insertAssistantType(AssistantTypeRecord assType, String actionStatus) throws Exception{

		String tmpAssTypeAct;
		if (assType.getAssTypeAct()==true) {
			tmpAssTypeAct = "Y";
		} else {
			tmpAssTypeAct = "N";
		}

		if (actionStatus.equals("ADD")) {
			// INSERT ASSISTANT TYPE
			String sql3 = "INSERT INTO SATBOOK_ASSISTANT_TYPES(ATYPE_ID,ATYPE_NAME,ATYPE_ACT) " +
					"VALUES (SATBOOK_ASSISTANT_TYPES_0.NEXTVAL,UPPER('"+assType.getAssTypeName()+"'),'"+tmpAssTypeAct+"' )";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertAssistantType (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			// update MATERIAL
			String sql4 = "UPDATE SATBOOK_ASSISTANT_TYPES " +
					"SET ATYPE_NAME = UPPER('"+assType.getAssTypeName()+"'), " +
					"    ATYPE_ACT = '"+tmpAssTypeAct+"' "+
					"WHERE ATYPE_ID = "+assType.getAssTypeId()+" ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertAssistantType (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	}

	public AssistantTypeRecord selectAssistantType(String assTypeId) throws Exception {

		AssistantTypeRecord assType = new AssistantTypeRecord();

		String select = "SELECT ATYPE_ID,ATYPE_NAME, ATYPE_ACT "+
        			    "FROM   SATBOOK_ASSISTANT_TYPES "+
        			    "WHERE  ATYPE_ID = '"+assTypeId+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assTypeList = jdt.queryForList(select);
			Iterator j = assTypeList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			assType.setAssTypeId(assTypeId);
    			assType.setAssTypeName(data.get("ATYPE_NAME").toString());
    			if (data.get("ATYPE_ACT").toString().equals("Y")) {
    				assType.setAssTypeAct(true);
    			} else {
    				assType.setAssTypeAct(false);
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAssistantType: Error occurred / "+ ex,ex);
    	} // end try

		return assType;
	}

	/**
	 * Test if assistant type was linked to an assistant
	*/
	public boolean assTypeUsed(String materialId) throws Exception {
		String aUsed;
		boolean assTypeUsed = false;

		String sql1 = "SELECT COUNT(ASS_ID) AS ASSTYPE_USED "+
						"FROM   SATBOOK_ASSISTANTS "+
						"WHERE  ASS_TYPE_ID = "+materialId+" ";

		try{
			aUsed = this.querySingleValue(sql1,"ASSTYPE_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: assTypeUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int aUsedInt = Integer.parseInt(aUsed);

		if (aUsedInt > 0) {
			assTypeUsed = true;
		} else {
			assTypeUsed = false;
		}

		return assTypeUsed;
	}

	public void deleteAssistantType(String assTypeId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_ASSISTANT_TYPES " +
					  "WHERE ATYPE_ID = "+assTypeId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteAssistantType (Delete): Error occurred /"+ ex,ex);
		}
	}

	/**
	 * select of all the assistants
	 * @return arraylist of assistants
	 * @throws Exception
	 */
	public ArrayList selectAssistantList(String assType, boolean objectList) throws Exception {
		ArrayList assistantList = new ArrayList();

		String select;
		if ((assType == null)||(assType.equals("0"))) {
			select = "SELECT ASS_ID, ASS_TYPE_ID, ASS_NAME, ASS_EMAIL, ATYPE_NAME "+
					"FROM   SATBOOK_ASSISTANTS, SATBOOK_ASSISTANT_TYPES "+
					"WHERE  SATBOOK_ASSISTANTS.ASS_TYPE_ID = SATBOOK_ASSISTANT_TYPES.ATYPE_ID "+
					"ORDER BY SATBOOK_ASSISTANTS.ASS_NAME ";

		} else {
			select = "SELECT ASS_ID, ASS_TYPE_ID, ASS_NAME, ASS_EMAIL, ATYPE_NAME "+
					"FROM   SATBOOK_ASSISTANTS, SATBOOK_ASSISTANT_TYPES "+
					"WHERE  SATBOOK_ASSISTANTS.ASS_TYPE_ID = SATBOOK_ASSISTANT_TYPES.ATYPE_ID "+
					"AND    SATBOOK_ASSISTANTS.ASS_TYPE_ID = "+assType+" "+
					"ORDER BY SATBOOK_ASSISTANTS.ASS_NAME ";

		}

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assList = jdt.queryForList(select);
			Iterator j = assList.iterator();
			if (objectList == false) {
				assistantList.add(new org.apache.struts.util.LabelValueBean("0",".."));
			}
			while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			if (objectList == true) {

    				AssistantRecord assistant = new AssistantRecord();
    				assistant.setAssistantId(data.get("ASS_ID").toString());
    				assistant.setAssistantTypeId(data.get("ASS_TYPE_ID").toString());
    				assistant.setAssistantName(data.get("ASS_NAME").toString());
    				assistant.setAssistantEmail(data.get("ASS_EMAIL").toString());
    				assistant.setAssistantTypeDesc(data.get("ATYPE_NAME").toString());

    				assistantList.add(assistant);
    			} else {
    				assistantList.add(new org.apache.struts.util.LabelValueBean(data.get("ASS_ID").toString(),data.get("ASS_NAME").toString()));
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAssistantList: Error occurred / "+ ex,ex);
    	} // end try

		return assistantList;
	}

	/**
	 * Test if assistant already exist
	*/
	public boolean assistantExist(String assTypeId, String assName) throws Exception {
		String aExist;
		boolean assExist = false;

		String sql1 = "SELECT ASS_ID "+
						"FROM   SATBOOK_ASSISTANTS "+
						"WHERE  ASS_TYPE_ID = "+assTypeId+" "+
						"AND    UPPER(ASS_NAME) = UPPER('"+assName+"')";

		try{
			aExist = this.querySingleValue(sql1,"ASS_ID");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: assistantExist (SELECT): Error occurred / "+ ex,ex);
		}

		if (aExist.length() > 0) {
			assExist = true;
		} else {
			assExist = false;
		}

		return assExist;
	}

	public void insertAssistant(AssistantRecord assistant, String actionStatus) throws Exception{

		if (actionStatus.equals("ADD")) {
			// INSERT ASSISTANT TYPE
			String sql3 = "INSERT INTO SATBOOK_ASSISTANTS(ASS_ID,ASS_NAME,ASS_TYPE_ID,ASS_EMAIL) " +
					"VALUES (SATBOOK_ASSISTANTS_0.NEXTVAL,'"+assistant.getAssistantName()+"',"+assistant.getAssistantTypeId()+
							", '"+assistant.getAssistantEmail()+"')";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql3,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertAssistant (INSERT): Error occurred / "+ ex,ex);
			}
		} else {
			// update MATERIAL
			String sql4 = "UPDATE SATBOOK_ASSISTANTS " +
					"SET ASS_NAME = '"+assistant.getAssistantName()+"', " +
					"    ASS_TYPE_ID = "+assistant.getAssistantTypeId()+", "+
					"    ASS_EMAIL = '"+assistant.getAssistantEmail()+"' "+
					"WHERE ASS_ID = "+assistant.getAssistantId()+" ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql4,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: insertAssistant (UPDATE): Error occurred / "+ ex,ex);
			}
		}
	}

	public AssistantRecord selectAssistant(String assistantId) throws Exception {

		AssistantRecord assistant = new AssistantRecord();

		String select = "SELECT ASS_ID, ASS_TYPE_ID, ASS_NAME, ASS_EMAIL, ATYPE_NAME "+
						"FROM   SATBOOK_ASSISTANTS, SATBOOK_ASSISTANT_TYPES "+
						"WHERE  SATBOOK_ASSISTANTS.ASS_ID = "+assistantId+" "+
						"AND    SATBOOK_ASSISTANTS.ASS_TYPE_ID = SATBOOK_ASSISTANT_TYPES.ATYPE_ID ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assTypeList = jdt.queryForList(select);
			Iterator j = assTypeList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

       			assistant.setAssistantId(data.get("ASS_ID").toString());
    			assistant.setAssistantTypeId(data.get("ASS_TYPE_ID").toString());
    			assistant.setAssistantName(data.get("ASS_NAME").toString());
    			assistant.setAssistantEmail(data.get("ASS_EMAIL").toString());
    			assistant.setAssistantTypeDesc(data.get("ATYPE_NAME").toString());

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAssistant: Error occurred / "+ ex,ex);
    	} // end try

		return assistant;
	}

	/**
	 * Test if assistant was linked to an assistant
	*/
	public boolean assistantUsed(String assId) throws Exception {
		String aUsed;
		boolean assUsed = false;

		String sql1 = "SELECT COUNT(BKNG_ID) AS ASS_USED "+
						"FROM   SATBOOK_BKNG_ASSISTANT A, SATBOOK_BKNG_MAIN B"+
						"WHERE  A.ASSISTANT_ID = "+assId+" "+
						"AND    A.BKNG_ID = B.BKNG_ID "+
						"AND    B.BKNG_START >= curdate()";

		try{
			aUsed = this.querySingleValue(sql1,"ASS_USED");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: assistantUsed (SELECT): Error occurred / "+ ex,ex);
		}
		int aUsedInt = Integer.parseInt(aUsed);

		if (aUsedInt > 0) {
			assUsed = true;
		} else {
			assUsed = false;
		}

		return assUsed;
	}

	public void deleteAssistant(String assId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_ASSISTANTS " +
					  "WHERE  ASS_ID = "+assId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: deleteAssistant (Delete): Error occurred /"+ ex,ex);
		}
	}

	/**
	 * Select user rights (maintain (-1) or access only (1))
	*/
	public String getUserRights(String userId) throws Exception {
		String userTmp;
		String userPermission;

		String sql1 = "SELECT PERMISSION "+
			          "FROM   SAKAI_SITE_USER "+
			          "WHERE  SITE_ID = 'unisa-satbook' "+
			          "AND    USER_ID = '"+userId+"'";

		try{
			userTmp = this.querySingleValue(sql1,"PERMISSION");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: getUserRights: Error occurred / "+ ex,ex);
		}

		if (userTmp.equals("-1")) {
			userPermission = "MAINTAIN";
		} else {
			userPermission = "ACCESS";
		}

		return userPermission;
	}

	/**
	 * Select user rights (maintain (-1) or access only (1))
	*/
	public String mayBookingBeUpdated(String bookingId) throws Exception {
		String tmp;
		String mayUpdate;

		String sql1 = "SELECT BKNG_ID "+
			          "FROM   SATBOOK_BKNG_MAIN "+
			          "WHERE  BKNG_ID = "+bookingId+" "+
			          "AND    (TO_CHAR(BKNG_START,'YYYY-MM-DD') <= TO_CHAR(SYSDATE,'YYYY-MM-DD') "+
			          "OR     BKNG_CONFIRMED = 'Y') ";

		try{
			tmp = this.querySingleValue(sql1,"BKNG_ID");

		} catch (Exception ex) {
			throw new Exception("SatbookDAO: mayBookingBeUpdated: Error occurred / "+ ex,ex);
		}

		if ((tmp == null)||(tmp.equals(""))) {
			mayUpdate = "TRUE";
		} else {
			mayUpdate = "FALSE";
		}
		/*
		if ((tmp == null)||(tmp.equals(""))) {
			mayUpdate = "FALSE";
		} else {
			mayUpdate = "TRUE";
		}
		*/

		return mayUpdate;
	}

	/* Audit trail */
	public void insertEvent(String systemId) throws Exception{
		// INSERT MATERIAL
		String sql3 = "INSERT INTO SATBOOK_EVENT(EVENT_ID,EVENT_DATE,EVENT,USER,EVENT_IP,BKNG_ID,DESCRIPTION,SYSTEM_ID) " +
				"VALUES (SATBOOK_MATERIALS_0.NEXTVAL,UPPER('"+systemId+"' )";
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql3,new Object[] {});
		} catch (Exception ex) {
			throw new Exception("SatbookDAO: insertEvent (audit trail) (INSERT): Error occurred / "+ ex,ex);
		}
	}
	
	public String selectUsers(String systemId) throws Exception {

		AssistantRecord assistant = new AssistantRecord();

		String select = "SELECT ASS_ID, ASS_TYPE_ID, ASS_NAME, ASS_EMAIL, ATYPE_NAME "+
						"FROM   SATBOOK_ASSISTANTS, SATBOOK_ASSISTANT_TYPES "+
						"WHERE  SATBOOK_ASSISTANTS.ASS_ID = "+systemId+" "+
						"AND    SATBOOK_ASSISTANTS.ASS_TYPE_ID = SATBOOK_ASSISTANT_TYPES.ATYPE_ID ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assTypeList = jdt.queryForList(select);
			Iterator j = assTypeList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

       			assistant.setAssistantId(data.get("ASS_ID").toString());
    			assistant.setAssistantTypeId(data.get("ASS_TYPE_ID").toString());
    			assistant.setAssistantName(data.get("ASS_NAME").toString());
    			assistant.setAssistantEmail(data.get("ASS_EMAIL").toString());
    			assistant.setAssistantTypeDesc(data.get("ATYPE_NAME").toString());

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookDAO: selectAssistant: Error occurred / "+ ex,ex);
    	} // end try

		return "";
	}
}
