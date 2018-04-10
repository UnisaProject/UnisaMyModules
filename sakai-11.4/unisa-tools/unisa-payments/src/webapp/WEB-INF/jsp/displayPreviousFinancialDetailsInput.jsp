 
<%@ page language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>
<sakai:html> 
	<html:form action="/displayFinancialDetails" focus="inputYear">
		<sakai:heading><fmt:message key="function.PreviousFinancialDetail"/></sakai:heading>
		<sakai:messages/>
		<fmt:message key="function.inputYear"/> <html:text property="inputYear" size="5" maxlength="4"/><br/>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.displaypfd"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clear"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		<hr/>
		<c:if test="${displayFinancialDetailsForm.studentUser == false}">
		<sakai:heading><fmt:message key="function.student"/> 
					   <bean:write name="displayFinancialDetailsForm" property="student.number"/> : 
					   <bean:write name="displayFinancialDetailsForm" property="student.name"/>&nbsp;
					   <bean:write name="displayFinancialDetailsForm" property="student.initials"/>&nbsp;
					   <bean:write name="displayFinancialDetailsForm" property="student.title"/>
		</sakai:heading>
		</c:if>
	</html:form>
</sakai:html>
