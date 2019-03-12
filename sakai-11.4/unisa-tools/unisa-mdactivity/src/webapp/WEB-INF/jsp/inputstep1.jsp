<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mdactivity.ApplicationResources"/>


<sakai:html>
	<html:form action="/displaymdactivity">	
	<html:hidden property="act" value="inputstep2"/>		

		<sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:messages/>

		<sakai:instruction><fmt:message key="page.instruction1a"/></sakai:instruction>

		<sakai:group_table>
			<tr>
				<td><strong><fmt:message key="page.heading.studentnr"/></strong></td>
				<td><html:text name="mdActivityForm" property="student.number" size="10" maxlength="8"/></td>
			</tr>
		</sakai:group_table>
		
		<logic:notEmpty name="mdActivityForm" property="studentLookupList">
		<sakai:group_table>
			<tr>
				<td><strong>OR</strong></td>
			</tr>
		</sakai:group_table>
		</logic:notEmpty>
		                                                
		<logic:notEmpty name="mdActivityForm" property="studentLookupList">
			<sakai:group_table>
				<tr>
					<td><sakai:instruction>
						<fmt:message key="page.instruction1b" />
					</sakai:instruction></td>
				</tr>
				
				<tr>
					<td><strong><fmt:message key="page.heading.studentnr" /></strong>&nbsp;
					<html:select property="selectedStudent" styleId="selectedStudent"
						onclick="disableLookupList()" style="font-family:courier">
						<html:optionsCollection name="mdActivityForm" property="studentLookupList" value="value"
							label="label"/>
					</html:select> <br />
					<br />
					</td>
				</tr>
			</sakai:group_table>
		</logic:notEmpty>	

		<sakai:actions>
			<html:submit property="act"><fmt:message key="button.continue"/></html:submit>
		</sakai:actions>

	</html:form>
</sakai:html>
