<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/editmodulesections.jsp $
 * $Id: editmodulesections.jsp 86862 2014-08-08 20:37:38Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011,2012, 2014 Etudes, Inc.
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
--%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://javascript4jsf.dev.java.net/" prefix="j4j" %>
<%@ taglib uri="date-time-converter" prefix="o" %>

<f:view>
<sakai:view title="Modules: Edit Module Sections" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>
<script type="text/javascript" language="javascript" src="/etudes-melete-tool/js/sharedscripts.js"></script>

<%@ page import="org.sakaiproject.util.ResourceLoader, javax.faces.application.FacesMessage,org.etudes.tool.melete.AddResourcesPage, org.etudes.tool.melete.AuthorPreferencePage, org.etudes.tool.melete.EditSectionPage"%> 

<% 
	ResourceLoader bundle = new ResourceLoader("org.etudes.tool.melete.bundle.Messages");
	String mensaje=bundle.getString("editmodulesections_uploading");
	final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
	
	if (request.getParameter("sectionId") != null && !request.getParameter("sectionId").equals("null"))
		request.setAttribute("attr_sId", request.getParameter("sectionId"));
	else 
	{
		response.sendRedirect("list_auth_modules.jsf");
	}
	
  final EditSectionPage eSectionPage = (EditSectionPage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "editSectionPage");
  final AuthorPreferencePage authorPreferencePage = (AuthorPreferencePage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "authorPreferences");
%>

<script type="text/javascript" language="javascript1.2">
var fckEditorInstance = null;
function FCKeditor_OnComplete( editorInstance )
{
	fckEditorInstance = editorInstance;	
}
var ckEditorInstance = null;
function CKeditor_OnComplete( editorInstance )
{
	ckEditorInstance = editorInstance;	
}

function saveEditor()
{
	var result = true;
	var sferyxdisplay = document.getElementById("EditSectionForm:contentEditorView:sferyxDisplay");
	if ((sferyxdisplay != undefined )&&(document.htmleditor!=undefined && document.htmleditor!= null))
	{	  	
		// document.htmleditor.saveToDefaultLocation();  
		document.htmleditor.addAdditionalDynamicParameter('mode',document.getElementById("EditSectionForm:mode").value);
        document.htmleditor.addAdditionalDynamicParameter('sId',document.getElementById("EditSectionForm:sId").value);
        if(document.getElementById("EditSectionForm:rId") != undefined || document.getElementById("EditSectionForm:rId") != null)
      	  document.htmleditor.addAdditionalDynamicParameter('resourceId',document.getElementById("EditSectionForm:rId").value);
       document.htmleditor.addAdditionalDynamicParameter('uId',document.getElementById("EditSectionForm:uId").value);		  
	   document.htmleditor.addAdditionalDynamicParameter('editLastSaveTime',document.getElementById("EditSectionForm:editLastSaveTime").innerHTML);
	   document.htmleditor.addAdditionalDynamicParameter('edited',document.htmleditor.isDocumentEdited());	
		result = document.htmleditor.uploadMultipartContent(true);			    	

		// get sferyx's idea about changes made
		var changed = document.htmleditor.isDocumentEdited();
		document.getElementById("EditSectionForm:edited").value = changed;
	}
	
	if (fckEditorInstance != null)
	{
		var changed = false;
		// if in source mode when submit, we might miss changes
		var cmd = fckEditorInstance.Commands.GetCommand("Source");
		var cmdState = cmd.GetState();
		if (cmdState == 1)
		{
			changed = true;
		}
		else
		{
			// ask FCK about changes made
			changed = fckEditorInstance.IsDirty();
		}
		document.getElementById("EditSectionForm:edited").value = changed;
	}
	
	if (ckEditorInstance != null)
	{
		var changed = false;
		changed = ckEditorInstance.checkDirty();
		document.getElementById("EditSectionForm:edited").value = changed;
	}

	return result;	
}

</script>

      <!-- This Begins the Main Text Area -->
	<h:form id="EditSectionForm" enctype="multipart/form-data" onsubmit="if (saveEditor()){ return true;}else {return false;}"> 	
	<j4j:param name="sectionId" value="#{editSectionPage.editId}" method="get" />
			  <h:inputHidden id="formName" value="EditSectionForm"/>  
			  <h:inputHidden id="mode" value="Edit"/>
			  <h:inputHidden id="sId" value="#{editSectionPage.section.sectionId}"/>
			  <h:inputHidden id="rId" value="#{editSectionPage.meleteResource.resourceId}" rendered="#{editSectionPage.meleteResource !=null}"/>
			  <h:inputHidden id="uId" value="#{editSectionPage.currUserId}"/>	
			  <h:inputHidden id="edited" value="#{editSectionPage.isComposeDataEdited}"/>	
		  	  <h:inputHidden id="activeCheck" value="#{editSectionPage.activeCheckUrl}" />
		<!-- top nav bar -->
		<f:subview id="top">
			<jsp:include page="topnavbar.jsp"/> 
		</f:subview>
		
		<t:saveState id="worksection" value="#{editSectionPage.section}" />
		<t:saveState id="workLicense" value="#{licensePage.licenseCodes}" />
		<t:saveState id="workId" value="#{editSectionPage.editId}" />
		<t:saveState id="workStartTime" value="#{editSectionPage.lastSavedAt}" />
				 
		<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/document_edit.gif" alt="" width="16" height="16" align="absbottom"><h:outputText id="captionText" value="#{msgs.editmodulesections_editing_section}" /> </div>
		<h:messages id="editsectionerror"  layout="table" showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
        <div id="errMsg1" style="color:red"><p> </p></div>
        <h:outputText id="errMsg2" styleClass="alertMessage" value="#{msgs.url_alert}" rendered="#{editSectionPage.httpAddressAlert != null && editSectionPage.httpAddressAlert == true}" /> 
        <table class="maincktableCollapseWithBorder">
     	   <tr>
            <td class="maintabledata3" style="padding:3px">
				  <table class="maincktableCollapseWithNoBorder">
                   <!-- table header -->
                   <tr>
			            <td width="100%" colspan="2" height="20" class="maintabledata2" style="padding:4px"> 
				            <table  width="100%"> 
			                   <tr >
					            <td width="70%" style="padding:4px">  
					            	<h:commandButton id="editPrevButton" actionListener="#{editSectionPage.editPrevSectionListener}" disabled="#{!editSectionPage.hasPrev}" value="#{msgs.editmodulesections_edit_prev}" accesskey="#{msgs.prev_access}" title="#{msgs.im_prev_text}" styleClass="BottomImgPrev">
					 					    
					       			</h:commandButton>      	   
									<h:commandButton id="TOCButton" action="#{editSectionPage.goTOC}" value="#{msgs.editmodulesections_TOC}" accesskey="#{msgs.toc_access}" title="#{msgs.im_toc_text}" styleClass="BottomImgTOC"/>
					       			<h:commandButton id="editNextButton" actionListener="#{editSectionPage.editNextSectionListener}" disabled="#{!editSectionPage.hasNext}" value="#{msgs.editmodulesections_edit_next}" accesskey="#{msgs.next_access}" title="#{msgs.im_next_text}" styleClass="BottomImgNext">
					    					       	
					       			</h:commandButton>
						     	
						            <h:outputText id="text4_3" value="  " styleClass="ExtraPaddingClass"/>	
									<h:commandButton id="editAddNewButton" actionListener="#{editSectionPage.saveAndAddAnotherSection}" value="#{msgs.editmodulesections_add_new}" accesskey="#{msgs.add_access}" title="#{msgs.im_save_text}" styleClass="BottomImgAdd">
																 
									</h:commandButton>  			
						        </td>
						        <td class="right" width="30%" style="padding:4px">    
									 <h:commandButton id="bookmarkSectionLink" action="#{editSectionPage.saveAndAddBookmark}" value="#{msgs.bookmark_text}" 
										 onclick="var w=OpenBookmarkWindow(#{editSectionPage.section.sectionId},document.getElementById('EditSectionForm:title').value,'','','','Melete Bookmark Window');"
										  accesskey="#{msgs.bookmark_access}" title="#{msgs.im_bmrk_text}" styleClass="BottomImgBookmarkIt">
									</h:commandButton>
						     		<h:commandButton id="myBookmarksLink" action="#{editSectionPage.gotoMyBookmarks}" value="#{msgs.my_bookmarks}" accesskey="#{msgs.mybookmarks_access}" title="#{msgs.im_mybmrks_text}" styleClass="BottomImgMyBookmarks"/>
								</td>
					          </tr>
				          </table>
			          </td>
			          </tr>		
			          <tr>
			          	<td colspan="2" class="maintabledata9" style="padding:4px" >
			     			<h:outputText id="text4" value="#{editSectionPage.module.title}" /> &raquo; <h:outputText id="text4_1" value="#{editSectionPage.section.title}" />
			          	</td>
			          </tr>	 
	                   <!-- end table header -->
                                   <tr>
                                    <td class="col1" align="left" valign="top" style="padding:4px"><h:outputText id="text7" value="#{msgs.editmodulesections_section_title}" /><span class="required">*</span></td>
                                    <td class="col2" align="left" valign="top" style="padding:4px">
									<h:inputText id="title" value="#{editSectionPage.section.title}" size="45" styleClass="formtext" />
									</td>
                                  </tr>                           
								  <tr>
                                    <td class="col1" align="left" valign="top" style="padding:4px"><h:outputText id="text9" value="#{msgs.editmodulesections_instructions}" /></td>
                                    <td class="col2" align="left" valign="top" style="padding:4px">
										  <h:inputTextarea id="instr" cols="45" rows="5" value="#{editSectionPage.section.instr}" styleClass="formtext" />
									</td>
                                  </tr>
                                  <tr>
                                    <td class="col1" align="left" valign="top" style="padding:4px"> <h:outputText id="text10" value="#{msgs.editmodulesections_modality}" /><span class="required">*</span></td>
                                    <td class="col2" align="left" valign="top" style="padding:4px"><h:outputText id="text11" value="#{msgs.editmodulesections_message1} "/>
									</td>
									  </tr>	
								  <tr>
								  <td class="col1" style="padding:4px">&nbsp;</td>
                                    <td class="col2" valign="top" style="padding:4px">
                        			<h:selectBooleanCheckbox id="contentext" title="textualContent" value="#{editSectionPage.section.textualContent}" >
									</h:selectBooleanCheckbox>
									<h:outputText  id="text12" value="#{msgs.editmodulesections_textual_content}"/>
									</td>
									  </tr>	
								  <tr>
								    <td class="col1" style="padding:4px">&nbsp;</td>
                                    <td class="col2" valign="top" style="padding:4px">									
									<h:selectBooleanCheckbox id="contentvideo" title="videoContent" value="#{editSectionPage.section.videoContent}" >
									</h:selectBooleanCheckbox>
									<h:outputText  id="text13" value="#{msgs.editmodulesections_visual_content}"/>
									</td>
									  </tr>	
								  <tr>
								    <td class="col1" style="padding:4px">&nbsp;</td>
                                    <td class="col2" style="padding:4px" valign="top">
									<h:selectBooleanCheckbox id="contentaudio" title="audioContent" value="#{editSectionPage.section.audioContent}" >
									</h:selectBooleanCheckbox>
									<h:outputText id="text14" value="#{msgs.editmodulesections_auditory_content}"/>			
										</td>
									  </tr>	
									  <tr>
								  	  <td  class="col1" align="left" valign="middle" style="padding:4px"><h:outputText id="text15" value="#{msgs.editmodulesections_content_type}" rendered="#{editSectionPage.shouldRenderContentTypeSelect}" /></td>
                                 	  <td class="col2" style="padding:4px"> 
										<h:inputHidden id="contentType"  value="#{editSectionPage.section.contentType}"  />	  								  	
										  <h:selectOneMenu id="contentType1" value="#{editSectionPage.section.contentType}" valueChangeListener="#{editSectionPage.showHideContent}" onchange="this.form.submit();"  rendered="#{editSectionPage.shouldRenderContentTypeSelect}">
											<f:selectItems value="#{editSectionPage.allContentTypes}" />											
										 </h:selectOneMenu>
											 </td>
								   </tr>
								
									<tr> 
										 <td colspan="2" style="padding:4px">						 									
											 <f:subview id="contentEditorView" rendered="#{editSectionPage.shouldRenderEditor && authorPreferences.shouldRenderSferyx}">
												<%if (authorPreferencePage.isShouldRenderSferyx() && eSectionPage.getShouldRenderEditor())
                                               		   { %> 
                                           				<jsp:include page="contentSferyxEditor.jsp" />
													<% } %> 
											
     											 <h:inputHidden id="sferyxDisplay" value="#{authorPreferences.shouldRenderSferyx}" />
											</f:subview>

											<f:subview id="othereditor" rendered="#{editSectionPage.shouldRenderEditor && authorPreferences.shouldRenderFCK}">
												<sakai:inputRichText textareaOnly="false" id="otherMeletecontentEditor" value="#{editSectionPage.contentEditor}"  rows="50" cols="90" width="700" rendered="#{editSectionPage.shouldRenderEditor && authorPreferences.shouldRenderFCK}" collectionBase="#{editSectionPage.FCK_CollId}" />
											</f:subview>										

											</td>
									  </tr>	
								  <tr>
								   <td colspan="2" style="padding:4px">
												<f:subview id="ResourcePropertiesPanel" rendered="#{editSectionPage.meleteResource !=null && !editSectionPage.shouldRenderNotype}">
													<jsp:include page="edit_sec_resourcePropertiesPanel.jsp"/>
												</f:subview>
									</td>	
									</tr>																									
		                           </table>
                </td>
              </tr>
              <tr>
                <td style="padding:3px">
                  <div class="actionBar" align="left">
                	<h:commandButton id="FinishButton" action="#{editSectionPage.Finish}" value="#{msgs.im_done}" accesskey="#{msgs.done_access}" title="#{msgs.im_done_text}" styleClass="BottomImgFinish"/>
              		<h:commandButton id="submitsave" actionListener="#{editSectionPage.save}" value="#{msgs.im_save}" accesskey="#{msgs.save_access}" title="#{msgs.im_save_text}" styleClass="BottomImgSave" />
         			<h:commandButton id="preview" actionListener="#{editSectionPage.getPreviewPageListener}" value="#{msgs.im_preview}" accesskey="#{msgs.preview_access}" title="#{msgs.im_preview_text}" styleClass="BottomImgPreview" />
					<h:commandButton id="saveAddAnotherbutton"  actionListener="#{editSectionPage.saveAndAddAnotherSection}" value="#{msgs.im_add_another_section}" accesskey="#{msgs.add_access}" title="#{msgs.im_add_another_section_text}" styleClass="BottomImgAdd" />
				 </div></td>
              </tr>
              
            </table>
			<table class="maintableCollapseWithNoBorder">
			<tr>
				<td rowspan="2" class="left">
					<span class="required">*</span>&nbsp; <h:outputText value="#{msgs.edit_module_required}" />
				</td>
				<td align="right" class="footnoteDates">
					<h:outputText value="#{msgs.editmodulesections_author}"/>&nbsp;<h:outputText value="#{editSectionPage.createdByAuthor}" /><h:outputText value=","/>&nbsp;
                    <h:outputText value="#{editSectionPage.section.creationDate}" styleClass="italics"><o:convertDateTime /></h:outputText>
                             
				</td>
			</tr>
			<tr>
				<td align="right" class="footnoteDates">
					<h:outputText value="#{msgs.editmodulesections_author_edit}"/>&nbsp;<h:outputText value="#{editSectionPage.modifiedByAuthor}" /><h:outputText value=","/>&nbsp;
                    <h:outputText value="#{editSectionPage.section.modificationDate}"><o:convertDateTime /></h:outputText>
                 
                </td>
			</tr>
			<tr>
				<td align="right" class="footnoteDates" style="display:none">					
                    <h:outputText value="#{msgs.editmodulesections_author_edit_start}"/>&nbsp;<h:outputText id="editLastSaveTime" value="#{editSectionPage.lastSavedAt}"></h:outputText>
                </td>
			</tr>
		</table>	
	  
  </h:form>
	 
<script>
updateTime=600000;	
updateUrl= document.getElementById("EditSectionForm:activeCheck").value; 
scheduleUpdate();
</script>
  <!-- This Ends -->
</sakai:view>
</f:view>

