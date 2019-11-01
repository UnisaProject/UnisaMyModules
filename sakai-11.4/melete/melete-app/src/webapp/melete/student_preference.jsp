<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/student_preference.jsp $
 * $Id: student_preference.jsp 80436 2012-06-19 21:48:06Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2010,2011 Etudes, Inc.
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

<f:view>
<sakai:view title="Modules: Student Preference" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="meleterightscheck.jsp" %>
 <h:form id="UserPreferenceForm">
	<!-- top nav bar -->
	<f:subview id="top">
			<jsp:include page="topnavbar.jsp?myMode=Preferences"/> 
	</f:subview>
    <div class="meletePortletToolBarMessage"><img src="/etudes-melete-tool/images/user1_preferences.gif" alt="" width="16" height="16" align="absbottom" border="0"><h:outputText value="#{msgs.student_preference_user_preference}" /></div>				
	<h:messages showDetail="true" showSummary="false" infoClass="BlueClass" errorClass="RedClass"/>
     <table  class="maintableCollapseWithBorder">
		     <tr>
                <td>
				  <table  class="maintableCollapseWithNoBorder">
				  <tr><td  height="20" class="maintabledata5" ><h:outputText id="t2" value="#{msgs.student_preference_view_select}"  styleClass="bold"/>          </td></tr>
			    	<tr>
					 <td>
							<h:selectOneRadio value="#{studentPreferences.userView}" onclick="this.form.submit();" valueChangeListener="#{studentPreferences.changeViewValue}" layout="pageDirection">
										<f:selectItem itemLabel="Expanded" itemValue="true" />
										<f:selectItem itemLabel="Collapsed" itemValue="false"/>
								</h:selectOneRadio>	
					</td>	
					</tr>		
					</table>
					</td></tr>           		 
            </table>         
	</h:form>

  <!-- This Ends the Main Text Area -->
</sakai:view>
</f:view>

