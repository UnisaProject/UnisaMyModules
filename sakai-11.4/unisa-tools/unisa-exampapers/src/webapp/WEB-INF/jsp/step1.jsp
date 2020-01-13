<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.exampapers.ApplicationResources"/>

<sakai:html>
	<script language="javascript">
		function printFriendly(){
			var url="<bean:write name="examPaperCoverDocketForm" property="printFriendlyUrl"/>";
			window.open(url,'mywindow');
			}			
	</script>
	<logic:equal name="examPaperCoverDocketForm" property="displayLetterHeadRights" value="true">
		<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="true">
			<sakai:tool_bar>
				<bean:write  name="examPaperCoverDocketForm" property="examPaperLetterHead" filter="false"/>
			</sakai:tool_bar>
		</logic:equal>
	</logic:equal>
	<html:form action="/examPaperCoverDocket">
		<sakai:messages/>
		<sakai:messages message="true"/>
		<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="true">
			<sakai:instruction>
				<fmt:message key="page.update.instruction"/>
			</sakai:instruction>
			<sakai:group_table>	
			<tr>
				<td>(<fmt:message key="page.lastUpdatedBy"/>:&nbsp;<bean:write name="examPaperCoverDocketForm" property="lastUpdatedBy"/>&nbsp;&nbsp;<fmt:message key="page.updatedOn"/>:&nbsp;<bean:write name="examPaperCoverDocketForm" property="lastUpdatedOn"/>)</td>
			</tr>
			<tr>
				<td>(<fmt:message key="page.contact"/>&nbsp;<bean:write name="contactName"/>
				<logic:notEmpty name="examPaperCoverDocketForm" property="contact.cellNumber">&nbsp;&nbsp;<fmt:message key="page.cellNumber"/>:&nbsp;<bean:write name="examPaperCoverDocketForm" property="contact.cellNumber"/></logic:notEmpty>
				<logic:notEmpty name="examPaperCoverDocketForm" property="contact.workNumber">&nbsp;&nbsp;<fmt:message key="page.workNumber"/>:&nbsp;<bean:write name="examPaperCoverDocketForm" property="contact.workNumber"/></logic:notEmpty>)
				</td>
			</tr>
			</sakai:group_table>	
		</logic:equal>
		<logic:equal  name="examPaperCoverDocketForm" property="docketExists" value="false">
			<sakai:instruction>
			<fmt:message key="page.instruction"/>
		</sakai:instruction>
		</logic:equal >
		<sakai:group_heading>
			<fmt:message key="step1.groupheading"/> 
		</sakai:group_heading>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.studyUnit"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnit" /></td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.studyUnitDesc" /></td>
			</tr>
			<tr>
				<td><fmt:message key="page.paperNumber"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.paperNo" /></td>
			</tr>
			<tr>
				<td><fmt:message key="page.examination"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examPeriodDesc"/>&nbsp;<bean:write name="examPaperCoverDocketForm" property="exampaper.examYear"/></td>
			</tr>
		</sakai:group_table>	
		<hr/>
		<!--<sakai:group_table>	
			<tr>
				<td><fmt:message key="permissions.updatenrofpages"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="updateNrOfPagesRights" /></td>				
			</tr>
			<tr>
				<td><fmt:message key="permissions.update"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="updateRights" /></td>
			</tr>
			<tr>
				<td><fmt:message key="permissions.updatestatusclose"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="updateStatusClosedRights"/>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="permissions.updatestatusopen"/>&nbsp;</td>			
				<td><bean:write name="examPaperCoverDocketForm" property="updateStatusOpenRights"/>&nbsp;</td>
			</tr>
		</sakai:group_table>-->	
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="page.examDate"/></td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examDate"/>&nbsp;</td>
				<td><bean:write name="examPaperCoverDocketForm" property="exampaper.examTime"/></td>
			</tr>
			<tr>
				<td><fmt:message key="page.duration"/></td>
				<td>
				<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationHours" value="0">
				<bean:write name="examPaperCoverDocketForm" property="exampaper.durationHours"/>&nbsp;<fmt:message key="page.hours"/>&nbsp;&nbsp;
				</logic:notEqual>
				<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationMin" value="0">
					<bean:write name="examPaperCoverDocketForm" property="exampaper.durationMin"/>&nbsp;<fmt:message key="page.minutes"/>
				</logic:notEqual>
				<logic:notEqual name="examPaperCoverDocketForm" property="exampaper.durationDays" value="0">
					<bean:write name="examPaperCoverDocketForm" property="exampaper.durationDays"/>&nbsp;<fmt:message key="page.days"/>
				</logic:notEqual>
				</td>
				<td>&nbsp;</td>
			</tr>
		</sakai:group_table>	
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.combinedPapers"/></th>		
				<th align="left"><fmt:message key="page.examDate"/></th>
				<th align="left"><fmt:message key="page.duration"/></th>				
			</tr>
			<logic:empty name="examPaperCoverDocketForm" property="exampaper.combinedPapers">
				<tr>
					<td colspan="3"><fmt:message key="page.none"/></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.combinedPapers">
				<logic:iterate name="examPaperCoverDocketForm" property="exampaper.combinedPapers" id="record">
					<tr>
						<td><bean:write name="record" property="studyUnit"/>
						</td>
						<td>
							<bean:write name="record" property="examDate"/>&nbsp;&nbsp;
							<bean:write name="record" property="examTime"/>
						</td>
						<td>
							<logic:notEqual name="record" property="durationHours" value="0">
								<bean:write name="record" property="durationHours"/>&nbsp;<fmt:message key="page.hours"/>&nbsp;&nbsp;
							</logic:notEqual>
							<logic:notEqual name="record" property="durationMin" value="0">
								<bean:write name="record" property="durationMin"/>&nbsp;<fmt:message key="page.minutes"/>&nbsp;&nbsp;
							</logic:notEqual>
							<logic:notEqual name="record" property="durationDays" value="0">
								<bean:write name="record" property="durationDays"/>&nbsp;<fmt:message key="page.days"/>&nbsp;&nbsp;
							</logic:notEqual>
						</td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<!--<logic:equal name="examPaperCoverDocketForm" property="educationFaculty" value="true">
			<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.examPanel"/></th>
			</tr>
			</sakai:flat_list>
		</logic:equal>-->
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.firstExaminers"/></th>
			</tr>
			<logic:empty name="examPaperCoverDocketForm" property="exampaper.firstExaminers">
				<tr>
					<td><fmt:message key="page.none"/></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.firstExaminers">
				<logic:iterate name="examPaperCoverDocketForm" property="exampaper.firstExaminers" id="record">
					<tr>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.secondExaminers"/></th>
			</tr>
			<logic:empty name="examPaperCoverDocketForm" property="exampaper.secondExaminers">
				<tr>
					<td><fmt:message key="page.none"/></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.secondExaminers">
				<logic:iterate name="examPaperCoverDocketForm" property="exampaper.secondExaminers" id="record">
					<tr>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<sakai:flat_list>
			<tr>
				<th align="left"><fmt:message key="page.externalExaminers"/></th>
			</tr>
			<logic:empty name="examPaperCoverDocketForm" property="exampaper.externalExaminers">
				<tr>
					<td><fmt:message key="page.none"/></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="examPaperCoverDocketForm" property="exampaper.externalExaminers">
				<logic:iterate name="examPaperCoverDocketForm" property="exampaper.externalExaminers" id="record">
					<tr>
						<td><bean:write name="record"/></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</sakai:flat_list>
		<sakai:instruction>
			<sakai:group_table>
				<tr>
					<td><fmt:message key="step1.instruction1"/></td>
				</tr>				
				<tr>
					<td>
						<sakai:group_heading>
							<fmt:message key = "step1.note1"/>
						</sakai:group_heading>
					</td>
				</tr>
			</sakai:group_table>
		</sakai:instruction>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.continue"/>
			</html:submit>		
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>		
			<logic:equal name="examPaperCoverDocketForm" property="docketExists" value="true">	
				<html:submit property="action" onclick="javascript:printFriendly();return false">
					<fmt:message key="button.view"/>			
				</html:submit>
			</logic:equal>			
		</sakai:actions>
	</html:form>
</sakai:html>
