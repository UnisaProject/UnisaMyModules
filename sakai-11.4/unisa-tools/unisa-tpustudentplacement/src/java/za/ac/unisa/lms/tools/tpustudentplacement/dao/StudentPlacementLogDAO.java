package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import za.ac.unisa.lms.tools.tpustudentplacement.model.StudentPlacementLog;
import za.ac.unisa.lms.tools.tpustudentplacement.forms.StudentPlacement;
import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
public class StudentPlacementLogDAO {
	 
	           databaseUtils dbutil;
	           public StudentPlacementLogDAO(){
                          dbutil=new databaseUtils();
               }
               public void addLogForCreateNewPlacementAction(StudentPlacement spl,int academicYear,int semester,
            		               String afterImage,int personnelNum) throws Exception{
            	                           String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
                                                      " MK_STUDY_UNIT_CODE,ACTION_GC201,AFTER_IMAGE," +
                                                      " UPDATED_BY,UPDATED_ON)values("+academicYear +","+semester+","+
                                                      spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','"+
                                                      "CREATE','"+afterImage+"',"+personnelNum+",SYSDATE)";
            	                                      String errorMsg="StudentPlacementLogDAO:Error inserting  a create log into  TPUSPLLOG";
    	                                   dbutil.update(sql,errorMsg);
               }
               public void addLogForDeletePlacementAction(StudentPlacement spl,int academicYear,int semester,
		                           String afterImage,int personnelNum)throws Exception{
            	                        String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
                                                   " MK_STUDY_UNIT_CODE,ACTION_GC201,AFTER_IMAGE," +
                                                   " UPDATED_BY,UPDATED_ON)values("+academicYear +","+semester+","+
                                                   spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','"+
                                                   "DELETE','"+afterImage+"',"+personnelNum+",SYSDATE)";
                                                   String errorMsg="StudentPlacementLogDAO:Error inserting a delete log into  TPUSPLLOG";
                                                   dbutil.update(sql,errorMsg);
	               
              }
              public void addLogForUpdatePlacementAction(StudentPlacement spl,int academicYear,int semester,
                                String beforeImage,String afterImage,int personnelNum)throws Exception{
	                                      String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
                                                     " MK_STUDY_UNIT_CODE,ACTION_GC201,BEFORE_IMAGE,AFTER_IMAGE," +
                                                     " UPDATED_BY,UPDATED_ON)values("+academicYear +","+semester+","+
                                                     spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','"+
                                                     "UPDATE','"+beforeImage+"','"+afterImage+"',"+personnelNum+",SYSDATE)";
                                                      String errorMsg="StudentPlacementLogDAO:Error inserting an update log into  TPUSPLLOG";
                                      dbutil.update(sql,errorMsg);
      
              }
              public void setLogOnEmailToStu(StudentPlacement spl,int academicYear,int semester,
                                String emailAddress,String afterImage,int personnelNum)throws Exception{
  	                                String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
  	                                    " MK_STUDY_UNIT_CODE,ACTION_GC201,AFTER_IMAGE," +
  	                                    " EMAIL_ADDRESS,UPDATED_BY,UPDATED_ON,CORRESPONDANCE_TO)values("+academicYear +","+semester+","+
  	                                      spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','EMAIL','"+
  	                                      afterImage+"','"+emailAddress+"',"+
  	                                      personnelNum+",SYSDATE,'STUDENT')";
  	                               String errorMsg="StudentPlacementLogDAO:Error inserting email log for email to a student into   TPUSPLLOG";
  	                           dbutil.update(sql,errorMsg);
               }
               public void setLogOnSmsToStu(StudentPlacement spl,int academicYear,int semester,
                                      String cellNum,String afterImage,int personnelNum)  throws Exception{
                             String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
                                " MK_STUDY_UNIT_CODE,ACTION_GC201,AFTER_IMAGE," +
                                " CELL_NO,UPDATED_BY,UPDATED_ON,CORRESPONDANCE_TO)values("+academicYear +","+semester+","+
                                  spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','SMS','"+
                                  afterImage+"','"+cellNum+"',"+
                                  personnelNum+",SYSDATE,'STUDENT')";
                             String errorMsg="StudentPlacementLogDAO:Error inserting sms log for sms to a school  into   TPUSPLLOG";
                             dbutil.update(sql,errorMsg);
              }
              public void setLogOnEmailToSchool(StudentPlacement spl,int academicYear,int semester,
                                 String emailAddress,String afterImage,int personnelNum)  throws Exception{
                                      String sql="insert into TPUSPLLOG(MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,MK_SCHOOL_CODE,"+
                                                 " MK_STUDY_UNIT_CODE,ACTION_GC201,AFTER_IMAGE," +
                                                 " EMAIL_ADDRESS,UPDATED_BY,UPDATED_ON,CORRESPONDANCE_TO)values("+academicYear +","+semester+","+
                                                 spl.getStuNum()+","+spl.getSchoolCode()+",'"+spl.getModule()+"','EMAIL','"+
                                                 afterImage+"','"+emailAddress+"',"+
                                                 personnelNum+",SYSDATE,'SCHOOL')";
                              String errorMsg="StudentPlacementLogDAO:Error inserting mail log for email to a school into   TPUSPLLOG";
                              dbutil.update(sql,errorMsg);
               }
               public List getListOfAllLogs()  throws Exception{
            	                   String sql="Select MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,b.name as schName,MK_SCHOOL_CODE,"+
            	                          " MK_STUDY_UNIT_CODE,ACTION_GC201,CORRESPONDANCE_TO,BEFORE_IMAGE,AFTER_IMAGE," +
            	                          " CELL_NO,a.EMAIL_ADDRESS,UPDATED_BY,novell_user_id,TO_CHAR(UPDATED_ON,'YYYY-MM-DD HH24:MI:SS') as updatedOn"+
            	                          " from TPUSPLLOG a,tpusch b,staff c"+
	                                      " where a.MK_SCHOOL_CODE=b.code  and updated_by=persno ";
	                  	           List placementList=getList(sql);
         	                return    placementList;
                }
                public List getListOfSelectedLogs(StudentPlacementLog sPLog)  throws Exception{  
	                        String sql="Select MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,c.name as schName,MK_SCHOOL_CODE,"+
	                          " MK_STUDY_UNIT_CODE,ACTION_GC201,CORRESPONDANCE_TO,BEFORE_IMAGE,AFTER_IMAGE," +
	                          " CELL_NO,b.EMAIL_ADDRESS,UPDATED_BY,novell_user_id,TO_CHAR(UPDATED_ON,'YYYY-MM-DD HH24:MI:SS') as "+
	                          " updatedOn from staff a,TPUSPLLOG b,tpusch c"+
	                          " where a.persno=b.updated_by and b.MK_SCHOOL_CODE=c.code  and MK_ACADEMIC_YEAR="+sPLog.getYear()+" and SEMESTER_PERIOD="+sPLog.getSemester();
	                          
	                          if((sPLog.getUpdatedBy()!=null)&&(!sPLog.getUpdatedBy().equals(""))){
	                        	  sql+=" and persno=  "+sPLog.getPersonnelNumber();
	                          }
	                          if((sPLog.getStuNum()!=null)&&(!sPLog.getStuNum().equals(""))){
	                        	  sql+=" and MK_STUDENT_NR="+sPLog.getStuNum();
	                          }
	                          if((sPLog.getSchoolCode()!=null)&&(!(""+sPLog.getSchoolCode()).equals(""))){
	                        	  sql+=" and MK_SCHOOL_CODE="+ +sPLog.getSchoolCode();
	                          }
	                          if((sPLog.getModule()!=null)&&(!sPLog.getModule().equals(""))){
	                        	    sql+=" and  MK_STUDY_UNIT_CODE='"+sPLog.getModule().toUpperCase()+"'";
	                           	    
	                          }
	                          if((sPLog.getAction()!=null)&&(!sPLog.getAction().equals(""))){
	                        	  if(sPLog.getAction().equals("PlacementDetails")){
	                        		   sql+=" and ((ACTION_GC201='CREATE' ) or (ACTION_GC201='UPDATE') or (ACTION_GC201='DELETE'))";
	                        	  }
	                        	  if(sPLog.getAction().equals("Email")){
	                        		  sql+=" and ACTION_GC201='EMAIL' ";
	                        	  }
	                        	  if(sPLog.getAction().equals("EmailsNsmses")){
	                        		  sql+=" and ((ACTION_GC201='EMAIL' ) or (ACTION_GC201='SMS') or (ACTION_GC201='SMS_EMAIL') or (ACTION_GC201='SMS_LETTER'))";
	                        	  }
	                          }
	                          DateUtil   dateUtil=new  DateUtil();
	                          if((sPLog.getStartDate()!=null)&&(!sPLog.getStartDate().equals(""))){
	                        	  sql+=" and TO_CHAR(UPDATED_ON,'YYYY-MM-DD')>='"+dateUtil.changeDateFormatFromDateYYYYMMDD(sPLog.getStartDate(),"-")+"'";
	                          }
	                          if((sPLog.getEndDate()!=null)&&(!sPLog.getEndDate().equals(""))){
	                           	  sql+="and  TO_CHAR(UPDATED_ON,'YYYY-MM-DD')<='"+dateUtil.changeDateFormatFromDateYYYYMMDD(sPLog.getEndDate(),"-")+"'";
	                          }
	                          if((sPLog.getSortOrder()!=null)&&(!sPLog.getSortOrder().equals(""))){
	                        	  if(sPLog.getSortOrder().equals("first")){
	                        		  sql+=" order by   MK_STUDENT_NR,MK_STUDY_UNIT_CODE,UPDATED_ON";
	                        	  }else{
	                        		  sql+=" order by   UPDATED_ON,MK_STUDENT_NR,MK_STUDY_UNIT_CODE";
	                        	  }
	                          }
	                          List placementList=getList(sql);
	                  return    placementList;
               }
               public String getPlacementImage(String action,String updatedOn,String updatedBy)  throws Exception{
              	                String sql ="select after_image  TPUSPLLOG  where ACTION_GC201='"+action+"' and UPDATED_BY='"+updatedBy+"'"+
              	                            " and TO_CHAR(UPDATED_ON,'YYYY-MM-DD HH24:MI:SS')='"+updatedOn+"'";
              	              String errorMsg="StudentPlacementLogDAO:Error reading   TPUSPLLOG";
              	                String image=dbutil.querySingleValue(sql,"after_image",errorMsg);
              	                return image;
              }
               public String getPlacementImage(int  stuNum,int schoolCode,String module)  throws Exception{
            	                  String sql ="select AFTER_IMAGE  from TPUSPLLOG where MK_STUDENT_NR="+stuNum+" and MK_SCHOOL_CODE="+
	            	                          schoolCode+"  and MK_STUDY_UNIT_CODE='"+module+"' "+
	            	                          " and ((ACTION_GC201='CREATE' ) or (ACTION_GC201='UPDATE')) and  UPDATED_ON=( select max(UPDATED_ON) from TPUSPLLOG  where MK_STUDENT_NR="+stuNum+")";
            	                  String errorMsg="StudentPlacementLogDAO:Error reading   TPUSPLLOG";
            	                  String image=dbutil.querySingleValue(sql,"AFTER_IMAGE",errorMsg);
                	              return image;
	           }
               public List getList(String sql)  throws Exception{
      	                      List placementLogList  = new ArrayList();
      	                      String errorMsg="StudentPlacementLogDAO:Error reading   TPUSPLLOG";
                              List queryList = dbutil.queryForList(sql,errorMsg);
                              for (int i=0; i<queryList.size();i++){
                            	  StudentPlacementLog sPLog = new StudentPlacementLog();
                                  ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                  setStudentPlacementLogObject(data,sPLog);
                                  placementLogList.add(sPLog);			
                             }
                  return placementLogList;
               }    
               public StudentPlacementLog getStoredLog(StudentPlacementLog sPLog,String action,String  updatedby,String  updatedOn)throws Exception{
            	              String sql="Select MK_ACADEMIC_YEAR,SEMESTER_PERIOD,MK_STUDENT_NR,b.name as schName,MK_SCHOOL_CODE"+
                                         " MK_STUDY_UNIT_CODE,ACTION_GC201,CORRESPONDANCE_TO,BEFORE_IMAGE,AFTER_IMAGE," +
                                         " CELL_NO,a.EMAIL_ADDRESS,UPDATED_BY,TO_CHAR(UPDATED_ON,'YYYY-MM-DD HH24:MI:SS') as updatedOn from TPUSPLLOG a,tpusch b"+
                                         " where a.MK_SCHOOL_CODE=b.code   and ACTION_GC201='"+action+
                                         "' and UPDATED_BY="+updatedby+" and TO_CHAR(UPDATED_ON,'YYYY-MM-DD HH24:MI:SS')='"+updatedOn+"'";
            	              String errorMsg="StudentPlacementLogDAO:Error reading   TPUSPLLOG";
            	              List queryList = dbutil.queryForList(sql,errorMsg);
                              ListOrderedMap data = (ListOrderedMap)queryList.get(0);
                              setStudentPlacementLogObject(data,sPLog);
                              return sPLog;
               }
               private void setStudentPlacementLogObject(ListOrderedMap data,StudentPlacementLog sPLog){
            	   	
            	               sPLog.setAction(dbutil.replaceNull(data.get("ACTION_GC201")));
                               sPLog.setAfterImage(dbutil.replaceNull(data.get("AFTER_IMAGE")));
                               sPLog.setBeforeImage(dbutil.replaceNull(data.get("BEFORE_IMAGE")));
                               sPLog.setCellNum(dbutil.replaceNull(data.get("CELL_NO")));
                               sPLog.setCorrespondenceTo(dbutil.replaceNull(data.get("CORRESPONDANCE_TO")));
                               sPLog.setEmailAddress(dbutil.replaceNull(data.get("EMAIL_ADDRESS")));
                               sPLog.setModule(dbutil.replaceNull(data.get("MK_STUDY_UNIT_CODE")));
                               sPLog.setYear(dbutil.replaceNull(data.get("MK_ACADEMIC_YEAR")));
                               sPLog.setSemester(dbutil.replaceNull(data.get("SEMESTER_PERIOD")));
                               sPLog.setUpdatedBy(dbutil.replaceNull(data.get("novell_user_id")));
                               sPLog.setSchoolDesc(dbutil.replaceNull(data.get("schName")));
                               sPLog.setStuNum(dbutil.replaceNull(data.get("MK_STUDENT_NR")));
                               String scode=dbutil.replaceNull(data.get("MK_SCHOOL_CODE"));
                               if(!scode.equals("")){
                 	                sPLog.setSchoolCode(Integer.parseInt(scode));
                               }
                               sPLog.setUpdatedOn(data.get("updatedOn").toString());
                               if((sPLog.getAfterImage()!=null) && (sPLog.getAfterImage().equals(""))){
                            	   sPLog.setImageTracker("0");
                               }else{
                            	   sPLog.setImageTracker("1");
                               }
    
            }
}
