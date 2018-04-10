/**
 * UniflowFindAndGetServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

import org.sakaiproject.component.cover.ServerConfigurationService;

import com.sun.jmx.remote.util.Service;

public class UniflowFindAndGetServiceLocator extends org.apache.axis.client.Service implements org.tempuri.UniflowFindAndGetService {

    public UniflowFindAndGetServiceLocator() {
    }


    public UniflowFindAndGetServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public UniflowFindAndGetServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IUniflowFindAndGetService
    private java.lang.String BasicHttpBinding_IUniflowFindAndGetService_address = ServerConfigurationService.getString("uniflow.webservice.path");
    //getServlet().getServletContext().getInitParameter("applicationFullPath")+"/";

    public java.lang.String getBasicHttpBinding_IUniflowFindAndGetServiceAddress() {
        return BasicHttpBinding_IUniflowFindAndGetService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName = "BasicHttpBinding_IUniflowFindAndGetService";

    public java.lang.String getBasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName() {
        return BasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName = name;
    }

    public org.tempuri.IUniflowFindAndGetService getBasicHttpBinding_IUniflowFindAndGetService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IUniflowFindAndGetService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IUniflowFindAndGetService(endpoint);
    }

    public org.tempuri.IUniflowFindAndGetService getBasicHttpBinding_IUniflowFindAndGetService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IUniflowFindAndGetServiceStub _stub = new org.tempuri.BasicHttpBinding_IUniflowFindAndGetServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IUniflowFindAndGetServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IUniflowFindAndGetService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.IUniflowFindAndGetService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IUniflowFindAndGetServiceStub _stub = new org.tempuri.BasicHttpBinding_IUniflowFindAndGetServiceStub(new java.net.URL(BasicHttpBinding_IUniflowFindAndGetService_address), this);
                _stub.setPortName(getBasicHttpBinding_IUniflowFindAndGetServiceWSDDServiceName());
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
        if ("BasicHttpBinding_IUniflowFindAndGetService".equals(inputPortName)) {
            return getBasicHttpBinding_IUniflowFindAndGetService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "UniflowFindAndGetService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IUniflowFindAndGetService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IUniflowFindAndGetService".equals(portName)) {
            setBasicHttpBinding_IUniflowFindAndGetServiceEndpointAddress(address);
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
