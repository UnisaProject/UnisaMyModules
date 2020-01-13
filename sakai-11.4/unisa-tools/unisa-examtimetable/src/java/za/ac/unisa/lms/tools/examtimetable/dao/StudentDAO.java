package za.ac.unisa.lms.tools.examtimetable.dao;

public class StudentDAO extends DatabaseUtils{
	public String getStudentEmail(String studentNr) throws Exception{

        String email = "";
        String query = "select email_address "+
				               " from adrph " +
				               " where reference_no = " + studentNr +
				               " and fk_adrcatcode=1";
          String errorMsg="StudentDAO of StudentPlacement: Error reading  adrph" ;
         email = querySingleValue(query,"email_address",errorMsg);		
      return email;
 }
}