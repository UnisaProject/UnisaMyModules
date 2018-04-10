<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c_rt" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.satbook.ApplicationResources"/>

<sakai:html>

	<html:form action="/satbookAdmin.do">
	<html:hidden property="atstep" value="scheduleemail"/>

    <sakai:heading><fmt:message key="email.confirm.heading"/></sakai:heading>

    <sakai:actions>
    	<html:submit property="action">
			<fmt:message key="button.monthlyview.adminview"/>
		</html:submit>
		<html:submit property="action">
			<fmt:message key="button.to.monthlyview"/>
		</html:submit>
	</sakai:actions>
    </html:form>
</sakai:html>