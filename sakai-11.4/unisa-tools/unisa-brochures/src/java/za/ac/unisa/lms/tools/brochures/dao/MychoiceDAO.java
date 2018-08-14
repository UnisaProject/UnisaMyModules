package za.ac.unisa.lms.tools.brochures.dao;

import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import org.sakaiproject.component.cover.ServerConfigurationService;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.brochures.constants.BrochureQueries;
import za.ac.unisa.lms.tools.brochures.forms.BrochuresForm;

public class MychoiceDAO extends StudentSystemDAO {

	private boolean myReg = false;
	private boolean myModule = false;

	public Document myChoiceXml(String code, String catCode, int year,
			String schCode, String dptCode, String researchFlag,
			String heqfComp, String nqfLevel, String qualificationCode)
			throws Exception {

		int lastyr = year - 1;
		int nextyr = year + 1;

		String query = "SELECT COLLEG.ABBREVIATION COLL, COLLEG.eng_description COLLEGE_DESCR,"
				+ " colleg.code code,"
				+ " grd.school_code schoolcode,"
				+ " (select school.eng_description from school where code =grd.school_code and SCHOOL.COLLEGE_CODE=colleg.code) SCHOOLDESCR,"
				+ " GRD.MK_ACAD_DEPT_CODE deptcode,"
				+ " (select dpt.eng_description from dpt where code=grd.MK_ACAD_DEPT_CODE) DEPT,"
				+
				// " GRD.MK_DEPARTMENT_CODE deptcode,"+
				// " (select dpt.eng_description from dpt where dpt.code=grd.MK_DEPARTMENT_CODE  ) DEPT,"+
				" QUASPC.MK_QUALIFICATION_C QUAL_CODE, grd.long_eng_descripti  QUAL_DESCR,"
				//+ " replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),"
				//+ " 'With','with'),'Hiv','HIV'),'Aids','AIDS'),'(Ctma)','(CTMA)'),'Phd','PhD'),' To ',' to '),"
				//+ " 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL') QUAL_DESCR,"
				+ " CASE" + " WHEN grd.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END qual_repeat,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.speciality_code"
				+ " ELSE ' '"
				+ " END SPEC_CODE,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.english_descriptio"
				//+ " THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior'),'Hiv','HIV'),'Aids','AIDS'),"
				//+ " ' Curriculum',' curriculum'),' Dissertation',' dissertation'),' Limited Scope',' limited scope'),' A ',' a ')"
				+ " ELSE ' '"
				+ " END SPEC_DESCR,"
				+ " CASE"
				+ " WHEN (quaspc.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ ")"
				+ " OR (quaspc.repeaters_only = 'Y' and quaspc.repeaters_from_yea<"
				+ year
				+ ")"
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END spec_repeat,"
				+ " grd.under_post_categor UNDER_POST, grd.type TYPE,"
				+ " grd.nqf_exit_level NQF_EXIT_LEVEL, grd.nqf_credits NQF_CREDITS,"
				//Aps Score from Function 42
				+ "grd.aps_score,"
				//Adding SAQA ID
				+ "grd.saqa_id,"
				//foundation flag to choose which aps_score to extract
				+ "grd.foundation_flag,"
				+ " quaspc.admission_require,"
				//Aps Score from Function 391
				+ "quaspc.aps_score aps_score_1,"
				+ " grd.fk_katcode CAT_CODE, kat.eng_description CAT_NAME,"
				+ " grd.from_year QUAL_FROM_YEAR, grd.to_year QUAL_TO_YEAR, grd.RESEARCH_DEGREE_FLAG research,"
				+ " quaspc.from_year SPEC_FROM_YEAR, quaspc.to_year SPEC_TO_YEAR,"
				+ "grd.minimum_time,"
				+ " gencod.afr_description CAT_GRP,gencod.eng_description CATEGORY_GROUP"
				+ " FROM quaspc, grd, colleg, kat, gencod"
				+ " WHERE quaspc.in_use_flag = 'Y'"
				+ " AND quaspc.mk_qualification_c = grd.code"
				+ " AND grd.in_use_flag = 'Y'"
				+ " AND (grd.to_year = 0 OR grd.to_year > "
				+ lastyr
				+ ")"
				+ " AND grd.from_year < "
				+ nextyr
				+ " AND grd.TYPE <> 'S'"
				+ " AND under_post_categor IN ('U','H')"
				+ " AND quaspc.college_code = colleg.code";
		
		if (!code.equals("-1")) {
			query = query + " AND colleg.code=" + code;
		} else {
		}
		if (!catCode.equals("-1")) {
			query = query + " AND kat.code=" + catCode;

		} else {
		}
		/*if (!code.equals("-1")) {
			query = query + " AND colleg.code=" + code;
		} else {
		}*/
		if (!schCode.equals("-1")) {
			query = query + " and grd.school_code=" + schCode;

		} else {
		}
		if (!dptCode.equals("-1")) {
			query = query + " and grd.MK_ACAD_DEPT_CODE=" + dptCode;
		} else {
		}
		if (researchFlag.equals("Y")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG='Y'";
		} else if (researchFlag.equals("N")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG=' '";
		} else if (researchFlag.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (heqfComp.equals("Y")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG='Y'";
		} else if (heqfComp.equals("N")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG=' '";
		} else if (heqfComp.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (!nqfLevel.equals("-1")) {
			query = query + " and grd.NQF_EXIT_LEVEL=" + nqfLevel;

		} else {
		}
		if (!qualificationCode.equals("-1")) {
			query = query + " AND grd.CODE=" + "'" + qualificationCode + "'";
		} else {
		}
		query = query
				+ " AND fk_katcode = kat.code"
				+ " and grd.fk_katcode = gencod.code"
				+ " and gencod.fk_gencatcode=172"
				+ " ORDER BY colleg.abbreviation,SCHOOLDESCR,DEPT,grd.under_post_categor,gencod.eng_description, grd.TYPE, GRD.CODE";
		
		// under_post_categor = H grd.TYPE = G
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();

			Document document = documentBuilder.newDocument();
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List qualInfo = jdt.queryForList(query);
			Iterator i = qualInfo.iterator();

			String collegeName = "";
			String schoolName = "";
			String department = "";
			String collcode = "";
			String schoolCode = "";
			String deptCode = "";
			String qual = "";
			String qualificationLevel = "";
			String qualificationGroup = "";
			String qualificationCategory = "";
			String cat_code = "";
			String foundationFlag = "";
			
			Element colleges = document.createElement("colleges");
			document.appendChild(colleges);
			Element qualification = null;
			Element qualLevel = null;
			Element college = null;
			Element school = null;
			// Element dpt = null;
			Element qualificationgroup = null;
			Element qualificationcategory = null;

			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();

				if (!collegeName.equals(data.get("COLLEGE_DESCR").toString())) {
					college = document.createElement("college");
					colleges.appendChild(college);

					collegeName = data.get("COLLEGE_DESCR").toString();
					collcode = data.get("code").toString();
					Element collName = document.createElement("name");
					Element collCode = document.createElement("code");
					collName.appendChild(document.createTextNode(collegeName));
					collCode.appendChild(document.createTextNode(collcode));
					college.appendChild(collName);
					college.appendChild(collCode);
					                                        
					qualificationLevel = data.get("UNDER_POST").toString();
					qualLevel = document.createElement("qualificationlevel");
					college.appendChild(qualLevel);
					Element level = document.createElement("name");
					level.appendChild(document.createTextNode(data.get(
									"UNDER_POST").toString()));
					qualLevel.appendChild(level);

					qualificationGroup = data.get("CATEGORY_GROUP").toString();
					cat_code = data.get("CAT_GRP").toString();
					qualificationgroup = document
									.createElement("qualificationgroup");
					qualLevel.appendChild(qualificationgroup);
					Element qualGroup = document.createElement("name");
					qualGroup.appendChild(document
									.createTextNode(qualificationGroup));
					Element catcode = document.createElement("code");
					catcode.appendChild(document.createTextNode(cat_code));
					qualificationgroup.appendChild(qualGroup);
					qualificationgroup.appendChild(catcode);

					qualificationCategory = data.get("CAT_NAME").toString();
					qualificationcategory = document
									.createElement("qualificationcategory");
					qualificationgroup.appendChild(qualificationcategory);
					Element qualCat = document.createElement("name");
					qualCat.appendChild(document
									.createTextNode(qualificationCategory));
					qualificationcategory.appendChild(qualCat);
 

				}
				/*
				 * String tempDescr; try{ tempDescr =
				 * data.get("SCHOOLDESCR").toString();
				 * }catch(NullPointerException ne){ tempDescr="Unknown"; }
				 * 
				 * if (! schoolName.equals(tempDescr)){ school =
				 * document.createElement("School");
				 * college.appendChild(school);
				 * 
				 * schoolName = tempDescr; schoolCode =
				 * data.get("schoolcode").toString(); Element schName=
				 * document.createElement("name"); Element schoolCode1 =
				 * document.createElement("code");
				 * schName.appendChild(document.createTextNode(schoolName));
				 * schoolCode1.appendChild(document.createTextNode(schoolCode));
				 * school.appendChild(schName); school.appendChild(schoolCode1);
				 * }
				 */
				/*
				 * String tempDept; String tempDptCode; try{ tempDept =
				 * data.get("dept").toString(); }catch(NullPointerException ne){
				 * tempDept = "Unknown"; } try{ tempDptCode =
				 * data.get("deptcode").toString(); }catch(NullPointerException
				 * ne){ tempDptCode = "Unknown"; }
				 * 
				 * if (! department.equals(tempDept)){ dpt =
				 * document.createElement("department");
				 * school.appendChild(dpt);
				 * 
				 * department = tempDept; deptCode = tempDptCode; Element dept=
				 * document.createElement("name"); Element dept_code =
				 * document.createElement("code");
				 * dept.appendChild(document.createTextNode(department));
				 * dept_code.appendChild(document.createTextNode(deptCode));
				 * dpt.appendChild(dept); dpt.appendChild(dept_code);
				 * 
				 * }
				 */

				if (!qualificationLevel.equals(rtrim(ltrim(data.get(
						"UNDER_POST").toString())))) {
					qualificationLevel = data.get("UNDER_POST").toString();
					qualLevel = document.createElement("qualificationlevel");
					college.appendChild(qualLevel);
					Element level = document.createElement("name");
					level.appendChild(document.createTextNode(data.get(
							"UNDER_POST").toString()));
					qualLevel.appendChild(level);
				}

				if (!qualificationGroup.equals(rtrim(ltrim(data.get(
						"CATEGORY_GROUP").toString())))) {
					qualificationGroup = data.get("CATEGORY_GROUP").toString();
					cat_code = data.get("CAT_GRP").toString();
					qualificationgroup = document
							.createElement("qualificationgroup");
					qualLevel.appendChild(qualificationgroup);
					Element qualGroup = document.createElement("name");
					qualGroup.appendChild(document
							.createTextNode(qualificationGroup));
					Element catcode = document.createElement("code");
					catcode.appendChild(document.createTextNode(cat_code));
					qualificationgroup.appendChild(qualGroup);
					qualificationgroup.appendChild(catcode);

				}
				if (!qualificationCategory.equals(data.get("CAT_NAME")
						.toString())) {
					qualificationCategory = data.get("CAT_NAME").toString();
					qualificationcategory = document
							.createElement("qualificationcategory");
					qualificationgroup.appendChild(qualificationcategory);
					Element qualCat = document.createElement("name");
					qualCat.appendChild(document
							.createTextNode(qualificationCategory));
					qualificationcategory.appendChild(qualCat);

				}
				qual = data.get("QUAL_DESCR").toString();
				qualification = document.createElement("qualification");
				qualificationcategory.appendChild(qualification);

				Element qualName = document.createElement("name");
				qualName.appendChild(document.createTextNode(data.get(
						"QUAL_DESCR").toString()));

				Element qualCode = document.createElement("code");
				qualCode.appendChild(document.createTextNode(data.get(
						"QUAL_CODE").toString()));

				Element spes = document.createElement("spec");
				spes.appendChild(document.createTextNode(data.get("SPEC_CODE")
						.toString()));
				Element spesDesc = document.createElement("specDesc");
				spesDesc.appendChild(document.createTextNode(data.get(
						"SPEC_DESCR").toString()));

				Element admission = document.createElement("admission");
				try {
					String temp = data.get("admission_require").toString();
					String[] strList;
					strList = temp.split("\n");
					for (int j = 0; j < strList.length; j++) {
						Element line = document.createElement("line" + (j + 1));
						line.appendChild(document.createTextNode(strList[j]));
						admission.appendChild(line);
					}
				} catch (NullPointerException e) {
				}
				Element credits = document.createElement("credits");
				credits.appendChild(document.createTextNode(data.get(
						"NQF_CREDITS").toString()));

				Element nqfl = document.createElement("nqfl");
				nqfl.appendChild(document.createTextNode(data.get(
						"NQF_EXIT_LEVEL").toString()));

				Element specRepeat = document.createElement("specRepeat");
				specRepeat.appendChild(document.createTextNode(data.get(
						"spec_repeat").toString()));

				Element qualRep = document.createElement("qualRepeat");
				qualRep.appendChild(document.createTextNode(data.get(
						"qual_repeat").toString()));

				Element research = document.createElement("research");
				try {
					research.appendChild(document.createTextNode(data.get(
							"research").toString()));
				} catch (NullPointerException ne) {
				}
				
				//New APS Score Element 42
				Element apsScore = document.createElement("aps_score");
				
				if(spesDesc.equals(data.get("SPEC_DESCR").toString() == null)){
					
					try {			
						apsScore.appendChild(document.createTextNode(data.get(
								"aps_score").toString()));

					} catch (NullPointerException e) {
						
					}	
				}
				else{
					//New APS Score Element 391
									
					try {			
						apsScore.appendChild(document.createTextNode(data.get(
								"aps_score_1").toString()));

					} catch (NullPointerException e) {
						
					}
				}
				//SAQA ID
				Element saqaId = document.createElement("saqa_id");
				try {
					saqaId.appendChild(document.createTextNode(data.get(
							"saqa_id").toString()));
				} catch (NullPointerException ne) {
				}
				qualification.appendChild(qualName);
				qualification.appendChild(qualCode);
				qualification.appendChild(spes);
				qualification.appendChild(spesDesc);
				qualification.appendChild(admission);
				qualification.appendChild(credits);
				qualification.appendChild(nqfl);
				qualification.appendChild(apsScore);
				qualification.appendChild(saqaId);
				qualification.appendChild(specRepeat);
				qualification.appendChild(qualRep);
				qualification.appendChild(research);
				// qualificationLevel = "";

			}
			/*System.out.println(document);*/
			return document;
			
		} catch (Exception ex) {
			/*System.out.println("Problem : " + ex);*/
			throw new Exception(
					"za.ac.unisa.lms.tools.brochures.dao.MychoiceDA0: myChoiceXml: Error occurred / "
							+ ex, ex);
		}
		
	}

	public boolean ismyChoiceList(String code, String catCode, int year,
			String schCode, String dptCode, String researchFlag,
			String heqfComp, String nqfLevel, String qualificationCode)
			throws Exception {

		int lastyr = year - 1;
		int nextyr = year + 1;

		String query = " SELECT COLLEG.ABBREVIATION COLL, COLLEG.eng_description COLLEGE_DESCR,"
				+ " colleg.code code,"
				+ " grd.school_code schoolcode,"
				+ " (select school.eng_description from school where code =grd.school_code and SCHOOL.COLLEGE_CODE=colleg.code) SCHOOLDESCR,"
				+ " GRD.MK_ACAD_DEPT_CODE deptcode,"
				+ " (select dpt.eng_description from dpt where code=grd.MK_ACAD_DEPT_CODE) DEPT,"
				+ " QUASPC.MK_QUALIFICATION_C QUAL_CODE, grd.long_eng_descripti QUAL_DESCR,"
				//+ " replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in ') QUAL_DESCR,"
				+ " CASE" + " WHEN grd.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END qual_repeat,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.speciality_code"
				+ " ELSE ' '"
				+ " END SPEC_CODE,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.english_descriptio"
				//+ " THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior')"
				+ " ELSE ' '"
				+ " END SPEC_DESCR,"
				+ " CASE"
				+ " WHEN (quaspc.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ ")"
				+ " OR (quaspc.repeaters_only = 'Y' and quaspc.repeaters_from_yea<"
				+ year
				+ ")"
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END spec_repeat,"
				+ " grd.under_post_categor UNDER_POST, grd.type TYPE,"
				+ " grd.nqf_exit_level NQF_EXIT_LEVEL, grd.nqf_credits NQF_CREDITS,"
				+ " quaspc.admission_require admission_require, grd.fk_katcode CAT_CODE, kat.eng_description CAT_NAME,"
				+ " grd.from_year QUAL_FROM_YEAR, grd.to_year QUAL_TO_YEAR,"
				+ " quaspc.from_year SPEC_FROM_YEAR, quaspc.to_year SPEC_TO_YEAR, grd.minimum_time, "
				+ " gencod.afr_description CAT_GRP,gencod.eng_description CATEGORY_GROUP"
				+ " FROM quaspc, grd, colleg, kat, gencod"
				+ " WHERE quaspc.in_use_flag = 'Y'"
				+ " AND quaspc.mk_qualification_c = grd.code"
				+ " AND grd.in_use_flag = 'Y'"
				+ " AND (grd.to_year = 0 OR grd.to_year >"
				+ lastyr
				+ ")"
				+ " AND grd.from_year < "
				+ nextyr
				+ " AND grd.TYPE <> 'S'"
				+ " AND under_post_categor IN ('U')"
				+ " AND quaspc.college_code = colleg.code";

		if (!catCode.equals("-1")) {
			query = query + " AND kat.code=" + catCode;
		} else {
		}
		if (!code.equals("-1")) {
			query = query + " AND colleg.code=" + code;
		} else {
		}
		if (!schCode.equals("-1")) {
			query = query + " and grd.school_code=" + schCode;

		} else {
		}
		if (!dptCode.equals("-1")) {
			query = query + " and GRD.MK_DEPARTMENT_CODE=" + dptCode;
		} else {
		}
		if (researchFlag.equals("Y")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG='Y'";
		} else if (researchFlag.equals("N")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG=' '";
		} else if (researchFlag.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (heqfComp.equals("Y")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG='Y'";
		} else if (heqfComp.equals("N")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG=' '";
		} else if (heqfComp.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (!nqfLevel.equals("-1")) {
			query = query + " and grd.NQF_EXIT_LEVEL=" + nqfLevel;

		} else {
		}
		if (!qualificationCode.equals("-1")) {
			query = query + " AND grd.CODE=" + "'" + qualificationCode + "'";
		} else {
		}
		query = query
				+ " AND fk_katcode = kat.code"
				+ " and gencod.fk_gencatcode=172"
				+ " ORDER BY colleg.abbreviation,SCHOOLDESCR,DEPT, grd.under_post_categor, gencod.eng_description,grd.TYPE, GRD.CODE";

		try {
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List qualInfo = jdt.queryForList(query);
			Iterator i = qualInfo.iterator();
			boolean isList = qualInfo.isEmpty();

			return isList;
		} catch (Exception ex) {
			throw new Exception("ismyChoiceList / " + ex, ex);
		}

	}

	public Connection getConnection() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			try {
				return DriverManager.getConnection(
						"jdbc:oracle:thin:@163.200.145.245:1521:DEV",
						"tmanamn", "Lethabo0692!@");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Document myChoiceMandD(String code, String catCode, int year,
			String schCode, String dptCode, String researchFlag,
			String heqfComp, String nqfLevel, String qualificationCode)
			throws Exception {

		int lastyr = year - 1;
		int nextyr = year + 1;

		String query = "SELECT COLLEG.ABBREVIATION COLL, COLLEG.eng_description COLLEGE_DESCR,"
				+ " colleg.code code,"
				+ " grd.school_code schoolcode,"
				//Aps Score from Function 42
				//+ " grd.aps_score,"
				//Adding SAQA ID
				+ "grd.saqa_id,"
				+ " (select school.eng_description from school where code =grd.school_code and SCHOOL.COLLEGE_CODE=colleg.code) SCHOOLDESCR,"
				+ " GRD.MK_ACAD_DEPT_CODE deptcode,"
				+ " (select dpt.eng_description from dpt where code=grd.MK_ACAD_DEPT_CODE) DEPT,"
				+ " QUASPC.MK_QUALIFICATION_C QUAL_CODE, grd.long_eng_descripti QUAL_DESCR,"
				//+ " replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),"
				//+ " 'With','with'),'Hiv','HIV'),'Aids','AIDS'),'Phd','PhD'),' To ',' to '),"
				//+ " 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL') QUAL_DESCR,"
				+ " CASE" + " WHEN grd.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END qual_repeat,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.speciality_code"
				+ " ELSE ' '"
				+ " END SPEC_CODE,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.english_descriptio"
				//+ " THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior'),'Hiv','HIV'),'Aids','AIDS'),"
				//+ " ' Curriculum',' curriculum'),' Dissertation',' dissertation'),' Limited Scope',' limited scope'),' A ',' a ')"
				+ " ELSE ' '"
				+ " END SPEC_DESCR,"
				+ " CASE"
				+ " WHEN (quaspc.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ ")"
				+ " OR (quaspc.repeaters_only = 'Y' and quaspc.repeaters_from_yea<"
				+ year
				+ ")"
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END spec_repeat,"
				+ " grd.under_post_categor UNDER_POST, grd.type TYPE, grd.research_degree_flag RESEARCH,"
				+ " grd.nqf_exit_level NQF_EXIT_LEVEL, grd.nqf_credits NQF_CREDITS,"
				+ " quaspc.admission_require, grd.fk_katcode CAT_CODE, kat.eng_description CAT_NAME,"
				+ " grd.from_year QUAL_FROM_YEAR, grd.to_year QUAL_TO_YEAR,"
				+ " quaspc.from_year SPEC_FROM_YEAR, quaspc.to_year SPEC_TO_YEAR, grd.minimum_time,"
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1),' ')\"RULE LINE1\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=2),' ')\"RULE LINE2\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=3),' ')\"RULE LINE3\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=4),' ')\"RULE LINE4\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=5),' ')\"RULE LINE5\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=6),' ')\"RULE LINE6\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=7),' ')\"RULE LINE7\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=8),' ')\"RULE LINE8\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=9),' ')\"RULE LINE9\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=10),' ')\"RULE LINE10\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=11),' ')\"RULE LINE11\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=12),' ')\"RULE LINE12\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=13),' ')\"RULE LINE13\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=14),' ')\"RULE LINE14\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=15),' ')\"RULE LINE15\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=16),' ')\"RULE LINE16\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=17),' ')\"RULE LINE17\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=18),' ')\"RULE LINE18\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=19),' ')\"RULE LINE19\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=20),' ')\"RULE LINE20\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=21),' ')\"RULE LINE21\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=22),' ')\"RULE LINE22\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=23),' ')\"RULE LINE23\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=24),' ')\"RULE LINE24\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=25),' ')\"RULE LINE25\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=26),' ')\"RULE LINE26\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=27),' ')\"RULE LINE27\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=28),' ')\"RULE LINE28\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=29),' ')\"RULE LINE29\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=30),' ')\"RULE LINE30\","
				+ " nvl((select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=31),' ')\"RULE LINE31\","
				+ " gencod.afr_description CAT_GRP, gencod.eng_description CATEGORY_GROUP"
				+ " FROM quaspc, grd, colleg, kat, gencod"
				+ " WHERE quaspc.in_use_flag = 'Y'"
				+ " AND quaspc.mk_qualification_c = grd.code"
				+ " AND grd.in_use_flag = 'Y'"
				+ " AND (grd.to_year = 0 OR grd.to_year > "
				+ lastyr
				+ ")"
				+ " AND grd.from_year < "
				+ nextyr
				+ " AND grd.TYPE <> 'S'"
				+ " AND grd.under_post_categor IN ('M','D')"
				+ " AND quaspc.college_code = colleg.code";
		if (!catCode.equals("-1")) {
			query = query + " AND kat.code=" + catCode;
		} else {
		}
		if (!code.equals("-1")) {
			query = query + " AND colleg.code=" + code;
		} else {
		}
		if (!schCode.equals("-1")) {
			query = query + " and grd.school_code=" + schCode;

		} else {
		}
		if (!dptCode.equals("-1")) {
			query = query + " and grd.MK_ACAD_DEPT_CODE=" + dptCode;
		} else {
		}
		if (researchFlag.equals("Y")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG='Y'";
		} else if (researchFlag.equals("N")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG=' '";
		} else if (researchFlag.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (heqfComp.equals("Y")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG='Y'";
		} else if (heqfComp.equals("N")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG=' '";
		} else if (heqfComp.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (!nqfLevel.equals("-1")) {
			query = query + " and grd.NQF_EXIT_LEVEL=" + nqfLevel;

		} else {
		}
		if (!qualificationCode.equals("-1")) {
			query = query + " AND grd.CODE=" + "'" + qualificationCode + "'";
		} else {
		}
		query = query
				+ " AND grd.fk_katcode = kat.code"
				+ " and grd.fk_katcode = gencod.code"
				+ " and gencod.fk_gencatcode=172"
				+
				// " ORDER BY colleg.abbreviation,SCHOOLDESCR,DEPT, grd.under_post_categor, gencod.eng_description, grd.TYPE, grd.code";
				" ORDER BY colleg.abbreviation, SCHOOLDESCR, DEPT, grd.under_post_categor, grd.TYPE, gencod.eng_description, kat.eng_description,grd.code";

		//System.out.println("############# | MyChoiceM&D sql Results | #################"+query);
		
		try {

			MyChoiceMDXmlBuilder builder = new MyChoiceMDXmlBuilder();
			return builder.buildXmlFile(query);

		} catch (Exception ex) {
			/*System.out.println("Problem : " + ex);*/
			throw new Exception(
					"za.ac.unisa.lms.tools.brochures.dao.MychoiceDA0: myChoiceXml: Error occurred / "
							+ ex, ex);
		}
	}

	/*
	 * data is empty or not
	 */
	public boolean ismyChoiceMandD(String code, String catCode, int year,
			String schCode, String dptCode, String researchFlag,
			String heqfComp, String nqfLevel, String qualificationCode)
			throws Exception {

		int lastyr = year - 1;
		int nextyr = year + 1;
	

		String query = "SELECT COLLEG.ABBREVIATION COLL, COLLEG.eng_description COLLEGE_DESCR,"
				+ " colleg.code code,"
				+ " grd.school_code schoolcode,"
				//New APS Score Element
				//+ "GRD.APS_SCORE aps_score,"
				+ " (select school.eng_description from school where code =grd.school_code and SCHOOL.COLLEGE_CODE=colleg.code) SCHOOLDESCR,"
				+ " GRD.MK_ACAD_DEPT_CODE deptcode,"
				+ " (select dpt.eng_description from dpt where code=grd.MK_ACAD_DEPT_CODE) DEPT,"
				+ " QUASPC.MK_QUALIFICATION_C QUAL_CODE, grd.long_eng_descripti QUAL_DESCR,"
				//+ " replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (grd.long_eng_descripti),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),"
				//+ " 'With','with'),'Hiv','HIV'),'Aids','AIDS'),'Phd','PhD'),' To ',' to '),"
				//+ " 'Specialisation','specialisation'),'Endorsement','endorsement'),'Tesol','TESOL') QUAL_DESCR,"
				+ " CASE" + " WHEN grd.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END qual_repeat,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.speciality_code"
				+ " ELSE ' '"
				+ " END SPEC_CODE,"
				+ " CASE"
				+ " WHEN quaspc.speciality_code > ' '"
				+ " THEN quaspc.english_descriptio"
				//+ " THEN replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(replace(NLS_INITCAP (quaspc.english_descriptio),' For ',' for '),"
				//+ " ' And ',' and '),' Of ',' of '),' Or ',' or '),' In ',' in '),'With','with'),'From','from'),' As ',' as '),' To ',' to '),'Prior','prior'),'Hiv','HIV'),'Aids','AIDS'),"
				//+ " ' Curriculum',' curriculum'),' Dissertation',' dissertation'),' Limited Scope',' limited scope'),' A ',' a ')"
				+ " ELSE ' '"
				+ " END SPEC_DESCR,"
				+ " CASE"
				+ " WHEN (quaspc.repeaters_from_yea BETWEEN 1 AND "
				+ year
				+ ")"
				+ " OR (quaspc.repeaters_only = 'Y' and quaspc.repeaters_from_yea<"
				+ year
				+ ")"
				+ " THEN 'R'"
				+ " ELSE ' '"
				+ " END spec_repeat,"
				+ " grd.under_post_categor UNDER_POST, grd.type TYPE, grd.research_degree_flag RESEARCH_DEGREE,"
				+ " grd.nqf_exit_level NQF_EXIT_LEVEL, grd.nqf_credits NQF_CREDITS,"
				+ " quaspc.admission_require, grd.fk_katcode CAT_CODE, kat.eng_description CAT_NAME,"
				+ " grd.from_year QUAL_FROM_YEAR, grd.to_year QUAL_TO_YEAR,"
				+ " quaspc.from_year SPEC_FROM_YEAR, quaspc.to_year SPEC_TO_YEAR, grd.minimum_time,"
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=1)\"RULE LINE1\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=2)\"RULE LINE2\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=3)\"RULE LINE3\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=4)\"RULE LINE4\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=5)\"RULE LINE5\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=6)\"RULE LINE6\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=7)\"RULE LINE7\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=8)\"RULE LINE8\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=9)\"RULE LINE9\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=10)\"RULE LINE10\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=11)\"RULE LINE11\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=12)\"RULE LINE12\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=13)\"RULE LINE13\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=14)\"RULE LINE14\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=15)\"RULE LINE15\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=16)\"RULE LINE16\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=17)\"RULE LINE17\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=18)\"RULE LINE18\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=19)\"RULE LINE19\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=20)\"RULE LINE20\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=21)\"RULE LINE21\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=22)\"RULE LINE22\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=23)\"RULE LINE23\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=24)\"RULE LINE24\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=25)\"RULE LINE25\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=26)\"RULE LINE26\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=27)\"RULE LINE27\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=28)\"RULE LINE28\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=29)\"RULE LINE29\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=30)\"RULE LINE30\","
				+ " (select text0 from qsprul where mk_qualification_c=quaspc.mk_qualification_c and mk_speciality_code=quaspc.speciality_code and seq_no=31)\"RULE LINE31\","
				+ " gencod.afr_description CAT_GRP, gencod.eng_description CATEGORY_GROUP"
				+ " FROM quaspc, grd, colleg, kat, gencod"
				+ " WHERE quaspc.in_use_flag = 'Y'"
				+ " AND quaspc.mk_qualification_c = grd.code"
				+ " AND grd.in_use_flag = 'Y'"
				+ " AND (grd.to_year = 0 OR grd.to_year > "
				+ lastyr
				+ ")"
				+ " AND grd.from_year < "
				+ nextyr
				+ " AND grd.TYPE <> 'S'"
				+ " AND grd.under_post_categor IN ('M','D','H')"
				+ " AND quaspc.college_code = colleg.code";
		if (!catCode.equals("-1")) {
			query = query + " AND kat.code=" + catCode;
		} else {
		}
		if (!code.equals("-1")) {
			query = query + " AND colleg.code=" + code;
		} else {
		}
		if (!schCode.equals("-1")) {
			query = query + " and grd.school_code=" + schCode;

		} else {
		}
		if (!dptCode.equals("-1")) {
			query = query + " and GRD.MK_DEPARTMENT_CODE=" + dptCode;
		} else {
		}
		if (researchFlag.equals("Y")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG='Y'";
		} else if (researchFlag.equals("N")) {
			query = query + " AND grd.RESEARCH_DEGREE_FLAG=' '";
		} else if (researchFlag.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (heqfComp.equals("Y")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG='Y'";
		} else if (heqfComp.equals("N")) {
			query = query + " AND grd.PQM_COMPLIANT_FLAG=' '";
		} else if (heqfComp.equals("B")) {
			// query =
			// query+" AND (grd.PQM_COMPLIANT_FLAG='Y' or grd.PQM_COMPLIANT_FLAG=' ')";
			query = query;
		}
		if (!nqfLevel.equals("-1")) {
			query = query + " and grd.NQF_EXIT_LEVEL=" + nqfLevel;

		} else {
		}
		if (!qualificationCode.equals("-1")) {
			query = query + " AND grd.CODE=" + "'" + qualificationCode + "'";
		} else {
		}
		query = query
				+ " AND grd.fk_katcode = kat.code"
				+ " and gencod.fk_gencatcode=172"
				+ " ORDER BY colleg.abbreviation,SCHOOLDESCR,DEPT, grd.under_post_categor, gencod.eng_description, grd.TYPE, grd.code";

		//System.out.println("IS my My Choice M&D sql+++++++++++++--"+query);
		
		try {
			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
			List qualInfo = jdt.queryForList(query);
			Iterator i = qualInfo.iterator();
			boolean qualList = qualInfo.isEmpty();
			return qualList;
		} catch (Exception ex) {
			throw new Exception("ismyChoiceMandD" + ex, ex);
		}	
		
	}

	/*
	 * myRegistration
	 */
	public Document myRegistration(String schCode, String dptCode,
			String researchFlag, String collCode, String catCode,
			String qualificationCode, String special, int year, int repeatYear,
			String repeat, String heqfComp, String reglevel) throws Exception {

		BrochureQueries bquery = new BrochureQueries();
		String query;

		query = bquery.myRegQuery(schCode, dptCode, researchFlag, collCode,
				catCode, qualificationCode, special, year, repeatYear, repeat,
				heqfComp, reglevel);

		System.out.println("My Registration sql+++++++++++++--"+query);

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());

			List qualInfo = jdt.queryForList(query);

			myReg = qualInfo.isEmpty();

			Iterator i = qualInfo.iterator();
			BrochureDetails brochureDetails = new BrochureDetails();
			brochureDetails.setData(qualInfo.isEmpty());

			String collegeName = "";
			String qual = "";
			String qualificationLevel = "";
			String qualificationType = "";
			String curricu = "";
			String qualificationGroup = "";
			String qualificationCategory = "";
			String qualificationChoice = "";
			String cat_code = "";
			String qualcode = "";
			String qualname = "";
			String spesCode = "";
			String foundationFlag = "";

			Element colleges = document.createElement("colleges");
			document.appendChild(colleges);

			Element qualification = null;
			Element qualLevel = null;
			Element college = null;
			Element qualificationchoice = null;
			Element qualificationgroup = null;
			Element qualificationcategory = null;
			Element module = null;

			while (i.hasNext()) {

				boolean repeaterAllow = false;

				ListOrderedMap data = (ListOrderedMap) i.next();

				String qual_repeat = data.get("qual_repeat").toString();
				String spec_repeat = data.get("spec_repeat").toString();

				if (repeat.equals("1")) {

					repeaterAllow = true;

				} else if (repeat.equals("2")) {

					if (!(qual_repeat.equals("R") || spec_repeat.equals("R"))) {

						repeaterAllow = true;

					} else {

						repeaterAllow = false;
					}
				}

				if (repeaterAllow == true) {

					if (!collegeName.equals(data.get("COLLEGE_DESCR")
							.toString())) {

						college = document.createElement("college");
						colleges.appendChild(college);
						collegeName = data.get("COLLEGE_DESCR").toString();

						Element collName = document.createElement("name");
						collName.appendChild(document
								.createTextNode(collegeName));
						college.appendChild(collName);
					}
					if (!qualificationGroup.equals(ltrim(rtrim(data.get(
							"CATEGORY_GROUP").toString())))) {

						qualificationGroup = data.get("CATEGORY_GROUP")
								.toString();
						cat_code = data.get("CAT_GRP").toString();
						qualificationgroup = document
								.createElement("qualificationgroup");
						college.appendChild(qualificationgroup);

						Element qualGroup = document.createElement("name");
						qualGroup.appendChild(document
								.createTextNode(qualificationGroup));
						qualificationgroup.appendChild(qualGroup);

					}
					if (!qualificationCategory.equals(data.get("catname")
							.toString())) {

						try {

							qualificationCategory = data.get("catname")
									.toString();

						} catch (NullPointerException ne) {
						}

						qualificationcategory = document
								.createElement("qualificationcategory");
						qualificationgroup.appendChild(qualificationcategory);

						Element qualCat = document.createElement("name");
						qualCat.appendChild(document
								.createTextNode(qualificationCategory));
						qualificationcategory.appendChild(qualCat);

					}

					boolean temp1 = false;

					if (!(qualname.equals(data.get("QUALDESC").toString()))
							|| !(spesCode.equals(data.get("SPES").toString()))) {

						qualification = document.createElement("qualification");
						qualificationcategory.appendChild(qualification);
						qualcode = data.get("QUAL").toString();

						Element qualName = document.createElement("name");
						qualname = data.get("QUALDESC").toString();
						qualName.appendChild(document.createTextNode(data.get(
								"QUALDESC").toString()));

						Element qualCode = document.createElement("qualCode");
						qualCode.appendChild(document.createTextNode(qualcode));

						Element admission = document.createElement("admission");

						try {

							admission.appendChild(document
									.createTextNode(rtrim(data.get(
											"admission_require").toString())));

						} catch (NullPointerException e) {
						}

						Element spes = document.createElement("spescode");
						spes.appendChild(document.createTextNode(data.get(
								"SPES").toString()));

						Element spesDesc = document.createElement("spesdesc");
						spesDesc.appendChild(document.createTextNode(data.get(
								"SPESDESC").toString()));
						spesCode = data.get("SPES").toString();

						Element nqfl = document.createElement("NQFel");
						nqfl.appendChild(document.createTextNode(data.get(
								"NQFLVL").toString()));
					
						Element credits = document.createElement("NQFc");
						credits.appendChild(document.createTextNode(data.get(
								"CREDS").toString()));

						Element qualrep = document.createElement("qualrep");
						qualrep.appendChild(document.createTextNode(data.get(
								"QUAL_REPEAT").toString()));

						Element specRepeat = document.createElement("spesrep");
						specRepeat.appendChild(document.createTextNode(data
								.get("SPEC_REPEAT").toString()));

						Element toyear = document.createElement("toyear");
						toyear.appendChild(document.createTextNode(data.get(
								"TO_YEAR").toString()));

						Element rules = document.createElement("rules");

						StringBuilder rulesBuilder = new StringBuilder();

						if (data.get("RULE LINE1") != null)
							rulesBuilder.append(data.get("RULE LINE1").toString());
						if (data.get("RULE LINE2") != null)
							rulesBuilder.append(data.get("RULE LINE2")
									.toString());
						if (data.get("RULE LINE3") != null)
							rulesBuilder.append(data.get("RULE LINE3")
									.toString());
						if (data.get("RULE LINE4") != null)
							rulesBuilder.append(data.get("RULE LINE4")
									.toString());
						if (data.get("RULE LINE5") != null)
							rulesBuilder.append(data.get("RULE LINE5")
									.toString());
						if (data.get("RULE LINE6") != null)
							rulesBuilder.append(data.get("RULE LINE6")
									.toString());
						if (data.get("RULE LINE7") != null)
							rulesBuilder.append(data.get("RULE LINE7")
									.toString());
						if (data.get("RULE LINE8") != null)
							rulesBuilder.append(data.get("RULE LINE8")
									.toString());
						if (data.get("RULE LINE9") != null)
							rulesBuilder.append(data.get("RULE LINE9")
									.toString());
						if (data.get("RULE LINE10") != null)
							rulesBuilder.append(data.get("RULE LINE10")
									.toString());
						if (data.get("RULE LINE11") != null)
							rulesBuilder.append(data.get("RULE LINE11")
									.toString());
						if (data.get("RULE LINE12") != null)
							rulesBuilder.append(data.get("RULE LINE12")
									.toString());
						if (data.get("RULE LINE13") != null)
							rulesBuilder.append(data.get("RULE LINE13")
									.toString());
						if (data.get("RULE LINE14") != null)
							rulesBuilder.append(data.get("RULE LINE14")
									.toString());
						if (data.get("RULE LINE15") != null)
							rulesBuilder.append(data.get("RULE LINE15")
									.toString());
						if (data.get("RULE LINE16") != null)
							rulesBuilder.append(data.get("RULE LINE16")
									.toString());
						if (data.get("RULE LINE17") != null)
							rulesBuilder.append(data.get("RULE LINE17")
									.toString());
						if (data.get("RULE LINE18") != null)
							rulesBuilder.append(data.get("RULE LINE18")
									.toString());
						if (data.get("RULE LINE19") != null)
							rulesBuilder.append(data.get("RULE LINE19")
									.toString());
						if (data.get("RULE LINE20") != null)
							rulesBuilder.append(data.get("RULE LINE20")
									.toString());
						if (data.get("RULE LINE21") != null)
							rulesBuilder.append(data.get("RULE LINE21")
									.toString());
						if (data.get("RULE LINE22") != null)
							rulesBuilder.append(data.get("RULE LINE22")
									.toString());
						if (data.get("RULE LINE23") != null)
							rulesBuilder.append(data.get("RULE LINE23")
									.toString());
						if (data.get("RULE LINE24") != null)
							rulesBuilder.append(data.get("RULE LINE24")
									.toString());
						if (data.get("RULE LINE25") != null)
							rulesBuilder.append(data.get("RULE LINE25")
									.toString());
						if (data.get("RULE LINE26") != null)
							rulesBuilder.append(data.get("RULE LINE26")
									.toString());
						if (data.get("RULE LINE27") != null)
							rulesBuilder.append(data.get("RULE LINE27")
									.toString());
						if (data.get("RULE LINE28") != null)
							rulesBuilder.append(data.get("RULE LINE28")
									.toString());
						if (data.get("RULE LINE29") != null)
							rulesBuilder.append(data.get("RULE LINE29")
									.toString());
						if (data.get("RULE LINE30") != null)
							rulesBuilder.append(data.get("RULE LINE30")
									.toString());
						if (data.get("RULE LINE31") != null)
							rulesBuilder.append(data.get("RULE LINE31")
									.toString());

						rules.appendChild(document
								.createTextNode(rtrim(rulesBuilder.toString())));

						Element deliveryMode = document
								.createElement("deliverymode");

						try {

							deliveryMode.appendChild(document
									.createTextNode(data.get("DELIVERY_MODE")
											.toString()));
						} catch (NullPointerException e) {
						}

						Element purpose_statement = document
								.createElement("purpose_statement");

						try {

							purpose_statement.appendChild(document
									.createTextNode(data.get(
											"purpose_statement").toString()));

						} catch (NullPointerException e) {
						}
						/*//New APS Score from Element 42
						Element apsScore = document.createElement("aps_score");
						
						if(foundationFlag.equals(data.get("foundation_flag").toString() == "N")){
							
							try {			
								apsScore.appendChild(document.createTextNode(data.get(
										"aps_score").toString()));

							} catch (NullPointerException e) {
								
							}	
						}
						else{
							//New APS Score from Element 391
											
							try {			
								apsScore.appendChild(document.createTextNode(data.get(
										"aps_score_1").toString()));

							} catch (NullPointerException e) {
								
							}
						}*/
						//Using SpecDescr
						//New APS Score Element 42
						Element apsScore = document.createElement("aps_score");
						
						if(spesDesc.equals(data.get("SPESDESC").toString() == null)){
							
							try {			
								apsScore.appendChild(document.createTextNode(data.get(
										"aps_score").toString()));

							} catch (NullPointerException e) {
								
							}	
						}
						else{
							//New APS Score Element 391
											
							try {			
								apsScore.appendChild(document.createTextNode(data.get(
										"aps_score_1").toString()));

							} catch (NullPointerException e) {
								
							}
						}
						try {

							qualification.appendChild(qualName);

						} catch (NullPointerException ne) {
						}

						//SAQA ID
						Element saqaId = document.createElement("saqa_id");
						try {
							saqaId.appendChild(document.createTextNode(data.get(
									"saqa_id").toString()));
						} catch (NullPointerException ne) {
						}
						qualification.appendChild(qualCode);
						qualification.appendChild(admission);
						qualification.appendChild(spes);
						qualification.appendChild(spesDesc);
						qualification.appendChild(nqfl);
						qualification.appendChild(apsScore);
						qualification.appendChild(saqaId);
						qualification.appendChild(credits);
						qualification.appendChild(qualrep);
						qualification.appendChild(specRepeat);
						qualification.appendChild(toyear);
						qualification.appendChild(rules);
						qualification.appendChild(deliveryMode);
						qualification.appendChild(purpose_statement);
						temp1 = true;
					}
					boolean temp = false;

					if (temp1 == true) {

						qualLevel = document
								.createElement("qualificationlevel");
						qualification.appendChild(qualLevel);
						qualificationLevel = data.get("LVL").toString();

						Element level = document.createElement("name");
						level.appendChild(document.createTextNode(data.get(
								"LVL").toString()));
						qualLevel.appendChild(level);

						temp = true;
						
						generateXMLRulesPerStudyLevel(data, document, qualLevel);

					} else {

						if (!qualificationLevel.equals(data.get("LVL")
								.toString())) {
							qualLevel = document
									.createElement("qualificationlevel");
							qualification.appendChild(qualLevel);
							qualificationLevel = data.get("LVL").toString();

							Element level = document.createElement("name");
							level.appendChild(document.createTextNode(data.get(
									"LVL").toString()));
							qualLevel.appendChild(level);

							temp = true;
							
							generateXMLRulesPerStudyLevel(data, document, qualLevel);
						}
					}
					
					

					if (temp == true) {

						qualificationchoice = document
								.createElement("qualificationchoice");
						qualLevel.appendChild(qualificationchoice);
						qualificationChoice = data.get("GROUPNAME").toString();
						Element name = document.createElement("name");

						try {

							name.appendChild(document
									.createTextNode(qualificationChoice));
							qualificationchoice.appendChild(name);

						} catch (NullPointerException e) {
						}
					} else {
						if (!qualificationChoice.equals(data.get("GROUPNAME")
								.toString())) {
							qualificationchoice = document
									.createElement("qualificationchoice");
							qualLevel.appendChild(qualificationchoice);
							qualificationChoice = data.get("GROUPNAME")
									.toString();

							Element name = document.createElement("name");

							try {

								name.appendChild(document
										.createTextNode(qualificationChoice));
								qualificationchoice.appendChild(name);

							} catch (NullPointerException e) {
							}

						}
					}

					module = document.createElement("module");
					qualificationchoice.appendChild(module);

					Element modName = document.createElement("name");
					modName.appendChild(document.createTextNode(data.get(
							"MODULE1").toString()));

					Element modCode = document.createElement("code");
					modCode.appendChild(document.createTextNode(data.get(
							"MODULE").toString()));

					Element preReq = document.createElement("prereq");

					try {

						preReq.appendChild(document.createTextNode(data.get(
								"Pre-requisite").toString()));

					} catch (NullPointerException ne) {
					}

					Element coReq = document.createElement("coreq");

					try {

						coReq.appendChild(document.createTextNode(data.get(
								"Co-requisite").toString()));

					} catch (NullPointerException ne) {
					}

					Element recommendation = document
							.createElement("recommendation");

					try {

						recommendation.appendChild(document.createTextNode(data
								.get("Recommendation").toString()));

					} catch (NullPointerException ne) {
					}

					Element fromYear = document.createElement("fromYear");
					fromYear.appendChild(document.createTextNode(data.get(
							"mod_from_year").toString()));

					module.appendChild(modName);
					module.appendChild(modCode);
					module.appendChild(preReq);
					module.appendChild(coReq);
					module.appendChild(recommendation);
					module.appendChild(fromYear);
					temp = false;
					temp1 = false;

				}

			}

			return document;

		} catch (Exception ex) {

			/*System.out.println("Problem : " + ex);*/
			throw new Exception(
					"za.ac.unisa.lms.tools.brochures.dao.MychoiceDA0: myRegistration: Error occurred while trying to retrieve myRegistration Brochure::: "
							+ ex, ex);

		}
	}

	/*
	 * myReg M&D
	 */
	public Document myRegistrationMD(String schCode, String dptCode,
			String researchFlag, String collCode, String catCode,
			String qualificationCode, String special, int year, int repeatYear,
			String repeat, String heqfComp, String reglevel) throws Exception {

		BrochureQueries bquery = new BrochureQueries();
		String query;

		query = bquery.myRegMDQuery(schCode, dptCode, researchFlag, collCode,
				catCode, qualificationCode, special, year, repeatYear, repeat,
				heqfComp, reglevel);

		//System.out.println("sql--"+query);

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());

			List qualInfo = jdt.queryForList(query);

			myReg = qualInfo.isEmpty();

			Iterator i = qualInfo.iterator();
			BrochureDetails brochureDetails = new BrochureDetails();
			brochureDetails.setData(qualInfo.isEmpty());

			String collegeName = "";
			String qual = "";
			String qualificationLevel = "";
			String qualificationType = "";
			String curricu = "";
			String qualificationGroup = "";
			String qualificationCategory = "";
			String qualificationChoice = "";
			String cat_code = "";
			String qualcode = "";
			String qualname = "";
			String spesCode = "";

			Element colleges = document.createElement("colleges");
			document.appendChild(colleges);

			Element qualification = null;
			Element qualLevel = null;
			Element college = null;
			Element qualificationchoice = null;
			Element qualificationgroup = null;
			Element qualificationcategory = null;
			Element module = null;

			while (i.hasNext()) {

				try {

					boolean repeaterAllow = false;

					ListOrderedMap data = (ListOrderedMap) i.next();

					String qual_repeat = data.get("qual_repeat").toString();
					String spec_repeat = data.get("spec_repeat").toString();

					if (repeat.equals("1")) {

						repeaterAllow = true;

					} else if (repeat.equals("2")) {

						if (!(qual_repeat.equals("R") || spec_repeat
								.equals("R"))) {

							repeaterAllow = true;

						} else {

							repeaterAllow = false;
						}
					}
					if (repeaterAllow == true) {

						if (!collegeName.equals(data.get("COLLEGE_DESCR")
								.toString())) {

							college = document.createElement("college");
							colleges.appendChild(college);

							collegeName = data.get("COLLEGE_DESCR").toString();
							Element collName = document.createElement("name");
							collName.appendChild(document
									.createTextNode(collegeName));
							college.appendChild(collName);
						}

						if (!qualificationGroup.equals(ltrim(rtrim(data.get(
								"CATEGORY_GROUP").toString())))) {

							qualificationGroup = data.get("CATEGORY_GROUP")
									.toString();

							try {

								cat_code = data.get("CAT_GRP").toString();

							} catch (NullPointerException ne) {
							}

							qualificationgroup = document
									.createElement("qualificationgroup");
							college.appendChild(qualificationgroup);

							Element qualGroup = document.createElement("name");
							qualGroup.appendChild(document
									.createTextNode(qualificationGroup));
							qualificationgroup.appendChild(qualGroup);

						}

						if (!qualificationCategory.equals(data.get("catname")
								.toString())) {

							try {

								qualificationCategory = data.get("catname")
										.toString();

							} catch (NullPointerException ne) {
							}

							qualificationcategory = document
									.createElement("qualificationcategory");
							qualificationgroup
									.appendChild(qualificationcategory);

							Element qualCat = document.createElement("name");
							qualCat.appendChild(document
									.createTextNode(qualificationCategory));
							qualificationcategory.appendChild(qualCat);

						}

						boolean temp1 = false;

						if (!(qualname.equals(data.get("QUALDESC").toString()))
								|| !(spesCode.equals(data.get("SPES")
										.toString()))) {

							qualification = document
									.createElement("qualification");
							qualificationcategory.appendChild(qualification);
							qualcode = data.get("QUAL").toString();

							Element qualName = document.createElement("name");
							qualname = data.get("QUALDESC").toString();
							qualName.appendChild(document.createTextNode(data
									.get("QUALDESC").toString()));

							Element qualCode = document
									.createElement("qualCode");
							qualCode.appendChild(document
									.createTextNode(qualcode));

							Element admission = document
									.createElement("admission");

							try {

								admission
										.appendChild(document
												.createTextNode(rtrim(data.get(
														"admission_require")
														.toString())));

							} catch (NullPointerException e) {
							}

							Element spes = document.createElement("spescode");
							spes.appendChild(document.createTextNode(data.get(
									"SPES").toString()));

							Element spesDesc = document
									.createElement("spesdesc");
							spesDesc.appendChild(document.createTextNode(data
									.get("SPESDESC").toString()));
							spesCode = data.get("SPES").toString();

							Element nqfl = document.createElement("NQFel");
							nqfl.appendChild(document.createTextNode(data.get(
									"NQFLVL").toString()));

							Element credits = document.createElement("NQFc");
							credits.appendChild(document.createTextNode(data
									.get("CREDS").toString()));

							Element qualrep = document.createElement("qualrep");
							qualrep.appendChild(document.createTextNode(data
									.get("QUAL_REPEAT").toString()));

							Element specRepeat = document
									.createElement("spesrep");
							specRepeat.appendChild(document.createTextNode(data
									.get("SPEC_REPEAT").toString()));

							Element toyear = document.createElement("toyear");
							toyear.appendChild(document.createTextNode(data
									.get("TO_YEAR").toString()));

							Element rules = document.createElement("rules");

							StringBuilder rulesBuilder = new StringBuilder();

							if (data.get("RULE LINE1") != null) {
								rulesBuilder.append(data.get("RULE LINE1")
										.toString());
							}
							if (data.get("RULE LINE2") != null) {
								rulesBuilder.append(data.get("RULE LINE2")
										.toString());
							}
							if (data.get("RULE LINE3") != null) {
								rulesBuilder.append(data.get("RULE LINE3")
										.toString());
							}
							if (data.get("RULE LINE4") != null) {
								rulesBuilder.append(data.get("RULE LINE4")
										.toString());
							}
							if (data.get("RULE LINE5") != null) {
								rulesBuilder.append(data.get("RULE LINE5")
										.toString());
							}
							if (data.get("RULE LINE6") != null) {
								rulesBuilder.append(data.get("RULE LINE6")
										.toString());
							}
							if (data.get("RULE LINE7") != null) {
								rulesBuilder.append(data.get("RULE LINE7")
										.toString());
							}
							if (data.get("RULE LINE8") != null) {
								rulesBuilder.append(data.get("RULE LINE8")
										.toString());
							}
							if (data.get("RULE LINE9") != null) {
								rulesBuilder.append(data.get("RULE LINE9")
										.toString());
							}
							if (data.get("RULE LINE10") != null) {
								rulesBuilder.append(data.get("RULE LINE10")
										.toString());
							}
							if (data.get("RULE LINE11") != null) {
								rulesBuilder.append(data.get("RULE LINE11")
										.toString());
							}
							if (data.get("RULE LINE12") != null) {
								rulesBuilder.append(data.get("RULE LINE12")
										.toString());
							}
							if (data.get("RULE LINE13") != null) {
								rulesBuilder.append(data.get("RULE LINE13")
										.toString());
							}
							if (data.get("RULE LINE14") != null) {
								rulesBuilder.append(data.get("RULE LINE14")
										.toString());
							}
							if (data.get("RULE LINE15") != null) {
								rulesBuilder.append(data.get("RULE LINE15")
										.toString());
							}
							if (data.get("RULE LINE16") != null) {
								rulesBuilder.append(data.get("RULE LINE16")
										.toString());
							}
							if (data.get("RULE LINE17") != null) {
								rulesBuilder.append(data.get("RULE LINE17")
										.toString());
							}
							if (data.get("RULE LINE18") != null) {
								rulesBuilder.append(data.get("RULE LINE18")
										.toString());
							}
							if (data.get("RULE LINE19") != null) {
								rulesBuilder.append(data.get("RULE LINE19")
										.toString());
							}
							if (data.get("RULE LINE20") != null) {
								rulesBuilder.append(data.get("RULE LINE20")
										.toString());
							}
							if (data.get("RULE LINE21") != null) {
								rulesBuilder.append(data.get("RULE LINE21")
										.toString());
							}
							if (data.get("RULE LINE22") != null) {
								rulesBuilder.append(data.get("RULE LINE22")
										.toString());
							}
							if (data.get("RULE LINE23") != null) {
								rulesBuilder.append(data.get("RULE LINE23")
										.toString());
							}
							if (data.get("RULE LINE24") != null) {
								rulesBuilder.append(data.get("RULE LINE24")
										.toString());
							}
							if (data.get("RULE LINE25") != null) {
								rulesBuilder.append(data.get("RULE LINE25")
										.toString());
							}
							if (data.get("RULE LINE26") != null) {
								rulesBuilder.append(data.get("RULE LINE26")
										.toString());
							}
							if (data.get("RULE LINE27") != null) {
								rulesBuilder.append(data.get("RULE LINE27")
										.toString());
							}
							if (data.get("RULE LINE28") != null) {
								rulesBuilder.append(data.get("RULE LINE28")
										.toString());
							}
							if (data.get("RULE LINE29") != null) {
								rulesBuilder.append(data.get("RULE LINE29")
										.toString());
							}
							if (data.get("RULE LINE30") != null) {
								rulesBuilder.append(data.get("RULE LINE30")
										.toString());
							}
							if (data.get("RULE LINE31") != null) {
								rulesBuilder.append(data.get("RULE LINE31")
										.toString());
							}

							rules.appendChild(document
									.createTextNode(rtrim(rulesBuilder
											.toString())));

							Element deliveryMode = document
									.createElement("deliverymode");

							try {

								toyear.appendChild(document.createTextNode(data
										.get("DELIVERY_MODE").toString()));
							} catch (NullPointerException e) {
							}
							
							//New APS Score Element
							/*Element apsScore = document.createElement("aps_score");
							
							try {			
								apsScore.appendChild(document.createTextNode(data.get(
										"aps_score").toString()));

							} catch (NullPointerException e) {
								
							}*/

							Element purpose_statement = document
									.createElement("purpose_statement");

							try {

								purpose_statement
										.appendChild(document
												.createTextNode(data.get(
														"purpose_statement")
														.toString()));

							} catch (NullPointerException e) {
							}

							try {

								qualification.appendChild(qualName);

							} catch (NullPointerException ne) {
							}
							//SAQA ID
							Element saqaId = document.createElement("saqa_id");
							try {
								saqaId.appendChild(document.createTextNode(data.get(
										"saqa_id").toString()));
							} catch (NullPointerException ne) {
							}
							qualification.appendChild(qualCode);
							qualification.appendChild(admission);
							qualification.appendChild(spes);
							qualification.appendChild(spesDesc);
							qualification.appendChild(nqfl);
							qualification.appendChild(saqaId);
							qualification.appendChild(credits);
							qualification.appendChild(qualrep);
							qualification.appendChild(specRepeat);
							qualification.appendChild(toyear);
							qualification.appendChild(rules);
							qualification.appendChild(deliveryMode);
							qualification.appendChild(purpose_statement);

							temp1 = true;
						}
						boolean temp = false;

						if (temp1 == true) {

							qualLevel = document
									.createElement("qualificationlevel");
							qualification.appendChild(qualLevel);
							qualificationLevel = data.get("LVL").toString();

							Element level = document.createElement("name");
							level.appendChild(document.createTextNode(data.get(
									"LVL").toString()));
							qualLevel.appendChild(level);

							temp = true;
							
													} else {

							if (!qualificationLevel.equals(data.get("LVL")
									.toString())) {

								qualLevel = document
										.createElement("qualificationlevel");
								qualification.appendChild(qualLevel);
								qualificationLevel = data.get("LVL").toString();

								Element level = document.createElement("name");
								level.appendChild(document.createTextNode(data
										.get("LVL").toString()));
								qualLevel.appendChild(level);

								temp = true;
							}
						}

						if (temp == true) {

							qualificationchoice = document
									.createElement("qualificationchoice");
							qualLevel.appendChild(qualificationchoice);

							try {

								qualificationChoice = data.get("GROUPNAME")
										.toString();

							} catch (NullPointerException e) {
							}

							Element name = document.createElement("name");
							try {

								name.appendChild(document
										.createTextNode(qualificationChoice));
								qualificationchoice.appendChild(name);

							} catch (NullPointerException e) {
							}

						} else {

							try {

								if (!qualificationChoice.equals(data.get(
										"GROUPNAME").toString())) {

									qualificationchoice = document
											.createElement("qualificationchoice");
									qualLevel.appendChild(qualificationchoice);

									try {

										qualificationChoice = data.get(
												"GROUPNAME").toString();

									} catch (NullPointerException e) {
									}

									Element name = document
											.createElement("name");

									try {

										name.appendChild(document
												.createTextNode(qualificationChoice));
										qualificationchoice.appendChild(name);

									} catch (NullPointerException e) {
									}

								}

							} catch (NullPointerException e) {
							}
						}

						module = document.createElement("module");
						qualificationchoice.appendChild(module);

						Element modName = document.createElement("name");
						modName.appendChild(document.createTextNode(data.get(
								"MODULE1").toString()));

						Element modCode = document.createElement("code");
						modCode.appendChild(document.createTextNode(data.get(
								"MODULE").toString()));

						Element preReq = document.createElement("prereq");
						try {

							preReq.appendChild(document.createTextNode(data
									.get("Pre-requisite").toString()));

						} catch (NullPointerException ne) {
						}

						Element coReq = document.createElement("coreq");

						try {

							coReq.appendChild(document.createTextNode(data.get(
									"Co-requisite").toString()));

						} catch (NullPointerException ne) {
						}

						Element recommendation = document
								.createElement("recommendation");

						try {

							recommendation.appendChild(document
									.createTextNode(data.get("Recommendation")
											.toString()));

						} catch (NullPointerException ne) {
						}

						Element fromYear = document.createElement("fromYear");
						fromYear.appendChild(document.createTextNode(data.get(
								"mod_from_year").toString()));

						module.appendChild(modName);
						module.appendChild(modCode);
						module.appendChild(preReq);
						module.appendChild(coReq);
						module.appendChild(recommendation);
						module.appendChild(fromYear);

						temp = false;
						temp1 = false;
					}
				} catch (NullPointerException npe) {

				}

			}

			return document;

		} catch (Exception ex) {

		/*	System.out.println("Problem : " + ex);*/
			throw new Exception(
					"za.ac.unisa.lms.tools.brochures.dao.MychoiceDA0: myChoiceXml: Error occurred / "
							+ ex, ex);
		}
	}

	public Document myModuleXml(String colCode, String schCode, String dptCode,
			String module, String subCode, int year) throws Exception {

		BrochureQueries bquery = new BrochureQueries();
		String query;

		query = bquery.myModuleQuery(colCode, schCode, dptCode, module,
				subCode, year);

		/*System.out.println("sql-- " + query);*/

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());

			List qualInfo = jdt.queryForList(query);
			myModule = qualInfo.isEmpty();

			Iterator i = qualInfo.iterator();

			String collegeName = "";
			String schoolName = "";
			String subjectName = "";
			String moduleCode = "";
			Element myModules = document.createElement("myModules");
			Element subjects = null;
			document.appendChild(myModules);
			Element college = null;
			Element school = null;

			while (i.hasNext()) {

				ListOrderedMap data = (ListOrderedMap) i.next();

				/*
				 * if (! collegeName.equals(data.get("COLL_DESCR").toString())){
				 * college = document.createElement("college");
				 * colleges.appendChild(college); Element colname =
				 * document.createElement("name"); collegeName =
				 * data.get("COLL_DESCR").toString();
				 * colname.appendChild(document.createTextNode(collegeName));
				 * college.appendChild(colname);
				 * 
				 * } if (!
				 * schoolName.equals(data.get("SCHOOL_DESCR").toString())){
				 * school = document.createElement("school");
				 * college.appendChild(school); Element schName =
				 * document.createElement("name"); schoolName =
				 * data.get("SCHOOL_DESCR").toString();
				 * schName.appendChild(document.createTextNode(schoolName));
				 * school.appendChild(schName);
				 * 
				 * }
				 */

				try {

					if (!subjectName
							.equals(data.get("SUBJECT_NAME").toString())) {

						subjects = document.createElement("subjects");
						myModules.appendChild(subjects);

						Element subjectname = document
								.createElement("subject_name");
						subjectName = data.get("SUBJECT_NAME").toString();
						subjectname.appendChild(document
								.createTextNode(subjectName));

						Element sunsub_comment = document
								.createElement("sunsub_comment");

						if (data.get("SUBJECT_COMMENT") != null) {

							try {
								String temp = rtrim(data.get("SUBJECT_COMMENT")
										.toString());
								String[] strList;
								strList = temp.split("\n");
								for (int j = 0; j < strList.length; j++) {
									Element line = document
											.createElement("line" + (j + 1));
									line.appendChild(document
											.createTextNode(strList[j]));
									sunsub_comment.appendChild(line);
								}
							} catch (NullPointerException e) {
							}
						}

						Element sunsbj_comment = document
								.createElement("sunsbj_comment");

						if (data.get("SUNSBJ_COMMENT") != null) {

							try {
								String temp = rtrim(data.get("SUNSBJ_COMMENT")
										.toString());
								String[] strList;
								strList = temp.split("\n");
								for (int j = 0; j < strList.length; j++) {
									Element line = document
											.createElement("line" + (j + 1));
									line.appendChild(document
											.createTextNode(strList[j]));
									sunsub_comment.appendChild(line);
								}
							} catch (NullPointerException e) {
							}
						}

						subjects.appendChild(subjectname);
						subjects.appendChild(sunsub_comment);
						subjects.appendChild(sunsbj_comment);

					}

				} catch (NullPointerException ne) {
				}

				if (!moduleCode
						.equals(rtrim(ltrim(data.get("CODE").toString())))) {

					moduleCode = data.get("CODE").toString();
					Element modules = document.createElement("modules");

					Element module_info = document
							.createElement("module_information");
					subjects.appendChild(modules);
					modules.appendChild(module_info);

					Element modulecode = document.createElement("modulecode");
					modulecode.appendChild(document.createTextNode(data.get(
							"CODE").toString()));

					Element oldmodulecode = document
							.createElement("oldmodulecode");
					oldmodulecode.appendChild(document.createTextNode(data.get(
							"CODE").toString()));

					Element moduledescription = document
							.createElement("moduledescription");
					moduledescription.appendChild(document.createTextNode(data
							.get("ENG_DESC").toString()));

					Element credits = document.createElement("credits");
					credits.appendChild(document.createTextNode(data.get(
							"NQF_CREDITS").toString()));

					Element nqflevel = document.createElement("nqflevel");
					nqflevel.appendChild(document.createTextNode(data.get(
							"NQF_LEVEL").toString()));

					Element pqm_nqf_level = document
							.createElement("pqm_nqf_level");
					pqm_nqf_level.appendChild(document.createTextNode(data.get(
							"PQM_NQF_LEVEL").toString()));

					Element moduleType = document.createElement("moduletype");
					moduleType.appendChild(document.createTextNode(data.get(
							"FK_SUNTYPCODE").toString()));

					Element language = document.createElement("language");
					language.appendChild(document.createTextNode(""));

					Element syllabus = document.createElement("syllabus");

					try {

						syllabus.appendChild(document.createTextNode(data.get(
								"SYLLABUS").toString()));

					} catch (NullPointerException e) {
					}

					Element tuition_indicator = document
							.createElement("tuition_indicator");
					tuition_indicator.appendChild(document.createTextNode(data
							.get("ONLINE_TUITION_FLAG").toString()));

					// Phase 2 myModule brochure
					Element studyMaterialIndicator = document
							.createElement("study_material_indicator");

					if (data.get("STUDY_MATERIAL_IND_GC196") != null) {

						studyMaterialIndicator
								.appendChild(document.createTextNode(data.get(
										"STUDY_MATERIAL_IND_GC196").toString()));
					}

					Element formativeAssessmentIndicator = document
							.createElement("formative_assessment_indicator");

					if (data.get("FORMATIVE_ASSESS_IND_GC197") != null) {

						formativeAssessmentIndicator.appendChild(document
								.createTextNode(data.get(
										"FORMATIVE_ASSESS_IND_GC197")
										.toString()));
					}

					Element summativeAssessmentIndicator = document
							.createElement("summative_assessment_indicator");

					if (data.get("SUMMATIVE_ASSESS_IND_GC198") != null) {

						summativeAssessmentIndicator.appendChild(document
								.createTextNode(data.get(
										"SUMMATIVE_ASSESS_IND_GC198")
										.toString()));
					}

					Element nonVenueExamType = document
							.createElement("non_venue_exam_type");

					if (data.get("NON_VENUE_EXAM_TYPE_GC195") != null) {

						nonVenueExamType
								.appendChild(document
										.createTextNode(data.get(
												"NON_VENUE_EXAM_TYPE_GC195")
												.toString()));
					}

					Element nonVenueExam = document
							.createElement("non_venue_exam");

					if (data.get("NON_VENUE_EXAM_INFO") != null) {

						nonVenueExam.appendChild(document.createTextNode(data
								.get("NON_VENUE_EXAM_INFO").toString()));
					}

					Element moduleLevel = document
							.createElement("module_level");

					Element masters = document.createElement("masters");
					Element b_tech = document.createElement("b_tech");
					Element under_grad_degree = document.createElement("under_grad_degree");
					Element certificate = document.createElement("certificate");
					Element doctorate = document.createElement("doctorate");
					Element diploma = document.createElement("diploma");
					Element high_cert = document.createElement("high_cert");
					Element honours = document.createElement("honours");
					Element pgdip = document.createElement("post_grad_diploma");
					Element advancedDiploma = document
							.createElement("advanced_diploma");
					Element advancedCertificate = document.createElement("advanced_certificate");

					if (data.get("MODULE_LEVEL") != null) {

						StringTokenizer tokens = new StringTokenizer(data.get(
								"MODULE_LEVEL").toString(), ",");

						while (tokens.hasMoreTokens()) {

							String mod_level = tokens.nextToken();

							if (mod_level.equalsIgnoreCase("MASTERS")) {
								masters.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("B_TECH")) {
								b_tech.appendChild(document.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("UND_DEGREE")) {
								under_grad_degree.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("CERTIFICAT")) {
								certificate.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("DOCTORAL")) {
								doctorate.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("DIPLOMA")) {
								diploma.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("HIGH_CERT")) {
								high_cert.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("HONOURS")) {
								honours.appendChild(document
										.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("PGDIP")) {
								pgdip.appendChild(document.createTextNode("Y"));
							}

							if (mod_level.equalsIgnoreCase("ADVDIP")) {
								advancedDiploma.appendChild(document
										.createTextNode("Y"));
							}
							if (mod_level.equalsIgnoreCase("ADVCERT")) {
								advancedCertificate.appendChild(document
										.createTextNode("Y"));
							}

						}

					}

					moduleLevel.appendChild(masters);
					moduleLevel.appendChild(b_tech);
					moduleLevel.appendChild(under_grad_degree);
					moduleLevel.appendChild(certificate);
					moduleLevel.appendChild(doctorate);
					moduleLevel.appendChild(diploma);
					moduleLevel.appendChild(high_cert);
					moduleLevel.appendChild(honours);
					moduleLevel.appendChild(pgdip);
					moduleLevel.appendChild(advancedDiploma);
					moduleLevel.appendChild(advancedCertificate);

					Element recommendation = document
							.createElement("recommendation");

					if (data.get("Recommendation") != null) {

						recommendation.appendChild(document.createTextNode(data
								.get("Recommendation").toString()));
					}

					Element languages = document.createElement("languages");
					/*Element a_languages = document.createElement("african_languages");*/
					Element english = document.createElement("english");
					Element n_sotho = document.createElement("northern_sotho");
					Element o_languages = document.createElement("other_languages");
					Element afrikaans = document.createElement("afrikaans");
					Element xhosa = document.createElement("xhosa");
					Element zulu = document.createElement("zulu");

					if (data.get("LANG") != null) {

						StringTokenizer tokens = new StringTokenizer(data.get(
								"LANG").toString(), ",");

						while (tokens.hasMoreTokens()) {

							String lang = tokens.nextToken();

							if (lang.equalsIgnoreCase("E")) {
								english.appendChild(document
										.createTextNode("Y"));
							}

							if (lang.equalsIgnoreCase("A")) {
								afrikaans.appendChild(document
										.createTextNode("Y"));
							}

							if (lang.equalsIgnoreCase("NS")) {
								n_sotho.appendChild(document
										.createTextNode("Y"));
							}

							if (lang.equalsIgnoreCase("OT")) {
								o_languages.appendChild(document
										.createTextNode("Y"));
							}
							if (lang.equalsIgnoreCase("XH")) {
								xhosa.appendChild(document
										.createTextNode("Y"));
							}
							if (lang.equalsIgnoreCase("ZU")) {
								zulu.appendChild(document
										.createTextNode("Y"));
							}
							/*if (lang.equalsIgnoreCase("AL")) {
								a_languages.appendChild(document
										.createTextNode("Y"));
							}*/
						}

					}

					languages.appendChild(english);
					languages.appendChild(afrikaans);
					languages.appendChild(n_sotho);
					languages.appendChild(o_languages);
					//new changes additional languages
					languages.appendChild(zulu);
					languages.appendChild(xhosa);
				//	languages.appendChild(a_languages);

					Element preRequisites = document
							.createElement("pre_requisites");

					if (data.get("Pre-requisite") != null) {

						preRequisites.appendChild(document.createTextNode(data
								.get("Pre-requisite").toString()));
					}

					Element coRequisites = document
							.createElement("co_requisites");

					if (data.get("Co-requisite") != null) {

						coRequisites.appendChild(document.createTextNode(data
								.get("Co-requisite").toString()));
					}

					module_info.appendChild(modulecode);
					module_info.appendChild(oldmodulecode);
					module_info.appendChild(moduledescription);
					module_info.appendChild(credits);
					module_info.appendChild(nqflevel);
					module_info.appendChild(pqm_nqf_level);
					module_info.appendChild(moduleType);
					module_info.appendChild(language);
					module_info.appendChild(syllabus);
					module_info.appendChild(preRequisites);
					module_info.appendChild(coRequisites);
					module_info.appendChild(tuition_indicator);
					// Phase 2
					module_info.appendChild(studyMaterialIndicator);
					module_info.appendChild(formativeAssessmentIndicator);
					module_info.appendChild(summativeAssessmentIndicator);
					module_info.appendChild(nonVenueExamType);
					module_info.appendChild(nonVenueExam);
					module_info.appendChild(moduleLevel);
					module_info.appendChild(recommendation);
					module_info.appendChild(languages);
					module_info.appendChild(preRequisites);
					module_info.appendChild(coRequisites);

				}

			}

			return document;

		} catch (Exception ex) {

			/*System.out.println("Problem : " + ex);*/
			throw new Exception(
					"za.ac.unisa.lms.tools.brochures.dao.MychoiceDA0: myModuleXML: Error occurred / "
							+ ex.getMessage(), ex);
		}
	}

	public ArrayList getColleges() {
		String sql = "select code,eng_description COLLEGE, COLLEG.ABBREVIATION coll from colleg"
				+ " where dean > 0  order by eng_description";
		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			String college = data.get("coll").toString() + ": "
					+ data.get("COLLEGE").toString();
			String code = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(college, code));
		}
		return results;
	}

	/**
	 * Get all the Department names for the institution under a given college
	 * code
	 * 
	 * @param d
	 *            (department name)
	 * @return
	 */
	public ArrayList getSchools(String code) {
		String sql = " select CODE, eng_description SCHOOL " + " from school "
				+ " where  COLLEGE_CODE =" + code + " and in_use_flag ='Y' "
				+ " order by SCHOOL ";

		/*System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&             "
				+ code);*/
		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);

		/*System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&             "
				+ queryList.size());*/
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String sch = data.get("SCHOOL").toString();
			String schcode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(sch, schcode));
		}
		return results;
	}

	public ArrayList getDepartment(String colCode, String schCode) {
		String sql = " select CODE, eng_description department " + " from DPT "
				+ " where  college_code = " + colCode + " and SCHOOL_CODE = "
				+ "'" + schCode + "'" + " and in_use_flag ='Y' "
				+ " order by department ";

		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String sch = data.get("department").toString();
			String schcode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(sch, schcode));
		}
		return results;
	}

	public ArrayList getSubjects(String modCode) {
		String sql = "select s1.subject_name, s1.subject_code from SUNSUB s1,  sunsbj s2"
				+ " where s1.subject_code = s2.MK_SUBJECT_CODE"
				+ " and s2.MK_STUDY_UNIT_CODE="
				+ "'"
				+ modCode
				+ "'"
				+ " order by subject_name";

		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String sub = data.get("subject_code").toString();
			String subCode = data.get("subject_name").toString();
			results.add(new org.apache.struts.util.LabelValueBean(subCode, sub));
		}
		return results;
	}

	/*
	 * categorey
	 */
	public ArrayList getCategoryList() {
		String sql = " SELECT CODE, ENG_DESCRIPTION  from kat where IN_USE_FLAG='Y' and student_flag = 'Y'order by ENG_DESCRIPTION ";

		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String sch = data.get("ENG_DESCRIPTION").toString();
			String schcode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(sch, schcode));
		}
		return results;
	}

	/*
	 * Qualification List
	 */
	public ArrayList<LabelValueBean> getQualification(String colCode) {
		int collegeCode = Integer.parseInt(colCode);

		String sql = "SELECT CODE, LONG_ENG_DESCRIPTI FROM GRD WHERE COLLEGE_CODE="
				+ "'" + collegeCode + "'" + " ORDER BY LONG_ENG_DESCRIPTI";

		ArrayList<LabelValueBean> results = new ArrayList<LabelValueBean>();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList;
		queryList = jdt.queryForList(sql);
		Iterator<?> i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String qual = data.get("LONG_ENG_DESCRIPTI").toString();
			String qualcode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(qual,
					qualcode));
		}
		return results;
	}

	/*
	 * Qualification List
	 */
	public ArrayList getSpecialization(String qualCode) {
		String sql = "SELECT SPECIALITY_CODE AS CODE, ENGLISH_DESCRIPTIO FROM QUASPC WHERE MK_QUALIFICATION_C="
				+ "'" + qualCode + "'" + " ORDER BY ENGLISH_DESCRIPTIO";

		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String spec = data.get("ENGLISH_DESCRIPTIO").toString();
			String speccode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(spec,
					speccode));
		}
		return results;
	}

	public String getCollegeName(String code) {
		String sql = "select COLLEG.ABBREVIATION  COLLEGE from colleg where code="
				+ code;

		String name = this.querySingleValue(sql, "COLLEGE");

		return name;
	}

	public ArrayList getModules(String colCode, String schCode, String dptCode,
			String year) {
		String sql = " select code, eng_long_descripti  from sun where in_use_flag = 'Y' AND formal_tuition_fla = 'F'"
				+ " AND academic_level IN ('U', 'H') AND sun.from_year <="
				+ year
				+ " AND (sun.to_year = 0 OR sun.to_year >="
				+ year
				+ ")"
				+ " and COLLEGE_CODE="
				+ colCode
				+ " and SCHOOL_CODE="
				+ schCode
				+ " and MK_DEPARTMENT_CODE="
				+ dptCode
				+ " and research_flag = 'N'"
				+ " and college_flag <> 'Y'"
				+ " order by code";

		/*
		 * try{ if(!dptCode.equals("-1")){ sql
		 * =sql+" AND MK_DEPARTMENT_CODE="+dptCode; }else{ }
		 * }catch(NullPointerException ne){} sql=sql+" order by code";
		 */
		/*System.out.println("myModules query" + sql);*/
		
		ArrayList results = new ArrayList();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		queryList = jdt.queryForList(sql);
		Iterator i = queryList.iterator();
		while (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();

			String sch = data.get("CODE").toString();
			String schcode = data.get("CODE").toString();
			results.add(new org.apache.struts.util.LabelValueBean(sch, schcode));
		}
		return results;
	}

	public boolean ismyRegistration() {

		return myReg;
	}

	public boolean ismyModules() {

		return myModule;
	}

	private String querySingleValue(String query, String field) {
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List queryList;
		String result = "";

		queryList = jdt.queryForList(query);
		Iterator i = queryList.iterator();
		if (i.hasNext()) {
			ListOrderedMap data = (ListOrderedMap) i.next();
			if (data.get(field) == null) {
			} else {
				result = data.get(field).toString();

			}
		}
		return result;
	}

	/*
	 * remove leading whitespace
	 */
	public static String ltrim(String source) {
		return source.replaceAll("^\\s+", "");
	}

	/* remove trailing whitespace */
	public static String rtrim(String source) {
		return source.replaceAll("\\s+$", "");
	}
	
	private void generateXMLRulesPerStudyLevel(ListOrderedMap data, Document document, Element qualLevel){
		
		//Rules per study level
		Element qualLevelRules = null;
		Element levelRuleLine1 = null;
		Element levelRuleLine2 = null;
		Element levelRuleLine3 = null;
		Element levelRuleLine4 = null;
		Element levelRuleLine5 = null;
		Element levelRuleLine6 = null;
		Element levelRuleLine7 = null;
		Element levelRuleLine8 = null;
		Element levelRuleLine9 = null;
		Element levelRuleLine10 = null;
		Element levelRuleLine11 = null;
		Element levelRuleLine12 = null;
		Element levelRuleLine13 = null;
		Element levelRuleLine14 = null;
		Element levelRuleLine15 = null;
		Element levelRuleLine16 = null;
		Element levelRuleLine17 = null;
		Element levelRuleLine18 = null;
		Element levelRuleLine19 = null;
		Element levelRuleLine20 = null;
		Element levelRuleLine21 = null;
		Element levelRuleLine22 = null;
		Element levelRuleLine23 = null;
		Element levelRuleLine24 = null;
		Element levelRuleLine25 = null;
		Element levelRuleLine26 = null;
		Element levelRuleLine27 = null;
		Element levelRuleLine28 = null;
		Element levelRuleLine29 = null;
		Element levelRuleLine30 = null;

		String line1 = "";	
		String line2 = "";	
		String line3 = "";	
		String line4 = "";	
		String line5 = "";	
		String line6 = "";	
		String line7 = "";	
		String line8 = "";	
		String line9 = "";	
		String line10 = "";	
		String line11 = "";	
		String line12 = "";	
		String line13 = "";	
		String line14 = "";
		String line15= "";	
		String line16 = "";	
		String line17 = "";	
		String line18 = "";	
		String line19 = "";	
		String line20 = "";
		String line21 = "";	
		String line22 = "";	
		String line23 = "";	
		String line24 = "";
		String line25= "";	
		String line26 = "";	
		String line27 = "";	
		String line28 = "";	
		String line29 = "";	
		String line30 = "";
		String qualificationLevelRules = "";


		if(data.get("LEVEL RULE LINE1") != null){
			
		
			qualLevelRules = document.createElement("rules");
			qualLevel.appendChild(qualLevelRules);
			
			qualificationLevelRules = data.get("LEVEL RULE LINE1").toString();
			
			Element ruleHeading = document.createElement("heading");

			try {

				ruleHeading.appendChild(document.createTextNode(qualificationLevelRules));
				qualLevelRules.appendChild(ruleHeading);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE2") != null){
			//line1
			line1 = data.get("LEVEL RULE LINE2").toString();
			
			levelRuleLine1 = document.createElement("line1");

			try {

				levelRuleLine1.appendChild(document.createTextNode(line1));
				qualLevelRules.appendChild(levelRuleLine1);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE3") != null){
			//line2
			line2 = data.get("LEVEL RULE LINE3").toString();
			
			levelRuleLine2 = document.createElement("line2");

			try {

				levelRuleLine2.appendChild(document.createTextNode(line2));
				qualLevelRules.appendChild(levelRuleLine2);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE4") != null){
			//line3
			line3 = data.get("LEVEL RULE LINE4").toString();
			
			levelRuleLine3 = document.createElement("line3");

			try {

				levelRuleLine3.appendChild(document.createTextNode(line3));
				qualLevelRules.appendChild(levelRuleLine3);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE5") != null){
			//line4
			line4 = data.get("LEVEL RULE LINE5").toString();
			
			levelRuleLine4 = document.createElement("line4");

			try {

				levelRuleLine4.appendChild(document.createTextNode(line4));
				qualLevelRules.appendChild(levelRuleLine4);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
			
		}
		
		if(data.get("LEVEL RULE LINE6") != null){
			//line5
			line5 = data.get("LEVEL RULE LINE6").toString();
			
			levelRuleLine5 = document.createElement("line5");

			try {

				levelRuleLine5.appendChild(document.createTextNode(line5));
				qualLevelRules.appendChild(levelRuleLine5);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE7") != null){
			
			//line6
			line6 = data.get("LEVEL RULE LINE7").toString();
			
			levelRuleLine6 = document.createElement("line6");

			try {

				levelRuleLine6.appendChild(document.createTextNode(line6));
				qualLevelRules.appendChild(levelRuleLine6);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE8") != null){
			
			//line7
			line7 = data.get("LEVEL RULE LINE8").toString();
			
			levelRuleLine7 = document.createElement("line7");

			try {

				levelRuleLine7.appendChild(document.createTextNode(line7));
				qualLevelRules.appendChild(levelRuleLine7);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE9") != null){
			
			line8 = data.get("LEVEL RULE LINE9").toString();
			
			levelRuleLine8 = document.createElement("line8");

			try {

				levelRuleLine8.appendChild(document.createTextNode(line8));
				qualLevelRules.appendChild(levelRuleLine8);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
			
		if(data.get("LEVEL RULE LINE10") != null){

			line9 = data.get("LEVEL RULE LINE10").toString();
			
			levelRuleLine9 = document.createElement("line9");

			try {

				levelRuleLine9.appendChild(document.createTextNode(line9));
				qualLevelRules.appendChild(levelRuleLine9);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE11") != null){	

			line10 = data.get("LEVEL RULE LINE11").toString();
			
			levelRuleLine10 = document.createElement("line10");

			try {

				levelRuleLine10.appendChild(document.createTextNode(line10));
				qualLevelRules.appendChild(levelRuleLine10);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE12") != null){	

			line11 = data.get("LEVEL RULE LINE12").toString();
			
			levelRuleLine11 = document.createElement("line11");

			try {

				levelRuleLine11.appendChild(document.createTextNode(line11));
				qualLevelRules.appendChild(levelRuleLine11);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE13") != null){	

			line12 = data.get("LEVEL RULE LINE13").toString();
			
			levelRuleLine12 = document.createElement("line12");

			try {

				levelRuleLine12.appendChild(document.createTextNode(line12));
				qualLevelRules.appendChild(levelRuleLine12);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE14") != null){	

			line13 = data.get("LEVEL RULE LINE14").toString();
			
			levelRuleLine13 = document.createElement("line13");

			try {

				levelRuleLine13.appendChild(document.createTextNode(line13));
				qualLevelRules.appendChild(levelRuleLine13);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE15") != null){	

			line14 = data.get("LEVEL RULE LINE15").toString();
			
			levelRuleLine14 = document.createElement("line14");

			try {

				levelRuleLine14.appendChild(document.createTextNode(line14));
				qualLevelRules.appendChild(levelRuleLine14);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE16") != null){	

			line15 = data.get("LEVEL RULE LINE16").toString();
			
			levelRuleLine15 = document.createElement("line15");

			try {

				levelRuleLine15.appendChild(document.createTextNode(line15));
				qualLevelRules.appendChild(levelRuleLine15);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE17") != null){	

			line16 = data.get("LEVEL RULE LINE17").toString();
			
			levelRuleLine16 = document.createElement("line16");

			try {

				levelRuleLine16.appendChild(document.createTextNode(line16));
				qualLevelRules.appendChild(levelRuleLine16);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}

		if(data.get("LEVEL RULE LINE18") != null){	

			line17 = data.get("LEVEL RULE LINE18").toString();
			
			levelRuleLine17 = document.createElement("line17");

			try {

				levelRuleLine17.appendChild(document.createTextNode(line17));
				qualLevelRules.appendChild(levelRuleLine17);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE19") != null){	

			line18 = data.get("LEVEL RULE LINE19").toString();
			
			levelRuleLine18 = document.createElement("line18");

			try {

				levelRuleLine18.appendChild(document.createTextNode(line18));
				qualLevelRules.appendChild(levelRuleLine18);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE20") != null){	

			line19 = data.get("LEVEL RULE LINE20").toString();
			
			levelRuleLine19 = document.createElement("line19");

			try {

				levelRuleLine19.appendChild(document.createTextNode(line19));
				qualLevelRules.appendChild(levelRuleLine19);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}

		
		if(data.get("LEVEL RULE LINE21") != null){	
			
			line20 = data.get("LEVEL RULE LINE21").toString();
			
			levelRuleLine20 = document.createElement("line20");

			try {

				levelRuleLine20.appendChild(document.createTextNode(line20));
				qualLevelRules.appendChild(levelRuleLine20);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE22") != null){	
			
			line21 = data.get("LEVEL RULE LINE22").toString();
			
			levelRuleLine21 = document.createElement("line21");

			try {

				levelRuleLine21.appendChild(document.createTextNode(line21));
				qualLevelRules.appendChild(levelRuleLine21);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE23") != null){	
			
			line22 = data.get("LEVEL RULE LINE23").toString();
			
			levelRuleLine22 = document.createElement("line22");

			try {

				levelRuleLine22.appendChild(document.createTextNode(line22));
				qualLevelRules.appendChild(levelRuleLine22);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE24") != null){	
			
			line23 = data.get("LEVEL RULE LINE24").toString();
			
			levelRuleLine23 = document.createElement("line23");

			try {

				levelRuleLine23.appendChild(document.createTextNode(line23));
				qualLevelRules.appendChild(levelRuleLine23);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE25") != null){	
			
			line24 = data.get("LEVEL RULE LINE25").toString();
			
			levelRuleLine24 = document.createElement("line24");

			try {

				levelRuleLine24.appendChild(document.createTextNode(line24));
				qualLevelRules.appendChild(levelRuleLine24);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE26") != null){	
			
			line25 = data.get("LEVEL RULE LINE26").toString();
			
			levelRuleLine25 = document.createElement("line25");

			try {

				levelRuleLine25.appendChild(document.createTextNode(line25));
				qualLevelRules.appendChild(levelRuleLine25);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE27") != null){	
			
			line26 = data.get("LEVEL RULE LINE27").toString();
			
			levelRuleLine26 = document.createElement("line26");

			try {

				levelRuleLine26.appendChild(document.createTextNode(line26));
				qualLevelRules.appendChild(levelRuleLine26);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE28") != null){	
			
			line27 = data.get("LEVEL RULE LINE28").toString();
			
			levelRuleLine27 = document.createElement("line27");

			try {

				levelRuleLine27.appendChild(document.createTextNode(line27));
				qualLevelRules.appendChild(levelRuleLine27);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		
		if(data.get("LEVEL RULE LINE29") != null){	
			
			line28 = data.get("LEVEL RULE LINE29").toString();
			
			levelRuleLine28 = document.createElement("line28");

			try {

				levelRuleLine28.appendChild(document.createTextNode(line28));
				qualLevelRules.appendChild(levelRuleLine28);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE30") != null){	
			
			line29 = data.get("LEVEL RULE LINE30").toString();
			
			levelRuleLine29 = document.createElement("line29");

			try {

				levelRuleLine29.appendChild(document.createTextNode(line29));
				qualLevelRules.appendChild(levelRuleLine29);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		
		if(data.get("LEVEL RULE LINE31") != null){	
			
			line30 = data.get("LEVEL RULE LINE31").toString();
			
			levelRuleLine30 = document.createElement("line30");

			try {

				levelRuleLine30.appendChild(document.createTextNode(line30));
				qualLevelRules.appendChild(levelRuleLine30);

			} catch (NullPointerException e) {
				System.out.println("error creating tag for rule level");
			}
		}
		//End of rules per study level
		
		
	}

	

}