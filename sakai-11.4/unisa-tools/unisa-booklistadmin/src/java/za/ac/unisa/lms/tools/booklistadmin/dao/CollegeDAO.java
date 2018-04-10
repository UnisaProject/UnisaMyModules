package za.ac.unisa.lms.tools.booklistadmin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.util.LabelValueBean;
public class CollegeDAO {
	public ArrayList getCollegeList() throws Exception {
		ArrayList collegeList = new ArrayList();
		String sql=" select code,eng_description college from colleg where " +
				   " dean > 0 order by eng_description ";	
		
		try{
			  DatabaseUtil databaseUtil=new  DatabaseUtil();
			  List queryList = databaseUtil.queryForList(sql);			  
		  String value="";
		  String label="";  
		  Iterator i = queryList.iterator();			
		  while (i.hasNext()){
			  ListOrderedMap data = (ListOrderedMap) i.next();
			  
			label = data.get("college").toString();	
		 	value = data.get("code").toString();
		 	value=value+"-"+label;		
		 collegeList.add(new LabelValueBean(label,value));						
		}
		
	} catch (Exception ex) {
      throw new Exception("AdminDAO : getCollegeList Error occurred / "+ ex,ex);
	}
	return collegeList;
	}
	public String collegeName(String subject) throws Exception {
		DatabaseUtil databaseUtil=new  DatabaseUtil();
			String college;
		String sql = "select ENG_DESCRIPTION from colleg where code=(select COLLEGE_CODE from sun where code='"+subject+"')";
		String name = databaseUtil.querySingleValue(sql,"ENG_DESCRIPTION");
		if (name.length()> 0) {
			college=name;
		}else{
			college="";
		}				
		return college;
	}

}
