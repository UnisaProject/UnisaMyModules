<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:heading>
		<b><fmt:message key="page.studentviewa.contactus"/></b>
	</sakai:heading>
	<sakai:group_table>
			<tr>
				<td><fmt:message key="page.studentviewa.telephonic"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewa.centerlocal"/></td>
				<td><fmt:message key="page.studentviewa.telephonelocal"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewa.centerinternational"/></td>
				<td><fmt:message key="page.studentviewa.telephoneinternational"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewa.note"/></td>
			</tr>
	</sakai:group_table>
	<fmt:message key="page.studentviewa.careerguidance"/>
	<br/><br/>

	<sakai:group_heading>
		<fmt:message key="page.studentviewa.selectenquiry"/>
	</sakai:group_heading>

	<html:form action="contactusaction.do?action=categorySelected">
		<sakai:group_table>
			<tr>
				<td><html:radio property="categorySelected" value="forgot" /></td>
				<td><fmt:message key="page.studentviewa.query.forgottenstudentnumber"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="myUnisa"/></td>
				<td><fmt:message key="page.studentviewa.query.myUnisa"/></td>
			</tr>
			<!--
			<tr>
				<td><html:radio property="categorySelected" value="webRegistration"/></td>
				<td><fmt:message key="page.studentviewa.query.webregistration"/></td>
			</tr>
			-->
			<tr>
				<td><html:radio property="categorySelected" value="currentRegistration"/></td>
				<td><fmt:message key="page.studentviewa.query.currentRegistration"/></td>
			</tr>

			<tr>
				<td><html:radio property="categorySelected" value="account"/></td>
				<td><fmt:message key="page.studentviewa.query.account"/></td>
			</tr>

			<tr>
				<td><html:radio property="categorySelected" value="assignments"/></td>
				<td><fmt:message key="page.studentviewa.query.assignments"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="examinations"/></td>
				<td><fmt:message key="page.studentviewa.query.examinations"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="despatch"/></td>
				<td><fmt:message key="page.studentviewa.query.despatch"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="fellowStudents"/></td>
				<td><fmt:message key="page.studentviewa.query.students"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="graduations"/></td>
				<td><fmt:message key="page.studentviewa.query.graduations"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="prescribedBooks"/></td>
				<td><fmt:message key="page.studentviewa.query.books"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="library"/></td>
				<td><fmt:message key="page.studentviewa.query.library"/></td>
			</tr>
			<tr>
				<td><html:radio property="categorySelected" value="other"/></td>
				<td><fmt:message key="page.studentviewa.query.other"/></td>
			</tr>
			<tr>
				<sakai:actions>
					<td><html:submit><fmt:message key="buttons.continue"/></html:submit></td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	</html:form>

</sakai:html>
