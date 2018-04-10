<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

     <sakai:html>
     <sakai:messages/>
     <html:form action="deanAuthorisation">
     
 <sakai:heading>
<fmt:message key="function.authdeanheading"/>
<logic:equal name="bookMenuForm" property="typeOfBookList" value="P"> <fmt:message key="function.booklist"/><bean:write name="bookMenuForm" property="deptName"/></logic:equal>
<logic:equal name="bookMenuForm" property="typeOfBookList" value="R"> <fmt:message key="function.booklistforR"/><bean:write name="bookMenuForm" property="deptName"/></logic:equal>
<logic:equal name="bookMenuForm" property="typeOfBookList" value="E"> <fmt:message key="function.booklistforE"/><bean:write name="bookMenuForm" property="deptName"/></logic:equal>
</sakai:heading>

     <sakai:instruction>
		<fmt:message key="function.deanauthinstr"/></sakai:instruction>

  <sakai:group_heading>
			<fmt:message key="label.deanAuthheading"/>
		</sakai:group_heading>

 <sakai:group_table>
       <tr><td><fmt:message key="label.prescribedbooksinstr"/></td></tr>
						<logic:iterate name="bookMenuForm" property="presCourseList" id="record" indexId="index">						
							<tr>	
								<td><html:radio name="bookMenuForm" property="selectedCourse" idName="record" value="label"></html:radio></td>
								<td><bean:write name="record" property="value"/>/<bean:write name="bookMenuForm" property="acadyear"/></td>
							</tr>
						</logic:iterate>
						<tr><td><fmt:message key="label.recommendeddbooksinstr"/></td></tr>
						<logic:iterate name="bookMenuForm" property="recCourseList" id="record" indexId="index">
							<tr>	
								<td><html:radio name="bookMenuForm" property="selectedCourse" idName="record" value="label"></html:radio></td>
								<td><bean:write name="record" property="value"/>/<bean:write name="bookMenuForm" property="acadyear"/></td>
							</tr>
						</logic:iterate>
						<tr><td><fmt:message key="label.ereservesinstr"/></td></tr>
						<logic:iterate name="bookMenuForm" property="eresCourseList" id="record" indexId="index">
							<tr>	
								<td><html:radio name="bookMenuForm" property="selectedCourse" idName="record" value="label"></html:radio></td>
								<td><bean:write name="record" property="value"/>/<bean:write name="bookMenuForm" property="acadyear"/></td>
							</tr>
						</logic:iterate>
					</sakai:group_table>
						   <html:hidden property="cancelOption" value="deanmainview"/>
						<html:submit styleClass="active" property="action"><fmt:message key="button.continue"/></html:submit>
						<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
						</html:form>
						</sakai:html>
					