<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.signoff.ApplicationResources"/>

<script language="JavaScript">
	function setAction() {
		document.signoffForm.action = 'signoff.do?act=editStandIn';
		document.signoffForm.submit();
	}
</script>

<sakai:html>
<html:form action="/signoff.do"> 
	<html:hidden property="atStep" value="2"/>
	<html:hidden property="type" value="${signoffForm.edit}"/>
	

	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	<sakai:heading>
	<logic:equal name="signoffForm" property="edit" value="a">
	<tr><td><b>Edit Stand-In COD</b></td></tr>
	</logic:equal>
	<logic:equal name="signoffForm" property="edit" value="b">
	<tr><td><b>Edit Stand-in School Director</b></td></tr>
	</logic:equal>
	<logic:equal name="signoffForm" property="edit" value="c">
	<tr><td><b>Edit Stand-in Dean</b></td></tr>
	</logic:equal>
	</sakai:heading>
	
	<p>
	<table>
		<tr>
			<td>
				<bean:write name="signoffForm"  property="structure"/>&nbsp;<sakai:required/>
			</td>
			<td>
				<html:select name="signoffForm" property="dptCode">
				    <html:option value="-1"><bean:write name="signoffForm"  property="structure"/></html:option>	
					<html:options collection="dptList" property="value" labelProperty="label"/>
				</html:select>
			</td>	
		</tr>
		<tr><td>
		<fmt:message key="signoff.add"/>&nbsp;<sakai:required/>
		</td>
		<td>
		<html:select property="noOfRecords" onchange="setAction();" >
		<html:options collection="noOfRecordsOptions" property="value" labelProperty="label"/>
		</html:select></td>
	</tr>
	
	</table>
	<hr>
	
	<p>
	<table>
		<logic:notEmpty name="signoffForm" property="staffList">
 	
 	<table>
			<logic:iterate name="signoffForm" property="staffList" id="record" indexId="i">
			<tr>
			<td colspan='3'><bean:write name="record"  property="head"/></td></tr>
			  <tr>
			<td> &nbsp; </td>
 	        <td><fmt:message key="signoff.networkcode"/></td>
 	        <td> <html:text name="signoffForm" property='<%= "recordIndexed3["+i+"].networkCode" %>' size="20" maxlength="20"/></td>
 	        </tr>
 	         <tr>
 	         <td> &nbsp; </td>
 	         <td><fmt:message key="signoff.staffnumber"/></td>
 	         <td> <html:text name="signoffForm" property='<%= "recordIndexed3["+i+"].staffNumber" %>' size="20" maxlength="8"/></td>
 	         </tr>
 	         <tr><td> &nbsp; </td></tr>
			</logic:iterate>
			</table>
			</logic:notEmpty>
	</table>
	
	</p>
	<sakai:group_table>
	<tr><td>
	<sakai:actions>
		<html:submit styleClass="button" property="act">
		<fmt:message key="button.submit"/>
		</html:submit>
		<html:submit styleClass="button" property="act">
			<fmt:message key="button.cancel"/>
		</html:submit>
	</sakai:actions>
	</td></tr>
	</sakai:group_table>
</html:form>
</sakai:html>