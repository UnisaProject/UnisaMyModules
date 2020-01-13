<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="za.ac.unisa.lms.tools.contactus.ApplicationResources"/>


<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
	<sakai:heading><fmt:message key="page.studentviewd.heading"/></sakai:heading>

	<sakai:instruction>
		
		<fmt:message key="page.studentviewd.note"/><br/><br/>
		<fmt:message key="page.studentviewd.required"/>
	</sakai:instruction>
	<sakai:group_heading>
		<fmt:message key="page.studentviewd.yourdetails"/>
	</sakai:group_heading>
	<html:form action="contactusaction.do?action=processMyUnisaRequest">
		<sakai:group_table>
			<tr>
				<td><fmt:message key="page.studentviewd.studentnumber"/><td>
				<td><html:text property="myUnisaStudentNumber" size="30" maxlength="8"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewd.email"/><td>
				<td><html:text property="myUnisaEmailAddress" size="30" maxlength="70"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewd.contact"/><td>
				<td><html:text property="myUnisaContactNumber" size="30" maxlength="30"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.studentviewd.enquiry"/><td>
			</tr>
			<tr>
				<td colspan="3"><html:textarea property="myUnisaEnquiryMessage" rows="15" cols="50"/></td>
			</tr>
			<tr>
				<td>
					<html:submit property="button" value="Submit"/>
					<html:submit property="button" value="Cancel"/>
					<html:submit property="button" value="Clear"/>
				</td>
			</tr>
		</sakai:group_table>
		
	</html:form>
</sakai:html>
