package za.ac.unisa.lms.dao;

import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

/**
 * @author udcsweb
 *
 */
public class IdvaltStudentDetailsDAO extends StudentSystemDAO  {
	
	public IdvaltStudentDetails getStudentDetails(String stuno)  throws Exception {
		IdvaltRowMapper stuDetails=null;
		IdvaltStudentDetails studentDetails =new IdvaltStudentDetails();
		try{
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "SELECT nvl(TRIM(IDVALT.MK_STUDENT_NR),' ') AS MK_STUDENT_NR , nvl(TRIM(IDVALT.EXCHANGEMAIL),' ') AS EXCHANGEMAIL," +
				      "nvl(TRIM(IDVALT.PASSWORD),' ') AS PASSWORD,nvl(ADRPH.EMAIL_ADDRESS,' ') AS EMAIL_ADDRESS ,nvl(ADRPH.CELL_NUMBER,' ') AS CELL_NUMBER " +
				      "FROM  IDVALT,ADRPH WHERE ADRPH.FK_ADRCATCODE=1 AND " +
				      "IDVALT.MK_STUDENT_NR=ADRPH.REFERENCE_NO and IDVALT.MK_STUDENT_NR =?";
		
		List studentDetailList=jdt.query(sql, new Object[] {stuno},new IdvaltRowMapper());
		
		if(studentDetailList==null || studentDetailList.size()==0){
			studentDetails.setExchangemail("");
			studentDetails.setPassword("");
			studentDetails.setStudentNr("");
			studentDetails.setCellNumber("");
			studentDetails.setEmailaddress("");
		}else{
		Iterator i = studentDetailList.iterator();
		if (i.hasNext()){
			studentDetails = (IdvaltStudentDetails) i.next();
		}
		}
	    } catch (Exception ex) {
        throw new Exception("IdvaltStudentDetailsDAO: getStudentDetails: (stno: "+stuno+") Error occurred / "+ ex,ex);
	  }
		return studentDetails;		
	}
	
}

