<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.canceluser.ApplicationResources"/>

<sakai:html>
	<html:form action="/cancelUserAction.do" >
		<sakai:heading>
   			<fmt:message key ="function.confirm"/><br>
		</sakai:heading>
		<sakai:messages/>
		<sakai:messages message="true"/>
		

	<sakai:flat_list>
		<tr>
			<th><fmt:message key="function.student"/></th>
			<th><fmt:message key="function.name"/></th>
			<logic:notEqual name="cancelUserForm" property="cancellationOption" value="myLife">
			       <th><fmt:message key="function.join"/></th>
			</logic:notEqual>
			<logic:equal name="cancelUserForm" property="cancellationOption" value="myLife">
				   <th><fmt:message key="function.reason"/></th>
		    </logic:equal>
		    <logic:notEqual name="cancelUserForm" property="cancellationOption" value="myLife">
			       <th><fmt:message key="function.blocked"/></th>
			</logic:notEqual>
		</tr>

	<tr>
		<td><bean:write name = "cancelUserForm" property ="studentNr"/></td>
		<td><bean:write name = "cancelUserForm" property ="studentName"/></td>
		<logic:notEqual name="cancelUserForm" property="cancellationOption" value="myLife">
		      <td><bean:write name = "cancelUserForm" property ="joinDate"/></td>
		</logic:notEqual>
		<logic:equal name="cancelUserForm" property="cancellationOption" value="myLife">
		      <td><bean:write name = "cancelUserForm" property ="reasonForCans"/></td>
		</logic:equal>
		<logic:notEqual name="cancelUserForm" property="cancellationOption" value="myLife">
		      <td><bean:write name = "cancelUserForm" property ="blocked"/></td>
		</logic:notEqual>
		
	</tr>
		</sakai:flat_list>
<sakai:actions>
				<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>
