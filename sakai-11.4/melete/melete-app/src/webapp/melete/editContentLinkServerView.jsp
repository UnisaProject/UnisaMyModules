<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/webapp/melete/editContentLinkServerView.jsp $
 * $Id: editContentLinkServerView.jsp 77082 2011-10-24 18:38:10Z rashmi@etudes.org $  
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
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<f:view>
<sakai:view title="Modules: Select Resource Item" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>
<script type="text/javascript" language="javascript1.2">

function fillTitle()
{
	var u = document.getElementById("EditServerViewForm:link").value;
	document.getElementById("EditServerViewForm:link_title").value = u;
}

</script>

<h:form id="EditServerViewForm" enctype="multipart/form-data">	
	<!-- top nav bar -->
	<f:subview id="top">
	  <jsp:include page="topnavbar.jsp"/> 
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/replace2.gif" alt="" width="16" height="16" align="absmiddle"><h:outputText value="#{msgs.editcontentlinkserverview_selecting}"/></div>
	
	<t:saveState id="newlinkUrl" value="#{listResourcesPage.newLinkUrl}" />
	<t:saveState id="newURLTitle" value="#{listResourcesPage.newURLTitle}" />
	<t:saveState id="openWindow" value="#{listResourcesPage.openWindow}" />
	<t:saveState id="fromPage" value="#{listResourcesPage.fromPage}" />
	<t:saveState id="sectionId" value="#{listResourcesPage.sectionId}" />
	
<!-- This Begins the Main Text Area -->
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	<p><h:outputText id="Stext_2" value="#{msgs.editcontentlinkserverview_msg1}"/></p>
	<!--replace with new link part Begin -->
	<table class="maintableCollapseWithBorder">
		<tr><td>
			<table class="maintableCollapseWithBorder" >
				<tr><td height="20" colspan="2" class="maintabledata8"> <h:outputText id="Stext_add" value="#{msgs.editcontentlinkserverview_replace}" styleClass="bold"/> 									 
				 <tr><td height="20" colspan="2"> 
													<h:outputText id="editlinkText6" value="#{msgs.editcontentlinkserverview_provide}" />
													<h:outputText id="editlinkText7" value=" " styleClass="ExtraPaddingClass"/>
													 <h:inputText id="link" value="#{listResourcesPage.newLinkUrl}" size="40" onchange="fillTitle()" /> 	
				</td></tr>	
				<tr><td height="20" colspan="2"> 
													<h:outputText id="editlinkText_6" value="#{msgs.resources_proper_pan_URL}" /> <h:outputText value="*" styleClass="required"/>
		 											<h:outputText id="editlinkText_7" value=" " styleClass="ExtraPaddingClass"/>
		 											<h:outputText id="editlinkText_7_1" value=" " styleClass="ExtraPaddingClass"/>
													 <h:inputText id="link_title" value="#{listResourcesPage.newURLTitle}" size="40" /> 	
				</td></tr>	
				<tr><td height="20" colspan="2"> 
												<h:selectBooleanCheckbox id="windowopen" title="openWindow" value="#{listResourcesPage.openWindow}">
												 </h:selectBooleanCheckbox>
													 <h:outputText id="editlinkText_8" value="#{msgs.editcontentlinkserverview_openwindow}" />
							                   					
				</td></tr>
				</table> 

		 	<div class="actionBar" align="left">
		          		<h:commandButton id="addButton" actionListener="#{listResourcesPage.addNewUrl}" value="#{msgs.im_continue}" action="#{listResourcesPage.setServerFile}" accesskey="#{msgs.continue_access}" title="#{msgs.im_continue_text}" styleClass="BottomImgContinue">
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
						          								                    													
											<f:subview id="LinkResourceListingForm" >	
												<jsp:include page="list_resources.jsp"/> 
											</f:subview>
																																												
															
			  </td>
            </tr>
            </table>					
	<!-- This Ends the Main Text Area -->
	     	</h:form>
</sakai:view>
</f:view>
