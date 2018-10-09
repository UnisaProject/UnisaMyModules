<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/list_modules_inst.jsp $
 * $Id: list_modules_inst.jsp 86862 2014-08-08 20:37:38Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2012, 2014 Etudes, Inc.
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
<sakai:view title="Modules: Student View" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="accesscheck.jsp" %>

<script type="text/javascript" language="javascript" src="/etudes-melete-tool/js/sharedscripts.js"></script>
<script type="text/javascript" language="javascript">

function showHideTable(index, show)
{
  var string2 = "listmodulesform:StudentTable:" + index +":preReqMsg0";
  var string2ele = document.getElementById(string2);
  // show the box
  if(string2ele != undefined && string2ele != null && string2ele.style.display == "none" && show.match("true")) 
	{
	string2ele.setAttribute("aria-hidden", "false");
	string2ele.tabIndex = -1;
	string2ele.style.display = "block";
	string2ele.style.visibility = "visible";
	string2ele.focus();	
	}
 else if(string2ele != undefined && string2ele != null && string2ele.style.display == "block" && !show.match("true"))
	{
	string2ele.setAttribute("aria-hidden", "true");
	string2ele.tabIndex = 0;
	string2ele.style.display = "none";
	string2ele.style.visibility = "hidden";	
	}
}
</script>

<h:form id="listmodulesform">
<h:inputHidden id="lmexp" value="#{listModulesPage.expandAllFlag}" />
<h:inputHidden id="lmshmodid" value="#{listModulesPage.showModuleId}"/>
<f:subview id="top">
	<jsp:include page="topnavbar.jsp?myMode=View"/> 
</f:subview>
<br/>
<div align="right">
<h:commandLink id="lastVisitedLink" actionListener="#{bookmarkPage.viewSection}" rendered="#{listModulesPage.bookmarkSectionId > 0}" styleClass="toolUiLink">
 <f:param name="sectionId" value="#{listModulesPage.bookmarkSectionId}" /> 
 <h:graphicImage id="lvisit_gif" value="/images/last-visited.png" alt="" styleClass="BmImgClass"/>
 <h:outputText id="lastvisit" value="#{msgs.last_visited}" />									
</h:commandLink>
<h:outputText value="|" rendered="#{listModulesPage.bookmarkSectionId > 0}"/> 
<h:commandLink id="myBookmarksLink" action="#{bookmarkPage.gotoMyBookmarks}" styleClass="toolUiLink">
<f:param name="fromPage" value="list_modules_inst" />
<h:graphicImage id="mybook_gif" value="/images/my-bookmarks.png" alt="" styleClass="BmImgClass"/>
 <h:outputText id="mybks" value="#{msgs.my_bookmarks}" />									
</h:commandLink>	
</div>
<h:messages showDetail="true" showSummary="false"/>
 <h:outputText id="nomodstext" value="#{msgs.no_modules}" rendered="#{listModulesPage.nomodsFlag == null || listModulesPage.nomodsFlag}" style="text-align:left"/>
 <h:dataTable id="StudentTable"  
                  value="#{listModulesPage.viewModuleBeans}" rendered="#{listModulesPage.nomodsFlag != null && !listModulesPage.nomodsFlag}" 
                  var="vmbean"  rowClasses="row1,row2" columnClasses="StudentListTitleClass,ListClosedClass,ListDateClass,ListDateClass,ListDateClass,ListDateClass,ListPrintClass" headerClass="tableheader"
                  border="0" cellpadding="3" cellspacing="0" width="100%" styleClass="valignStyle9" binding="#{listModulesPage.modTable}" summary="#{msgs.list_modules_inst_summary}">
        <h:column>      
        <f:facet name="header">
        <h:panelGroup>
         <h:commandLink id="expandCollpaseAction" action="#{listModulesPage.expandCollapseAction}" styleClass="toolUiLink">
     	   <h:graphicImage id="exp_all_gif" alt="#{msgs.list_modules_inst_expand_all}" title="#{msgs.list_modules_inst_expand_all}" value="/images/expand-collapse.gif"   rendered="#{listModulesPage.expandAllFlag != listModulesPage.trueFlag}" styleClass="ExpClass"/>
           <h:graphicImage id="col_all_gif" alt="#{msgs.list_modules_inst_collapse_all}" title="#{msgs.list_modules_inst_collapse_all}" value="/images/collapse-expand.gif"   rendered="#{listModulesPage.expandAllFlag == listModulesPage.trueFlag}" styleClass="ExpClass"/>
         </h:commandLink>
          <h:outputText id="t2" value="#{msgs.list_auth_modules_title}" />
        </h:panelGroup>
        </f:facet>    
      
      <h:commandLink id="showHideSections" action="#{listModulesPage.showHideSections}" styleClass="toolUiLink">
        <h:graphicImage id="exp_gif" alt="#{msgs.list_modules_inst_expand}" title="#{msgs.list_modules_inst_expand}" value="/images/expand.gif" rendered="#{((vmbean.moduleId != listModulesPage.showModuleId)&&(vmbean.vsBeans != listModulesPage.nullList)&&(listModulesPage.expandAllFlag != listModulesPage.trueFlag))}" styleClass="ExpClass"/>
        <h:graphicImage id="col_gif" alt="#{msgs.list_modules_inst_collapse}" title="#{msgs.list_modules_inst_collapse}" value="/images/collapse.gif" rendered="#{(((vmbean.moduleId == listModulesPage.showModuleId)&&(vmbean.vsBeans != listModulesPage.nullList))||((listModulesPage.expandAllFlag == listModulesPage.trueFlag)&&(vmbean.vsBeans != listModulesPage.nullList)))}" styleClass="ExpClass"/>
        </h:commandLink>   
       <h:graphicImage id="moduleFinishStatus" url="/images/status_away.png" alt="#{msgs.list_modules_alt_progress}" title="#{msgs.list_modules_alt_progress}" styleClass="AuthImgClass" rendered="#{vmbean.readDate != null && !vmbean.readComplete}" />
	   <h:graphicImage id="moduleFinishStatus1" url="/images/finish.gif" alt="#{msgs.list_modules_alt_complete}" title="#{msgs.list_modules_alt_complete}" styleClass="AuthImgClass" rendered="#{vmbean.readComplete}" /> 
       <h:outputText id="mod_seq" value="#{vmbean.seqNo}. " rendered="#{listModulesPage.autonumber}"/>
       <h:commandLink id="viewModule"  actionListener="#{listModulesPage.viewModule}" styleClass="toolUiLink">
           <f:param name="viewmodid" value="#{vmbean.moduleId}" />
                 <h:outputText id="title"
                           value="#{vmbean.title}" rendered="#{vmbean.visibleFlag == true}"/>
              <h:outputText id="title2"
                           value="#{vmbean.title}" styleClass="italics" rendered="#{vmbean.visibleFlag == false}" />             
                    
       </h:commandLink>
                
           <h:dataTable id="tablesec" rendered="#{(((vmbean.moduleId == listModulesPage.showModuleId)||(listModulesPage.expandAllFlag == listModulesPage.trueFlag)))}"
                  value="#{vmbean.vsBeans}" cellpadding="1"
                  var="vsbean"  rowClasses="#{vmbean.rowClasses}" width="95%" styleClass="secrow0" binding="#{listModulesPage.secTable}" summary="#{msgs.list_modules_inst_sections_summary}">
                 <h:column>   
                   <h:graphicImage id="bul_gif" value="/images/bullet_black.gif" rendered="#{!listModulesPage.autonumber}"/>
		      	   <h:outputText id="sec_seq" value="#{vsbean.displaySequence}. " rendered="#{listModulesPage.autonumber}"/>
					<h:commandLink id="viewSectionEditor"  actionListener="#{listModulesPage.viewSection}" rendered="#{vsbean.contentType == listModulesPage.typeLink}" styleClass="toolUiLink">
	                  	 <f:param name="viewmodid" value="#{vmbean.moduleId}" />  
                          <f:param name="viewsecid" value="#{vsbean.sectionId}" /> 
                         <h:outputText id="sectitleEditor" value="#{vsbean.title}" rendered="#{vmbean.visibleFlag == true}" />
	                 	<h:outputText id="sectitleEditor2" value="#{vsbean.title}" styleClass="italics" rendered="#{vmbean.visibleFlag == false}" />
	                   </h:commandLink>
	                  
		           <h:commandLink id="viewSectionLink"  actionListener="#{listModulesPage.viewSection}" rendered="#{vsbean.contentType != listModulesPage.typeLink}" styleClass="toolUiLink">
		                  <f:param name="viewmodid" value="#{vmbean.moduleId}" />  
                          <f:param name="viewsecid" value="#{vsbean.sectionId}" /> 
                         <h:outputText id="sectitleLink" value="#{vsbean.title}" rendered="#{vmbean.visibleFlag == true}" />
			              <h:outputText id="sectitleLink2" value="#{vsbean.title}" styleClass="italics" rendered="#{vmbean.visibleFlag == false}"/>
		             </h:commandLink>		           	          
	            </h:column>
	          </h:dataTable>
          <h:panelGrid id="nextsteps" columns="1" width="100%" rendered="#{vmbean.whatsNext != listModulesPage.isNull && ((listModulesPage.expandAllFlag == listModulesPage.trueFlag)||(vmbean.moduleId == listModulesPage.showModuleId))}">
	          <h:column>
		          <h:outputText id="emp_space6_bul" value="  " styleClass="NextStepsPaddingClass"/>
		          <h:outputText id="next_seq" value="#{vmbean.nextStepsNumber}. " rendered="#{listModulesPage.autonumber}"/>
		          <h:graphicImage id="bul_gif1" value="/images/bullet_black.gif" rendered="#{!listModulesPage.autonumber}" style="border:0"/>
		          
		          <h:commandLink id="whatsNext" actionListener="#{listModulesPage.goWhatsNext}" immediate="true" styleClass="toolUiLink">
				    <h:outputText  id="whatsNextMsg" value="#{msgs.list_modules_stud_next_steps}" rendered="#{vmbean.visibleFlag == listModulesPage.trueFlag}"/>
				    <h:outputText  id="whatsNextMsg2" value="#{msgs.list_modules_stud_next_steps}" styleClass="italics" rendered="#{vmbean.visibleFlag == false}"/>
				    <f:param name="viewmodid" value="#{vmbean.moduleId}" />  
                     <f:param name="viewmodseqno" value="#{vmbean.seqNo}" />
		          </h:commandLink>  

	          </h:column>
          </h:panelGrid> 
           </h:column>
           <h:column>   
	          <f:facet name="header">
	              <h:outputText value="&nbsp;" escape="false"/>
	          </f:facet>        	  
      		  <h:panelGrid columns="1" style="z-index:0;" rendered="#{vmbean.blockedBy != null}">   
               	<h:column>    
          			<h:graphicImage id="pre-req" value="/images/lock.png" alt="#{msgs.list_modules_locked}" title="#{msgs.list_modules_locked}" rendered="#{vmbean.blockedBy != null}" onclick="showHideTable('#{listModulesPage.modTable.rowIndex}','true')" styleClass="ExpClass"/>
             	</h:column>
             	<h:column>
             		  <h:outputLink value="#" onclick="showHideTable('#{listModulesPage.modTable.rowIndex}','true')" styleClass="toolUiLink">	
             			<h:outputText id="pre-req-text" value="#{msgs.list_modules_prereq}" styleClass="style3"/>   
       				</h:outputLink>	      				
   				  </h:column> 	
 	 	   	  </h:panelGrid>	
 			  <h:panelGroup id="preReqMsg0" style="position:relative;z-index:1;visibility:hidden;display:none;" rendered="#{vmbean.blockedBy != null}" >   
	   				<h:panelGrid id="preReqMsg" columns="1" border="1" rules="cols" bgcolor="#FFFFCC" cellpadding="5" width="390px" styleClass="prereqAlert" >   
		               	<h:column>     	  
		               			<h:outputText value="#{vmbean.blockedDetails}" escape="false"/>
		               			<h:outputText value=":" />
		             	</h:column>
		             	<h:column>
		             		  <h:graphicImage id="bul_gif" value="/images/bullet_black.gif" /><h:outputText value="#{vmbean.blockedBy}" escape="false"/>
		        	   </h:column> 	
		        	   <h:column>
	         			  	<h:outputLabel value="#{msgs.prerequisite_ok_msg}"  styleClass="BottomImgOK" onclick="showHideTable('#{listModulesPage.modTable.rowIndex}','false')" />
	    	   			</h:column>		        	  
	 	  			 </h:panelGrid>  	 	  			        	  
 	  		  </h:panelGroup>  
               <h:graphicImage id="closed_gif" value="/images/view_closed.gif" alt="#{msgs.list_modules_inst_closed}" title="#{msgs.list_modules_inst_closed}" rendered="#{vmbean.closedBeforeFlag == listModulesPage.trueFlag}" styleClass="ExpClass"/>
               <h:graphicImage id="cal_gif" value="/images/cal.gif" alt="#{msgs.list_modules_inst_notyetopen}" title="#{msgs.list_modules_inst_notyetopen}" rendered="#{vmbean.openLaterFlag == listModulesPage.trueFlag}" styleClass="ExpClass"/>
               <h:outputText id="brval" value="<BR>" escape="false" rendered="#{vmbean.openLaterFlag == listModulesPage.trueFlag}"/>
               <h:outputText value="#{msgs.notyetopen_msg}" rendered="#{vmbean.openLaterFlag == listModulesPage.trueFlag}" styleClass="style3"/>
           </h:column>
           <h:column>
             <f:facet name="header">
               <h:outputText value="#{msgs.list_modules_inst_start_date}" />
             </f:facet>
			  <h:outputText id="startDate0" value="-" rendered="#{vmbean.startDate == null}" />
              <h:outputText id="startDate" value="#{vmbean.startDate}" rendered="#{vmbean.visibleFlag == listModulesPage.trueFlag}" escape="false">
             	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
              </h:outputText> 
              <h:outputText id="startDate2" value="#{vmbean.startDate}"  rendered="#{!vmbean.visibleFlag}" styleClass="italics" escape="false">
             	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
             </h:outputText>           
           </h:column>
            <h:column>
              <f:facet name="header">
                <h:outputText value="#{msgs.list_modules_inst_end_date}" />
              </f:facet>  
			 <h:outputText id="endDate0" value="-" rendered="#{vmbean.endDate == null}" />
              <h:outputText id="endDate"  value="#{vmbean.endDate}" rendered="#{vmbean.visibleFlag == listModulesPage.trueFlag}" escape="false">
             	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
              </h:outputText> 
              <h:outputText id="endDate2"  value="#{vmbean.endDate}" styleClass="italics" rendered="#{!vmbean.visibleFlag}" escape="false">
       		      <f:converter converterId="melete.DateTimeConverter" />
  				  <f:attribute name="multiLine" value="true" />             
              </h:outputText>  
   	 </h:column> 
   	  <h:column>
              <f:facet name="header">
                <h:outputText value="#{msgs.list_modules_inst_allowuntil_date}" />
              </f:facet>  
			 <h:outputText id="auDate0" value="-" rendered="#{vmbean.allowUntilDate == null}" />
              <h:outputText id="auDate" value="#{vmbean.allowUntilDate}" rendered="#{vmbean.visibleFlag == listModulesPage.trueFlag}" escape="false">
              	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
              </h:outputText> 
          
             <h:outputText id="auDate2" value="#{vmbean.allowUntilDate}" rendered="#{!vmbean.visibleFlag}" styleClass="italics" escape="false">
             	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
             </h:outputText>
                    
   	 </h:column>   
   	  <h:column>
              <f:facet name="header">
                <h:outputText value="#{msgs.list_modules_inst_viewed_date}" />
              </f:facet>  
               <h:outputText id="viewDate0" value="-" rendered="#{vmbean.readDate == null}" />
              
              <h:outputText id="viewDate" value="#{vmbean.readDate}" rendered="#{vmbean.visibleFlag == listModulesPage.trueFlag}" escape="false">
              	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
              </h:outputText>
          
             <h:outputText id="viewDate2" value="#{vmbean.readDate}" rendered="#{!vmbean.visibleFlag}" styleClass="italics" escape="false">
            	 <f:converter converterId="melete.DateTimeConverter" />
  				 <f:attribute name="multiLine" value="true" />  
             </h:outputText>    
   	 </h:column>   
      <h:column> 
      		<f:facet name="header">
               <h:outputText value="&nbsp;" escape="false"/>
             </f:facet>
         <h:outputLink id="printModuleLink" value="list_modules_inst" onclick="OpenPrintWindow(#{listModulesPage.printModuleId},'Melete Print Window');" rendered="#{listModulesPage.printable && vmbean.visibleFlag}" styleClass="toolUiLink">
	  	    	<h:graphicImage id="printImgLink" value="/images/printer.png" alt="#{msgs.list_auth_modules_alt_print}" title="#{msgs.list_auth_modules_alt_print}" styleClass="AuthImgClass"/>
	 	 </h:outputLink>
	    
	    </h:column>	     
      </h:dataTable> 
  
	    <div class="actionBar" align="left">&nbsp;</div> 	

<!--End Content-->
 </h:form>
</sakai:view>
</f:view>

