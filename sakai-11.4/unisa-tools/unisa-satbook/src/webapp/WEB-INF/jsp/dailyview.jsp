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
	

	<sakai:heading>
	<logic:equal name="dailyForm" property="systemID" value="1">
			<fmt:message key="dailyview.heading.sat"/>
				
	</logic:equal>
	<logic:equal name="dailyForm" property="systemID" value="2">
		<fmt:message key="dailyview.heading.venue"/>
		<sakai:tool_bar>	 
       	 <html:link  href="HelpForVenueBooking.pdf" target="_Blank"><img align="right" src="/library/skin/unisa/images/help.gif"></html:link> 
	    </sakai:tool_bar>
	</logic:equal>
	</sakai:heading>
	
	<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<logic:equal name="monthlyForm" property="userPermission" value="MAINTAIN">
			<html:link href="satbookMonthly.do?action=adminview">
				<fmt:message key="button.monthlyview.adminview"/>
			</html:link>
		</logic:equal>
		<html:link href="satbookDaily.do?action=bookingsreport">
			<fmt:message key="booking.report.heading"/>
		</html:link>
	</sakai:tool_bar>

    <sakai:heading><fmt:message key="dailyview.heading"/></sakai:heading>
	<sakai:group_heading>
		<fmt:message key="dailyview.tableheading.date"/>
		<bean:write name="dailyForm" property="viewDate"/>
	</sakai:group_heading>

	<logic:equal name="dailyForm" property="systemID" value="1">
		<font color="#F88017">
			<b>This day belongs to <bean:write name="dailyForm" property="institution"/> </b>
		</font>
	</logic:equal>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<logic:equal name="dailyForm" property="systemID" value="1">
	    <logic:notEmpty name="dailyForm" property="bookingList">
		<sakai:flat_list>
			<tr>
				<th> <fmt:message key="dailyview.tableheading.starttime"/> </th>
				<th> <fmt:message key="dailyview.tableheading.broadcast"/> </th>
				<th> <fmt:message key="dailyview.tableheading.confirmed"/> </th>
				<th> <fmt:message key="bookingstep1.tableheading.isrebroadcast"/> </th>
			</tr>
			<logic:iterate name="dailyForm" property="bookingList" id="record" indexId="i">
				<tr>
					<td>
						<html:radio property="selectedBooking" value="bkngId" idName="record"/>
						<bean:write name="record" property="startDate"/> &nbsp; &nbsp;
						<bean:write name="record" property="startHH"/>:<bean:write name="record" property="startMM"/> -
						<bean:write name="record" property="endHH"/>:<bean:write name="record" property="endMM"/>
					</td>
					<td>
						<bean:write name="record" property="heading"/>
					</td>
					<td>
						<bean:write name="record" property="bkngConfirmed"/>
					</td>
					<td>
						<logic:equal name="record" property="rebroadcast" value="true">
							Yes
						</logic:equal>
						<logic:equal name="record" property="rebroadcast" value="false">
							No
						</logic:equal>
					</td>
					
				</tr>
			</logic:iterate>
		</sakai:flat_list>
		</logic:notEmpty>
	</logic:equal>
	<logic:equal name="dailyForm" property="systemID" value="2">
	    
		<logic:notEmpty name="dailyForm" property="bookingList">
		<sakai:flat_list>
			<tr>
				<th> <fmt:message key="dailyview.tableheading.starttime"/> </th>
				<th> <fmt:message key="dailyview.tableheading.broadcast"/> </th>
				<th> <fmt:message key="dailyview.tableheading.confirmed"/> </th>
			</tr>
			<logic:iterate name="dailyForm" property="bookingList" id="record" indexId="i">
				<tr>
					<td>
						<html:radio property="selectedBooking" value="bkngId" idName="record"/>
						<bean:write name="record" property="startDate"/> &nbsp; &nbsp;
						<bean:write name="record" property="startHH"/>:<bean:write name="record" property="startMM"/> -
						<bean:write name="record" property="endHH"/>:<bean:write name="record" property="endMM"/>
					</td>
					<td>
						<bean:write name="record" property="heading"/>
					</td>
					<td>
						<bean:write name="record" property="bkngConfirmed"/>
					</td>
				</tr>
			</logic:iterate>
				</sakai:flat_list>
	</logic:notEmpty>
		</logic:equal>		
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="dailyview.button.view"/>
		</html:submit>
		<logic:equal name="dailyForm" property="systemID" value="${dailyForm.satbook}">
			<logic:equal name="dailyForm" property="institution" value="UNISA">
				<logic:equal name="dailyForm" property="bookingDisable" value="false">
					<html:submit property="action" disabled="true">
						<fmt:message key="button.dailyview.addbooking"/>
					</html:submit>
				</logic:equal>
				<logic:equal name="dailyForm" property="bookingDisable" value="true">
					<html:submit property="action">
						<fmt:message key="button.dailyview.addbooking"/>
					</html:submit>
				</logic:equal>
				<html:submit property="action">
					<fmt:message key="dailyview.button.edit"/>
				</html:submit>
			</logic:equal>
		</logic:equal>
		<logic:equal name="dailyForm" property="systemID" value="${dailyForm.venbook}">
				<logic:equal name="dailyForm" property="bookingDisable" value="false">
					<html:submit property="action" disabled="true">
						<fmt:message key="button.dailyview.addbooking"/>
					</html:submit>
				</logic:equal>
				<logic:equal name="dailyForm" property="bookingDisable" value="true">
					<html:submit property="action">
						<fmt:message key="button.dailyview.addbooking"/>
					</html:submit>
				</logic:equal>
				<html:submit property="action">
					<fmt:message key="dailyview.button.edit"/>
				</html:submit>
		</logic:equal>	
		<logic:equal name="dailyForm" property="userPermission" value="ACCESS">
			<logic:equal name="dailyForm" property="deleteDisable" value="false">	
				<html:submit property="action"  disabled="true">
					<fmt:message key="button.dailyview.delete"/>
				</html:submit>
			</logic:equal>
			<logic:equal name="dailyForm" property="deleteDisable" value="true">	
				<html:submit property="action">
					<fmt:message key="button.dailyview.delete"/>
				</html:submit>
			</logic:equal>
		</logic:equal>
		<logic:equal name="dailyForm" property="userPermission" value="MAINTAIN">
			<html:submit property="action">
				<fmt:message key="button.dailyview.delete"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="dailyview.button.confirm"/>
			</html:submit>
			<html:submit property="action">
				<fmt:message key="dailyview.button.unconfirm"/>
			</html:submit>
		</logic:equal>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>
	</html:form>
</sakai:html>
