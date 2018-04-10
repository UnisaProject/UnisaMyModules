<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.filemgrutil.ApplicationResources"/>

<sakai:html>
	<html:form action="/filemanager.do" >
  <logic:notEmpty name="utilDetailsForm" property="wedids">
<tr><td>
<fmt:message key="annstm.norecord"/>
</td>
<td>
<logic:iterate name="utilDetailsForm"  id="d"  property="wedids" indexId="t">
<td>	
 <bean:write name="utilDetailsForm" property="wedids"/>
 </td>
</logic:iterate>
</td>
</tr>
</logic:notEmpty>
      <sakai:messages/>
		<sakai:messages message="true"/>
	   <sakai:heading>
       <fmt:message key = "general.information"></fmt:message><br>
        <sakai:instruction></sakai:instruction>
       </sakai:heading>
       <fmt:message key = "general.info"></fmt:message><br>
       <sakai:heading>
       <fmt:message key ="filemanager.input.instruction1"/><br>
       </sakai:heading>
		<sakai:group_table>
		
			<tr>
				<td><fmt:message key = "filemanagerutil.label.subjectCode"></fmt:message></td>
				<td><html:text property="subjectCode" size="9" maxlength="9"></html:text></td>
			</tr>
			<tr>
				<td><fmt:message key = "filemanagerutil.label.action"></fmt:message></td>
				<td>
					<fmt:message key = "filemanagerutil.label.fixRedFile"/><html:radio property="task" value="fix"></html:radio>
					
				</td>
				<td>
					<fmt:message key = "filemanagerutil.label.solveHtmlError"/><html:radio property="task" value="solve"></html:radio>
					
				</td>
			</tr>
		</sakai:group_table>
		
		<sakai:actions>
			<html:submit styleClass="active" property="action">
				<fmt:message key="button.continue"/>
			</html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>
</html>


