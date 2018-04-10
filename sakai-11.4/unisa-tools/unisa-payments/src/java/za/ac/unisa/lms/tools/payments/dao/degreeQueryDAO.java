package za.ac.unisa.lms.tools.payments.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class degreeQueryDAO extends StudentSystemDAO {

	/** Get the latest degree for the student
	 * input: student number
	 * output: degree
	 * */
	public String getStudentDegree(String studentNr) throws Exception{

		String degree = "";

		/* 6/01/2006 Change select
		 old select: String sql = "SELECT GRD.ENG_DESCRIPTION||' - '||STUANN.MK_HIGHEST_QUALIFI "+
                    "FROM GRD,STUANN "+
                    "WHERE STUANN.MK_STUDENT_NR = "+studentNr+" "+
                    "AND GRD.CODE = STUANN.MK_HIGHEST_QUALIFI "+
                    "AND stuann.mk_academic_year = (select max(mk_academic_year) "+
                                                   "from   stuann "+
                                                   "where  MK_STUDENT_NR = "+studentNr+") ";
        */
		String sql = "SELECT GRD.ENG_DESCRIPTION||' - '||STUANN.MK_HIGHEST_QUALIFI "+
        			 "FROM GRD,STUANN "+
        			 "WHERE STUANN.MK_STUDENT_NR = "+studentNr+" "+
        			 "AND MK_ACADEMIC_PERIOD = 1 "+
        			 "AND GRD.CODE = STUANN.MK_HIGHEST_QUALIFI "+
        			 "AND stuann.mk_academic_year = (select MK_LAST_ACADEMIC_Y "+
        			 " 								 FROM   STU "+
        			 "  							 WHERE  NR= "+studentNr+") ";

		try{
	//		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			degree = this.querySingleValue(sql,"GRD.ENG_DESCRIPTION||' - '||STUANN.MK_HIGHEST_QUALIFI");

		} catch (Exception ex) {
            throw new Exception("degreeQueryDAO: getStudentDegree: Stno "+studentNr+" :Error occurred / "+ ex,ex);
		}

       return degree;
   }


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
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }
 	   return result;
	}
}
