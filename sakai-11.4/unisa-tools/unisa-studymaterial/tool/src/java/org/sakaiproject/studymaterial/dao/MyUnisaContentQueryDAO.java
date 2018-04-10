package org.sakaiproject.studymaterial.dao;

import java.sql.Types;
import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Hashtable;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


import za.ac.unisa.lms.db.StudentSystemDAO;

public class MyUnisaContentQueryDAO extends StudentSystemDAO {

	
	
	//Exam Paper method
	public List getCourseList(String course)
	
	throws Exception {
		List examResources;
		String queryx	="	Select fk_study_unit_code, paper_no, fk_exam_year, fk_exam_period_cod,nvl( to_char (date_scanned, 'YYYYMMDD'),'00010101') as date_scanned ,nvl(to_char( date_afr_scanned, 'YYYYMMDD'),'00010101')as date_afr_scanned, " 
						+"	from_date , to_date "
						+"	FROM xtloge, regdat"
						+"	WHERE fk_study_unit_code = '"+course+"'"
						+"	AND  (nvl( to_char (date_scanned, 'YYYYMMDD'),'00010101') > '00010101'"
						+"	OR nvl(to_char( date_afr_scanned, 'YYYYMMDD'),'00010101') > '00010101' )"
						+"	AND to_char(sysdate,'YYYYMMDD') >= to_char (regdat.from_date, 'YYYYMMDD')"
						+"	AND to_char( sysdate,'YYYYMMDD') <= to_char (regdat.to_date, 'YYYYMMDD')"
						+"	AND xtloge.open_for_web = 'Y'"
						+"	AND regdat.academic_year  = fk_exam_year"
						+"	AND regdat.semester_period = fk_exam_period_cod"
						+"	AND type = 'XPAPWEB'"
						+"	ORDER BY paper_no, fk_exam_year, fk_exam_period_cod";
		//System.out.println(queryx);
		try {
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			// email = (String) jdt.queryForObject(query, String.class);
			examResources = jdt.queryForList(queryx);
						
		} catch (Exception ex) {
			throw new Exception(
					"MyUnisaContentQueryDAO: getExampapers: (course: "
							+ course + ") Error occurred / " + ex, ex);
		}
				
		return examResources;
	}
		
	
	public String getMainCourse(String course)
	
	throws Exception {
		String code;
		String queryx ="select MK_STUDY_UNIT_CODE " +
						"from xamequ "+
						"where equivalent_code = '"+course+"' "+
						"and same_paper_flag = 'Y'";

		try {
			
			code = querySingleValue(queryx, "MK_STUDY_UNIT_CODE");
						
		} catch (Exception ex) {
			throw new Exception(
					"MyUnisaContentQueryDAO: getMainCourse: (course: "
							+ course + ") Error occurred / " + ex, ex);
		}
				
		return code;
	}
		
	public String getEmailID(String course) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
				
		String sql ="select g.Eng_description Eng_description  from gencod g, sun s,colleg c where g.code=c.abbreviation and fk_gencatcode = '121'"+ 
			         " and s.college_code=c.code and s.code='"+course+"'";
		String email;
		try{
			email = querySingleValue(sql,"Eng_description");
			} catch (Exception ex) {
			    throw new Exception("MyUnisaContentQueryDAO : getEmailID Error occurred / "+ ex,ex);
			}
			return email;		
	    }
	private String querySingleValue(String query, String field) {

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			LinkedHashMap data = (LinkedHashMap) i.next();
			if (data.get(field) == null) {
			} else {
				result = data.get(field).toString();
			}
		}
		return result;
	}
}
