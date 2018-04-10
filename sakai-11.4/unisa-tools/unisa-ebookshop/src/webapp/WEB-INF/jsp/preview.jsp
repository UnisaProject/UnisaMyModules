<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.ebookshop.ApplicationResources"/>

<sakai:html>
	<sakai:heading>
		<fmt:message key="eshop.preview.title"/>
	</sakai:heading>

	<html:form action="eshop.do?action=processPreviewAction">

		<b><c_rt:out value="${eshopform.advert.addHeading}"/></b>, (<c_rt:out value="${eshopform.advert.courseDescription}"/>),&nbsp;<c_rt:out value="${eshopform.advert.courseCode}"/><br/>
		<c_rt:out value="${eshopform.advert.addText}"/><br/>
		<c_rt:out value="${eshopform.advert.contactDetails}"/><br/>
		<fmt:message key="eshop.preview.dateadded"/><c_rt:out value="${eshopform.advert.dateAdded}"/>

		<br/><br/>
		<sakai:actions>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.submit"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.cancel"/></html:submit>
			<html:submit property="buttonClicked"><fmt:message key="eshop.button.revise"/></html:submit>
		</sakai:actions>

	</html:form>


</sakai:html>