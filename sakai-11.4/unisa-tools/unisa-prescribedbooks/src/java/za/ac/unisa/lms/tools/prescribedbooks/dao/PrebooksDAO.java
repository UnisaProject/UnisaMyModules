package za.ac.unisa.lms.tools.prescribedbooks.dao;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
public class PrebooksDAO  extends StudentSystemDAO{

	public PrebooksDetails getLastPoster(String year, String course) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		PrebooksDetails  prebooksDetails = new PrebooksDetails();
		List results = jdt.query("select b.title Title,b.author Author, b.year_of_publish Pyear,b.edition Edition,b.notes Bnotes,c.notes Cnotes FROM books1 b, crsbs1 c WHERE b.booknr = c.booknr and (c.confirm = 1 or c.confirm = 2) and c.year = ? and c.book_status = 'P' and c.coursenr = ?",
							new Object[] { year,course},
							new int[] { Types.INTEGER },
									new PrebooksRowMapper());
		Iterator i = results.iterator();
		if (i.hasNext()){
			prebooksDetails = (PrebooksDetails) i.next();
		}
		return prebooksDetails;
	}

	public List getPrebooksList(String year, String course) throws Exception {
		
		List results;
	    int year1 = Integer.parseInt(year);
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		 if(!isReleaseDateLessThanToday('P',year1)){
			 results= new java.util.ArrayList();
			 return results;
         }
		if(year1<=2010){
			
			results = jdt.query("select distinct b.title Title,b.author Author, b.year_of_publish Pyear,publisher Sname, b.edition Edition,nvl(b.notes,' ') Bnotes,nvl(c.notes,' ') Cnotes FROM books1 b, crsbs1 c WHERE b.booknr = c.booknr and (c.confirm = 1 or c.confirm = 2) and c.book_status = 'P' and c.year = ? and c.coursenr = ? ",
					new Object[] { year,course},
					new int[] { Types.VARCHAR,Types.VARCHAR },
							new PrebooksRowMapper());	
		}else{
			
			results = jdt.query("select distinct b.title Title,b.author Author, b.year_of_publish Pyear,publisher Sname, b.edition Edition,nvl(b.notes,' ') Bnotes,nvl(p.crs_notes,' ') Cnotes FROM books1 b, crsbs1 c,pbcrsn p  WHERE b.booknr = c.booknr and (p.status=5 or p.status=6)and  c.year=p.mk_academic_year and c.coursenr=p.mk_study_unit_code and c.book_status = 'P' and c.year = ? and c.coursenr = ? ",
					new Object[] { year,course},
					new int[] { Types.VARCHAR,Types.VARCHAR },
					new PrebooksRowMapper());
		}
		return results;
	}
    public List getRecommendedBooksList(String year, String course) throws Exception {
	  	       List results;
               int year1 = Integer.parseInt(year);
	           JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	           if(!isReleaseDateLessThanToday('R',year1)){
	        	   results= new java.util.ArrayList();
	  			   return results;
	           }
	           if(year1>2010){
	           String queryStr="select distinct  b.title Title,b.author Author, b.year_of_publish Pyear,publisher Sname,b.edition Edition  FROM " +
	        	              "books1 b, crsbs1 c ,pbcrsn p WHERE b.booknr = c.booknr  and c.year = ? and c.year=p.mk_academic_year and " +
	        			      "c.coursenr=p.mk_study_unit_code and c.coursenr = ? and (p.status=5 or p.status=6) and c.book_status='R'";
		  	   results = jdt.query(queryStr,
				                   new Object[] {year,course},
	                               new int[] { Types.VARCHAR,Types.VARCHAR },
					               new recommendedBooksRowMapper());
		     		 
	           }else{
	        	     results=null;
	           }
	           return results;
    }
    public List getEreserveBooksList(String year, String course) throws Exception {
	        	List results;
	            int year1 = Integer.parseInt(year);
	            if(!isReleaseDateLessThanToday('E',year1)){
	            	results= new java.util.ArrayList();
	   			    return results;
	            }
		        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		        if(year1>2010){
		        	 String queryStr="select distinct b.title Title,b.author Author, b.year_of_publish Pyear,publisher Sname,nvl(b.url,'noUrl') URL,b.ebook_volume Volume  FROM " +
		        	 "books1 b, crsbs1 c ,pbcrsn p WHERE b.booknr = c.booknr  and c.year = ? and c.year=p.mk_academic_year and " +
		        			 "c.coursenr=p.mk_study_unit_code and c.coursenr = ? and (p.status=5 or p.status=6) and c.book_status='E'";
    		  	     results = jdt.query(queryStr,
   				                         new Object[] {year,course},
		                                 new int[] { Types.VARCHAR,Types.VARCHAR },
						                 new ereserveBooksRowMapper());
					 return results;
		        }else{
		        	results=null;
               }
               return results;
	}
	public String getConfirmStatus(String year,String course) throws Exception {
		//get confirmation or status of a  Prescribed Book in the approval process of the university
		 int year1 = Integer.parseInt(year);
		String sql = " select distinct nvl(Confirm,'0') Confirm from crsbs1 where year = "+year1+
				     " and book_status = 'P'"+
				     " and coursenr = '"+course+"'";
		String query =" select DISTINCT NVL(status,'0') status from pbcrsn " +
				      " where mk_study_unit_code = '"+course+"'"+
				      " and mk_academic_year= "+year1+
				      " and book_status = 'P'";
		String confirm = "0";
       	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		if(year1<=2010){
		List queryList  = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		if (i.hasNext()){
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("Confirm") == null){
				} else {
				  confirm = data.get("Confirm").toString();
			}
		}
	    }else{
		List queryList  = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		if (i.hasNext()){
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("status") == null){
					} else {
				 confirm = data.get("status").toString();
			}
		  }
	    }
		return confirm;
		
	 }
	public String getConfirmRStatus(String year,String course) throws Exception {
		  	//get confirmation or status of a  Recommended Book in the approval process of the university
		int year1 = Integer.parseInt(year);
			String query =" select DISTINCT NVL(status,'0') status from pbcrsn " +
				      " where mk_study_unit_code = '"+course+"'"+
				      " and mk_academic_year= "+year1+
				      " and book_status = 'R'";
		String confirm = "0";
        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		if(year1>2010){
		     List queryList  = jdt.queryForList(query);
		     Iterator i = queryList.iterator();
		     if (i.hasNext()){
			      ListOrderedMap data = (ListOrderedMap) i.next();
			      if (data.get("status") == null){
			    	    } else {
			    		   confirm = data.get("status").toString();
				          //System.out.println(confirm);
			      }//if
		     }//if
	    }//if
		return confirm;
		
	 }//getConfirmRStatus
	public String getConfirmEStatus(String year,String course) throws Exception {
		    //get confirmation or status of an Ereserve Book in the approval process of the university
		    int year1 = Integer.parseInt(year);
			String query =" select DISTINCT NVL(status,'0') status from pbcrsn " +
				      " where mk_study_unit_code = '"+course+"'"+
				      " and mk_academic_year= "+year1+
				      " and book_status = 'E'";
		    String confirm = "0";
            JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		    if(year1>2010){
		         List queryList  = jdt.queryForList(query);
		         Iterator i = queryList.iterator();
		         if (i.hasNext()){
			          ListOrderedMap data = (ListOrderedMap) i.next();
			          if (data.get("status") == null){
			          } else {
				               confirm =data.get("status").toString();
				               //System.out.println(confirm);
			         }//if
		         }//if
	        }//if
		return confirm;
	 }//getConfirmEStatus
	public boolean isReleaseDateLessThanToday(char bookStatus,int year){
		     String sql="SELECT  RELEASE_DATE FROM BLSTURD WHERE ACADEMIC_YEAR = "+year+" AND BOOK_STATUS = '"+bookStatus+"' AND RELEASE_DATE < SYSDATE";
		     JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		     List queryList  = jdt.queryForList(sql);
		     if(queryList.isEmpty()){
		    	 return false;
		     }else{
		    	 return true;
		     }
   }
}
