<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">
	<fmt:message key="dailyview.heading.venue"/>
	<sakai:tool_bar>
		<html:link href="satbookMonthly.do?action=monthlyview">
			<fmt:message key="button.to.monthlyview"/>
		</html:link>
		<br>
		<html:link href="satbookMonthly.do?action=adminLink">
				<fmt:message key="adminsystemlink.heading2"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminVenues">
			<fmt:message key="adminvenues.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminmaterial">
			<fmt:message key="adminmaterial.heading"/>
		</html:link>
		<html:link href="satbookAdmin.do?action=adminBookingType">
			<fmt:message key="adminbookingtype.heading"/>
		</html:link>
				
	</sakai:tool_bar>

    <sakai:heading><fmt:message key="adminbookingtype.heading"/></sakai:heading>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<logic:notEmpty name="adminForm" property="bkngTypeList">
	<sakai:flat_list>
		<tr>
			<td><fmt:message key="adminbookingtype.tableheading.type"/></td>
			<td><fmt:message key="adminmaterial.tableheading.active"/></td>
		</tr>
		<logic:iterate name="adminForm" property="bkngTypeList" id="records" indexId="i">
			<tr>
				<td>
					<html:radio property="selectedBkngType" value="bookingTypeId" idName="records"/>
					<bean:write name="records" property="bookingTypeDesc"/>
				</td>
				<td><bean:write name="records" property="bookingTypeAct"/></td>
			</tr>
		</logic:iterate>
	</sakai:flat_list>
	</logic:notEmpty>

	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="adminbookingtype.button.add"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminbookingtype.button.edit"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="adminbookingtype.button.btypeconfirmdelete"/>
		</html:submit>
		<input type="button" value="Print" onClick="window.print()" />
	</sakai:actions>

	</html:form>
</sakai:html>


