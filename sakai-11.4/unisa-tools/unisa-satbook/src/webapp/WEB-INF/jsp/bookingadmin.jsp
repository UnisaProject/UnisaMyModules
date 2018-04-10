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
	<html:hidden property="atstep" value="bookingadminpage"/>
	<sakai:heading>
		<fmt:message key="adminedit.heading"/> <br>
		<fmt:message key="bookingstep2.heading"/>
		<bean:write name="bookingForm" property="heading"/><br>
		<fmt:message key="bookingstep2.description"/>
		<bean:write name="bookingForm" property="description"/>
	</sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction><fmt:message key="adminedit.instruction"/></sakai:instruction>

	<sakai:group_table>
		<tr>
			<th colspan='6'><fmt:message key="adminedit.information"/></th>
		</tr><tr>
			<th>
				<fmt:message key="adminedit.format1"/> <br>
				<fmt:message key="adminedit.format2"/> <br>
				<fmt:message key="adminedit.format3"/>
			</th>
			<th>
				<fmt:message key="adminedit.casettenumber1"/><br>
				<fmt:message key="adminedit.casettenumber2"/><br>
			</th>
			<th>
				<fmt:message key="adminedit.timecodein1"/><br>
				<fmt:message key="adminedit.timecodein2"/>
			</th>
			<th>
				<fmt:message key="adminedit.timecodeout1"/> <br>
				<fmt:message key="adminedit.timecodeout2"/>
			</th>
			<th> <fmt:message key="adminedit.duration"/> <br><fmt:message key="adminedit.timecodeout2"/> </th>
			<th> <fmt:message key="adminedit.duplication"/> <br>Quantity</th>
		</tr>

		<logic:iterate name="bookingForm" property="adminMaterials" id="materialRecord" indexId="j">
			<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
				<tr>
					<td> <bean:write name="materialRecord" property="materialDesc"/> </td>
					<td> <html:text property='<%= "materialRecord["+j+"].cassette" %>' size="5" maxlength="10"/> </td>
					<td>
						<html:text property='<%= "materialRecord["+j+"].timeInHH" %>'  size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeInMM" %>' size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeInSS" %>' size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeInFF" %>' size="2" maxlength="2"/>
					</td>
					<td>
						<html:text property='<%= "materialRecord["+j+"].timeOutHH" %>' size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeOutMM" %>' size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeOutSS" %>' size="2" maxlength="2"/>:
						<html:text property='<%= "materialRecord["+j+"].timeOutFF" %>' size="2" maxlength="2"/>
					</td>
					<td>
						<bean:write name="materialRecord" property="duration"/>
					</td>
					<td>
						<html:text property='<%= "materialRecord["+j+"].duplication" %>' size="5" maxlength="5"/>
					</td>
				</tr>
			</logic:equal>
			<logic:equal name="bookingForm" property="pageStatus" value="VIEW">
				<tr>
					<td> <bean:write name="materialRecord" property="materialDesc"/> </td>
					<td> <bean:write name="materialRecord" property="cassette"/> </td>
					<td>
						<bean:write name="materialRecord" property="timeIn"/>:<bean:write name="materialRecord" property="timeInFF"/>
					</td>
					<td>
						<bean:write name="materialRecord" property="timeOut"/>:<bean:write name="materialRecord" property="timeOutFF"/>
					</td>
					<td>
						<bean:write name="materialRecord" property="duration"/>
					</td>
					<td>
						<bean:write name="materialRecord" property="duplication"/>
					</td>
				</tr>
			</logic:equal>
		</logic:iterate>
		<tr>
			<th colspan='6'><fmt:message key="adminedit.technicalerrors"/></th>
		</tr><tr>
			<td colspan='6'>
				<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
					<html:textarea property="technicalError" cols="25" rows="4"></html:textarea>
				</logic:equal>
				<logic:equal name="bookingForm" property="pageStatus" value="VIEW">
					<bean:write name="bookingForm" property="technicalError"/>
				</logic:equal>
			</td>
		</tr><tr>
			<th colspan='6'><fmt:message key="adminedit.assistants"/></th>
		</tr><tr>
		<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
			<logic:iterate name="bookingForm" property="assistantTypeList" id="assistantTypeRecord" indexId="i">
				<tr>
					<td><bean:write name="assistantTypeRecord" property="assTypeName"/>	</td>
					<td>
						<html:select property='<%= "assistantTypeRecord["+i+"].selectedAssistant" %>'>
							<html:options name="assistantTypeRecord" collection="assistantsList" property="label" labelProperty="value"/>
						</html:select>
					</td>
				</tr>
			</logic:iterate>
		</logic:equal>
		<logic:equal name="bookingForm" property="pageStatus" value="VIEW">
			<logic:iterate name="bookingForm" property="assistantTypeList" id="assistantTypeRecord" indexId="i">
				<tr>
					<td><bean:write name="assistantTypeRecord" property="assTypeName"/></td>
					<td><bean:write name="assistantTypeRecord" property="selectedAssName"/></td>
				</tr>
			</logic:iterate>
		</logic:equal>
		</tr>
	</sakai:group_table>

	<sakai:actions>
		<logic:equal name="bookingForm" property="pageStatus" value="EDIT">
			<html:submit property="action">
				<fmt:message key="button.booking.save"/>
			</html:submit>
		</logic:equal>
		<logic:equal name="bookingForm" property="pageStatus" value="VIEW">
			<html:submit property="action">
				<fmt:message key="button.back"/>
			</html:submit>
		</logic:equal>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>

	</html:form>
</sakai:html>