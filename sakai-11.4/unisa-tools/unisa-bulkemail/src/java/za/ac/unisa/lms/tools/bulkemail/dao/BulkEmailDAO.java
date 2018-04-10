package za.ac.unisa.lms.tools.bulkemail.dao;

import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
import java.util.*;
//import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;


import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import org.sakaiproject.tool.api.Session;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.bulkemail.forms.BulkEmailForm;
import za.ac.unisa.utils.CoursePeriodLookup;
import za.ac.unisa.utils.SendMail;

import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.email.api.EmailService;




public class BulkEmailDAO extends StudentSystemDAO {

	String novellUserID = "";
	private Log log;
	
	private EmailService emailService;


	public ArrayList getSitesPerLecturer(String novellUserID) throws Exception {

		ArrayList list = new ArrayList();
		String suc = "";
		String ay = "";
		String sp = "";
		String spString = "";
		log = LogFactory.getLog(this.getClass());

		String sitesQuery = "SELECT mk_study_unit_code, mk_academic_year, mk_semester_period " +
			"from usrsun where system_type = 'L' and novell_user_id = '" + novellUserID.toUpperCase() + "' " +
			"and access_level in ('PRIML','SECDL','CADMN') " +
			"order by mk_study_unit_code, mk_academic_year, mk_semester_period";
		/**
		 String sitesQuery = "SELECT mk_study_unit_code, mk_academic_year, mk_semester_period " +
		"from usrsun where system_type = 'L' and novell_user_id = 'FMYBURGH' " +
		"and access_level in ('PRIML','SECDL','CADMN') " +
		"order by mk_study_unit_code, mk_academic_year, mk_semester_period";
		**/

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			log.info("GENERATED QUERY FOR BULK E-MAIL SITES: " + sitesQuery);
			List queryList = jdt.queryForList(sitesQuery);
			Iterator i = queryList.iterator();

			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudentListSites studentListSites = new StudentListSites();
				suc = data.get("MK_STUDY_UNIT_CODE").toString();
				ay = data.get("MK_ACADEMIC_YEAR").toString();
				sp = (data.get("MK_SEMESTER_PERIOD")).toString();
				spString = CoursePeriodLookup.getCourseTypeAsString("0"+sp);
				studentListSites.setLecturerSites(suc + "-" + ay.substring(2,4) + "-" + spString);
				list.add(studentListSites);
				}

		}catch (Exception ex) {
			throw new Exception("Student List DAO : Error "+ ex);
		}

		return list;
	}
/**	To send an e-mail to students of a selected course
 *
 */
	public int sendMail(String confineGender,
							  String confineHomeLanguage,
							  String confineCountry,
							  String confineProvince,
							  String confineRegion,
							  String confineResRegion,
							  String confineRace,
							  Vector sites,
							  ActionForm form,
							  String emailFrom
								) throws Exception {

		BulkEmailForm bulkEmailForm = (BulkEmailForm)form;
        HashSet<String> emailset = new HashSet();
		String selectedCourse = "";
		String selectedYear = "";
		String selectedSemester = "";
		String messageSubject = "";
		String messageToStore = "";
		String subjectToStore = "";
		String messageText = "";
		String emailTo = "";
		String emailTolist="";
		String emailDate = "";
		//String emailFrom = ServerConfigurationService.getString("noReplyEmailFrom");
		if(emailFrom.equalsIgnoreCase("No reply address selected")) {
			emailFrom = "comms@my.unisa.ac.za";
		}

		log = LogFactory.getLog(this.getClass());

		int studentsWhomReceivedMail = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		int l = 0;
		String select = "select stuann.mk_student_nr student_number, email_address, solact.email_verified ";
		String from = "from stuann, stu, adr, adrph, stusun, solact ";
	    String where = "where stuann.mk_student_nr =stu.nr " +
			"and stu.nr = adr.reference_no " +
			"and adr.reference_no = stusun.fk_student_nr "+
			"and adrph.reference_no(+) = stusun.fk_student_nr "+
			"and stusun.fk_student_nr = stu.nr "+
			"and stuann.mk_academic_year = stusun.fk_academic_year "+
			"and stuann.mk_academic_period = 1 " +
			"and solact.student_nr = stu.nr " +
			//"and solact.email_verified = 'Y' " +
			"and adr.fk_adrcatypfk_adrc = 1 "+
			"and adr.fk_adrcatypfk_adrt = 1 "+
			"and adrph.fk_adrcatcode(+) = 1 " +
			"and stusun.status_code in ('RG','FC','DC','CA') and ";

		where += "(";

		for(l = 0; l < sites.size(); l++) {
			selectedCourse = (sites.get(l)).toString().substring(0,7);
			selectedYear = "20" +(sites.get(l)).toString().substring(8,10);
			selectedSemester = (sites.get(l)).toString().substring(11,13);
			where += "(" +
				"stusun.semester_period = " + CoursePeriodLookup.getPeriodTypeAsString(selectedSemester) + " " +
				"and stusun.fk_academic_year = " + selectedYear + " " +
				"and stusun.fk_academic_period = 1 " +
				"and stusun.mk_study_unit_code = '" + selectedCourse + "' ) ";

 			if(l != (sites.size() -1)) {
				where += " or ";
			}
		}

		where += ") ";

		if(!confineGender.equals("notspecified")) {
			if(confineGender.equals("M")) {
				where += "and stu.gender = 'M' ";
			}else {
				where += "and stu.gender = 'F' ";
			}
		}

		//include only when online user and where email address is not null or does exist.
		where += "and (nvl(adrph.email_address,'X') != 'X' and adrph.email_address != ' ') ";
		where += "and stuann.sol_user_flag = 'Y' ";

		if(!confineHomeLanguage.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_home_language = '" + confineHomeLanguage +"' ";
		}

		if(!confineCountry.equalsIgnoreCase("notspecified")) {
			where += "and stu.mk_country_code = '" + confineCountry + "' ";
		}

		if(!confineProvince.equalsIgnoreCase("notspecified")) {
			from += ", ldd ";
			where += "and stu.fk_lddcode = ldd.code and ldd.fk_prvcode = " + confineProvince + " ";
		}

		/**
		if(!confineDistrict.equalsIgnoreCase("notspecified")) {
			if(!(confineProvince.equalsIgnoreCase("notspecified"))) {
				where += "and ldd.code = " + confineDistrict + " ";
			}else {
				where += "and stu.fk_lddcode = " + confineDistrict + " ";
			}
		}
		**/

		if(!confineRegion.equalsIgnoreCase("notspecified")) {
			from += ", regoff ";
			where += "and stuann.mk_regional_office(+) = regoff.code and stuann.mk_regional_office = " + confineRegion + " "+
			         " and regoff.service_type in ('A','H','S')" ;
		}

		if(!confineResRegion.equalsIgnoreCase("notspecified")) {
			if(confineProvince.equalsIgnoreCase("notspecified")) {
				from += ", ldd ";
			}

			where += "and stu.fk_lddcode = ldd.code and ldd.mk_region_code = " + confineResRegion + " ";

		//from student list: String s2 = "select ENG_DESCRIPTION from regoff where regoff.code = " + getResidentialCode(data.get("FK_LDDCODE").toString());
		}

		if(!confineRace.equalsIgnoreCase("notspecified")) {
			where += "and stu.fk_racecode = " + confineRace + " ";
		}
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(select + from + where);
			log.info("GENERATED QUERY FOR BULK EMAIL STUDENTS: " + select + from + where);
			Iterator i = queryList.iterator();

			if(!i.hasNext()) {
				bulkEmailForm.setStudentExists(false);
			}else {
				bulkEmailForm.setStudentExists(true);
			}

			//inserted into the db as well as the message sent (except for the confinements)
			messageSubject = bulkEmailForm.getMessageSubject() + " for student number ";
			subjectToStore = bulkEmailForm.getMessageSubject();
			messageToStore = "For myUnisa users registered for subjects ";
			messageText = "For myUnisa users registered for subjects ";
			if(sites.size() != 0) {
				for (int h = 0; h < sites.size(); h++) {
					messageToStore += " " + sites.get(h) + " ";
					messageText += " " + sites.get(h) + " ";
					//messageText += "\'" + sites.get(h) + "\'";
					if(h != (sites.size() -1)) {
						messageText += ", ";
					}else {
						messageText += "\n";
						for(int g = 0; g<20; g++) {
							messageText += "--";
						}
					}
				}
			}
			messageToStore += bulkEmailForm.getMessageText();
			//messageToStore = messageToStore.replaceAll("\'", "\\\\\'");
			//messageSubject = messageSubject.replaceAll("\'", "\\\\\'");
			//subjectToStore = subjectToStore.replaceAll("\'", "\\\\\'");
			messageText += "\n" + bulkEmailForm.getMessageText();
			messageText += "\n----------------------------------------";
			emailDate = sdf.format(date);
			emailTo = "";
			log.info("preparing to send bulk e-mail...");



		   while(i.hasNext()) {
				//studentsWhomReceivedMail++;
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail bulkEmailDetails = new BulkEmailDetail();
				bulkEmailDetails.setStudentNumber(data.get("STUDENT_NUMBER").toString());
				if(data.get("EMAIL_ADDRESS") == null)  {
					bulkEmailDetails.setEmailAddress("");
				}else {
					bulkEmailDetails.setEmailAddress(data.get("EMAIL_ADDRESS").toString());
					emailTo = bulkEmailDetails.getEmailAddress();
				try{
					//emailset.add(emailTo);
					if(!emailset.contains(emailTo))
					{studentsWhomReceivedMail++;
					emailset.add(emailTo);
					InternetAddress  fromemail= new InternetAddress(emailFrom ,true);
					InternetAddress[] toemail  =InternetAddress.parse(emailTo,true);
					InternetAddress[] replyToStr=InternetAddress.parse(emailFrom,true);
					InternetAddress[] headerToStr=InternetAddress.parse(emailTo);
					String content = messageText;
					String subj = messageSubject + bulkEmailDetails.getStudentNumber();
					emailService = (EmailService) ComponentManager.get(EmailService.class);
					emailService.sendMail(fromemail,toemail,subj,content,headerToStr,replyToStr,null);
					}
					else
					    {System.out.println(emailTo+" has been added already to the set and hence no email will sent to this email");
						}
					}
				 catch(AddressException AdreEx)
				  { System.out.println(AdreEx.getLocalizedMessage());}
				    System.out.println("Executed sendmail part");

		  }

		}
	}
	catch (Exception ex) {
			log.info("unable to send bulk email: " + ex.getMessage());
			throw new Exception("Student List DAO : Error "+ ex);
		}

		//only insert the lecturers message if student's exist for the specific query.
		//This is to be uncommented only commented for test purposes.
		if(bulkEmailForm.isStudentExists()) {
			PersistEmaillDAO persistEmailDAO = new PersistEmaillDAO();
			String sysdate ="sysdate";
			log.info("preparing to insert bulk e-mail content into the database...");
			persistEmailDAO.insertContent(bulkEmailForm.getUserName(),
							subjectToStore,
							messageToStore,
							confineGender,
							getHomeLanguageEngDescription(confineHomeLanguage),
							getCountryEngDescription(confineCountry),
							getProvinceEngDescription(confineProvince),
							getRegionEngDescription(confineRegion),
							getResRegionEngDescription(confineResRegion),
							getRaceEngDescription(confineRace),
							sites
						);

			/**
			persistEmailDAO.insertContent(bulkEmailForm.getUserName(),
					subjectToStore,
					messageToStore,
					emailDate,
					confineGender,
					getHomeLanguageEngDescription(confineHomeLanguage),
					getCountryEngDescription(confineCountry),
					getProvinceEngDescription(confineProvince),
					getRegionEngDescription(confineRegion),
					getRaceEngDescription(confineRace),
					getResRegionEngDescription(confineResRegion),
					sites
				);
		//	**/

			log.info("bulk e-email content inserted into database");
		}
		return studentsWhomReceivedMail;
	}




	public ArrayList getHomeLanguages() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM TAL ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail homelanguages = new BulkEmailDetail();
				homelanguages.setHomeLanguage(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(homelanguages.getHomeLanguage(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Home language query: Error "+ ex);
		    }
		return list;
	}

	public ArrayList getCountries() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM LNS ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail countries = new BulkEmailDetail();
				countries.setCountry(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(countries.getCountry(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating country query: Error "+ ex);
		    }
		return list;
	}

	public ArrayList getProvinces() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM PRV ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail provinces = new BulkEmailDetail();
				provinces.setProvince(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(provinces.getProvince(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating province query: Error "+ ex);
		    }
		return list;
	}

	public ArrayList getRaces() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM RACE ORDER BY ENG_DESCRIPTION, CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail races = new BulkEmailDetail();
				races.setRace(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(races.getRace(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Races query: Error "+ ex);
		    }
		return list;
	}


	public ArrayList getRegions() throws Exception{
		ArrayList list = new ArrayList();
		//String languageQuery = "SELECT CODE, ENG_DESCRIPTION FROM REGOFF ORDER BY ENG_DESCRIPTION, CODE";
		String regionsQuery = " SELECT CODE, ENG_DESCRIPTION FROM REGOFF "+
		  " WHERE SERVICE_TYPE IN  ('A','H','S') "+
		  " ORDER BY ENG_DESCRIPTION, CODE";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(regionsQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail regions = new BulkEmailDetail();
				regions.setRegion(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(regions.getRegion(),data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Regions query: Error "+ ex);
		    }
		return list;
	}




	/**
	public ArrayList getDistricts() throws Exception{
		ArrayList list = new ArrayList();
		String languageQuery = "select CODE, ENG_DESCRIPTION FROM LDD ORDER BY ENG_DESCRIPTION, CODE";
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(languageQuery);
			Iterator i = queryList.iterator();
			list.add(new org.apache.struts.util.LabelValueBean("Not specified","notSpecified"));
			while(i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				BulkEmailDetail districts = new BulkEmailDetail();
				districts.setDistrict(data.get("ENG_DESCRIPTION").toString());
				list.add(new org.apache.struts.util.LabelValueBean(districts.getDistrict(), data.get("CODE").toString()));
			}
		}  catch (Exception ex) {
		       throw new Exception("Populating Districts query: Error "+ ex);
		    }
		return list;
	}
	**/

	public String getHomeLanguageEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}
		else {
			try {
				String query = "SELECT ENG_DESCRIPTION FROM TAL WHERE CODE = '" + code + "'";
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the TAL table: Error "+ ex);
			}
		}

		return engDescription;
	}

	public String getCountryEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "SELECT ENG_DESCRIPTION FROM LNS WHERE CODE = '" + code +"'";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the LNS table: Error "+ ex);
			}
		}

		return engDescription;
	}

	public String getProvinceEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "SELECT ENG_DESCRIPTION FROM PRV WHERE CODE = '" + code + "'";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the PRV table: Error "+ ex);
			}
		}
		return engDescription;
	}
	/**
	public String getDistrictEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "select ENG_DESCRIPTION FROM LDD WHERE CODE = '" + code + "'";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the LDD table: Error "+ ex);
			}
		}

		return engDescription;
	}
	**/
	public String getRegionEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "SELECT ENG_DESCRIPTION FROM REGOFF WHERE service_type in ('A','H','S') and CODE = '" + code + "'";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the REGOFF table: Error "+ ex);
			}
		}

		return engDescription;
	}

	public String getResRegionEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "SELECT ENG_DESCRIPTION FROM REGOFF WHERE service_type in ('A','H','S') and CODE = '" + code + "'";

			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the REGOFF table: Error "+ ex);
			}
		}

		return engDescription;
	}

	public String getRaceEngDescription(String code) throws Exception {
		String engDescription = "";
		if(code.equalsIgnoreCase("notspecified")) {
			return engDescription;
		}else {
			String query = "SELECT ENG_DESCRIPTION FROM RACE WHERE CODE = '" + code + "'";
			try {
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				engDescription = (String)jdt.queryForObject(query, String.class);
			}catch (Exception ex) {
				throw new Exception("BulkEmailDAO: Exception while querying the RACE table: Error "+ ex);
			}
		}

		return engDescription;
	}
}




