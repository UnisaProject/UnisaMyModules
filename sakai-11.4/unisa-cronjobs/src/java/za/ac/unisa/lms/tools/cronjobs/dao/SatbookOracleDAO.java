package za.ac.unisa.lms.tools.cronjobs.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;

public class SatbookOracleDAO extends StudentSystemDAO {

	public ArrayList<LabelValueBean> selectStudentEmailList(String subjCode, String acadPeriod, String acadYear, String forRegions) throws Exception {
		ArrayList<LabelValueBean> emailList = new ArrayList<LabelValueBean>();

		/* Verander select 26-SEP-2007 (Sonette Yzelle)
		String select = "SELECT adrph.EMAIL_ADDRESS as EMAIL "+
						"FROM   stusun, adrph, solact, stuann "+
						"WHERE  stusun.FK_ACADEMIC_YEAR = "+acadYear+" "+
						"and    stusun.FK_ACADEMIC_PERIOD = "+acadPeriod+" "+
						"and    stusun.MK_STUDY_UNIT_CODE = '"+subjCode+"' "+
						"and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO "+
						"and    solact.STUDENT_NR = stusun.FK_STUDENT_NR "+
						"and    solact.EMAIL_VERIFIED = 'Y' "+
						"and   (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ') "+
						"and    stusun.FK_ACADEMIC_YEAR  = stuann.MK_ACADEMIC_YEAR "+
						"and    stusun.FK_ACADEMIC_PERIOD = stuann.MK_ACADEMIC_PERIOD "+
						"and    stusun.FK_STUDENT_NR = stuann.MK_STUDENT_NR "+
						"and    stuann.MK_REGIONAL_OFFICE IN ("+forRegions+")";
						*/
		String select = "SELECT adrph.EMAIL_ADDRESS as EMAIL "+
						"FROM   stusun, adrph, solact, stu, ldd "+
						"WHERE  stusun.FK_ACADEMIC_YEAR = "+acadYear+" "+
						"and    stusun.FK_ACADEMIC_PERIOD = 1 "+
						"and    stusun.MK_STUDY_UNIT_CODE = '"+subjCode+"' "+
						"and    stusun.SEMESTER_PERIOD = "+acadPeriod+" "+
						"and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO "+
						"and    stusun.STATUS_CODE = 'RG' "+
						"and    solact.STUDENT_NR = stusun.FK_STUDENT_NR "+
						"and    solact.EMAIL_VERIFIED = 'Y' "+
						"and    adrph.fk_adrcatcode = 1 "+
						"and   (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ') "+
						"and    stusun.FK_STUDENT_NR = stu.NR "+
						"and    stu.fk_lddcode = ldd.CODE ";
						
		System.out.println("select email: "+select);

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> examInfoList = jdt.queryForList(select);
			Iterator<?> j = examInfoList.iterator();
	   		while (j.hasNext()) {
    			ListOrderedMap data = (ListOrderedMap) j.next();
    			emailList.add(new org.apache.struts.util.LabelValueBean(data.get("EMAIL").toString(), data.get("EMAIL").toString()));
	   		}
    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.tools.cronjobs.dao.SatbookOracleDAO: selectStudentEmailList: Error occurred / "+ ex,ex);
    	} // end try

		return emailList;
	}

	public ArrayList<LabelValueBean> selectStudentCellList(String subjCode, String acadPeriod, String acadYear, String forRegions) throws Exception {
		ArrayList<LabelValueBean> cellList = new ArrayList<LabelValueBean>();

		/* Change select 26-SEP-2007 (Sonette Yzelle)
		String select = "SELECT solact.STUDENT_NR as STNO, adrph.CELL_NUMBER as CELL "+
						"FROM   stusun, adrph, solact, stuann "+
						"WHERE  stusun.FK_ACADEMIC_YEAR = "+acadYear+" "+
						"and    stusun.FK_ACADEMIC_PERIOD = "+acadPeriod+" "+
						"and    stusun.MK_STUDY_UNIT_CODE = '"+subjCode+"' "+
						"and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO "+
						"and    solact.STUDENT_NR = stusun.FK_STUDENT_NR "+
						"and   (nvl(adrph.CELL_NUMBER,'X') != 'X' and adrph.CELL_NUMBER != ' ') "+
						"and    stusun.FK_STUDENT_NR = stuann.MK_STUDENT_NR "+
						"and    stusun.FK_ACADEMIC_YEAR  = stuann.MK_ACADEMIC_YEAR "+
						"and    stusun.FK_ACADEMIC_PERIOD = stuann.MK_ACADEMIC_PERIOD "+
						"and    stuann.MK_REGIONAL_OFFICE IN ("+forRegions+")"; */
		String select = "SELECT solact.STUDENT_NR as STNO, adrph.CELL_NUMBER as CELL "+
						"FROM   stusun, adrph, solact, stu, ldd, stuann "+
						"WHERE  stusun.FK_ACADEMIC_YEAR = "+acadYear+" "+
						"and    stusun.FK_ACADEMIC_PERIOD = 1 "+
						"and    stusun.MK_STUDY_UNIT_CODE = '"+subjCode+"' "+
						"and    stusun.SEMESTER_PERIOD = "+acadPeriod+" "+
						"and    stusun.FK_STUDENT_NR = adrph.REFERENCE_NO "+
						"and    adrph.fk_adrcatcode = 1 "+
						"and   (nvl(adrph.CELL_NUMBER,'X') != 'X' or adrph.CELL_NUMBER != ' ') "+
						"and    stusun.FK_STUDENT_NR = stu.NR "+
						"and   stusun.FK_ACADEMIC_YEAR  = stuann.MK_ACADEMIC_YEAR "+
						"and   stusun.FK_ACADEMIC_PERIOD = stuann.MK_ACADEMIC_PERIOD "+
						"and  stusun.FK_STUDENT_NR = stuann.MK_STUDENT_NR "+
						"and    stuann.SMS_FLAG != 'N' "+
						"and    stu.fk_lddcode = ldd.CODE "+
						"and    ldd.MK_REGION_CODE in ("+forRegions+") ";
		System.out.println("select cell: "+select);

    	try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		List<?> cellInfoList = jdt.queryForList(select);
			Iterator<?> j = cellInfoList.iterator();
	   		while (j.hasNext()) {
	   			ListOrderedMap data = (ListOrderedMap) j.next();
    			cellList.add(new org.apache.struts.util.LabelValueBean(data.get("CELL").toString(), data.get("STNO").toString()));
	   		}
    	} catch (Exception ex) {
    		throw new Exception("za.ac.unisa.lms.tools.cronjobs.dao.SatbookOracleDAO: selectStudentCellList: Error occurred / "+ ex,ex);
    	} // end try

		return cellList;
	}

	public String selectRegionDesc(String regionCode) throws Exception {
		String regionDesc = "";

		String select = "SELECT ENG_DESCRIPTION "+
		                "FROM   REGOFF "+
		                "WHERE  CODE = '"+regionCode+"' ";

  		try{
    		regionDesc = this.querySingleValue(select,"ENG_DESCRIPTION");

     	} catch (Exception ex) {
    		throw new Exception("SatbookOracleDAO: selectRegionDesc: Error occurred / "+ ex,ex);
    	} // end try

		return regionDesc;
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
	public ArrayList<LabelValueBean> getEmailAddress(String novellUserId) throws Exception{
		ArrayList<LabelValueBean> emailList = new ArrayList<LabelValueBean>();
		
		String query1 =	"SELECT EMAIL_ADDRESS FROM STAFF "+
						"WHERE UPPER(NOVELL_USER_ID) = '"+novellUserId+"'";
		
		try{
			JdbcTemplate jdt = new JdbcTemplate(this.getDataSource());
			List<?> list = jdt.queryForList(query1);
			Iterator<?> j = list.iterator();
			//First see if users exist in STAFF table.  If not, retrieve email address from table USR
			if (! j.hasNext()){
				String query2 =	"SELECT E_MAIL FROM USR "+
								"WHERE UPPER(NOVELL_USER_CODE) = '"+novellUserId+"'";
				list = jdt.queryForList(query2);
				j = list.iterator();
			}
			
			while (j.hasNext()){
				ListOrderedMap data = (ListOrderedMap) j.next();
				emailList.add(new org.apache.struts.util.LabelValueBean(data.get("EMAIL_ADDRESS").toString(), data.get("EMAIL_ADDRESS").toString()));
				}
		}catch (Exception e){
			throw new Exception("za.ac.unisa.lms.db.dao.SatbookOracleDAO: getEmailAddress: Error occurred / "+ e,e);
		}
		return emailList;
	}
	
	public String getLecturerContactDetails(String lecturerNovellId) throws Exception{
		String lecturerDetail = "";
		String sql1 =	"SELECT NAME || ' ' || INITCAP(SURNAME) || '#'|| CONTACT_TELNO ||'#'|| "+
						"EMAIL_ADDRESS AS LDETAILS, NOVELL_USER_ID "+
						"FROM STAFF WHERE upper(NOVELL_USER_ID) = upper('"+lecturerNovellId+"')";
		try{
			lecturerDetail = this.querySingleValue(sql1,"LDETAILS");
			if (lecturerDetail.length() == 0){
				String sql2 =	"SELECT NAME || '#' || PHONE_NUMBER || '#' || "+
								"E_MAIL AS LDETAILS FROM USR "+
								"WHERE upper(NOVELL_USER_CODE) = upper('"+lecturerNovellId+"') ";
				lecturerDetail = this.querySingleValue(sql2,"LDETAILS");
			}
		}catch (Exception e){
			throw new Exception("SatbookOracleDAO: lecturerExistStaff (SELECT): Error occurred");
		}
		return lecturerDetail;
	}
}
