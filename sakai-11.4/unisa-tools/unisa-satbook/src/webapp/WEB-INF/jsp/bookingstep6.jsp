<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>
	<html:form action="satbookBooking.do">
	<html:hidden property="atstep" value="6"/>
	<sakai:heading>
		<logic:equal name="bookingForm" property="systemID" value="1">
			<fmt:message key="dailyview.heading.sat"/>			
		</logic:equal>
		<logic:equal name="bookingForm" property="systemID" value="2">
			<fmt:message key="dailyview.heading.venue"/>	
		</logic:equal>
	</sakai:heading>
	
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}" >
		<sakai:heading>
			<fmt:message key="book.add"/>
		</sakai:heading>
		<fmt:message key="satbook.step1"/> <br></br>
		<fmt:message key="satbook.step2"/><br></br>
		<fmt:message key="satbook.step3"/><br></br>
		<fmt:message key="satbook.step4"/><br></br>
		<fmt:message key="satbook.step5"/><br></br>
		<b><fmt:message key="satbook.step6"/></b><br></br>
	</logic:equal>
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}" >
		<sakai:heading>
			<fmt:message key="book.add"/>
		</sakai:heading>
		<fmt:message key="venbook.step1"/><br></br>
		<fmt:message key="venbook.step2"/><br></br>
		<fmt:message key="venbook.step3"/><br></br>
		<b><fmt:message key="venbook.step4"/></b><br></br>	
	</logic:equal>


	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction><fmt:message key="bookingstep6.instruction"/></sakai:instruction>
	<sakai:group_heading><fmt:message key="bookingstep6.confirmation"/></sakai:group_heading>
	
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}" >
		<sakai:group_table>
			<tr>
				<td><fmt:message key="bookingstep2.heading"/></td>
				<td><bean:write name="bookingForm" property="heading"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep2.lecturer"/></td>
				<td>
					<bean:write name="bookingForm" property="lecturerNovellId"/> -
					<bean:write name="bookingForm" property="lecturerName"/> <br>
					<bean:write name="bookingForm" property="lecturerNum1"/> <br>
					<bean:write name="bookingForm" property="email"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.startdate"/></td>
				<td>
					<bean:write name="bookingForm" property="startDate"/> &nbsp;
					<bean:write name="bookingForm" property="startHH"/>:<bean:write name="bookingForm" property="startMM"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.enddate"/></td>
				<td>
					<bean:write name="bookingForm" property="endDate"/> &nbsp;
					<bean:write name="bookingForm" property="endHH"/>:<bean:write name="bookingForm" property="endMM"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep6.duration"/></td>
				<td><bean:write name="bookingForm" property="duration"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.registration"/></td>
				<td>
					<bean:write name="bookingForm" property="registrationPeriod"/>
					<bean:write name="bookingForm" property="registrationPeriodDesc"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.registrationyear"/></td>
				<td><bean:write name="bookingForm" property="registrationYear"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep2.description"/></td>
				<td><bean:write name="bookingForm" property="description"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.isrebroadcast"/></td>
				<td>
					<logic:equal name="bookingForm" property="rebroadcast" value="true">
						<fmt:message key="bookingstep1.rebr"/>
						<tr><td><fmt:message key="bookingstep1.tableheading.rebroadcastdate"/></td>
						<td><bean:write name="bookingForm" property="rebroadYear"/>-
							<bean:write name="bookingForm" property="rebroadMonth"/>-
							<bean:write name="bookingForm" property="rebroadDay"/>			
						</td>
					</tr>	
						
					</logic:equal>
					<logic:equal name="bookingForm" property="rebroadcast" value="false">
						<fmt:message key="bookingstep1.livebr"/>
					</logic:equal>
				</td>
			</tr><tr>
				<td><fmt:message key="bookingstep2.subjects"/></td>
				<td>
					<logic:iterate name="bookingForm" property="selectedSubjectsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep3.materials"/></td>
				<td>
					<logic:iterate name="bookingForm" property="selectedMaterialsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep4.visitors"/></td>
				<td>
					<logic:iterate name="bookingForm" property="visitors" id="value" indexId="i">
						<bean:write name="value" property="label"/> <br>
					</logic:iterate>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep5.classrooms"/></td>
				<td>
					<logic:iterate name="bookingForm" property="selectedClassroomsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
			</tr>
		</sakai:group_table>
	</logic:equal>
	
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}" >
		<sakai:group_table>
			<tr>
				<td><fmt:message key="bookingstep2.heading"/></td>
				<td><bean:write name="bookingForm" property="heading"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.createdby"/></td>
				<td>
					<bean:write name="bookingForm" property="createdBy"/>-
					<bean:write name="bookingForm" property="lecturerName"/> <br>
					<bean:write name="bookingForm" property="lecturerNum1"/> <br>
					<bean:write name="bookingForm" property="email"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.startdate"/></td>
				<td>
					<bean:write name="bookingForm" property="startDate"/> &nbsp;
					<bean:write name="bookingForm" property="startHH"/>:<bean:write name="bookingForm" property="startMM"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep1.tableheading.enddate"/></td>
				<td>
					<bean:write name="bookingForm" property="endDate"/> &nbsp;
					<bean:write name="bookingForm" property="endHH"/>:<bean:write name="bookingForm" property="endMM"/>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep6.duration"/></td>
				<td><bean:write name="bookingForm" property="duration"/></td>
			</tr> <tr>
				<td><fmt:message key="bookingstep2.description"/></td>
				<td><bean:write name="bookingForm" property="description"/></td>
			</tr><tr>
				<td><fmt:message key="bookingstep3.materials"/></td>
				<td>
					<logic:iterate name="bookingForm" property="selectedMaterialsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
			</tr> <tr>
				<td><fmt:message key="bookingstep5.classrooms"/></td>
				<td>
					<logic:iterate name="bookingForm" property="selectedClassroomsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
			</tr>
		</sakai:group_table>
	</logic:equal>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
		<logic:equal name="bookingForm" property="pageStatus" value="ADD">
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
		</logic:equal>
			<html:submit property="action">
				<fmt:message key="button.cancel"/>
			</html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>

