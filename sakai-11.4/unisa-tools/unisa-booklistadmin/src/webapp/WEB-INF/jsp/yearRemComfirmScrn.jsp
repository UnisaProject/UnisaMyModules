<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setBundle
	basename="za.ac.unisa.lms.tools.booklistadmin.ApplicationResources" />
<sakai:html>
<h3><fmt:message key="admin.heading" /></h3>
<hr />
<br />
<sakai:instruction>
	<fmt:message key="function.confirmremovalheading" />
</sakai:instruction>
<br>
<sakai:messages />
<br>
<html:form action="booklistadmin">
	<sakai:group_table>
		<tr>
			<sakai:flat_list>
				<th><fmt:message key="function.academicyear" /></th>
				<th><fmt:message key="function.openingdate" /></th>
				<th><fmt:message key="function.closingdate" /></th>
				<logic:notEmpty name="booklistAdminForm" property="booklist">
					<logic:equal name="booklistAdminForm" property="dateRemoved" value="false">
						<logic:iterate name="booklistAdminForm" property="booklist" id="c" indexId="cindex">
							<logic:equal name="c" property="remove" value="true">
								<tr>
									<td><bean:write name="c" property="academicYear" /></td>
									<td><bean:write name="c" property="openingDate" /></td>
									<td><bean:write name="c" property="closingDate" /></td>
								</tr>
							</logic:equal>
						</logic:iterate>
					</logic:equal>
				</logic:notEmpty>
			</sakai:flat_list>
		</tr>
	</sakai:group_table>
	<br>
	<sakai:group_table>
		<tr>
			<td><sakai:actions>
				<html:submit styleClass="button" property="action">
					<fmt:message key="button.datesremoval" />
				</html:submit>
				<html:submit styleClass="button" property="action">
					<fmt:message key="button.backtodatesmanagement" />
				</html:submit>
				<html:submit styleClass="button" property="action">
					<fmt:message key="button.cancelDatesSelection" />
				</html:submit>
			</sakai:actions></td>
		</tr>
	</sakai:group_table>
</html:form>
</sakai:html>
