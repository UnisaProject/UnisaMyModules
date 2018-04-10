/**
 * DataCleanup_ContentProcWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws;

public class DataCleanup_ContentProcWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebServiceService {

    public DataCleanup_ContentProcWebServiceServiceLocator() {
    }


    public DataCleanup_ContentProcWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataCleanup_ContentProcWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataCleanup_ContentProcWebService
    private java.lang.String DataCleanup_ContentProcWebService_address = "https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_ContentProcWebService.jws";

    public java.lang.String getDataCleanup_ContentProcWebServiceAddress() {
        return DataCleanup_ContentProcWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataCleanup_ContentProcWebServiceWSDDServiceName = "DataCleanup_ContentProcWebService";

    public java.lang.String getDataCleanup_ContentProcWebServiceWSDDServiceName() {
        return DataCleanup_ContentProcWebServiceWSDDServiceName;
    }

    public void setDataCleanup_ContentProcWebServiceWSDDServiceName(java.lang.String name) {
        DataCleanup_ContentProcWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebService_PortType getDataCleanup_ContentProcWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataCleanup_ContentProcWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataCleanup_ContentProcWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebService_PortType getDataCleanup_ContentProcWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataCleanup_ContentProcWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataCleanup_ContentProcWebServiceEndpointAddress(java.lang.String address) {
        DataCleanup_ContentProcWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_ContentProcWebService_jws.DataCleanup_ContentProcWebServiceSoapBindingStub(new java.net.URL(DataCleanup_ContentProcWebService_address), this);
                _stub.setPortName(getDataCleanup_ContentProcWebServiceWSDDServiceName());
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
        if ("DataCleanup_ContentProcWebService".equals(inputPortName)) {
            return getDataCleanup_ContentProcWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_ContentProcWebService.jws", "DataCleanup_ContentProcWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_ContentProcWebService.jws", "DataCleanup_ContentProcWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataCleanup_ContentProcWebService".equals(portName)) {
            setDataCleanup_ContentProcWebServiceEndpointAddress(address);
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
