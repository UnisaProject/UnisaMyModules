package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;




public class BooklistImportDAO extends StudentSystemDAO {




	public BooklistImportDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean checkBookNrAndBibNumMatch(String bibNr,String bookNR)throws Exception {

		String select = "select COUNT(*) as COUNT from books1 where BIBLIOGRAPHICNR='"+bibNr+"' and booknr= "+bookNR;
	
          try {
             String recCount = this.querySingleValue(select,"COUNT");
             int counter =  Integer.parseInt(recCount); 
	         if(counter > 0){
		          return true;
	           }	
	         else {
		     return false;
	            }
	
             } catch (Exception ex) {
	           throw new Exception("in checkBookNrAndBibNumMatch method "+ ex,ex);
                }
       
		
	}
public boolean checkBibliographicNumber(String bookNR)throws Exception {
	
		String query = " SELECT COUNT(*) as COUNT "+
                       " FROM BOOKS1 "+
                       " WHERE BOOKNR ="+bookNR;
	
          try {
             String recCount = this.querySingleValue(query,"COUNT");
             int counter =  Integer.parseInt(recCount); 

	         if(counter > 0){
		          return true;
	           }	
	         else {
		     return false;
	            }
	
             } catch (Exception ex) {
	           throw new Exception("in check bibliographicnumber method "+ ex,ex);
                }     
		
	}
public boolean checkBooknr(String booknr)throws Exception {
	
	String query = " SELECT COUNT(*) as COUNT "+
                   " FROM BOOKS1 "+
                   " WHERE BOOKNR ="+booknr;

      try {
         String recCount = this.querySingleValue(query,"COUNT");
         int counter =  Integer.parseInt(recCount); 

         if(counter > 0){
	          return true;
           }	
         else {
	     return false;
            }

         } catch (Exception ex) {
           throw new Exception("checkBooknr "+ ex,ex);
            }
   
	
}
public String  checkBookNrfromDB(String biblio)throws Exception {
	
	String booknr = "0";
	String query = "SELECT NVL(BOOKNR,0) as BOOKNR "+
                   "FROM BOOKS1 "+
                   "WHERE BIBLIOGRAPHICNR = '"+biblio+"'";

      try {
          booknr = this.querySingleValue(query,"BOOKNR");
          if (booknr.equals("")) {
        	  booknr = "0";
          }

      } catch (Exception ex) {
           throw new Exception("in check bibliographicnumber method "+ ex,ex);
            }
   
	return booknr;
}
public boolean  checkBibNrfromDB(String biblio)throws Exception {
	
	String bibNum;
	boolean recordFound = false;
	/*
	String query = "SELECT NVL(BIBLIOGRAPHICNR,0) as BIBLIOGRAPHICNR "+
                   "FROM BOOKS1 "+
                   "WHERE BIBLIOGRAPHICNR = '"+biblio+"'";

      try {
          bibNum = this.querySingleValue(query,"BIBLIOGRAPHICNR");
          if(bibNum==null){
        	  bibNum="0";
          }

      } catch (Exception ex) {
           throw new Exception("in checkBibNrfromDB method "+ ex,ex);
            }*/
	String query = "SELECT count(*) AS FOUND "+
    "FROM BOOKS1 "+
    "WHERE BIBLIOGRAPHICNR = '"+biblio+"'";

	try {
		String found = this.querySingleValue(query,"FOUND");
		if (found.equals("0")) {
			recordFound = false;
		} else {
			recordFound = true;
		}
	
	} catch (Exception ex) {
		throw new Exception("in checkBibNrfromDB method "+ ex,ex);
	}
   
	return recordFound;
}


   public String getBooknr()throws Exception {
	   
	   String booknr = "";
       String query = " select BOOKS1_SEQUENCE_NR.NEXTVAL as BOOKNR from dual";
      try {
        booknr = this.querySingleValue(query,"BOOKNR");
 


  } catch (Exception ex) {
    throw new Exception(" in iscoursecodevalid method "+ ex,ex);
     }
  return booknr;

}

     public boolean isCoursecodeValid(String courseCode)throws Exception {
	
	       String query = " SELECT COUNT(*) as COUNT "+
                          " FROM SUN "+
                          " WHERE CODE ='"+courseCode+"'";
      try {
         String recCount = this.querySingleValue(query,"COUNT");
         int counter =  Integer.parseInt(recCount); 

         if(counter > 0){
	          return true;
           }	
         else {
	     return false;
            }

         } catch (Exception ex) {
           throw new Exception(" in iscoursecodevalid method "+ ex,ex);
            }
   
	
      }
     
     public boolean isCoursecodeValid1(String courseCode)throws Exception {
    		
	       String query = " SELECT COUNT(*) as COUNT "+
                        " FROM SUN "+
                        " WHERE CODE = '"+courseCode+"'";
    try {
       String recCount = this.querySingleValue(query,"COUNT");
       int counter =  Integer.parseInt(recCount); 

       if(counter == 1){
	        
    	 return true; 
         }	
       else {
	     return false;
          }

       } catch (Exception ex) {
         throw new Exception(" in iscoursecodevalid method "+ ex,ex);
          }
 
	
    }
     
     
     
   public String getCourseCode(String courseCode)throws Exception {
    	 String code="";
	       String query = " select code from sun where substr(code,1,6) ="+courseCode;
  try {
     code = this.querySingleValue(query,"CODE");
    

     
     } catch (Exception ex) {
       throw new Exception(" in iscoursecodevalid method "+ ex,ex);
        }
    return code;
	
  }
     
    public void recommendedInsert( String biblionum,String title,String author,String otherAuthor,String[] isbnTempList,String edition,String publisher,String year,String note_to_library, String Booknr)throws Exception{

    	String title1 = title.replace("'","");
  	    String other_author = otherAuthor.replace("'","");
  	    if(other_author.length()>255){
  	    	other_author = other_author.substring(0,253);
  	    }
  	    String author1 = author.replace("'","");
  	    String publish = publisher.replace("'","");
    	
    	String isbn = "\'0\'";
    	String isbn1 = "\'0\'";
    	String isbn2 = "\'0\'";
    	String isbn3 = "\'0\'";
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		   for (int i=0; i< isbnTempList.length; i++) {
			   if (i==0) {
				   isbn = isbnTempList[i];
				   isbn=isbn.replace("'","");
			   } else if (i==1) {
				   isbn1 = isbnTempList[i];
				   isbn1=isbn1.replace("'","");
			   } else if (i==2) {
				   isbn2= isbnTempList[i];
				   isbn2=isbn2.replace("'","");
			   } else if (i==3) {
				   isbn3= isbnTempList[i];
				   isbn3=isbn3.replace("'","");
			   }
		   }
	    
	 String query2 =  " INSERT INTO BOOKS1 (BOOKNR,SUPPLIERNR, TITLE,AUTHOR,OTHER_AUTHORS," +
	 		          " BIBLIOGRAPHICNR,ISBN,ISBN1, ISBN2, ISBN3, EDITION, PUBLISHER, YEAR_OF_PUBLISH, " +
	 		          " NOTE_TO_LIBRARY)  VALUES (?,?,SUBSTR(?,0,254),SUBSTR(?,0,254),SUBSTR(?,0,254),?,?,?,?,?,SUBSTR(?,0,10),SUBSTR(?,0,254),SUBSTR(?,0,10),SUBSTR(?,0,300))";
	 		          //" NOTE_TO_LIBRARY)  VALUES (BOOKS1_SEQUENCE_NR.NEXTVAL,"+999999+","+title+","+author+","+otherAuthor+","+biblionum+","+isbn+","+ 
                      //isbn1+","+isbn2+","+isbn3+","+edition+","+publisher+","+year+","+note_to_library+")";
	 
        try{

        
           jdt.update(query2,new Object[]{Booknr,999999,title1,author1,other_author,biblionum,isbn,isbn1,isbn2,isbn3,edition,publish,year,note_to_library});
           
      
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: recommendedInsert"+ex);
           }

   }
    public void recommendedUpdate( String biblionum,String title, String author,String otherAuthor,String[] isbnTempList,String edition,String publisher,String year,String note_to_library,String Booknr)throws Exception{
	
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    String title1 = title.replace("'","");
        if(title1.length()>255){
        	title1 = title1.substring(0,254); 
        }
	    String other_author = otherAuthor.replace("'","");
	    if(other_author.length()>255){
	    	other_author = other_author.substring(0,253); 
        }

	    String author1 = author.replace("'","");
	    String publish = publisher.replace("'","");
	    String query = " Update BOOKS1 set TITLE =substr('"+title1+"',0,254)"+
	                 " ,AUTHOR =substr('"+author1+"',0,254)"+
	                 " ,OTHER_AUTHORS = substr('"+other_author+"',0,254)" +
	                 " ,BIBLIOGRAPHICNR ='"+biblionum+"'";
	   for (int i=0; i< isbnTempList.length; i++) {
		   if (i==0) {
			   query=query+",ISBN='"+isbnTempList[i].replace("'","")+"'";
		   } else if (i==1) {
			   query=query+",ISBN1='"+isbnTempList[i].replace("'","")+"'";
		   } else if (i==2) {
			   query=query+",ISBN2='"+isbnTempList[i].replace("'","")+"'";
		   } else if (i==3) {
			   query=query+",ISBN3='"+isbnTempList[i].replace("'","")+"'";
		   }
	   }
	             query=query+" ,EDITION = substr('"+edition+"',0,25)"+
	                 " ,PUBLISHER = substr('"+publish+"',0,254)"+ 
	                 " ,YEAR_OF_PUBLISH = substr('"+year+"',0,10)"+ 
	                 " ,NOTE_TO_LIBRARY = substr('"+note_to_library+"',0,254)"+ 
	                 " where BOOKNR ="+Booknr;
	        
        try{

           jdt.update(query);
        
      
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: recommendedUpdate"+ex);
           }
	
   }
    public void ereservesInsert( String biblionum,String title, String author, String otherAuthor, String publisher, 
    		String ebook_pages,String ebook_volume ,String year_publish, String notes,String url, String bookNR,String eReserveType)throws Exception{
     
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    String title1 = title.replace("'","");
	    String other_author = otherAuthor.replace("'","");
	    if(other_author.length()>255){
  	    	other_author = other_author.substring(0,253);
  	    }
	    String author1 = author.replace("'","");
	    String publish = publisher.replace("'","");
	    //eReserveType is newly added field for 2016 lists
	    String query2 = " insert into BOOKS1 (Booknr, Suppliernr, BIBLIOGRAPHICNR, Title," +
	 		            " AUTHOR, OTHER_AUTHORS, PUBLISHER,  EBOOK_PAGES, EBOOK_VOLUME, YEAR_OF_PUBLISH, Notes, URL,ERESERVE_TYPE)" +
	 		            " values(?,?,?,substr(?,0,254),substr(?,0,254),substr(?,0,254)," +
	 		            " substr(?,0,254),substr(?,0,20),substr(?,0,25),substr(?,0,10),substr(?,0,300),substr(?,0,100),?)";
	

        try{
	  
	     // System.out.println("ereserve insert+++++++++++,999999"+biblionum+","+title1+","+author1+","+other_author+","+publish+","+ebook_pages+","+ebook_volume+","+year_publish+","+notes+","+url);
	      jdt.update(query2,new Object[] {bookNR,999999,biblionum,title1,author1,other_author,publish,ebook_pages,ebook_volume,year_publish,notes,url,eReserveType});
	      //jdt.update(query2);
          
         }catch(Exception ex){
        	 System.out.println("BooklistImportDAO.java: ereservesInsert"+ex);
           throw new Exception("BooklistImportDAO.java: ereservesInsert"+ex);
         }

   }
    
    
    public void ereservesUpdate( String biblionum,String title, String author, String otherAuthor, String publisher, String ebook_pages, String ebook_volume , String year_publish, String notes,String url, String booknr,String eReserveType)throws Exception{
    
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    String title1 = title.replace("'","");
	    if(year_publish.length()>10){
	    	year_publish = year_publish.substring(0,9);
	    }
	    if(title1.length()>255){
        	title1 = title1.substring(0,254); 
        }
	    if(notes.length()>300){
	    	notes = notes.substring(0,299); 
        }
	    String other_author = otherAuthor.replace("'","");
	    if(other_author.length()>255){
	    	other_author = other_author.substring(0,254); 
        }
	    String author1 = author.replace("'","");
	    if(author1.length()>255){
	    	author1 = author1.substring(0,254); 
        }
	    if(url.length()>100){
	    	url = url.substring(0,99); 
        }
	    String publish = publisher.replace("'","");
	    String query = " Update BOOKS1 set Title ='"+title1+"'"+ 
	                " ,AUTHOR ='"+author1+"'"+
	                " ,BIBLIOGRAPHICNR ='"+biblionum+"'"+
	                " ,OTHER_AUTHORS ='"+other_author+"'"+
	                " ,PUBLISHER =substr('"+publish+"',0,254)"+
	                " ,EBOOK_PAGES = substr('"+ebook_pages+"',0,20)"+
	                " ,EBOOK_VOLUME = substr('"+ebook_volume+"',0,25)"+
	                " ,YEAR_OF_PUBLISH = '"+year_publish+"'"+
	                " ,Notes = '"+notes+"'"+
	                " ,URL ='" +url+"'"+
	                " ,ERESERVE_TYPE ='" +eReserveType+"'"+
	                " where BOOKNR ="+booknr;


        try{

           //jdt.update(query);
        	jdt.update(query);
    
        }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: ereservesUpdate"+ex);
        }

   }
    
    public boolean sevendigitInsert(String courseCode,String year,int Booknumber)throws Exception{
    	
	      
    	boolean temp= false;
    	List<?> queryList;
    	int count=0;
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());

	    String query1 = " select * from CRSBS1 where Coursenr ='"+courseCode+"'"+
	                    " and Year = "+year+
	                    " and Booknr = "+Booknumber+
	                    " and book_status ='"+"R"+"'";
	    
	 
	    
	 String query = " insert into CRSBS1 (Coursenr, Booknr, Book_status, Year, Confirm) " +
	 		        " values('"+courseCode+"',"+Booknumber+",'R',"+year+",0)";
	// System.out.println("INSERT "+query);

        try{
        	 queryList = jdt.queryForList(query1);
      	     count = queryList.size(); 
      	    // System.out.println(">>> "+Booknumber+" "+count);
      	     if(count==0){
      	    	 
               jdt.update(query);
      	     
           temp = true;
      	     }
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: sevendigitInsert"+ex);
           }
	return temp;
   }
    public boolean sevendigitInsert1(String courseCode,String year,int Booknumber)throws Exception{
    	
	  
    	boolean temp= false;
    	List<?> queryList;
    	int count=0;
    	
   	 String query1 = " select * from CRSBS1 where Coursenr ='"+courseCode+"'"+
   	 		         " and Year = "+year+
   	 		         " and Booknr = "+Booknumber+
   	 		         " and book_status ='"+"E"+"'";

      
   	  
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    
	    
	    
	 String query = " insert into CRSBS1 (Coursenr, Booknr, Book_status, Year, Confirm) " +
	 		        " values(?,?,?,?,?)";

        try{
        	     queryList = jdt.queryForList(query1);
        	     count = queryList.size(); 
        	     
        
           if(count==0){
           jdt.update(query,new Object[] {courseCode, Booknumber, "E",year,"0"});
     
           temp = true;
           }
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: sevendigitInsert1"+ex);
           }
	return temp;
   }
    
    public boolean checkRecordExist(String courseCode,String year,int Booknumber)throws Exception{
    	
    	List<?> queryList;
    	boolean temp= false;
    	int count=0;
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	 String query = " Select * from PBCRSN where mk_academic_year ="+year+
	                " and mk_study_unit_code ='"+courseCode+"'"+
	                " and book_status ='"+"R"+"'";

        try{
        	 queryList = jdt.queryForList(query);

		   	  count = queryList.size(); 
		   
		   	  if(count==0){
		   		 String query1 = " INSERT INTO PBCRSN (MK_ACADEMIC_YEAR, MK_STUDY_UNIT_CODE, BOOK_STATUS, Status)" +
		   		 		        " VALUES (?,?,?,?)";  
		   	    jdt.update(query1,new Object[] {year,courseCode,"R","5"});
		   	  }
       
           temp = true;
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: chekRecordExist"+ex);
           }
	return temp;
   }
   public boolean checkRecordExist1(String courseCode,String year,int Booknumber)throws Exception{
    	
    	List<?> queryList;
    	boolean temp= false;
    	int count=0;
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	 String query = " Select * from PBCRSN where mk_academic_year ="+year+
	                " and mk_study_unit_code ='"+courseCode+"'"+
	                " and book_status ='"+"E"+"'";


        try{
        	 queryList = jdt.queryForList(query);

		   	  count = queryList.size(); 
		 
		   	  if(count==0){
		   		 String query1 = " INSERT INTO PBCRSN (MK_ACADEMIC_YEAR, MK_STUDY_UNIT_CODE, BOOK_STATUS, Status)" +
		   		 		        " VALUES (?,?,?,?)";  
		   	    jdt.update(query1,new Object[] {year,courseCode,"E","5"});
		   	  }
       
           temp = true;
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: chekRecordExist1"+ex);
           }
	return temp;
   }
    
    
    public boolean insertRecommendedPBSAUD(String courseCode,String year)throws Exception{
    	

    	boolean temp;
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    
	    
	    
	    String query = " INSERT INTO PBSAUD (MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS,"+
                       " TRANSACTION_TIME,UPDATE_INFO) VALUES(?,?,?,?,Current_Timestamp,?)";
	

        try{
        	
		   	    jdt.update(query,new Object[] {"import",courseCode,year,"R","Import Recommended Books"});
		   	  
       
           temp = true;
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: chekRecordExist"+ex);
           }
	return temp;
   }
    
    
    
    public boolean insertRecommendedPBSAUD1(String courseCode,String year)throws Exception{
    	

    	boolean temp;
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    String query = " INSERT INTO PBSAUD (MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS,"+
                       " TRANSACTION_TIME,UPDATE_INFO) VALUES(?,?,?,?,Current_Timestamp,?)";
	

        try{
        	
		   	    jdt.update(query,new Object[] {"import",courseCode,year,"E","Import EReserves"});
		   	  
       
           temp = true;
           }catch(Exception ex){
           throw new Exception("BooklistImportDAO.java: insertRecommendedPBSAUD1"+ex);
           }
	return temp;
   }
    
    public void deleteRecords(String status,String year)throws Exception{
    	
	    JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	    
	    String query = "delete  from crsbs1 where YEAR='"+year+"'"+
	    		       " and BOOK_STATUS='"+status+"'";
	    try{
	            int rows = jdt.update(query);
	           System.out.println(rows + " row(s) deleted for import year "+year);
	    }catch(Exception ex){
	     throw new Exception("BooklistImportDAO.java: deleteRecords"+ex);
	    }
   }
    
  
    private String querySingleValue(String query, String field){
	  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	  List<?> queryList;
	  String result = "";
	

	  queryList = jdt.queryForList(query);
	  Iterator<?> i = queryList.iterator();
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
