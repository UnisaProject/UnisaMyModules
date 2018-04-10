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
	<html:hidden property="atstep" value="5"/>
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
		<b><fmt:message key="satbook.step5"/></b><br></br>
		<fmt:message key="satbook.step6"/><br></br>
	</logic:equal>
	<logic:equal name="bookingForm" property="systemID" value="${bookingForm.venbook}" >
		<sakai:heading>
			<fmt:message key="book.add"/>
		</sakai:heading>
		<fmt:message key="venbook.step1"/><br></br>
		<fmt:message key="venbook.step2"/><br></br>
		<b><fmt:message key="venbook.step3"/></b><br></br>
		<fmt:message key="venbook.step4"/><br></br>	
	</logic:equal>


	<sakai:messages/>
	<sakai:messages message="true"/>

	<sakai:instruction><fmt:message key="bookingstep5.instruction"/></sakai:instruction>
	<sakai:group_heading><fmt:message key="bookingstep5.classrooms"/></sakai:group_heading>
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="bookingstep5.button.all"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="bookingstep5.button.deselect"/>
		</html:submit>
	</sakai:actions>
	<sakai:group_table>
			<logic:iterate name="bookingForm" property="classrooms" id="value" indexId="i">
				<tr>
					<td>
						<html:multibox property="selectedClassrooms"><bean:write name="i"/></html:multibox>
						<bean:write name="value" property="value"/>
					</td>
				</tr>
			</logic:iterate>
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
