package za.ac.unisa.lms.tools.publicexamtimetable.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class ExamTimetableDAO extends StudentSystemDAO{

	public ExamTimetableDAO(){

	}

	public String getExamStatus(Integer year, Integer period) throws Exception{
		String examStatus ="";
		String sql = "select ADMISSION_DONE_FLAG from xaddon where mk_exam_year = "+year+" and mk_exam_period = "+period+" and mk_exam_type_code = 1";
		try{
			examStatus = this.querySingleValue(sql,"ADMISSION_DONE_FLAG");
		} catch (Exception ex) {
            throw new Exception("ExamTimeTableDAO error occurs / "+ ex,ex);
		}
       return examStatus;
	}

	public boolean isCodeInValid(String subject) throws Exception{
		String code ="";
		String sql = "select CODE from sun where CODE like '"+subject+"%'";
		try{
			code = this.querySingleValue(sql,"CODE");
		} catch (Exception ex) {
            throw new Exception("ExamTimeTableDAO method (isCodeInValid) error occurs / "+ ex,ex);
		}
		if (code.equalsIgnoreCase("")){
			return true;
		} else {

			return false;
		}
	}

	public ArrayList getExamTimetableList(String year,String period,String subjCodes) throws Exception{
		ArrayList results = new ArrayList();
		String sql;
		sql =	"select SUBSTR(XAMDAT.FK_STUDY_UNIT_CODE,1,7) STUDY_CODE," +
		 		"XAMDAT.FK_NR PAPER," +
		 		"TO_CHAR(XAMDAT.DATE0,'YYYY-MM-DD') EXAM_DATE," +
		 		"TO_CHAR(XAMDAT.STARTING_TIME,'HH24:MI') START_TIME," +
		 		"NVL(XAMDAT.DURATION_HOURS,'0') HOURS ," +
		 		" NVL(XAMPAP.DURATION_MINUTES,'0') MINUTES " +
		 		"from XAMDAT, XAMPAP " +
		 		"where XAMDAT.MK_EXAM_PERIOD_COD =  " +period+
		 		" AND XAMDAT.FK_EXAM_YEAR =  "+year+
		 		" AND XAMPAP.EXAM_YEAR =  "+year+
		 		" AND XAMPAP.MK_STUDY_UNIT_CODE = XAMDAT.FK_STUDY_UNIT_CODE "+
		 		"AND XAMPAP.NR = XAMDAT.FK_NR ";
				String[] result = subjCodes.split(",");
				for(int i=0 ; i < result.length; i++){
					if (i == 0){
						sql +=	"AND (upper(XAMDAT.FK_STUDY_UNIT_CODE) LIKE '"+result[i].toUpperCase()+"%'";
					} else {
						sql += " OR upper(XAMDAT.FK_STUDY_UNIT_CODE) LIKE '"+result[i].toUpperCase()+"%' ";
					}
				}
		sql +=	") ORDER by FK_STUDY_UNIT_CODE, FK_NR ASC";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			Iterator i = queryList.iterator();
			String examYear = "";
			while(i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamTimetableDetails examTimetableDetails = new ExamTimetableDetails();
				examTimetableDetails.setStudyUnit(data.get("STUDY_CODE").toString());
				examTimetableDetails.setPaperNo(data.get("PAPER").toString());
				examYear = data.get("EXAM_DATE").toString().substring(0,4);
				if (examYear.equalsIgnoreCase("1903")){
					examTimetableDetails.setExamDate("Departmental Requirements");
				} else if (examYear.equalsIgnoreCase("1901")){
					examTimetableDetails.setExamDate("Will be informed");
				} else {
					examTimetableDetails.setExamDate(data.get("EXAM_DATE").toString());
				}
				examTimetableDetails.setExamTime(data.get("START_TIME").toString());
				
				String minutes=data.get("MINUTES").toString();
				if(minutes.equals("0")){
					examTimetableDetails.setDuration(data.get("HOURS").toString()+" hour(s)");
					
				}else{
					examTimetableDetails.setDuration(data.get("HOURS").toString()+" hour(s)"+" "+minutes+" minutes");
					
				}
				
								
				results.add(examTimetableDetails);
			}
		}catch(Exception ex) {
			throw new Exception("ExamTimetableDAO : error reading the getExamTimetableList function / "+ ex,ex);
		}
		return results;
	}

	private String querySingleValue(String query, String field){
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
    	List queryList;
    	String result = "";

 	   queryList = jdt.queryForList(query);
 	   Iterator i = queryList.iterator();
 	   i = queryList.iterator();
 	   if (i.hasNext()) {
			 ListOrderedMap data = (ListOrderedMap) i.next();
			 if (data.get(field) == null){
			 } else {
				 result = data.get(field).toString();
			 }
 	   }

 	   return result;
	}
}
