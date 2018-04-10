package za.ac.unisa.lms.tools.smsbatch.dao;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;
import java.sql.SQLException;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;

import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.*;
import za.ac.unisa.utils.*;

public class SmsBatchDAO extends StudentSystemDAO{
	
	
	public void doMultiAdd() throws Exception {
		
		String sql = "insert all";
		for (int i = 100; i < 600; i++){
            sql = sql + " into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) values (56735," + String.valueOf(i) + ",8033048,'+27824145781','test',1127330)"; 
		} 
   
        sql = sql + " select * from dual";			

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("SMSBatchDAO : Error inserting SMSQUE / " + ex,ex);
		}	
	}
	
public void doMultiAdd2() throws Exception {
	
		final int count = 30000; 
		
		String sql = "insert into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) " +
		"values (56735,?,8033048,'+27824145781','test',1127330)";		

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			jdt.batchUpdate(sql, new BatchPreparedStatementSetter() {   
					
			public void setValues(PreparedStatement ps, int i) throws SQLException {  
				ps.setInt(1, i + 11100);  
			    }                        
				
				public int getBatchSize() {                                
					return count;                        
					}                
			});	
		}
		catch (Exception ex) {
			throw new Exception("SMSBatchDAO : Error inserting SMSQUE / " + ex,ex);
		}	
	}

public class batchUpdate {        
	
	private JdbcTemplate jdbcTemplate;        
	public void setDataSource(DataSource dataSource) {                
		this.jdbcTemplate = new JdbcTemplate(dataSource);        
		}        
		
	public void doBatch() {                
		final int count = 500;   
		
		jdbcTemplate.batchUpdate(                
				"insert into smsque (MK_BATCH_NR,SEQUENCE_NR,REFERENCE_NR,CELL_NR,MESSAGE,MK_PERS_NR) " +
				"values (56735,?,8033048,'+27824145781','test',1127330)",                
			
		new BatchPreparedStatementSetter() {   
					
			public void setValues(PreparedStatement ps, int i) throws SQLException {  
				ps.setInt(1, i + 600);  
			    }                        
				
				public int getBatchSize() {                                
					return count;                        
					}                
			}
		);        
					
					
		}
}
	
	public List getCountries(HttpServletRequest request) throws Exception{
		
		List list = new ArrayList();				
				
		String sql="select lns.code,lns.eng_Description from Lns lns where lns.code ='1015'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				list.add(new LabelValueBean(label,value + " : " + label));						
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading table lns / " + ex);
		}		
		//get rest of countries
		
		sql="select lns.code, lns.eng_Description from Lns lns where lns.in_Use_Flag ='Y' and lns.code <> '1015' ORDER BY lns.eng_Description";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				list.add(new LabelValueBean(label,value + " : " + label));						
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading table lns / " + ex);
		}		
		return list;	
		
	}
	
	public List getRegions(HttpServletRequest request) throws Exception{
		List list = new ArrayList();
		
		String sql="select regoff.code,regoff.eng_Description from Regoff where service_type > ' ' order by regoff.eng_Description";
		

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				list.add(new LabelValueBean(label,value + " : " + label));						
		}
	}
	catch (Exception ex) {
		throw new Exception("SmsBatchDAO : Error reading table regoff / " + ex);
	}		
	return list;	
	
}
		
	public List getDistricts(HttpServletRequest request) throws Exception{
		List list = new ArrayList();
		
		String sql = "select ldd.code,ldd.eng_Description from Ldd ldd where ldd.in_Use_Flag='Y' order by ldd.eng_Description";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				list.add(new LabelValueBean(label,value + " : " + label));						
		}
	}
	catch (Exception ex) {
		throw new Exception("SmsBatchDAO : Error reading table ldd / " + ex);
	}		
	return list;	
	}
	
	public ArrayList getSearchCodeList(HttpServletRequest request,String searchType, String searchCriteria, String criteriaType, short year, String registrationPeriod) throws Exception{
	ArrayList list = new ArrayList();
	
	String sql ="";
	
		if("M".equalsIgnoreCase(criteriaType)){
			if ("code".equalsIgnoreCase(searchType)){
				sql = "select distinct s.code,s.eng_Long_Descripti as eng_desc " +
						"from Sun s, sunpdt p " +
						"where p.mk_Academic_Period = 1" +
						" and p.semester_Period=" + Short.valueOf(registrationPeriod.substring(0,1)) +
						" and p.mk_Academic_Year=" + Short.valueOf(Short.toString(year)) +
						" and p.fk_Suncode(+)= s.code" +
						" and s.in_Use_Flag = 'Y'" +
						" and upper(s.code) like '" + searchCriteria.toUpperCase() + "%'" +
						" order by s.code";
			}
			if ("description".equalsIgnoreCase(searchType)){
				sql = "select distinct s.code,s.eng_Long_Descripti as eng_desc " +
						"from Sun s, sunpdt p" +
						" where p.mk_Academic_Period = 1" +
						" and p.semester_Period=" + Short.valueOf(registrationPeriod.substring(0,1)) +
						" and p.mk_Academic_Year=" + Short.valueOf(Short.toString(year)) +
						" and p.fk_Suncode(+)= s.code" +
						" and s.in_Use_Flag = 'Y'" +
						" and upper(s.eng_Long_Descripti) like '" + searchCriteria.toUpperCase()+ "%'" +
						" order by s.eng_Long_Descripti";				
			}
		}else if("Q".equalsIgnoreCase(criteriaType)){
			if ("code".equalsIgnoreCase(searchType)){	
				sql = "select g.code,g.eng_Description as eng_desc" +
						" from Grd g" +
						" where g.in_Use_Flag = 'Y'" +
						" and upper(g.code) like '" + searchCriteria.toUpperCase()+"%'" +
						" order by g.code";
			}
			if ("description".equalsIgnoreCase(searchType)){
				sql = "select g.code,g.eng_Description as eng_desc" +
						" from Grd g" +
						" where g.in_Use_Flag = 'Y'" +
						" and upper(g.eng_Description) like '" + searchCriteria.toUpperCase()+"%'" +
						" order by g.eng_Description";
			}
		}
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESC").toString();
				list.add(new LabelValueBean(value + " : " + label,value + " : " + label));						
		}
	}
	catch (Exception ex) {
		throw new Exception("SmsBatchDAO : Error reading table ldd / " + ex);
	}		
	return list;	
	}
	
	public GeneralItem getModuleDetail(HttpServletRequest request,String moduleCode, short year, String registrationPeriod) throws Exception{
		
		GeneralItem item = new GeneralItem();
		
		String sql= "select s.code,s.eng_Long_Descripti" +
				" from Sun s, sunpdt p" +
				" where p.mk_Academic_Period = 1" +
				" and p.semester_Period=" + Short.valueOf(registrationPeriod) +
				" and p.mk_Academic_Year=" + Short.valueOf(Short.toString(year)) +
				" and p.fk_Suncode(+)= s.code" +
				" and s.in_Use_Flag = 'Y'" +
				" and s.code='" + moduleCode.toUpperCase() + "'";
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				item.setCode(data.get("CODE").toString());
				item.setDesc(data.get("ENG_LONG_DESCRIPTI").toString());
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading sun, sunpdt/ " + ex);
		}		
		return item;		
	}	
	
	public GeneralItem getQualificationDetail(HttpServletRequest request,String qualificationCode) throws Exception{
		List dbList = new ArrayList();
		GeneralItem item = new GeneralItem();
		
		String sql= "select g.code,g.eng_Description" +
				" from Grd g" +
				" where g.in_Use_Flag = 'Y'" +
				" and upper(g.code) = '" + qualificationCode.toUpperCase() + "'" +
				" order by g.code";

		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				item.setCode(data.get("CODE").toString());
				item.setDesc(data.get("ENG_DESCRIPTION").toString());
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading grd / " + ex);
		}		
	return item;		
}			
		
	public List getGeneralCodesList(HttpServletRequest request, String genCode, int sortOrder) throws Exception{
		
		String orderBy = "";
		if (sortOrder == 0){
			orderBy = " ORDER BY gencod.code";
		}else if (sortOrder == 1){
			orderBy = " ORDER BY gencod.eng_Description";
		}else if (sortOrder == 2){
			orderBy = " ";
		}
		
		List list = new ArrayList();
		
		String sql = "SELECT gencod.code, gencod.eng_Description" +
				" FROM Gencod gencod" +
				" WHERE gencod.fk_Gencatcode =" + genCode +
				" AND gencod.in_Use_Flag='Y' "+ orderBy;
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				list.add(new LabelValueBean(label,value + " : " + label));						
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading table gencod / " + ex);
		}		
		return list;	
	}	
	
public String getPossibleDuplicateSMS(String selectionCriteria, String message) throws Exception{
		
		String duplicateSMS="";	
		
		String sql = "select batch_nr from smsreq" +
					" where sms_msg='" + message + "'" +
					" and selection_criteria='" + selectionCriteria + "'" +
					" and to_char(request_timestamp,'YYYYMMDD')=to_char(sysdate,'YYYYMMDD')";
					
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			
		
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (duplicateSMS.equalsIgnoreCase("")){
					duplicateSMS = data.get("batch_nr").toString();	
				} else {
					duplicateSMS = duplicateSMS +", " + data.get("batch_nr").toString();	
				}
												
			}
		}
		catch (Exception ex) {
			throw new Exception("SmsBatchDAO : Error reading table smsreq / " + ex);
		}		
		return duplicateSMS;	
	}	
}
