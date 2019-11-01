	<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/edit_sec_resourcePropertiesPanel.jsp $
 * $Id: edit_sec_resourcePropertiesPanel.jsp 86862 2014-08-08 20:37:38Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2010, 2014 Etudes, Inc.
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
<%@include file="accesscheck.jsp" %>
<script type="text/javascript" language="javascript1.2">

function fillTitle()
{
	var u = document.getElementById("EditSectionForm:ResourcePropertiesPanel:res_url");
	var utitle = document.getElementById("EditSectionForm:ResourcePropertiesPanel:res_name");
	if (u != undefined && utitle != undefined && utitle.value.length == 0) document.getElementById("EditSectionForm:ResourcePropertiesPanel:res_name").value = u.value;
	
	var v = document.getElementById("EditSectionForm:ResourcePropertiesPanel:edituploadText2");
	var vtitle = document.getElementById("EditSectionForm:ResourcePropertiesPanel:res_desc");
	if (v != undefined && vtitle != undefined && vtitle.value.length == 0) document.getElementById("EditSectionForm:ResourcePropertiesPanel:res_desc").value = v.value;
}
</script>
 
	   <h:panelGrid id="propertiesPanel" columns="1" width="100%" styleClass="maintabledata8">
		<h:column>
			<h:outputText id="propertiesPaneltxt" value="#{msgs.editsec_resources_proper_pan_properties}" />
		</h:column>
	</h:panelGrid>
	<h:panelGrid id="propertiesPanel2" columns="2" width="100%" cellpadding="3" columnClasses="col1,col2" border="0">
		
		<!-- TYPE UPLOAD -->
		<h:column rendered="#{editSectionPage.shouldRenderUpload}">
			<h:outputText id="edituploadText1" value="#{msgs.editcontentuploadview_file_uploaded}" />
		</h:column>
		<h:column rendered="#{editSectionPage.shouldRenderUpload}">
			<h:inputText id="edituploadText2" size="45" value="#{editSectionPage.secResourceName}" readonly="true" styleClass="formtext" />
				
			<h:outputText value=" " styleClass="ExtraPaddingClass"/>
			 <h:outputLink id="showResourceUpload" value="#{editSectionPage.previewContentData}" target="_blank" title="Section Resource" styleClass="toolUiLink" rendered="#{editSectionPage.previewContentData != null && editSectionPage.previewContentData != editSectionPage.emptyString}">	  
  				 <h:graphicImage id="contenttype_gif1" alt="#{msgs.edit_list_resources_content_upload}" title="#{msgs.edit_list_resources_content_upload}" value="#{editSectionPage.rgif}" styleClass="ExpClass" />
  				 <h:outputText value="View" />
  			</h:outputLink>	
  						
			<h:commandLink id="serverViewButton" actionListener="#{editSectionPage.setServerFileListener}" styleClass="toolUiLink">
				<f:param name="sectionId" value="#{editSectionPage.editId}" />
				<h:outputText value=" " styleClass="ExtraPaddingClass"/>
				<h:graphicImage id="replaceuploadImg" value="/images/replace2.gif" styleClass="AuthImgClass"/>
				<h:outputText value="#{msgs.editcontentuploadview_replace}"/>
		    </h:commandLink>	
	    
		</h:column>
		
		<!--  TYPE LINK -->
		<h:column rendered="#{(editSectionPage.shouldRenderLink)}" >
			 <h:outputText value="#{msgs.editsec_resources_proper_pan_URL}"  /><h:outputText value="*" styleClass="required" />
		</h:column>	 
		<h:column rendered="#{(editSectionPage.shouldRenderLink)}">				
			 <h:inputText id="res_url" size="75" value="#{editSectionPage.linkUrl}" styleClass="formtext" /> 		
		
			 <!-- preview link -->
			 <h:outputText value=" " styleClass="ExtraPaddingClass"/>
			<h:outputLink id="showResourceLink" value="#{editSectionPage.previewContentData}" target="_blank" title="Section Resource" styleClass="toolUiLink" rendered="#{editSectionPage.previewContentData != null && editSectionPage.previewContentData != editSectionPage.emptyString}">	  
	  			<h:graphicImage id="contenttype_gifLink" alt="#{msgs.edit_list_resources_content_url}" title="#{msgs.edit_list_resources_content_url}" value="/images/url.gif" styleClass="ExpClass" />
  				 <h:outputText value="View" />
	 	 	</h:outputLink>	
	  	
			<!-- replace -->  
			<h:commandLink id="serverLinkButton"  actionListener="#{editSectionPage.setServerUrlListener}" styleClass="toolUiLink">
				<f:param name="sectionId" value="#{editSectionPage.editId}" />
				<h:outputText value=" " styleClass="ExtraPaddingClass"/>
				<h:graphicImage id="replaceLinkImg2" value="/images/replace2.gif" styleClass="AuthImgClass"/>
				<h:outputText value="#{msgs.editcontentlinkview_replace}"/>
	    	</h:commandLink>	    
		</h:column>	
	
		<!--  TYPE LTI -->
		<h:column rendered="#{(editSectionPage.shouldRenderLTI)}" >
			 <h:outputText value="#{msgs.editsec_resources_proper_pan_launchURL}"  /><h:outputText value="*" styleClass="required" />
		</h:column>	 
		<h:column rendered="#{(editSectionPage.shouldRenderLTI)}">	
			<!-- url value -->			
			 <h:inputText id="res_url1" size="75" value="#{editSectionPage.LTIUrl}" styleClass="formtext" /> 
			
			 <!-- preview link -->
			 <h:outputText value=" " styleClass="ExtraPaddingClass"/>
			 <h:outputLink id="showResourceLTI" value="#{editSectionPage.previewContentData}" target="_blank" title="Section Resource" styleClass="toolUiLink" rendered="#{editSectionPage.previewContentData != null && editSectionPage.previewContentData != editSectionPage.emptyString}">	  
  				 <h:graphicImage id="contenttype_gifLTI" alt="#{msgs.edit_list_resources_content_url}" title="#{msgs.edit_list_resources_content_url}" value="/images/web_service.png" styleClass="ExpClass" />
  				 <h:outputText value="View" />
  			</h:outputLink>		
  			
  			<!-- replace -->  	
  			<h:commandLink id="serverLTIButton"  actionListener="#{editSectionPage.setServerLTIListener}" styleClass="toolUiLink">
				<f:param name="sectionId" value="#{editSectionPage.editId}" />
				<h:outputText value=" " styleClass="ExtraPaddingClass"/>
				<h:graphicImage id="replaceImg2" value="/images/replace2.gif" styleClass="AuthImgClass"/>
				<h:outputText value="#{msgs.editcontentlinkview_replace}"/>
            </h:commandLink>				
		</h:column>	
		
		<!--  open in new window -->
		<h:column/>
		<h:column>     	
          <h:selectBooleanCheckbox id="windowopen" title="openWindow" value="#{editSectionPage.section.openWindow}" />
		  <h:outputText id="editltiText_8" value="#{msgs.editcontentlinkserverview_openwindow}" />
        </h:column>
		
		<!-- title -->	
		<h:column rendered="#{(editSectionPage.shouldRenderLink || editSectionPage.shouldRenderLTI)}" >
			 <h:outputText value="#{msgs.editsec_resources_proper_pan_URLTitle}"  rendered="#{(editSectionPage.shouldRenderLink || editSectionPage.shouldRenderLTI)}" />
			 <h:outputText value="*" styleClass="required" rendered="#{(editSectionPage.shouldRenderLink || editSectionPage.shouldRenderLTI)}"/>
		</h:column>	 
		<h:column rendered="#{(editSectionPage.shouldRenderLink || editSectionPage.shouldRenderLTI)}">				
			 <h:inputText id="res_name" size="45" value="#{editSectionPage.secResourceName}" styleClass="formtext" onfocus="fillTitle()"/>			 		
		</h:column>
		
		<!-- Extra LTI Info -->
		<h:column rendered="#{(editSectionPage.shouldRenderLTI)}" >
			 <h:outputText value="#{msgs.editsec_resources_proper_pan_launchKey}"  /><h:outputText value="*" styleClass="required" />
		</h:column>	 
			<h:column rendered="#{(editSectionPage.shouldRenderLTI)}">				
			 <h:inputText id="res_key" size="45" value="#{editSectionPage.LTIKey}" styleClass="formtext" />		
		</h:column>	
		<h:column rendered="#{(editSectionPage.shouldRenderLTI)}" >
			 <h:outputText value="#{msgs.editsec_resources_proper_pan_launchSecret}"  /><h:outputText value="*" styleClass="required" />
		</h:column>	 
			<h:column rendered="#{(editSectionPage.shouldRenderLTI)}">				
			 <h:inputText id="res_secret" size="45" value="#{editSectionPage.LTIPassword}" styleClass="formtext" />		
		</h:column>	
		<h:column>
			<h:outputText value="#{msgs.editsec_resources_proper_pan_launchCustom}" rendered="#{editSectionPage.shouldRenderLTI}"  />											
		</h:column>	
		<h:column>		
			<h:inputTextarea id="res_custom" cols="45" rows="3" value="#{editSectionPage.customParameters}" styleClass="formtext"  
			rendered="#{editSectionPage.shouldRenderLTI}"  />																			
		</h:column>
		<!--end LTI -->
		
		<!--  alt text -->					                  
		<h:column>
			<h:outputText value="#{msgs.editsec_resources_proper_pan_description}" rendered="#{editSectionPage.shouldRenderUpload}"  />											
		</h:column>	
		<h:column>		
			<h:inputText id="res_desc" size="75" value="#{editSectionPage.secResourceDescription}" styleClass="formtext"  
			rendered="#{editSectionPage.shouldRenderUpload}"  onfocus="fillTitle()" />																			
		</h:column>						
	</h:panelGrid>

	<!-- copyright license code -->
    <f:subview id="LicenseForm" rendered="#{!editSectionPage.shouldRenderNotype}">
			<jsp:include page="licenseform.jsp?formName=EditSectionForm"/>  
	</f:subview>
		
			        <!-- end license code -->		
