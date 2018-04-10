package za.ac.unisa.lms.tools.brochures.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

public class BrochureDAO extends SakaiDAO {
	
	/*
	 *Set Audit Report
	 */
	public void setAuditReport(String userId, String timeStamp, String reportType,String college, int year, String format)throws Exception{
		
		String report ="";
		if(reportType.equals("myChoice")){
			report="UG";
			
		}else if(reportType.equals("myChoice M&D")){
			report="PG";
		}else if(reportType.equals("myRegistration")){
			report="UG";
		}else if(reportType.equals("myRegistration M&D")){
			report="PG";
			reportType="myReg M&D";
		}else if(reportType.equals("myModules")){
			report="myMod";
		}
		
		if(college.equals("-1")){
			college="ALL";
		}
		if(format.equals("1")){
			format="word";
		}else if(format.equals("2")){
			format="xml";
		}if(format.equals("3")){
			format="CCM";
		}
		
           String sql = " INSERT INTO REPORTS_AUDIT(AUDIT_USER, AUDIT_DATE, REPORT_TYPE, REPORT, COLLEGE, YEAR, FORMAT)"+
                        " VALUES (?, to_TIMESTAMP(?,'YYYY-MM-DD HH24:MI:SS.FF1'),?,?,?,?,?)";
     
           JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  	   try{
    		 jdt.update(sql,new Object[] {userId,timeStamp,reportType,report,college,year,format});
             System.out.println("inserted");
         
			  }catch(Exception ex){
				throw new Exception("BrochureDAO: setAuditReport"+ex);
			  }
			  System.out.println("inserted");
	 }
	
	  public ArrayList getAuditExport(){
		  
		
    	  
          String sql="select * from reports_audit";
          
          ArrayList results = new ArrayList();
          JdbcTemplate jdt = new JdbcTemplate(getDataSource());
          List queryList;
          queryList = jdt.queryForList(sql);
          Iterator i = queryList .iterator();
         
	         		  
	  while (i.hasNext()){	
     ListOrderedMap data = (ListOrderedMap) i.next();
	  BrochureDetails brochureDetails = new BrochureDetails();
	  MychoiceDAO mychoiceDAO = new MychoiceDAO();
		try{
			brochureDetails.setUserid(data.get("AUDIT_USER").toString());
			Date date = (Date)data.get("AUDIT_DATE");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			brochureDetails.setDate(dateFormat.format(date));
			brochureDetails.setReportType(data.get("REPORT_TYPE").toString());
			brochureDetails.setReport(data.get("REPORT").toString());
			String code = data.get("COLLEGE").toString();
			System.out.println("getAuditExport = "+code);
			if (code.equals("ALL")){
				brochureDetails.setCollege(code);
			}else{
				brochureDetails.setCollege(mychoiceDAO.getCollegeName(code));
			}
			brochureDetails.setYear(data.get("YEAR").toString());
			brochureDetails.setFormat(data.get("FORMAT").toString());
	
			results.add(brochureDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}
		
	  }
	 return results;
}	


}
