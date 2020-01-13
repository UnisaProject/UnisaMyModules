<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<html:errors/>
	<sakai:heading><fmt:message key="page.studentviewb.heading"/></sakai:heading>
	<sakai:instruction>
		<br/><fmt:message key="page.studentviewb.note"/><br/><br/>
		<fmt:message key="page.studentviewb.select"/>
	</sakai:instruction>

	<html:form action="contactusaction.do?action=libraryCategorySelected">
		<sakai:group_table>
			<tr>
				<td><html:radio property="libraryCategorySelected" value="pin" /></td>
				<td><fmt:message key="page.studentviewb2.query.pin"/></td>				
			</tr>
			<tr> 
				<td><html:radio property="libraryCategorySelected" value="articles" /></td>
				<td><fmt:message key="page.studentviewb2.query.articles"/></td>				
			</tr>
			<tr> 
				<td><html:radio property="libraryCategorySelected" value="oasisaccount" /></td>
				<td><fmt:message key="page.studentviewb2.query.oasisaccount"/></td>				
			</tr>
			<tr>
				<td><html:radio property="libraryCategorySelected" value="oasiscatalogue" /></td>
				<td><fmt:message key="page.studentviewb2.query.oasiscatalogue"/></td>				
			</tr>
			<tr>
				<td><html:radio property="libraryCategorySelected" value="books" /></td>
			    <td><fmt:message key="page.studentviewb2.query.books"/></td>				
			</tr>
			<tr>
				<td><html:radio property="libraryCategorySelected" value="general" /></td>
				<td><fmt:message key="page.studentviewb2.query.general"/></td>				
			</tr>
			<tr>
				<td><html:radio property="libraryCategorySelected" value="access" /></td>
				<td><fmt:message key="page.studentviewb2.query.access"/></td>				
			</tr>
			<tr>				
				<td><html:radio property="libraryCategorySelected" value="database" /></td>
				<td><fmt:message key="page.studentviewb2.query.database"/></td>
			</tr>
			<tr>
				<sakai:actions>
					<td><html:submit property="button" value="Continue"/></td>
					<td><html:submit property="button" value="Back"/></td>
					</td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>

</sakai:html>
