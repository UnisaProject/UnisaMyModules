package za.ac.unisa.lms.tools.tutoringplan.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tutoringplan.forms.TutoringMode;

public class TutorDAO extends StudentSystemDAO {
	public List getTutorList(short  acadYear,short semester, String studyUnit,String tutorMode) throws Exception{
        String sql="select distinct(mk_tutor_persno)  persno from tustgr  where   mk_academic_year="+acadYear+
      		  "  and  mk_study_unit_code='"+studyUnit+"'  and semester = " + semester+"  and  tutor_mode_gc181='"+tutorMode+"'";
          	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    			List queryList = jdt.queryForList(sql);
    			List  tutorList=new ArrayList();
    			if((queryList==null)||
    					(queryList.size()==0)){
    				           return tutorList;
    			}
    			Iterator i = queryList.iterator();
    			while (i.hasNext()) {
    				   ListOrderedMap data = (ListOrderedMap) i.next();
    				   if(data.get("persno")!=null){
    				       tutorList.add(data.get("persno").toString());
    				   }
    			}
    			return tutorList;
  }
  public List getLectList(short  acadYear,short semester,String studyUnit) throws Exception{
                 String sql="Select distinct(persno) personnelNum from usrsun" +
                             " where system_type='L'" +
                             " and access_level in ('PRIML','SECDL')" +
                             " and mk_study_unit_code = '" + studyUnit + "'" +
                             " and mk_academic_year = " + acadYear +
                             " and mk_semester_period = " + semester;
                JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		        List queryList = jdt.queryForList(sql);
		        List  tutorList=new ArrayList();
		        if((queryList==null)||(queryList.size()==0)){
			           return tutorList;
		        }
		        Iterator i = queryList.iterator();
		        while (i.hasNext()) {
			             ListOrderedMap data = (ListOrderedMap) i.next();
			             if(data.get("personnelNum")!=null){
			                  tutorList.add(data.get("personnelNum").toString());
			             }
		        }
		        return tutorList;
   }
   public boolean isLecAssignedAsTutor(short  acadYear,short semester,String studyUnit,String tutorMode)throws Exception{
          List tutorsInGrouping_List=getTutorList(acadYear,semester, studyUnit,tutorMode);
          List lecList=getLectList(acadYear,semester, studyUnit);
          Iterator iter=tutorsInGrouping_List.iterator();
          boolean lecAssignedAsTutor=false;
          while(iter.hasNext()){
          	if(lecList.contains(iter.next())){
          		lecAssignedAsTutor=true;
          		break;
          	}
          }
          return lecAssignedAsTutor;
  }
}
