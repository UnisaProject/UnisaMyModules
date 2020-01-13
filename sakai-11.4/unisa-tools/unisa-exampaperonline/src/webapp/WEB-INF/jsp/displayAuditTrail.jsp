<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<sakai:html>	
	<html:form action="/auditTrail">	
		<html:hidden property="currentPage" value="displayAuditTrail"/>
		<html:hidden property="xpoAction" value=""/>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="displayAuditTrail.heading"/>
		</sakai:heading>			
		<sakai:instruction>
			<fmt:message key="auditTrail.note1"/>&nbsp;<fmt:message key="note.required"/>
		</sakai:instruction>		
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td><html:text name="examPaperOnlineForm" property="listCriteriaExamYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td>
					<html:select name="examPaperOnlineForm" property="listCriteriaExamPeriod">
					<html:optionsCollection name="examPaperOnlineForm" property="listExamPeriods" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td><html:text name="examPaperOnlineForm" property="listCriteriaStudyUnit" size="10" maxlength="7"/>&nbsp;
					<bean:write name="examPaperOnlineForm" property="equivalentStr" />
				</td>								
			</tr>
			<tr>
				<td><fmt:message key="prompt.paperNumber"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td><html:text name="examPaperOnlineForm" property="listCriteriaPaperNo" size="2" maxlength="1"/></td>								
			</tr>
			<tr>
			<td><fmt:message key="prompt.paperContent"/>&nbsp;<fmt:message key="prompt.required"/></td>
				<td><sakai:group_table>
							<logic:iterate name="examPaperOnlineForm" property="listPaperContentTypes" id="record" indexId="index">
								<tr>
									<td><html:radio property="listCriteriaPaperContent" idName="record" value="code"></html:radio></td>
									<td><bean:write name="record" property="engDescription"/></td>
								</tr>
							</logic:iterate>
						</sakai:group_table>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.display"/>
			</html:submit>				
		</sakai:actions>		
		<hr/>			
		<sakai:flat_list>
			<tr>
				<td colspan="7">
					<sakai:heading>
						<fmt:message key="auditTrail.groupheading1"/> 
					</sakai:heading>								
				</td>				
			</tr>
			<tr>
				<th align="left"><fmt:message key="tabHead.audit.action"/></th>
				<th align="left"><fmt:message key="tabHead.audit.currentUser"/></th>
				<th align="left"><fmt:message key="tabHead.audit.actionedUser"/></th>
				<th align="left"><fmt:message key="tabHead.audit.updatedOn"/></th>				
				<th align="left"><fmt:message key="tabHead.audit.response"/></th>	
				<th align="left"><fmt:message key="tabHead.audit.reset"/></th>	
			</tr>
			<logic:notEmpty name="examPaperOnlineForm" property="availableDocList">
				<logic:iterate name="examPaperOnlineForm" property="availableDocList" id="record" indexId="index">
					<bean:define id="actionedUser" name="record" property="actionedUser"/>
					<tr>
						<td><bean:write name="record" property="action"/></td>						
						<td><bean:write name="record" property="currentUser"/> - <bean:write name="record" property="currentRole"/></td>
						<td>
							<logic:notEmpty  name="record" property="actionedUser">
								<logic:equal name="record" property="actionedRole" value="<%= actionedUser.toString() %>">
									<bean:write name="record" property="actionedUser"/>
								</logic:equal>
								<logic:notEqual name="record" property="actionedRole" value="<%= actionedUser.toString() %>">
									<bean:write name="record" property="actionedUser"/> - <bean:write name="record" property="actionedRole"/>
								</logic:notEqual>
							</logic:notEmpty>
						</td>
						<td><bean:write name="record" property="updatedOn"/></td>
						<td>
							<logic:notEmpty name="record" property="approvalStatus">(<bean:write name="record" property="approvalStatus"/>)</logic:notEmpty>
							<span title="<bean:write name="record" property="message"/>"><bean:write name="record" property="shortMessage"/></span>
						</td>
						<td><logic:equal name="record" property="resetFlag" value="Y">Yes</logic:equal>
							<logic:notEqual name="record" property="resetFlag" value="Y">No</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
	<sakai:actions>				
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.back"/>
			</html:submit>			
	</sakai:actions>	
	<script language="javascript">			
		function onClickSetXpoActionDefault(){				
			document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
		}		
	</script>	
	</html:form>
</sakai:html>