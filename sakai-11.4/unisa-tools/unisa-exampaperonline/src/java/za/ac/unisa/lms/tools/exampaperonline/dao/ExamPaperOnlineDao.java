package za.ac.unisa.lms.tools.exampaperonline.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Calendar;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.exampaperonline.forms.*;


public class ExamPaperOnlineDao extends StudentSystemDAO{
	
	public boolean isFirstUpload(String studyUnit,String examYear,String examPeriod, String paperNr, String docType) {
		String sql = "select count(*) " +
		             "from xposta " +
		             "where exam_year = '" + examYear.trim() + "'" +
		             " and mk_exam_period = '" + examPeriod.trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and paper_nr = '" + paperNr.trim() + "'" +
		             " and doc_type = '" + docType.toUpperCase().trim() + "'" +
		             " and LAST_ACTION <> 'RESET'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean resetValid(String userId) {	
		Calendar cal = Calendar.getInstance(); 
		int month = cal.get(Calendar.MONTH) + 1; 
		int year = cal.get(Calendar.YEAR); 
		int period = 1;
		if (month > 6) {
			period = 10;
		} else if (month > 3){
			period = 6;
		}
		String sql = "select count(*) from usrsun" + 
			" where (MK_ACADEMIC_YEAR > " + year +
			" or (MK_ACADEMIC_YEAR = " + year +
			" and MK_SEMESTER_PERIOD >=" + period + "))" +
			" and SYSTEM_TYPE ='E'" +
			" and ACCESS_LEVEL in ('1ST_EXAM','2ND_EXAM')" + 
			" and NOVELL_USER_ID = '" + userId.toUpperCase().trim() + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isExaminerType(String userId,String studyUnit,String examYear,String examPeriod,String paperNr,String examinerType) {
		String sql = "select count(*) " +
		             "from usrsun " +
		             "where novell_user_id = '" + userId.toUpperCase().trim() + "'" +
		             " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and mk_academic_year = " + Short.parseShort(examYear.trim()) +
		             " and mk_semester_period = " + Short.parseShort(examPeriod.trim()) +
		             " and nr = " + Short.parseShort(paperNr.trim()) +
		             " and system_type = 'E'" +
		             " and access_level = '" + examinerType + "'";
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}	
	
	public boolean isCod(String persno,String studyUnit) {
		String sql = "select count(*) " +
		             "from sun,dpt " +
		             "where sun.code = '" + studyUnit.toUpperCase().trim() + "'" +
		             " and sun.mk_department_code = dpt.code" +
		             " and dpt.head_of_dept = '" + persno + "'";	            
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			sql = "select count(*) " +
            "from sun,usrdpt " +
            "where sun.code = '" + studyUnit.toUpperCase().trim() + "'" +
            " and sun.mk_department_code = usrdpt.mk_department_code" +
            " and usrdpt.type='DPT'" +
            " and usrdpt.personnel_no = '" + persno + "'";
			result=jdt.queryForInt(sql);
			if (result == 0){
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}	
	
	public boolean isExamsQa1(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA1'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isExamsQa2(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA2'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isArchive(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='ARC'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isPrintProd(String userId){
		String sql = "select count(*) " +
        "from usr,funtyp " +
        "where usr.novell_user_code = '" + userId.toUpperCase().trim() + "'" +
        " and usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=796" +
        " and funtyp.type='PP'";      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}	
	
	public List getPotentialRolesToSendTo(String docType,String fromRole) throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String toRole = "";
		
		String sql = "select actioned_role from xporul " +
			         "where  current_role = '" + fromRole + "'" +
			         " and doc_type = '" + docType + "'" +
			         " and action = 'SEND'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				toRole=data.get("actioned_role").toString();	
				list.add(toRole);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xporul / " + ex,ex);
		}		
		
		return list;		
	}
	
	public List getEquivalents(String studyUnit, String examYear, String examPeriod) throws Exception {
		List eqvList = new ArrayList();
		String equivalent = "";
		String eqvSql = "select EQUIVALENT_CODE from xamequ,sunpdt" + 
		" where" + 
		" xamequ.MK_STUDY_UNIT_CODE = '" + studyUnit.toUpperCase() + "'" +
		" and XAMEQU.EQUIVALENT_CODE = SUNPDT.FK_SUNCODE " +
		" and ((SUNPDT.MK_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_EXAM_PERIOD=" + examPeriod.toString() + ")" + 
		" or (SUNPDT.MK_SUPP_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_SUPP_EXAM_PERIO=" + examPeriod.toString() + ")" +
		" or (SUNPDT.MK_3RD_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_3RD_EXAM_PERIOD=" + examPeriod.toString() + "))";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryEqvList = jdt.queryForList(eqvSql);
			Iterator j = queryEqvList.iterator();
			while (j.hasNext()){
				ListOrderedMap dataEqv = (ListOrderedMap) j.next();
				equivalent = dataEqv.get("EQUIVALENT_CODE").toString();
				if (!equivalent.contentEquals("")){
					eqvList.add(equivalent);
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xamequ,sunpdt / " + ex,ex);
		}		
		
		return eqvList;		
	}
	
	public List getEquivalentMotherCode(String studyUnit, String examYear, String examPeriod) throws Exception {
		List motherList = new ArrayList();
		String mother = "";
		String eqvSql = "select MK_STUDY_UNIT_CODE from xamequ,sunpdt" + 
		" where" + 
		" xamequ.EQUIVALENT_CODE = '" + studyUnit.toUpperCase() + "'" +
		" and XAMEQU.MK_STUDY_UNIT_CODE = SUNPDT.FK_SUNCODE " +
		" and ((SUNPDT.MK_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_EXAM_PERIOD=" + examPeriod.toString() + ")" + 
		" or (SUNPDT.MK_SUPP_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_SUPP_EXAM_PERIO=" + examPeriod.toString() + ")" +
		" or (SUNPDT.MK_3RD_EXAM_YEAR=" + examYear.toString() + " and SUNPDT.MK_3RD_EXAM_PERIOD=" + examPeriod.toString() + "))";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryEqvList = jdt.queryForList(eqvSql);
			Iterator j = queryEqvList.iterator();
			while (j.hasNext()){
				ListOrderedMap dataEqv = (ListOrderedMap) j.next();
				mother = dataEqv.get("MK_STUDY_UNIT_CODE").toString();
				if (!mother.contentEquals("")){
					motherList.add(mother);
				}
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xamequ,sunpdt / " + ex,ex);
		}
		return motherList;		
	}
	
	public List getExaminersForModulePerType(String studyUnit,String examYear,String examPeriod,String paperNr,String examinerType) throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String persNo = "";
		
		String sql = "select persno " +
        "from usrsun " +
        "where mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and mk_academic_year = " + Short.parseShort(examYear.trim()) +
        " and mk_semester_period = " + Short.parseShort(examPeriod.trim()) +
        " and nr = '" + Short.parseShort(paperNr.trim()) + "'" +
        " and system_type = 'E'" +
        " and access_level = '" + examinerType + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				persNo=data.get("persno").toString();	
				list.add(persNo);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return list;		
	}
	
	public List getExamsQa1Users()throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String persNo = "";
		
		String sql  = "select usr.personnel_no " +
        "from usr,funtyp " +
        "where usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA1'";   
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				persNo=data.get("personnel_no").toString();	
				list.add(persNo);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return list;		
	}
	
	public List getExamsQa2Users()throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String persNo = "";
		
		String sql  = "select usr.personnel_no " +
        "from usr,funtyp " +
        "where usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=795" +
        " and funtyp.type='QA2'";   
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				persNo=data.get("personnel_no").toString();	
				list.add(persNo);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return list;		
	}
	
	public List getPrintProdUsers()throws Exception {
		//Determine which of the following 
		ArrayList list = new ArrayList();
		String persNo = "";
		
		String sql  = "select usr.personnel_no " +
        "from usr,funtyp " +
        "where usr.nr = funtyp.fk_usrnr" +
        " and funtyp.fk_functnr=796" +
        " and funtyp.type='PP'";   
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				persNo=data.get("personnel_no").toString();	
				list.add(persNo);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return list;		
	}
	
	public Integer getLastLogSeqNrDocSendnotRetracted(Short examYear, Short examPeriod, String studyUnit, Short paperNr, String docType)throws Exception {
		//Determine which of the following 
		Integer seqNr = 0;
			
		String sql = "select seq_nr from xpolog a where a.action='SEND' " +
		" and exam_year = " + examYear +
        " and mk_exam_period = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and paper_nr = " + paperNr +        
        " and doc_type = '" + docType + "'" +
        " and RESET_FLAG <> 'Y'" +
		" and not exists" +
		" (select * from xpolog b where b.action='RETRACT' and b.seq_nr=(a.seq_nr+1)" +
		" and b.exam_year = " + examYear +
        " and b.mk_exam_period = " + examPeriod +
        " and b.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and b.paper_nr = " + paperNr +        
        " and b.doc_type = '" + docType + "'" + 
        " and RESET_FLAG <> 'Y'" + ")" +
		" order by a.seq_nr desc";
      	try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				seqNr=Integer.parseInt(data.get("seq_nr").toString());	
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return seqNr;		
	}
	
	public void insertExamPaperOnlineLog(ExamPaperOnlineLog log) throws Exception {
		
		String sql = "insert into xpolog (exam_year,mk_exam_period,mk_study_unit_code,paper_nr,doc_type," +
			"seq_nr,updated_on,current_user,current_role,actioned_user,actioned_role,action,approval_status,message,reset_flag) " +
			"values " +
			"(" + log.getExamYear() + "," + 
			log.getExamPeriod() + "," +
			"'" + log.getStudyUnit().toUpperCase() + "'," +
			log.getPaperNo() + "," +
			"'" + log.getDocType() + "'," +
			log.getSeqNr() + "," +
			"timestamp'" + log.getUpdatedOn() + "'," +
			"'" + log.getCurrentUser() + "'," +
			"'" + log.getCurrentRole() + "'," +
			"'" + log.getActionedUser() + "'," +
			"'" + log.getActionedRole() + "'," +
			"'" + log.getAction() + "'," +
			"'" + log.getApprovalStatus() + "'," +
			"'" + log.getMessage() + 	
			"'" + log.getResetFlag() + "')";	
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error inserting XPOLOG / " + ex,ex);
		}	
	}
	
	public void insertExamPaperOnlineStatus(ExamPaperOnlineStatus status) throws Exception {
			
			String sql = "insert into xposta (exam_year,mk_exam_period,mk_study_unit_code,paper_nr,doc_type," +
				"owner,updated_on,at_user,at_role,from_user,from_role,last_action,last_seq_nr) " +
				"values " +
				"(" + status.getExamYear() + "," + 
				status.getExamPeriod() + "," +
				"'" + status.getStudyUnit().toUpperCase() + "'," +
				status.getPaperNo() + "," +
				"'" + status.getDocType() + "'," +
				"'" + status.getOwner() + "'," +
				"timestamp'" + status.getUpdatedOn() + "'," + 
				"'" + status.getAtUser() + "'," +
				"'" + status.getAtRole() + "'," +
				"'" + status.getFromUser() + "'," +
				"'" + status.getFromRole() + "'," +
				"'" + status.getLastAction() + "'," +
				status.getLastSeqNr() + ")";
			try{ 
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				int result = jdt.update(sql);	
			}
			catch (Exception ex) {
				throw new Exception("ExamPaperOnineDao : Error inserting XPOSTA / " + ex,ex);
			}	
		}

	public void updateExamPaperOnlineStatus(ExamPaperOnlineStatus status) throws Exception {
		
		String sql = "update xposta set" +
				" owner = '" + status.getOwner() + "'," +
				" updated_on = sysdate " + "," +
				" at_user = '" + status.getAtUser() + "'," +
				" at_role = '" + status.getAtRole() + "'," +
				" from_user = '" + status.getFromUser() + "'," +
				" from_role = '" + status.getFromRole() + "'," +
				" last_action = '" + status.getLastAction() + "'," +
				" last_seq_nr = " + status.getLastSeqNr() +
				" where exam_year = " + status.getExamYear() + 
				" and mk_exam_period = " + status.getExamPeriod() + 
				" and mk_study_unit_code = '" + status.getStudyUnit().toUpperCase() + "'" + 
				" and paper_nr = " + status.getPaperNo() +
				" and doc_type = '" + status.getDocType() + "'";		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			int result = jdt.update(sql);	
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error updating XPOSTA / " + ex,ex);
		}	
	}
	
	public ExamPaperOnlineStatus getExamPaperOnlineStatus(Short examYear, Short examPeriod, String studyUnit, Short paperNr, String docType) throws Exception {
		ExamPaperOnlineStatus statusRec = new ExamPaperOnlineStatus();
		
		String sql = "select exam_year, mk_exam_period, mk_study_unit_code, paper_nr, doc_type, owner, " +
		"updated_on, at_user, at_role, from_user, from_role, last_action, last_seq_nr " +
        "from xposta " +
        "where exam_year = " + examYear +
        " and mk_exam_period = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and paper_nr = " + paperNr +        
        " and doc_type = '" + docType + "'";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				statusRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				statusRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				statusRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				statusRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				statusRec.setDocType(data.get("doc_type").toString());
				statusRec.setOwner(data.get("owner").toString());
				statusRec.setUpdatedOn(data.get("updated_on").toString());
				statusRec.setAtUser(data.get("at_user").toString());
				statusRec.setAtRole(data.get("at_role").toString());
				statusRec.setFromUser(data.get("from_user").toString());
				statusRec.setFromRole(data.get("from_role").toString());
				statusRec.setLastAction(data.get("last_action").toString());				
				statusRec.setLastSeqNr(Integer.parseInt(data.get("last_seq_nr").toString()));
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xposta / " + ex,ex);
		}		
		return statusRec;		
	}
	
	public ExamPaperOnlineLog getExamPaperOnlineLog(Short examYear, Short examPeriod, String studyUnit, Short paperNr, String docType, Integer seqNr) throws Exception {
		ExamPaperOnlineLog logRec = new ExamPaperOnlineLog();
		
		String sql = "select exam_year, mk_exam_period, mk_study_unit_code, paper_nr, doc_type, seq_nr, updated_on, current_user, current_role, actioned_user, actioned_role, action, approval_status, message " +
        "from xpolog " +
        "where exam_year = " + examYear +
        " and mk_exam_period = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and paper_nr = " + paperNr +        
        " and doc_type = '" + docType + "'" +
        " and seq_nr = " + seqNr;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				logRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				logRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				logRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				logRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				logRec.setDocType(data.get("doc_type").toString());
				logRec.setSeqNr(Integer.parseInt(data.get("seq_nr").toString()));
				logRec.setUpdatedOn(data.get("updated_on").toString());
				logRec.setCurrentUser(data.get("current_user").toString());
				logRec.setCurrentRole(data.get("current_role").toString());
				logRec.setActionedUser(replaceNull(data.get("actioned_user")));
				logRec.setActionedRole(replaceNull(data.get("actioned_role")));
				logRec.setAction(data.get("action").toString());
				logRec.setApprovalStatus(replaceNull(data.get("approval_status")));
				logRec.setMessage(replaceNull(data.get("message")));
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpolog / " + ex,ex);
		}		
		return logRec;		
	}
	
	public Rule getRule(String action, String docType, String currentRole, String actionedRole) throws Exception {
		Rule rule = new Rule();
			
		String sql = "select approval_required, rejection_allowed, approval_allowed, docs_shown " +
        "from xporul " +
        "where action = '" + action.toUpperCase().trim() + "'" +
        " and doc_type = '" + docType.toUpperCase().trim() + "'" +
        " and current_role = '" + currentRole.toUpperCase().trim() + "'";
		if (actionedRole!=null && !actionedRole.trim().equalsIgnoreCase("")){
			sql = sql + " and actioned_role = '" + actionedRole.toUpperCase().trim() + "'";
		}
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				rule.setApprovalRequired(replaceNull(data.get("approval_required")));
				rule.setApprovalAllowed(replaceNull(data.get("approval_allowed")));
				rule.setRejectionAllowed(replaceNull(data.get("rejection_allowed")));
				rule.setDocsShown(replaceNull(data.get("docs_shown")));				
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xporul / " + ex,ex);
		}		
		return rule;		
	}
	
	public List getExamPaperAuditTrail(Short examYear, Short examPeriod, String studyUnit, Short paperNr, String docType) throws Exception {
		ArrayList list = new ArrayList();
				
		String sql = "select exam_year, mk_exam_period, mk_study_unit_code, paper_nr, doc_type, seq_nr, updated_on, current_user, current_role, actioned_user, actioned_role, action, approval_status, message, reset_flag " +
        "from xpolog " +
        "where exam_year = " + examYear +
        " and mk_exam_period = " + examPeriod +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and paper_nr = " + paperNr +        
        " and doc_type = '" + docType + "'" +
        " order by updated_on";
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamPaperOnlineLog logRec = new ExamPaperOnlineLog();
				logRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				logRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				logRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				logRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				logRec.setDocType(data.get("doc_type").toString());
				logRec.setSeqNr(Integer.parseInt(data.get("seq_nr").toString()));
				logRec.setUpdatedOn(data.get("updated_on").toString());
				logRec.setCurrentUser(data.get("current_user").toString());
				logRec.setCurrentRole(data.get("current_role").toString());
				logRec.setActionedUser(replaceNull(data.get("actioned_user")));
				logRec.setActionedRole(replaceNull(data.get("actioned_role")));
				logRec.setAction(data.get("action").toString());
				logRec.setApprovalStatus(replaceNull(data.get("approval_status")));
				logRec.setMessage(replaceNull(data.get("message")));
				logRec.setResetFlag(replaceNull(data.get("reset_flag")));
				list.add(logRec);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpolog / " + ex,ex);
		}		
		return list;		
	}
	
	public List getActionedListForUser(String userId) throws Exception {
		ArrayList actionList = new ArrayList();		
		
		String sql = "select exam_year, mk_exam_period, mk_study_unit_code, paper_nr, doc_type, owner, updated_on, at_user, at_role, last_seq_nr " +
        "from xposta " +
        "where at_user = '" + userId + "'"; 
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamPaperOnlineStatus actionRec = new ExamPaperOnlineStatus();
				actionRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				actionRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				actionRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				actionRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				actionRec.setDocType(data.get("doc_type").toString());
				actionRec.setOwner(data.get("owner").toString());
				actionRec.setUpdatedOn(data.get("updated_on").toString());
				actionRec.setAtUser(data.get("at_user").toString());
				actionRec.setAtRole(data.get("at_role").toString());
				actionRec.setLastSeqNr(Integer.parseInt(data.get("last_seq_nr").toString()));
				actionList.add(actionRec);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpolog / " + ex,ex);
		}		
		return actionList;		
	}
	
	public List getActionedListPgUpPgDnForUserbck(String userId,String listAction,Integer rows,Short examYear, Short examPeriod, String studyUnit, String docType, String action, String timestamp, String userDirection) throws Exception {
		ArrayList actionList = new ArrayList();
		String sql="";
		
		
			sql = "select xposta.exam_year, xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type, xposta.owner," + 
			" to_char(xposta.updated_on,'YYYYMMDD HH24:MI:SS.FF9') as log_date, xposta.at_user, xposta.at_role, xposta.last_seq_nr, " +
			" xposta.from_user,xposta.from_role,xpolog.approval_status" +
	        " from xposta , xpolog" +
	        " where xposta.exam_year = xpolog.exam_year" +
	        " and xposta.mk_exam_period = xpolog.mk_exam_period" +
	        " and xposta.mk_study_unit_code = xpolog.mk_study_unit_code" +
	        " and xposta.paper_nr = xpolog.paper_nr" +
	        " and xposta.doc_type = xpolog.doc_type" +
	        " and xposta.last_seq_nr = xpolog.seq_nr";
			if (action.equalsIgnoreCase("RETRIEVE")){
				sql = sql + " and (xposta.last_action='RETRIEVE' or xposta.last_action='RETRACT' or xposta.last_action='RECOVER')";
			}else{
				sql = sql + " and xposta.last_action='" + action + "'";
			}
	       
			if (userDirection.equalsIgnoreCase("FROM")){
				sql = sql + " and xposta.from_user = '" + userId + "'";
			}else{
				sql = sql + " and xposta.at_user = '" + userId + "'";
			}
			if (timestamp!=null && !timestamp.equalsIgnoreCase("")){
				if (listAction.equals("PU")){
					sql = sql + " and xposta.updated_on >= to_timestamp('" + timestamp + "','YYYYMMDD HH24:MI:SS.FF9')";
				} else {
					sql = sql + " and xposta.updated_on <= to_timestamp('" + timestamp + "','YYYYMMDD HH24:MI:SS.FF9')";
				}					
			}	        
			if (studyUnit!=null && !studyUnit.equalsIgnoreCase("")){
				sql = sql + " and xposta.mk_study_unit_code ='" + studyUnit.toUpperCase() + "'";
			}
			if (examYear!=null){
				sql = sql + " and xposta.exam_year =" + examYear.toString();
			}
			if (examPeriod!=null){
				sql = sql + " and xposta.mk_exam_period =" + examPeriod.toString();
			} 
			if (docType!=null && !docType.equalsIgnoreCase("")){
				sql = sql + " and xposta.doc_type ='" + docType.toUpperCase() + "'";
			}
			if (listAction.equals("PU")){
				sql = sql + " order by xposta.updated_on, xposta.exam_year,xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type";
			} else {
				sql = sql + " order by xposta.updated_on desc, xposta.exam_year,xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type";
			}
			
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			int rownum = 0;
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamPaperOnlineLog actionRec = new ExamPaperOnlineLog();
				actionRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				actionRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				actionRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				actionRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				actionRec.setDocType(data.get("doc_type").toString());
				actionRec.setUpdatedOn(data.get("log_date").toString());
				actionRec.setActionedUser(replaceNull(data.get("at_user")));
				actionRec.setActionedRole(replaceNull(data.get("at_role")));
				actionRec.setCurrentUser(replaceNull(data.get("from_user")));
				actionRec.setCurrentRole(replaceNull(data.get("from_role")));
				actionRec.setApprovalStatus(replaceNull(data.get("approval_status")));
				rownum = rownum + 1;
				actionList.add(actionRec);
				if (rows!=null && rownum == rows + 1){
					break;
				}
			}			
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpolog / " + ex,ex);
		}		
		return actionList;		
	}
	
	public List getActionedListPgUpPgDnForUser(List listUserId,String listAction,Integer rows,Short examYear, Short examPeriod, String studyUnit, String docType, String action, String timestamp, String userDirection, String fromRole) throws Exception {
		ArrayList actionList = new ArrayList();
		String sql="";		
		
			sql = "select xposta.exam_year, xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type, xposta.owner," + 
			" to_char(xposta.updated_on,'YYYYMMDD HH24:MI:SS.FF9') as log_date, xposta.at_user, xposta.at_role, xposta.last_seq_nr, " +
			" xposta.from_user,xposta.from_role,xpolog.approval_status" +
	        " from xposta , xpolog" +
	        " where xposta.exam_year = xpolog.exam_year" +
	        " and xposta.mk_exam_period = xpolog.mk_exam_period" +
	        " and xposta.mk_study_unit_code = xpolog.mk_study_unit_code" +
	        " and xposta.paper_nr = xpolog.paper_nr" +
	        " and xposta.doc_type = xpolog.doc_type" +
	        " and xposta.last_seq_nr = xpolog.seq_nr" +
	        " and xpolog.reset_flag <> 'Y'";
			if (action.equalsIgnoreCase("RETRIEVE")){
				sql = sql + " and (xposta.last_action='RETRIEVE' or xposta.last_action='RETRACT' or xposta.last_action='RECOVER')";
			}else{
				sql = sql + " and xposta.last_action='" + action + "'";
			}
						  
			if (userDirection.equalsIgnoreCase("FROM")){
				sql = sql + " and xposta.from_user in (";
				for (int i=0; i < listUserId.size(); i++){
					if (i==0){
						sql = sql + "'" + listUserId.get(i).toString() + "'";
					}else{
						sql = sql + ",'" + listUserId.get(i).toString() + "'";
					}					
				}
				sql = sql + ")";
			}else{
				sql = sql + " and xposta.at_user in (";
				for (int i=0; i < listUserId.size(); i++){
					if (i==0){
						sql = sql + "'" + listUserId.get(i).toString() + "'";
					}else{
						sql = sql + ",'" + listUserId.get(i).toString() + "'";
					}					
				}
				sql = sql + ")";
			}
			if (timestamp!=null && !timestamp.equalsIgnoreCase("")){
				if (listAction.equals("PU")){
					sql = sql + " and xposta.updated_on >= to_timestamp('" + timestamp + "','YYYYMMDD HH24:MI:SS.FF9')";
				} else {
					sql = sql + " and xposta.updated_on <= to_timestamp('" + timestamp + "','YYYYMMDD HH24:MI:SS.FF9')";
				}					
			}	        
			if (studyUnit!=null && !studyUnit.equalsIgnoreCase("")){
				sql = sql + " and (xposta.mk_study_unit_code ='" + studyUnit.toUpperCase() + "'" +
				" or XPOSTA.MK_STUDY_UNIT_CODE = (select XAMEQU.MK_STUDY_UNIT_CODE from xamequ where XAMEQU.EQUIVALENT_CODE='" + studyUnit.toUpperCase() + "'))";
			}
			if (examYear!=null){
				sql = sql + " and xposta.exam_year =" + examYear.toString();
			}
			if (examPeriod!=null){
				sql = sql + " and xposta.mk_exam_period =" + examPeriod.toString();
			} 
			if (docType!=null && !docType.equalsIgnoreCase("")){
				sql = sql + " and xposta.doc_type ='" + docType.toUpperCase() + "'";
			}
			if (fromRole!=null && !fromRole.equalsIgnoreCase("") && !fromRole.equalsIgnoreCase("ALL")){
				sql = sql + " and xposta.from_role ='" + fromRole.toUpperCase() + "'";
			}
			if (listAction.equals("PU")){
				sql = sql + " order by xposta.updated_on, xposta.exam_year,xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type";
			} else {
				sql = sql + " order by xposta.updated_on desc, xposta.exam_year,xposta.mk_exam_period, xposta.mk_study_unit_code, xposta.paper_nr, xposta.doc_type";
			}
			
		
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			int rownum = 0;
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				ExamPaperOnlineLog actionRec = new ExamPaperOnlineLog();
				actionRec.setExamYear(Short.parseShort(data.get("exam_year").toString()));	
				actionRec.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				actionRec.setStudyUnit(data.get("mk_study_unit_code").toString());
				actionRec.setPaperNo(Short.parseShort(data.get("paper_nr").toString()));
				actionRec.setDocType(data.get("doc_type").toString());
				actionRec.setUpdatedOn(data.get("log_date").toString());
				actionRec.setActionedUser(replaceNull(data.get("at_user")));
				actionRec.setActionedRole(replaceNull(data.get("at_role")));
				actionRec.setCurrentUser(replaceNull(data.get("from_user")));
				actionRec.setCurrentRole(replaceNull(data.get("from_role")));
				actionRec.setApprovalStatus(replaceNull(data.get("approval_status")));
				LinkedPaper linkedPaper = new LinkedPaper();
				List eqvList = new ArrayList();
				linkedPaper = getLinkedInfo(actionRec.getStudyUnit(),
						actionRec.getExamYear().toString(),
						actionRec.getExamPeriod().toString());				
				if (linkedPaper.getInputStudyUnitStatus().equalsIgnoreCase("LINKED")){
					//do nothing - do not display linked papers
				}else{
					rownum = rownum + 1;
					actionRec.setLinkedPaper(linkedPaper);
					actionList.add(actionRec);
					if (rows!=null && rownum == rows + 1){
						break;
					}
				}				
			}			
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpolog / " + ex,ex);
		}		
		return actionList;		
	}
	
	public List getPrintAnnexuresOld(Short examYear, Short examPeriod, String studyUnit, Short paperNo) throws Exception {
		ArrayList annexureList = new ArrayList();		
		
		String sql = "select 1,'1.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'GP'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 2,'2.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'MP'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 3,'3.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'A3'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 4,'4.' as position, xamsty.eng_description, 'ACAD' as responsibility,to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'TB'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 5,'5.' as position, xamsty.eng_description, 'ACAD' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'FS'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 6,'6.' as position, xamsty.eng_description, 'ACAD' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'LA'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +		
		" select 7,'7.' as position, xamsty.eng_description, 'ACAD' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'RS'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +		
		" select 8,'8.' as position, 'MARK READING SHEET INSTRUCTIONS' as eng_description, 'EAD' as responsibility, decode(mcq_instr_flag,'Y','Yes','N','No',' ') as detail" +
        " from xampcd " +
        " where mk_exam_year(+) = " + examYear +
        " and mk_exam_period(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and mk_paper_nr(+) = " + paperNo +
		" union" +		
		" select 9,'9.' as position, 'ATTENDANCE REGISTER' as eng_description, 'EAD' as responsibility, decode(attend_reg_flag,'Y','Yes','N','No',' ') as detail" +
        " from xampcd " +
        " where mk_exam_year(+) = " + examYear +
        " and mk_exam_period(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and mk_paper_nr(+) = " + paperNo +
		" union" +		
		" select 10,'10.' as position, 'COLOUR OF QUESTION PAPER' as eng_description, 'EAD' as responsibility, paper_colour as detail" +
        " from xampcd " +
        " where mk_exam_year(+) = " + examYear +
        " and mk_exam_period(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and mk_paper_nr(+) = " + paperNo;
				
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Annexure record = new Annexure();
				record.setPosition(data.get("position").toString());
				record.setDescription(data.get("eng_description").toString());
				record.setResponsibility(data.get("responsibility").toString());
				record.setAveragePerStudent(replaceNull(data.get("detail")));
				annexureList.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpapst, xamsty / " + ex,ex);
		}		
		return annexureList;		
	}
	
	public List getPrintAnnexures(Short examYear, Short examPeriod, String studyUnit, Short paperNo) throws Exception {
		ArrayList annexureList = new ArrayList();		
		
		String sql = "select 1,'1.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'GP'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 2,'2.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'MP'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" +
		" union" +
		" select 3,'3.' as position, xamsty.eng_description, 'PP' as responsibility, to_char(xpapst.average_per_studen,'9999') as detail" +
        " from xpapst, xamsty " +
        " where xpapst.exam_year(+) = " + examYear +
        " and xpapst.mk_exam_period_cod(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and xpapst.nr(+) = " + paperNo +
		" and xamsty.item_code = 'A3'" +
		" and xpapst.fk_item_code(+) = xamsty.item_code" + 
		" union" +		
		" select 4,'4.' as position, 'MARK READING SHEET INSTRUCTIONS' as eng_description, " +
		" decode((select ELECTRONIC_PAPER from xtloge where xampcd.mk_exam_year=xtloge.fk_exam_year" +
		" and xampcd.mk_exam_period = xtloge.fk_exam_period_cod" +
		" and xampcd.mk_study_unit_code = xtloge. fk_study_unit_code" +
		" and xampcd.mk_paper_nr = xtloge.paper_no),'Y','ACAD','EAD') as responsibility, decode(mcq_instr_flag,'Y','Yes','N','No',' ') as detail" +
        " from xampcd " +
        " where mk_exam_year(+) = " + examYear +
        " and mk_exam_period(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and mk_paper_nr(+) = " + paperNo +
		" union" +		
		" select 5,'5.' as position, 'ATTENDANCE REGISTER' as eng_description, " +
		" decode((select ELECTRONIC_PAPER from xtloge where xampcd.mk_exam_year=xtloge.fk_exam_year" +
		" and xampcd.mk_exam_period = xtloge.fk_exam_period_cod" +
		" and xampcd.mk_study_unit_code = xtloge. fk_study_unit_code" +
		" and xampcd.mk_paper_nr = xtloge.paper_no),'Y','ACAD','EAD') as responsibility, decode(attend_reg_flag,'Y','Yes','N','No',' ') as detail" +
        " from xampcd " +
        " where mk_exam_year(+) = " + examYear +
        " and mk_exam_period(+) = " + examPeriod +
        " and mk_study_unit_code(+) = '" + studyUnit + "'" +
		" and mk_paper_nr(+) = " + paperNo;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				Annexure record = new Annexure();
				record.setPosition(data.get("position").toString());
				record.setDescription(data.get("eng_description").toString());
				record.setResponsibility(data.get("responsibility").toString());
				record.setAveragePerStudent(replaceNull(data.get("detail")));
				annexureList.add(record);
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xpapst, xamsty / " + ex,ex);
		}		
		return annexureList;		
	}
	
	public boolean coverDocketExist(String examYear, String examPeriod, String studyUnit, String paperNo){
		String sql = "select count(*) " +
        "from xampcd " +
        "where mk_exam_year = " + Short.parseShort(examYear.trim()) +
        " and mk_exam_period = " + Short.parseShort(examPeriod.trim()) +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and mk_paper_nr = " + Short.parseShort(paperNo.trim());      
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		int result = jdt.queryForInt(sql) ;
		if (result == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public Short getNrOfDocsSubmitted(String examYear, String examPeriod, String studyUnit, String paperNo)throws Exception {
		Short nrOfDocs = 0;
		String sql = "select NR_DOCS_SUBMITTED " +
        "from xampcd " +
        "where mk_exam_year = " + Short.parseShort(examYear.trim()) +
        " and mk_exam_period = " + Short.parseShort(examPeriod.trim()) +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and mk_paper_nr = " + Short.parseShort(paperNo.trim());      
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				if (data.get("NR_DOCS_SUBMITTED")==null){
					//nothing
				}else {
					nrOfDocs=Short.parseShort(data.get("NR_DOCS_SUBMITTED").toString());
				}
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xampcd / " + ex,ex);
		}		
		return nrOfDocs;		
	}
	
	public CoverDocket getCoverDocket(String examYear, String examPeriod, String studyUnit, String paperNo)throws Exception {
		CoverDocket coverDocket = new CoverDocket();
		String sql = "select mk_exam_year, mk_exam_period, mk_study_unit_code, mk_paper_nr, paper_colour, NR_DOCS_SUBMITTED " +
        "from xampcd " +
        "where mk_exam_year = " + Short.parseShort(examYear.trim()) +
        " and mk_exam_period = " + Short.parseShort(examPeriod.trim()) +
        " and mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and mk_paper_nr = " + Short.parseShort(paperNo.trim());      
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();				
				coverDocket.setExamYear(Short.parseShort(data.get("mk_exam_year").toString()));
				coverDocket.setExamPeriod(Short.parseShort(data.get("mk_exam_period").toString()));
				coverDocket.setPaperNo(Short.parseShort(data.get("mk_paper_nr").toString()));
				coverDocket.setStudyUnit(data.get("mk_study_unit_code").toString());
				coverDocket.setPaperColour(replaceNull(data.get("paper_colour")));
				if (data.get("NR_DOCS_SUBMITTED")==null){
					//nothing
				}else {
					coverDocket.setNrDocsSubmitted(Short.parseShort(data.get("NR_DOCS_SUBMITTED").toString()));
				}
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xampcd / " + ex,ex);
		}		
		return coverDocket;		
	}
		
	private String replaceNull(Object object){
		String stringValue="";
		if (object==null){			
		}else{
			stringValue=object.toString();
		}			
		return stringValue;
	}
	
	public LinkedPaper getLinkedInfo(String studyUnit, String examYear, String examPeriod) throws Exception {
		String inputType="SINGLE";
		List linkedList = new ArrayList();
		String motherCode = "";	
		LinkedPaper linkedPaper = new LinkedPaper();
			
		String sql= "select distinct mk_study_unit_code," +
		" EQUIVALENT_CODE," + 
		" DECODE(MK_STUDY_UNIT_CODE,'" + studyUnit.toUpperCase().trim() + "','MOTHER','LINKED') as input" +
		" from xamequ a,sunpdt b, sunpdt c" +
		" where (a.MK_STUDY_UNIT_CODE = '" + studyUnit.toUpperCase().trim() + "'" +
		" or a.EQUIVALENT_CODE  = '" + studyUnit.toUpperCase().trim() + "')" +
		" and a.MK_STUDY_UNIT_CODE = b.FK_SUNCODE" +
		" and ((b.MK_EXAM_YEAR = " + examYear + " and b.MK_EXAM_PERIOD=" + examPeriod + ")" + 
		" or (b.MK_SUPP_EXAM_YEAR = " + examYear + " and b.MK_SUPP_EXAM_PERIO=" + examPeriod + ")" +
		" or (b.MK_3RD_EXAM_YEAR= " + examYear + " and b.MK_3RD_EXAM_PERIOD=" + examPeriod + "))" +
		" and a.EQUIVALENT_CODE  = c.FK_SUNCODE" +
		" and ((c.MK_EXAM_YEAR = " + examYear + " and c.MK_EXAM_PERIOD=" + examPeriod + ")" + 
		" or (c.MK_SUPP_EXAM_YEAR = " + examYear + " and c.MK_SUPP_EXAM_PERIO=" + examPeriod + ")" +
		" or (c.MK_3RD_EXAM_YEAR= " + examYear + " and c.MK_3RD_EXAM_PERIOD=" + examPeriod + "))";
		try{
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryEqvList = jdt.queryForList(sql);
			Iterator j = queryEqvList.iterator();
			while (j.hasNext()){
				ListOrderedMap data= (ListOrderedMap) j.next();
				motherCode = data.get("MK_STUDY_UNIT_CODE").toString();
				linkedList.add(data.get("EQUIVALENT_CODE").toString());
				inputType = data.get("input").toString();
			}
			linkedPaper.setInputStudyUnitStatus(inputType);
			linkedPaper.setMotherCode(motherCode);
			linkedPaper.setLinkedPapers(linkedList);
			String desc ="";
			for (int i=0; i < linkedList.size(); i++){
				if(desc.equalsIgnoreCase("")){
					desc = linkedList.get(i).toString();
				}else{
					desc = desc + "," + linkedList.get(i).toString();
				}				
			}
			linkedPaper.setLinkedDesc(desc);
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table xamequ,sunpdt / " + ex,ex);
		}
		return linkedPaper ;		
	}
	
	public String updateRecordsOnAction(List updateList, ExamPaperOnlineStatus statusRec, ExamPaperOnlineLog logRec, Xtlog xtlogRec, Xtloge xtlogeRec, Integer nrOfDocuments) throws Exception {
		
		String errMsg="";
		
		Connection connection = null;
		try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			connection = jdt.getDataSource().getConnection();
			connection.setAutoCommit(false);	
			
			for (int i=0; i<updateList.size(); i++){
				UpdateModuleLog record = new UpdateModuleLog();
				record = (UpdateModuleLog)updateList.get(i);	
				
				
				Statement stmt = connection.createStatement();
			    //status record : xposta
				if (record.getStatusAction().equalsIgnoreCase("INSERT")){
				   stmt.executeUpdate("insert into xposta (exam_year,mk_exam_period,mk_study_unit_code,paper_nr,doc_type," +
							"owner,updated_on,at_user,at_role,from_user,from_role,last_action,last_seq_nr) " +
							"values " +
							"(" + statusRec.getExamYear() + "," + 
							statusRec.getExamPeriod() + "," +
							"'" + record.getStudyUnit().toUpperCase() + "'," +
							statusRec.getPaperNo() + "," +
							"'" + statusRec.getDocType() + "'," +
							"'" + statusRec.getOwner() + "'," +
							"timestamp'" + statusRec.getUpdatedOn() + "'," + 
							"'" + statusRec.getAtUser() + "'," +
							"'" + statusRec.getAtRole() + "'," +
							"'" + statusRec.getFromUser() + "'," +
							"'" + statusRec.getFromRole() + "'," +
							"'" + statusRec.getLastAction() + "'," +
							record.getSeqNr() + ")" );
				}
				if (record.getStatusAction().equalsIgnoreCase("UPDATE")){
				   stmt.executeUpdate("update xposta set" +
							" owner = '" + statusRec.getOwner() + "'," +
							" updated_on = timestamp'" + statusRec.getUpdatedOn() + "'," +
							" at_user = '" + statusRec.getAtUser() + "'," +
							" at_role = '" + statusRec.getAtRole() + "'," +
							" from_user = '" + statusRec.getFromUser() + "'," +
							" from_role = '" + statusRec.getFromRole() + "'," +
							" last_action = '" + statusRec.getLastAction() + "'," +
							" last_seq_nr = " + record.getSeqNr() +
							" where exam_year = " + statusRec.getExamYear() + 
							" and mk_exam_period = " + statusRec.getExamPeriod() + 
							" and mk_study_unit_code = '" + record.getStudyUnit().toUpperCase() + "'" + 
							" and paper_nr = " + statusRec.getPaperNo() +
							" and doc_type = '" + statusRec.getDocType() + "'" );
				}
				//log record : xpolog
				stmt.executeUpdate("insert into xpolog (exam_year,mk_exam_period,mk_study_unit_code,paper_nr,doc_type," +
						"seq_nr,updated_on,current_user,current_role,actioned_user,actioned_role,action,approval_status,message,reset_flag) " +
						"values " +
						"(" + logRec.getExamYear() + "," + 
						logRec.getExamPeriod() + "," +
						"'" + record.getStudyUnit().toUpperCase() + "'," +
						logRec.getPaperNo() + "," +
						"'" + logRec.getDocType() + "'," +
						record.getSeqNr() + "," +
						"timestamp'" + logRec.getUpdatedOn() + "'," +
						"'" + logRec.getCurrentUser() + "'," +
						"'" + logRec.getCurrentRole() + "'," +
						"'" + logRec.getActionedUser() + "'," +
						"'" + logRec.getActionedRole() + "'," +
						"'" + logRec.getAction() + "'," +
						"'" + logRec.getApprovalStatus() + "'," +
						"'" + logRec.getMessage() + "'," +
						"'" + logRec.getResetFlag() + "')" );
				if (logRec.getAction().equalsIgnoreCase("RESET")){
					//reset all previous log records
					stmt.executeUpdate("update xpolog set reset_flag='Y'" +
							" where exam_year = " + logRec.getExamYear() + 
							" and mk_exam_period = " + logRec.getExamPeriod() + 
							" and mk_study_unit_code = '" + record.getStudyUnit().toUpperCase() + "'" + 
							" and paper_nr = " + logRec.getPaperNo() +
							" and doc_type = '" + logRec.getDocType() + "'" );		
				}		
				//xampcd - update nr of documents submitted
				if (logRec.getDocType().equalsIgnoreCase("QUESTION") && logRec.getAction().equalsIgnoreCase("SEND")){
					if (nrOfDocuments!=null && nrOfDocuments!=0){
						stmt.executeUpdate("update xampcd set NR_DOCS_SUBMITTED = " + nrOfDocuments +
								" where MK_EXAM_YEAR = " + logRec.getExamYear() + 
								" and MK_EXAM_PERIOD = " + logRec.getExamPeriod() + 
								" and MK_STUDY_UNIT_CODE = '" + record.getStudyUnit().toUpperCase() + "'" + 
								" and MK_PAPER_NR = " + logRec.getPaperNo()); 
					}	
				}
				//xtlog record
				if (xtlogRec==null || xtlogRec.getExamYear()==null){
					//do nothing
				}else{
					//read record
					boolean xtlogExists = false;
					String sql = "select count(*)" +
			        " from xtlog" +
			        " where exam_year = " + xtlogRec.getExamYear() +
			        " and mk_exam_period_cod = " + xtlogRec.getExamPeriod() +
			        " and mk_study_unit_code = '" + record.getStudyUnit().toUpperCase().trim() + "'";     
					
					int result = jdt.queryForInt(sql) ;
					if (result == 0) {
						xtlogExists = false;
					} else {
						xtlogExists = true;
					}
					
					if (xtlogExists){
						//if exists - update
						//to be done if necessary
					}else{
						//if record does not exists - insert
						stmt.executeUpdate("insert into xtlog (exam_year,mk_exam_period_cod,mk_study_unit_code,combined_with,remarks,remarks2,remarks3," +
								"remarks4,remarks5,remarks6,paper_expected) " +
								"values " +
								"(" + xtlogRec.getExamYear() + "," + 
								xtlogRec.getExamPeriod() + "," +
								"'" + record.getStudyUnit().toUpperCase().trim() + "'," +
								"'" + xtlogRec.getCombined_with() + "'," +
								"'" + xtlogRec.getRemarks() + "'," +
								"'" + xtlogRec.getRemarks2() + "'," +
								"'" + xtlogRec.getRemarks3() + "'," +
								"'" + xtlogRec.getRemarks4() + "'," +
								"'" + xtlogRec.getRemarks5() + "'," +
								"'" + xtlogRec.getRemarks6() + "'," +
								"'" + xtlogRec.getPaperExpected() + "')" );
					}
					
				}
				//xtloge record				
				if (xtlogeRec==null ||xtlogeRec.getExamYear()==null){
					//do nothing
				}else{
					//read record
					boolean xtlogeExists = false;
					String sql = "select count(*)" +
			        " from xtloge " +
			        " where fk_exam_year = " + xtlogeRec.getExamYear() +
			        " and fk_exam_period_cod = " + xtlogeRec.getExamPeriod() +
			        " and fk_study_unit_code = '" + record.getStudyUnit().toUpperCase().trim() + "'" +
			        " and paper_no = " + xtlogeRec.getPaperNo();       
					
					int result = jdt.queryForInt(sql) ;
					if (result == 0) {
						xtlogeExists = false;
					} else {
						xtlogeExists = true;
					}
					if (xtlogeExists){
						//if record exists -update
						stmt.executeUpdate("update xtloge set" +
								" date_received = to_date('" + xtlogeRec.getDateReceived() + "','YYYY-MM-DD')," +
								" date_to_dept = to_date('" + xtlogeRec.getDateToDept() + "','YYYY-MM-DD')," +
								" date_from_dept = to_date('" + xtlogeRec.getDateFromDept() + "','YYYY-MM-DD')," +
								" date_to_print = to_date('" + xtlogeRec.getDateToPrint() + "','YYYY-MM-DD')," +
								" quant_to_print = " + Short.parseShort(xtlogeRec.getQuantToPrint()) + "," +
								" quant_calced_on = to_date('" + xtlogeRec.getQuantCalcOn() + "','YYYY-MM-DD')," +
								" date2_to_print = to_date('" + xtlogeRec.getDate2ToPrint() + "','YYYY-MM-DD')," +
								" quant2_to_print = " + Short.parseShort(xtlogeRec.getQuant2ToPrint()) + "," +
								" quant2_calced_on = to_date('" + xtlogeRec.getQuant2CalcOn() + "','YYYY-MM-DD')," +
								" electronic_paper = '" + xtlogeRec.getElectronicFlag() + "'" +
								" where fk_exam_year = " + xtlogeRec.getExamYear() + 
								" and fk_exam_period_cod = " + xtlogeRec.getExamPeriod() + 
								" and fk_study_unit_code = '" + record.getStudyUnit().toUpperCase().trim() + "'" + 
								" and paper_no = " + xtlogeRec.getPaperNo());
					}else{
						//if record does not exists - insert
						stmt.executeUpdate("insert into xtloge (paper_no,typist,paper_format,date_received,date_proof1,date_proof2,date_proof3,date_to_print," +
								"cpf_report_printed,fk_study_unit_code,fk_exam_period_cod,fk_exam_year,date_in_safe,date_to_dept,date_from_dept,date2_to_print," +
								"date2_from_print,date3_to_print,date3_from_print,quant3_to_print,quant_to_print,quant_calced_on,quant2_to_print," +
								"quant2_calced_on,surplus_quant,date_scanned,date_afr_scanned,open_for_web,docket_changes,paper_changes,electronic_paper) " +
							"values " +
							"(" + xtlogeRec.getPaperNo() + "," +                                  //paper_no
							"' '," +                                               //typist
							"' '," +                                               //paper_format
							"to_date('" + xtlogeRec.getDateReceived() + "','YYYY-MM-DD')," +        //date_received
							"to_date('00010101','YYYYMMDD')," +                    //date_proof1
							"to_date('00010101','YYYYMMDD')," +                    //date_proof2
							"to_date('00010101','YYYYMMDD')," +                    //date_proof3
							"to_date('00010101','YYYYMMDD')," +                    //date_to_print
							"' '," +                                               //cpf_report_printed
							"'" + record.getStudyUnit().toUpperCase().trim() + "'," +                //fk_study_unit_code
							xtlogeRec.getExamPeriod() + "," +                      //fk_exam_period_cod
							xtlogeRec.getExamYear() + "," +                                       //fk_exam_year
							"to_date('00010101','YYYYMMDD')," +                    //date_in_safe
							"to_date('00010101','YYYYMMDD')," +                    //date_to_dept
							"to_date('00010101','YYYYMMDD')," +                    //date_from_dept
							"to_date('00010101','YYYYMMDD')," +                    //date2_to_print
							"to_date('00010101','YYYYMMDD')," +                    //date2_from_print
							"to_date('00010101','YYYYMMDD')," +                    //date3_to_print
							"to_date('00010101','YYYYMMDD')," +                    //date3_from_print
							0 + "," +                                              //quant3_to_print
							0 + "," +                                              //quant_to_print
							"to_date('00010101','YYYYMMDD')," +                    //quant_calced_on
							0 + "," +                                              //quant2_to_print
							"to_date('00010101','YYYYMMDD')," +                    //quant2_calced_on
							0 + "," +                                              //surplus_quant
							"to_date('00010101','YYYYMMDD')," +                    //date_scanned
							"to_date('00010101','YYYYMMDD')," +                    //date_afr_scanned
							"'Y'," +                                               //open_for_web
							"'N'," +                                               //docket_changes
							"'N'," +                                               //paper_changes
							"'" + xtlogeRec.getElectronicFlag() + "')" );
					}
					
					
				}			   			
			}
			connection.commit();
			connection.setAutoCommit(true);	
			connection.close();
		}
		catch (Exception ex) {
			if (connection!=null){				
					connection.rollback();	
					connection.setAutoCommit(true);
					connection.close();
					throw new Exception("ExamPaperOnineDao : Error updating records, records have been rollbacked / " + ex,ex);	
			}
		}	
		return errMsg;
	}	
	public Integer getLastLogSeqNrForLastActionRole(Short examYear, Short examPeriod, String studyUnit, Short paperNr, String docType,String action, String role, String userType)throws Exception {
		//Determine which of the following 
		//userType allowed values : CURRENT, ACTIONED
		//role: any role
		//action: SEND, RETRIEVE, RETRACT
		Integer seqNr = 0;
			
		String sql = "select seq_nr from xpolog a where a.action='" + action + "'" +
		" and a.exam_year = " + examYear +
        " and a.mk_exam_period = " + examPeriod +
        " and a.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
        " and a.paper_nr = " + paperNr +        
        " and a. doc_type = '" + docType + "'" +
        " and a.reset_flag <> 'Y'";
		
        if (userType!=null && !userType.equalsIgnoreCase("")){
        	if (userType.equalsIgnoreCase("CURRENT")){
        		if (role!=null && !role.equalsIgnoreCase("")){
        			sql = sql + "and a.current_role = '" + role + "'";
        		}
        	}
        	if (userType.equalsIgnoreCase("ACTIONED")){
        		if (role!=null && !role.equalsIgnoreCase("")){
        			sql = sql + "and a.actioned_role = '" + role + "'";
        		}
        	}        	
        }		
        
        if (action.equalsIgnoreCase("SEND")){
        	 sql = sql +
     		" and not exists" +
     		" (select * from xpolog b where b.action='RETRACT' and b.seq_nr=(a.seq_nr+1)" +
     		" and b.exam_year = " + examYear +
             " and b.mk_exam_period = " + examPeriod +
             " and b.mk_study_unit_code = '" + studyUnit.toUpperCase().trim() + "'" +
             " and b.paper_nr = " + paperNr +        
             " and b.doc_type = '" + docType + "'" +
             " and a.reset_flag <> 'Y')";
        }        
        sql = sql + " order by a.seq_nr desc";
      	
        try{ 
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			List queryList = jdt.queryForList(sql);

			Iterator i = queryList.iterator();
			while (i.hasNext()) {
				ListOrderedMap data = (ListOrderedMap) i.next();
				seqNr=Integer.parseInt(data.get("seq_nr").toString());	
				break;
			}
		}
		catch (Exception ex) {
			throw new Exception("ExamPaperOnineDao : Error reading table usrsun / " + ex,ex);
		}		
		return seqNr;		
	}
}
