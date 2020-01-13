package za.ac.unisa.lms.tools.results.dao;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ResultsDAO extends StudentSystemDAO {

	public List getExamPeriods() {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.queryForList("SELECT * FROM XAMPRD ORDER BY CODE");
		
		List xamprds = new Vector();
		
		Iterator i = results.iterator();
		while (i.hasNext()) {
			ListOrderedMap row = (ListOrderedMap) i.next();
			Xamprd xamprd = new Xamprd();
			xamprd.setAfrDescription((String) row.get("AFR_DESCRIPTION"));
			xamprd.setAfrShortDescript((String) row.get("AFR_SHORT_DESCRIPTION"));
			xamprd.setEngDescription((String) row.get("ENG_DESCRIPTION"));
			xamprd.setEngShortDescript((String) row.get("ENG_SHORT_DESCRIPTION"));
			xamprd.setCode(new Short(((BigDecimal) row.get("CODE")).shortValue()));
			
			xamprds.add(xamprd);
		}
		
		return xamprds;
	}
	public boolean blockStudentResults(Integer studentNr) {
		
		String blockResult="N";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List results = jdt.queryForList("SELECT PUBLIC_WEB_BLOCK FROM STU" +
				" WHERE NR = " + studentNr);
		
		List xamprds = new Vector();
		
		Iterator i = results.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			Xamprd xamprd = new Xamprd();
			blockResult=replaceNull(data.get("PUBLIC_WEB_BLOCK"));			
		}
		
		if (blockResult.equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
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
