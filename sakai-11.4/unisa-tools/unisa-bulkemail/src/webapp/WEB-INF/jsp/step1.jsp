<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.bulkemail.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="page.generalheading"/>
	</sakai:heading>
	<sakai:instruction>
		<fmt:message key="page.step1.instruction"/>
	</sakai:instruction>
	<sakai:group_heading>
		<fmt:message key="page.step1.groupheading"/>
	</sakai:group_heading>
	<p><!-- fmt:message key="page.step1.notice"/--></p>
	
	<html:form action="bulkEmailAction.do?action=step2">
		<sakai:group_table>
			<logic:present name="sitesPerLecturer">
			<logic:iterate id="sites" name="sitesPerLecturer" indexId="index">
				<tr>
					<td><html:multibox property="indexNrOfSelectedSite"><bean:write name="index"/></html:multibox></td>
					<td><bean:write name="sites" property="lecturerSites"/></td>
				</tr>
			</logic:iterate>
			</logic:present>
			<tr>
				<sakai:actions>
					<td><html:submit value="Continue" disabled="${bulkEmailForm.noSitesExists == true}"/></td>
					
				</sakai:actions>
			</tr>
		</sakai:group_table>
 	</html:form>
	
</sakai:html>
