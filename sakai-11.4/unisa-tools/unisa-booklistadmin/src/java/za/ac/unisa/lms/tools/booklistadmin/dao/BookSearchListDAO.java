package za.ac.unisa.lms.tools.booklistadmin.dao;
import org.springframework.jdbc.core.RowMapper;
import java.sql.Types;
import java.util.List;
public class BookSearchListDAO {
	public List getSearchbook(String title, String author,String publisher,Integer academicYear , String typeOfbooklist,String eReserveType) throws Exception {
		          typeOfbooklist=typeOfbooklist.toUpperCase();
		List results;
		Integer acadYear =  academicYear- 3;
		eReserveType=formateReserveType(typeOfbooklist,eReserveType);
		String sql = "";
		if (!title.equalsIgnoreCase("") && author.equalsIgnoreCase("") && publisher.equalsIgnoreCase("")){
			      title = "%" +title.toUpperCase()+"%";
			      sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
			         DatabaseUtil databaseUtil=new  DatabaseUtil(); 
					 if(typeOfbooklist.equals("E")){
						 
							results= databaseUtil.query(sql,new Object[] {title,acadYear,typeOfbooklist,eReserveType }, 
									new int[] { Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
							       new BookSearchRowMapper1());
							System.out.println("Aca year="+acadYear+"title is "+title+"booklist is "+typeOfbooklist+" ereservetype is "+eReserveType+" sql E "+sql);
					}else{
						   results=queryDatabase(sql,new Object[] {title,acadYear,typeOfbooklist}, 
								new int[] {  Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
						       new BookSearchRowMapper1());
						   System.out.println("sql "+sql);
								
				}
		    		
			        		
		} else if (title.equalsIgnoreCase("") && !author.equalsIgnoreCase("") && publisher.equalsIgnoreCase("")){
			author = "%" +author.trim().toUpperCase()+"%";
			        sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
					 if(typeOfbooklist.equals("E")){
					results=queryDatabase(sql,new Object[] {author,acadYear,typeOfbooklist,eReserveType }, 
							new int[] { Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
					       new BookSearchRowMapper1());
			}else{
				   results=queryDatabase(sql,new Object[] {author,acadYear,typeOfbooklist}, 
						new int[] {  Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
				       new BookSearchRowMapper1());
						
		    }
			        
		}else if (title.equalsIgnoreCase("") && author.equalsIgnoreCase("") && !publisher.equalsIgnoreCase("")){
			         publisher = "%" +publisher.toUpperCase()+"%";
			         sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
			         if(typeOfbooklist.equals("E")){
					        results=queryDatabase(sql,new Object[] {publisher,acadYear,typeOfbooklist,eReserveType }, 
							new int[] { Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
					       new BookSearchRowMapper1());
			}else{
				   results=queryDatabase(sql,new Object[] {publisher,acadYear,typeOfbooklist}, 
						new int[] {  Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
				       new BookSearchRowMapper1());
						
		    }
		}else if(!title.equalsIgnoreCase("") && !author.equalsIgnoreCase("") && publisher.equalsIgnoreCase("")){
			title = "%" +title.toUpperCase()+"%";
			author = "%" +author.toUpperCase()+"%";
			sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
				        if(typeOfbooklist.equals("E")){
							results=queryDatabase(sql,new Object[] {title,author,acadYear,typeOfbooklist,eReserveType }, 
									new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
							       new BookSearchRowMapper1());
					}else{
						   results=queryDatabase(sql,new Object[] {title,author,acadYear,typeOfbooklist}, 
								new int[] {  Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
						       new BookSearchRowMapper1());
								
							}
		} else if(!title.equalsIgnoreCase("") && author.equalsIgnoreCase("") && !publisher.equalsIgnoreCase("")){
			title = "%" +title.toUpperCase()+"%";
			publisher = "%" +publisher.toUpperCase()+"%";
			sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
			 if(typeOfbooklist.equals("E")){
					results=queryDatabase(sql,new Object[] {title,publisher,acadYear,typeOfbooklist,eReserveType }, 
							new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
					       new BookSearchRowMapper1());
			}else{
				   results=queryDatabase(sql,new Object[] {title,publisher,acadYear,typeOfbooklist}, 
						new int[] {  Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
				       new BookSearchRowMapper1());
						
					}
		} else if(title.equalsIgnoreCase("") && !author.equalsIgnoreCase("") && !publisher.equalsIgnoreCase("")){
			publisher = "%" +publisher.toUpperCase()+"%";
			author = "%" +author.toUpperCase()+"%";
			sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
				       if(typeOfbooklist.equals("E")){
				results=queryDatabase(sql,new Object[] {author,publisher,acadYear,typeOfbooklist,eReserveType }, 
						new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
				       new BookSearchRowMapper1());
		}else{
			   results=queryDatabase(sql,new Object[] {author,publisher,acadYear,typeOfbooklist}, 
					new int[] {  Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
			       new BookSearchRowMapper1());
					
				}
		}else{
			title = "%" +title.toUpperCase()+"%";
			author = "%" +author.toUpperCase()+"%";
			publisher = "%" +publisher.toUpperCase()+"%";
			sql=getSql(typeOfbooklist,eReserveType,title,author,publisher);
				   	if(typeOfbooklist.equals("E")){
				results=queryDatabase(sql,new Object[] {title,author,publisher,acadYear,typeOfbooklist,eReserveType }, 
						new int[] { Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR},
				       new BookSearchRowMapper1());
		}else{
			   results=queryDatabase(sql,new Object[] {title,author,publisher,acadYear,typeOfbooklist}, 
					new int[] {  Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
			       new BookSearchRowMapper1());
					
				}
		}
	
		return results;
	}
	private String eReserveCheck(String bookListType,String eReserveType){
		 if(!bookListType.equals("E")){
		    	           return "";
		       }else{
		    	       return  "  AND ERESERVE_TYPE = ? ";
		       }
	}
	private String formateReserveType(String bookListType,String eReserveType){
		String  typeOfEreserve="";
		 if(bookListType.equals("E")){
	          if(eReserveType.toUpperCase().indexOf("JOURNAL")!=-1){
	        	  typeOfEreserve = "J";
			   } else if(eReserveType.toUpperCase().indexOf("LAW")!=-1){
				   typeOfEreserve = "L";
			   }else if(eReserveType.toUpperCase().indexOf("BOOK")!=-1){
				   typeOfEreserve = "B";
			   }
		 }else{
			 typeOfEreserve = "";
		}
	 return typeOfEreserve;
	}
	public List getSearchedBookList (String title, String author,Integer academicYear, String subject, String typeOfbooklist,String eReserveType) throws Exception {			
			List results = null;
		Integer acadYear = academicYear - 1;
		String sql = "";
		if(typeOfbooklist.equals("P")){
			acadYear=academicYear- 1;
		}else{
			acadYear = academicYear;
		}
		
		if (!title.equalsIgnoreCase("") && author.equalsIgnoreCase("")){
			title = "%" +title.toUpperCase()+"%";
			sql = "SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
					"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE " +
					" FROM books1 where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c " + 
					"where b.booknr=c.booknr and UPPER(title) like ? and UPPER(coursenr) <> "+
					"? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc"+
					eReserveCheck(typeOfbooklist,eReserveType);
			if(typeOfbooklist.equals("E")){
				results=queryDatabase(sql,new Object[] {title,subject,acadYear,typeOfbooklist,eReserveType }, 
						new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
				       new BookSearchRowMapper());
		}else{
			results=queryDatabase(sql,new Object[] {title,subject,acadYear,typeOfbooklist}, 
					new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
			       new BookSearchRowMapper());
					
				}
		} else if (title.equalsIgnoreCase("") && !author.equalsIgnoreCase("")){
			author = "%" +author.toUpperCase()+"%";
			sql="SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
					"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE  FROM books1 " +
					eReserveCheck(typeOfbooklist,eReserveType)+
					"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and UPPER(Author) " +
					"like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?)"+
					" ORDER BY Upper(Title), Upper(Author), Edition asc"+eReserveCheck(typeOfbooklist,eReserveType);
			if(typeOfbooklist.equals("E")){
					results=queryDatabase(sql,new Object[] { author,subject,acadYear,typeOfbooklist,eReserveType }, 
							new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR },
					       new BookSearchRowMapper());
			}else{
				results=queryDatabase(sql,new Object[] { author,subject,acadYear,typeOfbooklist}, 
						new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
				       new BookSearchRowMapper());
						
					}
		} else {
			title = "%" +title.toUpperCase()+"%";
			author = "%" +author.toUpperCase()+"%";
			 sql="SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
					"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE  FROM books1 " +
					eReserveCheck(typeOfbooklist,eReserveType)+
					"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and UPPER(Title) like ? and "+
					" UPPER(Author) like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc"+
					eReserveCheck(typeOfbooklist,eReserveType);
			     if(typeOfbooklist.equals("E")){
				                results=queryDatabase(sql,new Object[] { title,author,subject,acadYear,typeOfbooklist,eReserveType}, 
	            		        new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR ,Types.VARCHAR },
			                    new BookSearchRowMapper());
				 
			 }else{
			             results=queryDatabase(sql,new Object[] { title,author,subject,acadYear,typeOfbooklist}, 
			            		 new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
					new BookSearchRowMapper());
			 }
		}
		return results;
	}
	private List queryDatabase(String  sql,Object[]  obJects,int[] types,RowMapper bookSearchRowMapper){
                          DatabaseUtil databaseUtil=new  DatabaseUtil();  
                          List results = databaseUtil.query(sql,obJects,types,bookSearchRowMapper);
                          return results;
   }
	private String getSql(String typeOfbooklist,String eReserveType,String title,String author,String publisher){
	       String sql = "SELECT DISTINCT B.BOOKNR Booknr, C.Coursenr coursenr,C.year year,B.TITLE Title,B.AUTHOR Author,B.OTHER_AUTHORS otherAuthor, B.EDITION Edition, "+
			         " B.Year_of_publish Year_of_publish, B.publisher publisher,B.ISBN ISBN,B.ISBN1 ISBN1,B.ISBN2 ISBN2,B.ISBN3 ISBN3,B.notes notes,B.Is_Published Is_Published," +
			       " B.NOTE_TO_LIBRARY NOTE_TO_LIBRARY,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE,  "+
			       " B.AVAIL_AS_EBOOK AVAIL_AS_EBOOK,B.EBOOK_VOLUME EBOOK_VOLUME,B.EBOOK_PAGES EBOOK_PAGES,B.URL URL,B.MC_AVAIL MC_AVAIL,B.MC_FORMAT MC_FORMAT FROM BOOKS1 B, CRSBS1 C "+
			       " WHERE B.BOOKNR = C.BOOKNR "+ getPartOfSqlThatFilterTheSearch(title,author,publisher)+
			       " AND  YEAR > ? AND C.BOOK_STATUS = ? "+eReserveCheck(typeOfbooklist,eReserveType)+		
			        " ORDER BY UPPER(B.TITLE), UPPER(B.AUTHOR), B.EDITION ASC";
		          return sql;
	}
	private String getPartOfSqlThatFilterTheSearch(String title,String author,String publisher){
		                  String sqlFilterStr="";
 		                  if(!title.equalsIgnoreCase("")){
			          			sqlFilterStr=" and UPPER(B.TITLE) LIKE ? ";
 		                  }
 		                 if(!author.equalsIgnoreCase("")){
			          			sqlFilterStr=" and UPPER(B.AUTHOR) LIKE ? ";
		                  }
 		                if(!publisher.equalsIgnoreCase("")){
		          			sqlFilterStr=" and UPPER(B.PUBLISHER) LIKE ? ";
		                  }
 		                return sqlFilterStr;
   }

}
