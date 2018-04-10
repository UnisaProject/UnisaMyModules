<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.regdetails.ApplicationResources"/>

<sakai:html>

<!-- Form -->
<html:form action="/additions" >
	<html:hidden property="goto" value="6"/>

		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading><fmt:message key="page.heading.additions"/></sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.step4.important"/><br/><br/>
			<fmt:message key="page.additions.confirm1"/>
			<fmt:message key="page.additions.confirm2"/>
			<fmt:message key="page.additions.confirm3"/>
			<fmt:message key="page.additions.confirm4"/>
			<fmt:message key="page.additions.confirm5"/>
			<fmt:message key="page.additions.confirm6"/>
			<fmt:message key="page.additions.confirm7"/><br/><br/>
			<fmt:message key="page.step4.important.line1a"/>
			<fmt:message key="page.initial"/>
			<fmt:message key="page.step4.important.line1b"/>
			<a HREF='http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231' onClick="window.open('http://www.unisa.ac.za/default.asp?cmd=ViewContent&ContentID=17231', 'nextPage', 'height=640,width=630,directories=no,scrollbars=yes,resizable=yes,menubar=no'); return false;" target="_blank">
			<fmt:message key="page.step4.closingdate"/></a>
			<fmt:message key="page.step4.important.line2"/><br/>
		</sakai:instruction>
		<sakai:group_heading><fmt:message key="page.heading.confirm"/></sakai:group_heading>
		<sakai:group_table>
		<tr><td><fmt:message key="page.email"/></td>
			<td colspan="3"><bean:write name="regDetailsForm" property="email"/></td>
		</tr><tr>
			<td colspan="4"><fmt:message key="page.qual2"/></td>
		</tr><tr>
			<td ><fmt:message key="page.qual"/></td>
			<td colspan="3"><bean:write name="regDetailsForm" property="qual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.qualDesc"/></td>
		</tr>
		<logic:equal name="regDetailsForm" property="qual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
				<td colspan='3'><fmt:message key="page.additions.nospec"/></td>
			</tr>
		</logic:equal>
		<logic:notEqual name="regDetailsForm" property="qual.specCode" value="">
			<tr>
			    <td><fmt:message key="page.spec"/></td>
					<td colspan='3'><bean:write name="regDetailsForm" property="qual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="qual.specDesc"/></td>
			</tr>
		</logic:notEqual>
		<logic:notEqual name="regDetailsForm" property="amendQual" value="0">
		<tr>
			<td colspan="4"><fmt:message key="page.step4.newQual"/></td>
		</tr><tr>
			<td><fmt:message key="page.qual"/></td>
			<td colspan="3"><bean:write name="regDetailsForm" property="newQual.qualCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.qualDesc"/></td>
		</tr>
			<logic:equal name="regDetailsForm" property="newQual.specCode" value="">
				<tr>
			    	<td><fmt:message key="page.spec"/></td>
					<td colspan='3'><fmt:message key="page.additions.nospec"/></td>
				</tr>
			</logic:equal>
			<logic:notEqual name="regDetailsForm" property="newQual.specCode" value="">
				<tr>
					<td><fmt:message key="page.spec"/></td>
					<td colspan='3'><bean:write name="regDetailsForm" property="newQual.specCode"/>&nbsp;-&nbsp;<bean:write name="regDetailsForm" property="newQual.specDesc"/></td>
				</tr>
			</logic:notEqual>
		</logic:notEqual>

		<logic:equal name="amendqual" value="true">
			<tr>
			    <td colspan="4"><strong><fmt:message key="page.confirm.spec.information"/></strong></td>
			</tr>
		</logic:equal>

		<logic:notEmpty name="regDetailsForm" property="additionalStudyUnits">
		<tr>
			<td colspan="4"><fmt:message key="page.step4.studyUnits"/></td>
		</tr>
		<tr>
			<td><strong><fmt:message key="table.heading.code"/></strong></td>
			<td><strong><fmt:message key="table.heading.codeDesc"/></strong></td>
			<td><strong><fmt:message key="table.heading.semester"/></strong></td>
			<td><strong><fmt:message key="table.heading.language"/></strong></td>
			<!-- <td><strong><fmt:message key="table.heading.ndp"/></strong></td> -->
		</tr>
		<logic:iterate name="regDetailsForm" property="additionalStudyUnits" id="record" indexId="i">
			 <logic:present name="record" property="code">
			 	<tr>
					<td><bean:write name="record" property="code"/>&nbsp;</td>
					<td><bean:write name="record" property="desc"/>&nbsp;</td>

					<td>
						<logic:equal name="record" property="regPeriod" value="1">
							1st semester
						</logic:equal>
						<logic:equal name="record" property="regPeriod" value="2">
							2nd semester
						</logic:equal>
						<logic:equal name="record" property="regPeriod" value="0">
							Year module
						</logic:equal>
						<!-- 
							<logic:equal name="record" property="regPeriod" value="6">
								Cycle2
							</logic:equal>
						 -->
					<td>
						<logic:equal name="record" property="language" value="A2">
							Afr
						</logic:equal>
						<logic:equal name="record" property="language" value="A">
							Afr
						</logic:equal>
						<logic:equal name="record" property="language" value="E">
							Eng
						</logic:equal>
						<logic:equal name="record" property="language" value="E2">
							Eng
						</logic:equal>
					</td>
					<!-- 
						<td>
							<logic:equal name="record" property="ndp" value="N">
								No
							</logic:equal>
							<logic:equal name="record" property="ndp" value="Y">
								Yes
							</logic:equal>
						</td>
					-->
			 </tr>
			</logic:present>
		</logic:iterate>
	</logic:notEmpty>

</sakai:group_table>

	<sakai:actions>
		<logic:equal name="regDetailsForm" property="wil" value="true">
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</logic:equal>
		
		<logic:equal name="regDetailsForm" property="wil" value="false">
		<logic:equal name="regDetailsForm" property="odl" value="true">
			<html:submit property="action"><fmt:message key="button.continue"/></html:submit>
		</logic:equal>
		</logic:equal>
		
		<logic:equal name="regDetailsForm" property="wil" value="false">
		<logic:equal name="regDetailsForm" property="odl" value="false">
			<html:submit property="action"><fmt:message key="button.save"/></html:submit>
		</logic:equal>
		</logic:equal>
		
		<html:submit property="action"><fmt:message key="button.back"/></html:submit>
		<html:submit property="action"><fmt:message key="button.cancel"/></html:submit>
	</sakai:actions>
</html:form>

</sakai:html>