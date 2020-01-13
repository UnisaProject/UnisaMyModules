package za.ac.unisa.lms.tools.booklistadmin.dao;

public class UserDAO {
	 public String selectUserId(String userEID) throws Exception {
			String userId = "";
			
			String sql1 = " SELECT USER_ID "+
				          " FROM SAKAI_USER_ID_MAP" +
				          " WHERE EID = '"+userEID+"'";

			try{
				DatabaseUtil databaseUtil=new  DatabaseUtil();
				userId = databaseUtil.querySingleValue(sql1,"USER_ID");	
			} catch (Exception ex) {
				throw new Exception("SatbookDAO: selectUserId: Error occurred / "+ ex,ex);
			}
			return userId;
		}   

}
