/**
 * DataCleanup_CalendarWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws;

public class DataCleanup_CalendarWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebServiceService {

    public DataCleanup_CalendarWebServiceServiceLocator() {
    }


    public DataCleanup_CalendarWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataCleanup_CalendarWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataCleanup_CalendarWebService
    private java.lang.String DataCleanup_CalendarWebService_address = "https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_CalendarWebService.jws";

    public java.lang.String getDataCleanup_CalendarWebServiceAddress() {
        return DataCleanup_CalendarWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataCleanup_CalendarWebServiceWSDDServiceName = "DataCleanup_CalendarWebService";

    public java.lang.String getDataCleanup_CalendarWebServiceWSDDServiceName() {
        return DataCleanup_CalendarWebServiceWSDDServiceName;
    }

    public void setDataCleanup_CalendarWebServiceWSDDServiceName(java.lang.String name) {
        DataCleanup_CalendarWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebService_PortType getDataCleanup_CalendarWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataCleanup_CalendarWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataCleanup_CalendarWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebService_PortType getDataCleanup_CalendarWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataCleanup_CalendarWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataCleanup_CalendarWebServiceEndpointAddress(java.lang.String address) {
        DataCleanup_CalendarWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_CalendarWebService_jws.DataCleanup_CalendarWebServiceSoapBindingStub(new java.net.URL(DataCleanup_CalendarWebService_address), this);
                _stub.setPortName(getDataCleanup_CalendarWebServiceWSDDServiceName());
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
        if ("DataCleanup_CalendarWebService".equals(inputPortName)) {
            return getDataCleanup_CalendarWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_CalendarWebService.jws", "DataCleanup_CalendarWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_CalendarWebService.jws", "DataCleanup_CalendarWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataCleanup_CalendarWebService".equals(portName)) {
            setDataCleanup_CalendarWebServiceEndpointAddress(address);
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
