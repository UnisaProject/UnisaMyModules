<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.welcome.ApplicationResources"/>

<sakai:html>
	<html:form action="welcomedisplay">
		<c:if test="${welcomedisplayform.maintainUser == true}">
			<sakai:tool_bar>
				<html:link href="welcomedisplay.do?action=edit">
					<fmt:message key="function.headingEdit"/>
				</html:link>
			</sakai:tool_bar>
		</c:if>
		<%-- Unisa Changes:2018/09/27:Rename content variable to eliminate conflict with ckeditor v4.5.7 --%>
		<%-- <bean:write name="welcomedisplayform" property="content" filter="none"/> --%>
		<bean:write name="welcomedisplayform" property="welcomeContent" filter="none"/>
	</html:form>
</sakai:html>
