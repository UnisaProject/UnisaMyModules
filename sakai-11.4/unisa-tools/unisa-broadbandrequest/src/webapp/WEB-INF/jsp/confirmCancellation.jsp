<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.broadbandrequest.ApplicationResources"/>
<script language="javascript">
	function doAction(){
		document.broadbandRequestForm.action='broadbandRequest.do?act=cancelRequest';
		document.broadbandRequestForm.submit();
	}
</script>

<sakai:html>
	<html:form action="/broadbandRequest">
		<html:hidden property="currentPage" value="cancelRequest"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.cancelRequest"/>
		</sakai:heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.student"/>&nbsp;</td>
				<td><bean:write name="broadbandRequestForm" property="student.number"/>&nbsp;(<bean:write name="broadbandRequestForm" property="student.printName"/>)</td>		
			</tr>
			<tr>
				<td><fmt:message key="prompt.cellPhoneNr"/>&nbsp;</td>			
				<td><bean:write name="broadbandRequestForm" property="student.contactInfo.cellNumber"/></td>
				<td><fmt:message key="general.note1.cellPhoneCorrect"/></td>
			</tr>
		</sakai:group_table>		
		<hr/>			
		<sakai:group_table>	
			<tr>			
				<th><fmt:message key="prompt.serviceProvider"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><fmt:message key="prompt.selectedRegionalCentre"/>
					</logic:equal>
				</th>	
				<td><bean:write name="broadbandRequestForm" property="request.serviceProviderCost.spDescription"/>
					<logic:equal name="broadbandRequestForm" property="request.serviceProviderCost.spCode" value="XXX">
						<br/><bean:write name="broadbandRequestForm" property="request.regionalCentre.engDescription"/>
					</logic:equal>
				</td>
			</tr>		
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
			<tr>
				<th><fmt:message key="prompt.status"/></th>
				<td><bean:write name="broadbandRequestForm" property="request.statusDisplayed"/></td>
			</tr>
			<tr>
				<th><fmt:message key="prompt.packageRequestDate"/></th>			
				<td><bean:write name="broadbandRequestForm" property="request.requestDate"/></td>
			</tr>					
		</sakai:group_table>	
		<logic_equal name="tcsExists" value="Y">
			<sakai:heading>
				<fmt:message key="prompt.termsAndConditions"/>
			</sakai:heading>
			<bean:define id="tcsUrl" name="broadbandRequestForm" property="tcsUrl"/>	
		</logic_equal>	
		<iframe src="<%=tcsUrl%>" style="width:80%;height:150px"></iframe>					
		<sakai:actions>			
			<html:button property="act" onclick="javascript:disabled=true;doAction();">
					<fmt:message key="button.cancelConfirm"/>
			</html:button>		
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>
		</sakai:actions>	
	</html:form>
</sakai:html>		