<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.studentnosearch.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="/studentnosearch">
	
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		
		<logic:equal name="studentNoSearchForm" property="input" value="true">	
		<sakai:instruction>
		<fmt:message key="input.instruction"/><br/>	
		</sakai:instruction>
		</logic:equal>
			
		<logic:equal name="studentNoSearchForm" property="input" value="true">					
		<sakai:flat_list>
		<tr>
			<td><fmt:message key="input.studsurname"/>&nbsp;<sakai:required/></td>
			<td><html:text name="studentNoSearchForm" property="student.surname" maxlength="28" size="40"/></td>
		</tr>	
		<tr>
			<td><br/><fmt:message key="input.studfullname"/>&nbsp;<sakai:required/></td>
			<td><fmt:message key="input.note"/>&nbsp;<br/><html:text name="studentNoSearchForm" property="student.firstnames" maxlength="60" size="60"/></td>
		</tr>	
		<tr>
			<td><fmt:message key="input.studbirthdate"/>&nbsp;<sakai:required/></td>
			<td>Year(CCYY):&nbsp;&nbsp;<html:text name="studentNoSearchForm" property="student.birthYear" maxlength="4" size="6"/>&nbsp;
			&nbsp;Month(MM):&nbsp;&nbsp;<html:text name="studentNoSearchForm" property="student.birthMonth" maxlength="2" size="2"/>&nbsp;
			&nbsp;Day(DD):&nbsp;&nbsp;<html:text name="studentNoSearchForm" property="student.birthDay" maxlength="2" size="2"/>
			</td>
		</tr>
		<tr>
			<td><html:radio property="searchType" value="id"/><fmt:message key="input.id"/>:</td>
			<td><html:text property="student.idNumber"/>&nbsp;</td>
		</tr>
		<tr>
			<td>OR</td>
			<td>&nbsp;</td>
		</tr>			
		<tr>
			<td><html:radio property="searchType" value="passport"/><fmt:message key="input.passport"/>:</td>
			<td><html:text property="student.passport"/>&nbsp;</td>
		</tr>
		</sakai:flat_list>
		</logic:equal>
		
		<logic:notEqual name="studentNoSearchForm" property="input" value="true">	
		   <sakai:flat_list>
				<tr>	
				<logic:equal name="studentNoSearchForm" property="input" value="false">
					<td><bean:write name="studentNoSearchForm" property="messageToStudent" />&nbsp;</td>
				</logic:equal>
				<tr>	
			</sakai:flat_list>
		</logic:notEqual>
		
		<sakai:actions>	
		<logic:equal name="studentNoSearchForm" property="input" value="true">	
            <html:hidden property="action" value="submit"/>			
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>
		</logic:equal>
         <logic:notEqual name="studentNoSearchForm" property="input" value="true">
            <html:hidden property="action" value="back"/>			
            <html:submit property="action"><fmt:message key="button.back"/></html:submit>
         </logic:notEqual>
		</sakai:actions>
	</html:form>
	
</sakai:html>