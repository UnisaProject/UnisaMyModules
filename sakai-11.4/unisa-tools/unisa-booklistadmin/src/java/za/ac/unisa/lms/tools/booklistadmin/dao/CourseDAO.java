package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.PreparedStatementCreator;
import za.ac.unisa.lms.tools.booklistadmin.module.BookModule;
public class CourseDAO extends Book_CourseLinkDAO{
	
	

	public ArrayList getCourseListpercollege(String code,String year,String status,String typeofbooklist ) throws Exception {
		ArrayList courseList = new ArrayList();
		int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
		String sql=" select  distinct s.CODE studyunit,p.BOOK_STATUS booktype"+
                   " from colleg c,pbcrsn p,sun s where c.code= '"+code+"' and p.mk_academic_year= '"+year+"' "+
                   " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") "+
                   " and  s.code=p.mk_study_unit_code and p.status= '"+status+"' and p.BOOK_STATUS='"+typeofbooklist+"' and s.COLLEGE_CODE=c.code order by studyunit";			
		try{
			DatabaseUtil databaseUtil=new  DatabaseUtil();
		  List queryList = databaseUtil.queryForList(sql);		  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
		 	value = data.get("studyunit").toString();
			label = data.get("booktype").toString();			
		  courseList.add(new LabelValueBean(label,value));						
		}		
	} catch (Exception ex) {
      throw new Exception("AdminDAO : getCourseListpercollege Error occurred / "+ ex,ex);
	}	
	return courseList;
	}
    public String getCourselistCount(String code,String year,String status,String typeofbooklist ) throws Exception {
    	String count;
    	int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
    	String sql =   "select count(*) count "+
    	  " from colleg c,pbcrsn p,sun s where c.code= '"+code+"' and p.mk_academic_year= '"+year+"' "+
          " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") "+
          " and  s.code=p.mk_study_unit_code and p.status= '"+status+"' and p.BOOK_STATUS='"+typeofbooklist+"' and s.COLLEGE_CODE=c.code";
				try{
					   DatabaseUtil databaseUtil=new  DatabaseUtil();
					   count = databaseUtil.querySingleValue(sql,"count");
					   if(count.length()==0){
						    count=" ";
					   }					
				} catch (Exception ex) {
				    throw new Exception("AdminDAO : getNoActivityCount Error occurred / "+ ex,ex);
				}
	return count;
    }    
    public ArrayList getNoActionCourseList(String code,String year,String typeofbooklist) throws Exception {
		ArrayList courseList = new ArrayList();
		int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
			String sql="select distinct c.ENG_DESCRIPTION college, s.CODE studyunit from colleg c,school su, dpt d, sun s "+
		            "where  su.college_code = c.code and s.in_use_flag = 'Y' and s.ACADEMIC_LEVEL <> 'D' and s.research_flag = 'N' and "+
		            " s.college_flag <> 'Y' and s.COLLEGE_CODE=c.code and s.school_code = su.code and c.code="+code+
	                " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") and d.code = " +
	                "s.MK_DEPARTMENT_CODE and not exists(select * from pbcrsn p where p.mk_academic_year= "+year+" and "+
		            "p.BOOK_STATUS=  '"+typeofbooklist+"' and s.code=p.mk_study_unit_code) group by c.ENG_DESCRIPTION, su.ENG_DESCRIPTION, d.ENG_DESCRIPTION, s.CODE "+
		            " order by  s.CODE";
	
		try{
			DatabaseUtil databaseUtil=new  DatabaseUtil();
			  List queryList = databaseUtil.queryForList(sql);
			  String value="";
		      String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
		 	value = data.get("studyunit").toString();		 
			label = data.get("college").toString();			
		  courseList.add(new LabelValueBean(label,value));						
		}		
	} catch (Exception ex) {
      throw new Exception("AdminDAO : getNoActionCourseList Error occurred / "+ ex,ex);
	}	
	return courseList;
	}
    public String getCourselistCount(String code,String year,String typeofbooklist) throws Exception {
    	String count;
    	int tmpYear = Integer.parseInt(year);
		int acadyearLess1= Integer.parseInt(year)-1; 
		int acadyeargreater1= Integer.parseInt(year)+1; 
		String sql="select  count(*) count from colleg c,school su, dpt d, sun s "+
        "where  su.college_code = c.code and s.in_use_flag = 'Y' and s.ACADEMIC_LEVEL <> 'D' and s.research_flag = 'N' and "+
        " s.college_flag <> 'Y' and s.COLLEGE_CODE=c.code and s.school_code = su.code and c.code="+code+
        " and (s.from_year < 1 or s.FROM_YEAR < "+acadyeargreater1+") and (s.to_year < 1 or s.to_year > "+acadyearLess1+") and d.code = " +
        "s.MK_DEPARTMENT_CODE and not exists(select * from pbcrsn p where p.mk_academic_year= "+year+" and "+
        "p.BOOK_STATUS=  '"+typeofbooklist+"' and s.code=p.mk_study_unit_code)";		
				try{
					   DatabaseUtil databaseUtil=new  DatabaseUtil();
					   count = databaseUtil.querySingleValue(sql,"count");
					   if(count.length()==0){
						     count=" ";
					   }					
				} catch (Exception ex) {
				    throw new Exception("AdminDAO : countNoActivityList Errorred / "+ ex,ex);
				}
	return count;
    }
    public String getCourseNote (String subject,String acadyear, String typeofBooklist) throws Exception {
    	DatabaseUtil databaseUtil=new  DatabaseUtil();
				String courseNote = null;			
		String sql = "SELECT nvl(crs_notes,'') FROM pbcrsn where Mk_study_unit_code = '"+subject+"' and mk_academic_year= "+acadyear+" and BOOK_STATUS = '"+typeofBooklist+"' ";
		List courseNoteList = databaseUtil.queryForList(sql);
		if (courseNoteList.size() > 0) {
			courseNote = (String)databaseUtil.query(sql, String.class);
		}
				
		return courseNote;
	}
	public String courseValid(String subject) throws Exception {
		DatabaseUtil databaseUtil=new  DatabaseUtil();
			String course;
		String sql = "select CODE from sun where IN_USE_FLAG='Y' and code = '"+subject+"'";
		String code = databaseUtil.querySingleValue(sql,"CODE");
		if (code.length()> 0) {
			course=code;
		}else{
			course="";
		}				
		return course;
	}
public void saveCourseNote (String studyUnit,String acadYear,String courseNote,String status, String typeofBooklist) throws Exception {

		
		//Check if course note exists for current academic year.
		String sql = "SELECT crs_notes FROM pbcrsn where Mk_study_unit_code = '"+studyUnit+"' and mk_academic_year= '"+acadYear+"' and BOOK_STATUS = '"+typeofBooklist+"' ";
		DatabaseUtil databaseUtil=new  DatabaseUtil();
		List courseNoteList = databaseUtil.queryForList(sql);
		
		if (courseNoteList.size() > 0){
			String updateSql = "UPDATE pbcrsn SET crs_notes = '"+courseNote+"', status='"+status+"' WHERE " +
					"mk_study_unit_code = '"+studyUnit+"' and mk_academic_year= '"+acadYear+"' and book_status = '"+typeofBooklist+"'";
			databaseUtil.execute(updateSql);

		}
		else if (courseNoteList.size() == 0){
			
			String insertSql = "insert into PBCRSN (Mk_Academic_Year,Mk_Study_Unit_Code,Crs_Notes,status,book_status)"+
				                "values( '"+acadYear+"' , '"+studyUnit+"' ,'"+courseNote+"' , "+status+" , '"+typeofBooklist+"' )";
			databaseUtil.execute(insertSql);
						
		}			
	}
    public void linkBookToCourse(BookModule bookDetails, String courseId, String acadYear){
		PreparedStatementCreator stmt2 = pstsmtCreatorFactoryCourseLink.newPreparedStatementCreator(
			new Object[]{courseId,bookDetails.getBookId(),bookDetails.getCourseLang(),bookDetails.getTypeOfBookList(),acadYear}
			);
		DatabaseUtil databaseUtil=new  DatabaseUtil();
		databaseUtil.update(stmt2);	
  
        String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+courseId+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"' and MK_ACADEMIC_YEAR = "+acadYear;
	String course;
	
		course = databaseUtil.querySingleValue(sqlcourse,"mk_study_unit_code");
	    if(course.length()==0){
	     insertCourseBookLink(Integer.parseInt(acadYear),courseId,"",7,bookDetails.getTypeOfBookList());
	 }else{
	        updateCourseBookLink(7,course,Integer.parseInt(acadYear),bookDetails.getTypeOfBookList());
	 }
	
   }
   public void declareNoBooks(int year,String courseId,String bookListType){
	                String sql="update pbcrsn  set status=3  where  MK_ACADEMIC_YEAR="+year+
			               "  and mk_study_unit_code ='"+courseId+"' and BOOK_STATUS='"+bookListType+"'";
	                DatabaseUtil databaseUtil=new  DatabaseUtil();
	                databaseUtil.update(sql);
   }
	


}
