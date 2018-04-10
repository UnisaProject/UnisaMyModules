package za.ac.unisa.lms.tools.exampapers.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.exampapers.forms.PersonnelRecord;

public class PersonnelDAO extends StudentSystemDAO {
	
	public PersonnelRecord getPersonnelInfo(String persNr) throws Exception {
		
		List list;
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		PersonnelRecord record = new PersonnelRecord();
		
		list = jdt.queryForList("select SURNAME,INITIALS,TITLE,EMAIL_ADDRESS,CONTACT_TELNO from STAFF where PERSNO=" + persNr);
		Iterator i = list.iterator();
		i = list.iterator();
		
		record.setSurname("");
		record.setInitials("");
		record.setTitle("");
		record.setCellNumber("");
		record.setWorkNumber("");
		record.setHomeNumber("");
		record.setEmailAddress("");	
		
		if(i.hasNext()){
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("SURNAME") != null) {
				record.setSurname(data.get("SURNAME").toString());
			}
			if (data.get("INITIALS") != null) {
				record.setInitials(data.get("INITIALS").toString());
			}
			if (data.get("TITLE") != null) {
				record.setTitle(data.get("TITLE").toString());
			}
			//if (data.get("CONTACT_TELNO") != null) {
			//	record.setContactNumber(data.get("CONTACT_TELNO").toString());
			//}
			if (data.get("EMAIL_ADDRESS") != null) {
				record.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
			}
			list = jdt.queryForList("select CELL_NUMBER,WORK_NUMBER,HOME_NUMBER from ADRPH where REFERENCE_NO=" + persNr + "and FK_ADRCATCODE=33");
			Iterator j = list.iterator();
			j = list.iterator();
			
			if (j.hasNext()) {
				ListOrderedMap data2 = (ListOrderedMap) j.next();
				if (data2.get("CELL_NUMBER") != null &&
						!data2.get("CELL_NUMBER").toString().trim().equalsIgnoreCase("")) {
					record.setCellNumber(data2.get("CELL_NUMBER").toString());
				}
				if (data2.get("WORK_NUMBER") != null &&
						!data2.get("WORK_NUMBER").toString().trim().equalsIgnoreCase("")) {
					record.setWorkNumber(data2.get("WORK_NUMBER").toString());
				}
				if (data2.get("HOME_NUMBER") != null &&
						!data2.get("HOME_NUMBER").toString().trim().equalsIgnoreCase("")) {
					record.setHomeNumber(data2.get("HOME_NUMBER").toString());
				}			
				
			}
		}
		else{
			record.setSurname("NF");
		}
		return record;
		
	}
}
