package za.ac.unisa.lms.dao;

	import java.sql.ResultSet;
	import java.sql.SQLException;

	import org.springframework.jdbc.core.RowMapper;

	public class JoinActivationRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs,int arg1) throws SQLException {
			JoinActivationStuDetails studentDetails = new JoinActivationStuDetails();
			
			String studentNr="";
			String status_flag="";
			String mylife_act_status="";
			String act_status="";

			studentNr = rs.getString("USER_ID");
			if (studentNr.equals("") || studentNr == null || studentNr.equals(" ")) {
				studentNr = "";
			}
			studentDetails.setStudentNr(studentNr);
			
			status_flag = rs.getString("STATUS_FLAG");
			if (status_flag.equals("") || status_flag == null || status_flag.equals(" ")) {
				status_flag = "";
			}
			studentDetails.setStatus_flag(status_flag);	
			
			mylife_act_status = rs.getString("MYLIFE_ACT_STATUS");
			if (mylife_act_status.equals("") || mylife_act_status == null || mylife_act_status.equals(" ")) {
				mylife_act_status = "";
			}
			studentDetails.setMylife_act_status(mylife_act_status);
			
			act_status = rs.getString("ACT_STATUS");
			if (act_status.equals("") || act_status == null || act_status.equals(" ")) {
				act_status = "";
			}
			studentDetails.setAct_status(act_status);			
			return studentDetails;
		}			
}
