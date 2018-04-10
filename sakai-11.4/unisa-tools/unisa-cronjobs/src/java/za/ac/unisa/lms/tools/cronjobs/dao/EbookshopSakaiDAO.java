package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class EbookshopSakaiDAO extends SakaiDAO{

	public ArrayList<?> getBooksAdvertised() throws Exception {

		ArrayList<?> list = new ArrayList ();
		EshopStudentSystemDAO studentDAO = new EshopStudentSystemDAO();

		String query = "SELECT DISTINCT COURSE_CODE " +
					   "FROM   RESELLBOOK_INFO  "+
					   "WHERE  LENGTH(COURSE_CODE) < 7 ";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query);
			Iterator<?> i = queryList.iterator();
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String oldCourseCode = data.get("COURSE_CODE").toString();
				String newCourseCode = "";
				// SELECT FULL COURSE_CODE FROM TABLE SUN
				newCourseCode = studentDAO.studyUnitCode(oldCourseCode);

				// UPDATE CURRENT COURSE_CODE WITH FULL COURSE CODE
				//String errorMsg = updateCourseCode(oldCourseCode, newCourseCode);
				updateCourseCode(oldCourseCode, newCourseCode);
			}
		}catch (Exception ex) {
			throw new Exception("E-Bookshop: Error "+ ex);
		}

		return list;
	}

	private String updateCourseCode(String oldCourseCode, String newCourseCode) throws Exception{
		String errorMsg="";

		String sqlUpdate = "UPDATE RESELLBOOK_INFO "+
		                   "SET    COURSE_CODE = '"+newCourseCode+"' "+
		                   "WHERE  COURSE_CODE = '"+oldCourseCode+"' ";
		System.out.println("sqlUpdate:"+sqlUpdate);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sqlUpdate);
			errorMsg = errorMsg + ">> "+sqlUpdate;
		} catch (Exception ex) {
			errorMsg = "unisa-cronjobs: ebookshopUpdateJob: updateCourseCode (sqlUpdate="+sqlUpdate+"):  Error occurred / "+ ex;
			//throw new Exception("MyUnisaStatsDAO: updateUnisaMIS2 (sqlUpdate="+sqlUpdate+"): (event="+event+") Error occurred / "+ ex,ex);
		}

		return errorMsg;
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

