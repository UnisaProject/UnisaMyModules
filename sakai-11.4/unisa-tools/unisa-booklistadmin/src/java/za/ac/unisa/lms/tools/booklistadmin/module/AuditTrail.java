package za.ac.unisa.lms.tools.booklistadmin.module;

import za.ac.unisa.lms.tools.booklistadmin.dao.AuditTrailDAO;
import za.ac.unisa.lms.tools.booklistadmin.module.moduleImpl.AuditTrailImp;
public class AuditTrail  extends AuditTrailImp{
	
	
	
	AuditTrailImp auditTrailImp;
	public  AuditTrail(){
		     super(new AuditTrailDAO());
	}
	

}
