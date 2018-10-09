<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/editContentLinkServerView.jsp $
 * $Id: editContentLinkServerView.jsp 86862 2014-08-08 20:37:38Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011,2014 Etudes, Inc.
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

<f:view>
<sakai:view title="Modules: Select Resource Item" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>

<h:form id="EditServerViewForm" enctype="multipart/form-data">	
	<!-- top nav bar -->
	<f:subview id="top">
	  <jsp:include page="topnavbar.jsp"/> 
	</f:subview>
	
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/replace2.gif" alt="" width="16" height="16" align="absmiddle"><h:outputText value="#{msgs.editcontentlinkserverview_selecting}"/></div>
	<t:saveState id="workId" value="#{editSectionPage.editId}" />
	<t:saveState id="fromPage" value="#{listResourcesPage.fromPage}" />
	<t:saveState id="sectionId" value="#{listResourcesPage.sectionId}" />
	
<!-- This Begins the Main Text Area -->
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	<p><h:outputText id="Stext_2" value="#{msgs.editcontentlinkserverview_msg1}"/></p>
	<!--replace with new link part Begin -->
	<table class="maintableCollapseWithBorder">
         <tr>
           <td width="100%" valign="top">
				<f:subview id="LinkResourceListingForm" >	
					<jsp:include page="list_resources.jsp"/> 
				</f:subview>
   		  </td>
         </tr>
         <tr>
          	<td>
		    	<div class="actionBar" align="left">
					<h:commandButton id="return" actionListener="#{editSectionPage.returnBack}"  value="#{msgs.im_return}" accesskey="#{msgs.return_access}" title="#{msgs.im_return_text}" styleClass="BottomImgReturn">
					</h:commandButton>
   	        	</div>
		     </td>
		</tr>
     </table>					
	<!-- This Ends the Main Text Area -->
	     	</h:form>
</sakai:view>
</f:view>
