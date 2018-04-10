package za.ac.unisa.lms.tools.tutorstudentgrouping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.dao.StudentSystemGeneralDAO;
import za.ac.unisa.lms.dao.general.PersonnelDAO;
import za.ac.unisa.lms.dao.general.UserDAO;
import za.ac.unisa.lms.tools.tutorstudentgrouping.forms.*;
import za.ac.unisa.lms.dao.Gencod;

public class TutorStudentGroupingDAO extends StudentSystemDAO {
	
public List<Tutor> getTutorListXX(String studyUnit, Short acadYear, Short semester, String includeLecturers) throws Exception {
		
		List<Tutor> tutorList = new ArrayList<Tutor>(); 
		
		//int year = Calendar.getInstance().get(Calendar.YEAR);
		
		String sql = "select a.PERSNO as persno, a.ACCESS_LEVEL as role, b.tutorMode as tutMode,DECODE(b.nrGroups,null,0,b.nrGroups) as groupsAllocated," +
				" DECODE(b.nrStudents,null,0,b.nrStudents) as totalStudentsAllocated" + 
				" from usrsun a,(select mk_tutor_persno as tutorPersno, tutor_mode_gc181 as tutorMode, count(distinct group_nr) as nrGroups ,count(distinct mk_student_nr) as nrStudents" +
				" from tustgr" +
				" where inactive_date is null" +
				" group by mk_tutor_persno,tutor_mode_gc181) b" +
				" where a.mk_academic_year=" + acadYear +
				" and a.mk_semester_period=" + semester +
				" and a.mk_study_unit_code='" + studyUnit + "'" +
				" and a.system_type='L'";
		
		if (includeLecturers.equalsIgnoreCase("Y")){
			sql = sql.trim() + " and a.access_level in ('PRIML','SECDL','TUTOR')";
		}else {
			sql = sql.trim() + " and a.access_level ='TUTOR'";
		}
		
		sql = sql.trim() + " and a.persno = b.tutorPersno(+)";

		
      	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			PersonnelDAO dao = new PersonnelDAO();

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Person person = new Person();				
				person = dao.getPersonnelFromSTAFF(Integer.parseInt(data.get("persno").toString()));
				Calendar currentDate = Calendar.getInstance();
				Date nowDate = currentDate.getTime();
				boolean active = true;
				
				if (person.getName()!=null && !person.getName().trim().equalsIgnoreCase("")){
					if (person.getResignDate()!=null 
							&& !person.getResignDate().equalsIgnoreCase("00010101") 
							&& !person.getResignDate().trim().equalsIgnoreCase("")){
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=person.getResignDate();
				        Date resignDate = (Date)formatter.parse(str_date); 
				        
				        if (!resignDate.after(nowDate)){
				        	active=false;
				        }
					}
					if (active){
						Tutor tutor = new Tutor();
						tutor.setPerson(person);
						tutor.setRole(data.get("role").toString());
						tutor.setNumberGroupsAllocated(Integer.parseInt(data.get("groupsAllocated").toString()));
						tutor.setTotalStudentsAllocated(Integer.parseInt(data.get("totalStudentsAllocated").toString()));
						tutor.setTutoringMode(replaceNull(data.get("tutMode")));
						tutor.setTutorModeDesc("");
						if (tutor.getTutoringMode()!=null && !tutor.getTutoringMode().equalsIgnoreCase("")){
							Gencod gencod = new Gencod();
							StudentSystemGeneralDAO studentDao = new StudentSystemGeneralDAO();
							gencod = studentDao.getGenCode("181", tutor.getTutoringMode());
							if (gencod!=null && gencod.getEngDescription()!=null){
								tutor.setTutorModeDesc(gencod.getEngDescription());
							}	
						}						
						
						tutorList.add(tutor);
					}					
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading TutorList / " + ex);
		}	
		return tutorList;
		
	}

public List<Tutor> getTutorList(String studyUnit, Short acadYear, Short semester, String includeLecturers) throws Exception {
	
	List<Tutor> tutorList = new ArrayList<Tutor>(); 
	
	//int year = Calendar.getInstance().get(Calendar.YEAR);
	
	String sql = "select a.PERSNO as persno, a.ACCESS_LEVEL as role, a.novell_user_id" + 
			" from usrsun a" +
			" where a.mk_academic_year=" + acadYear +
			" and a.mk_semester_period=" + semester +
			" and a.mk_study_unit_code='" + studyUnit + "'" +
			" and a.system_type='L'";
	
	if (includeLecturers.equalsIgnoreCase("Y")){
		sql = sql.trim() + " and a.access_level in ('PRIML','SECDL','TUTOR')";
	}else {
		sql = sql.trim() + " and a.access_level ='TUTOR'";
	}

	
  	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		PersonnelDAO dao = new PersonnelDAO();
		UserDAO userDao = new UserDAO();

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			Person person = new Person();
			if (data.get("persno")==null || Integer.parseInt(data.get("persno").toString())==0){
				String novellUserId = data.get("novell_user_id").toString();
				person = userDao.getUserFromSTAFF(novellUserId);
			}else {
				person = dao.getPersonnelFromSTAFF(Integer.parseInt(data.get("persno").toString()));
			}			
			Calendar currentDate = Calendar.getInstance();
			Date nowDate = currentDate.getTime();
			boolean active = true;
			
			if (person.getName()!=null && !person.getName().trim().equalsIgnoreCase("")){
				if (person.getResignDate()!=null 
						&& !person.getResignDate().equalsIgnoreCase("00010101") 
						&& !person.getResignDate().trim().equalsIgnoreCase("")){
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
					String str_date=person.getResignDate();
			        Date resignDate = (Date)formatter.parse(str_date); 
			        
			        if (!resignDate.after(nowDate)){
			        	active=false;
			        }
				}
				if (active){
					Tutor tutor = new Tutor();
					tutor.setPerson(person);
					tutor.setRole(data.get("role").toString());
					List<TutorStudentGroup> groupList = new ArrayList<TutorStudentGroup>();
					groupList = getGroupsAllocatedToTutor(studyUnit, acadYear, semester, tutor.getPerson());					
					tutor.setNumberGroupsAllocated(groupList.size());
					int studentTotal=0;
					String tutorMode="";
					String tutorDesc="";
					for (int j=0; j < groupList.size(); j++){
						TutorStudentGroup group = new TutorStudentGroup();
						group = (TutorStudentGroup)groupList.get(j);
						studentTotal = studentTotal + group.getTotalStudentsAllocated();	
						if (tutorMode.equalsIgnoreCase("")){
							tutorMode=group.getTutoringMode().getTutorMode();	
							tutorDesc=group.getTutoringMode().getTutorModeDesc();
						}else{
							if (!tutorMode.contains(group.getTutoringMode().getTutorMode())){
								tutorMode=tutorMode + ", " + group.getTutoringMode().getTutorMode();	
								tutorDesc=tutorDesc + ", " + group.getTutoringMode().getTutorModeDesc();
							}							
						}						
					}
					tutor.setTotalStudentsAllocated(studentTotal);
					tutor.setTutoringMode(tutorMode);
					tutor.setTutorModeDesc(tutorDesc);
					if (tutorAllocatedToDifferentModule(acadYear, semester, studyUnit, person)){
						//do nothing tutor already allocated to a group linked to a different module
					}else{
						tutorList.add(tutor);
					}					
				}					
			}				
		}
	}
	catch (Exception ex) {
		throw new Exception("TutorStudentGroupingDAO: Error reading TutorList / " + ex);
	}	
	return tutorList;
	
}

	public List<TutorStudentGroup> getGroupsAllocatedToTutor(String studyUnit, Short acadYear, Short semester,Person tutor) throws Exception {
			
			List<TutorStudentGroup> tutorStudentGroupList = new ArrayList<TutorStudentGroup>(); 
		
			String sql = "select tutor_mode_gc181,group_nr,alloc_crit_gc183,count(*) as totalStudent" + 
			" from tustgr" +
			" where mk_academic_year=" + acadYear +
			" and semester=" + semester +
			" and mk_study_unit_code='" + studyUnit + "'" +
			" and mk_tutor_persno=" + tutor.getPersonnelNumber() +
			" and inactive_date is null" +
			" group by tutor_mode_gc181,group_nr,alloc_crit_gc183" +
			" order by group_nr";

		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			PersonnelDAO dao = new PersonnelDAO();
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				TutorStudentGroup group = new TutorStudentGroup();
				group.setGroupNumber(Integer.parseInt(data.get("group_nr").toString()));
				String mode = replaceNull(data.get("tutor_mode_gc181"));				
				TutoringMode tutorMode = new TutoringMode();
				tutorMode = getTutoringMode(studyUnit, acadYear, semester, mode);
				group.setTutoringMode(tutorMode);
				group.setTotalStudentsAllocated(Integer.parseInt(data.get("totalStudent").toString()));
				group.setTutor(tutor);
				tutorStudentGroupList.add(group);
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading GroupsAllocatedToTutor / " + ex);
		}	
		return tutorStudentGroupList;
	}
	
	public List<Integer> getStudentsInGroup(Short acadYear, Short semester, String studyUnit, TutorStudentGroup group)throws Exception {
		
		List<Integer> studentList = new ArrayList<Integer>(); 
		
		String sql = "select mk_student_nr" + 
		" from tustgr" +
		" where mk_academic_year=" + acadYear +
		" and semester=" + semester +
		" and mk_study_unit_code='" + studyUnit + "'" +
		" and tutor_mode_gc181='" + group.getTutoringMode().getTutorMode() + "'" +
		" and group_nr=" + group.getGroupNumber() +
		" and inactive_date is null" +
		" order by mk_student_nr" ;

	
  	try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		PersonnelDAO dao = new PersonnelDAO();

		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			int studentNr = Integer.parseInt(data.get("mk_student_nr").toString());
			studentList.add(studentNr);
		}
	}
	catch (Exception ex) {
		throw new Exception("TutorStudentGroupingDAO: Error reading students in tutor-student group / " + ex);
	}	
	return studentList;
	}
	
	public boolean tutorAllocatedToDifferentModule(Short acadYear, Short semester, String studyUnit, Person tutor) throws Exception {
		
		String sql = "select count(*)" + 
		" from tustgr" +
		" where mk_academic_year=" + acadYear +
		" and (mk_study_unit_code<>'" + studyUnit + "'" +
		" and (semester =" + semester +
		" or semester in (0,6)))" +
		" and mk_tutor_persno=" + tutor.getPersonnelNumber() +
		" and inactive_date is null";

	
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<TutorStudentGroup> getAvailableTutorStudentGroups(String studyUnit, Short acadYear, Short semester) throws Exception {
		
		List<TutorStudentGroup> tutorStudentGroupList = new ArrayList<TutorStudentGroup>(); 
		
		String sql = "select a.group_nr as groupNr, a.mk_tutor_persno as persno, a.tutor_mode_gc181 as tutMode, b.access_level as role, b.novell_user_id,count(*) as totalStudent" + 
			" from tustgr a, usrsun b, staff c" +
			" where a.mk_academic_year=" + acadYear +
			" and a.semester=" + semester +
			" and a.mk_study_unit_code='" + studyUnit + "'" +
			" and a.mk_academic_year=b.mk_academic_year" +
			" and a.semester=b.mk_semester_period" +
			" and a.mk_study_unit_code=b.mk_study_unit_code" + 
			" and b.system_type='L'" +
			" and b.access_level in ('PRIML','SECDL','TUTOR')" +
			" and a.mk_tutor_persno=c.persno" +
			" and c.novell_user_id=b.novell_user_id" +
			" and a.inactive_date is null" +
			" group by a.group_nr,a.mk_tutor_persno,a.tutor_mode_gc181,b.access_level,b.novell_user_id" +
			" order by a.tutor_mode_gc181, a.group_nr";

		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			PersonnelDAO dao = new PersonnelDAO();
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Person person = new Person();
				UserDAO userDao = new UserDAO();
				if (data.get("persno")==null || Integer.parseInt(data.get("persno").toString())==0){
					String novellUserId = data.get("novell_user_id").toString();
					person = userDao.getUserFromSTAFF(novellUserId);
				}else {
					person = dao.getPersonnelFromSTAFF(Integer.parseInt(data.get("persno").toString()));
				}				
				Calendar currentDate = Calendar.getInstance();
				Date nowDate = currentDate.getTime();
				boolean active = true;				
				if (person.getName()!=null && !person.getName().trim().equalsIgnoreCase("")){
					if (person.getResignDate()!=null 
							&& !person.getResignDate().equalsIgnoreCase("00010101") 
							&& !person.getResignDate().trim().equalsIgnoreCase("")){
						SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
						String str_date=person.getResignDate();
				        Date resignDate = (Date)formatter.parse(str_date); 				        
				        if (!resignDate.after(nowDate)){
				        	active=false;
				        }
					}
					if (active){
						TutorStudentGroup group = new TutorStudentGroup();
						group.setTutor(person);
						group.setGroupNumber(Integer.parseInt(data.get("groupNr").toString()));
						group.setRole(data.get("role").toString());
						group.setTotalStudentsAllocated(Integer.parseInt(data.get("totalStudent").toString()));
						String mode = replaceNull(data.get("tutMode"));
						TutoringMode tutorMode = new TutoringMode();
						tutorMode = getTutoringMode(studyUnit, acadYear, semester, mode);
						group.setTutoringMode(tutorMode);
						tutorStudentGroupList.add(group);
//						if (tutorMode.getTutorStuRatio() > group.getTotalStudentsAllocated()){
//							tutorStudentGroupList.add(group);
//						}						
					}					
				}				
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading AvailableTutorStudentGroups / " + ex);
		}	
		return tutorStudentGroupList;
		
	}
	
	public void assignStudentToNewGroup(String studyUnit, Short acadYear, Short semester, Integer studentNr, TutorStudentGroup newStudentGroup)throws Exception {
				
		String sql = "insert into tustgr (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
	   	"group_nr,mk_student_nr,mk_tutor_persno,assign_date,inactive_date,alloc_crit_gc183,sms_sent_date,email_sent_date)" +
	   	" values (?,?,?,?,?,?,?,sysTimestamp,null,?,sysTimestamp,sysTimestamp)";
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			Connection connection = jdt.getDataSource().getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			   
			ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, newStudentGroup.getTutoringMode().getTutorMode());
			ps.setInt(5, newStudentGroup.getGroupNumber());
			ps.setInt(6, studentNr);
			ps.setInt(7, Integer.parseInt(newStudentGroup.getTutor().getPersonnelNumber()));
			ps.setString(8, newStudentGroup.getTutoringMode().getAllocationCriteria());
			ps.executeUpdate(); 
			
			connection.close();
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error assigning Student to a Group / " + ex);
		}	
	}
	
public void switchTutor(String studyUnit,Short acadYear,Short semester,SwitchGroupTutor switchGroup, String userId)throws Exception {
		
		Connection connection = null;
		String sql="";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);	
			
			//assign new tutor to first Group
				sql = "update tustgr set mk_tutor_persno=?,sms_sent_date=sysTimestamp,email_sent_date=sysTimestamp" +
				   " where mk_academic_year=?" +
				   " and semester=?" +
				   " and mk_study_unit_code=?" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?" +
				   " and inactive_date is null";
				   
				PreparedStatement ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(switchGroup.getTutorStudentGroup2().getTutor().getPersonnelNumber()));
				ps.setShort(2, acadYear); 
				ps.setShort(3, semester); 
				ps.setString(4, studyUnit);
				ps.setString(5, switchGroup.getTutorStudentGroup1().getTutoringMode().getTutorMode());
				ps.setInt(6, switchGroup.getTutorStudentGroup1().getGroupNumber());
				ps.executeUpdate(); 
				
				//insert record in tustgrlog for first group	 			
				sql = "insert into tustgrlog (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
		    	 "group_nr,action,comment0,reference_nr,updated_on,updated_by)" +
		    	 " values (?,?,?,?,?,?,?,?,sysTimestamp,?)";
		   
				ps = connection.prepareStatement(sql);	   
		   
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, switchGroup.getTutorStudentGroup1().getTutoringMode().getTutorMode());
				ps.setInt(5, switchGroup.getTutorStudentGroup1().getGroupNumber());
				ps.setString(6, "UPDATE");
				ps.setString(7, " ");
				ps.setInt(8, Integer.parseInt(switchGroup.getTutorStudentGroup2().getTutor().getPersonnelNumber()));
				ps.setString(9, userId);
	       	   	ps.executeUpdate();	
	       	   	
		       	sql = "select nvl(max(user_sequence),0)" +
	    	   		" from usrsun" +
	    	   		" where novell_user_id='" + switchGroup.getTutorStudentGroup2().getTutor().getNovellUserId() + "'" +
	    	   		" and mk_study_unit_code='" + studyUnit + "'" +
	    	   		" and system_type='C'";
	    	   
	    	   	jdt = new JdbcTemplate(getDataSource());
	    	   	int userSequence = jdt.queryForInt(sql) ;
	    	   	userSequence = userSequence + 1;
	       	   	
	       	   	//update usrsun record for group 1
				sql = "update usrsun set persno=?, novell_user_id=?, user_sequence=?" +
				   " where mk_academic_year=?" +
				   " and mk_semester_period=?" +
				   " and mk_study_unit_code=?" +
				   " and system_type='C'" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?";
				   
				ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(switchGroup.getTutorStudentGroup2().getTutor().getPersonnelNumber()));
				ps.setString(2, switchGroup.getTutorStudentGroup2().getTutor().getNovellUserId());
				ps.setInt(3, userSequence);
				ps.setShort(4, acadYear); 
				ps.setShort(5, semester); 
				ps.setString(6, studyUnit);
				ps.setString(7, switchGroup.getTutorStudentGroup1().getTutoringMode().getTutorMode());
				ps.setInt(8, switchGroup.getTutorStudentGroup1().getGroupNumber());
				ps.executeUpdate(); 
				
				//assign new tutor to second Group
				sql = "update tustgr set mk_tutor_persno=?,sms_sent_date=sysTimestamp,email_sent_date=sysTimestamp" +
				   " where mk_academic_year=?" +
				   " and semester=?" +
				   " and mk_study_unit_code=?" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?" +
				   " and inactive_date is null";
				   
				ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(switchGroup.getTutorStudentGroup1().getTutor().getPersonnelNumber()));
				ps.setShort(2, acadYear); 
				ps.setShort(3, semester); 
				ps.setString(4, studyUnit);
				ps.setString(5, switchGroup.getTutorStudentGroup2().getTutoringMode().getTutorMode());
				ps.setInt(6, switchGroup.getTutorStudentGroup2().getGroupNumber());
				ps.executeUpdate(); 
				
				//insert record in tustgrlog for second group	 			
				sql = "insert into tustgrlog (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
		    	 "group_nr,action,comment0,reference_nr,updated_on,updated_by)" +
		    	 " values (?,?,?,?,?,?,?,?,sysTimestamp,?)";
		   
				ps = connection.prepareStatement(sql);	   
		   
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, switchGroup.getTutorStudentGroup2().getTutoringMode().getTutorMode());
				ps.setInt(5, switchGroup.getTutorStudentGroup2().getGroupNumber());
				ps.setString(6, "UPDATE");
				ps.setString(7, " ");
				ps.setInt(8, Integer.parseInt(switchGroup.getTutorStudentGroup1().getTutor().getPersonnelNumber()));
				ps.setString(9, userId);
	       	   	ps.executeUpdate();	
	       	   	
		       	sql = "select nvl(max(user_sequence),0)" +
	 	   		" from usrsun" +
	 	   		" where novell_user_id='" + switchGroup.getTutorStudentGroup1().getTutor().getNovellUserId() + "'" +
	 	   		" and mk_study_unit_code='" + studyUnit + "'" +
	 	   		" and system_type='C'";
	 	   
		 	   	jdt = new JdbcTemplate(getDataSource());
		 	   	userSequence = jdt.queryForInt(sql) ;
		 	   	userSequence = userSequence + 1;
    	   	
	    	   	//update usrsun record for group 1
				sql = "update usrsun set persno=?, novell_user_id=?, user_sequence=?" +
				   " where mk_academic_year=?" +
				   " and mk_semester_period=?" +
				   " and mk_study_unit_code=?" +
				   " and system_type='C'" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?";
				   
				ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(switchGroup.getTutorStudentGroup1().getTutor().getPersonnelNumber()));
				ps.setString(2, switchGroup.getTutorStudentGroup1().getTutor().getNovellUserId());
				ps.setInt(3, userSequence);
				ps.setShort(4, acadYear); 
				ps.setShort(5, semester); 
				ps.setString(6, studyUnit);
				ps.setString(7, switchGroup.getTutorStudentGroup2().getTutoringMode().getTutorMode());
				ps.setInt(8, switchGroup.getTutorStudentGroup2().getGroupNumber());
				ps.executeUpdate(); 
					
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();  
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("TutorStudentGroupingDAO : Data has been rollback, Error switching tutors / " + ex,ex);
			}
		}
		
	}
	
	public void replaceTutor(String studyUnit,Short acadYear,Short semester,List<ChangeGroupTutor> groupList, String userId)throws Exception {
		
		Connection connection = null;
		String sql="";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);	
			
			for (int j=0; j < groupList.size(); j++ )
			{
				ChangeGroupTutor changeGroup = new ChangeGroupTutor();
				changeGroup = (ChangeGroupTutor)groupList.get(j); 	 
				 
				sql = "update tustgr set mk_tutor_persno=?,sms_sent_date=sysTimestamp,email_sent_date=sysTimestamp" +
				   " where mk_academic_year=?" +
				   " and semester=?" +
				   " and mk_study_unit_code=?" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?" +
				   " and inactive_date is null";
				   
				PreparedStatement ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(changeGroup.getNewTutor().getPerson().getPersonnelNumber().trim()));
				ps.setShort(2, acadYear); 
				ps.setShort(3, semester); 
				ps.setString(4, studyUnit);
				ps.setString(5, changeGroup.getTutorStudentGroup().getTutoringMode().getTutorMode());
				ps.setInt(6, changeGroup.getTutorStudentGroup().getGroupNumber());
				ps.executeUpdate(); 
				
				//insert record in tustgrlog				
				sql = "insert into tustgrlog (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
		    	 "group_nr,action,comment0,reference_nr,updated_on,updated_by)" +
		    	 " values (?,?,?,?,?,?,?,?,sysTimestamp,?)";
		   
				ps = connection.prepareStatement(sql);	   
		   
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, changeGroup.getTutorStudentGroup().getTutoringMode().getTutorMode());
				ps.setInt(5, changeGroup.getTutorStudentGroup().getGroupNumber());
				ps.setString(6, "UPDATE");
				ps.setString(7, " ");
				ps.setInt(8, Integer.parseInt(changeGroup.getNewTutor().getPerson().getPersonnelNumber()));
				ps.setString(9, userId);
	       	   	ps.executeUpdate();	
	       	   
	       	   	sql = "select nvl(max(user_sequence),0)" +
	       	   		" from usrsun" +
	       	   		" where novell_user_id='" + changeGroup.getNewTutor().getPerson().getNovellUserId() + "'" +
	       	   		" and mk_study_unit_code='" + studyUnit + "'" +
	       	   		" and system_type='C'";
	       	   
	       	   	jdt = new JdbcTemplate(getDataSource());
	       	   	int userSequence = jdt.queryForInt(sql) ;
	       	   	userSequence = userSequence + 1 + j;  //Strange!!! do not take previous update into account add + j to avoid unique constrain
				
				//update usrsun record
				sql = "update usrsun set persno=?, novell_user_id=?, user_sequence=?" +
				   " where mk_academic_year=?" +
				   " and mk_semester_period=?" +
				   " and mk_study_unit_code=?" +
				   " and system_type='C'" +
				   " and tutor_mode_gc181=?" +
				   " and group_nr=?";
				   
				ps = connection.prepareStatement(sql);
				
				ps.setInt(1, Integer.parseInt(changeGroup.getNewTutor().getPerson().getPersonnelNumber().trim()));
				ps.setString(2, changeGroup.getNewTutor().getPerson().getNovellUserId());
				ps.setInt(3, userSequence);
				ps.setShort(4, acadYear); 
				ps.setShort(5, semester); 
				ps.setString(6, studyUnit);
				ps.setString(7, changeGroup.getTutorStudentGroup().getTutoringMode().getTutorMode());
				ps.setInt(8, changeGroup.getTutorStudentGroup().getGroupNumber());
				ps.executeUpdate(); 
				   
			}	 
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();  
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("TutorStudentGroupingDAO : Data has been rollback, Error replacing Tutor of selected groups / " + ex,ex);
			}
		}
		
	}
	
	public void reassignStudentToNewGroup(String studyUnit, Short acadYear, Short semester, Integer studentNr,TutorStudentGroup oldStudentGroup, TutorStudentGroup newStudentGroup)throws Exception {
		Connection connection = null;
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);
				
			String sql = "update tustgr set inactive_date=sysTimestamp" +
			" where mk_academic_year=?" +
			" and semester=?" +
			" and mk_study_unit_code=?" +
			" and tutor_mode_gc181=?" +
			" and group_nr=?" +
			" and mk_student_nr=?";
				 		
		   
			PreparedStatement ps = connection.prepareStatement(sql);
			   
			ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, oldStudentGroup.getTutoringMode().getTutorMode());
			ps.setInt(5, oldStudentGroup.getGroupNumber());
			ps.setInt(6, studentNr);
			ps.executeUpdate(); 	
			
			boolean inActiveMember = false;
			inActiveMember = isInActiveGroupMember(studyUnit, acadYear, semester, studentNr, newStudentGroup);
			
			if (inActiveMember==false){
				sql = "insert into tustgr (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
			   	"group_nr,mk_student_nr,mk_tutor_persno,assign_date,inactive_date,alloc_crit_gc183,sms_sent_date,email_sent_date)" +
			   	" values (?,?,?,?,?,?,?,sysTimestamp,null,?,sysTimestamp,sysTimestamp)";
		   
				ps = connection.prepareStatement(sql);
				   
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, newStudentGroup.getTutoringMode().getTutorMode());
				ps.setInt(5, newStudentGroup.getGroupNumber());
				ps.setInt(6, studentNr);
				ps.setInt(7, Integer.parseInt(newStudentGroup.getTutor().getPersonnelNumber()));
				ps.setString(8, newStudentGroup.getTutoringMode().getAllocationCriteria());
				ps.executeUpdate(); 
			}else {
				sql = "update tustgr set assign_date=sysTimestamp, inactive_date=null" +
				" where mk_academic_year=?" +
				" and semester=?" +
				" and mk_study_unit_code=?" +
				" and tutor_mode_gc181=?" +
				" and group_nr=?" +
				" and mk_student_nr=?";
				
				ps = connection.prepareStatement(sql);
				   
				ps.setShort(1, acadYear); 
				ps.setShort(2, semester); 
				ps.setString(3, studyUnit);
				ps.setString(4, newStudentGroup.getTutoringMode().getTutorMode());
				ps.setInt(5, newStudentGroup.getGroupNumber());
				ps.setInt(6, studentNr);
				ps.executeUpdate(); 
			}		 
			connection.commit();
			connection.setAutoCommit(true);
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){				
				connection.rollback();	
				connection.setAutoCommit(true);
				connection.close();
				throw new Exception("TutorStudentGroupingDAO : Error reassigning student, records have been rollbacked / " + ex,ex);	
			}
		}		  
	}

	public Student getStudent(int studentNr) throws Exception {
		
		Student student = new Student();
		
		String sql = "select mk_title,surname,initials,TO_CHAR(birth_date,'YYYY-MM-DD') as bday" + 
		" from stu" +
		" where nr=" + studentNr;
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String title ="";
				String surname="";
				String initials="";
				title= replaceNull(data.get("mk_title"));
				surname= replaceNull(data.get("surname"));
				initials= replaceNull(data.get("initials"));
				student.setNumber(studentNr);
				student.setName(surname + ' ' + initials + ' ' + title);
				student.setPrintName(title + ' ' + initials + ' ' + surname);
				student.setBirthDate(replaceNull(data.get("bday")));
				Contact contact = new Contact();
				contact = getContactDetails(studentNr, 1);
				student.setContactInfo(contact);
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Student / " + ex);
		}	
		return student;
		
	}
	
	public String getDepartmentRcCode(short deptCode) throws Exception {
		
		String rcCode = "";
		
		String sql = "select rc_code" + 
		" from dpt" +
		" where code=" + deptCode +
		" and in_use_flag<>'N'";
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				rcCode= replaceNull(data.get("rc_code"));
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading Dpt to get Rc-code / " + ex);
		}	
		return rcCode;
		
	}

	public Address getAddress(Integer referenceNo,Integer category,Integer type) throws Exception {
		
		Address address = new Address();
		
		String sql = "select address_line_1,address_line_2,address_line_3,address_line_4,address_line_5" +
		" address_line_6,postal_code" +
		" from adr" +
		" where reference_no=" + referenceNo +
		" and fk_adrcatypfk_adrc=" + category +
		" and fk_adrcatypfk_adrt=" + type;
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				address.setLine1(replaceNull(data.get("address_line_1")).trim());
				address.setLine2(replaceNull(data.get("address_line_2")).trim());
				address.setLine3(replaceNull(data.get("address_line_3")).trim());
				address.setLine4(replaceNull(data.get("address_line_4")).trim());
				address.setLine5(replaceNull(data.get("address_line_5")).trim());
				address.setLine6(replaceNull(data.get("address_line_6")).trim());
				address.setPostalCode(replaceNull(data.get("postal_code")).trim());
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading ADR / " + ex);
		}	
		return address;	
	}


	public TutoringMode getTutoringMode(String studyUnit, Short acadYear, Short semester, String tutorMode) throws Exception {
		TutoringMode record = new TutoringMode();
		
		String sql = "select tutor_mode_gc181,optionality,group_choice_gc182,tutor_stu_ratio," +
		" max_groups_per_tut, incl_lect_as_tutor, tut_contact_gc184, alloc_crit_gc183" +
        " from suntutmode" +
        " where mk_academic_year =" + acadYear +
        " and semester=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'" +
        " and tutor_mode_gc181='" + tutorMode + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				record.setTutorMode(replaceNull(data.get("tutor_mode_gc181")));
				Gencod gencod = new Gencod();
				StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
				gencod = dao.getGenCode("181", record.getTutorMode());
				if (gencod!=null && gencod.getEngDescription()!=null){
					record.setTutorModeDesc(gencod.getEngDescription());
				}	
				record.setOptionality(replaceNull(data.get("optionality")));
				record.setGroupChoice(replaceNull(data.get("group_choice_gc182")));
				record.setTutorStuRatio(Integer.parseInt(data.get("tutor_stu_ratio").toString()));
				record.setMaxGroupsPerTutor(Integer.parseInt(data.get("max_groups_per_tut").toString()));
				record.setIncludeLectAsTutors(replaceNull(data.get("incl_lect_as_tutor")));
				record.setTutorContact(replaceNull(data.get("tut_contact_gc184")));
				record.setAllocationCriteria(replaceNull(data.get("alloc_crit_gc183")));				
				}								
			}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading SUNTUTMODE / " + ex);
		}		
		return record;
	}
	
	public TutorStudentGroup getStudentGroup(String studyUnit, Short acadYear, Short semester, int studentNr)throws Exception {
		TutorStudentGroup tutorGroup = new TutorStudentGroup();
		
		String sql = "select group_nr,mk_tutor_persno,tutor_mode_gc181,TO_CHAR(assign_date,'YYYYMMDD') as assignDate" +
        " from tustgr" +
        " where mk_academic_year =" + acadYear +
        " and semester=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'" +
        " and mk_student_nr=" + studentNr +
		" and inactive_date is null";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			PersonnelDAO dao = new PersonnelDAO();

			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				tutorGroup.setStudentNr(Integer.parseInt(data.get("group_nr").toString()));
				tutorGroup.setGroupNumber(Integer.parseInt(data.get("group_nr").toString()));				
				Person person = new Person();				
				person = dao.getPersonnelFromSTAFF(Integer.parseInt(data.get("mk_tutor_persno").toString()));
				tutorGroup.setTutor(person);
				String mode = replaceNull(data.get("tutor_mode_gc181"));
				TutoringMode tutorMode = new TutoringMode();
				tutorMode = getTutoringMode(studyUnit, acadYear, semester, mode);
				tutorGroup.setTutoringMode(tutorMode);
				tutorGroup.setAssignDate(replaceNull(data.get("assignDate")));				
				}								
			}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading StudentTutorGroup(TUSTGR) / " + ex);
		}		
		
		return tutorGroup;
	}
	
	public List getAvailableTutorModeList(String studyUnit, Short acadYear, Short semester) throws Exception {
		List modeList = new ArrayList<TutoringMode>();
		
		String sql = "select tutor_mode_gc181,optionality,group_choice_gc182,tutor_stu_ratio," +
		" max_groups_per_tut, incl_lect_as_tutor, tut_contact_gc184, alloc_crit_gc183" +
        " from suntutmode" +
        " where mk_academic_year =" + acadYear +
        " and semester=" + semester +
        " and mk_study_unit_code='" + studyUnit + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {				
				ListOrderedMap data = (ListOrderedMap) i.next();
				TutoringMode record = new TutoringMode();
				record.setTutorMode(replaceNull(data.get("tutor_mode_gc181")));
				StudentSystemGeneralDAO dao = new StudentSystemGeneralDAO();
				Gencod gencod = new Gencod();
				gencod = dao.getGenCode("181", record.getTutorMode());
				if (gencod!=null && gencod.getEngDescription()!=null){
					record.setTutorModeDesc(gencod.getEngDescription());
				}				
				record.setOptionality(replaceNull(data.get("optionality")));
				record.setGroupChoice(replaceNull(data.get("group_choice_gc182")));
				record.setTutorStuRatio(Integer.parseInt(data.get("tutor_stu_ratio").toString()));
				record.setMaxGroupsPerTutor(Integer.parseInt(data.get("max_groups_per_tut").toString()));
				record.setIncludeLectAsTutors(replaceNull(data.get("incl_lect_as_tutor")));
				record.setTutorContact(replaceNull(data.get("tut_contact_gc184")));
				record.setAllocationCriteria(replaceNull(data.get("alloc_crit_gc183")));	
				modeList.add(record);
				}								
			}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO: Error reading SUNTUTMODES / " + ex);
		}		
		return modeList;
	}
	
	public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
		
		Contact contact = new Contact();
		
		String sql = "select home_number,work_number,fax_number,email_address,cell_number" +
		" from adrph" +
		" where reference_no=" + referenceNo +
		" and fk_adrcatcode=" + category;
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				contact.setHomeNumber(replaceNull(data.get("home_number")).trim());
				contact.setWorkNumber(replaceNull(data.get("work_number")).trim());
				contact.setFaxNumber(replaceNull(data.get("fax_number")).trim());
				contact.setCellNumber(replaceNull(data.get("cell_number")).trim());
				contact.setEmailAddress(replaceNull(data.get("email_address")).trim());
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading ADR / " + ex);
		}	
		return contact;	
	}
	
	public boolean isValidStudentNr(int studentNr) throws Exception {
		
		PreparedStatement pst = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    Connection con = jdt.getDataSource().getConnection();
		    int cnt = 0;
		
		    String sql =
		        "select count(*) from stu where nr = ?";
		    pst = con.prepareStatement(sql);	
		    pst.setInt(1, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	cnt = rs.getInt(1);	    
		    }
		    con.close();
		    rs.close();	    
		    if (cnt > 0){
		    	return true;
		    }else{
		    	return false;
		    }
	    }
		    catch (Exception ex) {
				throw new Exception("TutorStudentGroupingDAO : Error reading table STU / " + ex);
			}		
	}	
	
	public boolean isInActiveGroupMember(String studyUnit, Short acadYear, Short semester, Integer studentNr,TutorStudentGroup studentGroup) throws Exception {
		
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    Connection con = jdt.getDataSource().getConnection();
		    int cnt = 0;
		
		    String sql = "select count(*) from tustgr" +
				" where mk_academic_year =?" +
		        " and semester=?" +
		        " and mk_study_unit_code=?" +
		        " and tutor_mode_gc181=?" +
		        " and mk_student_nr=?" + 
		        " and group_nr=?";
		    
		    ps = con.prepareStatement(sql);
		    ps.setShort(1, acadYear); 
			ps.setShort(2, semester); 
			ps.setString(3, studyUnit);
			ps.setString(4, studentGroup.getTutoringMode().getTutorMode());
			ps.setInt(5, studentNr);
			ps.setInt(6, studentGroup.getGroupNumber());
		    
		    rs = ps.executeQuery();
		    if (rs.next()){
		    	cnt = rs.getInt(1);	    
		    }
		    con.close();
		    rs.close();	    
		    if (cnt > 0){
		    	return true;
		    }else{
		    	return false;
		    }
	    }
		    catch (Exception ex) {
				throw new Exception("TutorStudentGroupingDAO : Error reading table STU / " + ex);
			}		
	}	

	public boolean isStudentRegisteredForModule(String studyUnit, Short acadYear, Short semester, int studentNr) throws Exception {
		
		PreparedStatement pst = null;
	    ResultSet rs = null;
	    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    Connection con = jdt.getDataSource().getConnection();
		    int cnt = 0;
		
		    String sql =
		        "select count(*) from stusun" +
		        " where fk_academic_year = ?" +
		        " and semester_period= ?" +
		        " and mk_study_unit_code= ?" +
		        " and fk_student_nr= ?" +
		        " and status_code in ('RG','FC')";
		    
		    pst = con.prepareStatement(sql);	
		    pst.setShort(1, acadYear);
		    pst.setShort(2, semester);
		    pst.setString(3, studyUnit);
		    pst.setInt(4, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	cnt = rs.getInt(1);	    
		    }
		    con.close();
		    rs.close();	    
		    if (cnt > 0){
		    	return true;
		    }else{
		    	return false;
		    }
	    }
		    catch (Exception ex) {
				throw new Exception("TutorStudentGroupingDAO : Error reading table STUSUN / " + ex);
			}		
	}	
	
	public boolean isStudentAllocatedToATutorGroup (String studyUnit, Short acadYear, Short semester, int studentNr) throws Exception {
			
		PreparedStatement pst = null;
	    ResultSet rs = null;
		    
	    try{
		    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    Connection con = jdt.getDataSource().getConnection();
		    int cnt = 0;
		
		    String sql =
		        "select count(*) from tustgr" +
		        " where mk_academic_year = ?" +
		        " and semester = ?" +
		        " and mk_study_unit_code = ?" +
		        " and mk_student_nr = ?" +
		        " and inactive_date is null";
		    
		    pst = con.prepareStatement(sql);	
		    pst.setShort(1, acadYear);
		    pst.setShort(2, semester);
		    pst.setString(3, studyUnit);
		    pst.setInt(4, studentNr);
		    rs = pst.executeQuery();
		    if (rs.next()){
		    	cnt = rs.getInt(1);	    
		    }
		    con.close();
		    rs.close();	    
		    if (cnt > 0){
		    	return true;
		    }else{
		    	return false;
		    }
	    }
		    catch (Exception ex) {
				throw new Exception("TutorStudentGroupingDAO : Error reading table TUSTGR / " + ex);
			}		
	}	
	
	
	public int createTutorStudentGroup(String[] studentArray,String studyUnit, Short acadYear, Short semester, String tutoringMode, Tutor tutor, String allocCriteria, String userId) throws Exception {
		
		int groupNr = 0;
		final int count = studentArray.length; 
		final String[] studentArr = studentArray;
		
		String sql = "select max(group_nr) as lastGroupNr" +
					 " from tustgr" +
					 " where mk_academic_year = " + acadYear +
					 " and semester = " + semester +
					 " and mk_study_unit_code = '" + studyUnit + "'" +
					 " and tutor_mode_gc181 ='" + tutoringMode + "'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("lastGroupNr")==null){
					groupNr = 0;
				}else{
					groupNr = Integer.parseInt((data.get("lastGroupNr").toString()));
				}				
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO : Error reading tustgr to get next group number/ " + ex);
		}		
		
		Connection connection = null;
		groupNr = groupNr + 1;
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);			   
		   
		   sql = "insert into tustgr (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
		    	 "group_nr,mk_student_nr,mk_tutor_persno,assign_date,inactive_date,alloc_crit_gc183,sms_sent_date,email_sent_date)" +
		    	 " values (?,?,?,?,?,?,?,sysTimestamp,null,?,sysTimestamp,sysTimestamp)";
		   
		   PreparedStatement ps = connection.prepareStatement(sql);
		   
		   for (int i=0; i < studentArr.length; i++ ){
			 ps.setShort(1, acadYear); 
			 ps.setShort(2, semester); 
			 ps.setString(3, studyUnit);
			 ps.setString(4, tutoringMode);
			 ps.setInt(5, groupNr);
			 ps.setInt(6, Integer.parseInt(studentArr[i].toString()));
			 ps.setInt(7, Integer.parseInt(tutor.getPerson().getPersonnelNumber()));
			 ps.setString(8, allocCriteria);
	         ps.addBatch();
		   }
		   
		   ps.executeBatch();	  
		  
		   sql = "insert into tustgrlog (mk_academic_year,semester,mk_study_unit_code,tutor_mode_gc181," +
	    	 "group_nr,action,comment0,reference_nr,updated_on,updated_by)" +
	    	 " values (?,?,?,?,?,?,?,?,sysTimestamp,?)";
	   
		   ps = connection.prepareStatement(sql);	   
	   
		   ps.setShort(1, acadYear); 
		   ps.setShort(2, semester); 
		   ps.setString(3, studyUnit);
		   ps.setString(4, tutoringMode);
		   ps.setInt(5, groupNr);
		   ps.setString(6, "CREATE");
		   ps.setString(7, " ");
		   ps.setInt(8, Integer.parseInt(tutor.getPerson().getPersonnelNumber()));
		   ps.setString(9, userId);
       	   ps.executeUpdate();	
       	   
       	   sql = "select nvl(max(user_sequence),0)" +
       	   		" from usrsun" +
       	   		" where novell_user_id='" + tutor.getPerson().getNovellUserId() + "'" +
       	   		" and mk_study_unit_code='" + studyUnit + "'" +
       	   		" and system_type='C'";
       	   
       	   jdt = new JdbcTemplate(getDataSource());
       	   int userSequence = jdt.queryForInt(sql) ;
       	   userSequence = userSequence + 1;
       	   
       	   sql = "insert into usrsun (novell_user_id,mk_study_unit_code,user_type," +
       		"access_level,access_updated_by,system_type,mk_academic_year,mk_semester_period,user_sequence," +
       	    "persno,nr,tutor_mode_gc181,group_nr)" +
       		" values (?,?,?,?,?,?,?,?,?,?,null,?,?)";
  
       	   ps = connection.prepareStatement(sql);  
  
		   ps.setString(1, tutor.getPerson().getNovellUserId()); 
		   ps.setString(2, studyUnit); 
		   ps.setString(3, null);
		   ps.setString(4, "TUTOR");
		   ps.setString(5, null);
		   ps.setString(6, "C");
		   ps.setShort(7, acadYear);
		   ps.setShort(8, semester);
		   ps.setInt(9, userSequence);
		   ps.setInt(10,Integer.parseInt(tutor.getPerson().getPersonnelNumber()));
		   ps.setString(11,tutoringMode);
		   ps.setInt(12, groupNr);
	  	   ps.executeUpdate();	
		 
		  connection.commit();
		  connection.setAutoCommit(true);
		  connection.close();
			
		}
		catch (Exception ex) {
			if (connection!=null){
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("TutorStudentGroupingDAO : Data has been rollback, Error inserting records into TUSTGR / " + ex,ex);
			}
		}
			
		return groupNr;
	}
	
	public Regdat getRegDat(Short acadYear, Short semester, String type) throws Exception {
		
		Regdat regdat = new Regdat();
		
		String sql = "select to_char(from_date,'YYYYMMDD') as fromDate,TO_CHAR(to_date,'YYYYMMDD') as toDate" + 
		" from regdat" +
		" where academic_year=" + acadYear +
		" and semester_period=" + semester +
		" and type='" + type + "'";
		
	  	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
	
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();				
				String str_fromDate=replaceNull(data.get("fromDate"));
				if (str_fromDate!=null && !str_fromDate.equalsIgnoreCase("")){
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
					Date fromDate = formatter.parse(str_fromDate);
					regdat.setFromDate(fromDate);
				}
				String str_todate=replaceNull(data.get("toDate"));
				if (str_todate!=null && !str_todate.equalsIgnoreCase("")){
					SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
					Date toDate = formatter.parse(str_todate);
					regdat.setToDate(toDate);
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("Error reading regdat / " + ex);
		}	
		return regdat;
		
	}
	public void insertEmailLog(EmailLogRecord log) throws Exception {
		
		String sql = "insert into emllog (email_address,date_sent,recipient,recip_type_gc137,program," +
			"email_type_gc138,subject,body0) " +
			"values " +
			"('" + log.getEmailAddress().toUpperCase() + "'," + 
			"systimestamp," +
			"'" + log.getRecipient() + "'," +
			"'" + log.getRecipientType() + "'," +
			"'" + log.getProgram() + "'," +
			"'" + log.getEmailType() + "'," +
			"'" + log.getSubject() + "'," +
			"'" + log.getBody() + "')";	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO : Error inserting EMLLOG / " + ex,ex);
		}	
	}
	
	public boolean isAuthorised(String novellid,String studyUnit,Short academicYear,Short semester) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + novellid.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + academicYear +
		             " and mk_semester_period = " + semester +
		             " and system_type = 'L'" +
		             " and access_level in ('SECDL','CADMN','PRIML')";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public int getNrOfStudentsInGroup(String studyUnit, Short acadYear, Short semester, TutorStudentGroup group) {
		String sql = "select count(*) " +
		" from tustgr" +
		" where mk_academic_year=" + acadYear +
		" and semester=" + semester +
		" and mk_study_unit_code='" + studyUnit + "'" +
		" and tutor_mode_gc181='" + group.getTutoringMode().getTutorMode() + "'" +
		" and group_nr=" + group.getGroupNumber() +
		" and inactive_date is null";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		return result;
	}
	
public boolean isSunpdtExist(Short academicYear,Short semester,String studyUnit) throws Exception{
		
		String sql="select count(*) " +
		           "from sunpdt " +
		           "where mk_academic_year= " + academicYear +
		           " and mk_academic_period= 1" +
		           " and fk_suncode = '" + studyUnit.toUpperCase().trim() + "'" +
		           " and semester_period = " + semester;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.queryForInt(sql) ;
			if (result == 0) {
				return false;
			} else {
				return true;
			}
		}
		catch (Exception ex) {
			throw new Exception("TutorStudentGroupingDAO:  Error reading Study unit period detail / " + ex);
		}				
	}	

	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}

}
