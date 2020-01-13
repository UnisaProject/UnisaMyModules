package za.ac.unisa.lms.tools.satbook.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.sakaiproject.email.api.EmailService;
import org.springframework.jdbc.core.JdbcTemplate;

import Srsms02h.Abean.Srsms02sSendSmsToStudList;

import za.ac.unisa.lms.db.SakaiDAO;

import za.ac.unisa.lms.tools.satbook.dao.SatbookOracleDAO;
import za.ac.unisa.lms.tools.satbook.forms.AssistantRecord;
import za.ac.unisa.lms.tools.satbook.forms.AssistantTypeRecord;
import za.ac.unisa.lms.tools.satbook.forms.BookingForm;
import za.ac.unisa.lms.tools.satbook.forms.ClassroomForm;
import za.ac.unisa.lms.tools.satbook.forms.SatbookBkngMaterialRecord;
import za.ac.unisa.lms.tools.satbook.forms.SatbookDailyForm;

public class SatbookBookingDAO extends SakaiDAO {
	
	private EmailService emailService;

	public String saveBookingHeading(String actionStatus, String bkngId, String bkngHeading,
			String bkngLecturerNovellId, String bkngLectTel, String bkngStart, String bkngEnd,
			String bkngDesc, String rebroadcast,String rebroadDate,String createdBy, String bkngType, 
			String systemId) throws Exception{

		/* TABLE SATBOOK_BKNG_MAIN
		 * | bkng_id            | int(10) unsigned |      | PRI | NULL                | auto_increment |
| Bkng_heading       | varchar(30)      |      |     |                     |                |
| Bkng_lect_NovellId | varchar(99)      |      | MUL |                     |                |
| Bkng_lect_tel      | varchar(30)      |      |     |                     |                |
| Bkng_start         | timestamp        | YES  |     | CURRENT_TIMESTAMP   |                |
| Bkng_end           | timestamp        | YES  |     | 0000-00-00 00:00:00 |                |
| Bkng_desc          | varchar(255)     |      |     |                     |                |
| Bkng_confirmed     | char(1)          | YES  |     |                     |                |
| Broadcast_error    | text             | YES  |     | NULL                |                |
		 */
		
		BookingForm tmpForm = new BookingForm();
		String tmpRebroadcast = "N";
		if (systemId.equals(tmpForm.getSatbook())) {
			if (rebroadcast.equals("true")) {
				tmpRebroadcast = "Y";
			}
		}
		
		if (systemId.equals("2")) {
			tmpRebroadcast= "N";
		}

		if ((bkngLecturerNovellId == null)||(bkngLecturerNovellId.length()==0)) {
			bkngLecturerNovellId = createdBy.toUpperCase();
		} 
		bkngLecturerNovellId = bkngLecturerNovellId.toUpperCase();
		if (actionStatus.equals("EDIT")) {
			// update existing booking record
			String sql1 = "UPDATE SATBOOK_BKNG_MAIN " +
			              "SET    BKNG_HEADING = '"+bkngHeading+"', "+
			              "       BKNG_LECT_NOVELLID = '"+bkngLecturerNovellId+"', "+
			              "       BKNG_START = TO_DATE('"+bkngStart+"','YYYY-MM-DD HH24:MI'), "+
			              "       BKNG_END = TO_DATE('"+bkngEnd+"','YYYY-MM-DD HH24:MI'), "+
			              "       BKNG_DESC = '"+bkngDesc+"', "+
			              "       BROADCAST_ERROR = ' ', "+
			              "       BKNG_REBROADCAST = '"+tmpRebroadcast+"'";
			              
			if (tmpRebroadcast.equals("Y")) {
				sql1 = sql1+" ,       BKNG_REBROADCASTDATE = TO_DATE('"+rebroadDate+"','DD-MM-YYYY') ";
			}
			
			sql1 = sql1 + " WHERE  BKNG_ID = "+bkngId+" ";
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql1);
			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: saveBookingHeading (EDIT:update): Error occurred / "+ ex,ex);
			}

		} else {

			// select unique booking id
			String sql1 = "SELECT NVL(max(BKNG_ID)+1,1) as A "+
						  "FROM   SATBOOK_BKNG_MAIN ";

			try{
				bkngId = this.querySingleValue(sql1,"A");

			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: saveBookingHeading (SELECT unique bkng_id): Error occurred / "+ ex,ex);
			}

			// 	insert new booking
			String sql2="";
			
			if (systemId.equals(tmpForm.getSatbook())) {

				// if rebroadcast
				if (tmpRebroadcast.equalsIgnoreCase("Y")){
					sql2 = "INSERT INTO SATBOOK_BKNG_MAIN(BKNG_ID,BKNG_HEADING,BKNG_LECT_NOVELLID,BKNG_START,BKNG_END,"+
						" BKNG_DESC,BKNG_CONFIRMED,BROADCAST_ERROR, BKNG_REBROADCAST,BKNG_REBROADCASTDATE,BKNG_CREATEDBY,BKNG_CREATEDDATE)"+
								" VALUES ("+bkngId+",'"+bkngHeading+"','"+bkngLecturerNovellId+"',TO_DATE('"+bkngStart+"','YYYY-MM-DD HH24:MI'),"+
								" TO_DATE('"+bkngEnd+"','YYYY-MM-DD HH24:MI'),'"+bkngDesc+"','N',' ','"+tmpRebroadcast+"',TO_DATE('"+rebroadDate+"'," +
								" 'DD-MM-YYYY'),'"+createdBy+"',SYSTIMESTAMP)";
				} else {
					sql2 = "INSERT INTO SATBOOK_BKNG_MAIN(BKNG_ID,BKNG_HEADING,BKNG_LECT_NOVELLID,BKNG_START,BKNG_END,"+
							" BKNG_DESC,BKNG_CONFIRMED,BROADCAST_ERROR,BKNG_REBROADCAST,BKNG_CREATEDBY,BKNG_CREATEDDATE)"+
							" VALUES ("+bkngId+",'"+bkngHeading+"','"+bkngLecturerNovellId+"',TO_DATE('"+bkngStart+"','YYYY-MM-DD HH24:MI'),"+
							" TO_DATE('"+bkngEnd+"','YYYY-MM-DD HH24:MI'),'"+bkngDesc+"','N',' ','"+tmpRebroadcast+"','"+createdBy+"',SYSTIMESTAMP)";				
				}
			} else if (systemId.equals(tmpForm.getVenbook())) {
				sql2 = "INSERT INTO SATBOOK_BKNG_MAIN(BKNG_ID,BKNG_HEADING,BKNG_LECT_NOVELLID,BKNG_START,BKNG_END,"+
						" BKNG_DESC,BKNG_CONFIRMED,BROADCAST_ERROR,BKNG_CREATEDBY,BKNG_CREATEDDATE," +
						" BKNGTYPE_ID, SYSTEM_ID)"+
						" VALUES ("+bkngId+",'"+bkngHeading+"','"+createdBy+"',TO_DATE('"+bkngStart+"','YYYY-MM-DD HH24:MI'),"+
						" TO_DATE('"+bkngEnd+"','YYYY-MM-DD HH24:MI'),'"+bkngDesc+"','N',' ','"+createdBy+"',SYSTIMESTAMP, " +
						bkngType+","+systemId+")";
			}
			
			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql2,new Object[] {});
			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: saveBookingHeading (Insert): Error occurred / "+ ex,ex);
			}
		}

		return bkngId;
	}
	public String verifyRebroadcast(String rebroadDate, String systemID)throws Exception{
		String date;
		String sql="SELECT BKNG_START  " +
				" FROM  SATBOOK_BKNG_MAIN " +
				" WHERE TO_CHAR( BKNG_START ,'DD-MM-YYYY')='"+rebroadDate+"'" +
				" AND   SYSTEM_ID = "+systemID; 
		
		try{
			date = querySingleValue(sql,"BKNG_START");
			if(date.length()==0){
				date="";
			}
		} catch (Exception ex) {
		    throw new Exception("SatbookBookingDAO : verifyRebroadcast Error occurred / "+ ex,ex);
		}
		return date;
	}
	
	public String saveBookingHeadingTechError(String bkngId, String bkngError) throws Exception{

		// update existing Lecturer record
		String sql1 = "UPDATE SATBOOK_BKNG_MAIN " +
		              "SET    BROADCAST_ERROR = '"+bkngError+"' "+
		              "WHERE  BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: saveBookingHeadingTechError: Error occurred / "+ ex,ex);
		}

		return bkngId;
	}

	public void saveBookingSubjects(String actionStatus, String bkngId, ArrayList subjectList,
			String bkngPeriod, String bkngYear) throws Exception{

		/* TABLE SATBOOK_BKNG_SUBJECT
		 *
+-------------+------------------+------+-----+---------+-------+
| Field       | Type             | Null | Key | Default | Extra |
+-------------+------------------+------+-----+---------+-------+
| bkng_id     | int(10) unsigned |      | PRI | 0       |       |
| Subj_code   | varchar(7)       |      | PRI |         |       |
| Subj_period | int(2) unsigned  |      |     | 0       |       |
| Subj_year   | int(4) unsigned  |      |     | 0       |       |
+-------------+------------------+------+-----+---------+-------+
		 */

		SatbookOracleDAO db2 = new SatbookOracleDAO();

		// delete all subjects linked to the booking and re-insert selected subjects.
		String sql1 = "DELETE FROM SATBOOK_BKNG_SUBJECT " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: saveBookingSubjects (Delete): Error occurred /"+ ex,ex);
		}

		LabelValueBean tmpSubjCode;
		for (int i=0; i < subjectList.size(); i++) {
			tmpSubjCode = (LabelValueBean) subjectList.get(i);

			insertSatbookBkngSubject(bkngId,tmpSubjCode.getValue(),bkngPeriod,bkngYear);

			// now add the linked subjects
			ArrayList tmpLinkedSubjList = new ArrayList();

			tmpLinkedSubjList = db2.selectLinkedSubjects(tmpSubjCode.getValue(),bkngPeriod,bkngYear);

			for (int j=0; j < tmpLinkedSubjList.size(); j++) {
				LabelValueBean tmpLinkedSubjCode = (LabelValueBean) tmpLinkedSubjList.get(j);
				insertSatbookBkngSubject(bkngId,tmpLinkedSubjCode.getValue(),bkngPeriod,bkngYear);
			}
		}
	}

	public void saveBookingMaterials(String actionStatus, String bkngId, ArrayList materialList) throws Exception{

		/* TABLE  SATBOOK_BKNG_MATERIAL
		 *
| Field      | Type             | Null | Key | Default             | Extra |
+------------+------------------+------+-----+---------------------+-------+
| bkng_id    | int(10) unsigned |      | PRI | 0                   |       |
| mat_id     | int(2) unsigned  |      | PRI | 0                   |       |
| Cassette   | int(10) unsigned | YES  |     | 0                   |       |
| Time_in    | timestamp        | YES  |     | CURRENT_TIMESTAMP   |       |
| Time_inff  | char(2)          | YES  |     | NULL                |       |
| Time_out   | timestamp        | YES  |     | 0000-00-00 00:00:00 |       |
| Time_outff | char(2)          | YES  |     | NULL                |       |
| Duration   | timestamp        | YES  |     | 0000-00-00 00:00:00 |       |
| Dur_ff     | char(2)          | YES  |     | NULL                |       |
+------------+------------------+------+-----+---------------------+-------+
		 */
		
		//	 Delete all materials and then re-enter newly added materials.
		deleteMaterials(bkngId);
		
		LabelValueBean tmpMaterial;

		for (int i=0; i < materialList.size(); i++) {
			tmpMaterial = (LabelValueBean) materialList.get(i);
			SatbookBkngMaterialRecord bkngMaterial = new SatbookBkngMaterialRecord();

			// Does material already exist for booking
			bkngMaterial = selectBkngMaterial(bkngId, tmpMaterial.getLabel());

			if (bkngMaterial.getBkngId() == null) {
				// add new material record
				insertSatbookBkngMaterial(bkngId,tmpMaterial.getLabel(),"0");
			} else if (bkngMaterial.getBkngId().length() > 0) {
				// update material record
				updateSatbookBkngMaterial(bkngMaterial);
			}
		}
	}

	//copy code
	
/**	public void saveBookingMaterials(String actionStatus, String bkngId, ArrayList materialList) throws Exception{

		/* TABLE  SATBOOK_BKNG_CLASSROOM
		 *
+-----------+------------------+------+-----+---------+-------+
| Field     | Type             | Null | Key | Default | Extra |
+-----------+------------------+------+-----+---------+-------+
| bkng_id   | int(10) unsigned |      | PRI | 0       |       |
| region_id | int(2) unsigned  |      | PRI | 0       |       |
+-----------+------------------+------+-----+---------+-------+
		 */

		//		 Delete all materials and then re-enter newly added materials.
/**		deleteMaterials(bkngId);

		LabelValueBean tmpMaterial;

		for (int i=0; i < materialList.size(); i++) {
			tmpMaterial = (LabelValueBean) materialList.get(i);
			SatbookBkngMaterialRecord bkngMaterial = new SatbookBkngMaterialRecord();
			
			// Does material already exist for booking
			bkngMaterial = selectBkngMaterial(bkngId, tmpMaterial.getLabel());

			if (bkngMaterial.getBkngId() == null) {
				// add new material record
				insertSatbookBkngMaterial(bkngId,tmpMaterial.getLabel(),"0");
			} else if (bkngMaterial.getBkngId().length() > 0) {
				// update material record
				updateSatbookBkngMaterial(bkngMaterial);
			}
		}
	}*/

	public SatbookBkngMaterialRecord selectBkngMaterial(String bkngId, String materialId) throws Exception {

		SatbookBkngMaterialRecord bkngMaterial = new SatbookBkngMaterialRecord();

		String select = "SELECT BKNG_ID, MAT_ID, CASSETTE "+
        			    "FROM   SATBOOK_BKNG_MATERIAL "+
        			    "WHERE  BKNG_ID = "+bkngId+" "+
        			    "AND    MAT_ID ="+materialId;

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			bkngMaterial.setBkngId(bkngId);
    			bkngMaterial.setMaterialId(materialId);
    			bkngMaterial.setCassette(data.get("CASSETTE").toString());

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngMaterial: Error occurred / "+ ex,ex);
    	} // end try

		return bkngMaterial;
	}

	private void insertSatbookBkngMaterial(String bkngId, String matId, String cassette) throws Exception{

		String sql2 = "INSERT INTO SATBOOK_BKNG_MATERIAL(BKNG_ID, MAT_ID, CASSETTE)"+
					"VALUES (?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2,new Object[] {bkngId, matId, cassette});
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: insertSatbookBkngMaterial (Insert): Error occurred / "+ ex,ex);
		}
	}

	private void updateSatbookBkngMaterial(SatbookBkngMaterialRecord bkngMaterial) throws Exception{

		String sql2 = "UPDATE SATBOOK_BKNG_MATERIAL " +
        			  "SET    CASSETTE = "+bkngMaterial.getCassette()+" "+
        			  "WHERE  BKNG_ID = "+bkngMaterial.getBkngId()+" "+
        			  "AND    MAT_ID = "+bkngMaterial.getMaterialId();

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: updateSatbookBkngMaterial (EDIT:update): Error occurred / "+ ex,ex);
		}
	}

	public void updateSatbookBkngMaterialAdmin(SatbookBkngMaterialRecord bkngMaterial) throws Exception{

		/* Sonette 8 Nov remove:
		 * 		  "       DURATION = TO_DATE('"+bkngMaterial.getDuration()+"','YYYY-MM-DD HH24:MI:SS'), "+
		  "       DUR_FF = '"+bkngMaterial.getDurationFF()+"', "+
		  because duration is calculated every time, no need to strore in db
		 */
		String sql2 = "UPDATE SATBOOK_BKNG_MATERIAL " +
		  "SET    CASSETTE = "+bkngMaterial.getCassette()+", "+
		  "       TIME_IN = TO_DATE('"+bkngMaterial.getTimeIn()+"','YYYY-MM-DD HH24:MI:SS'), "+
		  "       TIME_OUT = TO_DATE('"+bkngMaterial.getTimeOut()+"','YYYY-MM-DD HH24:MI:SS'), "+
		  "       TIME_INFF = '"+bkngMaterial.getTimeInFF()+"', "+
		  "       TIME_OUTFF = '"+bkngMaterial.getTimeOutFF()+"', "+
		  "       DUPLICATIONS = "+bkngMaterial.getDuplication()+" "+
		  "WHERE  BKNG_ID = "+bkngMaterial.getBkngId()+" "+
		  "AND    MAT_ID = "+bkngMaterial.getMaterialId();

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: updateSatbookBkngMaterialAdmin (EDIT:update): Error occurred / "+ ex,ex);
		}
	}

	private void insertSatbookBkngSubject(String bkngId, String subjCode, String bkngPeriod, String bkngYear) throws Exception{

		String sql2 = "INSERT INTO SATBOOK_BKNG_SUBJECT(BKNG_ID, SUBJ_CODE, SUBJ_PERIOD, SUBJ_YEAR)"+
		"VALUES (?,?,?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2,new Object[] {bkngId,subjCode,bkngPeriod,bkngYear});
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: insertSatbookBkngSubject (Insert): Error occurred / "+ ex,ex);
		}
	}

	public void saveBookingVisitors(String actionStatus, String bkngId, ArrayList visitorList) throws Exception{

		/* TABLE  SATBOOK_BKNG_VISITORS
		 *
+---------+------------------+------+-----+---------+-------+
| Field   | Type             | Null | Key | Default | Extra |
+---------+------------------+------+-----+---------+-------+
| bkng_id | int(10) unsigned |      | PRI | 0       |       |
| Visitor | varchar(40)      |      | PRI |         |       |
+---------+------------------+------+-----+---------+-------+
		 */

		//		 Delete all visitors and then re-enter newly added visitors.
		deleteVisitors(bkngId);

		LabelValueBean tmpVisitor;

		for (int i=0; i < visitorList.size(); i++) {
			tmpVisitor = (LabelValueBean) visitorList.get(i);
			SatbookBkngMaterialRecord bkngMaterial = new SatbookBkngMaterialRecord();

			insertVisitor(bkngId,tmpVisitor.getLabel());
		}
	}

	private void deleteVisitors(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_VISITORS " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteVisitors (Delete): Error occurred /"+ ex,ex);
		}

	}

	private void deleteSubjects(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_SUBJECT " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteSubjects (Delete): Error occurred /"+ ex,ex);
		}

	}

	private void insertVisitor(String bkngId, String visitor) throws Exception{

		String sql2 = "INSERT INTO SATBOOK_BKNG_VISITORS(BKNG_ID, VISITOR)"+
					"VALUES (?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2,new Object[] {bkngId, visitor});
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: insertVisitor (Insert): Error occurred / "+ ex,ex);
		}
	}

	public void saveBookingClassrooms(String actionStatus, String bkngId, ArrayList classroomList) throws Exception{

		/* TABLE  SATBOOK_BKNG_CLASSROOM
		 *
+-----------+------------------+------+-----+---------+-------+
| Field     | Type             | Null | Key | Default | Extra |
+-----------+------------------+------+-----+---------+-------+
| bkng_id   | int(10) unsigned |      | PRI | 0       |       |
| region_id | int(2) unsigned  |      | PRI | 0       |       |
+-----------+------------------+------+-----+---------+-------+
		 */

		//		 Delete all visitors and then re-enter newly added visitors.
		deleteClassroom(bkngId);

		LabelValueBean tmpClassroom;

		for (int i=0; i < classroomList.size(); i++) {
			tmpClassroom = (LabelValueBean) classroomList.get(i);
			SatbookBkngMaterialRecord bkngMaterial = new SatbookBkngMaterialRecord();

			insertClassroom(bkngId,tmpClassroom.getLabel());
		}
	}

	private void deleteClassroom(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_CLASSROOM " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteClassrooms (Delete): Error occurred /"+ ex,ex);
		}
	}

	private void deleteMaterials(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_MATERIAL " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteMaterials (Delete): Error occurred /"+ ex,ex);
		}
	}

	private void deleteBkngMain(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_MAIN " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteBkngMain (Delete): Error occurred /"+ ex,ex);
		}
	}

	private void insertClassroom(String bkngId, String classroom) throws Exception{

		String sql2 = "INSERT INTO SATBOOK_BKNG_CLASSROOM(BKNG_ID, REGION_ID)"+
					"VALUES (?,?)";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2,new Object[] {bkngId, classroom});
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: insertClassroom (Insert): Error occurred / "+ ex,ex);
		}
	}

	// dates in format YYYY-MM-DD
	public ArrayList selectBookingList(String startDate, String endDate,String bkngType, String systemID) throws Exception {
		ArrayList bookingList = new ArrayList();
		SatbookOracleDAO db2 = new SatbookOracleDAO();

		/*
		String[] temp = null;
		temp = startDate.split("-");
		startDate = temp[2]+"-"+temp[1]+"-"+temp[0];
		*/

		String select = "SELECT A.BKNG_ID, A.BKNG_HEADING, A.BKNG_LECT_NOVELLID," +
				        "A.BKNG_LECT_TEL," +
				        "TO_CHAR(A.BKNG_START,'YYYY-MM-DD') AS STARTTIME, "+
				        "TO_CHAR(A.BKNG_START, 'HH24') AS STARTTIMEHH," +
				        "TO_CHAR(A.BKNG_START, 'MI') AS STARTTIMEMM," +
				        "TO_CHAR(A.BKNG_END,'YYYY-MM-DD') AS ENDTIME, "+
				        "TO_CHAR(A.BKNG_END, 'HH24') AS ENDTIMEHH," +
				        "TO_CHAR(A.BKNG_END, 'MI') AS ENDTIMEMM," +
				        "NVL(TO_CHAR(A.BKNG_REBROADCASTDATE,'DD-MM-YYYY'),' ') AS REBROADTIME, "+
				        "A.BKNG_DESC,A.BKNG_CONFIRMED, NVL(A.BKNG_REBROADCAST,'N') as REBROADCAST, "+
				        "NVL( TO_CHAR(A.BKNG_CREATEDDATE,'YYYY-MM-DD HH24:MI'),' ') AS CREATEDDATE, "+
				        "B.TYPE_NAME AS TYPENAME "+
		                "FROM SATBOOK_BKNG_MAIN A, SATBOOK_BKNG_TYPES B" +
		                " WHERE  A.SYSTEM_ID = "+systemID+
		                " AND   A.BKNGTYPE_ID = B.TYPE_ID (+)";

		if (endDate != null) {
			select = select+
					" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') >= '"+startDate+"' "+
					" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') <= '"+endDate+"' ";
		} else {
			select = select+
					" AND  TO_CHAR(A.BKNG_START,'YYYY-MM-DD') = '"+startDate+"' ";
		}

		if (bkngType.equals("Y")) {
			select = select+
					" AND    A.BKNG_CONFIRMED = 'Y' ";
		}
		if (bkngType.equals("N")) {
			select = select+
					"AND    A.BKNG_CONFIRMED = 'N' ";
		}

		select = select+
				 " ORDER BY A.BKNG_START ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			BookingForm booking = new BookingForm();
    			booking.setBkngId(data.get("BKNG_ID").toString());
    			booking.setHeading(data.get("BKNG_HEADING").toString());
    			booking.setLecturerNovellId(data.get("BKNG_LECT_NOVELLID").toString());
    			booking.setStartDate(data.get("STARTTIME").toString());
    			booking.setStartHH(data.get("STARTTIMEHH").toString());
    			booking.setStartMM(data.get("STARTTIMEMM").toString());
    			booking.setEndDate(data.get("ENDTIME").toString());
    			booking.setEndHH(data.get("ENDTIMEHH").toString());
    			booking.setEndMM(data.get("ENDTIMEMM").toString());
    			booking.setBkngConfirmed(data.get("BKNG_CONFIRMED").toString());
    			booking.setDescription(data.get("BKNG_DESC").toString());
    			booking.setDuration(calculateBookingDuration(booking.getStartHH(),booking.getEndHH(),
    					booking.getStartMM(),booking.getEndMM()));
      			String tmp1 = data.get("REBROADCAST").toString();
      			String rbdate = data.get("REBROADTIME").toString();
      			booking.setCreatedDate(data.get("CREATEDDATE").toString());
      			if (tmp1.equalsIgnoreCase("Y")) {
    				booking.setRebroadcast("true");
    				booking.setRebroadDate(rbdate);
    			} else {
    				booking.setRebroadcast("false");
    			}
    			String tmp = db2.lecturerExistStaff(booking.getLecturerNovellId());
    			if (tmp.equals("")) {
    				tmp = db2.lecturerExistUsr(booking.getLecturerNovellId());
    			}
    			String[] tmp2 = null;
    			tmp2 = tmp.split("#");
    			booking.setLecturerName(tmp2[0]);
    			booking.setDpt(db2.selectUserDpt(booking.getLecturerNovellId()));
    			
    			SatbookDailyForm dailyForm = new SatbookDailyForm();
    			// FOR VENBOOK
    			if (systemID.equals(dailyForm.getVenbook())) {
    				// select bkng type description
    				booking.setBkngTypeName(data.get("TYPENAME").toString());
    				
        			// select venues linked to the booking
        			selectBkngClassroomDetial(booking);
    			}
    			bookingList.add(booking);

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBookingList: Error occurred / "+ ex,ex);
    	} // end try

		return bookingList;
	}
	
	public ArrayList selectBookingListXtypePerVenue(String startDate, String endDate,String bkngType, String systemID, String XType) throws Exception {
		ArrayList venueList = new ArrayList();
		SatbookOracleDAO db2 = new SatbookOracleDAO();
		String select = "";

		if (XType.equals("DAYS")) {

			select = "SELECT to_char(A.bkng_start,'MON-YYYY') AS MONTH," +
					        "       B.region_id AS VENID,C.classroom_name AS VENUE, " +
					        "       count(distinct to_char(A.bkng_start,'DD-MM-YYYY')) AS NR "+
					        " from satbook_bkng_main A, satbook_bkng_classroom B, satbook_classrooms C"+
					        " where  A.bkng_id = B.bkng_id"+
					        " and    A.system_id = "+systemID+
					        " and    B.region_id = C.classroom_id ";
					if (endDate != null) {
						select = select+
								" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') >= '"+startDate+"' "+
								" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') <= '"+endDate+"' ";
					} else {
						select = select+
								" AND  TO_CHAR(A.BKNG_START,'YYYY-MM-DD') = '"+startDate+"' ";
					}
			
					if (bkngType.equals("Y")) {
						select = select+
								" AND    A.BKNG_CONFIRMED = 'Y' ";
					}
					if (bkngType.equals("N")) {
						select = select+
								"AND    A.BKNG_CONFIRMED = 'N' ";
					}
	
					select = select+
						" GROUP BY to_char(A.bkng_start,'MON-YYYY'), B.region_id, C.classroom_name  "+
						" order by to_char(A.bkng_start,'MON-YYYY'),C.classroom_name asc";
		} else {
			// if XType = HOURS
			select = "SELECT to_char(A.bkng_start,'MON-YYYY') AS MONTH," +
			        "       B.region_id AS VENID,C.classroom_name AS VENUE, " +
			        "       sum(TRUNC(MOD((bkng_end- bkng_start)*24,24))) AS NR "+
			        " from satbook_bkng_main A, satbook_bkng_classroom B, satbook_classrooms C"+
			        " where  A.bkng_id = B.bkng_id"+
			        " and    A.system_id = "+systemID+
			        " and    B.region_id = C.classroom_id ";
			if (endDate != null) {
				select = select+
						" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') >= '"+startDate+"' "+
						" AND    TO_CHAR(A.BKNG_START,'YYYY-MM-DD') <= '"+endDate+"' ";
			} else {
				select = select+
						" AND  TO_CHAR(A.BKNG_START,'YYYY-MM-DD') = '"+startDate+"' ";
			}
		
			if (bkngType.equals("Y")) {
				select = select+
						" AND    A.BKNG_CONFIRMED = 'Y' ";
			}
			if (bkngType.equals("N")) {
				select = select+
						"AND    A.BKNG_CONFIRMED = 'N' ";
			}
		
			select = select+
				" GROUP BY to_char(A.bkng_start,'MON-YYYY'), B.region_id, C.classroom_name  "+
				" order by to_char(A.bkng_start,'MON-YYYY'),C.classroom_name asc";
		}
		System.out.println("select: "+select);

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			ClassroomForm venue = new ClassroomForm();
    			venue.setMonth(data.get("MONTH").toString());
    			venue.setRegionDesc(data.get("VENUE").toString());
    			venue.setNumberOfDays(data.get("NR").toString());

    			venueList.add(venue);

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBookingListDaysPerVenue: Error occurred / "+ ex,ex);
    	} // end try

    	System.out.println("Venuelist = "+venueList.size());
		return venueList;
	}

	
	public void selectAllBkngDetail(BookingForm booking) throws Exception {

		SatbookOracleDAO db = new SatbookOracleDAO();
		
		// Select main booking detail
		booking = selectBkngMainDetail(booking);
		
		// Select lecturer detail
		String lectureContact = db.getlecturerContactDetails(booking.getLecturerNovellId());
		String[] temp = null;
		temp = lectureContact.split("#");
		booking.setLecturerName(temp[0]);
		booking.setLecturerNum1(temp[1]);
		booking.setEmail(temp[2]);
		
		// select subject detail
		booking = selectBkngSubjectDetial(booking);

		// select material detail
		booking = selectBkngMaterialDetial(booking);

		// select visitor details
		booking = selectBkngVisitorDetial(booking);
		
		// select classroom details
		booking = selectBkngClassroomDetial(booking);

	}

	public BookingForm selectBkngMainDetail(BookingForm bookingIn) throws Exception {

		BookingForm booking = (BookingForm)bookingIn;

		String select = "SELECT BKNG_ID, BKNG_HEADING, BKNG_LECT_NOVELLID," +
				         "TO_CHAR(BKNG_START,'YYYY-MM-DD') AS STARTTIME, "+
				         "TO_CHAR(BKNG_START, 'HH24') AS STARTTIMEHH," +
				         "TO_CHAR(BKNG_START, 'MI') AS STARTTIMEMM," +
				         "TO_CHAR(BKNG_END,'YYYY-MM-DD') AS ENDTIME, "+
				         "TO_CHAR(BKNG_END, 'HH24') AS ENDTIMEHH, " +
				         "TO_CHAR(BKNG_END, 'MI') AS ENDTIMEMM, " +
				         "BKNG_DESC,BKNG_CONFIRMED, Broadcast_error, NVL(BKNG_REBROADCAST,'N') AS REBROADCAST,"+
				         "NVL(TO_CHAR(BKNG_REBROADCASTDATE,'DD-MM-YYYY'),' ') AS REBROADDATE , " +
				         "NVL( TO_CHAR(BKNG_CREATEDDATE,'YYYY-MM-DD HH24:MI'),' ') AS CREATEDDATE, "+
				         "NVL(BKNG_CREATEDBY, ' ') AS CREATEDBY, NVL(BKNGTYPE_ID,'0') as BKNGTYPE, " +
				         "NVL(SATBOOK_BKNG_TYPES.TYPE_NAME,' ') AS TYPENAME "+
		                " FROM   SATBOOK_BKNG_MAIN, SATBOOK_BKNG_TYPES "+
		                "WHERE  BKNG_ID = '"+booking.getBkngId()+"' " +
		                "AND    SATBOOK_BKNG_MAIN.BKNGTYPE_ID = SATBOOK_BKNG_TYPES.TYPE_ID (+)";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List classInfoList = jdt.queryForList(select);
			Iterator j = classInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			//booking.setBkngId(bkngId);
    			booking.setHeading(data.get("BKNG_HEADING").toString());
    			booking.setStartDate(data.get("STARTTIME").toString());
    			booking.setStartHH(data.get("STARTTIMEHH").toString());
    			booking.setStartMM(data.get("STARTTIMEMM").toString());
    			booking.setEndDate(data.get("ENDTIME").toString());
    			booking.setEndHH(data.get("ENDTIMEHH").toString());
    			booking.setEndMM(data.get("ENDTIMEMM").toString());
    			booking.setBkngConfirmed(data.get("BKNG_CONFIRMED").toString());
    			booking.setLecturerNovellId(data.get("BKNG_LECT_NOVELLID").toString());
    			booking.setDescription(data.get("BKNG_DESC").toString());
    			booking.setTechnicalError(data.get("Broadcast_error").toString());
    			String tmp = data.get("REBROADCAST").toString();
    			booking.setCreatedBy(data.get("CREATEDBY").toString());
    			if (tmp.equalsIgnoreCase("Y")) {
    				booking.setRebroadcast("true");
    			} else {
    				booking.setRebroadcast("false");
    			}
    			booking.setRebroadDate(data.get("REBROADDATE").toString());
    			if ((tmp.equalsIgnoreCase("Y"))&&(!booking.getRebroadDate().equals(" "))){
    				String rb[] = booking.getRebroadDate().split("-");
    				booking.setRebroadYear(rb[0]);
    				booking.setRebroadMonth(rb[1]);
    				booking.setRebroadDay(rb[2]);
    			} else {
    				booking.setRebroadYear("");
    				booking.setRebroadMonth("");
    				booking.setRebroadDay("");
    			}
    			booking.setCreatedDate(data.get("CREATEDDATE").toString());
    			booking.setBkngType(data.get("BKNGTYPE").toString());
    			if (booking.getBkngType().equals(" ")) {
    				booking.setBkngTypeName(" ");
    			} else {
    				booking.setBkngTypeName(data.get("TYPENAME").toString());
    			}
    			
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngMainDetail: Error occurred / "+ ex,ex);
    	} // end try

		return booking;
	}

	public BookingForm selectBkngSubjectDetial(BookingForm bookingIn) throws Exception {

		BookingForm booking = (BookingForm)bookingIn;
		ArrayList subjectList = new ArrayList();

		String select = "SELECT SUBJ_CODE, SUBJ_PERIOD, SUBJ_YEAR " +
		                "FROM   SATBOOK_BKNG_SUBJECT "+
		                "WHERE  BKNG_ID = '"+booking.getBkngId()+"' "+
		                "ORDER BY SUBJ_CODE ";

		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			booking.setRegistrationPeriod(data.get("SUBJ_PERIOD").toString());
    			booking.setRegistrationYear(data.get("SUBJ_YEAR").toString());
    			//booking.setSubCode(data.get("SUBJ_CODE"));
    			subjectList.add(new org.apache.struts.util.LabelValueBean(data.get("SUBJ_CODE").toString(), data.get("SUBJ_CODE").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngSubjectDetial: Error occurred / "+ ex,ex);
    	} // end try

        booking.setSelectedSubjectsAL(subjectList);
		return booking;
	}

	
	public BookingForm selectBkngMaterialDetial(BookingForm bookingIn) throws Exception {

		BookingForm booking = (BookingForm)bookingIn;
		ArrayList materialList = new ArrayList();
		ArrayList materialList2 = new ArrayList();
		//SatbookBkngMaterialRecord[] materialList2 = new SatbookBkngMaterialRecord[20];

		String select = "SELECT MAT_ID, MATERIAL_NAME, CASSETTE, " +
		                "       NVL(TO_CHAR(TIME_IN, 'HH24:MI:SS'),'00:00:00') AS TIMEIN, "+
				        "       NVL(TO_CHAR(TIME_IN, 'HH24'),'00') AS INHH, "+
						"       NVL(TO_CHAR(TIME_IN, 'MI'),'00') AS INMM, "+
						"       NVL(TO_CHAR(TIME_IN, 'SS'),'00') AS INSS, "+
						"       NVL(TIME_INFF,'00') AS INFF, "+
		                "       NVL(TO_CHAR(TIME_OUT, 'HH24:MI:SS'),'00:00:00') AS TIMEOUT, "+
				        "       NVL(TO_CHAR(TIME_OUT, 'HH24'),'00') AS OUTHH, "+
						"       NVL(TO_CHAR(TIME_OUT, 'MI'),'00') AS OUTMM, "+
						"       NVL(TO_CHAR(TIME_OUT, 'SS'),'00') AS OUTSS, "+
						"       NVL(TIME_OUTFF,'00') AS OUTFF, "+
		                "       NVL(TO_CHAR(DURATION, 'HH24:MI:SS'),'00:00:00') AS DURATION, "+
				        "       NVL(TO_CHAR(DURATION, 'HH24'),'00') AS DURHH, "+
						"       NVL(TO_CHAR(DURATION, 'MI'),'00') AS DURMM, "+
						"       NVL(TO_CHAR(DURATION, 'SS'),'00') AS DURSS, "+
						"       NVL(DUR_FF,'00') AS DURFF, "+
						"       NVL(DUPLICATIONS,0) AS DUPL "+
						"FROM   SATBOOK_BKNG_MATERIAL, SATBOOK_MATERIALS "+
        			    "WHERE  BKNG_ID = "+booking.getBkngId()+" "+
        			    "AND    MAT_ID = MATERIAL_ID "+
		                "ORDER BY MATERIAL_NAME ";


    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
			int counter = 0;
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			SatbookBkngMaterialRecord materialRecord = new SatbookBkngMaterialRecord();
    			materialRecord.setBkngId(booking.getBkngId());
    			materialRecord.setMaterialId(data.get("MAT_ID").toString());
    			materialRecord.setMaterialDesc(data.get("MATERIAL_NAME").toString());
    			materialRecord.setCassette(data.get("CASSETTE").toString());
    			materialRecord.setTimeIn(data.get("TIMEIN").toString());
    			materialRecord.setTimeInHH(data.get("INHH").toString());
    			materialRecord.setTimeInMM(data.get("INMM").toString());
    			materialRecord.setTimeInSS(data.get("INSS").toString());
    			materialRecord.setTimeInFF(data.get("INFF").toString());
    			materialRecord.setTimeOut(data.get("TIMEOUT").toString());
    			materialRecord.setTimeOutHH(data.get("OUTHH").toString());
    			materialRecord.setTimeOutMM(data.get("OUTMM").toString());
    			materialRecord.setTimeOutSS(data.get("OUTSS").toString());
    			materialRecord.setTimeOutFF(data.get("OUTFF").toString());
    			materialRecord.setDuration(data.get("DURATION").toString());
    			materialRecord.setDurationHH(data.get("DURHH").toString());
    			materialRecord.setDurationMM(data.get("DURMM").toString());
    			materialRecord.setDurationSS(data.get("DURSS").toString());
    			materialRecord.setDurationFF(data.get("DURFF").toString());
    			materialRecord.setDuplication(data.get("DUPL").toString());
  				//materialList2[counter] = materialRecord;
   				materialList2.add(materialRecord);

   				materialList.add(new org.apache.struts.util.LabelValueBean(data.get("MAT_ID").toString(), data.get("MATERIAL_NAME").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngMaterialDetial: Error occurred / "+ ex,ex);
    	} // end try

    	booking.setAdminMaterials(materialList2);
   		booking.setSelectedMaterialsAL(materialList);

    	return booking;
	}

	public BookingForm selectBkngVisitorDetial(BookingForm bookingIn) throws Exception {

		BookingForm booking = (BookingForm)bookingIn;
		ArrayList visitorList = new ArrayList();

		String select = "SELECT VISITOR "+
        			    "FROM   SATBOOK_BKNG_VISITORS "+
        			    "WHERE  BKNG_ID = "+booking.getBkngId()+" "+
		                "ORDER BY VISITOR ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();

    			visitorList.add(new org.apache.struts.util.LabelValueBean(data.get("VISITOR").toString(), data.get("VISITOR").toString()));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngVisitorDetial: Error occurred / "+ ex,ex);
    	} // end try

    	booking.setVisitors(visitorList);

		return booking;
	}

	public BookingForm selectBkngClassroomDetial(BookingForm bookingIn) throws Exception {

		BookingForm booking = (BookingForm)bookingIn;
		SatbookDAO db2 = new SatbookDAO();
		ArrayList classroomList = new ArrayList();
		String classroomStr = "";

		String select = "SELECT REGION_ID "+
        			    "FROM   SATBOOK_BKNG_CLASSROOM "+
        			    "WHERE  BKNG_ID = "+booking.getBkngId()+" "+
		                "ORDER BY REGION_ID ";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List examInfoList = jdt.queryForList(select);
			Iterator j = examInfoList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			String regionDesc = db2.selectClassroomDesc(data.get("REGION_ID").toString());
    			if (classroomStr.equals("")) {
    				classroomStr = regionDesc;
    			} else {
    				classroomStr = classroomStr +"; "+ regionDesc;
    			}

    			classroomList.add(new org.apache.struts.util.LabelValueBean(data.get("REGION_ID").toString(), regionDesc));

    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBkngClassroomDetial: Error occurred / "+ ex,ex);
    	} // end try

    	booking.setSelectedClassroomsAL(classroomList);
    	booking.setClassroomStr(classroomStr);

		return booking;
	}

	public void saveBookingAssistants(String actionStatus, String bkngId, ArrayList assistantsTypeList) throws Exception{

		AssistantTypeRecord assistantTypeRecord;

		// delete all assistants
		deleteBookingAssistants(bkngId);

		for (int i=0; i < assistantsTypeList.size(); i++) {
			assistantTypeRecord = (AssistantTypeRecord) assistantsTypeList.get(i);

			if ((assistantTypeRecord.getSelectedAssistant() != null)) {
				// re-insert assistants
				insertBookingAssistant(bkngId, assistantTypeRecord.getSelectedAssistant());
			}
		}
	}

	private void insertBookingAssistant(String bkngId, String assistantId) throws Exception{

		if (!assistantId.equals("0")) {
			String sql2 = "INSERT INTO SATBOOK_BKNG_ASSISTANTS(BKNG_ID, ASSISTANT_ID)"+
			"VALUES (?,?)";

			try{
				JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
				jdt1.update(sql2,new Object[] {bkngId, assistantId});
			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: insertBookingAssistant (Insert): Error occurred / "+ ex,ex);
			}
		}
	}

	private void deleteBookingAssistants(String bkngId) throws Exception{

		String sql1 = "DELETE FROM SATBOOK_BKNG_ASSISTANTS " +
					  "WHERE BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(getDataSource());
			jdt1.update(sql1);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: deleteBookingAssistants (Delete): Error occurred /"+ ex,ex);
		}
	}

	public ArrayList selectBookingAssistants(String bkngId) throws Exception {

		ArrayList selectedAssistants = new ArrayList();

		String select = "SELECT ASSISTANT_ID, ASS_TYPE_ID "+
        			    "FROM   SATBOOK_BKNG_ASSISTANTS, SATBOOK_ASSISTANTS "+
        			    "WHERE  BKNG_ID = "+bkngId+" "+
        			    "AND    ASSISTANT_ID = ASS_ID "+
        			    "ORDER BY ASS_TYPE_ID";

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List assList = jdt.queryForList(select);
			Iterator j = assList.iterator();
    		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			selectedAssistants.add(new org.apache.struts.util.LabelValueBean(data.get("ASSISTANT_ID").toString(), data.get("ASS_TYPE_ID").toString()));
    		} // end while

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectBookingAssistants: Error occurred / "+ ex,ex);
    	} // end try

		return selectedAssistants;
	}

	public String validateDoubleBookings(String startDate, String endDate, String bkngId, ArrayList venueList, String systemId) throws Exception {
		String bookingExist = "false";
		String venues="";
		String doubleVenues="";
		BookingForm tmpForm = new BookingForm();
		
		for (int i=0; i<venueList.size();i++) {
			LabelValueBean tmpClassroom = (LabelValueBean) venueList.get(i);
			if (i==0) {
				venues = tmpClassroom.getLabel();
			} else {
				venues = venues+","+tmpClassroom.getLabel();
			}
		}

		String sql1= "";
		if (systemId.equals(tmpForm.getSatbook())) {
			// IF SATBOOK
			//SELECT COUNT(*) FROM SATBOOK_BKNG_MAIN WHERE '2007-01-30 20:00:00' BETWEEN BKNG_START AND BKNG_END;
			sql1 = "SELECT count(BKNG_ID) as bkngCount "+
						  " FROM   SATBOOK_BKNG_MAIN "+
						  " WHERE  ('"+startDate+"' BETWEEN TO_CHAR(BKNG_START,'YYYY-MM-DD HH24:MI') AND TO_CHAR(BKNG_END,'YYYY-MM-DD HH24:MI') "+
						  " OR     '"+endDate+"' BETWEEN TO_CHAR(BKNG_START,'YYYY-MM-DD HH24:MI') AND TO_CHAR(BKNG_END,'YYYY-MM-DD HH24:MI')) " +
						  " AND    SYSTEM_ID = "+systemId;
			
			if (bkngId == null){
				bkngId = "";
			}
			if (bkngId.length()>0) {
			  sql1 = sql1+ " AND    BKNG_ID != "+bkngId;
			}

			try{
				String nrOfBookings = this.querySingleValue(sql1,"bkngCount");
				int nrOfBookingsInt = Integer.parseInt(nrOfBookings);
				if (nrOfBookingsInt >= 1) {
					bookingExist = "true";
				}

			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: validateDoubleBookings : Error occurred / "+ ex,ex);
			}
		} else {
			// if VENBOOK
			sql1 = "SELECT SATBOOK_BKNG_CLASSROOM.REGION_ID AS ID,SATBOOK_CLASSROOMS.CLASSROOM_NAME AS NAME"+
				   " FROM  SATBOOK_BKNG_MAIN, SATBOOK_BKNG_CLASSROOM,  SATBOOK_CLASSROOMS"+
				   " WHERE ('"+startDate+"' BETWEEN TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_START-29/(60*24),'YYYY-MM-DD HH24:MI') AND TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_END + 29/(60*24),'YYYY-MM-DD HH24:MI')" +
				   "    OR  '"+endDate+"' BETWEEN TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_START-29/(60*24),'YYYY-MM-DD HH24:MI') AND TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_END+29/(60*24),'YYYY-MM-DD HH24:MI')"+
				   "    OR  TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_START-29/(60*24),'YYYY-MM-DD HH24:MI') between '"+startDate+"' and '"+endDate+"'"+
				   "    OR  TO_CHAR(SATBOOK_BKNG_MAIN.BKNG_END-29/(60*24),'YYYY-MM-DD HH24:MI') between '"+startDate+"' and '"+endDate+"')"+
				   " AND   SATBOOK_BKNG_MAIN.SYSTEM_ID = "+systemId+
				   " AND   SATBOOK_BKNG_MAIN.BKNG_ID = SATBOOK_BKNG_CLASSROOM.BKNG_ID" +
				   " AND   SATBOOk_BKNG_CLASSROOM.REGION_ID = SATBOOK_CLASSROOMS.CLASSROOM_ID";
			
			if (venueList.size()>=1) {			
				sql1 = sql1 + " AND   SATBOOK_BKNG_CLASSROOM.REGION_ID IN (";
				for (int i=0; i<venueList.size();i++) {
					LabelValueBean tmpClassroom = (LabelValueBean) venueList.get(i);
					if (i==0) {
						sql1 = sql1 +Integer.parseInt(tmpClassroom.getLabel()); 
					} else {
						sql1 = sql1 +","+Integer.parseInt(tmpClassroom.getLabel()); 
					}
				}
				sql1 = sql1 + ")";
			}

			if (bkngId == null){
				bkngId = "";
			}
			if (bkngId.length()>0) {
			  sql1 = sql1+ " AND    SATBOOK_BKNG_MAIN.BKNG_ID != "+bkngId;
			}
	
	    	try{
	    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
	    		List tmpList = jdt.queryForList(sql1);
				Iterator j = tmpList.iterator();
				int counter = 0;
				bookingExist="false";
	    		while (j.hasNext()) {
	    			ListOrderedMap data = (ListOrderedMap) j.next();
	    			if (bookingExist.equals("false")) {
	    				bookingExist = data.get("NAME").toString();
	    			} else {
	    				bookingExist = bookingExist+", "+ data.get("NAME").toString();
	    			}
	    		} // end while

			} catch (Exception ex) {
				throw new Exception("SatbookBookingDAO: validateDoubleBookings : Error occurred / "+ ex,ex);
			}
		}

		return bookingExist;
	}
	
	public boolean validateStartTime(String start_Time , String start_Date, String systemId) throws Exception {
		boolean validStartTime = true;

		String sql = "SELECT COUNT(*) as bkngcount"		
		+" FROM   SATBOOK_BKNG_MAIN"
		+" WHERE  TO_CHAR(BKNG_End,'YYYY-MM-DD') ='"+ start_Date + "'"
		+" AND    '"+ start_Time + "' BETWEEN TO_CHAR(BKNG_End,'HH24:MI') AND TO_CHAR(BKNG_End + 29/(60*24),'HH24:MI')" +
		 " AND    system_id = "+systemId;
		
		try{
			String nrOfBookings = this.querySingleValue(sql,"bkngCount");
			int nrOfBookingsInt = Integer.parseInt(nrOfBookings);
			if (nrOfBookingsInt >= 1) {
				validStartTime = false;
			}

		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: validateStartTime : Error occurred / "+ ex,ex);
		}
		
		
		return validStartTime;
	}
	
	 //Validate EndTime
	public boolean validateEndTime(String end_Time , String end_Date, String systemId) throws Exception {
		boolean validEndTime = true;

		String sql = "SELECT COUNT(*) as bkngcount"        
	        +" FROM   SATBOOK_BKNG_MAIN"
			+" WHERE  TO_CHAR(BKNG_End,'YYYY-MM-DD') ='"+ end_Date + "'"
			+" AND    '"+ end_Time + "' between TO_CHAR(BKNG_Start - 29/(60*24),'HH24:MI')AND TO_CHAR(bkng_start,'HH24:MI')" +
			" AND    system_id = "+systemId;
		
		try{
			String nrOfBookings = this.querySingleValue(sql,"bkngCount");
			int nrOfBookingsInt = Integer.parseInt(nrOfBookings);
			if (nrOfBookingsInt >= 1) {
				validEndTime = false;
			}
			
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: validateEndTime : Error occurred / "+ ex,ex);
		}	
		
		return validEndTime;
	}
			
	//7 days in advance booking validation
	public boolean validateAdvanceBooking(String enteredDate) throws Exception {
		boolean inAdvance = true;

		String sql = " SELECT 1 as greyout FROM DUAL " 
				     +" WHERE '"+enteredDate+"'"
				     +" Between to_char(sysdate,'YYYY-MM-DD') AND to_char(sysdate+7,'YYYY-MM-DD')";
				
		try{
			String greyout = this.querySingleValue(sql,"greyout");
			
			if (greyout.equals("1")) {
				inAdvance = false;
			}
			
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: validateAdvanceBooking : Error occurred / "+ ex,ex);
		}	
		
		return inAdvance;
	}
	
	public void deleteBooking(String bkngId) throws Exception {

		// sms students that booking was deleted if booking
		// is cancelled within 7 days before broadcast.
		sendCancelledSms(bkngId);
		
		// delete classroom details
		deleteClassroom(bkngId);

		// delete visitor details
		deleteVisitors(bkngId);

		// delete material details
		deleteMaterials(bkngId);

		// detete subject details
		deleteSubjects(bkngId);

		// delete main booking detail
		deleteBkngMain(bkngId);
			
	}

	public void confirmBooking(String bkngId, String status) throws Exception{

		String sql2 = "UPDATE SATBOOK_BKNG_MAIN " +
        			  "SET    BKNG_CONFIRMED = '"+status+"' "+
        			  "WHERE  BKNG_ID = "+bkngId+" ";

		try{
			JdbcTemplate jdt1 = new JdbcTemplate(super.getDataSource());
			jdt1.update(sql2);
		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: confirmBooking: Error occurred / "+ ex,ex);
		}
	}

	public String calculateBookingDuration(String startHH, String endHH,
			String startMM, String endMM) throws Exception {
		String duration = "";

		int startHHI = Integer.parseInt(startHH);
		int startMMI = Integer.parseInt(startMM);
		int endHHI = Integer.parseInt(endHH);
		int endMMI = Integer.parseInt(endMM);
		int hours = 0;
		int minutes = 0;

		//hours = endHHI - startHHI;
		//minutes = endMMI - startMMI;
		String select = "SELECT TRUNC(MOD( (TO_DATE('"+endHH+":"+endMM+"', 'HH24:MI') - "+
						"TO_DATE('"+startHH+":"+startMM+"', 'HH24:MI'))*24,24)) "+
						"|| ' HRS ' || "+
						"TRUNC(MOD( (TO_DATE('"+endHH+":"+endMM+"', 'HH24:MI') - " +
						"TO_DATE('"+startHH+":"+startMM+"','HH24:MI'))*24*60,60)) || ' MINUTES' AS DIFFERENCE " +
						"FROM   dual";

		try{
			duration = this.querySingleValue(select,"DIFFERENCE");

		} catch (Exception ex) {
			throw new Exception("SatbookBookingDAO: selectLecturerEmail: Error occurred / "+ ex,ex);
		} // end try

		//duration = hours+" hours and "+minutes+" minutes.";

		return duration;
	}

	public String selectLecturerEmail(String bkngId) throws Exception {

		String lecturerNovellId = "";
		String lecturerEmail = "";

		String select = "SELECT BKNG_LECT_NOVELLID " +
		                "FROM   SATBOOK_BKNG_MAIN "+
		                "WHERE  BKNG_ID = '"+bkngId+"'";

    	try{
    		lecturerNovellId = this.querySingleValue(select,"BKNG_LECT_NOVELLID");

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectLecturerEmail: Error occurred / "+ ex,ex);
    	} // end try

    	if (lecturerNovellId != null) {
    		SatbookOracleDAO db = new SatbookOracleDAO();
    		lecturerEmail = db.getLecturerEmailAddress(lecturerNovellId);
    	}

    	return lecturerEmail;
	}
	
	
	/* Bkng may not be updated within 7 days of broadcast
	 * return true - may update
	 * false - may not update
	 */
	public boolean mayUpdateBkng(String bkngId) throws Exception {

		boolean mayUpdate = false;
		String select = "SELECT count(bkng_ID) AS CNT " +
		                "FROM   SATBOOK_BKNG_MAIN "+
		                "WHERE  BKNG_ID = "+bkngId+" "+
		                "AND    SYSDATE NOT BETWEEN TO_DATE(BKNG_START,'DD-MON-YYYY')-7 AND TO_DATE(BKNG_START,'DD-MON-YYYY')";

    	try{
    		String tmpBkngCount = this.querySingleValue(select,"CNT");
    		int tmpCount = Integer.parseInt(tmpBkngCount);
    		if (tmpCount > 0) {
    			mayUpdate = true;
    		} else {
    			mayUpdate = false;
    		}

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: mayUpdateBkng: Error occurred / "+ ex,ex);
    	} // end try

    	

    	return mayUpdate;
	}

	public String selectInstitution(String forDate) throws Exception {

		String institution = "";

		String select = "SELECT UPPER(SATBOOK_INSTITUTIONS.INST_NAME) AS INAME " +
		                "FROM   SATBOOK_INSTITUTIONS, SATBOOK_INST_DAYS "+
		                "WHERE  SATBOOK_INSTITUTIONS.INST_ID = SATBOOK_INST_DAYS.INST_ID "+
		                "AND    TO_CHAR(SATBOOK_INST_DAYS.BROADCAST_DATE,'YYYY-MM-DD') = '"+forDate+"'";

    	try{
    		institution = this.querySingleValue(select,"INAME");

    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: selectInstitution: Error occurred / "+ ex,ex);
    	} // end try

    	return institution;
	}

	/**
	 * This method executes a query and returns a String value as result.
	 * An empty String value is returned if the query returns no results.
	 *
	 * @param query       The query to be executed on the database
	 * @param field		  The field that is queried on the database
	 * method written by: E Penzhorn
	*/
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
	
	public void sendCancelledSms(String bkngId) throws Exception {

		SatbookDAO db2 = new SatbookDAO();
		ArrayList selectedAssistants = new ArrayList();
		final String[] regionListF =    {"23","26","10","3", "11","5","12","9","20","16","7" ,"4" ,"8" ,"15","18","2", "17","24","6","25"};

		String select = " SELECT BKNG_ID AS A "+
						" FROM   SATBOOK_BKNG_MAIN"+
						" WHERE  BKNG_ID = "+bkngId+
						" AND  TO_CHAR(BKNG_START,'YYYY-MM-DD') BETWEEN TO_CHAR(sysdate,'YYYY-MM-DD') AND TO_CHAR(sysdate+7,'YYYY-MM-DD')"+
						" AND    BKNG_CONFIRMED = 'Y'";
	
    	try{
    		String tmpBkngId = this.querySingleValue(select,"A");
	    	if (tmpBkngId.equals(bkngId)) {
	    		// send sms
	    		BookingForm bookingDelete = new BookingForm();  
	    		bookingDelete.setBkngId(bkngId);
	    		selectBkngMainDetail(bookingDelete);
	    		
	    		bookingDelete = selectBkngSubjectDetial(bookingDelete);
	    		ArrayList subjectList = bookingDelete.getSelectedSubjectsAL();
	    		String feedbackEmail = "";
	    		
	    		// get student cell numbers linked to subject
    			if (subjectList != null) {
    				for (int i=0; i < subjectList.size(); i++) {
    					LabelValueBean subject = (LabelValueBean) subjectList.get(i);
    					String subjCode = subject.getValue();
    					short semesterPeriod = Short.parseShort(bookingDelete.getRegistrationPeriod());
    					short acadYear = Short.parseShort(bookingDelete.getRegistrationYear());

    					String smsMsg = subjCode+"-"+semesterPeriod+"-"+acadYear+" satellite broadcast "+
    									bookingDelete.getStartDate()+" "+bookingDelete.getStartHH()+":"+
    									bookingDelete.getStartMM()+" was cancelled call regional office/call centre(011 6709000)/ " +
    									"http://www.unisa.ac.za/utv";
    					
    					// 	proxy for multiple sms
    					Srsms02sSendSmsToStudList op = new Srsms02sSendSmsToStudList();
    					operListener op1 = new operListener();
    					op.addExceptionListener(op1);
    					op.clear();

    					// 	Input for proxy - first part - no sms will be send with this.
    				    String userCode = ServerConfigurationService.getString("satbookSMSResponsiblePerson");
    					op.setInNovellCodeWsStaffEmailAddress(userCode); // novell code of user sending sms
    					op.setInSmsRequestSmsMsg(smsMsg);
    					op.setInSmsRequestReasonGc27(2); // 2= learner support
    					op.setInSendSmsCsfStringsString1("N"); // N = don't send sms for testing
    					op.setInRegistrationCriteriaTypeCsfStringsString1("M"); // selected modules
    					op.setInMagisterialCriteriaTypeCsfStringsString1("R"); // selected regions
    					op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
    					op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
    					op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
    					op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
    					op.setInCsfClientServerCommunicationsAction("S");
    					op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(semesterPeriod);
    					op.setInWsStudyUnitPeriodDetailMkAcademicYear(acadYear);
    					op.setInRegistrationGpCsfStringsString15(0,subjCode);
    					for (int x=0; x < regionListF.length; x++) {
    						op.setInMagisterialGpCsfStringsString15(x,regionListF[x]);
    					}

    					op.execute();

    					// 	output from above input, to make sure sms must be send.
					    if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
			    			try {
			    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred: "+op.getOutCsfStringsString500(),"syzelle@unisa.ac.za");
			    			} catch (AddressException e) {
			    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
			    			}
			    			try {
			    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred: "+op.getOutCsfStringsString500(),userCode.toLowerCase()+"@unisa.ac.za");
			    			} catch (AddressException e) {
			    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
			    			}
					    }

					    String errorMessage = op.getOutCsfStringsString500();
					    String rcCode = op.getOutSmsRequestMkRcCode();
					    String rcCodeDesc = op.getOutRcDesriptionCsfStringsString60();
					    double costOfTotalSms = op.getOutTotalCostIefSuppliedTotalCurrency();
					    double costPerSms = op.getOutSmsCostCostPerSms();
					    double availableBudget = op.getOutAvailableBudgetAmountIefSuppliedTotalCurrency();
					    double totalBudget = op.getOutBudgetAmountIefSuppliedTotalCurrency();
					    int totalMsgToBeSend = op.getOutSmsRequestMessageCnt();
					    short departmentCode = op.getOutSmsRequestMkDepartmentCode();
					    int batchNumber = op.getOutSmsRequestBatchNr();
		
					    /* if there is messages to be send, do 2nd part of proxy where sms messages will be added to the
					     * SMSQUE table.
					     */
					    if (totalMsgToBeSend >= 1) {
					    	op.setInNovellCodeWsStaffEmailAddress(userCode); // novell code of user sending sms
					    	op.setInSmsRequestMkRcCode(rcCode);
					    	op.setInSmsRequestMkDepartmentCode(departmentCode);
					    	op.setInSmsRequestTotalCost(costOfTotalSms);
					    	op.setInSmsRequestFromSystemGc26(op.getInSmsRequestFromSystemGc26());
					    	op.setInSmsRequestSmsMsg(smsMsg);
					    	op.setInSmsRequestBatchNr(batchNumber);
					    	op.setInSmsRequestMkPersNr(op.getOutSmsRequestMkPersNr());
					    	op.setInSmsRequestMessageCnt(totalMsgToBeSend);
					    	op.setInSmsRequestReasonGc27(2);
					    	op.setInSendSmsCsfStringsString1("Y"); // Y = send sms
					    	op.setInFileNameWizfuncReportingControlPathAndFilename(op.getOutFileNameWizfuncReportingControlPathAndFilename());
					    	op.setInRegistrationCriteriaTypeCsfStringsString1("M");
					    	op.setInMagisterialCriteriaTypeCsfStringsString1("R");
						    op.setInCsfClientServerCommunicationsClientVersionNumber((short) 3);
						    op.setInCsfClientServerCommunicationsClientRevisionNumber((short) 1);
						    op.setInCsfClientServerCommunicationsClientDevelopmentPhase("C");
						    op.setInCsfClientServerCommunicationsClientIsGuiFlag("Y");
						    op.setInCsfClientServerCommunicationsAction("S");
							    op.setInWsStudyUnitPeriodDetailMkAcademicPeriod(semesterPeriod);
						    op.setInWsStudyUnitPeriodDetailMkAcademicYear(acadYear);
						    op.setInRegistrationGpCsfStringsString15(0,subjCode);
						    for (int x=0; x < regionListF.length; x++) {
						    		op.setInMagisterialGpCsfStringsString15(x,regionListF[x]);
						    }
		
						    op.execute();
		
						    /* output for 2nd part where sms was actually added to the smsque table. */
		        			errorMessage = op.getOutCsfStringsString500();
						    rcCode = op.getOutSmsRequestMkRcCode();
						    rcCodeDesc = op.getOutRcDesriptionCsfStringsString60();
						    costOfTotalSms = op.getOutTotalCostIefSuppliedTotalCurrency();
						    costPerSms = op.getOutSmsCostCostPerSms();
						    availableBudget = op.getOutAvailableBudgetAmountIefSuppliedTotalCurrency();
						    totalBudget = op.getOutBudgetAmountIefSuppliedTotalCurrency();
						    totalMsgToBeSend = op.getOutSmsRequestMessageCnt();
						    departmentCode = op.getOutSmsRequestMkDepartmentCode();
						    batchNumber = op.getOutSmsRequestBatchNr();
		
						    feedbackEmail = feedbackEmail+"Subject: "+subjCode+" registration year: "+acadYear+" registration period: "+semesterPeriod+"<br>";
						    if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
						    	feedbackEmail = feedbackEmail+"ERROR Occurred: "+ errorMessage+"<br>";
						    }
						    feedbackEmail = feedbackEmail+"User: "+op.getOutSecurityWsUserName()+"<br>";
						    feedbackEmail = feedbackEmail+"Sms message: "+smsMsg+"<br>";
						    feedbackEmail = feedbackEmail+"Total number of messages send: "+totalMsgToBeSend+"<br>";
						    feedbackEmail = feedbackEmail+"Cost per sms: "+costPerSms+"<br>";
						    feedbackEmail = feedbackEmail+"Total cost of messages send: "+costOfTotalSms+"<br>";
						    feedbackEmail = feedbackEmail+"RC Code: "+rcCode+" - "+rcCodeDesc+"<br>";
						    feedbackEmail = feedbackEmail+"Available budget: "+availableBudget+"<br>";
						    feedbackEmail = feedbackEmail+"Total Budget: "+totalBudget+"<br> ";
						    feedbackEmail = feedbackEmail+"Sms request batch number: "+batchNumber+" <hr>";
		
						    if (op1.getException() != null) {
						      	throw op1.getException();
						    }
						    if (op.getExitStateType() < 3) {
						       	throw new Exception(op.getExitStateMsg());
						    }
		
		   					// 	output from above input, to make sure sms must be send.
						    if (op.getOutErrorFlagCsfStringsString1().equals("Y")) {
				    			try {
				    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred: "+op.getOutCsfStringsString500(),"syzelle@unisa.ac.za");
				    			} catch (AddressException e) {
				    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
				    			}
				    			try {
				    				sendEmail("Satbook SMS error occured","Satbook SMS error occurred: "+op.getOutCsfStringsString500(),userCode.toLowerCase()+"@unisa.ac.za");
				    			} catch (AddressException e) {
				    				//log.info("error sending mail from LMSMIS HISTORY STATS to syzelle@unisa.ac.za");
				    			}
						    }
		
					    }  else { // if (totalMsgToBeSend >= 1)
					    	feedbackEmail = feedbackEmail+"Subject: "+subjCode+" registration year: "+acadYear+" registration period: "+semesterPeriod+"<br>";
						    feedbackEmail = feedbackEmail+"NO Messages was send <br>";
					    }
					} // for (int i=0; i < subjectList.size(); i++)

    				feedbackEmail = feedbackEmail + "</body></html>";
    				String emailSubject = "Satellite Broadcasting Cancelled sms";
    				
    				// select administrator email address.
    				ArrayList emailList = new ArrayList();
    				try {
    					emailList = db2.selectAssistantList("1",true);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}

    				for (int k=0; k< emailList.size(); k++) {
    					AssistantRecord assistant = (AssistantRecord) emailList.get(k);
    					if (null != assistant.getAssistantEmail()) {
    						try {
    							sendEmail(emailSubject,feedbackEmail,assistant.getAssistantEmail());
    						} catch (AddressException e) {
    							// TODO Auto-generated catch block
    							e.printStackTrace();
    						}
    					}
    				}
    			} // if (subjectList != null) {
		    		
	    	}  else {
	    		//System.out.println("DONT SEND SMS");
	    	}
    	} catch (Exception ex) {
    		throw new Exception("SatbookBookingDAO: sendCancelledSms: Error occurred / "+ ex,ex);
    	} // end try

	}
	
	public void sendEmail(String subject, String body, String emailAddress) throws AddressException {

		String tmpEmailFrom = ServerConfigurationService.getString("noReplyEmailFrom");

		InternetAddress toEmail = new InternetAddress(emailAddress);
		InternetAddress iaTo[] = new InternetAddress[1];
		iaTo[0] = toEmail;
		InternetAddress iaHeaderTo[] = new InternetAddress[1];
		iaHeaderTo[0] = toEmail;
		InternetAddress iaReplyTo[] = new InternetAddress[1];
		iaReplyTo[0] = new InternetAddress(tmpEmailFrom);
		List contentList = new ArrayList();
		contentList.add("Content-Type: text/html");

		emailService.sendMail(iaReplyTo[0],iaTo,subject,body,iaHeaderTo,iaReplyTo,contentList);
		//log.info("sending mail from "+iaReplyTo[0]+" to "+toEmail.toString());
	} // end of sendEmail

	private class operListener implements java.awt.event.ActionListener {
		private Exception exception = null;

		operListener() {
			exception = null;
		}

		public Exception getException() {
			return exception;
		}

		public void actionPerformed(java.awt.event.ActionEvent aEvent) {
			exception = new Exception(aEvent.getActionCommand());
		}
	}
	
	  public String getDateTime() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        Date date = new Date();
	        return dateFormat.format(date);
	    }
	
}
