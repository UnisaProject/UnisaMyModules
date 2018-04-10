package za.ac.unisa.lms.tools.graduationceremony.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.graduationceremony.forms.GraduationCeremonyForm;
import za.ac.unisa.lms.tools.graduationceremony.forms.Student;
import za.ac.unisa.lms.tools.graduationceremony.forms.Address;
import za.ac.unisa.lms.tools.graduationceremony.forms.Gradcerem;

public class GraduationCeremonyDao extends StudentSystemDAO {

	public Student getStudentinfo(String number) throws Exception {
		Student stu = new Student();
		String result = number;

		String sql = "Select nr, surname, first_names, initials, mk_title, " +
					 " to_char(birth_date,'YYYYMMDD') as birthdate ," +
		             " mk_correspondence from stu where nr = ?";
		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{number});
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				stu.setStudentnumber(data.get("NR").toString());
				stu.setSurname(data.get("SURNAME").toString());
				stu.setInitials(data.get("INITIALS").toString());
				stu.setTitle(data.get("MK_TITLE").toString());
				stu.setFirstnames(data.get("FIRST_NAMES").toString());
				stu.setBirthdate(data.get("BIRTHDATE").toString());
				stu.setBirthYear(data.get("BIRTHDATE").toString().substring(0,4));
				stu.setBirthMonth(data.get("BIRTHDATE").toString().substring(4,6));
				stu.setBirthDay(data.get("BIRTHDATE").toString().substring(6,8));
				stu.setCorrlanguage(data.get("MK_CORRESPONDENCE").toString());
				}
		
		} catch (Exception ex) {
			throw new Exception("GraduationCeremonyDao: getStudent : Error occurred / "+ ex,ex);
		}

		return stu;
	}

	public Address getStudentaddress(String gradStudentNr) throws Exception {
		Address adr = new Address();
		String result = "";

		String sql = "select address_line_1, address_line_2, address_line_3, address_line_4, " +
					 " address_line_5, address_line_6, lpad(to_char(postal_code),4,'0') as postal_code " +
					 " from adr where fk_adrcatypfk_adrc=1 and fk_adrcatypfk_adrt=1 " +
					 " and reference_no = ?";

		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{gradStudentNr});
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				adr.setAddressline1(data.get("ADDRESS_LINE_1").toString());
				adr.setAddressline2(data.get("ADDRESS_LINE_2").toString());
				adr.setAddressline3(data.get("ADDRESS_LINE_3").toString());
				adr.setAddressline4(data.get("ADDRESS_LINE_4").toString());
				adr.setAddressline5(data.get("ADDRESS_LINE_5").toString());
				adr.setAddressline6(data.get("ADDRESS_LINE_6").toString());
				adr.setPostcode(data.get("POSTAL_CODE").toString());
 				}
		
		} catch (Exception ex) {
			throw new Exception("GraduationCeremonyDao: getStudentaddress : Error occurred / "+ ex,ex);
		}
		
		String sql2 = "select home_number, work_number, fax_number, email_address,cell_number " +
			 " from adrph where fk_adrcatcode=1 and reference_no = ?";

		try { 
			JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
			List queryList2 = jdt2.queryForList(sql2, new Object []{gradStudentNr});
			Iterator i = queryList2.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				adr.setHomenumber(data.get("HOME_NUMBER").toString());
				adr.setWorknumber(data.get("WORK_NUMBER").toString());
				adr.setFaxnumber(data.get("FAX_NUMBER").toString());
				if (data.get("CELL_NUMBER") != null){
				adr.setCellnumber(data.get("CELL_NUMBER").toString());
				}
				if (data.get("EMAIL_ADDRESS") != null){
					adr.setEmailaddress(data.get("EMAIL_ADDRESS").toString());
				}else{
					adr.setEmailaddress("");
					}
				}

		} catch (Exception ex) {
			throw new Exception("GraduationCeremonyDao: getStudentaddress : Error occurred / "+ ex,ex);
		}

		return adr;
	}
	
	public void getGradceremonydates(GraduationCeremonyForm gradform) throws Exception {
			
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		//GraduationCeremonyForm gradform = new GraduationCeremonyForm();

		String sql = "select to_char(from_date, 'YYYYMMDD') as from_date, "+
		"  to_char(to_date, 'YYYYMMDD') as to_date " + 
		" from regdat where type='GRADCEREM' and semester_period=0 order by academic_year";
		
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("FROM_DATE") == null){
			} else {
				gradform.setBegindate(data.get("FROM_DATE").toString());
				gradform.setEnddate(data.get("TO_DATE").toString());
			}

			//return result;
		}
	}	
	
	public String getWebcer(String gradStudentNr) throws Exception {
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";
		String sql = "select student_nr, qualification_code " +
		" from webcer where student_nr= ?";
		
		queryList = jdt.queryForList(sql, new Object []{gradStudentNr});
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
//			System.out.println(">>>"+data.get("QUALIFICATION_CODE")+"="+data.get("STUDENT_NR"));
			if ("".equals(data.get("QUALIFICATION_CODE")) || data.get("QUALIFICATION_CODE") == null){
				result="Y";
			}else{
				result = "N";
			}
		}   

		return result;
	}	

	public Gradcerem getGraduationceremony(String gradStudentNr, String begindat, String enddat) throws Exception {
		Gradcerem gradcerem = new Gradcerem();
		
		String sql = "select to_char(grp.starting_time,'HH24:MI') as start_time, gradcnt.eng_description as venue, " +
		 " grd.eng_description as qualdesc, to_char(graduation_ceremon, 'YYYY-MM-DD') as graddate, " + 
		 " address_line_1, address_line_2, address_line_3, mk_graduation_cere, stuaca.mk_qualification_c as qual, " +
		 " grd.type as qualtype, grd.minimum_time as qualtime, " +
		 " grd.fk_katcode as qualcategory,grd.under_post_categor as qualUnderPostCat " +
		 " from stuaca, grp, gradcnt, grd, adr  where mk_student_nr= ?" +
		 " and stuaca.status_code = 'CO' and to_char(graduation_ceremon, 'YYYYMMDD') > ?" +
		 " and to_char(graduation_ceremon, 'YYYYMMDD') < ?"  + 
		 " and mk_graduation_cere = grp.code and grp.fk_gradcntcode = gradcnt.code " +
		 " and stuaca.mk_qualification_c = grd.code and adr.reference_no = gradcnt.code and " +
		 " adr.fk_adrcatypfk_adrc = 30 and adr.fk_adrcatypfk_adrt = 3";

		try { 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql, new Object []{gradStudentNr, begindat, enddat});
			Iterator i = queryList.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				gradcerem.setCeremonytime(data.get("start_time").toString());
				gradcerem.setCeremonydate(data.get("graddate").toString());
				gradcerem.setVenue(data.get("venue").toString());
				gradcerem.setCeremonynr(data.get("mk_graduation_cere").toString());
				gradcerem.setQualification(data.get("qual").toString());
				gradcerem.setQualtype(data.get("qualtype").toString());
				gradcerem.setQualtime(data.get("qualtime").toString());				
				gradcerem.setQualdescription(data.get("qualdesc").toString());
				gradcerem.setQualcategory(replaceNull(data.get("qualcategory")));
				gradcerem.setQualUnderGradCategory(replaceNull(data.get("qualUnderPostCat")));
				gradcerem.setVenueline1(data.get("address_line_1").toString());
				if (data.get("address_line_2") != null) {
				gradcerem.setVenueline2(data.get("address_line_2").toString());
				}
				if (data.get("address_line_3") != null) {
				gradcerem.setVenueline3(data.get("address_line_3").toString());
				}
				}
			
		} catch (Exception ex) {
			throw new Exception("GraduationCeremonyDao: getStudent : Error occurred / "+ ex,ex);
		}

		String sql2 = "select no_of_guests, present_flag, language0 from stugrd where mk_student_nr = ?" +
			" and mk_graduation_cere = ?" + 
			" and  mk_qualification_c = ?";

		try { 
			JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
			List queryList2 = jdt2.queryForList(sql2, 
					new Object []{
						gradStudentNr, 
						gradcerem.getCeremonynr(), 
						gradcerem.getQualification()});
			Iterator i = queryList2.iterator();
			while (i.hasNext()){
				ListOrderedMap data = (ListOrderedMap) i.next();
				gradcerem.setGuests(data.get("no_of_guests").toString());
				gradcerem.setPresentflag(data.get("present_flag").toString());
				gradcerem.setCertificatelang(data.get("language0").toString());
				/* Change Johanet 20130422 - set no_of_guests to spaces if 0 - to force entry of guests if Praesentia */
				if (gradcerem.getGuests()!=null && gradcerem.getGuests().equalsIgnoreCase("0")){
					gradcerem.setGuests("");
				}
			}

		} catch (Exception ex) {
			throw new Exception("GraduationCeremonyDao: getCeremonystudent : Error occurred / "+ ex,ex);
		}

		return gradcerem;
	}
	/**
	 * Update web graduation ceremony student details
	 * 
	 * @param gradStudentNr
	 * 			Student number 
	 * @param gradcerem
	 * 			Graduation ceremony details
	 * @throws Exception
	 */
	public void updateWebCeremony(String gradStudentNr, Gradcerem gradcerem)
			throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());

		String present = "";
		String lang = "";
		if ("A".equalsIgnoreCase(gradcerem.getCertificatelang())) {
			lang = ("Afrikaans");
		} else {
			lang = ("English");
		}
		if ("Y".equalsIgnoreCase(gradcerem.getPresentflag())) {
			present = ("P");
		} else {
			present = ("A");
		}
		
		String WEBCER_BY_GRAD_STU_NR_QRY = "select student_nr, qualification_code "+ 
											" from webcer where student_nr= ?";
		try {
			
			List queryList = jdt.queryForList(WEBCER_BY_GRAD_STU_NR_QRY,
											  new Object[] { gradStudentNr });
			Iterator i = queryList.iterator();
			boolean stu_exists = i.hasNext();
		
			JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());

			if (stu_exists) {
				int result = jdt2.update(new WebCerUpdatePreparedStatementCreator(
								gradStudentNr, lang, present, gradcerem));
			} else {
				int result = jdt2.update(
						new WebCerInsertPreparedStatementCreator(
								gradStudentNr, lang, present, gradcerem));
			}
		} catch (Exception ex) {
			throw new Exception(
					"GraduationCeremonyDao : Error updating student web ceremony / "
							+ gradStudentNr + ex, ex);
		}
		return;
	}


	public void updateStugrad(String gradStudentNr, Gradcerem gradcerem) throws Exception {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		
		String sql2 = "";
		String sql = "select mk_student_nr, mk_qualification_c, mk_graduation_cere " +
		" from stugrd where student_nr = " + gradStudentNr + 
		" and mk_qualification_c = " + gradcerem.getQualification();

		try { 
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get("student_nr") != null){
				sql2 = "update stugrd set language0='" + gradcerem.getCertificatelang() + "'," + 
				" present_flag='" + gradcerem.getPresentflag() + 
				" ', no_of_guests= " + gradcerem.getGuests() + 
				" where mk_student_nr= " + gradStudentNr + 
				" and mk_qualification_c = " + gradcerem.getQualification();
			}
		}
		} catch (Exception ex) {
		throw new Exception("GraduationCeremonyDao: getStudentaddress : Error occurred / "+ ex,ex);
		}

		return ;
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
	
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue.trim();
	}
}