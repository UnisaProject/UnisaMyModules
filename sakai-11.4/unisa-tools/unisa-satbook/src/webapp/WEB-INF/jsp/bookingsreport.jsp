<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>
  	<html:form action="/satbookDaily.do">

	<logic:equal name="dailyForm" property="systemID" value="${dailyForm.satbook}">
		<fmt:message key="dailyview.heading.sat"/>
	</logic:equal>
	<logic:equal name="dailyForm" property="systemID" value="${dailyForm.venbook}">
		<fmt:message key="dailyview.heading.venue"/>
	</logic:equal>
	<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<logic:equal name="dailyForm" property="userPermission" value="MAINTAIN">
			<html:link href="satbookMonthly.do?action=adminview">
				<fmt:message key="button.monthlyview.adminview"/>
			</html:link>
		</logic:equal>
	</sakai:tool_bar>

	<br>
	<sakai:messages/>
	<sakai:messages message="true"/>

    <sakai:heading><fmt:message key="booking.report.heading"/></sakai:heading>

    <sakai:group_table>
    	<tr>
			<td>Report</td>
			<td>
				<html:select property="selectedReport">
					<html:options name="dailyForm" collection="possibleReports" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="booking.report.type"/></td>
			<td>
				<html:select property="selectedReportType">
					<html:options name="dailyForm" collection="reportTypeList" property="value" labelProperty="label"/>
				</html:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="booking.report.fromdate"/></td>
			<td>
				<html:text property="fromDateDD" size="2" maxlength="2"/>-
				<html:text property="fromDateMM" size="2" maxlength="2"/>-
				<html:text property="fromDateYYYY" size="4" maxlength="4"/>
			</td>
		</tr><tr>
			<td><fmt:message key="emailschedule.todate"/></td>
			<td>
				<html:text property="toDateDD" size="2" maxlength="2"/>-
				<html:text property="toDateMM" size="2" maxlength="2"/>-
				<html:text property="toDateYYYY" size="4" maxlength="4"/>
			</td>
		</tr>
	</sakai:group_table>

<!--  SATBOOK -->
	<logic:equal name="dailyForm" property="systemID" value="${dailyForm.satbook}">
		<logic:notEmpty name="dailyForm" property="bookingList">
		<sakai:flat_list>
			<tr>
				<th> <fmt:message key="dailyview.tableheading.date"/> </th>
				<th> <fmt:message key="dailyview.tableheading.starttime"/> </th> 
				<th> <fmt:message key="dailyview.tableheading.duration"/> </th>	
				<th> <fmt:message key="dailyview.tableheading.lecturer"/> </th>
				
				<th> <fmt:message key="dailyview.tableheading.subjectCode"/> </th>
				<th> <fmt:message key="dailyview.tableheading.subjectName"/> </th>
				<th> <fmt:message key="dailyview.tableheading.registration"/> </th>
				<th> <fmt:message key="dailyview.tableheading.region"/> </th>
			
				
			</tr>
	     			<td><bean:write name="dailyForm" property="weekDay"/> </td>
			<logic:iterate name="dailyForm" property="bookingList" id="record" indexId="i">
		
				<tr>
					<td><bean:write name="record" property="startDate"/> </td>
					
					<td>
						<bean:write name="record" property="startHH"/>:<bean:write name="record" property="startMM"/> -
						<bean:write name="record" property="endHH"/>:<bean:write name="record" property="endMM"/>
					</td>
					<td><bean:write name="record" property="duration"/> </td>
					<td><bean:write name="record" property="lecturerName"/> </td>
					<td>
						<logic:iterate name="record" property="selectedSubjectsAL" id="value" indexId="i">
							<bean:write name="value" property="value"/> <br>
						
						</logic:iterate>
					</td>
					<td>
						<logic:iterate name="record" property="selectedSubjectNamesAL" id="value" indexId="i">
							<bean:write name="value" property="value"/> <br>
						
						</logic:iterate>
					</td>
												
					<%--  <th> <fmt:message key="bookingstep2.subjects"/></th>	
					<td><bean:write name="record" property="bkngConfirmed"/></td>
					<th> <fmt:message key="dailyview.tableheading.reg"/> </th>
					<td><bean:write name="record" property="heading"/></td>  --%>
					<td><bean:write name="record" property="registrationPeriodDesc"/></td>
					<td><fmt:message key="dailyview.instruction.region"/>
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		</logic:notEmpty>
	</logic:equal>
	
<!--  VENBOOK -->	
	<logic:equal name="dailyForm" property="systemID" value="${dailyForm.venbook}">	
		<!-- DETAILED REPORT -->
		<logic:equal name="dailyForm" property="selectedReport" value="DetailedReport"> 
			<logic:notEmpty name="dailyForm" property="bookingList">
			<sakai:flat_list>
				<tr>
					<th> <fmt:message key="dailyview.tableheading.date"/> </th>
					<th> <fmt:message key="dailyview.tableheading.starttime"/> </th> 
					<th> <fmt:message key="dailyview.tableheading.duration"/> </th>	
					<th> <fmt:message key="dailyview.tableheading.user"/> </th>
					<th> Department</th>				
					<th> <fmt:message key="dailyview.tableheading.heading"/> </th>
					<th> <fmt:message key="bookingstep1.typebkng"/> </th>
					<th> Venues </th>
				</tr>
				<logic:iterate name="dailyForm" property="bookingList" id="record" indexId="i">
					<tr>
						<td><bean:write name="record" property="startDate"/> </td>
						<td>
							<bean:write name="record" property="startHH"/>:<bean:write name="record" property="startMM"/> -
							<bean:write name="record" property="endHH"/>:<bean:write name="record" property="endMM"/>
						</td>
						<td><bean:write name="record" property="duration"/> </td>
						<td><bean:write name="record" property="lecturerName"/> </td>
						<td><bean:write name="record" property="dpt"/> </td>
						<td><bean:write name="record" property="heading"/></td>
						<td><bean:write name="record" property="bkngTypeName"/></td>
						<td><bean:write name="record" property="classroomStr"/></td>
					</tr>
				</logic:iterate>
			</sakai:flat_list>
			</logic:notEmpty>
		</logic:equal>	
		<!-- DAYS PER VENUE -->
		<logic:equal name="dailyForm" property="selectedReport" value="DaysReport"> 
			<logic:notEmpty name="dailyForm" property="venueList">
			<sakai:flat_list>
				<tr>
					<th> <fmt:message key="booking.report.MON"/> </th>
					<th> <fmt:message key="booking.report.VEN"/> </th> 
					<th> <fmt:message key="booking.report.NOFD"/> </th>	
				</tr>
				<logic:iterate name="dailyForm" property="venueList" id="record" indexId="i">
					<tr>
						<td><bean:write name="record" property="month"/> </td>
						<td><bean:write name="record" property="regionDesc"/></td>
						<td><bean:write name="record" property="numberOfDays"/> </td>
					</tr>
				</logic:iterate>
			</sakai:flat_list>
			</logic:notEmpty>
		</logic:equal>	
		<!-- HOURS PER VENUE -->
		<logic:equal name="dailyForm" property="selectedReport" value="HoursReport"> 
			<logic:notEmpty name="dailyForm" property="venueList">
			<sakai:flat_list>
				<tr>
					<th> <fmt:message key="booking.report.MON"/> </th>
					<th> <fmt:message key="booking.report.VEN"/> </th> 
					<th> <fmt:message key="booking.report.NOFH"/> </th>	
				</tr>
				<logic:iterate name="dailyForm" property="venueList" id="record" indexId="i">
					<tr>
						<td><bean:write name="record" property="month"/> </td>
						<td><bean:write name="record" property="regionDesc"/></td>
						<td><bean:write name="record" property="numberOfDays"/> </td>
					</tr>
				</logic:iterate>
			</sakai:flat_list>
			</logic:notEmpty>
		</logic:equal>	
	</logic:equal>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="booking.button.get"/>
		</html:submit>
			<html:submit property="action">
			<fmt:message key="booking.button.export"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>

	</html:form>
</sakai:html>