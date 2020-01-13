<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.maintainstaff.ApplicationResources"/>

<sakai:html>

<html:form action="/myUnisaHod">	
<sakai:messages/>
<sakai:messages message="true"/>


<sakai:group_table>
	<tr>
		<td><fmt:message key="savestanin.depertmentname"/></td>
		<td>
			<html:select property="departmentTypeForReport">
				<html:options collection="departmentList" property="value" labelProperty="label"/>
			</html:select>
		</td>   
	</tr>
	
	<tr>
		<td><fmt:message key="savestandin.networkCode"/><sakai:required/></td>
		<td><html:text name="department" property="userCode"  size="40" maxlength="40"> </html:text></td>
	</tr>	

</sakai:group_table>

<html:submit property="action">
			<fmt:message key="button.saveStandin"/>
		</html:submit>
</html:form>
</sakai:html>
