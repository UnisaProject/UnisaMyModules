package za.ac.unisa.lms.tools.honsadmission.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.honsadmission.forms.Application;
import za.ac.unisa.lms.tools.honsadmission.forms.PrevQual;
import za.ac.unisa.lms.tools.honsadmission.forms.Qualification;
import za.ac.unisa.lms.tools.honsadmission.forms.Referral;
import za.ac.unisa.lms.tools.honsadmission.forms.ApplicationPeriod;
import za.ac.unisa.lms.tools.honsadmission.forms.SignOffAccess;
import za.ac.unisa.lms.tools.honsadmission.forms.StuAdmRef;
import za.ac.unisa.lms.tools.honsadmission.forms.Student;
import za.ac.unisa.lms.tools.honsadmission.exception.*;


public class HonsAdmissionDAO extends StudentSystemDAO {
	
public List<SignOffAccess> getUserSignOffAccessList(Integer persno) throws Exception {
		
		List<SignOffAccess> accessList = new ArrayList<SignOffAccess>(); 
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	String sql = 	"select mk_qual_code, nvl(mk_spes_code,' ') as specCode, final_flag, stand_in,type " + 
									" from qsprout" +
									" where staff_number=?" +
									" and type='HAD'" +
									" order by mk_qual_code,mk_spes_code";
	
			 ps = connection.prepareStatement(sql);
			 	ps.setInt(1, persno);			
				
				rs = ps.executeQuery();	

			while (rs.next()) {
				SignOffAccess record = new SignOffAccess();
				record.setQualCode(rs.getString("mk_qual_code").trim());
				record.setSpecCode(rs.getString("specCode"));
				record.setLevel(rs.getString("final_flag").trim());
				record.setStandInInd(replaceNull(rs.getString("stand_in")));
				record.setType(rs.getString("type").trim());
				accessList.add(record);
			}			
		}
		catch (Exception ex) {		
			throw new Exception("HonsAdmissionDAO: Error reading UserSignOffAccessList / " + ex);
		}finally {
			try {
				if (connection!=null){connection.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}	
		return accessList;
	}



	public List<Referral> getUserAppReviewList(SignOffAccess signOffAccess, ApplicationPeriod appPeriod) throws Exception {
		
		List<Referral> reviewList = new ArrayList<Referral>(); 
		String referralType ="";
		if (signOffAccess.getType().equalsIgnoreCase("HAD")){
			referralType="R";
		}else{
			referralType="AR";
		}
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	String sql = 	"select academic_year,application_period,mk_student_nr,initials,surname,mk_title," +
					 				" referral,choice_nr,refer_level,seq_nr" +
					 				" from stuadmref, stu" +
					 				" where academic_year=?" +
					                " and application_period=?" +
					 				" and mk_qual_code=?" +
					                " and speciality_code=?" +
					                " and refer_level=?" +
					                " and referral=?" +
//					                " and referral in ('R','AR')" +
					                " and stu.nr=stuadmref.mk_student_nr" +
					                " and (sign_off_persno is null or sign_off_persno =0)";									
	
			 ps = connection.prepareStatement(sql);
			 
                ps.setShort(1, appPeriod.getAcademicYear());
                ps.setShort(2, appPeriod.getPeriod());
			 	ps.setString(3, signOffAccess.getQualCode());
			 	ps.setString(4, signOffAccess.getSpecCode());
			 	ps.setString(5, signOffAccess.getLevel());
			 	ps.setString(6, referralType);			
				
				rs = ps.executeQuery();	
	
			while (rs.next()) {
				
				Referral record = new Referral();
				ApplicationPeriod period = new ApplicationPeriod();
				
				period.setAcademicYear(rs.getShort("academic_year"));
				period.setPeriod(rs.getShort("application_period"));
				record.setApplicationPeriod(period);
				record.setQualCode(signOffAccess.getQualCode());
				record.setSpecCode(signOffAccess.getSpecCode());
				record.setType(rs.getString("referral"));
				record.setLevel(rs.getString("refer_level"));
				record.setChoice(rs.getShort("choice_nr"));
				record.setSeqNr(rs.getShort("seq_nr"));
				Student student = new Student();
				student.setNumber(rs.getInt("mk_student_nr"));
				student.setInitials(replaceNull(rs.getString("initials")));
				student.setSurname(replaceNull(rs.getString("surname")));
				student.setTitle(replaceNull(rs.getString("mk_title")));
				record.setStudent(student);
				reviewList.add(record);
			}	
			
		}
		catch (Exception ex) {		
			throw new Exception("HonsAdmissionDAO: Error reading UserAppReviewList / " + ex);
		}finally {
			try {
				if (connection!=null){connection.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}		
		return reviewList;
	}
	
public List<StuAdmRef> getAcademicRefferals(Referral referral) throws Exception {
	
	List<StuAdmRef> acadRefList = new ArrayList<StuAdmRef>();
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
		 try {	String sql = "select referral,seq_nr,mk_qual_code,GRD.ENG_DESCRIPTION as qualDesc,stuadmref.speciality_code,QUASPC.ENGLISH_DESCRIPTIO as specDesc," +
				 " to_char(refer_on,'YYYY-MM-DD HH24:MI') as referDate,refer_by,usr.name as referName,refer_level,refer_to_email,note_to_academic,recommendation,recommend_comment," +
				 " to_char(sign_off_timestamp,'YYYY-MM-DD HH24:MI') as signOffDate,sign_off_comment," +
				 " STAFF.TITLE || ' ' || STAFF.INITIALS || ' ' || STAFF.SURNAME as signOffStaff,sign_off_persno" +
				 " from stuadmref,grd,quaspc,staff,usr" +
				 " where STUADMREF.ACADEMIC_YEAR=?" +
				 " and STUADMREF.APPLICATION_PERIOD=?" +
				 " and STUADMREF.MK_STUDENT_NR=?" + 
				 " and STUADMREF.CHOICE_NR=?" +
				 " and grd.code=STUADMREF.MK_QUAL_CODE" +
				 " and QUASPC.MK_QUALIFICATION_C(+)=STUADMREF.MK_QUAL_CODE" +
				 " and QUASPC.SPECIALITY_CODE(+)=STUADMREF.SPECIALITY_CODE" +
				 " and STAFF.PERSNO(+)=STUADMREF.SIGN_OFF_PERSNO" +
				 " and usr.nr(+) = stuadmref.refer_by" +
				 " order by STUADMREF.REFER_ON ";									

		 ps = connection.prepareStatement(sql);
		 
            ps.setShort(1, referral.getApplicationPeriod().getAcademicYear());
            ps.setShort(2, referral.getApplicationPeriod().getPeriod());
		 	ps.setInt(3, referral.getStudent().getNumber()); 
		 	ps.setShort(4, referral.getChoice());
			
			rs = ps.executeQuery();	

		while (rs.next()) {
			
			StuAdmRef record = new StuAdmRef();
			Qualification qual = new Qualification();
			
			qual.setQualCode(replaceNull(rs.getString("mk_qual_code")));
			qual.setQualDesc(replaceNull(rs.getString("qualDesc")));
			qual.setSpecCode(replaceNull(rs.getString("speciality_code")));
			qual.setSpecDesc(replaceNull(rs.getString("specDesc")));
			record.setQualification(qual);
			record.setReferType(rs.getString("referral"));
			record.setReferDate(replaceNull(rs.getString("referDate")));
			record.setReferLevel(rs.getString("refer_level"));			
			record.setReferUser(rs.getString("refer_by").trim());	
			String referName = replaceNull(rs.getString("referName"));
			if (referName.equalsIgnoreCase("")){
//				record.setReferUser("staff");  //debug do nothing
			}else{
				record.setReferUser(referName);
			}
//			if (record.getReferUser().length()<7){
//				record.setReferUser("Admin Advisor");
//			}
			record.setReferEmail(replaceNull(rs.getString("refer_to_email")));
			record.setNoteToAcademic(replaceNull(rs.getString("note_to_academic")));
			record.setRecommendation(replaceNull(rs.getString("recommendation")));
			record.setRecommendationComment(replaceNull(rs.getString("recommend_comment")));
			record.setSeqNr(rs.getShort("seq_nr"));
			record.setSignOffComment(replaceNull(rs.getString("sign_off_comment")));
			record.setSignOffDate(replaceNull(rs.getString("signOffDate")));
			record.setSignOffPersno(replaceNull(rs.getString("sign_off_persno")));
			record.setSignOffName(replaceNull(rs.getString("signOffStaff")));
			
			acadRefList.add(record);
		}	
		
	}
	catch (Exception ex) {		
		throw new Exception("HonsAdmissionDAO: Error reading AcademicRefferals / " + ex);
	}finally {
		try {
			if (connection!=null){connection.close();}
			if (ps!=null){ps.close();}
			if (rs!=null){rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	return acadRefList;
}

public List<Referral> getStudentReferrals(ApplicationPeriod appPeriod,int studentNr)  throws Exception {
		
		List<Referral> referralList = new ArrayList<Referral>(); 
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		Connection connection = jdt.getDataSource().getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
			 try {	String sql = 	"select academic_year,application_period,mk_student_nr,initials,surname,mk_title," +
					 				" referral,choice_nr,refer_level,seq_nr,mk_qual_code,speciality_code" +
					 				" from stuadmref, stu" +
					 				" where academic_year=?" +
					                " and application_period=?" +
					 				" and mk_student_nr=?" +
					                " and referral in ('R','AR')" +
					                " and stu.nr=stuadmref.mk_student_nr";

			 ps = connection.prepareStatement(sql);
			 
                ps.setShort(1, appPeriod.getAcademicYear());
                ps.setShort(2, appPeriod.getPeriod());
                ps.setInt(3, studentNr);

				rs = ps.executeQuery();	
	
			while (rs.next()) {
				
				Referral record = new Referral();
				ApplicationPeriod period = new ApplicationPeriod();
				
				period.setAcademicYear(rs.getShort("academic_year"));
				period.setPeriod(rs.getShort("application_period"));
				record.setApplicationPeriod(period);
				record.setQualCode(rs.getString("mk_qual_code"));
				record.setSpecCode(rs.getString("speciality_code"));
				record.setType(rs.getString("referral"));
				record.setLevel(rs.getString("refer_level"));
				record.setChoice(rs.getShort("choice_nr"));
				record.setSeqNr(rs.getShort("seq_nr"));
				Student student = new Student();
				student.setNumber(rs.getInt("mk_student_nr"));
				student.setInitials(replaceNull(rs.getString("initials")));
				student.setSurname(replaceNull(rs.getString("surname")));
				student.setTitle(replaceNull(rs.getString("mk_title")));
				record.setStudent(student);
				referralList .add(record);
			}	
			
		}
		catch (Exception ex) {		
			throw new Exception("HonsAdmissionDAO: Error reading  getStudentReferrals / " + ex);
		}finally {
			try {
				if (connection!=null){connection.close();}
				if (ps!=null){ps.close();}
				if (rs!=null){rs.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}		
		return referralList;
	}
	
public List<PrevQual> getPrevUnisaQualifications(Integer studentNr) throws Exception {
	
	List<PrevQual> qualList = new ArrayList<PrevQual>(); 
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
		 try {	String sql = 	"select mk_qualification_c,actual_completion, grd.eng_description, status_code" + 
								" from stuaca, grd" +
								" where stuaca.mk_student_nr=?" +
								" and stuaca.status_code in ('CO','IN','FI')" +
								" and grd.code=stuaca.mk_qualification_c" +
								" order by actual_completion";

		 ps = connection.prepareStatement(sql);
		 	ps.setInt(1, studentNr);			
			
			rs = ps.executeQuery();	

		while (rs.next()) {
			PrevQual record = new PrevQual();
			record.setQualification(replaceNull(rs.getString("eng_description")));
			record.setInstitution("Unisa");			
			record.setAccreditationSource("");
			record.setYearCompleted(replaceNull(rs.getString("actual_completion")));
			String status = (replaceNull(rs.getString("status_code")));
			if (status.equalsIgnoreCase("IN")){
				record.setYearCompleted("incomplete");
			}
			if (status.equalsIgnoreCase("FI")){
				record.setYearCompleted("final year");
			}
			record.setComment("");
			qualList.add(record);
		}			
	}
	catch (Exception ex) {		
		throw new Exception("HonsAdmissionDAO: Error reading PrevUnisaQualifications / " + ex);
	}finally {
		try {
			if (connection!=null){connection.close();}
			if (ps!=null){ps.close();}
			if (rs!=null){rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	return qualList;
}

public List<PrevQual> getPrevOtherQualifications(Integer studentNr) throws Exception {
	
	List<PrevQual> qualList = new ArrayList<PrevQual>(); 
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	PreparedStatement ps = null;
	ResultSet rs = null;
		 try {	String sql = 	"select year_completed,completed_ind,institution,other_inst_qual,accredit_source," +
				 				" gencod.eng_description as accrediSourceDesc, mk_unk,unk.eng_name as unkName,other_comment" + 
								" from stuprev,unk,gencod" +
								" where stuprev.mk_student_nr=?" +
								" and stuprev.mk_unk=unk.code(+)" +
//								" and stuprev.completed_ind='Y'" +
								" and gencod.fk_gencatcode(+)=277" +
								" and gencod.code(+)=stuprev.accredit_source" +
								" order by year_completed";

		 ps = connection.prepareStatement(sql);
		 	ps.setInt(1, studentNr);			
			
			rs = ps.executeQuery();	

		while (rs.next()) {
			PrevQual record = new PrevQual();
			record.setQualification(replaceNull(rs.getString("other_inst_qual")).trim());
			record.setInstitution(replaceNull(rs.getString("institution")).trim());
			if (record.getInstitution()==null || record.getInstitution().equalsIgnoreCase("")){
				record.setInstitution(replaceNull(rs.getString("unkName")).trim());
			}
			record.setAccreditationSource(replaceNull(rs.getString("accrediSourceDesc")).trim());
			String competedInd = (replaceNull(rs.getString("completed_ind")));
			if (competedInd.equalsIgnoreCase("Y")){
				record.setYearCompleted(replaceNull(rs.getString("year_completed")).trim());
			}else{
				record.setYearCompleted("incomplete");
			}			
			record.setComment(replaceNull(rs.getString("other_comment")));
			qualList.add(record);
		}			
	}
	catch (Exception ex) {		
		throw new Exception("HonsAdmissionDAO: Error reading getPrevOtherQualifications / " + ex);
	}finally {
		try {
			if (connection!=null){connection.close();}
			if (ps!=null){ps.close();}
			if (rs!=null){rs.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	return qualList;
}

public void signOffApplication(Application appl,Integer userPersno) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	Connection connection = jdt.getDataSource().getConnection();
	connection.setAutoCommit(false);	
	PreparedStatement ps = null;	
	String sql = "";
		 
	try {	
			sql = "update stuadmref"							
					+ " set recommendation = ?,"
					+ " recommend_comment = ?,"
					+ " sign_off_persno = ?,"
					+ " sign_off_timestamp = systimestamp,"		
					+ " sign_off_comment = ?"			
					+ " where academic_year = ?"
					+ " and mk_student_nr = ?"
					+ " and application_period = ?"
					+ " and choice_nr = ?"
					+ " and referral = ? "
					+ " and seq_nr = ?"
			 		+ " and (sign_off_persno is null or sign_off_persno =0)";	
					
				ps = connection.prepareStatement(sql);
			
				ps.setString(1,appl.getSignOff().getRecommendation().trim());
				ps.setString(2,appl.getSignOff().getRecommendationComment().trim());
				ps.setInt(3,userPersno); 
				ps.setString(4,appl.getSignOff().getComment().trim());				
				ps.setShort(5,appl.getApplicationPeriod().getAcademicYear());
				ps.setInt(6,appl.getStudent().getNumber());				
				ps.setShort(7,appl.getApplicationPeriod().getPeriod());
				ps.setShort(8,appl.getChoice());
				ps.setString(9,appl.getRefferal().getType());
				ps.setShort(10,appl.getRefferal().getSeqNr());
				
				int updateCount = 0;
				updateCount = ps.executeUpdate(); 
				ps.close();				
		
				//update stuapq status to FS
				//to be done	
				
				if (updateCount==0){
					//No records updated
					throw new SignOffException("Application has already been reviewed by another user.");
				}else{
					sql = "update stuapq"
							+ " set refer_status=?"
							+ " where academic_year=?"
							+ " and application_period=?"
							+ " and mk_student_nr=?"
							+ " and choice_nr=?";
						
						ps = connection.prepareStatement(sql);
						
						ps.setString(1,"FS");
						ps.setShort(2,appl.getApplicationPeriod().getAcademicYear());
						ps.setShort(3,appl.getApplicationPeriod().getPeriod());
						ps.setInt(4,appl.getStudent().getNumber());		
						ps.setShort(5,appl.getChoice());
						
						ps.executeUpdate(); 
						ps.close();	
						
						//write action log entry
						StringBuffer actionComment = new StringBuffer(appl.getQualification().getQualCode());
						actionComment.append(" ");
						actionComment.append(appl.getQualification().getSpecCode());
						actionComment.append(" (");
						actionComment.append(appl.getSignOff().getRecommendationDesc());
						actionComment.append(")");
						
						sql = "insert into stuapl (academic_year,application_period,mk_student_number,"
							  + "seq_nr,"
							  +	"action_code_gc272,choice_nr,action_timestamp,action_public,action_comment,qual_code,pers_no,mk_user_number,user_network_id)" 
							  +	" values (?,?,?,"
						      + "(select nvl(max(b.seq_nr),0) + 1 from stuapl b where b.academic_year = ? and b.application_period=? and b.mk_student_number=?),"
							  + "?,?,SYSTIMESTAMP,?,?,?,?,0,' ')";
							   
						ps = connection.prepareStatement(sql);
						
						ps.setShort(1,appl.getApplicationPeriod().getAcademicYear());   //academic_year
						ps.setShort(2,appl.getApplicationPeriod().getPeriod());         //application_period
						ps.setInt(3,appl.getStudent().getNumber());                     //student_number			
						ps.setShort(4,appl.getApplicationPeriod().getAcademicYear());   //academic_year
						ps.setShort(5,appl.getApplicationPeriod().getPeriod());         //application_period
						ps.setInt(6,appl.getStudent().getNumber());                     //student_number		
						ps.setString(7,"REFBCK"); 					                    //action_code_gc272
						ps.setShort(8,appl.getChoice()); 		                      	//choice_nr
						ps.setString(9,"N");                                            //action_public
						ps.setString(10,actionComment.toString());                                           //action_comment
						ps.setString(11, appl.getRefferal().getQualCode()); 			//qual_code
						ps.setInt(12, userPersno); 			                            //persno
						ps.executeUpdate();
						ps.close();
					
						connection.commit();
				}
				
	}	
	catch (SignOffException e){
		throw new SignOffException("Application has already been reviewed by another user.");
	}
	catch (Exception e) {
		if (connection!=null){connection.rollback();}		
		throw new Exception("HonsAdmissionDAO: Error in signOffApplication(UPDATE STUADMREF or INSERT STUAPL) / " + e, e);
	} finally {		
		try { 
			if (connection!=null){
				connection.setAutoCommit(true);
				connection.close();					
			}
			if (ps!=null){ps.close();}
		} catch (Exception e) {	
				e.printStackTrace();
			}
		} 
}


	public Student getStudent(int studentNr) throws Exception {
		
		Student student = new Student();
		
		PreparedStatement pst = null;
		Connection con = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    con = jdt.getDataSource().getConnection();		
		    String sql =
		        "select nr,surname,initials,mk_title from stu where nr = ?";
		    pst = con.prepareStatement(sql);	
		    pst.setInt(1, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	student.setNumber(Integer.parseInt(rs.getString("nr")));
		    	student.setSurname(replaceNull(rs.getString("surname")).trim());  
		    	student.setInitials(replaceNull(rs.getString("initials")).trim());
		    	student.setTitle(replaceNull(rs.getString("mk_title")).trim());
		    }		     
		    return student;
	    }
		    catch (Exception ex) {
				throw new Exception("HonsAdmissionDAO  : : Error reading table stu/ " + ex, ex);				
		    } finally {
				try {
					if (con!=null){con.close();}
					if (pst!=null){pst.close();}
					if (rs!=null){rs.close();}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
	}	
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue.trim();
	}

}
