<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	
	<sakai:heading>
			<fmt:message key="page.studentviewc3.heading"/>
	</sakai:heading>

	<html:form action="contactusaction.do?action=forgotStudentNumberForm">
		<sakai:instruction>
			<fmt:message key="page.studentviewc3.note"/><br/><br/>
			<fmt:message key="page.studentviewc3.requireditems"/>
		</sakai:instruction>
	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.studentviewc3.surname"/></td>
				<td><html:text property="surname" maxlength="28" value="${contactusform.forgotSurname}"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.maiden"/></td>
				<td><html:text property="maiden" maxlength="28"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.firstnames"/></td>
				<td><html:text property="firstNames" maxlength="60" value="${contactusform.forgotName}"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.dob"/></td>
				<td>
					<html:text property="birthDateY" size="4" maxlength="4" value="${contactusform.forgotdoby}"/> / 
					<html:text property="birthDateM" size="2" maxlength="2" value="${contactusform.forgotdobm}"/>/ 
					<html:text property="birthDateD" size="2" maxlength="2" value="${contactusform.forgotdobd}"/>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.id"/></td>
					<td><html:text property="id" maxlength="13"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.or"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.passport"/></td>
					<td><html:text property="passport" maxlength="30"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.email"/></td>
					<td><html:text property="email" maxlength="70"/></td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewc3.postaladdress"/></td>
				<td colspan="2">
					<html:textarea property="postalAddress" rows="10" cols="20"/>
				</td>
			</tr>
			<tr>
				<sakai:actions>
					<td>
						<html:submit property="button" value="Continue"></html:submit>
						<html:submit property="button" value="Cancel"></html:submit>
						<html:submit property="button" value="Clear"></html:submit>
					</td>
				</sakai:actions>
			</tr>
		</sakai:group_table>
	
	</html:form>
	
</sakai:html>