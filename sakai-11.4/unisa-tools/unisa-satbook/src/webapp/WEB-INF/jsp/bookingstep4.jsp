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
	<html:hidden property="atstep" value="4"/>
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
		<b><fmt:message key="satbook.step4"/></b><br></br>
		<fmt:message key="satbook.step5"/><br></br>
		<fmt:message key="satbook.step6"/><br></br>
	</logic:equal>

	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction><fmt:message key="bookingstep4.instruction"/></sakai:instruction>
	<sakai:group_heading><fmt:message key="bookingstep4.visitors"/></sakai:group_heading>

		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="bookingstep4.visitor1"/> &nbsp;
					<html:text property="visitor1" size="30" maxlength="40"/>
				</td>
			</tr> <tr>
				<td>
					<fmt:message key="bookingstep4.visitor2"/>	&nbsp;
					<html:text property="visitor2" size="30" maxlength="40"/>
				</td>
			</tr> <tr>
				<td>
					<fmt:message key="bookingstep4.visitor3"/>	&nbsp;
					<html:text property="visitor3" size="30" maxlength="40"/>
				</td>
			</tr> <tr>
				<td>
					<fmt:message key="bookingstep4.visitor4"/>	&nbsp;
					<html:text property="visitor4" size="30" maxlength="40"/>
				</td>
			</tr>
		</sakai:group_table>

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