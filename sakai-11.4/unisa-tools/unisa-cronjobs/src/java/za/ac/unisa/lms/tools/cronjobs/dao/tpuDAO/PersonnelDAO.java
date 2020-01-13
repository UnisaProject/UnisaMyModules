package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;

import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;

import za.ac.unisa.lms.tools.cronjobs.forms.model.Personnel;
import za.ac.unisa.lms.tools.cronjobs.forms.util.DateUtil;
public class PersonnelDAO {
	
	
	 databaseUtils dbutil;
     public PersonnelDAO(){
                 dbutil=new databaseUtils();
     }
    
	public String getNetworkCode(String personnelNumber)throws Exception{
        String sql="Select novell_user_id  networkcode" +
                   " from STAFF  where persno = "+personnelNumber.trim();
        String errorMsg="PersonnelDAO:Error reading STAFF ";
       return   dbutil.querySingleValue(sql,"networkcode",errorMsg);
    }
	public String getPersonnelNumber(String username)throws Exception{
        String query="select   persno, to_char(resign_date,'YYYY-MM-DD') as resignDate from  staff where novell_user_id='"+username.trim().toUpperCase()+"'";
        String errorMsg="PersonnelDAO:Error reading staff";
        List queryList=dbutil.queryForList(query,errorMsg);
        String personnelNum="";
        for (int i=0; i<queryList.size();i++){
                   Personnel   personnel = new Personnel();	
                ListOrderedMap data = (ListOrderedMap) queryList.get(i);
                personnel.setPersonnelNumber(dbutil.replaceNull(data.get("persno")));
                personnel.setContractExpiryDate(dbutil.replaceNull(data.get("resignDate")));
                DateUtil  dateutil=new DateUtil();
                String date=personnel.getContractExpiryDate().trim();
                if(date==null||date.equals("")||dateutil.isDateGreaterThanSysDate(date)){
                	  personnelNum=personnel.getPersonnelNumber();
                      break;
                }
        }
        return personnelNum;
     }


}
