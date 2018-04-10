package za.ac.unisa.lms.tools.tutoringplan.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;
public class TutoringModeDAO extends AuditTrailDAO {
	
	  public boolean isLecAssignedAsTutor(short  acadYear,short semester,String studyUnit,String tutorMode)throws Exception{
		                TutorDAO dao=new TutorDAO();
		                return dao.isLecAssignedAsTutor(acadYear, semester, studyUnit,tutorMode);
	  }
	  public void addTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode)throws Exception {
		    Connection connection=null;
	    	String sql = "insert into suntutmode (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
		   	"optionality,group_choice_gc182,tutor_stu_ratio,max_groups_per_tut,incl_lect_as_tutor,tut_contact_gc184,alloc_crit_gc183)" +
		   	" values (?,?,?,?,null,?,?,?,?,?,?)";
	        try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				connection = jdt.getDataSource().getConnection();
				connection.setAutoCommit(false);
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, tutoringMode.getTutorMode());
				ps.setString(5, tutoringMode.getGroupChoice());
				ps.setInt(6, Integer.parseInt(tutoringMode.getTutorStuRatio()));
				ps.setInt(7, Integer.parseInt(tutoringMode.getMaxGroupsPerTutor()));
				ps.setString(8, tutoringMode.getIncludeLectAsTutors());
				ps.setString(9, tutoringMode.getTutorContact());
				ps.setString(10, tutoringMode.getAllocationCriteria());
				ps.executeUpdate();
				String comment="CREATE MODE:"+getLogComment(tutoringMode);;
				int returnVal=addAuditTrail(connection,comment);
				if(returnVal<1){
					 throw new Exception("TutoringPlanDAO: Error inserting audit log/ " );
				}
				commitChanges(connection);
			}
			catch (Exception ex) {
				    rollbackChanges(connection);
			        throw new Exception("TutoringPlanDAO: Error inserting Tutoring mode SUNTUTMODE / " + ex);
			}	
	    }
	   private void rollbackChanges( Connection connection)throws Exception {
		               connection.rollback();
			           connection.setAutoCommit(true);
			           connection.close();
	   }
	   private void commitChanges( Connection connection)throws Exception {
		               connection.commit();
			           connection.setAutoCommit(true);
			           connection.close();
      }
	 public void updateTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode)throws Exception {
		 Connection connection=null;
	    	String sql = "update suntutmode" +
		   	" set optionality=null," +
		   	" group_choice_gc182=?," +
		   	" tutor_stu_ratio=?," +
		   	" max_groups_per_tut=?," +
		   	" incl_lect_as_tutor=?," +
		   	" tut_contact_gc184=?," +
		   	" alloc_crit_gc183=?" +
		    " where mk_academic_year =?" +
	        " and semester=?" + 
	        " and mk_study_unit_code=?" +
	        " and tutor_mode_gc181=?";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				connection = jdt.getDataSource().getConnection();
				connection.setAutoCommit(false);
				PreparedStatement ps = connection.prepareStatement(sql);
				
				ps.setString(1, tutoringMode.getGroupChoice());
				ps.setInt(2, Integer.parseInt(tutoringMode.getTutorStuRatio()));
				ps.setInt(3, Integer.parseInt(tutoringMode.getMaxGroupsPerTutor()));
				ps.setString(4, tutoringMode.getIncludeLectAsTutors());
				ps.setString(5, tutoringMode.getTutorContact());
				ps.setString(6, tutoringMode.getAllocationCriteria());
				ps.setShort(7, acadYear); 
				ps.setShort(8, semester); 
				ps.setString(9, studyUnit);
				ps.setString(10, tutoringMode.getTutorMode());
				ps.executeUpdate(); 
				String comment="EDIT MODE:"+getLogComment(tutoringMode);
				int returnVal=addAuditTrail(connection,comment);
				if(returnVal<1){
					 throw new Exception("TutoringPlanDAO: Error inserting audit log/ " );
				}
				commitChanges(connection);
			}
			catch (Exception ex) {
				    rollbackChanges(connection);
				    throw new Exception("TutoringPlanDAO: Error updating Tutoring mode SUNTUTMODE / " + ex);
			}
	    }
	  public boolean removeTutoringMode(String studyUnit, Short acadYear, Short semester,TutoringMode tutoringMode) throws Exception {
		 Connection connection=null;
			String sql = "select count(*) " +
				 "from tustgr " +
				 " where mk_academic_year=" + acadYear +
				 " and semester=" + semester +
				 " and mk_study_unit_code='" + studyUnit + "'" +
				 " and tutor_mode_gc181='" + tutoringMode.getTutorMode() + "'";
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				sql = "delete from suntutmode" + 
					" where mk_academic_year=?" +
					" and semester=?" + 
					" and mk_study_unit_code=?" +
				    " and tutor_mode_gc181=?";
				try {
					jdt = new JdbcTemplate(getDataSource());
					connection = jdt.getDataSource().getConnection();
					connection.setAutoCommit(false);
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setShort(1, acadYear); 
					ps.setShort(2, semester); 
					ps.setString(3, studyUnit);
					ps.setString(4, tutoringMode.getTutorMode());
					ps.executeUpdate();
					String comment="DELETE MODE:"+getLogComment(tutoringMode);
					addAuditTrail(connection,comment);
					int returnVal=addAuditTrail(connection,comment);
					if(returnVal<1){
						 throw new Exception("TutoringPlanDAO: Error inserting audit log/ ");
					}
					commitChanges(connection);
				}
				catch (Exception ex) {
					   rollbackChanges(connection);
					   throw new Exception("TutoringPlanDAO: Error deleting TUSTGR / " + ex);
			    }	
				return true;
			} else {
				return false;
			}
		}
		
	 public String  hasGroupingStarted(short  acadYear,short semester, String studyUnit,String mode) throws Exception{
	        String sql="select *  from tustgr  where   mk_academic_year="+acadYear+
	      		  " and mk_study_unit_code='"+studyUnit+"'  and semester = " + semester+
	      		  " and tutor_mode_gc181='" + mode + "'";
	          	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    			List queryList = jdt.queryForList(sql);
	    			List  tutorList=new ArrayList();
	    			if((queryList==null)||
	    					(queryList.size()==0)){
	    				          return "N";
	    			}else{
	    				return "Y";
	    			}
	 }

		 private  String  getLogComment(TutoringMode tutoringMode){
			                return  tutoringMode.getTutorMode()+" RATIO:"+tutoringMode.getTutorStuRatio()+
				                  " MX_GROUPS: "+tutoringMode.getMaxGroupsPerTutor()+" INCL LECT:"+tutoringMode.getIncludeLectAsTutors();
		 }
}