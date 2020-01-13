package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class MyUnisaStatsStudentOracleDAO extends StudentSystemDAO {

	public String getActiveStudents(String forDate) throws Exception {

		String[] tmpDate = forDate.split("-");
		int eventStats = 0;
		String errorMsg = "";

		MyUnisaStatsDAO daoInsert = new MyUnisaStatsDAO();

		String select = "SELECT COUNT(MK_STUDENT_NR) AS COUNTA "+
						"FROM   STUANN "+
						"WHERE  MK_ACADEMIC_YEAR = "+tmpDate[0]+" "+
						"and    MK_ACADEMIC_PERIOD = 1 "+
						"AND    SOL_USER_FLAG = 'Y' ";
		//System.out.println("select stuann: "+select);

    	try{
    		eventStats = Integer.parseInt(this.querySingleValue(select,"COUNTA"));

    		daoInsert.insertUnisaMIS("active.students",forDate, eventStats, "gateway", "S");
    	} catch (Exception ex) {
    		errorMsg = "za.ac.unisa.lms.tools.cronjobs.dao.SatbookOracleDAO: selectStudentEmailList: Error occurred / "+ ex;
    		throw new Exception("za.ac.unisa.lms.tools.cronjobs.dao.SatbookOracleDAO: selectStudentEmailList: Error occurred / "+ ex,ex);

    	} // end try

		return errorMsg;
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
    	List<?> queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator<?> i = queryList.iterator();
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

