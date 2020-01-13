package za.ac.unisa.lms.tools.cronjobs.dao.tpuDAO;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;
import za.ac.unisa.lms.tools.cronjobs.forms.model.Contact;
import za.ac.unisa.lms.db.StudentSystemDAO;
;
public class ContactDAO extends StudentSystemDAO {
	                   
	              databaseUtils dbutil;
                  public ContactDAO(){
                           dbutil=new databaseUtils();
                  }  
                  public Contact getContactDetails(Integer referenceNo,Integer category) throws Exception {
                		
              		Contact contact = new Contact();
              		
              		String sql = "select home_number,work_number,fax_number,email_address,cell_number" +
              		" from adrph" +
              		" where reference_no=" + referenceNo +
              		" and fk_adrcatcode=" + category;
              		
              	  	try{ 
              			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
              			List queryList = jdt.queryForList(sql);
              	
              			Iterator i = queryList.iterator();
              			while (i.hasNext()) {
              				ListOrderedMap data = (ListOrderedMap) i.next();
              				contact.setHomeNumber(dbutil.replaceNull(data.get("home_number")).trim());
              				contact.setWorkNumber(dbutil.replaceNull(data.get("work_number")).trim());
              				contact.setFaxNumber(dbutil.replaceNull(data.get("fax_number")).trim());
              				contact.setCellNumber(dbutil.replaceNull(data.get("cell_number")).trim());
              				contact.setEmailAddress(dbutil.replaceNull(data.get("email_address")).trim());
              			}
              		}
              		catch (Exception ex) {
              			throw new Exception("Error reading ADR / " + ex);
              		}	
              		return contact;	
              	}
                  
              	
}
