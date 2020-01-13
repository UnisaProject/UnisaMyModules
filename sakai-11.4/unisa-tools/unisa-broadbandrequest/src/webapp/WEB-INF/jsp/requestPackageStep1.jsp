<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.broadbandrequest.ApplicationResources"/>

<sakai:html>
	<html:form action="/broadbandRequest">
		<html:hidden property="currentPage" value="requestPackageStep1"/>
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
			<fmt:message key="packageRequest.step1.heading"/>
		</sakai:group_heading>
		<sakai:instruction>
			<fmt:message key="packageRequest.step1.note1"/>
		</sakai:instruction>
		<sakai:group_table>
			<tr>
				<th><fmt:message key="prompt.serviceProvider"/>&nbsp;</th>
				<th style="text-align: right"><fmt:message key="prompt.3GDataBundle"/>&nbsp;</th>
				<logic:equal name="broadbandRequestForm" property="spOfferModem" value="Y">
					<th style="text-align: right"><fmt:message key="prompt.modemOpt"/>&nbsp;</th>	
				</logic:equal>				
			</tr>			
			<logic:iterate name="broadbandRequestForm" property="listServiceProviderCost" id="record" indexId="index">
				<tr>
					<td>
						<html:radio property="selectedServiceProviderCode" idName="record" value="spCode"></html:radio>
						<bean:write name="record" property="spDescription"/>
					</td>
					<td style="text-align: right"><bean:write name="record" property="simFeeStr"/></td>
					<logic:equal name="broadbandRequestForm" property="spOfferModem" value="Y">
						<td style="text-align: right"><bean:write name="record" property="modemFeeStr"/>
						<logic:notEqual name="record" property="modemFeeDbl" value="0.00">(Optional)</logic:notEqual>
						</td>
					</logic:equal>						
				</tr>
			</logic:iterate>	
		</sakai:group_table>	
		<sakai:instruction>
			<fmt:message key="packageRequest.step1.note2"/>
		</sakai:instruction>
		<logic:equal name="broadbandRequestForm" property="spOfferModem" value="Y">
			<sakai:group_table>
				<tr>
					<td>
						<html:checkbox name="broadbandRequestForm" property="selectedModemFlag" value="Y">
						<fmt:message key="prompt.purchaseModem"/></html:checkbox>
					</td>
				</tr>
			</sakai:group_table>
		</logic:equal>			
		<sakai:actions>		
			<html:submit property="act">
					<fmt:message key="button.continue"/>
			</html:submit>		
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>		
		</sakai:actions>	
	</html:form>
</sakai:html>	