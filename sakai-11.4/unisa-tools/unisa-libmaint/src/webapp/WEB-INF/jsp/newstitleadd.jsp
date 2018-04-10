<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.libmaint.ApplicationResources"/>

<sakai:html>
<html:form action="/maintenance"> 
	<html:hidden property="atstep" value="newstitle"/>

	<sakai:group_heading>
		<fmt:message key="heading"/> <br>
		<fmt:message key="news.add"/>
	</sakai:group_heading>
	</p>
	
	<p>
	<sakai:messages/>
	<sakai:messages message="true"/>
	</p>
	
	<table>
		<table>
			<tr>
				<td><fmt:message key="news"/> <sakai:required/> </td>
				<td><html:text property="newsTitleDesc" size="50" maxlength="255"/></td>
			</tr>
			<tr>
				<td><fmt:message key="enabled"/> <sakai:required/> </td>
				<td>
					<html:select property="enabled">
						<html:options collection="enabledOptionList" property="value" labelProperty="label"/>
					</html:select>
				</td>
			</tr>
		</table>
		
		</p>
		<sakai:actions>
			<html:submit property="act">
				<fmt:message key="button.save"/>
			</html:submit>
			<html:submit property="act">
				<fmt:message key="button.cancel"/>
			</html:submit>
		</sakai:actions>
	</table>
</html:form>
</sakai:html>