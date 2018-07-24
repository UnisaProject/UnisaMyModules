<!--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/webapp/melete/list_resources.jsp $
 * $Id: list_resources.jsp 3647 2012-12-02 22:30:41Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011 Etudes, Inc.
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
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>

<%@include file="accesscheck.jsp" %>
<t:saveState id="chunkSize1" value="#{listResourcesPage.chunkSize}" />	
<t:saveState id="sortAscFlag1" value="#{listResourcesPage.sortAscFlag}" />


<h:panelGrid styleClass="maintableCollapseWithNoBorder" >											
<h:column>		                    	
         <h:panelGrid id="selresPanel" columns="1" width="100%" rendered="#{listResourcesPage.callFromSection}" >	
	      	<h:column>
  				<h:outputText id="Stext3" value="#{msgs.edit_list_resources_select}" styleClass="bold"/> 
			</h:column>	   									
          </h:panelGrid>

                 <!-- navigation with showing 15 recs --> 
                <h:panelGrid id="selresNavigationPanel" columns="2"  width="100%" border="0" columnClasses="TitleWid4,ActionWid2" summary="#{msgs.list_resources_nav_summary}">
                <h:column/>
                <h:column>
                  <h:outputText id="nav_spaces_left1" value="" styleClass="ExtraPaddingClass" />
	          <h:outputText value="#{msgs.list_resources_viewing}" />
	          <h:outputText value="#{listResourcesPage.displayFromIndex}" />
	          <h:outputText value=" - " /><h:outputText value="#{listResourcesPage.displayEndIndex}" />
	           <h:outputText value=" "/><h:outputText value="#{msgs.list_resources_of}" /> 
	           <h:outputText value="#{listResourcesPage.totalSize -1}" /> 		
	          <h:outputText id="nav_spaces_right" value="" styleClass="ExtraPaddingClass" />
                </h:column>
                <h:column/>
   	        <h:column>
   		  <h:graphicImage id="leftImg_disable" value="/images/nav_left_disable.jpg" alt="#{msgs.list_resources_previous}" style="border:0 none;vertical-align:middle;" rendered="#{!listResourcesPage.prevListingFlag}" />
		  <h:commandLink id="prev_nav"  actionListener="#{listResourcesPage.goPrev}" rendered="#{listResourcesPage.prevListingFlag}">		
						 <h:graphicImage id="leftImg" value="/images/nav_left.jpg" alt="#{msgs.list_resources_previous2}" title="#{msgs.list_resources_previous2}" style="border:0 none;vertical-align:middle;"/>
		   </h:commandLink>		 
		   	 <h:outputText id="nav_spaces_left" value="" styleClass="ExtraPaddingClass" />
		  <h:selectOneMenu id="chunkSize"  value="#{listResourcesPage.chunkSize}" valueChangeListener="#{listResourcesPage.changeChunkSize}" onchange="this.form.submit();">
								<f:selectItem itemValue="30" itemLabel="#{msgs.list_resources_show30}"/>	
								<f:selectItem itemValue="100" itemLabel="#{msgs.list_resources_show100}"/>	
								<f:selectItem itemValue="-1" itemLabel="#{msgs.list_resources_showall}"/>	
			 </h:selectOneMenu>
		 <h:outputText id="nav_spaces" value="" styleClass="ExtraPaddingClass" />
				<h:commandLink id="next_nav" actionListener="#{listResourcesPage.goNext}" rendered="#{listResourcesPage.nextListingFlag}">
							 <h:graphicImage id="rightImg" value="/images/nav_right.jpg" alt="#{msgs.list_resources_next}" title="#{msgs.list_resources_next}" style="border:0 none;vertical-align:middle;" />
			   </h:commandLink>	 
			   <h:graphicImage id="rightImg_disable" value="/images/nav_right_disable.jpg" alt="#{msgs.list_resources_next2}" title="#{msgs.list_resources_next2}" style="border:0 none;vertical-align:middle;" rendered="#{!listResourcesPage.nextListingFlag}"/> 
   	      </h:column>											
              </h:panelGrid>
              <t:saveState id="fromIndex1" value="#{listResourcesPage.fromIndexParam}" />
              <t:saveState id="chunk1" value="#{listResourcesPage.chunkSize}" />
               <!-- navigation ends -->            		 
   
			 <h:dataTable id="table"  value="#{listResourcesPage.displayResourcesList}"  var="curr_resources" binding="#{listResourcesPage.table}" border="0" headerClass="tableheader2" columnClasses="TitleWid3,ActionWid" rowClasses="row1,row2"  width="100%" summary="#{msgs.list_resources_summary}">
				  <h:column>
					   <f:facet name="header">
							<h:panelGroup>
								<h:commandLink id="ascType" actionListener="#{listResourcesPage.sortResourcesAsc}" immediate="true" rendered="#{listResourcesPage.sortAscFlag}">
								    <h:graphicImage id="asc_Type_img" alt="#{msgs.manage_res_list_alt_asc}" title="#{msgs.manage_res_list_alt_asc}" value="/images/sortascending.gif" styleClass="ExpClass"/>
								 </h:commandLink>     
								 <h:commandLink id="descType" actionListener="#{listResourcesPage.sortResourcesDesc}" immediate="true" rendered="#{!listResourcesPage.sortAscFlag}">
								    <h:graphicImage id="des_Type_img" alt="#{msgs.manage_res_list_alt_desc}" title="#{msgs.manage_res_list_alt_desc}" value="/images/sortdescending.gif" styleClass="ExpClass"/>
								 </h:commandLink>        
					            
							  <h:outputText value="#{msgs.list_resources_title2}" />
							 </h:panelGroup> 
						 </f:facet>
					 <h:outputText id="emp_space" value="     "  styleClass="ExtraPaddingClass" rendered="#{curr_resources.resource_id != listResourcesPage.selResourceIdFromList}"/>	
					 <h:graphicImage id="check_gif" alt="#{msgs.list_resources_check}" title="#{msgs.list_resources_check}" value="/images/checkon.gif" rendered="#{curr_resources.resource_id == listResourcesPage.selResourceIdFromList}"/>
					 <h:graphicImage id="contenttype_gif" alt="#{msgs.list_resources_content_url}" title="#{msgs.list_resources_content_url}" value="/images/url.gif" styleClass="ExpClass" rendered="#{curr_resources.typeLink}"/>
					 <h:graphicImage id="contenttype_gifLTI" alt="#{msgs.edit_list_resources_content_url}" title="#{msgs.edit_list_resources_content_url}" value="/images/web_service.png" styleClass="ExpClass" rendered="#{curr_resources.typeLTI}"/>
					<h:graphicImage id="contenttype_gif1" alt="#{msgs.edit_list_resources_content_upload}" 
						title="#{msgs.edit_list_resources_content_upload}" value="#{curr_resources.resource_gif}" styleClass="ExpClass" rendered="#{(!curr_resources.typeLink && !curr_resources.typeLTI)}"/>
							<h:outputText id="emp_spacebefore" value="       "  styleClass="ExtraPaddingClass"/>
							<h:outputLink id="showResourceLink" value="#{curr_resources.resource_url}" target="_blank" title="Section Resource" styleClass="a1">
								  <h:outputText value="#{curr_resources.resource_title}" />
						  </h:outputLink>
				    </h:column>
				    <h:column>
				    <f:facet name="header">
							 <h:outputText id="t2" value="#{msgs.list_resources_actions2}" />
					 </f:facet>
					 <h:commandLink id="linkaction" action="#{listResourcesPage.link2meAction}" rendered="#{listResourcesPage.callFromSection}">
				     	<f:param name="selResourceIdFromList" value="#{curr_resources.resource_id}" />
				     	<f:param name="sectionId" value="#{listResourcesPage.sectionId}" />
				     	<h:graphicImage id="linkgif" alt="" value="/images/link2me.png" styleClass="AuthImgClass"  />
				     	<h:outputText id="emp_space-3" value=" "  />
						<h:outputText value="#{msgs.list_resources_link}" />
					 </h:commandLink>	
					  <h:outputText id="emp_space-1" value="     "  styleClass="ExtraPaddingClass" />
					 
					 <h:commandLink id="deleteaction" action="#{listResourcesPage.selectedResourceDeleteAction}" immediate="true">
					 		<f:param name="selResourceIdFromList" value="#{curr_resources.resource_id}" />
				     		<f:param name="sectionId" value="#{listResourcesPage.sectionId}" />
				       		<h:graphicImage id="delgif" alt="" value="/images/delete.gif" styleClass="AuthImgClass" />
							<h:outputText id="emp_space-2" value=" " />
				    		<h:outputText id="deltext" value="#{msgs.list_resources_del}"  />
					 </h:commandLink>	
				    </h:column>
                   </h:dataTable>		
	</h:column>					
</h:panelGrid>
