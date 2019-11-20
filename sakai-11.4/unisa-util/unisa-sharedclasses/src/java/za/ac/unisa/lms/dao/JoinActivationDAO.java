package za.ac.unisa.lms.dao;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.jdbc.core.JdbcTemplate;

import za.ac.unisa.lms.db.SakaiDAO;

/** Class for all join and activation related methods. */
/**
 * @author udcsweb
 *
 */
public class JoinActivationDAO extends SakaiDAO{

	/**
	 * @param stuno
	 * @return
	 * @throws Exception
	 */
	public JoinActivationStuDetails getStudentDetails(String stuno) throws Exception {
		JoinActivationRowMapper stuDetails = null;
		JoinActivationStuDetails studentDetails = new JoinActivationStuDetails();
		try {
			JdbcTemplate jdt = new JdbcTemplate(getDataSource());
			String sql = "SELECT USER_ID,STATUS_FLAG,MYLIFE_ACT_STATUS,ACT_STATUS FROM   JOIN_ACTIVATION WHERE  USER_ID =?";

			List studentDetailList = jdt.query(sql, new Object[] { stuno },
					new JoinActivationRowMapper());
			if (studentDetailList == null || studentDetailList.size() == 0) {
				studentDetails.setStudentNr("");
				studentDetails.setStatus_flag("");
				studentDetails.setAct_status("");
				studentDetails.setMylife_act_status("");
			} else {
				Iterator i = studentDetailList.iterator();
				if (i.hasNext()) {
					studentDetails = (JoinActivationStuDetails) i.next();
				}
			}
		} catch (Exception ex) {
			throw new Exception("JoinActivationDAO: getStudentDetails: (stno: " + stuno + ") Error occurred / " + ex, ex);
		}
		return studentDetails;
	}
	
}
