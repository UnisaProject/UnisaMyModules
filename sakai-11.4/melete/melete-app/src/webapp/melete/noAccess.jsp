<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://sakaiproject.org/jsf/sakai" prefix="sakai" %>
<f:view>
	<sakai:view_container title="#{msgs.title_bar}">
	<sakai:view_content>
		<h:outputText styleClass="alertMessage" value="#{msgs.auth_failed}" />
	</sakai:view_content>
	</sakai:view_container>
</f:view>