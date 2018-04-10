package za.ac.unisa.lms.tools.ebookshop.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class EshopStudentSystemDAO extends StudentSystemDAO{

	private Log log;

	//Regular refers to a student who does not have a myUnisa account but is registgered at Unisa
	public boolean regularAccountExist(String studentNumber) throws Exception {
		boolean regularAccountExist;
		if(null == studentNumber | studentNumber.equalsIgnoreCase("")) {
			regularAccountExist = false;
		}else {
			log = LogFactory.getLog(this.getClass());

			String query = "select stu.surname from stu where stu.nr=" +  studentNumber;
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				log.debug("E-Bookshop: Does the student exists on Student System: " + query);
				List list = jdt.queryForList(query);
				if(list.isEmpty()) {
					regularAccountExist = false;
				}else {
					regularAccountExist = true;
				}
			}catch (Exception ex) {
				//throw new Exception("E-Bookshop: EshopStudentSystemDAO error occurred: "+ ex);
				return false;
			}
		}
		return regularAccountExist;
	}

	public String studyUnitCoursecode(String code) throws Exception {
		log = LogFactory.getLog(this.getClass());
		String coursecode = "";
		String query = "SELECT CODE FROM SUN WHERE CODE LIKE '" + code.toUpperCase() + "%'";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			//log.debug("E-Bookshop: Retrieving the study unit description:" + query);
			List list = jdt.queryForList(query);
			if(!list.isEmpty()) {
				Iterator i = list.iterator();
				if(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					coursecode = (data.get("CODE").toString());
				}
			}else {
				coursecode = "no description";
			}
		}catch (Exception ex) {
			throw new Exception("E-Bookshop: EshopStudentSystemDAO error occurred while querying the study unit code: /" + ex, ex);
		}

		return coursecode;

	}
	public String studyUnitDescription(String code) throws Exception {
		log = LogFactory.getLog(this.getClass());
		String description = "";
		String query = "SELECT ENG_SHORT_DESCRIPT FROM SUN WHERE CODE ='" + code.toUpperCase() + "'";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			//log.debug("E-Bookshop: Retrieving the study unit description:" + query);
			List list = jdt.queryForList(query);
			if(!list.isEmpty()) {
				Iterator i = list.iterator();
				if(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					description = (data.get("ENG_SHORT_DESCRIPT").toString());
				}
			}else {
				description = "no description";
			}
		}catch (Exception ex) {
			throw new Exception("E-Bookshop: EshopStudentSystemDAO error occurred while querying the study unit code: /" + ex, ex);
		}
		return description;
	}

}
