/**
 * DataCleanup_GradebookWebService2ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws;

public class DataCleanup_GradebookWebService2ServiceLocator extends org.apache.axis.client.Service implements za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2Service {

    public DataCleanup_GradebookWebService2ServiceLocator() {
    }


    public DataCleanup_GradebookWebService2ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DataCleanup_GradebookWebService2ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DataCleanup_GradebookWebService2
    private java.lang.String DataCleanup_GradebookWebService2_address = "https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_GradebookWebService2.jws";

    public java.lang.String getDataCleanup_GradebookWebService2Address() {
        return DataCleanup_GradebookWebService2_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DataCleanup_GradebookWebService2WSDDServiceName = "DataCleanup_GradebookWebService2";

    public java.lang.String getDataCleanup_GradebookWebService2WSDDServiceName() {
        return DataCleanup_GradebookWebService2WSDDServiceName;
    }

    public void setDataCleanup_GradebookWebService2WSDDServiceName(java.lang.String name) {
        DataCleanup_GradebookWebService2WSDDServiceName = name;
    }

    public za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2_PortType getDataCleanup_GradebookWebService2() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DataCleanup_GradebookWebService2_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDataCleanup_GradebookWebService2(endpoint);
    }

    public za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2_PortType getDataCleanup_GradebookWebService2(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2SoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2SoapBindingStub(portAddress, this);
            _stub.setPortName(getDataCleanup_GradebookWebService2WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDataCleanup_GradebookWebService2EndpointAddress(java.lang.String address) {
        DataCleanup_GradebookWebService2_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2SoapBindingStub _stub = new za.ac.unisa.unisa_axis.DataCleanup_GradebookWebService2_jws.DataCleanup_GradebookWebService2SoapBindingStub(new java.net.URL(DataCleanup_GradebookWebService2_address), this);
                _stub.setPortName(getDataCleanup_GradebookWebService2WSDDServiceName());
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
        if ("DataCleanup_GradebookWebService2".equals(inputPortName)) {
            return getDataCleanup_GradebookWebService2();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_GradebookWebService2.jws", "DataCleanup_GradebookWebService2Service");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("https://mydev.int.unisa.ac.za/unisa-axis/DataCleanup_GradebookWebService2.jws", "DataCleanup_GradebookWebService2"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DataCleanup_GradebookWebService2".equals(portName)) {
            setDataCleanup_GradebookWebService2EndpointAddress(address);
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
