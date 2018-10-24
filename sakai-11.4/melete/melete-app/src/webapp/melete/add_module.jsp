<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/add_module.jsp $
 * $Id: add_module.jsp 85951 2014-03-14 16:53:27Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010, 2011, 2012 Etudes, Inc.
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
<%@ taglib uri="date-time-converter" prefix="o" %>

<f:view>
<sakai:view title="Modules: Add Module" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>


<%@ page import="org.sakaiproject.util.ResourceLoader"%>


<script language="JavaScript" src="/etudes-melete-tool/js/calendar2.js"></script>
<script language="javascript">
function newWindow(newContent){
  winContent = window.open(newContent, 'nextWin', 'right=0, top=20,width=750,height=600, toolbar=no,scrollbars=yes, resizable=no') }
</script>

<h:form id="AddModuleForm">
	<f:subview id="top">
		<jsp:include page="topnavbar.jsp"/> 
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/document_add.gif" alt="" width="16" height="16" align="absbottom"><h:outputText value="#{msgs.add_module_adding_module}" /> </div>
    <h:messages id="addmoduleerror" layout="table" showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	
	<table id="AutoNumber1" class="maintableCollapseWithBorder">
       <tr>
         <td>
         	<table class="maintableCollapseWithNoBorder" >
         	  <tr>
      			<td colspan="2" height="20" class="maintabledata2"> <h:outputText value="#{msgs.add_module_define_properties}" /> 	</td>
     		 </tr>
         	  <tr>
                <td class="col1" align="left" valign="top"> <h:outputText value="#{msgs.add_module_module_title}" /> <span class="required">*</span></td>
                <td  class="col2" align="left" valign="top">
					<h:inputText id="title" size="45" value="#{addModulePage.module.title}" required="true" styleClass="formtext" validator="#{addModulePage.validateField}" />
				</td>
              </tr>
			 
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_module_descr_over_object}" /> </td>
                <td  class="col2" align="left" valign="top">
					<h:inputTextarea id="description" cols="45" rows="5" value="#{addModulePage.module.description}" styleClass="formtext" validator="#{addModulePage.validateField}" />
				</td>
              </tr>
			  
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_module_keywords}" />				
                 </td>
                <td  class="col2" align="left" valign="top">
					<h:inputTextarea id="keywords" cols="45" rows="3" value="#{addModulePage.module.keywords}"  styleClass="formtext" validator="#{addModulePage.validateField}" />
				</td>
              </tr>
		     <tr>
                <td class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_module_start_date}" />
				</td>
                <td  class="col2" align="left" valign="top">
					  <a name="startCalender" class="toolUiLink"></a> <h:inputText id="startDate" 
                           value="#{addModulePage.moduleShdates.startDate}" size="22" styleClass="formtext" onchange="showInvalid('AddModuleForm:startDate','AddModuleForm:err_gifst');">
		        	      <o:convertDateTime />
        		    </h:inputText>
		            <h:outputLink id="viewsdateCal" onclick="showCal('AddModuleForm:startDate','8','0','AM');return false;" value="#startCalender" styleClass="toolUiLink">
        	    		<h:graphicImage id="sdateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           			</h:outputLink>
           			<h:graphicImage id="err_gifst" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}"  style="visibility:hidden;" onclick="showHideTable('AddModuleForm:invalidMsgSt0','true')"  styleClass="ExpClass"/>
	          	    <h:panelGroup id="invalidMsgSt0" style="position:relative;z-index:1;visibility:hidden;display:none;" >
			        <h:panelGrid id="invalidMsgSt" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				      <h:column>
				  	  <h:outputText value="#{msgs.invalid_msg4}"  />  
				      </h:column>
				      <h:column>
					  <h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddModuleForm:invalidMsgSt0','false')" />
				      </h:column>
			        </h:panelGrid>
			        </h:panelGroup>		
					 </td>
              </tr>
			  
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_module_end_date}" /></td>
                <td  class="col2" align="left" valign="top">
				<a name="endCalender" class="toolUiLink"></a><h:inputText id="endDate" 
                           value="#{addModulePage.moduleShdates.endDate}" size="22" styleClass="formtext" onchange="showInvalid('AddModuleForm:endDate','AddModuleForm:err_gifen');">
             			  <o:convertDateTime />
          		 </h:inputText>
          <h:outputLink id="viewedateCal" onclick="showCal('AddModuleForm:endDate','11','59','PM');return false;" value="#endCalender" styleClass="toolUiLink">
            <h:graphicImage id="edateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           </h:outputLink>
                 <h:graphicImage id="err_gifen" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('AddModuleForm:invalidMsgEn0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgEn0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			     <h:panelGrid id="invalidMsgEn" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				 <h:column>
				  	<h:outputText value="#{msgs.invalid_msg5}"  />  
				 </h:column>
				 <h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddModuleForm:invalidMsgEn0','false')" />
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
                           value="#{addModulePage.moduleShdates.allowUntilDate}" size="22" styleClass="formtext" onchange="showInvalid('AddModuleForm:allowUntilDate','AddModuleForm:err_gifal');">
             			  <o:convertDateTime />
          		 </h:inputText>
          		 <h:outputLink id="viewallowdateCal" onclick="showCal('AddModuleForm:allowUntilDate','11','59','PM');return false;" value="#allowUntilCalender" styleClass="toolUiLink">
           			 <h:graphicImage id="allowdateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
          		 </h:outputLink>
                 <h:graphicImage id="err_gifal" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('AddModuleForm:invalidMsgAllow0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgAllow0" style="position:relative;z-index:1;visibility:hidden;display:none;">
				     <h:panelGrid id="invalidMsgAllow" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
						 <h:column>
						  	<h:outputText value="#{msgs.invalid_msg6}"  />  
						 </h:column>
						 <h:column>
							<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddModuleForm:invalidMsgAllow0','false')" />
						 </h:column>
			    	 </h:panelGrid>
			     </h:panelGroup>	
				</td>
              </tr>			  
              <tr>
                <td  class="col1">&nbsp;</td>
                <td  class="col2" valign="top">
                 <h:selectBooleanCheckbox id="addtoschedule" title="addtoSchedule" value="#{addModulePage.moduleShdates.addtoSchedule}" rendered="#{addModulePage.calendarFlag}">
		         </h:selectBooleanCheckbox>
		         <h:outputText id="addtoScheduleTxt" value="#{msgs.add_module_schedule}" rendered="#{addModulePage.calendarFlag}"/>
			</td>
		  </tr>
		  </td></tr>		
		</table>
  		<div class="actionBar" align="left">
          	<h:commandButton action="#{addModulePage.save}" value="#{msgs.im_add_button}" accesskey="#{msgs.add_access}" title="#{msgs.im_add_button_text}" styleClass="BottomImgAdd"/>
			<h:commandButton id="sectionButton"  actionListener="#{addModulePage.addContentSections}" value="#{msgs.im_add_content_sections}" tabindex="" accesskey="#{msgs.add_access}" title="#{msgs.im_add_content_sections_text}" styleClass="BottomImgAdd" />
			<h:commandButton id="cancelButton" immediate="true" action="#{addModulePage.cancel}" value="#{msgs.im_cancel}" accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel"/>
	       </div>
        </td></tr>		
</table>
<p ><span class="required">*</span>&nbsp;<h:outputText value="#{msgs.edit_module_required}" /></p>
	<!-- here -->	
	</h:form>
</sakai:view>
</f:view>

