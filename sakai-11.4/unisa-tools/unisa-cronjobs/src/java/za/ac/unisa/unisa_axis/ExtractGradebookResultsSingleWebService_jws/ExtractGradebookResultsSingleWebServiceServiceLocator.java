/**
 * ExtractGradebookResultsSingleWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws;

public class ExtractGradebookResultsSingleWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebServiceService {

    public ExtractGradebookResultsSingleWebServiceServiceLocator() {
    }


    public ExtractGradebookResultsSingleWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ExtractGradebookResultsSingleWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ExtractGradebookResultsSingleWebService
    private java.lang.String ExtractGradebookResultsSingleWebService_address = "https://mydev.unisa.ac.za/unisa-axis/ExtractGradebookResultsSingleWebService.jws";

    public java.lang.String getExtractGradebookResultsSingleWebServiceAddress() {
        return ExtractGradebookResultsSingleWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ExtractGradebookResultsSingleWebServiceWSDDServiceName = "ExtractGradebookResultsSingleWebService";

    public java.lang.String getExtractGradebookResultsSingleWebServiceWSDDServiceName() {
        return ExtractGradebookResultsSingleWebServiceWSDDServiceName;
    }

    public void setExtractGradebookResultsSingleWebServiceWSDDServiceName(java.lang.String name) {
        ExtractGradebookResultsSingleWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebService_PortType getExtractGradebookResultsSingleWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ExtractGradebookResultsSingleWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getExtractGradebookResultsSingleWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebService_PortType getExtractGradebookResultsSingleWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getExtractGradebookResultsSingleWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setExtractGradebookResultsSingleWebServiceEndpointAddress(java.lang.String address) {
        ExtractGradebookResultsSingleWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.ExtractGradebookResultsSingleWebService_jws.ExtractGradebookResultsSingleWebServiceSoapBindingStub(new java.net.URL(ExtractGradebookResultsSingleWebService_address), this);
                _stub.setPortName(getExtractGradebookResultsSingleWebServiceWSDDServiceName());
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
        if ("ExtractGradebookResultsSingleWebService".equals(inputPortName)) {
            return getExtractGradebookResultsSingleWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.unisa.ac.za/unisa-axis/ExtractGradebookResultsSingleWebService.jws", "ExtractGradebookResultsSingleWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.unisa.ac.za/unisa-axis/ExtractGradebookResultsSingleWebService.jws", "ExtractGradebookResultsSingleWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ExtractGradebookResultsSingleWebService".equals(portName)) {
            setExtractGradebookResultsSingleWebServiceEndpointAddress(address);
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
