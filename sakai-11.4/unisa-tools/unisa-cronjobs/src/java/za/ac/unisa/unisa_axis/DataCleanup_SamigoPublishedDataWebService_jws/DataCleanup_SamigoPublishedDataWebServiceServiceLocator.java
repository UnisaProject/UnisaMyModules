/**
 * DataCleanup_SamigoPublishedDataWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws;

public class DataCleanup_SamigoPublishedDataWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceService {

    public DataCleanup_SamigoPublishedDataWebServiceServiceLocator() {
    }


    public DataCleanup_SamigoPublishedDataWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataCleanup_SamigoPublishedDataWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataCleanup_SamigoPublishedDataWebService
    private java.lang.String DataCleanup_SamigoPublishedDataWebService_address = "https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_SamigoPublishedDataWebService.jws";

    public java.lang.String getDataCleanup_SamigoPublishedDataWebServiceAddress() {
        return DataCleanup_SamigoPublishedDataWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataCleanup_SamigoPublishedDataWebServiceWSDDServiceName = "DataCleanup_SamigoPublishedDataWebService";

    public java.lang.String getDataCleanup_SamigoPublishedDataWebServiceWSDDServiceName() {
        return DataCleanup_SamigoPublishedDataWebServiceWSDDServiceName;
    }

    public void setDataCleanup_SamigoPublishedDataWebServiceWSDDServiceName(java.lang.String name) {
        DataCleanup_SamigoPublishedDataWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebService_PortType getDataCleanup_SamigoPublishedDataWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataCleanup_SamigoPublishedDataWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataCleanup_SamigoPublishedDataWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebService_PortType getDataCleanup_SamigoPublishedDataWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataCleanup_SamigoPublishedDataWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataCleanup_SamigoPublishedDataWebServiceEndpointAddress(java.lang.String address) {
        DataCleanup_SamigoPublishedDataWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_SamigoPublishedDataWebService_jws.DataCleanup_SamigoPublishedDataWebServiceSoapBindingStub(new java.net.URL(DataCleanup_SamigoPublishedDataWebService_address), this);
                _stub.setPortName(getDataCleanup_SamigoPublishedDataWebServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DataCleanup_SamigoPublishedDataWebService".equals(inputPortName)) {
            return getDataCleanup_SamigoPublishedDataWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_SamigoPublishedDataWebService.jws", "DataCleanup_SamigoPublishedDataWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_SamigoPublishedDataWebService.jws", "DataCleanup_SamigoPublishedDataWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataCleanup_SamigoPublishedDataWebService".equals(portName)) {
            setDataCleanup_SamigoPublishedDataWebServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
