<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>

	<html:form method="GET" action="displayFinancialDetails">
	    
		<sakai:heading><fmt:message key="function.PreviousFinancialDetail"/></sakai:heading>

		<fmt:message key="function.inputYear"/> <html:text property="inputYear" size="5" maxlength="4"/><br/>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.displaypfd"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clear"/></html:submit>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
		<hr/>
		<logic:equal name="displayFinancialDetailsForm" property="studentUser" value="false">
			<sakai:heading><fmt:message key="function.student"/> 
						   <bean:write name="displayFinancialDetailsForm" property="student.number"/> : 
						   <bean:write name="displayFinancialDetailsForm" property="student.name"/> &nbsp;
						   <bean:write name="displayFinancialDetailsForm" property="student.initials"/>&nbsp;
						   <bean:write name="displayFinancialDetailsForm" property="student.title"/><br>
			</sakai:heading>
		</logic:equal>
		<fmt:message key="function.view"/>  <html:select property="account_classifications" onchange="submit()">
			<html:options collection="classifications" property="value" labelProperty="label"/>
		</html:select>
		<br>
		<br>
		<sakai:group_heading> <fmt:message key="function.detailsRegYear"/> <bean:write name="displayFinancialDetailsForm" property="inputYear"/></sakai:group_heading>
		<sakai:flat_list>
			<tr>
				<th><fmt:message key="function.date"/></th>
				<th><fmt:message key="function.referenceno"/></th>
				<th><fmt:message key="function.allocation"/></th>
				<th><fmt:message key="function.description"/></th>	
				<th style="text-align:right"><fmt:message key="function.debitAmount"/></th>
				<th style="text-align:right"><fmt:message key="function.creditAmount"/></th>
				<th style="text-align:right"><fmt:message key="function.balanceAmount"/></th>							
				<!-- <th style="text-align:right"><fmt:message key="function.amount"/></th> -->
			</tr>
			<logic:notEmpty name="displayFinancialDetailsForm" property="statementRecords">
				<logic:iterate name="displayFinancialDetailsForm" property="statementRecords" id="record" indexId="i">
					<tr>
						<td><bean:write name="record" property="date"/>&nbsp;</td>						
						<td><bean:write name="record" property="reference"/>&nbsp;</td>
						<td><bean:write name="record" property="allocation"/>&nbsp;</td>
						<td><bean:write name="record" property="description"/>&nbsp;</td>
						<logic:equal name="record" property="debitAmount" value="0.0">
							<td>&nbsp;</td>
						</logic:equal>
						<logic:notEqual name="record" property="debitAmount" value="0.0">
							<td align=right><bean:write name="record" property="debitAmount" format="0.00"/>&nbsp;</td>
						</logic:notEqual>	
						<logic:equal name="record" property="creditAmount" value="0.0">	
							<td>&nbsp;</td>
						</logic:equal>
						<logic:notEqual name="record" property="creditAmount" value="0.0">
							<td align=right><bean:write name="record" property="creditAmount" format="0.00"/>&nbsp;</td>
						</logic:notEqual>						
						<td align=right><bean:write name="record" property="balanceAmount" format="0.00"/>&nbsp;</td>
						<!-- <td align=right><bean:write name="record" property="amount" format="0.00"/>&nbsp;</td> -->
					</tr>			
				</logic:iterate>
			</logic:notEmpty>
		</sakai:flat_list>
		<logic:empty name="displayFinancialDetailsForm" property="statementRecords">
			<fmt:message key="function.notAvailable"/>
		</logic:empty>
		<sakai:instruction>
			<fmt:message key="function.disclaimer"/>
		</sakai:instruction>
		<sakai:actions>
			<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
			<html:hidden property="action" value="displayPreviousFinancialDetails"/>
		</sakai:actions>		
	</html:form>
		
</sakai:html>