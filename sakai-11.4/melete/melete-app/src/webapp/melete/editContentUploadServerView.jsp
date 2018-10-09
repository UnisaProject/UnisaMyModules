<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/editContentUploadServerView.jsp $
 * $Id: editContentUploadServerView.jsp 77082 2011-10-24 18:38:10Z rashmi@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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
<%@ taglib uri="http://javascript4jsf.dev.java.net/" prefix="j4j" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<f:view>
<sakai:view title="Modules: Select Resource Item" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>

<t:saveState id="fromPage" value="#{listResourcesPage.fromPage}" />
<t:saveState id="sectionId" value="#{listResourcesPage.sectionId}" />

<%@ page import="javax.faces.application.FacesMessage, java.util.ResourceBundle"%>

<% 
	String status = (String)request.getAttribute("upload.status");
	String other = (String)request.getParameter("sectionId");
	
		if( status != null && !status.equalsIgnoreCase("ok"))
		{
			final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			facesContext.getExternalContext().redirect("editContentUploadServerView.jsf?fromPage=editContentUploadServerView&showMessage=true&sectionId="+other);			
	   }
	   
%>

<h:form id="EditUploadServerViewForm" enctype="multipart/form-data">	
<j4j:param name="sectionId" value="#{listResourcesPage.sectionId}" method="get" />
<j4j:param name="fromPage" value="#{listResourcesPage.fromPage}" method="get" />
<!-- top nav bar -->
    <f:subview id="top">
      <jsp:include page="topnavbar.jsp"/> 
    </f:subview>
	
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/replace2.gif" alt="" width="16" height="16" align="absmiddle"><h:outputText value="#{msgs.editcontentuploadserverview_selecting}" /></div>
<!-- This Begins the Main Text Area -->
			<h:outputText value="#{listResourcesPage.openWindow}" style="visibility:hidden;display:none"/>
			<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
			<p><h:outputText id="Stext_2" value="#{msgs.editcontentuploadserverview_msg1}"/></p>
    		<table class="maintableCollapseWithBorder">
				<tr><td>  					 
<!--replace with local part Begin -->
				<table class="maintableCollapseWithNoBorder" >
						<tr><td height="20" colspan="2" class="maintabledata8"> <h:outputText id="Stext_add" value="#{msgs.editcontentuploadserverview_replace}" styleClass="bold"/> 									 
						 <tr><td height="20" colspan="2"> <h:outputText id="Stext3" value="#{msgs.editcontentuploadserverview_upload}"/> 				
								<INPUT TYPE="FILE" id="file1" NAME="file1" style="visibility:visible" />
						</td></tr>	
						<tr><td  colspan="2"> 
							<h:outputText id="note" value="#{msgs.editcontentuploadserverview_note} #{listResourcesPage.maxUploadSize}MB."  styleClass="comment red"/>				
						</td></tr>
						<tr><td  colspan="2"> 
							<h:selectBooleanCheckbox id="windowopen" title="openWindow" value="#{listResourcesPage.openWindow}" />
	                        <h:outputText id="editlinkText_8" value="#{msgs.editcontentlinkserverview_openwindow}"/>
						</td></tr>	
					</table>
			       	<div class="actionBar" align="left">
		          		<h:commandButton id="addButton" actionListener="#{listResourcesPage.addNewFile}" value="#{msgs.im_continue}" action="#{listResourcesPage.setServerFile}" accesskey="#{msgs.continue_access}" title="#{msgs.im_continue_text}" styleClass="BottomImgContinue">
		          				<f:param name="sectionId" value="#{listResourcesPage.sectionId}" />		          			
					    </h:commandButton> 		          			
					      		
		          	 	<h:commandButton id="cancelButton" immediate="true" value="#{msgs.im_cancel}" actionListener="#{listResourcesPage.cancelServerFile}" tabindex="" accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel">
		          	 		<f:param name="sectionId" value="#{listResourcesPage.sectionId}" />		          			
					    </h:commandButton> 
        			 </div>
					 </td></tr>        
		   
		<!-- replace local end -->			            
				<!-- start main -->
						            <tr>
						              <td width="100%" valign="top">
						          								                    													
											<f:subview id="UploadResourceListingForm" >	
												<jsp:include page="list_resources.jsp"/> 
											</f:subview>
								
						  </td>
			            </tr>
			           	</table>					

	<!-- This Ends the Main Text Area -->
	     	</h:form>
</sakai:view>
</f:view>
