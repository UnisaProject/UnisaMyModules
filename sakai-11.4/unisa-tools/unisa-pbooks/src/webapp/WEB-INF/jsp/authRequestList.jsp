<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.pbooks.ApplicationResources"/>

     <sakai:html>
     <sakai:messages/>
     <html:form action="authorisation">
     
     <sakai:heading>
     <logic:notEqual name="bookMenuForm" property="schDirector" value="SD"><fmt:message key="function.authcodheading"/></logic:notEqual>
     <logic:equal name="bookMenuForm" property="schDirector" value="SD"><fmt:message key="function.authsdheading"/></logic:equal>
    
    </sakai:heading>
     <sakai:instruction>
		<fmt:message key="function.codauthinstr"/>
	</sakai:instruction>
  <sakai:group_heading>
			<fmt:message key="label.codAuthheading"/>
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
					<html:hidden property="cancelOption" value="codSelectAcadyear"/>
					<sakai:actions>
			<html:submit styleClass="active" property="action"><fmt:message key="button.continuecodauthorize"/></html:submit>
			<html:submit styleClass="active" property="action"><fmt:message key="button.back"/></html:submit>
		</sakai:actions>
	</html:form>				
  </sakai:html>
	