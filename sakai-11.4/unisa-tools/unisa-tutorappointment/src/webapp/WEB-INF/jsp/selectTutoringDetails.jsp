<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tutorappointments.ApplicationResources"/>
<sakai:html>
<html:form action="/tutorAppointments">
	<sakai:group_heading>
		<fmt:message key="tutorappointments." />
	</sakai:group_heading>
	
	<sakai:messages />
	<sakai:messages message="true" />
	
	<table>
		<tr>
			<td>&nbsp;</td>
			<td colspan='2'><fmt:message key="tutorappointments.academicyear" /> <html:select
					name="tutorAppointmentsForm" property='academicYear'>
					<html:options collection="academicYearOptions" property="value" labelProperty="label" />
				</html:select></td>
		</tr>
	</table>
</html:form>
</sakai:html>