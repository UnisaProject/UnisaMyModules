<%@ page import="org.etudes.tool.melete.LicensePage"%>
<!--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/webapp/melete/licenseform.jsp $
 * $Id: licenseform.jsp 3647 2012-12-02 22:30:41Z ggolden $  
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
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@include file="accesscheck.jsp" %>

<t:saveState id="codes" value="#{licensePage.licenseCodes}" />
<t:saveState id="formName1" value="#{licensePage.formName}" />
<t:saveState id="author" value="#{licensePage.copyright_owner}" />
<t:saveState id="year" value="#{licensePage.copyright_year}" />
<t:saveState id="cmrcl" value="#{licensePage.allowCmrcl}" />
<t:saveState id="modifications" value="#{licensePage.allowMod}" />

<h:panelGrid id="SectionView" columns="2" width="100%" columnClasses="col1,col2" rendered="#{licensePage.callFromSection}">
<h:column>
 <h:outputText value="#{msgs.licenseform_resources_proper_pan_cstatus}" />	 
 </h:column>
 <h:column>
 <h:selectOneMenu id="licenseCodes" value="#{licensePage.licenseCodes}" valueChangeListener="#{licensePage.hideLicense}" onchange="saveEditor(); this.form.submit();" >
	 <f:selectItems value="#{licensePage.licenseTypes}" />							
 </h:selectOneMenu>
 <h:outputText value="          " styleClass="ExtraPaddingClass" />
<h:outputLink value="licenses_explained.htm"  target="_blank">  <h:graphicImage value="/images/help.gif" alt="#{msgs.licenseform_options}" title="#{msgs.licenseform_options}" width="16" height="16" styleClass="ExpClass"/></h:outputLink>
 </h:column>
</h:panelGrid> 
 
 <h:panelGrid id="PreferenceView" cellspacing="5" cellpadding="5" columns="1" rendered="#{!licensePage.callFromSection}">
 <h:column>
   <h:outputText value="#{msgs.licenseform_select_license}" styleClass="bold"/>
 </h:column>
 <h:column/>
 <h:column>  	 
   <h:selectOneMenu id="licenseCodes2" value="#{licensePage.licenseCodes}" valueChangeListener="#{licensePage.hideLicense}" onchange="this.form.submit();">
	 <f:selectItems value="#{licensePage.licenseTypes}" />							
   </h:selectOneMenu>
   <h:outputText value="          " styleClass="ExtraPaddingClass" />
  <h:outputLink value="licenses_explained.htm"  target="_blank">  <h:graphicImage value="/images/help.gif" alt="#{msgs.licenseform_options}" title="#{msgs.licenseform_options}" width="16" height="16" styleClass="ExpClass"/></h:outputLink>
 </h:column>  
 <h:column/>
</h:panelGrid>

 <!--license table -->
<h:panelGrid id="LicenseForm" columns="1" cellpadding="3" width="100%" rendered="#{licensePage.shouldRenderCC || licensePage.shouldRenderCopyright || licensePage.shouldRenderPublicDomain || licensePage.shouldRenderFairUse}">
  <h:column>	
    <h:panelGrid id="licensetitle" columns="1" columnClasses="maintabledata8" width="100%">
	<h:column>
		<h:outputText value="#{msgs.licenseform_cclicense}" rendered="#{licensePage.shouldRenderCC}" />
		<h:outputText value="#{msgs.licenseform_cclicense_form_copy_of_aut}" rendered="#{licensePage.shouldRenderCopyright}"/> 
		<h:outputText value="#{msgs.licenseform_cclicense_form_public}" rendered="#{licensePage.shouldRenderPublicDomain}"/> 
		<h:outputText value="#{msgs.licenseform_cclicense_form_fair}" rendered="#{licensePage.shouldRenderFairUse}"/>
	</h:column>
    </h:panelGrid>
    <!-- cc license table -->
    <h:panelGrid id="cclicensetable2" columns="1" width="100%" columnClasses="valignStyle9" rendered="#{licensePage.shouldRenderCC}">
	 <h:column>
		 <h:outputText value="#{msgs.licenseform_cclicense_form_msg1}" />
		 <h:panelGrid id="licenseoptions" columns="2" width="100%" cellpadding="3" border="0" columnClasses="col1a, col2a"> 
		 	<h:column>
				<h:outputText value="#{msgs.licenseform_cclicense_form_allow_commercial}"/>
			</h:column>			
			<h:column>  
             	<h:selectOneRadio id="allowCmrcl" value="#{licensePage.allowCmrcl}">
				  	<f:selectItem itemValue="true" itemLabel="#{msgs.licenseform_cclicense_form_allowmod2}"/>
					<f:selectItem  itemValue="false" itemLabel="#{msgs.licenseform_cclicense_form_allowmod0}"/>
			  </h:selectOneRadio>
		</h:column>			
		<h:column>  
				<h:outputText value="#{msgs.licenseform_cclicense_form_allow_modification}"/>
    	 </h:column>
		 <h:column>          
			    <h:selectOneRadio id="allowMod" value="#{licensePage.allowMod}">
				  	<f:selectItem itemValue="2" itemLabel="#{msgs.licenseform_cclicense_form_allowmod2}"/>
					<f:selectItem itemValue="1" itemLabel="#{msgs.licenseform_cclicense_form_allowmod1}"/>
					<f:selectItem itemValue="0" itemLabel="#{msgs.licenseform_cclicense_form_allowmod0}"/>
				  </h:selectOneRadio>
    	</h:column>
	  </h:panelGrid>
	 </h:column>		
	</h:panelGrid>
    
    <!-- other licenses -->
    <h:panelGrid id="copyrighttable2" columns="1" cellpadding="3" width="100%" rendered="#{licensePage.shouldRenderCopyright || licensePage.shouldRenderPublicDomain || licensePage.shouldRenderFairUse}">
		<h:column>	  
	       <h:outputText value="#{msgs.licenseform_cclicense_form_msg2}"  rendered="#{licensePage.shouldRenderCopyright}"/>		 
	       <h:outputText value="#{msgs.licenseform_cclicense_form_msg3}" rendered="#{licensePage.shouldRenderPublicDomain}"/> 	
	       <h:outputText value="#{msgs.licenseform_cclicense_form_msg4}" rendered="#{licensePage.shouldRenderFairUse}"/> 	 
	  
	     
		</h:column>
	</h:panelGrid>
	 <h:panelGrid id="copyrightoptions" columns="2"  width="100%"  cellpadding="3" border="0" columnClasses="col1a, col2a"> 
		    <h:column>
				 <h:outputText value="#{msgs.licenseform_cclicense_form_lic_holder2}"/> 
	    </h:column>

	    <h:column>
	            <h:inputText id="copy_owner1" value="#{licensePage.copyright_owner}" size="45" styleClass="formtext"/>
	    </h:column>
	    <h:column>
		 		<h:outputText value="#{msgs.licenseform_cclicense_form_lic_year2}"/> 
	    </h:column>
	 	 <h:column>
				<h:inputText id="copy_year1" value="#{licensePage.copyright_year}" size="45" styleClass="formtext"/>
	     </h:column>
	     </h:panelGrid>
  </h:column>
 </h:panelGrid>  

			
 				 					 
