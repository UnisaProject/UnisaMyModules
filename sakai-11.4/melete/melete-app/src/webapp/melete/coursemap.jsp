<!--
 ***********************************************************************************
 * $URL: https://source.etudes.org/svn/apps/melete/tags/2.9.9/melete-app/src/webapp/melete/coursemap.jsp $
 * $Id: coursemap.jsp 3647 2012-12-02 22:30:41Z ggolden $  
 ***********************************************************************************
 *
 * Copyright (c) 2011 Etudes, Inc.
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
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>

 <html>
  <head>
  </head>
  <body>  
	  <f:view>
		  <h:form id="backCM" >
		  	<h:inputHidden id="rUrl" value="#{meleteSiteAndUserInfo.navigateCM}" />
		  	<script language="JavaScript">
			  var s = document.getElementById("backCM:rUrl").value;
			  parent.location.replace(s);
		  </script>
		  </h:form>
	  </f:view>
  </body>
 </html> 