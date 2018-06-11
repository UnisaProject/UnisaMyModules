package za.ac.unisa.lms.tools.studentupload.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.studentupload.bo.SavedDoc;
import za.ac.unisa.lms.tools.studentupload.bo.StudySelected;
import za.ac.unisa.lms.tools.studentupload.forms.StudentUploadForm;

@SuppressWarnings("unused")
public class SavedDocDao extends StudentSystemDAO {

	public static Log log = LogFactory.getLog(StudentSystemDAO.class);

	/**
	 * Get Document INFO or short name for workflow rename
	 * @param docType
	 * @return
	 * @throws Exception
	 */
	public String getDocType(String docType) throws Exception{
		//log.debug("SavedDocDao - getDocType :docType   "+docType);
		String result="document";
		 try {
			 String query = "select info as docType "
					 		+ " from gencod "
							+ " where code = ? "
							+ " and in_use_flag = 'Y' "
							+ " and gencod.fk_gencatcode = '208' ";

			//log.debug("getDocType: " + query);
			List<String> list = null;
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{docType});
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				result = data.get("docType").toString();
			}

		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading getting getDocType / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		 return result;
 	}

	/**
	 * Gets all uploaded documents for Optional list
	 * Note if optional shows required docs as well
	 * Then problem in this method.
	 * @param docDescs
	 * @param map
	 * @param studentNr
	 * @param acaYear
	 * @throws Exception
	 */
	public void getAllNonRequiredDocInfo(List<String> docDescs,Map<String, List<String>> map, String studentNr, String acaYear, boolean isStuExist) throws Exception{

		//log.debug("SavedDocDao - Entering getAllNonRequiredDocInfo");
		StudentUploadDAO dao = new StudentUploadDAO();
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		List<?> queryList = null;
		Set<String> readNames = new HashSet<String>();
		String query = "";

		 try {

				if (isStuExist || !"".equalsIgnoreCase(getSTUAPQUpload(studentNr, acaYear))){
					query = "select stuxml.reference_type as DocCode, stuxml.detail as DocName, gencod.eng_description as docDescription "
							+ "	from stuxml "
							+ "	inner join gencod on stuxml.reference_type = gencod.code " 
							+ "	where gencod.fk_gencatcode = '208' "
							+ " and stuxml.reference_value = '2' "
							+ " and stuxml.mk_student_nr = ? "
							+ " and stuxml.mk_academic_year = ? "
							+ " order by docDescription asc";
					queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
				}else{
					
					StudySelected selected = dao.queryStudySelected(studentNr, acaYear);
				
					String qual1 = "";
					String qual2 = "";
					String spec1 = "";
					String spec2 = "";
					
					if(selected!=null) {
						qual1 = selected.getQual1()==null ||"0".equals(selected.getQual1()) ? "" :selected.getQual1();
						qual2 = selected.getQual2()==null ||"0".equals(selected.getQual2()) ? "" :selected.getQual2();
						spec1 = selected.getSpec1()==null ||"0".equals(selected.getSpec1()) ? " " :selected.getSpec1();
						spec2 = selected.getSpec2()==null ||"0".equals(selected.getSpec2()) ? " " :selected.getSpec2();
					}
					
					 query = "select stuxml.reference_type as DocCode, stuxml.detail as DocName, gencod.eng_description as docDescription "
							+ "	from stuxml "
							+ "	inner join gencod on stuxml.reference_type = gencod.code " 
							+ "	where gencod.fk_gencatcode = '208' "
							+ " and stuxml.reference_value = '2' "
							+ " and stuxml.mk_student_nr = ? "
							+ " and stuxml.mk_academic_year = ? "
							+ "	and gencod.code not in ( "
							+ "	(select gencod.code "
							+ "	from qspdoc, gencod "
							+ "	where qspdoc.document_code = gencod.code "
							+ "	and gencod.in_use_flag = 'Y' "
							+ "	and gencod.fk_gencatcode = '208' "
							+ "	and qspdoc.mk_qual_code = ? "
							+ "	and qspdoc.mk_spes_code = ?) "
							+ "	UNION  "
							+ "	(select gencod.code "
							+ "	from qspdoc, gencod "
							+ "	where qspdoc.document_code = gencod.code "
							+ "	and gencod.in_use_flag = 'Y' "
							+ "	and gencod.fk_gencatcode = '208' "
							+ "	and qspdoc.mk_qual_code = ? "
							+ "	and qspdoc.mk_spes_code = ?) "
							+ "	)order by docDescription asc";
					queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, qual1, spec1, qual2, spec2});
				}
				
			//log.debug("SavedDocDao - getAllNonRequiredDocInfo Query=" + query);
			List<String> list = null;
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				String docCode = data.get("DocCode").toString();
				String docName = data.get("docName").toString();
				String desc = data.get("docDescription").toString();

				if(!readNames.contains(docCode)){
					readNames.add(docCode);
					docDescs.add(desc);
					list = new ArrayList<String>();
					map.put(desc, list);
				}
				list.add(docName);
			
			}

		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading getting getAllNonRequiredDocInfo list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		 //log.debug("SavedDocDaoDAO - getAllNonRequiredDocInfo - Done");
 	}

	/**
	 * Gets saved documents per document code (Righthand side)
	 * @param docCode
	 * @param studentNr
	 * @param acaYear
	 * @return
	 * @throws Exception
	 */
	public List<SavedDoc> getSavedDocByDoc(String docCode,String studentNr,String acaYear) throws Exception{
		//log.debug("SavedDocDao - getSavedDocByDoc");
		List<SavedDoc> docs = new ArrayList<SavedDoc>();

		 try {
			 String query = "select reference_type as DocCode, reference_value, detail as DocName"
						+ " from stuxml "
						+ " where mk_student_nr = ? "
						+ " and stuxml.reference_value = '1' "
						+ " and mk_academic_year = ? "
						+ " and reference_type = ? " 
						+ " order by DocName";

			//log.debug(query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, docCode});
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				SavedDoc doc = new SavedDoc();
				doc.setDocCode(data.get("DocCode").toString());
				doc.setDocName(data.get("DocName").toString());
				docs.add(doc);
			}

		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading getting getSavedDocByDoc list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return docs;
	}

	/**
	 * Save new documents to STUXML
	 * @param doc
	 * @param mandatory
	 * @param studentNr
	 * @param acaYear
	 * @throws Exception
	 */
	public void addSavedDocSTUXML(SavedDoc doc, String mandatory, String studentNr, String acaYear, String acaPeriod, String optReq) throws Exception{
		
		//log.debug("SavedDocDao - addSavedDocSTUXML");
		String docName = doc.getDocName().replace("'", "`");
		
		try {
			String query = "insert into stuxml (mk_student_nr,mk_academic_year,mk_academic_period,reference_type,reference_value,reference_sequence,detail,date_initial,date_modified) "
					+ " values (?, ?, ?, ?, ?, 1, ?, systimestamp, systimestamp) ";

			//log.debug("SavedDocDao - addSavedDocSTUXML - query="+query+", studentNr="+studentNr+", acaYear="+acaYear+", acaPeriod="+acaPeriod+", DocCode="+doc.getDocCode()+", optReq="+optReq+", docName="+docName);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(query, new Object []{studentNr, acaYear, acaPeriod, doc.getDocCode(), optReq, docName});
			
		} catch (Exception ex) {
			throw new Exception(
				"SavedDocDaoDAO : Error writing addSavedDocSTUXML / " + ex);

		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

		public void addSavedDocSTUAPD(SavedDoc doc, String mandatory, String studentNr, String acaYear, String optReq) throws Exception{

		  /************ Update STUAPD for documents per Qualification *************/
		//log.debug("SavedDocDao - addSavedDocSTUAPD - Update STUAPD - ExistQual studentNr: " + studentNr);

			String existQual = "";

			try {

				String query   = "select new_qual AS CODE, choice_nr "
							+ " from stuapq "
							+ " where mk_student_nr = ? "
							+ " and academic_year = ? "
							+ " order by choice_nr ";
										    	  
				//log.debug("SavedDocDao - addSavedDocSTUAPD - Part1(Select Qual) - query="+query+", studentNr="+studentNr+", acaYear="+acaYear);
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
				Iterator<?> i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					existQual = data.get("CODE").toString();
					
					/**NOTE!!!
					 *	Only one entry is written per document type, never mind what the qualification is. The entry is per student
					 *	Example: Student 12345678 selects Qual1: 98502 and Qual2: 98600 
					 *			On DSPDOC, Qual1: requires UG01 and UG02 and Qual2: requires UG01, UG02 and UG03
					 *			This method should just write the following to STUAPD:
					 *			Qual1:	UG01 and UG02
					 *			Qual2:  UG03 (No need to insert UG01 and UG02 again as these are already there for the student)
					 **/
					String checkSql = "select * from STUAPD where mk_student_nr = ? and academic_year = ? and document_code = ? ";
					String updSql   = "update STUAPD set received_flag = 'Y', received_date = sysdate where mk_student_nr = ? and academic_year = ? and document_code = ? ";
					String insSql   = "insert into STUAPD (mk_student_nr,academic_year,qualification_code,document_code,received_flag, received_date, verified_flag, verified_date, doc_mandatory) "
							+ " values (?, ?, ?, ?, 'Y', sysdate, ' ', '01/JAN/01', ?)";
					try {
						//log.debug("SavedDocDao - addSavedDocSTUAPD - Part2(Select STUAPD) - checkSql="+checkSql+", studentNr="+studentNr+", acaYear="+acaYear+", DocCode="+doc.getDocCode());
						JdbcTemplate jdt2 = new JdbcTemplate(getDataSource());
						List<?> queryList2 = jdt.queryForList(checkSql, new Object []{studentNr, acaYear, doc.getDocCode()});
						Iterator<?> j = queryList2.iterator();
						if (j.hasNext()) {
							try {
								//log.debug("SavedDocDao - addSavedDocSTUAPD - Part3(Update STUAPD) - insSql="+insSql+", studentNr="+studentNr+", acaYear="+acaYear+", DocCode="+doc.getDocCode());
								JdbcTemplate jdt3 = new JdbcTemplate(getDataSource());
								int result = jdt3.update(updSql, new Object []{studentNr, acaYear, doc.getDocCode()});
							} catch (Exception ex) {
								throw new Exception(
										"SavedDocDaoDAO: Error updating addSavedDocSTUAPD STUAPD for student nr / "+studentNr + ex);
							}
							return ;
						}else {
							try {
								//log.debug("SavedDocDao - addSavedDocSTUAPD - Part4(Insert STUAPD) - insSql="+insSql+", studentNr="+studentNr+", acaYear="+acaYear+", existQual="+existQual+", DocCode="+doc.getDocCode()+", mandatory="+mandatory);
								JdbcTemplate jdt4 = new JdbcTemplate(getDataSource());
								int result = jdt4.update(insSql, new Object []{studentNr, acaYear, existQual, doc.getDocCode(), mandatory});
							} catch (Exception ex) {
								throw new Exception(
										"SavedDocDaoDAO: Error inserting addSavedDoc STUAPD for student nr / "+studentNr + ex);
							}
							return ;
						}
					} catch (Exception ex) {
						throw new Exception(
								"SavedDocDaoDAO: Error occured on reading addSavedDoc STUAPD for student nr / "+studentNr + ex);

					} finally {
						
					}
				}
				
			} catch (Exception ex) {
				throw new Exception(
						"SavedDocDaoDAO: Error occured on reading addSavedDoc STUAPQ for student nr / "+studentNr + ex);
				
			} finally {
				
			}
	}
	
	public void setSTUAPQ(String studentNr, String acaYear) throws Exception{
		//log.debug("SavedDocDao - setSTUAPQ");
		  /************ Update STUAPQ to set document record *************/
			  //log.debug("ExistQual studentNr: " + studentNr);

			  String existQual = "";

		      try {
					String query = "update STUAPQ "
								+ " set status_code = 'NP' "
								+ " where mk_student_nr = ? "
								+ " and academic_year = ? "
								+ " and status_code = 'ND' "											;

					//log.debug("setSTUAPQ query Update: " + updSql);
					JdbcTemplate jdt = new JdbcTemplate(getDataSource());
					int result = jdt.update(query, new Object []{studentNr, acaYear});
					//log.debug("setSTUAPQ Update Result: " + result);
		      	} catch (Exception ex) {
		      			throw new Exception(
								"SavedDocDaoDAO: Error updating STUAPQ upload for student nr / "+studentNr + ex);
		      	} finally {

		      	}
	}
	
	public String getSTUAPQUpload(String studentNr,String acaYear) throws Exception{
		//log.debug("SavedDocDao - getSTUAPQUpload");
		String checkAP = "";

		try{		
			String query = "select status_code "
						+ " from STUAPQ "
						+ " where mk_student_nr = ? "
						+ " and academic_year = ? "
						+ " and status_code in ('AP','TN','RG') ";
			
			//log.debug("getSTUAPQUpload - query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				checkAP = data.get("status_code").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading getting STUAPQ Upload / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		//log.debug("getSTUAPQUpload - checkAP: " + checkAP );
		return checkAP;
	}

	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 * 
	 * @param studentNr
	 * @param qualSpec
	 * @return
	 * @throws Exception
	 */
	public String vrfyPrevQual(String qualSpec, String studentNr) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi AS qualification_code, "
					+ " grd.eng_description as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
    			  	+ " from stuann, grd "
    			  	+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.status_code in ('TN','RG') "
					+ " and rownum = 1 "
					+ " order by stuann.mk_academic_year desc";
		}else{
			query = "select stuann.speciality_code, "
					+ " quaspc.english_descriptio as SpecDesc "
					+ " from stuann, grd, quaspc "
					+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_highest_qualifi = quaspc.mk_qualification_c "
					+ " and stuann.speciality_code = quaspc.speciality_code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.status_code in ('TN','RG') "
					+ " and rownum = 1 "
					+ " order by stuann.mk_academic_year desc";
		}

		try {
			//log.debug("SavedDocDaoDAO - vrfyPrevQual - query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("SavedDocDaoDAO - vrfyPrevQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("qualification_code").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
						//log.debug("SavedDocDaoDAO - vrfyPrevQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = data.get("SpecDesc").toString().trim();
						}
						//log.debug("SavedDocDaoDAO - vrfyPrevQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error validating student current and saved quals / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 * 
	 * @param studentNr
	 * @param acaYear
	 * @return
	 * @throws Exception
	 */
	public String vrfyNewQual(String qualSpec, String choice, String studentNr, String acaYear) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuapq.new_qual, stuapq.choice_nr, "
					+ " grd.eng_description as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuapq, grd "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and stuapq.choice_nr = ? ";
		}else{
			query = "select stuapq.new_spes, stuapq.choice_nr, "
					+ " quaspc.english_descriptio as SpecDesc "
					+ " from stuapq, grd, quaspc "
					+ " where stuapq.new_qual = grd.code "
					+ " and stuapq.new_qual = quaspc.mk_qualification_c "
					+ " and stuapq.new_spes = quaspc.speciality_code "
					+ " and stuapq.mk_student_nr = ? "
					+ " and stuapq.academic_year = ? "
					+ " and stuapq.choice_nr = ? ";
		}

		try {
			//log.debug("SavedDocDaoDAO - vrfyNewQual - qualSpec: " + qualSpec + ", query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear, choice});
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("SavedDocDaoDAO - vrfyNewQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("new_qual").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
						//log.debug("SavedDocDaoDAO - vrfyNewQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("new_spes").toString().trim() != null && !"".equalsIgnoreCase(data.get("new_spes").toString().trim()) && !"0".equalsIgnoreCase(data.get("new_spes").toString().trim())){
							result = data.get("new_spes").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = data.get("SpecDesc").toString().trim();
						}
						//log.debug("SavedDocDaoDAO - vrfyNewQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error validating student saved quals / " + ex);
		}
		return result;
	}
	
	/**
	 * This method retrieves the student's saved qualifications from STUAPQ as a final validation check
	 * and returns the values to applysnrcomplete.jsp and applysnrcompleteRet.jsp for new and returning
	 * students respectively
	 * 
	 * @param studentNr
	 * @param acaYear
	 * @return
	 * @throws Exception
	 */
	public String vrfyNewMDQual(String qualSpec, String studentNr, String acaYear) throws Exception {

		String query = "";
		String stuChoice = "";
		String result = "";
		
		if ("Qual".equalsIgnoreCase(qualSpec)){
			query = "select stuann.mk_highest_qualifi, "
					+ " grd.eng_description as QualDesc, "
					+ " grd.long_eng_descripti as QualLongDesc "
					+ " from stuann, grd "
					+ " where stuann.mk_highest_qualifi = grd.code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.mk_academic_year = ? "
					+ " and stuann.status_code in ('AP') ";
		}else{
			query = "select stuann.speciality_code, "
					+ "quaspc.english_descriptio as SpecDesc "
					+ "from stuann, grd, quaspc "
					+ "where stuann.mk_highest_qualifi = grd.code "
					+ "and stuann.mk_highest_qualifi = quaspc.mk_qualification_c "
					+ "and stuann.speciality_code = quaspc.speciality_code "
					+ " and stuann.mk_student_nr = ? "
					+ " and stuann.mk_academic_year = ? "
					+ " and stuann.status_code in ('AP') ";
		}

		try {
			//log.debug("SavedDocDaoDAO - vrfyNewQual - query:" + query);
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
					//log.debug("SavedDocDaoDAO - vrfyNewQual - stuChoice:" + stuChoice);
					if ("Qual".equalsIgnoreCase(qualSpec)){
						result = data.get("mk_highest_qualifi").toString().trim() + " - " + data.get("QualDesc").toString().trim() + " (" + data.get("QualLongDesc").toString().trim() + ")";
						//log.debug("SavedDocDaoDAO - vrfyNewQual - Qual:" + result);
					}else if ("Spec".equalsIgnoreCase(qualSpec)){
						if (data.get("speciality_code").toString().trim() != null && !"".equalsIgnoreCase(data.get("speciality_code").toString().trim()) && !"0".equalsIgnoreCase(data.get("speciality_code").toString().trim())){
							result = data.get("speciality_code").toString().trim() + " - " + data.get("SpecDesc").toString().trim();
						}else{
							result = data.get("SpecDesc").toString().trim();
						}
						//log.debug("SavedDocDaoDAO - vrfyNewQual - Spec:" + result);
					}
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error validating MD student saved quals / " + ex);
		}
		return result;
	}
	
	public String getFinalMD(String studentNr,String acaYear) throws Exception{
		
		String checkMD = "";
		try{		
			String query = "select grd.under_post_categor "
						+ " from stuann, grd "
						+ " where stuann.mk_highest_qualifi = grd.code "
						+ " and stuann.mk_student_nr = ? "
						+ " and stuann.mk_academic_year = ? ";

			//log.debug("getSTUAPQUpload - query: " + query );
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List<?> queryList = jdt.queryForList(query, new Object []{studentNr, acaYear});
			Iterator<?> i = queryList.iterator();
			if (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				checkMD = data.get("under_post_categor").toString();
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading getting Under_Post list / " + ex);
		} finally {
			try {

			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		//log.debug("SavedDocDaoDAO - getSTUAPQUpload - checkMD: " + checkMD );
		return checkMD;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean getMatricCert(String studentNr) throws Exception{

		boolean result = false;;
		try{		
			String query = "select matric_certificate "
						+ " from stuapp "
						+ " where application_nr = ? "
						+ " and matric_certificate = 'CG' ";

			//log.debug("SavedDocDaoDAO - getMatricCert - query="+query+", studentNr="+studentNr);

			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(query, new Object []{studentNr});
			Iterator i = queryList.iterator();
			if (i.hasNext()) {
				result = true;
			}
		} catch (Exception ex) {
			throw new Exception(
					"SavedDocDaoDAO : Error reading Matric Certificate / " + ex);
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		//log.debug("SavedDocDaoDAO - getMatricCert - result: " + result );
		return result;		
		

	}
	
}
