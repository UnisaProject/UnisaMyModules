<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.biodetails.ApplicationResources"/>

<sakai:html>
<!-- Form -->
	<html:form action="emailoptionsaction">
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:heading><fmt:message key="page.heading.emailoptions"/></sakai:heading>					
		<!-- Exam centre-->
		<sakai:instruction>
			<fmt:message key="page.email.instruction1"/><br/>
			<fmt:message key="page.email.instruction3"/><br/>			
			<fmt:message key="page.required.instruction"/> <font color="red">*</font><br/><br/>
		</sakai:instruction>		
		<sakai:group_heading><fmt:message key="page.sub.heading.correspondance"/></sakai:group_heading>
		<sakai:group_table>
		<!-- Comment out by Johannes Ngoepe 04/05/2006 -->
		<!--<tr>
			<td><fmt:message key="page.form.reg"/><br/>
				<html:radio property="regOption" value="Y"/> <fmt:message key="page.form.yes"/><br/>
				<html:radio property="regOption" value="N"/> <fmt:message key="page.form.no"/>	
			</td>
		</tr>
		-->
		<tr>
			<td><fmt:message key="page.form.assignment"/>&nbsp;<sakai:required/><br/>
				<html:radio property="assignOption" value="Y"/> <fmt:message key="page.form.yes"/><br/>
				<html:radio property="assignOption" value="N"/> <fmt:message key="page.form.no"/>				
			</td>
		</tr>	
		<tr>
			<td><fmt:message key="page.exam.block.question"/>&nbsp;<sakai:required/><br/>
			    <fmt:message key="page.exam.blockinfo"/><br/>
				<html:radio property="blockExamResults" value="Y"/> <fmt:message key="page.form.yes"/><br/>
				<html:radio property="blockExamResults" value="N"/> <fmt:message key="page.form.no"/>				
			</td>
		</tr>			
		</sakai:group_table>
		<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.submit"/></html:submit>			
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>			
		</sakai:actions>		
	</html:form>
</sakai:html>