<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page   import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:tool_bar>
	<ul><li>
			<html:link href="authorisation.do?action=codAuthRequestList" >
			<fmt:message key="link.codAuthorisation"/>
		</html:link> </li>
			<li><html:link href="authorisation.do?action=schoolDirectorAuthRequestList" >
			<fmt:message key="link.schoolsdirectorAuthirization"/>
		</html:link></li>
		<li><html:link href="deanAuthorisation.do?action=deanview" >
			<fmt:message key="link.deanAuthorisation"/>
		</html:link></li></ul>
	</sakai:tool_bar>
	<sakai:instruction>
		<fmt:message key="function.instructiontext"/><br/>
		<fmt:message key="function.instructiontext1"/>
	</sakai:instruction>
	<html:form action="/deanAuthorisation">
		   <p/>
		   <sakai:group_heading>
			    <fmt:message key="label.selectedityearsemsg"/>
		   </sakai:group_heading>
	       <sakai:instruction>
		         <fmt:message key="label.selectacayearinstr"/>
	       </sakai:instruction>
	       	<html:select name="bookMenuForm"  property="acadyear">
			            <html:option  value="Select Academic Year">Select Academic Year</html:option>
					    <html:options  collection="yearsList" property="value" labelProperty="label" />
	   		</html:select><BR><BR>
	   		<sakai:actions>
			      <html:submit styleClass="active" property="action"><fmt:message key="button.continue"/></html:submit>
			      <html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		    </sakai:actions>
		    <html:hidden property="cancelOption" value="TOMAINVIEW"/>
    </html:form>
</sakai:html>