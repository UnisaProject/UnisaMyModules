package za.ac.unisa.lms.tools.discussionforums.dao;

import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;


public class OracleDAO  extends StudentSystemDAO{

	public OracleDAO(){

	}

	
	 public String getUserFullNames(int studentNumber)
		{
		    String fullName = "";
		    
			String sql = "select surname, INITIALS, MK_TITLE from stu where NR="+studentNumber;
			
			String surname = querySingleValue(sql, "surname");
			String intials = querySingleValue(sql, "INITIALS");
			
			fullName = intials+" "+surname;
			
			return fullName;
			
		}
		
	 
	 
	 public String getUserFullNames(String userId) throws Exception
		{
		 
		 
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
		 String today = dateFormat.format(date);
		    String fullName = "";
		    String name;
		    userId = userId.toUpperCase();
			String sql = "select SURNAME, INITIALS, TITLE from staff where NOVELL_USER_ID="+"'"+userId+"'"+
			             " AND (TO_CHAR(RESIGN_DATE, 'YYYY-MM-DD')>="+"'"+today+"'"+  
					     " OR RESIGN_DATE IS NULL)";
			try{
				String surname = querySingleValue(sql, "surname");
				String intials = querySingleValue(sql, "INITIALS");
				String title = querySingleValue(sql, "TITLE");				
				fullName = title+" "+intials+" "+surname;
				fullName = ltrim(fullName);
				fullName = rtrim(fullName);
			
				if ((null == fullName)||(fullName.length()==0)) {
					name = "0";
				} else {
					name = fullName;
				}
				if (name.equals("0")) {
					// no records were found for person in STAFF, check in table USR
					String sql2 = "SELECT USER_CODE_OWNER "+
					              "FROM   USR "+
					              "WHERE  UPPER(NOVELL_USER_CODE) = upper('"+userId+"') ";

					try{
						fullName = this.querySingleValue(sql2,"USER_CODE_OWNER");
					} catch (Exception ex) {
				      throw new Exception("OracleDAO: networkExist {table usr}: Error occurred / "+ ex,ex);
					}		
				}

			} catch (Exception ex) {
	            throw new Exception("OracleDAO: networkExist {table staff}: Error occurred / "+ ex,ex);
			}

		

			return fullName;
			
		}	
	 public String getUserNames(String userId)
		{
		 int stuNumber=0;
		 boolean isStudent=false;
		 boolean isStaff= false;
		 String fullNames ="";
		 try{   
		  stuNumber = Integer.parseInt(userId);
		  isStudent=true;
		 }catch(NumberFormatException ne){
			 isStaff=true;
		 }
		 if(isStudent==true){
			 fullNames = getUserFullNames(stuNumber);
		 }else if(isStaff==true) {
			 try {
				fullNames = getUserFullNames(userId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	
		 
			return fullNames;
		}
	 
	 
	public PosterDetails getLastPoster(Integer stno) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		PosterDetails posterDetails = new PosterDetails();		
		List result = jdt.query("select initials,INITCAP(surname) surname  from stu where nr = ? ",
				new Object[] { stno},
				new int[] { Types.INTEGER },
						new PosterRowMapper());
		Iterator i = result.iterator();
		if(i.hasNext()){
			posterDetails = (PosterDetails) i.next();
		}
		return (posterDetails);
	}

	public String getUserType(String user,String siteid) throws Exception {
		String userType;
		String subject = siteid.toString().substring(0,7);
		String year = "20"+siteid.toString().substring(8,10);
		String period = siteid.toString().substring(11,13);
		if (period.equalsIgnoreCase("S1")){
			period = "1";
		} else if (period.equalsIgnoreCase("S2")){
			period = "2";
		} else if (period.equalsIgnoreCase("Y1")){
			period = "0";
		} else if (period.equalsIgnoreCase("Y2")){
			period = "6";
		}
		String sql = "SELECT ACCESS_LEVEL FROM usrsun WHERE upper(novell_user_id) = upper('"+user+"') and upper(mk_study_unit_code)  = upper('"+subject+"') and mk_academic_year = "+year+" and upper(mk_semester_period) = "+period;
		try{
			userType = this.querySingleValue(sql,"ACCESS_LEVEL");
		} catch (Exception ex) {
            throw new Exception("OracleDAO : getUserType Error occurred / "+ ex,ex);
		}
		return userType;
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

	public boolean isInteger(String i)
	{
		try{
			Integer.parseInt(i);
			return true;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	
	/* remove leading whitespace */
	 public static String ltrim(String source) {
	        return source.replaceAll("^\\s+", "");
	    }

	    /* remove trailing whitespace */
	    public static String rtrim(String source) {
	        return source.replaceAll("\\s+$", "");
	    }
}
