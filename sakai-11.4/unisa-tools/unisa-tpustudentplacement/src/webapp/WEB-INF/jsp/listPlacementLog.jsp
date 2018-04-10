<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.tpustudentplacement.ApplicationResources"/>

<sakai:html>	
	<html:form action="/placementLogMaintenance">
			<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.placementLog"/>
		</sakai:heading>	
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:group_heading>
			<fmt:message key="placementLog.instruction"/> 
		</sakai:group_heading>
		<sakai:group_table  >	
			<tr>
				<td><fmt:message key="prompt.acadYear"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacementLog.year" size="4" maxlength="4"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.semester"/>&nbsp;<fmt:message key="prompt.mandatory"/></td>
				<td><html:select name="studentPlacementForm" property="studentPlacementLog.semester">
						<html:optionsCollection name="studentPlacementForm" property="listSemester" value="code" label="engDescription"/>
					</html:select>                                           
				</td>
				<td>&nbsp;</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.studentNumber"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacementLog.stuNum" size="10" maxlength="8"/></td>
				<td>&nbsp;</td>
			</tr>			
			<tr>
				<td><fmt:message key="prompt.module"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacementLog.module" size="10" maxlength="8"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.networkcode"/></td>
				<td><html:text name="studentPlacementForm" property="studentPlacementLog.updatedBy" size="10" maxlength="8"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td> <fmt:message key="prompt.column.actions"/>&nbsp;</td>
				<td>				
					     <html:radio property="studentPlacementLog.action"  value="All"><fmt:message key="prompt.all"/></html:radio><br>
					      <html:radio property="studentPlacementLog.action"  value="PlacementDetails"><fmt:message key="prompt.placementdetails"/></html:radio><br>
					      <html:radio property="studentPlacementLog.action"  value="Email"><fmt:message key="prompt.emails"/></html:radio><br>
					      <html:radio property="studentPlacementLog.action"  value="EmailsNsmses"><fmt:message key="prompt.emailsandsms"/></html:radio>
				</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.column.logsBeginDate"/></td>	
			     <td>
			         <html:text name="studentPlacementForm" property="studentPlacementLog.startDate" size="10" maxlength="10" ></html:text>
			         (<fmt:message key="prompt.dateformat"/>)
			     </td>
			</tr>
			<tr>
			     <td><fmt:message key="prompt.column.logsEndDate"/></td>	
			     <td>
			         <html:text name="studentPlacementForm" property="studentPlacementLog.endDate" size="10" maxlength="10" ></html:text>
			         (<fmt:message key="prompt.dateformat"/>)
			     </td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.sortorder"/>&nbsp;</td>
				<td><html:radio property="studentPlacementLog.sortOrder"  value="first" ><fmt:message key="prompt.logsFirstSortorder"/></html:radio>
				    <html:radio property="studentPlacementLog.sortOrder"  value="second" ><fmt:message key="prompt.logsSecSortorder"/></html:radio>
				</td>
			</tr>					
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="action">
					<fmt:message key="button.display"/>
			</html:submit>
			<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
		</sakai:actions>			
		<hr/>
		<logic:equal  name="studentPlacementForm" property="justEnteredPlacementLogs" value="no">
		<sakai:flat_list>
		   <tr >
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.studentNumber"/></th>		
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.module"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.school"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.action"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.timestamp"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.user"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.correspondenceTo"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.email"/></th>	
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.cellNumber"/></th>
				<th style="white-space:nowrap;align:left"><fmt:message key="prompt.column.image"/></th>
				       	
			</tr>
			<logic:notEmpty  name="studentPlacementForm" property="listLogs">
			 <logic:iterate name="studentPlacementForm" property="listLogs" id="rec" indexId="index">
			      <bean:define id="logAction" name="rec" property="action"></bean:define>
				  <bean:define id="logDate" name="rec" property="updatedOn"></bean:define>
				  <bean:define id="userName" name="rec" property="updatedBy"></bean:define>
				  <bean:define id="stuNr" name="rec" property="stuNum"></bean:define>
				  <bean:define id="course" name="rec" property="module"></bean:define>
				  <bean:define id="schoolName" name="rec" property="schoolDesc"></bean:define>
				  <bean:define id="code" name="rec" property="schoolCode"></bean:define>
				  <bean:define id="afterImageStr" name="rec" property="afterImage"></bean:define>
				  <bean:define id="communicTo" name="rec" property="correspondenceTo"></bean:define>
				  <bean:define id="yearStr" name="rec" property="year"></bean:define>
				  <bean:define id="semesterStr" name="rec" property="semester"></bean:define>
				  
			      <%
					   java.util.HashMap params = new java.util.HashMap();
					   params.put ("action",logAction);
					   params.put ("updatedOn",logDate);
					   params.put ("updatedBy",userName);
					   params.put ("stuNum",stuNr);
					   params.put ("module",course);
					   params.put ("schoolDesc",schoolName);
					   params.put ("schoolCode",code);
					   params.put ("afterImage",afterImageStr);
					   params.put ("correspondenceTo",communicTo);
					   params.put ("year",yearStr);
					   params.put ("semester",semesterStr);
					   request.setAttribute("paramsName",params);
				   %>
				<tr>
					<td style="white-space:nowrap;align:left">	
						<bean:write name="rec" property="stuNum"/>
					</td>
					<td><bean:write name="rec" property="module"/></td>
					<td><bean:write name="rec" property="schoolDesc"/></td>
					<td>
					     <logic:equal name="rec" property="action"   value="EMAIL">
					          <logic:equal name="rec" property="correspondenceTo"   value="STUDENT">
					              <html:link href="studentPlacement.do?action=inputCorrespondence" name="paramsName">
						            <fmt:message key="link.email"/>	
						          </html:link>
						      </logic:equal>
						      <logic:equal name="rec" property="correspondenceTo"   value="SCHOOL">
					              <html:link href="studentPlacement.do?action=inputCorrespondence" name="paramsName">
						            <fmt:message key="link.email"/>	
						          </html:link>
						      </logic:equal>
						 </logic:equal>
					     <logic:notEqual name="rec" property="action"   value="EMAIL">
					        <bean:write name="rec" property="action"/>
					     </logic:notEqual>
					        
					</td>
					<td><bean:write name="rec" property="updatedOn"/></td>
					<td><bean:write name="rec" property="updatedBy"/></td>
					<td><bean:write name="rec" property="correspondenceTo"/></td>
					<td><bean:write name="rec" property="emailAddress"/></td>	
					<td><bean:write name="rec" property="cellNum"/></td>
					<td style="white-space:nowrap;align:left">
					      <logic:equal name="rec" property="imageTracker"   value="1">
					          <html:link href="placementLogMaintenance.do?action=getLogEntryDetails" name="paramsName">
						              <fmt:message key="link.details"/>	
						       </html:link>
					           <bean:write name="rec" property="afterImage"/>
					      </logic:equal>
					 </td>									
				</tr>
			</logic:iterate>
			</logic:notEmpty>
			<logic:empty  name="studentPlacementForm" property="listLogs">
			      <td><fmt:message key="message.emptylog"/></td>	
		    </logic:empty>
			      
		</sakai:flat_list>
		</logic:equal>
	</html:form>
</sakai:html>