<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/edit_module.jsp $
 * $Id: edit_module.jsp 85951 2014-03-14 16:53:27Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012 Etudes, Inc.
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
<%@ taglib uri="date-time-converter" prefix="o" %>


<f:view>
<sakai:view title="Modules: Edit Module" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>

<%@ page import="org.sakaiproject.util.ResourceLoader"%>


<script language="JavaScript" src="/etudes-melete-tool/js/calendar2.js"></script>
<script language="javascript">
function newWindow(newContent){
  winContent = window.open(newContent, 'nextWin', 'right=0, top=20,width=750,height=600, toolbar=no,scrollbars=yes, resizable=no') }
</script>
<t:saveState id="empfirsec" value="#{editModulePage.firstSection}" />
<t:saveState id="empmsh" value="#{editModulePage.moduleShdates}" />
 <h:form id="EditModuleForm">
 <h:inputHidden id="modid" value="#{editModulePage.module.moduleId}"/>
 	<f:subview id="top">
		<jsp:include page="topnavbar.jsp"/>
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/document_add.gif" alt="" width="16" height="16" align="absbottom"> <h:outputText value="#{msgs.edit_module_editing_module}" /> </div>
    <h:inputHidden id="formName" value="EditModuleForm"/>  
    <h:messages id="editmoduleerror" layout="table" showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>

	<table class="maintableCollapseWithBorder" id="AutoNumber1" >
         <tr>
          <td valign="top">
		  	<table class="maintableCollapseWithNoBorder">
		   	  <tr>
		 		<td colspan="2" height="20" class="maintabledata2"> 
					<h:commandLink id="TOCButton"  action="#{editModulePage.gotoTOC}" styleClass="toolUiLink">
						<h:outputText id="toc" value="#{msgs.edit_module_TOC}" />
					</h:commandLink> &raquo;  <h:outputText value="#{editModulePage.module.title}" /> &raquo;
					<h:commandLink id="editFirstSection" actionListener="#{editModulePage.editSection}" rendered="#{editModulePage.hasSections}" styleClass="toolUiLink">						
					     <h:outputText id="editSectionText" value="#{msgs.edit_module_edit_sections}"/>				     
					 </h:commandLink> 	
					<h:outputText id="editSectionText_1" value=" / " rendered="#{editModulePage.hasSections}" />
				  	<h:commandLink id="addSection" actionListener="#{editModulePage.addContentSections}" styleClass="toolUiLink">
					   <h:outputText id="addSectionText" value="#{msgs.edit_module_add_content_sections}"/>
				  </h:commandLink> 				  
			 	</td>
	  		 </tr>
              <tr>
                <td  class="col1" align="left" valign="top"> <h:outputText value="#{msgs.edit_module_module_title}" /> <span class="required">*</span>  </td>
                <td  class="col2" align="left" valign="top">  
						<h:inputText id="title" size="45" value="#{editModulePage.module.title}" styleClass="formtext" required="true" validator="#{editModulePage.validateField}"/>											
				</td>
              </tr>
             
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.edit_module_descr_over_object}" /> </td>
                <td  class="col2" align="left" valign="top">
				<h:inputTextarea id="description" cols="45" rows="5" value="#{editModulePage.module.description}" styleClass="formtext" validator="#{editModulePage.validateField}" />
		
				</td>
              </tr>
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.edit_module_keywords}" />  
                </td>
                <td  class="col2" align="left" valign="top">
				<h:inputTextarea id="keywords" cols="45" rows="3" value="#{editModulePage.module.keywords}" styleClass="formtext" validator="#{editModulePage.validateField}" />
							
				</td>
              </tr>
       	 	  <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.edit_module_start_date}" /></td>
                <td  class="col2" align="left" valign="top">					
					  <a name="startCalender"></a><h:inputText id="startDate" 
                           value="#{editModulePage.moduleShdates.startDate}" size="22" styleClass="formtext" onchange="showInvalid('EditModuleForm:startDate','EditModuleForm:err_gifst');">
		        	      <o:convertDateTime />
        		    </h:inputText>
		            <h:outputLink id="viewsdateCal" onclick="showCal('EditModuleForm:startDate','8','0','AM');return false;" value="#startCalender">
        	    		<h:graphicImage id="sdateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           			</h:outputLink>
           			<h:graphicImage id="err_gifst" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}"  style="visibility:hidden;" onclick="showHideTable('EditModuleForm:invalidMsgSt0','true')"  styleClass="ExpClass"/>
	          	    <h:panelGroup id="invalidMsgSt0" style="position:relative;z-index:1;visibility:hidden;display:none;" >
			        <h:panelGrid id="invalidMsgSt" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				      <h:column>
				  	  <h:outputText value="#{msgs.invalid_msg4}"  />  
				      </h:column>
				      <h:column>
					  <h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('EditModuleForm:invalidMsgSt0','false')" />
				      </h:column>
			        </h:panelGrid>
			        </h:panelGroup>	
					 </td>
              </tr>
			  <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.edit_module_end_date}" /></td>
                <td  class="col2" align="left" valign="top">
					 <a name="endCalender"></a> <h:inputText id="endDate" 
                           value="#{editModulePage.moduleShdates.endDate}"  size="22" styleClass="formtext" onchange="showInvalid('EditModuleForm:endDate','EditModuleForm:err_gifen');">
               <o:convertDateTime />
            </h:inputText>
          <h:outputLink id="viewedateCal" onclick="showCal('EditModuleForm:endDate','11','59','PM');return false;" value="#endCalender" styleClass="toolUiLink">
            <h:graphicImage id="edateCal"  value="/images/date.png"  alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           </h:outputLink>
           <h:graphicImage id="err_gifen" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('EditModuleForm:invalidMsgEn0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgEn0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			     <h:panelGrid id="invalidMsgEn" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				 <h:column>
				  	<h:outputText value="#{msgs.invalid_msg5}"  />  
				 </h:column>
				 <h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('EditModuleForm:invalidMsgEn0','false')" />
				 </h:column>
			     </h:panelGrid>
			     </h:panelGroup>	           
					 </td>
              </tr>
                <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_module_allowuntil_date}" /></td>
                <td  class="col2" align="left" valign="top">
				<a name="allowUntilCalender"></a>
				<h:inputText id="allowUntilDate" 
                           value="#{editModulePage.moduleShdates.allowUntilDate}" size="22" styleClass="formtext" onchange="showInvalid('EditModuleForm:allowUntilDate','EditModuleForm:err_gifal');">
             			  <o:convertDateTime />
          		 </h:inputText>
          		 <h:outputLink id="viewallowdateCal" onclick="showCal('EditModuleForm:allowUntilDate','11','59','PM');return false;" value="#allowUntilCalender" styleClass="toolUiLink">
           			 <h:graphicImage id="allowdateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
          		 </h:outputLink>
                 <h:graphicImage id="err_gifal" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('EditModuleForm:invalidMsgAllow0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgAllow0" style="position:relative;z-index:1;visibility:hidden;display:none;">
				     <h:panelGrid id="invalidMsgAllow" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
						 <h:column>
						  	<h:outputText value="#{msgs.invalid_msg6}"  />  
						 </h:column>
						 <h:column>
							<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('EditModuleForm:invalidMsgAllow0','false')" />
						 </h:column>
			    	 </h:panelGrid>
			     </h:panelGroup>	
				</td>
              </tr>			
              <tr>
                <td  class="col1">&nbsp;</td>
                <td  class="col2" valign="top">
                 <h:selectBooleanCheckbox id="addtoschedule" title="addtoSchedule" value="#{editModulePage.moduleShdates.addtoSchedule}" rendered="#{editModulePage.calendarFlag}">
		         </h:selectBooleanCheckbox>
		         <h:outputText id="addtoScheduleTxt" value="#{msgs.edit_module_schedule}" rendered="#{editModulePage.calendarFlag}"/>
                </td>
              </tr>  
          		
     </table>	
 	<div class="actionBar" align="left">
 	  <h:commandButton id="returnButton"  action="#{editModulePage.goDone}" value="#{msgs.im_done}" tabindex="" accesskey="#{msgs.done_access}" title="#{msgs.im_done_text}" styleClass="BottomImgReturn" />
  	  <h:commandButton id="submitsave" action="#{editModulePage.save}" value="#{msgs.im_save}" tabindex="" accesskey="#{msgs.save_access}" title="#{msgs.im_save_text}" styleClass="BottomImgSave"/>
  	  <h:commandButton id="sectionButton" actionListener="#{editModulePage.addContentSections}" value="#{msgs.im_add_content_sections}" tabindex="" accesskey="#{msgs.add_access}" title="#{msgs.im_add_content_sections_text}" styleClass="BottomImgAdd"/>
  	  <h:commandButton id="cancelButton" immediate="true" action="#{editModulePage.cancel}" value="#{msgs.im_cancel}" tabindex="" accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel"/>
  	</div>
   </td>
  </tr>
</table>
<table class="maintableCollapseWithNoBorder">
	<tr>
		<td rowspan="2" class="left">
			<span class="required">*</span>&nbsp; <h:outputText value="#{msgs.edit_module_required}" />
		</td>
		<td align="right" class="footnoteDates">
			<h:outputText value="#{msgs.edit_module_created_by}"/> <h:outputText value="#{editModulePage.createdAuthor}"/><h:outputText value=","/>&nbsp;
			<h:outputText value="#{editModulePage.module.creationDate}"><o:convertDateTime /></h:outputText>
		</td>
	</tr>
	<tr>
		<td align="right" class="footnoteDates">
			<h:outputText value="#{msgs.edit_module_modified_by}"/> <h:outputText value="#{editModulePage.modifiedAuthor}"/><h:outputText value=","/>&nbsp;
			<h:outputText value="#{editModulePage.module.modificationDate}"><o:convertDateTime /></h:outputText>
		</td>
	</tr>
</table>
  </h:form>
</sakai:view>
</f:view>
