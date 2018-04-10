package za.ac.unisa.lms.tools.canceluser.dao;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.SakaiDAO;
public class CancelUserSakaiQueryDAO extends SakaiDAO{
	
	public String getJoinDate(String stno)throws Exception{
		
				String joinDate;
		String sql = "select JOIN_DATE FROM JOIN_ACTIVATION WHERE USER_ID = "+stno;
		try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		joinDate = this.querySingleValue(sql,"JOIN_DATE");
		if((joinDate==null)||(joinDate.length() == 0) || (joinDate.equals(""))){
			joinDate = null;
		}
		}catch (Exception ex) {
			// TODO: handle exception
			throw new Exception("MyLifeSupportQueryDAO: getStudentMyLifeEmail: (stno: "+stno+") Error occurred / "+ ex,ex);
		}
		return joinDate;
	}
	public boolean getvalidStudentRecord(String studentNr) throws Exception{
		// Get valid Student number
				String studentNrTmp = "";
				boolean studentRecordExist = false;

				
		String sql = " select user_id "+
				" from join_activation " +
				" where user_id = "+studentNr;

		try {
	    	studentNrTmp = this.querySingleValue(sql,"USER_ID");
			if ((studentNrTmp == null)||(studentNrTmp.length() == 0)) {
				studentNr = " ";
				studentRecordExist = false;

			} else {
				studentRecordExist = true;
			}
		} catch (Exception ex) {
	       throw new Exception("CancelUserStudentQueryDAO : getvalidStudent / "+ ex,ex);
	    }
		return studentRecordExist ;
		}
	public void setUserInactiveJOINACTIVATION (String studentNr) throws Exception{

		String sql = " UPDATE JOIN_ACTIVATION " +
				" SET STATUS_FLAG = 'NC',"+
				"     ACT_STATUS = 'N' "+
				" WHERE USER_ID = "+studentNr;

		System.out.println("update: "+sql);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("CancelUserSakaiQueryDAO: setUserInactiveJOINACTIVATION (Update): Error occurred /"+ ex,ex);
		}
	}
	public void setUserInactiveJOINACTIVATIONBoth (String studentNr) throws Exception{

		String sql = " UPDATE JOIN_ACTIVATION " +
				" SET STATUS_FLAG = 'NC',"+
				"     ACT_STATUS = 'N' ,"+
				" MYLIFE_ACT_STATUS='N'"+
				" WHERE USER_ID = "+studentNr;

		System.out.println("update: "+sql);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("CancelUserSakaiQueryDAO: setUserInactiveJOINACTIVATION (Update): Error occurred /"+ ex,ex);
		}
	}
	public void setUserInactiveJOINACTIVATIONBlocked(String studentNr) throws Exception{

		String sql = " UPDATE JOIN_ACTIVATION " +
				" SET STATUS_FLAG = 'BL',"+
				"     ACT_STATUS = 'N' "+
				" WHERE USER_ID = "+studentNr;

		System.out.println("update: "+sql);

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
			throw new Exception("CancelUserSakaiQueryDAO: setUserInactiveJOINACTIVATION (Update): Error occurred /"+ ex,ex);
		}
	}
	public void setMyLifeUserInactiveSOLACT (String studentNr) throws Exception{

		String sql = " UPDATE JOIN_ACTIVATION SET MYLIFE_ACT_STATUS = 'N'"+
				" where USER_ID = "+studentNr;
		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql);
		} catch (Exception ex) {
		}
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
  public String getBlockedStatus(String studentNr) { 
		        String query = "select status_flag from JOIN_ACTIVATION where USER_ID ="+studentNr; 
		        return querySingleValue(query,"status_flag");  
  }
}
