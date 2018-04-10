<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<html:errors/>
	
	<sakai:heading>
		<fmt:message key="page.studentviewc2c.heading"/>
	</sakai:heading>
	
		<fmt:message key="page.studentviewc2c.subheading"/><br/><br/>
	
	<sakai:group_table>
		<tr>
			<td><fmt:message key="page.studentviewc2c.noteheading"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.studentviewc2c.note1"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.studentviewc2c.note2"/></td>
		</tr>
		<tr>
			<td><fmt:message key="page.studentviewc2c.note3"/></td>
		</tr>
		</tr>
	</sakai:group_table>
	
	<html:form action="contactusaction.do?action=studentNumberNotFound">
		<sakai:group_table>
			<tr>
				<td>
					<html:submit property="button" value="Advisor Assistance"/>
					<html:submit property="button" value="Back"/>
				</td>
			</tr>
		</sakai:group_table>
	</html:form>
</sakai:html>
