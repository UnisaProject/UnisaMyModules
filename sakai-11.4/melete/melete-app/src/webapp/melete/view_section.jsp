<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.etudes.tool.melete.ViewSectionsPage"%>

<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/view_section.jsp $
 * $Id: view_section.jsp 86862 2014-08-08 20:37:38Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2009, 2010, 2011, 2013, 2014 Etudes, Inc.
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
<%
final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
final ViewSectionsPage vsPage = (ViewSectionsPage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "viewSectionsPage");

String moduleId = (String)request.getParameter("moduleId");
String secId = (String)request.getParameter("sectionId");
if ((moduleId != null) &&(secId != null))
{
	if (!moduleId.trim().equals("null") && moduleId.trim().length() > 0) vsPage.setModuleId(Integer.parseInt(moduleId));
	if (!secId.trim().equals("null") && secId.trim().length() > 0) vsPage.setSectionId(Integer.parseInt(secId));
}
String modSeqNo = (String)request.getParameter("moduleSeqNo");
if (modSeqNo != null)
{
	if (!modSeqNo.trim().equals("null") && modSeqNo.trim().length() > 0) vsPage.setModuleSeqNo(Integer.parseInt(modSeqNo));
}
%>
<f:view>
<sakai:view title="Modules: Student View" toolCssHref="/etudes-melete-tool/rtbc004.css">
<%@include file="meleterightscheck.jsp" %>
<script type="text/javascript" language="javascript" src="/etudes-melete-tool/js/sharedscripts.js"></script>
<t:saveState id="vspmod" value="#{viewSectionsPage.module}" />
<t:saveState id="vspsec" value="#{viewSectionsPage.section}" />
<t:saveState id="vspmid" value="#{viewSectionsPage.moduleId}" />
<t:saveState id="vsppsi" value="#{viewSectionsPage.prevSecId}" />
<t:saveState id="vspnsi" value="#{viewSectionsPage.nextSecId}" />
<t:saveState id="vspnso" value="#{viewSectionsPage.nextSeqNo}" />
<t:saveState id="vspmso" value="#{viewSectionsPage.moduleSeqNo}" />

<h:form id="viewsectionform"> 
	<f:subview id="top">
	  <jsp:include page="topnavbar.jsp?myMode=View"/> 
	</f:subview>  
<p></p>   
<table   class="maintableCollapseWithNoBorder">
<tr>
<td colspan="2" align="center">

<f:subview id="topmod">
 	<jsp:include page="view_navigate.jsp"/>
</f:subview>
<!-- breadcrumbs -->
<h:panelGrid id="crumbsItem" columns="1"  style=" border-width:medium; border-color: #E2E4E8">
	<h:column>
		<h:commandLink id="moduleItem" actionListener="#{viewSectionsPage.goCurrentModule}" immediate="true" styleClass="toolUiLinkU">
			<h:outputText id="modTitle" value="#{viewSectionsPage.module.title}"/>			
     	</h:commandLink>
     <h:outputText id="sep" value=" &raquo; "  escape="false" /> <h:outputText id="secTitle" value="#{viewSectionsPage.section.title}" />
</h:column>	
</h:panelGrid>

</td>
</tr>
<tr>
<td colspan="2" align="right">										
  <h:outputLink id="bookmarkSectionLink" value="view_section" onclick="OpenBookmarkWindow(#{viewSectionsPage.section.sectionId},'#{viewSectionsPage.section.title}','','','','Melete Bookmark Window');" styleClass="toolUiLink">
		    	<f:param id="moduleId" name="moduleId" value="#{viewSectionsPage.moduleId}" />
		    	<f:param id="sectionId" name="sectionId" value="#{viewSectionsPage.section.sectionId}" />
	  			<f:param id="sectionTitle" name="sectionTitle" value="#{viewSectionsPage.section.title}" />
	  			<f:param id="moduleSeqNo" name="moduleSeqNo" value="#{viewSectionsPage.moduleSeqNo}" />
	  			<h:graphicImage id="bul_gif" value="/images/bookmark-it.png" alt="" styleClass="AuthImgClass"/>
				      <h:outputText id="bookmarktext" value="#{msgs.bookmark_text}" > </h:outputText>
 	</h:outputLink>		
 <h:outputText value="|"/> 			
 <h:commandLink id="myBookmarksLink" action="#{bookmarkPage.gotoMyBookmarks}" immediate="true" styleClass="toolUiLink">
						<h:graphicImage id="mybook_gif" value="/images/my-bookmarks.png" alt="" styleClass="AuthImgClass"/>
						<h:outputText id="mybks" value="#{msgs.my_bookmarks}" />	
						<f:param name="fromPage" value="view_section" />	
						<f:param name="fromModuleId" value="#{viewSectionsPage.moduleId}" />
                        <f:param name="fromModuleSeqNo" value="#{viewSectionsPage.moduleSeqNo}" />	
                        <f:param name="fromSectionId" value="#{viewSectionsPage.sectionId}" />						
</h:commandLink>
</td>
</tr>
<tr>
<td colspan="2" align="center">	
   <h:outputText id="errMsg2" styleClass="alertMessage" value="#{msgs.url_alert_view}" rendered="#{viewSectionsPage.httpAddressAlert != null && viewSectionsPage.httpAddressAlert == true}" />
</td>
</tr>
<tr>
<td colspan="2" align="left">
     <h:outputText id="mod_seq" value="#{viewSectionsPage.moduleSeqNo}. " styleClass="bold style6" rendered="#{viewSectionsPage.autonumber}"/>
	 <h:outputText id="modtitle" value="#{viewSectionsPage.module.title}" styleClass="bold style6" />
</td>
</tr>    

<tr>
<td colspan="2" align="left">
<h:panelGrid id="sectionContentGrid" columns="1" width="100%" border="0" rendered="#{viewSectionsPage.section != null}">
<h:column>
 	<h:outputText id="sec_seq" value="#{viewSectionsPage.sectionDisplaySequence}. " styleClass="bold style7" rendered="#{viewSectionsPage.autonumber}"/>
 	<h:outputText id="title" value="#{viewSectionsPage.section.title}" styleClass="bold style7"></h:outputText>
</h:column>
<h:column>
	 	<h:outputText id="trackDateStr" value="#{viewSectionsPage.sectionTrackDateStr}" styleClass="italics"/>
</h:column>
<h:column>
	<h:outputText value="#{msgs.view_section_student_instructions} " rendered="#{((viewSectionsPage.section.instr != viewSectionsPage.nullString)&&(viewSectionsPage.section.instr != viewSectionsPage.emptyString))}" styleClass="italics"/>
	<h:outputText id="instr" value="#{viewSectionsPage.section.instr}" rendered="#{((viewSectionsPage.section.instr != viewSectionsPage.nullString)&&(viewSectionsPage.section.instr != viewSectionsPage.emptyString))}"/>
</h:column>
<h:column rendered="#{viewSectionsPage.section.contentType != viewSectionsPage.nullString}">
	<h:inputHidden id="contentType" value="#{viewSectionsPage.section.contentType}"/>
	<h:inputHidden id="openWindow" value="#{viewSectionsPage.section.openWindow}"/>
    
    <!-- links in new window -->
    <h:outputText id="anchorText" rendered="#{((viewSectionsPage.section.contentType == viewSectionsPage.typeLink || viewSectionsPage.section.contentType == viewSectionsPage.typeUpload || viewSectionsPage.section.contentType == viewSectionsPage.typeLTI)&&(viewSectionsPage.contentLink != viewSectionsPage.nullString)&&(viewSectionsPage.section.openWindow == true))}" escape="false">
		<f:verbatim>
		<a id="anchorLink" name="anchorLink" target='new_window' href="${viewSectionsPage.contentLink}" title="${viewSectionsPage.linkName}">${viewSectionsPage.linkName}</a>			
		</f:verbatim>
	</h:outputText>
	
	 <h:outputText rendered="#{((viewSectionsPage.section.contentType == viewSectionsPage.typeLink || viewSectionsPage.section.contentType == viewSectionsPage.typeUpload || viewSectionsPage.section.contentType == viewSectionsPage.typeLTI)&&(viewSectionsPage.contentLink != viewSectionsPage.nullString)&&(viewSectionsPage.section.openWindow == true))}">
    	<f:verbatim>
			<div style="height:150px"></div>
		</f:verbatim>
     </h:outputText>
               
	<!--  links in same window -->	  
 	<h:outputText id="contentFrame" rendered="#{(viewSectionsPage.section.contentType ==viewSectionsPage.typeLink)&&(viewSectionsPage.linkName !=
    viewSectionsPage.nullString)&&(viewSectionsPage.section.openWindow == false)}" escape="false">
		<f:verbatim>
		<iframe id="iframe1" name="iframe1" src="${viewSectionsPage.contentLink}" title="${viewSectionsPage.linkName}" width="100%" height="700px" style="visibility:visible" scrolling= "auto" border="0" frameborder= "0">
		</iframe>
		</f:verbatim>
	</h:outputText>
  
  	<!-- render typeUpload  content in same window --> 
	<h:outputText id="contentUploadFrame" rendered="#{(viewSectionsPage.section.contentType ==viewSectionsPage.typeUpload)&&(viewSectionsPage.section.openWindow == false)}" escape="false">
		<f:verbatim>
		<iframe id="iframe2" name="iframe2" src="${viewSectionsPage.contentLink}" title="${viewSectionsPage.altText}" width="100%" height="700px" style="visibility:visible" scrolling= "auto" border="0" frameborder= "0">
		</iframe>
		</f:verbatim>
	</h:outputText>

	<!-- render typeEditor content with form tags --> 
	<h:outputText id="contentTextFrame" rendered="#{(viewSectionsPage.section.contentType == viewSectionsPage.typeEditor)&& (viewSectionsPage.contentWithHtml == true) &&(viewSectionsPage.content != viewSectionsPage.nullString)}" >
		<f:verbatim>
		<iframe id="iframe3" name="iframe3" src="${viewSectionsPage.contentLink}" width="100%" height="100%" style="visibility:visible" scrolling= "auto" border="0" frameborder= "0">
		</iframe>
		</f:verbatim>
	</h:outputText>

	<!-- render typeEditor content without form tags -->
	<h:outputText value="#{viewSectionsPage.content}" escape="false" rendered="#{(viewSectionsPage.section.contentType == viewSectionsPage.typeEditor) && (viewSectionsPage.contentWithHtml == false) &&(viewSectionsPage.content != viewSectionsPage.nullString)}"/>	

	<!-- render typeLTI content -->
	<h:outputText id="contentLTI" value="#{viewSectionsPage.contentLTI}" 
              rendered="#{((viewSectionsPage.section.contentType == viewSectionsPage.typeLTI)&&(viewSectionsPage.section.openWindow == false))}" escape="false" />	
</h:column>
</h:panelGrid>

</td>

</tr>    
<tr>
<td colspan="2" align="center">
					<f:subview id="bottommod">
						<jsp:include page="view_navigate.jsp"/>
					</f:subview>
</td>
</tr>
</table>
</td>
</tr>
<tr><td>
<table  height="20"  class="maintableCollapseWithNoBorder" >
   	<tr>
	 <td align="center" class="meleteLicenseMsg center"><B>
  			<jsp:include page="license_info.jsp"/>      
         </B></td></tr>
	    </table>
	   
<script type="text/javascript">
	window.onload=function(){
	 var oIframe = document.getElementById("iframe3");
	 if(oIframe)
		 {
	        var oDoc = oIframe.contentWindow || oIframe.contentDocument;
		    if (oDoc.document) {
			oDoc = oDoc.document;	
			oIframe.style.height = oDoc.body.scrollHeight + 40 +"px";				       
		    } 
		    else oIframe.height = oDoc.body.offsetHeight + 40 ; 
		    
		   	for (i=0; i < document.styleSheets.length; i++)
			{
			  var link = document.createElement("link");
			  //finish constructing the links
		       link.setAttribute("rel", "stylesheet");
		       link.setAttribute("type", "text/css");
			   //assign this new link the href of the parent one
		       link.setAttribute("href", document.styleSheets[i].href);
			   oDoc.body.appendChild(link); 
		    }		    						
		  }

	  setMainFrameHeight('<h:outputText value="#{meleteSiteAndUserInfo.winEncodeName}"/>');	
	 $("#iframe1").contents().find("body").prop('title', $("#iframe1").attr('title')); 
	 $("#iframe2").contents().find("body").prop('title', $("#iframe2").attr('title')); 		 
	 $("#iframe2").contents().find("img").prop('alt', $("#iframe2").attr('title'));
	 }
</script>
</h:form>
</sakai:view>
</f:view>

