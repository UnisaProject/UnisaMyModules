<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="eshop.addadvert.title"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="eshop.addadvert.bookdetails.description"/>
	</sakai:instruction>
	<sakai:instruction>
		<fmt:message key="eshop.addadvert.bookdetails.description2"/>
	</sakai:instruction>
	<sakai:instruction>
			<fmt:message key="eshop.addadvert.yourdetails.message"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="eshop.addadvert.bookdetails.description1"/>
		</sakai:instruction>
	<sakai:group_heading>
		<fmt:message key="eshop.addadvert.bookdetails.groupheading"/>
	</sakai:group_heading>

	<html:form action="eshop.do?action=processNewAdd">
		<sakai:group_table>
			<tr>
					<td><fmt:message key="eshop.addadvert.bookdetails.coursecode"/></td>
					<td><html:text property="advert.courseCode" size="10" maxlength="7"/></td>
			</tr>
			<tr>
					<td><fmt:message key="eshop.addadvert.bookdetails.addheading"/></td>
					<td><html:text property="advert.addHeading" size="30" maxlength="128"/></td>
			</tr>
			<tr>
					<td><fmt:message key="eshop.addadvert.bookdetails.addtext"/></td>
					<td><html:textarea property="advert.addText" cols="40" rows="3"/></td>
			</tr>

			<fmt:message key="eshop.addadvert.bookdetails.exampletext"/>
		</sakai:group_table>
		<sakai:group_heading>
			<fmt:message key="eshop.addadvert.yourdetails.groupheading"/>
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="eshop.addadvert.yourdetails.description"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="eshop.addadvert.yourdetails.studentnumber"/>
				</td>
				<td>
					<html:text property="student.studentNumber" size="20"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="eshop.addadvert.yourdetails.contactdetails"/>
				</td>
				<td>
					<html:text property="advert.contactDetails" size="20" maxlength="128"/>
				</td>
			</tr>
		</sakai:group_table>

		<sakai:instruction><fmt:message key="eshop.addadvert.yourdetails.notice"/></sakai:instruction>

		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="eshop.addadvert.yourdetails.password"/>
				</td>
				<td>
					<html:password property="student.password" size="20" maxlength="10"/>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="eshop.addadvert.yourdetails.confirmpassword"/>
				</td>
				<td>
					<html:password property="student.confirmPassword" size="20" maxlength="10"/>
				</td>
			</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.submit"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.preview"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.clear"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>