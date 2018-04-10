package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import javax.sql.rowset.Joinable;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.pbooks.dao.submissionDatesDetails;

public class AuthorisationDAO extends StudentSystemDAO {
	
	
		
	public String getPersonnelNr(String novellUserId) throws Exception{
	      String persno="";
	
		String sql = "select persno  from staff" +
        " where novell_user_id ='" + novellUserId + "'" +
        " and (resign_date > sysdate" +
        " or resign_date is null)" +
        " and (persno < 52000000 or persno > 52999999)"; 
		try{
			persno = querySingleValue(sql,"persno");
		} catch (Exception ex) {
            throw new Exception("AuthorisationDAO : getPersonnelNr Error occurred / "+ ex,ex);
		}
		if(persno.length()==0){
			String sql2 = "select personnel_no  from usr" +
	        " where novell_user_code ='" + novellUserId + "'";
			try{
				persno = querySingleValue(sql2,"personnel_no");
			} catch (Exception ex) {
	            throw new Exception("AuthorisationDAO : getPersonnelNr Error occurred / "+ ex,ex);
			}
		}
	/*	public boolean checkIfNumber(String in) {
			
		}*/
		return persno;
	}
    public ArrayList getNoActionCourseList(String code,String year,String typeofbooklist) throws Exception {
		ArrayList courseList = new ArrayList();
		int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
		
		String sql = "select distinct c.ENG_DESCRIPTION college, s.CODE studyunit from colleg c,school su, dpt d, sun s "+
                     "where  su.college_code = c.code and s.in_use_flag = 'Y' and s.ACADEMIC_LEVEL <> 'D' and s.research_flag = 'N' and "+
                    "s.college_flag <> 'Y' and s.FORMAL_TUITION_FLA = 'F' and s.COLLEGE_CODE=c.code and s.school_code = su.code and d.code="+code+
              "and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") and d.code = "+
              "s.MK_DEPARTMENT_CODE and not exists(select * from pbcrsn p where p.mk_academic_year="+year+" and "+
              "p.BOOK_STATUS=  '"+typeofbooklist+"' and s.code=p.mk_study_unit_code) "+
              "group by c.ENG_DESCRIPTION, su.ENG_DESCRIPTION, d.ENG_DESCRIPTION, s.CODE "+
              "order by  s.CODE ";

		try{
		 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);		  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
		 	value = data.get("studyunit").toString();		 
			//label = data.get("college").toString();			
		  courseList.add(new LabelValueBean(label,value));						
		}		
	} catch (Exception ex) {
      throw new Exception("AuthorisationDAO : getNoActionCourseList Error occurred / "+ ex,ex);
	}	
	return courseList;
	}
    
    public ArrayList getAllModulesforDept(String code,String year,String typeofbooklist) throws Exception {
		ArrayList courseList = new ArrayList();
		int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
		
		String sql = "select  distinct s.CODE studyunit,p.status description "+
		 "from dpt d, pbcrsn p,sun s where d.code="+code+" and d.code=s.mk_department_code  and p.mk_academic_year="+year+
		 " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year >  "+acadyearLess1+") "+
		 "and  s.code=p.mk_study_unit_code  and p.BOOK_STATUS='"+typeofbooklist+"' order by studyunit";

		try{
		 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);		  
		  String value="";
		  String status="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
		 	value = data.get("studyunit").toString();			 	
			status = data.get("description").toString();		
			if(status.equals("0")){
				status="Booklist started but not submitted";
			}else if(status.equals("1")){
				status="Booklist submitted but not authorized";
			}else if(status.equals("2")){
				status="Booklist authorized by COD";
			}else if(status.equals("3")){
				status="Modules with no books declaration";
			}else if(status.equals("4")){
				status="Booklist authorized by School Director";
			}else if(status.equals("5")){
				status="Book list authorized by Dean";			
			}else if(status.equalsIgnoreCase("7")){
				status= "Booklist open for editing by administrator";	
			   }else if(status.equalsIgnoreCase("6")){
				   status="Book list published by administrator";	
			   }
		  courseList.add(new LabelValueBean(status,value));						
		}		
	} catch (Exception ex) {
      throw new Exception("AuthorisationDAO : getAllModulesforDept Error occurred / "+ ex,ex);
	}	
	return courseList;
	}
    
    public ArrayList getCourseListperdept(String code,String year,String status,String typeofbooklist ) throws Exception {
		ArrayList courseList = new ArrayList();
		int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
		String sql="  select  distinct s.CODE studyunit,p.BOOK_STATUS booktype "+
        "  from dpt d, pbcrsn p,sun s where d.code='"+code+"' and p.mk_academic_year='"+year+"' "+
        " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") "+
        " and  s.code=p.mk_study_unit_code and p.status= '"+status+"' and p.BOOK_STATUS='"+typeofbooklist+"' order by studyunit";	
		try{
		 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);		  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
		 	value = data.get("studyunit").toString();
			//label = data.get("booktype").toString();			
		  courseList.add(new LabelValueBean(label,value));						
		}		
	} catch (Exception ex) {
      throw new Exception("AuthorisationDAO : getCourseListperdept Error occurred / "+ ex,ex);
	}	
	return courseList;
	}
    
	public String courseValid(String subject) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String course;
		String sql = "select CODE from sun where IN_USE_FLAG='Y' and code = '"+subject+"'";
		String code = querySingleValue(sql,"CODE");
		if (code.length()> 0) {
			course=code;
		}else{
			course="";
		}				
		return course;
	}
	
	public String getStatus(String subject,String acadyear, String typeofBooklist) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String status;
		String sql = "SELECT status FROM pbcrsn where Mk_study_unit_code = '"+subject+"' and mk_academic_year= "+acadyear+" and BOOK_STATUS = '"+typeofBooklist+"' ";
		String confirmStatus = querySingleValue(sql,"STATUS");
		if (confirmStatus.length()> 0) {
			status=confirmStatus;
		}else{
			status="";
		}				
		return status;
	}
    
	
	public List getDeanAllDeptList(Integer personnelNo)  throws Exception {
		ArrayList dptList = new ArrayList();
		
	
/*	String sql ="select distinct code, eng_description from dpt where ((code >199 and code<400) or code>700) and IN_USE_FLAG='Y' and "+
	 "college_code=(select code from colleg where dean="+ personnelNo+" or deputy_dean1="+ personnelNo+" or DEPUTY_DEAN2="+ personnelNo+" or "+
	 "DEPUTY_DEAN3="+ personnelNo+" or DEPUTY_DEAN4="+ personnelNo+")";*/
		//Get the dept list for Dean 
		 String sql1 = "select distinct code, eng_description from dpt where ((code >199 and code<400) or code>700) " +
		 		"and IN_USE_FLAG='Y' and college_code=(select code from colleg where dean = "+ personnelNo+") order by eng_description";
		//Get the dept list for deputy dean  
		 String sql2 = "select distinct code, eng_description from dpt where ((code >199 and code<400) or code>700)" +
		 		" and IN_USE_FLAG='Y' and college_code   in (select college_code from usrdpt " +
		 		"where personnel_no = "+ personnelNo+" and type = 'COLLEGE' ) order by eng_description";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList1 = jdt.queryForList(sql1);
			List queryList2 = jdt.queryForList(sql2);
			//combine two lists 
			List<?> queryList = ListUtils.union(queryList1, queryList2);
			 
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				value=value+"-"+label;	
				dptList.add(new LabelValueBean(label,value));				
			}
			}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO :getDeanAllDeptList Error reading table colleg and dpt/ " + ex);
		}		
		return dptList;		
	}
	
	public ArrayList getSchDirDepts(int persno)
	 {
		String sql1 =" select eng_description department,code from dpt "+
                    "where ((code >199 and code<400) or code>700) and " +
                    "school_code=(select code from school where IN_USE_FLAG='Y' and head_of_school="+persno+") and "+
                    "college_code=(select college_code from school where IN_USE_FLAG='Y' and head_of_school="+persno+")"+
				    " order by eng_description ";
		
		 String sql2 = "select eng_description department, code from dpt where ((code >199 and code<400) or code>700)" +
	 		" and IN_USE_FLAG='Y' and college_code   in (select college_code from usrdpt " +
	 		"where personnel_no = "+ persno+" and type = 'SCHOOL' ) and " +
	 		" school_code in (select school_code from usrdpt where personnel_no = "+ persno+"  and type = 'SCHOOL') order by eng_description";
	
		ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					List queryList1 = jdt.queryForList(sql1);
					List queryList2 = jdt.queryForList(sql2);
				  List<?> queryList = ListUtils.union(queryList1, queryList2);
				  Iterator i = queryList .iterator();
				  String value="";
				  String label="";  
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
				    label= data.get("department").toString();
				    value   = data.get("code").toString();
					value=value+"-"+label;	
				 	results.add(new LabelValueBean(label,value));	
				  }
		return results;
	 }
	
	public Map getSchools1(String code)
	 {
		String sql =" select eng_description SCHOOL,CODE  from school " +
				" where college_code =(select CODE from colleg where ENG_DESCRIPTION= '"+code+"')"+
                 " and in_use_flag ='Y'  order by school";
					
		
		  Map results = Collections.synchronizedMap(new TreeMap());

				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
				
					String sch= data.get("SCHOOL").toString();
					String schcode   = data.get("CODE").toString();					
					results.put(schcode,sch);//Maps codes to colleges
				
				  }
		return results;
	 }

	public String getCODPersno(Integer personnelNo) throws Exception {
		 String deptCode="";
		
		String sql = "select head_of_dept " +
	                 "from dpt " +
	                 "where IN_USE_FLAG='Y' and head_of_dept = " + personnelNo;
	                
		try{ 
			deptCode = querySingleValue(sql,"head_of_dept");
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getCODDept Error occurred / "+ ex,ex);
		}
			
		return deptCode;		
	}
	public String getSchoolDirector(Integer personnelNo) throws Exception {
		 String head="";
		
		/*String sql = "select s.head_of_school headofschool from school s, dpt d where"+
                     " s.college_code = d.college_code and s.code = d.school_code  and s.head_of_school = d.head_of_dept and s.Head_of_School = "+personnelNo;*/
		 
		 String sql = "select head_of_school  from school where"+
                      " IN_USE_FLAG ='Y' and Head_of_School = "+personnelNo;
	                	
		try{ 
			head = querySingleValue(sql,"head_of_school");
			if(!(head.length()==0)){
				head="schoolDir";
			}
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getSchoolDirector Error occurred / "+ ex,ex);
		}
	/*	if(head.length()==0){
			String sql2 ="select code from colleg where (deputy_dean1 = " + personnelNo +
			" or deputy_dean2 = " + personnelNo +
			" or deputy_dean3 = " + personnelNo +
			" or deputy_dean4 = " + personnelNo+")";
			try{ 
				head = querySingleValue(sql2,"code");
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : getSchoolDirector Error occurred / "+ ex,ex);
			}}*/
		if(head.length()==0){
			String sql2 ="select PERSONNEL_NO from usrdpt where type = 'SCHOOL' and personnel_no ="+personnelNo;
			try{ 
				head = querySingleValue(sql2,"PERSONNEL_NO");
				if(!(head.length()==0)){
					head="standInSch";
				}				
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : getSchoolDirector Error occurred / "+ ex,ex);
			}}
		
		return head;		
	}
	public String getSchDirectorUserCode(String coursecode)throws Exception{
		 String headofschool="";
		 String persno="";
			
			String sql =  " select distinct sc.HEAD_OF_SCHOOL HEAD_OF_SCHOOL "+
                          " from sun su, school sc where su.COLLEGE_CODE=sc.college_code and  SU.school_code=sc.code "+
				          " and su.code = '"+coursecode+"'  and sc.IN_USE_FLAG='Y' and su.IN_USE_FLAG='Y'" ;
					
			/*"select distinct sc.HEAD_OF_SCHOOL HEAD_OF_SCHOOL"+
                         " from sun su,dpt d,school sc where d.COLLEGE_CODE=sc.college_code and  d.school_code=su.school_code and d.school_code=sc.code " +
                         " and su.mk_department_code = d.CODE and su.code = '"+coursecode+"' and sc.IN_USE_FLAG='Y' and d.IN_USE_FLAG='Y' and su.IN_USE_FLAG='Y'";*/
		                
			try{ 
				persno = querySingleValue(sql,"HEAD_OF_SCHOOL");
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : getSchDirectorUserCode Error occurred / "+ ex,ex);
			}		
			String sql2="select novell_user_id from staff where persno="+persno;
			try{ 
				headofschool = querySingleValue(sql2,"novell_user_id");
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : getSchDirectorUserCode Error occurred / "+ ex,ex);
			}	
			if(headofschool.length()==0){
				String sql3="select NOVELL_USER_CODE from usr where PERSONNEL_NO= '"+persno+"'";
				try{ 
					headofschool = querySingleValue(sql3,"NOVELL_USER_CODE");
				}catch (Exception ex) {
					 throw new Exception("AuthorisationDAO : getSchDirectorUserCode Error occurred / "+ ex,ex);
				}	
			}		
			
			return headofschool;	
	}
	public String getActingCODDept(Integer personnelNo) throws Exception {		
		String deptCode="";
		String sql = "select personnel_no" +
				" from usrdpt" +
				" where type='DPT' and personnel_no= " + personnelNo;	
		try{ 			
			deptCode = querySingleValue(sql,"personnel_no");
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getActingCODDept Error occurred / "+ ex,ex);
		}
		return deptCode;		
	}
	
public DeanForm selectSchDir(String course, String type) throws Exception {
		
		DeanForm deanform=new DeanForm();
		String select2="";
		String schDirPersno="";
		
		String select ="select PERSONNEL_NO from usrdpt u, sun s where u.type = 'SCHOOL' and s.college_code = u.college_code " +
				" and s.code =  '"+course+"' and s.school_code = u.school_code ";
		
   /*  String select = "select DEAN,DEPUTY_DEAN1,DEPUTY_DEAN2,DEPUTY_DEAN3,DEPUTY_DEAN4 from colleg co,dpt d,sun su where "+
            "d.COLLEGE_CODE=co.code and d.COLLEGE_CODE = su.COLLEGE_CODE and d.CODE=su.mk_department_code and  su.code = '"+course+"'";*/
		
			if(type.equals("COD")){
				 select2 = "select distinct sc.HEAD_OF_SCHOOL HEAD_OF_SCHOOL "+
                 " from sun su, school sc where su.COLLEGE_CODE=sc.college_code and  SU.school_code=sc.code "+
		          " and su.code = '"+course+"'  and sc.IN_USE_FLAG='Y' and su.IN_USE_FLAG='Y'" ;
				 
					 schDirPersno = querySingleValue(select2,"HEAD_OF_SCHOOL");
			}
			
			if(!(schDirPersno.length()==0)){
				String sql= "select initials,surname,novell_user_id from staff "+
				            "where persno="+schDirPersno;		
				try{ 			
		     		 deanform.setSchDirSurname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
					 deanform.setSchDirNetworkId(querySingleValue(sql,"novell_user_id"));			 
				}catch (Exception ex) {
					 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
				}
				}	
		
		return deanform;
	}
	
/*public ArrayList selectStandinSchDir(String course) throws Exception {
	
	ArrayList standInSchList = new ArrayList();
	
	String options="";
	
	String select = " select initials,surname,novell_user_id from staff st,usrdpt u, sun s " +
			"where   u.type = 'SCHOOL' and s.college_code = u.college_code " +
			" and s.code =  '"+course+"' and s.school_code = u.school_code and u.personnel_no = st.persno";

	try{
		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
		List atList = jdt.queryForList(select);
		Iterator j = atList.iterator();
		while (j.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) j.next();
            String initial = data.get("initials").toString();
            String surname = data.get("surname").toString();
            String name = initial+" "+surname;
            String novell_user_id = data.get("novell_user_id").toString();
         
            StandInScholDirListForm standInScholDirListForm = new StandInScholDirListForm();
            standInScholDirListForm.setStandinSchDirName(name);
            standInScholDirListForm.setNovelUserCode(novell_user_id);
            standInSchList.add(standInScholDirListForm);

	}     		
	}catch (Exception ex) {
		throw new Exception("AuthorisationDAO: selectStandinSchDir: Error occurred / "+ ex,ex);
	} 

	return standInSchList;
}*/
public List selectStandinSchDir (String course) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	List results = jdt.query("select initials,surname,novell_user_id from staff st,usrdpt u, sun s where  " +
			" u.type = 'SCHOOL' and s.college_code = u.college_code and s.school_code = u.school_code and u.personnel_no = st.persno and s.code = ?",
	new Object[] { course }, new int[] { Types.VARCHAR },
	new StandInSchMapper());

	return results;
}

public List selectDeputyDeans (String course) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	List results = jdt.query("select  initials,surname,novell_user_id from staff st, usrdpt u, sun s where" +
			" u.type = 'COLLEGE' and s.college_code = u.college_code and  u.personnel_no = st.persno and S.CODE = ?",
	new Object[] { course }, new int[] { Types.VARCHAR },
	new StandInSchMapper());

	return results;
}

public List getActingCod (String code) throws Exception {
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	List results = jdt.query("select NOVELL_USER_CODE,INITIALS,SURNAME FROM STAFF, USRDPT where PERSONNEL_NO=PERSNO and TYPE='DPT' and MK_DEPARTMENT_CODE=?",					
				new Object[] { code }, new int[] { Types.VARCHAR },
			new StandinMapper());			


	return results;
}

public DeanForm getDean(String course, String type) throws Exception {
	
	DeanForm deanform=new DeanForm();
	
	String select = "select DEAN,DEPUTY_DEAN1,DEPUTY_DEAN2,DEPUTY_DEAN3,DEPUTY_DEAN4 from colleg co,dpt d,sun su where "+
        "d.COLLEGE_CODE=co.code and d.COLLEGE_CODE = su.COLLEGE_CODE and d.CODE=su.mk_department_code and  su.code = '"+course+"'";
	
	String deanPersNo=querySingleValue(select,"DEAN");

	if(!(deanPersNo.length()==0)){
	String sql= "select initials,surname,novell_user_id from staff "+
	            "where persno="+deanPersNo;		
	try{ 			
 		 deanform.setDeanSurname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
		 deanform.setDeanNetworkId(querySingleValue(sql,"novell_user_id"));			 
	}catch (Exception ex) {
		 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
	}
	}
		
	return deanform;
}

	public DeanForm selectDean(String course, String type) throws Exception {
		
		DeanForm deanform=new DeanForm();
		String select2="";
		String schDirPersno="";
			String select = "select DEAN,DEPUTY_DEAN1,DEPUTY_DEAN2,DEPUTY_DEAN3,DEPUTY_DEAN4 from colleg co,dpt d,sun su where "+
            "d.COLLEGE_CODE=co.code and d.COLLEGE_CODE = su.COLLEGE_CODE and d.CODE=su.mk_department_code and  su.code = '"+course+"'";
		
			if(type.equals("COD")){
				 select2 = "select distinct sc.HEAD_OF_SCHOOL HEAD_OF_SCHOOL "+
                 " from sun su, school sc where su.COLLEGE_CODE=sc.college_code and  SU.school_code=sc.code "+
		          " and su.code = '"+course+"'  and sc.IN_USE_FLAG='Y' and su.IN_USE_FLAG='Y'" ;
				 
					 schDirPersno = querySingleValue(select2,"HEAD_OF_SCHOOL");
			}
	
		String deanPersNo=querySingleValue(select,"DEAN");
		String deputydean1PersNo=querySingleValue(select,"DEPUTY_DEAN1");
		String deputydean2PersNo=querySingleValue(select,"DEPUTY_DEAN2");
		String deputydean3PersNo=querySingleValue(select,"DEPUTY_DEAN3");
		String deputydean4PersNo=querySingleValue(select,"DEPUTY_DEAN4");
	  
		if(!(schDirPersno.length()==0)){
			String sql= "select initials,surname,novell_user_id from staff "+
			            "where persno="+schDirPersno;		
			try{ 			
	     		 deanform.setSchDirSurname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
				 deanform.setSchDirNetworkId(querySingleValue(sql,"novell_user_id"));			 
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
			}
			}		
		if(!(deanPersNo.length()==0)){
		String sql= "select initials,surname,novell_user_id from staff "+
		            "where persno="+deanPersNo;		
		try{ 			
     		 deanform.setDeanSurname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
			 deanform.setDeanNetworkId(querySingleValue(sql,"novell_user_id"));			 
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
		}
		}
		if(!(deputydean1PersNo.length()==0)){
			String sql= "select initials,surname,novell_user_id from staff "+
			            "where persno="+deputydean1PersNo;	
			try{ 
				 deanform.setDeputydean1Surname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
				 deanform.setDeputydean1NetworkId(querySingleValue(sql,"novell_user_id"));			 
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
			}
			}
		if(!(deputydean2PersNo.length()==0)){
			String sql= "select initials,surname,novell_user_id from staff "+
			            "where persno="+deputydean2PersNo;		
			try{ 
				 deanform.setDeputydean2Surname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
				 deanform.setDeputydean2NetworkId(querySingleValue(sql,"novell_user_id"));	 
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
			}
			}
		if(!(deputydean3PersNo.length()==0)){
			String sql= "select initials,surname,novell_user_id from staff "+
			            "where persno="+deputydean3PersNo;		
			try{
				 deanform.setDeputydean3Surname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
				 deanform.setDeputydean3NetworkId(querySingleValue(sql,"novell_user_id"));	 
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
			}
			}
		if(!(deputydean4PersNo.length()==0)){
			String sql= "select initials,surname,novell_user_id from staff "+
			            "where persno="+deputydean4PersNo;		
			try{ 			
				 deanform.setDeputydean4Surname(querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname"));
				 deanform.setDeputydean4NetworkId(querySingleValue(sql,"novell_user_id"));		 
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : selectDean Error occurred / "+ ex,ex);
			}
			}		
		
		return deanform;
	}
	
	public List getCodCourseList(String academicYear,String typeofbooklist,int status,String persno) throws Exception {
		ArrayList courseList = new ArrayList();
		String sql = "SELECT distinct s.CODE studyunit,p.BOOK_STATUS booktype FROM sun s,pbcrsn p,usrdpt ud,dpt dp WHERE "+  
		   " p.mk_academic_year=  "+academicYear+" and p.BOOK_STATUS=  '"+typeofbooklist+"' and " +
		   "p.STATUS= "+ status +"  and s.code=p.mk_study_unit_code and "+
		   "((dp.head_of_dept='"+persno+"' and dp.code=s.mk_department_code) " +
		   "or (ud.personnel_no='"+persno+"' and ud.mk_department_code=s.mk_department_code and type='DPT'))";		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);
			String value="";
			String label="";
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("studyunit").toString();
				label = data.get("booktype").toString();
				label=label+value;
			  courseList.add(new LabelValueBean(label,value));						
			}
			
		} catch (Exception ex) {
            throw new Exception("CourseDAO : getCodCourseList Error occurred / "+ ex,ex);
		}
		return courseList;
	}

	public String verifyModulefordean(String academicYear,String typeofbooklist,int status,String persno,String course) throws Exception {
		ArrayList courseList = new ArrayList();
		String module;
		//check course is valid for Dean
		String sql="SELECT p.mk_study_unit_code  studyunit FROM pbcrsn p,colleg c,sun s, dpt d "+
	           "where d.college_code=c.code and s.code=p.mk_study_unit_code and p.mk_academic_year="+academicYear+
               " and p.BOOK_STATUS= '"+typeofbooklist+"' and d.code=s.mk_department_code  and d.IN_USE_FLAG='Y' "+
	           "and  s.college_code=c.code and p.STATUS="+status+" and p.mk_study_unit_code ='"+course+"'"+
	            "and c.code=(select code from colleg  where dean='"+persno+"')";
		//check course is valid for deputy dean
		String sql1= "select distinct p.mk_study_unit_code  studyunit   from pbcrsn p, sun s ,usrdpt u where " +
				"s.code = p.mk_study_unit_code and p.mk_academic_year = "+academicYear+" and p.status = "+status+" " +
				"and u.college_code = s.college_code " +
				" and u.personnel_no = "+persno+" and u.type = 'COLLEGE'" +
				" and p.BOOK_STATUS= '"+typeofbooklist+"' and p.mk_study_unit_code ='"+course+"'";
		String courseValid = querySingleValue(sql,"studyunit");		
		String courseValid1 = querySingleValue(sql1,"studyunit");		
		if (courseValid.length()> 0) {
			module=courseValid;
		}else if(courseValid1.length()> 0){
			module=courseValid1;
		}else{
			module="";
		}
		return module;		
	}
	public String verifyModuleSubmitted(String academicYear,String typeofbooklist,int status,String persno,String course) throws Exception {
		ArrayList courseList = new ArrayList();
		String module = "";
		String sql1=" SELECT p.mk_study_unit_code  studyunit FROM school sc,pbcrsn p,dpt dp,sun s "+
            "where SC.College_code = DP.COLLEGE_CODE and sc.code=dp.SCHOOL_CODE and sc.HEAD_OF_SCHOOL= '"+persno+"' "+
           "and s.code=p.mk_study_unit_code and p.mk_academic_year="+academicYear+" and p.BOOK_STATUS=  '"+typeofbooklist+"'  and "+
           " dp.code=s.mk_department_code and p.STATUS="+ status +"  and sc.IN_USE_FLAG='Y' and p.mk_study_unit_code ='"+course+"'";
		
		String sql2 = "select distinct p.mk_study_unit_code  studyunit   from pbcrsn p, sun s ,usrdpt u where " +
				"s.code = p.mk_study_unit_code and p.mk_academic_year = "+academicYear+" and p.status = "+ status +
				" and u.college_code = s.college_code and " +
				"u.school_code = s.school_code and u.personnel_no = "+persno+
				" and   p.BOOK_STATUS= '"+typeofbooklist+"' " +
				"and p.mk_study_unit_code ='"+course+"' and u.type = 'SCHOOL'"; 
		String courseValid = querySingleValue(sql1,"studyunit");
		String courseValid1 = querySingleValue(sql2,"studyunit");
		if (courseValid.length()> 0) {
			module=courseValid;
		}else if(courseValid1.length()> 0){
			module=courseValid1;
		}				
		return module;		
	}
	
	public List getSChDirCourseList(String academicYear,String typeofbooklist,int status,String persno) throws Exception {
		ArrayList courseList = new ArrayList();
		
		//course list for school director
		String sql1 = "SELECT distinct s.CODE studyunit,p.BOOK_STATUS booktype FROM school sc,pbcrsn p,dpt dp,sun s "+
		              " where SC.College_code = DP.COLLEGE_CODE and sc.code=dp.SCHOOL_CODE and sc.HEAD_OF_SCHOOL= '"+persno+"' and s.code=p.mk_study_unit_code "+
		              " and p.mk_academic_year=  "+academicYear+"  and p.BOOK_STATUS= '"+typeofbooklist+"'  and " +
		              		" dp.code=s.mk_department_code and p.STATUS=  "+ status +" and sc.IN_USE_FLAG='Y'";
		//stand in school director course list
		String sql2 = "SELECT DISTINCT S.CODE studyunit, P.BOOK_STATUS booktype FROM USRDPT U, SUN S ,PBCRSN P WHERE  Type = 'SCHOOL' " +
		"AND S.CODE= P.MK_STUDY_UNIT_CODE and u.school_code = s.school_code AND P.MK_ACADEMIC_YEAR = "+academicYear+ 
		" AND p.BOOK_STATUS = '"+typeofbooklist+"' AND P.STATUS="+status+" and S.college_code = U.college_code " +
		"and U.Personnel_no =  '"+persno+"'";
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList1 = jdt.queryForList(sql1);
			List queryList2 = jdt.queryForList(sql2);
			//combine two lists 
			List<?> queryList = ListUtils.union(queryList1, queryList2);
			String value="";
			String label="";
			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("studyunit").toString();
				label = data.get("booktype").toString();
				label=label+value;
			  courseList.add(new LabelValueBean(label,value));						
			}
			
		} catch (Exception ex) {
            throw new Exception("CourseDAO : getSChDirCourseList Error occurred / "+ ex,ex);
		}
		return courseList;
	}
	public ArrayList getStandInSchoolDirList(String academicYear,String typeofbooklist,int status,String persno) throws Exception {
		ArrayList courseList = new ArrayList();
		/*String sql="SELECT DISTINCT S.CODE STUDYUNIT, P.BOOK_STATUS BOOKTYPE FROM COLLEG A, DPT B,SUN S ,PBCRSN P "+
		           "WHERE (DEPUTY_DEAN1 = '"+persno+"' OR DEPUTY_DEAN2 = '"+persno+"' OR DEPUTY_DEAN3 ='"+persno+"' OR DEPUTY_DEAN4 = '"+persno+"') "+
		           "AND A.CODE = B.COLLEGE_CODE AND S.CODE= P.MK_STUDY_UNIT_CODE AND S.CODE= P.MK_STUDY_UNIT_CODE "+
		           "AND P.MK_ACADEMIC_YEAR = "+academicYear+" AND p.BOOK_STATUS= '"+typeofbooklist+"' AND B.CODE=S.MK_DEPARTMENT_CODE AND P.STATUS="+ status;*/
			
			String sql = "SELECT DISTINCT S.CODE STUDYUNIT, P.BOOK_STATUS BOOKTYPE FROM USRDPT U, SUN S ,PBCRSN P WHERE  Type = 'SCHOOL' " +
					"AND S.CODE= P.MK_STUDY_UNIT_CODE and u.school_code = s.school_code AND P.MK_ACADEMIC_YEAR = "+academicYear+ 
					" AND p.BOOK_STATUS = '"+typeofbooklist+"'" + 
					" AND P.STATUS="+status+" and S.college_code = U.college_code and U.Personnel_no =  '"+persno+"'";
		try{
		 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);		  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
			value = data.get("studyunit").toString();
			label = data.get("booktype").toString();	
			label=label+value;
		  courseList.add(new LabelValueBean(label,value));						
		}
		
	} catch (Exception ex) {
      throw new Exception("CourseDAO : getCodCourseList Error occurred / "+ ex,ex);
	}
	return courseList;
	}
	
	public ArrayList getDeanCourseList(String academicYear,String typeofbooklist,int status,String persno,String department) throws Exception {
		ArrayList courseList = new ArrayList();
	/*	String sql="SELECT distinct s.CODE studyunit ,p.BOOK_STATUS booktype  FROM sun s,pbcrsn p,dpt dp,colleg a WHERE "+
                   " p.mk_academic_year=  "+academicYear+" and p.BOOK_STATUS=  '"+typeofbooklist+"' and p.STATUS= "+ status +" and s.code=p.mk_study_unit_code and "+
                   "(a.DEAN='"+persno+"' or a.DEPUTY_DEAN1='"+persno+"'  or a.DEPUTY_DEAN2='"+persno+"'  or a.DEPUTY_DEAN3='"+persno+"'  or a.DEPUTY_DEAN4='"+persno+"' )" +
                   " and dp.code=s.MK_DEPARTMENT_CODE and dp.code='"+department+"'";	*/
		
		String sql="select distinct s.code studyunit, p.book_status booktype from sun s, pbcrsn p , dpt dp, " +
				"colleg a, usrdpt ud where p.mk_academic_year=  "+academicYear+" and p.BOOK_STATUS= '"+typeofbooklist+"' and p.STATUS= 4 and " +
				"s.code = p.mk_study_unit_code and ((a.dean = '"+persno+"' and dp.code=s.mk_department_code) or " +
				" (ud.personnel_no = '"+persno+"'  and type='COLLEGE' and ud.college_code = s.college_code and dp.college_code = ud.college_code)) and  dp.code= '"+department+"'";	
		
		try{
		 JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList = jdt.queryForList(sql);		  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
			value = data.get("studyunit").toString();
			label = data.get("booktype").toString();
			label=label+value;
		  courseList.add(new LabelValueBean(label,value));						
		}
		
	} catch (Exception ex) {
      throw new Exception("CourseDAO : getCodCourseList Error occurred / "+ ex,ex);
	}
	return courseList;
	}
				
	

	public String getDeanCollege(String personnelNo) throws Exception{
		String college="";
		String checkdean="";
	/*	String sql ="select ENG_DESCRIPTION from colleg where (dean = " + personnelNo +
			" or deputy_dean1 = " + personnelNo +
			" or deputy_dean2 = " + personnelNo +
			" or deputy_dean3 = " + personnelNo +
			" or deputy_dean4 = " + personnelNo+")";*/
		
		String sql = "select ENG_DESCRIPTION from colleg where dean ="+personnelNo;
		try{ 			
			college = querySingleValue(sql,"ENG_DESCRIPTION");
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getDeanCollege Error occurred / "+ ex,ex);
		}
		//check deputy dean
		if(college.length()==0){
			String sql2 = "select ENG_DEScription from  colleg, usrdpt where usrdpt.type='COLLEGE' " +
					      "and usrdpt.PERSONNEL_NO = "+personnelNo+" and usrdpt.college_code =  colleg.code";
			try{ 			
				college = querySingleValue(sql2,"ENG_DESCRIPTION");
			}catch (Exception ex) {
				 throw new Exception("AuthorisationDAO : getDeanCollege Error occurred / "+ ex,ex);
			}
		}
		return college;	
	}

	public List getDeanDeptList(Integer personnelNo,String acadyear)  throws Exception {
		ArrayList dptList = new ArrayList();
		
		String sql1 ="select distinct b.code, b.eng_description from colleg a, dpt b,SUN S ,PBCRSN P where a.dean = " + personnelNo +
		" and a.code = b.college_code and S.CODE=P.MK_STUDY_UNIT_CODE and S.CODE=P.MK_STUDY_UNIT_CODE AND P.MK_ACADEMIC_YEAR = "+acadyear+
		" and b.code=s.MK_DEPARTMENT_CODE and P.STATUS=4 ";
		
		String sql2 ="select distinct d.code, d.eng_description  from pbcrsn p, sun s ,dpt d , usrdpt u where " +
				"s.code = p.mk_study_unit_code and p.mk_academic_year = "+acadyear+" and p.status = 4" +
				" and d.college_code = s.college_code and u.college_code = s.college_code and u.type = 'COLLEGE' and " +
				"u.college_code = d.college_code  and u.personnel_no =  "+ personnelNo +" and d.code=s.MK_DEPARTMENT_CODE"; 
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList1 = jdt.queryForList(sql1);
			List queryList2 = jdt.queryForList(sql2);
			//combine two lists 
			List<?> queryList = ListUtils.union(queryList1, queryList2);
			String value="";
			String label="";

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				value = data.get("CODE").toString();
				label = data.get("ENG_DESCRIPTION").toString();
				dptList.add(new LabelValueBean(label,value));		
				}
		}
		catch (Exception ex) {
			throw new Exception("AuthorisationDAO : Error reading table colleg and dpt/ " + ex);
		}		
		return dptList;		
	}
	
	
	public String getDeptCode(String deptCode) throws Exception{
	      String deptName="";
	String sql2="select eng_description from dpt where code="+deptCode;
	try{ 
		deptName = querySingleValue(sql2,"eng_description");
	}catch (Exception ex) {
		 throw new Exception("AuthorisationDAO : getDeptCode Error occurred / "+ ex,ex);
	}
	return deptName;
	}
	
	
	public String getDeanNetworkId(String persno) throws Exception{
	      String networkId="";
	String sql2="select novell_user_id from staff where persno="+persno;
	try{ 
		networkId = querySingleValue(sql2,"novell_user_id");
	}catch (Exception ex) {
		 throw new Exception("AuthorisationDAO : getDeanNetworkId Error occurred / "+ ex,ex);
	}	
	if(networkId.length()==0){
		String sql3="select NOVELL_USER_CODE from usr where PERSONNEL_NO= '"+persno+"'";
		try{ 
			networkId = querySingleValue(sql3,"NOVELL_USER_CODE");
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getDeanNetworkId Error occurred / "+ ex);
		}	
	}
	return networkId;
	}
	public String getUserCode(String persno) throws Exception{
	      String userCode="";
	      String sql= "select initials,surname,novell_user_id from staff "+
          "where persno="+persno;		
    try{ 
    	userCode=querySingleValue(sql,"initials")+" "+querySingleValue(sql,"surname");	 
     }catch (Exception ex) {
	 throw new Exception("AuthorisationDAO : getUserCode Error occurred / "+ ex,ex);
        }
	if(userCode.length()==0){
		String sql3="select NAME from usr where PERSONNEL_NO= '"+persno+"'";
		try{ 
			userCode=querySingleValue(sql,"NAME");
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : getUserCode Error occurred / "+ ex);
		}	
	}
	return userCode;
	}
	
	//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorization
	//get book list of unauthorized tutor counts
    public List<String> getTutorBookList( String typeofbooklist, int tutorStatus ) throws Exception {
    	List<String> courseList = new ArrayList<String>();
    	
		String sqlStr = "SELECT pbcrsn.mk_study_unit_code, pbcrsn.mk_academic_year,"+
				" pbcrsn.book_status, pbcrsn.tutor_count,pbcrsn.status"+
				" FROM pbcrsn WHERE book_status = '"+typeofbooklist+"'"+
				" AND tutor_status="+tutorStatus+
				" AND tutor_count IS NOT NULL"+
				" AND pbcrsn.mk_study_unit_code NOT IN"+
				              " (SELECT DISTINCT (pbsaud.mk_study_unit_code)"+
				                 " FROM pbsaud WHERE pbsaud.mk_book_status = '"+typeofbooklist+"'"+
				                 " AND pbsaud.update_info LIKE '(Tutor count authorized by DIIS)%')";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			//courseList = jdt.queryForList(sql);
			courseList = jdt.queryForList( sqlStr );	//jdt.query( sqlStr, new BookRowMapper() );
		 } catch (Exception ex) {
	      throw new Exception("AuthorisationDAO : getTutorBookList Error occurred / "+ ex,ex);
		}	
		
		return courseList;
	}
	
	//Sifiso Changes: 2017/08/16:LU_MA_BLU_06:Requirement 54:Tutor Booklist: DISS Authorization
	//check if count of any Tutor counts to authorize exist
	public boolean tutorCountsExist( String typeofbooklist, int tutorStatus, int authStatus) throws Exception{
		boolean isCountExists = false;
		int tutorCount = 0;
		
		try{
			String sqlStr = " SELECT COUNT (pbcrsn.tutor_status) AS tutCounts"+
							" FROM pbcrsn WHERE pbcrsn.book_status='"+typeofbooklist+"'"+
							" AND pbcrsn.status >= "+authStatus+
							" AND pbcrsn.tutor_status="+tutorStatus+
							" AND pbcrsn.tutor_count IS NOT NULL"+
							" AND pbcrsn.mk_study_unit_code IN"+
							" (SELECT DISTINCT (pbsaud.mk_study_unit_code)"+
								" FROM pbsaud WHERE pbsaud.mk_book_status='"+typeofbooklist+"'"+
								" AND pbsaud.update_info LIKE 'Tutor Count%')";
			
			tutorCount=Integer.parseInt( querySingleValue(sqlStr,"tutCounts") );
			if ( tutorCount > 0 )
				isCountExists = true;
			
		}catch (Exception ex) {
			 throw new Exception("AuthorisationDAO : tutorCountsExist Error occurred / "+ ex);
		}
		
		return isCountExists;
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