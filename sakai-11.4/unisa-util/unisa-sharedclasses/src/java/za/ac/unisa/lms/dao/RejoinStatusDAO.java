

package za.ac.unisa.lms.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class RejoinStatusDAO extends StudentSystemDAO {


               public boolean hasRejoined(String studentNr)throws Exception{
                              String result = "";

                              String query = "SELECT SOL_USER_FLAG FROM STUANN "+
                                             "WHERE  MK_ACADEMIC_PERIOD = 1 "+
                                             "AND    MK_STUDENT_NR = "+studentNr+" "+
                                             "AND    MK_ACADEMIC_YEAR = (select MK_LAST_ACADEMIC_Y "+
                                             " FROM   STU WHERE  NR = "+studentNr+") ";
                              try{ 
                                             JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());      
                                             result = (String) jdt.queryForObject(query, String.class);
                                             
                              } catch (Exception ex) {
                       throw new Exception("Unisa-Util:sharedclasses-Rejoinstatusdao: hasRejoined: Error occurred / "+ ex,ex);
                              }
                              
                              if("N".equals(result)) {
                                             String update_query = "UPDATE STUANN SET SOL_USER_FLAG ='Y' " +
                                             "WHERE  MK_ACADEMIC_PERIOD = 1 "+
                                             "AND MK_STUDENT_NR = "+studentNr+" "+
                                             "AND MK_ACADEMIC_YEAR = (select MK_LAST_ACADEMIC_Y FROM   STU WHERE  NR = "+studentNr+") ";                                 
                                             try{
                                                            JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
                                                            jdt1.update(update_query);
                                             } catch (Exception ex) {
                                                            throw new Exception("Unisa-Util:sharedclasses RejoinStatusDAO: hasRejoined: (stno: "+studentNr+")Error occurred /"+ ex,ex);
                                             }
                              }                             
               /*           if("Y".equals(result)) {
                                             return true;
                              } else {
                                             return false;
                              }*/
                              return true;
               }
               /**
               * This method executes a query and returns a String value as result.
               * An empty String value is returned if the query returns no results.
               *
               * @param query     The query to be executed on the database
               * @param field                  The field that is queried on the database
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
                                             result = data.get(field).toString();
                              }
                              return result;
               }


}
