/**
 * GradebookSyncStudentSystemWebServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws;

public class GradebookSyncStudentSystemWebServiceServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceService {

    public GradebookSyncStudentSystemWebServiceServiceLocator() {
    }


    public GradebookSyncStudentSystemWebServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GradebookSyncStudentSystemWebServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GradebookSyncStudentSystemWebService
    private java.lang.String GradebookSyncStudentSystemWebService_address = "https://mydev.int.unisa.ac.za/unisa-axis/GradebookSyncStudentSystemWebService.jws";

    public java.lang.String getGradebookSyncStudentSystemWebServiceAddress() {
        return GradebookSyncStudentSystemWebService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GradebookSyncStudentSystemWebServiceWSDDServiceName = "GradebookSyncStudentSystemWebService";

    public java.lang.String getGradebookSyncStudentSystemWebServiceWSDDServiceName() {
        return GradebookSyncStudentSystemWebServiceWSDDServiceName;
    }

    public void setGradebookSyncStudentSystemWebServiceWSDDServiceName(java.lang.String name) {
        GradebookSyncStudentSystemWebServiceWSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebService_PortType getGradebookSyncStudentSystemWebService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GradebookSyncStudentSystemWebService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGradebookSyncStudentSystemWebService(endpoint);
    }

    public za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebService_PortType getGradebookSyncStudentSystemWebService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getGradebookSyncStudentSystemWebServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGradebookSyncStudentSystemWebServiceEndpointAddress(java.lang.String address) {
        GradebookSyncStudentSystemWebService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceSoapBindingStub _stub = new za.ac.unisa.unisa_axis.GradebookSyncStudentSystemWebService_jws.GradebookSyncStudentSystemWebServiceSoapBindingStub(new java.net.URL(GradebookSyncStudentSystemWebService_address), this);
                _stub.setPortName(getGradebookSyncStudentSystemWebServiceWSDDServiceName());
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
        if ("GradebookSyncStudentSystemWebService".equals(inputPortName)) {
            return getGradebookSyncStudentSystemWebService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/GradebookSyncStudentSystemWebService.jws", "GradebookSyncStudentSystemWebServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/GradebookSyncStudentSystemWebService.jws", "GradebookSyncStudentSystemWebService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("GradebookSyncStudentSystemWebService".equals(portName)) {
            setGradebookSyncStudentSystemWebServiceEndpointAddress(address);
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
