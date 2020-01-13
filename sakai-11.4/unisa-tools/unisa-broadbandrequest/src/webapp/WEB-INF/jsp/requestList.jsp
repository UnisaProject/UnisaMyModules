<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.broadbandrequest.ApplicationResources"/>

<sakai:html>
	<logic:equal  name="broadbandRequestForm"  property="userAccess" value="update">
		<sakai:tool_bar>
			<html:link href="broadbandRequest.do?act=requestPackageStep1" >
				<fmt:message key="link.requestPackage"/>
			</html:link>
		</sakai:tool_bar>	
	</logic:equal>	
	<html:form action="/broadbandRequest">
		<html:hidden property="currentPage" value="listRequest"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
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
		<sakai:heading>
			<fmt:message key="heading.listRequest.listTable"/>
		</sakai:heading>
		<sakai:flat_list>			
			<tr>
				<th><fmt:message key="tableHeading.request.year"/></th>
				<th><fmt:message key="tableHeading.request.requestDate"/></th>
				<th><fmt:message key="tableHeading.request.sp"/></th>				
				<th><fmt:message key="tableHeading.request.signup"/></th>
				<!--<th><fmt:message key="tableHeading.request.regionalCentre"/></th>-->
				<th><fmt:message key="tableHeading.request.status"/></th>
				<th><fmt:message key="tableHeading.request.paymentRef"/></th>
				<th><fmt:message key="tableHeading.request.infoToSP"/></th>
				<th><fmt:message key="tableHeading.request.activationDate"/></th>
				<th><fmt:message key="tableHeading.request.terminationDate"/></th>
			</tr>
			<logic:empty name="broadbandRequestForm" property="listPackageRequest">
				<fmt:message key="requestList.note1"/>
			</logic:empty>
			<logic:iterate name="broadbandRequestForm" property="listPackageRequest" id="record" indexId="index">
				<bean:define id="year" name="record" property="year"/>
				<bean:define id="seqNr" name="record" property="sequenceNr"/>
				<%
					java.util.HashMap params = new java.util.HashMap();
					params.put("requestYear",year);
					params.put("requestSeqNr",seqNr);
					pageContext.setAttribute("params",params); 
				%>
				<tr>
					<td>
						<logic:notEqual  name="broadbandRequestForm"  property="userAccess" value="update">
							<html:link href="broadbandRequest.do?act=viewRequest" scope="page" name="params">                                   							
									<fmt:message key="prompt.linkViewText"/>
							</html:link>					
						</logic:notEqual>	
						<logic:equal  name="broadbandRequestForm"  property="userAccess" value="update">	
							<logic:equal  name="record"  property="link" value="view">
								<html:link href="broadbandRequest.do?act=viewRequest" scope="page" name="params">                                   							
									<fmt:message key="prompt.linkViewText"/>
								</html:link>	
							</logic:equal>				
							<logic:equal  name="record"  property="link" value="cancel">
								<html:link href="broadbandRequest.do?act=viewRequest" scope="page" name="params">                                   							
									<fmt:message key="prompt.linkViewText"/>
								</html:link>
								<html:link href="broadbandRequest.do?act=confirmCancellation" scope="page" name="params">                                   							
									<fmt:message key="prompt.linkCancelText"/>
								</html:link>	
							</logic:equal>					
						</logic:equal>		
						<bean:write name="record" property="year"/>
					</td>
					<td><bean:write name="record" property="requestDate"/></td>					
					<td><bean:write name="record" property="serviceProviderCost.spDescription"/></td>
					<td>			
						<logic:equal name="record" property="modemFlag" value="Y">
							<fmt:message key="prompt.packageModemIncluded"/>
						</logic:equal>
						<logic:notEqual name="record" property="modemFlag" value="Y">
							<fmt:message key="prompt.packageModemExcluded"/>
						</logic:notEqual>
					</td>
					<!--<td><bean:write name="record" property="regionalCentre.engDescription"/></td>-->
					<td><bean:write name="record" property="statusDisplayed"/></td>
					<td>
						<logic:equal name="record" property="serviceProviderCost.simFeeDbl" value="0">n.a.</logic:equal>	
						<logic:notEqual name="record" property="serviceProviderCost.simFeeDbl" value="0">
							<bean:write name="record" property="paymentReference"/>
						</logic:notEqual>
					</td>
					<td><bean:write name="record" property="infoToSpDate"/></td>
					<td><bean:write name="record" property="activationDate"/></td>
					<td><bean:write name="record" property="terminationDate"/></td>					
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:instruction>
		<bean:write name="broadbandRequestForm" property="cancelMessage"/><logic:notEmpty name="broadbandRequestForm" property="cancelMessage"><br/></logic:notEmpty>		
		<fmt:message key="requestList.note3"/>	
		<!--<fmt:message key="requestList.note2"/>-->
		</sakai:instruction>
	</html:form>
</sakai:html>