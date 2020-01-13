<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
	<html:form action="/additions">
	<html:hidden property="page" value="3"/>
	<html:hidden property="goto" value="3"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.step2b.instruction1"/>
			<fmt:message key="page.step2b.instruction2"/>
		</sakai:instruction>
		<sakai:group_heading><fmt:message key="page.heading.step2b"/></sakai:group_heading>
		<sakai:group_table>
		<logic:equal name="regDetailsForm" property="newQual.qualCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td><fmt:message key="page.additions.noqual"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="newQual.qualCode" value="">
			<tr>
				<td><fmt:message key="page.qual"/></td>
				<td><bean:write name="regDetailsForm" property="newQual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.qualDesc"/></td>
			</tr>
		</logic:notEqual>
		<logic:equal name="regDetailsForm" property="newQual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td><fmt:message key="page.additions.nospec"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="newQual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td><bean:write name="regDetailsForm" property="newQual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.specDesc"/></td>
			</tr>
		</logic:notEqual>
		<tr>
			<td colspan="2"><fmt:message key="page.displayMethod"/></td>
		</tr>
		<tr>
			<td colspan="2"><html:radio property="listType" value="S"/><fmt:message key="page.step2b.radio1"/></td>
		</tr>		
		<tr>
			<td colspan="2"><html:radio property="listType" value="A"/><fmt:message key="page.step2b.radio3"/></td>
		</tr><tr>
			<td colspan="2"><html:radio property="listType" value="T"/><fmt:message key="page.step2b.radio4"/></td>
		</tr><tr>
			<td colspan="2"><fmt:message key="page.order"/></td>
		</tr><tr>
			<td colspan="2"><html:radio property="orderType" value="C"/><fmt:message key="page.order.code"/></td>
		</tr><tr>
			<td colspan="2"><html:radio property="orderType" value="D"/><fmt:message key="page.order.desc"/></td>
		</tr><tr>
			<td colspan="2"><fmt:message key="page.number"/></td>
		</tr><tr>
			<td colspan="2">
				<html:select property="numberOfUnits">
 					<html:options collection="numbers" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		</sakai:group_table>

		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
			<html:submit property="action"><fmt:message key="button.back"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>

</sakai:html>