<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.mdadmission.ApplicationResources" />

<script language="javascript">
	function disableLookupList()
	{
		var selectedStudent=document.getElementById("selectedStudent");
		var studentNumber = document.getElementById("student.studentNumber").value;
		if (studentNumber.match(/^\s*$/)) 
		{
			selectedStudent.disabled = false;
		}
		else
		{
			selectedStudent.disabled = true;
		}
	}

	//window.onload=disableLookupList ; 
</script>
<script language="javascript">
	function doAction() {
		document.mdAdmissionForm.action = 'displaymdadmission.do?act=display';  
		document.mdAdmissionForm.submit();
	}
</script>

<sakai:html>
<html:form action="/displaymdadmission" >
	<html:hidden property="act" value="display" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page.heading" />
	</sakai:heading>


	<sakai:group_table>
		<tr>
			<td><sakai:instruction>
				<fmt:message key="page.instruction1" />
			</sakai:instruction></td>
		</tr>
		<tr>
			<td><strong><fmt:message key="page.heading.studentnr" /></strong></td>
			<td><html:text name="mdAdmissionForm"
				property="student.studentNumber" styleId="student.studentNumber"
				size="10" maxlength="8" onkeyup="disableLookupList()" /></td>
		</tr>
	</sakai:group_table>


	<logic:notEmpty name="studentLookupList">
		<sakai:group_table>
			<tr>
				<td><strong>OR</strong></td>
			</tr>
		</sakai:group_table>
	</logic:notEmpty>
	<!-- UNCOMMENT if need to capture student number
	<logic:empty name="studentLookupList">
	</logic:empty>
	

	<logic:empty name="studentLookupList">
		<sakai:group_table>
			<tr>
				<td>
				<p class=information><fmt:message
					key="page.information.nostudents" /></p>
				</td>
			</tr>
		</sakai:group_table>
	</logic:empty>
	
	-->

	<logic:notEmpty name="studentLookupList">
		<sakai:group_table>
			<tr>
				<td><sakai:instruction>
					<fmt:message key="page.instruction2" />
				</sakai:instruction></td>
			</tr>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr" /></strong>&nbsp;
				<html:select property="selectedStudent" styleId="selectedStudent"
					onclick="disableLookupList()" style="font-family:courier">
					<html:options collection="studentLookupList" property="value"
						labelProperty="label" />
				</html:select> <br />
				<br />
				</td>
			</tr>
		</sakai:group_table>
	</logic:notEmpty>	

	<logic:notEmpty name="studentLookupList">	
		<sakai:actions>
			<html:submit property="act" onclick="javascript:disabled=true;doAction();">
				<fmt:message key="button.continue" />
			</html:submit>
			<logic:equal name="mdAdmissionForm" property="oldUniflowInUse" value="true">
				<sakai:instruction><fmt:message key="page.information.docExtraction"/></sakai:instruction>
				<sakai:instruction><fmt:message key="page.information.UniflowDownTimes"/></sakai:instruction>
			</logic:equal>
		</sakai:actions>
	</logic:notEmpty>
	
	 <script>
	   disableLookupList();
    </script>

</html:form>
</sakai:html>
