package za.ac.unisa.lms.tools.finalmarkconcession.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;	

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.GradeBookObjectRecord;
import za.ac.unisa.lms.tools.finalmarkconcession.forms.GradeBookGradeRecord;

public class FinalMarkConcessionSakaiDAO extends SakaiDAO {	
	
	public List<String> getUserFiSites(String userId) throws Exception {	
		
		List<String> userFiSites = new ArrayList<String>();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection con = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	
				 String sql = "    select SS.title as SITE_TITLE, SSU.site_id, SUIM.eid as USERNAME, SSU.permission, SSU.user_id" 
						 		+ " from  sakai_site_user SSU, sakai_user_id_map SUIM , sakai_site SS" 
						 		+ " where upper(SUIM.eid) = ?"
						 		+ " and   SSU.user_ID = suim.user_id"
						 		+ " and   SS.site_id = SSU.site_id"
						 		+ " order by SS.title";
				 
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
			
				rs = ps.executeQuery();
				String site = "";
				
				while (rs.next()) {		
					
					site = replaceNull(rs.getString("site_id"));
					userFiSites.add(site);					
				}

			} catch (Exception ex) {
				throw new Exception(
						"FinalMarkConcessionSakaiDAO  : Error reading User FI Sites / " + ex, ex);
			} finally {
				try {
					if (con!=null){con.close();}
					if (ps!=null){ps.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			 return userFiSites;
	 }
	
	
public List<GradeBookObjectRecord> getSiteAssessments(String siteId) throws Exception {	
		
		List<GradeBookObjectRecord> siteAssessments = new ArrayList<GradeBookObjectRecord>();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection con = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	
				 String sql = "select   GBGOT.id as ID, GBGT.gradebook_uid, GBGOT.name,GBGOT.points_possible,GBGOT.gradebook_id"
						 		+ " from gb_gradable_object_t GBGOT, gb_gradebook_t GBGT"
						 		+ " where  GBGOT.gradebook_id = GBGT.id"
						 		+ " and GBGT.gradebook_uid = ?"
						 		+ " and GBGOT.points_possible is not null"		//Ask Francette??
				 				+ " and GBGOT.points_possible <> 0.0";	        //Ask Francette??
				 
				ps = con.prepareStatement(sql);
				ps.setString(1, siteId);
			
				rs = ps.executeQuery();			
				GradeBookObjectRecord objectRecord = new GradeBookObjectRecord();
				
				while (rs.next()) {		
					objectRecord = new GradeBookObjectRecord();
					objectRecord.setId(rs.getInt("id"));
					objectRecord.setName(replaceNull(rs.getString("name")));
					objectRecord.setGradeBookId(rs.getInt("gradebook_id"));			
					objectRecord.setPointsPossible(rs.getFloat("points_possible"));   
					siteAssessments.add(objectRecord);					
				}

			} catch (Exception ex) {
				throw new Exception(
						"FinalMarkConcessionSakaiDAO  : Error reading gradebook Assessments for a site / " + ex, ex);
			} finally {
				try {
					if (con!=null){con.close();}
					if (ps!=null){ps.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			 return siteAssessments;
	 }
	 
	public List<GradeBookGradeRecord> getStudentAssessmentMarks(int selectedObjectId) throws Exception {	
		
		List<GradeBookGradeRecord> studentAssessmentMarks = new ArrayList<GradeBookGradeRecord>();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection con = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null; 
		
		 try {	
			 String sql = "select GBR.POINTS_EARNED,"
					 + " (select sakai_user_id_map.eid from sakai_user_id_map where sakai_user_id_map.user_id = GBR.STUDENT_ID) AS studentNumber,"
					 + " (GBR.POINTS_EARNED/GBT.POINTS_POSSIBLE*100) AS PERCENTAGE "
					 + " from GB_GRADE_RECORD_T GBR, GB_GRADABLE_OBJECT_T GBT"
					 + " where GBR.GRADABLE_OBJECT_ID = ?"
					 + " and GBT.removed <> 1"
					 + " and GBR.GRADABLE_OBJECT_ID = GBT.ID"
					 + " and GBR.POINTS_EARNED is not null"
					 + " AND (GBR.POINTS_EARNED / GBT.POINTS_POSSIBLE * 100 BETWEEN -1 AND 101)"; 
			 
			ps = con.prepareStatement(sql);
			ps.setInt(1, selectedObjectId);
		
			rs = ps.executeQuery();		
			GradeBookGradeRecord gradeRecord = new GradeBookGradeRecord();
			
			while (rs.next()) {		
				gradeRecord = new GradeBookGradeRecord();
				String user = replaceNull(rs.getString("studentNumber"));
				if (isInteger(user)){
					gradeRecord.setStudentNumber(Integer.parseInt(user));
				} else{
					continue;
				}
				Double markPercentage = rs.getDouble("percentage") + 0.5;
				gradeRecord.setMarkPercentage(markPercentage.intValue());
				studentAssessmentMarks.add(gradeRecord);					
			}

		} catch (Exception ex) {
			throw new Exception(
					"FinalMarkConcessionSakaiDAO  : Error reading gradebook Assessment Marks / " + ex, ex);
		} finally {
			try {
				if (con!=null){con.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 return studentAssessmentMarks;
}

	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){
		}else{
			stringValue=object.toString().trim();
		}
	    return stringValue;
	}
	
	public boolean isInteger(String stringValue) {
		try
		{
			Integer i = Integer.parseInt(stringValue);
			return true;
		}	
		catch(NumberFormatException e)
		{}
		return false;
	}

}
