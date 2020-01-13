<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:messages/>
	<sakai:messages message="true"/>
	<sakai:heading>
		<fmt:message key="eshop.delete.title"/>
	</sakai:heading>
	<html:form action="eshop.do?action=deleteActionTaken">
		<sakai:group_table>
			<tr>
				<td>
					<b><c_rt:out value="${eshopform.advert.addHeading}"/></b> (<c_rt:out value="${eshopform.advert.courseCode}"/>)
				</td>
			</tr>
			<tr>
				<td>
					<c_rt:out value="${eshopform.advert.addText}"/>
				</td>
			</tr>
			<tr>
				<td>
					<c_rt:out value="${eshopform.advert.contactDetails}"/>
				</td>
			</tr>
		</sakai:group_table>
		<sakai:actions>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.delete"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.cancel"/></html:submit>
		</sakai:actions>
	</html:form>
</sakai:html>