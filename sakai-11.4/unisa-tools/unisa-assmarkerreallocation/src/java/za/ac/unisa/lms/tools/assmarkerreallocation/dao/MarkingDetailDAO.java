package za.ac.unisa.lms.tools.assmarkerreallocation.dao;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.assmarkerreallocation.actions.Utilities;
import za.ac.unisa.lms.tools.assmarkerreallocation.forms.MarkerDetailRecord;

public class MarkingDetailDAO  extends StudentSystemDAO{
	public MarkerDetailRecord getMarkerDetail(String studyUnit,Short year,Short period,Short assignmentNr,Integer persno)throws Exception{
		MarkerDetailRecord markerDetail = new MarkerDetailRecord();
		int nrStudentSubmitted=0;
		int nrMarked=0;
		int totalPercentage=0;
		
		String sql = " select to_char(b.DATE_RETURNED,'YYYYMMDD') as return_date, b.PERCENTAGE_OBTAINE  from assdct a,stuass b,dctstu c" +
		" where a.MK_ACADEMIC_YEAR  =" + year +
		" and a.MK_ACADEMIC_PERIOD =" + period +
		" and a.MK_STUDY_UNIT_CODE ='" + studyUnit + "'" +
		" and a.ASSIGNMENT_NR  =" + assignmentNr +
		" and a.MK_LECTURER_NR  =" + persno +
		" and A.NR = C.FK_ASS_DCT_NR" +
		" and A.MK_ACADEMIC_YEAR=B.FK_ACADEMIC_YEAR" +
		" and b.fk_academic_period=1" +
		" and A.MK_ACADEMIC_PERIOD=B.FK_SEMESTER_PERIOD" +
		" and A.MK_STUDY_UNIT_CODE=B.FK_STUDY_UNIT_CODE" +
		" and A.ASSIGNMENT_NR=B.MK_ASSIGNMENT_NR" +
		" and C.MK_STUDENT_NR =B.FK_STUDENT_NR" +
		" and B.CANCELLATION_CODE='NC'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				nrStudentSubmitted = nrStudentSubmitted + 1;
				String returnDate = Utilities.replaceNull(data.get("return_date"));
				String percentageObtained = Utilities.replaceNull(data.get("PERCENTAGE_OBTAINE"));
				
				if (returnDate.equalsIgnoreCase("") || returnDate.equalsIgnoreCase("00010101")){
				}else{
					nrMarked = nrMarked + 1;
				}	
				
				if (Utilities.isInteger(percentageObtained)){
					totalPercentage = totalPercentage + Integer.parseInt(percentageObtained);
				}			
			}
		}
		catch (Exception ex) {
			throw new Exception("AssMarkerReallocationDAO : Error reading unqass / " + ex);
		}	
		markerDetail.setMarked(nrMarked);
		markerDetail.setStudentSubmit(nrStudentSubmitted);
		markerDetail.setMarksOutstanding(nrStudentSubmitted-nrMarked);
		if (nrMarked>0){
			markerDetail.setAvgMarkPerc((int)((((double)totalPercentage)/((double)nrMarked))+0.5));
		}else{
			markerDetail.setAvgMarkPerc(0);
		}
		return markerDetail;	
	}

public int getStudentsRegistered(String studyUnit,Short year,Short period)throws Exception{
				
		String sql = "select count(*)" +
	    " from stusun" +
	    " where FK_ACADEMIC_YEAR = " + year +
	    " and SEMESTER_PERIOD = " + period + 
	    " and MK_STUDY_UNIT_CODE = '" + studyUnit.toUpperCase().trim() + "'" +
	    " and STATUS_CODE  in ('RG','FC')";
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
				
		return result;
	}
}
