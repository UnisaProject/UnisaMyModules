<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.broadbandrequest.ApplicationResources"/>
<script language="javascript">
	function doAction(){
		document.broadbandRequestForm.action='broadbandRequest.do?act=saveRequest';
		document.broadbandRequestForm.submit();
	}
</script>
<sakai:html>
	<html:form action="/broadbandRequest">
		<html:hidden property="currentPage" value="requestPackageStep2"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.requestPackage"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.student"/>&nbsp;</td>
				<td colspan="2"><bean:write name="broadbandRequestForm" property="student.number"/>&nbsp;(<bean:write name="broadbandRequestForm" property="student.printName"/>)</td>		
			</tr>
			<tr>
				<td><fmt:message key="prompt.cellPhoneNr"/>&nbsp;</td>			
				<td><bean:write name="broadbandRequestForm" property="student.contactInfo.cellNumber"/></td>
				<td><fmt:message key="general.note1.cellPhoneCorrect"/></td>
			</tr>
		</sakai:group_table>		
		<hr/>	
		<sakai:group_heading>
			<fmt:message key="packageRequest.step2.heading"/>
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<th>
					<fmt:message key="prompt.serviceProvider"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><fmt:message key="prompt.regionalCentre"/>
					</logic:equal>
				</th>
				<td>
					<bean:write name="broadbandRequestForm" property="request.serviceProviderCost.spDescription"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><html:select name="broadbandRequestForm" property="request.regionalCentre.code">
							<html:optionsCollection name="broadbandRequestForm" property="listRegionalCentre" value="code" label="engDescription"/>   	
						</html:select>
					</logic:equal>
				</td>		
			</tr>
			<logic:notEqual  name="broadbandRequestForm" property="request.serviceProviderCost.simFeeDbl" value="0.00">
				<tr>
					<th><fmt:message key="prompt.studentAccountBalance"/>&nbsp;</th>
					<td style="text-align: left"><bean:write name="broadbandRequestForm" property="studentAccountBalanceStr"/></td>
				</tr>
			</logic:notEqual>			
			<tr>
				<th colspan="2"><fmt:message key="prompt.packageSelected"/>&nbsp;</th>				
			</tr>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.3GDataBundle"/><br/>
					<logic:equal name="broadbandRequestForm" property="request.modemFlag" value="Y">
						<fmt:message key="prompt.modem"/><br/>
						<fmt:message key="prompt.totalDue"/>	
					</logic:equal>						
				</td>	
				<td style="text-align: left"><bean:write name="broadbandRequestForm" property="request.serviceProviderCost.simFeeStr"/><br/>
					<logic:equal name="broadbandRequestForm" property="request.modemFlag" value="Y">
						<bean:write name="broadbandRequestForm" property="request.serviceProviderCost.modemFeeStr"/><br/>					
						<bean:write name="broadbandRequestForm" property="amountDueStr"/>
					</logic:equal>		
				</td>
			</tr>	
			<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="VODACOM">				
				<tr>
					<th colspan="2"><fmt:message key="packageRequest.step2.note1.vodacom"/></th>
				</tr>
				<tr>											
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.activationMobileNumber"/></td>
					<td><html:text name="broadbandRequestForm" property="request.cardMobileNr" size="12" maxlength="10"/> e.g. 0823477409</td>
				</tr>
				<tr>					
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.confirmActivationMobileNumber"/></td>						
					<td><html:text name="broadbandRequestForm" property="confirmActivationMobileNr" size="12" maxlength="10"/> please retype mobile number</td>
				</tr>
			</logic:equal>	
			<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="TELKOM">				
				<tr>
					<th colspan="2"><fmt:message key="packageRequest.step2.note1.telkom"/></th>
				</tr>
				<tr>											
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.activationMobileNumber"/></td>
					<td><html:text name="broadbandRequestForm" property="request.cardMobileNr" size="12" maxlength="10"/> e.g. 0823477409</td>
				</tr>
				<tr>					
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prompt.confirmActivationMobileNumber"/></td>						
					<td><html:text name="broadbandRequestForm" property="confirmActivationMobileNr" size="12" maxlength="10"/> please retype mobile number</td>
				</tr>
			</logic:equal>		
		</sakai:group_table>
		<sakai:heading>
			<fmt:message key="prompt.termsAndConditions"/>
		</sakai:heading>
		<bean:define id="tcsUrl" name="broadbandRequestForm" property="tcsUrl"/>	
		<iframe src="<%=tcsUrl%>" style="width:80%;height:150px"></iframe>		
		<sakai:group_table>
			<tr>
				<td>
					<html:checkbox name="broadbandRequestForm" property="request.termsConditionFlag" value="Y" onclick='<%="javascript:clickedTermsAndCond(this.form,this.name);"%>'>
					<fmt:message key="prompt.acceptConditions"/></html:checkbox>
				</td>
			</tr>
			<tr>
				<td>
					<html:checkbox name="broadbandRequestForm" property="request.contactDetialFlag" value="Y" onclick='<%="javascript:clickedContactDetails(this.form,this.name);"%>'>
					<fmt:message key="prompt.acceptContactDetails"/></html:checkbox>
				</td>
			</tr>
			<logic:notEqual  name="broadbandRequestForm" property="request.serviceProviderCost.simFeeDbl" value="0.00">
				<bean:define id="stuBalance" name="broadbandRequestForm" property="studentAccountBalanceDbl"/>
				<bean:define id="stuAmountDue" name="broadbandRequestForm" property="amountDueDbl"/>
					<%	
					double balance = (Double) stuBalance;
					double amountDue = (Double) stuAmountDue;
					if (balance >= amountDue) {
					%>
					<tr>
						<td>
							<html:checkbox name="broadbandRequestForm" property="request.transferFundsFlag" value="Y">
							<fmt:message key="prompt.agreeTransferFunds"/></html:checkbox>
						</td>
					</tr>
					<%}%>
			</logic:notEqual>	
		</sakai:group_table>		
		<sakai:actions>	
			<logic:equal name="broadbandRequestForm" property="submitCounter" value="0">
				<logic:equal name="broadbandRequestForm" property="request.termsConditionFlag" value="Y">
					<logic:equal name="broadbandRequestForm" property="request.contactDetialFlag" value="Y">							
						<html:button property="act" styleId="submitButton" onclick="disabled=true;doAction()">
								<fmt:message key="button.acceptConfirm"/>
						</html:button>
					</logic:equal>	
					<logic:notEqual name="broadbandRequestForm" property="request.contactDetialFlag" value="Y">
						<html:button property="act" styleId="submitButton" disabled="true" onclick="disabled=true;doAction();">
								<fmt:message key="button.acceptConfirm"/>
						</html:button>
					</logic:notEqual>	
				</logic:equal>		
				<logic:notEqual name="broadbandRequestForm" property="request.termsConditionFlag" value="Y">
					<logic:equal name="broadbandRequestForm" property="request.contactDetialFlag" value="Y">
						<html:button property="act" styleId="submitButton" disabled="true" onclick="disabled=true;doAction();">
								<fmt:message key="button.acceptConfirm"/>
						</html:button>
					</logic:equal>	
					<logic:notEqual name="broadbandRequestForm" property="request.contactDetialFlag" value="Y">
						<html:button property="act" styleId="submitButton" disabled="true" onclick="disabled=true;doAction();">
								<fmt:message key="button.acceptConfirm"/>
						</html:button>
					</logic:notEqual>	
				</logic:notEqual>
				<html:submit property="act">
						<fmt:message key="button.back"/>
				</html:submit>
				<html:submit property="act">
						<fmt:message key="button.cancelRequest"/>
				</html:submit>		
			</logic:equal>	
			<logic:notEqual name="broadbandRequestForm" property="submitCounter" value="0">	
				<html:submit property="act">
						<fmt:message key="button.cancelRequest"/>
				</html:submit>		
			</logic:notEqual>
		</sakai:actions>
		<script language="javascript">
			function clickedTermsAndCond(form,TermsCondCheckbox){			
				if (form[TermsCondCheckbox].checked==true){
					if (document.forms["broadbandRequestForm"].elements["request.contactDetialFlag"].checked==true){					
						document.forms["broadbandRequestForm"].elements["submitButton"].disabled=false;
					}
					if (document.forms["broadbandRequestForm"].elements["request.contactDetialFlag"].checked==false){					
						document.forms["broadbandRequestForm"].elements["submitButton"].disabled=true;
					}
				}
				if (form[TermsCondCheckbox].checked==false){					
					document.forms["broadbandRequestForm"].elements["submitButton"].disabled=true;
				}
			}
			function clickedContactDetails(form,ContactDetailCheckbox){				
				if (form[ContactDetailCheckbox].checked==true){					
					if (document.forms["broadbandRequestForm"].elements["request.termsConditionFlag"].checked==true){						
						document.forms["broadbandRequestForm"].elements["submitButton"].disabled=false;
					}
					if (document.forms["broadbandRequestForm"].elements["request.termsConditionFlag"].checked==false){						
						document.forms["broadbandRequestForm"].elements["submitButton"].disabled=true;
					}
				}
				if (form[ContactDetailCheckbox].checked==false){						
					document.forms["broadbandRequestForm"].elements["submitButton"].disabled=true;
				}
			}
		</script>			
	</html:form>
</sakai:html>			