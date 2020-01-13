package za.ac.unisa.lms.tools.mylifesupport.DAO;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;
import za.ac.unisa.lms.tools.mylifesupport.forms.MyLifeSupportForm;

public class MyLifeSupportSakaiDAO extends SakaiDAO{
	

	public void getMyLifeJoinDetails(MyLifeSupportForm form) throws Exception{
		MyLifeSupportForm mylifesupportform = (MyLifeSupportForm) form;
		
		String sql ="select nvl(JOIN_DATE,'01/Dec/99') JOIN_DATE,STATUS_FLAG,MYLIFE_ACT_STATUS from JOIN_ACTIVATION where USER_ID ="+mylifesupportform.getStudentNr();
		try{
				JdbcTemplate jdt = new JdbcTemplate(getDataSource());
				List queryList = jdt.queryForList(sql);

				Iterator i = queryList.iterator();
				while (i.hasNext()) {
					ListOrderedMap data = (ListOrderedMap) i.next();
					
					
					String tmpDate=data.get("JOIN_DATE").toString().substring(0,4);
					if(tmpDate.equals("1999")){
						tmpDate="";
						mylifesupportform.setJoinDate(tmpDate);
					}else{
						mylifesupportform.setJoinDate(data.get("JOIN_DATE").toString().substring(0, 11));	
					}
					
					String tempStatusFlag = data.get("STATUS_FLAG").toString();					
					if(tempStatusFlag.equals("OT")){
						mylifesupportform.setStatusFlag("Joined ("+tempStatusFlag+")");
					}else if(tempStatusFlag.equals("NC")){
						mylifesupportform.setStatusFlag("Not Completed ("+tempStatusFlag+")");
					}else{
						mylifesupportform.setStatusFlag("Unknown");
					}
					String mylifeStatus = data.get("MYLIFE_ACT_STATUS").toString();
					if(mylifeStatus.equals("Y")){
						mylifesupportform.setMylifeStatus("Already Claimed ");
					}else if(mylifeStatus.equals("N")){
						mylifesupportform.setMylifeStatus("Not Claimed ");
					}else{
						mylifesupportform.setMylifeStatus(" ");
					}
					
				}
				}catch (Exception ex) {
		            throw new Exception("MyLifeSupportQueryDAO: getStudentAddr (Select): Error occurred / "+ ex,ex);
				}
		}
		}
	
