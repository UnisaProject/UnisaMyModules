<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
	        	<sakai:tool_bar>
                <html:link href="prebook.do?action=firstScreen"><fmt:message key="link.addnew"/></html:link>      
        	</sakai:tool_bar>
		<fmt:message key="prescribedbooks.heading"/> <bean:write name="bookMenuForm" property="courseId"/>
		<html:link href="https://mydev.unisa.ac.za/mercury/unisa.pbooks/mercury/default.do"><fmt:message key="prescribedbooks.link.manage"/></html:link>
	</sakai:heading>
	<sakai:instruction><fmt:message key="prescribedbooks.equiries"/></sakai:instruction>
	<logic:notEmpty name="bookMenuForm" property="prebooksList">
	<logic:iterate name="bookMenuForm" property="prebooksList" id="c" indexId="cindex">
		<sakai:flat_list>
			<logic:equal name="c" property="coloured" value="1">
				<tr>
					<td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
					<td><bean:write name="c" property="title"/></td>
				</tr>
				<tr>
					<td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
					<td><bean:write name="c" property="author"/></td>
				</tr>
				<tr>
					<td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
					<td><bean:write name="c" property="pubYear"/></td>
				</tr>
				<tr>
					<td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
					<td><bean:write name="c" property="edition"/></td>
				</tr>
				<tr>
					<td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
					<td><bean:write name="c" property="publisher"/></td>
				</tr>
				<logic:notEqual name="c" property="bookNotes" value=" ">
					<tr>
						<td width="15%"><fmt:message key="prescribedbooks.label.bnotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
				</logic:notEqual>
				<logic:notEqual name="c" property="courseNotes" value=" ">
					<tr>
						<td width="15%"><fmt:message key="prescribedbooks.label.cnotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
				</logic:notEqual>
			</logic:equal>
			<logic:equal name="c" property="coloured" value="0">
				<tr bgcolor="#eeeeee">
					<td width="15%"><fmt:message key="prescribedbooks.label.title"/></td>
					<td><bean:write name="c" property="title"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
					<td width="15%"><fmt:message key="prescribedbooks.label.author"/></td>
					<td><bean:write name="c" property="author"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
					<td width="15%"><fmt:message key="prescribedbooks.label.year"/></td>
					<td><bean:write name="c" property="pubYear"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
					<td width="15%"><fmt:message key="prescribedbooks.label.edition"/></td>
					<td><bean:write name="c" property="edition"/></td>
				</tr>
				<tr bgcolor="#eeeeee">
					<td width="15%"><fmt:message key="prescribedbooks.label.publisher"/></td>
					<td><bean:write name="c" property="publisher"/></td>
				</tr>
				<logic:notEqual name="c" property="bookNotes" value=" ">
					<tr bgcolor="#eeeeee">
						<td width="15%"><fmt:message key="prescribedbooks.label.bnotes"/></td>
						<td><bean:write name="c" property="bookNotes"/></td>
					</tr>
				</logic:notEqual>
				<logic:notEqual name="c" property="courseNotes" value=" ">
					<tr bgcolor="#eeeeee">
						<td width="15%"><fmt:message key="prescribedbooks.label.cnotes"/></td>
						<td><bean:write name="c" property="courseNotes"/></td>
					</tr>
				</logic:notEqual>
			</logic:equal>
		</sakai:flat_list>
	</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="bookMenuForm" property="prebooksList">
		<tr>
			<td><fmt:message key="prescribedbooks.instruction.nobooks"/></td>
		</tr>
	</logic:empty>
</sakai:html>