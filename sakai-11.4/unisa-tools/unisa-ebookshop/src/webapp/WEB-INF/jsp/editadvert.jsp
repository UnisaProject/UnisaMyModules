<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="eshop.editadvert.title"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="eshop.editadvert.instruction"/>
	</sakai:instruction>

	<sakai:group_heading>
		<fmt:message key="eshop.editadvert.bookdetails.groupheading"/>
	</sakai:group_heading>

	<html:form action="eshop.do?action=editAdvert">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="eshop.editadvert.bookdetails.coursecode"/></td>
				<td><html:text property="advert.courseCode" maxlength="7"/></td>
			</tr>
			<tr>
				<td><fmt:message key="eshop.editadvert.bookdetails.addheading"/></td>
				<td><html:text property="advert.addHeading" size="30"/></td>
			</tr>
			<tr>
				<td><fmt:message key="eshop.editadvert.bookdetails.addtext"/></td>
				<td><html:textarea property="advert.addText" cols="40" rows="3"/></td>
			</tr>
		</sakai:group_table>
		<fmt:message key="eshop.editadvert.bookdetails.exampletext"/><br/>

		<sakai:group_heading>
			<fmt:message key="eshop.editadvert.yourdetails.groupheading"/>
		</sakai:group_heading>

		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="eshop.editadvert.yourdetails.contactdetails"/>
				</td>
				<td>
					<html:text property="advert.contactDetails" size="20"/>
				</td>
			</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="editButton"><fmt:message key="eshop.button.submit"/></html:submit>
			<html:submit property="editButton"><fmt:message key="eshop.button.preview"/></html:submit>
			<html:submit property="editButton"><fmt:message key="eshop.button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>