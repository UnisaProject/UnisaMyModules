<%--
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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
--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<f:view>
<sakai:view title="Modules: Select Resource Item" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>

	<t:saveState id="editId" value="#{editSectionPage.editId}" />	
	<t:saveState id="fromPage" value="#{listResourcesPage.fromPage}" />
	<t:saveState id="sectionId" value="#{listResourcesPage.sectionId}" />
	<t:saveState id="ltidisp" value="#{editSectionPage.shouldLTIDisplayAdvanced}" />	
	
<h:form id="EditLtiServerViewForm" enctype="multipart/form-data">	
<!-- top nav bar -->
    <f:subview id="top">
      <jsp:include page="topnavbar.jsp"/> 
    </f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/replace2.gif" alt="" width="16" height="16" align="absmiddle"><h:outputText value="#{msgs.editcontentlinkserverview_selecting}"/></div>

<!-- This Begins the Main Text Area -->
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	<p><h:outputText id="Stext_2" value="#{msgs.editcontentlinkserverview_msg1}"/></p>
	<table class="maintableCollapseWithBorder">
			<tr><td height="20" colspan="2"> <h:outputText id="Stext_add" value="#{msgs.editcontentltiserverview_replace}" styleClass="bold"/> </td></tr>									 
						  
<!--replace with new link part Begin -->
					<table class="maintableCollapseWithNoBorder"  >
					<tr> <td class="col1"><h:outputText id="format_text" value="#{msgs.editcontentltiserverview_format}"/>
					</td>
                    <td class="col2">
                               <h:selectOneMenu id="LTIDisplay" value="#{editSectionPage.LTIDisplay}" 
                                    valueChangeListener="#{editSectionPage.toggleLTIDisplay}" 
                                    onchange="this.form.submit();"
                                    immediate="true" >
                            <f:selectItem itemValue="Basic" itemLabel="#{msgs.addmodulesections_basic_lti}"/>
                            <f:selectItem itemValue="Advanced" itemLabel="#{msgs.addmodulesections_advanced_lti}"/>
                            </h:selectOneMenu>
					</td></tr> 
					<tr><td colspan="2"> 
                                                <f:subview id="LTIBasic" rendered="#{editSectionPage.shouldLTIDisplayBasic}">
                                                        <jsp:include page="lti_basic_edit.jsp"/>
                                                </f:subview>
                                                <f:subview id="LTIAdvanced" rendered="#{editSectionPage.shouldLTIDisplayAdvanced}">
                                                        <jsp:include page="lti_advanced_edit.jsp"/>
                                                </f:subview>
                        </td></tr>          
                        <t:saveState id="ltiurl1" value="#{editSectionPage.LTIUrl}" />
						<t:saveState id="tltiurltitle" value="#{editSectionPage.newURLTitle}" />
						<t:saveState id="ltikey1" value="#{editSectionPage.LTIKey}" />
						<t:saveState id="ltipswd" value="#{editSectionPage.LTIPassword}" />              
						<t:saveState id="tltidescriptor1" value="#{editSectionPage.LTIDescriptor}" />
	
					</table> </td></tr>       
					<tr><td>
						<div class="actionBar" align="left">
							 <h:commandButton id="addButton_1" actionListener="#{editSectionPage.setServerLTI}" value="#{msgs.im_continue}" tabindex="" accesskey="#{msgs.continue_access}" title="#{msgs.im_continue_text}" styleClass="BottomImgContinue">
								 <f:attribute name="sectionId" value="#{editSectionPage.editId}" />
						 	</h:commandButton>
					     	<h:commandButton id="cancelButton_1" immediate="true" actionListener="#{editSectionPage.cancelServerFile}" value="#{msgs.im_cancel}" tabindex="" accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel">
					     		<f:attribute name="sectionId" value="#{editSectionPage.editId}" />
						 	</h:commandButton>		
						</div></td></tr>
	<!-- new link end -->				            		
						<!-- start main -->
				            <tr><td width="100%" valign="top">
						
									<f:subview id="ResourceListingForm" >
										<jsp:include page="list_resources.jsp"/> 
									</f:subview>	

					     </td></tr>
					    </table>					
	<!--end  main -->	
			
	<!-- This Ends the Main Text Area -->
     	</h:form>
</sakai:view>
</f:view>

