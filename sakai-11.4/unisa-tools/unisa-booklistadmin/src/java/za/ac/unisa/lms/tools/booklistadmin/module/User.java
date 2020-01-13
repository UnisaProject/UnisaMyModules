package za.ac.unisa.lms.tools.booklistadmin.module;


import za.ac.unisa.lms.tools.booklistadmin.dao.UserDAO;

public class User {
	
	UserDAO dao;
	public  User(){
		dao=new UserDAO();
		
	}
	public String selectUserId(String userEID) throws Exception {
		             return dao.selectUserId(userEID);
	} 
	

}
