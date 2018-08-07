<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/webapp/melete/add_special_access.jsp $
 * $Id: add_special_access.jsp 3647 2012-12-02 22:30:41Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2010, 2011, 2012 Etudes, Inc.
 *
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
<sakai:view title="Modules: Add Special Access" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>


<%@ page import="org.sakaiproject.util.ResourceLoader"%>

<script language="JavaScript" src="/etudes-melete-tool/js/calendar2.js"></script>
<script language="javascript">
function newWindow(newContent){
  winContent = window.open(newContent, 'nextWin', 'right=0, top=20,width=750,height=600, toolbar=no,scrollbars=yes, resizable=no') }
</script>

<t:saveState id="sapsp" value="#{specialAccessPage.specialAccess}" />
<t:saveState id="spModId" value="#{specialAccessPage.moduleId}"/>
<t:saveState id="spSaList" value="#{specialAccessPage.saList}"/>
<h:form id="AddSpecialAccessForm">

	<f:subview id="top">
		<jsp:include page="topnavbar.jsp"/> 
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/access.png" alt="" width="16" height="16" align="absbottom"><h:outputText value="#{msgs.add_special_access}" /> </div>
   
	<table id="AutoNumber1" class="maintableCollapseWithBorder">
       <tr>
         <td>
          <h:outputText id="title" value="#{specialAccessPage.module.title}"/>
          <h:messages id="addspecialaccesserror" layout="table" showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	
         	<table class="maintableCollapseWithNoBorder" >
         	  <tr>
      			<td colspan="2" height="20" class="maintabledata2"></td>
     		 </tr>
         	  <tr>
                <td class="col1" align="left" valign="top"> <h:outputText value="#{msgs.add_special_access_names}" />
				 </td>
				 <td>
				 <h:selectManyListbox id="usersList" value="#{specialAccessPage.users}">
								<f:selectItems value="#{specialAccessPage.usersList}" />							
				</h:selectManyListbox>	 	
				</td>
              </tr>
			   
              <tr>
                <td class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_special_access_start_date}" /></td>
                <td>
                      <h:inputHidden id="accessId" value="#{specialAccessPage.specialAccess.accessId}"/>
                      <h:inputHidden id="overrideStart" value="#{specialAccessPage.specialAccess.overrideStart}"/>
					  <h:inputHidden id="overrideEnd" value="#{specialAccessPage.specialAccess.overrideEnd}"/>
					  <h:inputHidden id="overrideAllowUntil" value="#{specialAccessPage.specialAccess.overrideAllowUntil}"/>
					  <h:inputHidden id="modStartDate" value="#{specialAccessPage.startDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <h:inputHidden id="modEndDate" value="#{specialAccessPage.endDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <h:inputHidden id="modAllowUntilDate" value="#{specialAccessPage.allowUntilDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <h:inputHidden id="prevStartDate" value="#{specialAccessPage.specialAccess.startDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <h:inputHidden id="prevEndDate" value="#{specialAccessPage.specialAccess.endDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <h:inputHidden id="prevAllowUntilDate" value="#{specialAccessPage.specialAccess.allowUntilDate}">
					  <o:convertDateTime />
					  </h:inputHidden>
					  <a name="startCalender"></a> <h:inputText id="startDate" 
                           value="#{specialAccessPage.specialAccess.startDate}" size="22" styleClass="formtext" onchange="showInvalid('AddSpecialAccessForm:startDate','AddSpecialAccessForm:err_gifst');">
		        	      <o:convertDateTime />
        		    </h:inputText>
		            <h:outputLink id="viewsdateCal" onclick="showCal('AddSpecialAccessForm:startDate','8','0','AM');return false;" value="#startCalender" >
        	    		<h:graphicImage id="sdateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           			</h:outputLink>
                    <h:graphicImage id="err_gifst" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}"  style="visibility:hidden;" onclick="showHideTable('AddSpecialAccessForm:invalidMsgSt0','true')"  styleClass="ExpClass"/>
	          	    <h:panelGroup id="invalidMsgSt0" style="position:relative;z-index:1;visibility:hidden;display:none;" >
			        <h:panelGrid id="invalidMsgSt" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				      <h:column>
				  	  <h:outputText value="#{msgs.invalid_msg4}"  />  
				      </h:column>
				      <h:column>
					  <h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddSpecialAccessForm:invalidMsgSt0','false')" />
				      </h:column>
			        </h:panelGrid>
			        </h:panelGroup>	           			
					 </td>
              </tr>
			  
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_special_access_end_date}" /></td>
                <td>
                <a name="endCalender"></a><h:inputText id="endDate" 
                           value="#{specialAccessPage.specialAccess.endDate}" size="22" styleClass="formtext" onchange="showInvalid('AddSpecialAccessForm:endDate','AddSpecialAccessForm:err_gifen');">
             			  <o:convertDateTime />
          		 </h:inputText>
          <h:outputLink id="viewedateCal" onclick="showCal('AddSpecialAccessForm:endDate','11','59','PM');return false;" value="#endCalender">
            <h:graphicImage id="edateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           </h:outputLink>
           <h:graphicImage id="err_gifen" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('AddSpecialAccessForm:invalidMsgEn0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgEn0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			     <h:panelGrid id="invalidMsgEn" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				 <h:column>
				  	<h:outputText value="#{msgs.invalid_msg5}"  />  
				 </h:column>
				 <h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddSpecialAccessForm:invalidMsgEn0','false')" />
				 </h:column>
			     </h:panelGrid>
			     </h:panelGroup>
			 </td>
              </tr>			  
             	
              <tr>
                <td  class="col1" align="left" valign="top"><h:outputText value="#{msgs.add_special_access_allowuntil_date}" /></td>
                <td>
                <a name="allowUntilCalender"></a><h:inputText id="allowUntilDate" 
                           value="#{specialAccessPage.specialAccess.allowUntilDate}" size="22" styleClass="formtext" onchange="showInvalid('AddSpecialAccessForm:allowUntilDate','AddSpecialAccessForm:err_gifallowuntil');">
             			  <o:convertDateTime />
          		 </h:inputText>
          <h:outputLink id="viewaudateCal" onclick="showCal('AddSpecialAccessForm:allowUntilDate','11','59','PM');return false;" value="#allowUntilCalender">
            <h:graphicImage id="audateCal"  value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="DatePickerClass"/>
           </h:outputLink>
           <h:graphicImage id="err_gifallowuntil" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('AddSpecialAccessForm:invalidMsgEn0','true')"  styleClass="ExpClass"/>
	             <h:panelGroup id="invalidMsgEn0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			     <h:panelGrid id="invalidMsgEn" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="250px" styleClass="invalidAlertSmall" >   
				 <h:column>
				  	<h:outputText value="#{msgs.invalid_msg5}"  />  
				 </h:column>
				 <h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('AddSpecialAccessForm:invalidMsgEn0','false')" />
				 </h:column>
			     </h:panelGrid>
			     </h:panelGroup>
			 </td>
              </tr>		
		</table>
  		<div class="actionBar" align="left">
          	<h:commandButton action="#{specialAccessPage.addSpecialAccess}" value="#{msgs.im_done}" accesskey="#{msgs.done_access}" title="#{msgs.im_done_text}" styleClass="BottomImgReturn"/>
			<h:commandButton id="cancelButton" immediate="true" action="#{specialAccessPage.cancel}" value="#{msgs.im_cancel}" accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel"/>
	       </div>
        </td></tr>		
</table>
<p ><span class="required">*</span>&nbsp;<h:outputText value="#{msgs.edit_module_required}" /></p>
	<!-- here -->	
	<script type="text/javascript">
        if (document.getElementById("AddSpecialAccessForm:accessId").value != "0")
        {
            if (document.getElementById("AddSpecialAccessForm:overrideStart").value != "true")
            {
                document.getElementById("AddSpecialAccessForm:startDate").value = document.getElementById("AddSpecialAccessForm:modStartDate").value;
                document.getElementById("AddSpecialAccessForm:prevStartDate").value = document.getElementById("AddSpecialAccessForm:modStartDate").value;
            }    
        	if (document.getElementById("AddSpecialAccessForm:overrideEnd").value != "true")
        	{	
        		document.getElementById("AddSpecialAccessForm:endDate").value = document.getElementById("AddSpecialAccessForm:modEndDate").value;
        		document.getElementById("AddSpecialAccessForm:prevEndDate").value = document.getElementById("AddSpecialAccessForm:modEndDate").value;
        	}
        	if (document.getElementById("AddSpecialAccessForm:overrideAllowUntil").value != "true")
        	{	
        		document.getElementById("AddSpecialAccessForm:allowUntilDate").value = document.getElementById("AddSpecialAccessForm:modAllowUntilDate").value;
        		document.getElementById("AddSpecialAccessForm:prevAllowUntilDate").value = document.getElementById("AddSpecialAccessForm:modAllowUntilDate").value;
        	}
        }              
    </script>    
	</h:form>
</sakai:view>
</f:view>

