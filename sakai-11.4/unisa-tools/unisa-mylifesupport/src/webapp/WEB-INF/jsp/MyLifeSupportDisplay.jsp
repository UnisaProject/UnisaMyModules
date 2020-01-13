<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mylifesupport.ApplicationResources"/>

<sakai:html>

	
<html:form method="GET" action="/myLifeSupport">
<script type = "text/javascript" >
function disableBackButton()
{
window.history.forward();
}
setTimeout("disableBackButton()", 0);
</script>

<sakai:messages/>
	<sakai:messages message="true"/>
	
	<sakai:heading><fmt:message key="function.heading"/></sakai:heading> <br/>

		<fmt:message key="function.studno"/>
		<html:text property="studentNr" size="8"></html:text> <br/> <br/>
		
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.display"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clearstno"/></html:submit>	
			</sakai:actions> <br/>
			
	<table>
	
	        <tr><td><fmt:message key="step2.instruction1"/></td><td><html:text property="studentNr" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction1a"/></td><td><html:text property="firstName" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction2"/></td><td><html:text property="surName" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction3"/></td><td><html:text property="studentID" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction3a"/></td><td><html:text property="passportNr" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction4"/></td><td><html:text property="birthDate" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction4a"/></td><td><html:text property="cellNumber" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction4b"/></td><td><html:text property="homeNumber" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction7"/></td><td><html:text property="studentAddress"  size="60"/></td></tr> 
			
			<tr><td><fmt:message key="step2.instruction5"/></td><td><html:text property="mylifEmail" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction6"/></td><td><html:text property="mylifePwd" readonly="true" size="35"/></td></tr>
			
			<tr><td><fmt:message key="step2.instruction8a"/></td><td><html:text property="regDate" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction8"/></td><td><html:text property="regStatus" readonly="true" size="35"/></td></tr>
			
			<tr><td><fmt:message key="step2.instruction9"/></td><td><html:text property="highestQual" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction10"/></td><td><html:text property="specialityCode" readonly="true" size="35"/></td></tr>
									
			<tr><td><fmt:message key="step2.instruction11"/></td><td><html:text property="joinDate" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction12"/></td><td><html:text property="statusFlag" readonly="true" size="35"/></td></tr>
			<tr><td><fmt:message key="step2.instruction13"/></td><td><html:text property="mylifeStatus" readonly="true" size="35"/></td></tr>
			
		
			
	</table>
	
</html:form>
</sakai:html>
