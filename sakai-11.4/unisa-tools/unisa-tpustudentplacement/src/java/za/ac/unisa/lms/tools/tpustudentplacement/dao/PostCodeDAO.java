package za.ac.unisa.lms.tools.tpustudentplacement.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.StudentSystemDAO;
public class PostCodeDAO extends StudentSystemDAO {
	


	public ArrayList getPostalCodeList(String searchType, String searchCriteria, String postalType) throws Exception {
	//Construct list object of postal codes and suburbs
	List postalCodeList = new ArrayList();
	ArrayList postalCodes = new ArrayList();
	String streetOrBox = "B";
	//Pstcod cod = new Pstcod();
	
	JdbcTemplate jdt = new JdbcTemplate(getDataSource());
	
//		if ("street".equalsIgnoreCase(postalType)|| "straat".equalsIgnoreCase(postalType)){
//			streetOrBox = "S";
//		} else{
//			streetOrBox = "B";
//		}

		if ("suburb".equalsIgnoreCase(searchType)){			
			postalCodeList = jdt.queryForList("select CODE,SUBURB,TOWN from PSTCOD where TYPE = ? and upper(SUBURB) like ?  order by SUBURB",						
					new Object[] { postalType, searchCriteria.toUpperCase()+"%" },
					new int[] { Types.VARCHAR, Types.VARCHAR });
		}
		if ("postal".equalsIgnoreCase(searchType)){
			postalCodeList = jdt.queryForList("select CODE, SUBURB, TOWN from PSTCOD where TYPE = ? and (substr('0000',1,4-length(CODE)) || to_char(CODE)) like ? order by CODE",
					new Object[] { postalType, searchCriteria.toUpperCase()+"%"},
					new int[] { Types.VARCHAR, Types.VARCHAR });
		}
		
		if (postalCodeList.size() > 0) {
			String code = "";
			String descr = "";
			String town = "";
			for ( int i=0; i<postalCodeList.size(); i++ ) { 
				ListOrderedMap test = (ListOrderedMap) postalCodeList.get(i);
				code =  (String) test.get("CODE").toString();
				descr = (String) test.get("SUBURB").toString();
				town = (String) test.get("TOWN").toString();
				if (code.length()==1){
					code = "000" + code;
				}else if (code.length()==2){
					code = "00" + code;
				}else if (code.length()==3){
					code = "0" + code;
				}
				postalCodes.add(new org.apache.struts.util.LabelValueBean(code + " - " + descr+", " + town, code + descr));
			} 
		}
	
	return postalCodes;
}

}
