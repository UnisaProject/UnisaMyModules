<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.mylife.ApplicationResources"/>

<sakai:html>
	<html:form action="/myUnisaMylife.do" >
		<html:hidden property="atstep" value="stnostep"/>
		

	<fmt:message key="mylife.stno.info1"/>	<br/><br/>
	<fmt:message key="mylife.stno.info2"/>
	
	<p>
	<sakai:group_heading> <fmt:message key="mylife.headingstep1"/> </sakai:group_heading>
	<sakai:messages/>
	<sakai:messages message="true"/>
	
		<sakai:group_table>
		<tr>
			<td colspan="2">
				<fmt:message key="mylife.required"/> <sakai:required/>
			</td>
		</tr>
		<tr>
			<td> <fmt:message key="mylife.pers.stno"/> <sakai:required/></td>
			<td> <html:text property="studentNr" size="10" maxlength="8"></html:text> </td>
		</tr>
	</sakai:group_table>
	
	
	<sakai:actions>
		<html:submit property="action">
			<fmt:message key="button.continue"/>
		</html:submit>
	</sakai:actions>
	</html:form>
</sakai:html>