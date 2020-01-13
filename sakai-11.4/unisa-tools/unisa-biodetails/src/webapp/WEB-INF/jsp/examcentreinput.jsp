<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>
<head>
	<SCRIPT type='text/javascript' language='javascript'>
 	function confirmPrison(pCheck) { 
 		var value = pCheck.value;
 		mySplitResult = value.split("@");
		if (mySplitResult[1] == "P"){
			var chkP = confirm("You have selected a correctional facility as exam center. Click on 'OK' to confirm or on 'Cancel' to select a new exam centre.");
			if (chkP == false) {
			   	document.getElementById("selectedExamCentre").selectedIndex = 0;
			   	bioDetailsForm.selectedExamCentre.focus();
			}
		}
	}
 	</SCRIPT>
</head>
<!-- Form -->
	<html:form action="examcentreaction">
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.exam"/></sakai:heading>			
		<!-- Exam centre-->
		<sakai:instruction>
			<fmt:message key="page.exam.instruction1"/><br/>
			<fmt:message key="page.address.instruction9"/><br/>
			<fmt:message key="page.required.instruction"/>&nbsp;<sakai:required/><br/><br/>
			<strong><fmt:message key="page.exam.instruction2"/></strong>
			<fmt:message key="page.exam.instruction3"/><br/><br/>
			<fmt:message key="page.exam.instruction4"/><br/>
			&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.exam.instruction5"/><br>
			&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.exam.instruction6"/><br>
			&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="page.exam.instruction7"/><br>				
		</sakai:instruction>		
		<sakai:group_heading><fmt:message key="page.exam.heading"/></sakai:group_heading>
		<sakai:group_table>
		<tr>
			<td><strong><fmt:message key="page.change.exam"/>&nbsp;<sakai:required/></strong></td>
			<td>
				<html:select property="selectedExamCentre" styleId="selectedExamCentre" onchange="confirmPrison(this);">
				<!-- <html:option value="">------------------------------------------------------------------------------------------</html:option>   -->
					<html:option value="">Select an exam centre</html:option>
					<html:options collection="listexam" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>	
		</sakai:group_table>
		<sakai:actions>	
			<html:hidden property="action" value="request"/>
			<html:submit><fmt:message key="button.request"/></html:submit>			
			<html:cancel><fmt:message key="button.cancel"/></html:cancel>			
		</sakai:actions>		
	</html:form>
</sakai:html>