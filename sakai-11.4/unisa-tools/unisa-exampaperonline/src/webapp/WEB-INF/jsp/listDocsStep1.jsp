<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampaperonline.ApplicationResources"/>


<%@page import="java.util.*" %>
<%@page import="za.ac.unisa.lms.tools.exampaperonline.forms.ExamPaperOnlineForm"%><sakai:html>	
	<html:form action="/listDocument">	
		<html:hidden property="currentPage" value="listDocsStep1"/>
		<html:hidden property="xpoAction" value=""/>
		<html:hidden property="displayListAction" value="D"/>
		<sakai:messages/>
		<sakai:messages message="true"/>	
		<sakai:heading>
			<fmt:message key="listDocsStep1.heading"/>
		</sakai:heading>	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.examYear"/>&nbsp;</td>
				<td><html:text name="examPaperOnlineForm" property="listCriteriaExamYear" size="4" maxlength="4"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.examPeriod"/>&nbsp;</td>
				<td>
					<html:select name="examPaperOnlineForm" property="listCriteriaExamPeriod">
					<html:optionsCollection name="examPaperOnlineForm" property="listExamPeriods" value="code" label="engDescription"/>
					</html:select>
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><html:text name="examPaperOnlineForm" property="listCriteriaStudyUnit" size="10" maxlength="7"/>&nbsp;
					<bean:write name="examPaperOnlineForm" property="listCriteriaEquivalentStr"/>
				</td>								
			</tr>
			<tr>
				<td><fmt:message key="prompt.paperContent"/>&nbsp;</td>
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
			<logic:notEmpty name="examPaperOnlineForm" property="listFromRoles">
				<tr>	
						<td><fmt:message key="prompt.fromRole"/>&nbsp;</td>
						<td><html:select name="examPaperOnlineForm" property="listCriteriaFromRole">
								<html:options name="examPaperOnlineForm" property="listFromRoles"/>
								</html:select>
						</td>				
					</tr>
			</logic:notEmpty>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action" onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.display"/>
			</html:submit>		
			<html:submit property="action"  onclick="javascript:onClickSetXpoActionDefault();">
					<fmt:message key="button.clear"/>
			</html:submit>				
		</sakai:actions>		
		<hr/>			
		<sakai:flat_list>
			<tr>
				<td colspan="3">
					<sakai:heading>
						<fmt:message key="listDocsStep1.groupheading1"/> 
					</sakai:heading>			
					<sakai:instruction>
						<fmt:message key="listDocsStep1.note1"/>
					</sakai:instruction>
				</td>				
				<td align="right" colspan="2" style="white-space: nowrap">					
					<sakai:actions>
						<logic:equal name="examPaperOnlineForm"  property="pageUpFlag" value="Y">
							<html:submit property="action"  onclick="javascript:setPageUp();">
								<fmt:message key="button.previous"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examPaperOnlineForm"  property="pageUpFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.previous"/>
							</html:submit>
						</logic:notEqual>
						<html:submit property="action" onclick="javascript:setFirst();">
								<fmt:message key="button.first"/>
						</html:submit>
						<logic:equal name="examPaperOnlineForm"  property="pageDownFlag" value="Y">
							<html:submit property="action" onclick="javascript:setPageDown();">
								<fmt:message key="button.next"/>
							</html:submit>								
						</logic:equal>	
						<logic:notEqual name="examPaperOnlineForm"  property="pageDownFlag" value="Y">	
							<html:submit property="action" disabled="true">
								<fmt:message key="button.next"/>
							</html:submit>
						</logic:notEqual>	
					</sakai:actions>
				</td>
			</tr>
			<tr>
				<th align="left"><fmt:message key="tabHead.listdocs.examination"/></th>
				<th align="left"><fmt:message key="tabHead.listdocs.linkedPapers"/></th>
				<th align="left"><fmt:message key="tabHead.listdocs.myRole"/></th>
				<th align="left"><fmt:message key="tabHead.listdocs.from"/></th>
				<th align="left"><fmt:message key="tabHead.listdocs.response"/></th>
				<th align="left"><fmt:message key="tabHead.listdocs.dateSent"/></th>				
			</tr>
			<logic:notEmpty name="examPaperOnlineForm" property="availableDocList">
				<logic:iterate name="examPaperOnlineForm" property="availableDocList" id="record">
					<bean:define id="studyUnit" name="record" property="studyUnit"/>
					<bean:define id="examYear" name="record" property="examYear"/>
					<bean:define id="examPeriod" name="record" property="examPeriod"/>
					<bean:define id="paperNo" name="record" property="paperNo"/>
					<bean:define id="docType" name="record" property="docType"/>
					<% 
						java.util.HashMap params = new java.util.HashMap();
						params.put("xpoStudyUnit",studyUnit);
						params.put("xpoExamYear",examYear);
						params.put("xpoExamPeriod",examPeriod);
						params.put("xpoPaperNo",paperNo);
						params.put("xpoDocType",docType);
						if (docType.toString().equalsIgnoreCase("MEMO")){
							params.put("xpoIsMemo","Yes");
						}else{
							params.put("xpoIsMemo","No");
						}
						params.put("xpoAction","ListDocs");
						pageContext.setAttribute("params",params);
					%>
					<% 
						java.util.HashMap uploadParams = new java.util.HashMap();
						uploadParams.put("studyUnit",studyUnit);
						uploadParams.put("examYear",examYear);
						uploadParams.put("examPeriod",examPeriod);
						uploadParams.put("paperNo",paperNo);
						uploadParams.put("docType",docType);
						uploadParams.put("fromAction","LISTDOCS");
						pageContext.setAttribute("uploadParams",uploadParams);
					%>
					<tr> 						
						<td style="white-space: nowrap">
							<bean:write name="record" property="studyUnit"/>-
							<bean:write name="record" property="examYear"/>-
							<bean:write name="record" property="examPeriod"/>-
							<bean:write name="record" property="paperNo"/>-
							<bean:write name="record" property="docType"/>&nbsp;&nbsp;&nbsp;
							<html:link href="listDocument.do?action=show" scope="page" name="params">                                   							
									<fmt:message key="link.show"/>
							</html:link>&nbsp;&nbsp;
							<html:link href="uploadDocument.do?action=initial" scope="page" name="uploadParams">                                   							
									<fmt:message key="link.upload"/>
							</html:link>	
							<bean:define id="userList" name="examPaperOnlineForm" property="listUserId"/>	
							<%
							List listUserId = (List) userList;
							for (int i = 0; i < listUserId.size(); i++){
								if (listUserId.get(i).toString().equalsIgnoreCase("PRINT_PROD")
								|| listUserId.get(i).toString().equalsIgnoreCase("EXAMS_QA2")){%>	
									<html:link href="printInstruction.do?action=initial" scope="page" name="uploadParams">                                   							
										<fmt:message key="link.printInstr"/>
									</html:link>
								<%i=listUserId.size();
								}		
							}%>							
						</td>
						<td style="white-space: nowrap">
							<logic:notEmpty name="record" property="linkedPaper.linkedDesc">
								<span title="<bean:write name="record" property="linkedPaper.linkedDesc"/>"><bean:write name="record" property="linkedPaper.shortLinkedDesc"/></span>
							</logic:notEmpty>
						</td>	
						<td style="white-space: nowrap"><bean:write name="record" property="actionedRole"/></td>					
						<td style="white-space: nowrap"><logic:notEmpty  name="record" property="currentUser"><bean:write name="record" property="currentUser"/> - <bean:write name="record" property="currentRole"/></logic:notEmpty></td>
						<td style="white-space: nowrap"><logic:notEmpty name="record" property="approvalStatus">(<bean:write name="record" property="approvalStatus"/>)</logic:notEmpty>
							<span title="<bean:write name="record" property="message"/>"><bean:write name="record" property="shortMessage"/></span>
						</td>
						<td style="white-space: nowrap"><bean:write name="record" property="updatedOn"/></td>
						
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
			function setPageUp(){
				document.forms["examPaperOnlineForm"].elements["displayListAction"].value="PU";
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";	
			}
			function setPageDown(){
				document.forms["examPaperOnlineForm"].elements["displayListAction"].value="PD";
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";	
			}
			function setFirst(){
				document.forms["examPaperOnlineForm"].elements["displayListAction"].value="D";
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";	
			}	
			function onClickSetXpoActionDefault(){				
				document.forms["examPaperOnlineForm"].elements["xpoAction"].value="";			
			}	
	</script>		
	</html:form>
</sakai:html>