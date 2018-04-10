package za.ac.unisa.lms.tools.exampapers.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.exampapers.forms.*;

public class ManagementInfoDAO extends StudentSystemDAO{
	
	public List getCollegelist() throws Exception {
			
			List collegeList = new ArrayList();
			List queryList = new ArrayList();
					
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			queryList = jdt.queryForList("select code,eng_description,abbreviation from colleges");
			for (int i=0; i<queryList.size();i++){
					CollegeRecord college = new CollegeRecord();
					ListOrderedMap data = (ListOrderedMap) queryList.get(i);
					college.setCode(Short.parseShort((String)data.get("CODE").toString()));
					college.setDescription((String) data.get("ENG_DESCRIPTION").toString());
					college.setAbbreviation((String) data.get("ABBREVIATION").toString());
					collegeList.add(college);						
				}
			return collegeList;
	}
	
	public DepartmentRecord getDepartment(Short code) throws Exception{
		
		DepartmentRecord dpt = new DepartmentRecord();
		
		String sql = "select code,eng_description,college_code" + 
        " from dpt" +
        " where code =" + code; 
						
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
			dpt.setDescription(data.get("ENG_DESCRIPTION").toString());
			dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
			}
		}
		catch (Exception ex) {
		throw new Exception("ManagementInfoDAO : Error reading Department / " + ex,ex);
		}		
		return dpt;		
		
	}
	
public CollegeRecord getCollege(Short code) throws Exception{
		
		CollegeRecord college = new CollegeRecord();
		
		String sql = "select code,eng_description,abbreviation" + 
        " from colleg" +
        " where code =" + code; 
		
		try{ 
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList = jdt.queryForList(sql);
		
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			college.setCode(Short.parseShort((String)data.get("CODE").toString()));
			college.setDescription(data.get("ENG_DESCRIPTION").toString());
			college.setAbbreviation(data.get("ABBREVIATION").toString());
			}
		}
		catch (Exception ex) {
		throw new Exception("ManagementInfoDAO : Error reading College / " + ex,ex);
		}		
		return college;		
		
	}

	public List getDepartmentList(Short college) throws Exception {
		
		List dptList = new ArrayList();
		List queryList = new ArrayList();
		
		DepartmentRecord dpt = new DepartmentRecord();
		dpt.setCode(Short.parseShort("0"));
		dpt.setDescription("ALL DEPARTMENTS");
		dptList.add(dpt);		
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		queryList = jdt.queryForList("select code,eng_description,college_code from dpt where college_code =" + college + " and in_use_flag='Y' order by eng_description");
		for (int i=0; i<queryList.size();i++){
				dpt = new DepartmentRecord();				
				ListOrderedMap data = (ListOrderedMap) queryList.get(i);
				dpt.setCode(Short.parseShort((String)data.get("CODE").toString()));
				dpt.setDescription((String) data.get("ENG_DESCRIPTION").toString());
				dpt.setCollegeCode(Short.parseShort((String)data.get("COLLEGE_CODE").toString()));
				dptList.add(dpt);						
			}
		return dptList;
	}
}
