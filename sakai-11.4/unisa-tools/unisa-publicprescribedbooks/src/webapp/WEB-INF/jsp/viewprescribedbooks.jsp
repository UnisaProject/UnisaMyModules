<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.publicprescribedbooks.ApplicationResources"/>
<sakai:html>
	<sakai:messages/>
	<sakai:heading><fmt:message key="prescribedbooks.display.heading"/> for <bean:write name="publicPrescribedBooksForm"
	 property="academicYear"/></sakai:heading>
		<sakai:instruction>
				<fmt:message key="prescribedbooks.input.instruction3"/><p></p>
				<fmt:message key="prescribedbooks.input.instruction4"/><br>
				<fmt:message key="prescribedbooks.input.instruction5"/>
				<fmt:message key="prescribedbooks.input.instruction6"/>
				<fmt:message key="prescribedbooks.input.instruction7"/>
				<fmt:message key="prescribedbooks.input.instruction8"/>
		</sakai:instruction>
		<html:form action="prescribedbooks">
		
	<sakai:group_table>
			<tr>
				<td><fmt:message key="prescribedbooks.label.date"/></td>
				<td><bean:write name="publicPrescribedBooksForm" property="listDate"/></td>
			</tr>
			
			<sakai:flat_list>
			<logic:notEmpty name="publicPrescribedBooksForm" property="prescribedBooksList">
			<c:set var="temp" value="0"></c:set>
			<logic:iterate name="publicPrescribedBooksForm" property="prescribedBooksList" id="c" indexId="cindex">
				<c:if test="${c.courseCode != temp}">
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						
					</tr>
					<tr bgcolor="#BDBDBD">
						<td><fmt:message key="prescribedbooks.label.coursecode"/></td>
						<td><bean:write name="c" property="courseCode"/>&nbsp;:&nbsp;<bean:write name="c" property="description"/></td>
						
					</tr>
					<c:set var="temp" value="${c.courseCode}"></c:set>
				</c:if>
					<logic:equal name="c" property="coloured" value="1">
					<tr>
						<td><bean:write name="c" property="count"/>&nbsp;<fmt:message key="prescribedbooks.label.title"/></td>
						<td><bean:write name="c" property="title"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.author"/></td>
						<td><bean:write name="c" property="author"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.yearpublished"/></td>
						<td><bean:write name="c" property="yearPublished"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.edition"/></td>
						<td><bean:write name="c" property="edition"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.publisher"/></td>
						<td><bean:write name="c" property="publisher"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.booknotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
					<tr>	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.coursenotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
					<tr>	
						<td></td>
					</tr>
					</logic:equal>
					<logic:equal name="c" property="coloured" value="0">
					<tr bgcolor="#eeeeee">
						<td><bean:write name="c" property="count"/>&nbsp;<fmt:message key="prescribedbooks.label.title"/></td>
						<td><bean:write name="c" property="title"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.author"/></td>
						<td><bean:write name="c" property="author"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.yearpublished"/></td>
						<td><bean:write name="c" property="yearPublished"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.edition"/></td>
						<td><bean:write name="c" property="edition"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.publisher"/></td>
						<td><bean:write name="c" property="publisher"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.booknotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="prescribedbooks.label.coursenotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
					<tr bgcolor="#eeeeee">	
						<td></td>
					</tr>
					</logic:equal>					
			</logic:iterate>
			</logic:notEmpty>						
			</sakai:flat_list>
		</sakai:group_table>
		<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>