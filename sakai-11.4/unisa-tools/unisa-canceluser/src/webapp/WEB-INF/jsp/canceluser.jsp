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
   			<fmt:message key ="function.useraccount"/><br>
		</sakai:heading>

		<sakai:messages/>
		<sakai:messages message="true"/>
		
		<fmt:message key = "function.instruction1"/>
	
		<sakai:group_table>
			<tr>
				<td><fmt:message key="function.studno"/>&nbsp;<sakai:required/></td>
				<td> <html:text property="studentNr" size="10" maxlength="8"></html:text> </td>
			</tr>
				<tr>
				<td><fmt:message key="function.reason"/>&nbsp;<sakai:required/></td>
				<td> <html:text property="reasonForCans" size="25" maxlength="100"></html:text> </td>
			</tr>
			<tr>
			<td><fmt:message key="function.blockStud"/></td>
			<td><html:checkbox property="blockCheck"></html:checkbox></td>
			
		</tr>
		<tr>
		<td><fmt:message key="function.radio"/></td>
		<td><html:radio name="cancelUserForm" property="cancellationOption" value="both"><b> myUnisa and myLife </b></html:radio></td>

		</tr><tr><td></td>
		<td><html:radio name="cancelUserForm" property="cancellationOption" value="one"><b> myUnisa </b></html:radio></td>
		 </tr>
		</tr><tr><td></td>
		<td><html:radio name="cancelUserForm" property="cancellationOption" value="myLife"><b> myLife</b></html:radio></td>
		</tr>
	</sakai:group_table>
	
		<sakai:actions>
				<html:submit property="action">
				 <fmt:message key="button.continue"/>
			    </html:submit>
        </sakai:actions>
	</html:form>
</sakai:html>

