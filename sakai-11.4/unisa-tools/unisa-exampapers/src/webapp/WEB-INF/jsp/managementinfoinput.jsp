<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>


<sakai:html>	
	<script language="javascript">
		function openIntructions(){
			var url="<bean:write name="examPaperCoverDocketForm" property="openInstructionUrl"/>";
			window.open(url,'mywindow','toolbar=no,width=700,height=500');
			}			
	</script>
	<html:form action="/managementInfo">
		<sakai:heading><fmt:message key="heading.managementInfo"/></sakai:heading>
		<html:hidden name="examPaperCoverDocketForm" property="currentPage" value="misinput"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:instruction>
			<fmt:message key="misInput.instruction"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.examYear"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="year" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examPeriod"/>&nbsp;</td>
				<td>
					<html:select name="examPaperCoverDocketForm" property="period">
					<html:optionsCollection name="examPaperCoverDocketForm" property="examPeriodCodes" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.college"/></td>
				<td><bean:write name="examPaperCoverDocketForm" property="college.description"/>
			</tr>
			<tr>
				<td><fmt:message key="page.department"/>&nbsp;</td>
				<td>
					<html:select property="selectedDepartment">
					<html:options collection="listDepartments" property="code" labelProperty="description"/>
					</html:select><BR>
					<sakai:instruction><fmt:message key="misDepartment.instruction"/></sakai:instruction>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="page.report"/>&nbsp;</td>
				<td><html:select property="selectedReport">
						<html:options collection="listReports" property="code" labelProperty="engDescription"/>
						</html:select>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="page.emailAddress"/>&nbsp;</td>
				<td><html:text name="examPaperCoverDocketForm" property="user.emailAddress" size="40" maxlength="60"/></td>
			</tr>
		</sakai:group_table>
		<sakai:instruction>
			<fmt:message key="misExtract.instruction"/>
		</sakai:instruction>	
		<sakai:heading>		
			<html:link href="#" onclick="javascript:openIntructions();return false"><fmt:message key="link.delimitedFileInstructions"/></html:link>
		</sakai:heading>	
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.extract"/>
			</html:submit>	
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>			
		</sakai:actions>		
		<logic:notEmpty name="examPaperCoverDocketForm" property="reportMessage">
			<hr>
			<p><bean:write name="examPaperCoverDocketForm" property="reportMessage"/></p>
		</logic:notEmpty>
	</html:form>
</sakai:html>