
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.acadhistory.ApplicationResources"/>
<sakai:html>
         <logic:empty name="acadHistoryDisplayForm" property="student">
                             <sakai:instruction><font color="red"><fmt:message key="page.nullstudenterror"/></font></sakai:instruction>
         </logic:empty>
         <logic:notEmpty name="acadHistoryDisplayForm" property="student">
                            <logic:empty name="acadHistoryDisplayForm" property="student.number">
                                                 <html:form action="/displayAcadHistory">
	                                                    <sakai:messages/>
	                                                    <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
	                                                    <sakai:instruction><fmt:message key="page.input.instruction"/></sakai:instruction>
	                                                    <sakai:group_table>
	                                                        <tr>
		                                                         <td><fmt:message key="page.studentNumber"/>&nbsp;</td>
		                                                         <td> <html:text property="student.number" size="10" maxlength="8"/></td>
		                                                    </tr>
	                                                    </sakai:group_table>
	                                                    <sakai:actions>
	                                                        <html:hidden property="action" value="displayAcadHistory"/>
		                                                    <html:submit titleKey="button.display"><fmt:message key="button.display"/></html:submit>
	                                                    </sakai:actions>
                                                  </html:form>
                           </logic:empty>                       
                     <logic:notEmpty name="acadHistoryDisplayForm" property="student.number">
                               <html:form action="/displayAcadHistory">
		<sakai:messages/>
		<html:hidden property="action" value="displayAcadHistory"/>
        <sakai:heading><fmt:message key="page.heading"/></sakai:heading>
		<sakai:instruction>
		<logic:equal name="acadHistoryDisplayForm" property="studentUser" value="true">
			<fmt:message key="page.instruction1a"/>
		</logic:equal>
		<logic:equal name="acadHistoryDisplayForm" property="studentUser" value="false">
			<fmt:message key="page.instruction1b"/>
		</logic:equal>
		</sakai:instruction>
        <logic:equal name="acadHistoryDisplayForm" property="studentUser" value="false">
		<table>
			<tr>
				<td><fmt:message key="page.studentNumber.enter"/>&nbsp;</td>
				<td><html:text property="student.number" size="10" maxlength="8" value=""/></td>
			</tr>
		</table>
		<sakai:actions>
			<html:submit titleKey="button.display"><fmt:message key="button.display"/></html:submit>
		</sakai:actions>
		<hr/>
		<table>
			<tr>
		        <td><strong><fmt:message key="page.studentNumber"/>&nbsp;</strong></td>
				<td><bean:write name="acadHistoryDisplayForm" property="student.number"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.studentName"/>&nbsp;</strong></td>
				<td><bean:write name="acadHistoryDisplayForm" property="student.name"/>&nbsp;
				<bean:write name="acadHistoryDisplayForm" property="student.initials"/>&nbsp;
				<bean:write name="acadHistoryDisplayForm" property="student.title"/></td>
			</tr>
		</table>

		<br/>
		</logic:equal>
		<sakai:flat_list>
		<tr>
			<th align="left"><fmt:message key="table.heading.code"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.desc"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.firstReg"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.lastYear"/>&nbsp;</th>
			<th align="left"><fmt:message key="table.heading.status"/>&nbsp;</th>
		</tr>
		<logic:notEmpty name="acadHistoryDisplayForm" property="qualRecords">
			<logic:iterate name="acadHistoryDisplayForm" property="qualRecords" id="record" indexId="i">
			<tr>
			<td><html:link href="displayAcadHistory.do?action=displayAcadStudyUnit" paramName="record" paramProperty="qualCode" paramId="selectedQual"><bean:write name="record" property="qualCode"/></html:link></td>
			<td><bean:write name="record" property="qualShortDescription"/>&nbsp;</td>
			<td><bean:write name="record" property="firstRegistrationDate"/>&nbsp;</td>
			<td><bean:write name="record" property="lastRegistrationYear"/>&nbsp;</td>
			<td><bean:write name="record" property="status"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		</sakai:flat_list>
	</html:form>
   </logic:notEmpty>
  </logic:notEmpty>
</sakai:html>

