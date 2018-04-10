package za.ac.unisa.lms.tools.studentemail.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.studentemail.Otherl;
import za.ac.unisa.lms.tools.studentemail.Priml;

public class StudentQueryDao extends StudentSystemDAO {
	public Priml getPrimaryLeturer(String course, int year, short period) throws Exception{
		// Get primary Lecturer for subject
		Priml priml = new Priml();
		String novellId;
		String email;

		String sql = "select NOVELL_USER_ID "+
		             "  from USRSUN         "+
		             " where MK_STUDY_UNIT_CODE = '" + course +"' "+
		             "   and SYSTEM_TYPE='L'"+
		             "   and MK_ACADEMIC_YEAR = " + year +
		             "   and MK_SEMESTER_PERIOD = " + period +
		             "   and ACCESS_LEVEL = 'PRIML'";


			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				novellId = data.get("NOVELL_USER_ID").toString();

				String sql0 = "select PERSNO, INITCAP(TITLE)||' '||Initcap(NAME)||' '||Initcap(SURNAME) NAME, CONTACT_TELNO," +
				      "       LOWER(EMAIL_ADDRESS) EMAIL"+
				      "  FROM STAFF " +
				      " WHERE NOVELL_USER_ID = '" + novellId +"'";

					List<?> queryList2 = jdt.queryForList(sql0);
					Iterator<?> j = queryList2.iterator();
					if (j.hasNext()){
						while (j.hasNext()) {
							ListOrderedMap data0 = (ListOrderedMap) j.next();
							if (data0.get("NAME") != null){
								priml.setName(data0.get("NAME").toString());
							} else {
								priml.setName("");
							}
							if (data0.get("CONTACT_TELNO")!=null){
								priml.setWtel(data0.get("CONTACT_TELNO").toString());
							} else {
								priml.setWtel("");
							}
							if (data0.get("EMAIL") != null ){
								email = data0.get("EMAIL").toString();
								priml.setEmail(email);
							} else {
								priml.setEmail("");
							}
							priml.setPersno(data0.get("PERSNO").toString());
						}
					} else {
						String sql1 = "select NAME, PHONE_NUMBER, LOWER(E_MAIL) EMAIL " +
							  "  from usr " +
						      " where NOVELL_USER_CODE = '" + novellId+"'";
							List<?> queryList3 = jdt.queryForList(sql1);
							Iterator<?> k = queryList3.iterator();
							while (k.hasNext()){
								ListOrderedMap data1 = (ListOrderedMap) k.next();
								priml.setName(data1.get("NAME").toString());
								priml.setWtel(data1.get("PHONE_NUMBER").toString());
								if (data1.get("EMAIL") != null) {
									email = data1.get("EMAIL").toString();
									priml.setEmail(email);
								} else {
									priml.setEmail("");
								}
							}
					}
			}
		return priml;
	}

	public ArrayList<Otherl> getOtherLecturers(String course, int year, short period) throws Exception {

		String novellId;
		String email;
		ArrayList<Otherl> list = new ArrayList<Otherl>();
		String sql = "select NOVELL_USER_ID "+
                     "  from USRSUN         "+
                     " where MK_STUDY_UNIT_CODE = '" + course +"' "+
                     "   and SYSTEM_TYPE='L'"+
                     "   and MK_ACADEMIC_YEAR = " + year +
                     "   and MK_SEMESTER_PERIOD = " + period +
                     "   and ACCESS_LEVEL = 'SECDL'";

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				novellId = data.get("NOVELL_USER_ID").toString();

				String sql0 = "select PERSNO, INITCAP(TITLE)||' '||Initcap(NAME)||' '||Initcap(SURNAME) NAME, CONTACT_TELNO," +
				              "       LOWER(EMAIL_ADDRESS) EMAIL"+
				              "  FROM STAFF " +
				              " WHERE NOVELL_USER_ID = '" + novellId +"'";
					List<?> queryList2 = jdt.queryForList(sql0);
					Iterator<?> j = queryList2.iterator();
					if (j.hasNext()){
						while (j.hasNext()) {
							ListOrderedMap data0 = (ListOrderedMap) j.next();
							Otherl otherl = new Otherl();
							if (data0.get("NAME") != null){
								otherl.setName(data0.get("NAME").toString());
							} else {
								otherl.setName("");
							}
							if (data0.get("CONTACT_TELNO")!=null){
								otherl.setWtel(data0.get("CONTACT_TELNO").toString());
							} else {
								otherl.setWtel("");
							}
							if (data0.get("EMAIL") != null ){
								email = data0.get("EMAIL").toString();
								otherl.setEmail(email);
							} else {
								otherl.setEmail("");
							}
							otherl.setPersno(data0.get("PERSNO").toString());
							list.add(otherl);
						}
					} else {
						String sql1 = "select NAME, PHONE_NUMBER, LOWER(E_MAIL) EMAIL " +
							  "  from usr " +
						      " where NOVELL_USER_CODE = '" + novellId+"'";
							List<?> queryList3 = jdt.queryForList(sql1);
							Iterator<?> k = queryList3.iterator();
							while (k.hasNext()){
								ListOrderedMap data1 = (ListOrderedMap) k.next();
								Otherl otherl = new Otherl();
								otherl.setName(data1.get("NAME").toString());
								otherl.setWtel(data1.get("PHONE_NUMBER").toString());
								if (data1.get("EMAIL") != null) {
									email = data1.get("EMAIL").toString();
									otherl.setEmail(email);
								} else {
									otherl.setEmail("");
								}
								list.add(otherl);
							}
					}
			}
		return list;
	}
	public String getStudentEmail(String number) throws Exception{
		String email ="";
		String sql = "select EMAIL_ADDRESS" +
				"       FROM ADRPH" +
				"      WHERE REFERENCE_NO = " + number +
				"        AND FK_ADRCATCODE = 1";
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(sql);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				email = data.get("EMAIL_ADDRESS").toString();
			}

		return email;
	}

}
