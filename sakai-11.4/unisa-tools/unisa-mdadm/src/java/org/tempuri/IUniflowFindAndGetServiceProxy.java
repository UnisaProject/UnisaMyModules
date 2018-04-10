package org.tempuri;

public class IUniflowFindAndGetServiceProxy implements org.tempuri.IUniflowFindAndGetService {
  private String _endpoint = null;
  private org.tempuri.IUniflowFindAndGetService iUniflowFindAndGetService = null;
  
  public IUniflowFindAndGetServiceProxy() {
    _initIUniflowFindAndGetServiceProxy();
  }
  
  public IUniflowFindAndGetServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initIUniflowFindAndGetServiceProxy();
  }
  
  private void _initIUniflowFindAndGetServiceProxy() {
    try {
      iUniflowFindAndGetService = (new org.tempuri.UniflowFindAndGetServiceLocator()).getBasicHttpBinding_IUniflowFindAndGetService();
      if (iUniflowFindAndGetService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iUniflowFindAndGetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iUniflowFindAndGetService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iUniflowFindAndGetService != null)
      ((javax.xml.rpc.Stub)iUniflowFindAndGetService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IUniflowFindAndGetService getIUniflowFindAndGetService() {
    if (iUniflowFindAndGetService == null)
      _initIUniflowFindAndGetServiceProxy();
    return iUniflowFindAndGetService;
  }
  
  public org.datacontract.schemas._2004._07.UniflowFindAndGetService.GetSearchTargetsResult getSearchTargets(org.datacontract.schemas._2004._07.UniflowFindAndGetService.AuthenticationDetails pAuthenticationDetails) throws java.rmi.RemoteException{
    if (iUniflowFindAndGetService == null)
      _initIUniflowFindAndGetServiceProxy();
    return iUniflowFindAndGetService.getSearchTargets(pAuthenticationDetails);
  }
  
  public org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchResult search(org.datacontract.schemas._2004._07.UniflowFindAndGetService.SearchRequest pRequest) throws java.rmi.RemoteException{
    if (iUniflowFindAndGetService == null)
      _initIUniflowFindAndGetServiceProxy();
    return iUniflowFindAndGetService.search(pRequest);
  }
  
  public org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalResult retrieve(org.datacontract.schemas._2004._07.UniflowFindAndGetService.RetrievalRequest pRequest) throws java.rmi.RemoteException{
    if (iUniflowFindAndGetService == null)
      _initIUniflowFindAndGetServiceProxy();
    return iUniflowFindAndGetService.retrieve(pRequest);
  }
  
  
}