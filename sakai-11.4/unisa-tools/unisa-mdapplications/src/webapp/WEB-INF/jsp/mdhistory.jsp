<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdapplications.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/mdapplications">
	<html:hidden property="page" value="history"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="md.page4.header"/></sakai:heading>

		<sakai:group_table>
		<tr>
			<td colspan="3"><strong><fmt:message key="md.page4.tertiary"/></strong></td>
		</tr><tr>
			<td><strong><fmt:message key="md.page4.ha1"/></strong></td>
			<td><strong><fmt:message key="md.page4.ha2"/></strong></td>
			<td><strong><fmt:message key="md.page4.ha5"/></strong></td>
		</tr><tr>
			<td><strong><fmt:message key="md.page4.hb1"/></strong></td>
			<td><strong><fmt:message key="md.page4.hb2"/></strong></td>
			<td><strong><fmt:message key="md.page4.hb5"/></strong></td>
		</tr><tr>
			<td><fmt:message key="md.page4.hc1"/></td>
			<td><fmt:message key="md.page4.hc2"/></td>
			<td><strong><fmt:message key="md.page4.hc5"/></strong></td>
		</tr>

		<logic:iterate name="mdApplicationsForm" property="mdprevList" id="record" indexId="index"> 
		<tr>
			<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histUniv"%>' maxlength="30" size="40"/></td>
			<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histQual"%>' maxlength="20" size="30"/></td>
			<td><html:text name="mdApplicationsForm" property='<%="mdprevList[" + index + "].histComplete"%>' maxlength="4" size="5"/></td> 
		</tr>				
		</logic:iterate>
		</sakai:group_table>
		<sakai:group_table>
		<tr>
			<td><fmt:message key="md.page4.q3"/>&nbsp;<sakai:required/></td>
			<td colspan="2">
				<html:select property="selectedInterestArea">
 					<html:options collection="interestArealist" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		</sakai:group_table>
		<sakai:group_table>
		<tr>
			<td colspan="3"><fmt:message key="md.page4.q2"/>&nbsp;<sakai:required/></td>
		</tr><tr>
			<td colspan="3"><html:textarea name="mdApplicationsForm" property="student.researchTopic" rows="4" cols="110"/></td>
		</tr><tr>
			<td colspan="3"><fmt:message key="md.page4.q4"/>&nbsp;<sakai:required/></td>
		</tr><tr>
			<td colspan="3"><html:text name="mdApplicationsForm" property="student.lecturer" maxlength="80" size="70"/></td>
		</tr><tr>
			<td colspan="2"><fmt:message key="md.page4.q5"/>&nbsp;<sakai:required/></td>
			<!-- <td><html:text name="mdApplicationsForm" property="student.passedndp" maxlength="1" size="1"/></td>  -->
			<td>
				<html:radio property="student.passedndp" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.passedndp" value="N"/><fmt:message key="page.no"/>
			</td>
		</tr><tr>
			<td colspan="2"><fmt:message key="md.page4.q6"/>&nbsp;<sakai:required/></td>
			<!-- <td><html:text name="mdApplicationsForm" property="student.appliedmd" maxlength="1" size="1"/></td>  -->
			<td>
				<html:radio property="student.appliedmd" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.appliedmd" value="N"/><fmt:message key="page.no"/>
			</td>
		</tr><tr>
			<td colspan="2"><fmt:message key="md.page4.q7"/>&nbsp;<sakai:required/></td>
			<!-- <td><html:text name="mdApplicationsForm" property="student.appliedqual" maxlength="1" size="1"/></td>  -->
			<td>
				<html:radio property="student.appliedqual" value="Y"/><fmt:message key="page.yes"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<html:radio property="student.appliedqual" value="N"/><fmt:message key="page.no"/>
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