<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>
 
<sakai:html>
 
<!-- Form -->
<html:form action="/exchange">
	<html:hidden property="goto" value="2" />

	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading><fmt:message key="page.heading.exchange" /></sakai:heading>

	<sakai:instruction>
		<fmt:message key="page.exchange.step1.important"/><br/>
		<fmt:message key="page.exchange.step1.instruction8" /><br/>
		<fmt:message key="page.exchange.step1.instruction2" />
		<fmt:message key="page.exchange.step1.instruction3" /><br/><br/>
		<fmt:message key="page.exchange.step1.instruction4a" />
		<a HREF='http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=17554' onClick="window.open('http://www.unisa.ac.za/Default.asp?Cmd=ViewContent&ContentID=17554', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
		<fmt:message key="page.exchange.step1.instruction4b"/></a>
		<fmt:message key="page.exchange.step1.instruction5" />
		<a HREF='http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/aegrotat.html' onClick="window.open('http://www.unisa.ac.za/contents/studyinfo/docs/process_docs/aegrotat.html', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
		<fmt:message key="page.general.clickhere"/></a>
		<fmt:message key="page.exchange.step1.instruction7" />
		<br/>
	</sakai:instruction>

	<sakai:group_heading>
		<fmt:message key="page.exchange.heading.step1" />
	</sakai:group_heading>
	<sakai:group_table>
		<tr>
			<td><fmt:message key="page.email" /></td>
			<td><bean:write name="regDetailsForm" property="email"/></td>
			<td colspan="3"><strong><fmt:message key="page.step1.instruction1"/></strong></td>
		</tr>
		<logic:notEmpty name="regDetailsForm" property="studyUnits">
			<tr>
				<td><fmt:message key="table.heading.code"/></td>
				<td><fmt:message key="table.heading.codeDesc"/></td>
				<td><fmt:message key="table.heading.semester.year"/></td>
				<td><fmt:message key="table.heading.semester.current"/></td>
				<td><fmt:message key="table.heading.semester.change"/></td>
			</tr>
			<logic:iterate name="regDetailsForm" property="pickList" id="studyUnit" indexId="index">
				<logic:present name="studyUnit" property="code">
					<!-- semester 1 -->
					<logic:equal name="studyUnit" property="regPeriod" value="1">
						<tr>
							<td><bean:write name="studyUnit" property="code" />&nbsp;</td>
							<td><bean:write	name="studyUnit" property="desc" />&nbsp;</td>
								<td><bean:write name="regDetailsForm" property="acadYear" />&nbsp;</td>
							<td><bean:write name="studyUnit" property="regPeriod" />&nbsp;</td>
							<!-- sem 2 exchange valid -->
							<logic:equal name="regDetailsForm" property="exSem2" value="Y">
								<td>2&nbsp;
								<html:multibox property="selectedItems"><bean:write name="index"/></html:multibox></td>
							</tr>
							</logic:equal>
							<!-- sem 2 exchange not valid -->
							<logic:equal name="regDetailsForm" property="exSem2" value="N">
								<td><fmt:message key="page.exchange.closed2" /></td>
							</tr>
							</logic:equal>
					</logic:equal>
					<!-- semester 2 -->
					<logic:equal name="studyUnit" property="regPeriod" value="2">
						<tr>
							<td><bean:write name="studyUnit" property="code" />&nbsp;</td>
							<td><bean:write name="studyUnit" property="desc" />&nbsp;</td>
							<td><bean:write name="regDetailsForm" property="acadYear" />&nbsp;</td>
							<td><bean:write name="studyUnit" property="regPeriod" />&nbsp;</td>
							<!-- sem 1 exchange valid -->
							<logic:equal name="regDetailsForm" property="exSem1" value="Y">
								<td>1&nbsp;
								<html:multibox property="selectedItems"><bean:write name="index"/></html:multibox>
								</td>
							</tr>
							</logic:equal>
							<!-- sem 1 exchange not valid -->
							<logic:equal name="regDetailsForm" property="exSem1" value="N">
								<td><fmt:message key="page.exchange.closed1" /></td>
							</tr>
							</logic:equal>
					</logic:equal>
				</logic:present>	
			</logic:iterate>
		</logic:notEmpty>
		
	</sakai:group_table>

	<sakai:actions>	
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit></td>	
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>	
	</sakai:actions>
</html:form>

</sakai:html
>