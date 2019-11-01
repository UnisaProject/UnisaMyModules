<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-app/src/webapp/melete/list_special_access.jsp $
 * $Id: list_special_access.jsp 5297 2013-06-28 19:20:29Z mallikamt $  
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
--%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="date-time-converter" prefix="o" %>

<f:view>
<sakai:view title="Modules: List Special Access" toolCssHref="/etudes-melete-tool/rtbc004.css">

<%@include file="accesscheck.jsp" %>
<script type="text/javascript" language="JavaScript" src="/etudes-melete-tool/js/calendar2.js"></script>
<script type="text/javascript" language="javascript">

function selectAll()
{
  var listSizeStr = "listspecialaccessform:listSize";
  var listSizeVal = document.getElementById(listSizeStr).value;
  if (document.getElementById("listspecialaccessform:table:allacccheck") != null)
  {	  
  if (document.getElementById("listspecialaccessform:table:allacccheck").checked == true)
  {	  
    for (i=0;i<parseInt(listSizeVal);i++)
    {
	  var accchStr = "listspecialaccessform:table:"+i+":accCheck";
	  if (document.getElementById(accchStr).checked == false)
	  {	  
	    document.getElementById(accchStr).checked=true;
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

function resetAllAcc()
{
	if (document.getElementById("listspecialaccessform:table:allacccheck") != null)
	{	
	  document.getElementById("listspecialaccessform:table:allacccheck").checked=false;
	}
}

</script>
<t:saveState id="spModId" value="#{specialAccessPage.moduleId}"/>
<h:form id="listspecialaccessform">

			    
    <f:subview id="top">
		<jsp:include page="topnavbar.jsp"/> 
	</f:subview>
	<div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/access.png" alt="" width="16" height="16" align="absbottom"><h:outputText value="#{msgs.list_special_access}" /> </div>
    
	
	<h:panelGrid columns="1" styleClass="maintableCollapseWithBorder">
	<h:column>
	<h:outputText id="title" value="#{specialAccessPage.module.title}" />
	<h:outputText id="br" escape="false" value="<br>" />
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
	
	    <h:panelGrid columns="2" columnClasses="authBarCol" border="0" width="16%" >
		<h:column>
			<h:commandLink id="addAction" actionListener="#{specialAccessPage.addAccessAction}" styleClass="toolUiLink">
			    <h:graphicImage id="addAccessImg" value="/images/document_add.gif" styleClass="AuthImgClass"/>
		  		<h:outputText  value="#{msgs.list_special_access_add}"/>
			</h:commandLink>
		</h:column>
		<h:column>
			<h:commandLink id="delAction" action="#{specialAccessPage.deleteAction}" styleClass="toolUiLink">
		        <h:graphicImage id="deleteImg" value="/images/delete.gif" styleClass="AuthImgClass"/>
		        <h:outputText  id="del" value="#{msgs.list_special_access_delete}"></h:outputText>
		     </h:commandLink>
		</h:column>
	    </h:panelGrid>
    </h:column>
    </h:panelGrid>
    	
   <h:dataTable id="table" 
                  value="#{specialAccessPage.saList}"
                  var="saObj"  headerClass="tableheader" rowClasses="row1,row2" columnClasses="ListModCheckClass,ListModCheckClass,ListTitleClass,ListDateInputClass,ListDateInputClass,ListDateInputClass" 
                  cellpadding="3" cellspacing="0" 
				  width="100%" binding="#{specialAccessPage.table}" styleClass="valignStyle9" summary="#{msgs.list_special_access_summary}">
                      
    <h:column>   
     <f:facet name="header">
       <h:outputText value="&nbsp;" escape="false"/>
     </f:facet>  
     <h:graphicImage id="err_gif" value="/images/warning.png" alt="#{msgs.list_auth_modules_invalid}" title="#{msgs.list_auth_modules_invalid}" rendered="#{saObj.valid == false}" onclick="showHideTable('listspecialaccessform:table:' + #{specialAccessPage.table.rowIndex} +':invalidMsg0','true')"  styleClass="ExpClass"/>
	 <h:panelGroup id="invalidMsg0" style="position:relative;z-index:1;visibility:hidden;display:none;" rendered="#{saObj.valid == false}" >
			 <h:panelGrid id="invalidMsg" columns="1" border="0" bgcolor="#FFFFCC" cellpadding="5" width="390px" styleClass="invalidAlert" >   
				<h:column>
				  	<h:outputText value="#{msgs.invalid_access_msg}" />
				</h:column>
				<h:column>
					<h:outputLabel value="#{msgs.invalid_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('listspecialaccessform:table:' + #{specialAccessPage.table.rowIndex} +':invalidMsg0','false')" />
				</h:column>
			  </h:panelGrid>
	</h:panelGroup>	                   
    </h:column>   
   <h:column>
     <f:facet name="header">
      <h:panelGroup>
       <h:selectBooleanCheckbox id="allacccheck" value="#{specialAccessPage.selectAllFlag}" onclick="selectAll()" valueChangeListener="#{specialAccessPage.selectAllAccess}" rendered="#{specialAccessPage.noAccFlag == false}" />   
      </h:panelGroup> 
     </f:facet>   
    <h:selectBooleanCheckbox id="accCheck" title="#{saObj.accessIdStr}" value="#{saObj.selected}" onclick="resetAllAcc()" valueChangeListener="#{specialAccessPage.selectedAccess}" />
     </h:column>               
   <h:column>
 	<f:facet name="header">
 	 <h:panelGroup>
      <h:outputText id="t2" value="#{msgs.list_special_access_name}" />
     </h:panelGroup>        
    </f:facet>	
     <h:commandLink id="editAcc" actionListener="#{specialAccessPage.editSpecialAccess}"  action="#{specialAccessPage.redirectToEditSpecialAccess}" styleClass="toolUiLink">     				
     <f:param name="accid" value="#{saObj.accessId}" />
     <h:outputText id="title2" value="#{saObj.userNames}" escape="false"></h:outputText>
     </h:commandLink>
    
     </h:column>      
       <h:column>
        <f:facet name="header">
             <h:outputText id="t4" value="#{msgs.list_special_access_start_date}" />
             </f:facet>             
             <h:outputText id="startDate0" 
                           value="#{msgs.list_special_access_default}"  styleClass="italics"  rendered="#{(saObj.overrideStart == false)}">
            </h:outputText>
             <h:outputText id="startDate1" 
                           value="#{msgs.list_special_access_open}"  styleClass="italics"  rendered="#{((saObj.startDate == null)&&(saObj.overrideStart == true))}">
            </h:outputText>
                  <h:outputText id="startDate" 
                           value="#{saObj.startDate}"    rendered="#{((saObj.startDate != null)&&(saObj.overrideStart == true))}">
              <o:convertDateTime />
            </h:outputText>
              </h:column>         
        <h:column>
               <f:facet name="header">
				 <h:outputText id="t6" value="#{msgs.list_special_access_end_date}" />
             </f:facet>

             <h:outputText id="endDate0" 
                           value="#{msgs.list_special_access_default}"  styleClass="italics"  rendered="#{(saObj.overrideEnd == false)}">
            </h:outputText>
             <h:outputText id="endDate1" 
                           value="#{msgs.list_special_access_open}" styleClass="italics"   rendered="#{((saObj.endDate == null)&&(saObj.overrideEnd == true))}">
             </h:outputText>

              <h:outputText id="endDate"
                           value="#{saObj.endDate}"
                              rendered="#{((saObj.endDate != null)&&(saObj.overrideEnd == true))}">
               <o:convertDateTime />
            </h:outputText>
        
         </h:column>  
          <h:column>
               <f:facet name="header">
				 <h:outputText id="t7" value="#{msgs.list_special_access_allowuntil_date}" />
             </f:facet>

             <h:outputText id="auDate0" 
                           value="#{msgs.list_special_access_default}"  styleClass="italics"  rendered="#{(saObj.overrideAllowUntil == false)}">
            </h:outputText>
             <h:outputText id="auDate1" 
                           value="#{msgs.list_special_access_open}" styleClass="italics"   rendered="#{((saObj.allowUntilDate == null)&&(saObj.overrideAllowUntil == true))}">
             </h:outputText>

              <h:outputText id="auDate"
                           value="#{saObj.allowUntilDate}"
                              rendered="#{((saObj.allowUntilDate != null)&&(saObj.overrideAllowUntil == true))}">
               <o:convertDateTime />
            </h:outputText>
        
         </h:column>          
    </h:dataTable>   
   <h:inputHidden id="listSize" value="#{specialAccessPage.listSize}"/>   
   <div class="actionBar" align="left">			
   	
	<h:commandButton id="returnButton"  immediate="true" action="#{specialAccessPage.returnAction}" value="#{msgs.im_return}" tabindex="" accesskey="#{msgs.return_access}" title="#{msgs.im_return_text}" styleClass="BottomImgReturn" />
   </div>	
	
<!--End Content-->
 
</h:form>
</sakai:view>
<script type="text/javascript">
 		 resetCheck();	
</script>
</f:view>


 

 
