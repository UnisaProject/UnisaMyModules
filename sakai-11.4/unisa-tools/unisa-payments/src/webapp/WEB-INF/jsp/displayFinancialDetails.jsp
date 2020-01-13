
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.payments.ApplicationResources"/>

<sakai:html>

	<html:form method="GET" action="displayFinancialDetails">
		<sakai:tool_bar>
			<html:link href="displayFinancialDetails.do?action=displayPreviousFinancialDetailsInput">
				<fmt:message key="function.PreviousFinancialDetail"/>
			</html:link>
			<logic:equal name="displayFinancialDetailsForm" property="studentUser" value="true">
				<html:link href="/unisa-findtool/default.do?sharedTool=unisa.creditcardpayment">
					<fmt:message key="function.headingCredit"/>
				</html:link>
			 </logic:equal>
		</sakai:tool_bar>
		<sakai:heading><fmt:message key="function.financialDetails"/></sakai:heading>
		<logic:equal name="displayFinancialDetailsForm" property="studentUser" value="false">
			<fmt:message key="function.stno"/>  <html:text property="student.number"/><br><br>
			<html:submit property="action"><fmt:message key="button.displayfd"/></html:submit>
			<html:submit property="action"><fmt:message key="button.clearstno"/></html:submit>
			<br>
			<hr/> 
			<sakai:heading><fmt:message key="function.student"/> 
						   <bean:write name="displayFinancialDetailsForm" property="student.number"/> : 
						   <bean:write name="displayFinancialDetailsForm" property="student.name"/> &nbsp;
						   <bean:write name="displayFinancialDetailsForm" property="student.initials"/>&nbsp;
						   <bean:write name="displayFinancialDetailsForm" property="student.title"/><br>
			</sakai:heading>
			<br/>
			
		</logic:equal>
		
		<fmt:message key="function.view"/>  <html:select property="account_classifications" onchange="submit()">
			<html:options collection="classifications" property="value" labelProperty="label"/>
		</html:select>
		<br>
		<br>
		<sakai:group_heading> <fmt:message key="function.amountsPayable"/> </sakai:group_heading>
		<sakai:flat_list>
			<tr>
				<th style="text-align:right"><fmt:message key="function.immediately"/></th>
				<th style="text-align:right"><bean:write name="displayFinancialDetailsForm" property="year"/><fmt:message key="function.dueMarch"/></th>
				<th style="text-align:right"><bean:write name="displayFinancialDetailsForm" property="year"/><fmt:message key="function.dueMay"/></th>
				<th style="text-align:right"><bean:write name="displayFinancialDetailsForm" property="year"/><fmt:message key="function.dueAugust"/></th>
				<th style="text-align:right"><bean:write name="displayFinancialDetailsForm" property="year"/><fmt:message key="function.dueNovember"/></th>
				<th style="text-align:right"><bean:write name="displayFinancialDetailsForm" property="next_year"/><fmt:message key="function.dueMarch"/></th>
			</tr>
			<tr>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_imm" format="0.00"/></td>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_march" format="0.00"/></td>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_may" format="0.00"/></td>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_aug" format="0.00"/></td>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_nov" format="0.00"/></td>
				<td align="right"><bean:write name="displayFinancialDetailsForm" property="due_next_march" format="0.00"/></td>
			</tr>
		</sakai:flat_list>
		<sakai:group_heading> <fmt:message key="function.detailsRegYear"/> <bean:write name="displayFinancialDetailsForm" property="year"/></sakai:group_heading>
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
				<tr>
					<td colspan=6><strong><html:messages id="msg" message="true"><bean:write name="msg"/></html:messages></strong></td>
					<td align="right"><strong><bean:write name="displayFinancialDetailsForm" property="balance" format="0.00"/></strong></td>
				</tr>
			</logic:notEmpty>
		</sakai:flat_list>		
		<logic:empty name="displayFinancialDetailsForm" property="statementRecords">
			<fmt:message key="function.notAvailable"/>
		</logic:empty>
		<sakai:instruction>
			<fmt:message key="function.disclaimer"/>
		</sakai:instruction>
		<html:hidden property="action" value="displayFinancialDetails"/>
		
		
	</html:form>
		
</sakai:html>