<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://sakaiproject.org/struts/sakai" prefix="sakai" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="za.ac.unisa.lms.tools.telecentre.ApplicationResources"/>
<sakai:html>
	<logic:equal name="telecentreForm" property="extStu"  value="1">
	<sakai:instruction>
	   <fmt:message key="telecenter.confirmationOfExtendedHours"/>
	</sakai:instruction>
	<sakai:messages/>
	<sakai:instruction>
	   <fmt:message key="extend.student.hrs"/>
	</sakai:instruction>
	<html:form action="/telecentre.do">	
	   <sakai:flat_list>
			<tr>
			     <th><fmt:message key="telecentre.studentNumber"/></th>
			     <th><fmt:message key="telecentre.studentName"/></th>
			     <th><fmt:message key="telecentre.monthYearExtended"/></th>
			     <th><fmt:message key="telecentre.newHourLimit"/></th>
			     <th><fmt:message key="telecentre.previousHourLimit"/></th>
			</tr>
			<tr>
			     <td><bean:write name ="telecentreForm" property="studentNr"/></td>
			     <td><bean:write name ="telecentreForm" property="studentName"/></td>
			     <td><bean:write name ="telecentreForm" property="monthYearString"/></td>
			     <td><bean:write name ="telecentreForm" property="newHourLimit"/></td>
			     <td><bean:write name ="telecentreForm" property="previousHourLimit"/></td>
			</tr>
	   </sakai:flat_list>
	   <sakai:group_table>
	         <tr>
	             <td><sakai:actions>
	             <logic:equal name="telecentreForm" property="hoursSubmitted"  value="0">
			           <html:submit styleClass="button" property="action">
			              <fmt:message key="telecentre.submit.hrs"/>
			            </html:submit>
			      </logic:equal>
			            <html:submit styleClass="button" property="action">
			                  <fmt:message key="telecentre.back"/>
			            </html:submit>
		               </sakai:actions>
		          </td>	
			  </tr>
	   </sakai:group_table>
	</html:form >
	</logic:equal>
	<logic:equal name="telecentreForm" property="extStu"  value="0">
	  <sakai:instruction>
	      <fmt:message key="telecenter.confirmationOfExtendedHours"/>
	  </sakai:instruction>
	  <sakai:messages/>
	  <sakai:instruction>
	      <fmt:message key="telecentre.extendHours"/>
	  </sakai:instruction>
	  <html:form action="/telecentre.do">	
	   <sakai:flat_list>
			<tr>
			     <th><fmt:message key="telecentre.Code"/></th>
			     <th><fmt:message key="telecentre.studentName"/></th>
			     <th><fmt:message key="telecentre.monthYearExtended"/></th>
			     <th><fmt:message key="telecentre.newHourLimit"/></th>
			     <th><fmt:message key="telecentre.previousHourLimit"/></th>
			</tr>
			<tr>
			     <td><bean:write name ="telecentreForm" property="telecentreCode"/></td>
			     <td><bean:write name ="telecentreForm" property="telecentreName"/></td>
			     <td><bean:write name ="telecentreForm" property="monthYearString"/></td>
			     <td><bean:write name ="telecentreForm" property="newHourLimit"/></td>
			     <td><bean:write name ="telecentreForm" property="previousHourLimit"/></td>
			</tr>
	   </sakai:flat_list>
	   <sakai:group_table>
	         <tr>
	             <td><sakai:actions>
	                 <logic:equal name="telecentreForm" property="hoursSubmitted"  value="0">
			           <html:submit styleClass="button" property="action">
			              <fmt:message key="telecentre.submit.hrs"/>
			            </html:submit>
			         </logic:equal>
			            <html:submit styleClass="button" property="action">
			                  <fmt:message key="telecentre.back"/>
			            </html:submit>
		               </sakai:actions>
		          </td>	
			  </tr>
	   </sakai:group_table>
	</html:form >
	</logic:equal>
</sakai:html>   