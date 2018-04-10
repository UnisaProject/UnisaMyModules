<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.trackandtrace.ApplicationResources"/>

<sakai:html>
	

		<html:form method="GET" action="/displayParcelTracking" focus="student.number">
		<sakai:messages/>
	<sakai:messages message="true"/>

			<sakai:instruction>
		<h3><fmt:message key="function.heading"/></h3><br></sakai:instruction>
		
		<sakai:group_table>
			<tr>
				<td><fmt:message key="function.studno"/>&nbsp;</td>
				<td><html:text property="student.number" size="10" maxlength="8"/></td>
			</tr>
	</sakai:group_table>
	
		
		<sakai:actions>
			<html:submit property="action">
			<fmt:message key="button.displayfd"/></html:submit>
		
		</sakai:actions>
		
			
		</html:form>
</sakai:html>
