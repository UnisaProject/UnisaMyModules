<!--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.1forSakai/melete-app/src/webapp/melete/addErrorMessage.jsp $
 * $Id: addErrorMessage.jsp 3647 2012-12-02 22:30:41Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2010 Etudes, Inc.
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
<%@ page import="org.etudes.tool.melete.AddResourcesPage"%>
<% 
	final javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
	final AddResourcesPage addPage = (AddResourcesPage)facesContext.getApplication().getVariableResolver().resolveVariable(facesContext, "addResourcesPage");
	String msg= request.getParameter("msg");
	
   	String m  = addPage.getMessageText(msg);  	
   	response.getWriter().write(m);	
%>