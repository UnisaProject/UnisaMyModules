package za.ac.unisa.lms.tools.publicprescribedbooks.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
//import za.ac.unisa.lms.tools.publicprescribedbooks.dao.PrescribedBooksDetails;

public class PrescribedBooksDAO extends StudentSystemDAO{
            
            public PrescribedBooksDAO(){
                        

            }
            
            public boolean isCodeInValid(String subject) throws Exception{
                        String code ="";
                        //makeJDBCCompatible(subject)+"%'" [method to handle ascii/funny character when subject code entered]
                        String sql = "select CODE from sun where CODE like '"+makeJDBCCompatible(subject)+"%'";
                        try{
                                    code = this.querySingleValue(sql,"CODE");
                        } catch (Exception ex) {
            throw new Exception("PrescribedBooksDAO method (isCodeInValid) error occurs / "+ ex,ex);
                        }
                        if (code.equalsIgnoreCase("")){
                                    return true;
                        } else {

                                    return false;
                        }
            }
            
            public ArrayList<PrescribedBooksDetails> getPrescribedBooksList(String year,String subjectcode) throws Exception{
                        ArrayList<PrescribedBooksDetails> results = new ArrayList<PrescribedBooksDetails>();
                        String sql;
                        int yr =Integer.parseInt(year);
                        if(!isReleaseDateLessThanToday('P',yr)){
         	        	   results= new java.util.ArrayList();
         	  			   return results;
         	           }
                    	if(yr < 2011){
                    		 sql =   "select Coursenr,b.title Title,b.author Author,b.year_of_publish Pyear,nvl(b.edition,' ') Edition,nvl(b.Publisher,' ') Publisher," +
                             "nvl(b.notes,' ') Bnotes, nvl(c.notes,' ') Cnotes ,Eng_Short_Descript Description " +
                             "FROM books1 b, crsbs1 c , sun " +
                             "WHERE b.booknr = c.booknr  and (c.confirm = 1 or c.confirm = 2) and c.coursenr = code " +
                             "and c.year = "+yr+" and (upper(COURSENR) LIKE '"+subjectcode.toUpperCase()+"%') ORDER by upper(Coursenr),upper(b.Title) ASC";
 
                    	}else{
                    		sql = "select p.mk_study_unit_code COURSENR, nvl(b.title,' ') Title ,nvl(b.author,' ') Author, nvl(b.year_of_publish,' ')  Pyear,nvl(b.Publisher,' ') Publisher," + 
                            "nvl (b.edition,' ') Edition,nvl(b.notes,' ') Bnotes,nvl(c.notes,' ') Cnotes,nvl(Eng_Short_Descript,' ') Description "+
                            "FROM books1 b,  crsbs1 c,pbcrsn p ,sun s WHERE b.booknr = c.booknr and s.code=p.mk_study_unit_code "+
                            "and (p.status=5 or p.status=6) and  c.year=p.mk_academic_year and p.book_status = c.BOOK_STATUS and "+
                            "c.coursenr =p.mk_study_unit_code and c.book_status = 'P' and c.year = "+yr+" and (upper(p.mk_study_unit_code) LIKE '"+subjectcode.toUpperCase()+"%')";

                    	}
                        try{
                                    JdbcTemplate jdt = new JdbcTemplate(getDataSource()); 
                                    List<?> queryList = jdt.queryForList(sql);
                                    Iterator<?> i = queryList.iterator();
                                    String temp1 = "0";
                                    int count = 1;
                                    while(i.hasNext()){
                                                ListOrderedMap data = (ListOrderedMap) i.next();
                                                PrescribedBooksDetails prescribedBooksDetails = new PrescribedBooksDetails();
                                                prescribedBooksDetails.setCourseCode(data.get("COURSENR").toString());
                                                prescribedBooksDetails.setDescription(data.get("DESCRIPTION").toString());
                                                prescribedBooksDetails.setTitle(data.get("TITLE").toString());
                                                prescribedBooksDetails.setAuthor(data.get("AUTHOR").toString());
                                                prescribedBooksDetails.setYearPublished(data.get("PYEAR").toString());
                                                prescribedBooksDetails.setEdition(data.get("EDITION").toString());
                                                prescribedBooksDetails.setPublisher(data.get("PUBLISHER").toString());
                                                prescribedBooksDetails.setBookNotes(data.get("BNOTES").toString());
                                                prescribedBooksDetails.setCourseNotes(data.get("CNOTES").toString());
                                                if (!prescribedBooksDetails.getCourseCode().equalsIgnoreCase(temp1)){
                                                            temp1 = prescribedBooksDetails.getCourseCode();
                                                            count = 1;
                                                            
                                                } else {
                                                            count++;
                                                }
                                                prescribedBooksDetails.setCount(new Integer(count));
                                                results.add(prescribedBooksDetails);
                                    }
                        }catch(Exception ex) {
                                    throw new Exception("PrescribedBooksDAO : error reading the getPrescribedBooksList function / "+ ex,ex);
                        }
                        return results;
            }
            public String getStatus(String course,String yr){
            	int year =Integer.parseInt(yr);
            	String confirmStatus;
            	if(year < 2011){             
                String selectSql = "select CONFIRM from crsbs1 where year = "+year+ " and book_status='P' and upper(coursenr) like '" +makeJDBCCompatible(course)+ "%'";
                            
                confirmStatus = querySingleValue(selectSql,"CONFIRM");
            	}else{
            		String selectSql="select STATUS from pbcrsn where mk_academic_year="+year+" and upper(mk_study_unit_code) like '" +makeJDBCCompatible(course)+ "%' and Book_status='P'";
            		 confirmStatus = querySingleValue(selectSql,"STATUS");
            	}
            
                      return confirmStatus;
                }
          
            public String getCourse(String code) throws Exception{
                String course = "";
                String sql;
                sql =   "select CODE from sun where upper(CODE) like '"+code+"%'";
                try{
                            JdbcTemplate jdt = new JdbcTemplate(getDataSource()); 
                            List<?> queryList = jdt.queryForList(sql);
                            Iterator<?> i = queryList.iterator();
                            int  count = 0;
                            while(i.hasNext()){
                                  ListOrderedMap data = (ListOrderedMap) i.next();
                                  if (count == 0){
                                      course = (String)data.get("CODE").toString();
                                  } else {
                                      course += ","+(String)data.get("CODE").toString();
                                  }
                                  
                                  count++;
                            }

                }catch(Exception ex) {
                            throw new Exception("PrescribedBooksDAO : error reading the getCourse function / "+ ex,ex);
                }
                return course;
    }

            //method to enter without clicking display/Enter
            private String querySingleValue(String query, String field){
                        JdbcTemplate jdt = new JdbcTemplate(getDataSource());
            List<?> queryList;
            String result = "";

               queryList = jdt.queryForList(query);
               Iterator<?> i = queryList.iterator();
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
            //method to handle Ascii characters
            private String makeJDBCCompatible(String convert) {
                String converted = null;
                
                if (convert.lastIndexOf("'") > -1) {
                    converted = convert.replaceAll("'","''");
                }
                else 
                    converted = convert;
                    
                return converted;
            }
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
