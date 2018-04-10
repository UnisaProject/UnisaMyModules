/**
 * DataCleanup_MeleteProcWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws;

public class DataCleanup_MeleteProcWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebServiceService {

    public DataCleanup_MeleteProcWebServiceServiceLocator() {
    }


    public DataCleanup_MeleteProcWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataCleanup_MeleteProcWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataCleanup_MeleteProcWebService
    private java.lang.String DataCleanup_MeleteProcWebService_address = "https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_MeleteProcWebService.jws";

    public java.lang.String getDataCleanup_MeleteProcWebServiceAddress() {
        return DataCleanup_MeleteProcWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataCleanup_MeleteProcWebServiceWSDDServiceName = "DataCleanup_MeleteProcWebService";

    public java.lang.String getDataCleanup_MeleteProcWebServiceWSDDServiceName() {
        return DataCleanup_MeleteProcWebServiceWSDDServiceName;
    }

    public void setDataCleanup_MeleteProcWebServiceWSDDServiceName(java.lang.String name) {
        DataCleanup_MeleteProcWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebService_PortType getDataCleanup_MeleteProcWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataCleanup_MeleteProcWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataCleanup_MeleteProcWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebService_PortType getDataCleanup_MeleteProcWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDataCleanup_MeleteProcWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataCleanup_MeleteProcWebServiceEndpointAddress(java.lang.String address) {
        DataCleanup_MeleteProcWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_MeleteProcWebService_jws.DataCleanup_MeleteProcWebServiceSoapBindingStub(new java.net.URL(DataCleanup_MeleteProcWebService_address), this);
                _stub.setPortName(getDataCleanup_MeleteProcWebServiceWSDDServiceName());
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
        if ("DataCleanup_MeleteProcWebService".equals(inputPortName)) {
            return getDataCleanup_MeleteProcWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_MeleteProcWebService.jws", "DataCleanup_MeleteProcWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_MeleteProcWebService.jws", "DataCleanup_MeleteProcWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataCleanup_MeleteProcWebService".equals(portName)) {
            setDataCleanup_MeleteProcWebServiceEndpointAddress(address);
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
