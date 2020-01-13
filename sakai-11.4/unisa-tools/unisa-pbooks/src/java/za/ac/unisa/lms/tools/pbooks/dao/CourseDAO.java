package za.ac.unisa.lms.tools.pbooks.dao;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.pbooks.dao.submissionDatesDetails;
import za.ac.unisa.lms.tools.pbooks.dao.BookDetails;
import za.ac.unisa.lms.tools.pbooks.dao.BookRowMapper;
import za.ac.unisa.lms.tools.pbooks.dao.CollegeDeptDetails;
import za.ac.unisa.lms.tools.pbooks.forms.BookMenuForm;

	public class CourseDAO  extends StudentSystemDAO{

		private PreparedStatementCreatorFactory pstsmtCreatorFactoryPreBook;
		private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseLink;
		private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseNote;
		private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseNoteEdit;
		private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseUpdatePbcrsn;
		private PreparedStatementCreatorFactory pstsmtCreatorFactoryCourseInsertPbcrsn;
		public CourseDAO(){
			pstsmtCreatorFactoryPreBook = new PreparedStatementCreatorFactory(
					"insert into BOOKS1 (Booknr,Suppliernr,Title,Author,ISBN,ISBN1,ISBN2,ISBN3,Edition,Year_Of_Publish,Notes,Publisher,is_published)"+
					"values(BOOKS1_SEQUENCE_NR.nextval,999999,?,?,?,?,?,?,?,?)",
					new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}
					);
			pstsmtCreatorFactoryPreBook.setGeneratedKeysColumnNames(new String[]{"Booknr"});
			pstsmtCreatorFactoryPreBook.setReturnGeneratedKeys(false);
			pstsmtCreatorFactoryCourseLink = new PreparedStatementCreatorFactory(
					"insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
					"values(?,?,?,?,?,0)",
					new int[] {Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.INTEGER}
					);
			pstsmtCreatorFactoryCourseNote = new PreparedStatementCreatorFactory(
					"insert into PBCRSN (Mk_Academic_Year,Mk_Study_Unit_Code,Crs_Notes,status,book_status)"+
					"values(?,?,?,?,?)",
					new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);
			pstsmtCreatorFactoryCourseNoteEdit = new PreparedStatementCreatorFactory(
					"update PBCRSN set Crs_Notes = ? where Mk_Academic_Year = ? and Mk_Study_Unit_Code = ?",
					new int[] {Types.VARCHAR,Types.INTEGER,Types.VARCHAR} 
					);			
			pstsmtCreatorFactoryCourseUpdatePbcrsn = new PreparedStatementCreatorFactory(
					"update pbcrsn set STATUS = ? where MK_STUDY_UNIT_CODE = ? and MK_ACADEMIC_YEAR = ? and book_status= ?",
					new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}
					);	
			pstsmtCreatorFactoryCourseInsertPbcrsn = new PreparedStatementCreatorFactory(
					"insert into pbcrsn(MK_ACADEMIC_YEAR,MK_STUDY_UNIT_CODE,CRS_NOTES,STATUS,BOOK_STATUS) " +
					"values(?,?,?,?,?)",
					new int[] {Types.INTEGER,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR}					
					);	
		}
		
		/**
		 * This methods updates the pbcrsn table, changing the CONFIRM field. 
		 * The CONFIRM field can have the following values {0,1,2,3,4}
		 * 0 : Unconfirmed
		 * 1 : Confirmed
		 * 2 : Authorized/Approved
		 * 3 : No books
		 * 4 : Closed
		 * The confirm parameter in this method contains the status change as listed above.
		 * @param confirm
		 * @param course
		 * @param acadyear
		 */
		 public List getOpenAndClosingDatesOfblist(String booklist_type,int selectedYear,String coursecode){
		      String sql="Select * from BLDATES b, SUN s where b.book_status='"+booklist_type+
				 "' and b.academic_year="+selectedYear+" and b.study_level=s.study_level and s.code='"+coursecode+"' and START_DATE <  sysdate and end_date > sysdate";
		      List results=new java.util.ArrayList();
		      //System.out.println("sql"+sql);
			  JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			  List queryList;
			  queryList = jdt.queryForList(sql);
			  Iterator i = queryList .iterator();
			  
		  while (i.hasNext()){	
			  ListOrderedMap data = (ListOrderedMap) i.next();
		      submissionDatesDetails bldatesDetails = new submissionDatesDetails();
			  try{
				   bldatesDetails.setAcademicYear(data.get("ACADEMIC_YEAR").toString());
				   bldatesDetails.setLevel(data.get("STUDY_LEVEL").toString());
				   bldatesDetails.setOpeningDate(data.get("START_DATE").toString().substring(0,11));
				   bldatesDetails.setClosingDate(data.get("END_DATE").toString().substring(0,11));
				   bldatesDetails.setActiveStatus(data.get("ACTIVE_STATUS").toString());
				   bldatesDetails.setRemove(false);
		  		   results.add(bldatesDetails);
			  }catch(NullPointerException e ){e.printStackTrace();}
			
		  }			  
	      return results;
	    }
		public void updateBookListStatus(String confirm,String course, String acadyear, String networkID, String typeOfbooklst)throws Exception{
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		//	String sql = "update CRSBS1 set confirm = "+confirm+" where Coursenr = '"+course+"' and Year = "+acadyear+" and book_status= '"+typeOfbooklst+"'";
			if (confirm.equals("1")){
									 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
								 new Object[]{ confirm,course,acadyear,typeOfbooklst});
									 jdt.update(stmt);				
			}else if(confirm.equals("3")){
				String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+course+"' and MK_ACADEMIC_YEAR = '"+acadyear+"' and BOOK_STATUS= '"+typeOfbooklst+"'";
				String selectedCourse;
				try{
					selectedCourse = querySingleValue(sqlcourse,"mk_study_unit_code");
					
				} catch (Exception ex) {
				    throw new Exception("CourseDAO : updateBookListStatus Error occurred / "+ ex,ex);
				}
			 if(selectedCourse.length()==0){
				 
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseInsertPbcrsn.newPreparedStatementCreator(	 
						 new Object[]{acadyear,course,"",3,typeOfbooklst});
				 jdt.update(stmt);						 
			
			}else{
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ confirm,course,acadyear,typeOfbooklst});
				 jdt.update(stmt);
				}
			}else if(confirm.equals("0")){
				
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ confirm,course,acadyear,typeOfbooklst});
				 jdt.update(stmt);				
			}else if(confirm.equals("2")){
				
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ confirm,course,acadyear,typeOfbooklst});
				 jdt.update(stmt);				
			}else if(confirm.equals("4")){
				
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ confirm,course,acadyear,typeOfbooklst});
				 jdt.update(stmt);				
			}else if(confirm.equals("5")){
				
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ confirm,course,acadyear,typeOfbooklst});
				 jdt.update(stmt);				
			}
			
			

		}
		
		public String getDBBookListStatus(String course, String acadyear, String typeOfbook){		
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String selectSql = "select STATUS from pbcrsn where mk_study_unit_code = '"+course;
			       selectSql += "' AND MK_ACADEMIC_YEAR = "+acadyear+" and BOOK_STATUS='"+typeOfbook+"'";
		    
			String confirmStatus = querySingleValue(selectSql,"STATUS");
			
			return confirmStatus;
		}
		
		//Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4:Tutor Booklist: Get Tutor Status
		public int getDBTutorBookListStatus( String course, String acadyear, String typeOfbook ) throws Exception{
			String stringStatus = "";
			int tutorStatus = 0;
			
			try{
				JdbcTemplate jdt = new JdbcTemplate( getDataSource() );
				String selectSql = "select TUTOR_STATUS from pbcrsn"+
								   " where mk_study_unit_code = '"+course+"'"+
								   " AND mk_academic_year="+acadyear+
								   " AND book_status = '"+typeOfbook+"'";
				
				stringStatus = querySingleValue( selectSql, "TUTOR_STATUS" );
				
				//convert stringStatus to int - must not equal null or ""
				if ( !stringStatus.isEmpty() )
					tutorStatus = Integer.parseInt( stringStatus );
				
			}catch( Exception ex ){
				throw new Exception("CourseDAO : getDBTutorBookListStatus Error occurred / "+ ex,ex);
			}
			
			return tutorStatus;
		}
		
		public CollegeDeptDetails getStandInDetails(String course) throws Exception{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
			
			List results = jdt.query("select distinct b.eng_description COLLEGE,d.eng_description DEPT,d.code" +
					" from sun su, dpt d, colleg b " +
					"where su.code = '"+course+"' and su.mk_department_code = d.code " +
					" and su.in_use_flag = 'Y' and su.college_code = b.code ",new CollegeDeptStandinDetailRowMapper());
			
			Iterator i = results.iterator();
			if (i.hasNext()){
				collegeDeptDetails = (CollegeDeptDetails) i.next();
			}
			return collegeDeptDetails;
		}
		public CollegeDeptDetails getCollegeDeptDetails(String course) throws Exception{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
			
			List results = jdt.query("select s.initials intials,s.surname surname,s.novell_user_id novelluserId,b.eng_description COLLEGE,d.eng_description DEPT,d.code" +
					                  " from staff s, sun su, dpt d, colleg b "+
					                 "where su.code = '"+course+"' and su.mk_department_code = d.code and "+
					                 "s.persno = d.head_of_dept and su.in_use_flag = 'Y' and su.college_code = b.code",new CollegeDeptDetailRowMapper());
			
			Iterator i = results.iterator();
			if (i.hasNext()){
				collegeDeptDetails = (CollegeDeptDetails) i.next();
			}
			return collegeDeptDetails;
		}


		public String getCollegeChairUserId(String persno){
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			CollegeDeptDetails collegeDeptDetails = new CollegeDeptDetails();
			
			String sql = "select s.novell_user_id userid from staff s, gencod g where s.persno = g.afr_description and g.afr_description = '"+persno+"'" ;
			String userid = "";
		
			userid = querySingleValue(sql,"userid");
			
			return userid;	
			
		}
		
		public List getActingCod (String code) throws Exception {
		
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List results = jdt.query("select NOVELL_USER_CODE,INITIALS,SURNAME FROM STAFF, USRDPT where PERSONNEL_NO=PERSNO and TYPE='DPT' and MK_DEPARTMENT_CODE=?",					
						new Object[] { code }, new int[] { Types.VARCHAR },
					new StandinMapper());			
		
		
			return results;
		}
		
		public BookDetails getLastPoster(String year, String course) throws Exception {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			BookDetails  bookDetails = new BookDetails();
			List results = jdt.query("select b.title Title,b.author Author, b.year_of_publish Pyear,b.edition Edition,b.notes Bnotes,c.notes Cnotes FROM books1 b, crsbs1 c WHERE b.booknr = c.booknr and c.confirm = 1 and c.year = ? and c.book_status = 'P' and c.coursenr = ?",
								new Object[] { year,course},
								new int[] { Types.INTEGER },
										new BookRowMapper());
			Iterator i = results.iterator();
			if (i.hasNext()){
				bookDetails = (BookDetails) i.next();
			}
			return bookDetails;
		}

		public List getPrebooksList(String year, String course) throws Exception {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List results = jdt.query("select b.title Title,b.author Author, b.year_of_publish Pyear,s.name Sname,b.edition Edition,nvl(b.notes,' ') Bnotes,nvl(c.notes,' ') Cnotes FROM books1 b, crsbs1 c, suppl1 s WHERE b.booknr = c.booknr and c.confirm = 1 and c.year = ? and c.book_status = 'P' and c.coursenr = ? and b.suppliernr = s.suppliernr",
					new Object[] { year,course},
					new int[] { Types.VARCHAR,Types.VARCHAR },
							new BookRowMapper());
			return results;
		}		
		

		public void  updateAuditTrail(AuditTrail trail) {
			String insertSql = "INSERT INTO PBSAUD (MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS, ";
			       insertSql += "TRANSACTION_TIME,UPDATE_INFO) VALUES('"+trail.getNetworkId()+"',";
			       insertSql += "'"+trail.getStudyUnitCode()+"',";
			       insertSql += "'"+trail.getAcadYear()+"',";
			       insertSql += "'"+trail.getBookStatus()+"',";
			       insertSql += "systimestamp,'"+makeJDBCCompatible(trail.getUpdateInfo())+"')";
			      	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				jdt.execute(insertSql);	
		}
		
		public AuditTrail getLastUpdated (String subject,String acadyear, String typeofbooklist) throws Exception {
			AuditTrail auditTrail  = null;
			String sql = "SELECT MK_NETWORK_ID, MK_STUDY_UNIT_CODE, MK_ACADEMIC_YEAR, MK_BOOK_STATUS," +
					"to_char(transaction_time, 'YYYY-MM-DD HH24:MI:SS') transaction_time,UPDATE_INFO FROM PBSAUD WHERE MK_STUDY_UNIT_CODE = ? AND MK_ACADEMIC_YEAR= ? and MK_BOOK_STATUS= ? " +
					" ORDER BY  transaction_time DESC";
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			
			List auditTrailList = jdt.query(sql,new Object[] { subject,acadyear,typeofbooklist}, new int[] { Types.VARCHAR,Types.VARCHAR,Types.VARCHAR},
				new AuditTrailerMapper());
			if (auditTrailList.size() > 0) {
				 auditTrail = (AuditTrail) auditTrailList.get(0);
			}		
			return auditTrail;
		}
		public String getRequestedPerson(String subject,String acadyear, String typeofbooklist, String updateInfo) throws Exception {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql ="select MK_NETWORK_ID from PBSAUD where MK_STUDY_UNIT_CODE = '"+subject+"' and MK_ACADEMIC_YEAR= '"+acadyear+"' and MK_BOOK_STATUS= '"+typeofbooklist+"'"+
				" and update_info like '%"+updateInfo+"%'ORDER BY  transaction_time DESC ";
			String userCode;
			try{
				userCode = querySingleValue(sql,"MK_NETWORK_ID");
				} catch (Exception ex) {
				    throw new Exception("CourseDAO : getRequestedPerson Error occurred / "+ ex,ex);
				}
				return userCode;		
		    }
		
		public String getLastUpdatedPerBook(String subject,String acadyear, String typeofbooklist, String booknr) throws Exception {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			booknr = "%" +booknr+"%";
			String sql = "select to_char(transaction_time, 'YYYY-MM-DD') transaction_time" +
					" FROM PBSAUD WHERE MK_STUDY_UNIT_CODE = '"+subject+"' AND MK_ACADEMIC_YEAR= '"+acadyear+"'" +
					"and MK_BOOK_STATUS= '"+typeofbooklist+"' and UPDATE_INFO like '"+booknr+"' " +
					" ORDER BY  transaction_time DESC";
			String transactiontime;
			try{
			transactiontime = querySingleValue(sql,"transaction_time");
			} catch (Exception ex) {
			    throw new Exception("CourseDAO : getLastUpdatedPerBook Error occurred / "+ ex,ex);
			}
			
		return transactiontime;
		}
		
	
		public List getCourseList (String user,String academicYear) throws Exception {
			
			String sql = "SELECT distinct MK_STUDY_UNIT_CODE FROM usrsun WHERE upper(novell_user_id) = upper('"+user+"') and " +
					"mk_academic_year >= "+academicYear+" and access_level in ('SECDL','PRIML','CADMN') ORDER BY MK_STUDY_UNIT_CODE";
			List courses = null;
    		try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				courses = jdt.queryForList(sql);
			} catch (Exception ex) {
	            throw new Exception("CourseDAO : getCourseList Error occurred / "+ ex,ex);
			}
			return courses;
		}
		
		

		public List getUserId (String course,String academicYear) throws Exception {
			GregorianCalendar calCurrent = new GregorianCalendar();
			academicYear = Integer.toString(calCurrent.get(Calendar.YEAR));
			String sql = "SELECT novell_user_id FROM usrsun where Mk_study_unit_code = '"+course+"' and mk_academic_year >= "+academicYear+" " +
					"and access_level in ('SECDL','PRIML','CADMN') ORDER BY novell_user_id";
			List users = null;
	
			try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				users = jdt.queryForList(sql);
			} catch (Exception ex) {
	            throw new Exception("CourseDAO : getUserId Error occurred / "+ ex,ex);
			}
			return users;
		}
		
		
		public Integer insertBook(BookDetails bookDetails, String bookType)throws Exception{	
			
			String type = "";
			String yearOfpublisher = "0";
			
			if(bookType.equalsIgnoreCase("0")){
				yearOfpublisher = bookDetails.getTxtYear();;
			}
			else if(bookType.equalsIgnoreCase("Journal")){
				type = "J";
				yearOfpublisher = bookDetails.getTxtYear();
			}
			else if(bookType.equalsIgnoreCase("Law Report")){
				type = "L";
			}
			else if(bookType.equalsIgnoreCase("Book Chapter")){
				type = "B";
				yearOfpublisher = bookDetails.getTxtYear();
			}
			else
			{
				type = "";
			}
			
			Integer bookNr = null;
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());

			StringBuilder insertBook = new StringBuilder("insert into BOOKS1 (Booknr,Suppliernr,Title,Author,ISBN,ISBN1,ISBN2,ISBN3,Edition," +
					"Year_Of_Publish,Notes,Publisher,is_published,OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK," +
					"EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE)" );
			insertBook.append(" values(BOOKS1_SEQUENCE_NR.nextval,999999,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(insertBook.toString(),
					new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
							   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, 
							   Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR});

			PreparedStatementCreator psCreatorMessageInsert = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
					new Object[]{bookDetails.getTxtTitle(),bookDetails.getTxtAuthor(),
				       bookDetails.getTxtISBN(),bookDetails.getTxtISBN1(),bookDetails.getTxtISBN2(),
				       bookDetails.getTxtISBN3(),bookDetails.getTxtEdition(),yearOfpublisher,
				        bookDetails.getTxtBookNote(),bookDetails.getTxtPublisher(),bookDetails.getPublishStatus(),bookDetails.getOtherAuthor(),
				        bookDetails.getNoteToLibrary(),bookDetails.getAvailableAsEbook(),bookDetails.getEbookVolume(),bookDetails.getEbook_pages(),bookDetails.getUrl(),
				        bookDetails.getMasterCopy(),bookDetails.getMasterCopyFormat(),type});

			jdt.update(psCreatorMessageInsert);
			
			try{
				bookNr = new Integer(getBooknr());					
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			String sqlcrs = "insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
				"values('"+bookDetails.getCourse()+"',"+bookNr+",'"+bookDetails.getCourseLang()+"',"+"'"+bookDetails.getTypeOfBookList()+"',"+bookDetails.getAcadYear()+",1)";
			jdt.update(sqlcrs);
			
			 String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+bookDetails.getCourse()+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"' and MK_ACADEMIC_YEAR = "+bookDetails.getAcadYear();
				String course;
				try{
					course = querySingleValue(sqlcourse,"mk_study_unit_code");
					
				} catch (Exception ex) {
				    throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
				}
			 if(course.length()==0){
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseInsertPbcrsn.newPreparedStatementCreator(	 
						 new Object[]{bookDetails.getAcadYear(),bookDetails.getCourse(),"",0,bookDetails.getTypeOfBookList()});
				 jdt.update(stmt);
				
				/* String sqlpbcrsn = "insert into pbcrsn(MK_ACADEMIC_YEAR,MK_STUDY_UNIT_CODE,CRS_NOTES,STATUS,BOOK_STATUS) " +
				 		"values("+bookDetails.getAcadYear()+",'"+bookDetails.getCourse()+"',"+"' ',"+0+", '"+bookDetails.getTypeOfBookList()+"')";
					jdt.update(sqlpbcrsn);*/
			 }else{
				 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
						 new Object[]{ 0,course,bookDetails.getAcadYear(),bookDetails.getTypeOfBookList()});
				 jdt.update(stmt);				 
				
			 }
			 bookDetails = null;
			 return bookNr;
		}
		
		public Integer updateBook(BookDetails bookDetails) throws Exception{
			Integer bookNr = null;
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			BookDetails bDetails =new BookDetails();
			
			String eReserveType = " ";

			if(bookDetails.geteReserveType() != null && bookDetails.geteReserveType().equalsIgnoreCase("Journal"))
			{
				eReserveType = "J";
			}
			else if(bookDetails.geteReserveType() != null && bookDetails.geteReserveType().equalsIgnoreCase("Law Report"))
			{
				eReserveType = "L";
			}
			else if(bookDetails.geteReserveType() != null && bookDetails.geteReserveType().equalsIgnoreCase("Book Chapter"))
			{
				eReserveType = "B";
			}

			StringBuilder insertBook = new StringBuilder("insert into BOOKS1 (Booknr,Suppliernr,Title,Author,ISBN,ISBN1,ISBN2,ISBN3,Edition," +
					"Year_Of_Publish,Notes,Publisher,is_published,OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK," +
					"EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE)" );
			insertBook.append(" values(BOOKS1_SEQUENCE_NR.nextval,999999,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(insertBook.toString(),
					new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
							   Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, 
							   Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR, Types.VARCHAR});

			PreparedStatementCreator psCreatorMessageInsert = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
					new Object[]{bookDetails.getTxtTitle(),bookDetails.getTxtAuthor(),
				       bookDetails.getTxtISBN(),bookDetails.getTxtISBN1(),bookDetails.getTxtISBN2(),
				       bookDetails.getTxtISBN3(),bookDetails.getTxtEdition(),bookDetails.getTxtYear(),
				        bookDetails.getTxtBookNote(),bookDetails.getTxtPublisher(),bookDetails.getPublishStatus(),bookDetails.getOtherAuthor(),
				        bookDetails.getNoteToLibrary(),bookDetails.getAvailableAsEbook(),bookDetails.getEbookVolume(),bookDetails.getEbook_pages(),bookDetails.getUrl(),
				        bookDetails.getMasterCopy(),bookDetails.getMasterCopyFormat(),eReserveType});

			jdt.update(psCreatorMessageInsert);
			try{
				bookNr = new Integer(getBooknr());	
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			String sqlcrs = "insert into CRSBS1 (Coursenr,Booknr,Course_Language,book_status,Year,Confirm)"+
				"values('"+bookDetails.getCourse()+"',"+bookNr+",'"+bookDetails.getCourseLang()+"','"+bookDetails.getTypeOfBookList()+"',"+bookDetails.getAcadYear()+",1)";
			
			jdt.update(sqlcrs);
			

			String sqldelcrsbs = "delete from CRSBS1 where Coursenr = '"+bookDetails.getCourse()+"' and Booknr = "+bookDetails.getBookId()+" and book_status = '"+bookDetails.getTypeOfBookList()+"' and Year = "+bookDetails.getAcadYear();
			jdt.update(sqldelcrsbs);
			
			String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+bookDetails.getCourse()+"' and MK_ACADEMIC_YEAR = '"+bookDetails.getAcadYear()+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"'";
			String course;
			try{
				course = querySingleValue(sqlcourse,"mk_study_unit_code");
				
			} catch (Exception ex) {
			    throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
			}
		 if(course.length()==0){
			 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseInsertPbcrsn.newPreparedStatementCreator(	 
					 new Object[]{bookDetails.getAcadYear(),course,"",0,bookDetails.getTypeOfBookList()});
			 jdt.update(stmt);	
		
		 }else{
			 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
					 new Object[]{ 0,course,bookDetails.getAcadYear(),bookDetails.getTypeOfBookList()});
			 jdt.update(stmt);
		 }
			
			return bookNr;
		}		
		
		
	    private String makeJDBCCompatible(String convert) {
	     /*   String converted = null;
	        
	         if (convert.lastIndexOf("'") > -1) {
	            converted = convert.replaceAll("'","''");
	        }
	        else 
	            converted = convert;*/
	            
	        return convert;
	    }
	    
		public void linkBook(BookDetails bookDetails, String courseId, String acadYear)throws Exception{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			PreparedStatementCreator stmt2 = pstsmtCreatorFactoryCourseLink.newPreparedStatementCreator(
					new Object[]{courseId,bookDetails.getBookId(),bookDetails.getCourseLang(),bookDetails.getTypeOfBookList(),acadYear}
					);
		  jdt.update(stmt2);	
		  
		  String sqlcourse = "select mk_study_unit_code from pbcrsn where mk_study_unit_code = '"+courseId+"' and BOOK_STATUS= '"+bookDetails.getTypeOfBookList()+"' and MK_ACADEMIC_YEAR = "+acadYear;
			String course;
			try{
				course = querySingleValue(sqlcourse,"mk_study_unit_code");
				} catch (Exception ex) {
			    throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
			}
		 if(course.length()==0){
		
			 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseInsertPbcrsn.newPreparedStatementCreator(	 
					 new Object[]{Integer.parseInt(acadYear),courseId,"",0,bookDetails.getTypeOfBookList()});
			 jdt.update(stmt);	
			
		 }else{
			 PreparedStatementCreator  stmt = pstsmtCreatorFactoryCourseUpdatePbcrsn.newPreparedStatementCreator(	 
					 new Object[]{ 0,course,Integer.parseInt(acadYear),bookDetails.getTypeOfBookList()});
			 jdt.update(stmt);
		 }
			
		}

		public void deleteBook(BookDetails bookDetails,String course, String acadyear){
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql = "delete from CRSBS1 where Coursenr = '"+course+"' and Booknr = "+bookDetails.getBookId()+" and Year = "+acadyear;
			jdt.update(sql);
		}
		
		public void insertBookLink(String subject,String bookNr,String courseLang,String typeOfbooklist,String year){
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			PreparedStatementCreator stmt = pstsmtCreatorFactoryCourseLink.newPreparedStatementCreator(
					new Object[]{subject,bookNr,courseLang,typeOfbooklist,year}
					
					);
			jdt.update(stmt);
			
		}		
		
		public String getBooknr () throws Exception {
			
			String sql = "SELECT max(Booknr) BOOKNR from books1";
			String booknr = "";
			try{
				booknr = querySingleValue(sql,"BOOKNR");
			} catch (Exception ex) {
	            throw new Exception("CourseDAO : getBooknr Error occurred / "+ ex,ex);
			}
			return booknr;
		}
		
		public String getEditOption (String subject, String acadyear, String typeOfbook) throws Exception {
			
			String sqlEditOption = "select PRIORITY FROM books1 b, crsbs1 c "+
			"where b.booknr=c.booknr and  c.CourseNr = '"+subject+"' and c.year= "+acadyear+" and Book_status='"+typeOfbook+"' ORDER BY Upper(Title), Upper(Author), Edition asc";
        String editOption;
          try{
	      editOption = querySingleValue(sqlEditOption,"PRIORITY");				
         } catch (Exception ex) {
         throw new Exception("CourseDAO : insertBook Error occurred / "+ ex,ex);
         }
			return editOption;
		}
		
		public List getBookList (String subject, String acadyear, String typeOfbook) throws Exception {
			BookMenuForm bookMenuForm = new BookMenuForm();
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List results = jdt.query("SELECT b.Booknr,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language,b.notes,Confirm,Is_Published,AVAIL_AS_EBOOK,EBOOK_PAGES," +
					"EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY FROM books1 b, crsbs1 c "+
						"where b.booknr=c.booknr and  c.CourseNr = ? and c.year= "+acadyear+" and Book_status='"+typeOfbook+"' ORDER BY Upper(Title), Upper(Author), Edition asc",
						new Object[] { subject }, new int[] { Types.VARCHAR },
					new BookRowMapper());			
		
		
			return results;
		}
		
		public List getBookList(String code, String acadyear, String typeOfbook,String status) throws Exception {
			BookMenuForm bookMenuForm = new BookMenuForm();
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			ArrayList results = new ArrayList();
			List queryList;
			String sql  ="SELECT c.COURSENR coursenr,b.booknr booknr ,nvl(Title,' ') Title,nvl(Author,' ')Author, "+
                 "DECODE (edition, null, ' ', edition) edition, "+
                "DECODE (Year_of_publish, null, ' ', Year_of_publish)  Year_of_publish , "+ 
               "DECODE (isbn, null, ' ', isbn)  isbn,nvl(b.notes,' ') notes " +
			  "FROM books1 b, crsbs1 c , dpt d, pbcrsn p,sun s "+ 
			  "where b.booknr=c.booknr and d.code= "+code+" and c.year= "+acadyear+" and p.Book_status='"+typeOfbook+"' and p.STATUS="+status+" and "+
			  "p.mk_academic_year=c.year and s.code=p.mk_study_unit_code and p.mk_study_unit_code=c.coursenr  "+
			  "ORDER BY Upper(Title),Upper(Author), Edition asc ";
				 queryList = jdt.queryForList(sql);
			  Iterator i = queryList .iterator();
			  String prevCourse = "";
			  
				
		  while (i.hasNext())
		  {
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  BookDetails pbooklistDetails = new BookDetails();
				
			try{
				pbooklistDetails.setCourse(data.get("coursenr").toString());
				pbooklistDetails.setTxtTitle(data.get("Title").toString());
				pbooklistDetails.setTxtAuthor(data.get("Author").toString());
				pbooklistDetails.setTxtEdition(data.get("edition").toString());
				pbooklistDetails.setTxtYear(data.get("Year_of_publish").toString());			
				pbooklistDetails.setTxtBookNote(data.get("notes").toString());		
			    results.add(pbooklistDetails);
				}
		    catch(NullPointerException e ){e.printStackTrace();}		
		  }			  
	 return results;
	}
		
		public List getSearchedBookList (String title, String author,Integer academicYear, String subject, String typeOfbooklist) throws Exception {			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List results = null;
			Integer acadYear = academicYear - 1;
			String sql = "";
			if(typeOfbooklist.equals("P")){
				acadYear=academicYear-1;
			}else{
				acadYear = academicYear;
			}
			
			if (!title.equalsIgnoreCase("") && author.equalsIgnoreCase("")){
				title = "%" +title.toUpperCase()+"%";
				sql = "SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE" +
						" FROM books1 where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c " +
						"where b.booknr=c.booknr and UPPER(title) like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc";
				results = jdt.query(sql,
					new Object[] { title,subject,acadYear,typeOfbooklist}, new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
					new BookSearchRowMapper());
			} else if (title.equalsIgnoreCase("") && !author.equalsIgnoreCase("")){
				author = "%" +author.toUpperCase()+"%";
				results = jdt.query("SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE FROM books1 " +
						"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and UPPER(Author) " +
						"like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc",
						new Object[] { author,subject,acadYear,typeOfbooklist }, new int[] { Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
						new BookSearchRowMapper());
			} else {
				title = "%" +title.toUpperCase()+"%";
				author = "%" +author.toUpperCase()+"%";
				results = jdt.query("SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE FROM books1 " +
						"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and UPPER(Title) like ? and UPPER(Author) like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc",
						new Object[] { title,author,subject,acadYear,typeOfbooklist}, new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
						new BookSearchRowMapper());
			}
			return results;
		}	
		
		public List getSearchedEreserveBookList (String title, String author,Integer academicYear, String subject, String typeOfbooklist, String ereserveType) throws Exception {	
			
			
			//System.out.println("----:: "+title +" \n"+ author +" \n"+ academicYear +" \n"+ subject +" \n"+ typeOfbooklist +" \n"+ ereserveType);
			
			String ereserve = "";
			
			if(ereserveType != null && ereserveType.equalsIgnoreCase("Journal"))
			{
				ereserve = "J";
			}
			else if(ereserveType != null && ereserveType.equalsIgnoreCase("Law Report"))
			{
				ereserve = "L";
			}
			else if(ereserveType != null && ereserveType.equalsIgnoreCase("Book Chapter"))
			{
				ereserve = "B";
			}
			
			//System.out.println("finally out ::::: "+ereserve);
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List results = null;
			Integer acadYear = academicYear - 1;
			String sql = "";
			if(typeOfbooklist.equals("P")){
				acadYear=academicYear-1;
			}else{
				acadYear = academicYear;
			}
			
			if (!title.equalsIgnoreCase("") && author.equalsIgnoreCase("")){
				title = "%" +title.toUpperCase()+"%";
				sql = "SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE" +
						" FROM books1 where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c " +
						"where b.booknr=c.booknr and ERESERVE_TYPE= ? and Upper(Title) like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc";
				results = jdt.query(sql,
					new Object[] { ereserve,title,subject,acadYear,typeOfbooklist}, new int[] { Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR},
					new BookSearchRowMapper());
			} else if (title.equalsIgnoreCase("") && !author.equalsIgnoreCase("")){
				author = "%" +author.toUpperCase()+"%";
				results = jdt.query("SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE FROM books1 " +
						"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and ERESERVE_TYPE= ? and UPPER(Author) " +
						"like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ? ) ORDER BY Upper(Title), Upper(Author), Edition asc",
						new Object[] { ereserve,author,subject,acadYear,typeOfbooklist }, new int[] { Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
						new BookSearchRowMapper());
			} else {
				title = "%" +title.toUpperCase()+"%";
				author = "%" +author.toUpperCase()+"%";
				results = jdt.query("SELECT distinct Booknr,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,notes,Is_Published," +
						"OTHER_AUTHORS,NOTE_TO_LIBRARY,AVAIL_AS_EBOOK,EBOOK_VOLUME,EBOOK_PAGES,URL,MC_AVAIL,MC_FORMAT,ERESERVE_TYPE FROM books1 " +
						"where booknr in (select distinct c.booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and ERESERVE_TYPE= ? and Upper(Title) like ? and UPPER(Author) like ? and UPPER(coursenr) <> ? and year < ? and c.book_status= ?) ORDER BY Upper(Title), Upper(Author), Edition asc",
						new Object[] { ereserve,title,author,subject,acadYear,typeOfbooklist}, new int[] { Types.VARCHAR,Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR },
						new BookSearchRowMapper());
			}
			return results;
		}
		public String getCourseNote (String subject,String acadyear, String typeofBooklist) throws Exception {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			
			String courseNote = null;			
			String sql = "SELECT crs_notes FROM pbcrsn where Mk_study_unit_code = '"+subject+"' and mk_academic_year= "+acadyear+" and BOOK_STATUS = '"+typeofBooklist+"' ";
			List courseNoteList = jdt.queryForList(sql);
			if (courseNoteList.size() > 0) {
				courseNote = (String)jdt.queryForObject(sql, String.class);
			}
					
			return courseNote;
		}

		
		
		public void saveCourseNote (String studyUnit,String acadYear,String courseNote,String status, String typeofBooklist) throws Exception {

			
			//Check if course note exists for current academic year.
			String sql = "SELECT crs_notes FROM pbcrsn where Mk_study_unit_code = '"+studyUnit+"' and mk_academic_year= '"+acadYear+"' and BOOK_STATUS = '"+typeofBooklist+"' ";
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List courseNoteList = jdt.queryForList(sql);
			
			if (courseNoteList.size() > 0){
				String updateSql = "UPDATE pbcrsn SET crs_notes = '"+makeJDBCCompatible(courseNote)+"' WHERE " +
						"mk_study_unit_code = '"+studyUnit+"' and mk_academic_year= '"+acadYear+"' and book_status = '"+typeofBooklist+"'";
				jdt.execute(updateSql);

			}
			else if (courseNoteList.size() == 0){
				
				PreparedStatementCreator  stmt2 = pstsmtCreatorFactoryCourseNote.newPreparedStatementCreator(
						 new Object[]{acadYear,studyUnit,makeJDBCCompatible(courseNote),status,typeofBooklist}
						);
			
			jdt.update(stmt2);
				
			}
		}
		public List getBookCopyList (String subject, String acadyear,String typeOfbooklist) throws Exception {
			int tmpYear = Integer.parseInt(acadyear);
			int acadyearLess1= Integer.parseInt(acadyear)-1; 
			
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List results = jdt.query("SELECT distinct b.Booknr,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language,IS_PUBLISHED," +
						"CONFIRM,b.Notes,AVAIL_AS_EBOOK,EBOOK_PAGES,EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY FROM books1 b, crsbs1 c where b.booknr=c.booknr and  c.CourseNr = ? and c.year= ? and c.book_status= ? "+
						" and c.BOOKNR NOT IN (SELECT b.Booknr FROM books1 b, crsbs1 c where b.booknr=c.booknr and  c.CourseNr = ? and c.year= ? and c.book_status= ? ) " +
						"ORDER BY Upper(Title), Upper(Author), Edition asc",
					new Object[] { subject,acadyearLess1,typeOfbooklist, subject, tmpYear,typeOfbooklist }, new int[] { Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR},
					new BookRowMapper());
			return results;
		}

		 public BookDetails getBookDetailsInfo (String bookId) {
             JdbcTemplate jdt = new JdbcTemplate(getDataSource());
             BookDetails bookDetail = new BookDetails();
             List results = jdt.query("SELECT b.Booknr,nvl(ERESERVE_TYPE,' ') ERESERVE_TYPE,Title,Author,Edition,Year_of_publish,publisher,ISBN,ISBN1,ISBN2,ISBN3,Course_Language," +
             		"b.Notes,Confirm,Is_Published,AVAIL_AS_EBOOK,EBOOK_PAGES,EBOOK_VOLUME,URL,MC_AVAIL,MC_FORMAT,OTHER_AUTHORS,NOTE_TO_LIBRARY FROM books1 b, crsbs1 c where b.booknr=c.booknr and  b.Booknr = ?",
                             new Object[] { bookId },
                             new int[] { Types.INTEGER },
                             new BookRowMapper());
             Iterator i = results.iterator();
             if (i.hasNext()){
            	 bookDetail = (BookDetails) i.next();
             }
             return bookDetail;
     }
		 
	 //Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 10:Update Tutor Count
	 public int updateTutorCount( int tutorCount, int tutorsStatus, String courseId, String acadyear, String typeOfBookList ) throws Exception{
		 	int resultInsert = -1;
		 	
		 	try{
				JdbcTemplate jdt = new JdbcTemplate( getDataSource() );
	
				StringBuilder updateTutorSql = new StringBuilder( "UPDATE pbcrsn SET TUTOR_COUNT = ?, TUTOR_STATUS = ? " );
				updateTutorSql.append( "WHERE mk_study_unit_code = '"+courseId+"' AND mk_academic_year = '"+acadyear+"' "+
						"AND book_status = '"+typeOfBookList+"' " );
				
				PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(updateTutorSql.toString(),
						new int[] {Types.NUMERIC,Types.NUMERIC});
	
				PreparedStatementCreator psCreatorMessageUpdate = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
						new Object[]{tutorCount,tutorsStatus});
	
				resultInsert = jdt.update(psCreatorMessageUpdate);
		 	} catch ( Exception ex ) {
			    throw new Exception( "CourseDAO : updateTutorCount() Error occurred / "+ ex,ex );
		 	}
			
			return resultInsert;
	 }
	 
	 //Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 54:Update Tutor Status
	 public int updateTutorStatus( int tutorsStatus, String courseId, String acadyear, String typeOfBookList,
			 int status, int tutorCount ) throws Exception{
		 	int resultInsert = -1;
		 	
		 	try{
				JdbcTemplate jdt = new JdbcTemplate( getDataSource() );
	
				StringBuilder updateTutorSql = new StringBuilder( "UPDATE pbcrsn SET TUTOR_STATUS = ? " );
				updateTutorSql.append( "WHERE mk_study_unit_code = '"+courseId+"' AND mk_academic_year = '"+acadyear+"' "+
						"AND book_status = '"+typeOfBookList+"' AND status="+status+" AND tutor_count="+tutorCount );
				
				PreparedStatementCreatorFactory pstsmtCreatorFactoryMessage = new PreparedStatementCreatorFactory(updateTutorSql.toString(),
						new int[] {Types.NUMERIC});
	
				PreparedStatementCreator psCreatorMessageUpdate = pstsmtCreatorFactoryMessage.newPreparedStatementCreator(
						new Object[]{tutorsStatus});
	
				resultInsert = jdt.update( psCreatorMessageUpdate );
		 	} catch ( Exception ex ) {
			    throw new Exception( "CourseDAO : updateTutorStatus() Error occurred / "+ ex,ex );
		 	}
			
			return resultInsert;
	 }
	 
	 //Sifiso Changes: 2017/08/08:LU_MA_BLU_06:Requirement 4:Get Tutor Count for viewing
	 public String getTutorCount( String courseId, String acadyear, String typeOfBookList ) throws Exception{
		 	String resultSelect = "-1";
		 	
		 	try{	
				String sqlSelect  = "SELECT tutor_count FROM pbcrsn "+
										"WHERE mk_study_unit_code = '"+courseId+"' AND mk_academic_year = '"+acadyear+"' "+
											"AND book_status = '"+typeOfBookList+"' "+
											"AND tutor_count IS NOT NULL "+
											"AND tutor_status IS NOT NULL ";
				
				resultSelect = querySingleValue(sqlSelect,"tutor_count");
		 	} catch ( Exception ex ) {
			    throw new Exception( "CourseDAO : getTutorCount() Error occurred / "+ ex,ex );
		 	}
			
			return resultSelect;
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

		public boolean isInteger(String i)
		{
			try{
				Integer.parseInt(i);
				return true;
			}catch(NumberFormatException nfe)
			{
				return false;
			}
		}
	}


