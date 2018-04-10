package za.ac.unisa.lms.tools.tutoringplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.tutoringplan.forms.AuditTrail;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringPlanDetails;
import za.ac.unisa.lms.tools.tutoringplan.impl.TutoringplanUtils;
public class AuditTrailDAO extends StudentSystemDAO {
	public List<AuditTrail> getAuditTrail(String studyUnit, Short acadYear, Short semester) throws Exception {
		List<AuditTrail> list = new ArrayList<AuditTrail>();
		
		String sql = "select action_gc53,comments,updated_by,to_char(updated_on,'YYYY-MM-DD HH24:MI:SS') as updatedOn"+
        " from asslog" +
        " where year =" + acadYear +
        " and period=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'" +
        " and type_gc52='TUTORPLAN'" +
        " order by updated_on";

	
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				AuditTrail record = new AuditTrail();
				record.setAction(TutoringplanUtils.replaceNull(data.get("action_gc53")));
				record.setComment(TutoringplanUtils.replaceNull(data.get("comments")));
				record.setUpdatedOn(TutoringplanUtils.replaceNull(data.get("updatedOn")));
				Person person = new Person();
				UserDAO userdao = new UserDAO();
				person = userdao.getPerson(TutoringplanUtils.replaceNull(data.get("updated_by")));	
				record.setUser(person);
				list.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("TutoringPlanDAO: Error reading Audit Trail / " + ex);
		}	
		
		return list;
	}
	public int addAuditTrail(Connection  connection,String comment) throws Exception {
	       String sql = "insert into asslog (year,period,mk_study_unit_code,type_gc52,action_gc53," +
		      "comments,return_email_addr,updated_by,updated_on,request_action_frm)" +
		      " values " +
		      "(?,?,?,?,?,?,?,?,sysTimestamp,?)";
			
	        PreparedStatement ps = connection.prepareStatement(sql);
			ps.setShort(1, TutoringplanUtils.getAcademicYear()); 
			ps.setShort(2, TutoringplanUtils.getSemester()); 
			ps.setString(3,TutoringplanUtils.getStudyUnit());
			ps.setString(4, "TUTORPLAN");
			ps.setString(5, "UPDDIS");
			ps.setString(6, comment);
			ps.setString(7, null);
			ps.setString(8, TutoringplanUtils.getNetworkCode());
			ps.setString(9, null);
			return ps.executeUpdate(); 
    }
  
}
