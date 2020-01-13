package za.ac.unisa.lms.tools.mylife.dao;
import za.ac.unisa.lms.tools.mylife.forms.MyUnisaMylifeForm;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.mylife.forms.MyUnisaMylifeForm;


public class MyUnisaMylifeStudentDAO  extends StudentSystemDAO{
	
	/**
	 * Method: getStudentExist
	 *  to select the students email address
	 *  from the student system database
	 *
	 * @param studentNr
	 * @return email address
	 * @throws Exception
	 */
	public boolean getStudentExist(String studentNr) throws Exception{

		boolean studentExist = false;
		int counter = 0;
		String query = "SELECT COUNT(*) AS A "+
	   	  				"FROM  STU " +
	   	  				"WHERE NR  = ?";

		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            try{
            	counter=jdt.queryForInt(query, new Object[] {studentNr});
                   
            	if (counter >= 1) {
    				studentExist = true;
    			}
            } catch (Exception ex) {
            	throw new Exception("MyUnisaMylifeStudentDAO: getStudentExist: ("+query+" =: "+studentNr+") Error occurred / "+ ex,ex);
            }

		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getStudentExist: ("+query+" =: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return studentExist;
	}
	
	/**
	 * Method: getStudentEmail
	 *  to select the students email address
	 *  from the student system database
	 *
	 * @param studentNr
	 * @return email address
	 * @throws Exception
	 */
	public String getStudentEmail(String studentNr) throws Exception{

		String email = "";
		String query = "select  nvl(TRIM(EXCHANGEMAIL),' ') AS EXCHANGEMAIL"+
	   	  				" from  IDVALT " +
	   	  				" where MK_STUDENT_NR = ?";

		try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentEmailList = jdt.queryForList(query,new Object[] {studentNr});
    		if (!studentEmailList.isEmpty()) {
				Iterator j = studentEmailList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			email = data.get("EXCHANGEMAIL").toString();
	    		} // end while
    		} //end of 
    		if(email.equals("")||email==null||email.equals(" ")){
    			email="";
			}else{
				email=email;
			}
		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getStudentEmail: ("+query+" "+studentNr+") Error occurred / "+ ex,ex);
		}
		return email;
	}
	
	public String getStudentPassword(String studentNr) throws Exception{

		String password = "";
		String query = "select  nvl(TRIM(PASSWORD),' ') AS PASSWORD"+
	   	  				" from  IDVALT " +
	   	  				" where MK_STUDENT_NR = ?";

		try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
       		List studentPasswordList = jdt.queryForList(query,new Object[] {studentNr});
    		if (!studentPasswordList.isEmpty()) {
				Iterator j = studentPasswordList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			password = data.get("PASSWORD").toString();
    			} // end while
    		} // end if (!studentPasswordList.isEmpty()) {
    		if(password.equals("")||password==null||password.equals(" ")){
    			password="";
			}else{
				password=password;
			}
		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getStudentPassword: ("+query+" "+studentNr+") Error occurred / "+ ex,ex);
		}
		return password;
	}
	/*
	 * 	update idvalt with the microsoft exchange email
	*/
	public void updateIdvalt(String studentNr, String email,String password) throws Exception{
		String sql1 = "UPDATE IDVALT "+
				  "SET EMAIL_ADDRESS = ?, EXCHANGEMAIL=?,password=? "+
				  "WHERE MK_STUDENT_NR = ?";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1,new Object[] {email,email,password,studentNr});
		} catch (Exception ex) {
			throw new Exception("MyUnisaMylifeStudentDAO: updateIdvalt: ("+sql1+","+email+","+studentNr+")Error occurred /"+ ex,ex);
		}
	}



	/**
		 * Method: updateSTUANN
		 *  update student system: student annual record
		 *
		 * @param studentNr
		 * @throws Exception
		 */
		public void updateSTUANN(String studentNr) throws Exception{

			/* 6/01/2006 Change the sql
			old sql: String sql = "UPDATE STUANN "+
				  "SET    SOL_USER_FLAG = 'Y' "+
				  "WHERE  MK_ACADEMIC_PERIOD = 1 "+
				  "AND    MK_ACADEMIC_YEAR = (select max(mk_academic_year) "+
	                                                 "from   stuann "+
	                                                 "where  MK_STUDENT_NR = "+studentNr+") "+
				  "AND    MK_STUDENT_NR = "+studentNr;
			*/

			String sql = "UPDATE STUANN "+
			  			 "SET    SOL_USER_FLAG = 'Y' "+
			  			 "WHERE  MK_ACADEMIC_PERIOD = 1 "+
			  			 "AND    MK_STUDENT_NR = ?"+
			  			 "AND    MK_ACADEMIC_YEAR = (select MK_LAST_ACADEMIC_Y "+
			  			 " 							 FROM   STU "+
			  			 " 							 WHERE  NR = ?) ";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql,new Object[] {studentNr,studentNr});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: updateSTUANN: (stno: "+studentNr+") Error occurred /"+ ex,ex);
			}
		} // updateSTUANN

	/**
	 * Method: getDuplicateStudent
	 *  Validate if new student number was not assigned to student.
	 *
	 * @param studentNr
	 * @return newStudentNr
	 * @throws Exception
	 */
	public String getDuplicateStudent(String studentNr) throws Exception{

		String newStudentNr = "";
		String sql = "SELECT FK_STUNR "+
		             "FROM   DUPSTU "+
		             "WHERE  INCORRECT_STUDENT = ?";

		try{
			//newStudentNr = this.querySingleValue(sql,"MK_STUDENT_NR");
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentNrList = jdt.queryForList(sql,new Object[] {studentNr});
    		if (!studentNrList.isEmpty()) {
				Iterator j = studentNrList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			newStudentNr = data.get("FK_STUNR").toString();
	    		} // end while
    		} //end of 
    		if(newStudentNr==null||newStudentNr.equals("")||newStudentNr.equals(" ")){
    			newStudentNr="";
			}
		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getDuplicateStudent: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
       return newStudentNr;
	}
	/*
	 * 	update ADRPH with the microsoft exchange email
	*/
	public void updateADRPH(String studentNr, String email) throws Exception{
		
		boolean recordExist=false;
		String studentNrTmp = "";
		String adrcatcode="";
		
		// does record already exist for student in ADRPH
		String sql = "SELECT REFERENCE_NO,FK_ADRCATCODE "+
		             "FROM   ADRPH "+
		             "WHERE  REFERENCE_NO = ?";
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentNrList = jdt.queryForList(sql,new Object[] {studentNr});
    		if (!studentNrList.isEmpty()) {
				Iterator j = studentNrList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			studentNrTmp = data.get("REFERENCE_NO").toString();
	    			adrcatcode = data.get("FK_ADRCATCODE").toString();
	    		} // end while
    		} //end of 
    		if(studentNrTmp==null||studentNrTmp.equals("")||studentNrTmp.equals(" ")){
    			recordExist = false;
			} else {
				recordExist = true;
			}

		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: updateADRPH (Select): (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		
		// if adrph record exist do an update
		if (recordExist==true) {		
				String sql1 = "UPDATE ADRPH "+
					  " SET EMAIL_ADDRESS = ?"+
					  " WHERE FK_ADRCATCODE = 1 "+
					  " AND   REFERENCE_NO = ?";
				try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1,new Object[]  {email,studentNr});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: updateADRPH: (stno: "+studentNr+")Error occurred /"+ ex,ex);
			}
		}else{ //		if (recordExist==true) {
		// if adrph record does not exist -  insert ADRPH record
			String sql1 = "INSERT INTO ADRPH(REFERENCE_NO,HOME_NUMBER,EMAIL_ADDRESS,FK_ADRCATCODE) "+
					      "VALUES(?,?,?,?)";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[]  {studentNr," ",email,1 });
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: updateADRPH (Insert): (stno: "+studentNr+") Error occurred / "+ ex,ex);
			}	
		} // end //		if (recordExist==true) {
	} // updateContactDetail
	
	
	/**
	 * Method: insertLibrec
	 * insert record into oracle table LIBREC 
	 * input: student number,sysdate
	 * */
	public void insertLibrec(String studentNr) throws Exception{

		boolean recordExist=false;
		String studentNrTmp ="";
		// does record already exist for student in LIBREC
		String sql = "SELECT MK_STUDENT_NR "+
		             "FROM   LIBREC "+
		             "WHERE  MK_STUDENT_NR = ?";
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentNrList = jdt.queryForList(sql,new Object[] {studentNr});
    		if (!studentNrList.isEmpty()) {
				Iterator j = studentNrList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			studentNrTmp = data.get("MK_STUDENT_NR").toString();
	    		} // end while
    		} //end of 
    		if(studentNrTmp==null||studentNrTmp.equals("")||studentNrTmp.equals(" ")){
    			recordExist = false;
			} else {
				recordExist = true;
			}

		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: insertLibrec (Select): (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}

		if (recordExist==true) {
			// update SOLACT
			String sql1 = "UPDATE LIBREC "+
						  "SET DATE0 = sysdate "+
						  "WHERE MK_STUDENT_NR = ?";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1,new Object[]  {studentNr});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: insertLibrec (Update): Error occurred /"+ ex,ex);
			}
		} else {
			// insert LIBREC
			String sql1 = "INSERT INTO LIBREC(MK_STUDENT_NR,DATE0) "+
					      "VALUES(?,sysdate)";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[]  {studentNr});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: insertLibrec (Insert): (stno: "+studentNr+") Error occurred / "+ ex,ex);
			}
		}
   }
	
	/**
	 * Method: insertEMLOPT
	 * insert record into table EMLOPT - email options student chose
	 * input: student number, emailAssignment
	 * */
	public void insertEMLOPT(String studentNr, String emailAssignment, String emailGenMarketing, String emailGenInfo) throws Exception{

		boolean emloptExist = false;
		String studentNrTmp = "";

		// does record already exist for student in EMLOPT
		String sql = "SELECT STUDENT_NR "+
		             "FROM   EMLOPT "+
		             "WHERE  STUDENT_NR = +?";
		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentNrList = jdt.queryForList(sql,new Object[] {studentNr});
    		if (!studentNrList.isEmpty()) {
				Iterator j = studentNrList.iterator();
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			studentNrTmp = data.get("STUDENT_NR").toString();
	    		} // end while
    		} //end of 
    		if(studentNrTmp==null||studentNrTmp.equals("")||studentNrTmp.equals(" ")){
    			emloptExist = false;
			} else {
				emloptExist = true;
			}
		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: insertEMLOPT (Select): (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}

		if (emloptExist==true) {
			// update EMLOPT
			String sql1 = "UPDATE EMLOPT "+
						  "SET   ASS_RESULTS = ?, "+
						  "      GENERAL_MARKETING =?, "+
						  "      GENERAL_EVENTS = ? "+
						  "WHERE STUDENT_NR =?";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1,new Object[]  {emailAssignment,emailGenMarketing,emailGenInfo,studentNr});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: insertEMLOPT (Update): (stno: "+studentNr+") Error occurred /"+ ex,ex);
			}
		} else {
			// insert EMLOPT
			String sql1 = "INSERT INTO EMLOPT(STUDENT_NR,NOTICES_ADMIN,ASS_RESULTS,NEW_COURSES, GENERAL_MARKETING,GENERAL_EVENTS, LIFELONG_LEARNING) "+
					      "VALUES (?,?,?,?,?,?,?)";
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1,new Object[] {studentNr,"N",emailAssignment,"N",emailGenMarketing,emailGenInfo,"N"});
			} catch (Exception ex) {
				throw new Exception("MyUnisaMylifeStudentDAO: insertEMLOPT (Insert): (stno: "+studentNr+") Error occurred / "+ ex,ex);
			}
		}
   }

	
	public String getCellNr(MyUnisaMylifeForm form) throws Exception{
		MyUnisaMylifeForm myUnisaMylifeForm = (MyUnisaMylifeForm) form;

		String cellNr = "";	
		String tempCellNr="";
		String query = "select  nvl(CELL_NUMBER,' ') AS CELL_NUMBER "+
	   	  				" from  ADRPH " +
	   	  				" where FK_ADRCATCODE=1 and REFERENCE_NO = ?";
				try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentCellNrList = jdt.queryForList(query,new Object[] {myUnisaMylifeForm.getStudentNr()});
			Iterator j = studentCellNrList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			cellNr = data.get("CELL_NUMBER").toString();    			
    			if(cellNr.equals("")||cellNr==null||cellNr.equals(" ")){
    				tempCellNr="";
    			}else{
    				tempCellNr=cellNr;
    			}
    		} // end while

		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getCellNr: (stno: "+myUnisaMylifeForm.getStudentNr()+") Error occurred / "+ ex,ex);
		}
		return tempCellNr;
	}
	
	
	/**
	 * Method: getADRPHEmail
	 *  to select the students email address
	 *  from the student system database
	 *
	 * @param studentNr
	 * @return email address
	 * @throws Exception
	 */
	public String getADRPHEmail(String stno) throws Exception{

		String email = "";
		String query = "select EMAIL_ADDRESS "+
	   	  				" from adrph " +
	   	  				" where reference_no = " + stno+
	   	  				" and fk_adrcatcode=1";

		try{
			
	   		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List studentEmailList = jdt.queryForList(query);
			Iterator j = studentEmailList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			email = data.get("EMAIL_ADDRESS").toString();
    		} // end while
	

		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getADRPHEmail: (stno: "+stno+") Error occurred / "+ ex,ex);
		}
		return email;
	}

	/**
	 * Method: getStudentLastAcadYear
	 *  to select the students last academic year
	 *  from the student system database
	 *
	 * @param studentNr
	 * @return lastAcadYear
	 * @throws Exception
	 */
	public int getStudentLastAcadYear(String studentNr) throws Exception{
		
		int lastAcadYear = 0;

		String query = "SELECT MK_LAST_ACADEMIC_Y AS YEAR "+
	   	  				"FROM  STU " +
	   	  				"WHERE NR  = ?";

		try{
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			lastAcadYear=jdt.queryForInt(query, new Object[] {studentNr});
            
		} catch (Exception ex) {
            throw new Exception("MyUnisaMylifeStudentDAO: getStudentLastAcadYear: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return lastAcadYear;
	}

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
