<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.assmarkerreallocation.ApplicationResources"/>

<sakai:html>
	<html:form action="/assMarkerReallocation">
		<html:hidden property="currentPage" value="detail"/>
		<sakai:messages/>
		<sakai:messages message="true"/>
		<sakai:heading>
			<fmt:message key="heading.detail"/>
		</sakai:heading>		
		<sakai:instruction>
			<fmt:message key="note.required"/>&nbsp;<fmt:message key="prompt.mandatory"/>
		</sakai:instruction>
		<sakai:group_table>	
			<tr>
				<td><fmt:message key="prompt.studyUnit"/>&nbsp;</td>
				<td><bean:write name="assMarkerReallocationForm" property="studyUnitRecord.code"/></td>
				<td><bean:write name="assMarkerReallocationForm" property="studyUnitRecord.engLongDesc"/></td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.yearSemester"/>&nbsp;</td>			
				<td><bean:write name="assMarkerReallocationForm" property="acadYear"/>/<bean:write name="assMarkerReallocationForm" property="semesterType"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.assignmentNumber"/>&nbsp;</td>			
				<td><bean:write name="assMarkerReallocationForm" property="assignmentNr"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.studentsRegistered"/>&nbsp;</td>			
				<td><bean:write name="assMarkerReallocationForm" property="studentsRegistered"/></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><fmt:message key="prompt.fileFormats"/>&nbsp;</td>			
				<td colspan="2"><bean:write name="assMarkerReallocationForm" property="fileFormats"/></td>				
			</tr>
			<tr>
				<td><fmt:message key="note.assessment.languagesforstu"/>&nbsp;</td>			
				<td colspan="2"><B><bean:write name="assMarkerReallocationForm" property="markingLanguageListAsString"/></B>                     </td>				
			</tr>
		</sakai:group_table>	
		<hr/>
		
		<%@page import="java.util.*" %>
		<%@page import="java.text.SimpleDateFormat" %>
		<%		
		java.util.Calendar calender = java.util.Calendar.getInstance();  
		Date today = calender.getTime();
		SimpleDateFormat  df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String currentDate = df.format(today); 		
		%>		
		
		<sakai:heading>
			<fmt:message key="heading.markers"/>&nbsp;<%=currentDate%>
		</sakai:heading>
		<sakai:instruction>
			<fmt:message key="note.detail.markers1"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="note.detail.markers2"/>
		</sakai:instruction>
		<sakai:instruction>
			<fmt:message key="note.detail.markers3"/>
		</sakai:instruction>
		
		<sakai:flat_list>
			<tr>
				<th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.toMarkPerc"/></th>
				<th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.actualMarkPerc"/></th>
				<th><fmt:message key="prompt.marker.tableHeading.name"/></th>
				<th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.personnelNr"/></th>	
				<th><fmt:message key="prompt.marker.tableHeading.department"/></th>	
				     <th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.studentSubmit"/></th>	
				     <th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.marked"/></th>	
				     <th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.markesOutstanding"/></th>	
				     <th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.avgMarkPerc"/></th>
				     <th><fmt:message key="prompt.marker.tableHeading.emplstastus"/></th>	
				     <th><fmt:message key="prompt.marker.tableHeading.role"/></th>
				<th><fmt:message key="prompt.marker.tableHeading.resignDate"/></th>
				<th style="text-align:right"><fmt:message key="prompt.marker.tableHeading.markinglanguages"/></th>			
			</tr>
			<logic:iterate name="assMarkerReallocationForm" property="listMarkerDetail" id="record" indexId="index">
				<tr>
					<td style="text-align:right">
							  <logic:equal name="record" property="marker.status" value="Inactive">
									<logic:equal name="record" property="marker.markPercentage" value="0">
										<bean:write name="record" property="marker.markPercentage"/>
									</logic:equal>
								</logic:equal>	
								<logic:equal name="record" property="marker.status" value="Inactive">
									<logic:notEqual name="record" property="marker.markPercentage" value="0">
										<html:text style="text-align:right" name="assMarkerReallocationForm" property='<%= "listMarkerDetail[" + index.toString() + "].marker.markPercentage"%>' size="4" maxlength="3"/>
									</logic:notEqual>
								</logic:equal>	
								<logic:notEqual name="record" property="marker.status" value="Inactive">					
									<html:text style="text-align:right" name="assMarkerReallocationForm" property='<%= "listMarkerDetail[" + index.toString() + "].marker.markPercentage"%>' size="4" maxlength="3"/>
								</logic:notEqual>						
				     </td>
					 <td style="text-align:right"><bean:write name="record" property="actualMarkedPerc"/></td>	
					 <td><bean:write name="record" property="marker.person.name"/></td>	
					 <td style="text-align:right"><bean:write name="record" property="marker.person.personnelNumber"/></td>					
					 <td><bean:write name="record" property="marker.departmentDesc"/></td>
					 <td style="text-align:right"><bean:write name="record" property="studentSubmit"/></td>
					 <td style="text-align:right"><bean:write name="record" property="marked"/></td>
					 <td style="text-align:right"><bean:write name="record" property="marksOutstanding"/></td>
					 <td style="text-align:right"><bean:write name="record" property="avgMarkPerc"/></td>
					 <td><bean:write name="record" property="marker.status"/></td>
					 <td style="text-align:right"><bean:write name="record" property="marker.role"/></td>
					 <td><bean:write name="record" property="marker.person.resignDate"/></td>
			         <td >
			          <logic:notEmpty name="assMarkerReallocationForm" property='<%= "listMarkerDetail[" + index.toString() + "].marker.markingLanguageList"%>'>
                        <logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="false">
                              <logic:iterate name="assMarkerReallocationForm" property='<%= "listMarkerDetail[" + index.toString() + "].marker.markingLanguageList"%>'    id="rec"  indexId="indx">
							           <bean:write name="assMarkerReallocationForm" property='<%= "listMarkerDetail[" + index.toString() + "].marker.markingLanguageList["+ indx.toString() +"]"%>'/><BR>
							 </logic:iterate>
						  </logic:equal>
				       </logic:notEmpty>
                    <logic:notEmpty name="assMarkerReallocationForm" property="markingLanguageList" >
                          <logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="true">
                            <html:select name="assMarkerReallocationForm" 
					                    property='<%= "listMarkerDetail[" + index.toString() + "].marker.chosenMarkingLanguageList"%>' multiple="true">
					                          <html:optionsCollection name="assMarkerReallocationForm" property="markingLanguageList" value="language" label="language" />
						      </html:select>  
					       </logic:equal>                                         
				    </logic:notEmpty>
			     </td>
			</tr>
			</logic:iterate>
		</sakai:flat_list>
		<sakai:actions>
			<logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="true">
				<html:submit property="action">
						<fmt:message key="button.update"/>
				</html:submit>	
			</logic:equal>
			<logic:equal name="assMarkerReallocationForm" property="updateAllowed" value="false">
				<html:submit property="action" disabled="true">
						<fmt:message key="button.update"/>
				</html:submit>	
			</logic:equal>
			<html:submit property="action">
					<fmt:message key="button.cancel"/>
			</html:submit>	
				<html:submit property="action">
					<fmt:message key="button.back"/>
			</html:submit>		
		</sakai:actions>				
	</html:form>
</sakai:html>