package za.ac.unisa.lms.tools.tpstudymaterial.dao;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.sakaiproject.component.cover.ServerConfigurationService;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.utils.NoStudyMaterialIssue;
public class TpStudyMaterialDAO extends StudentSystemDAO{
	/**
	 * Method: getStudentLastAcadYear
	 *  to select the students last academic year
	 *  from the student system database
	 *
	 * @param studentNr
	 * @return lastAcadYear
	 * @throws Exception
	 */
	public String getStudentLastAcadYear(String studentNr) throws Exception{
		String lastAcadYear;
		String query = "select max(mk_academic_year) as YEAR from stuann where mk_student_nr="+ studentNr;
		try{
			lastAcadYear = this.querySingleValue(query,"YEAR").toString();			
		} catch (Exception ex) {
            throw new Exception("TpStudyMaterialDAO: getStudentLastAcadYear: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return lastAcadYear;
	}
	public boolean getStudentExist(String studentNr) throws Exception{

		boolean studentExist = false;
		String query = "SELECT COUNT(*) AS A "+
	   	  				"FROM  STU " +
	   	  				"WHERE NR  = " + studentNr;
		try{
			int counter = Integer.parseInt(this.querySingleValue(query,"A"));
			if (counter >= 1) {
				studentExist = true;
			}

		} catch (Exception ex) {
            throw new Exception("TpStudyMaterialDAO: getStudentCell: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return studentExist;
	}
	public String getTpRegistrationStatus(String studentNr,String lastAcadYear) throws Exception{
        String courses;
        String select = "select status_code from stusun  where fk_academic_year = "+lastAcadYear+
                         " and fk_academic_period = 1  and  fk_student_nr ="+studentNr;
        //and  status_code='TP'
  
		try{
    		JdbcTemplate jdt = new JdbcTemplate(super.getDataSource());
    		 courses = this.querySingleValue(select,"status_code");    		 
		}catch (Exception ex) {
            throw new Exception("TpStudyMaterialDAO: getTpRegistrationStatus: (stno: "+studentNr+") Error occurred / "+ ex,ex);
		}
		return courses;
	}
	public ArrayList getCourseList(String studentNr,String lastAcademicYear){
		ArrayList results = new ArrayList();
/*		String sql = "  select s.MK_STUDY_UNIT_CODE Course,s.FK_ACADEMIC_YEAR year,decode(s.SEMESTER_PERIOD,1,'S1',2,'S2',0,'Y1',6,'Y2') as Semister from stuann a, stusun s"+
                    " where a.status_code in ('TN','RG') and s.FK_ACADEMIC_YEAR ="+lastAcademicYear+" and s.FK_STUDENT_NR="+studentNr+
                    "  and a.MK_STUDENT_NR =s.FK_STUDENT_NR and s.FK_ACADEMIC_YEAR=a.mk_academic_year";*/
		
		String sql =  "select s.MK_STUDY_UNIT_CODE Course,s.FK_ACADEMIC_YEAR year,decode(s.SEMESTER_PERIOD,1,'S1',2,'S2',0,'Y1',6,'Y2') as Semister "+
				      "from stuann a,stusun s where a.status_code in ('TN','RG') "+
				      "and s.FK_ACADEMIC_YEAR = "+lastAcademicYear+" and s.FK_STUDENT_NR="+studentNr+
				      "and a.MK_STUDENT_NR =s.FK_STUDENT_NR and s.FK_ACADEMIC_YEAR=a.mk_academic_year "+
				      "and s.SEMESTER_PERIOD != 2 union all "+
	                  "select Course,year,Semister from( select s.MK_STUDY_UNIT_CODE Course,s.FK_ACADEMIC_YEAR year,decode(s.SEMESTER_PERIOD,1,'S1',2,'S2',0,'Y1',6,'Y2') as Semister "+
	                  "from stuann a,stusun s where a.status_code in ('TN','RG') "+
	                  "and s.FK_ACADEMIC_YEAR = "+lastAcademicYear+" and s.FK_STUDENT_NR="+studentNr+
	                  " and a.MK_STUDENT_NR =s.FK_STUDENT_NR and s.FK_ACADEMIC_YEAR=a.mk_academic_year and s.SEMESTER_PERIOD = 2 ) "+
	                  "left join  REGDAT reg on year = Academic_year where SEMESTER_PERIOD = 2 "+
	                  "and TYPE = 'NOSTMISS' and SYSDATE > TO_DATE";
		
		//and s.status_code = 'TP'

		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  try{
		  while (i.hasNext()) {			
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  StudyMaterialDetails studyMaterialDetails=new StudyMaterialDetails();
			  studyMaterialDetails.setCourseCode(data.get("Course").toString());
			  studyMaterialDetails.setAcademicYear(data.get("year").toString().substring(2));
			  studyMaterialDetails.setSemister(data.get("Semister").toString());
			  NoStudyMaterialIssue noStMIss = new NoStudyMaterialIssue();
			// studyMaterialDetails.setBlockedStatus(true);
			  studyMaterialDetails.setBlockedStatus(noStMIss.getBlockedStatus(data.get("year").toString(),studyMaterialDetails.getSemister()));		  
			  results.add(studyMaterialDetails);
			}
		  }
	    catch(NullPointerException e ){e.printStackTrace();
	    }		
   return results;
	}
	
	
	public ArrayList getStudyMaterialList(String course,String academicYear,String semister){
		ArrayList results = new ArrayList();
		if(semister.equals("S1")){
			semister="1";
		}else if(semister.equals("S2")){
			semister="2";
		}else if(semister.equals("Y1")){
			semister="0";
		}else if(semister.equals("Y2")){
			semister="6";
		}
		 // String urlPrefix = ServerConfigurationService.getString("studyMaterialUrlPrefix");
		   String urlPrefix = ServerConfigurationService.getString("www3.base");
		   String secret = ServerConfigurationService. getString ("studymaterial.secret");


		 String sql ="select  decode(st.fk_annstm_stmtyp, 'TL', 'Tutorial Letter', 'GD', 'Study Guide', 'MA','Florida Manual', 'WB','Workbook'," 
              +" 'QB','Question Bank','MO','Module','LB','Logbook','BL','Booklet','BB','Business Calculations','HL','H Law Documents','RE','Reader','MG','Mentor Guide') ||' '|| st.fk_annstmnr||' ('||"
              +" decode(st.fk_annstm_lang,'A','Afr','E','Eng','Both') ||') for '||st.fk_annstmname as Item,"
	          +" st.fk_annstmname || '/' || decode(st.fk_annstm_stmtyp, 'TL', 'tl', 'GD', 'sg', 'MA','ma', 'WB','wb', "
	          +" 'QB','qb','MO','mo','LB','lb','BL','bl','BB','bb','HL','hl','RE','re','MG','mg', lower(st.fk_annstm_stmtyp)) ||" 
			  +" '/' || w.doc_name as Path,WEBID, decode((select 1 from dual where a.fk_webid > 0), 1, 1, 0) as WebStm,"
			  +" st.implementation_dat as impl_date"
			  +" from sunstm st,annstm a,webstm w "
			+" where a.academic_year = "+academicYear+" and st.mk_study_unit_code= '"+course+"' and"
			+"   st.fk_annstmname = a.name and st.fk_annstmacadyear = a.academic_year and " 
			+"   st.fk_annstmacadprd = a.academic_period and st.fk_annstmnr = a.nr and st.fk_annstm_lang = a.din_language and" 
			+"   st.fk_annstm_stmtyp = a.fk_stmtypcode and ( a.fk_stmtypcode = 'BB' or a.fk_stmtypcode = 'BL' "
			+"	 or a.fk_stmtypcode = 'GD' or a.fk_stmtypcode = 'HL' or a.fk_stmtypcode = 'LB' or a.fk_stmtypcode = 'MA' "
			+"	 or a.fk_stmtypcode = 'MO' or a.fk_stmtypcode = 'MG' or a.fk_stmtypcode = 'QB' or a.fk_stmtypcode = 'RE' "
			+"	 or a.fk_stmtypcode = 'TL' or a.fk_stmtypcode = 'WB') and w.master_copy = 'y' and "  
			+"   st.implementation_dat <= SYSDATE and w.webid (+)= a.fk_webid  and (w.expiration_date >= SYSDATE or w.expiration_date is NULL)";
			if (semister.equals("0")) {
				sql += "and " + "(a.academic_period = 0 or "
						+ "a.academic_period = 4 or " + "a.academic_period = 7) ";
			} else if(semister.equals("1")) {
				sql += "and " + "(a.academic_period = 1 or "
						+ "a.academic_period = 3 or " + "a.academic_period = 4) ";
			} else if(semister.equals("2")){
				sql += "and " + "(a.academic_period = 2 or "
						+ "a.academic_period = 3 or " + "a.academic_period = 4) ";
			} else if(semister.equals("6")){
				sql += "and " + "(a.academic_period = 4 or "
						+ "a.academic_period = 6 or " + "a.academic_period = 7) ";
			}
			sql += "  order by Item";
		  		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  List queryList;
		  queryList = jdt.queryForList(sql);
		  Iterator i = queryList .iterator();
		  try{
		  while (i.hasNext()) {			
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  StudyMaterialDetails studyMaterialDetails=new StudyMaterialDetails();
			  studyMaterialDetails.setDiscription(data.get("Item").toString());
			  studyMaterialDetails.setWebId(data.get("WEBID").toString());	
			  String webId=data.get("WEBID").toString();
			  String tmpPath=data.get("Path").toString();
			 // String path=urlPrefix+getDocumentPath(webId,tmpPath);
			  
				//String secret = ""; // Same as AuthTokenSecret
				String protectedPath = "/studymaterial/material/"; // Same as AuthTokenPrefix
				// boolean ipLimitation=false; // Same as AuthTokenLimitByIp
				long time = (new Date()).getTime(); // Time in decimal
				time = (time / 1000); // + (60 * 30); // timestamp of java is longer than PHP
				String hexTime = Long.toHexString(time); // hexTime in Hexadecimal
				//String filePathName = "/Adobe.exe";
				String token = getMD5((secret +"/"+ getDocumentPath(webId,tmpPath)  + hexTime).getBytes());
				String path =  urlPrefix+protectedPath+token + "/" + hexTime +"/"+ getDocumentPath(webId,tmpPath);
				//String path = protectedPath+token + "/" + hexTime +"/"+ getDocumentPath(webId,tmpPath);
				
			  
			  
			  studyMaterialDetails.setPath(path);	
			  int filelenght=0;
			  String filesize="";
			    /*try {
					 URL url = new URL(path);
				     URLConnection conn = url.openConnection();
				     filelenght = conn.getContentLength();
				      if(filelenght < 0){
				    	  filesize="File size not available";
				      }else
				    	  filesize = new Integer(filelenght/1024).toString()+" KB";				    	
				      conn.getInputStream().close();
				      }catch(Exception e) {
				      e.printStackTrace();
				      }*/
			  studyMaterialDetails.setFilesize(filesize);
			  studyMaterialDetails.setImplementationDate(data.get("impl_date").toString().substring(0, 10));			  
			  results.add(studyMaterialDetails);
			}
		  }
	    catch(NullPointerException e ){e.printStackTrace();
	    }		
     return results;
 }
	public String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
	public String getDocumentPath(String webid,String path)
	{		
		JdbcTemplate jdt = new JdbcTemplate(getDataSource());
		  Hashtable ht = new Hashtable();
		  int count = 0, countrec = 0;
		  List queryList1;
		  List queryList2;
		  List queryList3;
		  String docpath = "";	
	  
		  try {  
		  		
		  String query1 = "select start_academic_yea, doc_name "+
						"from webstm "+
						"where webid = "+webid;
		  queryList1 = jdt.queryForList(query1);
		  Iterator i = queryList1.iterator();
		  i = queryList1.iterator();
		  while(i.hasNext()){
			  ListOrderedMap data1 = (ListOrderedMap) i.next();
			  ht.put("syear",data1.get("start_academic_yea"));
			  ht.put("docname",data1.get("doc_name"));
		  }		  
		  String query2 = "select * from annstm "+
						"where fk_webid = "+webid+
						" and academic_year = "+ht.get("syear");
		  queryList2 = jdt.queryForList(query2);
		  Iterator j = queryList2.iterator();
		  j = queryList2.iterator();
		  while(j.hasNext()){
			  ListOrderedMap data2 = (ListOrderedMap) j.next();
			  ht.put("acadprd", data2.get("academic_period"));
			  ht.put("name", data2.get("name"));
			  ht.put("lang", data2.get("din_language"));
			  ht.put("type", data2.get("fk_stmtypcode").toString().toLowerCase());
			  ht.put("nr", data2.get("nr"));			
		  }	
		  
		  String query3 = "select mk_study_unit_code "+
						"from sunstm "+
						"where fk_annstmacadyear = "+ht.get("syear")+
						" and fk_annstmacadprd = "+ht.get("acadprd")+
						" and fk_annstmname ='"+ht.get("name")+"'"+
						" and fk_annstm_stmtyp ='"+ht.get("type").toString().toUpperCase()+"'"+
						" and fk_annstmnr = "+ht.get("nr")+
						" and fk_annstm_lang ='"+ht.get("lang")+"'";
		  //System.out.println(query3);
		  queryList3 = jdt.queryForList(query3);
		  Iterator k = queryList3.iterator();
		  k = queryList3.iterator();
		  count = queryList3.size();
		  		  		
		if (count > 1) {
			docpath = "collect/"+path;
			// return true;
		} else {
			docpath = path;
			// return false;
		}
				
	   } catch (Exception ex) {
           
		   System.out.println(ex.getMessage());
		}		
		return docpath;		
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
			if (data.get(field) == null) {
			} else {
				result = data.get(field).toString();
			}
		}
		return result;
	}
}
