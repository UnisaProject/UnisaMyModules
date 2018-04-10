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
	<html:hidden property="atstep" value="viewbooking"/>

	<script language="javascript">
		function printFriendly(){
			var url="<bean:write name="bookingForm" property="url"/>";
			window.open(url,'mywindow');
			}
	</script>
	
	<sakai:heading>
		<logic:equal name="bookingForm" property="systemID" value="1">
			<fmt:message key="dailyview.heading.sat"/>			
		</logic:equal>
		<logic:equal name="bookingForm" property="systemID" value="2">
			<fmt:message key="dailyview.heading.venue"/>	
		</logic:equal>
	</sakai:heading>

	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">
		<sakai:heading>
			<fmt:message key="book.view"/>
		</sakai:heading>
	</logic:equal>
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}" >
		<sakai:heading>
			<fmt:message key="book.view"/>
		</sakai:heading>
	</logic:equal>

	<sakai:messages/>
	<sakai:messages message="true"/>

		<sakai:group_table>
			<tr>
				<td colspan='3'>
					<sakai:group_heading><fmt:message key="viewbooking.maindetail"/></sakai:group_heading>
				</td>
			</tr>
			<tr>
				<td width='7%' valign='right'><fmt:message key="bookingstep2.heading"/></td>
				<td width='35%' colspan='2'>
					<bean:write name="bookingForm" property="heading"/>
				</td>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">
				</tr> <tr>
					<td align='right'><fmt:message key="bookingstep2.lecturer"/></td>
					<td>
						<bean:write name="bookingForm" property="lecturerNovellId"/> -
						<bean:write name="bookingForm" property="lecturerName"/>
					</td>
				</tr> <tr>
					<td align='right'><fmt:message key="viewbooking.contact"/></td>
					<td>
						<bean:write name="bookingForm" property="lecturerNum1"/> <br>
						<bean:write name="bookingForm" property="email"/>
					</td>
			</logic:equal>
			</tr><tr>
				<td align='right'><fmt:message key="viewbooking.startdate"/></td>
				<td>
					<bean:write name="bookingForm" property="startDate"/>
					<bean:write name="bookingForm" property="startHH"/>:<bean:write name="bookingForm" property="startMM"/>
				</td>
			</tr> <tr>
				<td align='right'><fmt:message key="viewbooking.enddate"/></td>
				<td>
					<bean:write name="bookingForm" property="endDate"/>
					<bean:write name="bookingForm" property="endHH"/>:<bean:write name="bookingForm" property="endMM"/>
				</td>
			</tr> <tr>
				<td align='right'><fmt:message key="bookingstep6.duration"/></td>
				<td><bean:write name="bookingForm" property="duration"/></td>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">
				</tr> <tr>
					<td align='right'><fmt:message key="bookingstep1.tableheading.registration"/></td>
					<td>
						<bean:write name="bookingForm" property="registrationPeriodDesc"/>
					</td>
				</tr> <tr>
					<td align='right'><fmt:message key="bookingstep1.tableheading.registrationyear"/></td>
					<td><bean:write name="bookingForm" property="registrationYear"/></td>
			</logic:equal>
			</tr> <tr>
				<td align='right'><fmt:message key="bookingstep2.description"/></td>
				<td><bean:write name="bookingForm" property="description"/></td>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">	
				<logic:equal name="bookingForm" property="rebroadcast" value="true">
					</tr><tr>
						<td><fmt:message key="bookingstep1.tableheading.isrebroadcast"/></td>
						<td><fmt:message key="bookingstep1.rebr"/></td>
					</tr><tr>
						<td><fmt:message key="viewbooking.rebroadcastdate"/></td>
						<td>
							<bean:write name="bookingForm" property="rebroadYear"/>-
							<bean:write name="bookingForm" property="rebroadMonth"/>-
							<bean:write name="bookingForm" property="rebroadDay"/>			
						</td>
				</logic:equal>
				<logic:equal name="bookingForm" property="rebroadcast" value="false">
					</tr><tr>
						<td><fmt:message key="bookingstep1.tableheading.isrebroadcast"/></td>
						<td><fmt:message key="bookingstep1.livebr"/></td>
				</logic:equal>
			</logic:equal>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}">
				</tr><tr>
						<td><fmt:message key="bookingstep1.typebkng"/></td>
						<td><bean:write name="bookingForm" property="bkngTypeName"/></td>
			</logic:equal>
			</tr><tr>
				<td align='right'><fmt:message key="viewbooking.createdby"/></td>
				<td>
				<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">		
					<bean:write name="bookingForm" property="createdBy"/>
				</logic:equal>
				<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}">		
					<bean:write name="bookingForm" property="createdBy"/>-
					<bean:write name="bookingForm" property="lecturerName"/> <br
					<bean:write name="bookingForm" property="lecturerNum1"/> <br>
					<bean:write name="bookingForm" property="lecturerNum1"/> <br>
					<bean:write name="bookingForm" property="email"/>
				</logic:equal>
				</td>
			</tr><tr>
				<td align='right'><fmt:message key="viewbooking.createdon"/></td>
				<td><bean:write name="bookingForm" property="createdDate"/></td>
			</tr><tr>
				<td></td>
				<td valign='top'>
					<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
						<logic:equal name="bookingForm" property="headingMayUpdate" value="TRUE">
							<sakai:actions>
								<html:submit property="action">
									<fmt:message key="button.booking.editheading"/>
								</html:submit>
							</sakai:actions>
						</logic:equal>
					</logic:equal>
				</td>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">
				</tr> <tr>
					<td colspan='3'>
						<sakai:group_heading><fmt:message key="bookingstep2.subjects"/></sakai:group_heading>
					</td>
				</tr> <tr>
					<td>
						<logic:iterate name="bookingForm" property="selectedSubjectsAL" id="value" indexId="i">
							<bean:write name="value" property="value"/> <br>
						</logic:iterate>
					</td>
					<td valign='top' colspan='2'>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<logic:equal name="bookingForm" property="mayUpdate" value="TRUE">
								<sakai:actions>
									<html:submit property="action">
										<fmt:message key="button.booking.editsubjects"/>
									</html:submit>
								</sakai:actions>
							</logic:equal>
						</logic:equal>
					</td>
			</logic:equal>
			</tr> <tr>
				<td colspan='3'>
					<sakai:group_heading><fmt:message key="bookingstep3.materials"/></sakai:group_heading>
				</td>
			</tr><tr>
				<td>
					<logic:iterate name="bookingForm" property="selectedMaterialsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>
				<td valign='top' colspan='2'>
					<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
						<logic:equal name="bookingForm" property="mayUpdate" value="TRUE">
							<sakai:actions>
								<html:submit property="action">
									<fmt:message key="button.booking.editmaterials"/>
								</html:submit>
							</sakai:actions>
						</logic:equal>
					</logic:equal>
				</td>
			<logic:equal name="bookingForm" property="systemID" value="${bookingForm.satbook}">	
				</tr> <tr>
					<td colspan='3'>
						<sakai:group_heading><fmt:message key="bookingstep4.visitors"/></sakai:group_heading>
					</td>
				</tr><tr>
					<td>
						<logic:iterate name="bookingForm" property="visitors" id="value" indexId="i">
							<bean:write name="value" property="label"/> <br>
						</logic:iterate>
					</td>
					<td valign='top' colspan='2'>
						<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
							<logic:equal name="bookingForm" property="mayUpdate" value="TRUE">
								<sakai:actions>
									<html:submit property="action">
										<fmt:message key="button.booking.editvisitors"/>
									</html:submit>
								</sakai:actions>
							</logic:equal>
						</logic:equal>
					</td>
				</logic:equal>
			</tr> <tr>
				<td colspan='3'>
					<sakai:group_heading><fmt:message key="bookingstep5.classrooms"/></sakai:group_heading>
				</td>
			</tr><tr>
				<td colspan='2'>
					<logic:iterate name="bookingForm" property="selectedClassroomsAL" id="value" indexId="i">
						<bean:write name="value" property="value"/> <br>
					</logic:iterate>
				</td>	
				<td>
					<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
						<logic:equal name="bookingForm" property="mayUpdate" value="TRUE">
							<sakai:actions>
								<html:submit property="action">
									<fmt:message key="button.booking.editclassrooms"/>
								</html:submit>
							</sakai:actions>
						</logic:equal>
					</logic:equal>
				</td>			
			</tr>
		</sakai:group_table>
	<sakai:actions>
		<logic:notEqual name="bookingForm" property="viewStatus" value="print">
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
			<html:submit property="action" onclick="javascript:printFriendly();return false">
				<fmt:message key="button.printable"/>
			</html:submit>
		</logic:notEqual>
		<logic:equal name="bookingForm" property="viewStatus" value="print">
			<input type="button" value="Print" onClick="window.print()" />
			<input type="button" value="Close Window" onClick="window.close()" />
		</logic:equal>
	</sakai:actions>
	</html:form>
</sakai:html>