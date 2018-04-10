package za.ac.unisa.lms.tools.sitestatsexport.dao;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NameList;

import javax.xml.crypto.Data;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.jdbc.core.JdbcTemplate;

//import com.sun.rowset.internal.Row;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.db.StudentSystemDAO;


public class SitestatsexportDAO extends StudentSystemDAO {

	 private Map collegeMap;
	 private Map collegeMap1;
	    
	   public SitestatsexportDAO()
	    { 
	    	collegeMap = new HashMap<String,String>();
	   	    collegeMap1=Collections.synchronizedMap(new TreeMap());}
	
	
	//Get all colleges i.e. all the college names in the colleg table
	
	
	public ArrayList getColleges()
    {
                       String sql=
                           " select code,eng_description COLLEGE from colleg "+
                           " order by eng_description ";
                       ArrayList results = new ArrayList();
                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                       List queryList;
                       queryList = jdt.queryForList(sql);
                       Iterator i = queryList .iterator();
                       while(i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                           String college= data.get("COLLEGE").toString();
                           String code   = data.get("CODE").toString();                            
                           results.add(new org.apache.struts.util.LabelValueBean(college,code));
                           //collegeMap.put(code, college);                            
                       }
         return results;
   }
	
	/** Get all the Department names for the institution under a given college code
	 *
	 * @param d (department name)
	 * @return
	 */
	public ArrayList getSchools(String code)
	 {
		String sql =
					" select CODE, eng_description SCHOOL "+ 
					" from school "+
					" where college_code = "+code+
					" and in_use_flag ='Y' "+
					" order by school "; 
		
		 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					
					String sch= data.get("SCHOOL").toString();
					String schcode   = data.get("CODE").toString();					
				    results.add(new org.apache.struts.util.LabelValueBean(sch,schcode));
				  }
		return results;
	 }
	
	/** Get all the Department names for the institution under a given college code
	 *
	 * @param d (department name)
	 * @return
	 */
	public ArrayList getDepts(String code, String collegeCode)
	 {
		String sql =" SELECT eng_description department,code from dpt " +
				    " where SCHOOL_CODE = "+code+
				    " and COLLEGE_CODE = "+collegeCode+
				    " and in_use_flag ='Y' "+
				    " order by eng_description ";
		 
		 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					
					String dept= data.get("DEPARTMENT").toString();
					String deptcode   = data.get("CODE").toString();					
				    results.add(new org.apache.struts.util.LabelValueBean(dept,deptcode));
				  }
		return results;
	 }
	/*
	 * check User ID is valid or not.
	 */
	public boolean isUserIdValid(String userId) throws Exception{
		String query = " select COUNT(*) as COUNT  from USRSUN where NOVELL_USER_ID ="+"'"+userId+"'";
        	try {
        	    String recCount = this.querySingleValue(query,"COUNT");
        	    int counter =  Integer.parseInt(recCount); 

        		if(counter > 0){
        			return true;
        		}	else {
        			return false;
        		}
        		
        	} catch (Exception ex) {
        		throw new Exception("novell userId valid or Not: Error occurred / "+ ex,ex);
        		
        	}
		
    }
	
	/*
	 *  course codes per department.
	 */
	
	public ArrayList getCourseCodes(String dept)
    {
                       String sql = " select distinct s.mk_department_code as DEPT_CODE, s.code as CODE from sun s " +
                       		        " where s.mk_department_code ="+dept+
                       		        " and s.IN_USE_FLAG='Y'"+
                       		        " order by s.code";
                       ArrayList results = new ArrayList();
                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                       List queryList;
                       queryList = jdt.queryForList(sql);
                       Iterator i = queryList .iterator();
                       while(i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                        
                           String code   = data.get("CODE").toString();
                           String code1   = data.get("CODE").toString();
                           results.add(new org.apache.struts.util.LabelValueBean(code,code1));
                           //collegeMap.put(code, college);                            
                       }
         return results;
   }
	
	/*
	 * gets the events
	 */
	public ArrayList getEvents() throws FileNotFoundException
    {
		   /* String SE = ServerConfigurationService.getString("SE");
                       String sql = " select distinct event_id as EVENT from "+SE+" order by EVENT";
                      ArrayList results = new ArrayList();
                       
                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                       List queryList;
                       queryList = jdt.queryForList(sql);
                       Iterator i = queryList .iterator();
                       while(i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                        
                           String event   = data.get("EVENT").toString();
                          
                           results.add(new org.apache.struts.util.LabelValueBean(event,event));
                          // results.addElement(event);
                           //collegeMap.put(code, college);                            
                       }
         return results; */
		
	    /*ArrayList results = new ArrayList();
	    //On local
	    //Scanner res = new Scanner(new File("c:\\drd\\sitestats.txt"));
	    Scanner res = new Scanner(new File("/data/sakai/sitestatsexport/sitestats.txt"));
	    
        int count = 0;
        while (res.hasNextLine()) {
           String name = res.nextLine();
           results.add(new org.apache.struts.util.LabelValueBean(name,name));
           count++;
        }
	    
	     System.out.println("results "+results.size());
	    return results;*/
		
		ArrayList results = new ArrayList();
		
		try{
			//File inputFile = new File("C:\\Data\\myUnisa10.4\\workspace\\trunk\\sitestats\\sitestats-api\\src\\config\\org\\sakaiproject\\sitestats\\config\\tooleventsdef.xml");
			File inputFile = new File("/data/sakai/src/sakai/sitestats/sitestats-api/src/config/org/sakaiproject/sitestats/config/toolEventsDef.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("event");
			//System.out.println("Element Root : " + doc.getDocumentElement().getNodeName());
			//System.out.println("total elements : " + nList.getLength());
					
			for (int temp = 0; temp < nList.getLength(); temp++){
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//System.out.println(eElement.getAttribute("eventId"));
					
					String name = eElement.getAttribute("eventId");
					results.add(new org.apache.struts.util.LabelValueBean(name,name));
				}
				
			}
			
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return results;
  }
 
	
	/*
	 * gets site STATS export details.
	 */
	
	public ArrayList getSiteStatsExport(String year, String collegeCode, String schCode, String deptCode, String userId, String eventId, String courseCode, String semister, String yr, String month, boolean valid){
		
	   ArrayList results = new ArrayList();
       String period = yr+"-"+month;
       String semister1="";
       if(semister.equals("1")){
    	   semister1="S1";
       } if(semister.equals("2")){
    	   semister1="S2";
       } if(semister.equals("0")){
    	   semister1="Y1";
       } if(semister.equals("6")){
    	   semister1="Y2";
       }
       String SI = ServerConfigurationService.getString("SI");
       String SE = ServerConfigurationService.getString("SE");
	   String sql =  " SELECT C.ENG_DESCRIPTION AS COLLEGE, S.ENG_DESCRIPTION AS SCH, D.ENG_DESCRIPTION AS DEPT, SE.SITE_ID AS COURSECODE,"+
	                 " U.ACCESS_LEVEL AS ROLE, U.NOVELL_USER_ID AS USERID, SE.EVENT_ID AS EVENT, TO_CHAR (SE.EVENT_DATE,'MON YY') AS MONTH, "+
	                 " SUM (SE.EVENT_COUNT) AS EVENT_COUNT FROM COLLEG C, SCHOOL S, DPT D, SUN, USRSUN U, "+
	                 SI+" SI,"+SE+" SE "+
	                 " WHERE U.SYSTEM_TYPE = 'L'"+ 
	                 " AND U.MK_ACADEMIC_YEAR ="+year+
	                 " AND U.ACCESS_LEVEL IN ('CADMN','PRIML','SECDL','TUTOR') "+
	                 " AND SUBSTR (SE.SITE_ID, 1, 7) = SUN.CODE "+ 
	                 " AND SUBSTR (SE.SITE_ID, 9, 2) = "+"'"+year.substring(2,4)+"'"+
	                 " AND SUN.COLLEGE_CODE ="+collegeCode;
	                 if(schCode.equals("-1")){
	                	 sql=sql;
	                 }else{sql=sql+
	                 " AND SUN.SCHOOL_CODE ="+schCode;
	                 }
	                 if(deptCode.equals("-1")){
	                	 sql=sql;
	                 }else{
	                	 sql=sql+
	                	   " AND SUN.MK_DEPARTMENT_CODE="+deptCode;
	                 }
	                 if(eventId.equals("-1")){
	                	 sql=sql;
	                 }else{sql=sql+
	                	  " AND SE.EVENT_ID = "+"'"+eventId+"'";
	                 }
	                 if(courseCode.equals("-1")){
	                	 sql=sql;
	                 }else{
	                	 sql =sql+
	                	  " AND SUN.CODE = "+"'"+courseCode+"'";
	                 }	            
	                 if(userId.equals(null)||userId.equals("")||userId.equals("o")||valid==false){
	                	sql=sql;
	                 }else if(valid==true){sql = sql+
	                	 
	                 " AND U.NOVELL_USER_ID = "+"'"+userId+"'";
	                 
	                 }
	                 if(semister.equals("-1")){
	                 sql=sql+
	                	 " AND U.MK_SEMESTER_PERIOD IN(1,2,0,6)";
	                 }else{
	                	 sql=sql+
	                	 " AND U.MK_SEMESTER_PERIOD ="+semister;
	                 }
	                 if(semister.equals("-1")){
	                	 sql=sql+
	                	 " AND SUBSTR (SE.SITE_ID, 12, 2) IN ('S1','S2','Y1','Y2')";
	                 }else{
	                	 sql=sql+
	                 " AND SUBSTR (SE.SITE_ID, 12, 2) ="+"'"+semister1+"'";
	                 }
	                 if(month.equals("0")){
	                	 period = yr;
	                	 sql = sql+" AND TO_CHAR(SE.EVENT_DATE, 'YYYY')= "+"'"+period+"'";
	                 }else{
	                	 sql = sql+" AND TO_CHAR(SE.EVENT_DATE, 'YYYY-MM')= "+"'"+period+"'";
	                 }
	                 sql = sql+
	                 " AND SI.EID = LOWER (U.NOVELL_USER_ID)"+ 
	                 " AND SE.USER_ID = SI.USER_ID "+
	                 " AND SUN.COLLEGE_CODE = C.CODE AND SUN.COLLEGE_CODE = S.COLLEGE_CODE"+ 
	                 " AND SUN.SCHOOL_CODE = S.CODE AND SUN.MK_DEPARTMENT_CODE=D.CODE"+ 
	                 " AND U.MK_STUDY_UNIT_CODE = SUN.CODE "+
	                 " GROUP BY C.ENG_DESCRIPTION, S.ENG_DESCRIPTION, D.ENG_DESCRIPTION, SE.SITE_ID,"+ 
	                 " U.ACCESS_LEVEL, U.NOVELL_USER_ID, SE.EVENT_ID, TO_CHAR (EVENT_DATE, 'MON YY') "+
	                 " ORDER BY C.ENG_DESCRIPTION, S.ENG_DESCRIPTION, D.ENG_DESCRIPTION, SUBSTR (SE.SITE_ID, 1, 10), U.ACCESS_LEVEL, U.NOVELL_USER_ID, SE.EVENT_ID, TO_CHAR (EVENT_DATE, 'MON YY')";

	           
		  System.out.println("sql"+sql); 
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
	  SitestatsexportDetails sitestatsDetails = new SitestatsexportDetails();
			
		try{
			sitestatsDetails.setCollege(data.get("COLLEGE").toString());
			sitestatsDetails.setSchool(data.get("SCH").toString());
			sitestatsDetails.setDepartment(data.get("DEPT").toString());
			sitestatsDetails.setCourseCode(data.get("COURSECODE").toString());
			sitestatsDetails.setRole(data.get("ROLE").toString());
			sitestatsDetails.setPerson(data.get("USERID").toString());
			sitestatsDetails.setEvent(data.get("EVENT").toString());
			sitestatsDetails.setMonth(data.get("MONTH").toString());
			sitestatsDetails.setEventCount(data.get("EVENT_COUNT").toString());
	
			results.add(sitestatsDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}
		
	  }			  
 return results;
}
	
	private String querySingleValue(String query, String field){
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";
		

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
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
	
