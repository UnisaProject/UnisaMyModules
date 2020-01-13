package za.ac.unisa.lms.tools.exampaperonline.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.exampaperonline.forms.Xtloge;
import za.ac.unisa.lms.tools.exampaperonline.forms.Xtlog;

public class XtlogDao extends StudentSystemDAO{
	
	public Xtlog getXtlog(Short examYear, Short examPeriod, String studyUnit) throws Exception {
		Xtlog logRec = new Xtlog();
		
		String sql = "select exam_year, mk_exam_period_cod, mk_study_unit_code, combined_with, remarks," +
		" remarks2, remarks3, remarks4, remarks5, remarks6, paper_expected" +
        " from xtlog" +
        " where exam_year = " + examYear +
        " and mk_exam_period_cod = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'";  
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				logRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				logRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period_cod").toString()));
				logRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				logRec.setCombined_with(replaceNull(data.get("combined_with")));
				logRec.setRemarks(replaceNull(data.get("remarks")));
				logRec.setRemarks2(replaceNull(data.get("remarks2")));
				logRec.setRemarks3(replaceNull(data.get("remarks3")));
				logRec.setRemarks4(replaceNull(data.get("remarks4")));
				logRec.setRemarks5(replaceNull(data.get("remarks5")));
				logRec.setRemarks6(replaceNull(data.get("remarks6")));
				logRec.setPaperExpected(replaceNull(data.get("paper_expected")));		
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("XtlogDao : Error reading table xtlog / " + ex,ex);						
		}		
		return logRec;		
	}
	
	public void insertXtlog(Xtlog xtlog) throws Exception {
		
		String sql = "insert into xtlog (exam_year,mk_exam_period_cod,mk_study_unit_code,combined_with,remarks,remarks2,remarks3," +
		"remarks4,remarks5,remarks6,paper_expected) " +
			"values " +
			"(" + xtlog.getExamYear() + "," + 
			xtlog.getExamPeriod() + "," +
			"'" + xtlog.getStudyUnit().toUpperCase() + "'," +
			"'" + xtlog.getCombined_with() + "'," +
			"'" + xtlog.getRemarks() + "'," +
			"'" + xtlog.getRemarks2() + "'," +
			"'" + xtlog.getRemarks3() + "'," +
			"'" + xtlog.getRemarks4() + "'," +
			"'" + xtlog.getRemarks5() + "'," +
			"'" + xtlog.getRemarks6() + "'," +
			"'" + xtlog.getPaperExpected() + "')";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("XtlogDao : Error inserting XTLOG / " + ex,ex);
		}	
	}
	
public void insertXtloge(Short examYear, Short examPeriod, String studyUnit, Short paperNo, String dateReceived) throws Exception {
		
		String sql = "insert into xtloge (paper_no,typist,paper_format,date_received,date_proof1,date_proof2,date_proof3,date_to_print," +
				"cpf_report_printed,fk_study_unit_code,fk_exam_period_cod,fk_exam_year,date_in_safe,date_to_dept,date_from_dept,date2_to_print," +
				"date2_from_print,date3_to_print,date3_from_print,quant3_to_print,quant_to_print,quant_calced_on,quant2_to_print," +
				"quant2_calced_on,surplus_quant,date_scanned,date_afr_scanned,open_for_web,docket_changes,paper_changes,electronic_paper) " +
			"values " +
			"(" + paperNo + "," +                                  //paper_no
			"' '," +                                               //typist
			"' '," +                                               //paper_format
			"to_date('" + dateReceived + "','YYYY-MM-DD')," +        //date_received
			"to_date('00010101','YYYYMMDD')," +                    //date_proof1
			"to_date('00010101','YYYYMMDD')," +                    //date_proof2
			"to_date('00010101','YYYYMMDD')," +                    //date_proof3
			"to_date('00010101','YYYYMMDD')," +                    //date_to_print
			"' '," +                                               //cpf_report_printed
			"'" + studyUnit + "'," +                               //fk_study_unit_code
			examPeriod + "," +                                     //fk_exam_period_cod
			examYear + "," +                                       //fk_exam_year
			"to_date('00010101','YYYYMMDD')," +                    //date_in_safe
			"to_date('00010101','YYYYMMDD')," +                    //date_to_dept
			"to_date('00010101','YYYYMMDD')," +                    //date_from_dept
			"to_date('00010101','YYYYMMDD')," +                    //date2_to_print
			"to_date('00010101','YYYYMMDD')," +                    //date2_from_print
			"to_date('00010101','YYYYMMDD')," +                    //date3_to_print
			"to_date('00010101','YYYYMMDD')," +                    //date3_from_print
			0 + "," +                                              //quant3_to_print
			0 + "," +                                              //quant_to_print
			"to_date('00010101','YYYYMMDD')," +                    //quant_calced_on
			0 + "," +                                              //quant2_to_print
			"to_date('00010101','YYYYMMDD')," +                    //quant2_calced_on
			0 + "," +                                              //surplus_quant
			"to_date('00010101','YYYYMMDD')," +                    //date_scanned
			"to_date('00010101','YYYYMMDD')," +                    //date_afr_scanned
			"'Y'," +                                               //open_for_web
			"'N'," +                                               //docket_changes
			"'N'," +                                               //paper_changes
			"'Y')";                                                //electronic_paper		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("XtlogDao : Error inserting XTLOGE / " + ex,ex);
		}	
	}

	public Xtloge getXtloge(Short examYear, Short examPeriod, String studyUnit, Short paperNr) throws Exception {
		Xtloge logRec = new Xtloge();
		
		String sql = "select fk_exam_year, fk_exam_period_cod, fk_study_unit_code, paper_no, to_char(date_received,'YYYY-MM-DD') as dateReceived," +
		" to_char(date_to_dept,'YYYY-MM-DD') as dateToDept, to_char(date_from_dept,'YYYY-MM-DD') as dateFromDept, to_char(date_to_print,'YYYY-MM-DD') as dateToPrint, to_char(date2_to_print,'YYYY-MM-DD') as date2ToPrint," +
		" to_char(date3_to_print,'YYYY-MM-DD') as date3ToPrint, quant3_to_print," +
		" quant_to_print, to_char(quant_calced_on,'YYYY-MM-DD') as quantCalcedOn, quant2_to_print, to_char(quant2_calced_on, 'YYYY-MM-DD') as quant2CalcedOn," +
		" open_for_web, docket_changes, paper_changes, electronic_paper" +
        " from xtloge " +
        " where fk_exam_year = " + examYear +
        " and fk_exam_period_cod = " + examPeriod +
        " and fk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and paper_no = " + paperNr;  
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				logRec.setExamYear(Short.parseShort(data.get("fk_exam_year").toString()));	
				logRec.setExamPeriod(Short.parseShort(data.get("fk_exam_period_cod").toString()));
				logRec.setStudyUnit(data.get("fk_study_unit_code").toString());
				logRec.setPaperNo(Short.parseShort(data.get("paper_no").toString()));
				logRec.setDateReceived(replaceNull(data.get("dateReceived")));
				logRec.setDateToDept(replaceNull(data.get("dateToDept")));
				logRec.setDateFromDept(replaceNull(data.get("dateFromDept")));
				logRec.setDateToPrint(replaceNull(data.get("dateToPrint")));
				logRec.setDate2ToPrint(replaceNull(data.get("date2ToPrint")));
				logRec.setDate3ToPrint(replaceNull(data.get("date3ToPrint")));
				logRec.setQuantToPrint(replaceNull(data.get("quant_to_print")));
				logRec.setQuant2ToPrint(replaceNull(data.get("quant2_to_print")));
				logRec.setQuant3ToPrint(replaceNull(data.get("quant3_to_print")));
				logRec.setQuantCalcOn(replaceNull(data.get("quantCalcedOn")));
				logRec.setQuant2CalcOn(replaceNull(data.get("quant2CalcedOn")));
				logRec.setOpenForWeb(replaceNull(data.get("open_for_web")));
				logRec.setDocketChanges(replaceNull(data.get("docket_changes")));
				logRec.setPaperChanges(replaceNull(data.get("paper_changes")));
				logRec.setElectronicFlag(replaceNull(data.get("electronic_paper")));
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("XtlogeDao : Error reading table XTLOGE / " + ex,ex);
		}		
		return logRec;		
	}
	
	public void updateXtloge(Xtloge xtloge) throws Exception {
		
		String sql = "update xtloge set" +
				" date_received = to_date('" + xtloge.getDateReceived() + "','YYYY-MM-DD')," +
				" date_to_dept = to_date('" + xtloge.getDateToDept() + "','YYYY-MM-DD')," +
				" date_from_dept = to_date('" + xtloge.getDateFromDept() + "','YYYY-MM-DD')," +
				" date_to_print = to_date('" + xtloge.getDateToPrint() + "','YYYY-MM-DD')," +
				" quant_to_print = " + Short.parseShort(xtloge.getQuantToPrint()) + "," +
				" quant_calced_on = to_date('" + xtloge.getQuantCalcOn() + "','YYYY-MM-DD')," +
				" date2_to_print = to_date('" + xtloge.getDate2ToPrint() + "','YYYY-MM-DD')," +
				" quant2_to_print = " + Short.parseShort(xtloge.getQuant2ToPrint()) + "," +
				" quant2_calced_on = to_date('" + xtloge.getQuant2CalcOn() + "','YYYY-MM-DD')," +
				" electronic_paper = '" + xtloge.getElectronicFlag() + "'" +
				" where fk_exam_year = " + xtloge.getExamYear() + 
				" and fk_exam_period_cod = " + xtloge.getExamPeriod() + 
				" and fk_study_unit_code = '" + xtloge.getStudyUnit().toUpperCase() + "'" + 
				" and paper_no = " + xtloge.getPaperNo();		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("XtlogDao : Error updating XTLOGE / " + ex,ex);
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
