package za.ac.unisa.lms.tools.join.dao;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;

public class MyUnisaJoinQueryDAO extends StudentSystemDAO {

	/** 
	 * Method: insertJoinSOLREG
	 * insert record into table SOLREG (myUnisa join record) 
	 * input: student number, success (N/Y)
	 * 
	 * REG_TYPE = S for sakai join
	 * SUCCESS_STATUS = N = not success; S = success
	 * */
	public void insertJoinSOLREG(String studentNr, String success) throws Exception{ 
              
		// set current time
		Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
	    
	    // select ipaddress
	    InetAddress thisIp = InetAddress.getLocalHost();
	    //System.out.println("getHostName: "+thisIp.getHostName());    // name
	    //System.out.println("getHostAddress: "+thisIp.getHostAddress()); 
	    
	    // country code
	    String countryCode = getCountryCode(studentNr);
		
		String sql = "INSERT INTO SOLREG(STUDENT_NR,TIMESTAMP,REG_TYPE,IPADDRESS,COUNTRY_CODE,SUCCESS_STATUS)"+
						"VALUES (?,?,?,?,?,?)";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());	
			jdt.update(sql,new Object[] {studentNr,new Timestamp(cal.getTimeInMillis()),"S",thisIp.getHostAddress(),countryCode,success});
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: insertJoinSOLREG: Error occurred / "+ ex,ex);
		}
   }

	/** 
	 * Method: insertJoinError
	 * insert record into table SOLERR if error occurs. 
	 * input: 
	 * 		student number
	 * 		errType = error type (S = sakai myUnisa join; R =Registration of a student; M  Matric exemption application (M30)
	 *		ipaddress 
	 *		inputData = data student entered
	 *		systemData = data as on student database.
	 *  
	 * */
	public void insertJoinERROR(String studentNr, String errType, String inputData, String systemData) throws Exception{ 
              
	    Calendar cal = Calendar.getInstance();
	    cal.setTimeInMillis(new java.util.Date().getTime());
		
		String sql = "INSERT INTO SOLERR(STUDENT_NR,TIMESTAMP,ERR_TYPE,IPADDRESS,INPUT_DATA,SYSTEM_DATA)"+
						"VALUES (?,?,?,?,?,?)";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());	
			jdt.update(sql,new Object[] {studentNr,new Timestamp(cal.getTimeInMillis()),errType,"ipAddress",inputData,systemData});
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: insertJoinERROR: Error occurred / "+ ex,ex);
		}
	}
	
	/** 
	 * Method: getStudentIncludeClasslist
	 *  to select answer (as chosen by student on registration)
	 *  if student want to be included in classlist
	 *  
	 * @param studentNr
	 * @return classlistAnswer (Y/N)
	 * @throws Exception
	 */
	public String getStudentIncludeClasslist(String studentNr) throws Exception{

		String classlistAnswer = "";
		/* 06/01/2005 Magdaleen request sql change
		   old select: String sql = "SELECT FELLOW_STUDENT_FLA "+
		             "FROM   STUANN "+
		             "WHERE  MK_STUDENT_NR = "+studentNr+" "+
		             "AND    MK_ACADEMIC_YEAR = (select max(mk_academic_year) "+
                                                 "from   stuann "+
                                                 "where  MK_STUDENT_NR = "+studentNr+") ";
        */

		String sql = "SELECT FELLOW_STUDENT_FLA "+
        			 "FROM   STUANN "+
        			 "WHERE  MK_STUDENT_NR = "+studentNr+" "+
        			 "AND    MK_ACADEMIC_PERIOD = 1"+
        			 "AND    MK_ACADEMIC_YEAR = (SELECT MK_LAST_ACADEMIC_y" +
        			 "                           FROM   STU" +
        			 "							 WHERE  NR = "+studentNr+") ";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());	
			classlistAnswer = (String) jdt.queryForObject(sql, String.class);
			if (classlistAnswer.length() == 0) {
				classlistAnswer = " ";
			}
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: getStudentIncludeClasslist: Error occurred / "+ ex,ex);
		}
       return classlistAnswer;
	}

	/** 
	 * Method: getCountryCode
	 *  to select the country code for the student
	 *  
	 * @param studentNr
	 * @return country code
	 * @throws Exception
	 */
	public String getCountryCode(String studentNr) throws Exception{

		String countryCode = "1015";
		String sql = "SELECT MK_COUNTRY_CODE "+
		             "FROM   STU "+
		             "WHERE  NR = "+studentNr;

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());	
			countryCode = (String) jdt.queryForObject(sql, String.class);
			if (countryCode.length() == 0) {
				countryCode = "1015";
			}
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: getCountryCode: Error occurred / "+ ex,ex);
		}
       return countryCode;
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
		String query = "select email_address "+ 
	   	  				" from adrph " +
	   	  				" where reference_no = " + studentNr +
	   	  				" and fk_adrcatcode=1"; 		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
			email = (String) jdt.queryForObject(query, String.class);
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: getStudentEmail: Error occurred / "+ ex,ex);
		}	
		return email;		
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
	public String getStudentCell(String studentNr) throws Exception{
		
		String cell = "";	
		String query = "select CELL_NUMBER "+ 
	   	  				" from adrph " +
	   	  				" where reference_no = " + studentNr +
	   	  				" and fk_adrcatcode=1"; 		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
			cell = (String) jdt.queryForObject(query, String.class);
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: getStudentCell: Error occurred / "+ ex,ex);
		}	
		return cell;		
	}	
	
	/** 
	 * Method: insertEMLOPT
	 * insert record into table EMLOPT - email options student chose 
	 * input: student number, emailAssignment, emailAdmin
	 * */
	public void insertEMLOPT(String studentNr, boolean emailAdmin, boolean emailAssignment) throws Exception{ 
              
		String emloptExist;
		String emailAssign;
		String emailAdm;
		
		// does record already exist for student in EMLOPT
		String sql = "SELECT STUDENT_NR "+
		             "FROM   EMLOPT "+
		             "WHERE  STUDENT_NR = "+studentNr;
		try{ 
			emloptExist = this.querySingleValue(sql,"STUDENT_NR"); 
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: insertEMLOPT (Select): Error occurred / "+ ex,ex);
		}
		
		if (emailAssignment == true) {
			emailAssign = "Y";
		} else {
			emailAssign = "N";
		}
		
		if (emailAdmin == true) {
			emailAdm = "Y";
		} else {
			emailAdm = "N";
		}
		
		if (emloptExist.length() > 0) {
			// update EMLOPT
			String sql1 = "UPDATE EMLOPT "+
						  "SET NOTICES_ADMIN = '"+emailAdm+"',"+
						  "    ASS_RESULTS = '"+emailAssign+"' "+
						  "WHERE STUDENT_NR ="+studentNr;
			
			try{ 
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("MyUnisaJoinQueryDAO: insertEMLOPT (Update): Error occurred /"+ ex,ex);
			}
		} else {
			// insert EMLOPT
			String sql1 = "INSERT INTO EMLOPT(STUDENT_NR,NOTICES_ADMIN,ASS_RESULTS,NEW_COURSES, GENERAL_MARKETING,GENERAL_EVENTS, LIFELONG_LEARNING) "+
					      "VALUES (?,?,?,?,?,?,?)";
			try{ 
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());	
				jdt1.update(sql1,new Object[] {studentNr,emailAdm,emailAssign,"N","N","N","N"});
			} catch (Exception ex) {
				throw new Exception("MyUnisaJoinQueryDAO: insertEMLOPT (Insert): Error occurred / "+ ex,ex);
			}
		}
   }

	/** 
	 * Method: insertSOLACT
	 * insert record into oracle table SOLACT - sol activation 
	 * input: student number, emailAssignment, emailAdmin
	 * */
	public void insertSOLACT(String studentNr, String actCode, String actStatus, String emailVerified) throws Exception{ 
              
		String recordExist;
		// does record already exist for student in EMLOPT
		String sql = "SELECT STUDENT_NR "+
		             "FROM   SOLACT "+
		             "WHERE  STUDENT_NR = "+studentNr;
		try{ 
			recordExist = this.querySingleValue(sql,"STUDENT_NR"); 
			
		} catch (Exception ex) {
            throw new Exception("MyUnisaJoinQueryDAO: insertSOLACT (Select): Error occurred / "+ ex,ex);
		}
		
		if (recordExist.length() > 0) {
			// update SOLACT
			String sql1 = "UPDATE SOLACT "+
						  "SET ACT_CODE = '"+actCode+"', "+
						  "    ACT_STATUS = '"+actStatus+"', "+
						  "    EMAIL_VERIFIED = '"+emailVerified+"' "+
						  "WHERE STUDENT_NR = "+studentNr;
						  						  
			try{ 
				JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("MyUnisaJoinQueryDAO: insertSOLACT (Update): Error occurred /"+ ex,ex);
			}
		} else {
			// insert SOLACT
			String sql1 = "INSERT INTO SOLACT(STUDENT_NR, ACT_CODE, ACT_STATUS, EMAIL_VERIFIED) "+
					      "VALUES (?,?,?,?)";

			try{ 
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());	
				jdt1.update(sql1,new Object[] {studentNr,actCode,actStatus,emailVerified});
			} catch (Exception ex) {
				throw new Exception("MyUnisaJoinQueryDAO: insertSOLACT (Insert): Error occurred / "+ ex,ex);
			}
		}
   }	
	
	/** 
	 * Method: updateContactDetail
	 *  update student system with the entered email address and cellphone
	 *  
	 * @param studentNr, email, cellNr
	 * @throws Exception
	 */
	public void updateContactDetail(String studentNr, String email, String cellNr) throws Exception{

		String sql1 = "";
		if (cellNr == null) {
			cellNr = "";
		}
		if (cellNr.length() == 0) {
			sql1 = "UPDATE ADRPH "+
			  "SET EMAIL_ADDRESS = '"+email+"' "+
			  "WHERE FK_ADRCATCODE = 1 "+
			  "AND   REFERENCE_NO = "+studentNr;			
		} else {
			sql1 = "UPDATE ADRPH "+
				  "SET EMAIL_ADDRESS = '"+email+"', "+
				  "    CELL_NUMBER = '"+cellNr+"' "+
				  "WHERE FK_ADRCATCODE = 1 "+
				  "AND   REFERENCE_NO = "+studentNr;
		}
		  						  
		try{ 
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("MyUnisaJoinQueryDAO: updateContactDetail: Error occurred /"+ ex,ex);
		}
	} // updateContactDetail	

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
		  			 "AND    MK_STUDENT_NR = "+studentNr+" "+
		  			 "AND    MK_ACADEMIC_YEAR = (select MK_LAST_ACADEMIC_Y "+
		  			 " 							 FROM   STU "+
		  			 " 							 WHERE  NR = "+studentNr+") ";
		  						  
		try{ 
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("MyUnisaJoinQueryDAO: updateSTUANN: Error occurred /"+ ex,ex);
		}
	} // updateSTUANN	
	
	/**
	 * method: querySingleValue
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
			 HashMap data = (HashMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}	
}