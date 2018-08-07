<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.etudes.tool.melete.ViewModulesPage"%>
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.1/melete-app/src/webapp/melete/view_module.jsp $
 * $Id: view_module.jsp 83082 2013-03-15 20:30:37Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011,2012 Etudes, Inc.
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

<%
final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
final ViewModulesPage vmPage = (ViewModulesPage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "viewModulesPage");

String moduleId = (String)request.getParameter("modId");

if (moduleId != null)
{
	if (!moduleId.trim().equals("null") && moduleId.trim().length() > 0) vmPage.setModuleId(Integer.parseInt(moduleId));
}

String moduleSeqNo = (String)request.getParameter("modSeqNo");
if (moduleSeqNo != null)
{
	if (!moduleSeqNo.trim().equals("null") && moduleSeqNo.trim().length() > 0) vmPage.setModuleSeqNo(Integer.parseInt(moduleSeqNo));
}
%>
<f:view>
<sakai:view title="Modules: Student View" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="meleterightscheck.jsp" %>
<script type="text/javascript" language="javascript" src="/etudes-melete-tool/js/sharedscripts.js"></script>
<t:saveState id="vmpvmbean" value="#{viewModulesPage.viewMbean}" />
<t:saveState id="vmppmbean" value="#{viewModulesPage.prevMbean}" />
<t:saveState id="vmppsn" value="#{viewModulesPage.prevSeqNo}" />
<t:saveState id="vmpmsn" value="#{viewModulesPage.moduleSeqNo}" />
<t:saveState id="vmpnsn" value="#{viewModulesPage.nextSeqNo}" />
<t:saveState id="vmppss" value="#{viewModulesPage.prevSectionSize}" />

<h:form id="viewmoduleform">
 <f:subview id="top">
  	<jsp:include page="topnavbar.jsp?myMode=View"/>
  </f:subview>
  <p></p>
	<table class="maintableCollapseWithNoBorder" >

<!--Page Content-->
		 <tr>
			<td align="center">			
			<f:subview id="topmod">
			  <jsp:include page="view_navigate_mod.jsp"/>
			</f:subview>
			</td>
		</tr>      
<tr>
<td align="right">
<h:commandLink id="myBookmarksLink" action="#{bookmarkPage.gotoMyBookmarks}">
 <h:graphicImage id="mybook_gif" value="/images/my-bookmarks.png" alt="" styleClass="AuthImgClass" />
 <h:outputText id="mybks" value="#{msgs.my_bookmarks}" />									
 <f:param name="fromPage" value="view_module" />
 <f:param name="fromModuleId" value="#{viewModulesPage.moduleId}" />
 <f:param name="fromModuleSeqNo" value="#{viewModulesPage.moduleSeqNo}" />
</h:commandLink>				  
</td>
</tr>			               
<tr>
<td>
	<h:panelGrid id="moduleContentGrid" columns="2" width="100%" columnClasses="style6,right" border="0" cellpadding="5" rendered="#{viewModulesPage.viewMbean != null}">
		<h:column>
			<h:graphicImage id="moduleFinishStatus" url="/images/status_away.png" alt="#{msgs.list_modules_alt_progress}" title="#{msgs.list_modules_alt_progress}" styleClass="AuthImgClass" rendered="#{viewModulesPage.viewMbean.readDate != null && !viewModulesPage.viewMbean.readComplete}" />
	   		<h:graphicImage id="moduleFinishStatus1" url="/images/finish.gif" alt="#{msgs.list_modules_alt_complete}" title="#{msgs.list_modules_alt_complete}" styleClass="AuthImgClass" rendered="#{viewModulesPage.viewMbean.readComplete}" /> 
			<h:outputText id="mod_seq" value="#{viewModulesPage.viewMbean.seqNo}. " styleClass="bold" rendered="#{viewModulesPage.autonumber}"/>
			<h:outputText id="title" value="#{viewModulesPage.viewMbean.title}" styleClass="bold" ></h:outputText>
		</h:column>
		<h:column rendered="#{viewModulesPage.printable}">
			<h:commandLink id="printModuleLink" actionListener="#{viewModulesPage.viewModule}" rendered="#{viewModulesPage.printable}">
		    	<f:param id="modId" name="modId" value="#{viewModulesPage.viewMbean.moduleId}" />
	  			<h:graphicImage id="printImgLink" value="/images/printer.png" onclick="OpenViewModulePrintWindow('#{viewModulesPage.printUrl}','Melete Print Window');" alt="#{msgs.list_auth_modules_alt_print}" title="#{msgs.list_auth_modules_alt_print}" styleClass="AuthImgClass"/>
		 	</h:commandLink>
		</h:column>
	</h:panelGrid>
	<h:panelGrid id="moduleContentGrid1" columns="1" width="97%" border="0" cellpadding="3" rendered="#{viewModulesPage.viewMbean != null }">
		<h:column>
			<h:outputText id="description" value="#{viewModulesPage.viewMbean.description}"  rendered="#{((viewModulesPage.viewMbean.description != viewModulesPage.nullString)&&(viewModulesPage.viewMbean.description != viewModulesPage.emptyString))}" />
		</h:column>
	<h:column>
		<h:outputText id="secs" value="#{msgs.view_module_student_content_section}" ></h:outputText>  
		<h:dataTable id="tablesec"  value="#{viewModulesPage.viewMbean.vsBeans}" var="sectionBean" columnClasses="SectionTableClassCol1,SectionTableClassCol2" rowClasses="row2,row1" headerClass="leftheader" rendered="#{viewModulesPage.sectionSize > 0}" styleClass="SectionTableClass" width="98%" summary="#{msgs.view_module_summary}">
 			  <h:column> 			  		
 					<h:panelGroup style="width:100%;">
	        		 
			          <h:commandLink id="viewSectionEditor"  actionListener="#{viewModulesPage.viewSection}" styleClass="#{sectionBean.displayClass}" rendered="#{sectionBean.title != viewModulesPage.nullString}" immediate="true">
					      <h:graphicImage id="bul_gif" value="/images/bullet_black.gif" rendered="#{!viewModulesPage.autonumber}" styleClass="AuthImgClass"/>
					      <h:outputText id="sec_seq" value="#{sectionBean.displaySequence}. " rendered="#{viewModulesPage.autonumber}"/>
						  <h:outputText id="sectitleEditor" value="#{sectionBean.title}" > </h:outputText>
					  </h:commandLink>					 
					 </h:panelGroup> 													 
				</h:column>
				<h:column>
					 <h:outputText id="viewDt" value="#{sectionBean.viewDate}" rendered="#{sectionBean.viewDate != null}">
						<o:convertDateTime />
	        	    </h:outputText>	
	        	    <h:outputText id="viewDt1" value="-" rendered="#{sectionBean.viewDate == null}" />
	        	    <f:facet name="header">
	        	    	<h:outputText id="viewDtheader" value="#{msgs.view_module_date_viewed}" />
	        	    </f:facet>
				</h:column>
 		 </h:dataTable>        
	</h:column>
	<h:column rendered="#{viewModulesPage.viewMbean.whatsNext != viewModulesPage.nullString}">
		<h:outputText value="#{msgs.view_module_student_whats_next}" styleClass="bold style7"></h:outputText>		
	</h:column>
	<h:column rendered="#{viewModulesPage.viewMbean.whatsNext != viewModulesPage.nullString}">
		<h:outputText id="whatsnext" value="#{viewModulesPage.viewMbean.whatsNext}"/>
	</h:column>	
	</h:panelGrid>	
</td>
</tr>             

<tr>
	<td align="center">
		<f:subview id="bottommod">
		  <jsp:include page="view_navigate_mod.jsp"/>
		</f:subview>		
	</td>	
		</tr>
		<tr><td class="maintabledata5">&nbsp;   </td></tr>    
		</table>

<!--End Content-->
</h:form>
</sakai:view>
</f:view>

 
