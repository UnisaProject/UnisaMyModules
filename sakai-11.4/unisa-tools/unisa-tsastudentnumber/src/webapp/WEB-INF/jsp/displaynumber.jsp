<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.tsastudentnumber.ApplicationResources"/>

<sakai:html>
	
<!-- Form -->
	<html:form action="/displaystudentnumber">
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>	
		
		<logic:equal name="studentNumberForm" property="input" value="true">	
			<sakai:instruction>
				<fmt:message key="page.instruction"/>
			</sakai:instruction>
		</logic:equal>
					
		<sakai:flat_list>
		<tr>
			<logic:equal name="studentNumberForm" property="input" value="true">
				<td><fmt:message key="page.tsa.studentnumber"/>&nbsp;<sakai:required/>&nbsp;<html:text property="tsaStudentNumber" size="13" maxlength="12"/>&nbsp;</td>
			</logic:equal>	
			<logic:equal name="studentNumberForm" property="input" value="false">
				<td><fmt:message key="page.unisa.studentnumber"/>&nbsp;<bean:write name="studentNumberForm" property="unisaStudentNumber" />&nbsp;</td>
			</logic:equal>
		<tr>	
		</sakai:flat_list>
		
		<sakai:actions>	
			<logic:equal name="studentNumberForm" property="input" value="true">	
				<html:hidden property="action" value="submit"/>
				<html:submit><fmt:message key="button.submit"/></html:submit>	
			</logic:equal>	
			<logic:equal name="studentNumberForm" property="input" value="false">	
				<html:hidden property="action" value="back"/>
				<html:submit><fmt:message key="button.back"/></html:submit>
			</logic:equal>	
		</sakai:actions>
	</html:form>
	
</sakai:html>