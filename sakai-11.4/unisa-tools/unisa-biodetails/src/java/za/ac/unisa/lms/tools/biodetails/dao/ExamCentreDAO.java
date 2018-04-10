package za.ac.unisa.lms.tools.biodetails.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.biodetails.forms.ExamCentreForm;

@SuppressWarnings({"rawtypes","unchecked"})
public class ExamCentreDAO extends StudentSystemDAO{

	public ArrayList getExamCentres() throws Exception{
		ArrayList records = new ArrayList();
		String query = "select code, eng_description,type from eks where in_use_flag='Y' and type in ('F','P') order by eng_description";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			Iterator i = queryList.iterator();
			while(i.hasNext()){
				ExamCentreForm examcentreform = new ExamCentreForm();
				ListOrderedMap data = (ListOrderedMap) i.next();
				examcentreform.setCode(data.get("CODE").toString());
				examcentreform.setCentreName(data.get("ENG_DESCRIPTION").toString());
				examcentreform.setCentreType(data.get("TYPE").toString());
				records.add(examcentreform);
			}
		} catch(Exception ex){
			throw new Exception("ExamCentreDAO : Error reading table eks  / "+ ex,ex);	
		}
		return records;
	}
	
}
