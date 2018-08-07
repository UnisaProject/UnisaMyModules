<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/webapp/melete/list_auth_modules.jsp $
 * $Id: list_auth_modules.jsp 80587 2012-07-02 17:53:22Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2012 Etudes, Inc.
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
<%@ taglib uri="date-time-converter" prefix="o" %>

<f:view>
<sakai:view title="Modules: Author View" toolCssHref="/etudes-melete-tool/rtbc004.css">

<%@include file="accesscheck.jsp" %>

<%
/*The following piece of java code needs to stay above the loadBundle tag, otherwise the request attribute gets replaced by msg*/

String msg = null;
if (request.getAttribute("msg") != null)
{	
  msg = (String) request.getAttribute("msg");	
}  
%>
<%@ page import="javax.faces.application.FacesMessage, java.util.Iterator, java.lang.String, org.sakaiproject.util.ResourceLoader"%>


<script type="text/javascript" language="JavaScript" src="/etudes-melete-tool/js/calendar2.js"></script>
<script type="text/javascript" language="javascript" src="/etudes-melete-tool/js/sharedscripts.js"></script>

<script type="text/javascript" language="javascript">
function selectAll()
{
  var listSizeStr = "listauthmodulesform:listSize";
  var listSizeVal = document.getElementById(listSizeStr).value;
  if (document.getElementById("listauthmodulesform:table:allmodcheck") != null)
  {	  
  if (document.getElementById("listauthmodulesform:table:allmodcheck").checked == true)
  {	  
    for (i=0;i<parseInt(listSizeVal);i++)
    {
	  var modchStr = "listauthmodulesform:table:"+i+":modCheck";
	  if (document.getElementById(modchStr).checked == false)
	  {	  
	    document.getElementById(modchStr).checked=true;
	  }  	  
    }
  } 
  else
  {	  
	  resetCheck();
   } 	   	  
  }
}

function resetCheck()
{
	 var inputs = document.getElementsByTagName("input");
	  for (var i = 0; i < inputs.length; i++) {   
		  if (inputs[i].type == "checkbox") {   
		  inputs[i].checked = false;
		  }
	  }	  
}

function resetAllMod()
{
	if (document.getElementById("listauthmodulesform:table:allmodcheck") != null)
	{	
	  document.getElementById("listauthmodulesform:table:allmodcheck").checked=false;
	}
}

</script>

<h:form id="listauthmodulesform">
<h:inputHidden id="lamexp" value="#{listAuthModulesPage.expandAllFlag}" />
<h:inputHidden id="lamshmodid" value="#{listAuthModulesPage.showModuleId}"/>
<%

if (msg != null)
{	
  final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
  FacesMessage facesMsg = new FacesMessage(null, msg);
  facesMsg.setSeverity(FacesMessage.SEVERITY_INFO);
  facesContext.addMessage(null, facesMsg);		
}  
%>
	<f:subview id="top">
		<jsp:include page="topnavbar.jsp?myMode=Author"/> 
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/pen_red.gif" alt="" width="16" height="16" align="absbottom"><h:outputText value="#{msgs.list_auth_modules_authoring_options}" /> </div>
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	
	<div class="right">
	     <h:commandLink id="lastVisitedLink" actionListener="#{bookmarkPage.editSection}" action="#{bookmarkPage.redirectEditSection}" rendered="#{listModulesPage.bookmarkSectionId > 0}">
	      <f:param name="sectionId" value="#{listModulesPage.bookmarkSectionId}" /> 
	      <h:graphicImage id="lvisit_gif" value="/images/last-visited.png" alt="" styleClass="AuthImgClass"/>
	      <h:outputText id="lastvisit" value="#{msgs.last_visited}" />									
	     </h:commandLink>
	     <h:outputText value="|" rendered="#{listModulesPage.bookmarkSectionId > 0}"/> 
	     <h:commandLink id="myBookmarksLink" action="#{bookmarkPage.gotoMyBookmarks}">
	     <f:param name="fromPage" value="list_auth_modules"/>	
	     <h:graphicImage id="mybook_gif" value="/images/my-bookmarks.png" alt="" styleClass="AuthImgClass"/>
           <h:outputText id="mybks" value="#{msgs.my_bookmarks}" />				
         </h:commandLink>		  
   	</div>
 <table width="100%" class="maintableCollapseWithBorder">
  <tr>
   <td>
 	<f:subview id="authtop">
		<jsp:include page="authnavbar.jsp"/> 
	</f:subview>
  </td>
 </tr>
 </table>
 <table width="100%" border="0">
 <tr>
   <td>
   	<h:outputText id="nomodmsg" value="#{msgs.list_auth_modules_no_modules_available}" rendered="#{listAuthModulesPage.nomodsFlag == null || listAuthModulesPage.nomodsFlag}" styleClass="left"/>
    
   <h:dataTable id="table"
                  value="#{listAuthModulesPage.moduleDateBeans}"
                  var="mdbean"  headerClass="tableheader" rowClasses="row1,row2" columnClasses="ListModCheckClass,ListModCheckClass,ListTitleClass,ListAuthorDateClass,ListAuthorDateClass,ListAuthorDateClass,ListActionClass" 
                  rendered="#{listAuthModulesPage.nomodsFlag != null && !listAuthModulesPage.nomodsFlag}" width="100%" 
				  binding="#{listAuthModulesPage.table}" styleClass="mainListTableCollapseWithBorder0" summary="#{msgs.list_auth_modules_summary}">
  
     <h:column>   
	     <f:facet name="header">
	       <h:outputText value="&nbsp;" escape="false"/>
	     </f:facet>  
	     	    <h:graphicImage id="err_gif" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" rendered="#{mdbean.moduleShdate.dateFlag || mdbean.sectionBeans == null}" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsg0','true')"  styleClass="ExpClass"/>
	          
		   <h:panelGroup id="invalidMsg0" style="position:relative;z-index:1;visibility:hidden;display:none;" rendered="#{mdbean.moduleShdate.dateFlag || mdbean.sectionBeans == null}" >
			 <h:panelGrid id="invalidMsg" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="390px" styleClass="invalidAlert" >   
				<h:column>
				  	<h:outputText value="#{msgs.invalid_msg3}" rendered="#{mdbean.moduleShdate.dateFlag && mdbean.sectionBeans == null}" />  
					<h:outputText value="#{msgs.invalid_msg}" rendered="#{mdbean.moduleShdate.dateFlag && mdbean.sectionBeans != null}" />
					<h:outputText value="#{msgs.invalid_msg2}" rendered="#{mdbean.sectionBeans == null && !mdbean.moduleShdate.dateFlag}" />						
				</h:column>
				<h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsg0','false')" />
				</h:column>
			  </h:panelGrid>
			</h:panelGroup>				 
    </h:column>                                  
    <h:column>
    <f:facet name="header">
    <h:panelGroup>
       <h:selectBooleanCheckbox id="allmodcheck" value="#{listAuthModulesPage.selectAllFlag}" onclick="selectAll()" valueChangeListener="#{listAuthModulesPage.selectAllModules}"  rendered="#{listAuthModulesPage.nomodsFlag == false}"/>   
    </h:panelGroup> 
     </f:facet> 
     
	   <h:selectBooleanCheckbox title="#{mdbean.module.moduleIdStr}" id="modCheck" value="#{mdbean.selected}" onclick="resetAllMod()" valueChangeListener="#{listAuthModulesPage.selectedModule}" />
     
    </h:column>               
   <h:column>
 	<f:facet name="header">
 	 <h:panelGroup>
      <h:commandLink id="expandCollapseAction" action="#{listAuthModulesPage.expandCollapseAction}">
     	<h:graphicImage id="exp_all_gif" alt="#{msgs.list_auth_modules_authoring_expand_all}" title="#{msgs.list_auth_modules_authoring_expand}" value="/images/expand-collapse.gif"   rendered="#{listAuthModulesPage.expandAllFlag != listAuthModulesPage.trueFlag}" styleClass="ExpClass"/>
        <h:graphicImage id="col_all_gif" alt="#{msgs.list_auth_modules_authoring_collapse_all}" title="#{msgs.list_auth_modules_authoring_collapse}" value="/images/collapse-expand.gif"   rendered="#{listAuthModulesPage.expandAllFlag == listAuthModulesPage.trueFlag}" styleClass="ExpClass"/>
      </h:commandLink>  
      <h:outputText id="t2" value="#{msgs.list_auth_modules_title}" />
     </h:panelGroup>        
    </f:facet>					
     <h:commandLink id="showHideSections" action="#{listAuthModulesPage.showHideSections}">
        <h:graphicImage id="exp_gif" alt="#{msgs.list_auth_modules_authoring_expand}" value="/images/expand.gif" rendered="#{((mdbean.moduleId != listAuthModulesPage.showModuleId)&&(mdbean.sectionBeans != listAuthModulesPage.nullList)&&(listAuthModulesPage.expandAllFlag != listAuthModulesPage.trueFlag))}" styleClass="ExpClass"/>
        <h:graphicImage id="col_gif" alt="#{msgs.list_auth_modules_authoring_collapse}" value="/images/collapse.gif" rendered="#{(((mdbean.moduleId == listAuthModulesPage.showModuleId)&&(mdbean.sectionBeans != listAuthModulesPage.nullList))||((listAuthModulesPage.expandAllFlag == listAuthModulesPage.trueFlag)&&(mdbean.sectionBeans != listAuthModulesPage.nullList)))}" styleClass="ExpClass"/>
     </h:commandLink>         
      <h:outputText id="mod_seq" value="#{mdbean.cmod.seqNo}. " />      
      <h:commandLink id="editMod" actionListener="#{listAuthModulesPage.editModule}">     
         <f:param name="editmodid" value="#{mdbean.moduleId}" />
         <h:outputText id="title2" value="#{mdbean.module.title}" />
      </h:commandLink>
      <h:dataTable id="tablesec" rendered="#{((mdbean.moduleId == listAuthModulesPage.showModuleId)||(listAuthModulesPage.expandAllFlag == listAuthModulesPage.trueFlag))}"
                  value="#{mdbean.sectionBeans}" cellpadding="2" border="0" 
                  var="sectionBean" rowClasses="#{mdbean.rowClasses}" width="95%" binding="#{listAuthModulesPage.secTable}" summary="#{msgs.list_auth_modules_sections_summary}">
               <h:column>
              <h:inputHidden id="hacksecid" value="#{sectionBean.section.sectionId}"/>
             
               <h:selectBooleanCheckbox id="secCheck" value="#{sectionBean.selected}" valueChangeListener="#{listAuthModulesPage.selectedSection}"/> 
               
               <h:outputText id="disp_seq" value="#{sectionBean.displaySequence}. " />
                             
              <h:commandLink id="editSec" actionListener="#{listAuthModulesPage.editSection}">
               <f:param name="editsecmodid" value="#{mdbean.moduleId}" />  
               <f:param name="sectionId" value="#{sectionBean.section.sectionId}" /> 
              <h:outputText id="sectitle" value="#{sectionBean.section.title}">
                </h:outputText>
              </h:commandLink>
            </h:column>
          </h:dataTable>     
        </h:column>      
       <h:column>   
         <f:facet name="header">
             <h:outputText id="t4" value="#{msgs.list_auth_modules_start_date}" />
          </f:facet> 
	       <h:panelGrid border="0" cellpadding="2px" width="100%" style="vertical-align:top;white-space:nowrap;">	       
	       <h:column>
	            <h:inputText id="startDate" size="24" style="font-size:95%;"
	                           value="#{mdbean.moduleShdate.startDate}" onchange="showInvalid('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':startDate','listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':err_gifst');">
	            	<o:convertDateTime />
	            </h:inputText>
	            <h:outputLink id="viewsdateCal" onclick="showCal('listauthmodulesform:table:'+#{listAuthModulesPage.table.rowIndex}+':startDate','8','0','AM');return false;" value="#">
	            	<h:graphicImage id="sdateCal" value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="ListDatePickerClass"/>
	           </h:outputLink> 	
	           <h:graphicImage id="err_gifst" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}"  style="visibility:hidden;" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgSt0','true')"  styleClass="ExpClass"/>
	          
			   <h:panelGroup id="invalidMsgSt0" style="position:relative;z-index:1;visibility:hidden;display:none;" >
				 <h:panelGrid id="invalidMsgSt" columns="1" border="0" bgcolor="#FFFFCC" width="250px" styleClass="invalidAlertSmall" >   
					<h:column>
					  	<h:outputText value="#{msgs.invalid_msg4}"  />  
					</h:column>
					<h:column>
						<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgSt0','false')" />
					</h:column>
				  </h:panelGrid>
				</h:panelGroup>		
	       </h:column>           		
	      </h:panelGrid> 	
       </h:column>  
       <h:column>
      		<f:facet name="header">
             <h:outputText id="t5" value="#{msgs.list_auth_modules_end_date}" />
          </f:facet> 
        <h:panelGrid border="0" cellpadding="2px" width="100%" style="vertical-align:top;white-space:nowrap;">	       
       <h:column>      
            <h:inputText id="endDate" size="24" style="font-size:95%;"
                           value="#{mdbean.moduleShdate.endDate}" onchange="showInvalid('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':endDate','listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':err_gifen');">
               <o:convertDateTime />
            </h:inputText>
             <h:outputLink id="viewedateCal" onclick="showCal('listauthmodulesform:table:'+#{listAuthModulesPage.table.rowIndex}+':endDate','11','59','PM');return false;" value="#">
            <h:graphicImage id="edateCal" value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="ListDatePickerClass"/>
           </h:outputLink>
           <h:graphicImage id="err_gifen" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgEn0','true')"  styleClass="ExpClass"/>
	          
		   <h:panelGroup id="invalidMsgEn0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			 <h:panelGrid id="invalidMsgEn" columns="1" border="0" bgcolor="#FFFFCC" width="250px" styleClass="invalidAlertSmall" >   
				<h:column>
				  	<h:outputText value="#{msgs.invalid_msg5}"  />  
				</h:column>
				<h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgEn0','false')" />
				</h:column>
			  </h:panelGrid>
			</h:panelGroup>	             
         </h:column>           		
	     </h:panelGrid> 	
       </h:column>    
       <h:column> 
      	<f:facet name="header">
             <h:outputText id="t6" value="#{msgs.list_auth_modules_allow_date}" />
          </f:facet> 
       <h:panelGrid border="0" cellpadding="2px" width="100%" style="vertical-align:top;white-space:nowrap;">	       
       <h:column>
	        <h:inputText id="allowUntilDate" size="24" style="font-size:95%;"
	                       value="#{mdbean.moduleShdate.allowUntilDate}" onchange="showInvalid('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':allowUntilDate','listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':err_gifal');">
	           <o:convertDateTime />
	        </h:inputText>
             <h:outputLink id="viewadateCal" onclick="showCal('listauthmodulesform:table:'+#{listAuthModulesPage.table.rowIndex}+':allowUntilDate','11','59','PM');return false;" value="#">
          		  <h:graphicImage id="adateCal" value="/images/date.png" alt="#{msgs.list_auth_modules_alt_popup_cal}" title="#{msgs.list_auth_modules_alt_popup_cal}" styleClass="ListDatePickerClass"/>
          	 </h:outputLink>
	        <h:graphicImage id="err_gifal" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" style="visibility:hidden;" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgAl0','true')"  styleClass="ExpClass"/>
		          
		    <h:panelGroup id="invalidMsgAl0" style="position:relative;z-index:1;visibility:hidden;display:none;">
			 <h:panelGrid id="invalidMsgAl" columns="1" border="0" bgcolor="#FFFFCC" width="250px" styleClass="invalidAlertSmall" >   
				<h:column>
				  	<h:outputText value="#{msgs.invalid_msg6}"  />  
				</h:column>
				<h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('listauthmodulesform:table:' + #{listAuthModulesPage.table.rowIndex} +':invalidMsgAl0','false')" />
				</h:column>
			  </h:panelGrid>
			 </h:panelGroup>	
 		  </h:column>           		
      	 </h:panelGrid> 
      </h:column>
      <h:column>
         	<h:panelGrid columns="4" styleClass="maintableCollapseWithNoBorder">         	
           <h:commandLink id="viewNextsteps" actionListener="#{listAuthModulesPage.viewNextsteps}" >
			   <h:graphicImage id="vns_gif" value="/images/add.gif" alt="#{msgs.list_auth_modules_alt_add_steps}" title="#{msgs.list_auth_modules_alt_add_steps}" styleClass="AddImgClass"  rendered="#{mdbean.module.whatsNext == listAuthModulesPage.isNull}"/>      
			   <h:graphicImage id="vns1_gif" value="/images/view_next.gif" alt="#{msgs.list_auth_modules_alt_next_steps}" title="#{msgs.list_auth_modules_alt_next_steps}" styleClass="AddImgClass"  rendered="#{mdbean.module.whatsNext != listAuthModulesPage.isNull}"/>        		   
           </h:commandLink>
         
           <h:commandLink id="specialAccess" actionListener="#{listAuthModulesPage.specialAccessAction}" >
			   <h:graphicImage id="acc_gif" value="/images/access_add.png" alt="#{msgs.list_auth_modules_alt_add_access}" title="#{msgs.list_auth_modules_alt_add_access}" styleClass="AddImgClass" rendered="#{mdbean.saFlag == false}"/>
			   <h:graphicImage id="acc_view_gif" value="images/access_view.png" alt="#{msgs.list_auth_modules_alt_view_access}" title="#{msgs.list_auth_modules_alt_view_access}" styleClass="AddImgClass" rendered="#{mdbean.saFlag == true}"/>            
			 </h:commandLink>           
           
		  <h:commandLink id="duplicateModule" action="#{listAuthModulesPage.duplicateAction}">
		  	  <h:graphicImage id="duplicateImg" value="/images/page_copy.png" alt="#{msgs.list_auth_modules_alt_duplicate}" title="#{msgs.list_auth_modules_alt_duplicate}" styleClass="AuthImgClass"/>
		  </h:commandLink>
		  
		     <h:outputLink id="printModuleLink" value="list_auth_modules" onclick="document.forms['listauthmodulesform'].elements['listauthmodulesform:saveChanges'].click();OpenPrintWindow(#{listAuthModulesPage.printModuleId},'Melete Print Window');">
		       	<h:graphicImage id="printImgLink" value="/images/printer.png" alt="#{msgs.list_auth_modules_alt_print}" title="#{msgs.list_auth_modules_alt_print}" styleClass="AuthImgClass"/>
		  </h:outputLink>  	  	   
		  </h:panelGrid>
        </h:column>          
    </h:dataTable>   
      <h:inputHidden id="listSize" value="#{listAuthModulesPage.listSize}"/>
      
	  <div class="actionBar" align="left">
	   <h:commandButton id="saveChanges" action="#{listAuthModulesPage.saveChanges}" rendered="#{listAuthModulesPage.nomodsFlag == false}" value="#{msgs.im_save}" accesskey="#{msgs.save_access}" title="#{msgs.im_save_text}" styleClass="BottomImgSave"/>
	   <h:commandButton id="cancelChanges" immediate="true" action="#{listAuthModulesPage.cancelChanges}" rendered="#{listAuthModulesPage.nomodsFlag == false}" value="#{msgs.im_cancel}"  accesskey="#{msgs.cancel_access}" title="#{msgs.im_cancel_text}" styleClass="BottomImgCancel"/>
	  </div>	 
<!--End Content-->
   </td>
  </tr>
 </table>
</h:form>
</sakai:view>
<script type="text/javascript">
 		 resetCheck();	
</script>
</f:view>


 

 
