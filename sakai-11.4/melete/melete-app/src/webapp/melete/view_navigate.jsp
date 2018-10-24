<!--
 ***********************************************************************************
 * $URL: https://source.sakaiproject.org/contrib/etudes/melete/tags/2.9.9/melete-app/src/webapp/melete/view_navigate.jsp $
 * $Id: view_navigate.jsp 85951 2014-03-14 16:53:27Z mallika@etudes.org $  
 ***********************************************************************************
 *
 * Copyright (c) 2008, 2013 Etudes, Inc.
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

<h:panelGrid id="navSectionsItem" columns="5"  style=" border-width:medium; border-color: #E2E4E8">
	<h:column>
		<h:commandLink id="prevItem" styleClass="toolUiLink" actionListener="#{viewSectionsPage.goPrevNext}" immediate="true"  rendered="#{viewSectionsPage.prevSecId != 0}">
			<h:outputText id="prevMsg" value="#{msgs.view_navigate_prev}"/>	
			<f:param name="modid" value="#{viewSectionsPage.moduleId}" />
			<f:param name="secid" value="#{viewSectionsPage.prevSecId}" />
			<f:param name="modseqno" value="#{viewSectionsPage.moduleSeqNo}" />
     	</h:commandLink>
     	<h:commandLink id="prevMod" styleClass="toolUiLink" actionListener="#{viewSectionsPage.goPrevModule}" immediate="true"  rendered="#{viewSectionsPage.prevSecId == 0}">
			<h:outputText id="prevMsg1" value="#{msgs.view_navigate_prev}"/>	
			<f:param name="modid" value="#{viewSectionsPage.moduleId}" />
			<f:param name="secid" value="#{viewSectionsPage.prevSecId}" />
     	</h:commandLink>
     </h:column>
		<h:column>
				<h:outputText id="seperatorMsg1" value=" | "/>	
		</h:column>
	<h:column>
	<h:commandLink id="TOCitem" styleClass="toolUiLink" action="#{viewSectionsPage.goTOC}" immediate="true">
		  <h:outputText id="TOCMsg" value="#{msgs.view_navigate_TOC}"/>
	</h:commandLink>    
	</h:column>
			<h:column>
				<h:outputText id="seperatorMsg2" value=" | "/>	
		</h:column>
	<h:column>
		<h:commandLink id="nextItem" styleClass="toolUiLink" actionListener="#{viewSectionsPage.goPrevNext}" immediate="true" rendered="#{viewSectionsPage.nextSecId != 0}">
		  <h:outputText id="nextMsg"  value="#{msgs.view_navigate_next}"></h:outputText>
		    <f:param name="modid" value="#{viewSectionsPage.moduleId}" />
            <f:param name="secid" value="#{viewSectionsPage.nextSecId}" />
            <f:param name="modseqno" value="#{viewSectionsPage.moduleSeqNo}" />
     </h:commandLink>
      <h:commandLink id="whatsNext" styleClass="toolUiLink" actionListener="#{viewSectionsPage.goWhatsNext}" immediate="true" rendered="#{((viewSectionsPage.module != null && viewSectionsPage.module.whatsNext != viewSectionsPage.nullString)&&(viewSectionsPage.module != null && viewSectionsPage.module.whatsNext != viewSectionsPage.emptyString)&&(viewSectionsPage.nextSecId == 0))}">
		  <h:outputText id="whatsNextMsg" value="#{msgs.view_navigate_next2}"></h:outputText>
		    <f:param name="modseqno" value="#{viewSectionsPage.moduleSeqNo}" />
     </h:commandLink>   
    <h:commandLink id="nextMod" styleClass="toolUiLink" actionListener="#{viewSectionsPage.goNextModule}" immediate="true" rendered="#{(((viewSectionsPage.module != null && viewSectionsPage.module.whatsNext == viewSectionsPage.nullString && meleteSiteAndUserInfo.navigateCM == null)||(viewSectionsPage.module != null && viewSectionsPage.module.whatsNext == viewSectionsPage.emptyString))&&(viewSectionsPage.nextSecId == 0)&&(viewSectionsPage.moduleSeqNo < viewSectionsPage.nextSeqNo)&& (meleteSiteAndUserInfo.navigateCM == null))}">
		  <h:outputText id="nextModMsg" value="#{msgs.view_navigate_next3}"></h:outputText>
		    <f:param name="modseqno" value="#{viewSectionsPage.nextSeqNo}" />
     </h:commandLink>  
     <h:commandLink id="returnCm" styleClass="toolUiLink" action="#{meleteSiteAndUserInfo.returnToCM}" immediate="true" rendered="#{(((viewSectionsPage.module != null && viewSectionsPage.module.whatsNext == viewSectionsPage.nullString)||(viewSectionsPage.module != null && viewSectionsPage.module.whatsNext == viewSectionsPage.emptyString))&&(viewSectionsPage.nextSecId == 0)&&(viewSectionsPage.moduleSeqNo < viewSectionsPage.nextSeqNo || viewSectionsPage.nextSeqNo == -1)&& (meleteSiteAndUserInfo.navigateCM != null))}">
		  <h:outputText  id="returnCmMsg" value="#{msgs.return_cm_msg}"></h:outputText>
     </h:commandLink>  
    </h:column>
</h:panelGrid>	