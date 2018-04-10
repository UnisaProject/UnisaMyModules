package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Contact;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Qualification;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.Student;

public class StudentDAO {
	 
              
	       
	       public StudentDAO(){
                    
           } 
	       public Student getStudent(int studentNr) throws Exception {
		
		                     Student student = new Student();
		
		                     String sql = "select mk_title,surname,initials" + 
		                                  " from stu" +
		                                  " where nr=" + studentNr;
		                  		      String errorMsg="Error reading Student / ";
		                  		      databaseUtils dbutil=new databaseUtils();
			                          List queryList = dbutil.queryForList(sql,errorMsg);
                            		  Iterator i = queryList.iterator();
			                          while (i.hasNext()) {
				                             ListOrderedMap data = (ListOrderedMap) i.next();
				                             String title ="";
				                             String surname="";
				                             String initials="";
				                             title= dbutil.replaceNull(data.get("mk_title"));
				                             surname= dbutil.replaceNull(data.get("surname"));
				                             initials= dbutil.replaceNull(data.get("initials"));
				                             student.setNumber(studentNr);
				                             student.setName(surname + ' ' + initials + ' ' + title);
				                             student.setPrintName(title + ' ' + initials + ' ' + surname);
			                          }
		                    return student;
 	     }
	       public void getStudent(Student student,int studentNr) throws Exception {
	   		
                              String sql = "select mk_title,surname,initials" + 
                                           " from stu" +
                                           " where nr=" + studentNr;
            		           String errorMsg="Error reading Student / ";
            		           databaseUtils dbutil=new databaseUtils();
                               List queryList = dbutil.queryForList(sql,errorMsg);
              		           Iterator i = queryList.iterator();
                               while (i.hasNext()) {
	                                   ListOrderedMap data = (ListOrderedMap) i.next();
	                                   String title ="";
	                                   String surname="";
	                                   String initials="";
	                                   title= dbutil.replaceNull(data.get("mk_title"));
	                                   surname= dbutil.replaceNull(data.get("surname"));
	                                   initials= dbutil.replaceNull(data.get("initials"));
	                                   student.setNumber(studentNr);
	                                   student.setName(surname + ' ' + initials + ' ' + title);
	                                   student.setPrintName(title + ' ' + initials + ' ' + surname);
                               }
	       }
	       public String getCountryCode(int studentNr) throws Exception {
		   		
               String sql = "select mk_country_code " + 
                            " from stu" +
                            " where nr=" + studentNr;
		           String errorMsg="Error reading Student / ";
		           databaseUtils dbutil=new databaseUtils();
                List queryList = dbutil.queryForList(sql,errorMsg);
		           Iterator i = queryList.iterator();
		           String countryCode="0";
                while (i.hasNext()) {
                        ListOrderedMap data = (ListOrderedMap) i.next();
                        countryCode= dbutil.replaceNull(data.get("mk_country_code "));
                }
                return countryCode;
         }
	     public Qualification getStudentQual(int studentNr, Short acadYear) throws Exception {
		
		                         Qualification qual = new Qualification();
				                 String sql = "select a.mk_highest_qualifi as qualCode, b.eng_description as qualDesc, b.short_description as qaulShortDesc" + 
		                                      " from stuann a,grd b" +
		                                      " where a.mk_student_nr=" + studentNr +
		                                      " and a.mk_academic_year=" + acadYear +
		                                      " and a.mk_academic_period=1" +
		                                      " and a.mk_highest_qualifi=b.code";
      	                                      databaseUtils dbutil=new databaseUtils();
      	                                      String errorMsg="Error reading stuan, grd tables, in StudentDAO";
			                                  List queryList = dbutil.queryForList(sql,errorMsg);
			                                  Iterator i = queryList.iterator();
			                                   while (i.hasNext()) {
				                                       ListOrderedMap data = (ListOrderedMap) i.next();
				                                       qual.setCode(dbutil.replaceNull(data.get("qualCode")));
				                                       qual.setDescription(dbutil.replaceNull(data.get("qualDesc")));
				                                       qual.setShortDesc(dbutil.replaceNull(data.get("qaulShortDesc")));
			                                    }
		                               return qual;
		
	      }
	public List getPracticalModuleList() throws Exception {
		
		                List moduleList = new ArrayList(); 
		                String sql = "select code as module" + 
		                           " from sun" +
		                           " where practical_flag='Y'" +
		                           " and college_code=9" +
		                           " and school_code=3"+
		                           " and formal_tuition_fla='F'"+
		                           " order by code";
		
      		            databaseUtils dbutil=new databaseUtils();
                        String errorMsg="Error reading sun  in StudentDAO";
			            List queryList = dbutil.queryForList(sql,errorMsg);
			            Iterator i = queryList.iterator();
			            while (i.hasNext()) {
				               ListOrderedMap data = (ListOrderedMap) i.next();
				               String module ="";				
				               module= dbutil.replaceNull(data.get("module"));
				               moduleList.add(module);
			           }
		return moduleList;
		
	}
	public List getStudentModuleList(int studentNr, Short acadYear, Short semester) throws Exception {
		
		                  List moduleList = new ArrayList(); 
		                  String sql = " select a.mk_study_unit_code as module "+ 
		                               " from stusun a, sun b "+
		                               " where a.fk_student_nr=" + studentNr +
	                                   " and a.fk_academic_year=" + acadYear +
		                               " and a.fk_academic_period=1"+
		                               " and a.semester_period=" + semester +
		                               " and a.status_code in ('RG','FC')"+
		                               " and a. mk_study_unit_code=b.code"+
		                               " and b.practical_flag='Y'"+
		                               " and b.college_code=9"+
		                               " and b.school_code=3"+
		                               " and b.formal_tuition_fla='F'"+
		                               " order by b.code";

      		              databaseUtils dbutil=new databaseUtils();
                          String errorMsg="Error reading stuan ,sun  in StudentDAO";
			              List queryList = dbutil.queryForList(sql,errorMsg);
                 		  Iterator i = queryList.iterator();
			              while (i.hasNext()) {
				                   ListOrderedMap data = (ListOrderedMap) i.next();
				                   String module ="";				
				                   module= dbutil.replaceNull(data.get("module"));
				                   moduleList.add(module);
			             }
		          return moduleList;
		
	}
	public String getStudentCell(String studentNr) throws Exception{

		                   String cell = "";
		                   String query = "select CELL_NUMBER "+
	   	  				                  " from adrph " +
	   	  				                  " where reference_no = " + studentNr +
	   	  				                  " and fk_adrcatcode=1";
		                   String errorMsg="StudentDAO of StudentPlacement: Error reading  adrph" ;
		                   databaseUtils dbutil=new databaseUtils();
                           cell = dbutil.querySingleValue(query,"CELL_NUMBER",errorMsg);
		                return cell;
	}
	public String getStudentEmail(String studentNr) throws Exception{

		                String email = "";
		                String query = "select email_address "+
	   	  				               " from adrph " +
	   	  				               " where reference_no = " + studentNr +
	   	  				               " and fk_adrcatcode=1";
		                 databaseUtils dbutil=new databaseUtils();
		                 String errorMsg="StudentDAO of StudentPlacement: Error reading  adrph" ;
		                 email = dbutil.querySingleValue(query,"email_address",errorMsg);		
		              return email;
	}
	public String getStudentContactNumber(int studentNumber) throws Exception{
		              ContactDAO  contactDAO=new ContactDAO();
		              Contact  contact  = contactDAO.getContactDetails(studentNumber,1);
                      String contactNum="";
                      if (contact.getCellNumber()!=null && !contact.getCellNumber().trim().equalsIgnoreCase("")){
                             contactNum=contact.getCellNumber();
                      }else if (contact.getWorkNumber()!=null && !contact.getWorkNumber().trim().equalsIgnoreCase("")){
                                  contactNum=contact.getWorkNumber();
                      }
                      if (contactNum==null){
                              contactNum="";
                      }
                   return contactNum;
    }

}
