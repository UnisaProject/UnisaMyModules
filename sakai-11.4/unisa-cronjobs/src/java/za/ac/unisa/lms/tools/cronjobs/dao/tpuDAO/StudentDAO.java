package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Student;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Contact;

public class StudentDAO {
	 
              
	       databaseUtils dbutil;
	       public void getStudent(Student student,int studentNr) throws Exception {
		   		   
                          String sql = "select mk_title,surname,initials" + 
                                    " from stu" +
                                    " where nr=" + studentNr;
   		                  String errorMsg="Error reading Student / ";
   		                  dbutil=new databaseUtils();
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
              public Student getStudent(int studentNr) throws Exception {
          		
                  Student student = new Student();

                  String sql = "select mk_title,surname,initials" + 
                               " from stu" +
                               " where nr=" + studentNr;
               		           String errorMsg="Error reading Student / ";
               		           dbutil=new databaseUtils();
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

}
