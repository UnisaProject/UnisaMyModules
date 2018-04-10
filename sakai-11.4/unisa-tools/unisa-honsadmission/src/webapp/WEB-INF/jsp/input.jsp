<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.honsadmission.ApplicationResources" />
<sakai:tool_bar>
	<html:link href="honsAdmission.do?act=displayStudentReferrals">
		<fmt:message key="link.viewStudentReferrals"/>
	</html:link>
</sakai:tool_bar>
<sakai:html>
<html:form action="/honsAdmission" >
	<sakai:messages />
	<sakai:messages message="true" />
	<sakai:heading>
		<fmt:message key="page.input.heading" />
	</sakai:heading>
	<sakai:group_table>	
		<tr>
			<td><fmt:message key="prompt.acadYear"/><fmt:message key="prompt.required"/></td>
			<td colspan="2"><html:text name="honsAdmissionForm" property="appPeriod.academicYear" size="4" maxlength="4"/></td>				
		</tr>
		<tr>
			<td><fmt:message key="prompt.applicationPeriod"/><fmt:message key="prompt.required"/></td>
			<td colspan="2">
				<html:select name="honsAdmissionForm" property="appPeriod.period">
				<html:optionsCollection name="honsAdmissionForm" property="listPeriod" value="value" label="label"/>
				</html:select>
			</td>				
		</tr>			
	</sakai:group_table>	
	<sakai:actions>
		<html:submit property="act">
			<fmt:message key="button.display"/>
		</html:submit>		
	</sakai:actions>
	<sakai:group_heading><fmt:message key="page.input.groupHeading.reviewList"/></sakai:group_heading>
	<fmt:message key="page.input.note.selectStudentNumber"/>
	<sakai:flat_list>	
		<tr>
			<th align="left"><fmt:message key="column.student"/></th> 
			<th align="left"><fmt:message key="column.qual"/></th>
			<th align="left"><fmt:message key="column.level"/></th>		
			<th align="left">&nbsp;</th>					
			<!-- <th align="left"><fmt:message key="column.name"/></th>			
			<th align="left"><fmt:message key="column.spec"/></th>						
			<th align="left">&nbsp;</th> 
			<!-- <th align="left"><fmt:message key="column.choice"/></th> -->	
			 
		</tr>	
		<logic:notEmpty name="honsAdmissionForm" property="listReview">
			<logic:iterate name="honsAdmissionForm" property="listReview" id="record">
				<bean:define id="stuNr" name="record" property="student.number"/>
				<bean:define id="qualCode" name="record" property="qualCode"/>
				<bean:define id="specCode" name="record" property="specCode"/>
				<bean:define id="choice" name="record" property="choice"/>
				<bean:define id="referType" name="record" property="type"/>
				<bean:define id="referLevel" name="record" property="level"/>
				<bean:define id="seqNr" name="record" property="seqNr"/>
				<% 
					java.util.HashMap params = new java.util.HashMap();
					params.put("stuNr",stuNr);
					params.put("qualCode",qualCode);
					params.put("specCode",specCode);
					params.put("choice",choice);
					params.put("referType",referType);
					params.put("referLevel",referLevel);
					params.put("seqNr",seqNr);
					pageContext.setAttribute("params",params);
				%>
				
				<tr>
					<td><html:link href="honsAdmission.do?act=reviewApplication" scope="page" name="params">
					       <bean:write name="record" property="student.number"/>
					    </html:link>&nbsp;-&nbsp;
					    <bean:write name="record" property="student.name"/>&nbsp;
					    <logic:equal name="record" property="type" value="AR">(Appeal)</logic:equal>  
					</td>								
					<td>
						<bean:write name="record" property="qualCode"/>&nbsp;
						<bean:write name="record" property="specCode"/>&nbsp;							
					</td>
					<td>
						<logic:equal name="record" property="level" value="F">Final</logic:equal>
						<logic:notEqual name="record" property="level" value="F"><bean:write name="record" property="level"/></logic:notEqual>
					</td>	
						<td>&nbsp;</td>								
					<!-- <td>
						<logic:equal name="record" property="choice" value="1">Preferred</logic:equal>
						<logic:equal name="record" property="choice" value="2">Second</logic:equal>
					</td> -->
				</tr>
		    </logic:iterate>
		</logic:notEmpty>    
		<logic:empty name="honsAdmissionForm" property="listReview">
			<tr>
				<td colspan="4"><fmt:message key="reviewList.noRecords" /></td>
			</tr>
		</logic:empty>
	</sakai:flat_list>			
</html:form>
</sakai:html>
