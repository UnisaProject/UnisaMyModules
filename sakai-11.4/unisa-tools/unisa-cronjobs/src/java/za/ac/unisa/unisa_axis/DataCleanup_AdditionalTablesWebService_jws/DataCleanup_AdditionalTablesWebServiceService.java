/**
 * DataCleanup_AdditionalTablesWebServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_AdditionalTablesWebService_jws;
import org.sakaiproject.component.cover.ServerConfigurationService;

public interface DataCleanup_AdditionalTablesWebServiceService extends javax.xml.rpc.Service {
    public java.lang.String getDataCleanup_AdditionalTablesWebServiceAddress();

    public za.ac.unisa.unisa_axis.DataCleanup_AdditionalTablesWebService_jws.DataCleanup_AdditionalTablesWebService_PortType getDataCleanup_AdditionalTablesWebService() throws javax.xml.rpc.ServiceException;

    public za.ac.unisa.unisa_axis.DataCleanup_AdditionalTablesWebService_jws.DataCleanup_AdditionalTablesWebService_PortType getDataCleanup_AdditionalTablesWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    
	public String serverUrl = ServerConfigurationService.getString("serverUrl");
	/*if (serverUrl.equals("http://localhost:8080")) {
		serverUrl = "https://mydev.unisa.ac.za";
	}*/
}
