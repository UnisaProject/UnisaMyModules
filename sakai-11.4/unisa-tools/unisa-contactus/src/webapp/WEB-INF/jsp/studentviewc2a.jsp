<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<html:errors/>

	<sakai:heading>
			<fmt:message key="page.studentviewc2a.heading"/>
	</sakai:heading>

	<html:form action="contactusaction.do?action=studentNumberFound">
			<b><fmt:message key="page.studentviewc2a.subheading"/></b>
		<br/>
		<c_rt:out value="${contactusform.studentNumberFormal}"/>
		<br/>
		<br/>
		<c_rt:if test="${contactusform.studentNumberNonFormal != null}">
			<c_rt:out value="${contactusform.studentNumberNonFormal}"/> <fmt:message key="page.studentviewc2a.nonformalnote"/>
			<br/>
			<br/>
		</c_rt:if>
		<html:submit property="button" value="Back"/>
	</html:form>

</sakai:html>