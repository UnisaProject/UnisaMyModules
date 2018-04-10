package za.ac.unisa.lms.dao;

	import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

	public class IdvaltRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			
			IdvaltStudentDetails studentDetails = new IdvaltStudentDetails();	
			String studentNr="";
			String exchangemail="";
			String password="";
			String emailAddress="";
			String cellNumber="";
		try {

			studentNr = rs.getString("MK_STUDENT_NR");
			if (studentNr.equals("") || studentNr == null || studentNr.equals(" ")) {
				studentNr = "";
			}
			studentDetails.setStudentNr(studentNr);

			exchangemail = rs.getString("EXCHANGEMAIL");
			if (exchangemail.equals("") || exchangemail == null || exchangemail.equals(" ")) {
				exchangemail = "";
			}
			studentDetails.setExchangemail(exchangemail);

			password = rs.getString("PASSWORD");
			if (password.equals("") || password == null || password.equals(" ")) {
				password = "";
			}
			studentDetails.setPassword(password);
			
			emailAddress = rs.getString("EMAIL_ADDRESS");
			if (emailAddress.equals("") || emailAddress == null || emailAddress.equals(" ")) {
				emailAddress = "";
			}
			studentDetails.setEmailaddress(emailAddress);
			
			cellNumber = rs.getString("CELL_NUMBER");
			if (cellNumber.equals("") || cellNumber == null || cellNumber.equals(" ")) {
				cellNumber = "";
			}
			studentDetails.setCellNumber(cellNumber);
			
			
		} catch (SQLException ex) {
			throw new SQLException ("IdvaltRowMapper: mapRow: (stno: " + studentNr+ ") Error occurred / " + ex, ex);
		}
			return studentDetails;
		}			
}
