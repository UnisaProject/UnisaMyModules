<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/editContentLinkView.jsp $
 * $Id: editContentLinkView.jsp 85951 2014-03-14 16:53:27Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008,2009,2010,2011 Etudes, Inc.
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
<%@include file="accesscheck.jsp" %>
 <h:panelGrid id="LinkPanel" columns="2" columnClasses="col1,col2" width="100%" border="0" rendered="#{editSectionPage.shouldRenderLink}">
	  <h:column>
	  	<h:outputText id="editlinkText1" value="#{msgs.editcontentlinkview_link1}"/>	
	  </h:column>
	  <h:column>	
		<h:commandLink id="serverLinkButton"  actionListener="#{editSectionPage.setServerUrlListener}" styleClass="toolUiLink">
			<f:param name="sectionId" value="#{editSectionPage.editId}" />
			<h:graphicImage id="replaceLinkImg2" value="/images/replace2.gif" styleClass="AuthImgClass"/>
			<h:outputText value="#{msgs.editcontentlinkview_replace}"/>
	    </h:commandLink>		
	  </h:column>
	  <h:column/>
	  <h:column>
	  <h:outputText id="editlinkText4" value="#{msgs.editcontentlinkview_noURL}" rendered="#{editSectionPage.displayCurrLink == null}" styleClass="bold"/> 
	  	<h:outputLink id="showResourceLink" value="#{editSectionPage.currLinkUrl}" target="_blank" title="Section Resource" styleClass="toolUiLink" rendered="#{editSectionPage.displayCurrLink != null}">	  
	  		<h:outputText id="editlinkText3" value="#{editSectionPage.displayCurrLink}" />
	  	</h:outputLink>	
	  </h:column> 	
	  <h:column/>
	  <h:column>     	
         <h:selectBooleanCheckbox id="windowopenLink" title="openWindow" value="#{editSectionPage.section.openWindow}" >
		 </h:selectBooleanCheckbox>
		 <h:outputText id="editlinkText_8Link" value="#{msgs.editcontentlinkserverview_openwindow}" />
      </h:column>   				  
</h:panelGrid>	
										
