package za.ac.unisa.lms.tools.maintainstaff.DAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.maintainstaff.forms.DepartmentRecordForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.DeptTypeListForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.MainForm;
import za.ac.unisa.lms.tools.maintainstaff.forms.StaffListForm;

public class HodQueryDao extends StudentSystemDAO {
 
	/** Select arraylist of departments */
	public ArrayList selectDepartmentList()throws Exception{
		ArrayList deptList = new ArrayList();
		//StudentSystemDAO db = new  StudentSystemDAO();
		
		 Log log = LogFactory.getLog(this.getClass());
		 
		 String select= "SELECT Code, Eng_Description FROM DPT WHERE IN_USE_FLAG = 'Y' ORDER BY Eng_Description" ;

	    	try{
	    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	    		List atList = jdt.queryForList(select);
				Iterator j = atList.iterator();
				
				while (j.hasNext()) {
	    			
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			String tmpCode = data.get("Code").toString();
	     			String tmpName = data.get("Eng_Description").toString();	
	     			
	    				DeptTypeListForm detList=new DeptTypeListForm();
	    				detList.setCode(tmpCode);
	    				
	    				detList.setEngdesc(tmpName);	    				
	    			//deptList.add(detList); 		
	    			deptList.add(new org.apache.struts.util.LabelValueBean(tmpName, tmpCode));
	    		
	    			
	    		}//end while
	    	} catch (Exception ex) {
	    		throw new Exception("HodQueryDao: selectDepartmentList: Error occurred / "+ ex,ex);
	    	} // end try
		return deptList;
	}
	
	
	/** Select list of all the standins linked to a deparment */
	public ArrayList selectDepartmentTypeList(String deptType, boolean objectList) throws Exception {
		ArrayList departmentOrderList = new ArrayList();

		String select;
		
			select = "select NOVELL_USER_CODE,PERSONNEL_NO,MK_DEPARTMENT_CODE,TITLE,INITIALS,SURNAME FROM STAFF, USRDPT where PERSONNEL_NO=PERSNO and MK_DEPARTMENT_CODE="+deptType;

		

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assList = jdt.queryForList(select);
			Iterator j = assList.iterator();
			if (objectList == false) {
				departmentOrderList.add(new org.apache.struts.util.LabelValueBean("0",".."));
			}
			while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			if (objectList == true) {

    				DepartmentRecordForm department = new DepartmentRecordForm();
    				
    				department.setUserCode(data.get("NOVELL_USER_CODE").toString());    				
    				department.setPnumber(data.get("PERSONNEL_NO").toString());    				
    				department.setSurName(data.get("SURNAME").toString());
    				department.setInitials(data.get("INITIALS").toString());
    				department.setTitle(data.get("TITLE").toString());
	     			
    				departmentOrderList.add(department);
    			
    			} else {
    				departmentOrderList.add(new org.apache.struts.util.LabelValueBean(data.get("NOVELL_USER_CODE").toString(),data.get("PERSONNEL_NO").toString()));
    			}

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("HodQueryDao: selectDepartmentTypeList: Error occurred / "+ ex,ex);
    	} // end try

		return departmentOrderList;
	}
	
	/** Select staff details using staff number */
	public String selectStaffDetails(String pNumber)throws Exception{
	    ArrayList staffDetails = new ArrayList();
		String tmpPersNo="";
		String select;
		 Log log = LogFactory.getLog(this.getClass());
		select="select PERSNO from staff where PERSNO="+pNumber;

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List atList = jdt.queryForList(select);
			Iterator j = atList.iterator();
			
			while (j.hasNext()) {				
				
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			tmpPersNo = data.get("PERSNO").toString();
       			StaffListForm staff=new StaffListForm();
    				
    				staff.setPersNo(tmpPersNo);    				
       				staffDetails.add(staff);
    		
			}//end while
			}catch (Exception ex) {
	    		throw new Exception("HodQueryDao: selectStaffDetails: Error occurred / "+ ex,ex);
	    	} // end try
		return tmpPersNo;
		
	}
	
	/** Select staff details using network id */
	public String selectStaffDetailsUserCode(String novellUserCode)throws Exception{
	    ArrayList staffDetails = new ArrayList();
		String tmpNovelUserCode="";
		String select;
		 Log log = LogFactory.getLog(this.getClass());
		select="select NOVELL_USER_ID from staff where NOVELL_USER_ID='"+novellUserCode+"'";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List atList = jdt.queryForList(select);
			Iterator j = atList.iterator();
			
			while (j.hasNext()) {				
				
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			tmpNovelUserCode = data.get("NOVELL_USER_ID").toString();
    			
       				StaffListForm staff=new StaffListForm();
    				
    				staff.setNovellUserCode(tmpNovelUserCode);    				
       				staffDetails.add(staff);
    		
			}//end while
			}catch (Exception ex) {
	    		throw new Exception("HodQueryDao: selectStaffDetails: Error occurred / "+ ex,ex);
	    	} // end try
		return tmpNovelUserCode;
		
	}
	
	/* Insert new standin */
	public void insertStandin(DepartmentRecordForm department) throws Exception{
		
		String sql="INSERT INTO USRDPT(NOVELL_USER_CODE,PERSONNEL_NO,MK_DEPARTMENT_CODE, TYPE,SCHOOL_CODE, COLLEGE_CODE) VALUES('"+department.getUserCode()+"','"+department.getPnumber()+"',"+department.getDepertmentId()+", 'DPT',0,0)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("HodQueyDao: insertStandin (INSERT): Error occurred / "+ ex,ex);
		}
	}
	
	/** Delete standin */
	public void deleteStandin(StringBuffer persNumber) throws Exception{
		
	String sql="DELETE FROM USRDPT WHERE PERSONNEL_NO in ("+persNumber+")" ;
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("HodQueyDao: deleteStandin (DELETE): Error occurred / "+ ex,ex);
		}
		
		 
	}	
	
	/*
	 * select DPT.code, DPT.eng_description, DPT.head_of_dept , DPT.COLLEGE_CODE, COLLEG.DEAN, DPT.SCHOOL_CODE, SCHOOL.HEAD_OF_SCHOOL
from dpt, COLLEG, SCHOOL 
where DPT.IN_USE_FLAG='Y' 
AND   DPT.CODE = 227
AND   DPT.COLLEGE_CODE = COLLEG.CODE
AND   DPT.SCHOOL_CODE = SCHOOL.CODE;
	 */
	
	/** Select departmental info, like Dean, head of school */
	public void selectDepartmentInfo(MainForm mainFormIn)throws Exception{
		mainFormIn.setChair("");
		mainFormIn.setDean("");
		mainFormIn.setDirector("");	
		
		String sql1 = "select NVL(DPT.head_of_dept,0) AS COD, NVL(COLLEG.DEAN,0) AS DEAN, NVL(SCHOOL.HEAD_OF_SCHOOL,0) AS HOS"+
					  " FROM  DPT, COLLEG, SCHOOL"+ 
					  " WHERE DPT.IN_USE_FLAG='Y'"+ 
					  " AND   DPT.CODE = '"+mainFormIn.getSelectedDepartment()+"'"+
					  " AND   DPT.COLLEGE_CODE = COLLEG.CODE"+
					  " AND   DPT.SCHOOL_CODE = SCHOOL.CODE"+
					  " AND   SCHOOL.COLLEGE_CODE = COLLEG.CODE"+
					  " AND   NVL(SCHOOL.IN_USE_FLAG,'Y') = 'Y'";;

		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List courseInfoList = jdt.queryForList(sql1);
			Iterator j = courseInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			String cod = data.get("COD").toString();
    			String dean = data.get("DEAN").toString();
    			String hos = data.get("HOS").toString();
    			
    			// get names
    			mainFormIn.setChair(selectPersonName(cod));
    			mainFormIn.setDean(selectPersonName(dean));
    			mainFormIn.setDirector(selectPersonName(hos));
    			/*
    			ArrayList tmpStandIns = selectDepartmentTypeList(mainFormIn.getSelectedDepartment(), false);
    			for (int i=0; i < tmpStandIns.size(); i++) {
    				
    			}
    			*/

    		}

		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: selectPerson: Error occurred / "+ ex,ex);
		}
		
	}
	
	/**
	 * This method will return staff member name
	 *
	 * @param staffno   	staff number
	 * @output name		
	*/
	public String selectPersonName(String staffNo) throws Exception {
		String name;

		// First check if person exists in table staff
		String sql1 = " SELECT TITLE||' '||SURNAME||' '||INITIALS AS NAME "+
		              " FROM   STAFF "+
		              " WHERE  PERSNO = "+staffNo;

		try{
			String tmpName = this.querySingleValue(sql1,"NAME");
			if ((null == tmpName)||(tmpName.length()==0)) {
				name = "No record found";
			} else {
				name = tmpName;
			}
		} catch (Exception ex) {
            throw new Exception("MaintainStaffStudentDAO: personExist {table staff}: Error occurred / "+ ex,ex);
		}

		return name;
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
}


