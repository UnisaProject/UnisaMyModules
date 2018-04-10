package za.ac.unisa.lms.tools.studentregistration.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.studentregistration.bo.Doc;
import za.ac.unisa.lms.tools.studentregistration.bo.StudySelected;
import za.ac.unisa.lms.tools.studentregistration.forms.StudentFile;

public class DocDao extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentSystemDAO.class);

	/**
	 * This method builds the dropdown menu for Optional documents.
	 * @param studentNr
	 * @param year
	 * @param period
	 * @param matrix
	 * @return
	 * @throws Exception
	 */
	public List<Doc> getAllOptionalDocs(String studentNr,String year,String period, String matrix, String stuExist) throws Exception{

		List<Doc> docs = new ArrayList<Doc>();

		//log.debug("DocDao getAllOptionalDocs - stuExist: " + stuExist + " studentNr: " + studentNr + " year: " + year + " period: " + period + " matrix: " + matrix);
		
		String query = "select distinct code AS docCode, eng_description as docDescription "
					+ " from gencod "
					+ " where fk_gencatcode = 208 "
					+ " and in_use_flag = 'Y' "
					+ " order by docDescription asc";

		try {
			//log.debug("DocDao - getAllOptionalDocs -  query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query);
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Doc doc = new Doc();

				doc.setDocCode(data.get("docCode").toString());
				doc.setDocDescription(data.get("docDescription").toString());

				docs.add(doc);

			}

		} catch (Exception ex) {
			throw new Exception(
					"DocDAO : Error reading getting getAllOptionalDocs list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}

		return docs;

	}


	/**
	 * Builds list of required documents for new students
	 * @param studentNr
	 * @param year
	 * @param period
	 * @param required
	 * @param matrix
	 * @return
	 * @throws Exception
	 */
	public List<Doc> getAllRequiredDocs(String studentNr,String year,String period, String required, String matrix, String stuExist, String loginSelectMain) throws Exception{

		//log.debug("DocDao getAllRequiredDocs - stuExist: " + stuExist + " studentNr: " + studentNr + " year: " + year + " period: " + period + " required: " + required + " matrix: " + matrix);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList = null;
		List<Doc> docs = new ArrayList<Doc>();
		String query = "";
		
		//log.debug("DocDao - getDocs - Matrix: " + matrix);
		try {
				
			if ("CG".equalsIgnoreCase(matrix)){ //Current Grade 12 only ID
				//log.debug("DocDao - getDocs - In Matrix");
				query = "select distinct code AS docCode, eng_description as docDescription "
							+ " from gencod "
							+ " where fk_gencatcode = '208' "
							+ " and in_use_flag = 'Y' "
							+ " and code = 'UG01' "
							+ " order by docDescription asc";
				//log.debug("DocDao - getDocs - query=" + query);
				queryList = jdt.queryForList(query);
			}else if ("curStu".equalsIgnoreCase(stuExist) || "MD".equalsIgnoreCase(loginSelectMain)){
				//log.debug("DocDao - getDocs - In Current Student");
				query = "select '' AS docCode, '' as docDescription "
							+ " from DUAL where Dummy = 'getDocs1'";
				//log.debug("DocDao - getDocs - query=" + query);
				queryList = jdt.queryForList(query);
			}else {
				//log.debug("DocDao - getDocs - NEWSTU - In New Student");
				//Get choice 1 and 2 from STUAPQ for New Student

				StudySelected selected = querySTUAPQSelected(studentNr, year, period);				
				String spec = (selected==null||selected.getDocSpec()==null) ||"0".equals(selected.getDocSpec()) ? " " :selected.getDocSpec();
				String qual = (selected==null||selected.getDocQual()==null) ||"0".equals(selected.getDocQual()) ? "" :selected.getDocQual();
				
				StudySelected selectedNew = querySTUAPQSelectedNew(studentNr, year, period);
				String qual1 = selectedNew.getDocQual1()==null ||"0".equals(selectedNew.getDocQual1()) ? "" :selectedNew.getDocQual1();
				String qual2 = selectedNew.getDocQual2()==null ||"0".equals(selectedNew.getDocQual2()) ? "" :selectedNew.getDocQual2();
				String spec1 = selectedNew.getDocSpec1()==null ||"0".equals(selectedNew.getDocSpec1()) ? " " :selectedNew.getDocSpec1();
				String spec2 = selectedNew.getDocSpec2()==null ||"0".equals(selectedNew.getDocSpec2()) ? " " :selectedNew.getDocSpec2();
				
				//log.debug("DocDao - getDocs - Qual1: " + qual1 + ", Spec1: " + spec1 + ", Qual2: " + qual2 + ", Spec2: " + spec2 + ", ");
				
				query = "SELECT distinct docCode, docDescription from ( "
							+ " (select gencod.code as docCode, gencod.eng_description as  docdescription "
							+ "  from qspdoc, gencod "
							+ "  where qspdoc.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '208' "
							+ "  and qspdoc.mk_qual_code = ? "
							+ "  and qspdoc.mk_spes_code = ?) "
							+ " UNION "
							+ " (select gencod.code as docCode, gencod.eng_description as  docdescription "
							+ "  from qspdoc, gencod "
							+ "  where qspdoc.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '208' "
							+ "  and qspdoc.mk_qual_code = ? "
							+ "  and qspdoc.mk_spes_code = ?) "
							+ " UNION "
							+ " (select gencod.code as docCode, gencod.eng_description as docDescription "
							+ "  from qspdoc, gencod "
							+ "  where qspdoc.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '208' "
							+ "  and qspdoc.mk_qual_code = ? "
							+ " and qspdoc.mk_spes_code = ?)"
							+ " ) tabname "
							+ " ORDER BY DocDescription ASC ";
				//log.debug("DocDao - getDocs - query=" + query + ", qual="+qual+", spec="+spec+", qual1="+qual1+", spec1="+spec1+", qual2="+qual2+", spec2="+spec2 );
				queryList = jdt.queryForList(query, new Object []{qual, spec, qual1, spec1, qual2, spec2});
			}

			Iterator<?> j = queryList.iterator();
			while (j.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) j.next();
				Doc doc = new Doc();
		
				doc.setDocCode(data.get("docCode").toString());
				doc.setDocDescription(data.get("docDescription").toString());
		
				docs.add(doc);
			}

		} catch (Exception ex) {
			throw new Exception(
				"DocDAO : Error reading getting getDocs(2) list / " + ex);
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return docs;
	}

	/**
	 * Builds list of required documents for new students
	 * @param studentNr
	 * @param year
	 * @param period
	 * @param required
	 * @param matrix
	 * @return
	 * @throws Exception
	 */
	public ArrayList<StudentFile> getAllMDRequiredDocs(String studentNr,String year, String qualCode, String specCode) throws Exception{

		//log.debug("MdApplicationsQueryDAO getAllRequiredDocs - studentNr=" + studentNr + ", year=" + year+", qualCode="+qualCode+", specCode="+specCode);
		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList = null;
		ArrayList<StudentFile> docs = new ArrayList<StudentFile>();
		String query = "";
		
		try {
			if (specCode == null || "0".equals(specCode) || "".equals(specCode) || "NVT".equalsIgnoreCase(specCode) || "undefined".equalsIgnoreCase(specCode)){ specCode = " ";}

				
			//log.debug("MdApplicationsQueryDAO - getAllRequiredDocs - qualCode: " + qualCode + ", specCode: " + specCode);
				
				query = "SELECT distinct docCode, docDescription from ( "
							+ " (select gencod.code as docCode, gencod.eng_description as  docdescription "
							+ "  from qspdoc, gencod "
							+ "  where qspdoc.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '134' "
							+ "  and qspdoc.mk_qual_code = ? "
							+ "  and qspdoc.mk_spes_code = ?) "
							+ " UNION "
							+ " (select stuapd.document_code as docCode, gencod.eng_description as docDescription "
							+ "  from stuapd, gencod "
							+ "  where stuapd.document_code = gencod.code "
							+ "  and gencod.in_use_flag = 'Y' "
							+ "  and gencod.fk_gencatcode = '134' "
							+ "  and stuapd.mk_student_nr = ? "
							+ "  and stuapd.academic_year = ?) "
							+ " ) tabname "
							+ " ORDER BY DocDescription ASC ";
				//log.debug("MdApplicationsQueryDAO - getAllRequiredDocs - query=" + query + ", qual="+qualCode+", spec="+specCode+", studentNr="+studentNr+", year="+year);
				queryList = jdt.queryForList(query, new Object []{qualCode, specCode, studentNr, year});

			Iterator<?> j = queryList.iterator();
			while (j.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) j.next();
				StudentFile doc = new StudentFile();
				doc.setFileType(data.get("docCode").toString());
				doc.setFileName(data.get("docDescription").toString());
				docs.add(doc);
			}
		} catch (Exception ex) {
			throw new Exception(
				"MdApplicationsQueryDAO : Error reading getting RequiredDocs list / " + ex);
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return docs;
	}
	
	public StudySelected querySTUAPQSelected(String studentNr, String acaYear, String acaPeriod) throws Exception{

		try {
			String query = "select new_qual as qualification_code, new_spes as speciality_code "
						+ " from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? ";
			//+ " and application_period = '" + period + "'"; //2014 Removed as we check for any existing record for this academic year

			//log.debug("DocDao - queryStudySelected - query: " + query+"studentNr="+studentNr+", acaYear="+acaYear);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				StudySelected selected = new StudySelected();
				selected.setDocQual(data.get("qualification_code").toString());
				
				if ("".equalsIgnoreCase(data.get("speciality_code").toString()) || " ".equalsIgnoreCase(data.get("speciality_code").toString())){
					selected.setDocSpec("0");
				}else{
					selected.setDocSpec(data.get("speciality_code").toString());
				}
				
				//log.debug("DocDao - queryStudySelected - qualification_code: " + (data.get("qualification_code").toString()));
				//log.debug("DocDao - queryStudySelected - speciality_code: " + (data.get("speciality_code").toString()));

				return selected;
			}
		} catch (Exception ex) {
			throw new Exception(
					"DocDao: Error reading saved qualification list / " + ex);

		} finally {

		}
		return null;
	}

	public StudySelected querySTUAPQSelectedNew(String studentNr, String acaYear, String acaPeriod) throws Exception {

		StudySelected selectedNew = new StudySelected();

		//log.debug("DocDao - queryStudySelectedNew - studentNr: " + studentNr);
		
		try {
			String query = "select new_qual, new_spes, choice_nr "
								+ " from STUAPQ "
								+ " where mk_student_nr = ? "
								+ " and academic_year = ? "
								+ " order by choice_nr";

			//log.debug("DocDao - queryStudySelectedNew - query: " + query+"studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();

					if ("1".equals(data.get("choice_nr").toString())){
						if (data.get("new_qual").toString() != null && !"".equalsIgnoreCase(data.get("new_qual").toString())){
							selectedNew.setDocQual1(data.get("new_qual").toString());
						
							if ("".equalsIgnoreCase(data.get("new_spes").toString()) || " ".equalsIgnoreCase(data.get("new_spes").toString())){
								selectedNew.setDocSpec1("0");
							}else{
								selectedNew.setDocSpec1(data.get("new_spes").toString());
							}
						}
					}else if ("2".equals(data.get("choice_nr").toString())){
						if (data.get("new_qual").toString() != null && !"".equalsIgnoreCase(data.get("new_qual").toString())){
							selectedNew.setDocQual2(data.get("new_qual").toString());
							
							if ("".equalsIgnoreCase(data.get("new_spes").toString()) || " ".equalsIgnoreCase(data.get("new_spes").toString())){
								selectedNew.setDocSpec2("0");
							}else{
								selectedNew.setDocSpec2(data.get("new_spes").toString());
							}
						}
					}
				}
				return selectedNew;
			}
		} catch (Exception ex) {
			throw new Exception(
				"DocDao: Error reading STUAPQ qualification  / " + ex);
		} finally {
		}
		return null;
	}
	
	public String getSTUAPQStatus(String studentNr,String acaYear,String period) throws Exception{
		
		String checkAP = "";

		try{		
			String query = "select status_code "
						+ " from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and status_code in ('AP','TN','RG') ";
						//+ " and application_period = '" + period + "'"; //2014 Removed as we check for any existing record for this academic year
			
			//log.debug("getSTUAPQStatus - query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				checkAP = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"DocDAO : Error reading getting getAllOptionalDocs list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		//log.debug("getSTUAPQStatus - checkAP: " + checkAP );
		return checkAP;
	}
	
}
