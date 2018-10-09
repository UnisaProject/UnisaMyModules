<%@ page import="org.etudes.tool.melete.MeleteSiteAndUserInfo"%>
<%
final javax.faces.context.FacesContext accFacesContext = javax.faces.context.FacesContext.getCurrentInstance();
final MeleteSiteAndUserInfo accMeleteSiteAndUserInfo = (MeleteSiteAndUserInfo)accFacesContext.getApplication().getVariableResolver().resolveVariable(accFacesContext, "meleteSiteAndUserInfo");

boolean authCheck = accMeleteSiteAndUserInfo.checkAuthorization();
if (authCheck == false)
{
	String destination = "noAccess.jsf";
   response.sendRedirect(response.encodeRedirectURL(destination));
}   
%>