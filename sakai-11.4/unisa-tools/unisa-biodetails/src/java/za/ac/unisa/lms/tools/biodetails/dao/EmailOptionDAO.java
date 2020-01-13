package za.ac.unisa.lms.tools.biodetails.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.biodetails.actions.BioDetailsAction;
import za.ac.unisa.lms.tools.biodetails.forms.BioDetailsForm;

@SuppressWarnings("rawtypes")
public class EmailOptionDAO extends StudentSystemDAO{
	
	Log log = LogFactory.getLog(BioDetailsAction.class);

	public void updateSmsOption(BioDetailsForm biodetailsform) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		// Comment out by Johannes 04/05/2006
		//String sql = "update EMLOPT set NOTICES_ADMIN = '"+biodetailsform.getRegOption()+"' ,ASS_RESULTS = '"+biodetailsform.getAssignOption()+"' where STUDENT_NR = "+biodetailsform.getNumber();
		String sql = "update EMLOPT set ASS_RESULTS = '"+biodetailsform.getAssignOption()+"' where STUDENT_NR = "+biodetailsform.getNumber();
		jdt.update(sql);
	}
	
	public void updateExamBlockedOption(BioDetailsForm biodetailsform) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		
		String sql = "update STU set PUBLIC_WEB_BLOCK = '"+biodetailsform.getBlockExamResults()+"' where NR = "+biodetailsform.getNumber();
		jdt.update(sql);
	}

	public void getSmsOptions(BioDetailsForm bioDetailsForm) throws Exception {
		String query = "select NOTICES_ADMIN,ASS_RESULTS from EMLOPT where STUDENT_NR = "+bioDetailsForm.getNumber();
		Iterator i = null;
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			i = queryList.iterator();
			while(i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				//Changed by Johannes Ngoepe 04/05/2006
				//bioDetailsForm.setRegOption((String)data.get("NOTICES_ADMIN"));

				bioDetailsForm.setAssignOption((String)data.get("ASS_RESULTS"));
				/*if (bioDetailsForm.getRegOption()==null){
					bioDetailsForm.setRegOption("N");
				}*/
				if (bioDetailsForm.getAssignOption()==null){
					bioDetailsForm.setAssignOption("N");
				}
			}
		} catch(Exception ex){
			throw new Exception("EmailOptionDAO : Error reading table EMLOPT  / "+ ex,ex);
		}
	}
	
	public void getBlockedExamResults(BioDetailsForm bioDetailsForm) throws Exception {
		String query = "select PUBLIC_WEB_BLOCK from STU where NR = "+bioDetailsForm.getNumber();
		Iterator i = null;
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query);
			i = queryList.iterator();
			while(i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				bioDetailsForm.setBlockExamResults((String)data.get("PUBLIC_WEB_BLOCK"));
				
				if (bioDetailsForm.getBlockExamResults()==null){
					bioDetailsForm.setBlockExamResults("N");
				}
			}
		} catch(Exception ex){
			throw new Exception("EmailOptionDAO : Error reading table STU  / "+ ex,ex);
		}
	}

	public void writeToGeneral(String studentnr,String ipaddr,String cat, String descr, String system_gc6){
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		String sql = "insert into solgen (STUDENT_NR,TIMESTAMP,IPADDRESS,CATEGORY,DESCRIPTION,SYSTEM_GC6,SEQUENCE_NR)"+
					 "values("+studentnr+",sysdate,'"+ipaddr+"','"+cat+"','"+descr+"','"+system_gc6+"',SOLGEN_SEQUENCE_NR.nextval)";
		log.debug("SQL "+sql);
		jdt.update(sql);
	}
}
