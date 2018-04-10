package za.ac.unisa.lms.tools.nosite.dao;


import java.util.Iterator;
import java.util.List;
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
import za.ac.unisa.lms.tools.nosite.dao.NositeDetails;
import za.ac.unisa.lms.tools.nosite.dao.College;

public class NositeDAO extends StudentSystemDAO {
    int start;//start of the page to be viewed;
    private Map collegeMap;
    private Map deptMap;
    private Map collegTotalMap;
    public SortedSet schools ;
    //public Vector schools ;
    
    /**
     * CollegeMap maps colleges names to college codes
     * deptMap maps department names to department codes
     * start is a global parameter used to signal the start of a page
     * Schools contains the set of school descriptions
     *
     *
   **/


    public NositeDAO()  {
	 collegeMap=Collections.synchronizedMap(new TreeMap());
	 collegTotalMap = Collections.synchronizedMap(new TreeMap());
	 deptMap=Collections.synchronizedMap(new TreeMap());
	 schools = Collections.synchronizedSortedSet(new TreeSet());

	 //schools=new Vector();
     }

    /**
     * Below are just getters and setters for the global parameters
     *
     */

	public int getStart() {
		return start;
	}
	public Map getCollegeMap() {
		return collegeMap;
	}
	public void setCollegeMap(Map collegeMap) {
		this.collegeMap = collegeMap;
	}
	public Map getDeptMap() {
		return deptMap;
	}
	public void setDeptMap(Map deptMap) {
		this.deptMap = deptMap;
	}


	//Get all colleges i.e. all the college names in the colleg table
	public Map getColleges()
	 {
	 			  String sql=
	 				" select code,eng_description COLLEGE from colleg "+
	 				" where dean > 0 " ;
	 			  Map results = Collections.synchronizedMap(new TreeMap());
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					NositeDetails nositeDetails = new NositeDetails();
					String college= data.get("COLLEGE").toString();
					String code   = data.get("CODE").toString();
					results.put(code,college);//Maps codes to colleges
					collegeMap.put(college,code);//Maps colleges to codes
				  }

		return results;
	 }

	/** Get all the Department names for the institution under a given college code
	 *
	 * @param d (department name)
	 * @return
	 */
	public Map getDepts(String d)
	 {
		String sql =" SELECT eng_description department,code from dpt" +
				" where college_code = "+d+
				" and in_use_flag ='Y' ";
		 Map results = Collections.synchronizedMap(new TreeMap());
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					NositeDetails nositeDetails = new NositeDetails();
					String DEPARTMENT= data.get("DEPARTMENT").toString();
					String code   = data.get("CODE").toString();
					results.put(code,DEPARTMENT);//Maps codes to departments
					deptMap.put(DEPARTMENT,code);//Maps departments to codes

				  }

		return results;
	 }
/**Get a  school code for given school and list contating school names and school codes
 *
 * @param schlist
 * @param sch
 * @return
 */
	public String getSchoolcode(List schlist,String sch)
	{
		List v = schlist;
		String schoolcode="";
		NositeDetails sites;
		Iterator i = v.iterator();
				while(i.hasNext())
				{   sites =  (NositeDetails) i.next();
				  		if (sites.getSchool().equals(sch))
				    	{ schoolcode=sites.getSchool_code();}
	            }
		return schoolcode;
	}

	/** Get all the Department names for the institution under a given college code
	 *
	 * @param s
	 * @return
	 */
	public Set getCollegeSchools(String s)
	 {
		String sql =" select eng_description AS SCHOOL " +
					" from school where college_code = "+s+
					" and in_use_flag ='Y' ";
	    		  SortedSet results = Collections.synchronizedSortedSet(new TreeSet());
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					results.add(data.get("SCHOOL").toString());

				  }

		return results;
	 }

	/**Get all the schools for the institution given a college code
	 *
	 * @param s
	 * @return
	 */

	public Vector getAllSchools(String s)
	 {
		String sql =" select code, eng_description AS SCHOOL " +
				    " from school where college_code = "+s+
				    " and in_use_flag ='Y' ";
		
	 			  Vector results = new Vector();
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList .iterator();
				  while(i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					NositeDetails nositeDetails = new NositeDetails();
					nositeDetails.setSchool(data.get("SCHOOL").toString());
    				nositeDetails.setSchool_code(data.get("CODE").toString());
					results.addElement(nositeDetails);
					schools.add(data.get("SCHOOL").toString());
				  }
		return results;
	 }

	/**Get the total number of inactive sites for a given year per college
	 *
	 * @param yr
	 * @return
	 */
	public Vector getInctiveTotals(String yr)
	 {
		  String sql =
			 " SELECT c.eng_description College, count(*) total "+
			 " FROM colleg c, sun s, sunpdt p "+
			 " WHERE s.code = p.fk_suncode  "+
			 
			 " and s.ACADEMIC_LEVEL <> 'D' "+
             " and research_flag = 'N' "+
             " and college_flag <> 'Y' "+  //Added by Johannes on 20 June 2008
			 
			 " AND s.in_use_flag = 'Y'  "+
			 " AND p.mk_academic_year = "+yr+
			 " AND c.code = s.college_code  "+
			 " AND p.mk_academic_period = 1  "+
			 " AND p.registration_allow = 'Y'  "+
			 " AND formal_tuition_fla = 'F' "+ //just added
			 " AND NOT EXISTS (SELECT * FROM usrsun u WHERE u.system_type = 'L'  "+
			                       " AND s.code = u.mk_study_unit_code  "+
			                       " AND p.fk_suncode = u.mk_study_unit_code  "+
			                       " AND p.mk_academic_year = u.mk_academic_year  "+
			                       " AND p.semester_period = u.mk_semester_period  "+
			                       " AND u.access_level = 'PRIML')  "+
			      " GROUP BY c.eng_description ";
	  Vector results = new Vector();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList.iterator();
	  while(i.hasNext()) {

		ListOrderedMap data = (ListOrderedMap) i.next();
		NositeDetails nositeDetails = new NositeDetails();
		nositeDetails.setCollege(data.get("COLLEGE").toString());
		nositeDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(nositeDetails);
	  }
return results;
}
	/**	Get the Total number of active sites per college for a given year where there are students enrolled
	 *  for some study unit
	 *
	 * @param yr
	 * @return
	 */


	public Vector getInctiveTotalsWithStudents(String yr)
	 {
		  String sql =
			 " SELECT c.eng_description College, count(*) total "+
			 " FROM colleg c, sun s, sunpdt p "+
			 " WHERE s.code = p.fk_suncode  "+
			 
			 " and s.ACADEMIC_LEVEL <> 'D' "+
             " and research_flag = 'N' "+
             " and college_flag <> 'Y' "+  //Added by Johannes on 7 July 2008
			 
			 " AND s.in_use_flag = 'Y'  "+
			 " AND p.mk_academic_year = "+yr+
			 " AND c.code = s.college_code  "+
			 " AND p.mk_academic_period = 1  "+
			 " AND p.registration_allow = 'Y'  "+
			 " AND formal_tuition_fla = 'F' "+ //just added
			 " AND NOT EXISTS (SELECT * FROM usrsun u WHERE u.system_type = 'L'  "+
			                       " AND s.code = u.mk_study_unit_code  "+
			                       " AND p.fk_suncode = u.mk_study_unit_code  "+
			                       " AND p.mk_academic_year = u.mk_academic_year  "+
			                       " AND p.semester_period = u.mk_semester_period  "+
			                       " AND u.access_level = 'PRIML')  "+
			      " GROUP BY c.eng_description ";
		          //" having COUNT (b.fk_student_nr)>0 ";

	  Vector results = new Vector();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList.iterator();
	  while(i.hasNext()) {

		ListOrderedMap data = (ListOrderedMap) i.next();
		NositeDetails nositeDetails = new NositeDetails();
		nositeDetails.setCollege(data.get("COLLEGE").toString());
		nositeDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(nositeDetails);

	  }
return results;
	 }
/**	Get the total number of active sites per college for a given year
 *
 * @param yr
 * @return
 */
	public Vector getActivesites(String yr)
	{
		  String sql =
			"  SELECT c.eng_description COLLEGE,count(*) TOTAL "+
			"  FROM colleg c, sun s, sunpdt p "+
			"  WHERE s.code = p.fk_suncode "+
			 
			" and s.ACADEMIC_LEVEL <> 'D' "+
            " and research_flag = 'N' "+
            " and college_flag <> 'Y' "+  //Added by Johannes on 7 July 2008
			 
			"  AND s.in_use_flag = 'Y' "+
			"  AND p.mk_academic_year = "+yr+
			"  AND c.code = s.college_code "+
			"  AND p.mk_academic_period = 1 "+
			"  AND p.registration_allow = 'Y' "+
			"  AND formal_tuition_fla = 'F' "+ //just added
			"  GROUP BY c.eng_description ";
		  SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
		  Vector results = new Vector();
		  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList.iterator();
		  while(i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			NositeDetails nositeDetails = new NositeDetails();
			nositeDetails.setCollege(data.get("COLLEGE").toString());
			nositeDetails.setTotal(data.get("TOTAL").toString());
			results.addElement(nositeDetails);
		  }
	return results;

	}

/**Sort the display of colleges on the page so that they can appear in alphabetical order
 *
 * @param c (set of colleges names)
 * @param l (List  )
 * @return
 *
 */
	public List sortCollegedisplay(Set c,List l)
	 {List temp =new Vector();
	  Iterator i =c.iterator();
	  while(i.hasNext()){
		  String college =i.next().toString();
		  Iterator j=l.iterator();
		  while (j.hasNext())
		  {College anothercollege =(College) j.next();
		   String college2 =anothercollege.getCollege();
		   if (college.equals(college2))
		     {temp.add(anothercollege);
		     break;}
		  }
	  }
	  return temp;
	 }


/**Associate college names to their corresponding sites
 *
 * @param active
 * @param inactive
 * @param inactivewithstudents
 * @return college details
 */
	public List getCollegeinfo(List active,List inactive,String year)
	{
	List a = active;
	Vector  c = new Vector();
	NositeDetails sites;
	College col;
	Iterator i = a.iterator();
	SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
			while(i.hasNext())
			{   sites =  (NositeDetails) i.next();
                col = new College();
                col.setCollege(sites.getCollege().toString());
                col.setActive(Integer.parseInt(sites.getTotal()));
                col.setInactive(getInactive(sites.getCollege(),inactive));
                String college =(String)collegeMap.get(sites.getCollege());
               // System.out.println(" This college is "+college+" and not "+sites.getCollege());
               // col.setInactivewithstudents(getInctivesitesWithStudents(college,year));
               //getInctivesitesWithStudents(String code, String yr)
               // getInctivesitesWithStudents(college,year);
                col.setInactivewithstudents(getInctivesitesWithStudents(college,year));
               // int tests=Integer.parseInt((collegTotalMap.get(sites.getCollege_code())).toString());
               // col.setInactivewithstudents(tests);
               // System.out.println("What is this now :"+tests);

               // col.setInactivewithstudents(getInactiveWithStudents(college,inactivewithstudents));

                c.add(col);
               }
			return c;
	}
/**
 *
 *
 * @param active
 * @param inactive
 * @param year
 * @return
 */
/*
	public College getCollegetotals(List active,List inactive,String year)
	{
	List a = active;
	Vector  c = new Vector();
	NositeDetails sites;
	College col;
	Iterator i = a.iterator();
	SortedSet test = Collections.synchronizedSortedSet(new TreeSet());
			while(i.hasNext())
			{   sites =  (NositeDetails) i.next();
                col = new College();
                col.setCollege(sites.getCollege().toString());
                col.setActive(Integer.parseInt(sites.getTotal()));
                col.setInactive(getInactive(sites.getCollege(),inactive));
                String college =(String)collegeMap.get(sites.getCollege());
               // System.out.println(" This college is "+college+" and not "+sites.getCollege());
               // col.setInactivewithstudents(getInctivesitesWithStudents(college,year));
               //getInctivesitesWithStudents(String code, String yr)
               // getInctivesitesWithStudents(college,year);
                col.setInactivewithstudents(getInctivesitesWithStudents(college,year));

               // col.setInactivewithstudents(getInactiveWithStudents(college,inactivewithstudents));

                c.add(col);
               }
			return c;
	}
	*/
	/** Get inactive sites with students for a given college
	 *
	 * @param college
	 * @param inactive
	 * @return
	 */
	public int getInactiveWithStudents(String college,List inactive)
	 {int inact=0;
	  NositeDetails sites;
	  Iterator i = inactive.iterator();

		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();
		    if (college.equals(sites.getCollege()))
		      {college=sites.getCollege();
		      // inact=Integer.parseInt(sites.getTotal());
		      inact=trimsites(inactive);
		      }
	    }
	 return inact;
	 }
/**  This method takes inactive sites for a given college and return only those with students registered on them
 *
 * @param inactive
 * @return
 */
	public int trimsites(List inactive){
		int k=0;
		NositeDetails sites;//=new List();
		Iterator i = inactive.iterator();
		while(i.hasNext())
		{sites = (NositeDetails) i.next();
			 if (sites.getTotal().equals("0"))
				 {k++;}
		}
	return k;
	}

	/**Get inactive sites for a given college
	 *
	 * @param college
	 * @param inactive
	 * @return
	 */
	public int getInactive(String college,List inactive)
	 {int inact=0;
	  NositeDetails sites;
	  Iterator i = inactive.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();
		    if (college.equals(sites.getCollege()))
		      {college=sites.getCollege();
		       inact=Integer.parseInt(sites.getTotal());
		      }
	    }
	 return inact;
	 }

/** Get a map of all schools under a given college  i.e. schools mapped to their corresponding codes
 *
 * @param college
 * @return
 */
public Map getSchools(String college)
	 {
	   Map results = Collections.synchronizedMap(new TreeMap());
		String sql=" select CODE, eng_description AS SCHOOL " +
				   " from SCHOOL where in_use_flag ='Y'";
				  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				  List queryList;
				  queryList = jdt.queryForList(sql);
				  Iterator i = queryList.iterator();
				  while(i.hasNext()) {
					  ListOrderedMap data = (ListOrderedMap) i.next();
					 NositeDetails nositeDetails = new NositeDetails();
				    String school= data.get("SCHOOL").toString();
					String code   = data.get("CODE").toString();
					results.put(code,school);//Maps codes to school
					schools.add(data.get("SCHOOL").toString());
				  }
		return results;

	 }

/**Get inactive sites with students for a given college code and year
*
* @param code
* @param yr
* @return
*/
	public int getInctivesitesWithStudents(String code, String yr)
	 {
	  String sql =
		"  SELECT s.school_code SCHOOL, s.mk_department_code DEPARTMENT, "+
		"  s.code STUDYUNIT, p.semester_period PERIOD, COUNT (b.fk_student_nr) TOTAL "+
		" FROM  sun s, sunpdt p, stusun b "+
		" WHERE s.code = p.fk_suncode  " +
		 
		" and s.ACADEMIC_LEVEL <> 'D' "+
        " and research_flag = 'N' "+
        " and college_flag <> 'Y' "+  //Added by Johannes on 7 July 2008
		 
		"AND s.in_use_flag = 'Y' " +
		"AND p.mk_academic_year = "+yr+
		" And S.COLLEGE_CODE  = "+code+
		" AND b.mk_study_unit_code(+) = p.fk_suncode "+
		    "  AND b.fk_academic_year(+) = p.mk_academic_year "+
		    "  AND b.fk_academic_period(+) = 1 "+
		    "  AND b.semester_period(+) = p.semester_period "+
		    "  AND b.status_code(+) <> 'TP' AND p.mk_academic_period = 1 "+
	    	"  AND p.registration_allow = 'Y'  "+
	    	"  AND formal_tuition_fla = 'F' "+
	  " AND NOT EXISTS (SELECT * FROM usrsun u WHERE u.system_type = 'L' "+
		                     " AND s.code = u.mk_study_unit_code "+
		                     " AND p.fk_suncode = u.mk_study_unit_code "+
		                     " AND p.mk_academic_year = u.mk_academic_year "+
		                     " AND p.semester_period = u.mk_semester_period "+
		                     " AND u.access_level = 'PRIML') "+
	 " GROUP BY s.school_code, s.mk_department_code,s.code, p.semester_period "+
	 " having COUNT (b.fk_student_nr)>0 "+
	 " order by s.code asc ";


	  Vector results = new Vector();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList.iterator();
	  while(i.hasNext()) {

		ListOrderedMap data = (ListOrderedMap) i.next();
		NositeDetails nositeDetails = new NositeDetails();
		nositeDetails.setCollege(code);
		nositeDetails.setSchool_code(data.get("SCHOOL").toString());
		nositeDetails.setDepartment_code(data.get("DEPARTMENT").toString());
		nositeDetails.setStudyunit(data.get("STUDYUNIT").toString());
		nositeDetails.setPeriod(data.get("PERIOD").toString());
		nositeDetails.setTotal(data.get("TOTAL").toString());
		results.addElement(nositeDetails);


	  }
	  //System.out.println(" This college has "+results.size()+" sites ");
return results.size();
	 }
/**Get inactive sites for a given college code and year
 *
 * @param code
 * @param yr
 * @return
 */
	public Vector getInctivesites(String code, String yr)
	 {
	  String sql =
		"  SELECT s.school_code SCHOOL, s.mk_department_code DEPARTMENT, "+
		"  s.code STUDYUNIT, p.semester_period PERIOD, COUNT (b.fk_student_nr) TOTAL "+
		" FROM  sun s, sunpdt p, stusun b "+
		" WHERE s.code = p.fk_suncode  " +
		 
		" and s.ACADEMIC_LEVEL <> 'D' "+
        " and research_flag = 'N' "+
        " and college_flag <> 'Y' "+  //Added by Johannes on 7 July 2008
		 
		"AND s.in_use_flag = 'Y' " +
		"AND p.mk_academic_year = "+yr+
		" And S.COLLEGE_CODE  = "+code+
		" AND b.mk_study_unit_code(+) = p.fk_suncode "+
		    "  AND b.fk_academic_year(+) = p.mk_academic_year "+
		    "  AND b.fk_academic_period(+) = 1 "+
		    "  AND b.semester_period(+) = p.semester_period "+
		    "  AND b.status_code(+) <> 'TP' AND p.mk_academic_period = 1 "+
	    	"  AND p.registration_allow = 'Y'  "+
	    	"  AND formal_tuition_fla = 'F' "+
	  " AND NOT EXISTS (SELECT * FROM usrsun u WHERE u.system_type = 'L' "+
		                     " AND s.code = u.mk_study_unit_code "+
		                     " AND p.fk_suncode = u.mk_study_unit_code "+
		                     " AND p.mk_academic_year = u.mk_academic_year "+
		                     " AND p.semester_period = u.mk_semester_period "+
		                     " AND u.access_level = 'PRIML') "+
	 " GROUP BY s.school_code, s.mk_department_code,s.code, p.semester_period "+
	 " having COUNT (b.fk_student_nr)>0 "+
	 " order by s.code asc ";

      int j=0;
	  Vector results = new Vector();
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List queryList;
	  queryList = jdt.queryForList(sql);
	  Iterator i = queryList.iterator();
	  while(i.hasNext()) {

		ListOrderedMap data = (ListOrderedMap) i.next();
		NositeDetails nositeDetails = new NositeDetails();
		nositeDetails.setCollege(code);
		nositeDetails.setSchool_code(data.get("SCHOOL").toString());
		nositeDetails.setDepartment_code(data.get("DEPARTMENT").toString());
		nositeDetails.setStudyunit(data.get("STUDYUNIT").toString());
		nositeDetails.setPeriod(data.get("PERIOD").toString());
		nositeDetails.setTotal(data.get("TOTAL").toString());
		if (Integer.parseInt(data.get("TOTAL").toString())!=0){j++;}
		results.addElement(nositeDetails);
	  }
	  collegTotalMap.put(code,j);
return results;
	 }

/**Get all the departments for the institution
 *
 * @param d
 * @return
 */

public Map collegeDepts(String d)
	{
	String sql =" SELECT eng_description department,code " +
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
				deptMap.put(DEPARTMENT,code);//Maps departments to codes

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
public List pager(int start,int records,List sites){
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

/**Get department sites for a given school and department
 *
 * @param school
 * @param dept
 * @param nosites
 * @return
 */
public List getDepsites(String school,String dept,List nosites)
{
List v = nosites;
List  t = new Vector();
NositeDetails sites;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();
			if (sites.getSchool_code().equals(school)&&sites.getDepartment_code().equals(dept))
			{	sites.getStudyunit();
			    sites.getPeriod();
			    sites.getTotal();
		  	    t.add(sites);
				}
		 }
	return t;
  }

/**Get department sites for a given department
 *
 * @param nosites
 * @param depts
 * @return
 */
public List getDepsites(List nosites,String depts)
{
List v = nosites;
List  t = new Vector();
NositeDetails sites;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();
				if (sites.getDepartment_code().equals(depts))
			{
				sites.getStudyunit();
			    sites.getPeriod();
			    sites.getTotal();
			    t.add(sites);
				}
		 }

return t;
  }

/**Get department sites for a given school
 *
 * @param school
 * @param nosites
 * @return
 */
public List getDepsites(String school,List nosites)
{
List v = nosites;
List  t = new Vector();
NositeDetails sites;
int k=0;
int f=0;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();
			if (sites.getSchool_code().equals(school))
			{
				sites.getStudyunit();
			    sites.getPeriod();
			    sites.getTotal();
			    t.add(sites);
				}

		 }
return t;
  }

/**Get departments for a given school and department
 *
 * @param school
 * @param dept
 * @param nosites
 * @return
 */
public SortedSet getDeps(String school,String dept,List nosites)
{
List v = nosites;
SortedSet results = Collections.synchronizedSortedSet(new TreeSet());
NositeDetails sites;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();

			if (sites.getSchool_code().equals(school)&&sites.getDepartment_code().equals(dept))
			{results.add(dept);}
		 }
return results;
  }

/**Get departments for a given school
 *
 * @param school
 * @param nosites
 * @param deptmap
 * @return
 */
public SortedSet getDeps(String school,List nosites,Map deptmap)
{

Map map=deptmap;
List v = nosites;
SortedSet results = Collections.synchronizedSortedSet(new TreeSet());
NositeDetails sites;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();

			if (sites.getSchool_code().equals(school))
			{ results.add(map.get(sites.getDepartment_code()));	}
		 }
return results;
  }

public SortedSet getDeps(String school,List nosites)
{
//Map map=deptmap;
List v = nosites;
SortedSet results = Collections.synchronizedSortedSet(new TreeSet());
NositeDetails sites;
Iterator i = v.iterator();
		while(i.hasNext())
		{   sites =  (NositeDetails) i.next();

			if (sites.getSchool_code().equals(school))
			{ results.add(sites.getDepartment());	}
		 }
return results;
  }



/** get a list of all the departments given a  vector containing nositeDetails,college,school and a dept
 *
 * Not in use
 *
 *
 */
public HashSet<String> getdepartments(List d,String college,String school,String department){
String dept=department;
HashSet<String> departments =new HashSet<String>();
List nosites =d;

Iterator i = nosites.iterator();
	while (i.hasNext())
	{NositeDetails data = (NositeDetails) i.next();

		if (data.getCollege().equals(college)&&data.getSchool().equals(school)&&data.getDepartment().equals(dept))
			{dept=data.getDepartment();
			 departments.add(dept);
			}
	}
return departments;

}

//get a list of all the departments given a vector containing nositeDetails,college and a school
public HashSet<String> getdepartments(List d,String school,Map map){
	String dept;//=department;
	HashSet<String> departments =new HashSet<String>();
	List nosites =d;
	Iterator i = nosites.iterator();
		while (i.hasNext())
		{NositeDetails data = (NositeDetails) i.next();
          try{
			if (data.getSchool_code().equals(school))
				{departments.add(map.get(data.getDepartment_code()).toString());
				}
          }
          catch (Exception ex){System.out.println("The problem is "+ex.getLocalizedMessage());}
		}
	return departments;
	}

public void testing( HashSet<String> test){
	HashSet t=test;
	Iterator i =t.iterator();
	while(i.hasNext())
	{String th =(String) i.next();
		System.out.println(th);


	}

  }

public Map getCollegTotalMap() {
	return collegTotalMap;
}

public void setCollegTotalMap(Map collegTotalMap) {
	this.collegTotalMap = collegTotalMap;
}


}