package za.ac.unisa.lms.tools.booklist.dao;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.booklist.dao.College;
import za.ac.unisa.lms.tools.booklist.dao.BooklistDetails;




public class BooklistDAO extends StudentSystemDAO {
    int start;
    private Map collegeMap;
    private Map collegeMap1;
    
    public BooklistDAO()
    { 
    	collegeMap = new HashMap<String,String>();
   	   collegeMap1=Collections.synchronizedMap(new TreeMap());}
    
	public int getStart() {
		return start;
	}

	public Map getCollegesMap()
	 {
	 			  String sql=
	 				" select code,eng_description COLLEGE from colleg "+
	 				" where dean > 0 "+
	 				" order by eng_description ";
	 			  
	 			  Map results = new HashMap();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails nositeDetails = new BooklistDetails();
					String college= data.get("COLLEGE").toString();
					String code   = data.get("CODE").toString();					
					results.put(code, college);
					//results.add(new org.apache.struts.util.LabelValueBean(college,code));
					//collegeMap.put(code, college);					
				  }
		return results;
	}
	
	
	//Get all colleges i.e. all the college names in the colleg table
	
	
	public ArrayList getColleges()
    {
                       String sql=
                           " select code,eng_description COLLEGE from colleg "+
                           " where dean > 0 "+
                           " order by eng_description ";
                       ArrayList results = new ArrayList();
                       JdbcTemplate jdt = new JdbcTemplate(getDataSource());
                       List queryList;
                       queryList = jdt.queryForList(sql);
                       Iterator i = queryList .iterator();
                       while(i.hasNext()) {
                           ListOrderedMap data = (ListOrderedMap) i.next();
                           BooklistDetails nositeDetails = new BooklistDetails();
                           String college= data.get("COLLEGE").toString();
                           String code   = data.get("CODE").toString();                            
                           results.add(new org.apache.struts.util.LabelValueBean(college,code));
                           //collegeMap.put(code, college);                            
                       }
         return results;
   }

	public Map getColleges2()
	 {
	 			  String sql=
	 				" select code,eng_description COLLEGE from colleg "+
	 				" where dean > 0 "+
	 				" order by eng_description ";
	 			  Map results = Collections.synchronizedMap(new TreeMap());
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails booksDetails = new BooklistDetails();
					String college= data.get("COLLEGE").toString();
					String code   = data.get("CODE").toString();					
					results.put(code,college);
					collegeMap1.put(college,code);
					//collegeMap.put(code, college);					
				  }
		return results;
	}
	//gets the total number of books for booklistopen
	public Vector getBooklistOPen(String yr){

		/*String sql = 
		" select l.eng_description college,count(distinct c.coursenr) total "+
		" from sun s, crsbs1 c, colleg l "+	
		" where s.code=c.coursenr(+) "+
		" and c.confirm(+)= 0"+
		" and year(+) = " +yr+			
		" and l.code in (1,2,3,4,5) " +
		" and college_code =l.code "+
		" group by l.eng_description "+		
		" order by l.eng_description asc ";*/
		
		String sql = " select  c.eng_description college,count(distinct p.MK_STUDY_UNIT_CODE) total"+  
		             " from colleg c,pbcrsn p,sun s where"+ 
		             " c.dean > 0 " +
		             " and p.mk_academic_year(+)="+yr+	  
		             " and p.BOOK_STATUS(+)= 'P' and s.code=p.mk_study_unit_code(+) " +
		             " and p.status(+)= 0" +
		             " and s.COLLEGE_CODE=c.code"+
		             " group by c.eng_description  " +
		             " order by c.eng_description asc";

		
     SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
     Vector results = new Vector();
	  String college= new String();
	 String total= new String();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList .iterator();
	  
	  while (i.hasNext())
	  {ListOrderedMap data = (ListOrderedMap) i.next();	
	  BooklistDetails booksDetails = new BooklistDetails();
		booksDetails.setCollege1(data.get("COLLEGE").toString());
	    booksDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(booksDetails);
	  	   
	  }	
	  
return results;
}
	public Vector getBooklistSubmitted(String yr){

		String sql = " select  c.eng_description college,count(distinct p.MK_STUDY_UNIT_CODE) total"+  
                     " from colleg c,pbcrsn p,sun s where"+ 
                     " c.dean > 0  " +
                     " and p.mk_academic_year(+)="+yr+	  
                     " and p.BOOK_STATUS(+)= 'P' and s.code=p.mk_study_unit_code(+) " +
                     " and p.status(+)= 1" +
                     " and s.COLLEGE_CODE=c.code"+
                     " group by c.eng_description  " +
                     " order by c.eng_description asc";	
     SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
     Vector results = new Vector();
	  String college= new String();
	 String total= new String();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList .iterator();
	  
	  while (i.hasNext())
	  {ListOrderedMap data = (ListOrderedMap) i.next();	
	  BooklistDetails booksDetails = new BooklistDetails();
		booksDetails.setCollege1(data.get("COLLEGE").toString());
	    booksDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(booksDetails);
	  	   
	  }	
	  
return results;
}
	public Vector getBooklistAuthorized(String yr){

		String sql = " select  c.eng_description college,count(distinct p.MK_STUDY_UNIT_CODE) total"+  
                     " from colleg c,pbcrsn p,sun s where"+ 
                     " c.dean > 0  " +
                     " and p.mk_academic_year(+)="+yr+	  
                     " and p.BOOK_STATUS(+)= 'P' and s.code=p.mk_study_unit_code(+) " +
                     " and p.status(+)= 5" +
                     " and s.COLLEGE_CODE=c.code"+
                     " group by c.eng_description  " +
                     " order by c.eng_description asc";
     SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
     Vector results = new Vector();
	  String college= new String();
	 String total= new String();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList .iterator();
	  
	  while (i.hasNext())
	  {ListOrderedMap data = (ListOrderedMap) i.next();	
	  BooklistDetails booksDetails = new BooklistDetails();
		booksDetails.setCollege1(data.get("COLLEGE").toString());
	    booksDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(booksDetails);
	  	   
	  }	
	  
return results;
}
	public Vector getNoBooklist(String yr){

		String sql = " select  c.eng_description college,count(distinct p.MK_STUDY_UNIT_CODE) total"+  
                     " from colleg c,pbcrsn p,sun s where"+ 
                     " c.dean > 0  " +
                     " and p.mk_academic_year(+)="+yr+	  
                     " and p.BOOK_STATUS(+)= 'P' and s.code=p.mk_study_unit_code(+) " +
                     " and p.status(+)= 6" +
                     " and s.COLLEGE_CODE=c.code"+
                     " group by c.eng_description  " +
                     " order by c.eng_description asc";
     SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
     Vector results = new Vector();
	  String college= new String();
	 String total= new String();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList .iterator();
	  
	  while (i.hasNext())
	  {ListOrderedMap data = (ListOrderedMap) i.next();	
	  BooklistDetails booksDetails = new BooklistDetails();
		booksDetails.setCollege1(data.get("COLLEGE").toString());
	    booksDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(booksDetails);
	  	   
	  }	
	  
return results;
}
	/**Associate college names to their corresponding sites
	 *
	 * @param active
	 * @param inactive
	 * @param inactivewithstudents
	 * @return college details
	 */
public List getCollegeinfo(List open,List submitted,List authorized,List nobooks,String year)
		{
		List a = open;
		Vector  c = new Vector();
		BooklistDetails booksDetails;
		StatusCollege stat;
		Iterator i = a.iterator();
		SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
				while(i.hasNext())
				{   booksDetails =  (BooklistDetails) i.next();
				stat = new StatusCollege();
				stat.setCollege(booksDetails.getCollege1().toString());
				stat.setBooklistOpen(Integer.parseInt(booksDetails.getTotal()));
				stat.setBooklistSubmitted(getBooklistSubmitted(booksDetails.getCollege1(),submitted));
				stat.setBooklistAuthorized(getBooklistAuthorized(booksDetails.getCollege1(),authorized));
				stat.setNobooklist(getNoBooks(booksDetails.getCollege1(),nobooks));
	            //String college =(String)collegeMap.get(booksDetails.getCollege1());
	           // stat.setBooklistAuthorized(getBooklistAuthorized(college,year));
	               // col.setInactivewithstudents(getInctivesitesWithStudents(college,year));
	               // int tests=Integer.parseInt((collegTotalMap.get(sites.getCollege_code())).toString());
	               // col.setInactivewithstudents(tests);
	               // System.out.println("What is this now :"+tests);

	               // col.setInactivewithstudents(getInactiveWithStudents(college,inactivewithstudents));

	                c.add(stat);
	               }
				return c;
		}
		
		
  public int getBooklistSubmitted(String college,List submitted)
		 {
	int inact=0;
		 BooklistDetails booksDetails;
		  Iterator i = submitted.iterator();
			while(i.hasNext())
			{   booksDetails =  (BooklistDetails) i.next();
			    if (college.equals(booksDetails.getCollege1()))
			      {college=booksDetails.getCollege1();
			       inact=Integer.parseInt(booksDetails.getTotal());
			  
			      }
		    }
		 return inact;
		 }	
	
   public int getBooklistAuthorized(String college,List authorized)
       {
int inact=0;
BooklistDetails booksDetails;
 Iterator i = authorized.iterator();
	while(i.hasNext())
	{   booksDetails =  (BooklistDetails) i.next();
	    if (college.equals(booksDetails.getCollege1()))
	      {college=booksDetails.getCollege1();
	       inact=Integer.parseInt(booksDetails.getTotal());
	  
	      }
   }
return inact;
}

public int getNoBooks(String college,List nobooks)
{
int inact=0;
BooklistDetails booksDetails;
 Iterator i = nobooks.iterator();
	while(i.hasNext())
	{   booksDetails =  (BooklistDetails) i.next();
	    if (college.equals(booksDetails.getCollege1()))
	      {college=booksDetails.getCollege1();
	       inact=Integer.parseInt(booksDetails.getTotal());
	  
	      }
   }
return inact;
}
public List sortCollegedisplay(Set c,List l)
{List temp =new Vector();
 Iterator i =c.iterator();
 while(i.hasNext()){
	  String college =i.next().toString();
	  Iterator j=l.iterator();
	  while (j.hasNext())
	  {StatusCollege anothercollege =(StatusCollege) j.next();
	   String college2 =anothercollege.getCollege();
	   if (college.equals(college2))
	     {temp.add(anothercollege);
	     break;}
	  }
 }
 return temp;
}

	/** Get all the Department names for the institution under a given college code
	 *
	 * @param d (department name)
	 * @return
	 */
	public ArrayList getDepts(String code)
	 {
		String sql =" SELECT eng_description department,code from dpt " +
				    " where college_code = "+code+
				    " and in_use_flag ='Y' "+
				    " order by eng_description ";
		 
		 ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails nositeDetails = new BooklistDetails();
					String dept= data.get("DEPARTMENT").toString();
					String deptcode   = data.get("CODE").toString();					
				    results.add(new org.apache.struts.util.LabelValueBean(dept,deptcode));
				  }
		return results;
	 }
	
	/**
	 * Get a map of departments to their codes
	 * 
	 * @param code
	 * @return
	 */
	public Map getDeptsMap(String code,String sch)
	 {
		String sql =" SELECT eng_description department,code from dpt " +
				    " where college_code = "+code+
				    " and in_use_flag ='Y' "+
				    " and school_code = "+sch+
				    " order by eng_description ";
		 
		 HashMap results = new HashMap();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails nositeDetails = new BooklistDetails();
					String dept= data.get("DEPARTMENT").toString();
					String deptcode   = data.get("CODE").toString();
					results.put(deptcode, dept);
				   // results.add(new org.apache.struts.util.LabelValueBean(dept,collegecode));
				  }
		return results;
	 }
	/**
	 * Get an Arraylist of departments and their corresponding codes
	 * 
	 * @param code
	 * @param sch
	 * @return
	 */
	public ArrayList getDepts(String code,String sch)
	 {
		String sql =" SELECT eng_description department,code from dpt " +
				    " where college_code = "+code+
				    " and in_use_flag ='Y' "+
				    " and school_code = "+sch+
				    " order by eng_description ";
		 
		ArrayList results = new ArrayList();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails nositeDetails = new BooklistDetails();
					String dept= data.get("DEPARTMENT").toString();
					String deptcode   = data.get("CODE").toString();
				    results.add(new org.apache.struts.util.LabelValueBean(dept,deptcode));
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
					BooklistDetails nositeDetails = new BooklistDetails();
					String sch= data.get("SCHOOL").toString();
					String schcode   = data.get("CODE").toString();					
				    results.add(new org.apache.struts.util.LabelValueBean(sch,schcode));
				  }
		return results;
	 }
	/**
	 * Get a mapping of schools to their corresponding codes
	 * @param code
	 * @return
	 */
	public Map getSchoolsMap(String code)
	 {
		String sql =
					" select CODE, eng_description SCHOOL "+ 
					" from school "+
					" where college_code = "+code+
					" and in_use_flag ='Y' "+
					" order by school "; 
		
		 HashMap results = new HashMap();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					BooklistDetails nositeDetails = new BooklistDetails();
					String sch= data.get("SCHOOL").toString();
					String schcode   = data.get("CODE").toString();
					results.put(schcode, sch);				   
				  }
		return results;
	 }	

	
	/**
	 * Map - map college names to total number of prescribed books per college
	 * 
	 * 
	 */
	
	public Map getCollegeTotals(int yr){

			String sql = 
			" select l.eng_description college,count(distinct c.coursenr) total "+
			" from sun s, crsbs1 c, colleg l "+	
			" where s.code=c.coursenr(+) "+
			" and c.confirm(+)= 2"+
			" and year(+) = " +yr+			
			" and l.dean > 0  " +
			" and college_code =l.code "+
			" group by l.eng_description "+		
			" order by l.eng_description asc ";	
		Map result = Collections.synchronizedMap(new TreeMap());
		  String college= new String();
		 String total= new String();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  
		  while (i.hasNext())
		  {ListOrderedMap data = (ListOrderedMap) i.next();	
	
		    college=data.get("college").toString();
		    total =data.get("total").toString();
		   result.put(college,total);
		  	   
		  }	
		  
return result;
	}
	public String getStatusTotals(int yr,int j){
		List result= new Vector();
		String sql = 
		" select l.eng_description college,count(distinct c.coursenr) total "+
		" from sun s, crsbs1 c, colleg l "+	
		" where s.code=c.coursenr(+) "+
		" and c.confirm(+)= "+j+
		" and year(+) = " +yr+			
		" and c.dean > 0  " +
		" and college_code =l.code "+
		" group by l.eng_description "+		
		" order by l.eng_description asc ";	
		
	  String total = new String();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList .iterator();
	  
	  while (i.hasNext())
	  {ListOrderedMap data = (ListOrderedMap) i.next();	
	    total =data.get("total").toString();
	   		    
	  }	
return total;
}
	/** * 
	 * 
	 * 
	 * @param yr
	 * @param college
	 * @return List of confirmed prescribed books for a given year and college
	 */
	public ArrayList getPrescibedBooksStat(String statusOptions,String yr,int college, String booklistTypes){
		ArrayList results = new ArrayList();
		/*String sql =
			" SELECT c.COURSENR coursenr,Title,Author," +
			" DECODE (edition, null, ' ', edition) edition, "+				
			" DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+
			" DECODE (isbn, null, 'No ISBN', isbn)  isbn,"+
			" Course_Language,b.notes notes,Confirm,Is_Published "+
			" FROM books1 b, crsbs1 c, sun s "+
			" where b.booknr=c.booknr "+
			" and s.college_code= "+college+  			
			" and s.code=c.coursenr "+
			" and c.confirm= "+ statusOptions+ 			
			" and c.year= "+yr+
			//" ORDER BY coursenr  , Upper(Author), Edition asc ";	
			" ORDER BY coursenr, Title asc ";*/
		   int statusOption = Integer.parseInt(statusOptions);
		
		
	String sql =  " SELECT DISTINCT c.coursenr AS studyunit , b.title AS title,"+
                  " DECODE (author, null, ' ', author) author, "+    
                  " DECODE (edition, null, ' ', edition) edition, "+                
                  " DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish ,"+ 
                  " DECODE (isbn, null, 'No ISBN', isbn)  isbn,"+
                  " DECODE (EBOOK_VOLUME, null, ' ', EBOOK_VOLUME)  EBOOK_VOLUME," +
                  " DECODE (EBOOK_PAGES, null, ' ', EBOOK_PAGES)  EBOOK_PAGES,"+
                  " DECODE (PUBLISHER, null, ' ',PUBLISHER) Publication"+
                  " FROM crsbs1 c, books1 b, pbcrsn pc, sun s ,colleg co  "+
                  " WHERE c.YEAR ="+yr+  
                  " AND pc.book_status ="+"'"+booklistTypes+"'"+
                  " and pc.book_status = c.BOOK_STATUS "+
                  " AND c.booknr = b.booknr"+
                  " AND c.coursenr = pc.mk_study_unit_code AND c.coursenr = s.code "+
                  " AND pc.mk_study_unit_code = s.code  AND c.YEAR = pc.mk_academic_year "+
                  " AND pc.status = "+statusOptions+
                  " and co.code=s.college_code and co.code="+college+
                  " ORDER BY c.coursenr, b.title ";

		   
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
			
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
			BooklistDetails bookDetails = new BooklistDetails();
			
		try{
			bookDetails.setStudyUnit(data.get("studyunit").toString());
			bookDetails.setTitle(data.get("Title").toString());
			bookDetails.setAuthor(data.get("Author").toString());
			bookDetails.setEdition(data.get("edition").toString());
			bookDetails.setPublYear(data.get("Year_of_publish").toString());		
			bookDetails.setIsbn(data.get("isbn").toString());	
			bookDetails.setEbookvolume(data.get("EBOOK_VOLUME").toString());
			bookDetails.setEbookpages(data.get("EBOOK_PAGES").toString());
			bookDetails.setPublication(data.get("Publication").toString());
			results.add(bookDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}
		
	  }			  
 return results;
}
	/**
	 * 
	 * @param yr
	 * @param college
	 * @param dpt
	 * @return List of confirmed prescribed books for a given year,department and a college 
	 */
	
/*	public ArrayList getPrescibedBooks(String yr,String dept,int college ,String booklistTypes){
		ArrayList results = new ArrayList();
		String sql =
			" SELECT c.COURSENR coursenr,Title,Author," +
			" DECODE (edition, null, ' ', edition) edition, "+				
			" DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+
			" DECODE (isbn, null, ' ', isbn)  isbn,"+
			" Course_Language,b.notes notes,Confirm,Is_Published "+
			" FROM books1 b, crsbs1 c, pbcrsn p, sun s "+
			" where b.booknr=c.booknr "+
			" and s.college_code= "+college+  
			//" and s.school_code= "+sch+	
			" and s.mk_department_code= "+dept+
			" and s.code=c.coursenr "+
			
			" and c.year= "+yr+
			" ORDER BY coursenr, Title asc ";	  
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
			
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
			BooklistDetails bookDetails = new BooklistDetails();			
		try{
			bookDetails.setStudyUnit(data.get("coursenr").toString());
			bookDetails.setTitle(data.get("Title").toString());
			bookDetails.setAuthor(data.get("Author").toString());
			bookDetails.setEdition(data.get("edition").toString());
			bookDetails.setPublYear(data.get("Year_of_publish").toString());		
			bookDetails.setIsbn(data.get("isbn").toString());
			bookDetails.setCollege(college);
			results.add(bookDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}		
	  }			  
 return results;
	}*/
	/**
	 * 
	 * @param yr
	 * @param college
	 * @param sch
	 * @return List of confirmed prescribed books for a given year,college and a school
	 */
	
	public ArrayList getPrescibedBooks(String yr,int college,String sch, String dept, String booklistTypes, String statusOptions){
		ArrayList results = new ArrayList();
		String sql =
			 " SELECT DISTINCT c.coursenr AS course,Title ,"+
			 " DECODE (author, null, ' ', author) author, "+  
	         " DECODE (edition, null, ' ', edition) edition,"+ 				
			 " DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish ,"+ 
		     " DECODE (isbn, null, ' ', isbn)  isbn, "+
		     " DECODE (EBOOK_VOLUME, null, ' ', EBOOK_VOLUME)  EBOOK_VOLUME," +
             " DECODE (EBOOK_PAGES, null, ' ', EBOOK_PAGES)  EBOOK_PAGES,"+
             " DECODE (PUBLISHER, null, ' ',PUBLISHER) Publication"+
	         " FROM crsbs1 c, books1 b, pbcrsn pc, sun s ,colleg co ,school sc "+
	         " WHERE c.YEAR = "+yr+
	         " AND pc.book_status ="+"'"+booklistTypes+"'"+
	         " and pc.book_status = c.BOOK_STATUS   AND c.booknr = b.booknr"+
	         " AND c.coursenr = pc.mk_study_unit_code AND c.coursenr = s.code AND pc.mk_study_unit_code = s.code"+ 
	         " AND c.YEAR = pc.mk_academic_year AND pc.status ="+statusOptions+
	         " and co.code=s.college_code and co.code="+college+
	         " and sc.code=s.school_code ";
	         if(sch.equals("-1")||sch==" "){
	        	 
	         }else{
	        	 sql = sql+" and sc.code="+sch;
	         }
	         if(dept.equals("-1")||dept==""){
	        	 
	         }else{
	        	sql = sql+" and s.MK_DEPARTMENT_CODE ="+dept;
	         }
	       sql = sql+
	         " ORDER BY c.coursenr, b.title ";
	       
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
			
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
			BooklistDetails bookDetails = new BooklistDetails();			
		try{
			bookDetails.setStudyUnit(data.get("course").toString());
			bookDetails.setTitle(data.get("Title").toString());
			bookDetails.setAuthor(data.get("Author").toString());
			bookDetails.setEdition(data.get("edition").toString());
			bookDetails.setPublYear(data.get("Year_of_publish").toString());		
			bookDetails.setIsbn(data.get("isbn").toString());
			bookDetails.setEbookvolume(data.get("EBOOK_VOLUME").toString());
			bookDetails.setEbookpages(data.get("EBOOK_PAGES").toString());
			bookDetails.setPublication(data.get("Publication").toString());
			bookDetails.setCollege(college);			
			bookDetails.setSchool(sch);
			results.add(bookDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}		
	  }			  
 return results;
}
	/**
	 * 
	 *
	 * @param yr
	 * @param college
	 * @param sch
	 * @param dept
	 * @return  List of confirmed prescribed books for a given year,college , school and a department
	 */
	/*public ArrayList getPrescibedBooks(String yr,int college,String sch,String dept,String statusOptions ){
		ArrayList results = new ArrayList();
		String sql =
			" SELECT c.COURSENR coursenr,Title,Author," +
			" DECODE (edition, null, ' ', edition) edition, "+				
			" DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+
			" DECODE (isbn, null, ' ', isbn)  isbn,"+
			" Course_Language,b.notes notes,Confirm,Is_Published "+
			" FROM books1 b, crsbs1 c, sun s "+
			" where b.booknr=c.booknr "+
			" and s.college_code= "+college+  
			" and s.school_code= "+sch+
			" and s.mk_department_code= "+dept+
			" and s.code=c.coursenr "+
			" and c.confirm= "+statusOptions+			
			" and c.year= "+yr+
			" ORDER BY coursenr, Title asc ";
		  
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
	
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
			BooklistDetails bookDetails = new BooklistDetails();
			
		try{
			bookDetails.setStudyUnit(data.get("coursenr").toString());
			bookDetails.setTitle(data.get("Title").toString());
			bookDetails.setAuthor(data.get("Author").toString());
			bookDetails.setEdition(data.get("edition").toString());
			bookDetails.setPublYear(data.get("Year_of_publish").toString());		
			bookDetails.setIsbn(data.get("isbn").toString());
			bookDetails.setCollege(college);
			bookDetails.setDepartment(dept);
			bookDetails.setSchool(sch);			
			results.add(bookDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}		
	  }			  
 return results;
}*/
/**
 * 
 * @param yr
 * @param college
 * @param sch
 * @param dept
 * @param coursecode
 * @return List of confirmed prescribed books for a given year,college , school , department and a course
 */
	public ArrayList getPrescibedBooks1(String yr,int college,String sch,String dept,String [] coursecodes, String booklistTypes, String statusOptions ){
		ArrayList results = new ArrayList();
		
		//Set s =
		String[] codes=coursecodes;
		String coursenrs ="("+"'"+codes[0]+"'";
		
		for (int i=1;i<codes.length;i++)
		{
			coursenrs +=",'"+codes[i]+"'";
		}
		coursenrs +=" )";

  System.out.println("course codes"+coursenrs);
		String sql =
			" SELECT c.COURSENR coursenr,Title," +
			 " DECODE (author, null, ' ', author) author, "+  
			" DECODE (edition, null, ' ', edition) edition, "+				
			" DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+
			" DECODE (isbn, null, ' ', isbn)  isbn,"+
		    " DECODE (EBOOK_VOLUME, null, ' ', EBOOK_VOLUME)  EBOOK_VOLUME," +
            " DECODE (EBOOK_PAGES, null, ' ', EBOOK_PAGES)  EBOOK_PAGES,"+
            " DECODE (PUBLISHER, null, ' ',PUBLISHER) Publication,"+
			" Course_Language,b.notes notes,Confirm,Is_Published "+
			" FROM books1 b, crsbs1 c, sun s , pbcrsn pc"+
			" where b.booknr=c.booknr "+
			" and s.college_code= "+college+  		
			" and s.school_code= "+sch+
			" and s.mk_department_code= "+dept+
			" and  c.CourseNr in "+coursenrs +
			"  and pc.book_status = c.BOOK_STATUS"+ 
			" and s.code=c.coursenr "+
	        " and pc.book_status ="+"'"+booklistTypes+"'"+
	        " AND c.coursenr = pc.mk_study_unit_code  " +
	        " AND c.YEAR = pc.mk_academic_year AND pc.status ="+statusOptions+
	        " AND pc.mk_study_unit_code = s.code"+ 
			" and c.year= "+yr+
			" ORDER BY coursenr, Title asc ";
		  
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
			
	  while (i.hasNext())
	  {	ListOrderedMap data = (ListOrderedMap) i.next();
			BooklistDetails bookDetails = new BooklistDetails();
			
		try{
			bookDetails.setStudyUnit(data.get("coursenr").toString());
			bookDetails.setTitle(data.get("Title").toString());
			bookDetails.setAuthor(data.get("Author").toString());
			bookDetails.setEdition(data.get("edition").toString());
			bookDetails.setPublYear(data.get("Year_of_publish").toString());		
			bookDetails.setIsbn(data.get("isbn").toString());
			bookDetails.setEbookvolume(data.get("EBOOK_VOLUME").toString());
			bookDetails.setEbookpages(data.get("EBOOK_PAGES").toString());
			bookDetails.setPublication(data.get("Publication").toString());
			bookDetails.setCollege(college);
			bookDetails.setDepartment(dept);
			bookDetails.setSchool(sch);			
			results.add(bookDetails);
			}
	    catch(NullPointerException e ){e.printStackTrace();}		
	  }			  
 return results;
}
	public ArrayList getModules(ArrayList booklist)
	{
		ArrayList modules =new ArrayList();
		ArrayList list =booklist;
		
		Iterator<BooklistDetails> i =list.iterator();
		
		while (i.hasNext()){
			BooklistDetails entry =(BooklistDetails)i.next();
			String module = entry.getStudyUnit();
			if (!modules.contains(module))
			{modules.add(module);
			
			}
		}	    
	return modules;
}
	/**
	 * Get an arraylist that returns only the first modules from a given sorted list of 
	 * modules to be presented as checkboxes on a page
	 * @param modules
	 * @return An arraylist of the first modules in a column
	 */

	 public ArrayList getFirstModules(ArrayList modules){
	 ArrayList temp=modules;
	 ArrayList firstMods=new ArrayList();
	 int i;
	 
     if(temp.size()<=5)
     {  if (temp.size()!=0)
    	 firstMods.add(temp.get(temp.size()-1));
       
     }
     else{	 
    	 	for (i=0;i<temp.size();i=i+5){    	 			        
	            if(temp.size()-i <5){
	            	i=temp.size()-1;
	            	firstMods.add(temp.get(i));
	            }
	            else if (i==0){}
	            else
	            {firstMods.add(temp.get(i));}	              
	        }	      
	   }	    
	  return firstMods;	
	 }
	
	
	
	
/*	
	
	public int trimsites(List inactive){
		int k=0;
		BooklistDetails sites;//=new List();
		Iterator i = inactive.iterator();
		while(i.hasNext())
		{sites = (BooklistDetails) i.next();
			 if (sites.getTotal().equals("0"))
				 {k++;}
		}
	return k;
	}
*/

/**Get inactive sites with students for a given college code and year
*
* @param code
* @param yr
* @return
*/

public Map collegeDepts(String d)
	{
	String sql =" SELECT eng_description department,code" +
			" from dpt where college_code = "+d+
			" and in_use_flag ='Y' ";
		 // String sql =" SELECT CODE,eng_description AS DEPARTMENT from DPT ";
		  Map results = Collections.synchronizedMap(new TreeMap());
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList.iterator();
		  while(i.hasNext())
		  {
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  String dept =data.get("DEPARTMENT").toString();
			  String DEPARTMENT= data.get("DEPARTMENT").toString();
			  String code   = data.get("CODE").toString();
			  results.put(code,DEPARTMENT);//Maps codes to departments
		  }
		  return results;
	}

/**This bean is used to page through the records
 *
 * @param start
 * @param records
 * @param sites
 * @return
 */
public List pager(int start,int records,ArrayList sites){
	int end=start+records;
	int j = sites.size();
	List s= sites;

	if (start<0){start=1;}
	if (j==0){start=0;return sites;}
	else if (j<=records)
			{start =0;
		     s=sites.subList(start,j);
			end=j;
			}
	else
	{
		if(j>records)
		  { if (start+records<=j)
		        {if (start==0){start=1;}
		         s=sites.subList(start-1,start+records-1);
		        end =start+records-1;
		        }
		  else if(start+records>j)
		          {start =j-(j%records);
		           s=sites.subList(start,j);
		           end=j;
		          }
		  }
	}
  this.start =start;
 return s;
	}


public void testing( HashSet<String> test){
	HashSet t=test;
	Iterator i =t.iterator();
	while(i.hasNext())
	{String th =(String) i.next();
		System.out.println(th);


	}

  }

public Map getCollegeMap() {
	return collegeMap;
}

public void setCollegeMap(Map collegeMap) {
	this.collegeMap = collegeMap;
}

public void setStart(int start) {
	this.start = start;
}




}