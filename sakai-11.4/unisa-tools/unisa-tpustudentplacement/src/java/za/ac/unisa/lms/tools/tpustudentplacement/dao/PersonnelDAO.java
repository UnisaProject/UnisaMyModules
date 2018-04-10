package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import za.ac.unisa.lms.tools.tpustudentplacement.utils.DateUtil;
public class PersonnelDAO {
	           databaseUtils dbutil;
               public PersonnelDAO(){
                         dbutil=new databaseUtils();
               }
     	       public String getNetworkCode(String personnelNumber)throws Exception{
                                String sql="Select novell_user_id  networkcode" +
                                 " from STAFF  where persno = "+personnelNumber.trim()+"  and (resign_date is null or resign_date>sysdate)";
                                 String errorMsg="PersonnelDAO:Error reading STAFF ";
                                return   dbutil.querySingleValue(sql,"networkcode",errorMsg);
              }
	           public String getPersonnelNumber(String username)throws Exception{
  	                              String query="select   persno, to_char(resign_date,'YYYY-MM-DD') as resignDate from  staff where novell_user_id='"+username+"'";
  	                              String errorMsg="CoordinatorDAO:Error reading staff";
                                  List queryList=dbutil.queryForList(query,errorMsg);
                                  String personnelNum="";
                                  for (int i=0; i<queryList.size();i++){
                                           ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                                           String date=dbutil.replaceNull(data.get("resignDate"));
                                           DateUtil  dateutil=new DateUtil();
                                           personnelNum="";
                                           if(date.equals("")||dateutil.isDateGreaterThanSysDate(date)){
                        	                       personnelNum=dbutil.replaceNull(data.get("persno"));
                                                   break;
                                           }
                                 }
                                 return personnelNum;
            }
	           public String getPersonnelNumber(int  persno)throws Exception{
                     String query="select   persno, to_char(resign_date,'YYYY-MM-DD') as resignDate from  staff where persno='"+persno+"'";
                     String errorMsg="CoordinatorDAO:Error reading staff";
                   List queryList=dbutil.queryForList(query,errorMsg);
                   String personnelNum="";
                   for (int i=0; i<queryList.size();i++){
                            ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                            String date=dbutil.replaceNull(data.get("resignDate"));
                            DateUtil  dateutil=new DateUtil();
                            personnelNum="";
                            if(date.equals("")||dateutil.isDateGreaterThanSysDate(date)){
         	                       personnelNum=dbutil.replaceNull(data.get("persno"));
                                    break;
                            }
                  }
                  return personnelNum;
}
}
