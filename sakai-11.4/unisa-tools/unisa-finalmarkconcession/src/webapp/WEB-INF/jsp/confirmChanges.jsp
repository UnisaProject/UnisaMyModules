<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.finalmarkconcession.ApplicationResources"/>

<sakai:html>		
	<html:form action="/finalMarkConcession">
		<sakai:heading><fmt:message key="heading.confirmChangesRequest"/></sakai:heading>
		<html:hidden name="finalMarkConcessionForm" property="currentPage" value="confirmChanges"/>
		<sakai:messages/>
		<sakai:messages message="true"/>		
		<sakai:group_table>
			<tr>
				<td>
					<fmt:message key="prompt.confirmChangesRequest"/>
				</td>
			</tr>
		</sakai:group_table>				
		<sakai:actions>	
			<html:submit property="act">
					<fmt:message key="button.withoutSaveRequestAuth"/>
			</html:submit>		
			<html:submit property="act">
					<fmt:message key="button.back"/>
			</html:submit>				
		</sakai:actions>
				
	</html:form>
</sakai:html>