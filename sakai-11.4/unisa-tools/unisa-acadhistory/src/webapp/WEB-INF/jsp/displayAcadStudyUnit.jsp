
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="za.ac.unisa.lms.tools.acadhistory.forms.Student"%>
<fmt:setBundle basename="za.ac.unisa.lms.tools.acadhistory.ApplicationResources"/>
<script>
	var linkWithMarksClick = false;
	var linkWithoutMarksClick = false;
	function checkWithMarksLink(link) {
		if (linkWithMarksClick) {
			//alert("old");			
			return false;
		}
		//alert("new");
		linkWithMarksClick=true;		
		link.innerHTML="Working...."
		return true;
	}
	function checkWithoutMarksLink(link) {
		if (linkWithoutMarksClick) {
			//alert("old");			
			return false;
		}
		//alert("new");
		linkWithoutMarksClick=true;
		link.innerHTML="Working...."
		return true;
	}
</script>

<sakai:html>
<logic:equal name="acadHistoryDisplayForm" property="acadRecRequestButtonState" value="E">
<sakai:tool_bar>
		<html:link href="displayAcadHistory.do?action=emailwithmarks" onclick='<%="return checkWithMarksLink(this);"%>'>
			<fmt:message key="link.emailWithMarks"/>
		</html:link>
		<html:link href="displayAcadHistory.do?action=emailwithoutmarks" onclick='<%="return checkWithoutMarksLink(this);"%>'>
			<fmt:message key="link.emailWithOutMarks"/>
		</html:link>
	</sakai:tool_bar>
</logic:equal>
<logic:equal name="acadHistoryDisplayForm" property="acadRecRequestButtonState" value="T">
	<sakai:tool_bar>	
		<sakai:instruction>		
				<fmt:message key="toolbar.tempUnavailableAREmailRequest"/>		
		</sakai:instruction>
	</sakai:tool_bar>
</logic:equal>
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
                            
               
<!-- form -->
	<html:form action="/displayAcadHistory.do?action=displayAcadStudyUnit">
	<html:hidden property="action" value="cancel"/>
	<sakai:messages/>
	<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="page.heading"/><br/>
			<logic:notEqual name="acadHistoryDisplayForm" property="creditsOnly" value="Y">
				<fmt:message key="page.instruction4"/>
			</logic:notEqual>
			<logic:equal name="acadHistoryDisplayForm" property="creditsOnly" value="Y">
				<fmt:message key="page.instruction.credits"/>
			</logic:equal>
			&nbsp;<bean:write name="acadHistoryDisplayForm" property="selectedQualDesc"/>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="page.instruction2"/><br/>
			<fmt:message key="page.instruction3"/>
		</sakai:instruction>
		<logic:equal name="acadHistoryDisplayForm" property="studentUser" value="false">
		 <table>
			<tr>
				<td><strong><fmt:message key="page.studentNumber"/>&nbsp;</strong></td>
				<td><bean:write name="acadHistoryDisplayForm" property="student.number"/></td>
			</tr><tr>
				<td><strong><fmt:message key="page.studentName"/>&nbsp;</strong></td>
				<td><bean:write name="acadHistoryDisplayForm" property="student.name"/>&nbsp;
				<td><bean:write name="acadHistoryDisplayForm" property="student.initials"/>&nbsp;
				<td><bean:write name="acadHistoryDisplayForm" property="student.title"/>&nbsp;</td>
			</tr>
		</table>
		<br/>
		</logic:equal>
        <sakai:group_table>
		    <tr>
		    	<td>
					<b><fmt:message key="function.view"/>&nbsp;&nbsp;</b>
					<html:select property="creditsOnly"
					onchange="submit();">
					<html:options collection="view" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
		</sakai:group_table>
	</html:form>

	<!-- form -->
	<html:form action="/displayAcadHistory">
	<html:hidden property="action" value="cancel"/>
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="table.heading.examDate"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.studyUnit"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.desc"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.mark"/>&nbsp;</th>
				<th align="left"><fmt:message key="table.heading.result"/>&nbsp;</th>
			</tr>		
			<logic:iterate name="acadHistoryDisplayForm" property="studyUnitList" id="record" indexId="i">
			<tr>
				<td><bean:write name="record" property="examDate"/></td>
				<td><bean:write name="record" property="studyUnit"/>&nbsp;</td>
				<td><bean:write name="record" property="studyUnitDescription"/>&nbsp;</td>
				<td><bean:write name="record" property="mark"/>&nbsp;</td>
				<td><bean:write name="record" property="resultTypeDescr"/>&nbsp;</td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>

		<sakai:actions>
			<html:submit><fmt:message key="button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
   </logic:notEmpty>
  </logic:notEmpty>
</sakai:html>

