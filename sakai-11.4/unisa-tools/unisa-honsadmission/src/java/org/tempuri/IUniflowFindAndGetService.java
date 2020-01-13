/**
 * IUniflowFindAndGetService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface IUniflowFindAndGetService extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.GetSearchTargetsResult getSearchTargets(org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails pAuthenticationDetails) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchResult search(org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchRequest pRequest) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalResult retrieve(org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalRequest pRequest) throws java.rmi.RemoteException;
}
