<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>

<fmt:setBundle basename="za.ac.unisa.lms.tools.results.ApplicationResources"/>

<sakai:html>
		<html:form action="/displayresults">
		<html:hidden property="action" value="display"/>
		<html:hidden property="currentPage" value="input"/>
		<%@page import="java.util.*" %>
		<% String fromSystem ="";
			if (request.getAttribute("fromSystem")==null)
				fromSystem="";
			else
				fromSystem=(String)request.getAttribute("fromSystem");
		%>
		<input type=hidden name =fromSystem value="<%=fromSystem%>"></input>
		<sakai:heading>Examination Results</sakai:heading>
		<sakai:messages/>
		<sakai:messages message="true"/>

		<sakai:instruction>Please enter the relevant information required for the displaying of results.</sakai:instruction>
		<sakai:group_table>
			<tr>
				<td><label>Exam Year:</label></td>
				<td><html:text property="examYear" size="6" maxlength="4"/></td>
			</tr>
			<tr>
				<td><label>Exam Period:</label></td>
				<td>
					<html:select property="examPeriod">
					<html:options collection="examPeriods" property="code" labelProperty="engDescription"/>
					</html:select>
				</td>
			</tr>
			<logic:equal name="resultsform" property="studentUser" value="false">
 				<tr>
					<td><label><bean:message key="prompt.results.studentnr"/>: </label></td>
					<td><html:text property="studentNr" size="10" maxlength="8"/></td>
				</tr>
			</logic:equal>
		</sakai:group_table>
		<br/>
		<sakai:actions>
		<html:submit value="Display"/>
		</sakai:actions>
		</html:form>
</sakai:html>
