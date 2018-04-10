<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.filemgrutil.ApplicationResources"/>

<sakai:html>
<html:form action="/filemanager.do">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
		<fmt:message key ="filemanager.webinputprompt.instruction1"/>
		 <bean:write name="utilDetailsForm" property="file_name"/>
   
   			 <fmt:message key ="filemanager.webinputprompt.instruction2"/>
   			
		</sakai:heading>
		
		<sakai:actions>
		<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
		
			</sakai:actions>
		</html:form>

</sakai:html>
