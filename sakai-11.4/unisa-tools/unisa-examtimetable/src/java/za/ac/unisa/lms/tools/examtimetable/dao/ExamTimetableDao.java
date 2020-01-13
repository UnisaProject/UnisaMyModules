package za.ac.unisa.lms.tools.examtimetable.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.domain.general.Person;
import za.ac.unisa.lms.tools.examtimetable.forms.*;;

public class ExamTimetableDao extends StudentSystemDAO{
	
	public List getFinalTimetableParText(int code) throws Exception {
		ArrayList list = new ArrayList();
		
		String sql = "select text from doklyn" +
        " where fk_dokcode=" + code +
        " and fk_dokmk_language='E'" +
        " and fk_dokwidth_type='L'" +
        " and fk_dokcontent='O'" +
        " order by sequence_number";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				list.add(data.get("TEXT").toString());
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamTimetableDao : Error reading table doklyn / " + ex);
		}				
		return list;
	}
	
	public Coordinates getVenueCoordinates(String venueCode) throws Exception{
		Coordinates record = new Coordinates();
		
		String sql = "select longitude,latitude" +
		" from lok" +
		" where code='" + venueCode +"'"; 
	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				record.setLatitude(replaceNull(data.get("LATITUDE")));
				record.setLongitude(replaceNull(data.get("LONGITUDE")));
			}
	}
	catch (Exception ex) {
		throw new Exception("ExamTimetableDAO : Error reading LOK / " + ex,ex);
	}		
	return record;		
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
