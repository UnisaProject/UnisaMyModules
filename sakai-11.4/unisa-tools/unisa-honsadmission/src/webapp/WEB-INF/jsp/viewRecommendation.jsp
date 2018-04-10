<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.honsadmission.ApplicationResources" />

<sakai:html>
<html:form action="/honsAdmission" >
	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page.viewRecommendation" />
	</sakai:heading>	
	<bean:define id="index" name="honsAdmissionForm" property="selectedSignOffIndex"/>
	<sakai:group_heading><fmt:message key="page.signOffDetail.application" /></sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="prompt.qualification"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].qualification.qualDesc" %>' /></td>
		</tr><tr>
			<td><strong><fmt:message key="prompt.speciality"/></strong></td>
			<td><bean:write name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].qualification.specDesc" %>' /></td>
		</tr>
	</sakai:group_table>	
	<sakai:group_heading><fmt:message key="page.signOffDetail.recommendation" /></sakai:group_heading>
	<sakai:group_table>
		<tr>			
			<td><bean:write name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].recommendationDesc" %>'/></td>
		</tr>
		<logic:notEmpty name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].recommendationComment" %>'>	
			<tr>
				<td><html:textarea name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].recommendationComment" %>' rows="3" cols="100" disabled="true"/></td>					
			</tr>
		</logic:notEmpty>
	</sakai:group_table>		
	<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="prompt.signOffComment"/></strong></td>
			<td><html:textarea name="honsAdmissionForm" property='<%="application.listSignOff[" + Integer.parseInt(index.toString()) + "].comment" %>' rows="3" cols="100" disabled="true"/></td>
		</tr>
	</sakai:group_table>
	
	<sakai:actions>	
		<html:submit property="act">
				<fmt:message key="button.back"/>
		</html:submit>
	</sakai:actions>
</html:form>
</sakai:html>	
	