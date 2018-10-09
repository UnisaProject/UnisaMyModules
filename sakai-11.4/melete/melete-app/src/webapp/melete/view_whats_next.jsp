<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.etudes.tool.melete.ViewNextStepsPage"%>
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/view_whats_next.jsp $
 * $Id: view_whats_next.jsp 85951 2014-03-14 16:53:27Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010,2011 Etudes, Inc.
 *
 * Portions completed before September 1, 2008 Copyright (c) 2004, 2005, 2006, 2007, 2008 Foothill College, ETUDES Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 **********************************************************************************
-->
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%
final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
final ViewNextStepsPage vnsPage = (ViewNextStepsPage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "viewNextStepsPage");

String nextSeqNo = (String)request.getParameter("nextSeqNo");
String moduleSeqNo = (String)request.getParameter("moduleSeqNo");
String prevSecId = (String)request.getParameter("prevSecId");
String prevModId = (String)request.getParameter("prevModId");
if (nextSeqNo != null)
{
	if (!nextSeqNo.trim().equals("null") && nextSeqNo.trim().length() > 0) vnsPage.setNextSeqNo(Integer.parseInt(nextSeqNo));
}
if (moduleSeqNo != null)
{
	if (!moduleSeqNo.trim().equals("null") && moduleSeqNo.trim().length() > 0) vnsPage.setModuleSeqNo(Integer.parseInt(moduleSeqNo));
}
if (prevSecId != null)
{
	if (!prevSecId.trim().equals("null") && prevSecId.trim().length() > 0) vnsPage.setPrevSecId(Integer.parseInt(prevSecId));
}
if (prevModId != null)
{
	if (!prevModId.trim().equals("null") && prevModId.trim().length() > 0) vnsPage.setPrevModId(Integer.parseInt(prevModId));
}
%>
<f:view>
<sakai:view title="Modules: Student View" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="meleterightscheck.jsp" %>
<a name="newanchor"></a>
<t:saveState id="vnspmod" value="#{viewNextStepsPage.module}" />
<t:saveState id="vnsppmod" value="#{viewNextStepsPage.prevModId}" />
<t:saveState id="vnsppsec" value="#{viewNextStepsPage.prevSecId}" />
<t:saveState id="vnspnso" value="#{viewNextStepsPage.nextSeqNo}" />
<h:form id="viewNSsectionform">   
	<f:subview id="top">
	  <jsp:include page="topnavbar.jsp?myMode=View"/> 
	</f:subview> 
	<table class="maintableCollapseWithNoBorder" >
	<!--Page Content-->

	<tr>
		<td align="center">
		     <!-- The getmodule method correctly determines the prev and next seq nos in the backing bean -->
			<!-- The hidden field below has been added just to get the getmodule method to execute first -->
		    <h:inputHidden id="hacktitle" value="#{viewNextStepsPage.module.title}"/>
					<f:subview id="topmod">
						<jsp:include page="view_navigate_wn.jsp"/>
					</f:subview>
				<h:panelGroup id="bcsecpgroup" binding="#{viewNextStepsPage.secpgroup}"/>
			</td>
</tr> 
<tr>
<td align="right">
<h:commandLink id="myBookmarksLink" action="#{bookmarkPage.gotoMyBookmarks}" styleClass="toolUiLink">
<h:graphicImage id="mybook_gif" value="/images/my-bookmarks.png" alt="" styleClass="AuthImgClass"/>
 <h:outputText id="mybks" value="#{msgs.my_bookmarks}" />
 <f:param name="fromPage" value="view_whats_next" />
 <f:param name="fromModuleId" value="#{viewNextStepsPage.prevModId}" />
 <f:param name="fromSectionId" value="#{viewNextStepsPage.prevSecId}" />
 <f:param name="fromModuleSeqNo" value="#{viewNextStepsPage.nextSeqNo}" />
</h:commandLink>				  
</td>
</tr>
<tr>
		<td align="left">  &nbsp;		</td>
</tr> 

<tr>
		<td align="left">  
				<h:outputText value="#{msgs.view_whats_next_whats_next}"   styleClass="bold style7"></h:outputText>      
		</td>
</tr> 
<tr>
		<td align="left">  
				<h:outputText id="wnext" value="#{viewNextStepsPage.module.whatsNext}"></h:outputText>     
		</td>
</tr> 

 <tr>
<td align="left"> &nbsp;</td>
</tr>
<tr>
<td align="center">
					<f:subview id="bottommod">
						<jsp:include page="view_navigate_wn.jsp"/>
					</f:subview>
</td>
</tr>                



  </table>

<!--End Content-->
</h:form>
</sakai:view>
</f:view>

