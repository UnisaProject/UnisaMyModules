package za.ac.unisa.lms.tools.cronjobs.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.cronjobs.util.Encryption;

public class StudentToIdVaultDAO extends StudentSystemDAO {

    public String getCryptKey(Long studno) {
        long tmp1, tmp2=0;
        tmp1 = studno.longValue();
        while (tmp1 > 0) {
            tmp2 += (tmp1 % 10);
            tmp1 = (tmp1 / 10);
        }
        tmp2 *= tmp2;
        return Long.toString(tmp2);
    }
    
	public List<?> getStudentsOnQueue(boolean withEmail, int limit) {
		List<?> result = null;
		String where = "";
		
		if (withEmail) where = " where EMAIL_ADDRESS IS NOT NULL and EMAIL_ADDRESS != ' '"; else 
			where = "";
		
		if (limit > 0) {
			if (where.length() > 0) where += " and "; else where += " where ";
			where += "rownum <= "+ (new Integer(limit));
		}

		result = new JdbcTemplate(getDataSource()).queryForList(
				"select MK_STUDENT_NR, EMAIL_ADDRESS from IDVALT" + where);
		
		return result;
	}
	
	public void removeStudentFromQueue(Long studentNr) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		jdt.update("DELETE FROM IDVALT WHERE MK_STUDENT_NR = ?",
				new Object[] { studentNr },
				new int[] { Types.BIGINT });
	}
	
	public String getStudentPasswd(Long studentNr) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String passwd = (String)
				jdt.queryForObject(
						"SELECT PASSWORD FROM WEBSOL WHERE " +
						"STUDENT_NR = ?", new Object[] { studentNr }, new int[] { Types.BIGINT },
						String.class);
		return Encryption.decrypt(passwd, getCryptKey(studentNr));
	}
	
	public String getStudentEmail(String studentNr) throws Exception {
		
		String email = "";	
		String query = "SELECT EMAIL_ADDRESS "+ 
	   	  				" FROM ADRPH " +
	   	  				" WHERE REFERENCE_NO = " + studentNr +
	   	  				" and FK_ADRCATCODE=1"; 		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
			email = (String) jdt.queryForObject(query, String.class);
			
		} catch (Exception ex) {
            throw new Exception(this+": getStudentEmail: Error occurred / "+ex+": "+ex.getMessage());
		}	
		return email;		
	}

	public String getStudentFirstName(String studentNr) throws Exception {
		
		String firstName = "";	
		String query = "SELECT FIRST_NAMES "+ 
	   	  				" FROM STU " +
	   	  				" WHERE STU.NR = " + studentNr; 		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
			firstName = (String) jdt.queryForObject(query, String.class);
			
		} catch (Exception ex) {
			return "Student";
            //throw new Exception(this+": getStudentFirstName: Error occurred / "+ex+": "+ex.getMessage());
		}	
		return firstName;		
	}
	
	public String getStudentLastName(String studentNr) throws Exception {
		
		String lastName = "";	
		String query = "SELECT SURNAME "+ 
	   	  				" FROM STU " +
	   	  				" WHERE STU.NR = " + studentNr;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());	
			lastName = (String) jdt.queryForObject(query, String.class);
			
		} catch (Exception ex) {
			lastName = "at myUnisa";
            //throw new Exception(this+": getStudentLastName: Error occurred / "+ex+": "+ex.getMessage());
		}	
		return lastName;		
	}
}
